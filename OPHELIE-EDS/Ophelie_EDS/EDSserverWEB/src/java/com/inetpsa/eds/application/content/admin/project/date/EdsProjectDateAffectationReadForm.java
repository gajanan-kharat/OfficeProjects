package com.inetpsa.eds.application.content.admin.project.date;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.tools.EDSTools;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Collections;

/**
 * This class provide component for reading project milestones.
 * 
 * @author Geometric Ltd.
 */
public class EdsProjectDateAffectationReadForm extends A_EdsReadForm {
    // PUBLICI
    /**
     * Parameterized constructor
     * 
     * @param project Eds project Details
     * @param controller Controller of EDS application
     */
    public EdsProjectDateAffectationReadForm(EdsProject project, EdsApplicationController controller) {
        super(controller);
        this.project = project;
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEds()
     */
    @Override
    public EdsEds getEds() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {
        String firstStageValue = controller.getBundle().getString("menu-app-step-prelim") + " : ";
        if (project.getEdsWordingByPWIdFirstStage() != null) {
            firstStageValue += EDSTools.getWordingValueOrNullRepresentation(project.getEdsWordingByPWIdFirstStage());
        } else {
            firstStageValue += controller.getBundle().getString("Admin-proj-jal-not-define") + " -";
        }
        vLBLprelimStageValue.setValue(firstStageValue);

        String secondStageValue = controller.getBundle().getString("menu-app-step-rob") + " : ";
        if (project.getEdsWordingByPWIdSecondStage() != null) {
            secondStageValue += EDSTools.getWordingValueOrNullRepresentation(project.getEdsWordingByPWIdSecondStage());
        } else {
            secondStageValue += controller.getBundle().getString("Admin-proj-jal-not-define") + " -";
        }
        vLBLrobustStageValue.setValue(secondStageValue);

        String thirdStageValue = controller.getBundle().getString("Validation-step-cons") + ": ";
        if (project.getEdsWordingByPWIdThirdStage() != null) {
            thirdStageValue += EDSTools.getWordingValueOrNullRepresentation(project.getEdsWordingByPWIdThirdStage());
        } else {
            thirdStageValue += controller.getBundle().getString("Admin-proj-jal-not-define") + " -";
        }
        vLBLconsolidateStageValue.setValue(thirdStageValue);

        String fourthStageValue = controller.getBundle().getString("menu-app-step-clot");
        if (project.getEdsWordingByPWIdFourthStage() != null) {
            fourthStageValue += EDSTools.getWordingValueOrNullRepresentation(project.getEdsWordingByPWIdFourthStage());
        } else {
            fourthStageValue += controller.getBundle().getString("Admin-proj-jal-not-define") + " -";
        }
        vLBLclosedStageValue.setValue(fourthStageValue);

        if (project.getPDateFirstStage() != null) {
            vLBLprelimStageDateValue.setValue(DateFormat.getDateInstance().format(project.getPDateFirstStage()));
        }
        if (project.getPDateSecondStage() != null) {
            vLBLrobustStageDateValue.setValue(DateFormat.getDateInstance().format(project.getPDateSecondStage()));
        }
        if (project.getPDateThirdStage() != null) {
            vLBLconsolidateStageDateValue.setValue(DateFormat.getDateInstance().format(project.getPDateThirdStage()));
        }
        if (project.getPDateFourthStage() != null) {
            vLBLclosedStageDateValue.setValue(DateFormat.getDateInstance().format(project.getPDateFourthStage()));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsProject
     */
    private EdsProject project;
    /**
     * Variable to hold value of Label for Preliminary stage
     */
    private Label vLBLprelimStageValue;
    /**
     * Variable to hold value of Label for Preliminary stage date
     */
    private Label vLBLprelimStageDateValue;
    /**
     * Variable to hold value of Label for Robust stage
     */
    private Label vLBLrobustStageValue;
    /**
     * Variable to hold value of Label for Robust stage date
     */
    private Label vLBLrobustStageDateValue;
    /**
     * Variable to hold value of Label for Consolidate stage
     */
    private Label vLBLconsolidateStageValue;
    /**
     * Variable to hold value of Label for Consolidate stage date
     */
    private Label vLBLconsolidateStageDateValue;
    /**
     * Variable to hold value of Label for Closed stage
     */
    private Label vLBLclosedStageValue;
    /**
     * Variable to hold value of Label for Closed stage date
     */
    private Label vLBLclosedStageDateValue;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize component for reading project milestones.
     */
    private void init() {
        setMargin(true);
        setSpacing(true);
        setCaption(controller.getBundle().getString("Admin-proj-jal"));

        // Preliminary milestone info
        HorizontalLayout prelimLayout = new HorizontalLayout();
        prelimLayout.setSpacing(true);
        this.vLBLprelimStageValue = new Label();
        this.vLBLprelimStageDateValue = new Label();
        prelimLayout.addComponent(vLBLprelimStageValue);
        prelimLayout.addComponent(vLBLprelimStageDateValue);
        addComponent(prelimLayout);

        // Robust milestone info
        HorizontalLayout robustLayout = new HorizontalLayout();
        robustLayout.setSpacing(true);
        this.vLBLrobustStageValue = new Label();
        this.vLBLrobustStageDateValue = new Label();
        robustLayout.addComponent(vLBLrobustStageValue);
        robustLayout.addComponent(vLBLrobustStageDateValue);
        addComponent(robustLayout);

        // Consolidate milestone info
        HorizontalLayout consolidatelayout = new HorizontalLayout();
        consolidatelayout.setSpacing(true);
        this.vLBLconsolidateStageValue = new Label();
        this.vLBLconsolidateStageDateValue = new Label();
        consolidatelayout.addComponent(vLBLconsolidateStageValue);
        consolidatelayout.addComponent(vLBLconsolidateStageDateValue);
        addComponent(consolidatelayout);

        // Closed milestone info
        HorizontalLayout closedLayout = new HorizontalLayout();
        closedLayout.setSpacing(true);
        this.vLBLclosedStageValue = new Label();
        this.vLBLclosedStageDateValue = new Label();
        closedLayout.addComponent(vLBLclosedStageValue);
        closedLayout.addComponent(vLBLclosedStageDateValue);
        addComponent(closedLayout);

        refreshViewData();

    }
}
