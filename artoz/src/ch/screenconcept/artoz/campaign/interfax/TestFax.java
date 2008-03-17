package ch.screenconcept.artoz.campaign.interfax;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import ch.screenconcept.artoz.campaign.interfax.InterFaxStub.Sendfax;
import ch.screenconcept.artoz.campaign.interfax.InterFaxStub.SendfaxResponse;

public class TestFax {

	private final String USERNAME = "screenconcept";
	private final String PASSWORD = "bambus";
	private final String FAX_NUMBER = "+41417484489";
	private final String SERVICEADR = "http://ws.interfax.net/";//http://ws.interfax.net/DFS.asmx";
	
    public void run() throws Exception {
    	
    	
    	InterFaxStub theBinding =  new InterFaxStub(SERVICEADR);

        // Send a simple text fax using the InterFax sendCharFax() web service method.
//        System.out.println("Sending Fax");
//
//	    SendCharFax sendCharFax = new SendCharFax();
//        sendCharFax.setUsername(USERNAME);
//        sendCharFax.setPassword(PASSWORD);
//        sendCharFax.setFaxNumber(FAX_NUMBER);
//        sendCharFax.setFileType("TXT");
//        sendCharFax.setData("Sending Fax using sendCharFax(). that's a test from screenconcept. hopefuly its' working.");
//        SendCharFaxResponse theResponse = theBinding.SendCharFax(sendCharFax);
//
//          long theReturnCode = theResponse.getSendCharFaxResult();
//          System.out.println("sendCharFax() call returned with code: " + theReturnCode);
        
//        SendfaxEx_2 sendfax2 = new SendfaxEx_2();
//        sendfax2.setUsername(USERNAME);
//        sendfax2.setPassword(PASSWORD);
//        sendfax2.setFaxNumbers(FAX_NUMBER);
//        sendfax2.setSubject("ScreenConcept Test");
//        sendfax2.setPageHeader("ScreenConcept Newsletter");
//        sendfax2.setReplyAddress(FAX_NUMBER);
        
//        File toSend = new File("/home/pascal/workspace/artoz/import/", "newsletter.csv");
//        DataHandler dh = new DataHandler(toSend, "text/plain");
        
//        sendfax2.setFilesData(dh);
//        sendfax2.setFileTypes("TXT");
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(new Date());
//        sendfax2.setPostpone(calendar);
//        SendfaxEx_2Response response = theBinding.SendfaxEx_2(sendfax2);
//        System.out.println("sendCharFax() call returned with code: " + response.getSendfaxEx_2Result());
        
        
        
        Sendfax sendfax = new Sendfax();
        sendfax.setUsername(USERNAME);
        sendfax.setPassword(PASSWORD);
        sendfax.setFaxNumber(FAX_NUMBER);        
        File toSend = new File("/home/pascal/workspace/artoz/import/", "hello.doc");
        FileDataSource fdh = new FileDataSource(toSend);
        if (!toSend.exists())
        	throw new Exception("No file available");
        
        DataHandler dh = new DataHandler(fdh);
//        sendfax.setFileData((DataHandler)DataHandlerUtils.getDataHandlerFromText("asdfasdfasdf", "text/plain" ));
        sendfax.setFileData(dh);
        sendfax.setFileType("DOC");
        SendfaxResponse response = theBinding.Sendfax(sendfax); 
        System.out.println("sendFax() call returned with code: " + response.getSendfaxResult());
        
    }
    
    public static void main(String[] args) {
        try {
            new TestFax().run();
        } catch(Exception theE) {
           System.out.println("Error encountered while running SendCharFaxTest:");
           theE.printStackTrace();
        }
     }
}