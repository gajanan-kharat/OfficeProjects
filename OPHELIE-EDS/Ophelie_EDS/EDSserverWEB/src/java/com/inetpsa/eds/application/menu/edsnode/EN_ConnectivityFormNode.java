package com.inetpsa.eds.application.menu.edsnode;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;

/**
 * This class represents the component representing the node "Connectivity" in menu "EDS navigation".
 * 
 * @author Joao Costa @ Alter Frame
 */
public class EN_ConnectivityFormNode extends EN_FormNode {

    private boolean edit;
    private EdsEds eds;

    public EN_ConnectivityFormNode(EdsApplicationController controller, EdsEds eds, A_EdsReadForm readForm, String editRight) {
        super(controller, readForm, null, editRight);
        this.edit = false;
        this.eds = eds;
    }

    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     * @param readForm Read form of EDS.
     * @param editForm Edit form of EDS.
     * @param editRight EDS write access.
     */
    public EN_ConnectivityFormNode(EdsApplicationController controller, EdsEds eds, A_EdsReadForm readForm, A_EdsEditForm editForm, String editRight) {
        super(controller, readForm, editForm, editRight);
        this.edit = true;
        this.eds = eds;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.EN_FormNode#rebuildIdleActionBar()
     */
    @Override
    protected void rebuildIdleActionBar() {
        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());
        getController().getActionBar().clear();
        if (edit && eds.getEdsChs().size() > 0) {
            super.rebuildIdleActionBar();
        }
    }
    
}
