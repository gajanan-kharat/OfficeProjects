package com.inetpsa.eds.tools.filter.validationlvl;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.inetpsa.eds.dao.model.EdsEds;
import com.vaadin.ui.ComboBox;
import java.util.List;

/**
 * Filter editor plug according to the level of EDS validation.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsValidationLevel extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application.
     */
    public FE_EdsValidationLevel(EdsApplicationController controller) {
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
        return new EdsValidationLevelFilter((Integer) vCMBequalsValue.getValue(), controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold combo box
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize EDS validation level filter editor
     */
    private void init() {
        this.setSpacing(true);

        List<Integer> validationLevelList = EdsEds.getAllValidationLevels();

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("filter-lvl-title"), validationLevelList) {
            @Override
            public String getItemCaption(Object itemId) {
                Integer validationLevel = (Integer) itemId;
                return controller.getBundle().getString(EdsEds.getValidationLevelText(validationLevel));
            }
        };
        this.vCMBequalsValue.select(validationLevelList.get(0));
        this.vCMBequalsValue.setNullSelectionAllowed(false);
        this.vCMBequalsValue.setTextInputAllowed(false);

        this.addComponent(vCMBequalsValue);
    }
}
