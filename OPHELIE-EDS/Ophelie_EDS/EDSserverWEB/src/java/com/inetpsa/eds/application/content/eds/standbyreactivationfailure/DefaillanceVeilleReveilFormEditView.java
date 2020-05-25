package com.inetpsa.eds.application.content.eds.standbyreactivationfailure;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsDefaillanceVeilleReveilFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Label;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * This class provide Edit view for asleep / awake Failure form
 * 
 * @author Geometric Ltd.
 */
public class DefaillanceVeilleReveilFormEditView extends A_EdsEditForm {
    /**
     * Parameterized constructor
     * 
     * @param eds Eds details
     * @param defaillance EdsDefaillanceVeilleReveilFormData
     * @param controller EDS application controller object.
     */
    public DefaillanceVeilleReveilFormEditView(EdsEds eds, EdsDefaillanceVeilleReveilFormData defaillance, EdsApplicationController controller) {
        // comment
        this.formData = defaillance;
        this.eds = eds;
        this.controller = controller;
        init();
    }

    /**
     * Variable to hold value of QuestionnaireEditView for 'A 20mAH electrical consumption over the CSE value ?'
     */
    private QuestionnaireEditView consomationQ;
    /**
     * Variable to hold value of QuestionnaireEditView for 'A Network holding?'
     */
    private QuestionnaireEditView maintienRQ;
    /**
     * Variable to hold value of QuestionnaireEditView for 'A network wakes up?'
     */
    private QuestionnaireEditView reveilIntQ;
    /**
     * Variable to hold value of QuestionnaireEditView for 'A switched power supplies holding ?'
     */
    private QuestionnaireEditView maintienAlimQ;
    /**
     * Variable to hold value of QuestionnaireEditView for 'A switched power supplies reload ?'
     */
    private QuestionnaireEditView realimAlimQ;
    /**
     * Variable to hold value of Accordion
     */
    private Accordion vACCaccordion;
    /**
     * Variable to hold value of EdsDefaillanceVeilleReveilFormData
     */
    private EdsDefaillanceVeilleReveilFormData formData;
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Edit view for asleep / awake Failure form
     */
    private void init() {
        vACCaccordion = new Accordion();

        consomationQ = new QuestionnaireEditView(controller);
        vACCaccordion.addTab(consomationQ, controller.getBundle().getString("Def-V-R-details-conso"));

        maintienRQ = new QuestionnaireEditView(controller);
        maintienRQ.withCourant(false);
        vACCaccordion.addTab(maintienRQ, controller.getBundle().getString("Def-V-R-Net"));

        reveilIntQ = new QuestionnaireEditView(controller);
        reveilIntQ.withCourant(false);
        vACCaccordion.addTab(reveilIntQ, controller.getBundle().getString("Def-V-R-R-Net"));

        maintienAlimQ = new QuestionnaireEditView(controller);
        maintienAlimQ.withCourant(false);
        vACCaccordion.addTab(maintienAlimQ, controller.getBundle().getString("Def-V-R-Alim"));

        realimAlimQ = new QuestionnaireEditView(controller);
        vACCaccordion.addTab(realimAlimQ, controller.getBundle().getString("Def-V-R-R-Alim"));

        Label vLABsoustitre = new Label(controller.getBundle().getString("Def-V-R-quest"));
        vLABsoustitre.addStyleName("h2");

        addComponent(vLABsoustitre);
        addComponent(vACCaccordion);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        if (!consomationQ.validate()) {
            return false;
        }

        if (!maintienRQ.validate()) {
            return false;
        }
        if (!reveilIntQ.validate()) {
            return false;
        }

        if (!maintienAlimQ.validate()) {
            return false;
        }
        if (!realimAlimQ.validate()) {
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
            formData.setDvrfdCommentaireConsommationPlusVintMah(consomationQ.getCom());
            formData.setDvrfdOccurenceTotalDefaillanceOrganeConsommationPlusVintMah(consomationQ.getTotOcurence());
            formData.setDvrfdCourantCorrespondantConsommationPlusVintMah(consomationQ.getCourant());
            formData.setDvrfdIsPlusVintMah(consomationQ.isActive());

            formData.setDvrfdCommentaireMaintienReseaux(maintienRQ.getCom());
            formData.setDvrfdOccurenceTotalDefaillanceOrganeMaintienReseaux(maintienRQ.getTotOcurence());
            formData.setDvrfdCourantCorrespondantMaintienReseaux(maintienRQ.getCourant());
            formData.setDvrfdIsMaintienReseaux(maintienRQ.isActive());

            formData.setDvrfdCommentaireReveilIntempestif(reveilIntQ.getCom());
            formData.setDvrfdOccurenceTotalDefaillanceOrganeReveilIntempestif(reveilIntQ.getTotOcurence());
            formData.setDvrfdCourantCorrespondantReveilIntempestif(reveilIntQ.getCourant());
            formData.setDvrfdIsReveilIntempsif(reveilIntQ.isActive());

            formData.setDvrfdCommentaireMaintienAlimentaion(maintienAlimQ.getCom());
            formData.setDvrfdOccurenceTotalDefaillanceOrganeMaintienAlimentaion(maintienAlimQ.getTotOcurence());
            formData.setDvrfdCourantCorrespondantMaintienAlimentaion(maintienAlimQ.getCourant());
            formData.setDvrfdIsMaintienAlimentation(maintienAlimQ.isActive());

            formData.setDvrfdCommentaireRealimentationAlimentation(realimAlimQ.getCom());
            formData.setDvrfdOccurenceTotalDefaillanceOrganeRealimentationAlimentation(realimAlimQ.getTotOcurence());
            formData.setDvrfdCourantCorrespondantRealimentationAlimentation(realimAlimQ.getCourant());
            formData.setDvrfdIsRealimentationAlimentation(realimAlimQ.isActive());

            eds.setEdsModifDate(new Date());
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

        try {

            if (formData.getDvrfdIsPlusVintMah() == 1) {
                consomationQ.setValue(formData.getDvrfdOccurenceTotalDefaillanceOrganeConsommationPlusVintMah(),
                        formData.getDvrfdCourantCorrespondantConsommationPlusVintMah(), formData.getDvrfdCommentaireConsommationPlusVintMah());
                consomationQ.setActive(true);
            }

            if (formData.getDvrfdIsMaintienReseaux() == 1) {
                maintienRQ.setValue(formData.getDvrfdOccurenceTotalDefaillanceOrganeMaintienReseaux(),
                        formData.getDvrfdCourantCorrespondantMaintienReseaux(), formData.getDvrfdCommentaireMaintienReseaux());
                maintienRQ.setActive(true);
            }
            if (formData.getDvrfdIsReveilIntempsif() == 1) {
                reveilIntQ.setValue(formData.getDvrfdOccurenceTotalDefaillanceOrganeReveilIntempestif(),
                        formData.getDvrfdCourantCorrespondantReveilIntempestif(), formData.getDvrfdCommentaireReveilIntempestif());
                reveilIntQ.setActive(true);
            }
            if (formData.getDvrfdIsMaintienAlimentation() == 1) {
                maintienAlimQ.setValue(formData.getDvrfdOccurenceTotalDefaillanceOrganeMaintienAlimentaion(),
                        formData.getDvrfdCourantCorrespondantMaintienAlimentaion(), formData.getDvrfdCommentaireMaintienAlimentaion());
                maintienAlimQ.setActive(true);
            }
            if (formData.getDvrfdIsRealimentationAlimentation() == 1) {
                realimAlimQ.setValue(formData.getDvrfdOccurenceTotalDefaillanceOrganeRealimentationAlimentation(),
                        formData.getDvrfdCourantCorrespondantRealimentationAlimentation(), formData.getDvrfdCommentaireRealimentationAlimentation());
                realimAlimQ.setActive(true);
            }
        } catch (Exception e) {
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singletonList((Object) formData);
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
        formData = controller.getFormDataModel(eds, formData.getClass());
    }
}
