package com.inetpsa.eds.tools.filter.chs.connectivity;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.A_FilterFactory;

/**
 * Factory filter sheet by Nb Embases.
 * 
 * @author Joao Costa @ Alter Frame
 */
public class FF_ChsConnectivity extends A_FilterFactory {
    // PUBLIC
    /**
     * Constant to hold value of FACTORY_NAME
     */
    public static final String FACTORY_NAME = "chs-filter-connect";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FF_ChsConnectivity(Chs chs, EdsApplicationController controller) {
        super(FACTORY_NAME, controller);
        init();
        this.chs = chs;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_FilterFactory#buildEditor()
     */
    @Override
    protected A_FilterEditor buildEditor() {
        return new FE_ChsConnectivity(controller, chs);
    }

    // PROTECTED
    // PRIVATE
    private Chs chs;

    /**
     * Initialize filter factory for name
     */
    private void init() {
    }
}
