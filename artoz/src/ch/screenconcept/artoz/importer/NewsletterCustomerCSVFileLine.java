package ch.screenconcept.artoz.importer;

import ch.screenconcept.artoz.exceptions.CSVFormatException;

public class NewsletterCustomerCSVFileLine extends AbstractCSVFileLine
{

	public NewsletterCustomerCSVFileLine(String[] line) throws CSVFormatException
	{
		super(line);
	}

	public String getCompany()
	{
		return getColumn(0);
	}

	public String getTitle()
	{
		return getColumn(1);
	}
	
	public String getName()
	{
		return getColumn(2);
	}

	public String getFirstname()
	{
		return getColumn(3);
	}

	public String getEMail()
	{
		return getColumn(4);
	}

	public String getFax()
	{
		return getColumn(5);
	}

	public String getLanguageIsoCode()
	{
		return getColumn(6);
	}
}
