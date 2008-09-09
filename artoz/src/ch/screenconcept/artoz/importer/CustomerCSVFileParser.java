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
