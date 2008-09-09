package ch.screenconcept.artoz.hmc.actions;

/*
The extension "Artoz" is free software: you can redistribute it and/or modify
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
import com.exedio.campaign.jalo.Newsletter;

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
			String newsletterName = ArtozConstants.NEWSLETTER_PREFIX + sdf.format(new Date());
			Newsletter newsletter = CampaignManager.getInstance().createNewsletter(newsletterName);
			newsletter.setName(newsletterName);
			item.setCampaignNewsletter(newsletter);
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
