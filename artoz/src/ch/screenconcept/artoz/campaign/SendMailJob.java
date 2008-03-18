package ch.screenconcept.artoz.campaign;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.log4j.Logger;

import ch.screenconcept.artoz.campaign.interfax.InterFaxStub;
import ch.screenconcept.artoz.campaign.interfax.InterFaxStub.SendfaxEx_2;
import ch.screenconcept.artoz.campaign.interfax.InterFaxStub.SendfaxEx_2Response;

import com.exedio.sendmail.MailSender;

import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;

public class SendMailJob extends GeneratedSendMailJob
{
	private static Logger log = Logger.getLogger(SendMailJob.class.getName());

	@Override
	protected CronJobResult performCronJob(CronJob c) throws AbortCronJobException
	{
		final SendMailCronJob cronJob = (SendMailCronJob) c;

		MailSender.sendMails(cronJob.getMailSource(), cronJob.getSmtpServer(), false, cronJob.getCountPerRun());
		try
		{
			sendFax(cronJob.getMailSource(), cronJob.getCountPerRun());
		}
		catch (Exception e)
		{
			new AbortCronJobException(e.getMessage());
		}
		return cronJob.getFinishedResult(true);
	}

	public void sendFax(MailFaxSource source, int countPerRun) throws Exception
	{
		SendfaxEx_2 sendfax = new SendfaxEx_2();
		Collection<EMailFax> faxList = source.getFaxesToSend(countPerRun);

		for (EMailFax fax : faxList)
		{
			sendfax.setUsername(fax.getFaxServiceUser());
			sendfax.setPassword(fax.getFaxServicePassword());
			sendfax.setFaxNumbers(fax.getRecipient());
			sendfax.setSubject(fax.getSubject());
			sendfax.setPageHeader("To: {To} From: " + fax.getFaxSender() + " Pages: {TotalPages}");
			sendfax.setPostpone(new GregorianCalendar());
			
			File fileToSend = File.createTempFile("tmp", "txt");
			//File fileToSend = new File(".", "tmp.txt");
			FileWriter fw = new FileWriter(fileToSend);

			final boolean isHTML = !fax.getTextAsHtml().equals("");
			if (isHTML)
				fw.write(fax.getTextAsHtml());
			else
				fw.write(fax.getText());
			fw.close();

			sendfax.setFileSizes("" + fileToSend.length());
			FileDataSource fdh = new FileDataSource(fileToSend);

			DataHandler dh = new DataHandler(fdh);
			sendfax.setFilesData(dh);
			if (isHTML)
				sendfax.setFileTypes("HTML");
			else
				sendfax.setFileTypes("TXT");

			InterFaxStub theBinding = new InterFaxStub(fax.getFaxServiceAdresse());
			SendfaxEx_2Response response = theBinding.SendfaxEx_2(sendfax);
			log.info("sendFax() call returned with code: " + response.getSendfaxEx_2Result());
			if (response.getSendfaxEx_2Result() > 0)
				fax.setLastSentDate(new Date());
			else
				fax.setFailure("Error code: " + response.getSendfaxEx_2Result());
		}
	}
}
