package ch.screenconcept.artoz.prices;

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

import java.util.HashMap;
import java.util.Map;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.jalo.ArtozManager;
import ch.screenconcept.artoz.product.ArtozProduct;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.order.price.JaloPriceFactoryException;
import de.hybris.platform.jalo.security.JaloSecurityException;

/**
 * Contains the values for a price row and creates ArtozPriceRows
 * 
 * @author Pascal Naef
 * 
 */
public class PriceRowValues
{
	private Long quantity = null;

	private Double price = null;

	private Currency currency = null;

	private Integer unitFactor = null;

	private EnumerationValue userGroup = null;

	public Long getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Long quantity)
	{
		this.quantity = quantity;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Integer getUnitFactor()
	{
		return unitFactor;
	}

	public void setUnitFactor(Integer unitFactor)
	{
		this.unitFactor = unitFactor;
	}

	public EnumerationValue getUserGroup()
	{
		return userGroup;
	}

	public void setUserGroup(EnumerationValue userGroup)
	{
		this.userGroup = userGroup;
	}

	/**
	 * Create a PriceRow with the given values
	 * 
	 * @param product
	 * @return PriceRow
	 * @throws JaloPriceFactoryException
	 */
	public ArtozPriceRow createPriceRow(ArtozProduct product) throws JaloPriceFactoryException
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ArtozPriceRow.PRODUCT, product);
		params.put(ArtozPriceRow.MINQTD, getQuantity());
		params.put(ArtozPriceRow.CURRENCY, getCurrency());
		params.put(ArtozPriceRow.UNIT, ArtozConstants.Units.getPieces());
		params.put(ArtozPriceRow.UNITFACTOR, getUnitFactor());
		params.put(ArtozPriceRow.PRICE, getPrice());
		params.put(ArtozPriceRow.UG, getUserGroup());
		params.put(ArtozPriceRow.UPDATECOUNTER, ArtozConstants.NumberSeries.getCurrentProductImportNumber());
		return ArtozManager.getInstance().createArtozPriceRow(params);
	}

	public void updatePriceRow(ArtozProduct product) throws JaloInvalidParameterException, JaloSecurityException,
				JaloBusinessException
	{
		ArtozPriceRow priceRow = product.getPriceRow(getQuantity(), getCurrency(), getUserGroup());

		if (priceRow != null)
		{
			priceRow.setUg(getUserGroup());
			priceRow.setAttribute(ArtozPriceRow.MINQTD, getQuantity());
			priceRow.setAttribute(ArtozPriceRow.PRICE, getPrice());
			priceRow.setAttribute(ArtozPriceRow.UNITFACTOR, getUnitFactor());
			priceRow.setAttribute(ArtozPriceRow.CURRENCY, getCurrency());
			priceRow.setAttribute(ArtozPriceRow.UPDATECOUNTER, ArtozConstants.NumberSeries
						.getCurrentProductImportNumber());
			return;
		}

		createPriceRow(product);
	}

}
