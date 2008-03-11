package ch.screenconcept.artoz.campaign;

import java.util.Collection;

import com.exedio.sendmail.MailSource;

public interface MailFaxSource extends MailSource
{
	public Collection<EMailFax> getFaxesToSend(final int maximumResultSize);
}
