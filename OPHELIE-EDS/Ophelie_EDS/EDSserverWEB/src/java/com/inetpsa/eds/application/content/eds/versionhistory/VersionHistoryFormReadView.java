package com.inetpsa.eds.application.content.eds.versionhistory;

import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;

/**
 * This class is used to display the EDS details of a particular version in read only mode.
 * 
 * @author Geometric Ltd..
 */
public class VersionHistoryFormReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     * @param eds EDS details.
     */
    public VersionHistoryFormReadView(EdsApplicationController controller, EdsEds eds) {
        super(controller);
        this.controller = controller;
        this.eds = eds;

        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "hist-title";
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
        return VersionHistoryFormBuilder.ID;
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
        EDSdao dao = EDSdao.getInstance();

        List<EdsEds> edsVersionsList = dao.getAllEdsVersionsByRef(getEdsRef());

        versionTable.setEdsList(edsVersionsList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {

        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());

    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store EDS details.
     */
    private EdsEds eds;
    /**
     * Table to store EDS versions.
     */
    private EdsVersionTable versionTable;

    /**
     * Initialization method.
     */
    private void init() {
        setSpacing(true);

        this.versionTable = new EdsVersionTable(controller);
        this.versionTable.setWidth("100%");

        addComponent(versionTable);
    }
}
