package com.inetpsa.eds.application.content.eds.cse;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.model.EdsCseFormData;
import com.inetpsa.eds.dao.model.EdsEds;

/**
 * This class provide component for reading CSE form.
 * 
 * @author Geometric Ltd.
 */
public class CSEFormReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized Constructor
     * 
     * @param formData object of EdsCseFormData
     * @param controller Controller of EDS application
     * @param eds Object of EdsEds
     */
    public CSEFormReadView(EdsCseFormData formData, EdsApplicationController controller, EdsEds eds) {
        super(controller);
        this.formData = formData;
        this.controller = controller;
        this.eds = eds;
        init();
    }

    /**
     * This method returns EdsCseFormData
     * 
     * @return EdsCseFormData
     */
    public EdsCseFormData getFormData() {
        return formData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "menu-app-CSE";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "CSE-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return CSEFormBuilder.ID;
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
        cseTable.setCseList(formData.getEdsCseLines());
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

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsCseFormData
     */
    private EdsCseFormData formData;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of CseTable
     */
    private CseTable cseTable;

    /**
     * initialize CSE form read view
     */
    private void init() {
        this.setSpacing(true);

        this.cseTable = new CseTable(controller);
        this.cseTable.setSizeFull();

        addComponent(this.cseTable);
    }
}
