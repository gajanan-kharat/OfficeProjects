package com.inetpsa.eds.application.content.eds.currentconsumption.robust;

import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;

/**
 * This class provide Robust Table
 * 
 * @author Geometric Ltd.
 */
public class RobusteTable extends Table {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of Eds application
     */
    public RobusteTable(EdsApplicationController controller) {
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
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Robust table
     */
    private void init() {
        setWidth("100%");
        addContainerProperty(controller.getBundle().getString((String) ORDER_DONNEE_PROPERTY_ID), String.class, "");
        addContainerProperty(controller.getBundle().getString((String) ORDER_VALEUR_PROPERTY_ID), String.class, "");
        addContainerProperty(controller.getBundle().getString((String) ORDER_INFORMBY_PROPERTY_ID), String.class, "");
        addContainerProperty(controller.getBundle().getString((String) ORDER_COMMENT_PROPERTY_ID), String.class, "");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Table#setPageLength(int)
     */
    @Override
    public void setPageLength(int pageLength) {
        super.setPageLength(pageLength);
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
    public void addOrderToContainer(Container container, String donnee, String valeur, String iformedBy, String comment) {

        Object itemId = container.addItem();
        Item item = container.getItem(itemId);
        item.getItemProperty(controller.getBundle().getString((String) ORDER_DONNEE_PROPERTY_ID)).setValue(donnee);
        item.getItemProperty(controller.getBundle().getString((String) ORDER_VALEUR_PROPERTY_ID)).setValue(valeur);
        item.getItemProperty(controller.getBundle().getString((String) ORDER_INFORMBY_PROPERTY_ID)).setValue(iformedBy);
        item.getItemProperty(controller.getBundle().getString((String) ORDER_COMMENT_PROPERTY_ID)).setValue(comment);
    }
}
