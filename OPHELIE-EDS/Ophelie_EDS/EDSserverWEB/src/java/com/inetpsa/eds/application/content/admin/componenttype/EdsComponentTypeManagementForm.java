package com.inetpsa.eds.application.content.admin.componenttype;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import java.util.Collection;
import java.util.Collections;

/**
 * This class provide EDS model Management
 * 
 * @author Geometric Ltd.
 */
public class EdsComponentTypeManagementForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsComponentTypeManagementForm(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of TabSheet
     */
    private TabSheet vTSmain;
    /**
     * Variable to hold value of EdsComponentTypeListEditForm
     */
    private EdsComponentTypeListEditForm listForm;
    /**
     * Variable to hold value of EdsComponentTypeRightsEditForm
     */
    private EdsComponentTypeRightsEditForm rightsForm;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize EdsComponentTypeManagement
     */
    private void init() {
        vTSmain = new TabSheet();

        // supplies label
        this.listForm = new EdsComponentTypeListEditForm(controller);
        this.rightsForm = new EdsComponentTypeRightsEditForm(controller);
        vTSmain.addTab(listForm, controller.getBundle().getString("generic-data-organe-model"));
        vTSmain.addTab(rightsForm, controller.getBundle().getString("Admin-model-form-associat"));

        addComponent(vTSmain);

        // Added listener selected tab change
        vTSmain.addListener(new TabSheet.SelectedTabChangeListener() {
            public void selectedTabChange(SelectedTabChangeEvent event) {
                if (vTSmain.getSelectedTab() == rightsForm) {
                    rightsForm.setComponentTypesList(listForm.getAllAvailableComponentTypes());
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return ((A_EdsEditForm) vTSmain.getSelectedTab()).isValid();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        return ((A_EdsEditForm) vTSmain.getSelectedTab()).commitChanges();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        ((A_EdsEditForm) vTSmain.getSelectedTab()).discardChanges();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return ((A_EdsEditForm) vTSmain.getSelectedTab()).getAllItemsToSave();
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
}
