package ch.screenconcept.artoz.campaign;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.jalo.ArtozManager;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.core.PK;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;

public class NewsletterText extends GeneratedNewsletterText
{
	private static Logger log = Logger.getLogger(NewsletterText.class.getName());

	public static NewsletterText getNewsletterTextByParagraph(PK pk)
	{
		Map<String, PK> attributes = new HashMap<String, PK>();
		attributes.put("paragraph", pk);
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + NewsletterText.PK + "} FROM {" + ArtozConstants.TC.NEWSLETTERTEXT + "} " + " WHERE {"
								+ NewsletterText.PARAGRAPH + "} = ?paragraph ", attributes, NewsletterText.class);
		return res.getResult().isEmpty() ? null : (NewsletterText) res.getResult().get(0);
	}
	
	public static NewsletterText createNewsletterTextWithParagraph(Paragraph paragraph){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(NewsletterText.PARAGRAPH, paragraph);
		params.put(NewsletterText.NAME, paragraph.getCode());
		return ArtozManager.getInstance().createNewsletterText(params);
	}
}
