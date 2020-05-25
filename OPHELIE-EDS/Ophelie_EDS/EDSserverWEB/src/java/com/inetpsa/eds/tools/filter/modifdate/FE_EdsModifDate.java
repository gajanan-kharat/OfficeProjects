package com.inetpsa.eds.tools.filter.modifdate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.timepicker.WeekTimePicker;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;

/**
 * Filter editor plug according to modification date.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsModifDate extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsModifDate(EdsApplicationController controller) {
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
        return new EdsModifDateFilter(timePicker.getSelectedStartDate(), timePicker.getSelectedEndDate(), controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of WeekTimePicker
     */
    private WeekTimePicker timePicker;

    /**
     * Initialize filter editor for modification date
     */
    private void init() {
        this.setSpacing(true);

        timePicker = new WeekTimePicker();

        this.addComponent(timePicker);
    }
}
