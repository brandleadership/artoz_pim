package ch.screenconcept.artoz.campaign;

import java.util.Iterator;

import com.exedio.campaign.jalo.CampaignContext;

import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.cms.jalo.ParagraphContent;
import de.hybris.platform.jalo.media.Media;

public class NewsletterTextProxy
{
	NewsletterText content;

	CampaignContext campaign;

	Media image;

	public NewsletterTextProxy(NewsletterText content, CampaignContext campaign)
	{
		this.content = content;
		this.campaign = campaign;
	}

	public NewsletterTextProxy(Object obj)
	{
		if (obj instanceof Media)
			this.image = (Media) obj;
	}

	public String getName()
	{
		return content.getName();
	}

	public String getUrl()
	{
		return campaign.getCampaign().getFrontEndHostAndPort();
	}

	/**
	 * Gibt Url zum IntroImage beim Verkaufsnewsletter zurück
	 * 
	 * @return String Image url
	 */
	public String getSrc()
	{
		if (image != null)
			return image.getURL();
		else
			return "";
	}

	/**
	 * Gibt Alt-Text zum IntroImage beim Verkaufsnewsletter zurück
	 * 
	 * @return String Image Alt-Text
	 */
	public String getAlt()
	{
		if (image != null)
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

	public String getTextAsHtml()
	{
		String text = content.getText();
		if (text == null)
			return "";
		return text.replace("\n\r", "<br />").replace("\n", "<br />");
	}

	public String getParagraphlink()
	{
		return content.getParagraph() != null ? content.getParagraph().getCode() : "";
	}

	public String getLayout()
	{
		return content.getImagelayout().getCode();
	}

	public String getImage0()
	{
		return content.getImage0() != null ? content.getImage0().getURL() : "";
	}

	public String getImage1()
	{
		return content.getImage1() != null ? content.getImage1().getURL() : "";
	}

	public String getAlttext0()
	{
		return content.getImage0() != null ? content.getImage0().getAltText() : "";
	}

	public String getAlttext1()
	{
		return content.getImage1() != null ? content.getImage1().getAltText() : "";
	}

	public String getLink()
	{
		String returnValue = "";
		Paragraph paragraph = content.getParagraph();
		if (paragraph != null)
		{
			Iterator<ParagraphContent> iterator = paragraph.getParagraphContents().iterator();
			ParagraphContent target = (ParagraphContent) iterator.next();
			returnValue = "<a href=\"http://" + campaign.getCampaign().getFrontEndHostAndPort()
						+ "/pages/index.jsf?pageid=" + target.getCode() + "&partid="
						+ campaign.getParticipation().getPK().toHexString() + "\" target=\"_blank\">"
						+ content.getLinktext() + "</a>";
		}
		return returnValue;
	}

	public String getTextlink()
	{
		String returnValue = "";
		Paragraph paragraph = content.getParagraph();
		if (paragraph != null)
		{
			Iterator<ParagraphContent> iterator = paragraph.getParagraphContents().iterator();
			ParagraphContent target = iterator.next();
			returnValue = "http://" + campaign.getCampaign().getFrontEndHostAndPort() + "/pages/index.jsf?pageid="
						+ target.getCode();
		}
		return returnValue;
	}
}
