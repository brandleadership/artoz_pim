package ch.screenconcept.artoz.campaign;

/*
The extension "Artoz" is free software: you can redistribute it and/or modify
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

			log.info("Found " + list.size() + " mails to send");
			
			return list;
		}

	};

}
