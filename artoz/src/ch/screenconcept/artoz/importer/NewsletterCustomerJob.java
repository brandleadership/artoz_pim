package ch.screenconcept.artoz.importer;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;

import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;

public class NewsletterCustomerJob extends GeneratedNewsletterCustomerJob
{
	private static Logger log = Logger.getLogger(NewsletterCustomerJob.class.getName());

	@Override
	protected CronJobResult performCronJob(CronJob cronjob) throws AbortCronJobException
	{
		final FileImportCronjob fileImportCronjob = (FileImportCronjob) cronjob;
		final FileInputStream fis;
		final NewsletterCustomerCSVFileParser newsletterParser;
		try
		{
			log.info("start import" + fileImportCronjob.getFileDirectory() + " " + fileImportCronjob.getFileName() );
			fis = new FileInputStream(new File(fileImportCronjob.getFileDirectory(), fileImportCronjob.getFileName()));
			newsletterParser = new NewsletterCustomerCSVFileParser(fis, ';', 5);
			for (NewsletterCustomerCSVFileLine line; !newsletterParser.isClosed();)
			{
				line = (NewsletterCustomerCSVFileLine) newsletterParser.readLine();
				createOrUpdateNewsletterCustomer(line);
			}
		}
		catch (Exception e)
		{
			throw new AbortCronJobException(e.getMessage());
		}
	
		return fileImportCronjob.getFinishedResult(true);
	}

	private void createOrUpdateNewsletterCustomer(NewsletterCustomerCSVFileLine line)
	{
		log.info("line: " + line.getColumn(0) + " " + line.getColumn(1) + " " + line.getColumn(2) + " " + line.getColumn(3) + " " + line.getColumn(4));
	}

}
