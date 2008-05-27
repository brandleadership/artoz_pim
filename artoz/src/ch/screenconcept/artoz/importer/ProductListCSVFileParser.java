package ch.screenconcept.artoz.importer;

import java.io.IOException;
import java.io.InputStream;

import ch.screenconcept.artoz.exceptions.CSVFormatException;

public class ProductListCSVFileParser extends AbstractCSVFileParser
{

	public ProductListCSVFileParser(InputStream inputStream, int columnCount, char columnChar,
				boolean ignoreHeaderLine, String charSet) throws IOException
	{
		super(inputStream, columnCount, columnChar, ignoreHeaderLine, charSet);
	}

	public ProductListCSVFileParser(InputStream inputStream, int columnCount) throws IOException
	{
		super(inputStream, columnCount);
	}

	public ProductListCSVFileParser(InputStream inputStream, char columnChar, int columnCount) throws IOException
	{
		super(inputStream, columnChar, columnCount);
	}

	public ProductListCSVFileParser(InputStream inputStream, char columnChar, int columnCount, boolean ignoreHeaderLine)
				throws IOException
	{
		super(inputStream, columnChar, columnCount, ignoreHeaderLine);
	}

	@Override
	protected AbstractCSVFileLine create(String[] content) throws CSVFormatException
	{
		return new ProductListCSVFileLine(content);
	}

}
