package com.inetpsa.eds.tools.filter.chs.nbembases;

import java.text.MessageFormat;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.inetpsa.eds.tools.filter.EdsFilterManager;
import com.vaadin.ui.TextField;

/**
 * Editor filter sheet by Nb Embases.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class FE_ChsNbEmbases extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_ChsNbEmbases(EdsApplicationController controller) {
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
                    .getString(FF_ChsNbEmbases.FACTORY_NAME)));
        }

        return new ChsNbEmbasesFilter((String) vTXTvalue.getValue(), EdsFilterManager.FILTER_EQUALS, controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Text field
     */
    private TextField vTXTvalue;

    /**
     * Initialize Filter editor for name
     */
    private void init() {
        this.setSpacing(true);

        this.vTXTvalue = new TextField(controller.getBundle().getString("chs-filter-embnum") + " :");

        this.addComponent(vTXTvalue);
    }
}
