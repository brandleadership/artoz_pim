package ch.screenconcept.artoz.importer;

import java.io.IOException;
import java.io.InputStream;

public class NewsletterCustomerCSVFileParser extends AbstractCSVFileParser
{

	public NewsletterCustomerCSVFileParser(InputStream inputStream, int columnCount) throws IOException
	{
		super(inputStream, columnCount);
	}

	@Override
	protected AbstractCSVFileLine create(String[] content)
	{
		return new NewsletterCustomerCSVFileLine(content);
	}

}
