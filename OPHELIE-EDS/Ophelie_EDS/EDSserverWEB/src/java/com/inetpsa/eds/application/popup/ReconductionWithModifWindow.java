package com.inetpsa.eds.application.popup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsProjectEds;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;

/**
 * This class is use to carry over the selected EDS to other projects with modifications.
 * <ul>
 * It performs the following operations:
 * <li>Create Re-conduction with modification pop-up.</li>
 * <li>Add multi-select combo-box which contains all the projects.</li>
 * <li>Add button and button listeners for validation.</li>
 * </ul>
 * 
 * @author Geometric Ltd.
 */
public class ReconductionWithModifWindow extends A_EdsWindow {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param readForm Current EDS read form.
     * @param editForm Current EDS edit form.
     * @param controller EDS application controller object.
     */
    public ReconductionWithModifWindow(A_EdsReadForm readForm, A_EdsEditForm editForm, EdsApplicationController controller) {
        super(controller.getBundle().getString("eds-pop-recond-modif-title"));
        this.edsSource = readForm.getEds();
        this.readForm = readForm;
        this.editForm = editForm;
        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.Window#close()
     */
    @Override
    protected void close() {
        EDSdao.getInstance().freeEdsAccess(controller.getAuthenticatedUser(), edsSource.getEdsId());
        super.close();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store current EDS details object.
     */
    private EdsEds edsSource;
    /**
     * Variable to store current EDS read form.
     */
    private A_EdsReadForm readForm;
    /**
     * Variable to store current EDS edit form.
     */
    private A_EdsEditForm editForm;
    /**
     * List of all projects in application.
     */
    private List<EdsProject> projects;
    /**
     * EDS application controller object.
     */
    private EdsApplicationController controller;

    /**
     * This method is used to initialize this class.
     * <ul>
     * It performs the following operations:
     * <li>Create Re-conduction with modification pop-up.</li>
     * <li>Add multi-select combo-box which contains all the projects.</li>
     * <li>Add button and button listeners for validation.</li>
     * </ul>
     */
    private void init() {

        this.projects = EDSdao.getInstance().getAllProjects();

        projects.remove(edsSource.getEdsProject());
        for (Object edsProjectEds : edsSource.getEdsProjectEdses()) {
            projects.remove(((EdsProjectEds) edsProjectEds).getEdsProject());
        }

        VerticalLayout mainLayout = (VerticalLayout) getContent();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        if (!projects.isEmpty()) {
            // Added list
            final ListSelect vLSTprojects = new ListSelect(controller.getBundle().getString("eds-pop-recond-modif-select-proj"));
            vLSTprojects.setMultiSelect(true);

            for (EdsProject project : projects) {
                vLSTprojects.addItem(project);
                vLSTprojects.setItemCaption(project, project.getPName());
            }
            vLSTprojects.setSizeFull();
            mainLayout.addComponent(vLSTprojects);
            mainLayout.setComponentAlignment(vLSTprojects, Alignment.TOP_CENTER);

            // Adding buttons
            HorizontalLayout buttons = new HorizontalLayout();
            buttons.setSpacing(true);

            Button okButton = new Button(controller.getBundle().getString("current-conso-list-alim-recond-button"));
            okButton.addListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    controller.reconduct(edsSource, new ArrayList<EdsProject>((Set) vLSTprojects.getValue()), true);
                    showNotification(controller.getBundle().getString("eds-pop-recond-modif-ok"));

                    close();
                }
            });

            buttons.addComponent(okButton);

            Button cancelButton = new Button(controller.getBundle().getString("button-cancel"));
            cancelButton.addListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    close();
                }
            });
            buttons.addComponent(cancelButton);

            mainLayout.addComponent(buttons);
            mainLayout.setComponentAlignment(buttons, Alignment.MIDDLE_RIGHT);

        } else {
            Label warning = new Label(controller.getBundle().getString("eds-pop-recond-modif-error"));
            mainLayout.addComponent(warning);
        }

        mainLayout.setSizeUndefined();
        setResizable(false);
    }
}
