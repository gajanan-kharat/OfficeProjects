package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.vaadin.data.Item;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * This class provide Read form view for Activation Inrush Current
 * 
 * @author Geometric Ltd.
 */
public class CourantAppelleActivationFormReadView extends GridLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param qcf Object of EdsQcf
     * @param controller Controller of EDS application
     */
    public CourantAppelleActivationFormReadView(EdsQcf qcf, EdsApplicationController controller) {
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
     * Constant to hold value of ORDER_TMIN_PROPERTY_ID
     */
    public static final Object ORDER_TMIN_PROPERTY_ID = "current-conso-tab-data-at-Tmin";
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
     * Initialize Read form view for Activation Inrush Current
     */
    private void init() {
        setWidth("100%");
        vLTitre = new Label();
        vLTitre.setStyleName("table-title");

        addComponent(vLTitre);

        t = new MyTable();
        t.setWidth("100%");
        t.addContainerProperty(ORDER_DONNEE_PROPERTY_ID, String.class, "", "", null, null);
        t.addContainerProperty(ORDER_TENSION_PROPERTY_ID, String.class, "", controller.getBundle().getString((String) ORDER_TENSION_PROPERTY_ID),
                null, null);
        if (qcf.getQcf1() == 1) {
            t.addContainerProperty(ORDER_TMIN_PROPERTY_ID, String.class, "", controller.getBundle().getString((String) ORDER_TMIN_PROPERTY_ID), null,
                    null);
        }
        t.addContainerProperty(ORDER_TMOY_PROPERTY_ID, String.class, "", controller.getBundle().getString((String) ORDER_TMOY_PROPERTY_ID), null,
                null);
        if (qcf.getQcf1() == 1) {
            t.addContainerProperty(ORDER_TMAX_PROPERTY_ID, String.class, "", controller.getBundle().getString((String) ORDER_TMAX_PROPERTY_ID), null,
                    null);
        }

        t.addContainerProperty(ORDER_COMMENT_PROPERTY_ID, String.class, "", controller.getBundle().getString((String) ORDER_COMMENT_PROPERTY_ID),
                null, null);
        addComponent(t);

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * This method sets the Page length
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
     * @param tension Tension
     * @param tmin Tmin
     * @param tmoy Tmoy
     * @param tmax Tmax
     * @param comment Notes
     */
    public void addOrderToContainer(String donnee, String tension, Float tmin, Float tmoy, Float tmax, String comment) {

        Object itemId = t.addItem();
        Item item = t.getItem(itemId);
        item.getItemProperty(ORDER_DONNEE_PROPERTY_ID).setValue(donnee);
        item.getItemProperty(ORDER_TENSION_PROPERTY_ID).setValue(tension);
        if (qcf.getQcf1() == 1) {
            item.getItemProperty(ORDER_TMIN_PROPERTY_ID).setValue(tmin);
        }
        item.getItemProperty(ORDER_TMOY_PROPERTY_ID).setValue(tmoy);
        if (qcf.getQcf1() == 1) {
            item.getItemProperty(ORDER_TMAX_PROPERTY_ID).setValue(tmax);
        }
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
