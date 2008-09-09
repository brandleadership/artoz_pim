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
