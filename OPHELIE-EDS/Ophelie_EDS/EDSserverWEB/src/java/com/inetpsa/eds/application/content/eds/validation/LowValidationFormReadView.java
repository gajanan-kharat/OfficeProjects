package com.inetpsa.eds.application.content.eds.validation;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.genericdata.EdsChronogram;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsValidation;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Reading component form for validation of a EDS record.
 * 
 * @author Geometric Ltd.
 */
public class LowValidationFormReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param formData Form data to be validated.
     * @param controller EDS application controller object.
     * @param eds EDS details.
     */
    public LowValidationFormReadView(EdsLowValidationFormData formData, EdsApplicationController controller, EdsEds eds) {
        super(controller);
        this.formData = formData;
        this.controller = controller;
        this.eds = eds;
        init();
    }

    /**
     * This method is used to retrieve the EDS validation form details.
     * 
     * @return validation form data.
     */
    public EdsLowValidationFormData getFormData() {
        return formData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "project-project-step-title-valid";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "Validation-title-0";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return HighValidationFormBuilder.ID;
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
        chronogram.refreshView(formData, eds.getEdsProject());

        preliminaryValidationPanelLayout.removeAllComponents();
        preliminaryValidationPanelLayout.addComponent(createValidationView(formData.getEdsValidationByLvfdPreliminaryId()));

        robustValidationPanelLayout.removeAllComponents();
        robustValidationPanelLayout.addComponent(createValidationView(formData.getEdsValidationByLvfdRobustId()));

        consolidatedValidationPanelLayout.removeAllComponents();
        consolidatedValidationPanelLayout.addComponent(createValidationView(formData.getEdsValidationByLvfdConsolidatedId()));

        supplierDataValidationPanelLayout.removeAllComponents();
        supplierDataValidationPanelLayout.addComponent(createValidationView(formData.getEdsValidationByLvfdSupplierDataId()));

        closedValidationPanelLayout.removeAllComponents();
        closedValidationPanelLayout.addComponent(createValidationView(formData.getEdsValidationByLvfdClosedId()));
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

    public void refreshAll() {
        init();
        // refreshItems();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store the Preliminary stage icon.
     */
    private static final Resource ICON_PRELIM = ResourceManager.getInstance().getThemeIconResource("icons/preliminary.ico");
    /**
     * Variable to store the Robust stage icon.
     */
    private static final Resource ICON_ROBUST = ResourceManager.getInstance().getThemeIconResource("icons/robust.ico");
    /**
     * Variable to store the Consolidated stage icon.
     */
    private static final Resource ICON_CONSOL = ResourceManager.getInstance().getThemeIconResource("icons/consolidated.ico");
    /**
     * Variable to store the Warning icon.
     */
    private static final Resource ICON_SUPP_DATA = ResourceManager.getInstance().getThemeIconResource("icons/small-warning.png");
    /**
     * Variable to store the Closed stage icon.
     */
    private static final Resource ICON_CLOSED = ResourceManager.getInstance().getThemeIconResource("icons/closed.ico");
    /**
     * Variable to store the Date format.
     */
    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy à HH:mm:ss");
    /**
     * Variable to store EDS low validation form data.
     */
    private EdsLowValidationFormData formData;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * Preliminary stage validation panel.
     */
    private Panel preliminaryValidationPanel;
    /**
     * Robust stage validation panel.
     */
    private Panel robustValidationPanel;
    /**
     * Consolidated stage validation panel.
     */
    private Panel consolidatedValidationPanel;
    /**
     * Closed stage validation panel.
     */
    private Panel closedValidationPanel;
    /**
     * Variable to store EDS chronogram details.
     */
    private EdsChronogram chronogram;
    /**
     * Layout of Preliminary stage validation panel.
     */
    final private VerticalLayout preliminaryValidationPanelLayout = new VerticalLayout();
    /**
     * Layout of Robust stage validation panel.
     */
    final private VerticalLayout robustValidationPanelLayout = new VerticalLayout();
    /**
     * Layout of Consolidated stage validation panel.
     */
    final private VerticalLayout consolidatedValidationPanelLayout = new VerticalLayout();
    /**
     * Layout of Supplier data validation panel.
     */
    final private VerticalLayout supplierDataValidationPanelLayout = new VerticalLayout();
    /**
     * Layout of Closed stage validation panel.
     */
    final private VerticalLayout closedValidationPanelLayout = new VerticalLayout();

    /**
     * Initialization method.
     */
    private void init() {
        this.setSpacing(true);

        // Validation summary table of 4 stages.
        chronogram = new EdsChronogram(controller);

        // Panel preliminary stage.
        preliminaryValidationPanel = new Panel(String.format(controller.getBundle().getString("menu-app-step-prelim") + " : %s",
                EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdFirstStage())), preliminaryValidationPanelLayout);
        preliminaryValidationPanel.setIcon(ICON_PRELIM);

        // Panel robust stage.
        robustValidationPanel = new Panel(String.format(controller.getBundle().getString("menu-app-step-rob") + " : %s",
                EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdSecondStage())), robustValidationPanelLayout);
        robustValidationPanel.setIcon(ICON_ROBUST);

        // Panel consolidated stage.
        consolidatedValidationPanel = new Panel(String.format(controller.getBundle().getString("Validation-step-cons") + " : %s",
                EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdThirdStage())), consolidatedValidationPanelLayout);
        consolidatedValidationPanel.setIcon(ICON_CONSOL);

        // Panel data provider.
        Panel supplierDataValidationPanel = new Panel(controller.getBundle().getString("Validation-resp-dev"), supplierDataValidationPanelLayout);
        supplierDataValidationPanel.setIcon(ICON_SUPP_DATA);

        // Panel closed stage.
        closedValidationPanel = new Panel(String.format(controller.getBundle().getString("menu-app-step-clot") + " : %s",
                EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdFourthStage())), closedValidationPanelLayout);
        closedValidationPanel.setIcon(ICON_CLOSED);

        addComponent(chronogram);
        addComponent(preliminaryValidationPanel);
        addComponent(robustValidationPanel);
        addComponent(consolidatedValidationPanel);
        addComponent(supplierDataValidationPanel);
        addComponent(closedValidationPanel);
    }

    /**
     * This method is used to create validation label.
     * 
     * @param validationStatus value of validation.
     * @return Validation label.
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
     * This method is used to create validation label with listener.
     * 
     * @param validationStatus value of validation.
     * @param clickListener listener to be added to the label.
     * @return Validation label.
     */
    private Component createValidationLabel(int validationStatus, ClickListener clickListener) {
        if (clickListener == null) {
            return createValidationLabel(validationStatus);
        }

        Button vBTvalidation = new Button(EdsValidation.getValidationText(validationStatus), clickListener);
        switch (validationStatus) {
        case EdsValidation.VALIDATION_NONE:
            vBTvalidation.setStyleName("validation-none");
            break;
        case EdsValidation.VALIDATION_STRICT_NOK:
            vBTvalidation.setStyleName("validation-strict-nok");
            break;
        case EdsValidation.VALIDATION_WEAK_NOK:
            vBTvalidation.setStyleName("validation-weak-nok");
            break;
        case EdsValidation.VALIDATION_OK:
            vBTvalidation.setStyleName("validation-ok");
            break;
        }
        vBTvalidation.setSizeUndefined();
        vBTvalidation.setWidth("100px");
        return vBTvalidation;
    }

    /**
     * This method is used to create the validation read view of each stage.
     * 
     * @param validation Validation to be displayed.
     * @return View of validation.
     */
    private VerticalLayout createValidationView(EdsValidation validation) {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setMargin(true);
        layout.setSpacing(true);

        if (validation.getEdsUser() != null) {
            layout.addComponent(new Label(controller.getBundle().getString("eds-comment-label") + " :"));
            layout.addComponent(new Label(EDSTools.getWordingValueByLocale(validation.getEdsWording(), controller.getApplication().getLocale())));
            if (validation.getVComment2() != null) {
                layout.addComponent(new Label(validation.getVComment2()));
            }
            layout.addComponent(createValidationLabel(validation.getVStatus()));
            Label vLBLeditionTag = new Label(MessageFormat.format(controller.getBundle().getString("edited-by"), validation.getEdsUser()
                    .toFullIdentity(), df.format(validation.getVValidationDate())));
            vLBLeditionTag.setStyleName("edition-tag");
            layout.addComponent(vLBLeditionTag);
        } else {
            layout.addComponent(new Label(controller.getBundle().getString("generic-data-com-no-com")));
            layout.addComponent(new Label(EDSTools.getWordingValueByLocale(validation.getEdsWording(), controller.getApplication().getLocale())));
            if (validation.getVComment2() != null) {
                layout.addComponent(new Label(validation.getVComment2()));
            }
            layout.addComponent(createValidationLabel(validation.getVStatus()));
            Label vLBLeditionTag = new Label(MessageFormat.format(controller.getBundle().getString("edited-by"), "--", "--"));
            vLBLeditionTag.setStyleName("edition-tag");
            layout.addComponent(vLBLeditionTag);
        }

        return layout;
    }
}
