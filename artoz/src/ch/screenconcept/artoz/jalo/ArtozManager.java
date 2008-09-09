package ch.screenconcept.artoz.jalo;

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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.campaign.NewsletterText;
import ch.screenconcept.artoz.constants.ArtozConstants;
import de.hybris.platform.cms.constants.CmsConstants;
import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.cms.jalo.Website;
import de.hybris.platform.core.Tenant;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloImplementationManager;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.extension.ExtensionManager;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.util.BridgeAbstraction;
import de.hybris.platform.util.BridgeInterface;
import de.hybris.platform.util.JaloObjectCreator;
import de.hybris.platform.util.JspContext;

/**
 * This is the extension manager of the Artoz extension.
 */
public class ArtozManager extends GeneratedArtozManager
{
	/*
	 * edit the local|project.properties to change logging behavior (properties
	 * log4j.*)
	 */
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ArtozManager.class.getName());

	/*
	 * uncomment this to register the sample product enhancer globally
	 * (eventsystem)
	 */

	/*
	 * static { new Registry.Init() { @Override protected void startup() {
	 * registerProductSampleEnhancer(); } }; }
	 */

	/*
	 * NOTE: If the extension.managersuperclass is set to 'PriceFactory' (i.e.
	 * you are using PriceFactory as superclass of this class), then you have to
	 * REMOVE the following getInstance() Method!! (PriceFactory already
	 * provides an implementation of getInstance).
	 */
	/**
	 * get the valid instance of this manager
	 * 
	 * @return the current instance of this manager
	 */
	public static ArtozManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (ArtozManager) em.getExtension(ArtozConstants.EXTENSIONNAME);
	}

	/**
	 * Implement this method to create initial objects. This method will be
	 * called by system creator during initialization and system update. Be sure
	 * that this method can be called repeatedly.
	 * 
	 * An example usage of this method is to create required cronjobs or
	 * modifying the type system (setting e.g some default values)
	 * 
	 * @param params
	 *            the parameters provided by user for creation of objects for
	 *            the extension
	 * @param jspc
	 *            the jsp context; you can use it to write progress information
	 *            to the jsp page during creation
	 */
	@Override
	public void createEssentialData(Map params, JspContext jspc) throws Exception
	{

	}

	/**
	 * Implement this method to create data that is used in your project. This
	 * method will be called during the system initialization.
	 * 
	 * An example use is to import initial data like currencies or languages for
	 * your project from an csv file.
	 * 
	 * @param params
	 *            the parameters provided by user for creation of objects for
	 *            the extension
	 * @param jspc
	 *            the jsp context; you can use it to write progress information
	 *            to the jsp page during creation
	 */
	@Override
	public void createProjectData(Map params, JspContext jspc) throws Exception
	{

	}

	// eventsystem methods

	public static class ItemMethodInterceptor implements MethodInterceptor, JaloObjectCreator, CallbackFilter
	{
		private final Class<? extends Item> itemClass;

		private final Set<Method> targetMethods;

		private Enhancer e;

		public ItemMethodInterceptor(Class<? extends Item> itemClass, Method... methods)
		{
			assert itemClass != null;
			assert methods != null && methods.length > 0;

			this.itemClass = itemClass;
			targetMethods = new HashSet<Method>(Arrays.asList(methods));
		}

		/**
		 * Lazy creation getter of CGLIB class {@link Enhancer enhancer}.
		 */
		private Enhancer getEnhancer()
		{
			if (e == null)
			{
				synchronized (this)
				{
					if (e == null)
					{
						e = new Enhancer();
						e.setSuperclass(itemClass);
						e.setUseCache(true);
						e.setUseFactory(false);
						e.setCallbackFilter(this);
						e.setCallbacks(new Callback[]
						{ NoOp.INSTANCE, this });
					}
				}
			}
			return e;
		}

		/**
		 * Intercepts all target method calls and wraps the actual call into
		 * {@link #before(Item, Method, Object[])} and
		 * {@link #after(Item, Method, Object[], Object[], Object)}.
		 */
		public final Object intercept(Object obj, Method method, Object[] args, MethodProxy methodproxy)
					throws Throwable
		{
			Item i = (Item) obj;
			Object[] adjustedArgs = before(i, method, args);
			Object ret = call(i, adjustedArgs, methodproxy);
			return after(i, method, args, adjustedArgs, ret);
		}

		/**
		 * Called before the actual method call. It is possible to return
		 * different arguments here.
		 * 
		 * By default this method simply returns the original arguments.
		 * 
		 * @param i
		 *            the item
		 * @param m
		 *            the method
		 * @param args
		 *            the original arguments
		 * @return the arguments to be used for calling the actual method
		 */
		protected Object[] before(Item i, Method m, Object[] args)
		{
			return args;
		}

		/**
		 * Called after the actual method call.
		 * 
		 * 
		 * By default this method simply passes on the original result.
		 * 
		 * @param i
		 *            the item
		 * @param m
		 *            the method
		 * @param originalArgs
		 *            the original arguments before the call
		 * @param args
		 *            the arguments used for the actual call
		 * @param returned
		 *            the result of the actual call
		 * @return the result to be returned as method call result
		 */
		protected Object after(Item i, Method m, Object[] originalArgs, Object[] args, Object returned)
		{
			return returned;
		}

		protected Object call(Item obj, Object[] args, MethodProxy proxy) throws Throwable
		{
			return proxy.invokeSuper(obj, args);
		}

		/**
		 * Here we're creating a jalo item instance. Later on it will be
		 * connected to its persistence delegate so don't try to invoke any
		 * business method here !
		 */
		public BridgeAbstraction createInstance(Tenant tenant, BridgeInterface impl)
		{
			return (BridgeAbstraction) getEnhancer().create();
		}

		/**
		 * To keep the generated class light enough we define <b>which</b>
		 * methods we'd like to intercept here.
		 * 
		 * This method maps untouched methods to 0 and all intercepted methods
		 * to 1 according to the configured callbacks inside our
		 * {@link Enhancer}.
		 */
		public int accept(Method method)
		{
			return targetMethods.contains(method) ? 1 : 0;
		}
	}

	/**
	 * Shows how to use configure the {@link ItemMethodInterceptor} for
	 * arbitrary item classes.
	 */
	protected static void registerProductSampleEnhancer()
	{
		try
		{
			JaloImplementationManager.replaceCoreJaloClass(Product.class, new ItemMethodInterceptor(Product.class,
						Product.class.getMethod("setCode", new Class[]
						{ SessionContext.class }), Product.class.getMethod("setName", new Class[]
						{ SessionContext.class }), Product.class.getMethod("setAllNames", new Class[]
						{ SessionContext.class, Map.class }), Product.class.getMethod("setUnit", new Class[]
						{ SessionContext.class }))
			{
				@Override
				protected Object[] before(Item p, Method m, Object[] args)
				{
					System.out.println("before calling " + m + " using " + Arrays.asList(args));
					return args;
				}

				@Override
				protected Object after(Item i, Method m, Object[] originalArgs, Object[] args, Object returned)
				{
					System.out.println("after calling " + m + " using " + Arrays.asList(args));
					return returned;
				}
			});
		}
		catch (Exception e)
		{
			throw new JaloSystemException(e);
		}
	}

	@Override
	public void setAllSetNewsletterHeadText(SessionContext ctx, Paragraph item, Map<Language, String> value)
	{
		NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
		ctx.setLanguage(null);
		newsletterText.setAllHeadtext(value);
	}

	@Override
	public Map<Language, String> getAllSetNewsletterHeadText(SessionContext ctx, Paragraph item)
	{
		NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
		if (newsletterText == null)
			return null;
		return NewsletterText.getNewsletterTextByParagraph(item).getAllHeadtext(ctx);
	}

	@Override
	public Map<Language, String> getAllSetNewsletterLinkText(SessionContext ctx, Paragraph item)
	{
		NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
		if (newsletterText == null)
			return null;
		return NewsletterText.getNewsletterTextByParagraph(item).getAllLinktext(ctx);
	}

	@Override
	public Map<Language, String> getAllSetNewsletterText(SessionContext ctx, Paragraph item)
	{
		NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
		if (newsletterText == null)
			return null;
		return NewsletterText.getNewsletterTextByParagraph(item).getAllText(ctx);
	}

	@Override
	public Media getSetNewsletterImage0(SessionContext ctx, Paragraph item)
	{
		NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
		if (newsletterText == null)
			return null;
		return NewsletterText.getNewsletterTextByParagraph(item).getImage0(ctx);
	}

	@Override
	public Media getSetNewsletterImage1(SessionContext ctx, Paragraph item)
	{
		NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
		if (newsletterText == null)
			return null;
		return NewsletterText.getNewsletterTextByParagraph(item).getImage1(ctx);
	}

	@Override
	public EnumerationValue getSetNewsletterLayout(SessionContext ctx, Paragraph item)
	{
		NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
		if (newsletterText == null)
			return null;
		return newsletterText.getImagelayout(ctx);
	}

	@Override
	public void setAllSetNewsletterLinkText(SessionContext ctx, Paragraph item, Map<Language, String> value)
	{
		NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
		ctx.setLanguage(null);
		newsletterText.setAllLinktext(ctx, value);
	}

	@Override
	public void setAllSetNewsletterText(SessionContext ctx, Paragraph item, Map<Language, String> value)
	{
		NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
		ctx.setLanguage(null);
		newsletterText.setAllText(ctx, value);
	}

	@Override
	public void setSetNewsletterImage0(SessionContext ctx, Paragraph item, Media value)
	{
		if (value != null)
		{
			NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
			newsletterText.setImage0(value);
		}
	}

	@Override
	public void setSetNewsletterImage1(SessionContext ctx, Paragraph item, Media value)
	{
		if (value != null)
		{
			NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
			newsletterText.setImage1(value);
		}
	}

	@Override
	public void setSetNewsletterLayout(SessionContext ctx, Paragraph item, EnumerationValue value)
	{
		NewsletterText newsletterText = NewsletterText.getNewsletterTextByParagraph(item);
		newsletterText.setImagelayout(value);
	}

	private boolean containsJustNullValues(Map map)
	{
		Collection<String> values = map.values();
		for (String value : values)
		{
			if (value != null)
				return false;
		}
		return true;
	}

	public static Paragraph getParagraphByCode(String code, Website website)
	{
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("code", code);
		attributes.put("website", website);
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(
					"SELECT {" + Paragraph.PK + "} FROM {" + CmsConstants.TC.PARAGRAPH + "} " + " WHERE {"
								+ Paragraph.CODE + "} = ?code AND {" + Paragraph.WEBSITE + "} = ?website ", attributes,
					Paragraph.class);
		return res.getResult().isEmpty() ? null : (Paragraph) res.getResult().get(0);
	}

	@Override
	public String getSetNewsletterHeadText(SessionContext ctx, Paragraph item)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetNewsletterLinkText(SessionContext ctx, Paragraph item)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetNewsletterName(SessionContext ctx, Paragraph item)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetNewsletterText(SessionContext ctx, Paragraph item)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSetNewsletterHeadText(SessionContext ctx, Paragraph item, String value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSetNewsletterLinkText(SessionContext ctx, Paragraph item, String value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSetNewsletterName(SessionContext ctx, Paragraph item, String value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSetNewsletterText(SessionContext ctx, Paragraph item, String value)
	{
		// TODO Auto-generated method stub
		
	}
}
