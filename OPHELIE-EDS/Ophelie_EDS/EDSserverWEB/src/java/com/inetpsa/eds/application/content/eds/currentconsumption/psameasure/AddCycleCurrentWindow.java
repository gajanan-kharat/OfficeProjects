package com.inetpsa.eds.application.content.eds.currentconsumption.psameasure;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsProject;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * This class add cycle consumptions window
 * 
 * @author Geometric Ltd.
 */
public class AddCycleCurrentWindow extends A_EdsWindow {
    // PUBLIC
    /**
     * Parameterized Constructor
     * 
     * @param view Object of PsaMeasureSupplyEditView
     * @param projects List of EdsProject
     * @param relatedProjects List of EdsProject
     * @param controller Controller of EDS application
     */
    public AddCycleCurrentWindow(PsaMeasureSupplyEditView view, List<EdsProject> projects, List<EdsProject> relatedProjects,
            EdsApplicationController controller) {
        super(controller.getBundle().getString("eds-add-nominal-curent"));
        this.view = view;
        this.projects = projects;
        this.relatedProjects = relatedProjects;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of PsaMeasureSupplyEditView
     */
    private PsaMeasureSupplyEditView view;

    /**
     * Variable to hold list of EdsProject
     */
    private List<EdsProject> projects;
    /**
     * Variable to hold list of EdsProject
     */
    private List<EdsProject> relatedProjects;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize cycle consumptions windows
     */
    private void init() {
        VerticalLayout mainLayout = (VerticalLayout) getContent();
        mainLayout.setMargin(true);

        GridLayout contentLayout = new GridLayout(2, 3);
        contentLayout.setSpacing(true);

        // Field name
        final TextField vTFstringValue = new TextField();
        vTFstringValue.setInputPrompt(controller.getBundle().getString("eds-tab-name"));
        vTFstringValue.setRequired(true);
        vTFstringValue.setRequiredError(controller.getBundle().getString("eds-tab-name-error"));
        contentLayout.addComponent(new Label(controller.getBundle().getString("eds-tab-name")));

        contentLayout.addComponent(vTFstringValue, 1, 0);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);

        // Project selection
        final ListSelect vLSTprojects = new ListSelect(controller.getBundle().getString("eds-select-associed-project"));
        vLSTprojects.setNullSelectionAllowed(false);
        for (EdsProject project : projects) {
            vLSTprojects.addItem(project);
            vLSTprojects.setItemCaption(project, project.getPName());
        }
        if (!relatedProjects.isEmpty()) {
            vLSTprojects.addItem(relatedProjects);
            vLSTprojects.setItemCaption(relatedProjects, StringUtils.join(relatedProjects, "/"));
        }
        vLSTprojects.setSizeFull();
        contentLayout.addComponent(vLSTprojects, 0, 1, 1, 1);

        // Buttons
        Button okButton = new Button(controller.getBundle().getString("Admin-proj-validate-button"));
        okButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (vTFstringValue.isValid() && null != vLSTprojects.getValue()) {
                    if (vLSTprojects.getValue() instanceof List) {
                        view.addNominalCurrentTab((String) vTFstringValue.getValue(), (List<EdsProject>) vLSTprojects.getValue());
                    } else {
                        view.addNominalCurrentTab((String) vTFstringValue.getValue(), (EdsProject) vLSTprojects.getValue());
                    }
                    close();
                } else {
                    showNotification(controller.getBundle().getString("eds-fields-error"));
                }
            }
        });
        buttonsLayout.addComponent(okButton);

        Button cancelButton = new Button(controller.getBundle().getString("button-cancel"));
        cancelButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });
        buttonsLayout.addComponent(cancelButton);

        contentLayout.addComponent(buttonsLayout, 1, 2);
        contentLayout.setComponentAlignment(okButton, Alignment.TOP_RIGHT);

        mainLayout.addComponent(contentLayout);
        mainLayout.setExpandRatio(contentLayout, 1);
        mainLayout.setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setSizeUndefined();

        setResizable(false);
    }
}
