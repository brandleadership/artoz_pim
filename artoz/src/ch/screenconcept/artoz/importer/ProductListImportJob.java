package ch.screenconcept.artoz.importer;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.product.ArtozProduct;
import ch.screenconcept.artoz.product.ProductList;

import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;

public class ProductListImportJob extends GeneratedProductListImportJob
{
	private static Logger log = Logger.getLogger(ProductListImportJob.class.getName());

	@Override
	protected CronJobResult performCronJob(CronJob cronjob) throws AbortCronJobException
	{
		final ProductImportMediaImportCronjob importCronjob = (ProductImportMediaImportCronjob) cronjob;

		final ProductListCSVFileParser productListParser;
		final ProductList productList = importCronjob.getProductList();
		try
		{
			productListParser = new ProductListCSVFileParser(importCronjob.getMedia().getDataFromStream(), ';', 1, true);
			while (!productListParser.isClosed())
			{
				ProductListCSVFileLine line = (ProductListCSVFileLine) productListParser.readLine();
				productList.getArtozProducts().add(ArtozProduct.findArtozProduct(line.getCode()));
			}
		}
		catch (Exception e)
		{
			throw new AbortCronJobException(e.getMessage());
		}

		return importCronjob.getFinishedResult(true);
	}

}
