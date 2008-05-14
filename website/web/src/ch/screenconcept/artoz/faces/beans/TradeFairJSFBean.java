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

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import sun.util.calendar.LocalGregorianCalendar.Date;

import ch.screenconcept.artoz.website.TradeFairParagraphData;
import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;

@Bean(name = "tradeFairJSFBean", scope = Scope.REQUEST)
public class TradeFairJSFBean
{
	
	private List<TradeFairParagraphData> tradefairData = new ArrayList<TradeFairParagraphData>();
	
	public List getTradeFairParagraphDatas()
	{
		Map attributes = new HashMap();
		attributes.put("code", "152003227400355456");
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_YEAR, -14);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00.0");
		attributes.put("end", dateFormat.format(cal.getTime()));
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch()
					.search(
								"SELECT {" + TradeFairParagraphData.PK + "} FROM {"
											+ WebsiteConstants.TC.TRADEFAIRPARAGRAPHDATA + "} " + "WHERE {"
											+ TradeFairParagraphData.TRADEFAIRPARAGRAPH + "} = ?code AND {"
											+ TradeFairParagraphData.ENDDATE + "} >= ?end "
											+ "ORDER BY {" + TradeFairParagraphData.STARTDATE + "} DESC", attributes,
								TradeFairParagraphData.class);
		tradefairData = (List) res.getResult();
		return tradefairData;
	}
}
