package ch.screenconcept.artoz.importer;

import ch.screenconcept.artoz.exceptions.CSVFormatException;

public class ProductListCSVFileLine extends AbstractCSVFileLine
{

	public ProductListCSVFileLine(String[] line) throws CSVFormatException
	{
		super(line);
	}

	public String getCode()
	{
		return getColumn(0);
	}
}
