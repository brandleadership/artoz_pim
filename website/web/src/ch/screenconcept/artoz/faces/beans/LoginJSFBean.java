package ch.screenconcept.artoz.faces.beans;

import java.util.HashMap;

import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.JaloSystemNotInitializedException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.user.User;

@Bean(name = "loginJSFBean", scope = Scope.SESSION)
public class LoginJSFBean
{
	private final String SUCCESSED = "submitted";

	private String customerName;

	private String customerPwd;

	private User loginUser = null;

	public String login()
	{
		setCustomerName((String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(
					"frontpage:loginform:customerName"));
		setPassword((String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(
					"frontpage:loginform:password"));
		try
		{
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("user.principal", getCustomerName());
			m.put("user.credentials", getPassword());

			JaloSession.getCurrentSession().transfer(m, true);
			
			setLoginUser(JaloSession.getCurrentSession().getUser());

			// return SUCCESSED;

			// customer =
			// UserManager.getInstance().getCustomerByLogin(customerName);
		}
		catch (JaloItemNotFoundException je)
		{
			return null;
		}
		catch (JaloInvalidParameterException e)
		{
			return null;
		}
		catch (JaloSecurityException e)
		{
			return null;
		}
		catch (JaloSystemNotInitializedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public boolean getIsAnonymous()
	{
		return JaloSession.getCurrentSession().getUser().isAnonymousCustomer();
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String getPassword()
	{
		return customerPwd;
	}

	public void setPassword(String customerPwd)
	{
		this.customerPwd = customerPwd;
	}

	public User getLoginUser()
	{
		return loginUser;
	}

	public void setLoginUser(User loginUser)
	{
		this.loginUser = loginUser;
	}


}
