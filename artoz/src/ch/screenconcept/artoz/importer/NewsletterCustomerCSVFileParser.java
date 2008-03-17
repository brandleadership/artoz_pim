package ch.screenconcept.artoz.importer;

import java.io.IOException;
import java.io.InputStream;

import ch.screenconcept.artoz.exceptions.CSVFormatException;

public class NewsletterCustomerCSVFileParser extends AbstractCSVFileParser
{

	public NewsletterCustomerCSVFileParser(InputStream inputStream, final char columnChar, int columnCount) throws IOException
	{
		super(inputStream, columnChar, columnCount);
	}

	@Override
	protected AbstractCSVFileLine create(String[] content) throws CSVFormatException
	{
		return new NewsletterCustomerCSVFileLine(content);
	}

}
