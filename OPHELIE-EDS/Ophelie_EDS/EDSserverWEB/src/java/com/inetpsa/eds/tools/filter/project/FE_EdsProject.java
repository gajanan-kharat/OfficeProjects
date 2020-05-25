package com.inetpsa.eds.tools.filter.project;

import java.text.MessageFormat;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.tools.filter.A_EdsFilter;
import com.inetpsa.eds.tools.filter.A_FilterEditor;
import com.inetpsa.eds.tools.filter.E_FilterNotReady;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;

/**
 * Filter Editor for a project.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsProject extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsProject(EdsApplicationController controller) {
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
        if (vCMBprojectValue.getValue() == null) {
            throw new E_FilterNotReady(MessageFormat.format(controller.getBundle().getString("filter-admin-error-message"), controller.getBundle()
                    .getString(FF_EdsProject.FACTORY_NAME)));
        }
        return new EdsProjectFilter((EdsProject) vCMBprojectValue.getValue(), controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of combo box for Project value
     */
    private ComboBox vCMBprojectValue;

    /**
     * initialize project filter editor
     */
    private void init() {
        this.setSpacing(true);

        EDSdao dao = EDSdao.getInstance();

        List<EdsProject> projectsList = dao.getAllProjects(false);

        this.vCMBprojectValue = new ComboBox(controller.getBundle().getString("filter-project") + " :", projectsList) {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.AbstractSelect#getItemCaption(java.lang.Object)
             */
            @Override
            public String getItemCaption(Object itemId) {
                EdsProject project = (EdsProject) itemId;
                return project.getPName();
            }
        };
        this.vCMBprojectValue.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
        this.vCMBprojectValue.setImmediate(true);
        this.vCMBprojectValue.setNullSelectionAllowed(false);

        this.addComponent(vCMBprojectValue);
    }
}
