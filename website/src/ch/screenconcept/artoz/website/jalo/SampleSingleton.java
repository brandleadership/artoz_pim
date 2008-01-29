package ch.screenconcept.artoz.website.jalo;

import de.hybris.platform.core.Registry;
import de.hybris.platform.util.HybrisInitFilter;
import de.hybris.platform.util.SingletonCreator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Sample class to show how to implement tenant aware singletons
 * using {@link Registry#getSingleton(de.hybris.platform.util.SingletonCreator.Creator)}
 * and {@link SingletonCreator}.
 * <p>
 * Please note that using these singletons requires a active tenant. Normally this is done
 * automatically by the {@link HybrisInitFilter}. If you're starting own threads or use
 * it outside web application scope see {@link Registry} upon how to activate and deactivate
 * a tenant.
 * 
 * @author ag
 */
public class SampleSingleton
{
	/**
	 * My singleton creator instance. This is re required to create one singleton
	 * instance per tenant.
	 */
   private static final SingletonCreator.Creator<SampleSingleton> SINGLETON_CREATOR =

   	new SingletonCreator.Creator<SampleSingleton>()
		{
   		// caching id here
			private final String SINGLETON_CREATOR_ID = SampleSingleton.class.getName().intern();
			
			/**
			 * Each singleton creation must have a unique ID.
			 */
			@Override 
			public String getID()
			{
				return SINGLETON_CREATOR_ID;
			}
			
			/**
			 * Called to create the actual singleton instance.
			 * <p>
			 * Do initialization logic here.
			 */
			@Override 
			public SampleSingleton create() throws Exception
			{
				return new SampleSingleton();
			}
			
			/**
			 * Called upon system shutdown, re-deployment etc. Means that
			 * our tenant is about to be destroyed together with all its singletons.
			 * <p>
			 * Do cleanup logic here.
			 */
			@Override 
			public void destroy( SampleSingleton im ) throws Exception
			{
				im.destroy();
			}
		};

	/**
	 * Public instance getter. All access has to go through this.
	 */
	public static SampleSingleton getInstance()
	{
		return Registry.getSingleton( SINGLETON_CREATOR );
	}
	
	private final Map<String,Object> myCache;	
		
	/**
	 * Private constructor to forbid creation outside.
	 */
	private SampleSingleton()
	{
		// init
		myCache = new ConcurrentHashMap<String, Object>();
	}
	
	private void destroy()
	{
		// cleanup
	}
	
	public Object get( String key )
	{
		return myCache.get( key );
	}
	
	public void cache( String key, Object value )
	{
		myCache.put(key,value);
	}
}
