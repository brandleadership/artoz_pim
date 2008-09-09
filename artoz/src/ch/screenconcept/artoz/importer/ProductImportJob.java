package ch.screenconcept.artoz.importer;

/*
 The extension "Artoz" is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.jalo.ArtozManager;
import ch.screenconcept.artoz.prices.ArtozMSRPrice;
import ch.screenconcept.artoz.prices.ArtozPriceRow;
import ch.screenconcept.artoz.prices.PriceRowValues;
import ch.screenconcept.artoz.product.ArtozProduct;
import ch.screenconcept.artoz.product.Sort;
import de.hybris.platform.catalog.constants.CatalogConstants;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.category.jalo.CategoryManager;
import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;
import de.hybris.platform.europe1.jalo.Europe1PriceFactory;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.security.JaloSecurityException;

/**
 * Imports the ArtozProducts
 * 
 * @author Pascal Naef
 * 
 */
public class ProductImportJob extends GeneratedProductImportJob
{
	private static Logger log = Logger.getLogger(ProductImportJob.class.getName());

	private final EnumerationValue userPriceGroupCHF = Europe1PriceFactory.getInstance().getUserPriceGroup(
				ArtozConstants.UserPriceGroups.CHF_STANDARD_NAME);

	private final EnumerationValue userPriceGroupEUR = Europe1PriceFactory.getInstance().getUserPriceGroup(
				ArtozConstants.UserPriceGroups.EUR_STANDARD_NAME);

	private final EnumerationValue userPriceGroupEUREX = Europe1PriceFactory.getInstance().getUserPriceGroup(
				ArtozConstants.UserPriceGroups.EUR_EXTENDED_NAME);

	private final EnumerationValue userPriceGroupGBP = Europe1PriceFactory.getInstance().getUserPriceGroup(
				ArtozConstants.UserPriceGroups.GBP_STANDARD_NAME);

	private final EnumerationValue userPriceGroupUSD = Europe1PriceFactory.getInstance().getUserPriceGroup(
				ArtozConstants.UserPriceGroups.USD_STANDARD_NAME);

	@Override
	protected CronJobResult performCronJob(CronJob cronJob) throws AbortCronJobException
	{
		// Get new update Number
		ArtozConstants.NumberSeries.getNewProductImportNumber();

		final FileImportCronjob fileImportCronjob = (FileImportCronjob) cronJob;
		final FileInputStream fis;
		final ProductCSVFileParser productParser;
		try
		{
			fis = new FileInputStream(fileImportCronjob.getFile());
			productParser = new ProductCSVFileParser(fis, ';', 138);
			while (!productParser.isClosed())
			{
				ProductCSVFileLine line = (ProductCSVFileLine) productParser.readLine();
				try
				{
					createOrUpdateArtozProduct(line);
				}
				catch (JaloInvalidParameterException jipe)
				{
					logFailedData(line, jipe);
				}
				catch (JaloSecurityException jse)
				{
					logFailedData(line, jse);
				}
				catch (JaloBusinessException jbe)
				{
					logFailedData(line, jbe);
				}
				catch (NullPointerException npe)
				{
					logFailedData(line, npe);
				}
			}
			deleteNotUpdatedProductsAndPriceRows();
		}
		catch (Exception e)
		{
			throw new AbortCronJobException(e.getMessage());
		}

		return fileImportCronjob.getFinishedResult(true);
	}

	private void logFailedData(ProductCSVFileLine line, Exception e)
	{
		log.warn("Article not imported: " + line.getCode() + "; Failure: " + e.getMessage());
	}

	private void createOrUpdateArtozProduct(ProductCSVFileLine line) throws JaloInvalidParameterException,
				JaloSecurityException, JaloBusinessException
	{

		if (line != null)
		{
			// Get the active catalog version
			CatalogManager catalogManager = CatalogManager.getInstance();
			CatalogVersion catalogVersion = catalogManager.getCatalog(ArtozConstants.STANDARDCATALOG)
						.getActiveCatalogVersion();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(CatalogConstants.Attributes.Product.CATALOGVERSION, catalogVersion);
			params.put(ArtozProduct.UNIT, ArtozConstants.Units.getPieces());
			params.put(ArtozProduct.CODE, line.getCode());
			params.put(ArtozProduct.MATERIALGROUP, line.getMaterialGroup());
			params.put(ArtozProduct.ITEMTYPEGROUP, line.getItemTypeGroup());
			params.put(ArtozProduct.GRAMMAGE, line.getGrammage());
			params.put(ArtozProduct.NEWNESSCODE, line.getNewnessCode());
			params.put(ArtozProduct.DISTRIBUTIONSTATUS, line.getStatus());
			params.put(ArtozProduct.MDAVIEW, line.getMdaView());
			params.put(CatalogConstants.Attributes.Product.EAN, line.getEAN());
			params.put(CatalogConstants.Attributes.Product.NUMBERCONTENTUNITS, line.getSalesUnit());
			params.put(CatalogConstants.Attributes.Product.CONTENTUNIT, ArtozConstants.Units.getPieces());
			params.put(CatalogConstants.Attributes.Product.OFFLINEDATE, null);
			params.put(ArtozProduct.DIN, line.getDIN());
			params.put(ArtozProduct.DIMENSIONS, line.getDimensions());
			params.put(ArtozProduct.UPDATECOUNTER, ArtozConstants.NumberSeries.getCurrentProductImportNumber());

			// Create name array with text in all 6 languages
			final Map<Language, String> names = new HashMap<Language, String>();
			names.put(ArtozConstants.Languages.getGerman(), line.getShortTextDE());
			names.put(ArtozConstants.Languages.getEnglish(), line.getShortTextEN());
			names.put(ArtozConstants.Languages.getFrench(), line.getShortTextFR());
			names.put(ArtozConstants.Languages.getItalian(), line.getShortTextIT());
			names.put(ArtozConstants.Languages.getSpanish(), line.getShortTextES());

			ArtozProduct product = ArtozProduct.findArtozProduct(line.getCode());

			if (product == null)
			{
				log.info("Create new Product: " + params.get(ArtozProduct.CODE));
				product = ArtozProduct.createArtozProduct(params, names, getPrices(line));
			}
			else
			{
				log.info("Update Product: " + product.getCode());
				product.update(params, names, getPrices(line));
			}

			if (line.getSort() != null)
			{
				// Add product to category
				final Collection<Category> sortCategories = CategoryManager.getInstance().getCategoriesByCode(
							line.getSort());
				final Category sortCategory;
				if (sortCategories == null || sortCategories.isEmpty())
				{
					Map<String, String> sortCatParams = new HashMap<String, String>();
					sortCatParams.put(Sort.CODE, line.getSort());
					sortCatParams.put(Sort.NAME, line.getSort());
					sortCategory = ArtozManager.getInstance().createSort(sortCatParams);
				}
				else
					sortCategory = sortCategories.iterator().next();

				// Update categories; Delete old once.
				boolean hasSortCategory = false;
				for (Category productCategory : catalogManager.getCategoriesByProduct(catalogVersion, product))
					if (productCategory.getCode().equals(sortCategory.getCode()))
						hasSortCategory = true;
					else
						productCategory.removeProduct(product);
				if (!hasSortCategory)
					sortCategory.addProduct(product);

				catalogManager.setCatalogVersion(sortCategory, catalogVersion);

				final Collection<Category> categories = CategoryManager.getInstance().getCategoriesByCode(
							line.getCategory());
				final Category category;
				if (categories == null || categories.isEmpty())
				{
					category = CategoryManager.getInstance().createCategory(line.getCategory());
					category.setName(line.getCategoryName());
				}
				else
					category = categories.iterator().next();

				// Update categories; Delete old once.
				boolean hasCategoryCategory = false;
				for (Category categoryCategory : sortCategory.getSupercategories())
					if (categoryCategory.getCode().equals(category.getCode()))
						hasCategoryCategory = true;
					else
						categoryCategory.removeProduct(product);
				if (!hasCategoryCategory)
					category.addSubcategory(sortCategory);

				catalogManager.setCatalogVersion(category, catalogVersion);
			}
			else
			{
				final Collection<Category> categories = CategoryManager.getInstance().getCategoriesByCode(
							line.getCategory());
				final Category category;
				if (categories == null || categories.isEmpty())
				{
					category = CategoryManager.getInstance().createCategory(line.getCategory());
					category.setName(line.getCategoryName());
				}
				else
					category = categories.iterator().next();

				// Update categories; Delete old once.
				boolean hasCategory = false;
				for (Category productCategory : catalogManager.getCategoriesByProduct(catalogVersion, product))
					if (productCategory.getCode().equals(category.getCode()))
						hasCategory = true;
					else
						productCategory.removeProduct(product);
				if (!hasCategory)
					category.addProduct(product);

				catalogManager.setCatalogVersion(category, catalogVersion);
			}

			catalogManager.setApprovalStatus(product, EnumerationManager.getInstance().getEnumerationValue(
						CatalogConstants.TC.ARTICLEAPPROVALSTATUS,
						CatalogConstants.Enumerations.ArticleApprovalStatus.APPROVED));

			// MSRPrice
			product.setMsrPrices(createMSRPrice(line));
		}
	}

	private void deleteNotUpdatedProductsAndPriceRows() throws JaloInvalidParameterException, JaloSecurityException,
				JaloBusinessException
	{
		for (ArtozProduct product : ArtozProduct.findNotUpdatedProducts(ArtozConstants.NumberSeries
					.getCurrentProductImportNumber()))
			product.setAttribute(CatalogConstants.Attributes.Product.OFFLINEDATE, new Date());

		for (ArtozPriceRow priceRow : ArtozPriceRow.findNotUpdatedPriceRows(ArtozConstants.NumberSeries
					.getCurrentProductImportNumber()))
			priceRow.remove();

		for (ArtozMSRPrice msrPrice : ArtozMSRPrice.findNotUpdatedArtozMSRPrice(ArtozConstants.NumberSeries
					.getCurrentProductImportNumber()))
			msrPrice.remove();
	}

	private PriceRowValues createPriceRowValues(Long quantity, Double price, Integer unitFactor, Currency currency,
				EnumerationValue userPriceGroup)
	{
		PriceRowValues priceRowValues = new PriceRowValues();
		priceRowValues.setQuantity(quantity);
		priceRowValues.setPrice(price);
		priceRowValues.setUnitFactor(unitFactor);
		priceRowValues.setCurrency(currency);
		priceRowValues.setUserGroup(userPriceGroup);
		return priceRowValues;
	}

	private List<PriceRowValues> getPrices(ProductCSVFileLine line)
	{

		List<PriceRowValues> priceRowsValues = new ArrayList<PriceRowValues>();

		// CHF
		if (line.getPlchf01() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf01() == null ? 1 : line.getPlstchf01(), line
						.getPlchf01(), line.getPlCHFUnit(), ArtozConstants.Currencies.getCHF(), userPriceGroupCHF));

		if (line.getPlstchf02() != null && line.getPlchf02() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf02(), line.getPlchf02(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF(), userPriceGroupCHF));

		if (line.getPlstchf03() != null && line.getPlchf03() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf03(), line.getPlchf03(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF(), userPriceGroupCHF));

		if (line.getPlstchf04() != null && line.getPlchf04() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf04(), line.getPlchf04(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF(), userPriceGroupCHF));

		if (line.getPlstchf05() != null && line.getPlchf05() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf05(), line.getPlchf05(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF(), userPriceGroupCHF));

		if (line.getPlstchf06() != null && line.getPlchf06() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf06(), line.getPlchf06(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF(), userPriceGroupCHF));

		if (line.getPlstchf07() != null && line.getPlchf07() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf07(), line.getPlchf07(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF(), userPriceGroupCHF));

		if (line.getPlstchf08() != null && line.getPlchf08() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf08(), line.getPlchf08(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF(), userPriceGroupCHF));

		if (line.getPlstchf09() != null && line.getPlchf09() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf09(), line.getPlchf09(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF(), userPriceGroupCHF));

		if (line.getPlstchf10() != null && line.getPlchf10() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf10(), line.getPlchf10(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF(), userPriceGroupCHF));

		// Euro
		if (line.getPleur01() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur01() == null ? 1 : line.getPlsteur01(), line
						.getPleur01(), line.getPlEURUnit(), ArtozConstants.Currencies.getEUR(), userPriceGroupEUR));

		if (line.getPlsteur02() != null && line.getPleur02() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur02(), line.getPleur02(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR(), userPriceGroupEUR));

		if (line.getPlsteur03() != null && line.getPleur03() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur03(), line.getPleur03(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR(), userPriceGroupEUR));

		if (line.getPlsteur04() != null && line.getPleur04() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur04(), line.getPleur04(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR(), userPriceGroupEUR));

		if (line.getPlsteur05() != null && line.getPleur05() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur05(), line.getPleur05(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR(), userPriceGroupEUR));

		if (line.getPlsteur06() != null && line.getPleur06() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur06(), line.getPleur06(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR(), userPriceGroupEUR));

		if (line.getPlsteur07() != null && line.getPleur07() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur07(), line.getPleur07(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR(), userPriceGroupEUR));

		if (line.getPlsteur08() != null && line.getPleur08() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur08(), line.getPleur08(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR(), userPriceGroupEUR));

		if (line.getPlsteur08() != null && line.getPleur09() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur09(), line.getPleur09(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR(), userPriceGroupEUR));

		if (line.getPlsteur10() != null && line.getPleur10() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur10(), line.getPleur10(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR(), userPriceGroupEUR));

		// Pound
		if (line.getPlgbp01() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp01() == null ? 1 : line.getPlstgbp01(), line
						.getPlgbp01(), line.getPlGBPUnit(), ArtozConstants.Currencies.getGBP(), userPriceGroupGBP));

		if (line.getPlstgbp02() != null && line.getPlgbp02() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp02(), line.getPlgbp02(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP(), userPriceGroupGBP));

		if (line.getPlstgbp03() != null && line.getPlgbp03() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp03(), line.getPlgbp03(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP(), userPriceGroupGBP));

		if (line.getPlstgbp04() != null && line.getPlgbp04() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp04(), line.getPlgbp04(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP(), userPriceGroupGBP));

		if (line.getPlstgbp05() != null && line.getPlgbp05() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp05(), line.getPlgbp05(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP(), userPriceGroupGBP));

		if (line.getPlstgbp06() != null && line.getPlgbp06() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp06(), line.getPlgbp06(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP(), userPriceGroupGBP));

		if (line.getPlstgbp07() != null && line.getPlgbp07() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp07(), line.getPlgbp07(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP(), userPriceGroupGBP));

		if (line.getPlstgbp08() != null && line.getPlgbp08() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp08(), line.getPlgbp08(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP(), userPriceGroupGBP));

		if (line.getPlstgbp09() != null && line.getPlgbp09() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp09(), line.getPlgbp09(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP(), userPriceGroupGBP));

		if (line.getPlstgbp10() != null && line.getPlgbp10() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp10(), line.getPlgbp10(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP(), userPriceGroupGBP));

		// EUREX
		if (line.getPleurexp01() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteurexp01() == null ? 1 : line.getPlsteurexp01(), line
						.getPleurexp01(), line.getPlEUREXPUnit(), ArtozConstants.Currencies.getEUR(),
						userPriceGroupEUREX));

		if (line.getPlsteurexp02() != null && line.getPleurexp02() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteurexp02(), line.getPleurexp02(), line
						.getPlEUREXPUnit(), ArtozConstants.Currencies.getEUR(), userPriceGroupEUREX));

		if (line.getPlsteurexp03() != null && line.getPleurexp03() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteurexp03(), line.getPleurexp03(), line
						.getPlEUREXPUnit(), ArtozConstants.Currencies.getEUR(), userPriceGroupEUREX));

		if (line.getPlsteurexp04() != null && line.getPleurexp04() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteurexp04(), line.getPleurexp04(), line
						.getPlEUREXPUnit(), ArtozConstants.Currencies.getEUR(), userPriceGroupEUREX));

		if (line.getPlsteurexp05() != null && line.getPleurexp05() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteurexp05(), line.getPleurexp05(), line
						.getPlEUREXPUnit(), ArtozConstants.Currencies.getEUR(), userPriceGroupEUREX));

		if (line.getPlsteurexp06() != null && line.getPleurexp06() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteurexp06(), line.getPleurexp06(), line
						.getPlEUREXPUnit(), ArtozConstants.Currencies.getEUR(), userPriceGroupEUREX));

		if (line.getPlsteurexp07() != null && line.getPleurexp07() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteurexp07(), line.getPleurexp07(), line
						.getPlEUREXPUnit(), ArtozConstants.Currencies.getEUR(), userPriceGroupEUREX));

		if (line.getPlsteurexp08() != null && line.getPleurexp08() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteurexp08(), line.getPleurexp08(), line
						.getPlEUREXPUnit(), ArtozConstants.Currencies.getEUR(), userPriceGroupEUREX));

		if (line.getPlsteurexp09() != null && line.getPleurexp09() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteurexp09(), line.getPleurexp09(), line
						.getPlEUREXPUnit(), ArtozConstants.Currencies.getEUR(), userPriceGroupEUREX));

		if (line.getPlsteurexp10() != null && line.getPleurexp10() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteurexp10(), line.getPleurexp10(), line
						.getPlEUREXPUnit(), ArtozConstants.Currencies.getEUR(), userPriceGroupEUREX));

		// USD
		if (line.getPlusd01() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstusd01() == null ? 1 : line.getPlstusd01(), line
						.getPlusd01(), line.getPlUSDUnit(), ArtozConstants.Currencies.getUSD(), userPriceGroupUSD));

		if (line.getPlstusd02() != null && line.getPlusd02() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstusd02(), line.getPlusd02(), line.getPlUSDUnit(),
						ArtozConstants.Currencies.getUSD(), userPriceGroupUSD));

		if (line.getPlstusd03() != null && line.getPlusd03() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstusd03(), line.getPlusd03(), line.getPlUSDUnit(),
						ArtozConstants.Currencies.getUSD(), userPriceGroupUSD));

		if (line.getPlstusd04() != null && line.getPlusd04() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstusd04(), line.getPlusd04(), line.getPlUSDUnit(),
						ArtozConstants.Currencies.getUSD(), userPriceGroupUSD));

		if (line.getPlstusd05() != null && line.getPlusd05() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstusd05(), line.getPlusd05(), line.getPlUSDUnit(),
						ArtozConstants.Currencies.getUSD(), userPriceGroupUSD));

		if (line.getPlstusd06() != null && line.getPlusd06() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstusd06(), line.getPlusd06(), line.getPlUSDUnit(),
						ArtozConstants.Currencies.getUSD(), userPriceGroupUSD));

		if (line.getPlstusd07() != null && line.getPlusd07() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstusd07(), line.getPlusd07(), line.getPlUSDUnit(),
						ArtozConstants.Currencies.getUSD(), userPriceGroupUSD));

		if (line.getPlstusd08() != null && line.getPlusd08() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstusd08(), line.getPlusd08(), line.getPlUSDUnit(),
						ArtozConstants.Currencies.getUSD(), userPriceGroupUSD));

		if (line.getPlstusd09() != null && line.getPlusd09() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstusd09(), line.getPlusd09(), line.getPlUSDUnit(),
						ArtozConstants.Currencies.getUSD(), userPriceGroupUSD));

		if (line.getPlstusd10() != null && line.getPlusd10() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstusd10(), line.getPlusd10(), line.getPlUSDUnit(),
						ArtozConstants.Currencies.getUSD(), userPriceGroupUSD));

		return priceRowsValues;
	}

	private Collection createMSRPrice(ProductCSVFileLine line)
	{
		final List<ArtozMSRPrice> msrprices = new ArrayList<ArtozMSRPrice>();
		final Map<String, Object> msrParams = new HashMap<String, Object>();
		if (line.getUVPCHF() != null)
			msrprices.add(createMSRPrice(line.getUVPCHF(), ArtozConstants.Currencies.getCHF(), line.getUVPCHFUnit(),
						userPriceGroupCHF));
		if (line.getUVPEUR() != null)
			msrprices.add(createMSRPrice(line.getUVPEUR(), ArtozConstants.Currencies.getEUR(), line.getUVPEURUnit(),
						userPriceGroupEUR));
		if (line.getUVPEUREX() != null)
			msrprices.add(createMSRPrice(line.getUVPEUREX(), ArtozConstants.Currencies.getEUR(),
						line.getUVPEUREXUnit(), userPriceGroupEUREX));
		if (line.getUVPGBP() != null)
			msrprices.add(createMSRPrice(line.getUVPGBP(), ArtozConstants.Currencies.getGBP(), line.getUVPGBPUnit(),
						userPriceGroupGBP));
		if (line.getUVPUSD() != null)
			msrprices.add(createMSRPrice(line.getUVPUSD(), ArtozConstants.Currencies.getUSD(), line.getUVPUSDUnit(),
						userPriceGroupUSD));
		ArtozManager.getInstance().createArtozMSRPrice(msrParams);
		return msrprices;
	}

	private ArtozMSRPrice createMSRPrice(Double price, Currency currency, Integer quantity, EnumerationValue ug)
	{
		final Map<String, Object> msrParams = new HashMap<String, Object>();
		msrParams.put(ArtozMSRPrice.CURRENCY, currency);
		msrParams.put(ArtozMSRPrice.UG, ug);
		msrParams.put(ArtozMSRPrice.PRICE, price);
		msrParams.put(ArtozMSRPrice.UNIT, ArtozConstants.Units.getPieces());
		msrParams.put(ArtozMSRPrice.QUANTITY, quantity);
		msrParams.put(ArtozMSRPrice.UPDATECOUNTER, ArtozConstants.NumberSeries.getCurrentProductImportNumber());

		return ArtozManager.getInstance().createArtozMSRPrice(msrParams);
	}
}
