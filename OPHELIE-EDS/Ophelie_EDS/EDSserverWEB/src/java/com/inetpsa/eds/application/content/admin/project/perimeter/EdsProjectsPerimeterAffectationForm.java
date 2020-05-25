package com.inetpsa.eds.application.content.admin.project.perimeter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsProject;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TabSheet;

/**
 * This class provide Project partner management form
 * 
 * @author Geometric Ltd.
 */
public class EdsProjectsPerimeterAffectationForm extends A_EdsEditForm {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public EdsProjectsPerimeterAffectationForm(EdsApplicationController controller) {
        this.controller = controller;
        init();
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
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        for (EdsProjectPerimeterAffectationEditForm form : editFormsMap.values()) {
            form.commitChanges();
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        EdsProject selectedProject = (EdsProject) vLSTprojects.getValue();
        vLSTprojects.removeListener(listener);
        vLSTprojects.removeAllItems();
        editFormsMap.clear();
        readFormsMap.clear();

        EDSdao dao = EDSdao.getInstance();

        List<EdsProject> projects = dao.getAllProjects(false);

        for (EdsProject project : projects) {
            vLSTprojects.addItem(project);
            vLSTprojects.setItemCaption(project, project.getPName());
        }
        vLSTprojects.addListener(listener);

        for (EdsProjectPerimeterAffectationEditForm form : editFormsMap.values()) {
            form.discardChanges();
        }
        for (EdsProjectPerimeterAffectationReadForm form : readFormsMap.values()) {
            form.refreshViewData();
        }
        // Selected the updated project is recovered.
        if (selectedProject != null) {
            projects.retainAll(Collections.singleton(selectedProject));
            vLSTprojects.select(projects.iterator().next());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return new ArrayList<Object>(editFormsMap.keySet());
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
     * Variable to hold value of ListSelect for project
     */
    private ListSelect vLSTprojects;
    /**
     * Variable to hold value of Map of EdsProject and EdsProjectPerimeterAffectationEditForm
     */
    private HashMap<EdsProject, EdsProjectPerimeterAffectationEditForm> editFormsMap;
    /**
     * Variable to hold value of Map of EdsProject and EdsProjectPerimeterAffectationReadForm
     */
    private HashMap<EdsProject, EdsProjectPerimeterAffectationReadForm> readFormsMap;
    /**
     * Variable to hold Listener for project value changes
     */
    private ValueChangeListener listener;
    /**
     * Variable to hold value of EdsProject
     */
    private EdsProject currentProject;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize Project partner management form.
     */
    private void init() {
        this.editFormsMap = new HashMap<EdsProject, EdsProjectPerimeterAffectationEditForm>();
        this.readFormsMap = new HashMap<EdsProject, EdsProjectPerimeterAffectationReadForm>();
        TabSheet vTSmain = new TabSheet();
        vTSmain.setSizeFull();

        vTSmain.addTab(getContentLayout(), controller.getBundle().getString("Admin-proj-proj-aut"));

        addComponent(vTSmain);
    }

    /**
     * This method provide layout for Project partner management form
     * 
     * @return Layout for Project partner management form
     */
    private Layout getContentLayout() {
        final HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setSpacing(true);
        contentLayout.setMargin(true);

        this.vLSTprojects = new ListSelect(controller.getBundle().getString("Admin-proj-ss-name"));
        vLSTprojects.setRows(20);
        vLSTprojects.setNullSelectionAllowed(false);
        vLSTprojects.setImmediate(true);
        vLSTprojects.setWidth("150px");

        this.listener = new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                EdsProject selectedProject = (EdsProject) vLSTprojects.getValue();
                if (selectedProject != null) {
                    if (currentProject != null) {
                        contentLayout.removeComponent(contentLayout.getComponent(1));
                    }
                    currentProject = selectedProject;
                    contentLayout.addComponent(getProjectForm(currentProject));
                }
            }
        };

        contentLayout.addComponent(vLSTprojects);
        contentLayout.setSizeFull();

        return contentLayout;
    }

    /**
     * This method returns layout for specified Eds project
     * 
     * @param project Eds Project Details
     * @return Layout for project selected
     */
    private Layout getProjectForm(EdsProject project) {
        Layout form = null;
        if (project.isActive()) {
            form = editFormsMap.get(project);
            if (form == null) {
                form = new EdsProjectPerimeterAffectationEditForm(project, controller);
                editFormsMap.put(project, (EdsProjectPerimeterAffectationEditForm) form);
                ((EdsProjectPerimeterAffectationEditForm) form).discardChanges();
                form.setWidth("500px");
            }
        } else {
            form = readFormsMap.get(project);
            if (form == null) {
                form = new EdsProjectPerimeterAffectationReadForm(project, controller);
                readFormsMap.put(project, (EdsProjectPerimeterAffectationReadForm) form);
                ((EdsProjectPerimeterAffectationReadForm) form).refreshViewData();
                form.setWidth("500px");
            }
        }
        return form;
    }
}
