package com.inetpsa.eds.tools.filter.launcherproject;

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
 * Filter editor plug in the launcher project.
 * 
 * @author Geometric Ltd.
 */
public class FE_EdsLauncherProject extends A_FilterEditor {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public FE_EdsLauncherProject(EdsApplicationController controller) {
        super(controller);
        init();
    }

    @Override
    public A_EdsFilter createFilter() throws E_FilterNotReady {
        if (vCMBequalsValue.getValue() == null) {
            throw new E_FilterNotReady(MessageFormat.format(controller.getBundle().getString("filter-admin-error-message"), controller.getBundle()
                    .getString(FF_EdsLauncherProject.FACTORY_NAME)));
        }
        return new EdsLauncherProjectFilter((EdsProject) vCMBequalsValue.getValue(), controller);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Combo box
     */
    private ComboBox vCMBequalsValue;

    /**
     * Initialize filter editor for launcher project
     */
    private void init() {
        this.setSpacing(true);

        List<EdsProject> projectList = null;
        EDSdao dao = EDSdao.getInstance();

        projectList = dao.getAllProjects(false);

        this.vCMBequalsValue = new ComboBox(controller.getBundle().getString("filter-project-launcher") + " :", projectList) {
            @Override
            public String getItemCaption(Object itemId) {
                EdsProject project = (EdsProject) itemId;
                return project.getPName();
            }
        };
        this.vCMBequalsValue.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
        this.vCMBequalsValue.setImmediate(true);
        this.vCMBequalsValue.setNullSelectionAllowed(false);

        this.addComponent(vCMBequalsValue);
    }
}
