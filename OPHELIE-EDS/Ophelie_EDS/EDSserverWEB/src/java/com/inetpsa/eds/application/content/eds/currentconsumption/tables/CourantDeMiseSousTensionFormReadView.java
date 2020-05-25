package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.vaadin.data.Item;

/**
 * This class provide Read form view for Powered on current
 * 
 * @author Geometric Ltd.
 */
public class CourantDeMiseSousTensionFormReadView extends MyTable {
    // PUBLIC
    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     */
    public CourantDeMiseSousTensionFormReadView(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of ORDER_DONNEE_PROPERTY_ID
     */
    public static final Object ORDER_DONNEE_PROPERTY_ID = "";
    /**
     * Constant to hold value of ORDER_TENSION_PROPERTY_ID
     */
    public static final Object ORDER_TENSION_PROPERTY_ID = "table-header-label-tension";
    /**
     * Constant to hold value of ORDER_TPIRECAS_PROPERTY_ID
     */
    public static final Object ORDER_TPIRECAS_PROPERTY_ID = "current-conso-tab-data-TPireCas";
    /**
     * Constant to hold value of ORDER_COMMENT_PROPERTY_ID
     */
    public static final Object ORDER_COMMENT_PROPERTY_ID = "eds-comnent";
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Read form view for Powered on current
     */
    private void init() {
        setWidth("100%");
        addContainerProperty(ORDER_DONNEE_PROPERTY_ID, String.class, "", "", null, null);
        addContainerProperty(ORDER_TENSION_PROPERTY_ID, String.class, "", controller.getBundle().getString((String) ORDER_TENSION_PROPERTY_ID), null,
                null);
        addContainerProperty(ORDER_TPIRECAS_PROPERTY_ID, String.class, "", controller.getBundle().getString((String) ORDER_TPIRECAS_PROPERTY_ID),
                null, null);
        addContainerProperty(ORDER_COMMENT_PROPERTY_ID, String.class, "", controller.getBundle().getString((String) ORDER_COMMENT_PROPERTY_ID), null,
                null);

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
     * @param donnee Data
     * @param tension Tension
     * @param tPircas Tworstcase
     * @param comment Notes
     */
    public void addOrderToContainer(String donnee, String tension, Float tPircas, String comment) {

        Object itemId = this.addItem();
        Item item = this.getItem(itemId);
        item.getItemProperty(ORDER_DONNEE_PROPERTY_ID).setValue(donnee);
        item.getItemProperty(ORDER_TENSION_PROPERTY_ID).setValue(tension);
        item.getItemProperty(ORDER_TPIRECAS_PROPERTY_ID).setValue(tPircas);
        item.getItemProperty(ORDER_COMMENT_PROPERTY_ID).setValue(comment);
    }
}
