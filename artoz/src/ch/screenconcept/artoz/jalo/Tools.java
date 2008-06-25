package ch.screenconcept.artoz.jalo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.flexiblesearch.FlexibleSearch;
import de.hybris.platform.jalo.numberseries.NumberGenerator;
import de.hybris.platform.jalo.numberseries.NumberSeriesManager;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;

public final class Tools
{
	private static Logger log = Logger.getLogger( Tools.class );

	private Tools()
	{
	}

	/**
	 * @return null if there is no instance
	 * @throws IllegalStateException
	 *            if there are more than one instance
	 */
	public static Item get(final Class classToSearch, final String attribute, final Serializable value)
	{
		return get(classToSearch, Collections.singletonMap(attribute, value));
	}

	public static Item get(final ComposedType typeToSearch, final String attribute, final Serializable value)
	{
		return get(typeToSearch, Collections.singletonMap(attribute, value));
	}

	/**
	 * @return null if there is no instance
	 * @throws IllegalStateException
	 *            if there are more than one instance
	 */
	public static Item get(final Class classToSearch, final Map attributeValues)
	{
		final ComposedType typeToSearch = TypeManager.getInstance().getComposedType(classToSearch);
		return get(JaloSession.getCurrentSession().getSessionContext(), typeToSearch, attributeValues);
	}

	public static Item get(final SessionContext ctx, final Class classToSearch, final Map attributeValues)
	{
		final ComposedType typeToSearch = TypeManager.getInstance().getComposedType(classToSearch);
		return get(ctx, typeToSearch, attributeValues);
	}

	public static Item get(final ComposedType itemType, final Map attributeValues)
	{
		return get( JaloSession.getCurrentSession().getSessionContext(), itemType, attributeValues );
	}

	public static Item get(final SessionContext ctx, final ComposedType itemType, final Map attributeValues)
	{
		if (itemType == null || attributeValues == null || attributeValues.isEmpty())
			throw new IllegalArgumentException("parameters itemType and attributeValues must not be null or empty");

		final StringBuffer query = new StringBuffer("SELECT {" + Item.PK + "} FROM {" + itemType.getCode() + "} WHERE");

		boolean first = true;
		for (final String attribute : (Set<String>) attributeValues.keySet())
		{
			final Serializable value = (Serializable) attributeValues.get(attribute);
			if (first)
				first = false;
			else
				query.append(" AND");

			query.append(" {" + attribute + "} " ).append( value == null ? "IS NULL" : ("=?"+ attribute) );

			assert first == false;
		}

		final List items = get(ctx, itemType.getJaloClass(), query.toString(), attributeValues);

		switch (items.size())
		{
		case 0:
			return null;
		case 1:
			return (Item) items.get(0);
		default:
		{
			final StringBuffer valuesAsString = new StringBuffer();
			boolean firstValue = true;
			for (final Iterator i = attributeValues.entrySet().iterator(); i.hasNext();)
			{
				final Map.Entry entry = (Map.Entry) i.next();
				if (!firstValue)
					valuesAsString.append("; ");
				valuesAsString.append(entry.getKey().toString() + "->" + entry.getValue().toString());
			}
			throw new IllegalStateException("Found " + items.size() + " instances of '" + itemType.getName()
					+ "' with attribute values '" + valuesAsString + "'.");
		}
		}
	}

	public static List get(final Class classToSearch, final String query, final Map attributeValues)
	{
		return Tools.get( JaloSession.getCurrentSession().getSessionContext(), classToSearch, query, attributeValues );
	}

	public static List get(final SessionContext ctx, final Class classToSearch, final String query, final Map attributeValues)
	{
		if( log.isDebugEnabled() )
			log.debug( "Searching for "+classToSearch+" using "+query+" with attributes "+attributeValues );

		return FlexibleSearch.getInstance().search(ctx, query, attributeValues, Collections.singletonList(classToSearch), true, true, 0,
				-1).getResult();
	}

	public static String getUniqueNumber(final String numberSeriesKey)
	{
		final NumberSeriesManager numberSeriesManager = NumberSeriesManager.getInstance();
		try
		{
			numberSeriesManager.getNumberSeries(numberSeriesKey);
		}
		catch (JaloInvalidParameterException e)
		{
			numberSeriesManager.createNumberSeries(numberSeriesKey, "0000000000",
					NumberGenerator.NumberSeriesConstants.TYPES.NUMERIC, 10);
		}
		return numberSeriesManager.getUniqueNumber(numberSeriesKey);
	}

	public static String getUniqueOrderNumber(final String numberSeriesKey)
	{
		final NumberSeriesManager numberSeriesManager = NumberSeriesManager.getInstance();
		try
		{
			numberSeriesManager.getNumberSeries(numberSeriesKey);
		}
		catch (JaloInvalidParameterException e)
		{
			numberSeriesManager.createNumberSeries(numberSeriesKey, "0120000000",
					NumberGenerator.NumberSeriesConstants.TYPES.NUMERIC, 10);
		}
		return numberSeriesManager.getUniqueNumber(numberSeriesKey);
	}

	/**
	 * returns a <code>Date</code> of the current Day at midnight. This method
	 * can be used when calling Product.getPriceInformations() to assure that
	 * price informations can be cached during one day.
	 *
	 * @return the truncated Date
	 */
	public static Date getTruncatedDateDay()
	{
		return truncateDay( new Date() );
	}

	/** return a date with the time set to 00:00:00 */
	public static Date truncateDay( final Date date )
	{
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime( date );
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return calendar.getTime();
	}

	public static Date getTruncatedDateHour()
	{
		return truncateHour( new Date() );
	}

	public static Date truncateHour( final Date date )
	{
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime( date );
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTime();
	}

	public static double round( final double in )
	{
		final double v = Math.floor( in * 100 );
		final double out = ((int)(v/5))*5+(v % 5 < 2.5 ? 0 : 5);
		return out / 100;
	}
	
	private static final int[] checksumFunction = new int[] {0, 9, 4, 6, 8, 2, 7, 1, 3, 5};
	
	public static int checksum( final String source )
	{
		int carry = 0;
		for( int pos = 0; pos < source.length(); pos++ )
		{
			final int digit = Integer.parseInt( source.substring( pos, pos+1 ) );
			carry = checksumFunction[(digit+carry)%10];
		}
		
		return (10 - carry) % 10;
	}
}
