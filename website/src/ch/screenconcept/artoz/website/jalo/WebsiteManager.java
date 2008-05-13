package ch.screenconcept.artoz.website.jalo;

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
