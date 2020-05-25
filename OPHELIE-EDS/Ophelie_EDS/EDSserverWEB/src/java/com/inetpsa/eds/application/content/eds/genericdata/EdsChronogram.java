package com.inetpsa.eds.application.content.eds.genericdata;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.I_ValidationFormData;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsValidation;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.gridheader.GridFirstHeader;
import com.inetpsa.eds.tools.gridheader.GridHeader;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is used to create the Generic data Chart for the Read view.
 * 
 * @author Geometric Ltd.
 */
public class EdsChronogram extends GridLayout {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public EdsChronogram(EdsApplicationController controller) {
        super(10, 4);

        this.controller = controller;
        // TODO proposer la sélection d'un projet.

        // this.eds = eds;
        // if ( project != null )
        // {
        // this.selectedProject = project;
        // }
        // else
        // {
        // this.selectedProject = eds.getEdsProject();
        // }
        init();
    }

    /**
     * This method is used to refresh the grid.
     * 
     * @param validationFormData Validation form data.
     * @param project EDS project details.
     */
    final public void refreshView(I_ValidationFormData validationFormData, EdsProject project) {
        clearContent();

        // Milestones info.
        addComponent(createMilestoneLabel(project.getEdsWordingByPWIdFirstStage()), 2, 1, 3, 1);
        addComponent(createMilestoneLabel(project.getEdsWordingByPWIdSecondStage()), 4, 1, 5, 1);
        addComponent(createMilestoneLabel(project.getEdsWordingByPWIdThirdStage()), 6, 1, 7, 1);
        addComponent(createMilestoneLabel(project.getEdsWordingByPWIdFourthStage()), 8, 1, 9, 1);

        // Info dates
        addComponent(createDateLabel(project.getPDateFirstStage(), validationFormData.getPreliminaryStatus()), 2, 2, 3, 2);
        setComponentAlignment(getComponent(2, 2), Alignment.MIDDLE_CENTER);
        addComponent(createDateLabel(project.getPDateSecondStage(), validationFormData.getRobustStatus()), 4, 2, 5, 2);
        setComponentAlignment(getComponent(4, 2), Alignment.MIDDLE_CENTER);
        addComponent(createDateLabel(project.getPDateThirdStage(), validationFormData.getConsolidatedStatus()), 6, 2, 7, 2);
        setComponentAlignment(getComponent(6, 2), Alignment.MIDDLE_CENTER);
        addComponent(createDateLabel(project.getPDateFourthStage(), validationFormData.getClosedStatus()), 8, 2, 9, 2);
        setComponentAlignment(getComponent(8, 2), Alignment.MIDDLE_CENTER);

        // Info status stages
        addComponent(createValidationStatusLayout(validationFormData.getPreliminaryStatus()), 1, 3, 2, 3);
        addComponent(createValidationStatusLayout(validationFormData.getRobustStatus()), 3, 3, 4, 3);
        addComponent(createValidationStatusLayout(validationFormData.getConsolidatedStatus()), 5, 3, 6, 3);
        addComponent(createValidationStatusLayout(validationFormData.getClosedStatus()), 7, 3, 8, 3);
        HorizontalLayout dash = new HorizontalLayout();
        dash.setSizeFull();
        dash.setStyleName("validation-tag");
        addComponent(dash, 9, 3);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store EDS application controller data.
     */
    private EdsApplicationController controller;

    /**
     * Initialization method. It is used to create the chronogram.
     */
    private void init() {
        setStyleName("grid-table");
        setMargin(true, false, true, false);
        setWidth("100%");

        setColumnExpandRatio(0, 0);
        setColumnExpandRatio(1, 1);
        setColumnExpandRatio(2, 1);
        setColumnExpandRatio(3, 1);
        setColumnExpandRatio(4, 1);
        setColumnExpandRatio(5, 1);
        setColumnExpandRatio(6, 1);
        setColumnExpandRatio(7, 1);
        setColumnExpandRatio(8, 1);
        setColumnExpandRatio(9, 1);

        addComponent(new GridFirstHeader(controller.getBundle().getString("project-project-step-prelim")), 1, 0, 2, 0);
        addComponent(new GridHeader(controller.getBundle().getString("project-project-step-rob")), 3, 0, 4, 0);
        addComponent(new GridHeader(controller.getBundle().getString("project-project-step-cons")), 5, 0, 6, 0);
        addComponent(new GridHeader(controller.getBundle().getString("project-project-step-clot")), 7, 0, 8, 0);

        addComponent(new GridFirstHeader(GridHeader.LEFT, controller.getBundle().getString("project-project-step-title-jal"), 90), 0, 1);
        addComponent(new GridHeader(GridHeader.LEFT, controller.getBundle().getString("eds-date"), 90), 0, 2);
        addComponent(new GridHeader(GridHeader.LEFT, controller.getBundle().getString("project-project-step-title-valid"), 90), 0, 3);
    }

    /**
     * This method is used to create the layout of validation status row.
     * 
     * @param validationStatus Validation value.
     * @return Layout for validation status.
     */
    private HorizontalLayout createValidationStatusLayout(int validationStatus) {
        HorizontalLayout innerLayout = new HorizontalLayout();
        innerLayout.setStyleName("validation-tag");
        innerLayout.setWidth("100%");
        innerLayout.setHeight("23px");
        innerLayout.addComponent(createValidationLabel(validationStatus));
        innerLayout.setComponentAlignment(innerLayout.getComponent(0), Alignment.MIDDLE_CENTER);
        return innerLayout;
    }

    /**
     * This method is used to create the validation value label.
     * 
     * @param validationStatus Status to be added.
     * @return Label of validation.
     */
    private Label createValidationLabel(int validationStatus) {
        Label vLBLvalidation = new Label(EdsValidation.getValidationText(validationStatus));
        switch (validationStatus) {
        case EdsValidation.VALIDATION_NONE:
            vLBLvalidation.setStyleName("validation-none");
            break;
        case EdsValidation.VALIDATION_STRICT_NOK:
            vLBLvalidation.setStyleName("validation-strict-nok");
            break;
        case EdsValidation.VALIDATION_WEAK_NOK:
            vLBLvalidation.setStyleName("validation-weak-nok");
            break;
        case EdsValidation.VALIDATION_OK:
            vLBLvalidation.setStyleName("validation-ok");
            break;
        }
        vLBLvalidation.setSizeUndefined();
        vLBLvalidation.setWidth("100px");
        return vLBLvalidation;
    }

    /**
     * This method is used to clear all the components.
     */
    private void clearContent() {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 4; j++) {
                removeComponent(i, j);
            }
        }
    }

    /**
     * This method is used to create labels for milestone values.
     * 
     * @param milestoneWording EDS wording details.
     * @return Label for milestone wording.
     */
    private Label createMilestoneLabel(EdsWording milestoneWording) {
        Label vLBLstage = new Label(EDSTools.getWordingValueOrNullRepresentation(milestoneWording));
        vLBLstage.setStyleName("large");
        return vLBLstage;
    }

    /**
     * This method is used to create labels for date values.
     * 
     * @param stageDeadLine Deadline date of the milestone.
     * @param stageStatus Status to be added under the date.
     * @return Label for showing date values.
     */
    private Label createDateLabel(Date stageDeadLine, int stageStatus) {
        StringBuilder builder = new StringBuilder();

        if (stageDeadLine != null) {
            builder.append(SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, controller.getApplication().getLocale()).format(stageDeadLine));

            if (stageStatus != EdsValidation.VALIDATION_OK) {
                long timeLeft = stageDeadLine.getTime() - new Date().getTime();
                double dayLeft = (double) timeLeft / (1000 * 60 * 60 * 24);
                // Not exceeded deadline
                if (dayLeft > 0) {
                    // If more than one month
                    if (dayLeft > 60) {
                        builder.append(MessageFormat.format("<br/>" + controller.getBundle().getString("project-project-step-mess-months-remain"),
                                (int) (dayLeft / 30)));

                    }
                    // If more than one day
                    else if (dayLeft > 1) {
                        builder.append(MessageFormat.format("<br/>" + controller.getBundle().getString("project-project-step-mess-days-remain"),
                                (int) dayLeft));
                    } else {
                        builder.append("<br/>(moins d'un jour restant)");
                    }
                } else {
                    dayLeft *= -1;
                    // More than one month late
                    if (dayLeft > 60) {
                        builder.append(MessageFormat.format("<br/>" + controller.getBundle().getString("project-project-step-mess-months-late"),
                                (int) (dayLeft / 30)));

                    }
                    // More than one day late
                    else if (dayLeft > 1) {
                        builder.append(MessageFormat.format("<br/>" + controller.getBundle().getString("project-project-step-mess-days-late"),
                                (int) dayLeft));
                    } else {
                        builder.append("<br/>(un jour de retard)");
                    }
                }
            }
        } else {
            builder.append(controller.getBundle().getString("project-project-step-not-define"));
        }

        Label vLBLdateInfo = new Label(builder.toString(), Label.CONTENT_XHTML);
        vLBLdateInfo.setStyleName("centered");
        vLBLdateInfo.addStyleName("chronogram-date");
        vLBLdateInfo.setSizeUndefined();

        return vLBLdateInfo;
    }
}
