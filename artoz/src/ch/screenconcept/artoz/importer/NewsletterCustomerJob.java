package ch.screenconcept.artoz.importer;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;

import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;

public class NewsletterCustomerJob extends GeneratedNewsletterCustomerJob
{
	private static Logger log = Logger.getLogger( NewsletterCustomerJob.class.getName() );

	@Override
	protected CronJobResult performCronJob(CronJob cronjob) throws AbortCronJobException
	{
		final FileImportCronjob fileImportCronjob = (FileImportCronjob) cronjob;
		FileInputStream fis;
		try
		{
			fis = new FileInputStream(new File(fileImportCronjob.getFileDirectory()));
			NewsletterCustomerCSVFileParser newsletterParser = new NewsletterCustomerCSVFileParser(fis, 5);
		}
		catch (Exception e)
		{
			throw new AbortCronJobException(e.getMessage());
		}
		return null;
	}

	
}
