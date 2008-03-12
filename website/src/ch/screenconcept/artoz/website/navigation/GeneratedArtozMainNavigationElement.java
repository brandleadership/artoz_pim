/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * ----------------------------------------------------------------
 */
package ch.screenconcept.artoz.website.navigation;

import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.cms.jalo.NavigationElement;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.media.Media;

public abstract class GeneratedArtozMainNavigationElement extends NavigationElement
{
	public static final String PICTURE = "picture".intern();
	public Media getPicture(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, PICTURE);
	}
	
	public Media getPicture()
	{
		return getPicture( getSession().getSessionContext() );
	}
	
	public void setPicture(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, PICTURE,value);
	}
	
	public void setPicture(final Media value)
	{
		setPicture( getSession().getSessionContext(), value );
	}
	
}
