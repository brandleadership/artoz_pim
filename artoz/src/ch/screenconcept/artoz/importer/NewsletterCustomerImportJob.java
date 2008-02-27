package ch.screenconcept.artoz.importer;

import java.io.FileInputStream;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.exedio.campaign.constants.CampaignConstants;

import ch.screenconcept.artoz.constants.ArtozConstants;
import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.jalo.user.UserGroup;
import de.hybris.platform.jalo.user.UserManager;

/**
 * Imports the customers for the newsletter extension. 
 * @author Pascal Naef
 */
public class NewsletterCustomerImportJob extends GeneratedNewsletterCustomerImportJob
{
	private static Logger log = Logger.getLogger(NewsletterCustomerImportJob.class.getName());

	@Override
	protected CronJobResult performCronJob(CronJob cronjob) throws AbortCronJobException
	{
		final FileImportCronjob fileImportCronjob = (FileImportCronjob) cronjob;
		final FileInputStream fis;
		final NewsletterCustomerCSVFileParser newsletterParser;
		try
		{
			fis = new FileInputStream(fileImportCronjob.getFile());
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

		final String login = line.getName() + "_" + line.getFirstname();
		Customer customer;
		try {
			customer = (Customer) UserManager.getInstance().getUserByLogin(login);
		}
		catch ( JaloItemNotFoundException je){
			customer = UserManager.getInstance().createCustomer(login);
		}

		updateCustomer(customer, line);
	}

	private void updateCustomer(Customer cus, NewsletterCustomerCSVFileLine line) throws JaloSecurityException, JaloBusinessException
	{
		final String email = line.getEMail();
		cus.setAttribute(Customer.LOGIN_DISABLED, true);
		cus.setAttribute(CampaignConstants.Attributes.User.EMAILADDRESS, email);
		Collection<UserGroup> userGroups = UserManager.getInstance().getAllUserGroups();
		
		for (UserGroup group : userGroups){
			if (group.getUID().equals(ArtozConstants.NEWSLETTERGROUP)){
				cus.addToGroup(group);
				break;
			}
		}
	}
}
