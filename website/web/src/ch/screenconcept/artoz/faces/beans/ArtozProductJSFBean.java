package ch.screenconcept.artoz.faces.beans;

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

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import ch.screenconcept.artoz.website.LexikonParagraph;
import ch.screenconcept.artoz.website.LexikonParagraphData;
import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.cms.jalo.ParagraphContent;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;

@Bean(name = "artozproductJSFBean", scope = Scope.REQUEST)
public class ArtozProductJSFBean
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
