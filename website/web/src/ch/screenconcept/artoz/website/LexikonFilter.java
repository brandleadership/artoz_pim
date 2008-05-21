package ch.screenconcept.artoz.website;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import ch.screenconcept.artoz.website.constants.WebsiteConstants;

import de.hybris.platform.cms.jalo.Paragraph;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.util.GZIPResponseWrapper;

public final class LexikonFilter implements Filter
{
	private FilterConfig filterConfig = null;
	
	private List<Pattern> patterns;
	
	private String targetPage;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig = filterConfig;
		targetPage = WebsiteConstants.LEXIKON;
		patterns = getPatterns();
	}
	
	private List getPatterns()
	{
		List pattern = null;
		//Map attributes = new HashMap();
		//attributes.put("code", WebsiteConstants.LEXIKON);
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch()
					.search(
								 "SELECT {" + LexikonParagraphData.PK + "} FROM {"
											+ WebsiteConstants.TC.LEXIKONPARAGRAPHDATA + "} "// + "WHERE {"
											/*+ LexikonParagraphData.LEXIKONPARAGRAPH + "} = ?code", attributes*/,
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
		if (patterns != null && wrapper.getContentType().equals("text/html;charset=UTF-8"))
		{
			String responseText = wrapper.toString();
			Iterator i = patterns.iterator();
			if(patterns != null)
			{
			while(i.hasNext())
			{
				LexikonParagraphData data = (LexikonParagraphData) i.next();
				Pattern pattern = Pattern.compile(data.getName().toString());
				Matcher matcher = pattern.matcher(responseText);
				responseText = matcher.replaceAll("<a href=\"index.jsf?pageid=" + this.targetPage +"#lexikon-"+data.toString()+"\">" + data.getName().toString() + "</a>");
			}
			}
			response.setContentLength(responseText.length());
			out.write(responseText);
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