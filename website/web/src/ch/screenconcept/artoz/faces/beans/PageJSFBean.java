package ch.screenconcept.artoz.faces.beans;

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
