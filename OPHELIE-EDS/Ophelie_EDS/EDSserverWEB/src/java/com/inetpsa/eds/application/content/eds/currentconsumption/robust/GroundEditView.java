package com.inetpsa.eds.application.content.eds.currentconsumption.robust;

import java.util.Collection;
import java.util.Collections;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ConsumptionTableEdit;
import com.inetpsa.eds.dao.model.EdsGround;
import com.inetpsa.eds.dao.model.EdsGroundRobustCurent;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.inetpsa.eds.dao.model.EdsRobustSupply;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This abstract class provide Ground Edit view
 * 
 * @author Geometric Ltd.
 */
public abstract class GroundEditView extends A_EdsEditForm implements Button.ClickListener {
    // PRIVE
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsGroundRobustCurent
     */
    private EdsGroundRobustCurent ground;
    /**
     * Variable to hold value of EdsGround
     */
    private EdsGround edsGround;
    /**
     * Variable to hold value of EdsGroundRobustCurent
     */
    private EdsGroundRobustCurent edsGroundRobustCurent;
    /**
     * Variable to hold value of EdsRobustCurentFormData
     */
    private EdsRobustCurentFormData ercfd;
    /**
     * Variable to hold value of EdsRobustSupply
     */
    private EdsRobustSupply edsRobustSupply;
    /**
     * Variable to hold value of GridLayout
     */
    private GridLayout mainLayout;
    /**
     * Variable to hold value of TextField for Iasleep (A)
     */
    private TextField vTFIveille;
    /**
     * Variable to hold value of TextField for Iawake mode non functioning mode(A)
     */
    private TextField vTFIrveilleInactif;
    /**
     * Variable to hold value of TextField for Inom Stab (A)
     */
    private TextField vTFInomStab;
    /**
     * Variable to hold value of TextField Iworst stab (A)
     */
    private TextField vTFIpireCas;
    /**
     * Variable to hold value of TextField for Ipeak (A) (Inrush Current)
     */
    private TextField vTFIpic;
    /**
     * Variable to hold value of TextField for Tpeak (s) (inrush current duration)
     */
    private TextField vTFTIpic;
    /**
     * Variable to hold value of TextField for Iasleep (A) Edited By
     */
    private TextField vTFIveilleRenseigner;
    /**
     * Variable to hold value of TextField for Iawake mode non functioning mode(A) Edited By
     */
    private TextField vTFIrveilleInactifRenseigner;
    /**
     * Variable to hold value of TextField Inom Stab (A) Edited By
     */
    private TextField vTFInomStabRenseigner;
    /**
     * Variable to hold value of TextField Iworst stab (A) Edited By
     */
    private TextField vTFIpireCasRenseigner;
    /**
     * Variable to hold value of TextField for Ipeak (A) (Inrush Current) Edited By
     */
    private TextField vTFIpicRenseigner;
    /**
     * Variable to hold value of TextField for Tpeak (s) (inrush current duration) Edited By
     */
    private TextField vTFTIpicRenseigner;
    /**
     * Variable to hold value of TextField for Iasleep (A) comment
     */
    private TextField vTFIveilleCom;
    /**
     * Variable to hold value of TextField for Iawake mode non functioning mode(A) comment
     */
    private TextField vTFIrveilleInactifCom;
    /**
     * Variable to hold value of TextField for Inom Stab (A) comment
     */
    private TextField vTFInomStabCom;
    /**
     * Variable to hold value of TextField for Iworst stab (A) comment
     */
    private TextField vTFIpireCasCom;
    /**
     * Variable to hold value of TextField for Ipeak (A) (Inrush Current) comment
     */
    private TextField vTFIpicCom;
    /**
     * Variable to hold value of TextField for Tpeak (s) (inrush current duration) comment
     */
    private TextField vTFTIpicCom;

    /**
     * Variable to hold value of TextField for Tnom stab
     */
    private TextField vTnomStab;
    /**
     * Variable to hold value of Label for Tnom stab Edited By
     */
    private TextField vTnomStabRenseigner;
    /**
     * Variable to hold value of TextField for Tnom stab Notes
     */
    private TextField vTnomStabCom;
    /**
     * Variable to hold value of TextField for TpireCas
     */
    private TextField vTpireCas;
    /**
     * Variable to hold value of Label for TpireCas stab Edited By
     */
    private TextField vTpireCasRenseigner;
    /**
     * Variable to hold value of TextField for TpireCas Notes
     */
    private TextField vTpireCasCom;
    /**
     * Variable to hold value of TextField for Idysf
     */
    private TextField vIdysf;
    /**
     * Variable to hold value of Label for Idysf stab Edited By
     */
    private TextField vIdysfRenseigner;
    /**
     * Variable to hold value of TextField for Tdysf Notes
     */
    private TextField vIdysfCom;
    /**
     * Variable to hold value of TextField for Idysf
     */
    private TextField vTdysf;
    /**
     * Variable to hold value of Label for Tdysf stab Edited By
     */
    private TextField vTdysfRenseigner;
    /**
     * Variable to hold value of TextField for Tdysf Notes
     */
    private TextField vTdysfCom;
    /**
     * Variable to hold value of TextField for Imst
     */
    private TextField vImst;
    /**
     * Variable to hold value of Label for Imst stab Edited By
     */
    private TextField vImstRenseigner;
    /**
     * Variable to hold value of TextField for Imst Notes
     */
    private TextField vImstCom;
    /**
     * Variable to hold value of TextField for Tmst
     */
    private TextField vTmst;
    /**
     * Variable to hold value of Label for Tmst stab Edited By
     */
    private TextField vTmstRenseigner;
    /**
     * Variable to hold value of TextField for Tmst Notes
     */
    private TextField vTmstCom;
    /**
     * Variable to hold value of TextField for title for ground
     */
    private TextField vTFTitle;
    /**
     * Variable to hold value of TextField for choice of ground
     */
    private TextField vTFChoise;
    /**
     * Variable to hold value of Button
     */
    private Button vBDelete;
    /**
     * Variable to hold value of ConsumptionTableEdit
     */
    private ConsumptionTableEdit groundTableEdit;
    /**
     * Variable to hold value of String specifying user
     */
    private String user;

    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of Eds application
     * @param edsGroundRobustCurent Object of EdsGroundRobustCurent
     * @param edsRobustSupply Object of EdsRobustSupply
     * @param edsGround Object of EdsGround
     * @param ercfd Object of EdsRobustCurentFormData
     */
    public GroundEditView(EdsApplicationController controller, EdsGroundRobustCurent edsGroundRobustCurent, EdsRobustSupply edsRobustSupply,
            EdsGround edsGround, EdsRobustCurentFormData ercfd) {
        this.edsGroundRobustCurent = edsGroundRobustCurent;
        this.ercfd = ercfd;
        this.edsRobustSupply = edsRobustSupply;
        this.edsGround = edsGround;
        this.controller = controller;
        init();
    }

    /**
     * Initialize Ground Edit View
     */
    private void init() {
        this.user = controller.getAuthenticatedUser().getUFirstname() + " " + controller.getAuthenticatedUser().getULastname();

        mainLayout = new GridLayout(3, 4);
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setWidth("100%");

        mainLayout.setColumnExpandRatio(0, 0f);
        mainLayout.setColumnExpandRatio(1, 1f);

        groundTableEdit = new ConsumptionTableEdit(controller);
        groundTableEdit.setPageLength(6);
        groundTableEdit.setWidth("100%");

        Label vLTitle = new Label(controller.getBundle().getString("current-conso-tab-mass-rob-mass-intit"));
        vLTitle.setWidth("230");
        mainLayout.addComponent(vLTitle, 0, 0);

        vTFTitle = new TextField();
        vTFTitle.setWidth("300");
        mainLayout.addComponent(vTFTitle, 1, 0);

        mainLayout.addComponent(new Label(controller.getBundle().getString("current-conso-tab-mass-rob-choose-is") + " : "), 0, 1);

        vTFChoise = new TextField();
        vTFChoise.setWidth("500");
        vTFChoise.setEnabled(false);
        mainLayout.addComponent(vTFChoise, 1, 1);

        vTFIveille = new TextField();
        vTFIveilleRenseigner = new TextField();
        vTFIveilleCom = new TextField();
        vTFIveilleCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-veil"), vTFIveille,
                vTFIveilleRenseigner, vTFIveilleCom);

        vTFIrveilleInactif = new TextField();
        vTFIrveilleInactifRenseigner = new TextField();
        vTFIrveilleInactifCom = new TextField();
        vTFIrveilleInactifCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-rev"), vTFIrveilleInactif,
                vTFIrveilleInactifRenseigner, vTFIrveilleInactifCom);

        vTFInomStab = new TextField();
        vTFInomStabRenseigner = new TextField();
        vTFInomStabCom = new TextField();
        vTFInomStabCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-nom"), vTFInomStab,
                vTFInomStabRenseigner, vTFInomStabCom);

        vTnomStab = new TextField();
        vTnomStabRenseigner = new TextField();
        vTnomStabCom = new TextField();
        vTnomStabCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tnom-stab"), vTnomStab,
                vTnomStabRenseigner, vTnomStabCom);

        vTFIpireCas = new TextField();
        vTFIpireCasRenseigner = new TextField();
        vTFIpireCasCom = new TextField();
        vTFIpireCasCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-pire"), vTFIpireCas,
                vTFIpireCasRenseigner, vTFIpireCasCom);

        vTpireCas = new TextField();
        vTpireCasRenseigner = new TextField();
        vTpireCasCom = new TextField();
        vTpireCasCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tpire-cas"), vTpireCas,
                vTpireCasRenseigner, vTpireCasCom);

        vIdysf = new TextField();
        vIdysfRenseigner = new TextField();
        vIdysfCom = new TextField();
        vIdysfCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Idysf"), vIdysf,
                vIdysfRenseigner, vIdysfCom);

        vTdysf = new TextField();
        vTdysfRenseigner = new TextField();
        vTdysfCom = new TextField();
        vTdysfCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tdysf"), vTdysf,
                vTdysfRenseigner, vTdysfCom);

        vImst = new TextField();
        vImstRenseigner = new TextField();
        vImstCom = new TextField();
        vImstCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Imst"), vImst,
                vImstRenseigner, vImstCom);
        vTmst = new TextField();

        vTmstRenseigner = new TextField();
        vTmstCom = new TextField();
        vTmstCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tmst"), vTmst,
                vTmstRenseigner, vTmstCom);

        vTFIpic = new TextField();
        vTFIpicRenseigner = new TextField();
        vTFIpicCom = new TextField();
        vTFIpicCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-pic"), vTFIpic,
                vTFIpicRenseigner, vTFIpicCom);
        vTFTIpic = new TextField();
        vTFTIpicRenseigner = new TextField();
        vTFTIpicCom = new TextField();
        vTFTIpicCom.setMaxLength(256);
        groundTableEdit.addOrderToContainer(groundTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tpic"), vTFTIpic,
                vTFTIpicRenseigner, vTFTIpicCom);

        mainLayout.addComponent(groundTableEdit, 0, 2, 1, 2);

        addComponent(new Label("<hr />", Label.CONTENT_XHTML));

        vBDelete = new Button(null, this);
        vBDelete.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/erase.png "));
        vBDelete.setStyleName(BaseTheme.BUTTON_LINK);

        vBDelete.setDescription(controller.getBundle().getString("ground-btb-delete"));

        vBDelete.setVisible(false);
        mainLayout.addComponent(vBDelete, 2, 0);

        addComponent(mainLayout);
        discardChanges();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        if (!isFloat()) {
            showNotification(controller.getBundle().getString("eds-format-nombre-title"),
                    controller.getBundle().getString("ground-number-format-error"));
            return false;
        }
        if (vTFTitle.getValue() == null || vTFTitle.getValue().toString().equals("")) {
            showNotification(controller.getBundle().getString("eds-title-label"), controller.getBundle().getString("ground-title-error"));
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        if (isValid()) {

            edsGroundRobustCurent.setGsedsTitreMasse(vTFTitle.getValue().toString());

            if (edsGroundRobustCurent.getGsedsIVeille() != EDSTools.convertStringToFloat(vTFIveille.getValue().toString())) {
                edsGroundRobustCurent.setGsedsIVeilleIformBy(user);
            }
            if (edsGroundRobustCurent.getGsedsIReveilleInactif() != EDSTools.convertStringToFloat(vTFIrveilleInactif.getValue().toString())) {
                edsGroundRobustCurent.setGsedsReveilleIformBy(user);
            }
            if (edsGroundRobustCurent.getGsedsINomStab() != EDSTools.convertStringToFloat(vTFInomStab.getValue().toString())) {
                edsGroundRobustCurent.setGsedsINomStabIformBy(user);
            }
            if (edsGroundRobustCurent.getGsedsTnomStab() != EDSTools.convertStringToFloat(vTnomStabCom.getValue().toString())) {
                edsGroundRobustCurent.setGsedsTnomStabInformBy(user);
            }
            if (edsGroundRobustCurent.getGsedsIPirecasStab() != EDSTools.convertStringToFloat(vTFIpireCas.getValue().toString())) {
                edsGroundRobustCurent.setGsedsIPirecasStabIformBy(user);
            }
            if (edsGroundRobustCurent.getGsedsTpireCas() != EDSTools.convertStringToFloat(vTpireCas.getValue().toString())) {
                edsGroundRobustCurent.setGsedsIPirecasStabIformBy(user);
            }
            if (edsGroundRobustCurent.getGsedsTdysf() != EDSTools.convertStringToFloat(vTdysf.getValue().toString())) {
                edsGroundRobustCurent.setGsedsTdysfInformBy(user);
            }
            if (edsGroundRobustCurent.getGsedsTdysf() != EDSTools.convertStringToFloat(vTdysf.getValue().toString())) {
                edsGroundRobustCurent.setGsedsTdysfInformBy(user);
            }
            if (edsGroundRobustCurent.getGsedsIdysf() != EDSTools.convertStringToFloat(vIdysf.getValue().toString())) {
                edsGroundRobustCurent.setGsedsIdysfInformBy(user);
            }

            if (edsGroundRobustCurent.getGsedsTmst() != EDSTools.convertStringToFloat(vTmst.getValue().toString())) {
                edsGroundRobustCurent.setGsedsTmstInformBy(user);
            }
            if (edsGroundRobustCurent.getGsedsImst() != EDSTools.convertStringToFloat(vImst.getValue().toString())) {
                edsGroundRobustCurent.setGsedsImstInformBy(user);
            }

            if (edsGroundRobustCurent.getGsedsIPic() != EDSTools.convertStringToFloat(vTFIpic.getValue().toString())) {
                edsGroundRobustCurent.setGsedsIPicIformBy(user);
            }
            if (edsGroundRobustCurent.getGsedsTIpic() != EDSTools.convertStringToFloat(vTFTIpic.getValue().toString())) {
                edsGroundRobustCurent.setGsedsTIPicIformBy(user);
            }
            edsGroundRobustCurent.setGsedsIVeille(EDSTools.convertStringToFloat(vTFIveille.getValue().toString()));
            edsGroundRobustCurent.setGsedsIReveilleInactif(EDSTools.convertStringToFloat(vTFIrveilleInactif.getValue().toString()));
            edsGroundRobustCurent.setGsedsINomStab(EDSTools.convertStringToFloat(vTFInomStab.getValue().toString()));
            edsGroundRobustCurent.setGsedsTnomStab(EDSTools.convertStringToFloat(vTnomStab.getValue().toString()));
            edsGroundRobustCurent.setGsedsIPirecasStab(EDSTools.convertStringToFloat(vTFIpireCas.getValue().toString()));
            edsGroundRobustCurent.setGsedsTpireCas(EDSTools.convertStringToFloat(vTpireCas.getValue().toString()));
            edsGroundRobustCurent.setGsedsIdysf(EDSTools.convertStringToFloat(vIdysf.getValue().toString()));
            edsGroundRobustCurent.setGsedsTdysf(EDSTools.convertStringToFloat(vTdysf.getValue().toString()));
            edsGroundRobustCurent.setGsedsImst(EDSTools.convertStringToFloat(vImst.getValue().toString()));
            edsGroundRobustCurent.setGsedsTmst(EDSTools.convertStringToFloat(vTmst.getValue().toString()));
            edsGroundRobustCurent.setGsedsIPic(EDSTools.convertStringToFloat(vTFIpic.getValue().toString()));
            edsGroundRobustCurent.setGsedsTIpic(EDSTools.convertStringToFloat(vTFTIpic.getValue().toString()));

            edsGroundRobustCurent.setGsedsIVeilleComment(vTFIveilleCom.getValue().toString());
            edsGroundRobustCurent.setGsedsReveilleComment(vTFIrveilleInactifCom.getValue().toString());
            edsGroundRobustCurent.setGsedsINomStabComment(vTFInomStabCom.getValue().toString());
            edsGroundRobustCurent.setGsedsTnomStabComment(vTnomStabCom.getValue().toString());
            edsGroundRobustCurent.setGsedsIPirecasComment(vTFIpireCasCom.getValue().toString());
            edsGroundRobustCurent.setGsedsTpireCasComment(vTpireCasCom.getValue().toString());
            edsGroundRobustCurent.setGsedsIdysfComment(vIdysfCom.getValue().toString());
            edsGroundRobustCurent.setGsedsTdysfComment(vTdysfCom.getValue().toString());
            edsGroundRobustCurent.setGsedsImstComment(vImstCom.getValue().toString());
            edsGroundRobustCurent.setGsedsTmstComment(vTmstCom.getValue().toString());
            edsGroundRobustCurent.setGsedsIPicComment(vTFIpicCom.getValue().toString());
            edsGroundRobustCurent.setGsedsTIPicComment(vTFTIpicCom.getValue().toString());

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
        vTFTitle.setValue(edsGroundRobustCurent.getGsedsTitreMasse());
        vTFChoise.setValue(ercfd.getRcChoixMasse() == null ? "" : controller.getBundle().getString(
                EdsRobustCurentFormData.CHOIX_MASSES.get(ercfd.getRcChoixMasse())));

        vTFIveille.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsIVeille()));
        vTFIveilleRenseigner.setValue(edsGroundRobustCurent.getGsedsIVeilleIformBy());
        vTFIveilleCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsIVeilleComment()));

        vTFIrveilleInactif.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsIReveilleInactif()));
        vTFIrveilleInactifRenseigner.setValue(edsGroundRobustCurent.getGsedsReveilleIformBy());
        vTFIrveilleInactifCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsReveilleComment()));

        vTFInomStab.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsINomStab()));
        vTFInomStabRenseigner.setValue(edsGroundRobustCurent.getGsedsINomStabIformBy());
        vTFInomStabCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsINomStabComment()));

        vTnomStab.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsTnomStab()));
        vTnomStabRenseigner.setValue(edsGroundRobustCurent.getGsedsTnomStabInformBy());
        vTnomStabCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsTnomStabComment()));

        vTFIpireCas.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsIPirecasStab()));
        vTFIpireCasRenseigner.setValue(edsGroundRobustCurent.getGsedsIPirecasStabIformBy());
        vTFIpireCasCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsIPirecasComment()));

        vTpireCas.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsTpireCas()));
        vTpireCasRenseigner.setValue(edsGroundRobustCurent.getGsedsTpireCasInformBy());
        vTpireCasCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsTpireCasComment()));

        vTdysf.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsTdysf()));
        vTdysfRenseigner.setValue(edsGroundRobustCurent.getGsedsTdysfInformBy());
        vTdysfCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsTdysfComment()));

        vIdysf.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsIdysf()));
        vIdysfRenseigner.setValue(edsGroundRobustCurent.getGsedsIdysfInformBy());
        vIdysfCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsIdysfComment()));

        vImst.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsImst()));
        vImstRenseigner.setValue(edsGroundRobustCurent.getGsedsImstInformBy());
        vImstCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsImstComment()));

        vTmst.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsTmst()));
        vTmstRenseigner.setValue(edsGroundRobustCurent.getGsedsTmstInformBy());
        vTmstCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsTmstComment()));

        vTFIpic.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsIPic()));
        vTFIpicRenseigner.setValue(edsGroundRobustCurent.getGsedsIPicIformBy());
        vTFIpicCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsIPicComment()));

        vTFIpic.setValue(EDSTools.convertFloatToVide(edsGroundRobustCurent.getGsedsTIpic()));
        vTFIpicRenseigner.setValue(edsGroundRobustCurent.getGsedsTIPicIformBy());
        vTFIpicCom.setValue(notNullVal(edsGroundRobustCurent.getGsedsTIPicComment()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) edsGroundRobustCurent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete ()
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
     * This method set the values for ground editing view
     * 
     * @param choise Choice of ground
     * @param title Title of ground
     * @param Iveille Iasleep (A)
     * @param Irveille Iawake mode non functioning mode(A)
     * @param InomStab Inom Stab (A)
     * @param IpireCas Iworst stab (A)
     * @param Ipic Ipeak (A) (Inrush Current)
     * @param tIpic Tpeak (s) (inrush current duration)
     */
    public void setValue(String choise, String title, String Iveille, String Irveille, String InomStab, String TnomStab, String IpireCas,
            String tPireCas, String iDysf, String tDysf, String iMst, String tMst, String Ipic, String tIpic) {

        vTFChoise.setValue(choise);
        vTFTitle.setValue(title);
        vTFIveille.setValue(Iveille);
        vTFIrveilleInactif.setValue(Irveille);
        vTFInomStab.setValue(InomStab);
        vTFIpireCas.setValue(IpireCas);
        vTFIpic.setValue(Ipic);
        vTFTIpic.setValue(tIpic);

        vTnomStab.setValue(TnomStab);
        vTpireCas.setValue(tPireCas);
        vIdysf.setValue(iDysf);
        vTdysf.setValue(tDysf);
        vImst.setValue(iMst);
        vTmst.setValue(tMst);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractComponentContainer#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        vTFIveille.setEnabled(enabled);
        vTFIrveilleInactif.setEnabled(enabled);
        vTFInomStab.setEnabled(enabled);
        vTFIpireCas.setEnabled(enabled);
        vTFIpic.setEnabled(enabled);
        vTFTIpic.setEnabled(enabled);

        vIdysf.setEnabled(enabled);
        vTpireCas.setEnabled(enabled);
        vTdysf.setEnabled(enabled);
        vTnomStab.setEnabled(enabled);
        vTmst.setEnabled(enabled);
        vImst.setEnabled(enabled);

    }

    /**
     * This method check if values passed are float
     * 
     * @return check if values passed are float
     */
    private boolean isFloat() {
        try {
            toFloat(vTFIveille.getValue().toString());
            toFloat(vTFIrveilleInactif.getValue().toString());
            toFloat(vTFInomStab.getValue().toString());
            toFloat(vTFIpireCas.getValue().toString());
            toFloat(vTFIpic.getValue().toString());
            toFloat(vTFTIpic.getValue().toString());

            toFloat(vIdysf.getValue().toString());
            toFloat(vTdysf.getValue().toString());
            toFloat(vTnomStab.getValue().toString());
            toFloat(vTmst.getValue().toString());
            toFloat(vImst.getValue().toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * This method convert String to Float
     * 
     * @param val String value
     * @return Value in Float format
     */
    private Float toFloat(String val) {
        if (val == null || val.equals("")) {
            return Float.NaN;
        }

        return Float.parseFloat(val);
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
     * This method return not null value
     * 
     * @param val Value to check for null
     * @return Value
     */
    private String notNullVal(String val) {
        if (val == null) {
            return "";
        }
        return val;
    }

    /**
     * This method set visibility for delete button
     * 
     * @param b Check for visibility of button
     */
    void isDelete(boolean b) {
        vBDelete.setVisible(b);
    }

    /**
     * This method returns EdsGround
     * 
     * @return EdsGround
     */
    public EdsGround getEdsGround() {
        return edsGround;
    }

    /**
     * This method set EdsGround
     * 
     * @param edsGround EdsGround to be set
     */
    public void setEdsGround(EdsGround edsGround) {
        this.edsGround = edsGround;
    }
}
