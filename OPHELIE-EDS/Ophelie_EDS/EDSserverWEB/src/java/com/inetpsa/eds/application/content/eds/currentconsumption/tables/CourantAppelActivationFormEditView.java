package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import java.util.Collection;
import java.util.Collections;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.composants.MyTextArea;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyMesure;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyTheoritic;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This abstract class provide Edit form view for Activation Inrush Current
 * 
 * @author Geometric Ltd.
 */
public abstract class CourantAppelActivationFormEditView extends A_EdsEditForm implements Button.ClickListener {

    private ConsolidateSupplyEdsTension tension;
    /**
     * Variable to hold value of BT_TBT
     */
    private Boolean bt = false;

    /**
     * Parameterized Constructor
     * 
     * @param qcf Object of EdsQcf
     * @param ecaa Object of EdsCourantAppelleActivation
     * @param edsSupply Object of EdsSupply
     * @param isRemove Check to remove
     * @param controller Controller of EDS application
     * @param bt
     */
    public CourantAppelActivationFormEditView(EdsQcf qcf, EdsCourantAppelleActivation ecaa, boolean isRemove, EdsApplicationController controller,
            ConsolidateSupplyEdsTension tension, Boolean bt) {
        this.qcf = qcf;
        this.ecaa = ecaa;
        this.isRemove = isRemove;
        this.controller = controller;
        this.tension = tension;
        this.bt = bt;
        init();
    }

    /**
     * Parameterized Constructor
     * 
     * @param qcf Object of EdsQcf
     * @param ecaa Object of EdsCourantAppelleActivation
     * @param edsSupply Object of EdsSupply
     * @param isRemove Check to remove
     * @param controller Controller of EDS application
     */
    public CourantAppelActivationFormEditView(EdsQcf qcf, EdsCourantAppelleActivation ecaa, boolean isRemove, EdsApplicationController controller,
            Boolean bt) {
        this(qcf, ecaa, isRemove, controller, new ConsolidateSupplyEdsTension(), bt);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {

        if (vTXTTminPulse.isFloat() && vTXTTmoyPulse.isFloat() && vTXTTmaxPulse.isFloat() && vTXTTminDt.isFloat() && vTXTTmoyDt.isFloat()
                && vTXTTmaxDt.isFloat()) {
            return true;
        }
        showNotification(controller.getBundle().getString("eds-format-nombre-title"),
                controller.getBundle().getString("courant-activation-format-error"));
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {

        // edsCourantAppelleActivation.setCaaedsQcf1( ConditionTemperature );
        if (isValid()) {
            resetQcf1();

            ecaa.setCaaedsTitre(vTXTTitle.getString());

            ecaa.setCaaedsTminImaxPulse(vTXTTminPulse.getFloat());
            ecaa.setCaaedsTmoyImaxPulse(vTXTTmoyPulse.getFloat());
            ecaa.setCaaedsTmaxImaxPulse(vTXTTmaxPulse.getFloat());
            ecaa.setCaaedsTmoyImaxPulseComment(vTACommentairePulse.getText());

            ecaa.setCaaedsTminDtPulse(vTXTTminDt.getFloat());
            ecaa.setCaaedsTmoyDtPulse(vTXTTmoyDt.getFloat());
            ecaa.setCaaedsTmaxDtPulse(vTXTTmaxDt.getFloat());
            ecaa.setCaaedsTmoyDtPulseComment(vTACommentaireDt.getText());

            return true;
        }
        return false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        vTXTTitle.setValue(ecaa.getCaaedsTitre());

        vTXTTminPulse.setValue(ecaa.getCaaedsTminImaxPulse());
        vTXTTminDt.setValue(ecaa.getCaaedsTminDtPulse());

        vTXTTmoyPulse.setValue(ecaa.getCaaedsTmoyImaxPulse());
        vTXTTmoyDt.setValue(ecaa.getCaaedsTmoyDtPulse());

        vTXTTmaxPulse.setValue(ecaa.getCaaedsTmaxImaxPulse());
        vTXTTmaxDt.setValue(ecaa.getCaaedsTmaxDtPulse());

        vTACommentairePulse.setValue(ecaa.getCaaedsTmoyImaxPulseComment());
        vTACommentaireDt.setValue(ecaa.getCaaedsTmoyDtPulseComment());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) ecaa);
    }

    /**
     * This method returns Collection of EdsCourantAppelleActivation
     * 
     * @return Collection of EdsCourantAppelleActivation
     */
    public Collection<EdsCourantAppelleActivation> getAllIEdsCourantAppelleActivation() {
        return Collections.singleton(ecaa);
    }

    // PROTECTED
    // PRIVATE

    /**
     * Variable to hold value of TextArea for Dt comment
     */
    private MyTextArea vTACommentaireDt;
    /**
     * Variable to hold value of TextArea for Dt comment pulse
     */
    private MyTextArea vTACommentairePulse;
    /**
     * Variable to hold value of TextField for Tmoy Dt
     */
    private MyTextField vTXTTmoyDt;
    /**
     * Variable to hold value of TextField for Tmoy Pulse
     */
    private MyTextField vTXTTmoyPulse;
    /**
     * Variable to hold value of TextField for Tmin Dt
     */
    private MyTextField vTXTTminDt;
    /**
     * Variable to hold value of TextField for Tmax Dt
     */
    private MyTextField vTXTTmaxDt;
    /**
     * Variable to hold value of TextField for Tmin Pulse
     */
    private MyTextField vTXTTminPulse;
    /**
     * Variable to hold value of TextField for Tmax Pulse
     */
    private MyTextField vTXTTmaxPulse;
    /**
     * Variable to hold value of EdsCourantAppelleActivation
     */
    private EdsCourantAppelleActivation ecaa;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcf;
    /**
     * Variable to hold value of Button to remove form
     */
    private Button vBRemove;
    /**
     * Variable to hold value of boolean to remove form
     */
    private boolean isRemove;
    /**
     * Variable to hold value of MyTextField for title
     */
    private MyTextField vTXTTitle;

    /**
     * Variable to hold value of MyTable
     */
    private MyTable table;

    private EdsConsolidateSupplyTheoritic supplyTheoritic;

    private EdsConsolidateSupplyMesure supplyMesure;

    /**
     * Initialize Edit form view for Activation Inrush Current
     */
    private void init() {

        vTXTTminPulse = new MyTextField();
        vTXTTmoyPulse = new MyTextField();
        vTXTTmaxPulse = new MyTextField();
        vTACommentairePulse = new MyTextArea();
        vTXTTminDt = new MyTextField();
        vTXTTmoyDt = new MyTextField();
        vTXTTmaxDt = new MyTextField();
        vTACommentaireDt = new MyTextArea();

        vTXTTitle = new MyTextField();
        vTXTTitle.setWidth("300");
        vTXTTitle.setNullRepresentation("");
        vTXTTitle.setInputPrompt(controller.getBundle().getString("table-title"));
        addComponent(vTXTTitle);

        HorizontalLayout vHlLayout = new HorizontalLayout();

        vHlLayout.addComponent(getCourantAppelActivation());

        if (isRemove) {

            vBRemove = new Button();
            vBRemove.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/erase.png"));
            vBRemove.setStyleName(BaseTheme.BUTTON_LINK);
            vBRemove.setDescription(controller.getBundle().getString("btn-delete-tab"));
            vBRemove.addListener(this);
            vHlLayout.addComponent(vBRemove);
            vHlLayout.setComponentAlignment(vBRemove, Alignment.MIDDLE_LEFT);
            vHlLayout.setSpacing(true);
        }
        addComponent(vHlLayout);
        discardChanges();

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * This method return table for activation inrush current
     * 
     * @return table for activation inrush current
     */
    public Table getCourantAppelActivation() {
        table = new MyTable();
        table.setPageLength(2);

        table.addContainer("0", Label.class, controller.getBundle().getString("table-header-label-data-to-edit"));
        table.addContainer("1", Label.class, controller.getBundle().getString("table-header-label-tension"));
        table.addContainer("2", TextField.class, controller.getBundle().getString("current-conso-tab-data-at-Tmin"));
        table.addContainer("3", TextField.class, controller.getBundle().getString("current-conso-tab-data-at-Tmoy"));
        table.addContainer("4", TextField.class, controller.getBundle().getString("current-conso-tab-data-at-Tmax"));

        table.addContainer("5", TextArea.class, controller.getBundle().getString("eds-comnent"));

        table.addItem(
                new Object[] { controller.getBundle().getString("current-conso-tab-data-ImaxPulse"), super.getUmoy12_5(tension.getCsEdsUmoy(), bt),
                        vTXTTminPulse, vTXTTmoyPulse, vTXTTmaxPulse, vTACommentairePulse }, new Integer(0));

        table.addItem(new Object[] { "∆t (66% ImaxPulse) (s)", super.getUmoy12_5(tension.getCsEdsUmoy(), bt), vTXTTminDt, vTXTTmoyDt, vTXTTmaxDt,
                vTACommentaireDt }, new Integer(1));

        if (qcf.getQcf1() != 1) {
            table.setVisibleColumns(new Object[] { "0", "1", "3", "5" });
        }
        return table;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
    }

    /**
     * This method shows notification
     * 
     * @param title Title of notification
     * @param message Message of notification
     */
    private void showNotification(String title, String message) {

        getWindow().showNotification(title, message, Notification.TYPE_ERROR_MESSAGE);
    }

    /**
     * This method set table editable based on specified boolean value
     * 
     * @param b Value to set editable table
     */
    public void setEditable(boolean b) {
        vTXTTminPulse.setEnabled(b);
        vTXTTminDt.setEnabled(b);

        vTXTTmoyPulse.setEnabled(b);
        vTXTTmoyDt.setEnabled(b);

        vTXTTmaxPulse.setEnabled(b);
        vTXTTmaxDt.setEnabled(b);

        vTACommentairePulse.setEnabled(b);
        vTACommentaireDt.setEnabled(b);
    }

    /**
     * This method resets EdsQcf object
     */
    private void resetQcf1() {
        if (qcf.getQcf1() == 0) {
            vTXTTminDt.setValue(null);
            vTXTTminPulse.setValue(null);

            vTXTTmaxDt.setValue(null);
            vTXTTmaxPulse.setValue(null);
        }
    }

}
