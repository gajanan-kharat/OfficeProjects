package com.inetpsa.eds.application.content.admin.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.apache.commons.collections.set.ListOrderedSet;
import org.vaadin.dialogs.ConfirmDialog;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.popup.UserActivationWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide user management form
 * 
 * @author Geometric Ltd.
 */
public class EdsUserManagementForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsUserManagementForm(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * This mehod refreshes users
     */
    public void refreshUsers() {
        users.clear();
        EDSdao dao = EDSdao.getInstance();

        users.addAll(dao.getAllUsers());

        vLSTuserList.removeAllItems();
        for (Object object : users) {
            EdsUser user = (EdsUser) object;
            vLSTuserList.addItem(user);
            vLSTuserList.setItemCaption(user, user.toPSAIdentity());
        }
    }

    /**
     * This method activate the given EdsUser
     * 
     * @param user EdsUser to be activated
     */
    public void activateUser(EdsUser user) {
        EDSdao dao = EDSdao.getInstance();

        dao.createUser(user);

        users.add(user);
        vLSTuserList.addItem(user);
        vLSTuserList.setItemCaption(user, user.toPSAIdentity());
        vLSTuserList.select(user);
    }

    /**
     * This method delete the selected user
     */
    public void deleteSelectedUser() {
        EdsUser user = (EdsUser) vLSTuserList.getValue();

        EDSdao dao = EDSdao.getInstance();

        dao.deleteUserById(user.getUId());

        vLSTuserList.removeItem(user);
        users.remove(user);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        for (Form form : editForms.values()) {
            if (!form.isValid()) {
                getApplication().getMainWindow().showNotification(controller.getBundle().getString("pop-error-title"), form.getRequiredError(),
                        Notification.TYPE_ERROR_MESSAGE);
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
        for (Form form : editForms.values()) {
            form.commit();
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
        editForms.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
        return editForms.keySet();
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
     * Constant to hold value of ACTIVATE_ICON
     */
    private static final Resource ACTIVATE_ICON = ResourceManager.getInstance().getThemeIconResource("icons/activate.png");
    /**
     * Constant to hold value of DELETE_ICON
     */
    private static final Resource DELETE_ICON = ResourceManager.getInstance().getThemeIconResource("icons/delete2.png");
    /**
     * Variable to hold value of ListSelect for users
     */
    private ListSelect vLSTuserList;
    /**
     * Variable to hold value of map of EdsUser and Form
     */
    private HashMap<EdsUser, Form> editForms;
    /**
     * Variable to hold value of Button to active User
     */
    private Button vBTactivateUser;
    /**
     * Variable to hold value of button to delete users
     */
    private Button vBTdeleteUser;
    // private Button vBTvalidateUser;
    /**
     * Variable to hold value of ListOrderedSet of users
     */
    private ListOrderedSet users;
    /**
     * Variable to hold value of HorizontalLayout
     */
    private HorizontalLayout mainLayout;
    /**
     * Variable to hold value of combo box for transfer from
     */
    private ComboBox vCMBshiftFromValue;
    /**
     * Variable to hold value of combo box for transfer to
     */
    private ComboBox vCMBshiftToValue;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize User management form
     */
    private void init() {
        this.users = new ListOrderedSet();
        this.editForms = new HashMap<EdsUser, Form>();

        TabSheet vTSmain = new TabSheet();
        vTSmain.setSizeFull();

        vTSmain.addTab(createUserManagementLayout(), controller.getBundle().getString("Admin-user-title"));

        vTSmain.addTab(createOwnershipShiftLayout(), controller.getBundle().getString("Admin-user-transf-title"));

        addComponent(vTSmain);
    }

    /**
     * This method provide layout for users management
     * 
     * @return Layout for users management
     */
    private HorizontalLayout createUserManagementLayout() {
        this.mainLayout = new HorizontalLayout();
        this.mainLayout.setSpacing(true);
        this.mainLayout.setMargin(true);
        this.mainLayout.setHeight("100%");

        this.vLSTuserList = new ListSelect(controller.getBundle().getString("Admin-user-act"));
        this.vLSTuserList.setNullSelectionAllowed(false);
        this.vLSTuserList.setRows(16);
        this.refreshUsers();
        this.vLSTuserList.setImmediate(true);

        VerticalLayout buttonsLayout = new VerticalLayout();
        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);

        vBTactivateUser = new Button(null, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                UserActivationWindow subWindow = new UserActivationWindow(EdsUserManagementForm.this, controller);
                getApplication().getMainWindow().addWindow(subWindow);
                subWindow.center();
            }
        });
        vBTactivateUser.setIcon(ACTIVATE_ICON);
        vBTactivateUser.setStyleName(BaseTheme.BUTTON_LINK);
        vBTactivateUser.setDescription(controller.getBundle().getString("Admin-user-activ-tt"));

        vBTdeleteUser = new Button(null, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("pop-confirm-delete"), controller.getBundle()
                        .getString("pop-confirm-delete-msg"), controller.getBundle().getString("consolid-qcf-oui"),
                        controller.getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                            public void onClose(ConfirmDialog cd) {
                                if (cd.isConfirmed()) {
                                    deleteSelectedUser();
                                }
                            }
                        });
            }
        });
        vBTdeleteUser.setIcon(DELETE_ICON);
        vBTdeleteUser.setStyleName(BaseTheme.BUTTON_LINK);
        vBTdeleteUser.setDescription(controller.getBundle().getString("Admin-user-suppr-tt"));

        buttonsLayout.addComponent(vBTactivateUser);
        buttonsLayout.addComponent(vBTdeleteUser);
        vBTdeleteUser.setVisible(false);

        mainLayout.addComponent(this.vLSTuserList);
        mainLayout.addComponent(buttonsLayout);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);

        vLSTuserList.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (mainLayout.getComponentCount() > 2) {
                    mainLayout.removeComponent(mainLayout.getComponent(2));
                }
                EdsUser selectedUser = (EdsUser) vLSTuserList.getValue();
                if (selectedUser != null) {
                    mainLayout.addComponent(getUserForm(selectedUser));
                }
            }
        });
        return mainLayout;
    }

    /**
     * This method provide layout for owner transfer
     * 
     * @return Layout for owner transfer
     */
    private Layout createOwnershipShiftLayout() {
        VerticalLayout mainOwnershipShiftLayout = new VerticalLayout();
        mainOwnershipShiftLayout.setSpacing(true);
        mainOwnershipShiftLayout.setMargin(true);
        mainOwnershipShiftLayout.setSizeFull();

        vCMBshiftFromValue = new ComboBox(controller.getBundle().getString("Admin-user-on"));
        for (Object object : users) {
            EdsUser user = (EdsUser) object;
            vCMBshiftFromValue.addItem(user);
            vCMBshiftFromValue.setItemCaption(user, user.toPSAIdentity());
        }
        vCMBshiftFromValue.setNullSelectionAllowed(false);

        vCMBshiftToValue = new ComboBox(controller.getBundle().getString("Admin-user-to"));
        for (Object object : users) {
            EdsUser user = (EdsUser) object;
            vCMBshiftToValue.addItem(user);
            vCMBshiftToValue.setItemCaption(user, user.toPSAIdentity());
        }
        vCMBshiftToValue.setNullSelectionAllowed(false);

        Button vBTshiftOwnership = new Button(controller.getBundle().getString("Admin-user-button-transf"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (vCMBshiftFromValue.getValue() == null || vCMBshiftToValue.getValue() == null) {
                    getApplication().getMainWindow().showNotification(controller.getBundle().getString("pop-error-title"),
                            controller.getBundle().getString("admin-user-warning-2"), Notification.TYPE_ERROR_MESSAGE);
                    return;
                }
                if (vCMBshiftFromValue.getValue().equals(vCMBshiftToValue.getValue())) {
                    getApplication().getMainWindow().showNotification(controller.getBundle().getString("pop-error-title"),
                            controller.getBundle().getString("admin-user-warning-1"), Notification.TYPE_ERROR_MESSAGE);
                    return;
                }

                ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("pop-warning-title"), controller.getBundle()
                        .getString("admin-user-transfert-warning")
                        + ((EdsUser) vCMBshiftFromValue.getValue()).toFullIdentity()
                        + controller.getBundle().getString("message-to")
                        + ((EdsUser) vCMBshiftToValue.getValue()).toFullIdentity()
                        + ".\n\n"
                        + controller.getBundle().getString("pop-confirm-ask"), controller.getBundle().getString("consolid-qcf-oui"), controller
                        .getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                    public void onClose(ConfirmDialog cd) {
                        EDSdao dao = EDSdao.getInstance();

                        dao.shiftOwnership((EdsUser) vCMBshiftFromValue.getValue(), (EdsUser) vCMBshiftToValue.getValue());

                    }
                });
            }
        });
        vBTactivateUser.setDescription(controller.getBundle().getString("Admin-user-activ-tt"));

        mainOwnershipShiftLayout.addComponent(vCMBshiftFromValue);
        mainOwnershipShiftLayout.addComponent(vCMBshiftToValue);
        mainOwnershipShiftLayout.addComponent(vBTshiftOwnership);

        return mainOwnershipShiftLayout;
    }

    /**
     * This method returns User form
     * 
     * @param userToEdit EdsUSer to be edited
     * @return Form
     */
    private Form getUserForm(EdsUser userToEdit) {
        Form form = null;
        if (userToEdit != null) {
            form = editForms.get(userToEdit);
            if (form == null) {
                form = new Form();
                form.setCaption(controller.getBundle().getString("Admin-user-info"));
                form.setWriteThrough(false);
                form.setInvalidCommitted(false);
                form.setFormFieldFactory(new EdsUserEditionFormFieldFactory(userToEdit, controller));
                form.setImmediate(true);
                form.setWidth("100%");
                BeanItem<EdsUser> item = new BeanItem(userToEdit, Arrays.asList("edsRole", "edsPerimeter", "edsSupplier", "UPsaId", "ULastname",
                        "UFirstname", "UService"));
                form.setItemDataSource(item);
                editForms.put(userToEdit, form);
            }
        }
        return form;
    }
}
