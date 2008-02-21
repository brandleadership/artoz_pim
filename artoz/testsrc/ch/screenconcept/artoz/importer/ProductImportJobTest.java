package ch.screenconcept.artoz.importer;

import java.util.HashMap;
import java.util.Map;

import ch.screenconcept.artoz.jalo.ArtozManager;
import de.hybris.platform.testframework.JaloTest;

public class ProductImportJobTest extends JaloTest
{

	public ProductImportJobTest(String name)
	{
		super(name);
	}
	
	public void testPerformCronJob(){
		Map<String, Object> paramsImportJob = new HashMap<String, Object>();
		paramsImportJob.put(ProductImportJob.CODE, "testProductImportJob");
		ProductImportJob importJob = ArtozManager.getInstance().createProductImportJob(paramsImportJob);
		registerForRemoval(importJob);

		Map<String, Object> paramsCronjob = new HashMap<String, Object>();
		paramsCronjob.put(FileImportCronjob.FILEDIRECTORY, "x");
		paramsCronjob.put(FileImportCronjob.FILENAME, "x");
		paramsCronjob.put(FileImportCronjob.JOB, importJob);
		FileImportCronjob cronjob = ArtozManager.getInstance().createFileImportCronjob(paramsCronjob);
		registerForRemoval(cronjob);
		
//		importJob.perform(cronjob);
	}
}
