package com.inetpsa.eds.application.menu.edsnode;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.validation.HighValidationFormEditView;
import com.inetpsa.eds.application.content.eds.validation.HighValidationFormReadView;
import com.inetpsa.eds.application.content.eds.validation.LowValidationFormEditView;
import com.inetpsa.eds.application.content.eds.validation.LowValidationFormReadView;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsRole;
import com.inetpsa.eds.tools.mail.EdsMailMessageBuilder;

/**
 * Specific navigation node to validate a form. Corresponds to the "Validation" node. This node produces a component different edition depending on
 * the stage of validation of the form.
 * 
 * @author Geometric Ltd.
 */
public class EN_ValidationNode extends EN_ClosedStageFormNode {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     * @param readForm Read form of EDS.
     * @param editRight EDS write access.
     */
    public EN_ValidationNode(EdsApplicationController controller, A_EdsReadForm readForm, String editRight) {
        super(controller, readForm, null, editRight);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.EN_FormNode#save()
     */
    @Override
    public void save() {
        EDSdao dao = EDSdao.getInstance();

        EdsEds curenteds = dao.getEdsByRef(readForm.getEds().getEdsRef());

        if (curenteds.getEdsMajorVersion() != readForm.getEds().getEdsMajorVersion()
                || curenteds.getEdsMinorVersion() != readForm.getEds().getEdsMinorVersion()) {
            controller.closeEds(readForm.getEds());
            controller.openEds(curenteds);
            controller.showWarning(controller.getBundle().getString("eds-pop-warning-title"), controller.getBundle().getString("eds-pop-eds-reload"));
        } else {
            super.save();

            if (readForm.getEds().getEdsValidLvl() == EdsEds.VALIDATION_LVL_HIGH) {
                int newStage = ((HighValidationFormReadView) readForm).getFormData().getValidationStage();
                if (newStage > oldStage) {
                    validateStageAndVersion(((HighValidationFormReadView) readForm).getFormData(), newStage);
                }
            } else if (readForm.getEds().getEdsValidLvl() == EdsEds.VALIDATION_LVL_LOW) {
                int newStage = ((LowValidationFormReadView) readForm).getFormData().getValidationStage();
                if (newStage > oldStage) {
                    validateStageAndVersion(((LowValidationFormReadView) readForm).getFormData(), newStage);
                }
            } else {
                throw new RuntimeException(controller.getBundle().getString("app-eds-validate-inconnu") + readForm.getEds().getEdsValidLvl());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.EN_FormNode#edit()
     */
    @Override
    public void edit() {
        if (readForm.getEds().getEdsValidLvl() == EdsEds.VALIDATION_LVL_HIGH) {
            this.oldStage = ((HighValidationFormReadView) readForm).getFormData().getValidationStage();
        } else if (readForm.getEds().getEdsValidLvl() == EdsEds.VALIDATION_LVL_LOW) {
            this.oldStage = ((LowValidationFormReadView) readForm).getFormData().getValidationStage();
        }

        EdsRole userRole = controller.getAuthenticatedUser().getEdsRole();

        if (readForm.getEds().getEdsValidLvl() == EdsEds.VALIDATION_LVL_HIGH) {
            EdsHighValidationFormData formData = ((HighValidationFormReadView) readForm).getFormData();

            editForm = new HighValidationFormEditView(formData, controller, readForm.getEds());
        } else if (readForm.getEds().getEdsValidLvl() == EdsEds.VALIDATION_LVL_LOW) {
            EdsLowValidationFormData formData = ((LowValidationFormReadView) readForm).getFormData();

            editForm = new LowValidationFormEditView(formData, controller, readForm.getEds());
        }

        super.edit();
    }

    // PRIVATE
    /**
     * This variable is used to store the old stage of EDS.
     */
    private int oldStage = EdsApplicationController.PRELIM_STAGE;

    /**
     * This method is used to show the pop-up error message.
     */
    private void popError() {
        controller.showError(controller.getBundle().getString("pop-error-title"), controller.getBundle().getString("eds-no-right-message"));
    }
}
