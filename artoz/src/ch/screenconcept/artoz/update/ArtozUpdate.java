package ch.screenconcept.artoz.update;

import java.util.ArrayList;
import java.util.Collection;

import ch.screenconcept.artoz.constants.ArtozConstants;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.product.ProductManager;

public class ArtozUpdate
{
	private static String[] languageISOCodes =
	{ ArtozConstants.Languages.IsoCodes.GERMAN, ArtozConstants.Languages.IsoCodes.ENGLISH,
				ArtozConstants.Languages.IsoCodes.FRENCH, ArtozConstants.Languages.IsoCodes.SPANISH,
				ArtozConstants.Languages.IsoCodes.PORTUGUESE, ArtozConstants.Languages.IsoCodes.ITALIAN };

	private static String[][] currencyISOCodes =
	{
	{ ArtozConstants.Currencies.IsoCodes.CHF, ArtozConstants.Currencies.Symbols.CHF },
	{ ArtozConstants.Currencies.IsoCodes.EUR, ArtozConstants.Currencies.Symbols.EUR },
	{ ArtozConstants.Currencies.IsoCodes.GBP, ArtozConstants.Currencies.Symbols.GBP } };

	public static void createLanguages() throws ConsistencyCheckException
	{
		for (String code : languageISOCodes)
		{
			try
			{
				C2LManager.getInstance().getLanguageByIsoCode(code);
			}
			catch (JaloItemNotFoundException je)
			{
				Language language = C2LManager.getInstance().createLanguage(code);
				language.setActive(true);
			}
		}
	}

	public static void createCurrencies() throws ConsistencyCheckException
	{
		for (String[] code : currencyISOCodes)
		{
			try
			{
				C2LManager.getInstance().getCurrencyByIsoCode(code[0]);
			}
			catch (JaloItemNotFoundException je)
			{
				Currency currency = C2LManager.getInstance().createCurrency(code[0]);
				currency.setSymbol(code[1]);
				currency.setDigits(2);
				currency.setActive(true);
				currency.setConversionFactor(1.00);

				if (code[0].equals(currencyISOCodes[0][0]))
					currency.setActive(true);
				else
					currency.setActive(false);
			}
		}
	}

	public static void createCatalog()
	{
		CatalogManager manager = CatalogManager.getInstance();
		if (manager.getCatalog(ArtozConstants.STANDARDCATALOG) == null){
			manager.createCatalog(ArtozConstants.STANDARDCATALOG);
			CatalogVersion version = manager.createCatalogVersion(manager.getCatalog(ArtozConstants.STANDARDCATALOG), "Online", ArtozConstants.Languages.getGerman());
			version.setActive(true);
			Collection<Language> languages = new ArrayList<Language>();
			languages.add(ArtozConstants.Languages.getGerman());
			languages.add(ArtozConstants.Languages.getEnglish());
			languages.add(ArtozConstants.Languages.getFrench());
			languages.add(ArtozConstants.Languages.getItalian());
			languages.add(ArtozConstants.Languages.getSpanish());
			languages.add(ArtozConstants.Languages.getPortuguese());
			version.setLanguages(languages);
		}
	}
	
	public static void creatUnits(){
		if (ProductManager.getInstance().getUnit(ArtozConstants.Units.IDs.PIECES) == null)
			ProductManager.getInstance().createUnit( "p" ,ArtozConstants.Units.IDs.PIECES);
	}
}