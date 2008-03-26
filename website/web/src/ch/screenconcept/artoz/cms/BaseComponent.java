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

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import de.hybris.platform.core.PK;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloSession;

/**
 * Superclass to all JSF-Components.
 */
public abstract class BaseComponent extends UIComponentBase
{

	protected Item getItem( String param )
	{
		Object o = getAttributes().get( param );
		
		if( o instanceof String )
		{
			return JaloSession.getCurrentSession().getItem( PK.parse( (String)o ) );
		}
		else if( o instanceof PK )
		{
			return JaloSession.getCurrentSession().getItem( (PK) o );
		}
		else if( o instanceof Item )
			return (Item)o;
		else 
			return null;
	}
	
	protected HttpSession getSession() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session;
	}
	
}
