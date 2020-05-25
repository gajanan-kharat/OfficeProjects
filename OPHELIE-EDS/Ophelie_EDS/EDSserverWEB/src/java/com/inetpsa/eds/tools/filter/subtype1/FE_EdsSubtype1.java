package com.inetpsa.eds.tools.filter.subtype1;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.ComboBox;
import java.util.List;

/**
 * Filter Editor plug according to subtype 1.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsSubtype1 extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller controller of EDS application
     */
    public FE_EdsSubtype1(EdsApplicationController controller) {
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
        return new EdsSubtype1Filter((Integer) vCMBequalsValue.getValue(), controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box.
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize Eds subtype 1 filter editor
     */
    private void init() {
        this.setSpacing(true);

        List<Integer> subtypes1 = EdsApplicationController.getAllSubtypes1();

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("generic-data-organe-type-1") + " :", subtypes1) {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.AbstractSelect#getItemCaption(java.lang.Object)
             */
            @Override
            public String getItemCaption(Object itemId) {
                Integer subtype = (Integer) itemId;
                return EdsApplicationController.getSubtype1Text(subtype);
            }
        };
        this.vCMBequalsValue.select(subtypes1.get(0));
        this.vCMBequalsValue.setNullSelectionAllowed(false);
        this.vCMBequalsValue.setTextInputAllowed(false);

        this.addComponent(vCMBequalsValue);
    }
}
