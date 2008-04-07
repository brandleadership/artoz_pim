package ch.screenconcept.artoz.publication;

import java.util.ArrayList;
import java.util.List;

import ch.screenconcept.artoz.product.ArtozProduct;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.xprint.jalo.LayoutTemplate;

public class PriceTableContent
{
	private List<Product> products = new ArrayList<Product>();

	private LayoutTemplate defaultLayout, priceQuantityScaleLayout;

	private boolean hasPriceQuantityScale = false;

	public List<Product> getProducts()
	{
		return products;
	}

	public void setProducts(List<Product> products)
	{
		this.products = products;
	}

	public LayoutTemplate getDefaultLayout()
	{
		return defaultLayout;
	}

	public void setDefaultLayout(LayoutTemplate defaultLayout)
	{
		this.defaultLayout = defaultLayout;
	}

	public LayoutTemplate getPriceQuantityScaleLayout()
	{
		return priceQuantityScaleLayout;
	}

	public void setPriceQuantityScaleLayout(LayoutTemplate staffelpreisLayout)
	{
		this.priceQuantityScaleLayout = staffelpreisLayout;
	}

	public void addProduct(Product product)
	{
		products.add(product);
	}

	public LayoutTemplate getLayoutToUse()
	{
		if (hasPriceQuantityScale)
			return getPriceQuantityScaleLayout();
		return getDefaultLayout();
	}

	// todo: Define rules
	public boolean isAcceptable(Product product) throws JaloInvalidParameterException, JaloSecurityException
	{
		boolean priceQuantityScale = ((ArtozProduct) product).hasPriceQuantityScale();			
		if (products.isEmpty()){
			hasPriceQuantityScale = priceQuantityScale;
			return true;
		}
		
		// Check Staffel
		if (hasPriceQuantityScale && !priceQuantityScale)
			return false;
		if (!hasPriceQuantityScale && priceQuantityScale)
			return false;
		return true;
	}
}