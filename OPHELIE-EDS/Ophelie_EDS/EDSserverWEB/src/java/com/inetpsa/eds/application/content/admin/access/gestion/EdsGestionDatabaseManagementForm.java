package com.inetpsa.eds.application.content.admin.access.gestion;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.gestion.GestionBrowserTreeTable;
import com.inetpsa.eds.dao.GestionConnector;
import com.inetpsa.eds.dao.GestionConnectorException;
import com.inetpsa.gestion.sync.exceptions.E_Auth;
import com.inetpsa.gestion.sync.exceptions.E_Forbidden;
import com.inetpsa.gestion.sync.exceptions.E_NoElement;
import com.inetpsa.gestion.sync.exceptions.E_NoRight;
import com.inetpsa.outils.configuration.ConfigurationManager;
import com.inetpsa.outils.exceptions.E_Configuration;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide Gestion database access Management.
 * 
 * @author Guillaume VILLEREZ - Alter Frame
 */
public class EdsGestionDatabaseManagementForm extends A_EdsEditForm {

    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsGestionDatabaseManagementForm(EdsApplicationController controller) {
        this.controller = controller;
    }

    public void prepare() {
        if (!initialized)
            init();
    }

    @Override
    public boolean isValid() {
        // Always valid
        return true;
    }

    @Override
    public boolean commitChanges() {
        if (gestionBrowser != null) {
            try {
                // Change the configuration
                ConfigurationManager.get().setValue("gestion-container-id",
                        gestionBrowser.getSelectedValue() != null ? gestionBrowser.getSelectedValue().getID() : "");

                // Save the changes
                ConfigurationManager.save();

                // Get the new db elec
                controller.initDbelec();
            } catch (E_Configuration e) {
                e.printStackTrace();
                controller.showError(e.getMessage());
            } finally {
                // Update view
                try {
                    gestionBrowser.refresh();
                } catch (E_NoRight | E_Auth | E_NoElement | E_Forbidden e) {
                    e.printStackTrace();
                    handleError(e, false);
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public void discardChanges() {
        try {
            if (gestionBrowser != null)
                gestionBrowser.refresh();
        } catch (E_NoRight | E_Auth | E_NoElement | E_Forbidden e) {
            e.printStackTrace();
            handleError(e, false);
        }
    }

    @Override
    public Collection<Object> getAllItemsToSave() {
        // Nothing to save.
        return new HashSet<Object>();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Collection getAllItemsToDelete() {
        // Nothing to delete.
        return new HashSet<Object>();
    }

    @Override
    public void refreshItems() {
        try {
            if (gestionBrowser != null)
                gestionBrowser.refresh();
        } catch (E_NoRight | E_Auth | E_NoElement | E_Forbidden e) {
            e.printStackTrace();
            handleError(e, false);
        }
    }

    // PROTECTED
    // PRIVATE

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -1224433090266553907L;

    /**
     * The EDS application controller.
     */
    private EdsApplicationController controller;

    /**
     * The Gestion browser view.
     */
    private GestionBrowserTreeTable gestionBrowser;

    private boolean initialized = false;

    /**
     * Initialize EdsRoleManagementForm
     */
    private void init() {
        if (initialized)
            return;

        initialized = true;

        TabSheet vTSmain = new TabSheet();
        vTSmain.setSizeFull();

        try {
            vTSmain.addTab(createContainerBrowser(), controller.getBundle().getString("admin-gestion-con-title"));
            addComponent(vTSmain);
        } catch (GestionConnectorException | E_NoRight | E_Auth | E_NoElement | E_Forbidden e) {
            e.printStackTrace();

            handleError(e, true);
        }
    }

    /**
     * Display the provided error.
     * 
     * @param e The error to display.
     */
    private void handleError(Exception e, boolean addLabel) {
        if (e instanceof GestionConnectorException) {
            // Show custom error
            if (e.getCause() != null) {
                // Show pop-up error
                controller.showError(controller.getBundle().getString("gestion-conn-error-title"),
                        MessageFormat.format(controller.getBundle().getString(e.getMessage()), e.getCause().getLocalizedMessage()));

                if (addLabel) // Also show a label error
                    addComponent(new Label(controller.getBundle().getString("gestion-conn-error-title") + " : "
                            + MessageFormat.format(controller.getBundle().getString(e.getMessage()), e.getCause().getLocalizedMessage())));
            } else {
                // Show pop-up error
                controller.showError(controller.getBundle().getString("gestion-conn-error-title"), controller.getBundle().getString(e.getMessage()));

                if (addLabel) // Also show a label error
                    addComponent(new Label(controller.getBundle().getString("gestion-conn-error-title") + " : "
                            + controller.getBundle().getString(e.getMessage())));
            }
        } else {
            // Generic error
            controller.showError(controller.getBundle().getString("gestion-conn-error-title"),
                    controller.getBundle().getString("gestion-conn-error-generic"));

            if (addLabel) // Also show a label error
                addComponent(new Label(controller.getBundle().getString("gestion-conn-error-title") + " : "
                        + controller.getBundle().getString(e.getMessage())));
        }
    }

    /**
     * Create the container browser panel.
     * 
     * @return The container browser panel.
     * @throws GestionConnectorException Thrown of the Gestion connector creation/retrieving failed.
     * @throws E_NoRight Thrown if the account used doesn't have sufficient credentials.
     * @throws E_Auth Thrown if an authentication error occurred.
     * @throws E_NoElement Thrown if the root container doesn't exists.
     * @throws E_Forbidden thrown if the account used doesn't have sufficient credentials.
     */
    private Panel createContainerBrowser() throws GestionConnectorException, E_NoRight, E_Auth, E_NoElement, E_Forbidden {
        // Custom type names
        Map<Integer, String> containers = new HashMap<Integer, String>();
        containers.put(1, controller.getBundle().getString("gestion-dbelec-type"));

        // Custom type icons
        Map<Integer, ThemeResource> icons = new HashMap<Integer, ThemeResource>();
        icons.put(1, new ThemeResource("icons/databaseIcon.png"));

        this.gestionBrowser = new GestionBrowserTreeTable(controller, containers, GestionConnector.getDbElecSelectedID());
        this.gestionBrowser.setIcons(icons);
        this.gestionBrowser.refresh();

        VerticalLayout vl = new VerticalLayout();

        vl.setMargin(false);
        vl.setSpacing(false);
        vl.addComponent(this.gestionBrowser);

        Panel panel = new Panel(controller.getBundle().getString("admin-gestion-con-content"));
        panel.setContent(vl);

        return panel;
    }
}
