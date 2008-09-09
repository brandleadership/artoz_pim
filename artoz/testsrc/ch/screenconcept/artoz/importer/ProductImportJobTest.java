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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.jalo.ArtozManager;
import ch.screenconcept.artoz.prices.ArtozPriceRow;
import ch.screenconcept.artoz.product.ArtozProduct;
import ch.screenconcept.artoz.update.ArtozUpdate;
import de.hybris.platform.catalog.constants.CatalogConstants;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.user.UserManager;
import de.hybris.platform.testframework.JaloTest;

public class ProductImportJobTest extends JaloTest
{

	private final String FILEDIR = "/home/pascal/workspace/artoz/import";

	private final String FILENAME = "junit_mat.csv";

	private final Map<String, Object> product1Values = new HashMap<String, Object>();

	private final Map<String, Object> product2Values = new HashMap<String, Object>();

	private final Map<String, Object> product3Values = new HashMap<String, Object>();

	public ProductImportJobTest(String name)
	{
		super(name);

		product1Values.put("Material", "101292");
		product1Values.put("Warengruppe", "1150");
		product1Values.put("WGText", "Bütten Cello alle");
		product1Values.put("Abmessungen", "210X210MM");
		product1Values.put("EAN", "7612996412861");
		product1Values.put("Versandeinheit", 6.0);
		product1Values.put("Positionstypengruppe", "NORM");
		product1Values.put("Materialgruppe", 1);
		product1Values.put("Neuheitencode", 81);
		product1Values.put("TextD", "Rondo Echt-Bütten weiss");
		product1Values.put("TextE", "Palazzo");
		product1Values.put("TextF", "Palazzo");
		product1Values.put("TextI", "Palazzo");
		product1Values.put("TextP", "Palazzo");
		product1Values.put("TextS", "Palazzo");
		product1Values.put("PLCHFEinheit", 1);
		product1Values.put("PLSTCHF01", 1);
		product1Values.put("PLCHF01", 2.00);
		product1Values.put("PLEUREinheit", 1);
		product1Values.put("PLSTEUR01", 1);
		product1Values.put("PLEUR01", 1.06);
		product1Values.put("PLGBPEinheit", 1);
		product1Values.put("PLSTGBP01", 1);
		product1Values.put("PLGBP01", 0.84);

		product2Values.put("Material", "10704500-547");
		product2Values.put("Warengruppe", "4400");
		product2Values.put("WGText", "1001 Cello/CP/Blocks");
		product2Values.put("Abmessungen", "217X303MM");
		product2Values.put("EAN", "7612450295221");
		product2Values.put("Versandeinheit", 6.0);
		product2Values.put("Positionstypengruppe", "NORM");
		product2Values.put("Materialgruppe", 42);
		product2Values.put("Neuheitencode", null);
		product2Values.put("TextD", "1001 Pkg 2 Sammelm. hellrot");
		product2Values.put("TextE", "1001 pack/2 social folder light red");
		product2Values.put("TextF", "1001 emb. 2 poch./offre rouge clair");
		product2Values.put("TextI", "1001 conf. 2 cartelle rosso chiaro");
		product2Values.put("TextP", "1001 blist. 2 doss. esp. verm.claro");
		product2Values.put("TextS", "1001 paq. 2 carp. camisa rojo claro");
		product2Values.put("PLCHFEinheit", 1);
		product2Values.put("PLSTCHF01", 1);
		product2Values.put("PLCHF01", 5.4);
		product2Values.put("PLEUREinheit", 1);
		product2Values.put("PLSTEUR01", 1);
		product2Values.put("PLEUR01", 3.00);
		product2Values.put("PLGBPEinheit", 1);
		product2Values.put("PLSTGBP01", 1);
		product2Values.put("PLGBP01", 2.48);

		product3Values.put("Material", "11012-427");
		product3Values.put("Warengruppe", "4600");
		product3Values.put("WGText", "PP-Karten Cello");
		product3Values.put("Abmessungen", null);
		product3Values.put("EAN", "7612450241921");
		product3Values.put("Versandeinheit", 6.0);
		product3Values.put("Positionstypengruppe", "NORM");
		product3Values.put("Materialgruppe", 47);
		product3Values.put("Neuheitencode", null);
		product3Values.put("TextD", "PP Pkg A6, 1001 royal");
		product3Values.put("TextE", "FC pack A6, 1001 royal");
		product3Values.put("TextF", "PP emb. A6, 1001 royal");
		product3Values.put("TextI", "PP conf. A6, 1001 blu royal");
		product3Values.put("TextP", "PP blist. A6, 1001 azul royal");
		product3Values.put("TextS", ";PP paq. A6, 1001 azul real");
		product3Values.put("PLCHFEinheit", 1);
		product3Values.put("PLSTCHF01", 1);
		product3Values.put("PLCHF01", 2.45);
		product3Values.put("PLEUREinheit", 1);
		product3Values.put("PLSTEUR01", 1);
		product3Values.put("PLEUR01", 1.3);
		product3Values.put("PLGBPEinheit", 1);
		product3Values.put("PLSTGBP01", 1);
		product3Values.put("PLGBP01", 1.08);		
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		ArtozUpdate.createLanguages();
		ArtozUpdate.createCurrencies();
		ArtozUpdate.createCatalog();
		ArtozUpdate.creatUnits();
	}

	public void testPerformCronJob() throws JaloInvalidParameterException, JaloSecurityException, AbortCronJobException
	{
		Map<String, Object> paramsImportJob = new HashMap<String, Object>();
		paramsImportJob.put(ProductImportJob.CODE, "testProductImportJob");
		ProductImportJob importJob = ArtozManager.getInstance().createProductImportJob(paramsImportJob);
		registerForRemoval(importJob);

		Map<String, Object> paramsCronjob = new HashMap<String, Object>();
		paramsCronjob.put(FileImportCronjob.FILEDIRECTORY, FILEDIR);
		paramsCronjob.put(FileImportCronjob.FILENAME, FILENAME);
		paramsCronjob.put(FileImportCronjob.JOB, importJob);
		paramsCronjob.put(FileImportCronjob.SESSIONUSER, UserManager.getInstance().getUserByLogin("admin"));
		paramsCronjob.put(FileImportCronjob.SESSIONLANGUAGE, ArtozConstants.Languages.getEnglish());
		paramsCronjob.put(FileImportCronjob.SESSIONCURRENCY, ArtozConstants.Currencies.getCHF());
		FileImportCronjob cronjob = ArtozManager.getInstance().createFileImportCronjob(paramsCronjob);
		registerForRemoval(cronjob);

		importJob.performCronJob(cronjob);

		checkProduct(ArtozProduct.findArtozProduct((String) product1Values.get("Material")), product1Values);
		checkProduct(ArtozProduct.findArtozProduct((String) product2Values.get("Material")), product2Values);

	}

	private void checkProduct(ArtozProduct product, Map<String, Object> values) throws JaloInvalidParameterException,
				JaloSecurityException
	{
		assertEquals((String) values.get("Abmessungen"), product.getDimensions());
		assertEquals((String) values.get("EAN"), product.getAttribute(CatalogConstants.Attributes.Product.EAN));
		assertEquals((Double) values.get("Versandeinheit"), product
					.getAttribute(CatalogConstants.Attributes.Product.NUMBERCONTENTUNITS));
		assertEquals((String) values.get("Positionstypengruppe"), product.getItemTypeGroup());
		assertEquals((Integer) values.get("Materialgruppe"), product.getMaterialGroup());
		assertEquals((Integer) values.get("Neuheitencode"), product.getNewnessCode());

		checkNames(product, values);
		checkPrices(product, values);
		checkCategory(product, values);
	}

	private void checkNames(ArtozProduct product, Map<String, Object> values)
	{
		final SessionContext ctx = JaloSession.getCurrentSession().createSessionContext();
		ctx.setLanguage(ArtozConstants.Languages.getGerman());
		assertEquals((String) values.get("TextD"), product.getName(ctx));
		ctx.setLanguage(ArtozConstants.Languages.getEnglish());
		assertEquals((String) values.get("TextE"), product.getName(ctx));
		ctx.setLanguage(ArtozConstants.Languages.getFrench());
		assertEquals((String) values.get("TextF"), product.getName(ctx));
		ctx.setLanguage(ArtozConstants.Languages.getItalian());
		assertEquals((String) values.get("TextI"), product.getName(ctx));
		ctx.setLanguage(ArtozConstants.Languages.getSpanish());
		assertEquals((String) values.get("TextS"), product.getName(ctx));
	}

	private void checkPrices(ArtozProduct product, Map<String, Object> values)
	{
		long quantity = (Integer) values.get("PLSTCHF01");
		ArtozPriceRow priceRow = product.getPriceRow(quantity, ArtozConstants.Currencies.getCHF(), null);
		assertNotNull("No CHF price row found", priceRow);
		assertEquals((Integer) values.get("PLCHFEinheit"), priceRow.getUnitFactor());
		assertEquals((Double) values.get("PLCHF01"), (Double) priceRow.getPrice());

		quantity = (Integer) values.get("PLSTEUR01");
		priceRow = product.getPriceRow(quantity, ArtozConstants.Currencies.getEUR(), null);
		assertNotNull("No EUR price row found", priceRow);
		assertEquals((Integer) values.get("PLEUREinheit"), priceRow.getUnitFactor());
		assertEquals((Double) values.get("PLEUR01"), (Double) priceRow.getPrice());

		quantity = (Integer) values.get("PLSTGBP01");
		priceRow = product.getPriceRow(quantity, ArtozConstants.Currencies.getGBP(), null);
		assertNotNull("No GPB price row found", priceRow);
		assertEquals((Integer) values.get("PLGBPEinheit"), priceRow.getUnitFactor());
		assertEquals((Double) values.get("PLGBP01"), (Double) priceRow.getPrice());
	}

	private void checkCategory(ArtozProduct product, Map<String, Object> values){
		
		CatalogManager catalogManager = CatalogManager.getInstance();
		CatalogVersion catalogVersion = catalogManager.getCatalog(ArtozConstants.STANDARDCATALOG)
					.getActiveCatalogVersion();
		
		Collection<Category> productCategories = catalogManager.getCategoriesByProduct(catalogVersion, product);
		assertEquals(productCategories.isEmpty(), false);
		assertEquals((String) values.get("Warengruppe"), productCategories.iterator().next().getCode());
	}
	
	private void removeProduct(String code) throws ConsistencyCheckException
	{
		ArtozProduct ap = ArtozProduct.findArtozProduct(code);
		if (ap != null)
			ap.remove();
	}

	@Override
	protected void tearDown() throws Exception
	{
		removeProduct((String) product1Values.get("Material"));
		removeProduct((String) product2Values.get("Material"));
		removeProduct((String) product3Values.get("Material"));
		super.tearDown();
	}

}
