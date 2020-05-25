package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel.I_Tensions;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.inetpsa.eds.dao.model.EdsReseauVeilleReveilleOrganeInactif;
import com.vaadin.data.Item;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

/**
 * This class provide Read form view for Asleep or awake network and inactive device (Customer mode)
 * 
 * @author Geometric Ltd.
 */
public class ReseauVeilleReveilleOrganeInactifFormReadView extends A_CurrentConsumptionTable implements Table.CellStyleGenerator, I_Tensions {
    /**
     * Variable to hold value of EdsReseauVeilleReveilleOrganeInactif
     */
    private EdsReseauVeilleReveilleOrganeInactif ervroi;
    /**
     * Variable to hold value of Mytable
     */
    private MyTable table;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcf;
    /**
     * Variable to hold value of BT_TBT
     */
    private Boolean bt = false;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Constant to hold value of ORDER_DONNEE_PROPERTY_ID
     */
    public static final String ORDER_DONNEE_PROPERTY_ID = "";

    /**
     * Constant to hold value of ORDER_PHASE_PROPERTY_ID
     */
    public static final String ORDER_PHASE_PROPERTY_ID = "Phase";
    /**
     * Constant to hold value of ORDER_TENSION_PROPERTY_ID
     */
    public static final String ORDER_TENSION_PROPERTY_ID = "Tension";
    /**
     * Constant to hold value of ORDER_TMIN_PROPERTY_ID
     */
    public static final String ORDER_TMIN_PROPERTY_ID = "Courant à T°min";
    /**
     * Constant to hold value of ORDER_TMOY_PROPERTY_ID
     */
    public static final String ORDER_TMOY_PROPERTY_ID = "Courant à T°moy";
    /**
     * Constant to hold value of ORDER_TMAX_PROPERTY_ID
     */
    public static final String ORDER_TMAX_PROPERTY_ID = "Courant à T°max";
    /**
     * Constant to hold value of ORDER_COMMENT_PROPERTY_ID
     */
    public static final String ORDER_COMMENT_PROPERTY_ID = "Commentaire";

    /**
     * Parameterized constructor
     * 
     * @param ervroi object of EdsReseauVeilleReveilleOrganeInactif
     * @param qcf Object of EdsQcf
     * @param controller Controller of EDS application
     * @param tensions
     * @param bt
     */
    public ReseauVeilleReveilleOrganeInactifFormReadView(EdsReseauVeilleReveilleOrganeInactif ervroi, EdsQcf qcf,
            EdsApplicationController controller, boolean addTitle, Boolean bt) {
        this(ervroi, qcf, controller, addTitle, new ConsolidateSupplyEdsTension(), bt);
    }

    /**
     * Parametrized constructor
     * 
     * @param ervroi
     * @param qcf
     * @param controller
     * @param addTitle
     * @param tension : The Object that contains Umin, Umoy and Umax.
     * @param bt
     */
    public ReseauVeilleReveilleOrganeInactifFormReadView(EdsReseauVeilleReveilleOrganeInactif ervroi, EdsQcf qcf,
            EdsApplicationController controller, boolean addTitle, ConsolidateSupplyEdsTension tension, Boolean bt) {
        super(controller, ervroi.getRvroiedsName(), addTitle);
        this.ervroi = ervroi;
        this.qcf = qcf;
        this.controller = controller;
        this.tension = tension;
        this.bt = bt;
        init();
    }

    /**
     * This method refreshes the view data
     */
    public void refreshViewData() {
    }

    // PROTECTED
    // PRIVATE
    private ConsolidateSupplyEdsTension tension;

    /**
     * Initialize Read form view for Asleep or awake network and inactive device (Customer mode)
     * 
     * @param tensions
     */
    private void init() {
        int i = 4;

        table = new MyTable();
        table.setCellStyleGenerator(this);

        table.addContainer("0", String.class, ORDER_DONNEE_PROPERTY_ID);
        table.addContainer("1", String.class, ORDER_PHASE_PROPERTY_ID);
        table.addContainer("2", String.class, ORDER_TENSION_PROPERTY_ID);
        if (qcf.getQcf1() == 1) {
            table.addContainer("3", String.class, ORDER_TMIN_PROPERTY_ID);
        }
        table.addContainer("4", String.class, ORDER_TMOY_PROPERTY_ID);
        if (qcf.getQcf1() == 1) {
            table.addContainer("5", String.class, ORDER_TMAX_PROPERTY_ID);
        }

        table.addContainer("6", String.class, ORDER_COMMENT_PROPERTY_ID);

        addOrderToContainer(controller.getBundle().getString("table-header-moteur-non-tournant-label"),
                controller.getBundle().getString("current-conso-tab-data-prem-veil"), getUmoy12_5(tension.getCsEdsUmoy(), bt),
                ervroi.getRvroiedsTminIveilleMoteurNonTourant(), ervroi.getRvroiedsTmoyIveilleMoteurNonTourant(),
                ervroi.getRvroiedsTmaxIveilleMoteurNonTourant(), ervroi.getRvroiedsIveilleMoteurNonTourantComment());

        if (qcf.getQcfC1() == 1) {
            i = i + 3;
            addOrderToContainer("", controller.getBundle().getString("current-conso-tab-data-prem-veil8H"), getUmoy12_5(tension.getCsEdsUmoy(), bt),
                    ervroi.getRvroiedsTminIveille8hMoteurNonTourant(), ervroi.getRvroiedsTmoyIveille8hMoteurNonTourant(),
                    ervroi.getRvroiedsTmaxIveille8hMoteurNonTourant(), ervroi.getRvroiedsIveille8hMoteurNonTourantComment());

            addOrderToContainer("", controller.getBundle().getString("current-conso-tab-data-prem-veil21J"), getUmoy12_5(tension.getCsEdsUmoy(), bt),
                    ervroi.getRvroiedsTminIveille21hMoteurNonTourant(), ervroi.getRvroiedsTmoyIveille21hMoteurNonTourant(),
                    ervroi.getRvroiedsTmaxIveille21hMoteurNonTourant(), ervroi.getRvroiedsIveille21hMoteurNonTourantComment());

            addOrderToContainer("", controller.getBundle().getString("current-conso-tab-data-prem-veil30J"), getUmoy12_5(tension.getCsEdsUmoy(), bt),
                    ervroi.getRvroiedsTminIveille30hMoteurNonTourant(), ervroi.getRvroiedsTmoyIveille30hMoteurNonTourant(),
                    ervroi.getRvroiedsTmaxIveille30hMoteurNonTourant(), ervroi.getRvroiedsIveille30hMoteurNonTourantComment());
        }
        addOrderToContainer("", controller.getBundle().getString("current-conso-tab-data-prem-rev"), getUmoy12_5(tension.getCsEdsUmoy(), bt),
                ervroi.getRvroiedsTminIreveilleInactifMoteurNonTourant(), ervroi.getRvroiedsTmoyIreveilleInactifMoteurNonTourant(),
                ervroi.getRvroiedsTmaxIreveilleInactifMoteurNonTourant(), ervroi.getRvroiedsIreveilleInactifMoteurNonTourantComment());

        addOrderToContainer("", controller.getBundle().getString("current-conso-tab-data-prem-mode-eco"), getUmoy12_5(tension.getCsEdsUmoy(), bt),
                ervroi.getRvroiedsTminImodeEcoMoteurNonTourant(), ervroi.getRvroiedsTmoyImodeEcoMoteurNonTourant(),
                ervroi.getRvroiedsTmaxImodeEcoMoteurNonTourant(), ervroi.getRvroiedsImodeEcoMoteurNonTourantComment());
        if (qcf.getQcfC3() == 1) {
            i++;
            addOrderToContainer("", "", getUmin(tension.getCsEdsUmin(), bt), ervroi.getRvroiedsTminIreveilleInactif10vMoteurTourant(),
                    ervroi.getRvroiedsTmoyIreveilleInactif10vMoteurTourant(), ervroi.getRvroiedsTmaxIreveilleInactif10vMoteurTourant(),
                    ervroi.getRvroiedsIreveilleInactif10vMoteurTourantComment());
        }

        addOrderToContainer(controller.getBundle().getString("table-header-moteur-tournant-label"),
                controller.getBundle().getString("current-conso-tab-data-prem-rev"), getUmoy13_5(tension.getCsEdsUmoy(), bt),
                ervroi.getRvroiedsTminIreveilleInactif13vMoteurTourant(), ervroi.getRvroiedsTmoyIreveilleInactif13vMoteurTourant(),
                ervroi.getRvroiedsTmaxIreveilleInactif13vMoteurTourant(), ervroi.getRvroiedsIreveilleInactif13vMoteurTourantComment());

        if (qcf.getQcfC2() == 1) {
            i++;
            addOrderToContainer("", "", getUmax(tension.getCsEdsUmax(), bt), ervroi.getRvroiedsTminIreveilleInactif15vMoteurTourant(),
                    ervroi.getRvroiedsTmoyIreveilleInactif15vMoteurTourant(), ervroi.getRvroiedsTmaxIreveilleInactif15vMoteurTourant(),
                    ervroi.getRvroiedsIreveilleInactif15vMoteurTourantComment());
        }

        table.setPageLength(i);
        addComponent(table);
        Label info = new Label(controller.getBundle().getString("message-advice-no-eco-mode"), Label.CONTENT_XHTML);
        addComponent(info);

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * This method refreshes Items
     */
    public void refreshItems() {
        // DO NOTHING
    }

    /**
     * This method add items in container in the given order
     * 
     * @param donnee Data
     * @param phase Phase
     * @param tension Tension
     * @param tmin Tmin
     * @param tmoy Tmoy
     * @param tmax Tmax
     * @param comment Notes
     */
    public void addOrderToContainer(String donnee, String phase, String tension, Float tmin, Float tmoy, Float tmax, String comment) {

        Object itemId = table.addItem();
        Item item = table.getItem(itemId);
        item.getItemProperty("0").setValue(donnee);
        item.getItemProperty("1").setValue(phase);
        item.getItemProperty("2").setValue(tension);
        if (qcf.getQcf1() == 1) {
            item.getItemProperty("3").setValue(tmin);
        }
        item.getItemProperty("4").setValue(tmoy);
        if (qcf.getQcf1() == 1) {
            item.getItemProperty("5").setValue(tmax);
        }
        item.getItemProperty("6").setValue(comment);
    }

    /**
     * Called by Table when a cell (and row) is painted.
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

        if (qcf.getQcfC1() == 1) {
            if (row < 7 && col == 0) {
                return "c1";
            }
        } else {
            if (row < 4 && col == 0) {
                return "c1";
            }
        }
        if (row % 2 == 0 && col > 0) {
            return "c5";
        } else if (row % 2 != 0 && col > 0) {
            return "c6";
        } else {
            return "white";
        }

    }

}
