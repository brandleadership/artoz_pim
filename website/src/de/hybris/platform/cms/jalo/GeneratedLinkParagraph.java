/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * ----------------------------------------------------------------
 */
package de.hybris.platform.cms.jalo;

import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.lang.String;
import java.util.Map;

public abstract class GeneratedLinkParagraph extends Paragraph
{
	public static final String PARAGRAPHHEADER = "paragraphHeader".intern();
	public static final String PARAGRAPHTEXT = "paragraphText".intern();
	public String getParagraphHeader(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedLinkParagraph.getParagraphHeader requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, PARAGRAPHHEADER);
	}
	
	public String getParagraphHeader()
	{
		return getParagraphHeader( getSession().getSessionContext() );
	}
	
	public Map<Language,String> getAllParagraphHeader(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,PARAGRAPHHEADER,C2LManager.getInstance().getAllLanguages());
	}
	
	public Map<Language,String> getAllParagraphHeader()
	{
		return getAllParagraphHeader( getSession().getSessionContext() );
	}
	
	public void setParagraphHeader(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedLinkParagraph.setParagraphHeader requires a session language", 0 );
		}
		setLocalizedProperty(ctx, PARAGRAPHHEADER,value);
	}
	
	public void setParagraphHeader(final String value)
	{
		setParagraphHeader( getSession().getSessionContext(), value );
	}
	
	public void setAllParagraphHeader(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,PARAGRAPHHEADER,value);
	}
	
	public void setAllParagraphHeader(final Map<Language,String> value)
	{
		setAllParagraphHeader( getSession().getSessionContext(), value );
	}
	
	public String getParagraphText(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedLinkParagraph.getParagraphText requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, PARAGRAPHTEXT);
	}
	
	public String getParagraphText()
	{
		return getParagraphText( getSession().getSessionContext() );
	}
	
	public Map<Language,String> getAllParagraphText(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,PARAGRAPHTEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	public Map<Language,String> getAllParagraphText()
	{
		return getAllParagraphText( getSession().getSessionContext() );
	}
	
	public void setParagraphText(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedLinkParagraph.setParagraphText requires a session language", 0 );
		}
		setLocalizedProperty(ctx, PARAGRAPHTEXT,value);
	}
	
	public void setParagraphText(final String value)
	{
		setParagraphText( getSession().getSessionContext(), value );
	}
	
	public void setAllParagraphText(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,PARAGRAPHTEXT,value);
	}
	
	public void setAllParagraphText(final Map<Language,String> value)
	{
		setAllParagraphText( getSession().getSessionContext(), value );
	}
	
}
