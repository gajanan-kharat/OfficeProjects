package com.inetpsa.eds;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.LoginErrorWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsUser;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

/**
 * This class is sub class of vaadin application. This class perform database migration and then initialize EDS application. Create confirm dialog
 * box. Set locale,show login window,add cookies and close the EDS application.
 * 
 * @author Geometric Ltd.
 */
public class EdsApplication extends Application implements HttpServletRequestListener {
    // Create Confirm Dialog box
    static {
        // Setting the order of buttons in dialogs
        ConfirmDialog.setFactory(new DefaultConfirmDialogFactory() {
            /*
             * (non-Javadoc)
             * 
             * @see org.vaadin.dialogs.DefaultConfirmDialogFactory#create(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
             */
            @Override
            public ConfirmDialog create(String caption, String message, String okCaption, String cancelCaption) {
                ConfirmDialog d = super.create(caption, message, okCaption, cancelCaption);

                Button ok = d.getOkButton();
                HorizontalLayout buttons = (HorizontalLayout) ok.getParent();
                buttons.removeComponent(ok);
                buttons.addComponent(ok, 1);
                buttons.setComponentAlignment(ok, Alignment.MIDDLE_RIGHT);

                return d;
            }
        });

    }

    /**
     * PUBLIC default constructor
     */
    public EdsApplication() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.Application#init()
     */
    @Override
    public void init() {
        // Set saxon as XSLT transformer.
        System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");

        setTheme("psa");
        this.controller = new EdsApplicationController(this);
        setLocaleOnly(((WebApplicationContext) getContext()).getBrowser().getLocale());
        b2bServerName = controller.getBundle().getString("b2b.server.name");
        logger.log(Level.INFO, "b2bServerName-->>" + b2bServerName);
        this.logoutFlag = false;
        String applicationUrl = super.getURL().toString();
        logger.log(Level.INFO, "Application URL:" + applicationUrl);

        logger.log(Level.INFO, "Request URL:" + url);

        isValidB2BURL();

        this.controller.setAuthorization(authorization);
        this.controller.setValidB2BRequest(isValidB2BRequest);
        this.controller.setHeaderAndLoginView();

        if (this.authorization != null) {
            logger.log(Level.INFO, "Logging in using B2B(internet) URL");
            if (isValidB2BRequest) {
                logger.log(Level.INFO, "B2B Request is valid");
                controller.showLoginWindow();
                this.logoutFlag = true;
                logger.log(Level.INFO, "window URL:" + super.getMainWindow().getURL().toString());
            } else {
                logger.log(Level.INFO, "B2B request is invalid");
                super.setMainWindow(new LoginErrorWindow());
            }
        } else {
            logger.log(Level.INFO, "Logging in using intranet URL");
            controller.showLoginWindow();
        }

    }

    /**
     * The method validates if the B2B request is valid
     */
    private void isValidB2BURL() {
        isValidB2BRequest = (this.authorization != null && isPPRF);
    }

    /*
     * (non-Javadoc) /* (non-Javadoc)
     * 
     * @see com.vaadin.Application#close()
     */
    @Override
    public void close() {
        logger.log(Level.INFO, "[SERVER] Closing...");

        EDSdao dao = EDSdao.getInstance();

        EdsUser user = dao.getUserByID(controller.getAuthenticatedUser().getUId());
        user.setUConf(controller.getAuthenticatedUser().getUConf());
        dao.updateDetachedDBObject(user);

        if (this.url != null && this.logoutFlag) {
            WebApplicationContext webCtx = (WebApplicationContext) this.getContext();
            HttpSession session = webCtx.getHttpSession();
            session.invalidate();
            this.getMainWindow().executeJavaScript("window.close()");
        } else {
            super.close();
        }
        logger.log(Level.INFO, "[SERVER] Closed!");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.Application#setLocale(java.util.Locale)
     */
    @Override
    public void setLocale(Locale locale) {
        Locale.setDefault(locale);
        super.setLocale(Locale.getDefault());
        if (this.controller != null) {
            this.controller.setLocale(locale);
        }
    }

    /**
     * This method is used to set the locale.
     */
    public void setLocaleOnly(Locale locale) {
        Locale.setDefault(locale);
        super.setLocale(Locale.getDefault());
        if (this.controller != null) {
            this.controller.setLocaleOnly(locale);
        }
    }

    /**
     * This method is called before the servlet gets destroy.
     */
    @PreDestroy
    public void unbound() {
        ((WebApplicationContext) getContext()).valueUnbound(null);
    }

    /**
     * This method is called when http servlet request starts.
     * 
     * @param request Variable to hold Http servlet request.
     * @param response Variable to hold Http servlet response.
     * @see com.vaadin.terminal.gwt.server.HttpServletRequestListener#onRequestStart(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "********************OnRequestStart*******************************");
        this.response = response;
        this.cookies = request.getCookies();
        this.url = request.getRequestURL().toString();
        logger.log(Level.INFO, "URL: " + this.url);

        try {
            InetAddress addr = InetAddress.getByName(request.getRemoteHost());

            logger.log(Level.INFO, "Host Name:--->> " + addr.getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.requestHost = request.getHeader("Host").toString();
        logger.log(Level.INFO, "Request Host:" + requestHost);
        String authorization = request.getHeader(AUTHORIZATION_HEADER_KEY);
        logger.log(Level.INFO, "Encrypted authorization: " + authorization);

        logger.log(Level.INFO, "URL---->>>>>" + getURL());
        if (getURL() != null) {
            logger.log(Level.INFO, "HOST---->>>>>" + getURL().getHost());
        }
        if (authorization != null) {
            this.authorization = authorization;
        } else {
            this.authorization = request.getHeader(PROXY_AUTHORIZATION_HEADER_KEY);
            logger.log(Level.INFO, "Encrypted proxy authorization: " + this.authorization);

        }

        String from = request.getParameter(FROM_KEY);
        logger.log(Level.INFO, "From parameter value: " + from);
        if (from != null && from.equals(PPRF)) {
            isPPRF = true;

        }
        logger.log(Level.INFO, "IsPPRF:" + isPPRF);

        logger.log(Level.INFO, "********************Header Start*******************************");
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {

            String headerName = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                // logger.log(Level.INFO,headerName + " : " + headerValue);
            }

        }
        logger.log(Level.INFO, "********************Header End*******************************");
        logger.log(Level.INFO, "********************OnRequestEnd*******************************");
    }

    /**
     * This method is called when http servlet request ends.
     * 
     * @param request Variable to hold Http servlet request.
     * @param response Variable to hold Http servlet response.
     * @see com.vaadin.terminal.gwt.server.HttpServletRequestListener#onRequestEnd(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */

    public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
    }

    /**
     * This method add cookies to response for tracking session.
     * 
     * @param cookie the variable to hold Cookie value
     */
    public void addCookie(Cookie cookie) {
        cookie.setMaxAge(3000000);
        response.addCookie(cookie);
    }

    /**
     * This method returns cookies associated with request.
     * 
     * @return array of cookies.
     */

    public Cookie[] getCookies() {
        return cookies;
    }

    // PROTECTED
    // PRIVATE
    /**
     * The static variable to initialize logger for the class.
     */
    private final static Logger logger = Logger.getLogger(EdsApplication.class.getName());
    /**
     * The variable to hold EdsApplication controller value.
     */
    private EdsApplicationController controller;
    /**
     * The variable for handling Http servlet response.
     */
    private HttpServletResponse response;
    /**
     * The array to hold cookies information.
     */
    private Cookie[] cookies;
    /**
     * This variable stores the authorization information in encoded form.
     */
    private String authorization;
    /**
     * This variable stores the current url.
     */
    private String url;
    /**
     * This variable stores the host name.
     */
    // private String host;

    /**
     * The variable determines if the request is from B2B portal.
     */
    private boolean isValidB2BRequest;

    /**
     * The variable determines if the request has pprf parameter.
     */
    private boolean isPPRF;
    /**
     * The variable to store pprf value.
     */
    private static final String PPRF = "pprf";
    /**
     * The variable to store form value.
     */
    private static final String FROM_KEY = "from";
    /**
     * The variable to store Authorization value.
     */
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";

    /**
     * The variable to store Proxy-Authorization value.
     */
    private static final String PROXY_AUTHORIZATION_HEADER_KEY = "Proxy-Authorization";

    /**
     * variable to store server name.
     */
    private static String b2bServerName;

    /**
     * variable to store value of Host in request header.
     */
    private String requestHost;

    /**
     * Variable to hold flag value for Valid B2B Request
     */
    private Boolean logoutFlag;

    /**
     * This method is used to retrieve the URL
     * 
     * @return returns the URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * The method returns if the request is from B2B.
     * 
     * @return The method returns if the request is from B2B.
     */
    public boolean isValidB2BRequest() {
        return isValidB2BRequest;
    }

}
