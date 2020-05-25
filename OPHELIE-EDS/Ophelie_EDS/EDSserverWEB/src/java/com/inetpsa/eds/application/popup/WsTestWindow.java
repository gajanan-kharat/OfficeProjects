package com.inetpsa.eds.application.popup;

import javax.ws.rs.core.MediaType;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.ws.model.UserInfo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * This class is used for creating the login window at the time of export.
 * 
 * @author Geometric Ltd.
 */
public class WsTestWindow extends A_EdsWindow {
    // PUBLIC
    /**
     * Constant variable to store EXPORT_PROJECT status.
     */
    public static final int EXPORT_PROJECT = 0;
    /**
     * Constant variable to store EXPORT_EDS status.
     */
    public static final int EXPORT_EDS = 1;

    /**
     * Parameterized constructor.
     * 
     * @param controller EDS Application controller object.
     * @param exportType Type of export(Project/EDS).
     * @param exportId Export ID.
     */
    public WsTestWindow(EdsApplicationController controller, int exportType, String exportId) {
        super(controller.getBundle().getString("eds-pop-ws-title"));
        this.controller = controller;
        this.exportId = exportId;
        this.exportType = exportType;
        init();
    }

    // PROTECTED
    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Window#close()
     */
    @Override
    protected void close() {
        if (tokenID != null) {
            logout(tokenID);
        }
        super.close();
    }

    // PRIVATE
    /**
     * Text field for user name.
     */
    private TextField vTFuserNameValue;
    /**
     * Password field for password.
     */
    private PasswordField vTFpasswordValue;
    /**
     * EDS Application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store layout of pop-up.
     */
    private VerticalLayout loginLayout;
    /**
     * Variable to store content layout of pop-up.
     */
    private VerticalLayout contentLayout;
    /**
     * Variable to store the token id of the request.
     */
    private String tokenID;
    /**
     * Variable to store the export type.
     */
    private int exportType;
    /**
     * Variable to store the export id.
     */
    private String exportId;

    /**
     * This method is used to initialize this class.
     */
    private void init() {
        VerticalLayout layout = (VerticalLayout) getContent();
        layout.setSizeUndefined();
        layout.setMargin(true);
        layout.addComponent(getLoginLayout());
    }

    /**
     * This method is used to login to the application.
     * 
     * @param userInfo User details.
     * @return token id.
     */
    private String login(UserInfo userInfo) {
        ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(controller.getApplication().getURL() + "resources").path("authenticate");

        WebResource loginResource = webResource.path("login");

        String token = loginResource.type(MediaType.APPLICATION_JSON).post(String.class, userInfo);
        client.destroy();
        return token;
    }

    /**
     * This method is used for logging out of the application.
     * 
     * @param tokenID token id.
     */
    private void logout(String tokenID) {
        ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(controller.getApplication().getURL() + "resources").path("authenticate");

        WebResource logoutResource = webResource.path("logout");

        logoutResource.queryParam("token-id", tokenID).get(String.class);
        client.destroy();
    }

    /**
     * This method is used to get the login pop-up window.
     * 
     * @return Component of login window.
     */
    private VerticalLayout getLoginLayout() {
        if (loginLayout == null) {
            loginLayout = new VerticalLayout();
            loginLayout.setCaption(controller.getBundle().getString("eds-pop-ws-connect"));
            loginLayout.setSpacing(true);
            loginLayout.setMargin(true);
            vTFuserNameValue = new TextField();
            vTFuserNameValue.setImmediate(true);
            vTFuserNameValue.setInputPrompt(controller.getBundle().getString("login.psaid"));
            vTFpasswordValue = new PasswordField();
            vTFpasswordValue.setImmediate(true);
            vTFpasswordValue.setInputPrompt(controller.getBundle().getString("login.password"));

            String login = controller.getCookieByID("username");
            String pass = controller.getCookieByID("password");

            vTFuserNameValue.setValue(login);
            vTFpasswordValue.setValue(pass);

            Button loginButton = new Button(controller.getBundle().getString("login.login"), new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    if (!"".equals(vTFpasswordValue.getValue()) && !"".equals(vTFuserNameValue.getValue())) {

                        EdsUser user = EDSdao.getInstance().getUserByPsaID((String) vTFuserNameValue.getValue());

                        if (user == null || controller.checkLDAPAuthentication(user, (String) vTFpasswordValue.getValue())) {
                            tokenID = login(new UserInfo((String) vTFuserNameValue.getValue(), (String) vTFpasswordValue.getValue()));
                            ((VerticalLayout) getContent()).replaceComponent(getLoginLayout(), getContentLayout());
                        } else {
                            showError(controller.getBundle().getString("login.error2"));
                        }
                    } else {
                        showError(controller.getBundle().getString("login.error4"));
                    }
                }
            });

            loginLayout.addComponent(vTFuserNameValue);
            loginLayout.addComponent(vTFpasswordValue);
            loginLayout.addComponent(loginButton);
        }
        return loginLayout;
    }

    /**
     * This method is used to get the content layout.
     * 
     * @return Component of login window.
     */
    private VerticalLayout getContentLayout() {
        if (contentLayout == null) {
            contentLayout = new VerticalLayout();
            contentLayout.setSpacing(true);
            contentLayout.setMargin(true);
            contentLayout.addComponent(new Label(controller.getBundle().getString("eds-pop-ws-link")));

            String resourceUrl = controller.getApplication().getURL() + "resources/";
            switch (exportType) {
            case EXPORT_EDS:
                resourceUrl += "eds?eds-ref=" + exportId + "&token-id=" + tokenID;
                break;
            case EXPORT_PROJECT:
                resourceUrl += "project?project-name=" + exportId + "&token-id=" + tokenID;
                break;
            default:
                throw new RuntimeException("Type d'export inconnu");
            }

            Link resourceLink = new Link("EXPORT (Effectuer un clique droit \"enregistrer la cible du lien sous\" pour enregistrer ce fichier) ",
                    new ExternalResource(resourceUrl));
            resourceLink.setTargetName("_blank");
            contentLayout.addComponent(resourceLink);
        }
        return contentLayout;
    }
}
