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

import java.util.HashMap;
import java.util.Map;

import ch.screenconcept.artoz.constants.ArtozConstants;
import ch.screenconcept.artoz.importer.ProductImportMediaImportCronjob;
import ch.screenconcept.artoz.importer.ProductListImportJob;
import ch.screenconcept.artoz.jalo.ArtozManager;
import ch.screenconcept.artoz.product.ProductList;
import de.hybris.platform.cronjob.jalo.CronJobManager;
import de.hybris.platform.hmc.util.action.ActionEvent;
import de.hybris.platform.hmc.util.action.ActionResult;
import de.hybris.platform.hmc.util.action.ItemAction;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.user.UserManager;

public class StartProductListImportAction extends ItemAction
{
	private final String PRODUCTLISTIMPORTJOBCODE = "productListImportJob";

	@Override
	public ActionResult perform(ActionEvent actionevent) throws JaloBusinessException
	{
		ProductList item = (ProductList) getItem(actionevent);

		ArtozManager artozManager = ArtozManager.getInstance();

		ProductListImportJob job = (ProductListImportJob) CronJobManager.getInstance().getJob(PRODUCTLISTIMPORTJOBCODE);
		if (job == null)
		{
			Map<String, Object> paramsImportJob = new HashMap<String, Object>();
			paramsImportJob.put(ProductListImportJob.CODE, PRODUCTLISTIMPORTJOBCODE);
			job = artozManager.createProductListImportJob(paramsImportJob);
		}

		Map<String, Object> paramsCronjob = new HashMap<String, Object>();
		paramsCronjob.put(ProductImportMediaImportCronjob.MEDIA, item.getProductImportFile());
		paramsCronjob.put(ProductImportMediaImportCronjob.PRODUCTLIST, item);
		paramsCronjob.put(ProductImportMediaImportCronjob.JOB, job);
		paramsCronjob.put(ProductImportMediaImportCronjob.SESSIONUSER, UserManager.getInstance()
					.getUserByLogin("admin"));
		paramsCronjob.put(ProductImportMediaImportCronjob.SESSIONLANGUAGE, ArtozConstants.Languages.getEnglish());
		paramsCronjob.put(ProductImportMediaImportCronjob.SESSIONCURRENCY, ArtozConstants.Currencies.getCHF());
		ProductImportMediaImportCronjob cronjob = artozManager.createProductImportMediaImportCronjob(paramsCronjob);

		job.perform(cronjob);

		return null;
	}

}
