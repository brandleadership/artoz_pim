/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * ----------------------------------------------------------------
 */
package ch.screenconcept.artoz.importer;

import ch.screenconcept.artoz.constants.ArtozConstants;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.jalo.SessionContext;
import java.lang.String;

public abstract class GeneratedFileImportCronjob extends CronJob
{
	public static final String FILEDIRECTORY = "fileDirectory".intern();
	public String getFileDirectory(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FILEDIRECTORY);
	}
	
	public String getFileDirectory()
	{
		return getFileDirectory( getSession().getSessionContext() );
	}
	
	public void setFileDirectory(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FILEDIRECTORY,value);
	}
	
	public void setFileDirectory(final String value)
	{
		setFileDirectory( getSession().getSessionContext(), value );
	}
	
}
