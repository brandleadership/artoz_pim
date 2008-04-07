package ch.screenconcept.artoz.publication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.AttributeDescriptor;
import de.hybris.platform.xprint.jalo.Placement;

public class ArtozTablePlacement extends GeneratedArtozTablePlacement
{
	private static Logger log = Logger.getLogger(ArtozTablePlacement.class.getName());

	@Override
	public List getTableData()
	{
		List<List<Object>> tableData = new ArrayList<List<Object>>();
		List<String> tableAttributes = new ArrayList<String>();
		List<Object> tableRow = new ArrayList<Object>();
		for (Iterator it = getTableAttributes().iterator(); it.hasNext();)
		{
			AttributeDescriptor ad = (AttributeDescriptor) it.next();
			tableRow.add(ad.getDescription());
			tableAttributes.add(ad.getQualifier());
		}
		tableData.add(tableRow);
		for (Iterator it = getValues().iterator(); it.hasNext();)
		{
			tableRow = new ArrayList<Object>();
			Placement placement = (Placement) it.next();
			for (Iterator<String> itAttributes = tableAttributes.iterator(); itAttributes.hasNext();)
			{
				String qualifier = (String) itAttributes.next();
				try
				{
					tableRow.add(placement.getAttribute(qualifier));
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
			tableData.add(tableRow);
		}
		return tableData;
	}

}
