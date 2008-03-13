package ch.screenconcept.artoz.wizard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.campaign.EMailFaxCampaign;
import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.jalo.ArtozManager;

import com.exedio.campaign.constants.CampaignConstants;
import com.exedio.campaign.jalo.CampaignManager;
import com.exedio.campaign.jalo.Newsletter;
import com.exedio.campaign.jalo.NewsletterCollectionFilter;

import de.hybris.platform.hmc.jalo.VetoException;
import de.hybris.platform.hmc.jalo.WizardEditorContext;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.util.Config;

public class CreateEMailFaxCampaignWizard extends GeneratedCreateEMailFaxCampaignWizard
{
	private static Logger log = Logger.getLogger(CreateEMailFaxCampaignWizard.class.getName());

	private final String TAB1 = "tab.createEMailFaxCampaignWizard.one";

	private final String TAB2 = "tab.createEMailFaxCampaignWizard.two";

	private final String TAB3 = "tab.createEMailFaxCampaignWizard.three";

	private String campaignName, campaignSubject, campaignSender, campaignHTMLText, campaignText;

	private String faxServiceUser = Config.getParameter("campaign.faxServiceUser");

	private String faxServicePassword = Config.getParameter("campaign.faxServicePassword");

	private String faxServiceAdresse = Config.getParameter("campaign.faxServiceAdresse");

	private String faxSender = Config.getParameter("campaign.faxSender");
	
	private Newsletter newsletter = null;

	private Map<Language, String> campaignSubjectMap, campaignHTMLTextMap, campaignTextMap;

	private boolean campaignFaxCampaign;

	private Media campaignImportFile, campaignHTMLTemplateDECH, campaignTextTemplateDECH, campaignHTMLTemplateFRCH,
				campaignTextTemplateFRCH, campaignHTMLTemplateITCH, campaignTextTemplateITCH, campaignHTMLTemplateEN,
				campaignTextTemplateEN, campaignHTMLTemplateES, campaignTextTemplateES, campaignHTMLTemplatePT,
				campaignTextTemplatePT;

	@Override
	public String getCampaignHTMLText(SessionContext ctx)
	{
		return campaignHTMLText;
	}

	@Override
	public String getCampaignSender(SessionContext ctx)
	{
		return campaignSender;
	}

	@Override
	public String getCampaignSubject(SessionContext ctx)
	{
		return campaignSubject;
	}

	@Override
	public String getCampaignText(SessionContext ctx)
	{
		return campaignText;
	}

	@Override
	public String getCampaignName(SessionContext ctx)
	{
		return campaignName;
	}

	@Override
	public Boolean isCampaignFaxCampaign(SessionContext ctx)
	{
		return campaignFaxCampaign;
	}

	@Override
	public void setCampaignFaxCampaign(SessionContext ctx, Boolean value)
	{
		campaignFaxCampaign = value;
	}

	@Override
	public void setCampaignHTMLText(SessionContext ctx, String value)
	{
		campaignHTMLText = value.trim();
	}

	@Override
	public void setCampaignSender(SessionContext ctx, String value)
	{
		campaignSender = value.trim();
	}

	@Override
	public void setCampaignSubject(SessionContext ctx, String value)
	{
		campaignSubject = value.trim();
	}

	@Override
	public void setCampaignText(SessionContext ctx, String value)
	{
		campaignText = value.trim();
	}

	@Override
	public void setCampaignName(SessionContext ctx, String value)
	{
		campaignName = value.trim();
	}

	@Override
	public Media getCampaignImportFile(SessionContext ctx)
	{
		return campaignImportFile;
	}

	@Override
	public void setCampaignImportFile(SessionContext ctx, Media value)
	{
		campaignImportFile = value;
	}

	@Override
	public String getFaxServiceAdresse(SessionContext ctx)
	{
		return faxServiceAdresse;
	}

	@Override
	public String getFaxServicePassword(SessionContext ctx)
	{
		return faxServicePassword;
	}

	@Override
	public String getFaxServiceUser(SessionContext ctx)
	{
		return faxServiceUser;
	}

	@Override
	public void setFaxServiceAdresse(SessionContext ctx, String value)
	{
		faxServiceAdresse = value.trim();
	}

	@Override
	public void setFaxServicePassword(SessionContext ctx, String value)
	{
		faxServicePassword = value.trim();
	}

	@Override
	public void setFaxServiceUser(SessionContext ctx, String value)
	{
		faxServiceUser = value.trim();
	}


	@Override
	public String getFaxSender(SessionContext ctx)
	{
		return faxSender;
	}

	@Override
	public void setFaxSender(SessionContext ctx, String value)
	{
		faxSender = value;
	}
	
	@Override
	public Map<Language, String> getAllCampaignHTMLText(SessionContext ctx)
	{
		return campaignHTMLTextMap;
	}

	@Override
	public Map<Language, String> getAllCampaignSubject(SessionContext ctx)
	{
		return campaignSubjectMap;
	}

	@Override
	public Map<Language, String> getAllCampaignText(SessionContext ctx)
	{
		return campaignTextMap;
	}

	@Override
	public void setAllCampaignHTMLText(SessionContext ctx, Map<Language, String> value)
	{
		campaignHTMLTextMap = value;
	}

	@Override
	public void setAllCampaignSubject(SessionContext ctx, Map<Language, String> value)
	{
		campaignSubjectMap = value;
	}

	@Override
	public void setAllCampaignText(SessionContext ctx, Map<Language, String> value)
	{
		campaignTextMap = value;
	}

	@Override
	public Newsletter getCampaignNewsletter(SessionContext ctx)
	{
		return newsletter;
	}

	@Override
	public void setCampaignNewsletter(SessionContext ctx, Newsletter value)
	{
		newsletter = value;
	}

	@Override
	public Media getCampaignHTMLTemplateDECH(SessionContext ctx)
	{
		return campaignHTMLTemplateDECH;
	}

	@Override
	public Media getCampaignHTMLTemplateEN(SessionContext ctx)
	{
		return campaignHTMLTemplateEN;
	}

	@Override
	public Media getCampaignHTMLTemplateES(SessionContext ctx)
	{
		return campaignHTMLTemplateES;
	}

	@Override
	public Media getCampaignHTMLTemplateFRCH(SessionContext ctx)
	{
		return campaignHTMLTemplateFRCH;
	}

	@Override
	public Media getCampaignHTMLTemplateITCH(SessionContext ctx)
	{
		return campaignHTMLTemplateITCH;
	}

	@Override
	public Media getCampaignHTMLTemplatePT(SessionContext ctx)
	{
		return campaignHTMLTemplatePT;
	}

	@Override
	public Media getCampaignTextTemplateDECH(SessionContext ctx)
	{
		return campaignTextTemplateDECH;
	}

	@Override
	public Media getCampaignTextTemplateEN(SessionContext ctx)
	{
		return campaignTextTemplateEN;
	}

	@Override
	public Media getCampaignTextTemplateES(SessionContext ctx)
	{
		return campaignTextTemplateES;
	}

	@Override
	public Media getCampaignTextTemplateFRCH(SessionContext ctx)
	{
		return campaignTextTemplateFRCH;
	}

	@Override
	public Media getCampaignTextTemplateITCH(SessionContext ctx)
	{
		return campaignTextTemplateITCH;
	}

	@Override
	public Media getCampaignTextTemplatePT(SessionContext ctx)
	{
		return campaignTextTemplatePT;
	}

	@Override
	public void setCampaignHTMLTemplateDECH(SessionContext ctx, Media value)
	{
		campaignHTMLTemplateDECH = value;
	}

	@Override
	public void setCampaignHTMLTemplateEN(SessionContext ctx, Media value)
	{
		campaignHTMLTemplateEN = value;
	}

	@Override
	public void setCampaignHTMLTemplateES(SessionContext ctx, Media value)
	{
		campaignHTMLTemplateES = value;
	}

	@Override
	public void setCampaignHTMLTemplateFRCH(SessionContext ctx, Media value)
	{
		campaignHTMLTemplateFRCH = value;
	}

	@Override
	public void setCampaignHTMLTemplateITCH(SessionContext ctx, Media value)
	{
		campaignHTMLTemplateITCH = value;
	}

	@Override
	public void setCampaignHTMLTemplatePT(SessionContext ctx, Media value)
	{
		campaignHTMLTemplatePT = value;
	}

	@Override
	public void setCampaignTextTemplateDECH(SessionContext ctx, Media value)
	{
		campaignTextTemplateDECH = value;
	}

	@Override
	public void setCampaignTextTemplateEN(SessionContext ctx, Media value)
	{
		campaignTextTemplateEN = value;
	}

	@Override
	public void setCampaignTextTemplateES(SessionContext ctx, Media value)
	{
		campaignTextTemplateES = value;
	}

	@Override
	public void setCampaignTextTemplateFRCH(SessionContext ctx, Media value)
	{
		campaignTextTemplateFRCH = value;
	}

	@Override
	public void setCampaignTextTemplateITCH(SessionContext ctx, Media value)
	{
		campaignTextTemplateITCH = value;
	}

	@Override
	public void setCampaignTextTemplatePT(SessionContext ctx, Media value)
	{
		campaignTextTemplatePT = value;
	}


	// ----------------------------------------------------------------------------------------
	public String getDescription(SessionContext ctx)
	{
		return "Create Artoz newsletter campaign Wizard";
	}

	@Override
	public void initialize(WizardEditorContext ctx)
	{
		super.initialize(ctx);
	}

	@Override
	public void start(WizardEditorContext ctx) throws VetoException
	{
		super.start(ctx);

		try
		{
			createEMailFaxCampaign();
		}
		catch (JaloBusinessException e)
		{
			ctx.showErrorTab(e.getMessage());
		}
		catch (IOException ie)
		{
			ctx.showErrorTab(ie.getMessage());
		}

		ctx.hideTab(TAB1);
		ctx.hideTab(TAB2);
		ctx.hideTab(TAB3);
		ctx.showSummaryTab("Die Kampanie wurde erfolgreich erstellt.");
	}

	@Override
	public void tabChanges(WizardEditorContext ctx, String fromTabName, String toTabName) throws VetoException
	{
		super.tabChanges(ctx, fromTabName, toTabName);

		if (toTabName.equals(TAB3))
			ctx.enableButton(START_BUTTON);
		else
			ctx.disableButton(START_BUTTON);
	}

	// ----------------------------------------------------------------------------------------

	private void createEMailFaxCampaign() throws JaloBusinessException, IOException
	{
		final SessionContext ctx = JaloSession.getCurrentSession().createSessionContext();
		ctx.setLanguage(null);

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(EMailFaxCampaign.NAME, getCampaignName(ctx));
		params.put(EMailFaxCampaign.SUBJECT, getAllCampaignSubject());
		params.put(EMailFaxCampaign.HTMLTEXT, createHTMLTemplateList());
		params.put(EMailFaxCampaign.PLAINTEXT, createTxtTemplateList());
		params.put(EMailFaxCampaign.SENDER, getCampaignSender());
		params.put(EMailFaxCampaign.FAXFAXONLY, isCampaignFaxCampaign());
		params.put(EMailFaxCampaign.FAXSENDER, getFaxSender());
		params.put(EMailFaxCampaign.FAXSERVICEADRESSE, getFaxServiceAdresse());
		params.put(EMailFaxCampaign.FAXUSERNAME, getFaxServiceUser());
		params.put(EMailFaxCampaign.FAXPASSWORD, getFaxServicePassword());
		params.put(EMailFaxCampaign.EXCLUDETARGETEDCUSTOMERS, false);

		params.put(EMailFaxCampaign.APPROVALSTATUS, EnumerationManager.getInstance().getEnumerationValue(
					CampaignConstants.TC.CAMPAIGNAPPROVALSTATUS,
					CampaignConstants.Enumerations.CampaignApprovalStatus.APPROVED));

		params.put(EMailFaxCampaign.FRONTENDHOSTANDPORT, "localhost");

		NewsletterCollectionFilter filter = CampaignManager.getInstance().createNewsletterCollectionFilter(
					Collections.singletonList(getCampaignNewsletter()));
		params.put(EMailFaxCampaign.FILTER, filter);

		params.put(EMailFaxCampaign.SCHEDULER, CampaignManager.getInstance().createIntervalCampaignScheduler(0, 0));
		ArtozManager.getInstance().createEMailFaxCampaign(ctx, params);
	}

	private Map<Language, String> createHTMLTemplateList() throws JaloBusinessException, IOException
	{
		Map<Language, String> values = new HashMap<Language, String>();
		values.put(ArtozConstants.Languages.getGerman(), getStringFromTxtMedia(getCampaignHTMLTemplateDECH()));
		values.put(ArtozConstants.Languages.getEnglish(), getStringFromTxtMedia(getCampaignHTMLTemplateEN()));
		values.put(ArtozConstants.Languages.getFrench(), getStringFromTxtMedia(getCampaignHTMLTemplateFRCH()));
		values.put(ArtozConstants.Languages.getItalian(), getStringFromTxtMedia(getCampaignHTMLTemplateITCH()));
		values.put(ArtozConstants.Languages.getSpanish(), getStringFromTxtMedia(getCampaignHTMLTemplateES()));
		values.put(ArtozConstants.Languages.getPortuguese(), getStringFromTxtMedia(getCampaignHTMLTemplatePT()));
		return values;
	}

	private Map<Language, String> createTxtTemplateList() throws JaloBusinessException, IOException
	{
		Map<Language, String> values = new HashMap<Language, String>();
		values.put(ArtozConstants.Languages.getGerman(), getStringFromTxtMedia(getCampaignTextTemplateDECH()));
		values.put(ArtozConstants.Languages.getEnglish(), getStringFromTxtMedia(getCampaignTextTemplateEN()));
		values.put(ArtozConstants.Languages.getFrench(), getStringFromTxtMedia(getCampaignTextTemplateFRCH()));
		values.put(ArtozConstants.Languages.getItalian(), getStringFromTxtMedia(getCampaignTextTemplateITCH()));
		values.put(ArtozConstants.Languages.getSpanish(), getStringFromTxtMedia(getCampaignTextTemplateES()));
		values.put(ArtozConstants.Languages.getPortuguese(), getStringFromTxtMedia(getCampaignTextTemplatePT()));
		return values;
	}

	private String getStringFromTxtMedia(Media media) throws JaloBusinessException, IOException
	{
		if (media == null)
			return null;
		StringBuffer data = new StringBuffer();
		char[] buffer = new char[4];
		BufferedReader br = new BufferedReader(new InputStreamReader(media.getDataFromStream()));
		while (br.read(buffer) > 0)
		{
			data.append(buffer);
		}

		return data.toString();
	}
}