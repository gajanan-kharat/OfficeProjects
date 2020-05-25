package com.inetpsa.eds.application.content.admin.project.date;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsWording;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;

/**
 * This class provide editing component of project milestones.
 * 
 * @author Geometric Ltd.
 */
public class EdsProjectDateAffectationEditForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param project Eds project Details
     * @param controller Controller of EDS application
     */
    public EdsProjectDateAffectationEditForm(EdsProject project, EdsApplicationController controller) {
        this.project = project;
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        project.setEdsWordingByPWIdFirstStage((EdsWording) vCMBprelimStageValue.getValue());
        project.setEdsWordingByPWIdSecondStage((EdsWording) vCMBrobustStageValue.getValue());
        project.setEdsWordingByPWIdThirdStage((EdsWording) vCMBconsolidateStageValue.getValue());
        project.setEdsWordingByPWIdFourthStage((EdsWording) vCMBclosedStageValue.getValue());
        project.setPDateFirstStage((Date) vPDFprelimStageDateValue.getValue());
        project.setPDateSecondStage((Date) vPDFrobustStageDateValue.getValue());
        project.setPDateThirdStage((Date) vPDFconsolidateStageDateValue.getValue());
        project.setPDateFourthStage((Date) vPDFclosedStageDateValue.getValue());
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        this.mileStonesList.clear();

        EDSdao dao = EDSdao.getInstance();

        this.mileStonesList = new ArrayList<EdsWording>(dao.getAllActiveWordingsByType(EdsWording.MILESTONE));

        this.mileStonesList.add(project.getEdsWordingByPWIdFirstStage());
        this.mileStonesList.add(project.getEdsWordingByPWIdSecondStage());
        this.mileStonesList.add(project.getEdsWordingByPWIdThirdStage());
        this.mileStonesList.add(project.getEdsWordingByPWIdFourthStage());
        refreshMilestoneValues(vCMBprelimStageValue);
        refreshMilestoneValues(vCMBrobustStageValue);
        refreshMilestoneValues(vCMBconsolidateStageValue);
        refreshMilestoneValues(vCMBclosedStageValue);

        vCMBprelimStageValue.setValue(project.getEdsWordingByPWIdFirstStage());
        vCMBrobustStageValue.setValue(project.getEdsWordingByPWIdSecondStage());
        vCMBconsolidateStageValue.setValue(project.getEdsWordingByPWIdThirdStage());
        vCMBclosedStageValue.setValue(project.getEdsWordingByPWIdFourthStage());
        vPDFprelimStageDateValue.setValue(project.getPDateFirstStage());
        vPDFrobustStageDateValue.setValue(project.getPDateSecondStage());
        vPDFconsolidateStageDateValue.setValue(project.getPDateThirdStage());
        vPDFclosedStageDateValue.setValue(project.getPDateFourthStage());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) project);
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

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsProject
     */
    private EdsProject project;
    /**
     * Variable to hold value of Combo box for Preliminary stage
     */
    private ComboBox vCMBprelimStageValue;
    /**
     * Variable to hold value of PopupDateField for Preliminary stage date
     */
    private PopupDateField vPDFprelimStageDateValue;
    /**
     * Variable to hold value of Combo box for Robust stage
     */
    private ComboBox vCMBrobustStageValue;
    /**
     * Variable to hold value of PopupDateField for Robust stage date
     */
    private PopupDateField vPDFrobustStageDateValue;
    /**
     * Variable to hold value of Combo box for consolidate stage
     */
    private ComboBox vCMBconsolidateStageValue;
    /**
     * Variable to hold value of PopupDateField for consolidate stage date
     */
    private PopupDateField vPDFconsolidateStageDateValue;
    /**
     * Variable to hold value of Combo box for Closed stage
     */
    private ComboBox vCMBclosedStageValue;
    /**
     * Variable to hold value of PopupDateField for closed stage date
     */
    private PopupDateField vPDFclosedStageDateValue;
    /**
     * Variable to hold value of List of EdsWording
     */

    private ArrayList<EdsWording> mileStonesList;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize editing component of project milestones.
     */
    private void init() {
        setMargin(true);
        setSpacing(true);
        setCaption(controller.getBundle().getString("Admin-proj-jal"));

        EDSdao dao = EDSdao.getInstance();

        this.mileStonesList = new ArrayList<EdsWording>(dao.getAllActiveWordingsByType(EdsWording.MILESTONE));

        // Preliminary milestone info
        HorizontalLayout prelimLayout = new HorizontalLayout();
        prelimLayout.setSpacing(true);
        this.vCMBprelimStageValue = new ComboBox(controller.getBundle().getString("menu-app-step-prelim"));
        this.vCMBprelimStageValue.setDescription(controller.getBundle().getString("Admin-proj-jal-prel"));
        initMilestoneValues(vCMBprelimStageValue, project.getEdsWordingByPWIdFirstStage());
        this.vPDFprelimStageDateValue = new PopupDateField("", project.getPDateFirstStage());
        this.vPDFprelimStageDateValue.setResolution(DateField.RESOLUTION_DAY);
        this.vPDFprelimStageDateValue.setImmediate(true);
        this.vPDFprelimStageDateValue.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Date selectedDate = (Date) vPDFprelimStageDateValue.getValue();
                Date robustDate = (Date) vPDFrobustStageDateValue.getValue();
                if (robustDate == null || selectedDate.after(robustDate)) {
                    vPDFrobustStageDateValue.setValue(selectedDate);
                }
            }
        });
        prelimLayout.addComponent(vCMBprelimStageValue);
        prelimLayout.addComponent(vPDFprelimStageDateValue);
        addComponent(prelimLayout);

        // Robust milestone info
        HorizontalLayout robustLayout = new HorizontalLayout();
        robustLayout.setSpacing(true);
        this.vCMBrobustStageValue = new ComboBox(controller.getBundle().getString("menu-app-step-rob"));
        this.vCMBrobustStageValue.setDescription(controller.getBundle().getString("Admin-proj-jal-robuste"));
        initMilestoneValues(vCMBrobustStageValue, project.getEdsWordingByPWIdSecondStage());
        this.vPDFrobustStageDateValue = new PopupDateField("", project.getPDateSecondStage());
        this.vPDFrobustStageDateValue.setResolution(DateField.RESOLUTION_DAY);
        this.vPDFrobustStageDateValue.setImmediate(true);
        this.vPDFrobustStageDateValue.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Date preliminaireDate = (Date) vPDFprelimStageDateValue.getValue();
                Date selectedDate = (Date) vPDFrobustStageDateValue.getValue();
                Date consolidateDate = (Date) vPDFconsolidateStageDateValue.getValue();
                if (consolidateDate == null || selectedDate.after(consolidateDate)) {
                    vPDFconsolidateStageDateValue.setValue(selectedDate);
                } else if (selectedDate.before(preliminaireDate)) {
                    vPDFrobustStageDateValue.setValue(preliminaireDate);
                }
            }
        });
        robustLayout.addComponent(vCMBrobustStageValue);
        robustLayout.addComponent(vPDFrobustStageDateValue);
        addComponent(robustLayout);

        // Consolidate milestone info
        HorizontalLayout consolidatelayout = new HorizontalLayout();
        consolidatelayout.setSpacing(true);
        this.vCMBconsolidateStageValue = new ComboBox(controller.getBundle().getString("Validation-step-cons"));
        this.vCMBconsolidateStageValue.setDescription(controller.getBundle().getString("Admin-proj-jal-consolide"));
        initMilestoneValues(vCMBconsolidateStageValue, project.getEdsWordingByPWIdThirdStage());
        this.vPDFconsolidateStageDateValue = new PopupDateField("", project.getPDateThirdStage());
        this.vPDFconsolidateStageDateValue.setResolution(DateField.RESOLUTION_DAY);
        this.vPDFconsolidateStageDateValue.setImmediate(true);
        this.vPDFconsolidateStageDateValue.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Date robustDate = (Date) vPDFrobustStageDateValue.getValue();
                Date selectedDate = (Date) vPDFconsolidateStageDateValue.getValue();
                Date closedDate = (Date) vPDFclosedStageDateValue.getValue();
                if (closedDate == null || selectedDate.after(closedDate)) {
                    vPDFclosedStageDateValue.setValue(selectedDate);
                } else if (selectedDate.before(robustDate)) {
                    vPDFrobustStageDateValue.setValue(robustDate);
                }
            }
        });
        consolidatelayout.addComponent(vCMBconsolidateStageValue);
        consolidatelayout.addComponent(vPDFconsolidateStageDateValue);
        addComponent(consolidatelayout);

        // Closed milestone info
        HorizontalLayout closedLayout = new HorizontalLayout();
        closedLayout.setSpacing(true);
        this.vCMBclosedStageValue = new ComboBox(controller.getBundle().getString("menu-app-step-clot"));
        this.vCMBclosedStageValue.setDescription(controller.getBundle().getString("Admin-proj-jal-cloture"));
        initMilestoneValues(vCMBclosedStageValue, project.getEdsWordingByPWIdFourthStage());
        this.vPDFclosedStageDateValue = new PopupDateField("", project.getPDateFourthStage());
        this.vPDFclosedStageDateValue.setResolution(DateField.RESOLUTION_DAY);
        this.vPDFclosedStageDateValue.setImmediate(true);
        this.vPDFclosedStageDateValue.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Date consolidateDate = (Date) vPDFconsolidateStageDateValue.getValue();
                Date selectedDate = (Date) vPDFclosedStageDateValue.getValue();
                if (selectedDate.before(consolidateDate)) {
                    vPDFrobustStageDateValue.setValue(consolidateDate);
                }
            }
        });
        closedLayout.addComponent(vCMBclosedStageValue);
        closedLayout.addComponent(vPDFclosedStageDateValue);
        addComponent(closedLayout);

    }

    /**
     * This Method set milestone values
     * 
     * @param milestoneBox Combo box for milestone
     * @param value EdsWording
     */
    private void initMilestoneValues(ComboBox milestoneBox, EdsWording value) {
        milestoneBox.setNullSelectionAllowed(true);
        milestoneBox.setImmediate(true);
        milestoneBox.setFilteringMode(Filtering.FILTERINGMODE_STARTSWITH);
        for (EdsWording milestone : mileStonesList) {
            milestoneBox.addItem(milestone);
            milestoneBox.setItemCaption(milestone, milestone.getWValue());
        }
        milestoneBox.setValue(value);
    }

    /**
     * This method refreshes milestone values
     * 
     * @param milestoneBox Combo box for milestone
     */
    private void refreshMilestoneValues(ComboBox milestoneBox) {
        milestoneBox.removeAllItems();
        for (EdsWording milestone : mileStonesList) {
            if (milestone != null) {
                milestoneBox.addItem(milestone);
                milestoneBox.setItemCaption(milestone, milestone.getWValue());
            }
        }
    }
}
