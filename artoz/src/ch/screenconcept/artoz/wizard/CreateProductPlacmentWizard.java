package ch.screenconcept.artoz.wizard;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.publication.PriceListGenerator;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.hmc.jalo.VetoException;
import de.hybris.platform.hmc.jalo.WizardEditorContext;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.xprint.jalo.LayoutTemplate;
import de.hybris.platform.xprint.jalo.MasterPage;
import de.hybris.platform.xprint.jalo.Publication;
import de.hybris.platform.xprint.jalo.TablePlacement;

public class CreateProductPlacmentWizard extends GeneratedCreateProductPlacmentWizard
{
	private static Logger log = Logger.getLogger(CreateProductPlacmentWizard.class.getName());

	private Category category;

	private TablePlacement tablePlacement;

	private MasterPage masterPageTemplate;

	private LayoutTemplate defaultTablePlacmentTemplate, pqsTablePlacmentTemplate;

	private Publication publication;

	@Override
	public Category getCategory(SessionContext ctx)
	{
		return category;
	}

	@Override
	public void setCategory(SessionContext ctx, Category value)
	{
		category = value;
	}

	@Override
	public TablePlacement getTablePlacement(SessionContext ctx)
	{
		return tablePlacement;
	}

	@Override
	public void setTablePlacement(SessionContext ctx, TablePlacement value)
	{
		tablePlacement = value;
	}

	@Override
	public void initialize(WizardEditorContext ctx)
	{
		super.initialize(ctx);
		ctx.enableButton(START_BUTTON);
	}

	@Override
	public void start(WizardEditorContext ctx) throws VetoException
	{
		super.start(ctx);
		try
		{
			new PriceListGenerator().generate(getPublication(), getMasterPageTemplate(), getDefauktTablePlacmentTemplate(),
						getPqsTablePlacmentTemplate(), getCategory());
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
	public Publication getPublication(SessionContext ctx)
	{
		return publication;
	}

	@Override
	public void setPublication(SessionContext ctx, Publication value)
	{
		publication = value;
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
}
