package ch.screenconcept.artoz.product;

import java.util.HashMap;
import java.util.Map;

import de.hybris.platform.testframework.JaloTest;

public class ArtozProductTest extends JaloTest
{
	private final String PRODUCTCODE = "testProduct";
	
	public ArtozProductTest(String name)
	{
		super(name);
	}
	
	public void testCreateAndFindArtozProduct(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ArtozProduct.CODE, PRODUCTCODE);
		ArtozProduct product = ArtozProduct.createArtozProduct(params);
		registerForRemoval(product);
		assertNotNull(ArtozProduct.findArtozProduct(PRODUCTCODE));
	}
}
