package com.inetpsa.eds.application.content.admin.right.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsRole;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TabSheet;

/**
 * This class provide application right management form
 * 
 * @author Geometric Ltd.
 */
public class EdsApplicationRightManagementForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsApplicationRightManagementForm(EdsApplicationController controller) {
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
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        for (EdsApplicationRightAffectationEditForm form : editFormsMap.values()) {
            form.commitChanges();
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        EdsRole selectedRole = (EdsRole) vLSTroles.getValue();
        vLSTroles.removeListener(listener);
        vLSTroles.removeAllItems();
        editFormsMap.clear();

        EDSdao dao = EDSdao.getInstance();

        List<EdsRole> roles = dao.getAllRoles();

        for (EdsRole role : roles) {
            vLSTroles.addItem(role);
            vLSTroles.setItemCaption(role, role.getRoName());
        }
        vLSTroles.addListener(listener);

        for (EdsApplicationRightAffectationEditForm form : editFormsMap.values()) {
            form.discardChanges();
        }
        vLSTroles.select(selectedRole);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return new ArrayList<Object>(editFormsMap.keySet());
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
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of ListSelect for roles
     */
    private ListSelect vLSTroles;
    /**
     * Variable to hold map of EdsRole and EdsApplicationRightAffectationEditForm
     */
    private HashMap<EdsRole, EdsApplicationRightAffectationEditForm> editFormsMap;
    /**
     * Variable to hold Listener for role value changes
     */
    private ValueChangeListener listener;
    /**
     * Variable to hold valu of Eds Role
     */
    private EdsRole currentRole;

    /**
     * Initialize application right management form
     */
    private void init() {
        this.editFormsMap = new HashMap<EdsRole, EdsApplicationRightAffectationEditForm>();
        TabSheet vTSmain = new TabSheet();
        vTSmain.setSizeFull();

        vTSmain.addTab(getContentLayout(), controller.getBundle().getString("Admin-droit-title-2"));

        addComponent(vTSmain);
    }

    /**
     * This method provide layout for application right management form
     * 
     * @return Layout for application right management form
     */
    private Layout getContentLayout() {
        final HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setSpacing(true);
        contentLayout.setMargin(true);

        this.vLSTroles = new ListSelect(controller.getBundle().getString("Admin-acc-role-name"));
        vLSTroles.setRows(20);
        vLSTroles.setNullSelectionAllowed(false);
        vLSTroles.setImmediate(true);
        vLSTroles.setWidth("300px");

        this.listener = new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                EdsRole selectedRole = (EdsRole) vLSTroles.getValue();
                if (selectedRole != null) {
                    if (currentRole != null) {
                        contentLayout.removeComponent(contentLayout.getComponent(1));
                    }
                    currentRole = selectedRole;
                    contentLayout.addComponent(getRoleForm(currentRole));
                }
            }
        };

        contentLayout.addComponent(vLSTroles);

        return contentLayout;
    }

    /**
     * This method returns layout for specified Eds role
     * 
     * @param role Eds Role Details
     * @return Layout for Eds Role selected
     */
    private Layout getRoleForm(EdsRole role) {
        Layout form = null;
        form = editFormsMap.get(role);
        if (form == null) {
            form = new EdsApplicationRightAffectationEditForm(role, controller);
            editFormsMap.put(role, (EdsApplicationRightAffectationEditForm) form);
            ((EdsApplicationRightAffectationEditForm) form).discardChanges();
        }
        return form;
    }
}
