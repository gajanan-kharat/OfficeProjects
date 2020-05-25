package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.MyTable;
import com.inetpsa.eds.application.content.eds.composants.TwinLabel;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsCourantEfficace;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsQcf;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;

/**
 * This class provide Read form view for Activation nominal current
 * 
 * @author Geometric Ltd.
 */
public class CourantNominaleActivationFormReadView extends A_CurrentConsumptionTable implements CellStyleGenerator {
    /**
     * Variable to hold value of MyTable
     */
    private MyTable table;
    /**
     * Variable to hold value of EdsCourantNominaleActivation
     */
    private EdsCourantNominaleActivation ecna;
    /**
     * Variable to hold value of EdsQcf
     */
    private EdsQcf qcf;
    /**
     * Variable to hold value of BT_TBT
     */
    private Boolean bt = false;
    /**
     * Constant to hold value of ORDER_DONNEE_PROPERTY_ID
     */
    public static final String ORDER_DONNEE_PROPERTY_ID = "table-header-etat-moteur-label";
    /**
     * Constant to hold value of ORDER_PHASE_PROPERTY_ID
     */
    public static final String ORDER_PHASE_PROPERTY_ID = "table-header-phase-vehicule-label";
    /**
     * Constant to hold value of ORDER_TENSION_PROPERTY_ID
     */
    public static final String ORDER_TENSION_PROPERTY_ID = "table-header-label-tension";
    /**
     * Constant to hold value of ORDER_TMIN_PROPERTY_ID
     */
    public static final String ORDER_TMIN_PROPERTY_ID = "current-conso-tab-data-at-Tmin";
    /**
     * Constant to hold value of ORDER_TMOY_PROPERTY_ID
     */
    public static final String ORDER_TMOY_PROPERTY_ID = "current-conso-tab-data-at-Tmoy";
    /**
     * Constant to hold value of ORDER_TMAX_PROPERTY_ID
     */
    public static final String ORDER_TMAX_PROPERTY_ID = "current-conso-tab-data-at-Tmax";
    /**
     * Constant to hold value of ORDER_COMMENT_PROPERTY_ID
     */
    public static final String ORDER_COMMENT_PROPERTY_ID = "eds-comnent";
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    private ConsolidateSupplyEdsTension tension;

    /**
     * Parameterized Constructor
     * 
     * @param ecna
     * @param qcf
     * @param controller
     * @param addTitle
     * @param tension (Umin, Umoy, Umax).
     * @param bt
     */
    public CourantNominaleActivationFormReadView(EdsCourantNominaleActivation ecna, EdsQcf qcf, EdsApplicationController controller,
            boolean addTitle, ConsolidateSupplyEdsTension tension, Boolean bt) {
        super(controller, ecna.getCnaedsName(), addTitle);
        this.controller = controller;
        this.ecna = ecna;
        this.qcf = qcf;
        this.tension = tension;
        this.bt = bt;
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param ecna Object of EdsCourantNominaleActivation
     * @param qcf Object of EdsQcf
     * @param controller Controller of EDS application
     */
    public CourantNominaleActivationFormReadView(EdsCourantNominaleActivation ecna, EdsQcf qcf, EdsApplicationController controller,
            boolean addTitle, Boolean bt) {
        this(ecna, qcf, controller, addTitle, new ConsolidateSupplyEdsTension(), bt);

    }

    // PROTECTED
    // PRIVATE

    /**
     * Initialize Read form view for Activation nominal current
     */
    private void init() {

        setSpacing(true);
        // Label title = new Label(ecna.getCnaedsName());
        // addComponent(title);
        addComponent(getCourantNominaleActivation());
        if (ecna.isCnaedsIEfficace()) {
            addComponent(getEfficaceTabel(ecna.getEdsCourantEfficace()));
        }
        Label info = new Label(controller.getBundle().getString("courant-nominal-message-1") + "<br>"
                + controller.getBundle().getString("courant-nominal-message-2"), Label.CONTENT_XHTML);
        addComponent(info);

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * This method returns Table for activation nominal current
     * 
     * @return Table for activation nominal current
     */
    public Table getCourantNominaleActivation() {

        table = new MyTable();
        table.setCellStyleGenerator(this);
        int i = 4;

        table.addContainer("0", String.class, controller.getBundle().getString((String) ORDER_DONNEE_PROPERTY_ID));
        table.addContainer("1", Label.class, controller.getBundle().getString((String) ORDER_PHASE_PROPERTY_ID));
        table.addContainer("2", String.class, controller.getBundle().getString((String) ORDER_TENSION_PROPERTY_ID));
        if (qcf.getQcf1() == 1) {
            table.addContainer("3", TwinLabel.class, controller.getBundle().getString((String) ORDER_TMIN_PROPERTY_ID));
        }
        table.addContainer("4", TwinLabel.class, controller.getBundle().getString((String) ORDER_TMOY_PROPERTY_ID));
        if (qcf.getQcf1() == 1) {
            table.addContainer("5", TwinLabel.class, controller.getBundle().getString((String) ORDER_TMAX_PROPERTY_ID));
        }

        table.addContainer("6", String.class, controller.getBundle().getString((String) ORDER_COMMENT_PROPERTY_ID));

        addOrderToContainer(table, controller.getBundle().getString("table-header-moteur-non-tournant-label"), new Label(controller.getBundle()
                .getString("current-conso-tab-data-prem-nom") + "<br>" + controller.getBundle().getString("current-conso-tab-data-rob-TnomStab"),
                Label.CONTENT_XHTML), getUmoy12_5(tension.getCsEdsUmoy(), bt), ecna.getCnaedsIminStabMnt(), ecna.getCnaedsTminStabMnt(),
                ecna.getCnaedsImoyStabMnt(), ecna.getCnaedsTmoyStabMnt(), ecna.getCnaedsImaxStabMnt(), ecna.getCnaedsTmaxStabMnt(),
                ecna.getCnaedsStabMntComment());

        addOrderToContainer(table, "", new Label(controller.getBundle().getString("current-conso-tab-data-prem-pire") + "**<br>"
                + controller.getBundle().getString("current-conso-tab-data-tpirecas"), Label.CONTENT_XHTML), getUmoy12_5(tension.getCsEdsUmoy(), bt),
                ecna.getCnaedsIminPireCasMnt(), ecna.getCnaedsTminPireCasMnt(), ecna.getCnaedsImoyPireCasMnt(), ecna.getCnaedsTmoyPireCasMnt(),
                ecna.getCnaedsImaxPireCasMnt(), ecna.getCnaedsTmaxPireCasMnt(), ecna.getCnaedsPireCasMntComment());

        if (qcf.getQcfC3() == 1) {
            i++;
            addOrderToContainer(table, "", new Label(), getUmin(tension.getCsEdsUmin(), bt), ecna.getCnaedsIminStab10Mt(),
                    ecna.getCnaedsTminStab10Mt(), ecna.getCnaedsImoyStab10Mt(), ecna.getCnaedsTmoyStab10Mt(), ecna.getCnaedsImaxStab10Mt(),
                    ecna.getCnaedsTmaxStab10Mt(), ecna.getCnaedsStab10MtComment());
        }
        addOrderToContainer(table, controller.getBundle().getString("table-header-moteur-tournant-label"), new Label(controller.getBundle()
                .getString("current-conso-tab-data-prem-nom") + "<br>" + controller.getBundle().getString("current-conso-tab-data-rob-TnomStab"),
                Label.CONTENT_XHTML), getUmoy13_5(tension.getCsEdsUmoy(), bt), ecna.getCnaedsIminStab13Mt(), ecna.getCnaedsTminStab13Mt(),
                ecna.getCnaedsImoyStab13Mt(), ecna.getCnaedsTmoyStab13Mt(), ecna.getCnaedsImaxStab13Mt(), ecna.getCnaedsTmaxStab13Mt(),
                ecna.getCnaedsStab13MtComment());
        if (qcf.getQcfC2() == 1) {
            i++;
            addOrderToContainer(table, "", new Label(), getUmax(tension.getCsEdsUmax(), bt), ecna.getCnaedsIminStab15Mt(),
                    ecna.getCnaedsTminStab15Mt(), ecna.getCnaedsImoyStab15Mt(), ecna.getCnaedsTmoyStab15Mt(), ecna.getCnaedsImaxStab15Mt(),
                    ecna.getCnaedsTmaxStab15Mt(), ecna.getCnaedsStab15MtComment());
        }
        if (qcf.getQcfC3() == 1) {
            i++;
            addOrderToContainer(table, "", new Label(), getUmin(tension.getCsEdsUmin(), bt), ecna.getCnaedsIminPireCas10Mt(),
                    ecna.getCnaedsTminPireCas10Mt(), ecna.getCnaedsImoyPireCas10Mt(), ecna.getCnaedsTmoyPireCas10Mt(),
                    ecna.getCnaedsImaxPireCas10Mt(), ecna.getCnaedsTmaxPireCas10Mt(), ecna.getCnaedsPireCas10MtComment());
        }
        addOrderToContainer(table, "", new Label(controller.getBundle().getString("current-conso-tab-data-prem-pire") + "**<br>"
                + controller.getBundle().getString("current-conso-tab-data-tpirecas"), Label.CONTENT_XHTML), getUmoy13_5(tension.getCsEdsUmoy(), bt),
                ecna.getCnaedsIminPireCas13Mt(), ecna.getCnaedsTminPireCas13Mt(), ecna.getCnaedsImoyPireCas13Mt(), ecna.getCnaedsTmoyPireCas13Mt(),
                ecna.getCnaedsImaxPireCas13Mt(), ecna.getCnaedsTmaxPireCas13Mt(), ecna.getCnaedsPireCas13MtComment());

        if (qcf.getQcfC2() == 1) {
            i++;
            addOrderToContainer(table, "", new Label(), getUmax(tension.getCsEdsUmax(), bt), ecna.getCnaedsIminPireCas15Mt(),
                    ecna.getCnaedsTminPireCas15Mt(), ecna.getCnaedsImoyPireCas15Mt(), ecna.getCnaedsTmoyPireCas15Mt(),
                    ecna.getCnaedsImaxPireCas15Mt(), ecna.getCnaedsTmaxPireCas15Mt(), ecna.getCnaedsPireCas15MtComment());
        }
        if (qcf.getQcfC4() == 1) {
            i++;
            addOrderToContainer(table, controller.getBundle().getString("table-header-conso-demarrage-label"), new Label(controller.getBundle()
                    .getString("current-conso-tab-data-InomDem") + "<br>" + controller.getBundle().getString("current-conso-tab-data-TnomDem"),
                    Label.CONTENT_XHTML), controller.getBundle().getString("current-conso-tab-data-rob-Udem"), null, null,
                    ecna.getCnaedsImoyPireCas15Mt(), ecna.getCnaedsTmoyDem(), null, null, ecna.getCnaedsDemComment());
        }

        table.setPageLength(i);
        return table;
    }

    /**
     * This method add items in container in the given order
     * 
     * @param table MyTabel
     * @param donnee Data
     * @param phase Phase
     * @param tension Voltage
     * @param imin Imin
     * @param tmin Tmin
     * @param imoy Imed
     * @param tmoy Tmed
     * @param imax Imax
     * @param tmax Tmax
     * @param comment Notes
     */
    public void addOrderToContainer(MyTable table, String donnee, Label phase, String tension, Float imin, Float tmin, Float imoy, Float tmoy,
            Float imax, Float tmax, String comment) {

        Object itemId = table.addItem();
        Item item = table.getItem(itemId);
        item.getItemProperty("0").setValue(donnee);
        item.getItemProperty("1").setValue(phase);
        item.getItemProperty("2").setValue(tension);
        if (qcf.getQcf1() == 1) {
            TwinLabel tLMin = new TwinLabel();
            tLMin.setValue(imin, tmin);
            item.getItemProperty("3").setValue(tLMin);
        }
        TwinLabel tLMoy = new TwinLabel();
        tLMoy.setValue(imoy, tmoy);
        item.getItemProperty("4").setValue(tLMoy);
        if (qcf.getQcf1() == 1) {
            TwinLabel tLMax = new TwinLabel();
            tLMax.setValue(imax, tmax);
            item.getItemProperty("5").setValue(tLMax);
        }
        item.getItemProperty("6").setValue(comment);
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

        if ((row == 1 || row == 2) && col < 1) {
            return "c1";
        } else if (row > 2 && row < 9 && col < 1) {
            return "c2";
        } else if (row == 9 && col < 2) {
            return "c5";
        } else if (row == 1 && col == 1) {
            return "c3";
        } else if (row == 2 && col == 1) {
            return "c4";
        } else if (row > 2 && row < 6 && col == 1) {
            return "c3";
        } else if (row > 5 && row < 9 && col == 1) {
            return "c4";
        } else if (row % 2 == 0 && col > 1) {
            return "c5";
        } else if (row % 2 != 0 && col > 1) {
            return "c6";
        } else {
            return "white";
        }

    }

    /**
     * This method show component for Effective current Table
     * 
     * @param ece Object of EdsCourantEfficace
     * @return component for Effective current Table
     */
    private Component getEfficaceTabel(EdsCourantEfficace ece) {
        MyTable vTEfficace = new MyTable();
        vTEfficace.setPageLength(4);
        vTEfficace.addContainer("0", String.class, controller.getBundle().getString(ORDER_DONNEE_PROPERTY_ID));
        vTEfficace.addContainer("1", Label.class, controller.getBundle().getString(ORDER_PHASE_PROPERTY_ID));
        vTEfficace.addContainer("2", String.class, controller.getBundle().getString(ORDER_TENSION_PROPERTY_ID));
        if (qcf.getQcf1() == 1) {
            vTEfficace.addContainer("3", TwinLabel.class, controller.getBundle().getString(ORDER_TMIN_PROPERTY_ID));
        }
        vTEfficace.addContainer("4", TwinLabel.class, controller.getBundle().getString(ORDER_TMOY_PROPERTY_ID));
        if (qcf.getQcf1() == 1) {
            vTEfficace.addContainer("5", TwinLabel.class, controller.getBundle().getString(ORDER_TMAX_PROPERTY_ID));
        }

        vTEfficace.addContainer("6", String.class, controller.getBundle().getString(ORDER_COMMENT_PROPERTY_ID));

        addOrderToContainer(vTEfficace, controller.getBundle().getString("table-header-moteur-non-tournant-label"), new Label(controller.getBundle()
                .getString("current-conso-tab-data-Ieff") + "<br>" + controller.getBundle().getString("current-conso-tab-data-Teff"),
                Label.CONTENT_XHTML), getUmoy12_5(tension.getCsEdsUmoy(), bt), ece.getCeedsTminIeff12(), ece.getCeedsTminTeff12(),
                ece.getCeedsTmoyIeff12(), ece.getCeedsTmoyTeff12(), ece.getCeedsTmaxIeff12(), ece.getCeedsTmaxTeff12(), ece.getCeedsCom12v());

        addOrderToContainer(vTEfficace, " ", null, getUmin(tension.getCsEdsUmin(), bt), ece.getCeedsTminIeff10(), null, ece.getCeedsTmoyIeff10(),
                null, ece.getCeedsTmaxIeff10(), null, ece.getCeedsCom10v());

        addOrderToContainer(vTEfficace, controller.getBundle().getString("table-header-moteur-tournant-label"), new Label(controller.getBundle()
                .getString("current-conso-tab-data-Ieff"), Label.CONTENT_XHTML), getUmoy13_5(tension.getCsEdsUmoy(), bt), ece.getCeedsTminIeff13(),
                null, ece.getCeedsTmoyIeff13(), null, ece.getCeedsTmaxIeff13(), null, ece.getCeedsCom13v());

        addOrderToContainer(vTEfficace, " ", null, getUmin(tension.getCsEdsUmin(), bt), ece.getCeedsTminIeff15(), null, ece.getCeedsTmoyIeff15(),
                null, ece.getCeedsTmaxIeff15(), null, ece.getCeedsCom15v());

        vTEfficace.setCellStyleGenerator(new CellStyleGenerator() {
            public String getStyle(Object itemId, Object propertyId) {
                if (propertyId == null) {
                    return "green";
                }
                int row = ((Integer) itemId).intValue();
                int col = Integer.parseInt((String) propertyId);
                if (row == 1) {
                    return "c1";
                } else {
                    return "white";
                }
            }
        });

        return vTEfficace;
    }

}
