package com.inetpsa.eds.tools.mail;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.localisation.BundleManager;
import com.inetpsa.eds.tools.uri.FragmentManager;

/**
 * This class is used to create the mail content and subject based on the message type which is provided. This class is used to create mail structure
 * based on the form ID provided. Language of the mail can also be selected.
 * 
 * @author Geometric Ltd.
 */
public class EdsMailMessageBuilder {
    // PUBLIC
    /**
     * Variable to store EDS_UPDATED value to 0.
     */
    public static final int EDS_UPDATED = 0;
    /**
     * Variable to store EDS_VERSIONNED value to 1.
     */
    public static final int EDS_VERSIONNED = 1;
    /**
     * Variable to store EDS_ABORTED value to 2.
     */
    public static final int EDS_ABORTED = 2;
    /**
     * Variable to store EDS_RETRIEVED value to 3.
     */
    public static final int EDS_RETRIEVED = 3;
    /**
     * Variable to store EDS_VALIDATED value to 4.
     */
    public static final int EDS_VALIDATED = 4; // Corresponds to a minor versioning

    /**
     * Parameterized constructor.
     * 
     * @param messageType Type of mail message.
     * @param language Language of mail message.
     * @param formId Id of the form.
     * @param author Author of mail.
     * @param eds EDS details to be sent.
     * @param baseURL Base URL of the application.
     * @param controller EDS application controller object.
     */
    public EdsMailMessageBuilder(int messageType, String language, String formId, EdsUser author, EdsEds eds, String baseURL,
            EdsApplicationController controller) {
        this.messageType = messageType;
        this.bundle = BundleManager.getInstance().getResourceBundle(new Locale(language));
        this.formId = formId;
        this.author = author;
        this.eds = eds;
        this.baseURL = baseURL;
        this.controller = controller;
        init();
    }

    /**
     * This method returns the content of mail to be sent.
     * 
     * @return Mail content.
     */
    public String getContent() {
        return content;
    }

    /**
     * The method returns the mail subject to be sent.
     * 
     * @return Mail subject.
     */
    public String getSubject() {
        return subject;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Map to store the message type and subject ID details.
     */
    private static final HashMap<Integer, String> MESSAGE_TYPE_TO_SUBJECT_ID = new HashMap<Integer, String>();
    /**
     * Map to store the message type and content ID.
     */
    private static final HashMap<Integer, String> MESSAGE_TYPE_TO_CONTENT_ID = new HashMap<Integer, String>();
    /**
     * Map to store the message type in short and content ID.
     */
    private static final HashMap<Integer, String> MESSAGE_TYPE_SHORT_TO_CONTENT_ID = new HashMap<Integer, String>();
    /**
     * EDS application controller object.
     */
    private EdsApplicationController controller;

    static {
        MESSAGE_TYPE_TO_SUBJECT_ID.put(EDS_UPDATED, "mail-subject-eds-updated");
        MESSAGE_TYPE_TO_SUBJECT_ID.put(EDS_VERSIONNED, "mail-subject-eds-versionned");
        MESSAGE_TYPE_TO_SUBJECT_ID.put(EDS_VALIDATED, "mail-subject-eds-validated");
        MESSAGE_TYPE_TO_SUBJECT_ID.put(EDS_ABORTED, "mail-subject-eds-aborted");
        MESSAGE_TYPE_TO_SUBJECT_ID.put(EDS_RETRIEVED, "mail-subject-eds-retrieved");

        MESSAGE_TYPE_TO_CONTENT_ID.put(EDS_UPDATED, "mail-content-eds-updated");
        MESSAGE_TYPE_TO_CONTENT_ID.put(EDS_VERSIONNED, "mail-content-eds-versionned");
        MESSAGE_TYPE_TO_CONTENT_ID.put(EDS_VALIDATED, "mail-content-eds-versionned");
        MESSAGE_TYPE_TO_CONTENT_ID.put(EDS_ABORTED, "mail-content-eds-aborted");
        MESSAGE_TYPE_TO_CONTENT_ID.put(EDS_RETRIEVED, "mail-content-eds-retrieved");

        MESSAGE_TYPE_SHORT_TO_CONTENT_ID.put(EDS_UPDATED, "mail-content-eds-updated-short");
        MESSAGE_TYPE_SHORT_TO_CONTENT_ID.put(EDS_VERSIONNED, "mail-content-eds-versionned-short");
        MESSAGE_TYPE_SHORT_TO_CONTENT_ID.put(EDS_VALIDATED, "mail-content-eds-validated-short");
        MESSAGE_TYPE_SHORT_TO_CONTENT_ID.put(EDS_ABORTED, "mail-content-eds-aborted-short");
        MESSAGE_TYPE_SHORT_TO_CONTENT_ID.put(EDS_RETRIEVED, "mail-content-eds-retrieved-short");
    }
    /**
     * Variable to store main theme color.
     */
    private static final String COLOR_THEME_MAIN = "#ff6600";
    /**
     * Variable to store grey theme color 1.
     */
    private static final String COLOR_THEME_GREY1 = "#999999";
    /**
     * Variable to store grey theme color 2.
     */
    private static final String COLOR_THEME_GREY2 = "#e6e6e6";
    /**
     * Variable to store grey theme color 3.
     */
    private static final String COLOR_THEME_GREY3 = "#f9f9f9";
    /**
     * Variable to store type of mail message.
     */
    private int messageType;
    /**
     * Resource bundle to retrieve values from configuration file.
     */
    private ResourceBundle bundle;
    /**
     * Variable to store form ID.
     */
    private String formId;
    /**
     * Variable to store the details of the user sending the mail.
     */
    private EdsUser author;
    /**
     * Variable to store EDS details.
     */
    private EdsEds eds;
    /**
     * Variable to store the mail subject.
     */
    private String subject;
    /**
     * Variable to store mail content.
     */
    private String content;
    /**
     * Variable to store base URL of application.
     */
    private String baseURL;

    /**
     * Initialization block. It is used to build subject and content of the mail.
     */
    private void init() {
        buildSubject();
        buildContent();
    }

    /**
     * This method is used to build the subject of the mail.
     */
    private void buildSubject() {
        if (MESSAGE_TYPE_TO_SUBJECT_ID.containsKey(messageType)) {
            subject = MessageFormat.format(bundle.getString(MESSAGE_TYPE_TO_SUBJECT_ID.get(messageType)), eds.getEdsName() + " (" + eds.getEdsRef()
                    + ")");
        } else {
            subject = "Unknown message type";
        }
    }

    /**
     * This method is used to build the content of the mail.
     */
    private void buildContent() {
        if (MESSAGE_TYPE_TO_CONTENT_ID.containsKey(messageType)) {
            Date mailDate = new Date();
            StringBuilder contentBuilder = new StringBuilder();

            contentBuilder.append("<style type='text/css'>");
            contentBuilder.append("body {");
            contentBuilder.append("background-color:#f0f0f0;");
            contentBuilder.append("font-size:12px;");
            contentBuilder.append("}");
            contentBuilder.append("</style>");
            contentBuilder.append("<table cellpadding='0' cellspacing='0' border='0' width='100%'>");
            contentBuilder.append("<tr>");
            contentBuilder.append("<td width='170' bgcolor='#f0f0f0' valign='top'>");
            contentBuilder.append("<table cellpadding='0' cellspacing='0' border='0' width='100%'>");
            contentBuilder.append("<tr>");
            contentBuilder.append("<td>");

            contentBuilder
                    .append(" <table cellpadding='3' cellspacing='0' style='border-top-style: none; border-right-style: none; border-bottom-style: solid; border-left-style: none; border-width: 1px; border-color: #3c78b5;' width='100%'>");
            contentBuilder.append("<tr>");
            contentBuilder.append("<td style='background-color: #ddd;'>");
            contentBuilder.append("<b>");
            contentBuilder.append(bundle.getString("mail-content-eds-resume"));
            contentBuilder.append("</b><br>");
            contentBuilder.append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("</table>");
            contentBuilder.append("<table cellpadding='3' cellspacing='0' border='0' width='100%'>");
            contentBuilder.append("<tr style='vertical-align: top;'>");
            contentBuilder.append("<td style='width: 1%; white-space: nowrap;'><b>");
            contentBuilder.append(bundle.getString("generic-data-organe-ref"));
            contentBuilder.append(":</b></td>");
            contentBuilder.append("<td style='font-weight: bold; width: 99%'>").append(createEdsLink(eds, baseURL)).append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("<tr style='vertical-align: top;'>");
            contentBuilder.append("<td style='font-weight: bold;'>");
            contentBuilder.append(bundle.getString("mail-content-eds-name"));
            contentBuilder.append(":</td>");
            contentBuilder.append("<td>").append(eds.getEdsName()).append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("<tr style='vertical-align: top;'>");
            contentBuilder.append("<td style='font-weight: bold; white-space: nowrap;'>");
            contentBuilder.append(bundle.getString("mail-content-eds-projet"));
            contentBuilder.append(":</td>");
            contentBuilder.append("<td>").append(eds.getEdsProject().getPName()).append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("<tr style='vertical-align: top;'>");
            contentBuilder.append("<td style='font-weight: bold; white-space: nowrap;'>");
            contentBuilder.append(bundle.getString("mail-content-eds-stade"));
            contentBuilder.append(":</td>");
            contentBuilder.append("<td>").append(EdsApplicationController.getStageText(controller.retrieveEdsStage(eds, eds.getEdsProject())))
                    .append("</td>");
            contentBuilder.append("</tr>");

            contentBuilder.append("<tr style='vertical-align: top;'>");
            contentBuilder
                    .append("<td style='font-weight: bold; white-space: nowrap; background-color: #ddd;  border-bottom: solid 1px #3c78b5;' colspan='2'>");
            contentBuilder.append(bundle.getString("mail-content-eds-interlo"));
            contentBuilder.append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("<tr style='vertical-align: top;'>");
            contentBuilder.append("<td style='font-weight: bold; white-space: nowrap;'>");
            contentBuilder.append(bundle.getString("mail-content-eds-resp-psa"));
            contentBuilder.append(":</td>");
            contentBuilder.append("<td>").append(eds.getEdsUserByEdsOfficerId() == null ? "" : eds.getEdsUserByEdsOfficerId().toFullIdentity())
                    .append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("<tr style='vertical-align: top;'>");
            contentBuilder.append("<td style='font-weight: bold; white-space: nowrap;'>");
            contentBuilder.append(bundle.getString("mail-content-eds-admin-fiche"));
            contentBuilder.append(":</td>");
            contentBuilder.append("<td>").append(eds.getEdsUserByEdsAdminId() == null ? "" : eds.getEdsUserByEdsAdminId().toFullIdentity())
                    .append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("<tr style='vertical-align: top;'>");
            contentBuilder.append("<td style='font-weight: bold; white-space: nowrap;'>");
            contentBuilder.append(bundle.getString("mail-content-eds-charge-dev"));
            contentBuilder.append(":</td>");
            contentBuilder.append("<td>").append(eds.getEdsUserByEdsManagerId() == null ? "" : eds.getEdsUserByEdsManagerId().toFullIdentity())
                    .append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("<tr style='vertical-align: top;'>");
            contentBuilder.append("<td style='font-weight: bold; white-space: nowrap;'>");
            contentBuilder.append(bundle.getString("mail-content-eds-fnr"));
            contentBuilder.append(":</td>");
            contentBuilder.append("<td>").append(eds.getEdsSupplier() == null ? "" : eds.getEdsSupplier().getSName()).append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("</table>");
            contentBuilder.append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("</table>");

            contentBuilder.append(" </td>");
            contentBuilder.append(" <td bgcolor='#ffffff' valign='top'>");
            contentBuilder.append(" <table width='100%' cellpadding='10' cellspacing='0' border='0'>");
            contentBuilder.append("  <tr>");
            contentBuilder.append(" <td>");

            contentBuilder.append(" <table cellpadding='1' cellspacing='0' border='0' bgcolor='#bbbbbb' width='100%' align='center'>");
            contentBuilder.append(" <tr>");
            contentBuilder.append(" <td>");
            contentBuilder.append(" <table cellpadding='4' cellspacing='0' style='background-color: #EFEFEF; border: 0; width: 100%;'>");
            contentBuilder.append(" <tr>");
            contentBuilder.append("<td style='background-color: #EFEFEF; width: 100%; vertical-align: top;'>");
            contentBuilder.append("  <b><font size='4' color='#003366'>");
            contentBuilder.append(bundle.getString("mail-content-eds-title-notification") + "  ");
            contentBuilder.append(MessageFormat.format(bundle.getString(MESSAGE_TYPE_SHORT_TO_CONTENT_ID.get(messageType)), eds.getEdsName() + " ("
                    + eds.getEdsRef() + ")"));
            contentBuilder.append(" </font></b>&nbsp; <br>");
            contentBuilder.append(" <font style='font-weight: bold; font-size: 0.90em;'> ");
            contentBuilder.append(bundle.getString("mail-content-eds-title-update"));
            contentBuilder.append("<font color='#336699' style='font-weight: bold; font-size: 0.80em;'>&nbsp;&nbsp;");
            contentBuilder.append(MessageFormat.format(bundle.getString("mail-content-eds-date-format"), mailDate));
            contentBuilder.append("</font> &nbsp; ");
            contentBuilder.append(bundle.getString("mail-content-eds-title-creation"));
            contentBuilder.append("<font color='#336699' style='font-weight: bold; font-size: 0.80em;'>&nbsp;&nbsp;");
            contentBuilder.append(MessageFormat.format(bundle.getString("mail-content-eds-date-format"), eds.getEdsCreaDate()));
            contentBuilder.append("</font> &nbsp; </font></td>");

            contentBuilder.append("</tr>");
            contentBuilder.append("</table>");
            contentBuilder.append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("</table>");
            contentBuilder.append("<br>");
            contentBuilder.append("<table cellpadding='1' cellspacing='0' border='0' bgcolor='#bbbbbb' width='100%' align='center'>");
            contentBuilder.append("<tr>");
            contentBuilder.append("<td>");
            contentBuilder.append("<table cellpadding='4' cellspacing='0' border='0' width='100%' bgcolor='#ffffff'>");
            contentBuilder.append("<tr style='background-color: #EFEFEF;'>");
            contentBuilder.append("<td>");
            contentBuilder.append(bundle.getString("mail-content-eds-modif"));
            contentBuilder.append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("<tr>");
            contentBuilder.append("<td colspan='2' style='background-color: #fff;'><b>");
            contentBuilder.append(bundle.getString("mail-content-eds-modification-author"));
            contentBuilder.append(":</b>&nbsp;&nbsp;");

            contentBuilder.append(author.toFullIdentity());
            contentBuilder.append("<br>");
            contentBuilder.append("<b>Date:</b> <span style='color: #369'>");
            contentBuilder.append(MessageFormat.format(bundle.getString("mail-content-eds-date-format"), mailDate));
            contentBuilder.append("</span><br>");
            contentBuilder.append("<b>");
            contentBuilder.append(bundle.getString("mail-content-eds-modif-type"));
            contentBuilder.append(":</b> <br>");
            contentBuilder.append("<font style='font-size: 1.30em;'> ").append(bundle.getString(EdsFormFactory.getBuilder(formId).getCaption()))
                    .append("</font>");
            contentBuilder.append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("</table>");
            contentBuilder.append(" </td>");
            contentBuilder.append("</tr>");
            contentBuilder.append(" </table>");
            contentBuilder.append(" <br>");

            contentBuilder.append("<table cellpadding='2' cellspacing='0' border='0' width='100%' align='center'>");
            contentBuilder.append("<tr>");
            contentBuilder
                    .append("<td style='color: #fff; background-color: #bbb; width: 1%; white-space: nowrap; text-align: center; font-weight: bold;'> &nbsp;");
            contentBuilder.append(bundle.getString("mail-content-eds-modification-description"));
            contentBuilder.append("&nbsp;</td>");
            contentBuilder.append(" <td>&nbsp;</td>");
            contentBuilder.append(" </tr>");
            contentBuilder.append(" </table>");

            contentBuilder.append(" <table cellpadding='0' cellspacing='1' border='0' width='100%' bgcolor='#bbbbbb' align='center'>");
            contentBuilder.append(" <tr>");
            contentBuilder.append(" <td>");
            contentBuilder.append("<table cellpadding='2' cellspacing='0' border='0' width='100%'>");
            contentBuilder.append(" <tr>");
            contentBuilder.append(" <td bgcolor='#ffffff' valign='top'>");
            contentBuilder
                    .append("<p>")
                    .append(MessageFormat.format(bundle.getString(MESSAGE_TYPE_TO_CONTENT_ID.get(messageType)),
                            bundle.getString(EdsFormFactory.getBuilder(formId).getCaption()), // TODO
                                                                                              // à
                                                                                              // modifier
                                                                                              // pour
                                                                                              // récupérer
                                                                                              // la
                                                                                              // valeur
                                                                                              // dans
                                                                                              // le
                                                                                              // bundle
                            author.toFullIdentity(), mailDate, eds.getEdsRef(), eds.getVersionLong())).append("</p>");
            contentBuilder.append(" </td>");
            contentBuilder.append("</tr>");
            contentBuilder.append(" </table>");
            contentBuilder.append("</td>");
            contentBuilder.append(" </tr>");
            contentBuilder.append("</table>");

            contentBuilder.append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("</table>");
            contentBuilder.append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("</table>");

            contentBuilder.append("<table width='100%' cellpadding='4'>");
            contentBuilder.append("<tr>");
            contentBuilder.append("<td align='center' style='border-top: 1px solid black;'>");
            contentBuilder.append("Ce message a été généré automatiquement par <a href='");
            contentBuilder.append(controller.getServerBindAdress());
            contentBuilder
                    .append("'>EDS Tools</a>, Outil de gestion de fiches de données électrique. Veuillez ne pas répondre directement à ce mail.<br/>");
            contentBuilder
                    .append("        Si vous pensez qu'il n'a pas été envoyé juste titre, contactez l'un des <a href='http://toolsup-ee.inetpsa.com/'>administrateurs</a> de ce serveur.<br> ");
            contentBuilder.append("Ce mail a été génèré automatiquement par le site, veuillez ne pas repondre svp.");
            contentBuilder.append("</td>");
            contentBuilder.append("</tr>");
            contentBuilder.append("</table>");
            content = contentBuilder.toString();
        } else {
            content = "Unknown message type";
        }
    }

    /**
     * This method is used to create HTML link for the EDS provided.
     * 
     * @param eds EDS for which link to be created.
     * @param baseURL Base URL of the application.
     * @return Link of the EDS in HTML format.
     */
    private String createEdsLink(EdsEds eds, String baseURL) {
        StringBuilder builder = new StringBuilder("<a href=\"");
        builder.append(controller.getServerBindAdress());
        builder.append('#');
        builder.append(FragmentManager.formatURLFragmentForEDS(eds));
        builder.append("\">");
        builder.append(eds.getEdsRef());
        builder.append("</a>");
        return builder.toString();
    }
}
