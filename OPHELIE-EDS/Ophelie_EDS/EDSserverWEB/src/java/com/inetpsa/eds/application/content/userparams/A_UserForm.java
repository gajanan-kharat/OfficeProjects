package com.inetpsa.eds.application.content.userparams;

import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.UserConfig;
import java.util.Collection;
import java.util.Collections;

/**
 * This class represents the User form on the UI.
 * 
 * @author Geometric Ltd.
 */
public abstract class A_UserForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param name User name.
     * @param config User configuration.
     */
    public A_UserForm(String name, UserConfig config) {
        this.name = name;
        this.config = config;
        init();
    }

    /**
     * This method returns the user configuration detials.
     * 
     * @return user configuration details.
     */
    public UserConfig getConfig() {
        return config;
    }

    /**
     * This method is used to set the user configuration details.
     * 
     * @param config User config to be set.
     */
    public void setConfig(UserConfig config) {
        this.config = config;
    }

    /**
     * This method is used to retrieve the name.
     * 
     * @return name of the user.
     */
    public String getName() {
        return name;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store the user configuration.
     */
    private UserConfig config;
    /**
     * Variable to store the user name.
     */
    private String name;

    /**
     * Initialization method.
     */
    private void init() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
        return Collections.EMPTY_LIST;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
    }
}
