package ch.screenconcept.artoz.website;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.ItemAttributeMap;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import org.apache.log4j.Logger;

import ch.screenconcept.artoz.website.ContactFormParagraphData;
import ch.screenconcept.artoz.website.TradeFairParagraphData;
import ch.screenconcept.artoz.website.constants.WebsiteConstants;

public class TradeFairParagraph extends GeneratedTradeFairParagraph
{
	private static Logger log = Logger.getLogger(TradeFairParagraph.class.getName());

	private List<TradeFairParagraphData> tradefairData = new ArrayList<TradeFairParagraphData>();

	public TradeFairParagraph()
	{
		// empty
	}

	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes)
				throws JaloBusinessException
	{
		// business code placed here will be executed before the item is created
		// then create the item
		Item item = super.createItem(ctx, type, allAttributes);
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}

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
