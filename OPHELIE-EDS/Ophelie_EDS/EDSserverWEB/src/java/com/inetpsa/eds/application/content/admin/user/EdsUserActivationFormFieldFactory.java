package com.inetpsa.eds.application.content.admin.user;

import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.popup.UserActivationWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsPerimeter;
import com.inetpsa.eds.dao.model.EdsRole;
import com.inetpsa.eds.dao.model.EdsSupplier;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

/**
 * This class provide Factory field for activation of users.
 * 
 * @author Geometric Ltd.
 * @see UserActivationWindow
 */
public class EdsUserActivationFormFieldFactory extends DefaultFieldFactory {
    // PUBLIC
    /**
     * Constant to hold value of COMMON_FIELD_WIDTH
     */
    public static final String COMMON_FIELD_WIDTH = "200px";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsUserActivationFormFieldFactory(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.DefaultFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
     */
    @Override
    public Field createField(Item item, Object propertyId, Component uiContext) {
        Field f = null;

        if ("edsRole".equals(propertyId)) {
            return vCMBroles;
        } else if ("edsPerimeter".equals(propertyId)) {
            return vCMBperimeters;
        } else if ("edsSupplier".equals(propertyId)) {
            return vCMBsuppliers;
        } else {
            f = super.createField(item, propertyId, uiContext);
            f.setWidth(COMMON_FIELD_WIDTH);
        }

        if ("UPsaId".equals(propertyId)) {
            TextField tf = (TextField) f;
            tf.setCaption(controller.getBundle().getString("login.psaid") + " :");
            tf.setNullRepresentation("");
            tf.setNullSettingAllowed(false);
            tf.setRequired(true);
            tf.setRequiredError(controller.getBundle().getString("admin-user-id-error"));
            tf.setMaxLength(32);
        } else if ("ULastname".equals(propertyId)) {
            TextField tf = (TextField) f;
            tf.setCaption(controller.getBundle().getString("Admin-user-name"));
            tf.setNullRepresentation("");
            tf.setNullSettingAllowed(false);
            // tf.setRequired( true );
            // tf.setRequiredError( controller.getBundle().getString( "admin-user-lName-error" ) );
            tf.setMaxLength(128);
        } else if ("UFirstname".equals(propertyId)) {
            TextField tf = (TextField) f;
            tf.setCaption(controller.getBundle().getString("Admin-user-surname"));
            tf.setNullRepresentation("");
            tf.setNullSettingAllowed(false);
            // tf.setRequired( true );
            // tf.setRequiredError( controller.getBundle().getString( "admin-user-fName-error" ) );
            tf.setMaxLength(128);
        } else if ("UService".equals(propertyId)) {
            TextField tf = (TextField) f;
            tf.setCaption(controller.getBundle().getString("Admin-user-service"));
            tf.setNullRepresentation("");
            tf.setNullSettingAllowed(true);
            tf.setMaxLength(256);
        }

        return f;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box for roles
     */
    private ComboBox vCMBroles;
    // PRIVATE
    /**
     * Variable to hold value of combo box for suppliers
     */
    private ComboBox vCMBsuppliers;
    // PRIVATE
    /**
     * Variable to hold value of combo box for partner
     */
    private ComboBox vCMBperimeters;
    // PRIVATE
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Factory field activation of users.
     */
    private void init() {

        vCMBroles = new ComboBox(controller.getBundle().getString("Admin-user-role"));
        vCMBsuppliers = new ComboBox(controller.getBundle().getString("generic-data-represent-FNR"));
        vCMBsuppliers.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);
        vCMBperimeters = new ComboBox(controller.getBundle().getString("Admin-user-partn"));

        EDSdao dao = EDSdao.getInstance();

        List<EdsRole> rolesList = dao.getAllRoles();
        List<EdsSupplier> suppliersList = dao.getAllSuppliers();
        List<EdsPerimeter> perimetersList = dao.getAllPerimeters();

        vCMBroles.setItemCaption(null, "<" + controller.getBundle().getString("Admin-user-no-role") + ">");
        for (EdsRole role : rolesList) {
            vCMBroles.addItem(role);
            vCMBroles.setItemCaption(role, role.getRoName());

        }

        for (EdsSupplier supplier : suppliersList) {
            vCMBsuppliers.addItem(supplier);
            vCMBsuppliers.setItemCaption(supplier, supplier.getSName());
        }

        for (EdsPerimeter perimeter : perimetersList) {
            vCMBperimeters.addItem(perimeter);
            vCMBperimeters.setItemCaption(perimeter, perimeter.getPeName());
        }

        vCMBroles.setWidth(COMMON_FIELD_WIDTH);
        vCMBroles.setNullSelectionAllowed(false);
        vCMBroles.setTextInputAllowed(false);
        vCMBroles.setImmediate(true);
        vCMBsuppliers.setWidth(COMMON_FIELD_WIDTH);
        vCMBsuppliers.setNullSelectionAllowed(false);
        vCMBperimeters.setWidth(COMMON_FIELD_WIDTH);
        vCMBperimeters.setNullSelectionAllowed(false);
        vCMBperimeters.setTextInputAllowed(false);

        vCMBsuppliers.setEnabled(false);
        vCMBperimeters.setEnabled(false);

        vCMBroles.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue() == null) {
                    if (vCMBperimeters.isEnabled()) {
                        vCMBperimeters.setEnabled(false);
                        vCMBperimeters.setRequired(false);
                    }
                    if (vCMBsuppliers.isEnabled()) {
                        vCMBsuppliers.setEnabled(false);
                        vCMBsuppliers.setRequired(false);
                    }
                    return;
                }

                if (((EdsRole) event.getProperty().getValue()).isSupplier()) // provider
                {
                    vCMBsuppliers.setEnabled(true);
                    vCMBsuppliers.setRequired(true);
                    if (vCMBperimeters.isEnabled()) {
                        vCMBperimeters.setEnabled(false);
                        vCMBperimeters.setRequired(false);
                    }
                } else if (((EdsRole) event.getProperty().getValue()).isPerimeter()) // partner
                {
                    vCMBperimeters.setEnabled(true);
                    vCMBperimeters.setRequired(true);
                    if (vCMBsuppliers.isEnabled()) {
                        vCMBsuppliers.setEnabled(false);
                        vCMBsuppliers.setRequired(false);
                    }
                } else {
                    if (vCMBperimeters.isEnabled()) {
                        vCMBperimeters.setEnabled(false);
                        vCMBperimeters.setRequired(false);
                    }
                    if (vCMBsuppliers.isEnabled()) {
                        vCMBsuppliers.setEnabled(false);
                        vCMBsuppliers.setRequired(false);
                    }
                }
            }
        });
    }
}
