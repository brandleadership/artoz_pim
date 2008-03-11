package ch.screenconcept.artoz.constants;

import ch.screenconcept.artoz.jalo.Tools;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Country;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.numberseries.NumberSeriesManager;
import de.hybris.platform.jalo.product.ProductManager;
import de.hybris.platform.jalo.product.Unit;



/**
 * Global class for all Artoz constants.  
 * You can add global constants for your extension into this class 
 */
public final class ArtozConstants extends GeneratedArtozConstants
{
	public static final String NEWSLETTERGROUP = "newslettergroup";
	public static final String STANDARDCATALOG = "Artoz - Standard";
	public static final String NEWSLETTER_PREFIX = "Artoz_Newsletter_";
	
	public static final class MimeTypes
	{
		public static final String XML = "text/xml";

		public static final String CSV = "text/x-comma-separated-values";

		public static final String XSL = "application/xslt+xml";
		
		public static final String TXT = "text/plain";
	}

	public static final class Charsets
	{
		public static final String UTF8 = "UTF-8";
		public static final String ISO_8859_1 = "ISO-8859-1";
		public static final String LATIN_1 = "latin1";
		public static final String CP1252 = "cp1252";
	}


	public static final class Languages
	{
		public final class IsoCodes
		{
			public static final String GERMAN = "deCH";

			public static final String ENGLISH = "en";

			public static final String ITALIAN = "itCH";

			public static final String FRENCH = "frCH";

			public static final String SPANISH = "es";
			
			public static final String PORTUGUESE = "pt";
		}

		public static Language getGerman()
		{
			return (Language) Tools.get(Language.class, Language.ISOCODE, IsoCodes.GERMAN);
		}

		public static SessionContext getGermanCtx()
		{
			final SessionContext ctxDE = JaloSession.getCurrentSession().createSessionContext();
			ctxDE.setLanguage(getGerman());
			return ctxDE;
		}

		public static Language getEnglish()
		{
			return (Language) Tools.get(Language.class, Language.ISOCODE, IsoCodes.ENGLISH);
		}

		public static SessionContext getEnglishCtx()
		{
			final SessionContext ctxEN = JaloSession.getCurrentSession().createSessionContext();
			ctxEN.setLanguage(getEnglish());
			return ctxEN;
		}

		public static Language getItalian()
		{
			return (Language) Tools.get(Language.class, Language.ISOCODE, IsoCodes.ITALIAN);
		}

		public static SessionContext getItalianCtx()
		{
			final SessionContext ctxEN = JaloSession.getCurrentSession().createSessionContext();
			ctxEN.setLanguage(getItalian());
			return ctxEN;
		}

		public static Language getFrench()
		{
			return (Language) Tools.get(Language.class, Language.ISOCODE, IsoCodes.FRENCH);
		}

		public static SessionContext getFrenchCtx()
		{
			final SessionContext ctxFR = JaloSession.getCurrentSession().createSessionContext();
			ctxFR.setLanguage(getFrench());
			return ctxFR;
		}
		
		public static Language getSpanish()
		{
			return (Language) Tools.get(Language.class, Language.ISOCODE, IsoCodes.SPANISH);
		}

		public static SessionContext getSpanishCtx()
		{
			final SessionContext ctxFR = JaloSession.getCurrentSession().createSessionContext();
			ctxFR.setLanguage(getSpanish());
			return ctxFR;
		}

		public static Language getPortuguese()
		{
			return (Language) Tools.get(Language.class, Language.ISOCODE, IsoCodes.PORTUGUESE);
		}

		public static SessionContext getPortugueseCtx()
		{
			final SessionContext ctxFR = JaloSession.getCurrentSession().createSessionContext();
			ctxFR.setLanguage(getPortuguese());
			return ctxFR;
		}

	}

	public static final class Countries
	{
		public final class IsoCodes
		{
			public static final String LICHTENSTEIN = "fl";
			public static final String SWITZERLAND = "ch";			
			public static final String GERMANY = "DE";
			public static final String FRANCE = "FR";
			public static final String PORTUGAL = "PT";
			public static final String SPAIN = "ES";
			public static final String USA = "US";
			public static final String GREATBRITAN = "GB";
		}

		public static Country getLichtenstein()
		{
			return (Country) Tools.get(Country.class, Country.ISOCODE, IsoCodes.LICHTENSTEIN);
		}

		public static Country getSwitzerland()
		{
			return (Country) Tools.get(Country.class, Country.ISOCODE, IsoCodes.SWITZERLAND);
		}
		
	}

	public static final class Currencies
	{
		public static final class IsoCodes
		{
			public static final String CHF = "CHF";
			public static final String EUR = "EUR";
			public static final String GBP = "GBP";
			public static final String USD = "USD";
		}

		public static final class Symbols
		{
			public static final String CHF = "CHF";
			public static final String EUR = "€";
			public static final String GBP = "£";
		}
		
		public static final Currency getCHF()
		{
			return (Currency) Tools.get(Currency.class, Currency.ISOCODE, IsoCodes.CHF);
		}

		public static final Currency getEUR()
		{
			return (Currency) Tools.get(Currency.class, Currency.ISOCODE, IsoCodes.EUR);
		}
		
		public static final Currency getGBP()
		{
			return (Currency) Tools.get(Currency.class, Currency.ISOCODE, IsoCodes.GBP);
		}

		public static final Currency getUSD()
		{
			return (Currency) Tools.get(Currency.class, Currency.ISOCODE, IsoCodes.USD);
		}
	}
	
	public static final class Units{
		
		public static final class IDs
		{
			public static final String PIECES = "pieces";
		}
				
		public static final Unit getPieces()
		{
			return ProductManager.getInstance().getUnit("pieces");
		}
	}
	
	public static final class NumberSeries
	{
		private static int currentNumber;
		
		public static final class IDs
		{
			public static final String PRODUCTIMPORT = "productimport";
			public static final String NEWSLETTERCUSTOMER = "newslettercustomer";
		}
		
		public static int getNewProductImportNumber(){
			
			NumberSeriesManager numberManager = NumberSeriesManager.getInstance();
			try {
				numberManager.getNumberSeries(IDs.PRODUCTIMPORT);
			}
			catch(JaloInvalidParameterException je){
				numberManager.createNumberSeries( IDs.PRODUCTIMPORT, "0000000000", 1, 10 );
			}
			currentNumber = Integer.parseInt(numberManager.getUniqueNumber(IDs.PRODUCTIMPORT));
			return currentNumber;
		}
		
		public static int getCurrentProductImportNumber(){
			return currentNumber;
		}
		
		public static int getNewsletterCustomerNumber(){
			
			NumberSeriesManager numberManager = NumberSeriesManager.getInstance();
			try {
				numberManager.getNumberSeries(IDs.NEWSLETTERCUSTOMER);
			}
			catch(JaloInvalidParameterException je){
				numberManager.createNumberSeries( IDs.NEWSLETTERCUSTOMER, "0000000000", 1, 10 );
			}
			return Integer.parseInt(numberManager.getUniqueNumber(IDs.NEWSLETTERCUSTOMER));
		}
	}
}
