package ch.screenconcept.artoz.importer;

import java.io.File;

import org.apache.log4j.Logger;

public class FileImportCronjob extends GeneratedFileImportCronjob
{
	private static Logger log = Logger.getLogger( FileImportCronjob.class.getName() );

	public File getFile(){
		return new File(getFileDirectory(), getFileName());
	}
}
