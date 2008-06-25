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

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import de.hybris.platform.cms.web.CMSRequestHandler;

/**
 * This component adds javascript functions to the page necessary for the context menus.
 * @author bvh
 */
public class CMSScriptComponent  extends UIComponentBase
{
	private static final String IMAGES = "/images/cms/";
	private final static String script = "<script type=\"text/JavaScript\">\n"+
		"var currentHighlightedElement = null;\n"+
		"\n"+
		"function getLeft(element)\n"+
		"{\n"+
		"	cmsLeft = element.offsetLeft;\n"+
		"	tempElement = element.offsetParent;\n"+
		"	while (tempElement != null)\n"+
		"	{\n"+
		"		cmsLeft += tempElement.offsetLeft;\n"+
		"		tempElement = tempElement.offsetParent;\n"+
		"	}\n"+
		"	return cmsLeft;\n"+
		"}\n"+
		"\n"+
		"function getTop(element)\n"+
		"{\n"+
		"	tsop = element.offsetTop;\n"+
		"	tempElement = element.offsetParent;\n"+
		"	while (tempElement != null)\n"+
		"	{\n"+
		"		tsop += tempElement.offsetTop;\n"+
		"		tempElement = tempElement.offsetParent;\n"+
		"	}\n"+
		"	return tsop;\n"+
		"}\n"+
		"\n"+
		"function getWidth(element)\n"+
		"{\n"+
		"	return element.offsetWidth;\n"+
		"}\n"+
		"\n"+
		"function getHeight(element)\n"+
		"{\n"+
		"	return element.offsetHeight;\n"+
		"}\n"+
		"\n"+
		"function highlight(element)\n"+
		"{\n"+
		"	document.getElementById(\"marker.top\").style.visibility=\"visible\";\n"+
		"	document.getElementById(\"marker.top\").style.left=getLeft(element);\n"+
		"	document.getElementById(\"marker.top\").style.width=getWidth(element);\n"+
		"	document.getElementById(\"marker.top\").style.top=getTop(element);\n"+
		"\n"+
		"	document.getElementById(\"marker.right\").style.visibility=\"visible\";\n"+
		"	document.getElementById(\"marker.right\").style.left=getLeft(element)+getWidth(element)-2;\n"+
		"	document.getElementById(\"marker.right\").style.top=getTop(element);\n"+
		"	document.getElementById(\"marker.right\").style.height=getHeight(element);\n"+
		"\n"+
		"	document.getElementById(\"marker.bottom\").style.visibility=\"visible\";\n"+
		"	document.getElementById(\"marker.bottom\").style.left=getLeft(element);\n"+
		"	document.getElementById(\"marker.bottom\").style.width=getWidth(element);\n"+
		"	document.getElementById(\"marker.bottom\").style.top=getTop(element)+getHeight(element)-2;\n"+
		"\n"+
		"	document.getElementById(\"marker.left\").style.visibility=\"visible\";\n"+
		"	document.getElementById(\"marker.left\").style.left=getLeft(element);\n"+
		"	document.getElementById(\"marker.left\").style.top=getTop(element);\n"+
		"	document.getElementById(\"marker.left\").style.height=getHeight(element);\n"+
		"}\n"+
		"\n"+
		"function lowlight(element)\n"+
		"{\n"+
		"	document.getElementById(\"marker.top\").style.visibility=\"hidden\";\n"+
		"	document.getElementById(\"marker.right\").style.visibility=\"hidden\";\n"+
		"	document.getElementById(\"marker.bottom\").style.visibility=\"hidden\";\n"+
		"	document.getElementById(\"marker.left\").style.visibility=\"hidden\";\n"+
		"}\n"+
		"\n"+
		"function showBorder(element)\n"+
		"{\n"+
		"	element.style.border=\"2px solid red\";\n"+
		"	element.style.zIndex=\"100\";\n"+
		"	element.style.display=\"block\";\n"+
		"}\n"+
		"\n"+
		"function removeBorder(element)\n"+
		"{\n"+
		"	element.style.border=\"\";\n"+
		"	element.style.zIndex=\"\";\n"+
		"	element.style.display=\"\";\n"+
		"}\n"+
		"\n" +
		"var currentCMSMenu = null;\n"+
		"\n"+
		"function showCMSMenu( menu, event )\n"+
		"{\n"+
		"	if (currentCMSMenu != null)\n"+
		"	{\n"+
		"		currentCMSMenu.style.visibility='hidden';\n"+
		"	}\n"+
		"	currentCMSMenu = menu;\n"+
		"	currentCMSMenu.style.visibility='visible';\n"+
		"	var scrollTopPos = getScrollY();\n"+
		"	var scrollLeftPos = getScrollX();\n"+
		"	var newCMSLeft = scrollLeftPos + event.clientX - 5;\n"+
		"	var newCMSTop = scrollTopPos + event.clientY - 5;\n"+
		"	currentCMSMenu.style.left = newCMSLeft + \"px\";\n"+
		"	currentCMSMenu.style.top = newCMSTop + \"px\"\n"+
		"	if( currentCMSMenu.hasChildNodes() )\n"+
		"	{\n"+
		"		while(currentCMSMenu.childNodes.length > 0)\n"+
		"		{\n"+
		"			currentCMSMenu.removeChild( currentCMSMenu.firstChild );\n"+
		"		}\n"+
		"	}\n"+
		"}\n"+
		"\n"+
		"function addTemplateLink( menu, locString, callbackEvent, callbackWindow, template, webroot )\n"+
		"{\n"+
		"	currentCMSMenu = menu;\n"+
		"\n"+
		"	var nobr = document.createElement(\"nobr\");\n"+
		"	var link = document.createElement(\"a\");\n"+
		"	link.setAttribute( \"href\", \"#\" );\n"+
		"	link.onclick=function(){\n"+
		"		wnd = window.open( callbackEvent + \"=openTemplate(\" + template + \")\", callbackWindow + \"_cms\" );\n"+
		"		wnd.focus();\n"+
		"		document.getElementById('cmscontextmenu').style.visibility='hidden';\n"+
		"		return false;\n"+
		"	};\n"+
		"	var image = document.createElement(\"img\");\n"+
		"	image.setAttribute( \"src\", webroot+\"" + IMAGES + "editTemplate.gif\" );\n"+
		"	image.setAttribute( \"border\", \"0\" );\n"+
		"	var text = document.createTextNode(locString);\n"+
		"	link.appendChild( image );\n"+
		"	link.appendChild( text );\n"+
		"	nobr.appendChild( link );\n"+
		"	currentCMSMenu.appendChild( nobr );\n"+
		"}\n"+
		"\n"+
		"function addItemLinks( menu, locEditContent, locCopyContent, callbackEvent, callbackWindow, jaloPk, webroot )\n"+
		"{\n"+
		"	currentCMSMenu = menu;\n"+
		"	if( currentCMSMenu.hasChildNodes() )\n"+
		"	{\n"+
		"		var br1 = document.createElement(\"br\");\n"+
		"		currentCMSMenu.appendChild( br1 );\n"+
		"	}\n"+
		"	var nobr = document.createElement(\"nobr\");\n"+
		"	var link = document.createElement(\"a\");\n"+
		"	link.setAttribute( \"href\", \"#\" );\n"+
		"	link.onclick=function(){\n"+
		"		wnd = window.open( callbackEvent + \"=openItem(\" + jaloPk + \")\", callbackWindow + \"_cms\" );\n"+
		"		wnd.focus();\n"+
		"		document.getElementById('cmscontextmenu').style.visibility='hidden';\n"+
		"		return false;\n"+
		"	};\n"+
		"	var image = document.createElement(\"img\");\n"+
		"	image.setAttribute( \"src\", webroot+\"" + IMAGES +"edit.gif\" );\n"+
		"	image.setAttribute( \"border\", \"0\" );\n"+
		"	var text = document.createTextNode(locEditContent);\n"+
		"	link.appendChild( image );\n"+
		"	link.appendChild( text );\n"+
		"	nobr.appendChild( link );\n"+
		"	currentCMSMenu.appendChild( nobr );\n"+
		"\n"+
		"	var br = document.createElement(\"br\");\n"+
		"	currentCMSMenu.appendChild( br );\n"+
		"\n"+
		"	var nobr2 = document.createElement(\"nobr\");\n"+
		"	var link2 = document.createElement(\"a\");\n"+
		"	link2.setAttribute( \"href\", \"#\" );\n"+
		"	link2.onclick=function(){\n"+
		"		wnd = window.open( callbackEvent + \"=copyItem(\" + jaloPk + \")\", callbackWindow + \"_cms\");\n"+
		"		window.clipboardData.setData( \"Text\", \"component://\" + jaloPk )\n"+
		"		document.getElementById('cmscontextmenu').style.visibility='hidden';\n"+
		"		return false;\n"+
		"	};\n"+
		"	var image2 = document.createElement(\"img\");\n"+
		"	image2.setAttribute( \"src\", webroot+\"" + IMAGES + "copy.gif\" );\n"+
		"	image2.setAttribute( \"border\", \"0\" );\n"+
		"	var text2 = document.createTextNode(locCopyContent);\n"+
		"	link2.appendChild( image2 );\n"+
		"	link2.appendChild( text2 );\n"+
		"	nobr2.appendChild( link2 );\n"+
		"	currentCMSMenu.appendChild( nobr2 );\n"+							
		"}\n"+
		"\n"+
		"	function getScrollX()\n"+
		"	{\n"+
		"		var scrOfX = 0;\n"+
		"		if( typeof( window.pageYOffset ) == 'number' )\n"+
		"		{\n"+
		"			//Netscape compliant\n"+
		"			scrOfX = window.pageXOffset;\n"+
		"		}\n"+
		"		else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) )\n"+
		"		{\n"+
		"			//DOM compliant\n"+
		"			scrOfX = document.body.scrollLeft;\n"+
		"		}\n"+
		"		else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) )\n"+
		"		{\n"+
		"			//IE6 standards compliant mode\n"+
		"			scrOfX = document.documentElement.scrollLeft;\n"+
		"		}\n"+
		"		return scrOfX;\n"+
		"	}\n"+
		"\n"+
		"	function getScrollY()\n"+
		"	{\n"+
		"		var scrOfY = 0;\n"+
		"		if( typeof( window.pageYOffset ) == 'number' )\n"+
		"		{\n"+
		"			//Netscape compliant\n"+
		"			scrOfY = window.pageYOffset;\n"+
		"		}\n"+
		"		else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) )\n"+
		"		{\n"+
		"			//DOM compliant\n"+
		"			scrOfY = document.body.scrollTop;\n"+
		"		}\n"+
		"		else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) )\n"+
		"		{\n"+
		"			//IE6 standards compliant mode\n"+
		"			scrOfY = document.documentElement.scrollTop;\n"+
		"		}\n"+
		"		return scrOfY;\n"+
		"	}\n"+
		"</script>\n";
	
	@Override
	public void encodeBegin( FacesContext ctx ) throws IOException
	{
		HttpSession session = (HttpSession)ctx.getExternalContext().getSession(false); 
		if (CMSRequestHandler.getHMCCallbackWindow(session) != null)
		{
			ctx.getResponseWriter().write( script );
		}
	}
	
	@Override
	public void encodeEnd( FacesContext ctx ) throws IOException
	{
	}
	
	@Override
	public String getFamily()
	{
		return "de.hybris.cms";
	}

}
