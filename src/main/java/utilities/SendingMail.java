package utilities;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.File;
import java.util.*;

public class SendingMail

{
	public static String reportFolder=System.getProperty("user.dir") +"//ExecutionReports//HtmlReport";
	public static String destFolder=System.getProperty("user.dir")+"//Reports.zip";
	//reportFileName = TestExecutionResultFileName
	public static void execute(String reportFileName) throws Exception

	{
		
		
		Zip1.zipFolder(reportFolder, destFolder);
		//File folder =  new File(reportFolder);
		//FileFilterDateIntervalUtils filter =new FileFilterDateIntervalUtils("2017-01-04", "2050-01-20");
		
		
        //File files[] = folder.listFiles(filter);
       //date
        
        //String fileName=files[files.length-1].getName();
        //String extentFilePath=reportFolder+fileName;
       
        


		String[] to={"sanyam.arora@indiangiftsportal.com"};
		String[] cc={};
		String[] bcc={};

		SendingMail.sendMail("sanyam.arora@testingxperts.com",
				"Sanyam123",
				"smtp.gmail.com",
				"465",
				"true",
				"true",
				true,
				"javax.net.ssl.SSLSocketFactory",
				"false",
				to,
				cc,
				bcc,
				"Automation Report",
				"Hi, A new test suite has been executed. ",
				destFolder,
				reportFileName);
	}

	public static boolean sendMail(String userName,
			String passWord,
			String host,
			String port,
			String starttls,
			String auth,
			boolean debug,
			String socketFactoryClass,
			String fallback,
			String[] to,
			String[] cc,
			String[] bcc,
			String subject,
			String text,
			String attachmentPath,
			String attachmentName){

		//Object Instantiation of a properties file.
		Properties props = new Properties();

		props.put("mail.smtp.user", userName);

		props.put("mail.smtp.host", host);

		if(!"".equals(port)){
			props.put("mail.smtp.port", port);
		}

		if(!"".equals(starttls)){
			props.put("mail.smtp.starttls.enable",starttls);
			props.put("mail.smtp.auth", auth);
		}

		if(debug){

			props.put("mail.smtp.debug", "true");

		}else{

			props.put("mail.smtp.debug", "false");

		}

		if(!"".equals(port)){
			props.put("mail.smtp.socketFactory.port", port);
		}
		if(!"".equals(socketFactoryClass)){
			props.put("mail.smtp.socketFactory.class",socketFactoryClass);
		}
		if(!"".equals(fallback)){
			props.put("mail.smtp.socketFactory.fallback", fallback);
		}

		try{
			
			Session session = Session.getDefaultInstance(props, null);

			session.setDebug(debug);

			MimeMessage msg = new MimeMessage(session);

			msg.setText(text);

			msg.setSubject(subject);

			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			String html = "Test\n" + text + "\n<a href='http://test.com'>Test.com</a>";
            messageBodyPart.setText(html, "UTF-8", "html");
			DataSource source = new FileDataSource(attachmentPath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(attachmentName);
			multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);
			msg.setFrom(new InternetAddress(userName));

			for(int i=0;i<to.length;i++){
				msg.addRecipient(Message.RecipientType.TO, new
						InternetAddress(to[i]));
			}

			for(int i=0;i<cc.length;i++){
				msg.addRecipient(Message.RecipientType.CC, new
						InternetAddress(cc[i]));
			}

			for(int i=0;i<bcc.length;i++){
				msg.addRecipient(Message.RecipientType.BCC, new
						InternetAddress(bcc[i]));
			}

			msg.saveChanges();

			Transport transport = session.getTransport("smtp");

			transport.connect(host, userName, passWord);

			transport.sendMessage(msg, msg.getAllRecipients());

			transport.close();

			return true;

		} catch (Exception mex){
			mex.printStackTrace();
			return false;
		}
	}
}
