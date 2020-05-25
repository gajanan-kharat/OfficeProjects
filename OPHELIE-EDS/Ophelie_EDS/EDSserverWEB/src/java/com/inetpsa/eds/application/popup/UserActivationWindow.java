package com.inetpsa.eds.application.popup;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.inetpsa.clp.LDAPUser;
import com.inetpsa.clp.exception.LDAPException;
import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.admin.user.EdsUserActivationFormFieldFactory;
import com.inetpsa.eds.application.content.admin.user.EdsUserManagementForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsUser;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Form;
import com.vaadin.ui.VerticalLayout;

/**
 * This class is used to activate a user in EDS application.
 * <ul>
 * It performs the following operations:
 * <li>Create 'Activation of a PSA user' pop-up.</li>
 * <li>Add labels and text fields to the pop-up.</li>
 * <li>Add buttons and button listeners for validation.</li>
 * </ul>
 * 
 * @author Geometric Ltd.
 * @see EdsUserManagementForm
 */
public class UserActivationWindow extends A_EdsWindow {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param parent User management form object.
     * @param controller EDS Application controller object.
     */
    public UserActivationWindow(EdsUserManagementForm parent, EdsApplicationController controller) {
        super(controller.getBundle().getString("Admin-user-activation-mess"));
        this.parent = parent;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store User management form.
     */
    private EdsUserManagementForm parent;
    /**
     * Button to activate the user.
     */
    private Button vBTactivate;
    /**
     * Variable to store user form.
     */
    private Form userForm;
    /**
     * Variable to store User details.
     */
    private EdsUser newUser;
    /**
     * EDS Application controller object.
     */
    private EdsApplicationController controller;

    /**
     * This method is used to initialize this class.
     * <ul>
     * It performs the following operations:
     * <li>Create 'Activation of a PSA user' pop-up.</li>
     * <li>Add labels and text fields to the pop-up.</li>
     * <li>Add buttons and button listeners for validation.</li>
     * </ul>
     */
    private void init() {
        this.setWidth("400px");

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        this.userForm = new Form();
        this.userForm.setFormFieldFactory(new EdsUserActivationFormFieldFactory(controller));
        this.newUser = new EdsUser(UUID.randomUUID().toString(), null, null, null, EdsUser.USER_ACTIVE);
        this.userForm.setItemDataSource(new BeanItem<EdsUser>(newUser, Arrays.asList("UPsaId", "ULastname", "UFirstname", "UService", "edsRole",
                "edsPerimeter", "edsSupplier")));

        this.vBTactivate = new Button(controller.getBundle().getString("Admin-user-button"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                try {
                    if (userForm.getField("UPsaId").getValue() == null) {
                        getWindow().showNotification(controller.getBundle().getString("pop-error-title"), userForm.getRequiredError(),
                                Notification.TYPE_ERROR_MESSAGE);
                        return;
                    }
                    String UPsaId = userForm.getField("UPsaId").getValue().toString();

                    LDAPUser ldapUser = new LDAPUser();
                    if (!ldapUser.findUserByUid(UPsaId).hasMoreElements() || UPsaId.length() < 7) {
                        showError(controller.getBundle().getString("pop-error-title"),
                                MessageFormat.format(controller.getBundle().getString("Admin-user-not-existe"), UPsaId));

                        return;
                    }

                    ldapUser.setUid(UPsaId);

                    userForm.getField("ULastname").setValue(ldapUser.getLastName());
                    userForm.getField("UFirstname").setValue(ldapUser.getFirstName());
                    userForm.getField("UService").setValue(ldapUser.getDirection());

                    if (userForm.getField("edsRole").getValue() == null) {
                        showError(controller.getBundle().getString("admin-user-role-error"));
                        return;
                    }

                    userForm.commit();

                    String psaId = newUser.getUPsaId();

                    EDSdao dao = EDSdao.getInstance();

                    EdsUser user = dao.getUserByPsaID(psaId);

                    if (user != null && user.getUActive() == EdsUser.USER_ACTIVE) {
                        showError(controller.getBundle().getString("pop-error-title"),
                                MessageFormat.format(controller.getBundle().getString("Admin-user-actif"), UPsaId));
                        return;
                    }

                    user = dao.retrievePSAUserData(psaId);
                    if (user == null) {
                        showError(controller.getBundle().getString("pop-error-title"),
                                MessageFormat.format(controller.getBundle().getString("Admin-user-not-existe"), UPsaId));
                        return;
                    }

                    parent.activateUser(newUser);
                    close();
                } catch (LDAPException ex) {
                    Logger.getLogger(UserActivationWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        vBTactivate.setImmediate(true);

        mainLayout.addComponent(userForm);
        mainLayout.addComponent(vBTactivate);
        mainLayout.setComponentAlignment(vBTactivate, Alignment.MIDDLE_RIGHT);

        this.setContent(mainLayout);

        this.setModal(true);
    }
}
