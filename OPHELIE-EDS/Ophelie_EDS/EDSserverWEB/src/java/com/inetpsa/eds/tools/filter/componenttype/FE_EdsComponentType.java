package com.inetpsa.eds.tools.filter.componenttype;

import java.text.MessageFormat;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsComponentType;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;

/**
 * Filter editor plug depending on the type of component.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsComponentType extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsComponentType(EdsApplicationController controller) {

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
                    .getString(FF_EdsComponentType.FACTORY_NAME)));
        }
        return new EdsComponentTypeFilter(controller, (EdsComponentType) vCMBequalsValue.getValue());
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize filter Editor for type of component
     */
    private void init() {
        this.setSpacing(true);

        List<EdsComponentType> componentTypeList = null;
        EDSdao dao = EDSdao.getInstance();

        componentTypeList = dao.getAllComponentTypes();

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("menu-project-tab-model") + " :", componentTypeList) {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.AbstractSelect#getItemCaption(java.lang.Object)
             */
            @Override
            public String getItemCaption(Object itemId) {
                EdsComponentType ct = (EdsComponentType) itemId;
                return ct.getLocaleCtName(controller.getApplication().getLocale());
            }
        };
        this.vCMBequalsValue.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
        this.vCMBequalsValue.setImmediate(true);
        this.vCMBequalsValue.setNullSelectionAllowed(false);

        this.addComponent(vCMBequalsValue);
    }
}
