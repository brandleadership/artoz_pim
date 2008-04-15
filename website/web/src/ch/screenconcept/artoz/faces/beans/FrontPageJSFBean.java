package ch.screenconcept.artoz.faces.beans;

import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.category.jalo.CategoryManager;
import de.hybris.platform.cms.jalo.CmsManager;
import de.hybris.platform.cms.jalo.PageContent;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserManager;
import de.hybris.platform.webfoundation.util.CommerceUtils;

import java.util.*;

import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

@Bean(name = "frontPageBean", scope = Scope.REQUEST)
public class FrontPageJSFBean
{
	public static final String FRONTPAGE_CATEGORY_CODE = "home";

	PageContent pageContent;

	public FrontPageJSFBean()
	{
		//pageContent = (PageContent)CmsManager.getInstance().getPageContent("frontpage", CommerceUtils.getWebsite() );
	}

	public Collection getProducts()
	{
		CategoryManager cm = CategoryManager.getInstance();
		User oldUser = JaloSession.getCurrentSession().getSessionContext().getUser();
		JaloSession.getCurrentSession().getSessionContext().setUser(UserManager.getInstance().getAdminEmployee());
		Collection categories = cm.getCategoriesByCode(FRONTPAGE_CATEGORY_CODE);
		JaloSession.getCurrentSession().getSessionContext().setUser(oldUser);
		if (categories.size() == 0)
			return new LinkedList();
		else
		{
			return ((Category) categories.iterator().next()).getProducts();
		}
	}

	public PageContent getPageContent()
	{
		if (this.pageContent == null)
		{
			pageContent = (PageContent) CmsManager.getInstance().getPageContent("home", CommerceUtils.getWebsite());
		}
		return pageContent;
	}

	public void setPageContent(PageContent pageContent)
	{
		this.pageContent = pageContent;
	}

	/**
	 * We provide this getter get all global FacesMessages because the
	 * h:messages Tag doesn't provide escaping.
	 */
	public Collection getGlobalMessages()
	{
		LinkedList c = new LinkedList();
		for (Iterator iter = FacesContext.getCurrentInstance().getMessages(null); iter.hasNext();)
			c.add(iter.next());

		return c;
	}

}
