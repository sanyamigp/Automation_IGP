package utilities;

 


import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail1 extends Utility
{
   public static boolean sendingMail(String path,String subject) {
      // Recipient's email ID needs to be mentioned.
      String to = "sanyam.arora@indiangiftsportal.com,priyesh.neema@igp.com,rishab@indiangiftsportal.com";

      // Sender's email ID needs to be mentioned
      String from = "sanyam.arora@testingxperts.com";
      final String username = "sanyam.arora@testingxperts.com";//change accordingly
      final String password = "Sanyam123";//change accordingly

      // Assuming you are sending email through relay.jangosmtp.net
      String host = "smtp.gmail.com";

      Properties props = new Properties();
      props.setProperty("mail.smtp.host", "smtp.gmail.com");
      props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      props.setProperty("mail.smtp.socketFactory.fallback", "false");
      props.setProperty("mail.smtp.port", "465");
      props.setProperty("mail.smtp.socketFactory.port", "465");
      props.put("mail.smtp.auth", "true");
      props.put("mail.debug", "true");
      props.put("mail.store.protocol", "pop3");
      props.put("mail.transport.protocol", "smtp");
      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
            }
         });

      try {

         // Create a default MimeMessage object.
         Message message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(to));

         // Set Subject: header field
         message.setSubject(subject);

         // This mail has 2 part, the BODY and the embedded image
         MimeMultipart multipart = new MimeMultipart("related");

         // first part (the html)
         BodyPart messageBodyPart = new MimeBodyPart();
         String htmlText = "<H1>Test Failed</H1><img src=\"cid:image\">";
         messageBodyPart.setContent(htmlText, "text/html");
         // add it
         multipart.addBodyPart(messageBodyPart);

         // second part (the image)
         messageBodyPart = new MimeBodyPart();
         DataSource fds = new FileDataSource(
            path);

         messageBodyPart.setDataHandler(new DataHandler(fds));
         messageBodyPart.setHeader("Content-ID", "<image>");

         // add image to the multipart
         multipart.addBodyPart(messageBodyPart);

         // put everything together
         message.setContent(multipart);
         // Send message
         Transport.send(message);

         System.out.println("Sent message successfully....");
         return true;

      } catch (Exception mex){
			mex.printStackTrace();
			return false;
      }
   }
}