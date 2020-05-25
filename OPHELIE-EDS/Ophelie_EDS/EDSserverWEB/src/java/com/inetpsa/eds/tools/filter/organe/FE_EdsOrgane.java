package com.inetpsa.eds.tools.filter.organe;

import java.text.MessageFormat;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;

/**
 * Filter editor plug according to Body.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsOrgane extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsOrgane(EdsApplicationController controller) {
        super(controller);
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Combo box
     */
    private ComboBox vCMBorganeValue;

    /**
     * Initialize filter editor plug according to Body.
     */
    private void init() {
        this.setSpacing(true);
        EDSdao dao = EDSdao.getInstance();

        List<EdsWording> organsFamilies = dao.getAllActiveWordingsByType(EdsWording.ORGAN_FAMILY);

        this.vCMBorganeValue = new ComboBox(controller.getBundle().getString("filter-organe-title"), organsFamilies) {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.AbstractSelect#getItemCaption(java.lang.Object)
             */
            @Override
            public String getItemCaption(Object itemId) {
                EdsWording organe = (EdsWording) itemId;
                return organe.getValueByLocale(controller.getApplication().getLocale());
            }
        };
        this.vCMBorganeValue.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);
        this.vCMBorganeValue.setImmediate(true);
        this.vCMBorganeValue.setNullSelectionAllowed(false);

        this.addComponent(vCMBorganeValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.tools.filter.A_FilterEditor#createFilter()
     */
    @Override
    public A_EdsFilter createFilter() throws E_FilterNotReady {
        if (vCMBorganeValue.getValue() == null) {
            throw new E_FilterNotReady(MessageFormat.format(controller.getBundle().getString("filter-admin-error-message"), controller.getBundle()
                    .getString(FF_EdsOrgane.FACTORY_NAME)));
        }
        return new EdsOrganeFilter((EdsWording) vCMBorganeValue.getValue(), controller);
    }
}
