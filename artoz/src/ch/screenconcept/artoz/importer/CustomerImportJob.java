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

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;

import com.exedio.campaign.constants.CampaignConstants;

import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.jalo.user.UserGroup;
import de.hybris.platform.jalo.user.UserManager;

public class CustomerImportJob extends GeneratedCustomerImportJob
{
	private static Logger log = Logger.getLogger(CustomerImportJob.class.getName());

	@Override
	protected CronJobResult performCronJob(CronJob cronJob) throws AbortCronJobException
	{
		final FileImportCronjob fileImportCronjob = (FileImportCronjob) cronJob;
		final FileInputStream fis;
		final CustomerCSVFileParser customerParser;
		try
		{
			fis = new FileInputStream(new File(fileImportCronjob.getFileDirectory(), fileImportCronjob.getFileName()));
			customerParser = new CustomerCSVFileParser(fis, ';', 13);
			while (!customerParser.isClosed())
			{
				createOrUpdateCustomer((CustomerCSVFileLine) customerParser.readLine());
			}
		}
		catch (Exception e)
		{
			throw new AbortCronJobException(e.getMessage());
		}

		return fileImportCronjob.getFinishedResult(true);
	}

	private void createOrUpdateCustomer(CustomerCSVFileLine line)
				throws JaloInvalidParameterException, JaloSecurityException, JaloBusinessException
	{
		
		final String login = line.getUID();
		Customer customer;
		try
		{
			customer = (Customer) UserManager.getInstance().getUserByLogin(login);
		}
		catch (JaloItemNotFoundException je)
		{
			customer = UserManager.getInstance().createCustomer(login);
		}

		updateCustomer(customer, line);
	}

	private void updateCustomer(Customer cus, CustomerCSVFileLine line) throws JaloSecurityException,
				JaloBusinessException
	{
		cus.setAttribute(Customer.NAME, true);
		cus.setAttribute(CampaignConstants.Attributes.User.EMAILADDRESS, line.getEMail());
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(Address.COUNTRY, line.getCountry());
		params.put(Address.POSTALCODE, line.getZipCode());
		params.put(Address.FIRSTNAME, line.getFristname());
		params.put(Address.FAX, line.getFax());
		params.put(Address.EMAIL, line.getEMail());
		final Address adress = cus.createAddress(params);
		
		Collection<UserGroup> userGroups = UserManager.getInstance().getAllUserGroups();

		for (UserGroup group : userGroups)
		{
			if (group.getUID().equals(ArtozConstants.NEWSLETTERGROUP))
			{
				cus.addToGroup(group);
				break;
			}
		}
	}
}
