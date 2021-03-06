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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.SessionContext;

public class LexikonParagraph extends GeneratedLexikonParagraph
{
	private static Logger log = Logger.getLogger( LexikonParagraph.class.getName() );
	
	private List<LexikonParagraphData> lexikonData = new ArrayList<LexikonParagraphData>();

	@Override
	public List<LexikonParagraphData> getLastLexikonParagraphDatas(SessionContext ctx)
	{
		Map attributes = new HashMap();
		attributes.put("code", this);
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch()
					.search(
								"SELECT {" + LexikonParagraphData.PK + "} FROM {"
											+ WebsiteConstants.TC.LEXIKONPARAGRAPHDATA + "} " + "WHERE {"
											+ LexikonParagraphData.LEXIKONPARAGRAPH + "} = ?code ORDER BY {"
											+ LexikonParagraphData.NAME + "} ASC", attributes,
								LexikonParagraphData.class);
		lexikonData = (List) res.getResult();
		return lexikonData;
	}

	@Override
	public void setLastLexikonParagraphDatas(SessionContext ctx, List<LexikonParagraphData> value)
	{
		// TODO Auto-generated method stub
		
	}
	
}
