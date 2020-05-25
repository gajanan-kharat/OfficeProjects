package com.inetpsa.eds.tools.filter.subtype2;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.ComboBox;
import java.util.List;

/**
 * Filter Editor plug according subtype 2.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsSubtype2 extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsSubtype2(EdsApplicationController controller) {
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
        return new EdsSubtype2Filter((Integer) vCMBequalsValue.getValue(), controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize Eds subtype 2 filter editor
     */

    private void init() {
        this.setSpacing(true);

        List<Integer> subtypes2 = EdsApplicationController.getAllSubtypes2();

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("generic-data-organe-type-2") + " :", subtypes2) {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.AbstractSelect#getItemCaption(java.lang.Object)
             */
            @Override
            public String getItemCaption(Object itemId) {
                Integer subtype = (Integer) itemId;
                return EdsApplicationController.getSubtype2Text(subtype);
            }
        };
        this.vCMBequalsValue.select(subtypes2.get(0));
        this.vCMBequalsValue.setNullSelectionAllowed(false);
        this.vCMBequalsValue.setTextInputAllowed(false);

        this.addComponent(vCMBequalsValue);
    }
}
