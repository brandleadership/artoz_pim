package ch.screenconcept.artoz.importer;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;

import com.exedio.campaign.constants.CampaignConstants;

import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserManager;

/**
 * Imports the customers for the newsletter extension. 
 * @author Pascal Naef
 */
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
			fis = new FileInputStream(new File(fileImportCronjob.getFileDirectory(), fileImportCronjob.getFileName()));
			newsletterParser = new NewsletterCustomerCSVFileParser(fis, ';', 5);
			while (!newsletterParser.isClosed())
			{
				createOrUpdateNewsletterCustomer((NewsletterCustomerCSVFileLine) newsletterParser.readLine());
			}
		}
		catch (Exception e)
		{
			throw new AbortCronJobException(e.getMessage());
		}

		return fileImportCronjob.getFinishedResult(true);
	}

	private void createOrUpdateNewsletterCustomer(NewsletterCustomerCSVFileLine line)
				throws JaloInvalidParameterException, JaloSecurityException, JaloBusinessException
	{
		log.info("line: " + line.getColumn(0) + " " + line.getColumn(1) + " " + line.getColumn(2) + " "
					+ line.getColumn(3) + " " + line.getColumn(4));

		final String login = line.getColumn(1) + "_" + line.getColumn(2);
		User existingCustomer = UserManager.getInstance().getUserByLogin(login);

		if (existingCustomer != null && existingCustomer instanceof Customer)
			updateCustomer((Customer) existingCustomer, line);
		else
			updateCustomer(UserManager.getInstance().createCustomer(login), line);
	}

	private void updateCustomer(Customer cus, NewsletterCustomerCSVFileLine line) throws JaloSecurityException, JaloBusinessException
	{
		final String email = line.getColumn(3);
		cus.setAttribute(Customer.LOGIN_DISABLED, true);
		cus.setAttribute(CampaignConstants.Attributes.User.EMAILADDRESS, email);
	}
}
