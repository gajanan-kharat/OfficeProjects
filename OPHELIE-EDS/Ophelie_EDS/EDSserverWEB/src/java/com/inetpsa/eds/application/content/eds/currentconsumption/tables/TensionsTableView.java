/*
 * Creation : 21 mai 2015
 */
package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.cse.FloatField;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class TensionsTableView extends A_CurrentConsumptionTable {

    // PUBLIC
    public static final String ORDER_DATA_PROPERTY_ID = "";

    public static final String ORDER_TENSION_PROPERTY_ID = "table-header-label-tension";

    /**
     * Constant to hold value of ORDER_COMMENT_PROPERTY_ID
     */
    public static final String ORDER_COMMENT_PROPERTY_ID = "eds-comnent";

    /**
     * @param controller
     */
    public TensionsTableView(ConsolidateSupplyEdsTension tension, EdsApplicationController controller, boolean addOperatingModeName) {
        super(controller, tension.getOperatingModeName(), addOperatingModeName);
        this.controller = controller;
        this.tension = tension;
        init();
    }

    public void setEditable(boolean editable) {
        table.setEditable(editable);
    }

    /**
     * This method returns controller of EDS application
     * 
     * @return controller of EDS application
     */
    public EdsApplicationController getController() {
        return controller;
    }

    /**
     * Getter tension
     * 
     * @return the tension
     */
    public ConsolidateSupplyEdsTension getTension() {
        return tension;
    }

    public void commitTensions() {

        tension.setCsEdsUmin((Float) table.getContainerProperty(1, ORDER_TENSION_PROPERTY_ID).getValue());
        tension.setCsEdsUminComment((String) table.getContainerProperty(1, ORDER_COMMENT_PROPERTY_ID).getValue());
        tension.setCsEdsUmoy((Float) table.getContainerProperty(2, ORDER_TENSION_PROPERTY_ID).getValue());
        tension.setCsEdsUmoyComment((String) table.getContainerProperty(2, ORDER_COMMENT_PROPERTY_ID).getValue());
        tension.setCsEdsUmax((Float) table.getContainerProperty(3, ORDER_TENSION_PROPERTY_ID).getValue());
        tension.setCsEdsUmaxComment((String) table.getContainerProperty(3, ORDER_COMMENT_PROPERTY_ID).getValue());
    }

    // PRIVATE
    private EdsApplicationController controller;
    private ConsolidateSupplyEdsTension tension;
    private MyTable table;

    @SuppressWarnings("serial")
    private void init() {
        setSpacing(true);
        table = new MyTable();
        table.setWidth("100%");
        table.setStyleName("tensions-table");
        table.addContainerProperty(ORDER_DATA_PROPERTY_ID, String.class, "", "", null, null);
        table.addContainerProperty(ORDER_TENSION_PROPERTY_ID, Float.class, "", controller.getBundle().getString(ORDER_TENSION_PROPERTY_ID), null,
                null);
        table.addContainerProperty(ORDER_COMMENT_PROPERTY_ID, String.class, "", controller.getBundle().getString(ORDER_COMMENT_PROPERTY_ID), null,
                null);

        // Add
        addOrderToContainer(table, "Umin", tension.getCsEdsUmin(), tension.getCsEdsUminComment());
        addOrderToContainer(table, "Umoy", tension.getCsEdsUmoy(), tension.getCsEdsUmoyComment());
        addOrderToContainer(table, "Umax", tension.getCsEdsUmax(), tension.getCsEdsUmaxComment());

        table.setTableFieldFactory(new TableFieldFactory() {

            @Override
            public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {

                if (propertyId.equals(ORDER_DATA_PROPERTY_ID)) {
                    TextField tf = new TextField();
                    tf.setReadOnly(true);
                    return tf;
                } else if (propertyId.equals(ORDER_TENSION_PROPERTY_ID)) {
                    FloatField ff = new FloatField();
                    ff.setNullRepresentation("");
                    ff.setNullSettingAllowed(true);
                    ff.setMaxLength(64);
                    return ff;
                } else if (propertyId.equals(ORDER_COMMENT_PROPERTY_ID)) {
                    TextArea ta = new TextArea();
                    ta.setHeight("35px");
                    ta.setWidth("100%");
                    ta.setNullRepresentation("");
                    ta.setNullSettingAllowed(true);
                    ta.setMaxLength(256);
                    ta.addListener(new TextChangeListener() {
                        public void textChange(TextChangeEvent event) {
                            if (event.getText().length() > 255) {
                                getController().showWarning("Le champ de texte ne peut contenir plus de 256 caractères.");
                            }
                        }
                    });

                    return ta;
                } else
                    return null;

            }
        });

        table.setSelectable(true);
        table.setMultiSelect(true);
        table.setPageLength(3);
        table.setHeight("");

        this.addComponent(table);

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * This method add items in container in the given order
     * 
     * @param donnee Data
     * @param tension Tension
     * @param tPircas Tworstcase
     * @param comment Notes
     */
    public void addOrderToContainer(MyTable t, String data, Float tension, String comment) {

        Object itemId = t.addItem();
        Item item = t.getItem(itemId);
        item.getItemProperty(ORDER_DATA_PROPERTY_ID).setValue(data);
        item.getItemProperty(ORDER_TENSION_PROPERTY_ID).setValue(tension);
        item.getItemProperty(ORDER_COMMENT_PROPERTY_ID).setValue(comment);
    }

}
