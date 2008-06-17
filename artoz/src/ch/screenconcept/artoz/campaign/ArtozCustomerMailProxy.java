package ch.screenconcept.artoz.campaign;

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
