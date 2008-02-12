package ch.screenconcept.artoz.faces.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import ch.screenconcept.artoz.website.navigation.ArtozMainNavigationElement;
import de.hybris.platform.cms.jalo.AbstractCMSItem;
import de.hybris.platform.cms.jalo.CmsManager;
import de.hybris.platform.cms.jalo.NavigationElement;
import de.hybris.platform.cms.jalo.Website;

/**
 * Managed bean that brigdes to the CMS extension.
 * 
 * @author p-naef
 */
@Bean(name = "cmsJSFBean", scope = Scope.SESSION)
public class CmsJSFBean
{
	private static final Logger log = Logger.getLogger(CmsJSFBean.class.getName());

	public static final String MAIN_NAV = "main_nav";

	public AbstractCMSItem getMainNav()
	{
		log.info("Entering MainNav");
		return getWebsite().getNavigationElement(MAIN_NAV);
	}

	/* a the moment not in use */
	public List<ArtozMainNavigationElement> getMainNavigationElments()
	{
		List<ArtozMainNavigationElement> results = new ArrayList<ArtozMainNavigationElement>();
		Collection<NavigationElement> children = ((NavigationElement) getMainNav()).getChildren();
		for (NavigationElement c : children)
		{
			log.info("Found one" + c.getClass().getName());
			if (c instanceof ArtozMainNavigationElement){
				log.info("added " + c.getName());
				results.add((ArtozMainNavigationElement) c);
			}
		}
		return results;
	}

	public Website getWebsite()
	{
		log.info("getWebsite");
		return CmsManager.getInstance().getWebsite("Artoz");
	}

}
