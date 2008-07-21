/*
 * Generated by ExtGen v3.0 
 */
package ch.screenconcept.artoz.website.hmc;

import de.hybris.platform.hmc.webchips.*;

import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Implements methods for displaying a website content chip.
 *
 * @author  ExtGen v3.0
 * @version ExtGen v3.0 */
 
public class WebsiteContentChip extends AbstractChip
{
	//Log4J implementation - edit project.properties file's Logging section to configurate your own log channel
	static final Logger log = Logger.getLogger( WebsiteContentChip.class.getName() );
	
	public WebsiteContentChip(DisplayState displayState, Chip parent)
	{
		super(displayState, parent);
	}
			
	/**
	 * @see de.hybris.platform.hmc.webchips.Chip#processEvents(Map)
	 */
	public void processEvents(Map events)
	{
	}
	
	/**
	 * @see de.hybris.platform.hmc.webchips.Chip#getJSPURI()
	 */
	public String getJSPURI()
	{
		return "/ext/website/WebsiteContentChip.jsp";
	}
	
	/**
	 * Test method generated by ExtGen
	 */
	public boolean isTestOK()
	{
		return true;
	}
}