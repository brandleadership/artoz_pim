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

import de.hybris.platform.hmc.TreeLeafChip;
import de.hybris.platform.hmc.jalo.AccessManager;
import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;

import org.apache.log4j.Logger;

/**
 * Implements methods for displaying a artoz tree node.
 *
 * @author  ExtGen v3.0
 * @version ExtGen v3.0
 */
public class ArtozTreeLeafChip extends TreeLeafChip
{
	//Log4J implementation - edit project.properties file's Logging section to configurate your own log channel
	static final Logger log = Logger.getLogger( ArtozTreeLeafChip.class.getName() );
	
	private ArtozContentChip displayChip;
	
	/**
	 * @see de.hybris.platform.hmc.AbstractExplorerMenuTreeNodeChip#getDisplayChip(Chip)
	 */
	public ArtozTreeLeafChip(DisplayState displayState, Chip parent)
	{
		super(displayState, parent);
	}

	@Override
	protected Chip getDisplayChip(Chip parent)
	{
		if( displayChip == null )
		{
			displayChip = new ArtozContentChip(getDisplayState(), parent);
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
		return getDisplayState().getLocalizedString("artoznode");
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
