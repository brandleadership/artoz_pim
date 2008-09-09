package ch.screenconcept.artoz.importer;

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

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.product.ArtozProduct;
import ch.screenconcept.artoz.product.ProductList;

import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;

public class ProductListImportJob extends GeneratedProductListImportJob
{
	private static Logger log = Logger.getLogger(ProductListImportJob.class.getName());

	@Override
	protected CronJobResult performCronJob(CronJob cronjob) throws AbortCronJobException
	{
		final ProductImportMediaImportCronjob importCronjob = (ProductImportMediaImportCronjob) cronjob;

		final ProductListCSVFileParser productListParser;
		final ProductList productList = importCronjob.getProductList();
		try
		{
			productListParser = new ProductListCSVFileParser(importCronjob.getMedia().getDataFromStream(), ';', 1, true);
			while (!productListParser.isClosed())
			{
				ProductListCSVFileLine line = (ProductListCSVFileLine) productListParser.readLine();
				productList.getArtozProducts().add(ArtozProduct.findArtozProduct(line.getCode()));
			}
		}
		catch (Exception e)
		{
			throw new AbortCronJobException(e.getMessage());
		}

		return importCronjob.getFinishedResult(true);
	}

}
