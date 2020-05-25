package com.inetpsa.eds.tools.filter.bttbt;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.inetpsa.eds.dao.model.EdsEds;
import com.vaadin.ui.ComboBox;
import java.util.Arrays;
import java.util.List;

/**
 * Filter editor according to the sheet type of network.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsBtTbt extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsBtTbt(EdsApplicationController controller) {
        super(controller);
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_FilterEditor#createFilter()
     */
    @Override
    public A_EdsFilter createFilter() throws E_FilterNotReady {
        return new EdsBtTbtFilter((Integer) vCMBequalsValue.getValue(), controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize filter editor for type of network
     */
    private void init() {
        this.setSpacing(true);

        List<Integer> validationLevelList = EdsEds.getAllValidationLevels();

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("filter-bt-tbt") + " :", Arrays.asList(0, 1));
        this.vCMBequalsValue.setItemCaption(0, controller.getBundle().getString("consolid-qcf-non"));
        this.vCMBequalsValue.setItemCaption(1, controller.getBundle().getString("consolid-qcf-oui"));
        this.vCMBequalsValue.select(1);
        this.vCMBequalsValue.setNullSelectionAllowed(false);
        this.vCMBequalsValue.setTextInputAllowed(false);

        this.addComponent(vCMBequalsValue);
    }
}
