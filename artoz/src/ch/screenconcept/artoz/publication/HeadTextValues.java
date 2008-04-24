package ch.screenconcept.artoz.publication;

import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.order.price.PriceInformation;

public class HeadTextValues
{
	private String currency;
	private PriceInformation pi1, pi2; 

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
		return pi1.getQualifierValue(PriceRow.MINQTD) + " - " + getOrderQuantity2();
	}
	
	public String getOrderQuantity2(){
		if (pi2 == null)
			return "";
		return ">" +pi2.getQualifierValue(PriceRow.MINQTD) + 1;
	}
	
	public String getUnitFactor1(){
		if (pi2 == null)
			return "";
		if (pi1 == null)
			return "";
		return "" + pi1.getQualifierValue(PriceRow.UNITFACTOR);
	}

	public String getUnitFactor2(){
		if (pi2 == null)
			return "";
		return "" + pi2.getQualifierValue(PriceRow.UNITFACTOR);
	}

	public String unitFactorWithSlash1(){
		return "/" + getUnitFactor1();
	}

	public String unitFactorWithSlash2(){
		return "/" + getUnitFactor2();
	}

	
}
