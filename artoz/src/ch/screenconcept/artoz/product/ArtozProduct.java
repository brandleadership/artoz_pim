package ch.screenconcept.artoz.product;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.jalo.ArtozManager;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;

public class ArtozProduct extends GeneratedArtozProduct
{
	private static Logger log = Logger.getLogger(ArtozProduct.class.getName());

	/**
	 * Creates an ArtozProduct with the attribute values from the passed Map.
	 * @param params attribute values
	 */
	public static ArtozProduct createArtozProduct(Map<String, Object> params)
	{
		return ArtozManager.getInstance().createArtozProduct(params);
	}

	/**
	 * Search the ArtozProdukt with this code.
	 * @param code
	 * @return ArtozProduct the located ArtozProduct
	 */
	public static ArtozProduct findArtozProduct(String code)
	{
		Map<String, Object> value = new HashMap<String, Object>();

		value.put(ArtozProduct.CODE, code);

		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + ArtozProduct.PK + "} FROM {" + ArtozConstants.TC.ARTOZPRODUCT + "} " + "WHERE {"
								+ ArtozProduct.CODE + "} = ?code", value,
					Collections.singletonList(ArtozProduct.class), true, // fail
					// on
					// unknown
					// fields
					true, // don't need total
					0, -1 // range
					);
		return (ArtozProduct) res.getResult().get(0);
	}
}
