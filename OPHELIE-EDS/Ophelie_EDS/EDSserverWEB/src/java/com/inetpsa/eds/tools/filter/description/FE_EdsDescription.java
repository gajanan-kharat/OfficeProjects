package com.inetpsa.eds.tools.filter.description;

import java.text.MessageFormat;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.inetpsa.eds.tools.filter.EdsFilterManager;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

/**
 * Editor filter sheet by description.
 */
public class FE_EdsDescription extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsDescription(EdsApplicationController controller) {
        super(controller);
        init();
    }

    @Override
    public A_EdsFilter createFilter() throws E_FilterNotReady {
        if (EDSTools.convertEmptyStringToNull((String) vTXTvalue.getValue()) == null) {
            throw new E_FilterNotReady(MessageFormat.format(controller.getBundle().getString("filter-admin-error-message"), controller.getBundle()
                    .getString(FF_EdsDescription.FACTORY_NAME)));
        }

        if (vCMBfilterType.getValue().equals(EdsFilterManager.FILTER_EQUALS)) {
            return new EdsDescriptionFilter((String) vTXTvalue.getValue(), EdsFilterManager.FILTER_EQUALS, controller);
        } else {
            return new EdsDescriptionFilter((String) vTXTvalue.getValue(), EdsFilterManager.FILTER_ISLIKE, controller);
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box for filter type
     */
    private ComboBox vCMBfilterType;
    /**
     * Variable to hold value of Text field
     */
    private TextField vTXTvalue;

    /**
     * Initialize Filter editor for name
     */
    private void init() {
        this.setSpacing(true);

        this.vTXTvalue = new TextField(controller.getBundle().getString("filter-description") + " :");

        this.vCMBfilterType = EdsFilterManager.buildFilterTypeComboBox(null);

        this.addComponent(vCMBfilterType);
        this.addComponent(vTXTvalue);
    }
}
