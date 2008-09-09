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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import ch.screenconcept.artoz.website.TradeFairParagraph;
import ch.screenconcept.artoz.website.TradeFairParagraphData;
import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.cms.jalo.ParagraphContent;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;

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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar cal1 = Calendar.getInstance();//new GregorianCalendar();
		cal1.add(Calendar.DAY_OF_YEAR, -14);
		Date date1 = cal1.getTime();
		dateFormat.format(date1);
		attributes.put("end", date1);
		Calendar cal2 = Calendar.getInstance();//new GregorianCalendar();
		cal2.add(Calendar.DAY_OF_YEAR, 300);
		Date date2 = cal2.getTime();
		dateFormat.format(date2);
		attributes.put("start", date2);
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
