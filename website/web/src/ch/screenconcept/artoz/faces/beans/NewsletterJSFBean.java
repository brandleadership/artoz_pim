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
import javax.servlet.http.HttpServletRequest;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import com.exedio.campaign.constants.CampaignConstants;

import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserManager;

/**
 * Managed bean that brigdes to the hmc extension.
 * 
 * @author r-simecek
 */
@Bean(name = "newsletterJSFBean", scope = Scope.SESSION)
public class NewsletterJSFBean
{
	static private final String UNREG_INVALID = "unreginvalid";
	static private final String UNREG_VALID = "unregvalid";
	
	private String email = "";
	private User user = null;
	private String name = "";
	private String fullname = "";
	private Address address = null;
	
	/**
	 *  Liest die UserID aus der URL aus.
	 */
	private void assureInitialized()
	{
		if( user==null )
		{
			try
			{
				final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				final String userid = request.getParameter("id");
				if(userid!=null)
				{
					user = UserManager.getInstance().getUserByLogin( userid );
					name = user.getName();
					address = user.getDefaultPaymentAddress();
					if(address != null)
						fullname = address.getAttribute(Address.FIRSTNAME)+" "+address.getAttribute(Address.LASTNAME);
				}
			}
			catch( JaloItemNotFoundException e )
			{
				user = null;
			}
			catch( JaloSecurityException c )
			{
				user = null;
			}
		}
	}
	
	public void setEmail( String email )
	{
		this.email = email;
	}
	
	public String getEmail()
	{
		assureInitialized();
		return email;
	}
	
	public String getName()
	{
		assureInitialized();
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getFullname()
	{
		assureInitialized();
		return fullname;
	}
	
	public void setFullname(String fullname)
	{
		this.fullname = fullname;
	}
		
	public String doNewsletterUnregistration()
	{
		String fromEmail = this.email;
		String userEmail;
		try 
		{
			userEmail = (String)user.getAttribute(CampaignConstants.Attributes.User.EMAILADDRESS);
		} 
		catch (JaloInvalidParameterException e) 
		{
			userEmail = null;
		} 
		catch (JaloSecurityException e) 
		{
			userEmail = null;
		}
		
		if ( fromEmail.equals(userEmail) )
		{
			try 
			{
				user.setAttribute("NEWSLETTERSUBSCRIBED", false);
			} 
			catch (JaloInvalidParameterException e) 
			{
				e.printStackTrace();
				return UNREG_INVALID;
			} 
			catch (JaloSecurityException e) 
			{
				e.printStackTrace();
				return UNREG_INVALID;
			} 
			catch (JaloBusinessException e) 
			{
				e.printStackTrace();
				return UNREG_INVALID;
			}
		}
		else
		{
			return UNREG_INVALID;
		}
		
		return UNREG_VALID;
	}

}
