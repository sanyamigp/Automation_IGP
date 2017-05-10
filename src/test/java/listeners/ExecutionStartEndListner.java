package listeners;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

import javax.mail.MessagingException;

import org.apache.commons.io.FileUtils;
import org.testng.IExecutionListener;
import org.testng.annotations.AfterSuite;

import utilities.GlobalUtil;
import utilities.LogUtil;
import utilities.ReportFactoryDB;
import utilities.SendMail;
import utilities.SendMail1;
import utilities.SendingMail;
import utilities.Utility;
import utilities.Zip1;


public class ExecutionStartEndListner extends Utility implements IExecutionListener {
	public String file=System.getProperty("user.dir")+"//ExecutionReports//HtmlReport//FailedScreenshots";
	public static String reportFolder=System.getProperty("user.dir") +"//ExecutionReports//HtmlReport";
	public static String destFolder=System.getProperty("user.dir")+"//Reports.zip";
	//This method will be the Starting point for whole test process with TestNg
	@Override
	public void onExecutionStart()
	{
		try {
			FileUtils.cleanDirectory(new File(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*This method will be the run at last when for whole test process with TestNg is finished*/
	@Override
	public void onExecutionFinish() {
		// TODO Auto-generated method stub
		LogUtil.infoLog("TestProcessEnd", "Test process has ended");
		
		
		//1. Send Mail functionality
		
//		if (GlobalUtil.getCommonSettings().getEmailOutput().equalsIgnoreCase("Y"))
			try {
				
				//SendMail1.execute();
				Zip1.zipDir(reportFolder, destFolder);
				
				//SendingMail.execute("TestReport.zip");
				
			} //2. Extenet Report Finish
 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		//3. Report open for view

//		String htmlReportFile = System.getProperty("user.dir") + "\\" + Utility.getValue("HtmlReportFullPath");
//		File f = new File(htmlReportFile);
//		if (f.exists()) {
//
//			try {
//				Process p = Runtime.getRuntime()
//						.exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe \"" + htmlReportFile
//								+ "\"");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		//4. Generate Comparison sheet 
		ReportFactoryDB.getComparisonReport(GlobalUtil.getCommonSettings().getProjectName());
		
		//5. Rename test file
		GlobalUtil.renameFile();
		
	}

	@AfterSuite
	public void sendEmail()
	{
		
	}
	

}
