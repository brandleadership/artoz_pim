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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import ch.screenconcept.artoz.website.ArtozSortOverviewParagraph;
import ch.screenconcept.artoz.website.ArtozSortParagraph;
import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.cms.jalo.ParagraphContent;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;

@Bean(name = "ajaxParagraphJSFBean", scope = Scope.REQUEST)
public class AjaxParagraphJSFBean
{
	private List<ArtozSortParagraph> sortData = new ArrayList<ArtozSortParagraph>();

	PageJSFBean pageBean;
	
	Paragraph paragraph;

	@SuppressWarnings("unchecked")
	public List getAjaxParagraphDatas()
	{
		Map attributes = new HashMap();
		Paragraph paragraph = getParagraph();
		if(paragraph == null)
			return new ArrayList();
		attributes.put("code", paragraph.getPK().toString());
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + ArtozSortParagraph.PK + "} FROM {" + WebsiteConstants.TC.ARTOZSORTPARAGRAPH + "} "
								+ "WHERE {" + ArtozSortParagraph.ARTOZSORTOVERVIEWPARAGRAPH + "} = ?code", attributes,
					ArtozSortParagraph.class);
		sortData = (List) res.getResult();
		return sortData;
	}
	
	public Paragraph getShowAjaxParagraph()
	{
		Map attributes = new HashMap();
		String paragraphCode = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(
					"paragraph");
		attributes.put("code", paragraphCode);
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + ArtozSortParagraph.PK + "} FROM {" + WebsiteConstants.TC.ARTOZSORTPARAGRAPH + "} "
								+ "WHERE {" + ArtozSortParagraph.CODE + "} = ?code", attributes,
					ArtozSortParagraph.class);
		Iterator i = res.getResult().iterator();
		paragraph = (Paragraph)i.next();
		return paragraph;
	}

	public Paragraph getParagraph()
	{
		final ParagraphContent content = (ParagraphContent) getPageBean().getPageContent();
		for (final Paragraph paragraph : (List<Paragraph>) content.getParagraphs())
			if (paragraph instanceof ArtozSortOverviewParagraph)
				return (ArtozSortOverviewParagraph) paragraph;
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
	public void setPageBean(PageJSFBean pageBean)
	{
		this.pageBean = pageBean;
	}
}
