package ch.screenconcept.artoz.importer;

public class NewsletterCustomerCSVFileLine extends AbstractCSVFileLine
{

	public NewsletterCustomerCSVFileLine(String[] line)
	{
		super(line);
	}

	public String getCompany()
	{
		return getColumn(0);
	}

	public String getName()
	{
		return getColumn(1);
	}

	public String getFirstname()
	{
		return getColumn(2);
	}

	public String getEMail()
	{
		return getColumn(3);
	}

	public String getFax()
	{
		return getColumn(4);
	}

}
