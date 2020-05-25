package com.inetpsa.eds.tools.filter.manager;

import java.text.MessageFormat;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;

/**
 * Filter editor plug in the PSA responsible
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsManager extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsManager(EdsApplicationController controller) {
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
                    .getString(FF_EdsManager.FACTORY_NAME)));
        }
        return new EdsManagerFilter((EdsUser) vCMBequalsValue.getValue(), controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize filter editor for PSA responsible
     */
    private void init() {
        this.setSpacing(true);

        List<EdsUser> userList = null;
        EDSdao dao = EDSdao.getInstance();

        userList = dao.getAllUsers();

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("generic-data-represent-responsible") + " :", userList) {
            @Override
            public String getItemCaption(Object itemId) {
                EdsUser user = (EdsUser) itemId;
                return user.toFullIdentity();
            }
        };
        this.vCMBequalsValue.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
        this.vCMBequalsValue.setImmediate(true);
        this.vCMBequalsValue.setNullSelectionAllowed(false);

        this.addComponent(vCMBequalsValue);
    }
}
