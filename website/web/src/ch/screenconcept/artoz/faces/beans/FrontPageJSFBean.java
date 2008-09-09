package ch.screenconcept.artoz.faces.beans;

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

import ch.screenconcept.artoz.website.constants.WebsiteConstants;

@Bean(name = "frontPageBean", scope = Scope.REQUEST)
public class FrontPageJSFBean
{
	public static final String FRONTPAGE_CATEGORY_CODE = "home";

	PageContent pageContent;

	public FrontPageJSFBean()
	{
		// pageContent =
		// (PageContent)CmsManager.getInstance().getPageContent("frontpage",
		// CommerceUtils.getWebsite() );
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
		this.pageContent = (PageContent) CmsManager.getInstance().getPageContent(
					(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(
								WebsiteConstants.PARAM_PAGEID), CommerceUtils.getWebsite());
		if (this.pageContent == null)
		{
			pageContent = (PageContent) CmsManager.getInstance().getPageContent(FRONTPAGE_CATEGORY_CODE,
						CommerceUtils.getWebsite());
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
