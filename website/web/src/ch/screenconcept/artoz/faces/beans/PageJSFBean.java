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

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import ch.screenconcept.artoz.website.constants.WebsiteConstants;

import de.hybris.platform.cms.jalo.CmsManager;
import de.hybris.platform.cms.jalo.PageContent;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.webfoundation.jalo.bean.PageContentBean;

@Bean(name = "pageJSFBean", scope = Scope.REQUEST)
public class PageJSFBean
{
	protected PageContentBean pageContentBean = null;

	private static final Logger log = Logger.getLogger(PageJSFBean.class.getName());

	public PageJSFBean()
	{
		final String id = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(
					WebsiteConstants.PARAM_PAGEID);
		if (id != null)
		{
			final PageContent pageContent = CmsManager.getInstance().getActiveWebsite().getPageContent(id);
			this.pageContentBean = new PageContentBean(pageContent);
		}
		else
		{
			log.error("No parameter: " + WebsiteConstants.PARAM_PAGEID + " found in request");
		}
	}

	public PageContentBean getPageContentBean()
	{
		return this.pageContentBean;
	}

	public Item getItem()
	{
		return (Item) this.pageContentBean.getObject();
	}

	/**
	 * Gets the title of the page that is displayed.
	 */
	public String getTitle()
	{
		return getPageContent().getTitle();
	}

	public PageContent getPageContent()
	{
		return getPageContentBean().getPageContent();
	}

	public void setPageContent(PageContent pc)
	{
		getPageContentBean().setPageContent(pc);
	}
}
