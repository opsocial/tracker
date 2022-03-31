package com.projeto.tracker.email;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
/*import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;*/
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.projeto.tracker.TrackerApplication;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class SendMail {

	public static final String MAIL_TRANSPORT_PROTOCOL = "smtp";
	public static final String MAIL_SMTP_HOST = "smtplw.com.br";
	public static final String MAIL_SMTP_USER = "contato@opsocial.com.br";
	public static final String MAIL_USER = "reichembachpr";
	public static final String MAIL_PASSWORD = "brEF6r2k@puC";
	public static final String MAIL_SMTP_PORT = "587";
	
    private String email;
    private String password;
    private String file;
    private Session session;
    private Boolean wasSend = false;
    
   public SendMail(final String fromEmail, final String subject, final String toEmail, String html, String personal) {
    	
	   Properties properties = new Properties();
	   properties.put("mail.transport.protocol", MAIL_TRANSPORT_PROTOCOL);
	   properties.put("mail.smtp.host", MAIL_SMTP_HOST);
	   properties.put("mail.smtp.auth", "true");
	   properties.put("mail.smtp.port", MAIL_SMTP_PORT);

	   session = Session.getDefaultInstance(properties);
	   session.setDebug(false);

	   sendMailHTML(fromEmail, toEmail, personal, subject, html, session);
	}
   
    public SendMail(final String subject, String toEmails, String html, Map<String, String> fileNames) {
    	
       Properties properties = new Properties();
 	   properties.put("mail.transport.protocol", MAIL_TRANSPORT_PROTOCOL);
 	   properties.put("mail.smtp.host", MAIL_SMTP_HOST);
 	   properties.put("mail.smtp.auth", "true");
 	   properties.put("mail.smtp.port", MAIL_SMTP_PORT);

 	   session = Session.getDefaultInstance(properties);
 	   session.setDebug(false);
 	   
 	   sendMailHTML(MAIL_SMTP_USER, toEmails, subject, html, fileNames, session);
    }
    
    public SendMail(final String subject, final String bodyMessage, String footerMessage, final String toEmail, Boolean withTimer) {
    	
    	try {
    		
    		Properties properties = new Properties();
    		properties.put("mail.transport.protocol", MAIL_TRANSPORT_PROTOCOL);
    		properties.put("mail.smtp.host", MAIL_SMTP_HOST);
    		properties.put("mail.smtp.auth", "true");
    		properties.put("mail.smtp.port", MAIL_SMTP_PORT);
    		
			session = Session.getDefaultInstance(properties);
			session.setDebug(false);
			
			File emailSupport = new File(TrackerApplication.EMAILS_DIR + "default-email.html");
			
			final Document doc = Jsoup.parse(emailSupport, "UTF-8");
		
			doc.getElementById("content").html(bodyMessage);
			doc.getElementById("footer").html(footerMessage);
			
			if(withTimer) {
	    	
		    	Timer timer = new Timer();
				
				timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						sendMailHTML(MAIL_SMTP_USER, toEmail, "", subject, doc.html(), session);
						wasSend = true;	
					}
				}, 0);
			} else {
				wasSend = sendMailHTML(MAIL_SMTP_USER, toEmail, "", subject, doc.html(), session);
			}
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public boolean sendMailHTML(String from, String toEmails, String subject, String message, Map<String, String> fileNames, Session session) {

    	try {

    		// Create a default MimeMessage object.
			Message mimeMessage = new MimeMessage(session);

			// Set From: header field of the header.
			mimeMessage.setFrom(new InternetAddress(from));
			
			// Set To: header field of the header.
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmails));
			
			mimeMessage.setSubject(subject);
			
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			
			// Now set the actual message
			messageBodyPart.setContent(message, "text/html; charset=UTF-8");
			
			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			
			for(Map.Entry<String, String> fileName : fileNames.entrySet()) {
				
				// Part two is attachment
				BodyPart fileBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(fileName.getKey());
				fileBodyPart.setDataHandler(new DataHandler(source));
				fileBodyPart.setFileName(fileName.getValue());
				
				multipart.addBodyPart(fileBodyPart);
			}
			
			// Send the complete message parts
			mimeMessage.setContent(multipart);
			
	    	Transport tr = session.getTransport(MAIL_TRANSPORT_PROTOCOL);
	        tr.connect(MAIL_SMTP_HOST, MAIL_USER, MAIL_PASSWORD);
	
	        mimeMessage.saveChanges();
	        tr.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
	        tr.close();
	        
			return true;
		} catch (Exception e) {
            e.printStackTrace();
            return false;
		}
    }
    
    public boolean sendMailHTML(String from, String to, String personal, String subject, String message, Session session) {
    	 
        Message msg = new MimeMessage(session);

        try {
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setFrom(new InternetAddress(from, personal));
            msg.setSubject(subject);
            msg.setContent(message, "text/html; charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Transport tr;
        try {
            tr = session.getTransport(MAIL_TRANSPORT_PROTOCOL);
            tr.connect(MAIL_SMTP_HOST, MAIL_USER, MAIL_PASSWORD);

            msg.saveChanges();
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
            
            return true;
        } catch (Exception e) {
        	//----- System.out.println("Erro ao tentar enviar email para: " + to);
            e.printStackTrace();
            return false;
        }
    }
    
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Boolean wasSend() {
		return wasSend;
	}

	public void setWasSend(Boolean wasSend) {
		this.wasSend = wasSend;
	}
}
