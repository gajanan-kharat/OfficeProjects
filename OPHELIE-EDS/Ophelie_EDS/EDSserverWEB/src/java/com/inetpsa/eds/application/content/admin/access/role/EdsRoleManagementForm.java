package com.inetpsa.eds.application.content.admin.access.role;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.popup.EditRoleWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsRole;
import com.inetpsa.eds.tools.doublelist.DoubleList;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.event.MouseEvents.DoubleClickEvent;
import com.vaadin.event.MouseEvents.DoubleClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide Profiles(role) access Management
 * 
 * @author Geometric Ltd.
 */
public class EdsRoleManagementForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsRoleManagementForm(EdsApplicationController controller) {
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
        // Check that all roles have a different name
        HashSet<String> differentNameCount = new HashSet<String>();
        Collection roles = new HashSet(vDLSTroles.getAllOptions());
        roles.addAll(vDLSTroles.getAllSelections());
        int i = 0;
        for (Object o : roles) {
            differentNameCount.add(((EdsRole) o).getRoName());
            i++;
        }
        boolean isValid = (i == differentNameCount.size());
        if (!isValid) {
            getWindow().showNotification(controller.getBundle().getString("pop-error-title"),
                    controller.getBundle().getString("Admin-user-two-role"), Notification.TYPE_ERROR_MESSAGE);
        }
        return isValid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        for (Object inactiveRole : vDLSTroles.getAllOptions()) {
            ((EdsRole) inactiveRole).setRoActive(EdsRole.INACTIVE);
        }
        for (Object activeRole : vDLSTroles.getAllSelections()) {
            ((EdsRole) activeRole).setRoActive(EdsRole.ACTIVE);
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
        rolesDeleted.clear();
        vDLSTroles.removeAllItems();

        for (EdsRole role : EDSdao.getInstance().getAllRoles(true)) {
            if (role.getRoActive() == EdsRole.ACTIVE) {
                vDLSTroles.addSelection(role);
            } else {
                vDLSTroles.addOption(role);
            }
            vDLSTroles.setItemCaption(role, role.getRoName());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        Collection itemsToSave = new HashSet(vDLSTroles.getAllOptions());
        itemsToSave.addAll(vDLSTroles.getAllSelections());
        itemsToSave.addAll(rolesDeleted);
        return itemsToSave;
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
     * Variable to hold value of DoubleList for Roles
     */
    private DoubleList vDLSTroles;
    /**
     * Variable to hold set of EdsRole
     */
    private HashSet<EdsRole> rolesDeleted;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize EdsRoleManagementForm
     */
    private void init() {
        this.rolesDeleted = new HashSet<EdsRole>();
        TabSheet vTSmain = new TabSheet();
        vTSmain.setSizeFull();

        vTSmain.addTab(getRoleLayout(), controller.getBundle().getString("Admin-acc-rol-ss-title"));

        addComponent(vTSmain);
    }

    /**
     * This method returns profiles access Management layout
     * 
     * @return Layout for profiles access Management
     */
    private Layout getRoleLayout() {
        HorizontalLayout roleLayout = new HorizontalLayout();
        roleLayout.setSpacing(true);
        roleLayout.setMargin(true);

        this.vDLSTroles = new DoubleList();
        vDLSTroles.setLeftColumnCaption(controller.getBundle().getString("Admin-acc-role-inactif"));
        vDLSTroles.setRightColumnCaption(controller.getBundle().getString("Admin-acc-role-actif"));
        vDLSTroles.setRows(20);
        vDLSTroles.setMultiSelect(true);
        vDLSTroles.setNullSelectionAllowed(true);
        vDLSTroles.setImmediate(true);
        vDLSTroles.setWidth("500px");
        roleLayout.addComponent(vDLSTroles);

        // Adding listener double click for option list -> Rename window opens
        vDLSTroles.addOptionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsRole>) vDLSTroles.getOptionValue()).isEmpty()) {
                    final EdsRole selectedRole = ((Set<EdsRole>) vDLSTroles.getOptionValue()).iterator().next();
                    EditRoleWindow window = new EditRoleWindow(selectedRole, controller, new EditRoleWindow.ValidateListener() {
                        @Override
                        public void onValidate(String newValue, int roType) {
                            selectedRole.setRoName(newValue);
                            selectedRole.setRoType(roType);
                            vDLSTroles.setItemCaption(selectedRole, newValue);
                        }
                    });
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        // Adding listener double click for selected list -> Rename window opens
        vDLSTroles.addSelectionDoubleClickListener(new DoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if (!((Set<EdsRole>) vDLSTroles.getSelectedValue()).isEmpty()) {

                    final EdsRole selectedRole = ((Set<EdsRole>) vDLSTroles.getSelectedValue()).iterator().next();
                    EditRoleWindow window = new EditRoleWindow(selectedRole, controller, new EditRoleWindow.ValidateListener() {
                        @Override
                        public void onValidate(String newValue, int roType) {
                            selectedRole.setRoName(newValue);
                            selectedRole.setRoType(roType);
                            vDLSTroles.setItemCaption(selectedRole, newValue);
                        }
                    });
                    window.setModal(true);
                    getApplication().getMainWindow().addWindow(window);
                    window.center();
                }
            }
        });

        // Button to add a role
        Button addButton = new Button("", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                EdsRole newRole = new EdsRole(UUID.randomUUID().toString(), controller.getBundle().getString("Admin-acc-role-new"), EdsRole.INACTIVE,
                        "app-general-access-eds", "form-read-generic-data", 0, Collections.EMPTY_SET);
                vDLSTroles.addOption(newRole);
                vDLSTroles.setItemCaption(newRole, newRole.getRoName());
            }
        });
        addButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png"));
        addButton.setStyleName(BaseTheme.BUTTON_LINK);
        addButton.setDescription("Ajouter un nouveau rôle");
        vDLSTroles.addButton(addButton);

        roleLayout.setExpandRatio(vDLSTroles, 1);

        return roleLayout;
    }
}
