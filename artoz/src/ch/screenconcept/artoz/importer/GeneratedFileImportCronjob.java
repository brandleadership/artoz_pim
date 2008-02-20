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
	public static final String FILENAME = "fileName".intern();
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
	
	public String getFileName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FILENAME);
	}
	
	public String getFileName()
	{
		return getFileName( getSession().getSessionContext() );
	}
	
	public void setFileName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FILENAME,value);
	}
	
	public void setFileName(final String value)
	{
		setFileName( getSession().getSessionContext(), value );
	}
	
}
