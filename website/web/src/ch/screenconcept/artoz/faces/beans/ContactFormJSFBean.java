package ch.screenconcept.artoz.faces.beans;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.velocity.VelocityContext;

import ch.screenconcept.artoz.jalo.ArtozManager;
import ch.screenconcept.artoz.website.constants.WebsiteConstants;
import ch.screenconcept.artoz.website.jalo.WebsiteManager;
import de.hybris.platform.webfoundation.util.CommerceUtils;
import ch.screenconcept.artoz.website.ContactFormParagraph;
import ch.screenconcept.artoz.website.ContactFormParagraphData;
import de.hybris.platform.cms.jalo.CmsManager;
import de.hybris.platform.cms.jalo.FormParagraph;
import de.hybris.platform.cms.jalo.Website;
import de.hybris.platform.jalo.JaloSession;

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
