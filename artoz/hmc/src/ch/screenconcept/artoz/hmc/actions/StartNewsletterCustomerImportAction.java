package ch.screenconcept.artoz.hmc.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.importer.MediaNewsletterImportCronjob;
import ch.screenconcept.artoz.importer.NewsletterCustomerImportJob;
import ch.screenconcept.artoz.jalo.ArtozManager;
import ch.screenconcept.artoz.wizard.CreateEMailFaxCampaignWizard;

import com.exedio.campaign.jalo.CampaignManager;

import de.hybris.platform.cronjob.jalo.CronJobManager;
import de.hybris.platform.hmc.util.action.ActionEvent;
import de.hybris.platform.hmc.util.action.ActionResult;
import de.hybris.platform.hmc.util.action.ItemAction;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.user.UserManager;

public class StartNewsletterCustomerImportAction extends ItemAction
{
	private final String NEWSLETTERCUSTOMERIMPORTJOBCODE = "newsletterCustomerImportJob";
	
	@Override
	public ActionResult perform(ActionEvent actionevent) throws JaloBusinessException
	{
		CreateEMailFaxCampaignWizard item = (CreateEMailFaxCampaignWizard) getItem(actionevent);
		if (item.getCampaignNewsletter() == null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
			item.setCampaignNewsletter(CampaignManager.getInstance().createNewsletter(
						ArtozConstants.NEWSLETTER_PREFIX + sdf.format(new Date())));
		}

		ArtozManager artozManager = ArtozManager.getInstance();

		NewsletterCustomerImportJob job = (NewsletterCustomerImportJob) CronJobManager.getInstance().getJob(
					NEWSLETTERCUSTOMERIMPORTJOBCODE);
		if (job == null)
		{
			Map<String, Object> paramsImportJob = new HashMap<String, Object>();
			paramsImportJob.put(NewsletterCustomerImportJob.CODE, NEWSLETTERCUSTOMERIMPORTJOBCODE);
			job = artozManager.createNewsletterCustomerImportJob(paramsImportJob);
		}

		Map<String, Object> paramsCronjob = new HashMap<String, Object>();
		paramsCronjob.put(MediaNewsletterImportCronjob.MEDIA, item.getCampaignImportFile());
		paramsCronjob.put(MediaNewsletterImportCronjob.NEWSLETTER, item.getCampaignNewsletter());
		paramsCronjob.put(MediaNewsletterImportCronjob.JOB, job);
		paramsCronjob.put(MediaNewsletterImportCronjob.SESSIONUSER, UserManager.getInstance().getUserByLogin("admin"));
		paramsCronjob.put(MediaNewsletterImportCronjob.SESSIONLANGUAGE, ArtozConstants.Languages.getEnglish());
		paramsCronjob.put(MediaNewsletterImportCronjob.SESSIONCURRENCY, ArtozConstants.Currencies.getCHF());
		MediaNewsletterImportCronjob cronjob = artozManager.createMediaNewsletterImportCronjob(paramsCronjob);

		job.perform(cronjob);
		
		return null;
	}

}
