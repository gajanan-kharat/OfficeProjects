package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.TextField;

/**
 * This class provide Current Consumption Edit table
 * 
 * @author Geometric Ltd.
 */
public class ConsumptionTableEdit extends MyTable {
    // PUBLIC
    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     */
    public ConsumptionTableEdit(EdsApplicationController controller) {
        this.controller = controller;
        init();

    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of ORDER_DONNEE_PROPERTY_ID
     */
    public static final Object ORDER_DONNEE_PROPERTY_ID = "CSE-data";
    /**
     * Constant to hold value of ORDER_VALEUR_PROPERTY_ID
     */
    public static final Object ORDER_VALEUR_PROPERTY_ID = "current-conso-tab-gen-val";
    /**
     * Constant to hold value of ORDER_INFORMBY_PROPERTY_ID
     */
    public static final Object ORDER_INFORMBY_PROPERTY_ID = "current-conso-tab-gen-by";
    /**
     * Constant to hold value of ORDER_COMMENT_PROPERTY_ID
     */
    public static final Object ORDER_COMMENT_PROPERTY_ID = "eds-comnent";
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Current Consumption Edit table
     */
    private void init() {
        addContainerProperty(ORDER_DONNEE_PROPERTY_ID, Object.class, "", controller.getBundle().getString((String) ORDER_DONNEE_PROPERTY_ID), null,
                null);
        addContainerProperty(ORDER_VALEUR_PROPERTY_ID, TextField.class, "", controller.getBundle().getString((String) ORDER_VALEUR_PROPERTY_ID),
                null, null);
        addContainerProperty(ORDER_INFORMBY_PROPERTY_ID, Object.class, "", controller.getBundle().getString((String) ORDER_INFORMBY_PROPERTY_ID),
                null, null);
        addContainerProperty(ORDER_COMMENT_PROPERTY_ID, TextField.class, "", controller.getBundle().getString((String) ORDER_COMMENT_PROPERTY_ID),
                null, null);

    }

    /**
     * This method add items in container in the given order
     * 
     * @param container Object of Container
     * @param donnee Data
     * @param valeur Value
     * @param iformedBy Edited By
     * @param comment Notes
     */
    public void addOrderToContainer(Container container, Object donnee, TextField valeur, Object iformedBy, TextField comment) {

        Object itemId = container.addItem();
        Item item = container.getItem(itemId);
        item.getItemProperty(ORDER_DONNEE_PROPERTY_ID).setValue(donnee);
        item.getItemProperty(ORDER_VALEUR_PROPERTY_ID).setValue(valeur);
        item.getItemProperty(ORDER_INFORMBY_PROPERTY_ID).setValue(iformedBy);
        item.getItemProperty(ORDER_COMMENT_PROPERTY_ID).setValue(comment);
    }
}
