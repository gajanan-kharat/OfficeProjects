package com.inetpsa.eds.tools.filter.supplier;

import java.text.MessageFormat;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsSupplier;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;

/**
 * Filter editor plug depending on the provider.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsSupplier extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsSupplier(EdsApplicationController controller) {
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
                    .getString(FF_EdsSupplier.FACTORY_NAME)));
        }
        return new EdsSupplierFilter((EdsSupplier) vCMBequalsValue.getValue(), controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value for combo box.
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize EDS supplier filter editor
     */
    private void init() {
        this.setSpacing(true);

        List<EdsSupplier> supplierList = null;
        EDSdao dao = EDSdao.getInstance();

        supplierList = dao.getAllSuppliers();

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("mail-content-eds-fnr") + " :", supplierList) {
            @Override
            public String getItemCaption(Object itemId) {
                EdsSupplier supplier = (EdsSupplier) itemId;
                return supplier.getSName();
            }
        };
        this.vCMBequalsValue.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
        this.vCMBequalsValue.setImmediate(true);
        this.vCMBequalsValue.setNullSelectionAllowed(false);

        this.addComponent(vCMBequalsValue);
    }
}
