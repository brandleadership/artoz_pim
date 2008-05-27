/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2008 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 * $Revision$
 */
package ch.screenconcept.artoz.cms;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import de.hybris.platform.cms.jalo.Website;
import de.hybris.platform.cms.web.CMSRequestHandler;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.util.Config;
import de.hybris.platform.webfoundation.util.CommerceUtils;

/**
 * IF the website is viewed as preview from the HMC, this component encloses its body with a red frame. If the user clicks 
 * right a context menu is shown that allows to open a HMC editors of the item that is surrounded e.g. a product.
 *    
 * @author bvh
 */
public class CMSItemComponent extends BaseComponent
{
	
	protected String getStart( )
	{
		HttpSession session = getSession();
		
		Item item = getItem( "item" );
		Website website = CommerceUtils.getWebsite();
		String template = (String)getAttributes().get( "template" );
		String webroot = website.getFrontendURL();;

		//System.out.println( "session="+session+" hmccallback="+CMSRequestHandler.getHMCCallbackWindow(session)+" item="+item );	
		
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
			if( item != null )
			{
				onContextMenu.append( "addItemLinks(document.getElementById('cmscontextmenu'), '"+CMSRequestHandler.getLocalizedString(session, "editcontent")+"', '"+CMSRequestHandler.getLocalizedString(session, "copycontent")+"','"+CMSRequestHandler.getHMCCallbackEvent(session)+"', '"+CMSRequestHandler.getHMCCallbackWindow(session)+"', '"+item.getPK().toString()+"', '" +webroot+ "');" );
			}
			onContextMenu.append( "event.cancelBubble=true;return false;" );				
			return "<span onmouseover="+onMouseOver+" onmouseout="+onMouseOut+" oncontextmenu=\""+onContextMenu.toString()+"\">";
		}
		return "";
	}		

	protected String getEnd()
	{
		HttpSession session = getSession();
		Item item = getItem( "item" );
		if (CMSRequestHandler.getHMCCallbackWindow(session) != null
			&& (item != null || CMSRequestHandler.canEditTemplates(session)))
		{
				return "</span>";
		}
		return "";
	}	
	
	
	@Override
	public void encodeBegin( FacesContext ctx ) throws IOException
	{
		//System.out.println( "encoding begining" );
      ctx.getResponseWriter().write( getStart() );
	}

	@Override
	public void encodeEnd( FacesContext ctx ) throws IOException
	{
		//System.out.println( "encoding end " );
		ctx.getResponseWriter().write( getEnd() );
	}

	@Override
	public String getFamily()
	{
		return "de.hybris.cms";
	}

}
