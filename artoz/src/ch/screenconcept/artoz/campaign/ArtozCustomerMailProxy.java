package ch.screenconcept.artoz.campaign;

/*
The extension "Artoz" is free software: you can redistribute it and/or modify
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

import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.jalo.user.Title;

public class ArtozCustomerMailProxy
{

	Customer customer;

	public ArtozCustomerMailProxy(Customer customer)
	{
		this.customer = customer;
	}

	public String getFullNameWithTitle()
	{
		Address address = customer.getDefaultPaymentAddress();
		StringBuffer fullName = new StringBuffer();
		try
		{
			if (address != null)
			{
				if (address.getAttribute("title") != null)
				{
					fullName.append(((Title) address.getAttribute("title")).getName());
					fullName.append(" ");
				}
				if (address.getAttribute("firstname") != null && !address.getAttribute("firstname").equals(""))
				{
					fullName.append(address.getAttribute("firstname"));
					fullName.append(" ");
				}
				fullName.append(address.getAttribute("lastname"));
			}
		}
		catch (JaloInvalidParameterException e)
		{
			e.printStackTrace();
		}
		catch (JaloSecurityException e)
		{
			e.printStackTrace();
		}
		return fullName.toString();
	}
}
