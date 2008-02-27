package ch.screenconcept.artoz.product;

import java.util.HashMap;
import java.util.Map;

import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.testframework.JaloTest;

public class ArtozProductTest extends JaloTest
{
	private final String PRODUCTCODE = "testProduct";
	
	public ArtozProductTest(String name)
	{
		super(name);
	}
	
	public void testCreateAndFindArtozProduct() throws JaloInvalidParameterException, JaloSecurityException, JaloBusinessException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ArtozProduct.CODE, PRODUCTCODE);
		ArtozProduct product = ArtozProduct.createArtozProduct(params, null, null);
		registerForRemoval(product);
		assertNotNull(ArtozProduct.findArtozProduct(PRODUCTCODE));
	}
}
