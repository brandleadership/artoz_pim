package ch.screenconcept.artoz.faces.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import ch.screenconcept.artoz.website.navigation.ArtozMainNavigationElement;

import com.exedio.campaign.jalo.EMailCampaignParticipation;

import de.hybris.platform.cms.constants.CmsConstants;
import de.hybris.platform.cms.jalo.AbstractCMSItem;
import de.hybris.platform.cms.jalo.CmsManager;
import de.hybris.platform.cms.jalo.NavigationElement;
import de.hybris.platform.cms.jalo.PageContent;
import de.hybris.platform.cms.jalo.Website;
import de.hybris.platform.core.PK;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.flexiblesearch.FlexibleSearch;
import de.hybris.platform.jalo.user.User;

/**
 * Managed bean that brigdes to the CMS extension.
 * 
 * @author p-naef
 */
@Bean(name = "cmsJSFBean", scope = Scope.REQUEST)
public class CmsJSFBean
{
	private static final Logger log = Logger.getLogger(CmsJSFBean.class.getName());

	public static final String FRONTPAGE_CATEGORY_CODE = "home";

	public CmsJSFBean()
	{
		final String participationID = getRequestParameter(WebsiteConstants.PARAM_PARTICIPATION);
		if (!GenericValidator.isBlankOrNull(participationID))
		{
			try
			{
				final EMailCampaignParticipation participation = (EMailCampaignParticipation) JaloSession
							.getCurrentSession().getItem(PK.parseHex(participationID));
				participation.setPositiveReaction(true);
			}
			catch (Exception e)
			{
				log.error("", e);
			}
		}

		CmsManager.getInstance().setActiveWebsite(getWebsite());

		LoginJSFBean loginBean = (LoginJSFBean) FacesContext.getCurrentInstance().getApplication().createValueBinding(
					"#{loginJSFBean}").getValue(FacesContext.getCurrentInstance());

		LanguageJSFBean languageBean = (LanguageJSFBean) FacesContext.getCurrentInstance().getApplication()
					.createValueBinding("#{languageBean}").getValue(FacesContext.getCurrentInstance());

		User user = loginBean.getLoginUser();

		Language lang = languageBean.getLanguage();

		if (user != null)
		{
			JaloSession.getCurrentSession().setUser(user);
		}
		JaloSession.getCurrentSession().getSessionContext().setLanguage(lang);
	}

	public String getRequestParameter(String name)
	{
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}

	public String getTemplate()
	{
		return FRONTPAGE_CATEGORY_CODE;
	}

	public String getParagraph()
	{
		return FRONTPAGE_CATEGORY_CODE;
	}

	public AbstractCMSItem getMainNav()
	{
		return getWebsite().getNavigationElement(WebsiteConstants.MAIN_NAV);
	}

	public AbstractCMSItem getSubNav()
	{
		return getActivMainNavigationElement();
	}

	public AbstractCMSItem getHelpNav()
	{
		return getWebsite().getNavigationElement(WebsiteConstants.HELP_NAV);
	}

	public AbstractCMSItem getQuickNav()
	{
		return getWebsite().getNavigationElement(WebsiteConstants.QUICK_NAV);
	}

	public PageContent getPageContent()
	{
		return (PageContent) CmsManager.getInstance().getPageContent(
					(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(
								WebsiteConstants.PARAM_PAGEID), getWebsite());
	}

	public NavigationElement getActivMainNavigationElement()
	{
		final NavigationElement link = getActivNavigationElement();

		if (link == null)
			return null;

		if (getMainNav().equals(link.getParent()))
			return link;
		if (getMainNav().equals(link.getParent().getParent()))
			return link.getParent();
		if (null == link.getParent().getParent())
			return null;
		if (getMainNav().equals(link.getParent().getParent().getParent()))
			return link.getParent().getParent();
		return null;

	}

	public NavigationElement getActivSubNavigationElement()
	{
		final NavigationElement link = getActivNavigationElement();

		if (link == null)
			return null;

		if (getMainNav().equals(link.getParent()))
			return null;
		if (getMainNav().equals(link.getParent().getParent()))
			return link;
		if (null == link.getParent().getParent())
			return null;
		if (getMainNav().equals(link.getParent().getParent().getParent()))
			return link.getParent();
		return null;
	}

	public NavigationElement getActivNavigationElement()
	{
		final String pageID = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get(WebsiteConstants.PARAM_PAGEID);
		if (pageID == null)
			return null;
		if (pageID.equals(""))
			return null;
		final PageContent page = CmsManager.getInstance().getPageContent(pageID, getWebsite());
		if (page == null)
			return null;

		final String query = "SELECT {" + NavigationElement.PK + "} " + "FROM {" + CmsConstants.TC.NAVIGATIONELEMENT
					+ "} " + "WHERE {" + NavigationElement.CONTENT + "}=?page";
		final Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("page", page);

		final List<NavigationElement> elements = FlexibleSearch.getInstance().search(query, parameter,
					NavigationElement.class).getResult();
		if (elements == null || elements.isEmpty())
			return null;

		final NavigationElement link = elements.get(0);
		return link;
	}

	public List<ArtozMainNavigationElement> getMainNavigationElements()
	{
		List<ArtozMainNavigationElement> results = new ArrayList<ArtozMainNavigationElement>();
		Collection<NavigationElement> children = ((NavigationElement) getMainNav()).getChildren();
		for (NavigationElement c : children)
		{
			if (c instanceof ArtozMainNavigationElement)
			{
				results.add((ArtozMainNavigationElement) c);
			}
		}
		return results;
	}

	public List<ArtozMainNavigationElement> getSubNavigationElements()
	{
		List<ArtozMainNavigationElement> results = new ArrayList<ArtozMainNavigationElement>();
		Collection<NavigationElement> children = ((NavigationElement) getSubNav()).getChildren();
		for (NavigationElement c : children)
		{
			if (c instanceof ArtozMainNavigationElement)
			{
				results.add((ArtozMainNavigationElement) c);
			}
		}
		return results;
	}

	public Website getWebsite()
	{
		return CmsManager.getInstance().getWebsite("artoz");
	}

}
