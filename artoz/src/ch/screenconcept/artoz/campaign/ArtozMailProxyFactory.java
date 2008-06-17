package ch.screenconcept.artoz.campaign;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.exedio.campaign.jalo.CampaignContext;
import com.exedio.campaign.jalo.MailProxyCreator;
import com.exedio.campaign.jalo.MailProxyFactory;
import com.exedio.campaign.jalo.proxy.AddressMailProxy;
import com.exedio.campaign.jalo.proxy.DateMailProxy;
import com.exedio.campaign.jalo.proxy.OrderMailProxy;
import com.exedio.campaign.jalo.proxy.ProductMailProxy;

import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.jalo.order.Order;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.jalo.user.User;

public class ArtozMailProxyFactory implements MailProxyFactory
{

	private static ArtozMailProxyFactory instance = null;

	public static ArtozMailProxyFactory getInstance()
	{
		if (instance == null)
			instance = new ArtozMailProxyFactory();
		return instance;
	}

	public Object createMailProxy(Object obj, CampaignContext campaigncontext)
	{
		if (obj instanceof List)
		{
			final List<Object> proxies = new ArrayList<Object>();
			for (Object innerObject : (List) obj)
				proxies.add(createMailProxy(innerObject, campaigncontext));
			return proxies;
		}
		if (obj instanceof Set)
		{
			final Set<Object> proxies = new HashSet<Object>();
			for (final Object innerObject : (Set) obj)
				proxies.add(createMailProxy(innerObject, campaigncontext));
			return proxies;
		}
		if (obj instanceof Order)
			return new OrderMailProxy((Order) obj);
		if (obj instanceof Date)
			return new DateMailProxy((Date) obj);
		if (obj instanceof Address)
			return new AddressMailProxy((Address) obj, campaigncontext.getParticipation());
		if (obj instanceof Product)
			return new ProductMailProxy((Product) obj, campaigncontext.getCampaign(), campaigncontext
						.getParticipation());
		if (obj instanceof Customer)
			return new ArtozCustomerMailProxy((Customer) obj);
		if (obj instanceof MailProxyCreator)
			return ((MailProxyCreator) obj).createMailProxy(campaigncontext);
		if (obj instanceof NewsletterText)
			return new NewsletterTextProxy((NewsletterText) obj, campaigncontext);
		if (obj instanceof Media)
			return new NewsletterTextProxy((Media) obj);
		else
			return new JaloSystemException((new StringBuilder("Could not create MailProxy for object >")).append(
						obj.toString()).append("< class >").append(obj.getClass().getName()).append("<").toString());
	}

}
