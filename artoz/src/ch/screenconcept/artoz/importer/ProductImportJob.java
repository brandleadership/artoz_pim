package ch.screenconcept.artoz.importer;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.prices.ArtozPriceRow;
import ch.screenconcept.artoz.prices.PriceRowValues;
import ch.screenconcept.artoz.product.ArtozProduct;
import de.hybris.platform.catalog.constants.CatalogConstants;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.category.jalo.CategoryManager;
import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
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
			productParser = new ProductCSVFileParser(fis, ';', 86);
			while (!productParser.isClosed())
			{
				createOrUpdateArtozProduct((ProductCSVFileLine) productParser.readLine());
			}
			deleteNotUpdatedProductsAndPriceRows();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new AbortCronJobException(e.getMessage());
		}

		return fileImportCronjob.getFinishedResult(true);
	}

	private void createOrUpdateArtozProduct(ProductCSVFileLine line) throws JaloInvalidParameterException,
				JaloSecurityException, JaloBusinessException
	{

		if (line != null)
		{
			//Get the active catalog version
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

			//Create name array with text in all 6 languages
			final Map<Language, String> names = new HashMap<Language, String>();
			names.put(ArtozConstants.Languages.getGerman(), line.getShortTextDE());
			names.put(ArtozConstants.Languages.getEnglish(), line.getShortTextEN());
			names.put(ArtozConstants.Languages.getFrench(), line.getShortTextFR());
			names.put(ArtozConstants.Languages.getItalian(), line.getShortTextIT());
			names.put(ArtozConstants.Languages.getSpanish(), line.getShortTextES());
			names.put(ArtozConstants.Languages.getPortuguese(), line.getShortTextPT());
			
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

			// Add product to category
			Collection<Category> categories = CategoryManager.getInstance().getCategoriesByCode(line.getCategory());
			final Category category;
			if (categories == null || categories.isEmpty())
			{
				category = CategoryManager.getInstance().createCategory(line.getCategory());
				category.setName(line.getCategoryName());
			}
			else
				category = categories.iterator().next();

			Collection<Category> productCategories = catalogManager.getCategoriesByProduct(catalogVersion, product);

			//Update categories; Delete old once.
			boolean hasCategory = false;
			for (Category productCategory : productCategories)
				if (productCategory.getCode().equals(category.getCode()))
					hasCategory = true;
				else
					productCategory.removeProduct(product);
			if (!hasCategory)
				category.addProduct(product);

			catalogManager.setCatalogVersion(category, catalogVersion);
			catalogManager.setApprovalStatus(product, EnumerationManager.getInstance().getEnumerationValue(
						CatalogConstants.TC.ARTICLEAPPROVALSTATUS,
						CatalogConstants.Enumerations.ArticleApprovalStatus.APPROVED));
		}
	}

	private void deleteNotUpdatedProductsAndPriceRows() throws JaloInvalidParameterException, JaloSecurityException, JaloBusinessException
	{
		for (ArtozProduct product : ArtozProduct.findNotUpdatedProducts(ArtozConstants.NumberSeries
					.getCurrentProductImportNumber()))
			product.setAttribute(CatalogConstants.Attributes.Product.OFFLINEDATE, new Date());

		for (ArtozPriceRow priceRow : ArtozPriceRow.findNotUpdatedPriceRows(ArtozConstants.NumberSeries
					.getCurrentProductImportNumber()))
			priceRow.remove();
	}

	private PriceRowValues createPriceRowValues(Long quantity, Double price, Integer unitFactor, Currency currency)
	{
		PriceRowValues priceRowValues = new PriceRowValues();
		priceRowValues.setQuantity(quantity);
		priceRowValues.setPrice(price);
		priceRowValues.setUnitFactor(unitFactor);
		priceRowValues.setCurrency(currency);
		return priceRowValues;
	}

	private List<PriceRowValues> getPrices(ProductCSVFileLine line)
	{

		List<PriceRowValues> priceRowsValues = new ArrayList<PriceRowValues>();
		// CHF
		if (line.getPlchf01() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf01() == null ? 1 : line.getPlstchf01(), line
						.getPlchf01(), line.getPlCHFUnit(), ArtozConstants.Currencies.getCHF()));

		if (line.getPlstchf02() != null && line.getPlchf02() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf02(), line.getPlchf02(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF()));

		if (line.getPlstchf03() != null && line.getPlchf03() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf03(), line.getPlchf03(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF()));

		if (line.getPlstchf04() != null && line.getPlchf04() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf04(), line.getPlchf04(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF()));

		if (line.getPlstchf05() != null && line.getPlchf05() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf05(), line.getPlchf05(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF()));

		if (line.getPlstchf06() != null && line.getPlchf06() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf06(), line.getPlchf06(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF()));

		if (line.getPlstchf07() != null && line.getPlchf07() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf07(), line.getPlchf07(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF()));

		if (line.getPlstchf08() != null && line.getPlchf08() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf08(), line.getPlchf08(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF()));

		if (line.getPlstchf09() != null && line.getPlchf09() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf09(), line.getPlchf09(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF()));

		if (line.getPlstchf10() != null && line.getPlchf10() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstchf10(), line.getPlchf10(), line.getPlCHFUnit(),
						ArtozConstants.Currencies.getCHF()));

		// Euro
		if (line.getPleur01() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur01() == null ? 1 : line.getPlsteur01(), line
						.getPleur01(), line.getPlEURUnit(), ArtozConstants.Currencies.getEUR()));

		if (line.getPlsteur02() != null && line.getPleur02() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur02(), line.getPleur02(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR()));

		if (line.getPlsteur03() != null && line.getPleur03() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur03(), line.getPleur03(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR()));

		if (line.getPlsteur04() != null && line.getPleur04() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur04(), line.getPleur04(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR()));

		if (line.getPlsteur05() != null && line.getPleur05() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur05(), line.getPleur05(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR()));

		if (line.getPlsteur06() != null && line.getPleur06() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur06(), line.getPleur06(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR()));

		if (line.getPlsteur07() != null && line.getPleur07() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur07(), line.getPleur07(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR()));

		if (line.getPlsteur08() != null && line.getPleur08() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur08(), line.getPleur08(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR()));

		if (line.getPlsteur08() != null && line.getPleur09() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur09(), line.getPleur09(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR()));

		if (line.getPlsteur10() != null && line.getPleur10() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlsteur10(), line.getPleur10(), line.getPlEURUnit(),
						ArtozConstants.Currencies.getEUR()));

		// Pound
		if (line.getPlgbp01() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp01() == null ? 1 : line.getPlstgbp01(), line
						.getPlgbp01(), line.getPlGBPUnit(), ArtozConstants.Currencies.getGBP()));

		if (line.getPlstgbp02() != null && line.getPlgbp02() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp02(), line.getPlgbp02(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP()));

		if (line.getPlstgbp03() != null && line.getPlgbp03() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp03(), line.getPlgbp03(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP()));

		if (line.getPlstgbp04() != null && line.getPlgbp04() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp04(), line.getPlgbp04(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP()));

		if (line.getPlstgbp05() != null && line.getPlgbp05() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp05(), line.getPlgbp05(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP()));

		if (line.getPlstgbp06() != null && line.getPlgbp06() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp06(), line.getPlgbp06(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP()));

		if (line.getPlstgbp07() != null && line.getPlgbp07() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp07(), line.getPlgbp07(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP()));

		if (line.getPlstgbp08() != null && line.getPlgbp08() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp08(), line.getPlgbp08(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP()));

		if (line.getPlstgbp09() != null && line.getPlgbp09() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp09(), line.getPlgbp09(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP()));

		if (line.getPlstgbp10() != null && line.getPlgbp10() != null)
			priceRowsValues.add(createPriceRowValues(line.getPlstgbp10(), line.getPlgbp10(), line.getPlGBPUnit(),
						ArtozConstants.Currencies.getGBP()));

		return priceRowsValues;
	}
}
