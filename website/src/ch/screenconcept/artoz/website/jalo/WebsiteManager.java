package ch.screenconcept.artoz.website.jalo;

/*
The extension "website" is free software: you can redistribute it and/or modify
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

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;

/**
 * This is the extension manager of the Website extension.
 */
public class WebsiteManager extends GeneratedWebsiteManager
{
	/*
	 * edit the local|project.properties to change logging behavior (properties
	 * log4j.*)
	 */
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(WebsiteManager.class.getName());

	/*
	 * NOTE: If the extension.managersuperclass is set to 'PriceFactory' (i.e.
	 * you are using PriceFactory as superclass of this class), then you have to
	 * REMOVE the following getInstance() Method!! (PriceFactory already
	 * provides an implementation of getInstance).
	 */
	/**
	 * get the valid instance of this manager
	 * 
	 * @return the current instance of this manager
	 */
	public static WebsiteManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (WebsiteManager) em.getExtension(WebsiteConstants.EXTENSIONNAME);
	}

}
