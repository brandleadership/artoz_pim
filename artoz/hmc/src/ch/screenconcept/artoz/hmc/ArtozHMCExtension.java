package ch.screenconcept.artoz.hmc;

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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.campaign.NewsletterText;
import ch.screenconcept.artoz.constants.ArtozConstants;
import de.hybris.platform.cms.constants.CmsConstants;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.hmc.AbstractEditorMenuChip;
import de.hybris.platform.hmc.AbstractExplorerMenuTreeNodeChip;
import de.hybris.platform.hmc.EditorTabChip;
import de.hybris.platform.hmc.extension.HMCExtension;
import de.hybris.platform.hmc.extension.MenuEntrySlotEntry;
import de.hybris.platform.hmc.generic.ClipChip;
import de.hybris.platform.hmc.generic.ToolbarActionChip;
import de.hybris.platform.hmc.util.action.ActionResult;
import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;


/**
 * Provides necessary meta information about the artoz hmc extension.
 *
 * @author  ExtGen v3.0
 * @version ExtGen v3.0
 */
public class ArtozHMCExtension extends HMCExtension
{
	@Override
	public ActionResult afterSave(Item item, DisplayState displayState, Map values, ActionResult actionResult)
	{
		checkParagraphAfterSave(item);
		return super.afterSave(item, displayState, values, actionResult);
	}

	private void checkParagraphAfterSave(Item item)
	{
		if ( item.getComposedType().getSuperType().getCode().equals(CmsConstants.TC.PARAGRAPH) ||  item.getComposedType().getCode().equals(CmsConstants.TC.PARAGRAPH) )
		{
			NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph((Paragraph) item);
			if(newsletterTextIsEmpty(newsletterText))
			{
				try
				{
					newsletterText.remove();
				}
				catch (ConsistencyCheckException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				newsletterText.setParagraph((Paragraph) item);
			}
		}
	}

	@Override
	public ActionResult afterCreate(Item item, DisplayState displayState, Map initialValues, Map values,
				ActionResult actionResult)
	{
		checkParagraphAfterSave(item);
		return super.afterCreate(item, displayState, initialValues, values, actionResult);
	}

	private boolean newsletterTextIsEmpty(NewsletterText newsletterText)
	{
		if(newsletterText.getAllHeadtext().isEmpty() && newsletterText.getAllText().isEmpty() && newsletterText.getAllLinktext().isEmpty())
			return true;
		else
			return false;
	}

	@Override
	public ActionResult beforeCreate(ComposedType itemType, DisplayState displayState, Map initialValues)
	{
		if(itemType.getSuperType().getCode().equals(CmsConstants.TC.PARAGRAPH) || itemType.getComposedType().getSuperType().getCode().equals(CmsConstants.TC.PARAGRAPH) )
		{
			NewsletterText newsletterText = NewsletterText.createNewsletterTextWithParagraphCode((String)initialValues.get("code"));
		}
		return super.beforeCreate(itemType, displayState, initialValues);
	}

	@Override
	public ActionResult beforeSave(Item item, DisplayState displayState, Map currentValues, Map initialValues)
	{
		if ( item.getComposedType().getSuperType().getCode().equals(CmsConstants.TC.PARAGRAPH) ||  item.getComposedType().getCode().equals(CmsConstants.TC.PARAGRAPH) )
		{
			NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph((Paragraph) item);
			if(newsletterText == null)
			{
				newsletterText = NewsletterText.createNewsletterTextWithParagraph((Paragraph) item);
			}
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
