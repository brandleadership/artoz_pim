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

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.shared_impl.renderkit.html.util.HTMLEncoder;

import sun.text.normalizer.UTF16;

import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;

public final class LexikonFilter implements Filter
{
	private FilterConfig filterConfig = null;

	private List<Pattern> patterns;

	private String targetPage;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig = filterConfig;
		targetPage = WebsiteConstants.LEXIKON;
		patterns = this.getPatterns();
	}

	private List getPatterns()
	{
		List pattern = null;
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch()
					.search(
								"SELECT {" + LexikonParagraphData.PK + "} FROM {"
											+ WebsiteConstants.TC.LEXIKONPARAGRAPHDATA + "} ",
								LexikonParagraphData.class);
		pattern = (List) res.getResult();
		return pattern;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
				ServletException
	{
		if (filterConfig == null)
			return;

		PrintWriter out = response.getWriter();
		CharResponseWrapper wrapper = new CharResponseWrapper((HttpServletResponse) response);
		chain.doFilter(request, wrapper);
		String encoding = wrapper.getCharacterEncoding();
		if (patterns != null && encoding.equals("UTF-8") || patterns != null && encoding.equals("ISO-8859-1"))
		{
			String responseText = wrapper.toString();
			Iterator i = patterns.iterator();
			if (patterns != null)
			{
				while (i.hasNext())
				{
					LexikonParagraphData data = (LexikonParagraphData) i.next();

					if (data != null)
					{
						if (data.getName() != null)
						{
							String name = HTMLEncoder.encode(data.getName());
							Pattern pattern = Pattern.compile(name);
							Matcher matcher = pattern.matcher(responseText);
							responseText = matcher.replaceAll("<a href=\"" + this.targetPage + "#lexikon-"
										+ data.toString() + "\">" + name + "</a>");
							
							String nameUTF = URLEncoder.encode(data.getName(), "UTF-8");
							Pattern patternUTF = Pattern.compile(nameUTF);
							Matcher matcherUTF = patternUTF.matcher(responseText);
							responseText = matcherUTF.replaceAll("<a href=\"" + this.targetPage + "#lexikon-"
										+ data.toString() + "\">" + nameUTF + "</a>");
						}
					}
				}
			}
			String text = new String(responseText.getBytes(), response.getCharacterEncoding());
			response.setContentLength(text.length());
			//response.setCharacterEncoding(encoding);
			out.write(text);
		}
		else
			out.write(wrapper.toString());
		out.close();
	}

	public void destroy()
	{
		this.filterConfig = null;
	}

	private void setPatterns()
	{

	}

}
