package com.inetpsa.eds.tools.filter.perimeter;

import java.text.MessageFormat;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsPerimeter;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;

/**
 * Filter editor plug depending on the partner.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsPerimeter extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsPerimeter(EdsApplicationController controller) {
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
        if (vCMBequalsValue.getValue() == null) {
            throw new E_FilterNotReady(MessageFormat.format(controller.getBundle().getString("filter-admin-error-message"), controller.getBundle()
                    .getString(FF_EdsPerimeter.FACTORY_NAME)));
        }
        return new EdsPerimeterFilter((EdsPerimeter) vCMBequalsValue.getValue(), controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize Eds parameter filter editor
     */
    private void init() {
        this.setSpacing(true);

        List<EdsPerimeter> perimeterList = null;
        EDSdao dao = EDSdao.getInstance();

        perimeterList = dao.getAllPerimeters(true);

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("Admin-user-partn") + " :", perimeterList) {
            @Override
            public String getItemCaption(Object itemId) {
                EdsPerimeter perimeter = (EdsPerimeter) itemId;
                return perimeter.getPeName();
            }
        };
        this.vCMBequalsValue.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
        this.vCMBequalsValue.setImmediate(true);
        this.vCMBequalsValue.setNullSelectionAllowed(false);

        this.addComponent(vCMBequalsValue);
    }
}
