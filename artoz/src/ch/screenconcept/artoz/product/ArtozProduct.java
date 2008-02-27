package ch.screenconcept.artoz.product;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.jalo.ArtozManager;
import ch.screenconcept.artoz.prices.ArtozPriceRow;
import ch.screenconcept.artoz.prices.PriceRowValues;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.security.JaloSecurityException;

/**
 * Product with special properties for Artoz.
 * 
 * @author Pascal Naef
 * 
 */
public class ArtozProduct extends GeneratedArtozProduct
{
	private static Logger log = Logger.getLogger(ArtozProduct.class.getName());

	/**
	 * Creates an ArtozProduct with the attribute values from the passed Map.
	 * 
	 * @param params
	 *            attribute values
	 * @throws JaloBusinessException
	 * @throws JaloSecurityException
	 */
	public static ArtozProduct createArtozProduct(Map<String, Object> params, Map<Language, String> descriptions,
				List<PriceRowValues> priceRowValues) throws JaloInvalidParameterException, JaloSecurityException,
				JaloBusinessException
	{
		// Create Product
		final ArtozProduct product = ArtozManager.getInstance().createArtozProduct(params);

		// Create Descriptions
		final SessionContext ctx = JaloSession.getCurrentSession().createSessionContext();
		ctx.setLanguage(null);
		product.setAttribute(ctx, ArtozProduct.DESCRIPTION, descriptions);

		// Create PriceRows
		for (PriceRowValues priceRowValue : priceRowValues)
		{
			priceRowValue.createPriceRow(product);
		}

		return product;
	}

	/**
	 * Updates an ArtozProduct
	 * 
	 * @param params
	 * @throws JaloBusinessException
	 * @throws JaloSecurityException
	 * @throws JaloInvalidParameterException
	 */
	public void update(Map<String, Object> params, Map<Language, String> descriptions,
				List<PriceRowValues> priceRowValues) throws JaloInvalidParameterException, JaloSecurityException,
				JaloBusinessException
	{
		setAllAttributes(params);

		final SessionContext ctx = JaloSession.getCurrentSession().createSessionContext();
		ctx.setLanguage(null);
		setAttribute(ctx, ArtozProduct.DESCRIPTION, descriptions);

		// Create PriceRows
		for (PriceRowValues priceRowValue : priceRowValues)
		{
			priceRowValue.updatePriceRow(this);
		}
	}

	/**
	 * Search the ArtozProdukt with this code.
	 * 
	 * @param code
	 * @return ArtozProduct the located ArtozProduct
	 */
	public static ArtozProduct findArtozProduct(String code)
	{
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("code", code);

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
		if (res.getResult().isEmpty())
			return null;
		return (ArtozProduct) res.getResult().get(0);
	}

	public static List<ArtozProduct> findNotUpdatedProducts(int importNumber)
	{

		Map<String, Object> value = new HashMap<String, Object>();
		value.put("number", importNumber);

		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + ArtozProduct.PK + "} FROM {" + ArtozConstants.TC.ARTOZPRODUCT + "} " + "WHERE {"
								+ ArtozProduct.UPDATECOUNTER + "} < ?number", value,
					Collections.singletonList(ArtozProduct.class), true, // fail
					// on
					// unknown
					// fields
					true, // don't need total
					0, -1 // range
					);
		log.info("Found " + res.getResult().size() + " not updated products by import number " + importNumber);
		return res.getResult();

	}

	public List<ArtozPriceRow> getAllPriceRows()
	{
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("product", this);

		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + ArtozPriceRow.PK + "} FROM {" + ArtozConstants.TC.ARTOZPRICEROW + "} " + "WHERE {"
								+ ArtozPriceRow.PRODUCT + "} = ?product", value,
					Collections.singletonList(ArtozPriceRow.class), true, // fail
					// on
					// unknown
					// fields
					true, // don't need total
					0, -1 // range
					);
		if (res.getResult().isEmpty())
			return null;

		return res.getResult();
	}

	public ArtozPriceRow getPriceRow(Long quantity, Currency currency)
	{
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("product", this);
		value.put("quantity", quantity);
		value.put("currency", currency);

		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + ArtozPriceRow.PK + "} FROM {" + ArtozConstants.TC.ARTOZPRICEROW + "} " + "WHERE {"
								+ ArtozPriceRow.PRODUCT + "} = ?product AND {" + ArtozPriceRow.MINQTD
								+ "} = ?quantity AND {" + ArtozPriceRow.CURRENCY + "} = ?currency", value,
					Collections.singletonList(ArtozPriceRow.class), true, // fail
					// on
					// unknown
					// fields
					true, // don't need total
					0, -1 // range
					);
		if (res.getResult().isEmpty())
			return null;

		return (ArtozPriceRow) res.getResult().get(0);
	}
}