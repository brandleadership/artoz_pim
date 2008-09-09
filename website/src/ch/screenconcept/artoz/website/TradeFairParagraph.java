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

public class TradeFairParagraph extends GeneratedTradeFairParagraph
{
	private static Logger log = Logger.getLogger(TradeFairParagraph.class.getName());

	private List<TradeFairParagraphData> tradefairData = new ArrayList<TradeFairParagraphData>();

	@Override
	public List<TradeFairParagraphData> getLastTradeFairParagraphDatas(SessionContext ctx)
	{
		Map attributes = new HashMap();
		attributes.put("code", this);
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch()
					.search(
								"SELECT {" + TradeFairParagraphData.PK + "} FROM {"
											+ WebsiteConstants.TC.TRADEFAIRPARAGRAPHDATA + "} " + "WHERE {"
											+ TradeFairParagraphData.TRADEFAIRPARAGRAPH + "} = ?code ORDER BY {"
											+ TradeFairParagraphData.STARTDATE + "} DESC", attributes,
								TradeFairParagraphData.class);
		tradefairData = (List) res.getResult();
		return tradefairData;
	}

	@Override
	public void setLastTradeFairParagraphDatas(SessionContext ctx, List<TradeFairParagraphData> value)
	{
		// TODO Auto-generated method stub

	}

}
