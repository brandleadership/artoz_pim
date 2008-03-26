package ch.screenconcept.artoz.faces.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import ch.screenconcept.artoz.website.navigation.ArtozMainNavigationElement;
import de.hybris.platform.cms.constants.CmsConstants;
import de.hybris.platform.cms.jalo.AbstractCMSItem;
import de.hybris.platform.cms.jalo.CmsManager;
import de.hybris.platform.cms.jalo.NavigationElement;
import de.hybris.platform.cms.jalo.PageContent;
import de.hybris.platform.cms.jalo.Website;
import de.hybris.platform.jalo.flexiblesearch.FlexibleSearch;

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
	
	public static final String	FRONTPAGE_CATEGORY_CODE	= "frontpage";
	
	PageContent	pageContent;

	public AbstractCMSItem getMainNav()
	{
		return getWebsite().getNavigationElement(MAIN_NAV);
	}
	
	public NavigationElement getActiveMainNav()
	{
		final String pageID = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get( "pageid" );
		System.out.println("pageID: "+pageID);
		if( pageID==null )
			return null;
		final PageContent page = CmsManager.getInstance().getPageContent( pageID, getWebsite() );
		System.out.println("page: "+page);
		if( page==null )
			return null;
		
		final String query = "SELECT {"+NavigationElement.PK+"} " +
				"FROM {"+CmsConstants.TC.NAVIGATIONELEMENT+"} " +
				"WHERE {"+NavigationElement.CONTENT+"}=?page";
		final Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put( "page", page );

		final List<NavigationElement> elements = FlexibleSearch.getInstance().search( query, parameter, NavigationElement.class ).getResult();
		if( elements==null || elements.isEmpty() )
			return null;
		
		final NavigationElement link = elements.get( 0 );
		if( !getMainNav().equals( link.getParent() ) )
			return link.getParent();
		else
			return link;
	}

	/* a the moment not in use */
	public List<ArtozMainNavigationElement> getMainNavigationElments()
	{
		List<ArtozMainNavigationElement> results = new ArrayList<ArtozMainNavigationElement>();
		Collection<NavigationElement> children = ((NavigationElement) getMainNav()).getChildren();
		for (NavigationElement c : children)
		{
			if (c instanceof ArtozMainNavigationElement){
				results.add((ArtozMainNavigationElement) c);
			}
		}
		return results;
	}

	public Website getWebsite()
	{
		return CmsManager.getInstance().getWebsite("Artoz");
	}

}
