package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import java.util.Collection;
import java.util.Collections;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.composants.MyTextArea;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.application.content.eds.composants.TwinTextField;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This abstract class provide Edit form view for Blocked couple Current/ Dysfunctional Current
 * 
 * @author Geometric Ltd.
 */
public abstract class CourantCoupleBloqueCourantDysfonctionnelFormEditView extends A_EdsEditForm implements Button.ClickListener {
    /**
     * Variable to hold value of TwinTextField for Tmin
     */
    private TwinTextField vTXTTmin;
    /**
     * Variable to hold value of TwinTextField for Tmed
     */
    private TwinTextField vTXTTmoy;
    /**
     * Variable to hold value of TwinTextField for Tmax
     */
    private TwinTextField vTXTTmax;
    /**
     * Variable to hold value of MyTextArea for Notes
     */
    private MyTextArea vTACommentaire;
    /**
     * Variable to hold value of Checkbox for Occurrence
     */
    private CheckBox vCBOccurence;
    /**
     * Variable to hold value of MyTextField for Occurrence
     */
    private MyTextField vTXTNbrOccurence;
    /**
     * Variable to hold value of EdsCourantBloqueCourantDysfonctionnement
     */
    private EdsCourantBloqueCourantDysfonctionnement ecbcd;
    /**
     * Variable to hold value of MyTable
     */
    private MyTable table;
    /**
     * Variable to hold value of Boolean to set view removable
     */
    private boolean remove;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcf;
    /**
     * Variable to hold value of MyTextField for title
     */
    private MyTextField vTXTTitle;
    /**
     * Variable to hold value of Remove Button
     */
    private Button vBRemove;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    private ConsolidateSupplyEdsTension tension;

    // PUBLIC

    /**
     * Parameterized constructor
     * 
     * @param qcf Object of EdsQcf
     * @param ecbcd Object of EdsCourantBloqueCourantDysfonctionnement
     * @param remove Boolean to set view removable
     * @param controller Controller of EDS application
     */
    public CourantCoupleBloqueCourantDysfonctionnelFormEditView(EdsQcf qcf, EdsCourantBloqueCourantDysfonctionnement ecbcd, boolean remove,
            EdsApplicationController controller, ConsolidateSupplyEdsTension tension) {
        this.controller = controller;
        this.ecbcd = ecbcd;
        this.remove = remove;
        this.qcf = qcf;
        this.tension = tension;
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param qcf Object of EdsQcf
     * @param ecbcd Object of EdsCourantBloqueCourantDysfonctionnement
     * @param remove Boolean to set view removable
     * @param controller Controller of EDS application
     */
    public CourantCoupleBloqueCourantDysfonctionnelFormEditView(EdsQcf qcf, EdsCourantBloqueCourantDysfonctionnement ecbcd, boolean remove,
            EdsApplicationController controller) {
        this(qcf, ecbcd, remove, controller, new ConsolidateSupplyEdsTension());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
        // throw new UnsupportedOperationException( "Not yet implemented" );

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        // ecbcd.setCbcdedsQcf1( conditionTemperature );
        resetQcf1();

        ecbcd.setCbcdedsTitre(vTXTTitle.getString());
        ecbcd.setCbcdedsRemove(remove);

        ecbcd.setCbcdedsTminTdys(vTXTTmin.getTime());
        ecbcd.setCbcdedsTmoyTdys(vTXTTmoy.getTime());
        ecbcd.setCbcdedsTmaxTdys(vTXTTmax.getTime());
        ecbcd.setCbcdedsOccurenceDefaillenceTdys(vTXTNbrOccurence.getFloat());
        ecbcd.setCbcdedsComment(vTACommentaire.getText());

        ecbcd.setCbcdedsTminIdys(vTXTTmin.getCourant());
        ecbcd.setCbcdedsTmoyIdys(vTXTTmoy.getCourant());
        ecbcd.setCbcdedsTmaxIdys(vTXTTmax.getCourant());
        ecbcd.setCbcdedsOccurenceDefaillence(vCBOccurence.booleanValue());

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {

        vTXTTitle.setValue(ecbcd.getCbcdedsTitre());
        vTXTTmin.setCourant(ecbcd.getCbcdedsTminIdys());
        vTXTTmoy.setCourant(ecbcd.getCbcdedsTmoyIdys());
        vTXTTmax.setCourant(ecbcd.getCbcdedsTmaxIdys());
        vTXTNbrOccurence.setValue(ecbcd.getCbcdedsOccurenceDefaillenceTdys());
        vTACommentaire.setValue(ecbcd.getCbcdedsComment());

        vTXTTmin.setTime(ecbcd.getCbcdedsTminTdys());
        vTXTTmoy.setTime(ecbcd.getCbcdedsTmoyTdys());
        vTXTTmax.setTime(ecbcd.getCbcdedsTmaxTdys());
        vCBOccurence.setValue(ecbcd.getCbcdedsOccurenceDefaillence());

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) ecbcd);
    }

    /**
     * This method returns Collection of EdsCourantBloqueCourantDysfonctionnement
     * 
     * @return Collection of EdsCourantBloqueCourantDysfonctionnement
     */
    public Collection<EdsCourantBloqueCourantDysfonctionnement> getAllEdsCourantBloqueCourantDysfonctionnement() {
        return Collections.singleton(ecbcd);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Edit form view for Blocked couple Current/ Dysfunctional Current
     */
    private void init() {

        setSpacing(false);
        setWidth("100%");
        vTXTTitle = new MyTextField();
        vTXTTitle.setWidth("300");
        addComponent(vTXTTitle);

        HorizontalLayout hl = new HorizontalLayout();
        vCBOccurence = new CheckBox(controller.getBundle().getString("occurence-sup"));
        hl.addComponent(vCBOccurence);
        hl.setComponentAlignment(vCBOccurence, Alignment.BOTTOM_LEFT);
        addComponent(hl);

        HorizontalLayout vHlLayout = new HorizontalLayout();
        vHlLayout.setWidth("100%");
        vHlLayout.addComponent(tableauCourantAppelActivation());
        vHlLayout.setExpandRatio(table, 1f);
        if (remove) {

            vBRemove = new Button();
            vBRemove.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/erase.png"));
            vBRemove.setStyleName(BaseTheme.BUTTON_LINK);
            vBRemove.setDescription(controller.getBundle().getString("btn-delete-tab"));
            vBRemove.addListener(this);
            vHlLayout.addComponent(vBRemove);
            vHlLayout.setComponentAlignment(vBRemove, Alignment.MIDDLE_LEFT);
            vHlLayout.setExpandRatio(vBRemove, 0f);
            vHlLayout.setSpacing(true);
        }
        addComponent(vHlLayout);
        Label info = new Label(controller.getBundle().getString("courant-i-pircas-message"), Label.CONTENT_XHTML);
        addComponent(info);
        discardChanges();

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

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
     * This method return Table for Blocked couple current
     * 
     * @return Table for Blocked couple current
     */
    public Table tableauCourantAppelActivation() {
        table = new MyTable();
        table.setWidth("100%");
        table.setPageLength(1);

        vTXTTmin = new TwinTextField(true, controller);
        vTXTTmoy = new TwinTextField(true, controller);
        vTXTTmax = new TwinTextField(true, controller);
        vTXTNbrOccurence = new MyTextField();
        vTACommentaire = new MyTextArea();

        table.addContainer("0", Label.class, controller.getBundle().getString("table-header-label-data-to-edit"));
        table.addContainer("1", Label.class, controller.getBundle().getString("table-header-label-tension"));
        table.addContainer("2", TwinTextField.class, controller.getBundle().getString("current-conso-tab-data-at-Tmin"));
        table.addContainer("3", TwinTextField.class, controller.getBundle().getString("current-conso-tab-data-at-Tmoy"));
        table.addContainer("4", TwinTextField.class, controller.getBundle().getString("current-conso-tab-data-at-Tmax"));
        table.addContainer("5", MyTextField.class, controller.getBundle().getString("current-conso-tab-data-occurrence"));
        table.addContainer("6", MyTextArea.class, controller.getBundle().getString("eds-comnent"));

        Label l = new Label("Idys (A)<br>tdys (s)", Label.CONTENT_XHTML);
        table.addItem(new Object[] { l, controller.getBundle().getString("current-conso-tab-data-Udysf"), vTXTTmin, vTXTTmoy, vTXTTmax,
                vTXTNbrOccurence, vTACommentaire }, new Integer(1));

        if (qcf.getQcf1() != 1) {
            table.setVisibleColumns(new Object[] { "0", "1", "3", "5", "6" });
        }

        return table;
    }

    /**
     * This method set table editable based on specified boolean value
     * 
     * @param enabled Value to set editable table
     */
    public void setEditable(boolean enabled) {
        vTXTTmin.setEnabled(enabled);
        vTXTTmoy.setEnabled(enabled);
        vTXTTmax.setEnabled(enabled);
        vTACommentaire.setEnabled(enabled);
        vCBOccurence.setEnabled(enabled);
        vTXTNbrOccurence.setEnabled(enabled);
    }

    /**
     * This method resets EdsQcf object
     */
    private void resetQcf1() {
        if (qcf.getQcf1() == 0) {
            vTXTTmin.reset();
            vTXTTmax.reset();

        }
    }

}
