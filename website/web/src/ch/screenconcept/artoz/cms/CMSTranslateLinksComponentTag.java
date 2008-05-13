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
 *  
 */
package ch.screenconcept.artoz.cms;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;
//import org.apache.myfaces.taglib.UIComponentTagBase;

/**
 * Helper class that bridges between a tag and a component.
 * @author bvh
 */
public class CMSTranslateLinksComponentTag extends UIComponentTagBase
{

	Object website;


	public Object getWebsite()
	{
		return website;
	}

	public void setWebsite( Object website )
	{
		this.website = website;
	}
	
	@Override
	public String getComponentType()
	{
		return "de.hybris.cms.CMSTranslateLinksComponent";
	}

	@Override
	protected void setProperties( UIComponent component )
	{
		super.setProperties( component );
		component.getAttributes().put( "website", website );
	}

	@Override
	public String getRendererType()
	{
		return null;
	}

}
