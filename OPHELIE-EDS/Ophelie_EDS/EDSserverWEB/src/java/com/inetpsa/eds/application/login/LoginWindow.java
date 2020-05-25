package com.inetpsa.eds.application.login;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.I_ViewRefreshable;
import com.inetpsa.eds.application.header.PsaHeader;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * User login window.
 * 
 * @author Geometric Ltd.
 */
public class LoginWindow extends A_EdsWindow implements I_ViewRefreshable {

    // PUBLIC

    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public LoginWindow(EdsApplicationController controller) {
        super("Eds | Login");
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store EDS logo.
     */
    private static final Resource EDS_ICON = ResourceManager.getInstance().getThemeIconResource("icons/eds-logo-large.png");
    /**
     * Text field to input user name.
     */
    private TextField vTXTloginValue;
    /**
     * Password field to input password.
     */
    private PasswordField vTXTpasswordValue;
    /**
     * Option group to select language.
     */
    private OptionGroup vOGlanguageValue;
    // Labels to refresh
    /**
     * Label to display 'PSA identifier'.
     */
    private Label vLBLlogin;
    /**
     * Label to display 'RPI password'.
     */
    private Label vLBLpassword;
    /**
     * Button to login.
     */
    private Button vBTlogin;
    /**
     * Map to store language.
     */
    private static final HashMap<String, String> LANGUAGE = new HashMap<String, String>();

    static {
        LANGUAGE.put("fr", "Francais");
        LANGUAGE.put("en", "English");
        LANGUAGE.put("zh", "中国的");
    }

    /**
     * The method is used to extract the encoded username and password.
     */
    private void extractDecodedUserAndPassword() {
        String authorization = this.controller.getAuthorization();
        boolean isValidB2BRequest = this.controller.isValidB2BRequest();

        if (authorization != null && isValidB2BRequest) {
            if (authorization.startsWith("Basic")) {
                String[] encodedstr = authorization.split(" ");
                authorization = encodedstr[1].trim();
            }

            String credentials = new String(org.apache.commons.codec.binary.Base64.decodeBase64(authorization.getBytes()));
            final String[] values = credentials.split(":", 2);
            if (values.length > 1) {
                setUserNameAndPassword(values[0], values[1]);
            }
        }
    }

    /**
     * This method is used to set the default username and password and disable them.
     * 
     * @param userName username to be added.
     * @param password password to be added.
     */
    private void setUserNameAndPassword(String userName, String password) {
        vTXTloginValue.setValue(userName);
        vTXTloginValue.setEnabled(false);
        vTXTpasswordValue.setValue(password);
        vTXTpasswordValue.setEnabled(false);
    }

    /**
     * Initialization method. This method creates the UI for login page.
     */
    private void init() {

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();

        VerticalLayout displayLayout = new VerticalLayout();
        displayLayout.setSizeFull();
        displayLayout.setStyleName("main-layout");

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSpacing(true);

        mainLayout.addComponent(new Embedded(null, EDS_ICON));

        VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setSizeFull();

        GridLayout loginForm = new GridLayout(2, 4);
        loginForm.setSpacing(true);

        vTXTloginValue = new TextField();
        vTXTloginValue.setValue(controller.getCookieByID(EdsApplicationController.USERNAME_COOKIE));
        vTXTloginValue.setRequired(true);
        vTXTpasswordValue = new PasswordField();
        vTXTpasswordValue.setRequired(true);
        vTXTpasswordValue.setValue(controller.getCookieByID(EdsApplicationController.PASSWORD_COOKIE));

        extractDecodedUserAndPassword();

        vLBLlogin = new Label(controller.getBundle().getString("login.psaid"));
        loginForm.addComponent(vLBLlogin, 0, 0);
        loginForm.setComponentAlignment(vLBLlogin, Alignment.MIDDLE_LEFT);
        loginForm.addComponent(vTXTloginValue, 1, 0);
        loginForm.setComponentAlignment(vTXTloginValue, Alignment.MIDDLE_LEFT);

        vLBLpassword = new Label(controller.getBundle().getString("login.password"));
        loginForm.addComponent(vLBLpassword, 0, 1);
        loginForm.setComponentAlignment(vLBLpassword, Alignment.MIDDLE_LEFT);
        loginForm.addComponent(vTXTpasswordValue, 1, 1);
        loginForm.setComponentAlignment(vTXTpasswordValue, Alignment.MIDDLE_LEFT);

        vBTlogin = new Button(controller.getBundle().getString("login.login"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                login();
            }
        });

        loginForm.addComponent(vBTlogin, 1, 3);

        loginLayout.addComponent(loginForm);
        loginLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_LEFT);

        vOGlanguageValue = new OptionGroup();
        vOGlanguageValue.setImmediate(true);
        vOGlanguageValue.setStyleName("horizontal");
        for (Locale locale : controller.getAvailableLocale()) {
            vOGlanguageValue.addItem(locale);
            vOGlanguageValue.setItemCaption(locale, LANGUAGE.get(locale.getLanguage()));
        }

        // Select a language based on the browser language.
        vOGlanguageValue.setValue(Locale.ENGLISH);
        for (Locale loc : (Collection<Locale>) vOGlanguageValue.getItemIds()) {
            if (loc.getLanguage().equals(controller.getApplication().getLocale().getLanguage())) {
                vOGlanguageValue.setValue(loc);
                break;
            }
        }

        vOGlanguageValue.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Locale.setDefault((Locale) vOGlanguageValue.getValue());
                getApplication().setLocale(Locale.getDefault());
            }
        });
        checkForLanguagevisibility();
        loginLayout.addComponent(vOGlanguageValue);

        mainLayout.addComponent(loginLayout);

        displayLayout.addComponent(mainLayout);
        displayLayout.setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);
        displayLayout.setExpandRatio(mainLayout, 1f);
        displayLayout.addComponent(controller.getFooter());
        // contentLayout.addComponent( controller.getPsaHeader());
        contentLayout.addComponent(controller.getHeader());
        contentLayout.addComponent(displayLayout);
        contentLayout.setExpandRatio(displayLayout, 1f);
        setContent(contentLayout);
    }

    /**
     * This method is used to show/hide language selection option based on Header value
     */
    private void checkForLanguagevisibility() {
        if (controller.getHeader() instanceof PsaHeader) {
            vOGlanguageValue.setVisible(true);
        } else {
            vOGlanguageValue.setVisible(false);
        }
    }

    /**
     * This method is used to login to the application. This method validates the user name and password.
     */
    private void login() {
        if (!vTXTloginValue.isValid()) {
            showWarning(controller.getBundle().getString("login.error"));
            return;
        }
        if (!vTXTpasswordValue.isValid()) {
            showWarning(controller.getBundle().getString("login.error1"));
            return;
        }

        EDSdao dao = EDSdao.getInstance();

        final EdsUser user = dao.getUserByPsaID((String) vTXTloginValue.getValue());

        if (user == null || !controller.checkLDAPAuthentication(user, (String) vTXTpasswordValue.getValue())) {
            showWarning(controller.getBundle().getString("login.error2"));
            return;
        }
        if (!controller.checkLDAPGroup(user)) {
            showWarning(controller.getBundle().getString("login.error5"));
            return;
        }

        if (!user.getEdsRole().isActive()) {
            showWarning(controller.getBundle().getString("login.error3"));
            return;
        }
        controller.addCookie(EdsApplicationController.USERNAME_COOKIE, (String) vTXTloginValue.getValue());
        controller.addCookie(EdsApplicationController.PASSWORD_COOKIE, (String) vTXTpasswordValue.getValue());

        controller.setAuthenticatedUser(user);
        controller.showUserWindow();

        // Ex_Conn_113 - Load the db elect. from Gestion server
        controller.initDbelec();
    }

    /**
     * This method is used to refresh the login page.
     * 
     * @see com.inetpsa.eds.application.I_ViewRefreshable#refreshView()
     */
    public void refreshView() {
        vLBLlogin.setValue(controller.getBundle().getString("login.psaid"));
        vLBLpassword.setValue(controller.getBundle().getString("login.password"));
        vBTlogin.setCaption(controller.getBundle().getString("login.login"));
    }
}
