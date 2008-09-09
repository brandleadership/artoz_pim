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
