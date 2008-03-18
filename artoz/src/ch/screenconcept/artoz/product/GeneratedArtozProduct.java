/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * ----------------------------------------------------------------
 */
package ch.screenconcept.artoz.product;

import ch.screenconcept.artoz.constants.ArtozConstants;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.product.Product;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

public abstract class GeneratedArtozProduct extends Product
{
	public static final String ARTIST = "artist".intern();
	public static final String MDAVIEW = "mdaView".intern();
	public static final String UPDATECOUNTER = "updateCounter".intern();
	public static final String GRAMMAGE = "grammage".intern();
	public static final String ITEMTYPEGROUP = "itemTypeGroup".intern();
	public static final String ICONCODE = "iconCode".intern();
	public static final String MATERIALGROUP = "materialGroup".intern();
	public static final String NEWNESSCODE = "newnessCode".intern();
	public static final String DIMENSIONS = "dimensions".intern();
	public static final String DISTRIBUTIONSTATUS = "distributionStatus".intern();
	public static final String DIN = "din".intern();
	public String getArtist(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ARTIST);
	}
	
	public String getArtist()
	{
		return getArtist( getSession().getSessionContext() );
	}
	
	public void setArtist(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ARTIST,value);
	}
	
	public void setArtist(final String value)
	{
		setArtist( getSession().getSessionContext(), value );
	}
	
	public String getDimensions(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DIMENSIONS);
	}
	
	public String getDimensions()
	{
		return getDimensions( getSession().getSessionContext() );
	}
	
	public void setDimensions(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DIMENSIONS,value);
	}
	
	public void setDimensions(final String value)
	{
		setDimensions( getSession().getSessionContext(), value );
	}
	
	public String getDin(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DIN);
	}
	
	public String getDin()
	{
		return getDin( getSession().getSessionContext() );
	}
	
	public void setDin(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DIN,value);
	}
	
	public void setDin(final String value)
	{
		setDin( getSession().getSessionContext(), value );
	}
	
	public String getDistributionStatus(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DISTRIBUTIONSTATUS);
	}
	
	public String getDistributionStatus()
	{
		return getDistributionStatus( getSession().getSessionContext() );
	}
	
	public void setDistributionStatus(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DISTRIBUTIONSTATUS,value);
	}
	
	public void setDistributionStatus(final String value)
	{
		setDistributionStatus( getSession().getSessionContext(), value );
	}
	
	public String getGrammage(final SessionContext ctx)
	{
		return (String)getProperty( ctx, GRAMMAGE);
	}
	
	public String getGrammage()
	{
		return getGrammage( getSession().getSessionContext() );
	}
	
	public void setGrammage(final SessionContext ctx, final String value)
	{
		setProperty(ctx, GRAMMAGE,value);
	}
	
	public void setGrammage(final String value)
	{
		setGrammage( getSession().getSessionContext(), value );
	}
	
	public String getIconCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ICONCODE);
	}
	
	public String getIconCode()
	{
		return getIconCode( getSession().getSessionContext() );
	}
	
	public void setIconCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ICONCODE,value);
	}
	
	public void setIconCode(final String value)
	{
		setIconCode( getSession().getSessionContext(), value );
	}
	
	public String getItemTypeGroup(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ITEMTYPEGROUP);
	}
	
	public String getItemTypeGroup()
	{
		return getItemTypeGroup( getSession().getSessionContext() );
	}
	
	public void setItemTypeGroup(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ITEMTYPEGROUP,value);
	}
	
	public void setItemTypeGroup(final String value)
	{
		setItemTypeGroup( getSession().getSessionContext(), value );
	}
	
	public Integer getMaterialGroup(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, MATERIALGROUP);
	}
	
	public Integer getMaterialGroup()
	{
		return getMaterialGroup( getSession().getSessionContext() );
	}
	
	public int getMaterialGroupAsPrimitive(final SessionContext ctx)
	{
		Integer value = getMaterialGroup( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	public int getMaterialGroupAsPrimitive()
	{
		return getMaterialGroupAsPrimitive( getSession().getSessionContext() );
	}
	
	public void setMaterialGroup(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, MATERIALGROUP,value);
	}
	
	public void setMaterialGroup(final Integer value)
	{
		setMaterialGroup( getSession().getSessionContext(), value );
	}
	
	public void setMaterialGroup(final SessionContext ctx, final int value)
	{
		setMaterialGroup( ctx,Integer.valueOf( value ) );
	}
	
	public void setMaterialGroup(final int value)
	{
		setMaterialGroup( getSession().getSessionContext(), value );
	}
	
	public Boolean isMdaView(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, MDAVIEW);
	}
	
	public Boolean isMdaView()
	{
		return isMdaView( getSession().getSessionContext() );
	}
	
	public boolean isMdaViewAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isMdaView( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	public boolean isMdaViewAsPrimitive()
	{
		return isMdaViewAsPrimitive( getSession().getSessionContext() );
	}
	
	public void setMdaView(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, MDAVIEW,value);
	}
	
	public void setMdaView(final Boolean value)
	{
		setMdaView( getSession().getSessionContext(), value );
	}
	
	public void setMdaView(final SessionContext ctx, final boolean value)
	{
		setMdaView( ctx,Boolean.valueOf( value ) );
	}
	
	public void setMdaView(final boolean value)
	{
		setMdaView( getSession().getSessionContext(), value );
	}
	
	public Integer getNewnessCode(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, NEWNESSCODE);
	}
	
	public Integer getNewnessCode()
	{
		return getNewnessCode( getSession().getSessionContext() );
	}
	
	public int getNewnessCodeAsPrimitive(final SessionContext ctx)
	{
		Integer value = getNewnessCode( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	public int getNewnessCodeAsPrimitive()
	{
		return getNewnessCodeAsPrimitive( getSession().getSessionContext() );
	}
	
	public void setNewnessCode(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, NEWNESSCODE,value);
	}
	
	public void setNewnessCode(final Integer value)
	{
		setNewnessCode( getSession().getSessionContext(), value );
	}
	
	public void setNewnessCode(final SessionContext ctx, final int value)
	{
		setNewnessCode( ctx,Integer.valueOf( value ) );
	}
	
	public void setNewnessCode(final int value)
	{
		setNewnessCode( getSession().getSessionContext(), value );
	}
	
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
