package com.inetpsa.eds.tools.filter.ref;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.EdsFilterManager;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.inetpsa.eds.tools.EDSTools;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import java.text.MessageFormat;

/**
 * Filter editor plug according to the reference.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsRef extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsRef(EdsApplicationController controller) {
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
        if (EDSTools.convertEmptyStringToNull((String) vTXTvalue.getValue()) == null) {
            throw new E_FilterNotReady(MessageFormat.format(controller.getBundle().getString("filter-admin-error-message"), controller.getBundle()
                    .getString(FF_EdsRef.FACTORY_NAME)));
        }

        if (vCMBfilterType.getValue().equals(EdsFilterManager.FILTER_EQUALS)) {
            return new EdsRefFilter((String) vTXTvalue.getValue(), EdsFilterManager.FILTER_EQUALS, controller);
        } else {
            return new EdsRefFilter((String) vTXTvalue.getValue(), EdsFilterManager.FILTER_ISLIKE, controller);
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of comb box for filter type
     */
    private ComboBox vCMBfilterType;
    /**
     * Variable to hold value of Text field
     */
    private TextField vTXTvalue;

    /**
     * Initialize filter editor of Eds reference
     */
    private void init() {
        this.setSpacing(true);

        this.vTXTvalue = new TextField(controller.getBundle().getString("filter-ref-title") + " :");

        this.vCMBfilterType = EdsFilterManager.buildFilterTypeComboBox(null);

        this.addComponent(vCMBfilterType);
        this.addComponent(vTXTvalue);
    }
}
