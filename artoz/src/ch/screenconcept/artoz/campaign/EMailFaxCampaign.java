package ch.screenconcept.artoz.campaign;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.jalo.ArtozManager;

import com.exedio.campaign.jalo.CampaignConfig;
import com.exedio.campaign.jalo.CampaignContext;
import com.exedio.campaign.jalo.CampaignManager;
import com.exedio.campaign.jalo.EMailCampaignParticipation;
import com.exedio.campaign.jalo.EMailConfig;
import com.exedio.campaign.jalo.MailProxyFactory;
import com.exedio.campaign.jalo.MailTemplateException;

import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.jalo.user.User;

public class EMailFaxCampaign extends GeneratedEMailFaxCampaign
{
	private static Logger log = Logger.getLogger(EMailFaxCampaign.class.getName());

	@Override
	public void performNow() throws MailTemplateException
	{
		setProperty(LASTRUN, new Integer(getLastRunAsPrimitive() + 1));
		// final long sentBefore = getSentTotal();

		final Map values = new HashMap();
		final String query = getFilter().createQuery(values, isExcludeTargetedCustomersAsPrimitive(), this,
					getLastRunAsPrimitive());

		final int packageNo = (int) Math.floor(getSentTotal() == null ? 0 : getSentTotal() / getCountPerRun() + 1);
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(query, values,
					Arrays.asList(getFilter().getResultClasses()), true, true, packageNo, getCountPerRun());

		int i = 0;
		for (final Iterator it = res.getResult().iterator(); it.hasNext();)
		{
			final List line = (getFilter().getResultNames().length == 1) ? Collections.singletonList(it.next())
						: (List) it.next();
			sendMail(line);
		}
		addSent(res.getCount());
	}

	// --------------------------------------------------------------------------------------------------------------
	// copied private methods from EMailCampaign
	// --------------------------------------------------------------------------------------------------------------

	private void addSent(int count)
	{
		long oldCount = getSentTotalAsPrimitive();
		setProperty(SENTTOTAL, new Long(oldCount + count));
	}

	private void sendMail(List resultVector) throws MailTemplateException
	{
		final Map<String, Object> values = new HashMap<String, Object>();
		values.put(EMailCampaignParticipation.CAMPAIGN, this);
		values.put(EMailCampaignParticipation.USER, resultVector.get(0));
		values.put(EMailCampaignParticipation.CAMPAIGNRUN, getLastRunAsPrimitive());
		final EMailCampaignParticipation participation = CampaignManager.getInstance()
					.createEMailCampaignParticipation(values);

		sendMail(resultVector, CampaignManager.getInstance().getEmailAddress((User) resultVector.get(0)), participation);
	}

	public void sendMail(final List resultVector, final String recieverEMailAddress) throws MailTemplateException
	{
		sendMail(resultVector, recieverEMailAddress, null);
	}

	private void sendMail(final List resultVector, final String recieverEMailAddress,
				final EMailCampaignParticipation participation) throws MailTemplateException
	{
		final User user = (User) resultVector.get(0);
		final Language sessionLanguage = user.getSessionLanguage();
		getSession().createLocalSessionContext().setLanguage(sessionLanguage);

		final VelocityContext ctx = new VelocityContext();
		final CampaignContext campaignContext = new CampaignContext(this, participation);
		final MailProxyFactory proxyFactory = ArtozMailProxyFactory.getInstance();
		if (log.isDebugEnabled())
			log.debug("MailProxyFactory used >" + proxyFactory.getClass().getName() + "< ");

		for (int i = 0; i < getFilter().getResultNames().length; i++)
			ctx
						.put(getFilter().getResultNames()[i], proxyFactory.createMailProxy(resultVector.get(i),
									campaignContext));
		int counter = 0;
		if (getProducts() != null)
		{
			ctx.put("products", proxyFactory.createMailProxy(getProducts(), campaignContext));
		}

		if (getIntroImage() != null)
		{
			ctx.put("introimage", proxyFactory.createMailProxy(getIntroImage(), campaignContext));
		}

		if (getListNewsletterTextCollection() != null)
		{
			ctx.put("content", proxyFactory.createMailProxy(getListNewsletterTextCollection(), campaignContext));
			ctx.put("user", proxyFactory.createMailProxy(user, campaignContext));
		}

		if (getListRightNewsletterTextCollection() != null)
		{
			ctx.put("contentright", proxyFactory.createMailProxy(getListRightNewsletterTextCollection(),
						campaignContext));
		}

		//Set optOutInfo empty because the optOutInfo is fix in the template
		ctx.put("optOutInfo", "");
		
		String plainText = getPlainText() == null || getPlainText().trim().length() <= 20 ? null : EMailConfig
					.evaluate(ctx, getPlainText());
		String htmlText = getHtmlText() == null || getHtmlText().trim().length() <= 20 ? null : EMailConfig.evaluate(
					ctx, getHtmlText());

		final Map<String, Object> attributes = new HashMap<String, Object>();
		if (ArtozConstants.Enumerations.CampaignEnum.FAX.equals(this.getTyp().getCode())
					|| (ArtozConstants.Enumerations.CampaignEnum.EMAILFAX.equals(this.getTyp().getCode()))
					&& recieverEMailAddress == null && recieverEMailAddress.equals(""))
		{
			try
			{
				attributes.put(EMailFax.RECIPIENT, getFaxNumber(user));
				attributes.put(EMailFax.SENDER, EMailConfig.evaluate(ctx, getSender()));
				attributes.put(EMailFax.REPLYTO, null);
				attributes.put(EMailFax.SUBJECT, EMailConfig.evaluate(ctx, getSubject()));
				attributes.put(EMailFax.TYPE, CampaignManager.getInstance().getCampaignConfig("eMailCampaign"));
				attributes.put(EMailFax.USER, user);
				attributes.put(EMailFax.TEXT, plainText);
				attributes.put(EMailFax.TEXTASHTML, htmlText);
				attributes.put(EMailFax.FAXSERVICEUSER, getFaxUserName());
				attributes.put(EMailFax.FAXSENDER, getFaxSender());
				attributes.put(EMailFax.FAXSERVICEPASSWORD, getFaxPassword());
				attributes.put(EMailFax.FAXSERVICEADRESSE, getFaxServiceAdresse());
				attributes.put(EMailFax.FAX, true);
			}
			catch (JaloInvalidParameterException je)
			{
				throw new MailTemplateException(je.getMessage(), je);
			}
			catch (JaloSecurityException se)
			{
				throw new MailTemplateException(se.getMessage(), se);
			}
		}
		else
		{
			attributes.put(EMailFax.RECIPIENT, recieverEMailAddress);
			attributes.put(EMailFax.SENDER, EMailConfig.evaluate(ctx, getSender()));
			attributes.put(EMailFax.REPLYTO, null);
			attributes.put(EMailFax.SUBJECT, EMailConfig.evaluate(ctx, getSubject()));
			attributes.put(EMailFax.TYPE, CampaignManager.getInstance().getCampaignConfig("eMailCampaign"));
			attributes.put(EMailFax.USER, user);
			attributes.put(EMailFax.TEXT, plainText);
			attributes.put(EMailFax.TEXTASHTML, htmlText);
			attributes.put(EMailFax.FAX, false);
		}
		ArtozManager.getInstance().createEMailFax(attributes);

		if (sessionLanguage != null)
			getSession().removeLocalSessionContext();
	}

	// --------------------------------------------------------------------------------------------------------------
	// fax functionality
	// --------------------------------------------------------------------------------------------------------------

	private String getFaxNumber(User user) throws JaloInvalidParameterException, JaloSecurityException
	{
		Collection<Address> addresses = user.getAllAddresses();

		if (!addresses.isEmpty())
			return (String) addresses.iterator().next().getAttribute(Address.FAX);
		return null;
	}

}
