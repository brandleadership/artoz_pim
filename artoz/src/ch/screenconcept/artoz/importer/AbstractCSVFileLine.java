package ch.screenconcept.artoz.importer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.exceptions.CSVFormatException;

public abstract class AbstractCSVFileLine
{
	private static final Logger log = Logger.getLogger(AbstractCSVFileLine.class);

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	private final String[] line;

	public AbstractCSVFileLine(final String[] line) throws CSVFormatException
	{
		this.line = line;
	}

	protected String getColumn(final int column)
	{
		return line[column];
	}

	protected int getColumnCount()
	{
		return line.length;
	}

	protected Integer getInteger(final int column)
	{
		return line[column] == null ? null : Integer.valueOf(line[column]);
	}

	protected Long getLong(final int column)
	{
		return line[column] == null ? null : Long.valueOf(line[column]);
	}

	protected Double getDouble(final int column)
	{
		return line[column] == null ? null : Double.valueOf(line[column]);
	}

	protected Boolean getBoolean(final int column)
	{
		return line[column] == null ? null : "Y".equals(line[column]);
	}

	protected Date getDate(final int column)
	{
		try
		{
			return line[column] == null ? null : dateFormat.parse(line[column]);
		}
		catch (ParseException e)
		{
			log.error("Error while parsing line '" + column + "'", e);
			return null;
		}
	}
}
