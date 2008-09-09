package ch.screenconcept.artoz.website;

/*
The extension "website" is free software: you can redistribute it and/or modify
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

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CharResponseWrapper extends HttpServletResponseWrapper
{
	private CharArrayWriter output;

	public String toString()
	{
		return output.toString();
	}
	
	public CharResponseWrapper(HttpServletResponse response)
	{
		super(response);
		output = new CharArrayWriter();
	}

	public PrintWriter getWriter()
	{
		return new PrintWriter(output);
	}
}
