package com.inetpsa.eds.tools.mail;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsProjectEds;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.localisation.BundleManager;
import com.inetpsa.eds.tools.uri.FragmentManager;

/**
 * This class is used to create the mail content and subject based on the message type which is provided. This mail is generally sent when an EDS is
 * created and assigned to a specific user. Language of the mail can also be selected.
 * 
 * @author Geometric Ltd.
 */
public class EdsMailAdviceBuilder {
    // PUBLIC
    /**
     * Variable to store EDS_CD_NOTIFICATION value to 5.
     */
    public static final int EDS_CD_NOTIFICATION = 5;
    /**
     * Variable to store EDS_AFF_NOTIFICATION value to 6.
     */
    public static final int EDS_AFF_NOTIFICATION = 6;

    /**
     * Parameterized constructor.
     * 
     * @param messageType Type of mail message.
     * @param language Language of mail message.
     * @param author Author of mail.
     * @param comment Additional information to be sent through mail.
     * @param eds EDS details to be sent.
     * @param create If true then new EDS as been created.
     */
    public EdsMailAdviceBuilder(int messageType, String language, EdsUser author, String comment, EdsEds eds, Boolean create,
            EdsApplicationController controller) {
        this.controller = controller;
        this.messageType = messageType;
        this.bundle = BundleManager.getInstance().getResourceBundle(new Locale(language != null ? language : "fr"));
        this.author = author;
        this.comment = comment;
        this.eds = eds;
        this.language = language;
        this.create = create;
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
     * Map to store the message and content ID.
     */
    private static final HashMap<Integer, String> MESSAGE_CONTENT_ID = new HashMap<Integer, String>();

    static {

        MESSAGE_TYPE_TO_SUBJECT_ID.put(EDS_CD_NOTIFICATION, "mail-subject-eds-cd-notification");
        MESSAGE_TYPE_TO_SUBJECT_ID.put(EDS_AFF_NOTIFICATION, "mail-subject-eds-aff-notification");

        MESSAGE_CONTENT_ID.put(EDS_CD_NOTIFICATION, "mail-cd-notification-messsage");
        MESSAGE_CONTENT_ID.put(EDS_AFF_NOTIFICATION, "mail-cd-affectation-messsage");

    }
    /**
     * Variable to store type of mail message.
     */
    private int messageType;
    /**
     * Resource bundle to retrieve values from configuration file.
     */
    private ResourceBundle bundle;
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
     * Variable to store mail language.
     */
    private String language;
    /**
     * Variable to store additional information to be sent through mail.
     */
    private String comment;
    /**
     * If true then new EDS as been created.
     */
    private Boolean create;

    private EdsApplicationController controller;

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

        Date mailDate = new Date();
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("<style type='text/css'>");
        contentBuilder.append("body {");
        contentBuilder.append("background-color:#f0f0f0;");
        contentBuilder.append("font-size:12px;");
        contentBuilder.append("}");
        contentBuilder.append(".footer {");
        contentBuilder.append("text-align: center;");
        contentBuilder.append("margin-top: 60px;");
        contentBuilder.append("border-top: #323336 dashed 2px;");
        contentBuilder.append("border-bottom: #323336 dashed 2px;");
        contentBuilder.append("padding: 4px;");
        contentBuilder.append("}");
        contentBuilder.append(".comment {");
        contentBuilder.append("border: #323336 solid 1px;");
        contentBuilder.append("padding: 6px;");
        contentBuilder.append("}");
        contentBuilder.append("</style>");

        String imageName = "bandeau-" + language + ".png";
        contentBuilder.append("<div style='text-align:center; font-size: 15px; top: 100px;'>");
        contentBuilder.append("<img src=").append(imageName).append(" />");
        contentBuilder.append("</div>");
        contentBuilder.append("</br></br></br>");
        contentBuilder.append(MessageFormat.format(bundle.getString(MESSAGE_CONTENT_ID.get(messageType)), "<b>" + eds.getEdsName() + "</b>",
                createEdsLink(eds), "<b>" + eds.getEdsUserByEdsAffectationUserId().toFullIdentity() + "</b>", "<b>" + author.toFullIdentity()
                        + "</b>"));
        contentBuilder.append("</br>");

        contentBuilder.append(bundle.getString("filter-project-launcher"));
        contentBuilder.append(" :");
        contentBuilder.append(createProjectLink(eds.getEdsProject()));
        contentBuilder.append("</br>");

        contentBuilder.append(bundle.getString("generic-data-link-follow"));
        contentBuilder.append(" :");
        for (EdsProjectEds ep : eds.getEdsProjectEdses()) {
            contentBuilder.append(createProjectLink(ep.getEdsProject()));
            contentBuilder.append(" ");
        }
        contentBuilder.append("</br>");
        if (create) {
            contentBuilder.append("<h3>Informations complémentaires :</h3>");
            contentBuilder.append("<br>");
            contentBuilder.append("<div class=comment>");
            contentBuilder.append(bundle.getString("eds-new-affect-auto"));
            contentBuilder.append("</div>");

        } else {
            contentBuilder.append(buildComment());
        }

        contentBuilder.append("<div class=footer>");
        contentBuilder.append(bundle.getString("mail-footer-line-1"));
        contentBuilder.append("</br>");
        contentBuilder.append(MessageFormat.format(bundle.getString("mail-footer-line-2"),
                createLink("Ophelie EDS", controller.getServerBindAdress())));
        contentBuilder.append("</br>");
        contentBuilder.append(MessageFormat.format(bundle.getString("mail-footer-line-3"),
                createLink("doc-info link", "http://docinfogroupe.inetpsa.com/ead/doc/ref.01301_13_00456/v.vc/fiche")));
        contentBuilder.append("</br>");
        contentBuilder
                .append(MessageFormat.format(bundle.getString("mail-footer-line-4"), createLink("ToolSupEE", "http://toolsup-ee.inetpsa.com/")));
        contentBuilder.append("</div>");

        content = contentBuilder.toString();

    }

    /**
     * This method is used to create HTML link for the EDS provided.
     * 
     * @param eds EDS for which link to be created.
     * @return Link of the EDS in HTML format.
     */
    private String createEdsLink(EdsEds eds) {
        StringBuilder builder = new StringBuilder("<a href=\"");
        builder.append(controller.getServerBindAdress());
        builder.append('#');
        builder.append(FragmentManager.formatURLFragmentForEDS(eds));
        builder.append("\">");
        builder.append(eds.getEdsRef());
        builder.append("</a>");
        return builder.toString();
    }

    /**
     * This method is used to create HTML link.
     * 
     * @param name Name to be appended to the link.
     * @param baseURL Base URL of the link.
     * @return Link in HTML format.
     */
    private String createLink(String name, String baseURL) {
        StringBuilder builder = new StringBuilder("<a href=\"");
        builder.append(baseURL);
        builder.append("\">");
        builder.append(name);
        builder.append("</a>");
        return builder.toString();
    }

    /**
     * This method is used to create HTML link for project.
     * 
     * @param ep Project for which link needs to be created.
     * @return Link of project in HTML format.
     */
    private String createProjectLink(EdsProject ep) {
        StringBuilder builder = new StringBuilder("<a href=\"");
        builder.append(controller.getServerBindAdress());
        builder.append("#project&id=");
        builder.append(ep.getPId());
        builder.append("\">");
        builder.append(ep.getPName());
        builder.append("</a>");
        return builder.toString();
    }

    /**
     * This method is used to append additional information to the mail content.
     * 
     * @return Additional information to be added.
     */
    private Object buildComment() {
        if (comment != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("<h3>Informations complémentaires :</h3>");
            builder.append("<br>");
            builder.append("<div class=comment>");
            builder.append(comment.replace("\n", "<br>"));
            builder.append("</div>");
            return builder;
        }
        return "";
    }
}
