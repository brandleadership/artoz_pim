package ch.screenconcept.artoz.faces.beans;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import de.hybris.platform.cms.jalo.CmsManager;
import de.hybris.platform.cms.jalo.PageContent;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.webfoundation.jalo.bean.PageContentBean;

@Bean(name = "pageBean", scope = Scope.SESSION)
public class PageJSFBean
{

	protected PageContentBean baseBean = null;

	private static final Logger log = Logger.getLogger(PageJSFBean.class.getName());

	/** URL Parameter: page */
	private static final String PARAM_PAGEID = "pageid";

	public PageContentBean getBaseBean()
	{
		return this.baseBean;
	}

	public Item getItem()
	{
		return (Item) this.baseBean.getObject();
	}

	public PageJSFBean()
	{
		final String id = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(
					PARAM_PAGEID);
		if (id != null)
		{
			final PageContent pageContent = CmsManager.getInstance().getActiveWebsite().getPageContent( id );
			this.baseBean = new PageContentBean( pageContent );
		}
		else
		{
			log.error("No parameter: " + PARAM_PAGEID + " found in request");
		}

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
		return getBaseBean().getPageContent();
	}

	public void setPageContent(PageContent pc)
	{
		getBaseBean().setPageContent(pc);
	}
}
