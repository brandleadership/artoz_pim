package ch.screenconcept.artoz.publication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.jalo.ArtozManager;

import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.AttributeDescriptor;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.xprint.constants.PrintConstants;
import de.hybris.platform.xprint.jalo.Chapter;
import de.hybris.platform.xprint.jalo.LayoutTemplate;
import de.hybris.platform.xprint.jalo.MasterPage;
import de.hybris.platform.xprint.jalo.Page;
import de.hybris.platform.xprint.jalo.Placement;
import de.hybris.platform.xprint.jalo.PlacementRelationDescriptor;
import de.hybris.platform.xprint.jalo.PrintManager;
import de.hybris.platform.xprint.jalo.ProductPlacement;
import de.hybris.platform.xprint.jalo.Publication;
import de.hybris.platform.xprint.jalo.TablePlacement;

public class PriceListGeneratorSave
{
	static int pageCounter = 0;

	public void generate(Publication publication, MasterPage masterPageLayoutTemplate, LayoutTemplate layoutTemplate,
				LayoutTemplate staffelpreisLayoutTemplate,

				Category category)
	{
		TablePlacement tablePlacement = null;
		List<TablePlacement> placementList = new ArrayList<TablePlacement>();
		Collection<Product> products = category.getProducts();
		int productCounter = 0;
		int placementCounter = 0;
		Page singlePage = null;
		for (Product product : products)
		{
			if (productCounter % 55 == 0 || productCounter == 0)
			{
				if (placementCounter % 2 == 0 || placementCounter == 0)
				{
					if (placementCounter != 0)
					{
						addPlacements(singlePage, placementList);
						((Chapter) publication.getChapters(0, 1).iterator().next()).addToPages(singlePage);
						placementList.clear();
					}

					singlePage = createDynamicPage(publication, masterPageLayoutTemplate);
				}
				tablePlacement = createTablePlacement(publication, layoutTemplate);
				placementList.add(tablePlacement);
				placementCounter++;
			}
			createProductPlacement(tablePlacement, product);
			productCounter++;
		}

		// the last placement is not set in the loop
		addPlacements(singlePage, placementList);
		((Chapter) publication.getChapters(0, 1).iterator().next()).addToPages(singlePage);
	}

	public TablePlacement createTablePlacement(Publication publication, LayoutTemplate layoutTemplate)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TablePlacement.VALUETYPE, TypeManager.getInstance().getComposedType(
					PrintConstants.TC.XPRODUCTPLACEMENT));
		params.put(TablePlacement.PUBLICATION, publication);
		params.put(TablePlacement.LAYOUTTEMPLATE, layoutTemplate);

		ArtozTablePlacement tablePlacement = ArtozManager.getInstance().createArtozXTablePlacement(params);
		Collection<AttributeDescriptor> tabecolumns = new ArrayList<AttributeDescriptor>(4);
		ComposedType tableEntryType = TypeManager.getInstance().getComposedType(PrintConstants.TC.XPRODUCTPLACEMENT);
		tabecolumns.add(tableEntryType.getAttributeDescriptor(ProductPlacement.CODE));
		tabecolumns.add(tableEntryType
					.getAttributeDescriptor(ArtozConstants.Attributes.XProductPlacement.NUMBERCONTENTUNITS));
		tabecolumns.add(tableEntryType.getAttributeDescriptor(ProductPlacement.PRICES));
		tabecolumns.add(tableEntryType.getAttributeDescriptor(ArtozConstants.Attributes.XProductPlacement.MSRPRICE));
		tablePlacement.setTableAttributes(tabecolumns);

		return tablePlacement;
	}

	public Page createSinglePage(Publication publication, MasterPage masterPageLayoutTemplate)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(Page.PUBLICATION, publication);
		params.put(Page.CODE, "Page" + pageCounter++);
		params.put(Page.LAYOUTTEMPLATE, masterPageLayoutTemplate);
		Page singlePage;
		try
		{
			singlePage = (Page) TypeManager.getInstance().getComposedType(PrintConstants.TC.XDEFAULTSINGLEPAGE)
						.newInstance(params);
		}
		catch (Exception e)
		{
			throw new JaloSystemException(e);
		}
		singlePage.setAlignment(EnumerationManager.getInstance().getEnumerationValue(PrintConstants.TC.XPAGEALIGNMENT,
					PrintConstants.Enumerations.XPageAlignment.AUTO));
		return singlePage;
	}

	public Page createDynamicPage(Publication publication, MasterPage masterPageLayoutTemplate)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(Page.PUBLICATION, publication);
		params.put(Page.CODE, "Page" + pageCounter++);
		params.put(Page.LAYOUTTEMPLATE, masterPageLayoutTemplate);
		Page singlePage;
		try
		{
			singlePage = (Page) TypeManager.getInstance().getComposedType(PrintConstants.TC.XDEFAULTDYNAMICRANGE)
						.newInstance(params);
		}
		catch (Exception e)
		{
			throw new JaloSystemException(e);
		}
		singlePage.setAlignment(EnumerationManager.getInstance().getEnumerationValue(PrintConstants.TC.XPAGEALIGNMENT,
					PrintConstants.Enumerations.XPageAlignment.AUTO));
		return singlePage;
	}

	public void addProducts(TablePlacement tablePlacement, Category category)
	{
		createProductsPlacements(tablePlacement, category.getProducts());
	}

	public void createProductsPlacements(TablePlacement tablePlacement, Collection<Product> products)
	{
		PrintManager printMan = PrintManager.getInstance();

		for (Product product : products)
		{
			createProductPlacement(tablePlacement, product);
		}
	}

	public void createProductPlacement(TablePlacement tablePlacement, Product product)
	{
		PrintManager printMan = PrintManager.getInstance();
		tablePlacement.addToValues((Placement) printMan
					.createProductPlacement(product, tablePlacement.getPublication()));
	}

	/**
	 * Adds a Collection of Placements to the given Page
	 * 
	 * @param page
	 *            The Page, to which to add the placements
	 * @param newPlacements
	 *            The Placements that shall be added
	 */
	private void addPlacements(final Page page, final Collection newPlacements)
	{
		for (Iterator placementsADiter = page.getPlacementAttributes().iterator(); placementsADiter.hasNext();)
		{
			final AttributeDescriptor currentAttributeDescriptor = (AttributeDescriptor) placementsADiter.next();
			if (currentAttributeDescriptor instanceof PlacementRelationDescriptor)
			{
				try
				{
					List placements = new ArrayList();
					Collection currentPlacements = (Collection) page.getAttribute(currentAttributeDescriptor
								.getQualifier());
					if (currentPlacements != null)
						placements.addAll(currentPlacements);

					placements.addAll(newPlacements);
					page.setAttribute(currentAttributeDescriptor.getQualifier(), placements);
				}
				catch (Exception e)
				{
					throw new JaloSystemException(e);
				}
			}
		}
	}

}
