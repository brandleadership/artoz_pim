package ch.screenconcept.artoz.publication;

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
