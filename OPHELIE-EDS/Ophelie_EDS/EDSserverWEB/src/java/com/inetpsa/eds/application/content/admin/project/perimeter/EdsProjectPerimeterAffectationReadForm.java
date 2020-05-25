package com.inetpsa.eds.application.content.admin.project.perimeter;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPerimeter;
import com.inetpsa.eds.dao.model.EdsProject;
import com.vaadin.ui.Label;
import java.util.Set;

/**
 * This class provide reading component of the project partners.
 * 
 * @author Geometric Ltd.
 */
public class EdsProjectPerimeterAffectationReadForm extends A_EdsReadForm {
    // PUBLICI
    /**
     * Parameterized constructor
     * 
     * @param project Eds project Details
     * @param controller Controller of EDS application
     */
    public EdsProjectPerimeterAffectationReadForm(EdsProject project, EdsApplicationController controller) {
        super(controller);
        this.project = project;
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEds()
     */
    @Override
    public EdsEds getEds() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {
        removeAllComponents();
        if (project.getEdsPerimeters().isEmpty()) {
            addComponent(new Label(controller.getBundle().getString("Admin-proj-no-partners")));
        } else {
            addComponent(new Label(controller.getBundle().getString("Admin-proj-autorised-partners") + " :"));
            for (EdsPerimeter perimeter : (Set<EdsPerimeter>) project.getEdsPerimeters()) {
                addComponent(new Label(" - " + perimeter.getPeName()));
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of EdsProject
     */
    private EdsProject project;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize reading component of the project partners.
     */
    private void init() {
        setMargin(true);
        setSpacing(true);
        setCaption(controller.getBundle().getString("Admin-proj-partners"));
        refreshViewData();
    }
}
