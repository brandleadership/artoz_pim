package ch.screenconcept.artoz.publication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.product.ArtozProduct;
import ch.screenconcept.artoz.product.ArtozProductComparator;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.LocalizableItem;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.AttributeDescriptor;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.jalo.user.User;
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
import de.hybris.platform.xprint.jalo.PublicationComponent;
import de.hybris.platform.xprint.jalo.TablePlacement;
import de.hybris.platform.xprint.jalo.TextTablePlacement;

public class PriceListGenerator
{
	private static Logger log = Logger.getLogger(PriceListGenerator.class.getName());

	static int pageCounter = 0;

	private int rowPerPage = 60;

	private List<String> headTexts;

	private TreeSet<ArtozProduct> sortedProducts = new TreeSet<ArtozProduct>(new ArtozProductComparator());

	public void addArtozProduct(ArtozProduct product)
	{
		sortedProducts.add(product);
	}

	public void addCategory(Category category)
	{
		Collection<ArtozProduct> products = (Collection<ArtozProduct>) category.getProducts();
		for (ArtozProduct product : products)
			addArtozProduct(product);
	}

	public void setHeadTexts(List<String> texts)
	{
		headTexts = texts;
	}

	public List<String> getHeadTexts()
	{
		return headTexts;
	}

	public void generate(String publicationName, String publicationTitle, MasterPage masterPageLayoutTemplate,
				MasterPage priceQuantityScaleMasterPageLayoutTemplate, LayoutTemplate layoutTemplate,
				LayoutTemplate priceQuantityScaleLayoutTemplate) throws JaloInvalidParameterException,
				JaloSecurityException
	{
		log.info("founded products: " + sortedProducts.size());

		Publication publication = createPublication(publicationName, publicationTitle);
		publication.addToChapters(createChapter(publication));

		List<PriceTableContent> priceTables = new ArrayList<PriceTableContent>();
		int productCounter = 0;
		PriceTableContent priceTableContent = new PriceTableContent(getHeadTexts());
		for (ArtozProduct product : sortedProducts)
		{
			if (priceTableContent.isAcceptable(product))
				priceTableContent.addProduct(product);
			else
			{
				priceTableContent = getNewPriceTableContent(priceTableContent, priceTables, layoutTemplate,
							priceQuantityScaleLayoutTemplate);
				if (priceTableContent.isAcceptable(product))
				{
					priceTableContent.addProduct(product);
				}
			}
			productCounter++;

			if (productCounter % rowPerPage == 0 || productCounter == sortedProducts.size())
			{
				priceTableContent = getNewPriceTableContent(priceTableContent, priceTables, layoutTemplate,
							priceQuantityScaleLayoutTemplate);
				log.info("did placement to: " + productCounter);
			}
		}

		int placementCounter = 0;
		List<Placement> placementList = new ArrayList<Placement>();
		boolean hasPriceQuantityScale = false;
		for (PriceTableContent priceTable : priceTables)
		{
			TextTablePlacement tablePlacement = createTextTablePlacement(publication, priceTable.getLayoutToUse());
			tablePlacement.setText(priceTable.getText(publication));

			if (!hasPriceQuantityScale)
				hasPriceQuantityScale = priceTable.hasPriceQuantityScale();
			// TablePlacement tablePlacement = createTablePlacement(publication,
			// priceTable.getLayoutToUse());
			placementList.add(tablePlacement);
			placementCounter++;

			if (placementCounter % 2 == 0 || placementCounter == priceTables.size())
			{
				Page singlePage = createDynamicPage(publication,
							hasPriceQuantityScale ? priceQuantityScaleMasterPageLayoutTemplate
										: masterPageLayoutTemplate);
				addPlacements(singlePage, placementList);
				((Chapter) publication.getChapters(0, 1).iterator().next()).addToPages(singlePage);
				placementList.clear();
				hasPriceQuantityScale = false;
			}
		}

		publication.synchronize(PublicationComponent.SYNC_TREE);
		publication.generateGetters();
	}

	private Publication createPublication(String publicationName, String publicationTitle)
	{
		PrintManager printMan = PrintManager.getInstance();

		// TODO: get your objects (user, date, ...) in another way
		final User user = JaloSession.getCurrentSession().getUser();
		final Date date = new Date();

		// Create a special SessionContext that is used while creating and later
		// synchronizing a publication
		final SessionContext ctx = JaloSession.getCurrentSession().createSessionContext();
		ctx.setUser(user);
		ctx.setCurrency(JaloSession.getCurrentSession().getSessionContext().getCurrency());
		ctx.setLanguage(JaloSession.getCurrentSession().getSessionContext().getLanguage());

		// activate language fallback
		ctx.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);

		// **************************************************************************
		// ** Create the publication
		// **************************************************************************
		String pubCode = publicationName;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(Publication.CODE, pubCode);
		params.put(Publication.SYNCLANGUAGE, ctx.getLanguage());
		params.put(Publication.SYNCCURRENCY, ctx.getCurrency());
		params.put(Publication.SYNCUSER, user);
		params.put(Publication.SYNCIMAGEMODE, EnumerationManager.getInstance().getEnumerationValue(
					PrintConstants.TC.XIMAGEMODE, PrintConstants.Enumerations.XImageMode.HIGHRES));
		params.put(Publication.SYNCPRICEDATE, date);
		params.put(Publication.SYNCPRICEISNET, new Boolean(false));
		params.put(Publication.USABLEPLACEMENTTYPES, getUsablePlacementTypes());
		params.put(Publication.USABLEPAGETYPES, getUsablePageTypes());

		Publication publication = printMan.createXPublication(params);

		// TODO: you may set values for other languages too by using
		// setAllTitle() or setAllSubTitle() or setAllDescription()
		publication.setTitle(publicationTitle);
		return publication;
	}

	public Chapter createChapter(Publication publication)
	{
		PrintManager printMan = PrintManager.getInstance();
		Chapter rootChapter = printMan.createChapter("pricelist", publication);
		rootChapter.setTitle("pricelist");
		return rootChapter;
	}

	private TextTablePlacement createTextTablePlacement(Publication publication, LayoutTemplate layoutTemplate)
	{

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TextTablePlacement.PUBLICATION, publication);
		params.put(TextTablePlacement.LAYOUTTEMPLATE, layoutTemplate);

		return PrintManager.getInstance().createXTextTablePlacement(params);
	}

	private PriceTableContent getNewPriceTableContent(PriceTableContent priceTableContent,
				List<PriceTableContent> priceTables, LayoutTemplate layoutTemplate,
				LayoutTemplate priceQuantityScaleLayoutTemplate)
	{
		priceTableContent.setDefaultLayout(layoutTemplate);
		priceTableContent.setPriceQuantityScaleLayout(priceQuantityScaleLayoutTemplate);
		priceTables.add(priceTableContent);

		return new PriceTableContent(getHeadTexts());
	}

	public TablePlacement createTablePlacement(Publication publication, LayoutTemplate layoutTemplate)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TablePlacement.VALUETYPE, TypeManager.getInstance().getComposedType(
					PrintConstants.TC.XPRODUCTPLACEMENT));
		params.put(TablePlacement.PUBLICATION, publication);
		params.put(TablePlacement.LAYOUTTEMPLATE, layoutTemplate);
		params.put(TablePlacement.HASHEADER, false);

		TablePlacement tablePlacement = PrintManager.getInstance().createXTablePlacement(params);
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
		createProductPlacements(tablePlacement, category.getProducts());
	}

	public void createProductPlacements(TablePlacement tablePlacement, Collection<Product> products)
	{
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

	/**
	 * Returns the Placement Types that can be used within your Publication
	 * 
	 * @return The Placement Types that can be used within your Publication
	 */
	private Collection getUsablePlacementTypes()
	{
		// TODO: This method returns any existing placement type. You may modify
		// this code to return certain placement types only
		final Collection types = TypeManager.getInstance().getComposedType(Placement.class).getAllSubTypes();
		final Collection nonAbstractTypes = new ArrayList();
		if (!types.isEmpty())
		{
			for (Iterator typesIter = types.iterator(); typesIter.hasNext();)
			{
				ComposedType type = (ComposedType) typesIter.next();

				if (!type.isAbstract())
					nonAbstractTypes.add(type);
			}
		}

		return nonAbstractTypes;
	}

	/**
	 * Returns the Page Types that can be used within your Publication
	 * 
	 * @return The Page Types that can be used within your Publication
	 */
	private Collection getUsablePageTypes()
	{
		// TODO: This method returns any existing page type. You may modify this
		// code to return certain page types only
		final Collection types = TypeManager.getInstance().getComposedType(Page.class).getAllSubTypes();
		final Collection nonAbstractTypes = new ArrayList();
		if (!types.isEmpty())
		{
			for (Iterator typesIter = types.iterator(); typesIter.hasNext();)
			{
				ComposedType type = (ComposedType) typesIter.next();

				if (!type.isAbstract())
					nonAbstractTypes.add(type);
			}
		}

		return nonAbstractTypes;
	}
}
