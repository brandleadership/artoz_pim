package ch.screenconcept.artoz.website;

/*
The extension "website" is free software: you can redistribute it and/or modify
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.website.constants.WebsiteConstants;
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
