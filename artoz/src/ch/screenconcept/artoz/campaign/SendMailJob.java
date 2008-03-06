package ch.screenconcept.artoz.campaign;

import org.apache.log4j.Logger;

import com.exedio.sendmail.MailSender;

import de.hybris.platform.cronjob.jalo.AbortCronJobException;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.cronjob.jalo.CronJob.CronJobResult;

public class SendMailJob extends GeneratedSendMailJob
{
	private static Logger log = Logger.getLogger( SendMailJob.class.getName() );

	@Override
	protected CronJobResult performCronJob(CronJob c) throws AbortCronJobException
	{
		final SendMailCronJob cronJob = (SendMailCronJob)c;
        MailSender.sendMails( cronJob.getMailSource(), cronJob.getSmtpServer(), false, cronJob.getCountPerRun() );
        return cronJob.getFinishedResult(true);
	}
	
	
//	public void sendFax() throws Exception
//	{
//		Sendfax sendfax = new Sendfax();
//		sendfax.setUsername(getFaxUserName());
//		sendfax.setPassword(getFaxPassword());
//		sendfax.setFaxNumber(getFaxFaxNumber());
//
//		File toSend = new File("/home/pascal/workspace/artoz/import/", "hello.doc");
//		FileDataSource fdh = new FileDataSource(toSend);
//		if (!toSend.exists())
//			throw new Exception("No file available");
//
//		DataHandler dh = new DataHandler(fdh);
//		sendfax.setFileData(dh);
//		sendfax.setFileType("DOC");
//
//		InterFaxStub theBinding = new InterFaxStub(getFaxServiceAdresse());
//		SendfaxResponse response = theBinding.Sendfax(sendfax);
//		System.out.println("sendFax() call returned with code: " + response.getSendfaxResult());
//	}
}
