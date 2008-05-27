package ch.screenconcept.artoz.prices;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;

public class ArtozPriceRow extends GeneratedArtozPriceRow
{
	private static Logger log = Logger.getLogger( ArtozPriceRow.class.getName() );

	public static List<ArtozPriceRow> findNotUpdatedPriceRows(int importNumber)
	{
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("number", importNumber);

		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + ArtozPriceRow.PK + "} FROM {" + ArtozConstants.TC.ARTOZPRICEROW + "} " + "WHERE {"
								+ ArtozPriceRow.UPDATECOUNTER + "} < ?number", value,
					Collections.singletonList(ArtozPriceRow.class), true, // fail
					// on
					// unknown
					// fields
					true, // don't need total
					0, -1 // range
					);

		log.info("Found " + res.getResult().size() + " not updated price rows by import number " + importNumber);

		return res.getResult();
	}
}
