/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import java.util.Collection;
import java.util.Collections;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.composants.MyTextArea;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.dao.model.EdsReseauVeilleReveilleOrganeInactif;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;

/**
 * This class provide Edit form view for Asleep or awake network and inactive device (Customer mode)
 * 
 * @author Geometric Ltd.
 */
public class ReseauVeilleReveilleOrganeInactifFormEditView extends A_EdsEditForm implements Table.CellStyleGenerator {
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcf;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    private ConsolidateSupplyEdsTension tension;
    /**
     * Variable to hold value of BT_TBT
     */
    private Boolean bt = false;

    // PUBLIC

    /**
     * Parameterized constructor
     * 
     * @param qcf Object of EdsQcf
     * @param ervroi Object of EdsReseauVeilleReveilleOrganeInactif
     * @param edsSupply Object of EdsSupply
     * @param controller Controller of EDS application
     */
    public ReseauVeilleReveilleOrganeInactifFormEditView(EdsQcf qcf, EdsReseauVeilleReveilleOrganeInactif ervroi,
            EdsApplicationController controller, ConsolidateSupplyEdsTension tension, Boolean bt) {

        this.qcf = qcf;
        this.ervroi = ervroi;
        this.controller = controller;
        this.tension = tension;
        this.bt = bt;

        init();

    }

    /**
     * Parameterized constructor
     * 
     * @param qcf Object of EdsQcf
     * @param ervroi Object of EdsReseauVeilleReveilleOrganeInactif
     * @param edsSupply Object of EdsSupply
     * @param controller Controller of EDS application
     */
    public ReseauVeilleReveilleOrganeInactifFormEditView(EdsQcf qcf, EdsReseauVeilleReveilleOrganeInactif ervroi,
            EdsApplicationController controller, Boolean bt) {
        this(qcf, ervroi, controller, new ConsolidateSupplyEdsTension(), bt);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {

        resetQcf();
        /* T°min */
        ervroi.setRvroiedsTminIveilleMoteurNonTourant(tMinIveille.getFloat());
        ervroi.setRvroiedsTminIveille8hMoteurNonTourant(tMinIveille8h.getFloat());
        ervroi.setRvroiedsTminIveille21hMoteurNonTourant(tMinIveille21j.getFloat());
        ervroi.setRvroiedsTminIveille30hMoteurNonTourant(tMinIveille30j.getFloat());
        ervroi.setRvroiedsTminIreveilleInactifMoteurNonTourant(tMinIreveilleInactif.getFloat());
        ervroi.setRvroiedsTminImodeEcoMoteurNonTourant(tMinModeEco.getFloat());
        ervroi.setRvroiedsTminIreveilleInactif10vMoteurTourant(tMinIreveilleInactif10.getFloat());
        ervroi.setRvroiedsTminIreveilleInactif13vMoteurTourant(tMinIreveilleInactif13.getFloat());
        ervroi.setRvroiedsTminIreveilleInactif15vMoteurTourant(tMinIreveilleInactif15.getFloat());

        /* T°moy */
        ervroi.setRvroiedsTmoyIveilleMoteurNonTourant(tMoyIveille.getFloat());
        ervroi.setRvroiedsTmoyIveille8hMoteurNonTourant(tMoyIveille8h.getFloat());
        ervroi.setRvroiedsTmoyIveille21hMoteurNonTourant(tMoyIveille21j.getFloat());
        ervroi.setRvroiedsTmoyIveille30hMoteurNonTourant(tMoyIveille30j.getFloat());
        ervroi.setRvroiedsTmoyIreveilleInactifMoteurNonTourant(tMoyIreveilleInactif.getFloat());
        ervroi.setRvroiedsTmoyImodeEcoMoteurNonTourant(tMoyModeEco.getFloat());
        ervroi.setRvroiedsTmoyIreveilleInactif10vMoteurTourant(tMoyIreveilleInactif10.getFloat());
        ervroi.setRvroiedsTmoyIreveilleInactif13vMoteurTourant(tMoyIreveilleInactif13.getFloat());
        ervroi.setRvroiedsTmoyIreveilleInactif15vMoteurTourant(tMoyIreveilleInactif15.getFloat());

        /* T°max */
        ervroi.setRvroiedsTmaxIveilleMoteurNonTourant(tMaxIveille.getFloat());
        ervroi.setRvroiedsTmaxIveille8hMoteurNonTourant(tMaxIveille8h.getFloat());
        ervroi.setRvroiedsTmaxIveille21hMoteurNonTourant(tMaxIveille21j.getFloat());
        ervroi.setRvroiedsTmaxIveille30hMoteurNonTourant(tMaxIveille30j.getFloat());
        ervroi.setRvroiedsTmaxIreveilleInactifMoteurNonTourant(tMaxIreveilleInactif.getFloat());
        ervroi.setRvroiedsTmaxImodeEcoMoteurNonTourant(tMaxModeEco.getFloat());
        ervroi.setRvroiedsTmaxIreveilleInactif10vMoteurTourant(tMaxIreveilleInactif10.getFloat());
        ervroi.setRvroiedsTmaxIreveilleInactif13vMoteurTourant(tMaxIreveilleInactif13.getFloat());
        ervroi.setRvroiedsTmaxIreveilleInactif15vMoteurTourant(tMaxIreveilleInactif15.getFloat());

        /* Comment */
        ervroi.setRvroiedsIveilleMoteurNonTourantComment(comIveille.getText());
        ervroi.setRvroiedsIveille8hMoteurNonTourantComment(comIveille8h.getText());
        ervroi.setRvroiedsIveille21hMoteurNonTourantComment(comIveille21j.getText());
        ervroi.setRvroiedsIveille30hMoteurNonTourantComment(comIveille30j.getText());
        ervroi.setRvroiedsIreveilleInactifMoteurNonTourantComment("" + comIreveilleInactif.getText());
        ervroi.setRvroiedsImodeEcoMoteurNonTourantComment("" + comModeEco.getText());
        ervroi.setRvroiedsIreveilleInactif10vMoteurTourantComment(comIreveilleInactif10.getText());
        ervroi.setRvroiedsIreveilleInactif13vMoteurTourantComment(comIreveilleInactif13.getText());
        ervroi.setRvroiedsIreveilleInactif15vMoteurTourantComment(comIreveilleInactif15.getText());

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {

        /* TMin */
        tMinIveille.setValue(ervroi.getRvroiedsTminIveilleMoteurNonTourant());
        tMinIveille8h.setValue(ervroi.getRvroiedsTminIveille8hMoteurNonTourant());
        tMinIveille21j.setValue(ervroi.getRvroiedsTminIveille21hMoteurNonTourant());
        tMinIveille30j.setValue(ervroi.getRvroiedsTminIveille30hMoteurNonTourant());
        tMinIreveilleInactif.setValue(ervroi.getRvroiedsTminIreveilleInactifMoteurNonTourant());
        tMinModeEco.setValue(ervroi.getRvroiedsTminImodeEcoMoteurNonTourant());
        tMinIreveilleInactif10.setValue(ervroi.getRvroiedsTminIreveilleInactif10vMoteurTourant());
        tMinIreveilleInactif13.setValue(ervroi.getRvroiedsTminIreveilleInactif13vMoteurTourant());
        tMinIreveilleInactif15.setValue(ervroi.getRvroiedsTminIreveilleInactif15vMoteurTourant());

        /* TMoy */
        tMoyIveille.setValue(ervroi.getRvroiedsTmoyIveilleMoteurNonTourant());
        tMoyIveille8h.setValue(ervroi.getRvroiedsTmoyIveille8hMoteurNonTourant());
        tMoyIveille21j.setValue(ervroi.getRvroiedsTmoyIveille21hMoteurNonTourant());
        tMoyIveille30j.setValue(ervroi.getRvroiedsTmoyIveille30hMoteurNonTourant());
        tMoyIreveilleInactif.setValue(ervroi.getRvroiedsTmoyIreveilleInactifMoteurNonTourant());
        tMoyModeEco.setValue(ervroi.getRvroiedsTmoyImodeEcoMoteurNonTourant());
        tMoyIreveilleInactif10.setValue(ervroi.getRvroiedsTmoyIreveilleInactif10vMoteurTourant());
        tMoyIreveilleInactif13.setValue(ervroi.getRvroiedsTmoyIreveilleInactif13vMoteurTourant());
        tMoyIreveilleInactif15.setValue(ervroi.getRvroiedsTmoyIreveilleInactif15vMoteurTourant());

        /* TMax */
        tMaxIveille.setValue(ervroi.getRvroiedsTmaxIveilleMoteurNonTourant());
        tMaxIveille8h.setValue(ervroi.getRvroiedsTmaxIveille8hMoteurNonTourant());
        tMaxIveille21j.setValue(ervroi.getRvroiedsTmaxIveille21hMoteurNonTourant());
        tMaxIveille30j.setValue(ervroi.getRvroiedsTmaxIveille30hMoteurNonTourant());
        tMaxIreveilleInactif.setValue(ervroi.getRvroiedsTmaxIreveilleInactifMoteurNonTourant());
        tMaxModeEco.setValue(ervroi.getRvroiedsTmaxImodeEcoMoteurNonTourant());
        tMaxIreveilleInactif10.setValue(ervroi.getRvroiedsTmaxIreveilleInactif10vMoteurTourant());
        tMaxIreveilleInactif13.setValue(ervroi.getRvroiedsTmaxIreveilleInactif13vMoteurTourant());
        tMaxIreveilleInactif15.setValue(ervroi.getRvroiedsTmaxIreveilleInactif15vMoteurTourant());

        /* Comment */
        comIveille.setValue(ervroi.getRvroiedsIveilleMoteurNonTourantComment());
        comIveille8h.setValue(ervroi.getRvroiedsIveille8hMoteurNonTourantComment());
        comIveille21j.setValue(ervroi.getRvroiedsIveille21hMoteurNonTourantComment());
        comIveille30j.setValue(ervroi.getRvroiedsIveille30hMoteurNonTourantComment());
        comIreveilleInactif.setValue(ervroi.getRvroiedsIreveilleInactifMoteurNonTourantComment());
        comModeEco.setValue(ervroi.getRvroiedsImodeEcoMoteurNonTourantComment());
        comIreveilleInactif10.setValue(ervroi.getRvroiedsIreveilleInactif10vMoteurTourantComment());
        comIreveilleInactif13.setValue(ervroi.getRvroiedsIreveilleInactif13vMoteurTourantComment());
        comIreveilleInactif15.setValue(ervroi.getRvroiedsIreveilleInactif15vMoteurTourantComment());

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) ervroi);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of MyTextField for Tmin of asleep8h
     */
    private MyTextField tMinIveille8h;
    /**
     * Variable to hold value of MyTextField for Tmed of asleep8h
     */
    private MyTextField tMoyIveille8h;
    /**
     * Variable to hold value of MyTextField for Tmax of asleep8h
     */
    private MyTextField tMaxIveille8h;
    /**
     * Variable to hold value of MyTextField for Tmin of asleep21j
     */
    private MyTextField tMinIveille21j;
    /**
     * Variable to hold value of MyTextField for Tmed of asleep21j
     */
    private MyTextField tMoyIveille21j;
    /**
     * Variable to hold value of MyTextField for Tmax of asleep21j
     */
    private MyTextField tMaxIveille21j;
    /**
     * Variable to hold value of MyTextField for Tmin of asleep30j
     */
    private MyTextField tMinIveille30j;
    /**
     * Variable to hold value of MyTextField for Tmed of asleep30j
     */
    private MyTextField tMoyIveille30j;
    /**
     * Variable to hold value of MyTextField for Tmax of asleep30j
     */
    private MyTextField tMaxIveille30j;
    /**
     * Variable to hold value of MyTextField for Tmin of awake mode non functioning mode 15
     */
    private MyTextField tMinIreveilleInactif15;
    /**
     * Variable to hold value of MyTextField for Tmed of awake mode non functioning mode 15
     */
    private MyTextField tMoyIreveilleInactif15;
    /**
     * Variable to hold value of MyTextField for Tmax of awake mode non functioning mode 15
     */
    private MyTextField tMaxIreveilleInactif15;
    /**
     * Variable to hold value of MyTextField for Tmin of awake mode non functioning mode 10
     */
    private MyTextField tMinIreveilleInactif10;
    /**
     * Variable to hold value of MyTextField for Tmed of awake mode non functioning mode 10
     */
    private MyTextField tMoyIreveilleInactif10;
    /**
     * Variable to hold value of MyTextField for Tmax of awake mode non functioning mode 10
     */
    private MyTextField tMaxIreveilleInactif10;
    /**
     * Variable to hold value of MyTextField for Tmin of asleep
     */
    private MyTextField tMinIveille;
    /**
     * Variable to hold value of MyTextField for Tmed of asleep
     */
    private MyTextField tMoyIveille;
    /**
     * Variable to hold value of MyTextField for Tmax of asleep
     */
    private MyTextField tMaxIveille;
    /**
     * Variable to hold value of MyTextField for Tmin of awake mode non functioning mode
     */
    private MyTextField tMinIreveilleInactif;
    /**
     * Variable to hold value of MyTextField for Tmed of awake mode non functioning mode
     */
    private MyTextField tMoyIreveilleInactif;
    /**
     * Variable to hold value of MyTextField for Tmax of awake mode non functioning mode
     */
    private MyTextField tMaxIreveilleInactif;
    /**
     * Variable to hold value of MyTextField for Tmin of eco mode
     */
    private MyTextField tMinModeEco;
    /**
     * Variable to hold value of MyTextField for Tmed of eco mode
     */
    private MyTextField tMoyModeEco;
    /**
     * Variable to hold value of MyTextField for Tmax of eco moe
     */
    private MyTextField tMaxModeEco;
    /**
     * Variable to hold value of MyTextField for Tmin of awake mode non functioning mode 13
     */
    private MyTextField tMinIreveilleInactif13;
    /**
     * Variable to hold value of MyTextField for Tmed of awake mode non functioning mode 13
     */
    private MyTextField tMoyIreveilleInactif13;
    /**
     * Variable to hold value of MyTextField for Tmax of awake mode non functioning mode 13
     */
    private MyTextField tMaxIreveilleInactif13;
    /**
     * Variable to hold value of MyTextArea for Comment of aasleep8h
     */
    private MyTextArea comIveille8h;
    /**
     * Variable to hold value of MyTextArea for Comment of aasleep21j
     */
    private MyTextArea comIveille21j;
    /**
     * Variable to hold value of MyTextArea for Comment of aasleep30j
     */
    private MyTextArea comIveille30j;
    /**
     * Variable to hold value of MyTextArea for comment of awake mode non functioning mode 15
     */
    private MyTextArea comIreveilleInactif15;
    /**
     * Variable to hold value of MyTextArea for comment of awake mode non functioning mode 10
     */
    private MyTextArea comIreveilleInactif10;
    /**
     * Variable to hold value of MyTextArea for comment of asleep
     */
    private MyTextArea comIveille;
    /**
     * Variable to hold value of MyTextArea for comment of awake mode non functioning mode
     */
    private MyTextArea comIreveilleInactif;
    /**
     * Variable to hold value of MyTextArea for comment of eco mode
     */
    private MyTextArea comModeEco;
    /**
     * Variable to hold value of MyTextArea for comment of awake mode non functioning mode 13
     */
    private MyTextArea comIreveilleInactif13;
    /**
     * Variable to hold value of Label for Engine off
     */
    private Label vLBLmoteurNonTournant;
    /**
     * Variable to hold value of Label for Engine off
     */
    private Label vLBLmoteurTournant;

    /**
     * Variable to hold value of EdsReseauVeilleReveilleOrganeInactif
     */
    private EdsReseauVeilleReveilleOrganeInactif ervroi;

    /**
     * Initialize Edit form view for Asleep or awake network and inactive device (Customer mode)
     */
    private void init() {
        Label useCaseName = new Label(ervroi.getRvroiedsName() == null ? "-" : ervroi.getRvroiedsName());
        addComponent(useCaseName);

        vLBLmoteurNonTournant = new Label(controller.getBundle().getString("table-header-moteur-non-tournant-label"));
        vLBLmoteurTournant = new Label(controller.getBundle().getString("table-header-moteur-tournant-label"));
        tMinIveille = new MyTextField();
        tMoyIveille = new MyTextField();
        tMaxIveille = new MyTextField();
        tMinIveille8h = new MyTextField();
        tMoyIveille8h = new MyTextField();
        tMaxIveille8h = new MyTextField();
        tMinIveille21j = new MyTextField();
        tMoyIveille21j = new MyTextField();
        tMaxIveille21j = new MyTextField();
        tMinIveille30j = new MyTextField();
        tMoyIveille30j = new MyTextField();
        tMaxIveille30j = new MyTextField();
        tMinIreveilleInactif = new MyTextField();
        tMoyIreveilleInactif = new MyTextField();
        tMaxIreveilleInactif = new MyTextField();
        tMinModeEco = new MyTextField();
        tMoyModeEco = new MyTextField();
        tMaxModeEco = new MyTextField();
        tMinIreveilleInactif13 = new MyTextField();
        tMoyIreveilleInactif13 = new MyTextField();
        tMaxIreveilleInactif13 = new MyTextField();
        tMinIreveilleInactif15 = new MyTextField();
        tMoyIreveilleInactif15 = new MyTextField();
        tMaxIreveilleInactif15 = new MyTextField();
        tMinIreveilleInactif10 = new MyTextField();
        tMoyIreveilleInactif10 = new MyTextField();
        tMaxIreveilleInactif10 = new MyTextField();
        comIveille8h = new MyTextArea();
        comIveille21j = new MyTextArea();
        comIveille30j = new MyTextArea();
        comIveille = new MyTextArea();
        comIreveilleInactif = new MyTextArea();
        comModeEco = new MyTextArea();
        comIreveilleInactif13 = new MyTextArea();
        comIreveilleInactif15 = new MyTextArea();
        comIreveilleInactif10 = new MyTextArea();
        addComponent(getTable());
        Label info = new Label(controller.getBundle().getString("message-advice-no-eco-mode"), Label.CONTENT_XHTML);
        addComponent(info);
        discardChanges();

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * This method get Table for Asleep or awake network and inactive device (Customer mode)
     * 
     * @return Table for Asleep or awake network and inactive device (Customer mode)
     */
    public Table getTable() {
        int i = 5;
        MyTable table = new MyTable();
        table.setCellStyleGenerator(this);
        table.addContainer("0", Label.class, controller.getBundle().getString("table-header-etat-moteur-label"));
        table.addContainer("1", Label.class, controller.getBundle().getString("table-header-phase-vehicule-label"));
        table.addContainer("2", Label.class, controller.getBundle().getString("table-header-label-tension"));
        table.addContainer("3", MyTextField.class, controller.getBundle().getString("current-conso-tab-data-at-Tmin"));
        table.addContainer("4", MyTextField.class, controller.getBundle().getString("current-conso-tab-data-at-Tmoy"));
        table.addContainer("5", MyTextField.class, controller.getBundle().getString("current-conso-tab-data-at-Tmax"));
        table.addContainer("6", TextArea.class, controller.getBundle().getString("eds-comnent"));

        table.addItem(
                new Object[] { vLBLmoteurNonTournant, controller.getBundle().getString("current-conso-tab-data-prem-veil"),
                        getUmoy12_5(tension.getCsEdsUmoy(), bt), tMinIveille, tMoyIveille, tMaxIveille, comIveille }, new Integer(0));

        if (qcf.getQcfC1() == 1) {
            i = i + 3;
            table.addItem(
                    new Object[] { "", controller.getBundle().getString("current-conso-tab-data-prem-veil8H"),
                            getUmoy12_5(tension.getCsEdsUmoy(), bt), tMinIveille8h, tMoyIveille8h, tMaxIveille8h, comIveille8h }, new Integer(1));

            table.addItem(
                    new Object[] { "", controller.getBundle().getString("current-conso-tab-data-prem-veil21J"),
                            getUmoy12_5(tension.getCsEdsUmoy(), bt), tMinIveille21j, tMoyIveille21j, tMaxIveille21j, comIveille21j }, new Integer(2));
            table.addItem(
                    new Object[] { "", controller.getBundle().getString("current-conso-tab-data-prem-veil30J"),
                            getUmoy12_5(tension.getCsEdsUmoy(), bt), tMinIveille30j, tMoyIveille30j, tMaxIveille30j, comIveille30j }, new Integer(3));
        }
        table.addItem(new Object[] { "", controller.getBundle().getString("current-conso-tab-data-prem-rev"),
                getUmoy12_5(tension.getCsEdsUmoy(), bt), tMinIreveilleInactif, tMoyIreveilleInactif, tMaxIreveilleInactif, comIreveilleInactif },
                new Integer(4));

        table.addItem(
                new Object[] { "", controller.getBundle().getString("current-conso-tab-data-prem-mode-eco"), getUmoy12_5(tension.getCsEdsUmoy(), bt),
                        tMinModeEco, tMoyModeEco, tMaxModeEco, comModeEco }, new Integer(5));
        if (qcf.getQcfC3() == 1) {
            i++;
            table.addItem(new Object[] { "", "", super.getUmin(tension.getCsEdsUmin(), bt), tMinIreveilleInactif10, tMoyIreveilleInactif10,
                    tMaxIreveilleInactif10, comIreveilleInactif10 }, new Integer(6));
        }

        table.addItem(
                new Object[] { vLBLmoteurTournant, controller.getBundle().getString("current-conso-tab-data-prem-rev"),
                        getUmoy13_5(tension.getCsEdsUmoy(), bt), tMinIreveilleInactif13, tMoyIreveilleInactif13, tMaxIreveilleInactif13,
                        comIreveilleInactif13 }, new Integer(7));
        if (qcf.getQcfC2() == 1) {
            i++;
            table.addItem(new Object[] { "", "", super.getUmax(tension.getCsEdsUmax(), bt), tMinIreveilleInactif15, tMoyIreveilleInactif15,
                    tMaxIreveilleInactif15, comIreveilleInactif15 }, new Integer(8));
        }

        if (qcf.getQcf1() != 1) {
            table.setVisibleColumns(new Object[] { "0", "1", "2", "4", "6" });
        }

        table.setPageLength(i);
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
     * This method is called by Table when a cell (and row) is painted.
     * 
     * @param itemId The itemId of the painted cell
     * @param propertyId The propertyId of the cell, null when getting row style
     * @return The style name to add to this cell or row
     */
    public String getStyle(Object itemId, Object propertyId) {
        if (propertyId == null) {
            return "green";
        }
        int row = ((Integer) itemId).intValue();
        int col = Integer.parseInt((String) propertyId);

        if (row < 6 && col == 0) {
            return "c3";
        } else if (row % 2 == 0 && col > 0) {
            return "c5";
        } else if (row % 2 != 0 && col > 0) {
            return "c6";
        } else {
            return "white";
        }

    }

    /**
     * This method reset EdsQcf for all questions
     */
    private void resetQcf() {

        if (qcf.getQcf1() == 0) {
            resetQcf1();
        }
        if (qcf.getQcfC1() == 0) {
            resetQcfc1();
        }
        if (qcf.getQcfC2() == 0) {
            resetQcfc2();
        }
        if (qcf.getQcfC3() == 0) {
            resetQcfc3();
        }
    }

    /**
     * This method reset EdsQcf for 1st question
     */
    private void resetQcf1() {
        tMinIveille.setValue(null);
        tMinIveille8h.setValue(null);
        tMinIveille21j.setValue(null);
        tMinIveille30j.setValue(null);
        tMinIreveilleInactif.setValue(null);
        tMinModeEco.setValue(null);
        tMinIreveilleInactif10.setValue(null);
        tMinIreveilleInactif13.setValue(null);
        tMinIreveilleInactif15.setValue(null);

        tMaxIveille.setValue(null);
        tMaxIveille8h.setValue(null);
        tMaxIveille21j.setValue(null);
        tMaxIveille30j.setValue(null);
        tMaxIreveilleInactif.setValue(null);
        tMaxModeEco.setValue(null);
        tMaxIreveilleInactif10.setValue(null);
        tMaxIreveilleInactif13.setValue(null);
        tMaxIreveilleInactif15.setValue(null);

    }

    /**
     * This method reset EdsQcf for 1st question
     */
    private void resetQcfc1() {

        tMinIveille8h.setValue(null);
        tMinIveille21j.setValue(null);
        tMinIveille30j.setValue(null);

        tMoyIveille8h.setValue(null);
        tMoyIveille21j.setValue(null);
        tMoyIveille30j.setValue(null);

        tMaxIveille8h.setValue(null);
        tMaxIveille21j.setValue(null);
        tMaxIveille30j.setValue(null);

        comIveille8h.setText("");
        comIveille21j.setText("");
        comIveille30j.setText("");

    }

    /**
     * This method reset EdsQcf for 3rd question
     */
    private void resetQcfc3() {

        tMinIreveilleInactif10.setValue(null);
        tMoyIreveilleInactif10.setValue(null);
        tMaxIreveilleInactif10.setValue(null);
        comIreveilleInactif10.setText("");
    }

    /**
     * This method reset EdsQcf for 2nd question
     */
    private void resetQcfc2() {
        tMinIreveilleInactif15.setValue(null);
        tMoyIreveilleInactif15.setValue(null);
        tMaxIreveilleInactif15.setValue(null);
        comIreveilleInactif15.setText("");
    }

}
