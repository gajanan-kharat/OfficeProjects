package com.inetpsa.eds.tools.filter.number96k;

import java.text.MessageFormat;
import java.util.Set;
import java.util.TreeSet;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsNumber96k;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.inetpsa.eds.tools.filter.EdsFilterManager;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

/**
 * Filter editor plug in the number 96XXX.
 * 
 * @author Geometric Ltd.
 */
public class FE_Eds96K extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_Eds96K(EdsApplicationController controller) {
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
        if (vCMBfilterType.getValue().equals(EdsFilterManager.FILTER_EQUALS)) {
            if (vCMBequalsValue.getValue() == null) {
                throw new E_FilterNotReady(MessageFormat.format(controller.getBundle().getString("filter-admin-error-message"), controller
                        .getBundle().getString(FF_Eds96K.FACTORY_NAME)));
            }
            return new Eds96KFilter(((EdsNumber96k) vCMBequalsValue.getValue()).getNValue(), EdsFilterManager.FILTER_EQUALS, controller);
        } else {
            if (EDSTools.convertEmptyStringToNull((String) vTXTcontainsValue.getValue()) == null) {
                throw new E_FilterNotReady(MessageFormat.format(controller.getBundle().getString("filter-admin-error-message"), controller
                        .getBundle().getString(FF_Eds96K.FACTORY_NAME)));
            }
            return new Eds96KFilter((String) vTXTcontainsValue.getValue(), EdsFilterManager.FILTER_ISLIKE, controller);
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box for type of filter
     */
    private ComboBox vCMBfilterType;
    /**
     * Variable to hold value of combo box
     */
    private ComboBox vCMBequalsValue;
    /**
     * Variable to hold value of Text field
     */
    private TextField vTXTcontainsValue;

    /**
     * Initialize filter editor for a number 96XXX.
     */
    private void init() {
        this.setSpacing(true);

        Set<EdsNumber96k> n96kList = new TreeSet<EdsNumber96k>();
        EDSdao dao = EDSdao.getInstance();

        // n96kList = dao.getAll96kNumbers();
        for (EdsNumber96k number96k : dao.getAll96kNumbers()) {
            n96kList.add(number96k);
        }

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("filter-96") + " :", n96kList) {
            @Override
            public String getItemCaption(Object itemId) {
                EdsNumber96k n96k = (EdsNumber96k) itemId;
                return n96k.getNValue();
            }
        };
        this.vCMBequalsValue.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
        this.vCMBequalsValue.setImmediate(true);
        this.vCMBequalsValue.setNullSelectionAllowed(false);

        this.vTXTcontainsValue = new TextField("Numéro 96k :");
        this.vTXTcontainsValue.setVisible(false);

        this.vCMBfilterType = EdsFilterManager.buildFilterTypeComboBox(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue().equals(EdsFilterManager.FILTER_EQUALS)) {
                    vCMBequalsValue.setVisible(true);
                    vTXTcontainsValue.setVisible(false);
                } else {
                    vCMBequalsValue.setVisible(false);
                    vTXTcontainsValue.setVisible(true);
                }
            }
        });

        this.addComponent(vCMBfilterType);
        this.addComponent(vCMBequalsValue);
        this.addComponent(vTXTcontainsValue);
    }
}
