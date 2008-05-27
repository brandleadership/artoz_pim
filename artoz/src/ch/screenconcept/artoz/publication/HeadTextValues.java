package ch.screenconcept.artoz.publication;

import ch.screenconcept.artoz.prices.ArtozPriceRow;

public class HeadTextValues
{
	private String currency = null;

	private ArtozPriceRow priceRow1, priceRow2;

	private String minNumberContentUnits;

	public HeadTextValues(ArtozPriceRow pr1, ArtozPriceRow pr2)
	{
		priceRow1 = pr1;
		priceRow2 = pr2;
		if (priceRow1 != null)
			setCurrency(priceRow1.getCurrency().getIsoCode());
	}

	public String getCurrency()
	{
		return currency == null ? "" : currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getOrderQuantity1()
	{
		if (priceRow2 == null)
			return "1";
		if (priceRow1 == null)
			return "";
		return minNumberContentUnits + " - " + (priceRow1.getMinqtd() + 1);
	}

	public String getOrderQuantity2()
	{
		if (priceRow2 == null)
			return "";
		return ">" + (priceRow1.getMinqtd() + 1);
	}

	public String getUnitFactor1()
	{
		if (priceRow2 == null)
			return "";
		if (priceRow1 == null)
			return "";
		return "" + priceRow1.getUnitFactorAsPrimitive();
	}

	public String getUnitFactor2()
	{
		if (priceRow2 == null)
			return "";
		return "" + priceRow2.getUnitFactorAsPrimitive();
	}

	public String getUnitFactorWithSlash1()
	{
		String uf = getUnitFactor1();
		if ("".equals(uf))
			return "";
		return "/" + uf;
	}

	public String getUnitFactorWithSlash2()
	{
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
