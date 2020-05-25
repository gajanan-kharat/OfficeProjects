package com.inetpsa.eds.application.popup;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.inetpsa.dbelec.model.componentname.ComponentName;
import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsComponentType;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsUser;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * The class is used to create a pop-up window to 'Create New EDS'.
 * <ul>
 * It performs the following operations:
 * <li>Create 'Create New EDS' pop-up.</li>
 * <li>Add labels, combo box and text fields.</li>
 * <li>Add validate button and button listeners for validation.</li>
 * </ul>
 * 
 * @author Geometric Ltd.
 */
public class NewEdsWindow extends A_EdsWindow {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS Application controller object.
     * @param project Project details for which EDS to be created.
     */
    public NewEdsWindow(EdsApplicationController controller, EdsProject project) {
        super(controller.getBundle().getString("button-create-tt"));
        this.controller = controller;
        this.project = project;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store EDS Application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store project details for which EDS to be created.
     */
    private EdsProject project;

    /**
     * This method is used to initialize this class.
     * <ul>
     * It performs the following operations:
     * <li>Create 'Create New EDS' pop-up.</li>
     * <li>Add labels, combo box and text fields.</li>
     * <li>Add validate button and button listeners for validation.</li>
     * </ul>
     */
    private void init() {
        VerticalLayout mainLayout = (VerticalLayout) getContent();
        mainLayout.setMargin(true);

        GridLayout contentLayout = new GridLayout(2, 7);
        contentLayout.setSpacing(true);

        // Ex_Conn_94 - Name field
        final ComboBox vCMBcomponentNameValue = new ComboBox();
        vCMBcomponentNameValue.setRequired(true);
        vCMBcomponentNameValue.setRequiredError(controller.getBundle().getString("eds-pop-new-error6"));

        vCMBcomponentNameValue.addItem("-"); // Empty choice
        if (controller.getDbelec() != null) {
            for (ComponentName name : controller.getDbelec().getComponentsName().getAll()) {
                vCMBcomponentNameValue.addItem(name.getID_2());
                vCMBcomponentNameValue.setItemCaption(name.getID_2(), name.getID_2());
            }
        }

        contentLayout.addComponent(new Label(controller.getBundle().getString("filter-name") + " :"));
        contentLayout.addComponent(vCMBcomponentNameValue, 1, 0);

        // Ex_Conn_94 - Description field
        final TextField vTFcomponentDescriptionValue = new TextField();
        vTFcomponentDescriptionValue.setNullRepresentation("");
        vTFcomponentDescriptionValue.setNullSettingAllowed(true);
        contentLayout.addComponent(new Label(controller.getBundle().getString("filter-description") + " :"));
        contentLayout.addComponent(vTFcomponentDescriptionValue, 1, 1);
        // Ex_Conn_94 end

        final ComboBox vCMBcomponentTypeValue = new ComboBox();
        vCMBcomponentTypeValue.setRequired(true);
        vCMBcomponentTypeValue.setRequiredError(controller.getBundle().getString("eds-pop-new-error2"));

        for (EdsComponentType componentType : EDSdao.getInstance().getAllComponentTypes()) {
            vCMBcomponentTypeValue.addItem(componentType);
            vCMBcomponentTypeValue.setItemCaption(componentType, componentType.getLocaleCtName(controller.getApplication().getLocale()));
        }

        contentLayout.addComponent(new Label(controller.getBundle().getString("menu-project-tab-model") + " :"));
        contentLayout.addComponent(vCMBcomponentTypeValue, 1, 2);

        final ComboBox vCBvalidationLevelValue = new ComboBox();
        vCBvalidationLevelValue.setRequired(true);
        vCBvalidationLevelValue.setRequiredError(controller.getBundle().getString("eds-pop-new-error3"));
        vCBvalidationLevelValue.addItem(EdsEds.VALIDATION_LVL_HIGH);
        vCBvalidationLevelValue.setItemCaption(EdsEds.VALIDATION_LVL_HIGH,
                controller.getBundle().getString(EdsEds.getValidationLevelText(EdsEds.VALIDATION_LVL_HIGH)));
        vCBvalidationLevelValue.addItem(EdsEds.VALIDATION_LVL_LOW);
        vCBvalidationLevelValue.setItemCaption(EdsEds.VALIDATION_LVL_LOW,
                controller.getBundle().getString(EdsEds.getValidationLevelText(EdsEds.VALIDATION_LVL_LOW)));

        contentLayout.addComponent(new Label(controller.getBundle().getString("generic-data-organe-level") + " :"));
        contentLayout.addComponent(vCBvalidationLevelValue, 1, 3);

        final ComboBox vCBtTbt = new ComboBox();
        vCBtTbt.setRequired(true);
        vCBtTbt.setRequiredError(controller.getBundle().getString("eds-pop-new-error4"));
        contentLayout.addComponent(new Label(controller.getBundle().getString("eds-pop-new-reseau-bt-tbt") + " :"));
        vCBtTbt.addItem(EdsEds.IS_BTTBT);
        vCBtTbt.setItemCaption(EdsEds.IS_BTTBT, EdsEds.getBtTbtText(EdsEds.IS_BTTBT));
        vCBtTbt.addItem(EdsEds.IS_NOT_BTTBT);
        vCBtTbt.setItemCaption(EdsEds.IS_NOT_BTTBT, EdsEds.getBtTbtText(EdsEds.IS_NOT_BTTBT));
        contentLayout.addComponent(vCBtTbt, 1, 4);
        /*------------------*/
        final ComboBox vCMBAffectation = new ComboBox();
        vCMBAffectation.setRequired(true);
        vCMBAffectation.setRequiredError(controller.getBundle().getString("eds-pop-new-error5"));
        vCMBAffectation.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);

        for (EdsUser user : EDSdao.getInstance().getAllUsers()) {
            vCMBAffectation.addItem(user);
            vCMBAffectation.setItemCaption(user, user.toFullIdentity());
        }

        vCMBAffectation.setValue(controller.getAuthenticatedUser());
        contentLayout.addComponent(new Label(controller.getBundle().getString("button-affectation") + " :"));
        contentLayout.addComponent(vCMBAffectation, 1, 5);
        /**
         * *********************
         */
        Button okButton = new Button(controller.getBundle().getString("Admin-proj-validate-button"));
        okButton.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (isValid()) {
                    try {
                        controller.addNewEds((String) vCMBcomponentNameValue.getValue(), (String) vTFcomponentDescriptionValue.getValue(),
                                (EdsComponentType) vCMBcomponentTypeValue.getValue(), project, (Integer) vCBvalidationLevelValue.getValue(),
                                (Integer) vCBtTbt.getValue(), (EdsUser) vCMBAffectation.getValue());

                    } catch (IOException ex) {
                        Logger.getLogger(NewEdsWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    close();
                } else {
                    showNotification(controller.getBundle().getString("eds-fields-error"));
                }
            }

            private boolean isValid() {
                boolean isValid = true;
                if (!vCMBcomponentTypeValue.isValid() || !vCBvalidationLevelValue.isValid() || !vTFcomponentDescriptionValue.isValid()
                        || !vCMBcomponentNameValue.isValid() || !vCBtTbt.isValid() || !vCMBAffectation.isValid()) {
                    isValid = false;
                }
                return isValid;
            }
        });
        contentLayout.addComponent(okButton, 1, 6);
        contentLayout.setComponentAlignment(okButton, Alignment.TOP_RIGHT);

        mainLayout.addComponent(contentLayout);
        mainLayout.setExpandRatio(contentLayout, 1);
        mainLayout.setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setSizeUndefined();

        setResizable(false);
    }
}
