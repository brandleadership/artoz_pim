package ch.screenconcept.artoz.campaign;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.flexiblesearch.FlexibleSearch;

public class SendMailCronJob extends GeneratedSendMailCronJob
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(SendMailCronJob.class);

	public MailFaxSource getMailSource()
	{
		return new InternalMailSource();
	}

	private static class InternalMailSource implements MailFaxSource
	{

		public Collection getMailsToSend(final int maximumResultSize)
		{
			return getMailsToSend(maximumResultSize, false);
		}

		public Collection<EMailFax> getFaxesToSend(final int maximumResultSize)
		{
			return getMailsToSend(maximumResultSize, true);
		}

		private Collection<EMailFax> getMailsToSend(final int maximumResultSize, boolean isFax)
		{
			final Calendar calendar = new GregorianCalendar();
			calendar.add(Calendar.MINUTE, -1);

			final Map<String, Object> values = new HashMap<String, Object>();
			values.put("creationTime", calendar.getTime());

			final StringBuffer query = new StringBuffer();

			query.append("SELECT {" + Item.PK + "} ");
			query.append("FROM {" + ArtozConstants.TC.EMAILFAX + "} ");
			query.append("WHERE {" + EMailFax.LASTSENTDATE + "} IS NULL ");
			query.append("AND {" + Item.CREATION_TIME + "} < ?creationTime ");
			query.append("AND {" + EMailFax.FAILURE + "} IS NULL ");
			if (isFax)
				query.append("AND {" + EMailFax.FAX + "} is true ");
			else
				query.append("AND {" + EMailFax.FAX + "} is false ");
			query.append("ORDER BY {" + Item.CREATION_TIME + "}");

			final FlexibleSearch flexibleSearch = JaloSession.getCurrentSession().getFlexibleSearch();
			List<EMailFax> list = flexibleSearch.search(query.toString(), values,
						Collections.singletonList(EMailFax.class), true, true, 0, maximumResultSize).getResult();

			log.info("did query");
			for (EMailFax mail : list)
				log.info(mail.getRecipient() + " " + mail.getText());
			return list;
		}

	};

}
