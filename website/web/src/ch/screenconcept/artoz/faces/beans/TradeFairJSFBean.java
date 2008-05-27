package ch.screenconcept.artoz.faces.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import sun.util.calendar.LocalGregorianCalendar.Date;

import ch.screenconcept.artoz.website.TradeFairParagraph;
import ch.screenconcept.artoz.website.TradeFairParagraphData;
import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import ch.screenconcept.artoz.website.jalo.WebsiteManager;
import de.hybris.platform.cms.jalo.CmsManager;
import de.hybris.platform.cms.jalo.FormParagraph;
import de.hybris.platform.cms.jalo.PageContent;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.cms.jalo.ParagraphContent;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.webfoundation.jalo.bean.PageContentBean;

@Bean(name = "tradeFairJSFBean", scope = Scope.REQUEST)
public class TradeFairJSFBean
{
	
	private List<TradeFairParagraphData> tradefairData = new ArrayList<TradeFairParagraphData>();
	PageJSFBean pageBean;
	
	@SuppressWarnings("unchecked")
	public List getTradeFairParagraphDatas()
	{
		Map attributes = new HashMap();
		Paragraph paragraph = getParagraph();
		attributes.put("code", paragraph.getPK().toString());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar cal1 = Calendar.getInstance();//new GregorianCalendar();
		cal1.add(Calendar.DAY_OF_YEAR, -14);
		attributes.put("end", cal1.getTime());
		Calendar cal2 = Calendar.getInstance();//new GregorianCalendar();
		cal2.add(Calendar.DAY_OF_YEAR, 300);
		attributes.put("start", cal2.getTime());
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch()
					.search(
								"SELECT {" + TradeFairParagraphData.PK + "} FROM {"
											+ WebsiteConstants.TC.TRADEFAIRPARAGRAPHDATA + "} " + "WHERE {"
											+ TradeFairParagraphData.TRADEFAIRPARAGRAPH + "} = ?code AND {"
											+ TradeFairParagraphData.ENDDATE + "} >= ?end AND {"
											+ TradeFairParagraphData.STARTDATE + "} <= ?start "
											+ "ORDER BY {" + TradeFairParagraphData.STARTDATE + "} ASC", attributes,
								TradeFairParagraphData.class);
		tradefairData = (List) res.getResult();
		return tradefairData;
	}
	
	private Paragraph getParagraph() 
	{
		final ParagraphContent content = (ParagraphContent)getPageBean().getPageContent();
		for( final Paragraph paragraph : (List<Paragraph>) content.getParagraphs() )
			if( paragraph instanceof TradeFairParagraph )
				return (TradeFairParagraph)paragraph;
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
