/*
 * Creation : 20 mai 2015
 */
package com.inetpsa.eds.application.actionbar.checkreport;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.vaadin.data.Item;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class CheckTableFormReadView extends MyTable {

    private static final long serialVersionUID = -2055921570355406029L;

    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param qcf Object of EdsQcf
     * @param controller Controller of EDS application
     */
    public CheckTableFormReadView(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of ORDER_DONNEE_PROPERTY_ID
     */
    public static final Object ORDER_DONNEE_PROPERTY_ID = "Data";
    /**
     * Constant to hold value of ORDER_TENSION_PROPERTY_ID
     */
    public static final Object ORDER_TENSION_PROPERTY_ID = "Value";
    /**
     * Constant to hold value of ORDER_TMOY_PROPERTY_ID
     */
    public static final Object ORDER_TMOY_PROPERTY_ID = "Valide/Invalide";
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    private boolean valid = true;

    private List<Object> elements;

    /**
     * Initialize Read form View for Park Mode
     */
    private void init() {
        setWidth("100%");
        setStyleName("check");
        addContainerProperty(ORDER_DONNEE_PROPERTY_ID, String.class, "", controller.getBundle().getString("check-report-data"), null, null);
        addContainerProperty(ORDER_TENSION_PROPERTY_ID, String.class, "", controller.getBundle().getString("check-report-value"), null, null);
        addContainerProperty(ORDER_TMOY_PROPERTY_ID, String.class, "", controller.getBundle().getString("check-report-valid"), null, null);
        elements = new ArrayList<Object>();

        this.addGeneratedColumn(ORDER_TMOY_PROPERTY_ID, new ColumnGenerator() {

            private static final long serialVersionUID = 1L;

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Object value = elements.get(((Integer) itemId) - 1);
                Label label = new Label();

                if (value != null) {
                    label.setValue(controller.getBundle().getString("check-report-OK"));
                    label.setStyleName("check-ok");
                } else {
                    label.setValue(controller.getBundle().getString("check-report-KO"));
                    label.setStyleName("check-ko");
                }

                return label;
            }
        });

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

    public boolean isValid() {
        return valid;
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
    public void addOrderToContainer(String data, Float value) {

        Object itemId = this.addItem();
        Item item = this.getItem(itemId);
        item.getItemProperty(ORDER_DONNEE_PROPERTY_ID).setValue(data);
        item.getItemProperty(ORDER_TENSION_PROPERTY_ID).setValue(value);

        if (value != null) {
            elements.add(value);
            item.getItemProperty(ORDER_TMOY_PROPERTY_ID).setValue(controller.getBundle().getString("check-report-OK"));
        } else {
            item.getItemProperty(ORDER_TMOY_PROPERTY_ID).setValue(controller.getBundle().getString("check-report-KO"));
            elements.add(null);
            valid = false;
        }
    }

    public void addOrderToContainer(String data, String value) {

        Object itemId = this.addItem();
        Item item = this.getItem(itemId);
        item.getItemProperty(ORDER_DONNEE_PROPERTY_ID).setValue(data);
        item.getItemProperty(ORDER_TENSION_PROPERTY_ID).setValue(value);

        if (value != null) {
            elements.add(value);
            item.getItemProperty(ORDER_TMOY_PROPERTY_ID).setValue(controller.getBundle().getString("check-report-OK"));
        } else {
            item.getItemProperty(ORDER_TMOY_PROPERTY_ID).setValue(controller.getBundle().getString("check-report-KO"));
            elements.add(null);
            valid = false;
        }
    }

}
