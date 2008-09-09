package ch.screenconcept.artoz.campaign.interfax;

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

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import ch.screenconcept.artoz.campaign.interfax.InterFaxStub.Sendfax;
import ch.screenconcept.artoz.campaign.interfax.InterFaxStub.SendfaxResponse;

public class TestFax {

	private final String USERNAME = "";
	private final String PASSWORD = "";
	private final String FAX_NUMBER = "";
	private final String SERVICEADR = "http://ws.interfax.net/";
	
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