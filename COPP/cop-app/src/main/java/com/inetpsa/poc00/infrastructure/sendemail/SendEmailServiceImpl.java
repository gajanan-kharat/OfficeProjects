
package com.inetpsa.poc00.infrastructure.sendemail;

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

import com.inetpsa.poc00.application.email.Email;
import com.inetpsa.poc00.application.sendemail.SendEmailService;
import com.inetpsa.poc00.commonapp.ConstantsApp;

/**
 * The Class SendEmailServiceImpl.
 */
public class SendEmailServiceImpl implements SendEmailService {

	/** The logger. */
	@Logging
	Logger logger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.sendemail.SendEmailService#sendEmail(com.inetpsa.poc00.application.email.Email)
	 */
	@Override
	public void sendEmail(Email email) {
		List<String> to = email.getTo();
		String pdfPath = email.getPdfPath();
		String subject = email.getSubject();
		String host = email.getHost();
		String port = email.getPort();
		String from = email.getFrom();
		String fileName = email.getFileName();
		final String userName = email.getUserName();
		final String password = email.getPassword();

		try {

			// Get the session object
			Properties props = new Properties();

			props.put(ConstantsApp.SMTP_HOST, host);
			props.put(ConstantsApp.SMTP_PORT, port);

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
