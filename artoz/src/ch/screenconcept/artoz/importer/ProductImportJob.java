package ch.screenconcept.artoz.importer;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.prices.ArtozPriceRow;
import ch.screenconcept.artoz.prices.PriceRowValues;
import ch.screenconcept.artoz.product.ArtozProduct;
import de.hybris.platform.catalog.constants.CatalogConstants;
import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.c2l.Language;
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
			productParser = new ProductCSVFileParser(fis, ';', 83);
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
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(ArtozProduct.CODE, line.getCode());
			params.put(ArtozProduct.NAME, line.getCode());
			params.put(ArtozProduct.MATERIALGROUP, line.getMaterialGroup());
			params.put(ArtozProduct.ITEMTYPEGROUP, line.getItemTypeGroup());
			params.put(ArtozProduct.GRAMMAGE, line.getGrammage());
			params.put(ArtozProduct.NEWNESSCODE, line.getNewnessCode());
			params.put(ArtozProduct.DISTRIBUTIONSTATUS, line.getStatus());
			params.put(ArtozProduct.MDAVIEW, line.getMdaView());
			params.put(CatalogConstants.Attributes.Product.EAN, line.getEAN());
			params.put(ArtozProduct.DIN, line.getDIN());
			params.put(ArtozProduct.DIMENSIONS, line.getDimensions());
			params.put(ArtozProduct.SALESUNIT, line.getSalesUnit());
			params.put(ArtozProduct.UPDATECOUNTER, ArtozConstants.NumberSeries.getCurrentProductImportNumber());

			final Map<Language, String> descriptions = new HashMap<Language, String>();
			descriptions.put(ArtozConstants.Languages.getGerman(), line.getShortTextDE());
			descriptions.put(ArtozConstants.Languages.getEnglish(), line.getShortTextEN());
			descriptions.put(ArtozConstants.Languages.getFrench(), line.getShortTextFR());
			descriptions.put(ArtozConstants.Languages.getItalian(), line.getShortTextIT());
			descriptions.put(ArtozConstants.Languages.getSpanish(), line.getShortTextES());
			descriptions.put(ArtozConstants.Languages.getPortuguese(), line.getShortTextPT());

			ArtozProduct product = ArtozProduct.findArtozProduct(line.getCode());

			if (product == null){
				log.info("Create new Product: " + params.get(ArtozProduct.CODE) );
				ArtozProduct.createArtozProduct(params, descriptions, getPrices(line));				
			}
			else {
				log.info("Update Product: " + product.getCode());
				product.update(params, descriptions, getPrices(line));				
			}
		}
	}

	private void deleteNotUpdatedProductsAndPriceRows() throws ConsistencyCheckException
	{
		for (ArtozProduct product : ArtozProduct.findNotUpdatedProducts(ArtozConstants.NumberSeries
					.getCurrentProductImportNumber()))
			product.remove();
		//sef offline

		for (ArtozPriceRow priceRow : ArtozPriceRow.findNotUpdatedPriceRows(ArtozConstants.NumberSeries
					.getCurrentProductImportNumber())){
			log.info("remove price row " + priceRow.getMinqtd() + " " + priceRow.getCurrency().getIsoCode() + " " + priceRow.getProduct().getCode());
			priceRow.remove();
		}
			
	}

	private List<PriceRowValues> getPrices(ProductCSVFileLine line)
	{

		List<PriceRowValues> priceRowsValues = new ArrayList<PriceRowValues>();

		// CHF
		PriceRowValues valuesCHF01 = new PriceRowValues();
		valuesCHF01.setQuantity(line.getPlstchf01() == null ? 1 : line.getPlstchf01());
		valuesCHF01.setPrice(line.getPlchf01());
		valuesCHF01.setCurrency(ArtozConstants.Currencies.getCHF());
		priceRowsValues.add(valuesCHF01);

		if (line.getPlstchf02() != null)
		{
			PriceRowValues valuesCHF02 = new PriceRowValues();
			valuesCHF02.setQuantity(line.getPlstchf02());
			valuesCHF02.setPrice(line.getPlchf02());
			valuesCHF02.setCurrency(ArtozConstants.Currencies.getCHF());
			priceRowsValues.add(valuesCHF02);
		}

		if (line.getPlstchf03() != null)
		{
			//priceRowsValues.add( generate( line.getPlchf03() ) );
			PriceRowValues valuesCHF03 = new PriceRowValues();
			valuesCHF03.setQuantity(line.getPlstchf03());
			valuesCHF03.setPrice(line.getPlchf03());
			valuesCHF03.setCurrency(ArtozConstants.Currencies.getCHF());
			priceRowsValues.add(valuesCHF03);
		}

		if (line.getPlstchf04() != null)
		{
			PriceRowValues valuesCHF04 = new PriceRowValues();
			valuesCHF04.setQuantity(line.getPlstchf04());
			valuesCHF04.setPrice(line.getPlchf04());
			valuesCHF04.setCurrency(ArtozConstants.Currencies.getCHF());
			priceRowsValues.add(valuesCHF04);
		}

		if (line.getPlstchf05() != null)
		{
			PriceRowValues valuesCHF05 = new PriceRowValues();
			valuesCHF05.setQuantity(line.getPlstchf05());
			valuesCHF05.setPrice(line.getPlchf05());
			valuesCHF05.setCurrency(ArtozConstants.Currencies.getCHF());
			priceRowsValues.add(valuesCHF05);
		}

		if (line.getPlstchf04() != null)
		{
			PriceRowValues valuesCHF04 = new PriceRowValues();
			valuesCHF04.setQuantity(line.getPlstchf04());
			valuesCHF04.setPrice(line.getPlchf04());
			valuesCHF04.setCurrency(ArtozConstants.Currencies.getCHF());
			priceRowsValues.add(valuesCHF04);
		}

		if (line.getPlstchf04() != null)
		{
			PriceRowValues valuesCHF06 = new PriceRowValues();
			valuesCHF06.setQuantity(line.getPlstchf06());
			valuesCHF06.setPrice(line.getPlchf06());
			valuesCHF06.setCurrency(ArtozConstants.Currencies.getCHF());
			priceRowsValues.add(valuesCHF06);
		}

		if (line.getPlstchf07() != null)
		{
			PriceRowValues valuesCHF07 = new PriceRowValues();
			valuesCHF07.setQuantity(line.getPlstchf07());
			valuesCHF07.setPrice(line.getPlchf07());
			valuesCHF07.setCurrency(ArtozConstants.Currencies.getCHF());
			priceRowsValues.add(valuesCHF07);
		}

		if (line.getPlstchf08() != null)
		{
			PriceRowValues valuesCHF08 = new PriceRowValues();
			valuesCHF08.setQuantity(line.getPlstchf08());
			valuesCHF08.setPrice(line.getPlchf08());
			valuesCHF08.setCurrency(ArtozConstants.Currencies.getCHF());
			priceRowsValues.add(valuesCHF08);
		}

		if (line.getPlstchf09() != null)
		{
			PriceRowValues valuesCHF09 = new PriceRowValues();
			valuesCHF09.setQuantity(line.getPlstchf09());
			valuesCHF09.setPrice(line.getPlchf09());
			valuesCHF09.setCurrency(ArtozConstants.Currencies.getCHF());
			priceRowsValues.add(valuesCHF09);
		}

		if (line.getPlstchf10() != null)
		{
			PriceRowValues valuesCHF10 = new PriceRowValues();
			valuesCHF10.setQuantity(line.getPlstchf10());
			valuesCHF10.setPrice(line.getPlchf10());
			valuesCHF10.setCurrency(ArtozConstants.Currencies.getCHF());
			priceRowsValues.add(valuesCHF10);
		}

		// Euro
		PriceRowValues valuesEUR01 = new PriceRowValues();
		valuesEUR01.setQuantity(line.getPlsteur01() == null ? 1 : line.getPlsteur01());
		valuesEUR01.setPrice(line.getPleur01());
		valuesEUR01.setCurrency(ArtozConstants.Currencies.getEUR());
		priceRowsValues.add(valuesEUR01);

		if (line.getPlsteur02() != null)
		{
			PriceRowValues valuesEUR02 = new PriceRowValues();
			valuesEUR02.setQuantity(line.getPlsteur02());
			valuesEUR02.setPrice(line.getPleur02());
			valuesEUR02.setCurrency(ArtozConstants.Currencies.getEUR());
			priceRowsValues.add(valuesEUR02);
		}

		if (line.getPlsteur03() != null)
		{
			PriceRowValues valuesEUR03 = new PriceRowValues();
			valuesEUR03.setQuantity(line.getPlsteur03());
			valuesEUR03.setPrice(line.getPleur03());
			valuesEUR03.setCurrency(ArtozConstants.Currencies.getEUR());
			priceRowsValues.add(valuesEUR03);
		}

		if (line.getPlsteur04() != null)
		{
			PriceRowValues valuesEUR04 = new PriceRowValues();
			valuesEUR04.setQuantity(line.getPlsteur04());
			valuesEUR04.setPrice(line.getPleur04());
			valuesEUR04.setCurrency(ArtozConstants.Currencies.getEUR());
			priceRowsValues.add(valuesEUR04);
		}

		if (line.getPlsteur05() != null)
		{
			PriceRowValues valuesEUR05 = new PriceRowValues();
			valuesEUR05.setQuantity(line.getPlsteur05());
			valuesEUR05.setPrice(line.getPleur05());
			valuesEUR05.setCurrency(ArtozConstants.Currencies.getEUR());
			priceRowsValues.add(valuesEUR05);
		}

		if (line.getPlsteur06() != null)
		{
			PriceRowValues valuesEUR06 = new PriceRowValues();
			valuesEUR06.setQuantity(line.getPlsteur06());
			valuesEUR06.setPrice(line.getPleur06());
			valuesEUR06.setCurrency(ArtozConstants.Currencies.getEUR());
			priceRowsValues.add(valuesEUR06);
		}

		if (line.getPlsteur07() != null)
		{
			PriceRowValues valuesEUR07 = new PriceRowValues();
			valuesEUR07.setQuantity(line.getPlsteur07());
			valuesEUR07.setPrice(line.getPleur07());
			valuesEUR07.setCurrency(ArtozConstants.Currencies.getEUR());
			priceRowsValues.add(valuesEUR07);
		}

		if (line.getPlsteur08() != null)
		{
			PriceRowValues valuesEUR08 = new PriceRowValues();
			valuesEUR08.setQuantity(line.getPlsteur08());
			valuesEUR08.setPrice(line.getPleur08());
			valuesEUR08.setCurrency(ArtozConstants.Currencies.getEUR());
			priceRowsValues.add(valuesEUR08);
		}

		if (line.getPlsteur09() != null)
		{
			PriceRowValues valuesEUR09 = new PriceRowValues();
			valuesEUR09.setQuantity(line.getPlsteur09());
			valuesEUR09.setPrice(line.getPleur09());
			valuesEUR09.setCurrency(ArtozConstants.Currencies.getEUR());
			priceRowsValues.add(valuesEUR09);
		}

		if (line.getPlsteur10() != null)
		{
			PriceRowValues valuesEUR10 = new PriceRowValues();
			valuesEUR10.setQuantity(line.getPlsteur10());
			valuesEUR10.setPrice(line.getPleur10());
			valuesEUR10.setCurrency(ArtozConstants.Currencies.getEUR());
			priceRowsValues.add(valuesEUR10);
		}

		// Pound
		PriceRowValues valuesGBP01 = new PriceRowValues();
		valuesGBP01.setQuantity(line.getPlstgbp01() == null ? 1 : line.getPlstgbp01());
		valuesGBP01.setPrice(line.getPlgbp01());
		valuesGBP01.setCurrency(ArtozConstants.Currencies.getGBP());
		priceRowsValues.add(valuesGBP01);

		if (line.getPlstgbp02() != null)
		{
			PriceRowValues valuesGBP02 = new PriceRowValues();
			valuesGBP02.setQuantity(line.getPlstgbp02());
			valuesGBP02.setPrice(line.getPlgbp02());
			valuesGBP02.setCurrency(ArtozConstants.Currencies.getGBP());
			priceRowsValues.add(valuesGBP02);
		}

		if (line.getPlstgbp03() != null)
		{
			PriceRowValues valuesGBP03 = new PriceRowValues();
			valuesGBP03.setQuantity(line.getPlstgbp03());
			valuesGBP03.setPrice(line.getPlgbp03());
			valuesGBP03.setCurrency(ArtozConstants.Currencies.getGBP());
			priceRowsValues.add(valuesGBP03);
		}

		if (line.getPlstgbp04() != null)
		{
			PriceRowValues valuesGBP04 = new PriceRowValues();
			valuesGBP04.setQuantity(line.getPlstgbp04());
			valuesGBP04.setPrice(line.getPlgbp04());
			valuesGBP04.setCurrency(ArtozConstants.Currencies.getGBP());
			priceRowsValues.add(valuesGBP04);
		}

		if (line.getPlstgbp05() != null)
		{
			PriceRowValues valuesGBP05 = new PriceRowValues();
			valuesGBP05.setQuantity(line.getPlstgbp05());
			valuesGBP05.setPrice(line.getPlgbp05());
			valuesGBP05.setCurrency(ArtozConstants.Currencies.getGBP());
			priceRowsValues.add(valuesGBP05);
		}

		if (line.getPlstgbp06() != null)
		{
			PriceRowValues valuesGBP06 = new PriceRowValues();
			valuesGBP06.setQuantity(line.getPlstgbp06());
			valuesGBP06.setPrice(line.getPlgbp06());
			valuesGBP06.setCurrency(ArtozConstants.Currencies.getGBP());
			priceRowsValues.add(valuesGBP06);
		}

		if (line.getPlstgbp07() != null)
		{
			PriceRowValues valuesGBP07 = new PriceRowValues();
			valuesGBP07.setQuantity(line.getPlstgbp07());
			valuesGBP07.setPrice(line.getPlgbp07());
			valuesGBP07.setCurrency(ArtozConstants.Currencies.getGBP());
			priceRowsValues.add(valuesGBP07);
		}

		if (line.getPlstgbp08() != null)
		{
			PriceRowValues valuesGBP08 = new PriceRowValues();
			valuesGBP08.setQuantity(line.getPlstgbp08());
			valuesGBP08.setPrice(line.getPlgbp08());
			valuesGBP08.setCurrency(ArtozConstants.Currencies.getGBP());
			priceRowsValues.add(valuesGBP08);
		}

		if (line.getPlstgbp09() != null)
		{
			PriceRowValues valuesGBP09 = new PriceRowValues();
			valuesGBP09.setQuantity(line.getPlstgbp09());
			valuesGBP09.setPrice(line.getPlgbp09());
			valuesGBP09.setCurrency(ArtozConstants.Currencies.getGBP());
			priceRowsValues.add(valuesGBP09);
		}

		if (line.getPlstgbp10() != null)
		{
			PriceRowValues valuesGBP10 = new PriceRowValues();
			valuesGBP10.setQuantity(line.getPlstgbp10());
			valuesGBP10.setPrice(line.getPlgbp10());
			valuesGBP10.setCurrency(ArtozConstants.Currencies.getGBP());
			priceRowsValues.add(valuesGBP10);
		}

		return priceRowsValues;
	}
}
