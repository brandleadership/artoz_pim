/*
 * Generated by ExtGen v3.0 
 */
package ch.screenconcept.artoz.hmc;

import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.hmc.*;
import de.hybris.platform.hmc.extension.HMCExtension;
import de.hybris.platform.hmc.extension.MenuEntrySlotEntry;
import de.hybris.platform.hmc.generic.ClipChip;
import de.hybris.platform.hmc.generic.ToolbarActionChip;
import de.hybris.platform.hmc.util.action.ActionResult;
import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.Type;

import java.util.*;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.campaign.NewsletterText;
import ch.screenconcept.artoz.constants.ArtozConstants;


/**
 * Provides necessary meta information about the artoz hmc extension.
 *
 * @author  ExtGen v3.0
 * @version ExtGen v3.0
 */
public class ArtozHMCExtension extends HMCExtension
{
	@Override
	public ActionResult beforeCreate(ComposedType itemType, DisplayState displayState, Map initialValues)
	{
		System.out.println("itemType: "+itemType.getName());
		return super.beforeCreate(itemType, displayState, initialValues);
	}

	@Override
	public ActionResult beforeSave(Item item, DisplayState displayState, Map currentValues, Map initialValues)
	{
		if(item instanceof Paragraph)
		{
			System.out.println("save");
			
		}
		return super.beforeSave(item, displayState, currentValues, initialValues);
	}

	//Log4J implementation - edit the project.properties file's Logging section to configurate your own log channel
	static final Logger log = Logger.getLogger( ArtozHMCExtension.class.getName() );

	private static final HashMap<String, ResourceBundle> resourceBundlesMap = new HashMap<String, ResourceBundle>();
	public final static String RESOURCE_PATH = "ch.screenconcept.artoz.hmc.locales";


	/**
	 * Constructor for ArtozHMCExtension.
	 */
	public ArtozHMCExtension()
	{
		super();
	}

	/**
	 * @see HMCExtension#getTreeNodeChips(de.hybris.platform.hmc.webchips.DisplayState, de.hybris.platform.hmc.webchips.Chip)
	 */
	@Override
	public List<AbstractExplorerMenuTreeNodeChip> getTreeNodeChips( final DisplayState displayState, Chip parent)
	{
      return Collections.EMPTY_LIST;
	}

	/**
	 * @see HMCExtension#getMenuEntrySlotEntries(de.hybris.platform.hmc.webchips.DisplayState, de.hybris.platform.hmc.webchips.Chip)
	 */
	@Override
	public List<MenuEntrySlotEntry> getMenuEntrySlotEntries(DisplayState displayState, Chip parent)
	{
		return Collections.EMPTY_LIST;
	}

	/**
	 * @see HMCExtension#getSectionChips(de.hybris.platform.hmc.webchips.DisplayState, de.hybris.platform.hmc.generic.ClipChip)
	 */
	@Override
	public List<ClipChip> getSectionChips(DisplayState displayState, ClipChip parent)
	{
		return Collections.EMPTY_LIST;
	}
	
	@Override
	public List<EditorTabChip> getEditorTabChips(DisplayState displayState, AbstractEditorMenuChip parent)
	{
		return Collections.EMPTY_LIST;
	}
	
	
	/**
	 * @see HMCExtension#getToolbarActionChips(de.hybris.platform.hmc.webchips.DisplayState, de.hybris.platform.hmc.webchips.Chip)
	 */
	@Override
	public List<ToolbarActionChip> getToolbarActionChips(DisplayState displayState, Chip parent)
	{
		return Collections.EMPTY_LIST;
	}
    
    /**
     * Returns the resource bundle for the given iso code. Uses <code>BUNDLEPATH</code> to locate the resource bundle.
     * @param isoCode   the resource bundle iso code.
     * @return ResourceBundle   the resource bundle
     * @throws MissingResourceException if the resource bundle is missing at the given path
     */
    public static synchronized ResourceBundle getLocalizeResBundle(String isoCode) throws MissingResourceException
    {

        if( resourceBundlesMap.containsKey( isoCode ) )
        {
            return resourceBundlesMap.get( isoCode );
        }
        else
        {            
            ResourceBundle bundle = ResourceBundle.getBundle( RESOURCE_PATH, new Locale( isoCode, "") );
            resourceBundlesMap.put( isoCode, bundle );
            return bundle;
        }
    }
    
    
    /**
     * Returns a localized string for the given key. If the key doesn't exists the key itself will be returned.
     * @param strKey    the localized string key
     * @param isocode   the language iso code.
     */
    public static String getLocalizedString(String strKey, String isocode)
    {
        try
        {
            return getLocalizeResBundle( isocode ).getString(strKey);
        }
        catch (MissingResourceException e)
        {
            return strKey;
        }
    }    
    
    /**
     * Returns a localized string for the given key. If the key doesn't exists the key itself will be returned.
     * @param strKey    the localized string key.
     * @param ctx the current session context. will be used to compute the isocode of the session context language.
     */
    public static String getLocalizedString(String strKey, SessionContext ctx)
    {
        return getLocalizedString(strKey, ctx.getLanguage().getIsoCode());
    }
    
    /**
     * Returns a localized string for the given key.
     * @deprecated please use {@link #getLocalizedString(String, SessionContext)} instead
     */
    @Deprecated
	public static String getLocalizedString(String strKey, JaloSession jaloSession)
   {
		return getLocalizedString(strKey, jaloSession.getSessionContext());
   }    
	
   @Override
	public ResourceBundle getLocalizeResourceBundle( Locale locale )
	{
		return null;	
	}
	
   @Override
   public String getResourcePath()
   {
    	return RESOURCE_PATH;
   }

	@Override
	public ActionResult beforeRemove(Item item, ComposedType itemType, DisplayState displayState, Map values) {
		if( ArtozConstants.TC.ARTOZPRODUCT.equals( itemType.getCode() ) )
		{
			return new ActionResult( ActionResult.FAILED, getLocalizedString("notallowed", JaloSession.getCurrentSession().getSessionContext()), false );
		}
		else
		{
			return super.beforeRemove(item, itemType, displayState, values);
		}
	} 
   
   
}
