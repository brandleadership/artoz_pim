package ch.screenconcept.artoz.importer;

/*
The extension "Artoz" is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.exceptions.CSVFormatException;

public abstract class AbstractCSVFileParser
{
	private static final Logger log = Logger.getLogger(AbstractCSVFileParser.class);

	private final char tabulator;

	private final int columnCount;

	private boolean isClosed = false;

	private final InputStream inputStream;

	private final InputStreamReader inReader;

	private final BufferedReader reader;

	public AbstractCSVFileParser(final InputStream inputStream, final int columnCount, final char columnChar,
				final boolean ignoreHeaderLine, final String charSet) throws IOException
	{
		this.columnCount = columnCount;
		this.tabulator = columnChar;
		this.inputStream = inputStream;
		this.inReader = new InputStreamReader(inputStream, charSet);
		this.reader = new BufferedReader(inReader);
		// Kopfzeile ignorieren
		if (!ignoreHeaderLine)
			reader.readLine();
	}

	public AbstractCSVFileParser(final InputStream inputStream, final int columnCount) throws IOException
	{
		this(inputStream, columnCount, '\t', false, "CP1252");
	}

	public AbstractCSVFileParser(final InputStream inputStream, final char columnChar, final int columnCount)
				throws IOException
	{
		this(inputStream, columnCount, columnChar, false, "CP1252");
	}

	public AbstractCSVFileParser(final InputStream inputStream, final char columnChar, final int columnCount,
				boolean ignoreHeaderLine) throws IOException
	{
		this(inputStream, columnCount, columnChar, ignoreHeaderLine, "CP1252");
	}

	public final boolean isClosed()
	{
		return isClosed;
	}

	public final void close()
	{
		try
		{
			isClosed = true;
			reader.close();
			inReader.close();
			inputStream.close();
		}
		catch (IOException e)
		{
			log.error("Error while closing.", e);
		}
	}

	protected abstract AbstractCSVFileLine create(final String[] content) throws CSVFormatException;

	public final AbstractCSVFileLine readLine() throws IOException, CSVFormatException
	{
		final String[] content = new String[columnCount];
		if (!isClosed() && fill(content))
		{
			if (log.isDebugEnabled())
				log.debug(Arrays.asList(content));
			return create(content);
		}
		else if (!isClosed())
		{
			close();
			return null;
		}
		else
		{
			return null;
		}
	}

	private final boolean fill(final String[] params) throws IOException
	{
		Arrays.fill(params, null);

		String toParse = this.reader.readLine();
		if (toParse == null)
		{
			return false;
		}
		else
		{
			toParse = toParse.replace('\u0013', ' ');
			int paramCount = 0;
			int posBegin = 0;
			int posEnd = -1;

			for (int pos = 0; pos <= toParse.length() && paramCount < columnCount; pos++)
			{
				if (pos == toParse.length() || toParse.charAt(pos) == tabulator)
				{
					if (posEnd <= posBegin)
						posEnd = pos;

					final String columnContent = toParse.substring(posBegin, posEnd).trim();
					params[paramCount++] = "".equals(columnContent) ? null : columnContent;

					posBegin = pos + 1;
				}
			}

			return paramCount > 0;
		}
	}
}
