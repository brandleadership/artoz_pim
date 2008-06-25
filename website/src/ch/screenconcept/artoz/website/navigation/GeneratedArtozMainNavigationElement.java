/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * ----------------------------------------------------------------
 */
package ch.screenconcept.artoz.website.navigation;

import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.cms.jalo.NavigationElement;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;

public abstract class GeneratedArtozMainNavigationElement extends NavigationElement
{
	public static final String COLOR = "color".intern();
	public EnumerationValue getColor(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, COLOR);
	}
	
	public EnumerationValue getColor()
	{
		return getColor( getSession().getSessionContext() );
	}
	
	public void setColor(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, COLOR,value);
	}
	
	public void setColor(final EnumerationValue value)
	{
		setColor( getSession().getSessionContext(), value );
	}
	
}
