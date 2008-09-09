package ch.screenconcept.artoz.cms;

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
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.hybris.platform.cms.constants.CmsConstants;
import de.hybris.platform.cms.jalo.Website;
import de.hybris.platform.webfoundation.util.CommerceUtils;


/**
 * This component translates the cms specific links within its body to real links.
 * @author bvh
 */
public class CMSTranslateLinksComponent extends BaseComponent
{
	static final Logger log = Logger.getLogger(CMSTranslateLinksComponent.class.getName());

	public static final String PK_MATCHER = "{pk}";

	protected String replaceLinks( String source, Website website, HttpServletResponse response ) 
	{
		StringWriter out = new StringWriter();
		try
		{
			int lastPos = 0;
            final Pattern linkExpr = Pattern.compile(CmsConstants.COMPONENT_LINK_PATTERN, Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
            Matcher m = linkExpr.matcher(source);
            while(m.find())
            {
                out.write(source.substring(lastPos, m.start()));
                String linkPK = m.group(1);
                while(linkPK.endsWith("/"))
                {
                    linkPK = linkPK.substring(0, linkPK.length()-1);
                }
                String linkText = m.group(2);
                
                if( website != null )
                {
                    String targetLinkURL = website.getItemViewURLPreview();
                    if( targetLinkURL.indexOf(PK_MATCHER) > -1 )
                    {
                       targetLinkURL = response.encodeURL(targetLinkURL.substring(0, targetLinkURL.indexOf(PK_MATCHER)).concat(linkPK).concat(targetLinkURL.substring(targetLinkURL.indexOf(PK_MATCHER)+PK_MATCHER.length())));
                    }
                    else
                    {
                  	  log.warn("itemViewURLPreview is not yet implemented!");
                    }
                    out.write("<a href=\"".concat(targetLinkURL).concat("\">").concat(linkText).concat("</a>"));
                }
                else
                {
                    out.write( linkText );
                }
                lastPos = m.end();
            }
			out.write(source.substring(lastPos, source.length()));
			return out.toString();
		}
		catch(PatternSyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}	

	
	ResponseWriter old;
	StringWriter out;
	
	@Override
	public void encodeBegin( FacesContext ctx ) throws IOException
	{
      old = ctx.getResponseWriter();
      out = new StringWriter();
      ResponseWriter j = ctx.getRenderKit().createResponseWriter( out, null, null );
      ctx.setResponseWriter( j );
	}
	
	@Override
	public void encodeEnd( FacesContext ctx ) throws IOException
	{

		ctx.setResponseWriter( old );
		Website website = CommerceUtils.getWebsite();
		
		HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();

		old.write( replaceLinks( out.toString(), website, response ) );
	}

	@Override
	public String getFamily()
	{
		return "de.hybris.cms";
	}

	
}
