package ch.screenconcept.artoz.faces.beans;

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
import ch.screenconcept.artoz.website.LexikonParagraph;
import ch.screenconcept.artoz.website.LexikonParagraphData;
import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.cms.jalo.CmsManager;
import de.hybris.platform.cms.jalo.PageContent;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.cms.jalo.ParagraphContent;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.webfoundation.util.CommerceUtils;

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
