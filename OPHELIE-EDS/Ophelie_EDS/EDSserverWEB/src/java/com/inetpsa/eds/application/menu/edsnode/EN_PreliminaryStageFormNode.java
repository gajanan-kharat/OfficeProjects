package com.inetpsa.eds.application.menu.edsnode;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.model.EdsRight;

/**
 * This class represents the component representing the node "Preliminary stage" in menu "EDS navigation".
 * 
 * @author Geometric Ltd.
 */
public class EN_PreliminaryStageFormNode extends EN_FormNode {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     * @param readForm Read form of EDS.
     * @param editForm Edit form of EDS.
     * @param editRight EDS write access.
     */
    public EN_PreliminaryStageFormNode(EdsApplicationController controller, A_EdsReadForm readForm, A_EdsEditForm editForm, String editRight) {
        super(controller, readForm, editForm, editRight);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.EN_FormNode#rebuildIdleActionBar()
     */
    @Override
    protected void rebuildIdleActionBar() {
        if (controller.retrieveEdsStage(readForm.getEds(), readForm.getEds().getEdsProject()) > EdsApplicationController.PRELIM_STAGE) // TODO :
                                                                                                                                       // Mettre le
                                                                                                                                       // projet
                                                                                                                                       // courant au
                                                                                                                                       // lieu du
                                                                                                                                       // projet
                                                                                                                                       // lanceur
        {
            getController().getActionBar().clear();
            if (controller.userHasSufficientRights(EdsRight.APP_EDS_FREEZE_EDS)) {
                getController().getActionBar().addLeftButton(getvBTversion());
            }
            if (controller.userHasSufficientRights(EdsRight.APP_EDS_RECONDUCT_EDS_WITHOUT_MODIF)) {
                getController().getActionBar().addLeftButton(getReconductWithoutModifButton());
            }
            if (controller.userHasSufficientRights(EdsRight.APP_EDS_RECONDUCT_EDS_WITH_MODIF)) {
                getController().getActionBar().addLeftButton(getReconductWithModifButton());
            }
            if (controller.userHasSufficientRights(EdsRight.APP_EDS_RECONSULT_EDS)) {
                getController().getActionBar().addLeftButton(getReconsultButton());
            }
            if (controller.userHasSufficientRights(EdsRight.APP_GENERAL_BE_EDS_AFFECTATION)) {
                getController().getActionBar().addLeftButton(getAffectationButton());
            }

            getController().getActionBar().addRightButton(getCloseEdsButton());
        } else {
            super.rebuildIdleActionBar();
        }
    }
    // PROTECTED
    // PRIVATE
}
