package ch.screenconcept.artoz.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.product.ArtozProduct;
import ch.screenconcept.artoz.publication.PriceListGenerator;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.hmc.jalo.ValidationException;
import de.hybris.platform.hmc.jalo.VetoException;
import de.hybris.platform.hmc.jalo.WizardEditorContext;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.util.Config;
import de.hybris.platform.xprint.jalo.LayoutTemplate;
import de.hybris.platform.xprint.jalo.MasterPage;

public class CreateProductPlacmentWizard extends GeneratedCreateProductPlacmentWizard
{
	private static Logger log = Logger.getLogger(CreateProductPlacmentWizard.class.getName());

	private MasterPage masterPageTemplate, pqsMasterPageTemplate;

	private LayoutTemplate defaultTablePlacmentTemplate, pqsTablePlacmentTemplate;

	private Map<Language, String> allHeadTextColumn1;

	private Map<Language, String> allHeadTextColumn2;

	private Map<Language, String> allHeadTextColumn3;

	private Map<Language, String> allHeadTextColumn4;

	private Map<Language, String> allHeadTextColumn5;

	private Collection<ArtozProduct> artozProducts = new ArrayList<ArtozProduct>();

	private Collection<Category> categoriesCollection = new ArrayList<Category>();

	private String publicationName;

	private Map<Language, String> allPublicationTitle;
	
	@Override
	public void initialize(WizardEditorContext ctx)
	{
		super.initialize(ctx);

		final Map<Language, String> valueHeadText1 = new HashMap<Language, String>();
		if (Config.getParameter("pricelist.headtextcolumn1.german") != null)
			valueHeadText1.put(ArtozConstants.Languages.getGerman(), Config
						.getParameter("pricelist.headtextcolumn1.german"));
		if (Config.getParameter("pricelist.headtextcolumn1.english") != null)
			valueHeadText1.put(ArtozConstants.Languages.getEnglish(), Config
						.getParameter("pricelist.headtextcolumn1.english"));
		if (Config.getParameter("pricelist.headtextcolumn1.french") != null)
			valueHeadText1.put(ArtozConstants.Languages.getFrench(), Config
						.getParameter("pricelist.headtextcolumn1.french"));
		if (Config.getParameter("pricelist.headtextcolumn1.italian") != null)
			valueHeadText1.put(ArtozConstants.Languages.getItalian(), Config
						.getParameter("pricelist.headtextcolumn1.italian"));
		if (Config.getParameter("pricelist.headtextcolumn1.spanish") != null)
			valueHeadText1.put(ArtozConstants.Languages.getSpanish(), Config
						.getParameter("pricelist.headtextcolumn1.spanish"));
		setAllHeadTextColumn1(valueHeadText1);

		final Map<Language, String> valueHeadText2 = new HashMap<Language, String>();
		if (Config.getParameter("pricelist.headtextcolumn2.german") != null)
			valueHeadText2.put(ArtozConstants.Languages.getGerman(), Config
						.getParameter("pricelist.headtextcolumn2.german"));
		if (Config.getParameter("pricelist.headtextcolumn2.english") != null)
			valueHeadText2.put(ArtozConstants.Languages.getEnglish(), Config
						.getParameter("pricelist.headtextcolumn2.english"));
		if (Config.getParameter("pricelist.headtextcolumn2.french") != null)
			valueHeadText2.put(ArtozConstants.Languages.getFrench(), Config
						.getParameter("pricelist.headtextcolumn2.french"));
		if (Config.getParameter("pricelist.headtextcolumn2.italian") != null)
			valueHeadText2.put(ArtozConstants.Languages.getItalian(), Config
						.getParameter("pricelist.headtextcolumn2.italian"));
		if (Config.getParameter("pricelist.headtextcolumn2.spanish") != null)
			valueHeadText2.put(ArtozConstants.Languages.getSpanish(), Config
						.getParameter("pricelist.headtextcolumn2.spanish"));
		setAllHeadTextColumn2(valueHeadText2);

		final Map<Language, String> valueHeadText3 = new HashMap<Language, String>();
		if (Config.getParameter("pricelist.headtextcolumn3.german") != null)
			valueHeadText3.put(ArtozConstants.Languages.getGerman(), Config
						.getParameter("pricelist.headtextcolumn3.german"));
		if (Config.getParameter("pricelist.headtextcolumn3.english") != null)
			valueHeadText3.put(ArtozConstants.Languages.getEnglish(), Config
						.getParameter("pricelist.headtextcolumn3.english"));
		if (Config.getParameter("pricelist.headtextcolumn3.french") != null)
			valueHeadText3.put(ArtozConstants.Languages.getFrench(), Config
						.getParameter("pricelist.headtextcolumn3.french"));
		if (Config.getParameter("pricelist.headtextcolumn3.italian") != null)
			valueHeadText3.put(ArtozConstants.Languages.getItalian(), Config
						.getParameter("pricelist.headtextcolumn3.italian"));
		if (Config.getParameter("pricelist.headtextcolumn3.spanish") != null)
			valueHeadText3.put(ArtozConstants.Languages.getSpanish(), Config
						.getParameter("pricelist.headtextcolumn3.spanish"));
		setAllHeadTextColumn3(valueHeadText3);

		final Map<Language, String> valueHeadText4 = new HashMap<Language, String>();
		if (Config.getParameter("pricelist.headtextcolumn4.german") != null)
			valueHeadText4.put(ArtozConstants.Languages.getGerman(), Config
						.getParameter("pricelist.headtextcolumn4.german"));
		if (Config.getParameter("pricelist.headtextcolumn4.english") != null)
			valueHeadText4.put(ArtozConstants.Languages.getEnglish(), Config
						.getParameter("pricelist.headtextcolumn4.english"));
		if (Config.getParameter("pricelist.headtextcolumn4.french") != null)
			valueHeadText4.put(ArtozConstants.Languages.getFrench(), Config
						.getParameter("pricelist.headtextcolumn4.french"));
		if (Config.getParameter("pricelist.headtextcolumn4.italian") != null)
			valueHeadText4.put(ArtozConstants.Languages.getItalian(), Config
						.getParameter("pricelist.headtextcolumn4.italian"));
		if (Config.getParameter("pricelist.headtextcolumn4.spanish") != null)
			valueHeadText4.put(ArtozConstants.Languages.getSpanish(), Config
						.getParameter("pricelist.headtextcolumn4.spanish"));
		setAllHeadTextColumn4(valueHeadText4);

		final Map<Language, String> valueHeadText5 = new HashMap<Language, String>();
		if (Config.getParameter("pricelist.headtextcolumn5.german") != null)
			valueHeadText5.put(ArtozConstants.Languages.getGerman(), Config
						.getParameter("pricelist.headtextcolumn5.german"));
		if (Config.getParameter("pricelist.headtextcolumn5.english") != null)
			valueHeadText5.put(ArtozConstants.Languages.getEnglish(), Config
						.getParameter("pricelist.headtextcolumn5.english"));
		if (Config.getParameter("pricelist.headtextcolumn5.french") != null)
			valueHeadText5.put(ArtozConstants.Languages.getFrench(), Config
						.getParameter("pricelist.headtextcolumn5.french"));
		if (Config.getParameter("pricelist.headtextcolumn5.italian") != null)
			valueHeadText5.put(ArtozConstants.Languages.getItalian(), Config
						.getParameter("pricelist.headtextcolumn5.italian"));
		if (Config.getParameter("pricelist.headtextcolumn5.spanish") != null)
			valueHeadText5.put(ArtozConstants.Languages.getSpanish(), Config
						.getParameter("pricelist.headtextcolumn5.spanish"));
		setAllHeadTextColumn5(valueHeadText5);

		ctx.enableButton(START_BUTTON);
	}

	@Override
	public void tabChanges(WizardEditorContext ctx, String fromTabName, String toTabName) throws VetoException
	{
		// TODO Auto-generated method stub
		super.tabChanges(ctx, fromTabName, toTabName);
	}

	@Override
	public void validate(WizardEditorContext ctx, String fromTab, String toTab) throws ValidationException
	{
		// TODO Auto-generated method stub
		super.validate(ctx, fromTab, toTab);
	}

	@Override
	public void start(WizardEditorContext ctx) throws VetoException
	{
		super.start(ctx);
		try
		{
			PriceListGenerator generator = new PriceListGenerator();

			for (ArtozProduct product : getArtozProducts())
				generator.addArtozProduct(product);

			for (Category category : getCategories())
				generator.addCategory(category);

			List<String> headTexts = new ArrayList<String>();
			headTexts.add(getHeadTextColumn1());
			headTexts.add(getHeadTextColumn2());
			headTexts.add(getHeadTextColumn3());
			headTexts.add(getHeadTextColumn4());
			headTexts.add(getHeadTextColumn5());
			generator.setHeadTexts(headTexts);

			generator.generate(getPublicationName(), getPublicationTitle(), getMasterPageTemplate(),
						getPqsMasterPageTemplate(), getDefauktTablePlacmentTemplate(), getPqsTablePlacmentTemplate());
		}
		catch (JaloInvalidParameterException e)
		{
			e.printStackTrace();
		}
		catch (JaloSecurityException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public MasterPage getMasterPageTemplate(SessionContext ctx)
	{
		return masterPageTemplate;
	}

	@Override
	public LayoutTemplate getDefauktTablePlacmentTemplate(SessionContext ctx)
	{
		return defaultTablePlacmentTemplate;
	}

	@Override
	public void setMasterPageTemplate(SessionContext ctx, MasterPage value)
	{
		masterPageTemplate = value;
	}

	@Override
	public void setDefauktTablePlacmentTemplate(SessionContext ctx, LayoutTemplate value)
	{
		defaultTablePlacmentTemplate = value;
	}

	@Override
	public LayoutTemplate getPqsTablePlacmentTemplate(SessionContext ctx)
	{
		return pqsTablePlacmentTemplate;
	}

	@Override
	public void setPqsTablePlacmentTemplate(SessionContext ctx, LayoutTemplate value)
	{
		pqsTablePlacmentTemplate = value;
	}

	@Override
	public Map<Language, String> getAllHeadTextColumn1(SessionContext ctx)
	{
		return allHeadTextColumn1;
	}

	@Override
	public Map<Language, String> getAllHeadTextColumn2(SessionContext ctx)
	{
		return allHeadTextColumn2;
	}

	@Override
	public Map<Language, String> getAllHeadTextColumn3(SessionContext ctx)
	{
		return allHeadTextColumn3;
	}

	@Override
	public Map<Language, String> getAllHeadTextColumn4(SessionContext ctx)
	{
		return allHeadTextColumn4;
	}

	@Override
	public Map<Language, String> getAllHeadTextColumn5(SessionContext ctx)
	{
		return allHeadTextColumn5;
	}

	@Override
	public Collection<ArtozProduct> getArtozProducts(SessionContext ctx)
	{
		return artozProducts;
	}

	@Override
	public Collection<Category> getCategories(SessionContext ctx)
	{
		return categoriesCollection;
	}

	@Override
	public String getHeadTextColumn1(SessionContext ctx)
	{
		return allHeadTextColumn1.get(ctx.getLanguage());
	}

	@Override
	public String getHeadTextColumn2(SessionContext ctx)
	{
		return allHeadTextColumn2.get(ctx.getLanguage());
	}

	@Override
	public String getHeadTextColumn3(SessionContext ctx)
	{
		return allHeadTextColumn3.get(ctx.getLanguage());
	}

	@Override
	public String getHeadTextColumn4(SessionContext ctx)
	{
		return allHeadTextColumn4.get(ctx.getLanguage());
	}

	@Override
	public String getHeadTextColumn5(SessionContext ctx)
	{
		return allHeadTextColumn5.get(ctx.getLanguage());
	}

	@Override
	public String getPublicationName(SessionContext ctx)
	{
		return publicationName;
	}

	@Override
	public void setAllHeadTextColumn1(SessionContext ctx, Map<Language, String> value)
	{
		allHeadTextColumn1 = value;
	}

	@Override
	public void setAllHeadTextColumn2(SessionContext ctx, Map<Language, String> value)
	{
		allHeadTextColumn2 = value;
	}

	@Override
	public void setAllHeadTextColumn3(SessionContext ctx, Map<Language, String> value)
	{
		allHeadTextColumn3 = value;
	}

	@Override
	public void setAllHeadTextColumn4(SessionContext ctx, Map<Language, String> value)
	{
		allHeadTextColumn4 = value;
	}

	@Override
	public void setAllHeadTextColumn5(SessionContext ctx, Map<Language, String> value)
	{
		allHeadTextColumn5 = value;
	}

	@Override
	public void setArtozProducts(SessionContext ctx, Collection<ArtozProduct> value)
	{
		artozProducts = value;
	}

	@Override
	public void setCategories(SessionContext ctx, Collection<Category> value)
	{
		categoriesCollection = value;
	}

	@Override
	public void setHeadTextColumn1(SessionContext ctx, String value)
	{
		allHeadTextColumn1.put(ctx.getLanguage(), value);
	}

	@Override
	public void setHeadTextColumn2(SessionContext ctx, String value)
	{
		allHeadTextColumn2.put(ctx.getLanguage(), value);
	}

	@Override
	public void setHeadTextColumn3(SessionContext ctx, String value)
	{
		allHeadTextColumn3.put(ctx.getLanguage(), value);
	}

	@Override
	public void setHeadTextColumn4(SessionContext ctx, String value)
	{
		allHeadTextColumn4.put(ctx.getLanguage(), value);
	}

	@Override
	public void setHeadTextColumn5(SessionContext ctx, String value)
	{
		allHeadTextColumn5.put(ctx.getLanguage(), value);
	}

	@Override
	public void setPublicationName(SessionContext ctx, String value)
	{
		publicationName = value;
	}

	@Override
	public Map<Language, String> getAllPublicationTitle(SessionContext ctx)
	{
		return allPublicationTitle;
	}

	@Override
	public String getPublicationTitle(SessionContext ctx)
	{
		return allPublicationTitle.get(ctx.getLanguage());
	}

	@Override
	public void setAllPublicationTitle(SessionContext ctx, Map<Language, String> value)
	{
		allPublicationTitle = value;
	}

	@Override
	public void setPublicationTitle(SessionContext ctx, String value)
	{
		allPublicationTitle.put(ctx.getLanguage(), value);
	}

	@Override
	public MasterPage getPqsMasterPageTemplate(SessionContext ctx)
	{
		return pqsMasterPageTemplate;
	}

	@Override
	public void setPqsMasterPageTemplate(SessionContext ctx, MasterPage value)
	{
		pqsMasterPageTemplate = value;
	}
}
