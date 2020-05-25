package com.inetpsa.eds.application.content.connectivity;

import com.inetpsa.eds.application.ChsController;
import com.inetpsa.eds.application.EdsApplicationController;

/**
 * Component display standard tables sheet CHS. It contains a table and a filter component.
 * 
 * @author Joao Costa @ Alter Frame
 * @see EdsTable, EdsFilterPanel
 */
public class ConnectivityCHSListView extends DefaultCHSListView {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public ConnectivityCHSListView(EdsApplicationController controller, ChsController chsController) {
        super(controller, chsController);
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialize Standard table sheet EDS
     */
    private void init() {

    }
}
