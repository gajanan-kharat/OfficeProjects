package com.inetpsa.eds.dao;

import java.util.ResourceBundle;

/**
 * @author ALTER SOLUTIONS - Rabah OULD TAHAR <rabah.ouldtahar@ext.mpsa.com>
 */
public class EdsConfRessourceBundle {
    // PUBLIC
    public static EdsConfRessourceBundle getInstance() {
        return BundleManagerHolder.INSTANCE;
    }

    public ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("global_conf");
    }

    // PRIVATE
    private void init() {
    }

    private EdsConfRessourceBundle() {
        init();
    }

    private static class BundleManagerHolder {
        private static final EdsConfRessourceBundle INSTANCE = new EdsConfRessourceBundle();
    }
}
