package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.composants.MyTextArea;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.dao.model.EdsCourantCycle;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This abstract class provide Edit form view Cycle current consumptions
 * 
 * @author Geometric Ltd.
 */
public abstract class CourantCycleFormEditView extends A_EdsEditForm implements Button.ClickListener {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param ecc Object of EdsCourantCycle
     * @param cnaedsRemove Boolean to set removable
     * @param controller Controller of EDS application
     */
    public CourantCycleFormEditView(EdsCourantCycle ecc, boolean cnaedsRemove, EdsApplicationController controller) {
        this.edsCourantCycle = ecc;
        this.cnaedsRemove = cnaedsRemove;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsCourantCycle
     */
    private EdsCourantCycle edsCourantCycle;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of Boolean to set table removable
     */
    private boolean cnaedsRemove;
    /**
     * Variable to hold value of MyTable
     */
    private MyTable vTETable;
    /**
     * Variable to hold value of MyTextField for title
     */
    private MyTextField vTXTTitle;
    /**
     * Variable to hold value of MyTextField for Cycle
     */
    private MyTextField vTXTTCycle;
    /**
     * Variable to hold value of MyTextArea for comment
     */
    private MyTextArea vTAComment;
    /**
     * Variable to hold value of Label for project name
     */
    private Label projectsName;
    /**
     * Variable to hold value of Remove button
     */
    private Button vBRemove;

    /**
     * Initialize Edit form view Cycle current consumptions
     */
    private void init() {

        vTETable = new MyTable();
        vTXTTitle = new MyTextField();
        vTXTTitle.setWidth("300");
        vTXTTCycle = new MyTextField();
        vTAComment = new MyTextArea();
        projectsName = new Label();
        vBRemove = new Button();
        vBRemove.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/erase.png"));
        vBRemove.setStyleName(BaseTheme.BUTTON_LINK);
        vBRemove.setDescription(controller.getBundle().getString("btn-delete-tab"));
        vBRemove.addListener(this);

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);

        hl.addComponent(vTXTTitle);
        hl.addComponent(projectsName);

        GridLayout layout = new GridLayout(2, 2);
        layout.setSpacing(true);
        layout.setWidth("100%");
        layout.addComponent(hl, 0, 0);

        layout.addComponent(getTable(), 0, 1);

        if (cnaedsRemove) {
            layout.addComponent(vBRemove, 1, 0, 1, 1);
            layout.setComponentAlignment(vBRemove, Alignment.MIDDLE_LEFT);
            layout.setColumnExpandRatio(1, 0f);
        }

        addComponent(layout);
        addComponent(new Label(controller.getBundle().getString("courant-t-cycle-message")));

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        edsCourantCycle.setCcedsName(vTXTTitle.getString());
        edsCourantCycle.setCcedsTcycle(vTXTTCycle.getFloat());
        edsCourantCycle.setCcedsComent(vTAComment.getText());
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        vTXTTitle.setValue(edsCourantCycle.getCcedsName());
        vTXTTCycle.setValue(edsCourantCycle.getCcedsTcycle());
        vTAComment.setValue(edsCourantCycle.getCcedsComent());
        projectsName.setValue("(" + StringUtils.join(edsCourantCycle.getEdsProjects(), ",") + ")");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
        return Collections.singleton((Object) edsCourantCycle);
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
     * This method returns Table for cycle current consumption
     * 
     * @return Table for cycle current consumption
     */
    private Component getTable() {

        vTETable.setPageLength(2);
        vTETable.setWidth("100%");
        vTETable.setSelectable(true);

        vTETable.addContainerProperty(controller.getBundle().getString("eds-courant-label"), Label.class, null);

        vTETable.addContainerProperty(controller.getBundle().getString("current-conso-tab-data-TCycle"), MyTextField.class, null);
        vTETable.addContainerProperty(controller.getBundle().getString("eds-comnent") + " *", MyTextArea.class, null);

        vTETable.addItem(new Object[] { controller.getBundle().getString("current-conso-tab-data-ICycle"), vTXTTCycle, vTAComment }, new Integer(0));

        return vTETable;
    }
}
