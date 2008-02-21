package ch.screenconcept.artoz.importer;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.product.ArtozProduct;
import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;

/**
 * Imports the ArtozProducts
 * @author Pascal Naef
 *
 */
public class ProductImportJob extends GeneratedProductImportJob
{
	private static Logger log = Logger.getLogger( ProductImportJob.class.getName() );

	@Override
	protected CronJobResult performCronJob(CronJob cronjob) throws AbortCronJobException
	{
		final FileImportCronjob fileImportCronjob = (FileImportCronjob) cronjob;
		final FileInputStream fis;
		final ProductCSVFileParser productParser;
		try
		{
			fis = new FileInputStream(new File(fileImportCronjob.getFileDirectory(), fileImportCronjob.getFileName()));
			productParser = new ProductCSVFileParser(fis, ';', 5);
			while (!productParser.isClosed())
			{
				createOrUpdateArtozProduct((NewsletterCustomerCSVFileLine) productParser.readLine());
			}
		}
		catch (Exception e)
		{
			throw new AbortCronJobException(e.getMessage());
		}

		return fileImportCronjob.getFinishedResult(true);
	}
	
	private void createOrUpdateArtozProduct(NewsletterCustomerCSVFileLine line){
		final String code = line.getColumn(0);
		ArtozProduct product = ArtozProduct.findArtozProduct(code);
		
		if (product == null){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(ArtozProduct.CODE, code);
			ArtozProduct.createArtozProduct(params);
		}
	}
}
