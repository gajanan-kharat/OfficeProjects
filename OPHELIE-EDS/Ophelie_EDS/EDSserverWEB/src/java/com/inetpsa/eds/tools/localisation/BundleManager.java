package com.inetpsa.eds.tools.localisation;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class provide resource bundle. Helps to Manage local specific data.
 * 
 * @author Geometric Ltd.
 */
public class BundleManager {
    // PUBLIC
    /**
     * Method used to get Instance of class.
     * 
     * @return instance of BundleManager
     */
    public static BundleManager getInstance() {
        return BundleManagerHolder.INSTANCE;
    }

    /**
     * This method return the locale specific resource bundle
     * 
     * @param loc locale
     * @return Resource bundle
     */
    public ResourceBundle getResourceBundle(Locale loc) {
        return ResourceBundle.getBundle("lang", loc);
    }

    // PRIVATE
    /**
     * Initialize Resource bundle
     */
    private void init() {
    }

    /**
     * Default constructor to Initialize resource bundle
     */
    private BundleManager() {
        init();
    }

    /**
     * Static class to manage resource bundle
     * 
     * @author Geometric Ltd.
     */

    private static class BundleManagerHolder {
        /**
         * Constant Varible to hold instance of bundler manager
         */
        private static final BundleManager INSTANCE = new BundleManager();
    }
}
