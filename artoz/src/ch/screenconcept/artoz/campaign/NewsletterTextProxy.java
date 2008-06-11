package ch.screenconcept.artoz.campaign;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bsh.org.objectweb.asm.Constants;
import ch.screenconcept.artoz.constants.ArtozConstants;

import com.exedio.campaign.jalo.CampaignContext;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.cms.constants.CmsConstants;
import de.hybris.platform.cms.jalo.NavigationElement;
import de.hybris.platform.cms.jalo.PageContent;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.cms.jalo.ParagraphContent;

public class NewsletterTextProxy
{
	NewsletterText content;

	CampaignContext campaign;
	
	Media image;
	
	User user;

	public NewsletterTextProxy(NewsletterText content, CampaignContext campaign)
	{
		this.content = content;
		this.campaign = campaign;
	}
	
	public NewsletterTextProxy(Object obj)
	{
		if (obj instanceof User)
			this.user = (User) obj;
		if (obj instanceof Media)
			this.image = (Media)obj;
	}

	public String getName()
	{
		return content.getName();
	}
	
	public String getUid()
	{
		return user.getUID();
	}
	
	public String getUrl()
	{
		return campaign.getCampaign().getFrontEndHostAndPort();
	}
	/**
	 * Gibt Url zum IntroImage beim Verkaufsnewsletter zur�ck
	 * @return String Image url
	 */
	public String getSrc()
	{
		if(image != null)
			return image.getURL();
		else
			return "";
	}
	
	/**
	 * Gibt Alt-Text zum IntroImage beim Verkaufsnewsletter zur�ck
	 * @return String Image Alt-Text
	 */
	public String getAlt()
	{
		if(image != null)
			return image.getAltText();
		else
			return "";
	}
	
	public String getHeadtext()
	{
		return content.getHeadtext();
	}
	
	public String getText()
	{
		return content.getText();
	}
	
	public String getParagraphlink()
	{
		Paragraph par = content.getParagraph();
		return par.getCode();
	}
	
	public String getLayout()
	{
		return content.getImagelayout().getCode();
	}
	
	public String getImage0()
	{
		return content.getImage0().getURL();
	}
	
	public String getImage1()
	{
		return content.getImage1().getURL();
	}
	
	public String getAlttext0()
	{
		return content.getImage0().getAltText();
	}
	
	public String getAlttext1()
	{
		return content.getImage1().getAltText();
	}

	public String getLink()
	{
		String ret = "";
		Paragraph par = content.getParagraph();
		Iterator i = par.getParagraphContents().iterator();
		ParagraphContent target = (ParagraphContent) i.next();
		ret = "<a href=\"http://" + campaign.getCampaign().getFrontEndHostAndPort() + "/pages/index.jsf?pageid="
					+ target.getCode() + "\" target=\"_blank\">" + content.getLinktext() + "</a>";
		return ret;
	}
	
	public String getTextlink()
	{
		String ret = "";
		Paragraph par = content.getParagraph();
		Iterator i = par.getParagraphContents().iterator();
		ParagraphContent target = (ParagraphContent) i.next();
		ret = "http://" + campaign.getCampaign().getFrontEndHostAndPort() + "/pages/index.jsf?pageid="
					+ target.getCode();
		return ret;
	}
}
