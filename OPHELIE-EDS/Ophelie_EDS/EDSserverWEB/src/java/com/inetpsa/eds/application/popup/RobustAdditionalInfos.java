package com.inetpsa.eds.application.popup;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * This class is used to add a power supply when 'LV or HV power supply' value is 'BT'.
 * 
 * @author Geometric Ltd.
 */
public abstract class RobustAdditionalInfos extends A_EdsWindow implements Button.ClickListener {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS Application controller object.
     */
    public RobustAdditionalInfos(EdsApplicationController controller) {

        super(controller.getBundle().getString("eds-pop-robust-infos-title"));
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Combo-box to store values for Device destination.
     */
    private ComboBox vCBAlimDist;
    /**
     * Text field to enter Voltage Name.
     */
    private TextField vTFAlimU;
    /**
     * variable to store EDS Application controller object.
     */
    private EdsApplicationController controller;
    /**
     * List of device destion.
     */
    private static final String[] ORGANE_DIST = new String[] { "HY", "VE", "HY+VE" };

    /**
     * This method is used to initialize this class.
     * <ul>
     * It performs the following operations:
     * <li>Create Robust additional info component.</li>
     * <li>Add labels, text field and combo-box to the component.</li>
     * <li>Add buttons and button listeners for validation.</li>
     * </ul>
     */
    private void init() {
        // Destination organe
        VerticalLayout mainLayout = (VerticalLayout) getContent();
        mainLayout.setMargin(true);

        GridLayout vGLAlimDest = new GridLayout(2, 3);
        vGLAlimDest.setWidth("100%");
        vGLAlimDest.setSpacing(true);

        Label vLAlimDist = new Label(controller.getBundle().getString("current-conso-list-alim-BT-dest") + " :");
        vLAlimDist.setWidth("210");
        vGLAlimDest.addComponent(vLAlimDist, 0, 0);
        vCBAlimDist = new ComboBox();
        vCBAlimDist.setWidth("200");
        for (int i = 0; i < ORGANE_DIST.length; i++) {
            vCBAlimDist.addItem(ORGANE_DIST[i]);
        }
        vGLAlimDest.addComponent(vCBAlimDist, 1, 0);
        Label vLAlimU = new Label(controller.getBundle().getString("current-conso-list-alim-BT-intitul") + " :");

        vGLAlimDest.addComponent(vLAlimU, 0, 1);

        vTFAlimU = new TextField();
        vTFAlimU.setWidth("200");
        vGLAlimDest.addComponent(vTFAlimU, 1, 1);
        Button okButton = new Button(controller.getBundle().getString("Admin-proj-validate-button"));
        okButton.addListener(this);

        vGLAlimDest.addComponent(okButton, 1, 2);
        mainLayout.addComponent(vGLAlimDest);
        mainLayout.setExpandRatio(vGLAlimDest, 1);
        mainLayout.setComponentAlignment(vGLAlimDest, Alignment.MIDDLE_CENTER);
        mainLayout.setSizeUndefined();

        setResizable(false);

    }

    /**
     * This method is used to validate if the Device destion is valid.
     * 
     * @return True if valid else false.
     */
    public boolean isValid() {
        if (vCBAlimDist.getValue() == null) {
            showNotification(controller.getBundle().getString("current-conso-rob-rec-msg"));
            return false;
        }
        return true;
    }

    /**
     * This mehod is used to add the component to the main window.
     */
    public void show() {
        this.setModal(true);
        controller.getApplication().getMainWindow().addWindow(this);
        this.center();
    }

    /**
     * This method is used to retrieve the Device destination which is selected.
     * 
     * @return selected Device destination.
     */
    public String getUcaracName() {
        return (String) vTFAlimU.getValue();
    }

    /**
     * This method is used to retrieve the Voltage name entered.
     * 
     * @return entered Voltage name.
     */
    public String getOrgane() {
        return (String) vCBAlimDist.getValue();
    }
}
