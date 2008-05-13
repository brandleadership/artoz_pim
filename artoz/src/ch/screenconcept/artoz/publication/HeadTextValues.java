package ch.screenconcept.artoz.publication;

import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.order.price.PriceInformation;

public class HeadTextValues
{
	private String currency;
	private PriceInformation pi1, pi2; 
	private String minNumberContentUnits;

	public HeadTextValues(PriceInformation priceInformation1, PriceInformation priceInformation2){
		pi1 = priceInformation1;
		pi2 = priceInformation2;
	}
	
	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}
	
	public String getOrderQuantity1(){
		if (pi2 == null)
			return "1";
		if (pi1 == null)
			return "";
		return minNumberContentUnits + " - " + ((Long)pi1.getQualifierValue(PriceRow.MINQTD) + 1);
	}
	
	public String getOrderQuantity2(){
		if (pi2 == null)
			return "";
		return ">" + ((Long)pi1.getQualifierValue(PriceRow.MINQTD) + 1);
	}
	
	public String getUnitFactor1(){
		if (pi2 == null)
			return "";
		if (pi1 == null)
			return "";
		return "" + ((PriceRow)pi1.getQualifierValue("pricerow")).getUnitFactorAsPrimitive();
	}

	public String getUnitFactor2(){
		if (pi2 == null)
			return "";
		return "" + ((PriceRow)pi2.getQualifierValue("pricerow")).getUnitFactorAsPrimitive();
	}

	public String getUnitFactorWithSlash1(){
		String uf = getUnitFactor1();
		if ("".equals(uf))
			return "";
		return "/" + uf;
	}

	public String getUnitFactorWithSlash2(){
		String uf = getUnitFactor2();
		if ("".equals(uf))
			return "";
		return "/" + uf;
	}

	public String getMinNumberContentUnits()
	{
		return minNumberContentUnits;
	}

	public void setMinNumberContentUnits(String minNumberContentUnits)
	{
		this.minNumberContentUnits = minNumberContentUnits;
	}

	
}
