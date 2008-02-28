package ch.screenconcept.artoz.importer;

import java.io.IOException;
import java.io.InputStream;

import ch.screenconcept.artoz.exceptions.CSVFormatException;

public class ProductCSVFileParser extends AbstractCSVFileParser
{

	public ProductCSVFileParser(InputStream inputStream, int columnCount, char columnChar, boolean ignoreHeaderLine,
				String charSet) throws IOException
	{
		super(inputStream, columnCount, columnChar, ignoreHeaderLine, charSet);
	}

	public ProductCSVFileParser(InputStream inputStream, int columnCount) throws IOException
	{
		super(inputStream, columnCount);
	}

	public ProductCSVFileParser(InputStream inputStream, char columnChar, int columnCount) throws IOException
	{
		super(inputStream, columnChar, columnCount);
	}

	@Override
	protected AbstractCSVFileLine create(String[] content) throws CSVFormatException
	{
		return new ProductCSVFileLine(content);
	}

}
