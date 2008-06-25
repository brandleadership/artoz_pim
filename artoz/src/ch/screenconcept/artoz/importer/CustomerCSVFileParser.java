package ch.screenconcept.artoz.importer;

import java.io.IOException;
import java.io.InputStream;

import ch.screenconcept.artoz.exceptions.CSVFormatException;

public class CustomerCSVFileParser extends AbstractCSVFileParser
{

	public CustomerCSVFileParser(InputStream inputStream, int columnCount, char columnChar, boolean ignoreHeaderLine,
				String charSet) throws IOException
	{
		super(inputStream, columnCount, columnChar, ignoreHeaderLine, charSet);
	}

	public CustomerCSVFileParser(InputStream inputStream, int columnCount) throws IOException
	{
		super(inputStream, columnCount);
	}

	public CustomerCSVFileParser(InputStream inputStream, char columnChar, int columnCount) throws IOException
	{
		super(inputStream, columnChar, columnCount);
	}

	@Override
	protected AbstractCSVFileLine create(String[] content) throws CSVFormatException
	{
		return new CustomerCSVFileLine(content);
	}

}
