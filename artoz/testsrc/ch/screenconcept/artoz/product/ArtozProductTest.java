package ch.screenconcept.artoz.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.prices.PriceRowValues;
import ch.screenconcept.artoz.update.ArtozUpdate;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.testframework.JaloTest;

public class ArtozProductTest extends JaloTest
{
	private final String PRODUCTCODE = "testProduct";

	private final String NAMEDE = "nameDE";

	private final String NAMEEN = "nameNE";

	private final String NAMEFR = "nameFR";

	private final String NAMEIT = "nameIT";

	private final String NAMEES = "nameES";

	private final String DIN = "A4";

	public ArtozProductTest(String name)
	{
		super(name);
	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		ArtozUpdate.createLanguages();
	}

	public void testCreateAndFindArtozProduct() throws JaloInvalidParameterException, JaloSecurityException,
				JaloBusinessException
	{
		createArtozProduct();

		ArtozProduct findedProduct = ArtozProduct.findArtozProduct(PRODUCTCODE);
		assertNotNull(findedProduct);

		final SessionContext ctx = JaloSession.getCurrentSession().createSessionContext();
		ctx.setLanguage(ArtozConstants.Languages.getGerman());
		assertEquals(NAMEDE, findedProduct.getName(ctx));
		ctx.setLanguage(ArtozConstants.Languages.getEnglish());
		assertEquals(NAMEEN, findedProduct.getName(ctx));
		ctx.setLanguage(ArtozConstants.Languages.getFrench());
		assertEquals(NAMEFR, findedProduct.getName(ctx));
		ctx.setLanguage(ArtozConstants.Languages.getItalian());
		assertEquals(NAMEIT, findedProduct.getName(ctx));
		ctx.setLanguage(ArtozConstants.Languages.getSpanish());
		assertEquals(NAMEES, findedProduct.getName(ctx));
	}

	public void testUpdate() throws JaloInvalidParameterException, JaloSecurityException, JaloBusinessException
	{
		ArtozProduct product = createArtozProduct();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ArtozProduct.DIN, DIN);

		product.update(params, null, null);

		ArtozProduct findedProduct = ArtozProduct.findArtozProduct(PRODUCTCODE);
		assertNotNull(findedProduct);
		assertEquals(DIN, findedProduct.getDin());
	}

	private ArtozProduct createArtozProduct() throws JaloInvalidParameterException, JaloSecurityException,
				JaloBusinessException
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ArtozProduct.CODE, PRODUCTCODE);

		final Map<Language, String> names = new HashMap<Language, String>();
		names.put(ArtozConstants.Languages.getGerman(), NAMEDE);
		names.put(ArtozConstants.Languages.getEnglish(), NAMEEN);
		names.put(ArtozConstants.Languages.getFrench(), NAMEFR);
		names.put(ArtozConstants.Languages.getItalian(), NAMEIT);
		names.put(ArtozConstants.Languages.getSpanish(), NAMEES);

		ArtozProduct product = ArtozProduct.createArtozProduct(params, names, new ArrayList<PriceRowValues>());
		registerForRemoval(product);

		return product;
	}
}
