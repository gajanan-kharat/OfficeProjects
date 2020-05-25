package com.inetpsa.eds.application.content.eds.standbyreactivationfailure;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.dao.model.EdsDefaillanceVeilleReveilFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Label;

/**
 * This class provide Read view for asleep / awake Failure form
 * 
 * @author Geometric Ltd.
 */
public class DefaillanceVeilleReveilFormReadView extends A_EdsReadForm {
    /**
     * Variable to hold value of EdsEDs
     */
    private EdsEds eds;
    /**
     * Variable to hold value of EdsDefaillanceVeilleReveilFormData
     */
    private EdsDefaillanceVeilleReveilFormData formData;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Parameterized constructor
     * 
     * @param eds EDS details.
     * @param data EdsDefaillanceVeilleReveilFormData.
     * @param controller Controller of EDS application
     */
    public DefaillanceVeilleReveilFormReadView(EdsEds eds, EdsDefaillanceVeilleReveilFormData data, EdsApplicationController controller) {
        super(controller);
        this.eds = eds;
        this.formData = data;
        this.controller = controller;
        init();
    }

    /**
     * Variable to hold value of Accordion
     */
    private Accordion vACCaccordion;
    /**
     * Variable to hold value of Label
     */
    private Label vLABsoustitre;
    /**
     * Variable to hold value of QuestionnaireReadView for 'A 20mAH electrical consumption over the CSE value ?'
     */
    private QuestionnaireReadView qrvConsomation;
    /**
     * Variable to hold value of QuestionnaireReadView for 'A Network holding?'
     */
    private QuestionnaireReadView qrvMaintienR;
    /**
     * Variable to hold value of QuestionnaireReadView for 'A network wakes up?'
     */
    private QuestionnaireReadView qrvReveilInt;
    /**
     * Variable to hold value of QuestionnaireReadView for 'A switched power supplies holding ?'
     */
    private QuestionnaireReadView qrvMaintienA;
    /**
     * Variable to hold value of QuestionnaireReadView for 'A switched power supplies reload ?'
     */
    private QuestionnaireReadView qrvRealimAlim;

    /**
     * Initialize read view for asleep / awake Failure form
     */
    private void init() {

        qrvConsomation = new QuestionnaireReadView(controller);

        qrvMaintienR = new QuestionnaireReadView(controller);
        qrvMaintienR.withCourant(false);

        qrvReveilInt = new QuestionnaireReadView(controller);
        qrvReveilInt.withCourant(false);

        qrvMaintienA = new QuestionnaireReadView(controller);
        qrvMaintienA.withCourant(false);

        qrvRealimAlim = new QuestionnaireReadView(controller);

        vACCaccordion = new Accordion();
        vACCaccordion.addTab(qrvConsomation, controller.getBundle().getString("Def-V-R-details-conso"));
        vACCaccordion.addTab(qrvMaintienR, controller.getBundle().getString("Def-V-R-Net"));
        vACCaccordion.addTab(qrvReveilInt, controller.getBundle().getString("Def-V-R-R-Net"));
        vACCaccordion.addTab(qrvMaintienA, controller.getBundle().getString("Def-V-R-Alim"));
        vACCaccordion.addTab(qrvRealimAlim, controller.getBundle().getString("Def-V-R-R-Alim"));

        vLABsoustitre = new Label(controller.getBundle().getString("Def-V-R-quest"));
        vLABsoustitre.addStyleName("h2");

        addComponent(vLABsoustitre);
        addComponent(vACCaccordion);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "Def-V-R-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return getFormName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return DefaillanceVeilleReveilFormBuilder.ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        return eds.getEdsRef();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEds()
     */
    @Override
    public EdsEds getEds() {
        return eds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {

        qrvConsomation.setValue(formData.getDvrfdOccurenceTotalDefaillanceOrganeConsommationPlusVintMah(),
                formData.getDvrfdCourantCorrespondantConsommationPlusVintMah(), formData.getDvrfdCommentaireConsommationPlusVintMah());

        qrvMaintienA.setValue(formData.getDvrfdOccurenceTotalDefaillanceOrganeMaintienAlimentaion(),
                formData.getDvrfdCourantCorrespondantMaintienAlimentaion(), formData.getDvrfdCommentaireMaintienAlimentaion());

        qrvMaintienR.setValue(formData.getDvrfdOccurenceTotalDefaillanceOrganeMaintienReseaux(),
                formData.getDvrfdCourantCorrespondantMaintienReseaux(), formData.getDvrfdCommentaireMaintienReseaux());

        qrvRealimAlim.setValue(formData.getDvrfdOccurenceTotalDefaillanceOrganeRealimentationAlimentation(),
                formData.getDvrfdCourantCorrespondantRealimentationAlimentation(), formData.getDvrfdCommentaireRealimentationAlimentation());

        qrvReveilInt.setValue(formData.getDvrfdOccurenceTotalDefaillanceOrganeReveilIntempestif(),
                formData.getDvrfdCourantCorrespondantReveilIntempestif(), formData.getDvrfdCommentaireReveilIntempestif());

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        formData = controller.getFormDataModel(eds, formData.getClass());
    }
}
