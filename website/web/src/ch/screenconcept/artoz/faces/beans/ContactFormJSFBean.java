package ch.screenconcept.artoz.faces.beans;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import ch.screenconcept.artoz.website.ContactFormParagraph;
import ch.screenconcept.artoz.website.ContactFormParagraphData;
import ch.screenconcept.artoz.website.jalo.WebsiteManager;

/**
 * Managed bean that brigdes to the hmc extension.
 * 
 * @author r-simecek
 */
@Bean(name = "contactFormJSFBean", scope = Scope.REQUEST)
public class ContactFormJSFBean
{
	static private final String SUCCESSED = "submitted";
	private static Logger log = Logger.getLogger( ContactFormJSFBean.class );
	
	private String firstname, lastname, street, city, country;
	private String email, telephone, telefax, message;
	private String receiverEmail;
	private String form;
	private Properties emailProp;
	
	public String doSubmit()
	{
		initialize();
		ContactFormParagraph contactPar = ContactFormParagraph.getContactFormParagraph(form);
		
		final Map<Object, Object> data = new HashMap<Object, Object>();
		
		data.put( ContactFormParagraphData.FIRSTNAME, firstname);
		data.put( ContactFormParagraphData.LASTNAME, lastname);
		data.put( ContactFormParagraphData.STREET, street);
		data.put( ContactFormParagraphData.CITY, city);
		data.put( ContactFormParagraphData.COUNTRY, country);
		data.put( ContactFormParagraphData.EMAIL, email);
		data.put( ContactFormParagraphData.TELEPHONE, telephone);
		data.put( ContactFormParagraphData.TELEFAX, telefax);
		data.put( ContactFormParagraphData.MESSAGE, message);
		data.put( ContactFormParagraphData.CONTACTFORMPARAGRAPH, contactPar);
				
		WebsiteManager.getInstance().createContactFormParagraphData( data );
		
		
		return SUCCESSED;
	}
	
	private void initialize()
	{
		form =(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("form");
		firstname =(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frontpage:contactForm:firstname");
		lastname =(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frontpage:contactForm:lastname");
		street =(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frontpage:contactForm:street");
		city =(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frontpage:contactForm:city");
		country =(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frontpage:contactForm:country");
		email =(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frontpage:contactForm:email");
		telephone =(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frontpage:contactForm:telephone");
		telefax =(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frontpage:contactForm:telefax");
		message =(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frontpage:contactForm:message");
		receiverEmail =(String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("receiverEmail");
	}
	
}
