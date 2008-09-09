package ch.screenconcept.artoz.cms;

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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import de.hybris.platform.cms.jalo.AbstractCMSItem;
import de.hybris.platform.cms.jalo.Template;
import de.hybris.platform.cms.jalo.Website;
import de.hybris.platform.cms.web.CMSRequestHandler;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.util.Config;
import de.hybris.platform.webfoundation.util.CommerceUtils;

/**
 * If the website is viewed as preview from the HMC, this component encloses its body with a red frame. If the user clicks 
 * right a context menu is shown that allows to open a certain set of HMC editors. These include an editor to the CMS item
 * that represents the surrounded view component and an HMC-Text-editor that allows to edit the xhtml template of the view
 * component.
 *     
 * @author bvh
 */

public class CMSEditableComponent extends BaseComponent
{
	

	protected String getTemplate()
	{
		Object n = getAttributes().get( "template" );
		if( !(n instanceof String) && !(n instanceof Template) )
			throw new IllegalArgumentException("Attribute Template is not of Type java.lang.String or de.hybris.platform.cms.jalo.Template but of type "+(n!=null?n.getClass():null)+"." );
		String template ="";
		if( n instanceof String )
			template = (String)n;
		else
			template = ((Template)n).getCode();
		return template;
	}
	
	protected String getStart( )
	{
		HttpSession session = getSession();
		
		
		Item item = getItem( "item" );
		Website website = CommerceUtils.getWebsite();


		//System.out.println( "session="+session+" hmccallback="+CMSRequestHandler.getHMCCallbackWindow(session)+" item="+item );	
		String template = getTemplate();
		String webroot = website.getFrontendURL();
		
		
		StringWriter stw = new StringWriter();
		PrintWriter out = new PrintWriter( stw );
		String code = item instanceof AbstractCMSItem ? ((AbstractCMSItem)item).getCode() : ""+item;
		out.println( "<!-- start CMSEditable: "+code+" template:"+template+" -->" );
		
		if (CMSRequestHandler.getHMCCallbackWindow(session) != null
				&& (item != null || CMSRequestHandler.canEditTemplates(session)))
		{

			final String typeView = Config.getParameter( "cms.typeview" );
			final String onMouseOver;
			final String onMouseOut;
			if( typeView != null && "border".equals( typeView ) )
			{
				onMouseOver = "\"event.cancelBubble=true;return showBorder(this);\"";
				onMouseOut = "\"event.cancelBubble=true;return removeBorder(this);\"";					
			}
			else
			{
				onMouseOver = "\"event.cancelBubble=true;return highlight(this);\"";
				onMouseOut = "\"event.cancelBubble=true;return lowlight(this);\"";
			}
			
			final StringBuilder onContextMenu = new StringBuilder();
			onContextMenu.append( "showCMSMenu( document.getElementById('cmscontextmenu'), event);" );
							
			if( CMSRequestHandler.canEditTemplates(session) && template!=null )
			{
				onContextMenu.append( "addTemplateLink(document.getElementById('cmscontextmenu'), '"+CMSRequestHandler.getLocalizedString(session, "edittemplate")+"', '"+CMSRequestHandler.getHMCCallbackEvent(session)+"', '"+CMSRequestHandler.getHMCCallbackWindow(session)+"', '"+template+"', '" +webroot+ "');" );
			}
			if( item != null )
			{
				onContextMenu.append( "addItemLinks(document.getElementById('cmscontextmenu'), '"+CMSRequestHandler.getLocalizedString(session, "editcontent")+"', '"+CMSRequestHandler.getLocalizedString(session, "copycontent")+"','"+CMSRequestHandler.getHMCCallbackEvent(session)+"', '"+CMSRequestHandler.getHMCCallbackWindow(session)+"', '"+item.getPK().toString()+"', '" +webroot+ "');" );
			}
			onContextMenu.append( "event.cancelBubble=true;return false;" );

			out.println( "<span onmouseover="+onMouseOver+" onmouseout="+onMouseOut+" oncontextmenu=\""+onContextMenu.toString()+"\">" );
		}

		return stw.toString();
	}		

	protected String getEnd()
	{
		Item item = getItem( "item" );
		String template = getTemplate();
		
		StringWriter stw = new StringWriter();
		PrintWriter out = new PrintWriter( stw );
		String code = item instanceof AbstractCMSItem ? ((AbstractCMSItem)item).getCode() : ""+item;
		
		out.println( "<!-- end CMSEditable: "+code+" template:"+template+" -->" );

		if (CMSRequestHandler.getHMCCallbackWindow(getSession()) != null
			&& (item != null || CMSRequestHandler.canEditTemplates(getSession())))
		{
				out.println( "</span>" );
		}
		return stw.toString();
	}	
	
	
	/**
	 * Method that is invoked when the opening tag is handled.    
	 */
	@Override
	public void encodeBegin( FacesContext ctx ) throws IOException
	{
      ctx.getResponseWriter().write( getStart() );
	}
	
	/**
	 * Method that is invoked when the closing tag is handled.    
	 */
	@Override
	public void encodeEnd( FacesContext ctx ) throws IOException
	{
		ctx.getResponseWriter().write( getEnd() );
	}

	
	/**
	 * Gets the component family.
	 */
	@Override
	public String getFamily()
	{
		return "de.hybris.cms";
	}
	
}
