package ch.screenconcept.artoz.importer;

import ch.screenconcept.artoz.exceptions.CSVFormatException;

public class CustomerCSVFileLine extends AbstractCSVFileLine
{

	public CustomerCSVFileLine(String[] line) throws CSVFormatException
	{
		super(line);
	}

	public String getUID()
	{
		return getColumn(0);
	}

	public String getCountry()
	{
		return getColumn(1);
	}

	public String getName1()
	{
		return getColumn(2);
	}

	public String getName2()
	{
		return getColumn(3);
	}

	public String getCity()
	{
		return getColumn(4);
	}

	public String getZipCode()
	{
		return getColumn(5);
	}

	public String getRegion()
	{
		return getColumn(6);
	}

	public String getStreet()
	{
		return getColumn(7);
	}

	public String getFristname()
	{
		return getColumn(8);
	}

	public String getContact()
	{
		return getColumn(9);
	}

	public String getSalutation()
	{
		return getColumn(10);
	}

	public String getFax()
	{
		return getColumn(11);
	}

	public String getEMail()
	{
		return getColumn(12);
	}
}
