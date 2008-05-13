package ch.screenconcept.artoz.publication;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import ch.screenconcept.artoz.prices.ArtozMSRPrice;
import ch.screenconcept.artoz.product.ArtozProduct;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.order.price.JaloPriceFactoryException;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.xprint.jalo.LayoutTemplate;
import de.hybris.platform.xprint.jalo.Publication;

public class PriceTableContent
{
	private final String NEWCELL = "\t";

	private final String NEWROW = "\n";

	private final String LINEBREAK = "<0x000A>";

	private static Logger log = Logger.getLogger(PriceTableContent.class.getName());

	private List<ArtozProduct> products = new ArrayList<ArtozProduct>();

	private LayoutTemplate defaultLayout, priceQuantityScaleLayout;

	private boolean hasPriceQuantityScale = false;

	private List<String> headTexts;

	private double minNumberContentUnits = 999999;

	public PriceTableContent(List<String> texts)
	{
		headTexts = texts;
	}

	public List<ArtozProduct> getProducts()
	{
		return products;
	}

	public void setProducts(List<ArtozProduct> products)
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

	public void setPriceQuantityScaleLayout(LayoutTemplate priceQuantityScaleLayout)
	{
		this.priceQuantityScaleLayout = priceQuantityScaleLayout;
	}

	public void addProduct(ArtozProduct product)
	{
		products.add(product);
	}

	public LayoutTemplate getLayoutToUse()
	{
		if (hasPriceQuantityScale)
			return getPriceQuantityScaleLayout();
		return getDefaultLayout();
	}

	public boolean hasPriceQuantityScale(){
		return hasPriceQuantityScale;
	}
	
	// todo: Define rules
	public boolean isAcceptable(Product product) throws JaloInvalidParameterException, JaloSecurityException
	{
		boolean priceQuantityScale = ((ArtozProduct) product).hasPriceQuantityScale();
		if (products.isEmpty())
		{
			log.info("set to " + priceQuantityScale);
			hasPriceQuantityScale = priceQuantityScale;
			return true;
		}
		// Check Staffel
		if (hasPriceQuantityScale != priceQuantityScale)
			return false;

		return true;
	}

	public String getText(Publication publication)
	{
		for (Product product : products)
		{
			try
			{
				if ((Double) product.getAttribute("numberContentUnits") < minNumberContentUnits) {
					minNumberContentUnits = (Double) product.getAttribute("numberContentUnits");
					if (minNumberContentUnits == 1)
						continue;
				}
			}
			catch (JaloInvalidParameterException e)
			{
				minNumberContentUnits = -1;
				continue;
			}
			catch (JaloSecurityException e)
			{
				minNumberContentUnits = -1;
				continue;
			}
		}
		
		StringBuffer sb = getHeader().append(getBody(publication));
		return sb.toString();
	}

	private StringBuffer getHeader()
	{
		StringBuffer header = new StringBuffer();

		try
		{
			final HeadTextValues headTextValues = new HeadTextValues(products.get(0).getPriceQuantityScale(1), products
						.get(0).getPriceQuantityScale(2));
			headTextValues.setMinNumberContentUnits(getUnitFormat(minNumberContentUnits));
			headTextValues.setCurrency(JaloSession.getCurrentSession().getSessionContext().getCurrency().getIsoCode());

			final VelocityContext ctx = new VelocityContext();
			ctx.put("header", headTextValues);

			final String headText1 = replaceLinebreaks(headTexts.get(0));
			header.append(evaluateVeloCity(ctx, headText1));
			header.append(NEWCELL);

			final String headText2 = replaceLinebreaks(headTexts.get(1));
			header.append(evaluateVeloCity(ctx, headText2));
			header.append(NEWCELL);

			final String headText3 = replaceLinebreaks(headTexts.get(2));
			header.append(evaluateVeloCity(ctx, headText3));
			header.append(NEWCELL);

			if (hasPriceQuantityScale)
			{
				final String headText4 = replaceLinebreaks(headTexts.get(3));
				header.append(evaluateVeloCity(ctx, headText4));
				header.append(NEWCELL);
			}

			final String headText5 = replaceLinebreaks(headTexts.get(4));
			header.append(evaluateVeloCity(ctx, headText5));
			header.append(NEWROW);
		}
		catch (JaloPriceFactoryException e)
		{
			e.printStackTrace();
		}
		catch (ParseErrorException e)
		{
			e.printStackTrace();
		}
		catch (MethodInvocationException e)
		{
			e.printStackTrace();
		}
		catch (ResourceNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return header;
	}

	private StringBuffer getBody(Publication publication)
	{
		StringBuffer body = new StringBuffer();
		for (ArtozProduct product : products)
		{
			body.append(product.getCode());
			body.append(NEWCELL);

			try
			{
				body.append(getUnitFormat((Double) product.getAttribute("numberContentUnits")));
				body.append(NEWCELL);

				double price = product.getPriceQuantityScalePriceValueForOneUnitFactor(1);
				body.append(price == -1 ? "" : getPriceFormat(price));
				body.append(NEWCELL);

				if (hasPriceQuantityScale)
				{
					body.append(getPriceFormat(product.getPriceQuantityScalePriceValueForOneUnitFactor(2)));
					body.append(NEWCELL);
				}

				ArtozMSRPrice msrPrice = product.getMsrPrice();
				if (msrPrice != null)
					body.append(getPriceFormat(product.getMsrPrice().getPriceAsPrimitive()));
				else
					body.append("-");
				body.append(NEWROW);
			}
			catch (JaloInvalidParameterException e)
			{
				e.printStackTrace();
			}
			catch (JaloSecurityException e)
			{
				e.printStackTrace();
			}
			catch (JaloPriceFactoryException e)
			{
				e.printStackTrace();
			}
		}

		return body;
	}

	private String getPriceFormat(double price)
	{
		DecimalFormat df = new DecimalFormat("##0.00");
		return df.format(price);
	}

	private String getUnitFormat(double price)
	{
		DecimalFormat df = new DecimalFormat("##0");
		return df.format(price);
	}

	private String replaceLinebreaks(String value)
	{
		if (value != null)
		{
			value = value.replaceAll("\r", "");
			return value.replaceAll("\n", LINEBREAK);
		}
		return null;
	}

	private String evaluateVeloCity(VelocityContext ctx, String value) throws ParseErrorException,
				MethodInvocationException, ResourceNotFoundException, IOException
	{
		StringWriter writer;
		writer = new StringWriter();

		Velocity.evaluate(ctx, writer, "pricelist.header", value);
		return writer.toString();
	}
}
