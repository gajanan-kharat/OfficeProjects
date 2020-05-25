package com.inetpsa.eds.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.Cookie;

import org.vaadin.dialogs.ConfirmDialog;

import com.inetpsa.clp.LDAPGroup;
import com.inetpsa.clp.LDAPUser;
import com.inetpsa.clp.core.LdapAuthStatus;
import com.inetpsa.clp.exception.LDAPException;
import com.inetpsa.clp.exception.LDAPObjNotFoundException;
import com.inetpsa.dbelec.DBelecControllerDAO;
import com.inetpsa.eds.EdsApplication;
import com.inetpsa.eds.application.actionbar.EdsActionBar;
import com.inetpsa.eds.application.busy.BusyWindow;
import com.inetpsa.eds.application.content.EdsContent;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.genericdata.GenericDataFormBuilder;
import com.inetpsa.eds.application.content.home.HomeView;
import com.inetpsa.eds.application.content.userparams.EdsUserParamManagementForm;
import com.inetpsa.eds.application.footer.EdsFooter;
import com.inetpsa.eds.application.header.B2BHeader;
import com.inetpsa.eds.application.header.EdsApplicationHeader;
import com.inetpsa.eds.application.header.PsaHeader;
import com.inetpsa.eds.application.login.LoginWindow;
import com.inetpsa.eds.application.menu.EdsAdminNavigationMenu;
import com.inetpsa.eds.application.menu.EdsNavigationMenu;
import com.inetpsa.eds.application.menu.admin.EN_AdminNode;
import com.inetpsa.eds.application.popup.DuplicateProjectWindow;
import com.inetpsa.eds.application.popup.EdsAffectationWindow;
import com.inetpsa.eds.application.popup.NewEdsWindow;
import com.inetpsa.eds.application.popup.ReconductionWithModifWindow;
import com.inetpsa.eds.application.popup.ReconductionWithoutModifWindow;
import com.inetpsa.eds.application.popup.ReconsultationWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.EdsConfRessourceBundle;
import com.inetpsa.eds.dao.GestionConnector;
import com.inetpsa.eds.dao.GestionConnectorException;
import com.inetpsa.eds.dao.I_ProjectSpecificData;
import com.inetpsa.eds.dao.I_ReconsultableFormData;
import com.inetpsa.eds.dao.model.EdsComponentType;
import com.inetpsa.eds.dao.model.EdsConsolidateSupply;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsCourantCycle;
import com.inetpsa.eds.dao.model.EdsCourantMiseSousTension;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsModeMontage;
import com.inetpsa.eds.dao.model.EdsModeParc;
import com.inetpsa.eds.dao.model.EdsNumber96k;
import com.inetpsa.eds.dao.model.EdsPerimeter;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsProjectEds;
import com.inetpsa.eds.dao.model.EdsPsaMesureSupply;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.dao.model.EdsReseauVeilleReveilleOrganeInactif;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.dao.model.EdsSubscription;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.localisation.BundleManager;
import com.inetpsa.eds.tools.mail.EdsMailAdviceBuilder;
import com.inetpsa.eds.tools.mail.EdsMailMessageBuilder;
import com.inetpsa.eds.tools.uri.A_FragmentHandler;
import com.inetpsa.eds.tools.uri.FH_Admin;
import com.inetpsa.eds.tools.uri.FH_EdsEds;
import com.inetpsa.eds.tools.uri.FH_EdsProject;
import com.inetpsa.eds.tools.uri.FH_Home;
import com.inetpsa.eds.tools.uri.FH_Logout;
import com.inetpsa.eds.tools.uri.FH_Null;
import com.inetpsa.eds.tools.uri.FH_UserParams;
import com.inetpsa.eds.tools.uri.FragmentManager;
import com.inetpsa.eds.ws.model.EdsWsSessionToken;
import com.inetpsa.outils.configuration.ConfigurationManager;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.terminal.gwt.server.WebBrowser;
import com.vaadin.ui.Component;
import com.vaadin.ui.UriFragmentUtility;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

/**
 * This is Main application controller.
 * 
 * @author Geometric Ltd.
 */
public class EdsApplicationController implements FragmentChangedListener, Serializable {
    // PUBLIC
    // Stages

    /**
     * The static variable to initialize logger for the class.
     */
    private final static Logger logger = Logger.getLogger(EdsApplicationController.class.getName());
    /**
     * Constant variable to hold value of PRELIM_STAGE
     */
    public static final int PRELIM_STAGE = 0;

    /**
     * Constant variable to hold value of PRELIM_STAGE_TEXT
     */
    public static final String PRELIM_STAGE_TEXT = "Préliminaire";

    /**
     * Constant variable to hold english value of PRELIM_STAGE_TEXT
     */
    public static final String PRELIM_STAGE_TEXT_EN = "Preliminary";
    /**
     * Constant variable to hold value of ROBUST_STAGE
     */
    public static final int ROBUST_STAGE = 1;
    /**
     * Constant variable to hold value of ROBUST_STAGE_TEXT
     */
    public static final String ROBUST_STAGE_TEXT = "Robuste";
    /**
     * Constant variable to hold english value of ROBUST_STAGE_TEXT
     */
    public static final String ROBUST_STAGE_TEXT_EN = "Robust";
    /**
     * Constant variable to hold value of SOLID_STAGE
     */
    public static final int SOLID_STAGE = 2;
    /**
     * Constant variable to hold value of SOLID_STAGE_TEXT
     */
    public static final String SOLID_STAGE_TEXT = "Consolidé";
    /**
     * Constant variable to hold english value of SOLID_STAGE_TEXT
     */
    public static final String SOLID_STAGE_TEXT_EN = "Consolidated";
    /**
     * Constant variable to hold value of CLOSED_STAGE
     */
    public static final int CLOSED_STAGE = 3;
    /**
     * Constant variable to hold value of CLOSED_STAGE_TEXT
     */
    public static final String CLOSED_STAGE_TEXT = "Clôturé";
    /**
     * Constant variable to hold english value of CLOSED_STAGE_TEXT
     */
    public static final String CLOSED_STAGE_TEXT_EN = "Closed";

    // Subtypes
    /**
     * Constant variable to hold value of ST1_BT
     */
    public static final int ST1_BT = 0;
    /**
     * Constant variable to hold value of ST1_BT_TEXT
     */
    public static final String ST1_BT_TEXT = "BT";
    /**
     * Constant variable to hold value of ST1_TBT
     */
    public static final int ST1_TBT = 1;
    /**
     * Constant variable to hold value of ST1_TBT_TEXT
     */
    public static final String ST1_TBT_TEXT = "TBT";
    /**
     * Constant variable to hold value of ST2_GMP
     */
    public static final int ST2_GMP = 2;
    /**
     * Constant variable to hold value of ST2_GMP_TEXT
     */
    public static final String ST2_GMP_TEXT = "GMP";
    /**
     * Constant variable to hold value of ST2_LAS
     */
    public static final int ST2_LAS = 3;
    /**
     * Constant variable to hold value of ST2_LAS_TEXT
     */
    public static final String ST2_LAS_TEXT = "LAS";
    /**
     * Constant variable to hold value of ST2_AUTRE
     */
    public static final int ST2_AUTRE = 4;
    /**
     * Constant variable to hold value of ST2_AUTRE_TEXT
     */
    public static final String ST2_AUTRE_TEXT = "Autre";
    // Cookies
    /**
     * Constant variable to hold value of USERNAME_COOKIE
     */
    public final static String USERNAME_COOKIE = "username";
    /**
     * Constant variable to hold value of PASSWORD_COOKIE
     */
    public final static String PASSWORD_COOKIE = "password";

    /**
     * Constant variable to hold value of CONF_SERVER_IMAGE_PATH
     */
    public static final String CONF_SERVER_IMAGE_PATH = "server_image_path";
    /**
     * Constant variable to hold value of CONF_SERVER_BIND_ADRESS
     */
    public static final String CONF_SERVER_BIND_ADRESS = "server_bind_adress";
    /**
     * variable to hold value A_EdsReadForm
     */
    private A_EdsReadForm readForm;
    /**
     * variable to hold value authorization
     */
    private String authorization;
    /**
     * variable determine if the B2B request is valid
     */
    private boolean isValidB2BRequest;

    /**
     * DBelec
     */
    private DBelecControllerDAO dbelec;

    /**
     * PUBLIC parameterized constructor
     * 
     * @param application Eds application
     */
    public EdsApplicationController(EdsApplication application) {
        this.application = application;
        init();
    }

    /**
     * Get the component database. Null if not set.
     * 
     * @return The component database.
     */
    public DBelecControllerDAO getDbelec() {
        return dbelec;
    }

    /**
     * This method set locale
     * 
     * @param locale Locale represents language
     */
    public void setLocale(Locale locale) {
        this.bundle = BundleManager.getInstance().getResourceBundle(locale);

        getHeader().refreshView();
        getLoginWindow().refreshView();
    }

    /**
     * This method set locale
     * 
     * @param locale Locale represents language
     */
    public void setLocaleOnly(Locale locale) {
        this.bundle = BundleManager.getInstance().getResourceBundle(locale);

    }

    /**
     * This method is used to set the header and login view.
     */
    public void setHeaderAndLoginView() {
        getHeader().refreshView();
        getLoginWindow().refreshView();
    }

    /**
     * This methods gets all available locale.
     * 
     * @return list of locale
     */
    public List<Locale> getAvailableLocale() {
        return Arrays.asList(Locale.FRENCH, Locale.ENGLISH);
    }

    /**
     * This method is called when program need locale specific objects.
     * 
     * @return bundle Variable holding resource bundle value
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * This method used to get Eds application.
     * 
     * @return application Eds application
     */
    public EdsApplication getApplication() {
        return application;
    }

    /**
     * This method used to get Login window for Eds application.
     * 
     * @return loginWindow Eds login window
     */
    public LoginWindow getLoginWindow() {
        if (null == loginWindow) {
            loginWindow = new LoginWindow(this);
        }
        return loginWindow;
    }

    public ResourceBundle getEdsConfRessourceBundle() {
        return edsConfRessourceBundle;
    }

    public void setEdsConfRessourceBundle(ResourceBundle edsConfRessourceBundle) {
        this.edsConfRessourceBundle = edsConfRessourceBundle;
    }

    public OutputStream getImageOutputStream(String filename) {
        try {

            // File docroot = new File("../");
            // String s = docroot.getCanonicalPath() + "/";
            File imageFile = new File(edsConfRessourceBundle.getString(CONF_SERVER_IMAGE_PATH), filename);

            if (!imageFile.getParentFile().exists()) {
                imageFile.getParentFile().mkdirs();
                imageFile.createNewFile();
            }
            logger.log(Level.INFO, "[INFO] Path image Output: {0}", imageFile.getAbsolutePath());
            return new FileOutputStream(imageFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getImageFilePath(String filename) {
        // File docroot = new File("../");
        // String path = "";
        // try {
        // path = docroot.getCanonicalPath() + "/";
        // } catch (IOException ex) {
        // Logger.getLogger(EDSdao.class.getName()).log(Level.SEVERE,
        // null,
        // ex);
        // }
        // logger.log(Level.INFO,
        // "[INFO] Path image : {0}",
        // path + edsConfRessourceBundle.getString(CONF_SERVER_IMAGE_PATH) + filename);
        return edsConfRessourceBundle.getString(CONF_SERVER_IMAGE_PATH) + filename;
    }

    public String getConfEntry(String confKey) {
        return edsConfRessourceBundle.getString(confKey);
    }

    public String getServerBindAdress() {
        return edsConfRessourceBundle.getString(CONF_SERVER_BIND_ADRESS);
    }

    /**
     * This method used to get User window.
     * 
     * @return userWindow User window
     */
    public EdsUserWindow getUserWindow() {
        if (null == userWindow) {
            userWindow = new EdsUserWindow(this);
        }
        return userWindow;
    }

    /**
     * This method used to get PSA header.
     * 
     * @return psaHeader PSA header
     */
    public EdsApplicationHeader getPsaHeader() {
        if (header == null) {
            header = new PsaHeader(this);
        }
        return header;
    }

    /**
     * This method used to get header of EDS application
     * 
     * @return EdsApplicationHeader EDS application Header
     */
    public EdsApplicationHeader getHeader() {
        if (!isValidB2BRequest) {

            if (header == null) {
                header = new PsaHeader(this);
            }

            return header;

        } else {
            if (header == null) {
                header = new B2BHeader(this);
            }

            return header;
        }
    }

    /**
     * This method used to get navigation menu.
     * 
     * @return navigationMenu Navigation Menu
     */
    public EdsNavigationMenu getNavigationMenu() {
        if (null == navigationMenu) {
            navigationMenu = new EdsNavigationMenu(this);
        }
        return navigationMenu;
    }

    /**
     * This method used to get Admin navigation menu.
     * 
     * @return adminNavigationMenu Navigation Menu for admin user
     */
    public EdsAdminNavigationMenu getAdminNavigationMenu() {
        if (null == adminNavigationMenu) {
            adminNavigationMenu = new EdsAdminNavigationMenu(this);
        }
        return adminNavigationMenu;
    }

    /**
     * This method is used to get Action Bar.
     * 
     * @return actionBar Action bar of EDS application
     */
    public EdsActionBar getActionBar() {
        if (null == actionBar) {
            actionBar = new EdsActionBar(this);
        }
        if (!actionBar.isVisible()) {
            actionBar.setVisible(true);
        }
        return actionBar;
    }

    /**
     * This method is used to hide action bar.
     */
    public void hideActionBar() {
        if (actionBar != null && actionBar.isVisible()) {
            actionBar.setVisible(false);
        }
    }

    /**
     * This method is used to get home view
     * 
     * @return homeView Home view
     */
    public HomeView getHomeView() {
        if (homeView == null) {
            homeView = new HomeView(this);
        }
        return homeView;
    }

    /**
     * This method is used to get User param node.
     * 
     * @return userParamManagementNode Admin node for English locale
     */
    public EN_AdminNode getUserParamsNode() {
        if (userParamManagementNode == null) {
            userParamManagementNode = new EN_AdminNode(this, getBundle().getString("Admin-user-parametre"), new EdsUserParamManagementForm(this));
        }
        return userParamManagementNode;
    }

    /**
     * This method is used to get UI display content .
     * 
     * @return content Content to display on UI
     */
    public EdsContent getContent() {
        if (null == content) {
            content = new EdsContent();
        }
        return content;
    }

    /**
     * This method is used to set UI display content .
     * 
     * @param component Variable to set Component value
     */
    public void setContent(Component component) {
        getContent().removeAllComponents();
        getContent().addComponent(component);
    }

    /**
     * This method is used to get Footer of Eds application.
     * 
     * @return footer Variable to hold EdsFooter value
     */
    public EdsFooter getFooter() {
        if (this.footer == null) {
            this.footer = new EdsFooter(this);
        }

        return this.footer;
    }

    /**
     * This method is used to get URI fragment utility
     * 
     * @return uriFragmentUtility variable to hold UriFragmentUtility value
     */
    public UriFragmentUtility getUriFragmentUtility() {
        if (uriFragmentUtility == null) {
            uriFragmentUtility = new UriFragmentUtility();
            uriFragmentUtility.addListener(this);
        }
        return uriFragmentUtility;
    }

    /**
     * This method is used to get current Edsproject.
     * 
     * @return currentProject Current EdsProject
     */
    public EdsProject getCurrentProject() {
        return currentProject;
    }

    /**
     * This method is used to set current EdsProject.
     * 
     * @param project Variable to set EdsProject value
     */
    public void setCurrentProject(EdsProject project) {
        this.currentProject = project;
    }

    /**
     * This method is used to get Authenticated User
     * 
     * @return authenticatedUser Variable to hold EdsUser value
     */
    public EdsUser getAuthenticatedUser() {
        return authenticatedUser;
    }

    /**
     * This method is used to set Authenticated User
     * 
     * @param authenticatedUser Variable to set EdsUser value
     */
    public void setAuthenticatedUser(EdsUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    /**
     * Performs authentication with the LDAP PSA
     * 
     * @param user The user to authenticate
     * @param password The password entered by the user
     * @return If LDAP authentication worked
     */
    public boolean checkLDAPAuthentication(EdsUser user, String password) {
        if (user == null || user.getUPsaId() == null || password == null) {
            throw new NullPointerException();
        }

        try {
            LDAPUser ldapUser = new LDAPUser();
            ldapUser.setUid(user.getUPsaId());

            LdapAuthStatus status = ldapUser.authenticate(password);
            if (status.isError()) {
                return false;
            } else {
                return true;
            }
        } catch (LDAPObjNotFoundException ex) {
            return false;
        } catch (LDAPException ex) {
            throw new RuntimeException(ex);
        }

    }

    // public String lookInLdapcInfos( EdsUser edsUser )
    // {
    // return "";
    // }
    /**
     * This method is used to get User mail.
     * 
     * @param user The authenticated user
     * @return mail of user
     */
    public String getUserMail(EdsUser user) {
        String mail = null;
        if (user == null || user.getUPsaId() == null) {
            throw new NullPointerException();
        }

        try {

            LDAPUser ldapUser = new LDAPUser();
            ldapUser.setUid(user.getUPsaId());

            mail = ldapUser.getEmail();

        } catch (LDAPObjNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (LDAPException ex) {
            throw new RuntimeException(ex);
        }
        return mail;
    }

    /**
     * Checks if user has sufficient right.
     * 
     * @param rightId String representing right id
     * @return if user has sufficient rights
     */
    public boolean userHasSufficientRights(String rightId) {
        return EdsRight.hasSufficientRights(authenticatedUser, rightId);
    }

    /**
     * checks user has subscribed EDS.
     * 
     * @param eds Eds details
     * @return if user has subscribed Eds
     */
    public boolean userHasSubscribed(EdsEds eds) {
        boolean res;

        res = EDSdao.getInstance().getUserSubscriptionToEDS(authenticatedUser.getUId(), eds.getEdsRef()) != null;

        return res;
    }

    /**
     * This method shows the home page view
     */
    public void showHomePage() {
        getUserWindow().setUserMenu();
        getNavigationMenu().unselectNodes();
        actionBar.setVisible(false);
        getHomeView().refresh();
        setContent(getHomeView());
    }

    /**
     * This method shows user params page
     */
    public void showUserParamsPage() {
        getUserWindow().setUserMenu();
        actionBar.setVisible(false);
        getNavigationMenu().unselectNodes();
        getUserParamsNode().onEnter();
    }

    /**
     * This method shows admin page
     */
    public void showAdminPage() {
        getUserWindow().setAdminMenu();
        getAdminNavigationMenu().unselectNode();
        getAdminNavigationMenu().selectProjectManagement();
    }

    /**
     * This method shows project details.
     * 
     * @param projectID Name of project
     */
    public void showProject(String projectID) {
        getUserWindow().setUserMenu();
        getNavigationMenu().getMainNavigatorMenu().selectProject(projectID);
        authenticatedUser.getConfig().addViewedProject(projectID);
    }

    /**
     * This method shows all EDS
     * 
     * @param parameters Hash map holding URI fragment parameters
     */
    public void showAllEds(HashMap<String, String> parameters) {
        getUserWindow().setUserMenu();
        getNavigationMenu().getMainNavigatorMenu().selectAllEdsNode(parameters);
    }

    /**
     * Change the status of the eds to "quit."
     * 
     * @param eds Variable that hold EDS details
     */
    public void abortEds(final EdsEds eds) {
        eds.setEdsState(EdsEds.ABORTED_STATE);
        eds.setEdsModifDate(new Date());
        eds.setEdsUserByEdsModifUserId(getAuthenticatedUser());

        // eds.setEdsMajorVersion( eds.getEdsMajorVersion() + 1 );
        EDSdao.getInstance().mergeDetachedDBObject(eds);

        sendNotificationMail(GenericDataFormBuilder.ID, eds, EdsMailMessageBuilder.EDS_ABORTED);
        closeEds(eds);
    }

    /**
     * Change the status of the eds to "normal".
     * 
     * @param eds Variable that hold EDS details
     */
    public void unabortEds(final EdsEds eds) {
        eds.setEdsState(EdsEds.DEFAULT_STATE);
        eds.setEdsModifDate(new Date());

        EDSdao.getInstance().mergeDetachedDBObject(eds);

        sendNotificationMail(GenericDataFormBuilder.ID, eds, EdsMailMessageBuilder.EDS_RETRIEVED);
        closeEds(eds);
        openEds(eds);
    }

    /**
     * This method subscribes user to eds whose reference is edsRef
     * 
     * @param edsRef Reference to EDS
     */
    public void subscribeToEds(String edsRef) {
        EDSdao dao = EDSdao.getInstance();

        dao.saveDetachedDBObject(new EdsSubscription(UUID.randomUUID().toString(), edsRef, authenticatedUser));

        authenticatedUser.getConfig().addSubscribedEds(edsRef);
    }

    /**
     * This method Unsubcribe the user from eds whose reference is edsRef
     * 
     * @param edsRef Reference to EDS
     */
    public void unsubscribeFromEds(String edsRef) {
        EDSdao dao = EDSdao.getInstance();

        dao.deleteDetachedDBObject(dao.getUserSubscriptionToEDS(authenticatedUser.getUId(), edsRef));

    }

    /**
     * This method Send notification mail.
     * 
     * @param formId Form ID
     * @param eds Eds Details
     * @param messageType Type of message
     */
    public void sendNotificationMail(String formId, EdsEds eds, int messageType) {

        List<EdsUser> subscribers = EDSdao.getInstance().getAllSubscribersToEds(eds.getEdsRef());

        // Subscribers are treated by language
        Map<String, List<InternetAddress>> subscribersMap = new HashMap<String, List<InternetAddress>>();
        try {
            for (EdsUser subscriber : subscribers) {
                // Check the user rights - the right to read the form and access the EDS required
                if (EdsRight.hasSufficientRights(subscriber, EdsFormFactory.getBuilder(formId).getReadingRightId())
                        && userCanAccessEds(subscriber, eds)) {
                    // Check the message type
                    if (userMustBeNotified(messageType, subscriber)) {
                        String language = subscriber.getConfig().getPreferredLanguage();
                        if (!subscribersMap.containsKey(language)) {
                            subscribersMap.put(language, new ArrayList<InternetAddress>());
                        }
                        subscribersMap.get(language).add(new InternetAddress(subscriber.getConfig().getMailAddress()));
                    }
                }
            }
        } catch (AddressException ex) {
            Logger.getLogger(EdsApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Sending a message by language
        for (String language : subscribersMap.keySet()) {
            EdsMailMessageBuilder builder = new EdsMailMessageBuilder(messageType, language, formId, authenticatedUser, eds, getApplication()
                    .getURL().toExternalForm(), this);
            EDSdao.getInstance().sendMail(subscribersMap.get(language).toArray(new InternetAddress[subscribersMap.get(language).size()]),
                    builder.getSubject(), builder.getContent(), EDSdao.MAIL_CONTENT_HTML);
        }
    }

    /**
     * This method send Advice mail.
     * 
     * @param user EdsUser
     * @param comment Comment
     * @param eds Eds details
     * @param messageType Type of Message
     * @param create check to create
     */
    public void sendAdviceMail(EdsUser user, String comment, EdsEds eds, int messageType, Boolean create) {

        // Subscribers are treated by language
        Map<String, List<InternetAddress>> subscribersMap = new HashMap<String, List<InternetAddress>>();
        try {
            String language = null;
            if (user.getConfig().getPreferredLanguage() == null) {
                language = "fr";
            } else {
                language = user.getConfig().getPreferredLanguage();
            }

            if (!subscribersMap.containsKey(language)) {
                subscribersMap.put(language, new ArrayList<InternetAddress>());
            }
            subscribersMap.get(language).add(new InternetAddress(getUserMail(user)));
        } catch (AddressException ex) {
            Logger.getLogger(EdsApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Sending a message by language
        for (String language : subscribersMap.keySet()) {

            String basepath = getApplication().getContext().getBaseDirectory().getAbsolutePath();
            File bendeau = new File(basepath + "/VAADIN/themes/psa/icons/bandeau-" + language + ".png");
            ArrayList<File> files = new ArrayList<File>();
            files.add(bendeau);

            EdsMailAdviceBuilder builder = new EdsMailAdviceBuilder(messageType, language, authenticatedUser, comment, eds, create, this);

            EDSdao.getInstance().sendCDMail(subscribersMap.get(language).toArray(new InternetAddress[subscribersMap.get(language).size()]),
                    builder.getSubject(), builder.getContent(), EDSdao.MAIL_CONTENT_HTML, files);
        }
    }

    /**
     * This method open interface for duplicating project.
     */
    public void queryDuplicateProject() {
        DuplicateProjectWindow window = new DuplicateProjectWindow(this);
        window.setModal(true);
        getApplication().getMainWindow().addWindow(window);
        window.center();
    }

    /**
     * This method open interface for renewal of Eds form without modification.
     * 
     * @param readForm variable holds A_EdsReadForm value
     * @param editForm variable holds A_EdsEditdForm value
     */
    public void queryReconductionWithoutModif(A_EdsReadForm readForm, A_EdsEditForm editForm) {
        ReconductionWithoutModifWindow window = new ReconductionWithoutModifWindow(readForm, editForm, this);
        window.setModal(true);
        getApplication().getMainWindow().addWindow(window);
        window.center();
    }

    /**
     * This method open interface for renewal of EDS form with modification.
     * 
     * @param readForm variable holds A_EdsReadForm value
     * @param editForm variable holds A_EdsEditdForm value
     */
    public void queryReconductionWithModif(A_EdsReadForm readForm, A_EdsEditForm editForm) {
        ReconductionWithModifWindow window = new ReconductionWithModifWindow(readForm, editForm, this);
        window.setModal(true);
        getApplication().getMainWindow().addWindow(window);
        window.center();
    }

    /**
     * This method renew the EDS
     * 
     * @param eds EDS details
     * @param projects List of projects
     * @param withModif check if modify
     */
    public void reconduct(EdsEds eds, List<EdsProject> projects, boolean withModif) {
        Set<Object> itemsToSave = new LinkedHashSet<Object>();
        for (EdsProject project : projects) {
            EdsProjectEds epe = new EdsProjectEds(UUID.randomUUID().toString(), eds, project, new Date(), 1,
                    withModif ? EdsProjectEds.RECONDUCTED_WITH_MODIF : EdsProjectEds.RECONDUCTED_WITHOUT_MODIF);
            itemsToSave.add(epe);

            // Adding project-specific data in the forms concerned if extension with modification.
            if (withModif) {
                List<I_ProjectSpecificData> specificDatas = getAllSpecificFormData(eds);
                for (I_ProjectSpecificData data : specificDatas) {
                    data.addProjectReconductionData(project);
                    itemsToSave.addAll(data.getAllItemsToSave());
                }
            }
        }

        for (Object o : itemsToSave) {
            EDSdao.getInstance().mergeDetachedDBObject(o);
        }
        // Refresh the Eds
        EDSdao.getInstance().refreshDetachedDBObject(eds);

        // if ( readForm != null )
        // {
        // readForm.refreshViewData();
        // readForm.refreshItems();
        // }
        //
        // if ( editForm != null )
        // {
        // editForm.refreshItems();
        // }
        closeEds(eds);
        openEds(eds);

    }

    /**
     * This method update the affected users after changes in EDS
     * 
     * @param eds Eds details
     * @param edsUser Eds User details
     */
    public void affectUser(EdsEds eds, EdsUser edsUser) {
        Set<Object> itemsToSave = new LinkedHashSet<Object>();
        eds.setEdsUserByEdsAffectationUserId(edsUser);
        itemsToSave.add(eds);

        for (Object o : itemsToSave) {
            EDSdao.getInstance().mergeDetachedDBObject(o);
        }
        EDSdao.getInstance().refreshDetachedDBObject(eds);
        closeEds(eds);
        openEds(eds);

    }

    /**
     * This method open the interface for reconsulting EDS
     * 
     * @param eds EDS details
     */
    public void queryReconsultation(EdsEds eds) {
        ReconsultationWindow window = new ReconsultationWindow(eds, this);
        window.setModal(true);
        getApplication().getMainWindow().addWindow(window);
        window.center();
    }

    /**
     * This method open the interface for EDS assignment
     * 
     * @param eds Eds details
     */
    public void queryAffectation(EdsEds eds) {

        EdsAffectationWindow window = new EdsAffectationWindow(eds, this);
        window.setModal(true);
        getApplication().getMainWindow().addWindow(window);
        window.center();
    }

    /**
     * This method reconsult the EDS of Project
     * 
     * @param eds EDS Details
     * @param project EDS Project details
     * @param open check if open
     */
    public void reconsult(EdsEds eds, EdsProject project, boolean open) {
        Set<Object> itemsToSave = new LinkedHashSet<Object>();

        EdsEds newEds = new EdsEds(UUID.randomUUID().toString(), project, eds.getEdsComponentType(), getAuthenticatedUser(), EDSdao.getInstance()
                .generateFormRef(), EdsEds.FIRST_MAJOR_VERSION, EdsEds.FIRST_MINOR_VERSION, new Date(), new Date(), eds.getEdsName() + "-rec",
                eds.getEdsDescription(), eds.getEdsIsBttbt(), EdsEds.DEFAULT_STATE, 0);
        newEds.setEdsUserByEdsAdminId(eds.getEdsUserByEdsAdminId());
        newEds.setEdsValidLvl(eds.getEdsValidLvl());
        newEds.setEdsSubtype1(eds.getEdsSubtype1());
        newEds.setEdsSubtype2(eds.getEdsSubtype2());
        newEds.setEdsUserByEdsManagerId(eds.getEdsUserByEdsManagerId());
        newEds.setEdsUserByEdsOfficerId(eds.getEdsUserByEdsOfficerId());
        newEds.setEdsOrganFamily(eds.getEdsOrganFamily());
        for (EdsNumber96k number96k : eds.getEdsNumber96ks()) {
            newEds.getEdsNumber96ks().add(new EdsNumber96k(number96k));
        }

        // newEds.getEdsNumber96ks().addAll( eds.getEdsNumber96ks() );
        newEds.setEdsPLaunchCount(1);

        itemsToSave.add(newEds);

        // Copy reconsultables forms launcher project
        HashMap<String, Object> copiesMap = new HashMap<String, Object>();
        List<I_ReconsultableFormData> reconsultableData = getAllReconsultableFormData(eds);
        for (I_ReconsultableFormData data : reconsultableData) {
            itemsToSave.addAll(data.getReconsultableCopy(newEds, copiesMap).getAllItemsToSave());
        }

        for (Object o : itemsToSave) {
            EDSdao.getInstance().saveDetachedDBObject(o);
        }

        if (open) {
            openEds(newEds);
            getNavigationMenu().getEdsNavigatorMenu().selectEds(newEds.getEdsId());
        }

    }

    /**
     * This method open the interface for creating new EDS form.
     * 
     * @param project Eds project details
     */
    public void queryNewEds(EdsProject project) {
        NewEdsWindow newEdsWindow = new NewEdsWindow(this, project);
        newEdsWindow.setModal(true);
        getApplication().getMainWindow().addWindow(newEdsWindow);
        newEdsWindow.center();
    }

    /**
     * This method add new EDS
     * 
     * @param componentName Name of component
     * @param componentDescription Description of component
     * @param componentType EdsComponentType
     * @param project Eds project details
     * @param validationType Validation type
     * @param btTbt check if bt/tbt
     * @param affectation Affected Edsuser
     * @throws IOException
     */
    public void addNewEds(String componentName, String componentDescription, EdsComponentType componentType, EdsProject project,
            Integer validationType, Integer btTbt, EdsUser affectation) throws IOException {
        EdsEds newEds = new EdsEds(UUID.randomUUID().toString(), project, componentType, getAuthenticatedUser(), EDSdao.getInstance()
                .generateFormRef(), EdsEds.FIRST_MAJOR_VERSION, EdsEds.FIRST_MINOR_VERSION, new Date(), new Date(), componentName,
                componentDescription, btTbt, EdsEds.DEFAULT_STATE, 0);
        newEds.setEdsValidLvl(validationType);
        newEds.setEdsUserByEdsAdminId(getAuthenticatedUser());
        newEds.setEdsPLaunchCount(1);
        newEds.setEdsUserByEdsAffectationUserId(affectation);

        sendAdviceMail(affectation, null, newEds, EdsMailAdviceBuilder.EDS_AFF_NOTIFICATION, true);

        EDSdao.getInstance().saveDetachedDBObject(newEds);

        openEds(newEds);

        EdsWsSessionToken wsToken = new EdsWsSessionToken(UUID.randomUUID().toString(), getAuthenticatedUser(), "125.02.02.21", new Date(
                System.currentTimeMillis() + 600000), EdsWsSessionToken.SOURCE_DEFAULT);

        EDSdao.getInstance().saveDetachedDBObject(wsToken);
    }

    /**
     * This method opens the EDS
     * 
     * @param eds Eds details
     */
    public void openEds(EdsEds eds) {

        getUserWindow().setUserMenu();
        if (eds != null) {
            getNavigationMenu().getEdsNavigatorMenu().addEdsNode(eds);

            getNavigationMenu().getEdsNavigatorMenu().selectEds(eds.getEdsId());
            authenticatedUser.getConfig().addViewedEds(eds.getEdsId());
        }

    }

    /**
     * This method opens the interface to Freeze the EDS
     * 
     * @param eds Eds details
     */
    public void queryFreezeEds(final EdsEds eds) {
        ConfirmDialog.show(getApplication().getMainWindow(), getBundle().getString("pop-title-eds-freeze"),
                getBundle().getString("pop-message-eds-freeze"), getBundle().getString("consolid-qcf-oui"),
                getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                    public void onClose(ConfirmDialog cd) {
                        if (cd.isConfirmed()) {
                            EDSdao dao = EDSdao.getInstance();

                            EdsEds curenteds = dao.getEdsByRef(eds.getEdsRef());

                            if (curenteds.getEdsMajorVersion() != eds.getEdsMajorVersion()
                                    || curenteds.getEdsMinorVersion() != eds.getEdsMinorVersion()) {

                                closeEds(eds);
                                openEds(curenteds);
                                showWarning(getBundle().getString("eds-pop-warning-title"), getBundle().getString("eds-pop-eds-reload"));
                            } else {
                                freezeEds(eds);
                            }

                        }

                        EDSdao.getInstance().freeEdsAccess(getAuthenticatedUser(), eds.getEdsId());
                    }
                });
    }

    /**
     * This method freeze the EDS.
     * 
     * @param eds Eds details
     */
    public void freezeEds(EdsEds eds) {
        EDSdao dao = EDSdao.getInstance();

        EdsEds newVersionEds = dao.copyEdsForMinorVersion(eds, getAuthenticatedUser());

        closeEds(eds);
        openEds(newVersionEds);
        getNavigationMenu().getEdsNavigatorMenu().selectEds(newVersionEds.getEdsId());
        sendNotificationMail(GenericDataFormBuilder.ID, newVersionEds, EdsMailMessageBuilder.EDS_VERSIONNED);
    }

    /**
     * This method returns the form data model.
     * 
     * @param eds Eds details
     * @param clazz Generic class
     * @param <T> Type of class instantiated
     * @return returns null if a parameter is empty.
     */
    public static <T> T getFormDataModel(EdsEds eds, Class<T> clazz) {
        if (eds == null || clazz == null) {
            return null;
        }

        T formData = EDSdao.getInstance().getFormData(eds.getEdsId(), clazz);
        if (formData == null) {
            try {
                formData = clazz.getConstructor(String.class, EdsEds.class).newInstance(UUID.randomUUID().toString(), eds);
                EDSdao.getInstance().saveDetachedDBObject(formData);
            } catch (InstantiationException ex) {
                // Abstract class or interface
                Logger.getLogger(EdsApplicationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                // non accessible class
                Logger.getLogger(EdsApplicationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                // Wrong type of parameter
                Logger.getLogger(EdsApplicationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                // Exception raised by the manufacturer
                Logger.getLogger(EdsApplicationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(EdsApplicationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(EdsApplicationController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return formData;
    }

    /**
     * This method close the EDS.
     * 
     * @param eds Eds details
     */
    public void closeEds(EdsEds eds) {
        getNavigationMenu().getEdsNavigatorMenu().removeEdsNode(eds.getEdsId());
        getUriFragmentUtility().setFragment("home");
    }

    /**
     * Removes all {@link EdsEds} forms specific to {@link EdsProject}
     * 
     * @param eds Eds details
     * @param project Eds project details
     */
    public static void removeAllSpecificFormData(EdsEds eds, EdsProject project) {
        List<I_ProjectSpecificData> specificDatas = getAllSpecificFormData(eds);
        for (I_ProjectSpecificData specificData : specificDatas) {
            specificData.removeProjectReconductionData(project);
        }

        for (I_ProjectSpecificData specificData : specificDatas) {
            EDSdao.getInstance().mergeDetachedDBObject(specificData);
        }

    }

    /**
     * Returns a list of {@link EdsEds} forms data models specific to {@link EdsProject}.
     * 
     * @param eds Eds details
     * @return list of project specific EDS
     */
    public static List<I_ProjectSpecificData> getAllSpecificFormData(EdsEds eds) {
        List<I_ProjectSpecificData> specificDatasList = new ArrayList<I_ProjectSpecificData>();

        for (Class<?> clazz : EDSdao.getAllProjectSpecificFormDataClasses()) {
            I_ProjectSpecificData data = (I_ProjectSpecificData) EDSdao.getInstance().getFormData(eds.getEdsId(), clazz);
            if (null != data) {
                specificDatasList.add(data);
            }
        }

        return specificDatasList;
    }

    /**
     * Returns a list of {@link EdsEds} forms data models to consult again.
     * 
     * @param eds Eds details
     * @return list of EDS which need to consult again
     */
    public List<I_ReconsultableFormData> getAllReconsultableFormData(EdsEds eds) {
        List<I_ReconsultableFormData> formDataList = new ArrayList<I_ReconsultableFormData>();

        for (Class<?> clazz : EDSdao.getInstance().getAllReconsultableFormDataClasses()) {
            I_ReconsultableFormData data = (I_ReconsultableFormData) EDSdao.getInstance().getFormData(eds.getEdsId(), clazz);
            if (null != data) {
                formDataList.add(data);
            }
        }

        return formDataList;
    }

    /**
     * This method shows login window.
     */
    public void showLoginWindow() {
        getApplication().setMainWindow(getLoginWindow());
    }

    /**
     * This method is used to add authorization.
     * 
     * @param authorization authorization required for login.
     */
    public void setAuthorization(String authorization) {
        if (authorization != null) {
            this.authorization = authorization;
        }
    }

    /*
     * public void showLoginErrorWindow() { getApplication().setMainWindow( getLoginErrorWindow() ); }
     */
    /**
     * This method shows User window.
     */
    public void showUserWindow() {
        getApplication().removeWindow(getApplication().getMainWindow());
        getApplication().setMainWindow(getUserWindow());
        getFooter().removeVersionLabel();
        getUriFragmentUtility().setFragment(A_FragmentHandler.HOME_FRAGMENT_KEY);
        getApplication().getMainWindow().addListener(new Window.CloseListener() {
            public void windowClose(Window.CloseEvent e) {
                if (readForm != null) {
                    EDSdao.getInstance().freeFormAccess(getAuthenticatedUser(), readForm.getEds().getEdsId(), readForm.getID());
                    setContent(readForm);
                    readForm.refreshViewData();
                }
                // getApplication().close();
            }
        });
    }

    /**
     * This method show warning messages.
     * 
     * @param message Warning message
     */
    public void showWarning(String message) {
        application.getMainWindow().showNotification(message, Notification.TYPE_WARNING_MESSAGE);
    }

    /**
     * This method show warning messages with warning title.
     * 
     * @param title Title of warning
     * @param message Warning message
     */
    public void showWarning(String title, String message) {
        application.getMainWindow().showNotification(title, message, Notification.TYPE_WARNING_MESSAGE);
    }

    /**
     * This method shows warning with title, message and type
     * 
     * @param title Title of warning
     * @param message Warning message
     * @param Msec Type if warning message
     */
    public void showWarning(String title, String message, int Msec) {
        Notification notif = new Notification(title, message, Notification.TYPE_WARNING_MESSAGE);
        notif.setDelayMsec(Msec);
        application.getMainWindow().showNotification(notif);
    }

    /**
     * This method show error message
     * 
     * @param message Error message
     */
    public void showError(String message) {
        application.getMainWindow().showNotification(message, Notification.TYPE_ERROR_MESSAGE);
    }

    /**
     * This method shows error message with title
     * 
     * @param title Title of error
     * @param message Error message
     */
    public void showError(String title, String message) {
        application.getMainWindow().showNotification(title, message, Notification.TYPE_ERROR_MESSAGE);
    }

    /**
     * This method handle the fragment when fragment changed event occurs.
     * 
     * @param source fragment changed event variable
     */
    public void fragmentChanged(FragmentChangedEvent source) {
        try {
            fragmentManager.handleFragment(source.getUriFragmentUtility().getFragment());
        } catch (IllegalArgumentException ex) {
            getUserWindow().showError(ex.getMessage());
        }
    }

    /**
     * Static method returning list of all stages
     * 
     * @return list of stages
     */
    public static List<Integer> getAllStages() {
        return Arrays.asList(PRELIM_STAGE, ROBUST_STAGE, SOLID_STAGE, CLOSED_STAGE);
    }

    /**
     * Static method returns list of all subtypes 1
     * 
     * @return List of subtypes 1
     */
    public static List<Integer> getAllSubtypes1() {
        return Arrays.asList(ST1_BT, ST1_TBT);
    }

    /**
     * Static method returns the list of all subtypes 2
     * 
     * @return list of subtypes 2
     */
    public static List<Integer> getAllSubtypes2() {
        return Arrays.asList(ST2_LAS, ST2_GMP, ST2_AUTRE);
    }

    /**
     * This method returns the text related to selected stage.
     * 
     * @param stage Stage numeric format
     * @return text format of stage selected
     */
    public static String getStageText(int stage) {
        switch (stage) {
        case PRELIM_STAGE:
            return PRELIM_STAGE_TEXT;
        case ROBUST_STAGE:
            return ROBUST_STAGE_TEXT;
        case SOLID_STAGE:
            return SOLID_STAGE_TEXT;
        case CLOSED_STAGE:
            return CLOSED_STAGE_TEXT;
        default:
            return null;
        }
    }

    /**
     * This method returns the english text related to selected stage.
     * 
     * @param stage Stage numeric format
     * @return text format of stage selected
     */
    public static String getStageTextEn(int stage) {
        switch (stage) {
        case PRELIM_STAGE:
            return PRELIM_STAGE_TEXT_EN;
        case ROBUST_STAGE:
            return ROBUST_STAGE_TEXT_EN;
        case SOLID_STAGE:
            return SOLID_STAGE_TEXT_EN;
        case CLOSED_STAGE:
            return CLOSED_STAGE_TEXT_EN;
        default:
            return null;
        }
    }

    /**
     * This method returns the text related to selected subtype 1.
     * 
     * @param subtype Subtype 1 in numeric format
     * @return text format of subtype1 selected
     */
    public static String getSubtype1Text(int subtype) {
        switch (subtype) {
        case ST1_BT:
            return ST1_BT_TEXT;
        case ST1_TBT:
            return ST1_TBT_TEXT;
        default:
            return null;
        }
    }

    /**
     * This method returns the text related to selected subtype 2.
     * 
     * @param subtype Subtype 2 in numeric format
     * @return text format of subtype2 selected
     */
    public static String getSubtype2Text(int subtype) {
        switch (subtype) {
        case ST2_LAS:
            return ST2_LAS_TEXT;
        case ST2_GMP:
            return ST2_GMP_TEXT;
        case ST2_AUTRE:
            return ST2_AUTRE_TEXT;
        default:
            return null;
        }
    }

    /**
     * static method which Returns the partners associated with active projects of the plug.
     * 
     * @param eds Eds details
     * @param includeEdsPerimeters includes specific partners in case if true
     * @return set of Eds partners
     */
    public static Set<EdsPerimeter> getAllAuthorizedPerimeters(EdsEds eds, boolean includeEdsPerimeters) {
        Set<EdsPerimeter> authorizedPerimeters = new TreeSet<EdsPerimeter>(new Comparator<EdsPerimeter>() {
            public int compare(EdsPerimeter p1, EdsPerimeter p2) {
                return p1.getPeName().compareTo(p2.getPeName());
            }
        });
        if (eds.getEdsProject().isActive()) {
            authorizedPerimeters.addAll(eds.getEdsProject().getEdsPerimeters());
        }
        for (EdsProjectEds followingProject : (Set<EdsProjectEds>) eds.getEdsProjectEdses()) {
            if (followingProject.getEdsProject().isActive()) {
                authorizedPerimeters.addAll(followingProject.getEdsProject().getEdsPerimeters());
            }
        }
        if (includeEdsPerimeters) {
            authorizedPerimeters.addAll(eds.getEdsPerimeters());
        }
        return authorizedPerimeters;
    }

    /**
     * This method renew/reconduct the PSA supply
     * 
     * @param psaSupply EdsPsaMesureSupply object
     * @param consolidateSupply EdsConsolidateSupply object
     * @param projects set of EdsProjects
     */
    public void reconductSupply(EdsPsaMesureSupply psaSupply, EdsConsolidateSupply consolidateSupply, Set<EdsProject> projects) {
        // QCM predefined answers: no, yes, no, no, no, no -> default creation of a park table mode in
        // addition to the basic tables.

        psaSupply.getEdsPmCourantAppelleActivations().add(new EdsCourantAppelleActivation(UUID.randomUUID().toString()));
        psaSupply.getEdsPmCourantBloqueCourantDysfonctionnements().add(new EdsCourantBloqueCourantDysfonctionnement(UUID.randomUUID().toString()));
        psaSupply.getEdsPmCourantMiseSousTensions().add(new EdsCourantMiseSousTension(UUID.randomUUID().toString()));
        psaSupply.getEdsPmModeParcs().add(new EdsModeParc(UUID.randomUUID().toString()));
        if (consolidateSupply.getEdsQcf().getQcf3() == 1) {
            psaSupply.getEdsPmModeMontages().add(new EdsModeMontage(UUID.randomUUID().toString(), 0));
        }
        psaSupply.getEdsPmReseauVeilleReveilleOrganeInactifs().add(new EdsReseauVeilleReveilleOrganeInactif(UUID.randomUUID().toString()));
        psaSupply.getEdsPmCourantNominaleActivations().add(new EdsCourantNominaleActivation(UUID.randomUUID().toString()));

        EdsCourantCycle ecc = new EdsCourantCycle(UUID.randomUUID().toString());
        ecc.setEdsProjects(projects);
        ecc.setCcedsRemove(false);
        ecc.setCcedsWithModif(EdsCourantCycle.RECONDUCTED_WITHOUT_MODIF);
        psaSupply.getEdsCourantCycles().add(ecc);
        psaSupply.setEdsQcf(new EdsQcf(consolidateSupply.getEdsQcf()));
    }

    /**
     * This method helps to retrieve EDS stage
     * 
     * @param eds Eds details
     * @param project Eds project details
     * @return Stage of EDS
     */
    public int retrieveEdsStage(EdsEds eds, EdsProject project) {
        int stage = PRELIM_STAGE;

        if (eds.getEdsValidLvl() == EdsEds.VALIDATION_LVL_LOW) {
            stage = getFormDataModel(eds, EdsLowValidationFormData.class).getValidationStage();
        } else if (eds.getEdsValidLvl() == EdsEds.VALIDATION_LVL_HIGH) {
            stage = getFormDataModel(eds, EdsHighValidationFormData.class).getValidationStage();
        }

        return stage;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsApplication
     */
    private EdsApplication application;
    /**
     * Variable to hold value of LoginWindow
     */
    private LoginWindow loginWindow;

    /**
     * Variable to hold value of LoginErrorWindow
     */
    private LoginErrorWindow loginErrorWindow;
    /**
     * Variable to hold value of BusyWindow
     */
    private BusyWindow busyWindow;
    /**
     * Variable to hold value of EdsUserWindow
     */
    private EdsUserWindow userWindow;
    /**
     * Variable to hold value of PsaHeader
     */
    private EdsApplicationHeader header;
    /*
     * private PsaHeader psaHeader;
     * 
     * private B2BHeader b2BHeader;
     */
    /**
     * Variable to hold value of EdsNavigationMenu
     */
    private EdsNavigationMenu navigationMenu;
    /**
     * Variable to hold value of EdsAdminNavigationWindow
     */
    private EdsAdminNavigationMenu adminNavigationMenu;
    /**
     * Variable to hold value of EdsActionBar
     */
    private EdsActionBar actionBar;
    /**
     * Variable to hold value of EdsContent
     */
    private EdsContent content;
    /**
     * Variable to hold value of EdsFooter
     */
    private EdsFooter footer;
    /**
     * Variable to hold value of UriFragmentUtility
     */
    private UriFragmentUtility uriFragmentUtility;
    /**
     * Variable to hold value of EdsProject
     */
    private EdsProject currentProject;
    // User
    /**
     * Variable to hold value of EdsUser
     */
    private EdsUser authenticatedUser;
    /**
     * Variable to hold value of HomeView
     */
    private HomeView homeView;
    /**
     * Variable to hold value of EN_AdminNode
     */
    private EN_AdminNode userParamManagementNode;
    // URL
    /**
     * Variable to hold value of FragmentManager
     */
    private FragmentManager fragmentManager;
    // language Bundle

    /**
     * Variable to hold value of ResourceBundle
     */
    private transient ResourceBundle bundle;
    /**
     * Variable to hold value of edsConfRessourceBundle
     */
    private ResourceBundle edsConfRessourceBundle;

    /**
     * Initialize EdsApplication
     */
    private void init() {
        // Do nothing
        this.loginWindow = null;
        this.loginErrorWindow = null;
        this.userWindow = null;
        this.header = null;
        this.navigationMenu = null;
        this.actionBar = null;
        this.content = null;
        this.footer = null;
        this.uriFragmentUtility = null;
        this.authenticatedUser = null;
        this.homeView = null;
        this.fragmentManager = new FragmentManager();
        this.fragmentManager.addFragmentHandler(new FH_Null(this));
        this.fragmentManager.addFragmentHandler(new FH_Home(this));
        this.fragmentManager.addFragmentHandler(new FH_UserParams(this));
        this.fragmentManager.addFragmentHandler(new FH_Admin(this));
        this.fragmentManager.addFragmentHandler(new FH_EdsProject(this));
        this.fragmentManager.addFragmentHandler(new FH_EdsEds(this));
        this.fragmentManager.addFragmentHandler(new FH_Logout(this));
        this.edsConfRessourceBundle = EdsConfRessourceBundle.getInstance().getResourceBundle();
        this.bundle = null;

    }

    /**
     * This method checks if User can access the Eds
     * 
     * @param user Eds User details
     * @param eds Eds details
     * @return Check if User can access EDS
     */
    private boolean userCanAccessEds(EdsUser user, EdsEds eds) {
        boolean canAccess = true;
        if (user.getEdsRole().isSupplier()) {
            if (!user.getEdsSupplier().equals(eds.getEdsSupplier())) {
                canAccess = false;
            }
        } else if (user.getEdsRole().isPerimeter()) {

            canAccess = EDSdao.getInstance().isEdsIn(eds, user.getEdsPerimeter());

        }
        return canAccess;
    }

    /**
     * This method checks if User must be notified or not.
     * 
     * @param messageType Type of message
     * @param subscriber Eds user details
     * @return check if User must be notified or not
     */
    private boolean userMustBeNotified(int messageType, EdsUser subscriber) {
        boolean mustBeNotified = false;
        switch (messageType) {
        case EdsMailMessageBuilder.EDS_ABORTED:
            mustBeNotified = subscriber.getConfig().isNotifyStatus();
            break;
        case EdsMailMessageBuilder.EDS_RETRIEVED:
            mustBeNotified = subscriber.getConfig().isNotifyStatus();
            break;
        case EdsMailMessageBuilder.EDS_UPDATED:
            mustBeNotified = subscriber.getConfig().isNotifyModification();
            break;
        case EdsMailMessageBuilder.EDS_VALIDATED:
            mustBeNotified = subscriber.getConfig().isNotifyStatus();
            break;
        case EdsMailMessageBuilder.EDS_VERSIONNED:
            mustBeNotified = subscriber.getConfig().isNotifyVersionning();
            break;
        }
        return mustBeNotified;
    }

    /**
     * This method returns the name of browser and its version
     * 
     * @return Name of browser and its version
     */
    public String getBrowserAndVersion() {
        String userAgent = null;
        if (getApplication().getContext() instanceof WebApplicationContext) {
            WebBrowser webBrowser = ((WebApplicationContext) getApplication().getContext()).getBrowser();
            if (webBrowser.isFirefox()) {
                userAgent = "FireFox" + webBrowser.getBrowserMajorVersion();
            } else if (webBrowser.isChrome()) {
                userAgent = "Chrome" + webBrowser.getBrowserMajorVersion();
            } else if (webBrowser.isIE()) {
                userAgent = "IE" + webBrowser.getBrowserMajorVersion();
            }
        }
        return userAgent;
    }

    /**
     * This method returns read form.
     * 
     * @return Eds Read form
     */
    public A_EdsReadForm getReadForm() {
        return readForm;
    }

    /**
     * This method set Eds Read form
     * 
     * @param readForm Read form object
     */
    public void setReadForm(A_EdsReadForm readForm) {
        this.readForm = readForm;
    }

    /**
     * This method sets window for application which is under Maintenance mode
     */
    public void showBusyWindow() {
        getApplication().setMainWindow(getBusyWindow());
    }

    /**
     * This method return window for application which is under Maintenance mode
     * 
     * @return window for application which is under Maintenance mode
     */
    private Window getBusyWindow() {
        if (null == busyWindow) {
            busyWindow = new BusyWindow(this);
        }
        return busyWindow;
    }

    /**
     * Saving the id and password
     * 
     * @param key Key of cookie
     * @param value Value of cookie
     */
    public void addCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        getApplication().addCookie(cookie);
    }

    /**
     * This method returns the cookie value using key
     * 
     * @param key Key of cookie
     * @return Value of cookie
     */
    public String getCookieByID(String key) {
        if (getApplication().getCookies() == null) {
            return "";
        }

        Cookie[] cookies = getApplication().getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (key.equals(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return "";
    }

    /**
     * This method check if user belongs to Valid LDAP User Group
     * 
     * @param user Eds User
     * @return Check for if user belongs to Valid LDAP User Group
     */
    public boolean checkLDAPGroup(EdsUser user) {

        if (user == null || user.getUPsaId() == null) {
            throw new NullPointerException();
        }

        try {
            LDAPUser ldapUser = new LDAPUser();
            ldapUser.setUid(user.getUPsaId());

            LDAPGroup grp = new LDAPGroup();
            grp.setName("OPH.EDS_USERS");

            return ldapUser.isMember(grp);

        } catch (LDAPObjNotFoundException ex) {
            return false;
        } catch (LDAPException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * This method method returns Authorization String
     * 
     * @return Authorization String
     */
    public String getAuthorization() {
        return authorization;
    }

    /**
     * Returns isValidB2BRequest
     * 
     * @return the isValidB2BRequest
     */
    public boolean isValidB2BRequest() {
        return isValidB2BRequest;
    }

    /**
     * Sets isValidB2BRequest
     * 
     * @param isValidB2BRequest the isValidB2BRequest to set
     */
    public void setValidB2BRequest(boolean isValidB2BRequest) {
        this.isValidB2BRequest = isValidB2BRequest;
    }

    /**
     * Get the elect. database from Gestion server and handle errors. Ex_Conn_113.
     */
    public void initDbelec() {
        try {
            dbelec = GestionConnector.loadDbElecDatabase();
        } catch (GestionConnectorException e) {
            if (e.getCause() != null)
                showError(getBundle().getString("gestion-conn-error-title"),
                        MessageFormat.format(getBundle().getString(e.getMessage()), e.getCause().getLocalizedMessage()));
            else
                showError(getBundle().getString("gestion-conn-error-title"), getBundle().getString(e.getMessage()));

            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "[INFO] Could not load any component database : {0}", e.getMessage());
        }

        // Show a debug message if the load succeeded
        if (dbelec != null) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "[INFO] Loading of component database {0}",
                    ConfigurationManager.get().getString("gestion-container-id"));
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "[INFO] Could not load any component database (No error provided)");
        }
    }
}
