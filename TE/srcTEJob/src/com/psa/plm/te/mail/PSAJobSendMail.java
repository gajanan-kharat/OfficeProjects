/*
 * Creation : 28 juin 2016
 */

package com.psa.plm.te.mail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
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

import com.psa.plm.te.tools.PSATEMufoConfig;

class MyAuth extends Authenticator {
    private PasswordAuthentication authentication;

    public MyAuth(String userName, String password) {
        this.authentication = new PasswordAuthentication(userName, password);
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return this.authentication;
    }
}

public class PSAJobSendMail {
    Session session;
    Properties properties;
    String strFromId = "";
    String strToId = "";
    String strSubjectLine = "";
    String strStatusMailblock = "";
    String strSummaryCSVPath = "";
    String strerrorCSVPath = "";
    String strPassword = "";
    String strMailUser = "";
    String strSMTPHost = "";

    public PSAJobSendMail() {

        properties = new Properties(System.getProperties());
        // Setup mail server
        String envMufoState = System.getenv("ETAT");
        if (envMufoState.equals("prod")) {
            strPassword = PSATEMufoConfig.mail_prod_password;
            strFromId = PSATEMufoConfig.mail_prod_from;
            strMailUser = PSATEMufoConfig.mail_prod_user;
            strSMTPHost = PSATEMufoConfig.mail_prod_smtp_host;
        } else {
            strPassword = PSATEMufoConfig.mail_test_password;
            strFromId = PSATEMufoConfig.mail_test_from;
            strMailUser = PSATEMufoConfig.mail_test_user;
            strSMTPHost = PSATEMufoConfig.mail_test_smtp_host;
        }
        System.out.println("strFromId: " + strFromId);
        System.out.println("strMailUser: " + strMailUser);

        // Get the default Session object.
        MyAuth authenticator = new MyAuth(strMailUser, strPassword);
        session = Session.getInstance(properties, authenticator);
    }

    public void seToId(String emailID) {
        strToId = emailID;
    }

    public void setSubjectLine(String strPrjID, String strReportPath, String execStatus, String retCodeStatus) {
        String strSubjectLinePattern = "[Synthèse TE (Env:<TEEnv>)]  (<EXECSTATUS>/<RETCODESTATUS>) <PROJECT_ID>";
        String strEnv = System.getenv("ETAT") != null ? System.getenv("ETAT") : "undefined";
        strSubjectLine = strSubjectLinePattern.replace("<TEEnv>", strEnv);
        strSubjectLine = strSubjectLine.replace("<EXECSTATUS>", execStatus);
        strSubjectLine = strSubjectLine.replace("<RETCODESTATUS>", retCodeStatus);
        strSubjectLine = strSubjectLine.replace("<PROJECT_ID>", strPrjID);
        initStaticBlock(strPrjID, strReportPath, execStatus, retCodeStatus);
    }

    private void initStaticBlock(String strPrjID, String strReportPath, String execStatus, String retCodeStatus) {
        strStatusMailblock = PSATEMufoConfig.strTableCSS;

        strStatusMailblock += PSATEMufoConfig.strMailMessage.replace("#PROJECT_NAME#", strPrjID);
        strStatusMailblock = strStatusMailblock.replace("#REPRORTLINK#", strReportPath);
        strStatusMailblock = strStatusMailblock.replace("#EXECSTATUS#", execStatus);
        strStatusMailblock = strStatusMailblock.replace(" #RETSTATUS#", retCodeStatus);
    }

    public void sendMail(String strContent, String strContentType) {
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(strFromId));

            System.out.println("Setting the FROM ID: " + strFromId);

            // Set To: header field of the header.
            if (strToId.contains(";")) {
                String[] arrArrIds = strToId.split(";");
                for (String mId : arrArrIds) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(mId));
                }
            } else {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(strToId));
            }

            // Set Subject: header field
            message.setSubject(strSubjectLine);

            // Send the actual HTML message, as big as you like
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(strStatusMailblock + strContent, strContentType);

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            if (strSummaryCSVPath.length() > 0) {
                MimeBodyPart summaryCSVAttachement = attachFile(strSummaryCSVPath);
                if (summaryCSVAttachement != null)
                    multipart.addBodyPart(summaryCSVAttachement);
            }

            if (strerrorCSVPath.length() > 0) {
                MimeBodyPart summaryCSVAttachement = attachFile(strerrorCSVPath);
                if (summaryCSVAttachement != null)
                    multipart.addBodyPart(summaryCSVAttachement);
            }

            // Set all the details...
            message.setContent(multipart);

            // Send message
            Transport tr = session.getTransport("smtp");
            tr.connect(strSMTPHost, strMailUser, strPassword);
            message.saveChanges();
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();
            // Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private MimeBodyPart attachFile(String strfilePath) {
        MimeBodyPart bodyAttachement = new MimeBodyPart();
        DataSource source = new FileDataSource(strfilePath);
        try {
            bodyAttachement.setDataHandler(new DataHandler(source));
            File file = new File(strfilePath);
            bodyAttachement.setFileName(file.getName());
        } catch (MessagingException e) {
            e.printStackTrace();
            bodyAttachement = null;
        }
        return bodyAttachement;
    }

    public void setSummaryCSV(String csvPath) {
        strSummaryCSVPath = csvPath;
    }

    public void setErrorCSV(String csvPath) {
        strerrorCSVPath = csvPath;
    }
}
