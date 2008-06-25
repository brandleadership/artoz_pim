package ch.screenconcept.artoz.faces.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import ch.screenconcept.artoz.website.LexikonParagraph;
import ch.screenconcept.artoz.website.LexikonParagraphData;
import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.cms.jalo.ParagraphContent;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;

@Bean(name = "lexikonJSFBean", scope = Scope.REQUEST)
public class LexikonJSFBean
{
	
	private List<LexikonParagraphData> lexikonData = new ArrayList<LexikonParagraphData>();
	PageJSFBean pageBean;
	
	@SuppressWarnings("unchecked")
	public List getLexikonParagraphDatas()
	{
		Map attributes = new HashMap();
		Paragraph paragraph = getParagraph();
		attributes.put("code", paragraph.getPK().toString());
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch()
					.search(
								 "SELECT {" + LexikonParagraphData.PK + "} FROM {"
											+ WebsiteConstants.TC.LEXIKONPARAGRAPHDATA + "} " + "WHERE {"
											+ LexikonParagraphData.LEXIKONPARAGRAPH + "} = ?code " 
											+ "ORDER BY {" + LexikonParagraphData.NAME + "} ASC", attributes,
											LexikonParagraphData.class);
		lexikonData = (List) res.getResult();
		return lexikonData;
	}
	
	private Paragraph getParagraph() 
	{
		final ParagraphContent content = (ParagraphContent)getPageBean().getPageContent();
		for( final Paragraph paragraph : (List<Paragraph>) content.getParagraphs() )
			if( paragraph instanceof LexikonParagraph )
				return (LexikonParagraph)paragraph;
		return null;
	}
	
	/**
	 * Getter of a property. This property is a managed property. 
	 */
	public PageJSFBean getPageBean()
	{
		return new PageJSFBean();
	}

	/**
	 * Setter of a property. This property is a managed property. 
	 */
	public void setPageBean( PageJSFBean pageBean )
	{
		this.pageBean = pageBean;
	}
}
