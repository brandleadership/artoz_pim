package ch.screenconcept.artoz.website;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import org.apache.log4j.Logger;

import ch.screenconcept.artoz.website.LexikonParagraphData;
import ch.screenconcept.artoz.website.constants.WebsiteConstants;

public class LexikonParagraph extends GeneratedLexikonParagraph
{
	private static Logger log = Logger.getLogger( LexikonParagraph.class.getName() );
	
	private List<LexikonParagraphData> lexikonData = new ArrayList<LexikonParagraphData>();
	
	
	public LexikonParagraph()
	{
		// empty
	}
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		// business code placed here will be executed before the item is created
		// then create the item
		Item item = super.createItem( ctx, type, allAttributes );
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}

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
