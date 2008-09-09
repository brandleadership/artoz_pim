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

import ch.screenconcept.artoz.exceptions.CSVFormatException;

public class CustomerCSVFileLine extends AbstractCSVFileLine
{

	public CustomerCSVFileLine(String[] line) throws CSVFormatException
	{
		super(line);
	}

	public String getUID()
	{
		return getColumn(0);
	}

	public String getCountry()
	{
		return getColumn(1);
	}

	public String getName1()
	{
		return getColumn(2);
	}

	public String getName2()
	{
		return getColumn(3);
	}

	public String getCity()
	{
		return getColumn(4);
	}

	public String getZipCode()
	{
		return getColumn(5);
	}

	public String getRegion()
	{
		return getColumn(6);
	}

	public String getStreet()
	{
		return getColumn(7);
	}

	public String getFristname()
	{
		return getColumn(8);
	}

	public String getContact()
	{
		return getColumn(9);
	}

	public String getSalutation()
	{
		return getColumn(10);
	}

	public String getFax()
	{
		return getColumn(11);
	}

	public String getEMail()
	{
		return getColumn(12);
	}
}
