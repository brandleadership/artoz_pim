package ch.screenconcept.artoz.campaign;

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

	public static NewsletterText getNewsletterTextByParagraph(Paragraph paragraph)
	{
		Map<String, PK> attributesPK = new HashMap<String, PK>();
		attributesPK.put("paragraph", paragraph.getPK());
		final SearchResult resPK = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + NewsletterText.PK + "} FROM {" + ArtozConstants.TC.NEWSLETTERTEXT + "} " + " WHERE {"
								+ NewsletterText.PARAGRAPH + "} = ?paragraph ", attributesPK, NewsletterText.class);
		if(resPK.getResult().isEmpty())
		{
			Map<String, String> attributesCode = new HashMap<String, String>();
			attributesCode.put("name", paragraph.getCode());
			final SearchResult resCode = JaloSession.getCurrentSession().getFlexibleSearch().search(
						"SELECT {" + NewsletterText.PK + "} FROM {" + ArtozConstants.TC.NEWSLETTERTEXT + "} " + " WHERE {"
									+ NewsletterText.NAME + "} = ?name ", attributesCode, NewsletterText.class);
			return resCode.getResult().isEmpty() ? null : (NewsletterText) resCode.getResult().get(0);
		}
		
		return resPK.getResult().isEmpty() ? null : (NewsletterText) resPK.getResult().get(0);
	}

	public static NewsletterText createNewsletterTextWithParagraph(Paragraph paragraph)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(NewsletterText.PARAGRAPH, paragraph);
		params.put(NewsletterText.NAME, paragraph.getCode());
		return ArtozManager.getInstance().createNewsletterText(params);
	}

	public static NewsletterText createNewsletterTextWithParagraphCode(String code)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(NewsletterText.NAME, code);
		return ArtozManager.getInstance().createNewsletterText(params);
	}
}
