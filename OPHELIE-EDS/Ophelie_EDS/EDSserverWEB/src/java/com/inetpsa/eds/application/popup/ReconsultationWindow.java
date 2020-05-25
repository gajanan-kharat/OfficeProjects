package com.inetpsa.eds.application.popup;

import java.util.List;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;

/**
 * This class is use to re-consult the selected EDS to other projects.
 * <ul>
 * It performs the following operations:
 * <li>Create Reconsult pop-up.</li>
 * <li>Add multi-select combo-box which contains all the projects.</li>
 * <li>Add button and button listeners for validation.</li>
 * </ul>
 * 
 * @author Geometric Ltd.
 */
public class ReconsultationWindow extends A_EdsWindow {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details to be re-consult.
     * @param controller EDS application controller object.
     */
    public ReconsultationWindow(EdsEds eds, EdsApplicationController controller) {
        super(controller.getBundle().getString("eds-pop-reconsult-title"));
        this.edsSource = eds;
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
     * Variable to store EDS details object.
     */
    private EdsEds edsSource;
    /**
     * EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * List of all projects in application.
     */
    private List<EdsProject> projects;

    /**
     * This method is used to initialize this class.
     * <ul>
     * It performs the following operations:
     * <li>Create Reconsult pop-up.</li>
     * <li>Add multi-select combo-box which contains all the projects.</li>
     * <li>Add button and button listeners for validation.</li>
     * </ul>
     */
    private void init() {

        this.projects = EDSdao.getInstance().getAllProjects();

        VerticalLayout mainLayout = (VerticalLayout) getContent();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        // Ajout de la liste
        final ListSelect vLSTprojects = new ListSelect(controller.getBundle().getString("eds-pop-reconsult-select-proj"));
        vLSTprojects.setMultiSelect(false);
        vLSTprojects.setNullSelectionAllowed(false);

        for (EdsProject project : projects) {
            vLSTprojects.addItem(project);
            vLSTprojects.setItemCaption(project, project.getPName());
        }
        vLSTprojects.setSizeFull();
        mainLayout.addComponent(vLSTprojects);
        mainLayout.setComponentAlignment(vLSTprojects, Alignment.TOP_CENTER);

        // Ajout des boutons
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);

        Button okButton = new Button(controller.getBundle().getString("button-reconsult"));
        okButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (isValid()) {
                    controller.reconsult(edsSource, (EdsProject) vLSTprojects.getValue(), true);
                    showNotification(controller.getBundle().getString("eds-pop-reconsult-ok"));
                    close();
                }
            }

            private boolean isValid() {
                if (vLSTprojects.getValue() == null) {
                    showNotification(controller.getBundle().getString("eds-pop-reconsult-error"));
                    return false;
                }
                return true;

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

        mainLayout.setSizeUndefined();
        setResizable(false);
    }
}
