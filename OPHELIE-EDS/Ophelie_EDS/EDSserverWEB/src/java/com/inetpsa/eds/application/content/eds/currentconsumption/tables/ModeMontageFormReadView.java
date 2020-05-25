package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.vaadin.data.Item;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * This class provide Read form view for Assembled Mode
 * 
 * @author Geometric Ltd.
 */
public class ModeMontageFormReadView extends GridLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public ModeMontageFormReadView(EdsApplicationController controller) {
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of ORDER_DONNEE_PROPERTY_ID
     */
    public static final String ORDER_DONNEE_PROPERTY_ID = "";
    /**
     * Constant to hold value of ORDER_TENSION_PROPERTY_ID
     */
    public static final String ORDER_TENSION_PROPERTY_ID = "table-header-label-tension";
    /**
     * Constant to hold value of ORDER_TMOY_PROPERTY_ID
     */
    public static final String ORDER_TMOY_PROPERTY_ID = "current-conso-tab-data-at-Tmoy";
    /**
     * Constant to hold value of ORDER_COMMENT_PROPERTY_ID
     */
    public static final String ORDER_COMMENT_PROPERTY_ID = "eds-comnent";
    /**
     * Variable to hold value of MyTable
     */
    private MyTable t;
    /**
     * Variable to hold value of Label for title
     */
    private Label vLTitre;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Read form view for assembled mode
     */
    private void init() {
        setWidth("100%");
        vLTitre = new Label();
        vLTitre.setStyleName("table-title");

        addComponent(vLTitre);

        t = new MyTable();
        t.setWidth("100%");

        t.addContainerProperty(ORDER_DONNEE_PROPERTY_ID, String.class, "", "", null, null);
        t.addContainerProperty(ORDER_TENSION_PROPERTY_ID, String.class, "", controller.getBundle().getString(ORDER_TENSION_PROPERTY_ID), null, null);
        t.addContainerProperty(ORDER_TMOY_PROPERTY_ID, String.class, "", controller.getBundle().getString(ORDER_TMOY_PROPERTY_ID), null, null);
        t.addContainerProperty(ORDER_COMMENT_PROPERTY_ID, String.class, "", controller.getBundle().getString(ORDER_COMMENT_PROPERTY_ID), null, null);

        addComponent(t);

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * This method set the page length
     * 
     * @param pageLength Page length
     */
    public void setPageLength(int pageLength) {
        t.setPageLength(pageLength);
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

        Object itemId = t.addItem();
        Item item = t.getItem(itemId);
        item.getItemProperty(ORDER_DONNEE_PROPERTY_ID).setValue(donnee);
        item.getItemProperty(ORDER_TENSION_PROPERTY_ID).setValue(tension);
        item.getItemProperty(ORDER_TMOY_PROPERTY_ID).setValue(tmoy);
        item.getItemProperty(ORDER_COMMENT_PROPERTY_ID).setValue(comment);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractComponent#setCaption(java.lang.String)
     */
    @Override
    public void setCaption(String caption) {
        vLTitre.setValue(caption);
    }
}
