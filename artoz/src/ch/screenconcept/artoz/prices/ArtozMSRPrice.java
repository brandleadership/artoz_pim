package ch.screenconcept.artoz.prices;

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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;

public class ArtozMSRPrice extends GeneratedArtozMSRPrice
{
	private static Logger log = Logger.getLogger(ArtozMSRPrice.class.getName());

	public static List<ArtozMSRPrice> findNotUpdatedArtozMSRPrice(int importNumber)
	{
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("number", importNumber);

		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + ArtozMSRPrice.PK + "} FROM {" + ArtozConstants.TC.ARTOZMSRPRICE + "} " + "WHERE {"
								+ ArtozMSRPrice.UPDATECOUNTER + "} < ?number", value,
					Collections.singletonList(ArtozMSRPrice.class), true, true, 0, -1);

		log.info("Found " + res.getResult().size() + " not updated price rows by import number " + importNumber);

		return res.getResult();
	}
}
