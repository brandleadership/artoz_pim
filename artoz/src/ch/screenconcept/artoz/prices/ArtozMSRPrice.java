package ch.screenconcept.artoz.prices;

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
