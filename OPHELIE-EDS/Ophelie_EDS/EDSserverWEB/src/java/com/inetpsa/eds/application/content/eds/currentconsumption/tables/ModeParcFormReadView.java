package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.vaadin.data.Item;

/**
 * This class provide Read form view for Park Mode
 * 
 * @author Geometric Ltd.
 */
public class ModeParcFormReadView extends MyTable {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param qcf Object of EdsQcf
     * @param controller Controller of EDS application
     */
    public ModeParcFormReadView(EdsQcf qcf, EdsApplicationController controller) {
        this.qcf = qcf;
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
     * Constant to hold value of ORDER_TMOY_PROPERTY_ID
     */
    public static final Object ORDER_TMOY_PROPERTY_ID = "current-conso-tab-data-at-Tmoy";
    /**
     * Constant to hold value of ORDER_TMAX_PROPERTY_ID
     */
    public static final Object ORDER_TMAX_PROPERTY_ID = "current-conso-tab-data-at-Tmax";
    /**
     * Constant to hold value of ORDER_COMMENT_PROPERTY_ID
     */
    public static final Object ORDER_COMMENT_PROPERTY_ID = "eds-comnent";
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcf;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Variable to hold value of MyTable
     */
    private MyTable table;

    /**
     * Initialize Read form View for Park Mode
     */
    private void init() {
        setWidth("100%");
        addContainerProperty(ORDER_DONNEE_PROPERTY_ID, String.class, "");
        addContainerProperty(ORDER_TENSION_PROPERTY_ID, String.class, "", controller.getBundle().getString("table-header-label-tension"), null, null);
        addContainerProperty(ORDER_TMOY_PROPERTY_ID, String.class, "", controller.getBundle().getString("current-conso-tab-data-at-Tmoy"), null, null);
        if (qcf.getQcf1() == 1) {
            addContainerProperty(ORDER_TMAX_PROPERTY_ID, String.class, "", controller.getBundle().getString("current-conso-tab-data-at-Tmax"), null,
                    null);
        }

        addContainerProperty(ORDER_COMMENT_PROPERTY_ID, String.class, "", controller.getBundle().getString("eds-comnent"), null, null);

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
     * @param tension Voltage
     * @param tmoy Tmoy
     * @param tmax Tmax
     * @param comment Notes
     */
    public void addOrderToContainer(String donnee, String tension, Float tmoy, Float tmax, String comment) {

        Object itemId = this.addItem();
        Item item = this.getItem(itemId);
        item.getItemProperty(ORDER_DONNEE_PROPERTY_ID).setValue(donnee);
        item.getItemProperty(ORDER_TENSION_PROPERTY_ID).setValue(tension);
        item.getItemProperty(ORDER_TMOY_PROPERTY_ID).setValue(tmoy);
        item.getItemProperty(ORDER_TMAX_PROPERTY_ID).setValue(tmax);
        item.getItemProperty(ORDER_COMMENT_PROPERTY_ID).setValue(comment);
    }

    /**
     * This method add items in container in the given order
     * 
     * @param donnee Data
     * @param tension Voltage
     * @param tmoy Tmoy
     * @param comment Notes
     */
    public void addOrderToContainer(String donnee, String tension, Float tmoy, String comment) {

        Object itemId = this.addItem();
        Item item = this.getItem(itemId);
        item.getItemProperty(ORDER_DONNEE_PROPERTY_ID).setValue(donnee);
        item.getItemProperty(ORDER_TENSION_PROPERTY_ID).setValue(tension);
        item.getItemProperty(ORDER_TMOY_PROPERTY_ID).setValue(tmoy);
        item.getItemProperty(ORDER_COMMENT_PROPERTY_ID).setValue(comment);
    }
}
