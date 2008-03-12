/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * ----------------------------------------------------------------
 */
package ch.screenconcept.artoz.prices;

import ch.screenconcept.artoz.constants.ArtozConstants;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.SessionContext;
import java.lang.Integer;

public abstract class GeneratedArtozPriceRow extends PriceRow
{
	public static final String UPDATECOUNTER = "updateCounter".intern();
	public Integer getUpdateCounter(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, UPDATECOUNTER);
	}
	
	public Integer getUpdateCounter()
	{
		return getUpdateCounter( getSession().getSessionContext() );
	}
	
	public int getUpdateCounterAsPrimitive(final SessionContext ctx)
	{
		Integer value = getUpdateCounter( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	public int getUpdateCounterAsPrimitive()
	{
		return getUpdateCounterAsPrimitive( getSession().getSessionContext() );
	}
	
	public void setUpdateCounter(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, UPDATECOUNTER,value);
	}
	
	public void setUpdateCounter(final Integer value)
	{
		setUpdateCounter( getSession().getSessionContext(), value );
	}
	
	public void setUpdateCounter(final SessionContext ctx, final int value)
	{
		setUpdateCounter( ctx,Integer.valueOf( value ) );
	}
	
	public void setUpdateCounter(final int value)
	{
		setUpdateCounter( getSession().getSessionContext(), value );
	}
	
}
