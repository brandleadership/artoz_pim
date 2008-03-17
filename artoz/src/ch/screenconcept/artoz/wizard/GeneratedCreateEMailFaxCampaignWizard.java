/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * ----------------------------------------------------------------
 */
package ch.screenconcept.artoz.wizard;

import ch.screenconcept.artoz.constants.ArtozConstants;
import com.exedio.campaign.jalo.Newsletter;
import de.hybris.platform.hmc.jalo.WizardBusinessItem;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.media.Media;
import java.lang.Boolean;
import java.lang.String;
import java.util.Map;

public abstract class GeneratedCreateEMailFaxCampaignWizard extends WizardBusinessItem
{
	public static final String CAMPAIGNTEXTTEMPLATEPT = "campaignTextTemplatePT".intern();
	public static final String CAMPAIGNTEXTTEMPLATEEN = "campaignTextTemplateEN".intern();
	public static final String FAXSERVICEPASSWORD = "faxServicePassword".intern();
	public static final String FAXSERVICEADRESSE = "faxServiceAdresse".intern();
	public static final String CAMPAIGNNEWSLETTER = "campaignNewsletter".intern();
	public static final String CAMPAIGNHTMLTEMPLATEPT = "campaignHTMLTemplatePT".intern();
	public static final String CAMPAIGNHTMLTEMPLATEEN = "campaignHTMLTemplateEN".intern();
	public static final String CAMPAIGNSENDER = "campaignSender".intern();
	public static final String CAMPAIGNTEXTTEMPLATEITCH = "campaignTextTemplateITCH".intern();
	public static final String CAMPAIGNTEXTTEMPLATEDECH = "campaignTextTemplateDECH".intern();
	public static final String CAMPAIGNTEXTTEMPLATEES = "campaignTextTemplateES".intern();
	public static final String CAMPAIGNHTMLTEMPLATEITCH = "campaignHTMLTemplateITCH".intern();
	public static final String CAMPAIGNTEXT = "campaignText".intern();
	public static final String FAXSENDER = "faxSender".intern();
	public static final String CAMPAIGNHTMLTEMPLATEFRCH = "campaignHTMLTemplateFRCH".intern();
	public static final String CAMPAIGNIMPORTFILE = "campaignImportFile".intern();
	public static final String CAMPAIGNSUBJECT = "campaignSubject".intern();
	public static final String CAMPAIGNHTMLTEXT = "campaignHTMLText".intern();
	public static final String FAXSERVICEUSER = "faxServiceUser".intern();
	public static final String CAMPAIGNNAME = "campaignName".intern();
	public static final String CAMPAIGNTEXTTEMPLATEFRCH = "campaignTextTemplateFRCH".intern();
	public static final String CAMPAIGNFAXCAMPAIGN = "campaignFaxCampaign".intern();
	public static final String CAMPAIGNHTMLTEMPLATEDECH = "campaignHTMLTemplateDECH".intern();
	public static final String CAMPAIGNHTMLTEMPLATEES = "campaignHTMLTemplateES".intern();
	public abstract Boolean isCampaignFaxCampaign(final SessionContext ctx);
	
	public Boolean isCampaignFaxCampaign()
	{
		return isCampaignFaxCampaign( getSession().getSessionContext() );
	}
	
	public boolean isCampaignFaxCampaignAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isCampaignFaxCampaign( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	public boolean isCampaignFaxCampaignAsPrimitive()
	{
		return isCampaignFaxCampaignAsPrimitive( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignFaxCampaign(final SessionContext ctx, final Boolean value);
	
	public void setCampaignFaxCampaign(final Boolean value)
	{
		setCampaignFaxCampaign( getSession().getSessionContext(), value );
	}
	
	public void setCampaignFaxCampaign(final SessionContext ctx, final boolean value)
	{
		setCampaignFaxCampaign( ctx,Boolean.valueOf( value ) );
	}
	
	public void setCampaignFaxCampaign(final boolean value)
	{
		setCampaignFaxCampaign( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignHTMLTemplateDECH(final SessionContext ctx);
	
	public Media getCampaignHTMLTemplateDECH()
	{
		return getCampaignHTMLTemplateDECH( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignHTMLTemplateDECH(final SessionContext ctx, final Media value);
	
	public void setCampaignHTMLTemplateDECH(final Media value)
	{
		setCampaignHTMLTemplateDECH( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignHTMLTemplateEN(final SessionContext ctx);
	
	public Media getCampaignHTMLTemplateEN()
	{
		return getCampaignHTMLTemplateEN( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignHTMLTemplateEN(final SessionContext ctx, final Media value);
	
	public void setCampaignHTMLTemplateEN(final Media value)
	{
		setCampaignHTMLTemplateEN( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignHTMLTemplateES(final SessionContext ctx);
	
	public Media getCampaignHTMLTemplateES()
	{
		return getCampaignHTMLTemplateES( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignHTMLTemplateES(final SessionContext ctx, final Media value);
	
	public void setCampaignHTMLTemplateES(final Media value)
	{
		setCampaignHTMLTemplateES( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignHTMLTemplateFRCH(final SessionContext ctx);
	
	public Media getCampaignHTMLTemplateFRCH()
	{
		return getCampaignHTMLTemplateFRCH( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignHTMLTemplateFRCH(final SessionContext ctx, final Media value);
	
	public void setCampaignHTMLTemplateFRCH(final Media value)
	{
		setCampaignHTMLTemplateFRCH( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignHTMLTemplateITCH(final SessionContext ctx);
	
	public Media getCampaignHTMLTemplateITCH()
	{
		return getCampaignHTMLTemplateITCH( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignHTMLTemplateITCH(final SessionContext ctx, final Media value);
	
	public void setCampaignHTMLTemplateITCH(final Media value)
	{
		setCampaignHTMLTemplateITCH( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignHTMLTemplatePT(final SessionContext ctx);
	
	public Media getCampaignHTMLTemplatePT()
	{
		return getCampaignHTMLTemplatePT( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignHTMLTemplatePT(final SessionContext ctx, final Media value);
	
	public void setCampaignHTMLTemplatePT(final Media value)
	{
		setCampaignHTMLTemplatePT( getSession().getSessionContext(), value );
	}
	
	public abstract String getCampaignHTMLText(final SessionContext ctx);
	
	public String getCampaignHTMLText()
	{
		return getCampaignHTMLText( getSession().getSessionContext() );
	}
	
	public abstract Map<Language,String> getAllCampaignHTMLText(final SessionContext ctx);
	
	public Map<Language,String> getAllCampaignHTMLText()
	{
		return getAllCampaignHTMLText( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignHTMLText(final SessionContext ctx, final String value);
	
	public void setCampaignHTMLText(final String value)
	{
		setCampaignHTMLText( getSession().getSessionContext(), value );
	}
	
	public abstract void setAllCampaignHTMLText(final SessionContext ctx, final Map<Language,String> value);
	
	public void setAllCampaignHTMLText(final Map<Language,String> value)
	{
		setAllCampaignHTMLText( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignImportFile(final SessionContext ctx);
	
	public Media getCampaignImportFile()
	{
		return getCampaignImportFile( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignImportFile(final SessionContext ctx, final Media value);
	
	public void setCampaignImportFile(final Media value)
	{
		setCampaignImportFile( getSession().getSessionContext(), value );
	}
	
	public abstract String getCampaignName(final SessionContext ctx);
	
	public String getCampaignName()
	{
		return getCampaignName( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignName(final SessionContext ctx, final String value);
	
	public void setCampaignName(final String value)
	{
		setCampaignName( getSession().getSessionContext(), value );
	}
	
	public abstract Newsletter getCampaignNewsletter(final SessionContext ctx);
	
	public Newsletter getCampaignNewsletter()
	{
		return getCampaignNewsletter( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignNewsletter(final SessionContext ctx, final Newsletter value);
	
	public void setCampaignNewsletter(final Newsletter value)
	{
		setCampaignNewsletter( getSession().getSessionContext(), value );
	}
	
	public abstract String getCampaignSender(final SessionContext ctx);
	
	public String getCampaignSender()
	{
		return getCampaignSender( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignSender(final SessionContext ctx, final String value);
	
	public void setCampaignSender(final String value)
	{
		setCampaignSender( getSession().getSessionContext(), value );
	}
	
	public abstract String getCampaignSubject(final SessionContext ctx);
	
	public String getCampaignSubject()
	{
		return getCampaignSubject( getSession().getSessionContext() );
	}
	
	public abstract Map<Language,String> getAllCampaignSubject(final SessionContext ctx);
	
	public Map<Language,String> getAllCampaignSubject()
	{
		return getAllCampaignSubject( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignSubject(final SessionContext ctx, final String value);
	
	public void setCampaignSubject(final String value)
	{
		setCampaignSubject( getSession().getSessionContext(), value );
	}
	
	public abstract void setAllCampaignSubject(final SessionContext ctx, final Map<Language,String> value);
	
	public void setAllCampaignSubject(final Map<Language,String> value)
	{
		setAllCampaignSubject( getSession().getSessionContext(), value );
	}
	
	public abstract String getCampaignText(final SessionContext ctx);
	
	public String getCampaignText()
	{
		return getCampaignText( getSession().getSessionContext() );
	}
	
	public abstract Map<Language,String> getAllCampaignText(final SessionContext ctx);
	
	public Map<Language,String> getAllCampaignText()
	{
		return getAllCampaignText( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignText(final SessionContext ctx, final String value);
	
	public void setCampaignText(final String value)
	{
		setCampaignText( getSession().getSessionContext(), value );
	}
	
	public abstract void setAllCampaignText(final SessionContext ctx, final Map<Language,String> value);
	
	public void setAllCampaignText(final Map<Language,String> value)
	{
		setAllCampaignText( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignTextTemplateDECH(final SessionContext ctx);
	
	public Media getCampaignTextTemplateDECH()
	{
		return getCampaignTextTemplateDECH( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignTextTemplateDECH(final SessionContext ctx, final Media value);
	
	public void setCampaignTextTemplateDECH(final Media value)
	{
		setCampaignTextTemplateDECH( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignTextTemplateEN(final SessionContext ctx);
	
	public Media getCampaignTextTemplateEN()
	{
		return getCampaignTextTemplateEN( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignTextTemplateEN(final SessionContext ctx, final Media value);
	
	public void setCampaignTextTemplateEN(final Media value)
	{
		setCampaignTextTemplateEN( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignTextTemplateES(final SessionContext ctx);
	
	public Media getCampaignTextTemplateES()
	{
		return getCampaignTextTemplateES( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignTextTemplateES(final SessionContext ctx, final Media value);
	
	public void setCampaignTextTemplateES(final Media value)
	{
		setCampaignTextTemplateES( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignTextTemplateFRCH(final SessionContext ctx);
	
	public Media getCampaignTextTemplateFRCH()
	{
		return getCampaignTextTemplateFRCH( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignTextTemplateFRCH(final SessionContext ctx, final Media value);
	
	public void setCampaignTextTemplateFRCH(final Media value)
	{
		setCampaignTextTemplateFRCH( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignTextTemplateITCH(final SessionContext ctx);
	
	public Media getCampaignTextTemplateITCH()
	{
		return getCampaignTextTemplateITCH( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignTextTemplateITCH(final SessionContext ctx, final Media value);
	
	public void setCampaignTextTemplateITCH(final Media value)
	{
		setCampaignTextTemplateITCH( getSession().getSessionContext(), value );
	}
	
	public abstract Media getCampaignTextTemplatePT(final SessionContext ctx);
	
	public Media getCampaignTextTemplatePT()
	{
		return getCampaignTextTemplatePT( getSession().getSessionContext() );
	}
	
	public abstract void setCampaignTextTemplatePT(final SessionContext ctx, final Media value);
	
	public void setCampaignTextTemplatePT(final Media value)
	{
		setCampaignTextTemplatePT( getSession().getSessionContext(), value );
	}
	
	public abstract String getFaxSender(final SessionContext ctx);
	
	public String getFaxSender()
	{
		return getFaxSender( getSession().getSessionContext() );
	}
	
	public abstract void setFaxSender(final SessionContext ctx, final String value);
	
	public void setFaxSender(final String value)
	{
		setFaxSender( getSession().getSessionContext(), value );
	}
	
	public abstract String getFaxServiceAdresse(final SessionContext ctx);
	
	public String getFaxServiceAdresse()
	{
		return getFaxServiceAdresse( getSession().getSessionContext() );
	}
	
	public abstract void setFaxServiceAdresse(final SessionContext ctx, final String value);
	
	public void setFaxServiceAdresse(final String value)
	{
		setFaxServiceAdresse( getSession().getSessionContext(), value );
	}
	
	public abstract String getFaxServicePassword(final SessionContext ctx);
	
	public String getFaxServicePassword()
	{
		return getFaxServicePassword( getSession().getSessionContext() );
	}
	
	public abstract void setFaxServicePassword(final SessionContext ctx, final String value);
	
	public void setFaxServicePassword(final String value)
	{
		setFaxServicePassword( getSession().getSessionContext(), value );
	}
	
	public abstract String getFaxServiceUser(final SessionContext ctx);
	
	public String getFaxServiceUser()
	{
		return getFaxServiceUser( getSession().getSessionContext() );
	}
	
	public abstract void setFaxServiceUser(final SessionContext ctx, final String value);
	
	public void setFaxServiceUser(final String value)
	{
		setFaxServiceUser( getSession().getSessionContext(), value );
	}
	
}
