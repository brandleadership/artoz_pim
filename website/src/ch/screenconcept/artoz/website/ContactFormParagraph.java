package ch.screenconcept.artoz.website;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import ch.screenconcept.artoz.website.jalo.WebsiteManager;

import de.hybris.platform.cms.jalo.CmsManager;
import de.hybris.platform.core.PK;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.SessionContext;

public class ContactFormParagraph extends GeneratedContactFormParagraph
{
	private static Logger log = Logger.getLogger(ContactFormParagraph.class.getName());

	private List<ContactFormParagraphData> contactData = new ArrayList<ContactFormParagraphData>();

	public static ContactFormParagraph getContactFormParagraph(String code)
	{
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("code", code);

		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + ContactFormParagraph.PK + "} FROM {" + ContactFormParagraphData.CONTACTFORMPARAGRAPH
								+ "} " + "WHERE {" + ContactFormParagraph.CODE + "} = ?code", value,
					Collections.singletonList(ContactFormParagraph.class), true, // fail
					// on
					// unknown
					// fields
					true, // don't need total
					0, -1 // range
					);
		if (res.getResult().isEmpty())
			return null;
		return (ContactFormParagraph) res.getResult().get(0);
	}

	@Override
	public List<ContactFormParagraphData> getLastContactFormParagraphDatas(SessionContext ctx)
	{
		Map attributes = new HashMap();
		attributes.put("code", this);
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + ContactFormParagraphData.PK + "} FROM {"
								+ WebsiteConstants.TC.CONTACTFORMPARAGRAPHDATA + "} " + "WHERE {"
								+ ContactFormParagraphData.CONTACTFORMPARAGRAPH + "} = ?code ORDER BY {"
								+ ContactFormParagraphData.CREATION_TIME + "} DESC", attributes,
					Collections.singletonList(ContactFormParagraphData.class), true, true, 0, 20);
		contactData = (List) res.getResult();
		return contactData;
	}

}
