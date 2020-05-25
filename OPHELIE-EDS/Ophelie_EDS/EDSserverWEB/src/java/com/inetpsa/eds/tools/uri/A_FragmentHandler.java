package com.inetpsa.eds.tools.uri;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class that defines a handler for URI fragment. It allows you to match a fragment to a view (page) associated. The fragment of a URI as
 * defined by Vaadin is the part located after the # symbol (anchor of a HTML page).
 * 
 * @author Geometric Ltd.
 * @see FragmentManager
 */
public abstract class A_FragmentHandler implements Serializable {
    /**
     * Constant Variable for admin fragment key
     */
    public static final String ADMIN_FRAGMENT_KEY = "admin";
    /**
     * Constant Variable for home fragment key
     */
    public static final String HOME_FRAGMENT_KEY = "home";
    /**
     * Constant Variable for Eds fragment key
     */
    public static final String EDS_FRAGMENT_KEY = "eds";
    /**
     * Constant Variable for project fragment key
     */
    public static final String PROJECT_FRAGMENT_KEY = "project";
    /**
     * Constant Variable for user param fragment key
     */
    public static final String USER_PARAMS_FRAGMENT_KEY = "user-params";
    /**
     * Constant Variable for Logout fragment key
     */
    public static final String LOGOUT_FRAGMENT_KEY = "logout";

    /**
     * Default constructor
     */
    public A_FragmentHandler() {
        this(null);
    }

    /**
     * Parameterized constructor
     * 
     * @param key Type of key
     */
    public A_FragmentHandler(String key) {
        this.key = key;
        init();
    }

    /**
     * This method returns type of key
     * 
     * @return type of key
     */
    public String getKey() {
        return key;
    }

    /**
     * This method set the key
     * 
     * @param key type of key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * This method parse the fragment of URI after # key.
     * 
     * @param fragmentResidue Fragment of URI
     * @return Map of fragment key and its value
     * @throws IllegalArgumentException Invalid argument
     */
    public HashMap<String, String> parseFragmentResidue(String fragmentResidue) throws IllegalArgumentException {
        HashMap<String, String> parameters = new HashMap<String, String>();
        StringTokenizer tokenizer = new StringTokenizer(fragmentResidue, "&", false);

        while (tokenizer.hasMoreTokens()) {
            String[] parameter = tokenizer.nextToken().split("=");
            if (parameter.length == 1) {
                parameters.put(parameter[0], null);
            } else if (parameter.length == 2) {
                try {
                    parameters.put(parameter[0], URLDecoder.decode(parameter[1], "UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(A_FragmentHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return parameters;
    }

    /**
     * This method works on URI parameters and show the key related pages.
     * 
     * @param parameters Map of parameters key and value
     */
    public abstract void handleWithParameters(HashMap<String, String> parameters);

    // PROTECTED
    /**
     * Variable to hold key value
     */
    protected String key;

    // PRIVATE

    /**
     * Initialize fragment handler
     */
    private void init() {
    }
}
