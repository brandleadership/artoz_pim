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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;

import com.exedio.campaign.constants.CampaignConstants;
import com.exedio.campaign.jalo.Newsletter;

import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.jalo.user.Title;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserGroup;
import de.hybris.platform.jalo.user.UserManager;

/**
 * Imports the customers for the newsletter extension.
 * 
 * @author Pascal Naef
 */
public class NewsletterCustomerImportJob extends GeneratedNewsletterCustomerImportJob
{
	private static Logger log = Logger.getLogger(NewsletterCustomerImportJob.class.getName());

	@Override
	protected CronJobResult performCronJob(CronJob cronjob) throws AbortCronJobException
	{
		final MediaNewsletterImportCronjob importCronjob = (MediaNewsletterImportCronjob) cronjob;

		final NewsletterCustomerCSVFileParser newsletterParser;
		final Newsletter newsletter = importCronjob.getNewsletter();
		try
		{
			newsletterParser = new NewsletterCustomerCSVFileParser(importCronjob.getMedia().getDataFromStream(), ';', 7);
			while (!newsletterParser.isClosed())
			{
				createOrUpdateNewsletterCustomer((NewsletterCustomerCSVFileLine) newsletterParser.readLine(),
							newsletter);
			}
		}
		catch (Exception e)
		{
			throw new AbortCronJobException(e.getMessage());
		}

		return importCronjob.getFinishedResult(true);

	}

	private void createOrUpdateNewsletterCustomer(NewsletterCustomerCSVFileLine line, Newsletter newsletter)
				throws JaloInvalidParameterException, JaloSecurityException, JaloBusinessException
	{
		log.info("line: " + line.getColumn(0) + " " + line.getColumn(1) + " " + line.getColumn(2) + " "
					+ line.getColumn(3) + " " + line.getColumn(4));

		// Don't check fax because the email address identifies the customer
		Customer customer;
		if (line.getEMail() != null && !line.getEMail().equals(""))
			customer = findCustomerByMailOrFax(line.getEMail(), null);
		else
			customer = getCustomerByName(line.getName() + "_" + line.getFirstname());

		if (customer == null)
		{
			customer = UserManager.getInstance().createCustomer(
						"nlc_" + ArtozConstants.NumberSeries.getNewsletterCustomerNumber());
			customer.setAttribute(CampaignConstants.Attributes.User.NEWSLETTERSUBSCRIBED, true);
		}

		log.info("UID " + customer.getUID());
		updateCustomer(customer, line, newsletter);
	}

	private void updateCustomer(Customer cus, NewsletterCustomerCSVFileLine line, Newsletter newsletter)
				throws JaloSecurityException, JaloBusinessException
	{
		final String email = line.getEMail();
		final Language customerLanguage = C2LManager.getInstance().getLanguageByIsoCode(line.getLanguageIsoCode());
		cus.setAttribute(Customer.LOGIN_DISABLED, true);
		cus.setAttribute(Customer.NAME, line.getName() + "_" + line.getFirstname());
		cus.setAttribute(Customer.SESSION_LANGUAGE, customerLanguage);	
		cus.setAttribute(CampaignConstants.Attributes.User.EMAILADDRESS, email);
		Boolean isNewsletterSubscribed = (Boolean) cus.getAttribute(CampaignConstants.Attributes.User.NEWSLETTERSUBSCRIBED);
		if (isNewsletterSubscribed == null || isNewsletterSubscribed)
			cus.setAttribute(CampaignConstants.Attributes.Customer.NEWSLETTERS, Collections.singletonList(newsletter));

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(Address.TITLE, getTitleByName(line.getTitle(), customerLanguage));
		params.put(Address.LASTNAME, line.getName());
		params.put(Address.FIRSTNAME, line.getFirstname());
		params.put(Address.COMPANY, line.getCompany());
		params.put(Address.FAX, line.getFax());

		Address address = cus.getDefaultPaymentAddress();
		if (address != null)
			address.setAllAttributes(params);
		else
			cus.setDefaultPaymentAddress(cus.createAddress(params));

		// Replace all user groups and add the newsletter one
		UserGroup userGroup = UserManager.getInstance().getUserGroupByGroupID(ArtozConstants.NEWSLETTERGROUP);
		Set<UserGroup> usergroups = new HashSet<UserGroup>();
		usergroups.add(userGroup);
		cus.setGroups(usergroups);
	}

	public Customer findCustomerByMailOrFax(String mail, String fax)
	{
		boolean emptyMail = mail == null || mail.equals("");
		boolean emptyFax = fax == null || fax.equals("");

		if (!emptyMail || !emptyFax)
		{
			Map<String, Object> value = new HashMap<String, Object>();
			if (!emptyMail)
				value.put("email", mail);
			if (!emptyFax)
				value.put("fax", fax);

			StringBuffer query = new StringBuffer();
			query.append("SELECT {u." + User.PK + "} FROM {"
						+ TypeManager.getInstance().getComposedType(Customer.class).getCode() + " as u} ");
			if (!emptyFax)
				query.append(", {" + TypeManager.getInstance().getComposedType(Address.class).getCode() + " as a} ");
			query.append("WHERE ");
			if (!emptyFax)
			{
				query.append("{a." + Address.USER + "} = {u." + User.PK + "} AND ");
				if (!emptyMail)
					query.append("(");
				query.append("{a." + Address.FAX + "} = ?fax");
				if (!emptyMail)
					query.append(" OR ");
			}
			if (!emptyMail)
			{
				query.append("{u." + CampaignConstants.Attributes.User.EMAILADDRESS + "} = ?email");
				if (!emptyFax)
					query.append(")");
			}

			final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(query.toString(),
						value, Collections.singletonList(Customer.class), true, true, 0, -1);
			if (res.getResult().isEmpty())
				return null;
			return (Customer) res.getResult().get(0);
		}
		return null;
	}

	private Title getTitleByName(String name, Language language)
	{
		Map<String, String> value = new HashMap<String, String>();
		value.put("name", name);

		String query = "SELECT { " + Title.PK + "} FROM {"
					+ TypeManager.getInstance().getComposedType(Title.class).getCode() + "} WHERE {" + Title.NAME
					+ "} = ?name";

		final SessionContext ctx = JaloSession.getCurrentSession().createSessionContext();
		ctx.setLanguage(language);
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(ctx, query.toString(),
					value, Collections.singletonList(Title.class), true, true, 0, -1);

		if (res.getResult().isEmpty())
			return null;
		return (Title) res.getResult().get(0);
	}

	private Customer getCustomerByName(String name)
	{
		Map<String, String> value = new HashMap<String, String>();
		value.put("name", name);

		String query = "SELECT { " + Customer.PK + "} FROM {"
					+ TypeManager.getInstance().getComposedType(Customer.class).getCode() + "} WHERE {" + Customer.NAME
					+ "} = ?name";

		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(query.toString(), value,
					Collections.singletonList(Customer.class), true, true, 0, -1);

		if (res.getResult().isEmpty())
			return null;
		return (Customer) res.getResult().get(0);
	}
}
