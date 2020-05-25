package com.inetpsa.eds.application.content.eds.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.popup.ValidationErrorWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.dao.model.EdsValidation;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Buffered.SourceException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Editing component validating a record level 2 (Responsible Energy).
 * 
 * @author Geometric Ltd.
 */
public class HighValidationFormEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param formData Form data to be validated.
     * @param controller EDS application controller object.
     * @param eds EDS details.
     */
    public HighValidationFormEditView(EdsHighValidationFormData formData, EdsApplicationController controller, EdsEds eds) {
        this.formData = formData;
        this.controller = controller;
        this.eds = eds;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {

        return preliminaryForm.isValid() && robustForm.isValid() && consolidatedDbeesForm.isValid()
                && consolidatedDbeedForm.isValid() && consolidatedCadeForm.isValid() && supplierDataForm.isValid() && closedDbeesForm.isValid()
                && closedDbeedForm.isValid() && closedCadeForm.isValid();
    }

    @Override
    public int isValidEDS() {
        if (!preliminaryForm.isValid()) {
            return 1;
        } else if (!robustForm.isValid()) {
            return 1;
        } else if (!consolidatedDbeesForm.isValid()) {
            return 1;
        } else if (!closedDbeesForm.isValid()) {
            return 1;
        } else if (!consolidatedDbeedForm.isValid()) {
            return 1;
        } else if (!closedDbeedForm.isValid()) {
            return 1;
        } else if (!supplierDataForm.isValid()) {
            return 1;
        } else if (!consolidatedCadeForm.isValid()) {
            return 1;
        } else if (!closedCadeForm.isValid()) {
            return 1;
        } else if (eds.getEdsHasDrift() == 1) {
            controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-Drift-Error"));
            return 1;
        }

        switch (formData.getValidationStage()) {
        case EdsApplicationController.PRELIM_STAGE:
            if (!arePreliminaryFormsStatusValidated()) {
                if (areRobustFormsStatusValidated()) {
                    controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle()
                            .getString("Validation-message-1"));
                    return 1;
                }
                if (areConsolidatedFormsStatusValidated()) {
                    controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle()
                            .getString("Validation-message-2"));
                    return 1;
                }
                if (areClosedFormsStatusValidated()) {
                    controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle()
                            .getString("Validation-message-3"));
                    return 1;
                }
            }
        case EdsApplicationController.ROBUST_STAGE:
            if (!areRobustFormsStatusValidated()) {
                if (areConsolidatedFormsStatusValidated()) {
                    controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle()
                            .getString("Validation-message-4"));
                    return 1;
                }
                if (areClosedFormsStatusValidated()) {
                    controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle()
                            .getString("Validation-message-5"));
                    return 1;
                }
            } else if (areRobustFormsStatusValidated() && !areConsolidatedFormsStatusValidated()) {
                if (formData.getEdsEds().getEdsNumber96fcts().isEmpty()) {
                    return -1;
                }
            }
        case EdsApplicationController.SOLID_STAGE:
            if (!areConsolidatedFormsStatusValidated()) {
                if (areClosedFormsStatusValidated()) {
                    controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle()
                            .getString("Validation-message-6"));
                    return 1;
                }
            } else if (areConsolidatedFormsStatusValidated()
                    && supplierDataForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {

                if (formData.getEdsEds().getEdsNumber96fnrs().isEmpty()) {
                    ValidationErrorWindow errorWindow = new ValidationErrorWindow(controller, eds);
                    errorWindow.setModal(true);
                    getApplication().getMainWindow().addWindow(errorWindow);
                    errorWindow.center();
                    return 1;
                }
                if (formData.getEdsEds().getEdsNumber96fcts().isEmpty()
                        && (controller.retrieveEdsStage(eds, eds.getEdsProject()) < controller.ROBUST_STAGE)) {
                    return -1;
                }
            }
        case EdsApplicationController.CLOSED_STAGE:
            if (areClosedFormsStatusValidated() && !supplierDataForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-message-7"));
                return 1;
            }
            break;
        default:
            throw new IllegalStateException("Validation stage unknown: " + formData.getValidationStage());
        }
        boolean isValid = preliminaryForm.isValid() && robustForm.isValid() && consolidatedDbeesForm.isValid()
                && consolidatedDbeedForm.isValid() && consolidatedCadeForm.isValid() && supplierDataForm.isValid() && closedDbeesForm.isValid()
                && closedDbeedForm.isValid() && closedCadeForm.isValid();
        return isValid ? 0 : 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        if (!(isValidEDS() == 0 || isValidEDS() == -1)) {
            return false;
        }

        try {
            long date = System.currentTimeMillis();

            // VALIDATION LVL 1
            if (preliminaryForm.isModified()) {
                preliminaryForm.commitFor(controller.getAuthenticatedUser(), new Date(date));

                if (formData.getEdsValidationByHvfdRsPrimaryId().getVStatus() == EdsValidation.VALIDATION_OK) {
                    formData.setHvfdStage(EdsApplicationController.ROBUST_STAGE);
                }
            }
            // VALIDATION LVL 2
            if (robustForm.isModified()) {
                robustForm.commitFor(controller.getAuthenticatedUser(), new Date(date));

                if (formData.getEdsValidationByHvfdReRobustId().getVStatus() == EdsValidation.VALIDATION_OK) {
                    formData.setHvfdStage(EdsApplicationController.SOLID_STAGE);
                }
            }

            // VALIDATION LVL 3
            if (consolidatedDbeesForm.isModified()) {
                consolidatedDbeesForm.commitFor(controller.getAuthenticatedUser(), new Date(date));
            }
            if (closedDbeesForm.isModified()) {
                closedDbeesForm.commitFor(controller.getAuthenticatedUser(), new Date(date));
            }
            // VALIDATION LVL 4
            if (consolidatedDbeedForm.isModified()) {
                consolidatedDbeedForm.commitFor(controller.getAuthenticatedUser(), new Date(date));
            }
            if (closedDbeedForm.isModified()) {
                closedDbeedForm.commitFor(controller.getAuthenticatedUser(), new Date(date));
            }
            // VALIDATION SUPPLIER DATA
            if (supplierDataForm.isModified()) {
                supplierDataForm.commitFor(controller.getAuthenticatedUser(), new Date(date));
            }
            // VALIDATION LVL 5
            if (consolidatedCadeForm.isModified()) {
                consolidatedCadeForm.commitFor(controller.getAuthenticatedUser(), new Date(date));
            }
            if (closedCadeForm.isModified()) {
                closedCadeForm.commitFor(controller.getAuthenticatedUser(), new Date(date));
            }

            if (formData.getConsolidatedStatus() == EdsValidation.VALIDATION_OK) {
                formData.setHvfdStage(EdsApplicationController.CLOSED_STAGE);
            }
            eds.setEdsModifDate(new Date());

            return true;
        } catch (InvalidValueException e) {
            e.printStackTrace();
            return false;
        } catch (SourceException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {

        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_PRELIMINAIRE)) {
            preliminaryForm.discard();
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_ROBUSTE)) {
            robustForm.discard();
        }

        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_3)) {
            consolidatedDbeesForm.discard();
            closedDbeesForm.discard();
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_4)) {
            consolidatedDbeedForm.discard();
            closedDbeedForm.discard();
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_SUPPLIER_DATA)) {
            supplierDataForm.discard();
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_5)) {
            consolidatedCadeForm.discard();
            closedCadeForm.discard();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<EdsHighValidationFormData> getAllItemsToSave() {
        return Collections.singleton(formData);
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
        EDSdao dao = EDSdao.getInstance();
        eds = dao.getEdsByRef(eds.getEdsRef());
        dao.refreshDetachedDBObject(formData);

    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store Preliminary stage icon.
     */
    private static final Resource ICON_PRELIM = ResourceManager.getInstance().getThemeIconResource("icons/preliminary.ico");
    /**
     * Variable to store Robust stage icon.
     */
    private static final Resource ICON_ROBUST = ResourceManager.getInstance().getThemeIconResource("icons/robust.ico");
    /**
     * Variable to store Consolidated stage icon.
     */
    private static final Resource ICON_CONSOL = ResourceManager.getInstance().getThemeIconResource("icons/consolidated.ico");
    /**
     * Variable to store warning icon.
     */
    private static final Resource ICON_SUPP_DATA = ResourceManager.getInstance().getThemeIconResource("icons/small-warning.png");
    /**
     * Variable to store Closed stage icon.
     */
    private static final Resource ICON_CLOSED = ResourceManager.getInstance().getThemeIconResource("icons/closed.ico");
    /**
     * Variable to store property status.
     */
    private static final String PROPERTY_STATUS = "VStatus";
    // GENERAL
    /**
     * Variable to store High validation form data.
     */
    private EdsHighValidationFormData formData;
    /**
     * Variable to store EDS application controller form data.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store EDS details.
     */
    private EdsEds eds;
    // VALIDATION LVL 1
    /**
     * Variable to store Preliminary stage validation form data.
     */
    private EdsValidationForm preliminaryForm;
    // VALIDATION LVL 2
    /**
     * Variable to store Robust stage validation form data.
     */
    private EdsValidationForm robustForm;

    // VALIDATION LVL 3
    /**
     * Variable to store Consolidated stage validation form data.
     */
    private EdsValidationForm consolidatedDbeesForm;
    /**
     * Variable to store Closed stage validation form data.
     */
    private EdsValidationForm closedDbeesForm;
    // VALIDATION LVL 4
    /**
     * Variable to store Consolidated stage validation form data.
     */
    private EdsValidationForm consolidatedDbeedForm;
    /**
     * Variable to store Closed stage validation form data.
     */
    private EdsValidationForm closedDbeedForm;
    // VALIDATION SUPPLIER DATA
    /**
     * Variable to store Supplier validation form data.
     */
    private EdsValidationForm supplierDataForm;
    // VALIDATION LVL 5
    /**
     * Variable to store Consolidated stage validation form data.
     */
    private EdsValidationForm consolidatedCadeForm;
    /**
     * Variable to store Closed stage validation form data.
     */
    private EdsValidationForm closedCadeForm;

    /**
     * Initialization method. This method is used to validate all the four stages and Device development leader.
     */
    private void init() {
        this.setSpacing(true);

        // GUI PRELIMINARY STAGE VALIDATION
        EdsValidation validation = formData.getEdsValidationByHvfdRsPrimaryId();
        this.preliminaryForm = new EdsValidationForm(validation, controller.getApplication().getLocale(), new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)) {
                    controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                            controller.getBundle().getString("Validation-prelim-mess"), 5000);
                }
            }
        }, controller);

        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_PRELIMINAIRE)) {
            VerticalLayout preliminaryValidationPanelLayout = new VerticalLayout();
            preliminaryValidationPanelLayout.setWidth("100%");
            preliminaryValidationPanelLayout.setMargin(true);
            preliminaryValidationPanelLayout.setSpacing(true);

            if (formData.getPreliminaryStatus() == EdsValidation.VALIDATION_OK) {
                this.preliminaryForm.setReadOnly(true);
            }

            preliminaryValidationPanelLayout.addComponent(this.preliminaryForm);

            Panel preliminaryValidationPanel = new Panel(String.format(controller.getBundle().getString("menu-app-step-prelim") + " : %s",
                    EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdFirstStage())),
                    preliminaryValidationPanelLayout);
            preliminaryValidationPanel.setIcon(ICON_PRELIM);
            this.addComponent(preliminaryValidationPanel);
        }

        // GUI ROBUST STAGE VALIDATION
        EdsValidation robustValidation = formData.getEdsValidationByHvfdReRobustId();
        this.robustForm = new EdsValidationForm(robustValidation, controller.getApplication().getLocale(), new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)) {
                    controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                            controller.getBundle().getString("Validation-robuste-mess"), 5000);
                }
            }
        }, controller);

        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_ROBUSTE)) {
            VerticalLayout robustValidationPanelLayout = new VerticalLayout();
            robustValidationPanelLayout.setWidth("100%");
            robustValidationPanelLayout.setMargin(true);
            robustValidationPanelLayout.setSpacing(true);

            if (formData.getRobustStatus() == EdsValidation.VALIDATION_OK) {
                this.robustForm.setReadOnly(true);
            }

            robustValidationPanelLayout.addComponent(this.robustForm);

            Panel robustValidationPanel = new Panel(String.format(controller.getBundle().getString("menu-app-step-rob") + " : %s",
                    EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdSecondStage())), robustValidationPanelLayout);
            robustValidationPanel.setIcon(ICON_ROBUST);
            this.addComponent(robustValidationPanel);
        }

        EdsValidation consolidatedDbeesValidation = formData.getEdsValidationByHvfdDbeesConsolidateId();
        this.consolidatedDbeesForm = new EdsValidationForm(consolidatedDbeesValidation, controller.getApplication().getLocale(),
                new ValueChangeListener() {
                    public void valueChange(ValueChangeEvent event) {
                        if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)
                                && consolidatedCadeForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                                && consolidatedDbeedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                                && supplierDataForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                            controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                                    controller.getBundle().getString("Validation-consolide-mess"), 5000);
                        }
                    }
                }, controller);

        EdsValidation consolidatedDbeedValidation = formData.getEdsValidationByHvfdDbeedConsolidateId();
        this.consolidatedDbeedForm = new EdsValidationForm(consolidatedDbeedValidation, controller.getApplication().getLocale(),
                new ValueChangeListener() {
                    public void valueChange(ValueChangeEvent event) {
                        if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)
                                && consolidatedCadeForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                                && consolidatedDbeesForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                                && supplierDataForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                            controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                                    controller.getBundle().getString("Validation-consolide-mess"), 5000);
                        }
                    }
                }, controller);

        EdsValidation consolidatedCadeValidation = formData.getEdsValidationByHvfdCadeConsolidateId();
        this.consolidatedCadeForm = new EdsValidationForm(consolidatedCadeValidation, controller.getApplication().getLocale(),
                new ValueChangeListener() {
                    public void valueChange(ValueChangeEvent event) {
                        if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)
                                && consolidatedDbeedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                                && consolidatedDbeesForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                                && supplierDataForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                            controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                                    controller.getBundle().getString("Validation-consolide-mess"), 5000);
                        }
                    }
                }, controller);

        // GUI DATA PROVIDER / AKA. Device development leader
        EdsValidation supplierDataValidation = formData.getEdsValidationByHvfdSupplierDataId();
        this.supplierDataForm = new EdsValidationForm(supplierDataValidation, controller.getApplication().getLocale(), controller);

        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_3)
                || controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_4)
                || controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_5))

        {
            VerticalLayout consolidateValidationPanelLayout = new VerticalLayout();
            consolidateValidationPanelLayout.setWidth("100%");
            consolidateValidationPanelLayout.setMargin(true);
            consolidateValidationPanelLayout.setSpacing(true);

            if (formData.getConsolidatedStatus() == EdsValidation.VALIDATION_OK) {
                this.consolidatedDbeesForm.setReadOnly(true);
                this.consolidatedDbeedForm.setReadOnly(true);
                this.supplierDataForm.setReadOnly(true);
                this.consolidatedCadeForm.setReadOnly(true);
            }

            if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_3)) {
                Label vLBLsubTitle = new Label(controller.getBundle().getString("Validate-producteur-stockeur"));
                vLBLsubTitle.setStyleName("h2");
                consolidateValidationPanelLayout.addComponent(vLBLsubTitle);
                consolidateValidationPanelLayout.addComponent(this.consolidatedDbeesForm);
            }
            if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_4)) {
                Label vLBLsubTitle = new Label(controller.getBundle().getString("Validation-resp-arch-simu"));
                vLBLsubTitle.setStyleName("h2");
                consolidateValidationPanelLayout.addComponent(vLBLsubTitle);
                consolidateValidationPanelLayout.addComponent(this.consolidatedDbeedForm);
            }
            if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_5)) {
                Label vLBLsubTitle = new Label(controller.getBundle().getString("Validation-resp-arch-conc"));
                vLBLsubTitle.setStyleName("h2");
                consolidateValidationPanelLayout.addComponent(vLBLsubTitle);
                consolidateValidationPanelLayout.addComponent(this.consolidatedCadeForm);
            }

            Panel consolidateValidationPanel = new Panel(String.format(controller.getBundle().getString("Validation-step-cons") + " : %s",
                    EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdThirdStage())),
                    consolidateValidationPanelLayout);
            consolidateValidationPanel.setIcon(ICON_CONSOL);
            this.addComponent(consolidateValidationPanel);
        }

        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_SUPPLIER_DATA)) // If the user
        // can edit the
        // validation
        // of supplier
        // data
        {
            VerticalLayout supplierDataValidationPanelLayout = new VerticalLayout();
            supplierDataValidationPanelLayout.setWidth("100%");
            supplierDataValidationPanelLayout.setMargin(true);
            supplierDataValidationPanelLayout.setSpacing(true);

            supplierDataValidationPanelLayout.addComponent(this.supplierDataForm);

            // Device development leader Panel
            Panel supplierDataValidationPanel = new Panel(controller.getBundle().getString("Validation-resp-dev"), supplierDataValidationPanelLayout);
            supplierDataValidationPanel.setIcon(ICON_SUPP_DATA);
            this.addComponent(supplierDataValidationPanel);
        }

        EdsValidation closedDbeesValidation = formData.getEdsValidationByHvfdDbeesClosedId();
        this.closedDbeesForm = new EdsValidationForm(closedDbeesValidation, controller.getApplication().getLocale(), new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)
                        && closedCadeForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                        && closedDbeedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                    controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                            controller.getBundle().getString("Validation-cloture-mess"), 5000);
                }
            }
        }, controller);

        EdsValidation closedDbeedValidation = formData.getEdsValidationByHvfdDbeedClosedId();
        this.closedDbeedForm = new EdsValidationForm(closedDbeedValidation, controller.getApplication().getLocale(), new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)
                        && closedCadeForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                        && closedDbeesForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                    controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                            controller.getBundle().getString("Validation-cloture-mess"), 5000);
                }
            }
        }, controller);

        EdsValidation closedCadeValidation = formData.getEdsValidationByHvfdCadeClosedId();
        this.closedCadeForm = new EdsValidationForm(closedCadeValidation, controller.getApplication().getLocale(), new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)
                        && closedDbeedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                        && closedDbeesForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                    controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                            controller.getBundle().getString("Validation-cloture-mess"), 5000);
                }
            }
        }, controller);

        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_3)
                || controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_4)
                || controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_5)) {
            VerticalLayout closedValidationPanelLayout = new VerticalLayout();
            closedValidationPanelLayout.setWidth("100%");
            closedValidationPanelLayout.setMargin(true);
            closedValidationPanelLayout.setSpacing(true);

            if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_3)) {
                Label vLBLsubTitle = new Label(controller.getBundle().getString("Validate-producteur-stockeur"));
                vLBLsubTitle.setStyleName("h2");
                closedValidationPanelLayout.addComponent(vLBLsubTitle);
                closedValidationPanelLayout.addComponent(this.closedDbeesForm);
            }
            if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_4)) {
                Label vLBLsubTitle = new Label(controller.getBundle().getString("Validation-resp-arch-simu"));
                vLBLsubTitle.setStyleName("h2");
                closedValidationPanelLayout.addComponent(vLBLsubTitle);
                closedValidationPanelLayout.addComponent(this.closedDbeedForm);
            }
            if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_HIGH_LEVEL_5)) {
                Label vLBLsubTitle = new Label(controller.getBundle().getString("Validation-resp-arch-conc"));
                vLBLsubTitle.setStyleName("h2");
                closedValidationPanelLayout.addComponent(vLBLsubTitle);
                closedValidationPanelLayout.addComponent(this.closedCadeForm);
            }

            Panel closedValidationPanel = new Panel(String.format(controller.getBundle().getString("menu-app-step-clot") + " : %s",
                    EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdFourthStage())), closedValidationPanelLayout);
            closedValidationPanel.setIcon(ICON_CLOSED);
            this.addComponent(closedValidationPanel);
        }

    } // END INIT

    /**
     * This method is used to check if Preliminary stage is validated.
     * 
     * @return true if validated, else false.
     */
    private boolean arePreliminaryFormsStatusValidated() {
        return preliminaryForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK);
    }

    /**
     * This method is used to check if Robust stage is validated.
     * 
     * @return true if validated, else false.
     */
    private boolean areRobustFormsStatusValidated() {
        return robustForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK);
    }

    /**
     * This method is used to check if Consolidated stage is validated.
     * 
     * @return true if validated, else false.
     */
    private boolean areConsolidatedFormsStatusValidated() {
        return consolidatedCadeForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                && consolidatedDbeesForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                && consolidatedDbeedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK);
    }

    /**
     * This method is used to check if Closed stage is validated.
     * 
     * @return true if validated, else false.
     */
    private boolean areClosedFormsStatusValidated() {
        return closedCadeForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                && closedDbeesForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                && closedDbeedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK);
    }
}
