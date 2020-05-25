package com.inetpsa.eds.application.content.userparams;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.UserConfig;
import com.vaadin.ui.TabSheet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class is used to manage the parameters required by the User form.
 * 
 * @author Geometric Ltd.
 */
public class EdsUserParamManagementForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EdsUserParamManagementForm(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        for (A_UserForm form : forms) {
            if (!form.isValid()) {
                return false;
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        for (A_UserForm form : forms) {
            form.commitChanges();
        }
        // Made in getAllItemsToSave
        // controller.getAuthenticatedUser().setConfig( config );
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        config = controller.getAuthenticatedUser().getNewConfig();

        for (A_UserForm form : forms) {
            form.setConfig(config);
            form.discardChanges();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        // Hack to save the clob (normally a hibernate session is open when this function is called)
        // The mod is performed on the user is recovered directly from the session.
        controller.setAuthenticatedUser(EDSdao.getInstance().getUserByPsaID(controller.getAuthenticatedUser().getUPsaId()));
        controller.getAuthenticatedUser().setConfig(config);
        return Collections.singleton((Object) controller.getAuthenticatedUser());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
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

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store the EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store user configuration.
     */
    private UserConfig config;
    /**
     * List of user forms.
     */
    private List<A_UserForm> forms;

    /**
     * Initialization method. This method adds the list of users to tabsheet.
     */
    private void init() {
        TabSheet vTSmain = new TabSheet();
        vTSmain.setSizeFull();
        addComponent(vTSmain);

        this.forms = new ArrayList<A_UserForm>();
        forms.add(new UserMailNotificationForm(controller.getBundle().getString("Param-title-ong"), config, controller));

        for (A_UserForm form : forms) {
            vTSmain.addTab(form, form.getName());
        }
    }
}
