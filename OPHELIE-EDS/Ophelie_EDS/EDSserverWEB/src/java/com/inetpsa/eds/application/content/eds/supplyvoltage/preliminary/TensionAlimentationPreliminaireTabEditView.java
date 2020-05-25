package com.inetpsa.eds.application.content.eds.supplyvoltage.preliminary;

import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

/**
 * This method is used to generate table component.
 * 
 * @author Geometric Ltd.
 */
public class TensionAlimentationPreliminaireTabEditView extends Table {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public TensionAlimentationPreliminaireTabEditView(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    /**
     * Combo-box value for 'Donnée'.
     */
    public static final Object ORDER_DONNEE_PROPERTY_ID = "Donnée";
    /**
     * Combo-box value for 'Valeur'.
     */
    public static final Object ORDER_VALEUR_PROPERTY_ID = "Valeur";
    /**
     * Combo-box value for 'Origine de la donnée'.
     */
    public static final Object ORDER_ORIGIN_PROPERTY_ID = "Origine de la donnée";
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;

    /**
     * Initialization method. This method is used to generate the table with three columns.
     */
    private void init() {
        addContainerProperty(ORDER_DONNEE_PROPERTY_ID, Object.class, "", controller.getBundle().getString("Pilote-data-tab-title-data"), null, null);
        addContainerProperty(ORDER_VALEUR_PROPERTY_ID, TextField.class, "", controller.getBundle().getString("alim-voltage-tab-title-2"), null, null);
        addContainerProperty(ORDER_ORIGIN_PROPERTY_ID, ComboBox.class, "", controller.getBundle().getString("alim-voltage-tab-title-3"), null, null);

    }

    /**
     * This method is used to add values to the table.
     * 
     * @param donnee Data column value.
     * @param valeur Value text field.
     * @param origine Data origin combo box field.
     */
    public void addOrderToContainer(Object donnee, TextField valeur, ComboBox origine) {

        Object itemId = this.addItem();
        Item item = this.getItem(itemId);
        item.getItemProperty(ORDER_DONNEE_PROPERTY_ID).setValue(donnee);
        item.getItemProperty(ORDER_VALEUR_PROPERTY_ID).setValue(valeur);
        item.getItemProperty(ORDER_ORIGIN_PROPERTY_ID).setValue(origine);
    }
}
