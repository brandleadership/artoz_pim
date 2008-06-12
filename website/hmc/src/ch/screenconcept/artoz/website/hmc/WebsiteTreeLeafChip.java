/*
 * Generated by ExtGen v3.0 
 */
package ch.screenconcept.artoz.website.hmc;

import de.hybris.platform.hmc.TreeLeafChip;
import de.hybris.platform.hmc.jalo.AccessManager;
import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;

import org.apache.log4j.Logger;

/**
 * Implements methods for displaying a website tree node.
 *
 * @author  ExtGen v3.0
 * @version ExtGen v3.0
 */
public class WebsiteTreeLeafChip extends TreeLeafChip
{
	//Log4J implementation - edit project.properties file's Logging section to configurate your own log channel
	static final Logger log = Logger.getLogger( WebsiteTreeLeafChip.class.getName() );
	
	private WebsiteContentChip displayChip;
	
	/**
	 * @see de.hybris.platform.hmc.AbstractExplorerMenuTreeNodeChip#getDisplayChip(Chip)
	 */
	public WebsiteTreeLeafChip(DisplayState displayState, Chip parent)
	{
		super(displayState, parent);
	}

	@Override
	protected Chip getDisplayChip(Chip parent)
	{
		if( displayChip == null )
		{
			displayChip = new WebsiteContentChip(getDisplayState(), parent);
		}
		
		return displayChip;
	}

	/**
	 * @see de.hybris.platform.hmc.AbstractTreeNodeChip#getIcon()
	 */
	@Override
	public String getIcon()
	{
		return "images/icons/t_undefined.gif";
	}

	/**
	 * @see de.hybris.platform.hmc.AbstractTreeNodeChip#getName()
	 */
	@Override
	public String getName()
	{
		return getDisplayState().getLocalizedString("websitenode");
	}

	/**
	 * use this method to restrict access to this tree node. 
	 * @return true if this node should be displayed, false otherwise
	 */
	@Override
	public boolean isActive()
	{
		// as an example we return only true is the current user has admin rights		
		return AccessManager.getInstance().isAdmin( getJaloSession().getUser() );
		
		// different example: we display this panel only if the current user has read access to the Product type
		
		//return AccessManager.getInstance().canRead( getJaloSession().getTypeManager().getComposedType(Product.class) );
			
	}
}