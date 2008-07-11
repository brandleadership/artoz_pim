package ch.screenconcept.artoz.faces.beans;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

import ch.screenconcept.artoz.constants.ArtozConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;

@Bean(name = "languageBean", scope = Scope.SESSION)
public class LanguageJSFBean
{
	private Language language;

	private List<Language> selectableLanguages = null;

	public LanguageJSFBean()
	{
		language = JaloSession.getCurrentSession().getSessionContext().getLanguage();
		// sollte aus dem Browser-Tag ausgelesen werden via HTTP-Request
		if ("de".equals(getIsoCode().toLowerCase()))
			setLanguage(ArtozConstants.Languages.getGerman());
		if ("fr".equals(getIsoCode().toLowerCase()))
			setLanguage(ArtozConstants.Languages.getFrench());
		if ("it".equals(getIsoCode().toLowerCase()))
			setLanguage(ArtozConstants.Languages.getItalian());
	}

	public List getSelectableLanguages()
	{
		if (selectableLanguages == null)
		{
			selectableLanguages = new LinkedList();
			selectableLanguages.addAll((Collection<Language>) C2LManager.getInstance().getActiveLanguages());
			// remove current language
			// selectableLanguages.remove();
			// Collections.sort(selectableLanguages);
		}
		return selectableLanguages;
	}

	public String getName()
	{
		return language.getName();
	}

	public String getIsoCode()
	{
		return language.getIsoCode();
	}

	public Locale getLocale()
	{
		return language.getLocale();
	}

	public void setLanguage(Language lang)
	{
		JaloSession.getCurrentSession().getSessionContext().setLanguage(lang);
		JaloSession.getCurrentSession().setAttribute("language", lang);
		language = lang;
	}

	public String doChoose()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
		String isoCode = request.getParameter("language");
		Language lang = C2LManager.getInstance().getLanguageByIsoCode(isoCode);
		this.setLanguage(lang);
		return null;
	}

	public Language getLanguage()
	{
		if (language == null)
			setLanguage(ArtozConstants.Languages.getGerman());
		return (Language) language;
	}
}
