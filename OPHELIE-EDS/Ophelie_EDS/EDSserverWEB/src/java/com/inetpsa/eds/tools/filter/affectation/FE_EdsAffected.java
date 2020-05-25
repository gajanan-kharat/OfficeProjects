package com.inetpsa.eds.tools.filter.affectation;

import java.text.MessageFormat;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;

/**
 * Filter Editor according to assignment
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsAffected extends A_FilterEditor {

    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsAffected(EdsApplicationController controller) {
        super(controller);
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Combo box
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize Eds affected filter editor
     */
    private void init() {
        this.setSpacing(true);

        List<EdsUser> userList = null;
        EDSdao dao = EDSdao.getInstance();

        userList = dao.getAllUsers();

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("eds-affected-user") + " :", userList) {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.AbstractSelect#getItemCaption(java.lang.Object)
             */
            @Override
            public String getItemCaption(Object itemId) {
                EdsUser user = (EdsUser) itemId;
                return user.toFullIdentity();
            }
        };
        this.vCMBequalsValue.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);
        this.vCMBequalsValue.setImmediate(true);
        this.vCMBequalsValue.setNullSelectionAllowed(false);

        this.addComponent(vCMBequalsValue);
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
                    .getString(FF_EdsAffected.FACTORY_NAME)));
        }
        return new EdsAffectationFilter((EdsUser) vCMBequalsValue.getValue(), controller);
    }
}
