package com.inetpsa.eds.application.content.eds.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.popup.ValidationErrorWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.dao.model.EdsValidation;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Buffered.SourceException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

/**
 * Editing validation component of a low record.
 * 
 * @author Geometric Ltd.
 */
public class LowValidationFormEditView extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param formData Form data to be validated.
     * @param controller EDS application controller object.
     * @param eds EDS details.
     */
    public LowValidationFormEditView(EdsLowValidationFormData formData, EdsApplicationController controller, EdsEds eds) {
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
        return preliminaryForm.isValid() && robustForm.isValid() && consolidatedForm.isValid() && supplierDataForm.isValid() && closedForm.isValid();
    }

    @Override
    public int isValidEDS() {
        if (!preliminaryForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
            if (robustForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-message-1"));
                return 1;

            } else if (consolidatedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-message-2"));
                return 1;
            } else if (closedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-message-3"));
                return 1;
            }
        } else if (!robustForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
            if (consolidatedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-message-4"));
                return 1;
            } else if (closedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-message-5"));
                return 1;
            } else if (eds.getEdsHasDrift() == 1) {
                controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-Drift-Error"));
                return 1;
            }
        } else if (!consolidatedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
            if (closedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-message-6"));
                return 1;
            } else if (eds.getEdsHasDrift() == 1) {
                controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-Drift-Error"));
                return 1;
            } else if (formData.getEdsEds().getEdsNumber96fcts().isEmpty()) {

                return -1;
            }
        } else if (consolidatedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
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
        } else if (closedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                && !supplierDataForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
            controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-message-7"));
            return 1;
        } else if (eds.getEdsHasDrift() == 1) {
            controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("Validation-Drift-Error"));
            return 1;
        } else if (robustForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
            if (formData.getEdsEds().getEdsNumber96fcts().isEmpty()
                    && (controller.retrieveEdsStage(eds, eds.getEdsProject()) < controller.ROBUST_STAGE)) {
                return -1;
            }
        }

        boolean isValid = preliminaryForm.isValid() && robustForm.isValid() && consolidatedForm.isValid() && supplierDataForm.isValid()
                && closedForm.isValid();

        return isValid ? 0 : 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {

        try {
            long date = System.currentTimeMillis();

            if (preliminaryForm.isModified()) {
                preliminaryForm.commitFor(controller.getAuthenticatedUser(), new Date(date));

                if (formData.getEdsValidationByLvfdPreliminaryId().getVStatus() == EdsValidation.VALIDATION_OK) {
                    formData.setLvfdStage(EdsApplicationController.ROBUST_STAGE);
                }
            }
            if (robustForm.isModified()) {
                robustForm.commitFor(controller.getAuthenticatedUser(), new Date(date));

                if (formData.getEdsValidationByLvfdRobustId().getVStatus() == EdsValidation.VALIDATION_OK) {
                    formData.setLvfdStage(EdsApplicationController.SOLID_STAGE);
                }
            }
            if (consolidatedForm.isModified()) {
                consolidatedForm.commitFor(controller.getAuthenticatedUser(), new Date(date));

                if (formData.getEdsValidationByLvfdConsolidatedId().getVStatus() == EdsValidation.VALIDATION_OK
                        && formData.getEdsValidationByLvfdSupplierDataId().getVStatus() == EdsValidation.VALIDATION_OK) {
                    formData.setLvfdStage(EdsApplicationController.CLOSED_STAGE);
                }
            }
            if (supplierDataForm.isModified()) {
                supplierDataForm.commitFor(controller.getAuthenticatedUser(), new Date(date));

                if (formData.getEdsValidationByLvfdConsolidatedId().getVStatus() == EdsValidation.VALIDATION_OK
                        && formData.getEdsValidationByLvfdSupplierDataId().getVStatus() == EdsValidation.VALIDATION_OK) {
                    formData.setLvfdStage(EdsApplicationController.CLOSED_STAGE);
                }
            }
            if (closedForm.isModified()) {
                closedForm.commitFor(controller.getAuthenticatedUser(), new Date(date));
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
    } // END OF COMMIT CHANGES

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        preliminaryForm.discard();
        robustForm.discard();
        consolidatedForm.discard();
        supplierDataForm.discard();
        closedForm.discard();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
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
        EDSdao.getInstance().refreshDetachedDBObject(formData);

    }

    public void refreshAll() {
        init();
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
     * Variable to store Supplier data icon.
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
    /**
     * Variable to store Low validation data.
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
     * Variable to store Preliminary stage validation form.
     */
    private EdsValidationForm preliminaryForm;
    /**
     * Variable to store Robust stage validation form.
     */
    private EdsValidationForm robustForm;
    /**
     * Variable to store Consolidated stage validation form.
     */
    private EdsValidationForm consolidatedForm;
    /**
     * Variable to store Supplier data validation form.
     */
    private EdsValidationForm supplierDataForm;
    /**
     * Variable to store Closed stage validation form.
     */
    private EdsValidationForm closedForm;

    /**
     * Initialization method.
     */
    private void init() {
        // GUI PRELIMINARY STAGE

        EdsValidation preliminaryValidation = formData.getEdsValidationByLvfdPreliminaryId();

        HorizontalLayout preliminaryValidationPanelLayout = new HorizontalLayout();
        preliminaryValidationPanelLayout.setWidth("100%");
        preliminaryValidationPanelLayout.setMargin(true);
        preliminaryValidationPanelLayout.setSpacing(true);

        this.preliminaryForm = new EdsValidationForm(preliminaryValidation, controller.getApplication().getLocale(), new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)) {
                    controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                            controller.getBundle().getString("Validation-prelim-mess"), 5000);
                }
            }
        }, controller);

        if (formData.getEdsValidationByLvfdPreliminaryId().getVStatus() == EdsValidation.VALIDATION_OK) {
            this.preliminaryForm.setReadOnly(true);
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_PRELIMINAIRE)) {

            preliminaryValidationPanelLayout.addComponent(this.preliminaryForm);

            Panel preliminaryValidationPanel = new Panel(String.format(controller.getBundle().getString("menu-app-step-prelim") + " : %s",
                    EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdFirstStage())),
                    preliminaryValidationPanelLayout);
            preliminaryValidationPanel.setIcon(ICON_PRELIM);
            this.addComponent(preliminaryValidationPanel);
        }

        // GUI ROBUST STAGE
        EdsValidation robustValidation = formData.getEdsValidationByLvfdRobustId();

        HorizontalLayout robustValidationPanelLayout = new HorizontalLayout();
        robustValidationPanelLayout.setWidth("100%");
        robustValidationPanelLayout.setMargin(true);
        robustValidationPanelLayout.setSpacing(true);

        this.robustForm = new EdsValidationForm(robustValidation, controller.getApplication().getLocale(), new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)
                        && preliminaryForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                    controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                            controller.getBundle().getString("Validation-robuste-mess"), 5000);
                }
            }
        }, controller);

        if (formData.getEdsValidationByLvfdRobustId().getVStatus() == EdsValidation.VALIDATION_OK) {
            this.robustForm.setReadOnly(true);
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_ROBUSTE)) {
            robustValidationPanelLayout.addComponent(this.robustForm);

            Panel robustValidationPanel = new Panel(String.format(controller.getBundle().getString("menu-app-step-rob") + " : %s",
                    EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdSecondStage())), robustValidationPanelLayout);
            robustValidationPanel.setIcon(ICON_ROBUST);
            this.addComponent(robustValidationPanel);
        }

        // GUI CONSOLIDATED STAGE
        EdsValidation consolidateValidation = formData.getEdsValidationByLvfdConsolidatedId();

        HorizontalLayout consolidateValidationPanelLayout = new HorizontalLayout();
        consolidateValidationPanelLayout.setWidth("100%");
        consolidateValidationPanelLayout.setMargin(true);
        consolidateValidationPanelLayout.setSpacing(true);

        this.consolidatedForm = new EdsValidationForm(consolidateValidation, controller.getApplication().getLocale(), new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)
                        && preliminaryForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                        && robustForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                    controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                            controller.getBundle().getString("Validation-consolide-mess"), 5000);
                }
            }
        }, controller);

        if (formData.getEdsValidationByLvfdConsolidatedId().getVStatus() == EdsValidation.VALIDATION_OK) {
            this.consolidatedForm.setReadOnly(true);
        }
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_LOW_CONSOLIDE)) {
            consolidateValidationPanelLayout.addComponent(this.consolidatedForm);

            Panel consolidateValidationPanel = new Panel(String.format(controller.getBundle().getString("Validation-step-cons") + " : %s",
                    EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdThirdStage())),
                    consolidateValidationPanelLayout);
            consolidateValidationPanel.setIcon(ICON_CONSOL);
            this.addComponent(consolidateValidationPanel);
        }
        // GUI DATA PROVIDER
        EdsValidation supplierDataValidation = formData.getEdsValidationByLvfdSupplierDataId();
        this.supplierDataForm = new EdsValidationForm(supplierDataValidation, controller.getApplication().getLocale(), controller);

        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_SUPPLIER_DATA)) // Si
        // l'utilisateur
        // peut éditer
        // la
        // validation
        // des données
        // fournisseur
        {
            HorizontalLayout supplierDataValidationPanelLayout = new HorizontalLayout();
            supplierDataValidationPanelLayout.setWidth("100%");
            supplierDataValidationPanelLayout.setMargin(true);
            supplierDataValidationPanelLayout.setSpacing(true);

            if (formData.getEdsValidationByLvfdSupplierDataId().getVStatus() == EdsValidation.VALIDATION_OK) {
                this.supplierDataForm.setReadOnly(true);
            }

            supplierDataValidationPanelLayout.addComponent(this.supplierDataForm);

            Panel supplierDataValidationPanel = new Panel(controller.getBundle().getString("Validation-resp-dev"), supplierDataValidationPanelLayout);
            supplierDataValidationPanel.setIcon(ICON_SUPP_DATA);
            this.addComponent(supplierDataValidationPanel);
        }

        // GUI CLOSED STAGE
        EdsValidation closedValidation = formData.getEdsValidationByLvfdClosedId();

        HorizontalLayout closedValidationPanelLayout = new HorizontalLayout();
        closedValidationPanelLayout.setWidth("100%");
        closedValidationPanelLayout.setMargin(true);
        closedValidationPanelLayout.setSpacing(true);

        this.closedForm = new EdsValidationForm(closedValidation, controller.getApplication().getLocale(), new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue().equals(EdsValidation.VALIDATION_OK)
                        && preliminaryForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                        && robustForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)
                        && consolidatedForm.getField(PROPERTY_STATUS).getValue().equals(EdsValidation.VALIDATION_OK)) {
                    controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"),
                            controller.getBundle().getString("Validation-cloture-mess"), 5000);
                }
            }
        }, controller);
        if (controller.userHasSufficientRights(EdsRight.APP_EDS_VALIDATE_LOW_CLOTURE)) {
            closedValidationPanelLayout.addComponent(this.closedForm);

            Panel closedValidationPanel = new Panel(String.format(controller.getBundle().getString("menu-app-step-clot") + " : %s",
                    EDSTools.getWordingValueOrNullRepresentation(eds.getEdsProject().getEdsWordingByPWIdFourthStage())), closedValidationPanelLayout);
            closedValidationPanel.setIcon(ICON_CLOSED);
            this.addComponent(closedValidationPanel);
        }
    }
}
