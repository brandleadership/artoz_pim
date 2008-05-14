/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * ----------------------------------------------------------------
 */
package de.hybris.platform.cms.jalo;

import ch.screenconcept.artoz.website.TradeFairParagraphData;
import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.lang.String;
import java.util.List;
import java.util.Map;

public abstract class GeneratedTradeFairParagraph extends Paragraph
{
	public static final String HALL = "hall".intern();
	public static final String COUNTRY = "country".intern();
	public static final String LASTTRADEFAIRPARAGRAPHDATAS = "lastTradeFairParagraphDatas".intern();
	public static final String STAND = "stand".intern();
	public static final String CITY = "city".intern();
	public static final String STARTDATE = "startdate".intern();
	public static final String ENDDATE = "enddate".intern();
	public static final String TRADEFAIR = "tradefair".intern();
	public String getCity(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.getCity requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, CITY);
	}
	
	public String getCity()
	{
		return getCity( getSession().getSessionContext() );
	}
	
	public Map<Language,String> getAllCity(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,CITY,C2LManager.getInstance().getAllLanguages());
	}
	
	public Map<Language,String> getAllCity()
	{
		return getAllCity( getSession().getSessionContext() );
	}
	
	public void setCity(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.setCity requires a session language", 0 );
		}
		setLocalizedProperty(ctx, CITY,value);
	}
	
	public void setCity(final String value)
	{
		setCity( getSession().getSessionContext(), value );
	}
	
	public void setAllCity(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,CITY,value);
	}
	
	public void setAllCity(final Map<Language,String> value)
	{
		setAllCity( getSession().getSessionContext(), value );
	}
	
	public String getCountry(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.getCountry requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, COUNTRY);
	}
	
	public String getCountry()
	{
		return getCountry( getSession().getSessionContext() );
	}
	
	public Map<Language,String> getAllCountry(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,COUNTRY,C2LManager.getInstance().getAllLanguages());
	}
	
	public Map<Language,String> getAllCountry()
	{
		return getAllCountry( getSession().getSessionContext() );
	}
	
	public void setCountry(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.setCountry requires a session language", 0 );
		}
		setLocalizedProperty(ctx, COUNTRY,value);
	}
	
	public void setCountry(final String value)
	{
		setCountry( getSession().getSessionContext(), value );
	}
	
	public void setAllCountry(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,COUNTRY,value);
	}
	
	public void setAllCountry(final Map<Language,String> value)
	{
		setAllCountry( getSession().getSessionContext(), value );
	}
	
	public String getEnddate(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.getEnddate requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, ENDDATE);
	}
	
	public String getEnddate()
	{
		return getEnddate( getSession().getSessionContext() );
	}
	
	public Map<Language,String> getAllEnddate(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,ENDDATE,C2LManager.getInstance().getAllLanguages());
	}
	
	public Map<Language,String> getAllEnddate()
	{
		return getAllEnddate( getSession().getSessionContext() );
	}
	
	public void setEnddate(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.setEnddate requires a session language", 0 );
		}
		setLocalizedProperty(ctx, ENDDATE,value);
	}
	
	public void setEnddate(final String value)
	{
		setEnddate( getSession().getSessionContext(), value );
	}
	
	public void setAllEnddate(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,ENDDATE,value);
	}
	
	public void setAllEnddate(final Map<Language,String> value)
	{
		setAllEnddate( getSession().getSessionContext(), value );
	}
	
	public String getHall(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.getHall requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, HALL);
	}
	
	public String getHall()
	{
		return getHall( getSession().getSessionContext() );
	}
	
	public Map<Language,String> getAllHall(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,HALL,C2LManager.getInstance().getAllLanguages());
	}
	
	public Map<Language,String> getAllHall()
	{
		return getAllHall( getSession().getSessionContext() );
	}
	
	public void setHall(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.setHall requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HALL,value);
	}
	
	public void setHall(final String value)
	{
		setHall( getSession().getSessionContext(), value );
	}
	
	public void setAllHall(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,HALL,value);
	}
	
	public void setAllHall(final Map<Language,String> value)
	{
		setAllHall( getSession().getSessionContext(), value );
	}
	
	public abstract List<TradeFairParagraphData> getLastTradeFairParagraphDatas(final SessionContext ctx);
	
	public List<TradeFairParagraphData> getLastTradeFairParagraphDatas()
	{
		return getLastTradeFairParagraphDatas( getSession().getSessionContext() );
	}
	
	public abstract void setLastTradeFairParagraphDatas(final SessionContext ctx, final List<TradeFairParagraphData> value);
	
	public void setLastTradeFairParagraphDatas(final List<TradeFairParagraphData> value)
	{
		setLastTradeFairParagraphDatas( getSession().getSessionContext(), value );
	}
	
	public String getStand(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.getStand requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, STAND);
	}
	
	public String getStand()
	{
		return getStand( getSession().getSessionContext() );
	}
	
	public Map<Language,String> getAllStand(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,STAND,C2LManager.getInstance().getAllLanguages());
	}
	
	public Map<Language,String> getAllStand()
	{
		return getAllStand( getSession().getSessionContext() );
	}
	
	public void setStand(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.setStand requires a session language", 0 );
		}
		setLocalizedProperty(ctx, STAND,value);
	}
	
	public void setStand(final String value)
	{
		setStand( getSession().getSessionContext(), value );
	}
	
	public void setAllStand(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,STAND,value);
	}
	
	public void setAllStand(final Map<Language,String> value)
	{
		setAllStand( getSession().getSessionContext(), value );
	}
	
	public String getStartdate(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.getStartdate requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, STARTDATE);
	}
	
	public String getStartdate()
	{
		return getStartdate( getSession().getSessionContext() );
	}
	
	public Map<Language,String> getAllStartdate(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,STARTDATE,C2LManager.getInstance().getAllLanguages());
	}
	
	public Map<Language,String> getAllStartdate()
	{
		return getAllStartdate( getSession().getSessionContext() );
	}
	
	public void setStartdate(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.setStartdate requires a session language", 0 );
		}
		setLocalizedProperty(ctx, STARTDATE,value);
	}
	
	public void setStartdate(final String value)
	{
		setStartdate( getSession().getSessionContext(), value );
	}
	
	public void setAllStartdate(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,STARTDATE,value);
	}
	
	public void setAllStartdate(final Map<Language,String> value)
	{
		setAllStartdate( getSession().getSessionContext(), value );
	}
	
	public String getTradefair(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.getTradefair requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TRADEFAIR);
	}
	
	public String getTradefair()
	{
		return getTradefair( getSession().getSessionContext() );
	}
	
	public Map<Language,String> getAllTradefair(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TRADEFAIR,C2LManager.getInstance().getAllLanguages());
	}
	
	public Map<Language,String> getAllTradefair()
	{
		return getAllTradefair( getSession().getSessionContext() );
	}
	
	public void setTradefair(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTradeFairParagraph.setTradefair requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TRADEFAIR,value);
	}
	
	public void setTradefair(final String value)
	{
		setTradefair( getSession().getSessionContext(), value );
	}
	
	public void setAllTradefair(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TRADEFAIR,value);
	}
	
	public void setAllTradefair(final Map<Language,String> value)
	{
		setAllTradefair( getSession().getSessionContext(), value );
	}
	
}
