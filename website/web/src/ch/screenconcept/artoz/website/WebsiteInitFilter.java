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

import de.hybris.platform.cms.web.CMSRequestHandler;
import de.hybris.platform.cms.web.CMSServletFilter;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.Tenant;
import de.hybris.platform.util.HybrisInitFilter;
import de.hybris.platform.util.Utilities;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Configured as Filter in the <code>web.xml</code>. See
 * <link>http://java.sun.com/j2ee/1.4/docs/tutorial/doc/Servlets8.html#wp64572</link>
 * for futher information on the concepts of filters.
 */
public class WebsiteInitFilter extends HybrisInitFilter
{
	private static final Logger log = Logger.getLogger(WebsiteInitFilter.class);

	@Override
	public boolean doPreRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		// TODO Auto-generated method stub
		
		return super.doPreRequest(request, response);
	}

	@Override
	public void doPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		// TODO Auto-generated method stub
		CharResponseWrapper wrapper = new CharResponseWrapper((HttpServletResponse) response);
		
		PrintWriter out = null;
		
		if(wrapper.getContentType().equals("text/html;charset=UTF-8"))
		{
			
			CharArrayWriter caw = new CharArrayWriter();
			try
			{
				out = response.getWriter();
				caw.write( wrapper.toString().substring(0, wrapper.toString().indexOf("</body>")) );
				caw.write("<html><body><div>bla</div>\n</body></html>");
				response.setContentLength(caw.toString().length());
				out.write(caw.toString());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			out.write(wrapper.toString());
		out.close();
		HttpServletResponse ret = new CharResponseWrapper((HttpServletResponse)wrapper);
		super.doPostRequest(request, ret);
	}
}
