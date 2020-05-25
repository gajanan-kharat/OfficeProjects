package com.inetpsa.poc00.rest.receptionfile;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SendEmail.
 */
public class SendEmail {

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(SendEmail.class);

	/**
	 * Instantiates a new send email.
	 */
	private SendEmail() {

	}

	/**
	 * Send email.
	 * 
	 * @param to the to
	 * @param pdfPath the pdf path
	 * @param subject the subject
	 * @param host the host
	 * @param port the port
	 * @param from the from
	 * @param userName the user name
	 * @param password the password
	 */
	public static void sendEmail(List<String> to, String pdfPath, String subject, String host, String port, String from,final String userName,final String password) {

		try {

			String fileName = "Reception_File.pdf";

			// Get the session object
			Properties props = new Properties();

			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});

			/*	COMPOSE THE MESSAGE	*/
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			String recepients = StringUtils.join(to, ",");
			logger.info("List of recepients : {} ", recepients);
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recepients));
			
			message.setSubject(subject);

			/*	CREATE MIMEBODYPART OBJECT AND SET YOUR MESSAGE TEXT */
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("This is message body");

			/*	CREATE NEW MIME BODY PART OBJECT AND SET DATA HANDLER OBJECT TO THIS OBJECT	*/
			MimeBodyPart attachPdf = new MimeBodyPart();
			//String filename = pdfPath.substring(pdfPath.lastIndexOf('/'));
			DataSource source = new FileDataSource(pdfPath);
			attachPdf.setDataHandler(new DataHandler(source));
			attachPdf.setFileName(fileName);

			/*	CREATE MULTIPART OBJECT AND ADD MIMEBODYPART OBJECTS TO THIS OBJECT	*/
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachPdf);

			/*	SET THE MULTIPLART OBJECT TO THE MESSAGE OBJECT	*/
			message.setContent(multipart);

			if (session != null) {

				Transport.send(message);

				logger.info("Sent message successfully....");

			} else {
				
				logger.info("Message sendig Failed....");
			}
			
		} catch (MessagingException messagingException) {
			
			logger.error("Issue Occured while Sending Email --: ", messagingException);
		}
	}
}