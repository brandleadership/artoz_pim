package ch.screenconcept.artoz.campaign;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.exedio.campaign.constants.CampaignConstants;
import com.exedio.campaign.jalo.EMail;
import com.exedio.sendmail.MailSource;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.flexiblesearch.FlexibleSearch;

public class SendMailCronJob extends GeneratedSendMailCronJob
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(SendMailCronJob.class);

	public MailSource getMailSource()
	{
		return new InternalMailSource();
	}

	private static class InternalMailSource implements MailSource
	{

		public Collection getMailsToSend(final int maximumResultSize)
		{
			final Calendar calendar = new GregorianCalendar();
			calendar.add(Calendar.MINUTE, -1);

			final Map<String, Object> values = new HashMap<String, Object>();
			values.put("creationTime", calendar.getTime());

			final StringBuffer query = new StringBuffer();

			query.append("SELECT {" + Item.PK + "} ");
			query.append("FROM {" + CampaignConstants.TC.EMAIL + "} ");
			query.append("WHERE {" + EMail.LASTSENTDATE + "} IS NULL ");
			query.append("AND {" + Item.CREATION_TIME + "} < ?creationTime ");
			query.append("AND {" + EMail.FAILURE + "} IS NULL ");
			query.append("ORDER BY {" + Item.CREATION_TIME + "}");

			final FlexibleSearch flexibleSearch = JaloSession.getCurrentSession().getFlexibleSearch();
			List<EMail> list = flexibleSearch.search(query.toString(), values, Collections.singletonList(EMail.class), true, true,
						0, maximumResultSize).getResult();
			
			for (EMail mail : list)
				log.info(mail.getRecipient() + " " + mail.getText());
			return list;
		}

	};

}
