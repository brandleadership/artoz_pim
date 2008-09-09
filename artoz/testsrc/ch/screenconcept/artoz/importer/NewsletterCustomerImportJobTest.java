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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.jalo.ArtozManager;

import com.exedio.campaign.constants.CampaignConstants;
import com.exedio.campaign.jalo.CampaignManager;
import com.exedio.campaign.jalo.Newsletter;

import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.jalo.user.UserManager;
import de.hybris.platform.testframework.JaloTest;

public class NewsletterCustomerImportJobTest extends JaloTest
{

	private Map<String, String> customerValues = new HashMap<String, String>();

	public NewsletterCustomerImportJobTest(String name)
	{
		super(name);
		
		customerValues.put("lastName", "Test");
		customerValues.put("firstName", "Hans");
		customerValues.put("eMail", "info@screenconcept.ch");
		customerValues.put("company", "ScreenConcept");
		customerValues.put("fax", "+41417484489");
	}
	
	public void testFindCustomerByMailOrFax() throws JaloInvalidParameterException, JaloSecurityException, JaloBusinessException{
		
		Newsletter newsletter = CampaignManager.getInstance().createNewsletter("TestNewsletter");
		registerForRemoval(newsletter);
					
		Customer customer = UserManager.getInstance().createCustomer(
					"nlc_" + ArtozConstants.NumberSeries.getNewsletterCustomerNumber());
		String uid = customer.getUID();
		customer.setAttribute(Customer.LOGIN_DISABLED, true);
		customer.setAttribute(Customer.NAME, customerValues.get("lastName") + "_" + customerValues.get("firstName"));
		customer.setAttribute(Customer.SESSION_LANGUAGE, ArtozConstants.Languages.getEnglish());
		customer.setAttribute(CampaignConstants.Attributes.User.EMAILADDRESS, customerValues.get("eMail"));
		customer.setAttribute(CampaignConstants.Attributes.Customer.NEWSLETTERS, Collections.singletonList(newsletter));

		Map<String, String> params = new HashMap<String, String>();
		params.put(Address.LASTNAME, customerValues.get("lastName"));
		params.put(Address.FIRSTNAME, customerValues.get("firstName"));
		params.put(Address.COMPANY, customerValues.get("company"));
		params.put(Address.FAX,customerValues.get("fax"));
		Address address = customer.createAddress(params);
		registerForRemoval(address);
		registerForRemoval(customer);
		
		Map<String, Object> paramsImportJob = new HashMap<String, Object>();
		paramsImportJob.put(NewsletterCustomerImportJob.CODE, "newsletterCustomerImportJob");
		NewsletterCustomerImportJob job = ArtozManager.getInstance().createNewsletterCustomerImportJob(paramsImportJob);
		registerForRemoval(job);
		
		final Customer foundedCustomerMail = job.findCustomerByMailOrFax(customerValues.get("eMail"), null);
		assertNotNull(foundedCustomerMail);
		assertEquals(uid, foundedCustomerMail.getUID());

		final Customer foundedCustomerFax = job.findCustomerByMailOrFax(null, customerValues.get("fax"));
		assertNotNull(foundedCustomerFax);
		assertEquals(uid, foundedCustomerFax.getUID());

		final Customer foundedCustomerMailFax = job.findCustomerByMailOrFax(customerValues.get("eMail"), customerValues.get("fax"));
		assertNotNull(foundedCustomerMailFax);
		assertEquals(uid, foundedCustomerMailFax.getUID());
	}

}
