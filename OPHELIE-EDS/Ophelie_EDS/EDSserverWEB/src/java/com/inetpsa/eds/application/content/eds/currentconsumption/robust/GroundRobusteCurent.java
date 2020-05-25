package com.inetpsa.eds.application.content.eds.currentconsumption.robust;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsGroundRobustCurent;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

/**
 * This class provide Layout for Ground of Robust stage Current consumption. This layout opens when we add new ground using Ground monitor of the
 * device as "other"
 * 
 * @author Geometric Ltd.
 */
public class GroundRobusteCurent extends GridLayout {
    // PUBLIC
    /**
     * Default constructor
     */
    public GroundRobusteCurent() {
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param edsGRC Object of EdsGroundRobustCurent
     * @param controller Controller of EDS application
     * @param edsRobustCurentFormData Object of EdsRobustCurentFormData
     */
    public GroundRobusteCurent(EdsGroundRobustCurent edsGRC, EdsApplicationController controller, EdsRobustCurentFormData edsRobustCurentFormData) {
        this.edsGRC = edsGRC;
        this.controller = controller;
        this.edsRobustCurentFormData = edsRobustCurentFormData;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsGroundRobustCurent
     */
    private EdsGroundRobustCurent edsGRC;
    /**
     * Variable to hold value of EdsRobustCurentFormData
     */
    private EdsRobustCurentFormData edsRobustCurentFormData;
    /**
     * Variable to hold value of Label for title
     */
    private Label vLTitre;
    /**
     * Variable to hold value of Label for Choice
     */
    private Label vLChoix;
    /**
     * Variable to hold value of Label for title value
     */
    private Label vLTitreVal;
    /**
     * Variable to hold value of Label for Choice value
     */
    private Label vLChoixVal;
    /**
     * Variable to hold value of Table
     */
    private Table vTMasse;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Constant to hold value of ORDER_DONNEE_PROPERTY_ID
     */
    public static final Object ORDER_DONNEE_PROPERTY_ID = "CSE-data";
    /**
     * Constant to hold value of ORDER_VALEUR_PROPERTY_ID
     */
    public static final Object ORDER_VALEUR_PROPERTY_ID = "current-conso-tab-gen-val";
    /**
     * Constant to hold value of ORDER_INFORMBY_PROPERTY_ID
     */
    public static final Object ORDER_INFORMBY_PROPERTY_ID = "current-conso-tab-gen-by";
    /**
     * Constant to hold value of ORDER_COMMENT_PROPERTY_ID
     */
    public static final Object ORDER_COMMENT_PROPERTY_ID = "eds-comnent";

    /**
     * initialize Layout for Ground of Robust stage Current consumption
     */
    private void init() {
        setColumns(2);
        setRows(3);
        setSpacing(true);
        setMargin(true);
        setWidth("100%");
        setColumnExpandRatio(0, 0f);
        setColumnExpandRatio(1, 1f);

        vLChoix = new Label(controller.getBundle().getString("current-conso-tab-mass-rob-choose-is") + " : ");
        vLChoix.setWidth("250");
        addComponent(vLChoix, 0, 0);

        vLChoixVal = new Label(controller.getBundle().getString(EdsRobustCurentFormData.CHOIX_MASSES.get(edsRobustCurentFormData.getRcChoixMasse())));
        addComponent(vLChoixVal, 1, 0);

        vLTitre = new Label(controller.getBundle().getString("current-conso-tab-mass-rob-mass-intit") + " :");
        addComponent(vLTitre, 0, 1);

        vLTitreVal = new Label(edsGRC.getGsedsTitreMasse());
        addComponent(vLTitreVal, 1, 1);

        vTMasse = new Table();
        vTMasse.setPageLength(13);
        vTMasse.setWidth("100%");
        vTMasse.addContainerProperty(controller.getBundle().getString((String) ORDER_DONNEE_PROPERTY_ID), String.class, "");
        vTMasse.addContainerProperty(controller.getBundle().getString((String) ORDER_VALEUR_PROPERTY_ID), Float.class, 0);
        vTMasse.addContainerProperty(controller.getBundle().getString((String) ORDER_INFORMBY_PROPERTY_ID), String.class, "$0");
        vTMasse.addContainerProperty(controller.getBundle().getString((String) ORDER_COMMENT_PROPERTY_ID), String.class, "$0");

        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-prem-veil"), edsGRC.getGsedsIVeille(),
                edsGRC.getGsedsIVeilleIformBy(), edsGRC.getGsedsIVeilleComment());
        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-prem-rev"), edsGRC.getGsedsIReveilleInactif(),
                edsGRC.getGsedsReveilleIformBy(), edsGRC.getGsedsReveilleComment());
        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-prem-nom"), edsGRC.getGsedsINomStab(),
                edsGRC.getGsedsINomStabIformBy(), edsGRC.getGsedsINomStabComment());
        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-rob-Tnom-stab"), edsGRC.getGsedsTnomStab(),
                edsGRC.getGsedsTnomStabInformBy(), edsGRC.getGsedsTnomStabComment());
        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-prem-pire"), edsGRC.getGsedsIPirecasStab(),
                edsGRC.getGsedsIPirecasStabIformBy(), edsGRC.getGsedsIPirecasComment());
        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-rob-Tpire-cas"), edsGRC.getGsedsTpireCas(),
                edsGRC.getGsedsTpireCasInformBy(), edsGRC.getGsedsTpireCasComment());
        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-rob-Idysf"), edsGRC.getGsedsIdysf(),
                edsGRC.getGsedsIdysfInformBy(), edsGRC.getGsedsIdysfComment());
        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-rob-Tdysf"), edsGRC.getGsedsTdysf(),
                edsGRC.getGsedsTdysfInformBy(), edsGRC.getGsedsTdysfComment());
        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-rob-Imst"), edsGRC.getGsedsImst(),
                edsGRC.getGsedsImstInformBy(), edsGRC.getGsedsImstComment());
        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-rob-Tmst"), edsGRC.getGsedsTmst(),
                edsGRC.getGsedsTmstInformBy(), edsGRC.getGsedsTmstComment());
        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-rob-pic"), edsGRC.getGsedsIPic(),
                edsGRC.getGsedsIPicIformBy(), edsGRC.getGsedsIPicComment());
        addOrderToContainer(vTMasse, controller.getBundle().getString("current-conso-tab-data-rob-Tpic"), edsGRC.getGsedsTIpic(),
                edsGRC.getGsedsTIPicIformBy(), edsGRC.getGsedsTIPicComment());

        // vTMasse.
        addComponent(vTMasse, 0, 2, 1, 2);

    }

    /**
     * This method add items in container in the given order
     * 
     * @param container Object of Container
     * @param donnee Data
     * @param valeur Value
     * @param iformedBy Edited By
     * @param comment Notes
     */
    private void addOrderToContainer(Container container, String donnee, Float valeur, String iformedBy, String comment) {
        Object itemId = container.addItem();
        Item item = container.getItem(itemId);
        item.getItemProperty(controller.getBundle().getString((String) ORDER_DONNEE_PROPERTY_ID)).setValue(donnee);
        item.getItemProperty(controller.getBundle().getString((String) ORDER_VALEUR_PROPERTY_ID)).setValue(valeur);
        item.getItemProperty(controller.getBundle().getString((String) ORDER_INFORMBY_PROPERTY_ID)).setValue(iformedBy);
        item.getItemProperty(controller.getBundle().getString((String) ORDER_COMMENT_PROPERTY_ID)).setValue(comment);
    }
}
