package listeners;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;

import org.testng.IExecutionListener;

import utilities.GlobalUtil;
import utilities.LogUtil;
import utilities.ReportFactoryDB;
import utilities.SendMail;
import utilities.Utility;

public class ExecutionStartEndListner extends Utility implements IExecutionListener {

	//This method will be the Starting point for whole test process with TestNg
	@Override
	public void onExecutionStart() {}
	
	/*This method will be the run at last when for whole test process with TestNg is finished*/
	@Override
	public void onExecutionFinish() {
		// TODO Auto-generated method stub
		LogUtil.infoLog("TestProcessEnd", "Test process has ended");
		
		
		//1. Send Mail functionality
		if (GlobalUtil.getCommonSettings().getEmailOutput().equalsIgnoreCase("Y"))
			try {
				SendMail.sendEmailToClient();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//2. Extenet Report Finish
		
		
		//3. Report open for view

		String htmlReportFile = System.getProperty("user.dir") + "\\" + Utility.getValue("HtmlReportFullPath");
		File f = new File(htmlReportFile);
		if (f.exists()) {

			try {
				Process p = Runtime.getRuntime()
						.exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe \"" + htmlReportFile
								+ "\"");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//4. Generate Comparison sheet 
		ReportFactoryDB.getComparisonReport(GlobalUtil.getCommonSettings().getProjectName());
		
		//5. Rename test file
		GlobalUtil.renameFile();
		
	}

	

}
