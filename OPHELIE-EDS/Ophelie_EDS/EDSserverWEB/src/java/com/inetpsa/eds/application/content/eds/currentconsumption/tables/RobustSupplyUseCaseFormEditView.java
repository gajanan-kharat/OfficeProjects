package com.inetpsa.eds.application.content.eds.currentconsumption.tables;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsRobustSupplyUseCase;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This abstract class provide Edit form view for Use Case
 * 
 * @author Clément Fleury @ Alter Frame
 */
public abstract class RobustSupplyUseCaseFormEditView extends A_EdsEditForm implements Button.ClickListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // PUBLIC
    /**
     * Parameterized Constructor
     * 
     * @param useCase Object of EdsRobustSupplyUseCase
     * @param controller Controller of EDS application
     */
    public RobustSupplyUseCaseFormEditView(EdsRobustSupplyUseCase useCase, EdsApplicationController controller) {
        this.useCase = useCase;
        this.controller = controller;
        init();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        // use case name not empty
        if (vTFUCName.getValue().toString().equals("")) {
            showNotification(controller.getBundle().getString("eds-supply-usecase-name-error-message"), "");
            return false;
        }

        try {
            Float.parseFloat(vTFIveille.getValue().toString().equals("") ? "0" : vTFIveille.getValue().toString());
            Float.parseFloat(vTFIReveil.getValue().toString().equals("") ? "0" : vTFIReveil.getValue().toString());
            Float.parseFloat(vTFInomStab.getValue().toString().equals("") ? "0" : vTFInomStab.getValue().toString());
            Float.parseFloat(vTFIpirecas.getValue().toString().equals("") ? "0" : vTFIpirecas.getValue().toString());
            Float.parseFloat(vTFImax.getValue().toString().equals("") ? "0" : vTFImax.getValue().toString());
            Float.parseFloat(vTFTImax.getValue().toString().equals("") ? "0" : vTFTImax.getValue().toString());
            Float.parseFloat(vTFTIConsoParc.getValue().toString().equals("") ? "0" : vTFTIConsoParc.getValue().toString());
            // New values
            Float.parseFloat(vTnomStab.getValue().toString().equals("") ? "0" : vTnomStab.getValue().toString());
            Float.parseFloat(vTpireCas.getValue().toString().equals("") ? "0" : vTpireCas.getValue().toString());
            Float.parseFloat(vIdysf.getValue().toString().equals("") ? "0" : vIdysf.getValue().toString());
            Float.parseFloat(vTdysf.getValue().toString().equals("") ? "0" : vTdysf.getValue().toString());
            Float.parseFloat(vImst.getValue().toString().equals("") ? "0" : vImst.getValue().toString());
            Float.parseFloat(vTmst.getValue().toString().equals("") ? "0" : vTmst.getValue().toString());

            if (useCase.getRobustSupply().getRsedsTbtBt().equals("BT")) {
                Float.parseFloat(vTFUmin.getValue().toString().equals("") ? "0" : vTFUmin.getValue().toString());
                Float.parseFloat(vTFUmax.getValue().toString().equals("") ? "0" : vTFUmax.getValue().toString());
                Float.parseFloat(vTFUmoy.getValue().toString().equals("") ? "0" : vTFUmoy.getValue().toString());
                Float.parseFloat(vTFCarac.getValue().toString().equals("") ? "0" : vTFCarac.getValue().toString());
            }
        } catch (NumberFormatException e) {
            showNotification(controller.getBundle().getString("eds-format-nombre-title"), controller.getBundle().getString("alim-format-nombre-msg")
                    + " " + this.toString());
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        if (isValid()) {
            if (!(useCase.getRsucIVeille() == null ? "" : useCase.getRsucIVeille().toString()).equals(vTFIveille.getValue().toString())) {
                useCase.setRsucIVeilleIformBy(user);
            }

            if (!(useCase.getRsucIReveilInactif() == null ? "" : useCase.getRsucIReveilInactif().toString()).equals(vTFIReveil.getValue().toString())) {
                useCase.setRsucIReveilIformBy(user);
            }

            if (!(useCase.getRsucINomStab() == null ? "" : useCase.getRsucINomStab().toString()).equals(vTFInomStab.getValue().toString())) {
                useCase.setRsucINomStabIformBy(user);
            }

            if (!(useCase.getRsucIPirecasStab() == null ? "" : useCase.getRsucIPirecasStab().toString()).equals(vTFIpirecas.getValue().toString())) {
                useCase.setRsucIPirecasStabIformBy(user);
            }

            if (!(useCase.getRsucIMax() == null ? "" : useCase.getRsucIMax().toString()).equals(vTFImax.getValue().toString())) {
                useCase.setRsucIMaxInformBy(user);
            }
            if (!(useCase.getRsucTIMax() == null ? "" : useCase.getRsucTIMax().toString()).equals(vTFTImax.getValue().toString())) {
                useCase.setRsucTIMaxInformBy(user);
            }
            if (!(useCase.getRsucIConsoParc() == null ? "" : useCase.getRsucIConsoParc().toString()).equals(vTFTImax.getValue().toString())) {
                useCase.setRsucIConsoParcInformBy(user);
            }

            // New values
            if (!(useCase.getRsucTNomStab() == null ? "" : useCase.getRsucTNomStab().toString()).equals(vTnomStab.getValue().toString())) {
                useCase.setRsucTnomStabInformBy(user);
            }
            if (!(useCase.getRsucTPireCas() == null ? "" : useCase.getRsucTPireCas().toString()).equals(vTpireCas.getValue().toString())) {
                useCase.setRsucTpireCasInformBy(user);
            }
            if (!(useCase.getRsucIdysf() == null ? "" : useCase.getRsucIdysf().toString()).equals(vIdysf.getValue().toString())) {
                useCase.setRsucIdysfInformBy(user);
            }
            if (!(useCase.getRsucTdysf() == null ? "" : useCase.getRsucTdysf().toString()).equals(vTdysf.getValue().toString())) {
                useCase.setRsucTdysfInformBy(user);
            }
            if (!(useCase.getRsucImst() == null ? "" : useCase.getRsucImst().toString()).equals(vImst.getValue().toString())) {
                useCase.setRsucImstInformBy(user);
            }
            if (!(useCase.getRsucTmst() == null ? "" : useCase.getRsucTmst().toString()).equals(vTmst.getValue().toString())) {
                useCase.setRsucTmstInformBy(user);
            }

            if (useCase.getRobustSupply().getRsedsTbtBt().equals("BT")) {
                if (!(useCase.getRsucUMin() == null ? "" : useCase.getRsucUMin().toString()).equals(vTFUmin.getValue().toString())) {
                    useCase.setRsucUMinInformBy(user);
                }

                if (!(useCase.getRsucUMoy() == null ? "" : useCase.getRsucUMoy().toString()).equals(vTFUmoy.getValue().toString())) {
                    useCase.setRsucUMoyInformBy(user);
                }

                if (!(useCase.getRsucUMax() == null ? "" : useCase.getRsucUMax().toString()).equals(vTFUmax.getValue().toString())) {
                    useCase.setRsucUMaxInformBy(user);
                }

                if (!(useCase.getRsucUCaracValue() == null ? "" : useCase.getRsucUCaracValue().toString()).equals(vTFCarac.getValue().toString())) {
                    useCase.setRsucUCaracInformBy(user);
                }

                useCase.setRsucUMinComment(vTFUminCom.getValue().toString());
                useCase.setRsucUMoyComment(vTFUmoyCom.getValue().toString());
                useCase.setRsucUMaxComment(vTFUmaxCom.getValue().toString());
                useCase.setRsucUCaracComment(vTFCaracCom.getValue().toString());

            }

            useCase.setRsucName(vTFUCName.getValue().toString());
            useCase.setRsucIVeilleComment(vTFIveilleCom.getValue().toString());
            useCase.setRsucIReveilComment(vTFIReveilCom.getValue().toString());
            useCase.setRsucINomStabComment(vTFInomStabCom.getValue().toString());
            useCase.setRsucIPirecasComment(vTFIpirecasCom.getValue().toString());
            useCase.setRsucIMaxComment(vTFImaxCom.getValue().toString());
            useCase.setRsucTIMaxComment(vTFTImaxCom.getValue().toString());
            useCase.setRsucIConsoParcComment(vTFTIConsoParcCom.getValue().toString());
            // New values
            useCase.setRsucTnomStabComment(vTnomStabCom.getValue().toString());
            useCase.setRsucTpireCasComment(vTpireCasCom.getValue().toString());
            useCase.setRsucIdysfComment(vIdysfCom.getValue().toString());
            useCase.setRsucTdysfComment(vTdysfCom.getValue().toString());
            useCase.setRsucImstComment(vImstCom.getValue().toString());
            useCase.setRsucTmstComment(vTmstCom.getValue().toString());

            useCase.setRsucIVeille(EDSTools.convertStringToFloat(vTFIveille.getValue().toString()));
            useCase.setRsucIReveilInactif(EDSTools.convertStringToFloat(vTFIReveil.getValue().toString()));
            useCase.setRsucINomStab(EDSTools.convertStringToFloat(vTFInomStab.getValue().toString()));
            useCase.setRsucIPirecasStab(EDSTools.convertStringToFloat(vTFIpirecas.getValue().toString()));
            useCase.setRsucIMax(EDSTools.convertStringToFloat(vTFImax.getValue().toString()));
            useCase.setRsucTIMax(EDSTools.convertStringToFloat(vTFTImax.getValue().toString()));
            useCase.setRsucIConsoParc(EDSTools.convertStringToFloat(vTFTIConsoParc.getValue().toString()));
            // New values
            useCase.setRsucTNomStab(EDSTools.convertStringToFloat(vTnomStab.getValue().toString()));
            useCase.setRsucTPireCas(EDSTools.convertStringToFloat(vTpireCas.getValue().toString()));
            useCase.setRsucIdysf(EDSTools.convertStringToFloat(vIdysf.getValue().toString()));
            useCase.setRsucTdysf(EDSTools.convertStringToFloat(vTdysf.getValue().toString()));
            useCase.setRsucImst(EDSTools.convertStringToFloat(vImst.getValue().toString()));
            useCase.setRsucTmst(EDSTools.convertStringToFloat(vTmst.getValue().toString()));

            if (useCase.getRobustSupply().getRsedsTbtBt().equals("BT")) {
                useCase.setRsucUMin(EDSTools.convertStringToFloat(vTFUmin.getValue().toString()));
                useCase.setRsucUMoy(EDSTools.convertStringToFloat(vTFUmoy.getValue().toString()));
                useCase.setRsucUMax(EDSTools.convertStringToFloat(vTFUmax.getValue().toString()));
                useCase.setRsucUCaracValue(EDSTools.convertStringToFloat(vTFCarac.getValue().toString()));
            }

            return true;
        }
        return false;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        vTFUCName.setValue(useCase.getRsucName() == null ? "" : useCase.getRsucName());

        vTFIveille.setValue(EDSTools.convertFloatToVide(useCase.getRsucIVeille()));
        vTFIveilleCom.setValue(useCase.getRsucIVeilleComment() == null ? "" : useCase.getRsucIVeilleComment());
        vLIveilleRenseigner.setValue(useCase.getRsucIVeilleIformBy() == null ? "" : useCase.getRsucIVeilleIformBy());

        vTFIReveil.setValue(EDSTools.convertFloatToVide(useCase.getRsucIReveilInactif()));
        vTFIReveilCom.setValue(useCase.getRsucIReveilComment() == null ? "" : useCase.getRsucIReveilComment());
        vLIReveilRenseigner.setValue(useCase.getRsucIReveilIformBy() == null ? "" : useCase.getRsucIReveilIformBy());

        vTFInomStab.setValue(EDSTools.convertFloatToVide(useCase.getRsucINomStab()));
        vTFInomStabCom.setValue(useCase.getRsucINomStabComment() == null ? "" : useCase.getRsucINomStabComment());
        vLInomStabRenseigner.setValue(useCase.getRsucINomStabIformBy() == null ? "" : useCase.getRsucINomStabIformBy());

        vTnomStab.setValue(EDSTools.convertFloatToVide(useCase.getRsucTNomStab()));
        vTnomStabCom.setValue(useCase.getRsucTnomStabComment() == null ? "" : useCase.getRsucTnomStabComment());
        vTnomStabRenseigner.setValue(useCase.getRsucTnomStabInformBy() == null ? "" : useCase.getRsucTnomStabInformBy());

        vTFIpirecas.setValue(EDSTools.convertFloatToVide(useCase.getRsucIPirecasStab()));
        vTFIpirecasCom.setValue(useCase.getRsucIPirecasComment() == null ? "" : useCase.getRsucIPirecasComment());
        vLIpirecasRenseigner.setValue(useCase.getRsucIPirecasStabIformBy() == null ? "" : useCase.getRsucIPirecasStabIformBy());

        vTpireCas.setValue(EDSTools.convertFloatToVide(useCase.getRsucTPireCas()));
        vTpireCasCom.setValue(useCase.getRsucTpireCasComment() == null ? "" : useCase.getRsucTpireCasComment());
        vTpireCasRenseigner.setValue(useCase.getRsucTpireCasInformBy() == null ? "" : useCase.getRsucTpireCasInformBy());

        vIdysf.setValue(EDSTools.convertFloatToVide(useCase.getRsucIdysf()));
        vIdysfCom.setValue(useCase.getRsucIdysfComment() == null ? "" : useCase.getRsucIdysfComment());
        vIdysfRenseigner.setValue(useCase.getRsucIdysfInformBy() == null ? "" : useCase.getRsucIdysfInformBy());

        vTdysf.setValue(EDSTools.convertFloatToVide(useCase.getRsucTdysf()));
        vTdysfCom.setValue(useCase.getRsucTdysfComment() == null ? "" : useCase.getRsucTdysfComment());
        vTdysfRenseigner.setValue(useCase.getRsucTdysfInformBy() == null ? "" : useCase.getRsucTdysfInformBy());

        vImst.setValue(EDSTools.convertFloatToVide(useCase.getRsucImst()));
        vImstCom.setValue(useCase.getRsucImstComment() == null ? "" : useCase.getRsucImstComment());
        vImstRenseigner.setValue(useCase.getRsucImstInformBy() == null ? "" : useCase.getRsucImstInformBy());

        vTmst.setValue(EDSTools.convertFloatToVide(useCase.getRsucTmst()));
        vTmstCom.setValue(useCase.getRsucImstComment() == null ? "" : useCase.getRsucTmstComment());
        vTmstRenseigner.setValue(useCase.getRsucImstInformBy() == null ? "" : useCase.getRsucTmstInformBy());

        vTFImax.setValue(EDSTools.convertFloatToVide(useCase.getRsucIMax()));
        vTFImaxCom.setValue(useCase.getRsucIMaxComment() == null ? "" : useCase.getRsucIMaxComment());
        vLImaxRenseigner.setValue(useCase.getRsucIMaxInformBy() == null ? "" : useCase.getRsucIMaxInformBy());

        vTFTImax.setValue(EDSTools.convertFloatToVide(useCase.getRsucTIMax()));
        vTFTImaxCom.setValue(useCase.getRsucTIMaxComment() == null ? "" : useCase.getRsucTIMaxComment());
        vLTImaxRenseigner.setValue(useCase.getRsucTIMaxInformBy() == null ? "" : useCase.getRsucTIMaxInformBy());

        vTFTIConsoParc.setValue(EDSTools.convertFloatToVide(useCase.getRsucIConsoParc()));
        vTFTIConsoParcCom.setValue(useCase.getRsucIConsoParcComment() == null ? "" : useCase.getRsucIConsoParcComment());
        vLIConsoParcRenseigner.setValue(useCase.getRsucTIMaxInformBy() == null ? "" : useCase.getRsucIConsoParcInformBy());

        if (useCase.getRobustSupply().getRsedsTbtBt().equals("BT")) {
            vTFUmin.setValue(EDSTools.convertFloatToVide(useCase.getRsucUMin()));
            vTFUminCom.setValue(useCase.getRsucUMinComment() == null ? "" : useCase.getRsucUMinComment());
            vLUminRenseigner.setValue(useCase.getRsucUMinInformBy() == null ? "" : useCase.getRsucUMinInformBy());

            vTFUmoy.setValue(EDSTools.convertFloatToVide(useCase.getRsucUMoy()));
            vTFUmoyCom.setValue(useCase.getRsucUMoyComment() == null ? "" : useCase.getRsucUMoyComment());
            vLUmoyRenseigner.setValue(useCase.getRsucUMoyInformBy() == null ? "" : useCase.getRsucUMoyInformBy());

            vTFUmax.setValue(EDSTools.convertFloatToVide(useCase.getRsucUMax()));
            vTFUmaxCom.setValue(useCase.getRsucUMaxComment() == null ? "" : useCase.getRsucUMaxComment());
            vLUmaxRenseigner.setValue(useCase.getRsucUMaxInformBy() == null ? "" : useCase.getRsucUMaxInformBy());

            vTFCarac.setValue(EDSTools.convertFloatToVide(useCase.getRsucUCaracValue()));
            vTFCaracCom.setValue(useCase.getRsucUCaracComment() == null ? "" : useCase.getRsucUCaracComment());
            vLCaracRenseigner.setValue(useCase.getRsucUCaracInformBy() == null ? "" : useCase.getRsucUCaracInformBy());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) useCase);
    }

    // PROTECTED
    // PRIVATE

    /**
     * Variable to hold value of ConsumptionTableEdit
     */
    private ConsumptionTableEdit robusteTableEdit;
    /**
     * Variable to hold value of TextField for UseCase name
     */
    private TextField vTFUCName;
    /**
     * Variable to hold value of TextField for Iasleep (A)
     */
    private TextField vTFIveille;
    /**
     * Variable to hold value of Label for Iasleep (A) Edited By
     */
    private Label vLIveilleRenseigner;
    /**
     * Variable to hold value of TextField for Iasleep (A) Notes
     */
    private TextField vTFIveilleCom;
    /**
     * Variable to hold value of TextField for Iawake mode non functioning mode(A)
     */
    private TextField vTFIReveil;
    /**
     * Variable to hold value of Label for Iawake mode non functioning mode(A) Edited By
     */
    private Label vLIReveilRenseigner;
    /**
     * Variable to hold value of TextField for Iawake mode non functioning mode(A) Notes
     */
    private TextField vTFIReveilCom;
    /**
     * Variable to hold value of TextField for Inom Stab (A)
     */
    private TextField vTFInomStab;
    /**
     * Variable to hold value of Label for Inom Stab (A) Edited By
     */
    private Label vLInomStabRenseigner;
    /**
     * Variable to hold value of TextField for Inom Stab (A) Notes
     */
    private TextField vTFInomStabCom;
    /**
     * Variable to hold value of TextField for Iworst stab (A)
     */
    private TextField vTFIpirecas;
    /**
     * Variable to hold value of Label for Iworst stab (A) Edited By
     */
    private Label vLIpirecasRenseigner;
    /**
     * Variable to hold value of TextField for Iworst stab (A) Notes
     */
    private TextField vTFIpirecasCom;
    /**
     * Variable to hold value of TextField for Umin (V)
     */
    private TextField vTFUmin;
    /**
     * Variable to hold value of Label for Umin (V) Edited By
     */
    private Label vLUminRenseigner;
    /**
     * Variable to hold value of TextField for Umin (V) notes
     */
    private TextField vTFUminCom;
    /**
     * Variable to hold value of TextField for Umed(V)
     */
    private TextField vTFUmoy;
    /**
     * Variable to hold value of Label for Umed(V) Edited By
     */
    private Label vLUmoyRenseigner;
    /**
     * Variable to hold value of TextField for Umed(V) Notes
     */
    private TextField vTFUmoyCom;
    /**
     * Variable to hold value of TextField for Umax(V)
     */
    private TextField vTFUmax;
    /**
     * Variable to hold value of Label for Umax(V) Edited By
     */
    private Label vLUmaxRenseigner;
    /**
     * Variable to hold value of TextField for Umax(V) Notes
     */
    private TextField vTFUmaxCom;
    /**
     * Variable to hold value of TextField for Ipeak (A) (Inrush Current)
     */
    private TextField vTFImax;
    /**
     * Variable to hold value of Label for Ipeak (A) (Inrush Current) Edited By
     */
    private Label vLImaxRenseigner;
    /**
     * Variable to hold value of TextField for Ipeak (A) (Inrush Current) Note
     */
    private TextField vTFImaxCom;
    /**
     * Variable to hold value of TextField for Tpeak (s) (inrush current duration)
     */
    private TextField vTFTImax;
    /**
     * Variable to hold value of Label for Tpeak (s) (inrush current duration) Edited By
     */
    private Label vLTImaxRenseigner;
    /**
     * Variable to hold value of TextField for Tpeak (s) (inrush current duration) Notes
     */
    private TextField vTFTImaxCom;
    /**
     * Variable to hold value of TextField for Robust table
     */
    private TextField vTFCarac;
    /**
     * Variable to hold value of TextField for Robust table Edited by
     */
    private Label vLCaracRenseigner;
    /**
     * Variable to hold value of TextField for Robust table notes
     */
    private TextField vTFCaracCom;
    /**
     * Variable to hold value of TextField for Title of Robust table
     */
    private Label vLCaracTitle;
    /**
     * Variable to hold value of TextField for Ipark asleep (A)
     */
    private TextField vTFTIConsoParc;
    /**
     * Variable to hold value of Label for Ipark asleep (A) Edited By
     */
    private Label vLIConsoParcRenseigner;
    /**
     * Variable to hold value of TextField for Ipark asleep (A) Notes
     */
    private TextField vTFTIConsoParcCom;

    // New values
    /**
     * Variable to hold value of TextField for Tnom stab
     */
    private TextField vTnomStab;
    /**
     * Variable to hold value of Label for Tnom stab Edited By
     */
    private Label vTnomStabRenseigner;
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
    private Label vTpireCasRenseigner;
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
    private Label vIdysfRenseigner;
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
    private Label vTdysfRenseigner;
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
    private Label vImstRenseigner;
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
    private Label vTmstRenseigner;
    /**
     * Variable to hold value of TextField for Tmst Notes
     */
    private TextField vTmstCom;

    /**
     * Variable to hold value of EdsCourantAppelleActivation
     */
    private EdsRobustSupplyUseCase useCase;
    /**
     * Variable to hold value of String representing User
     */
    private String user;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of Button to remove form
     */
    private Button vBRemove;

    public EdsRobustSupplyUseCase getUseCase() {
        return useCase;
    }

    public void setUseCase(EdsRobustSupplyUseCase useCase) {
        this.useCase = useCase;
    }

    /**
     * Initialize Edit form view for robust supply use case
     */
    private void init() {
        this.user = controller.getAuthenticatedUser().getUFirstname() + " " + controller.getAuthenticatedUser().getULastname();

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

        setSpacing(true);
        setWidth("100%");

        // new UseCase
        HorizontalLayout hlUCName = new HorizontalLayout();
        hlUCName.setSpacing(true);
        Label vLUCName = new Label(controller.getBundle().getString("current-conso-list-alim-modif-usecase-name") + " :");
        hlUCName.addComponent(vLUCName);
        vTFUCName = new TextField();
        vTFUCName.setRequired(true);
        vTFUCName.setRequiredError(MessageFormat.format(controller.getBundle().getString("eds-field-not-empty"), vLUCName.getValue().toString()));
        hlUCName.addComponent(vTFUCName);
        addComponent(hlUCName);

        robusteTableEdit = new ConsumptionTableEdit(controller);
        int i = 14;

        vTFIveille = new TextField();
        vTFIveilleCom = new TextField();
        vLIveilleRenseigner = new Label();

        vTFIReveil = new TextField();
        vTFIReveilCom = new TextField();
        vLIReveilRenseigner = new Label();

        vTFInomStab = new TextField();
        vTFInomStabCom = new TextField();
        vLInomStabRenseigner = new Label();

        vTFIpirecas = new TextField();
        vTFIpirecasCom = new TextField();
        vLIpirecasRenseigner = new Label();

        vTFImax = new TextField();
        vTFImaxCom = new TextField();
        vLImaxRenseigner = new Label();

        vTFTImax = new TextField();
        vTFTImaxCom = new TextField();
        vLTImaxRenseigner = new Label();

        vTFTIConsoParc = new TextField();
        vLIConsoParcRenseigner = new Label();
        vTFTIConsoParcCom = new TextField();

        // New values
        vTnomStab = new TextField();
        vTnomStabRenseigner = new Label();
        vTnomStabCom = new TextField();

        vTpireCas = new TextField();
        vTpireCasRenseigner = new Label();
        vTpireCasCom = new TextField();

        vIdysf = new TextField();
        vIdysfRenseigner = new Label();
        vIdysfCom = new TextField();

        vTdysf = new TextField();
        vTdysfRenseigner = new Label();
        vTdysfCom = new TextField();

        vImst = new TextField();
        vImstRenseigner = new Label();
        vImstCom = new TextField();

        vTmst = new TextField();
        vTmstRenseigner = new Label();
        vTmstCom = new TextField();

        vTFIveilleCom.setMaxLength(128);
        vTFIReveilCom.setMaxLength(128);
        vTFInomStabCom.setMaxLength(128);
        vTFIpirecasCom.setMaxLength(128);
        vTFTIConsoParcCom.setMaxLength(128);
        // New values
        vTnomStabCom.setMaxLength(128);
        vTpireCasCom.setMaxLength(128);
        vIdysfCom.setMaxLength(128);
        vTdysfCom.setMaxLength(128);
        vImstCom.setMaxLength(128);
        vTmstCom.setMaxLength(128);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-veil"), vTFIveille,
                vLIveilleRenseigner, vTFIveilleCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-rev"), vTFIReveil,
                vLIReveilRenseigner, vTFIReveilCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-nom"), vTFInomStab,
                vLInomStabRenseigner, vTFInomStabCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tnom-stab"), vTnomStab,
                vTnomStabRenseigner, vTnomStabCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-pire"), vTFIpirecas,
                vLIpirecasRenseigner, vTFIpirecasCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tpire-cas"), vTpireCas,
                vTpireCasRenseigner, vTpireCasCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Idysf"), vIdysf,
                vIdysfRenseigner, vIdysfCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tdysf"), vTdysf,
                vTdysfRenseigner, vTdysfCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Imst"), vImst,
                vImstRenseigner, vImstCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tmst"), vTmst,
                vTmstRenseigner, vTmstCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-pic"), vTFImax,
                vLImaxRenseigner, vTFImaxCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tpic"), vTFTImax,
                vLTImaxRenseigner, vTFTImaxCom);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-veil-parc"),
                vTFTIConsoParc, vLIConsoParcRenseigner, vTFTIConsoParcCom);

        if (useCase.getRobustSupply().getRsedsTbtBt().equals("BT")) {
            i += 3;

            vTFUmin = new TextField();
            vTFUminCom = new TextField();
            vLUminRenseigner = new Label();

            vTFUmoy = new TextField();
            vTFUmoyCom = new TextField();
            vLUmoyRenseigner = new Label();

            vTFUmax = new TextField();
            vTFUmaxCom = new TextField();
            vLUmaxRenseigner = new Label();

            vTFCarac = new TextField();
            vTFCaracCom = new TextField();
            vLCaracRenseigner = new Label();
            vLCaracTitle = new Label(useCase.getRobustSupply().getRsedsUcaracName() + " (V)");

            vTFUmin.setMaxLength(128);
            vTFUmoyCom.setMaxLength(128);
            vTFUmaxCom.setMaxLength(128);
            vTFCaracCom.setMaxLength(128);

            robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Umin"), vTFUmin,
                    vLUminRenseigner, vTFUminCom);

            robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Umoy"), vTFUmoy,
                    vLUmoyRenseigner, vTFUmoyCom);

            robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Umax"), vTFUmax,
                    vLUmaxRenseigner, vTFUmaxCom);

            if (useCase.getRobustSupply().getRsedsUcaracName() != null && !useCase.getRobustSupply().getRsedsUcaracName().equals("")) {
                i++;
                robusteTableEdit.addOrderToContainer(robusteTableEdit, vLCaracTitle, vTFCarac, vLCaracRenseigner, vTFCaracCom);
            }

        }
        robusteTableEdit.setPageLength(i);
        robusteTableEdit.setWidth("100%");

        vBRemove = new Button();
        vBRemove.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/erase.png"));
        vBRemove.setStyleName(BaseTheme.BUTTON_LINK);
        vBRemove.setWidth("16px");
        vBRemove.setDescription(controller.getBundle().getString("btn-delete-tab"));
        vBRemove.addListener(this);

        HorizontalLayout hlUseCase = new HorizontalLayout();
        hlUseCase.setSpacing(true);
        hlUseCase.setWidth("100%");
        hlUseCase.addComponent(robusteTableEdit);
        hlUseCase.addComponent(vBRemove);
        hlUseCase.setExpandRatio(robusteTableEdit, 1);
        hlUseCase.setExpandRatio(vBRemove, 0);
        hlUseCase.setComponentAlignment(vBRemove, Alignment.MIDDLE_LEFT);
        addComponent(hlUseCase);

        discardChanges();

        setSpacing(true);
        setWidth("100%");

        // separator
        addComponent(new Label("<br />", Label.CONTENT_XHTML));

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection<?> getAllItemsToDelete() {
        return Collections.EMPTY_SET;
    }

    /**
     * {@inheritDoc}
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
}
