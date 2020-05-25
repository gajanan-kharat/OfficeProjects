package com.inetpsa.eds.application.content.admin.project.perimeter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsPerimeter;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.tools.doublelist.DoubleList;

/**
 * This class provide Editing component of project partners.
 * 
 * @author Geometric Ltd.
 */
public class EdsProjectPerimeterAffectationEditForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param project Eds project Details
     * @param controller Controller of EDS application
     */
    public EdsProjectPerimeterAffectationEditForm(EdsProject project, EdsApplicationController controller) {
        this.project = project;
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        project.setEdsPerimeters(new HashSet(vDLSTperimeters.getAllSelections()));
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        this.perimeters.clear();

        EDSdao dao = EDSdao.getInstance();

        this.perimeters.addAll(dao.getAllPerimeters());

        this.perimeters.removeAll(project.getEdsPerimeters());

        for (EdsPerimeter perimeter : this.perimeters) {
            vDLSTperimeters.addOption(perimeter);
            vDLSTperimeters.setItemCaption(perimeter, perimeter.getPeName());
        }
        for (EdsPerimeter perimeter : (Set<EdsPerimeter>) project.getEdsPerimeters()) {
            vDLSTperimeters.addSelection(perimeter);
            vDLSTperimeters.setItemCaption(perimeter, perimeter.getPeName());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) project);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
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
     * Variable to hold DoubleList for partners
     */
    private DoubleList vDLSTperimeters;
    /**
     * Variable to hold set of EdsPerimeter
     */
    private Set<EdsPerimeter> perimeters;
    /**
     * Variable to hold value of Controller of Eds application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Editing component of project partners.
     */
    private void init() {
        setMargin(true);
        setSpacing(true);
        setCaption(controller.getBundle().getString("Admin-proj-partners"));

        this.perimeters = new HashSet<EdsPerimeter>();
        this.vDLSTperimeters = new DoubleList();
        this.vDLSTperimeters.setLeftColumnCaption(controller.getBundle().getString("Admin-proj-partners-no-ok"));
        this.vDLSTperimeters.setRightColumnCaption(controller.getBundle().getString("Admin-proj-partners-ok"));
        this.vDLSTperimeters.setImmediate(true);
        this.vDLSTperimeters.setNullSelectionAllowed(false);
        this.vDLSTperimeters.setRows(17);
        this.vDLSTperimeters.setSizeFull();

        addComponent(vDLSTperimeters);
    }
}
