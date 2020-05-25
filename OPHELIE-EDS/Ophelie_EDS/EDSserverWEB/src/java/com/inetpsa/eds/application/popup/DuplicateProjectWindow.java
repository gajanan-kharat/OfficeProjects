package com.inetpsa.eds.application.popup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.inetpsa.eds.application.A_EdsWindow;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

/**
 * This class is used to create the pop-up of Duplicate project window.
 * <ul>
 * It has the following pop-ups:
 * <li>Project selection pop-up</li>
 * <li>Carry over the EDS without modifications pop-up</li>
 * <li>Carry over the EDS with modifications pop-up</li>
 * <li>Sheets to re-consult pop-up</li>
 * <li>Summary pop-up</li>
 * </ul>
 * 
 * @author Geometric Ltd.
 */
public class DuplicateProjectWindow extends A_EdsWindow {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     */
    public DuplicateProjectWindow(EdsApplicationController controller) {
        super(controller.getBundle().getString("eds-popup-duplication-title"));
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store EDS application controller.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store source project details.
     */
    private EdsProject srcProject;
    /**
     * Variable to store destination project details.
     */
    private EdsProject dstProject;
    /**
     * Variable to store button layout structure.
     */
    private HorizontalLayout buttonsLayout;
    /**
     * Next button variable.
     */
    private Button vBTnext;
    /**
     * Previous button variable.
     */
    private Button vBTprev;
    /**
     * Cancel button variable.
     */
    private Button vBTcancel;
    /**
     * List of EDS to be re-conduct without modification.
     */
    private ArrayList<EdsEds> edsesToReconductWithoutModif;
    /**
     * List of EDS to be re-conduct with modification.
     */
    private ArrayList<EdsEds> edsesToReconductWithModif;
    /**
     * List of EDS to be re-consult.
     */
    private ArrayList<EdsEds> edsesToReconsult;
    /**
     * List of source project EDS.
     */
    private ArrayList<EdsEds> srcProjectEdses;

    /**
     * Initialize DuplicateProjectWindow
     */
    private void init() {
        this.vBTprev = null;
        this.vBTnext = null;
        this.vBTcancel = null;
        this.buttonsLayout = null;
        this.edsesToReconductWithoutModif = new ArrayList<EdsEds>();
        this.edsesToReconductWithModif = new ArrayList<EdsEds>();
        this.edsesToReconsult = new ArrayList<EdsEds>();
        this.srcProjectEdses = new ArrayList<EdsEds>();
        getContent().addComponent(getProjectSelectionLayout());
        getContent().setSizeUndefined();
    }

    /**
     * This method is used to create the layout for project selection.
     * <ul>
     * The method performs the following functions:
     * <li>Retrieve all projects from database.</li>
     * <li>Create combo box for Origin/Source project.</li>
     * <li>Create combo box for Destination project.</li>
     * <li>Populate both the combo-box with project names.</li>
     * <li>Add components to the pop-up component.</li>
     * <li>Add listeners.</li>
     * </ul>
     * 
     * @return The project selection pop-up component.
     */
    private VerticalLayout getProjectSelectionLayout() {
        VerticalLayout projectSelectionLayout = new VerticalLayout();
        projectSelectionLayout.setMargin(true);
        projectSelectionLayout.setSpacing(true);

        // Retrieve all projects from database.

        List<EdsProject> projects = EDSdao.getInstance().getAllProjects(true);

        // Create combo box for Origin/Source project.
        ComboBox vCMBsrcProjectValue = new ComboBox(controller.getBundle().getString("eds-popup-duplication-project-source"));
        // Create combo box for Destination project.
        ComboBox vCMBdstProjectValue = new ComboBox(controller.getBundle().getString("eds-popup-duplication-project-dist"));

        // Populate both the combo-box with project names.
        for (EdsProject project : projects) {
            vCMBsrcProjectValue.addItem(project);
            vCMBsrcProjectValue.setItemCaption(project, project.getPName());
            vCMBdstProjectValue.addItem(project);
            vCMBdstProjectValue.setItemCaption(project, project.getPName());
        }

        vCMBsrcProjectValue.setValue(srcProject);
        vCMBdstProjectValue.setValue(dstProject);

        // Add components to the pop-up component.
        projectSelectionLayout.addComponent(vCMBsrcProjectValue);
        projectSelectionLayout.addComponent(vCMBdstProjectValue);
        projectSelectionLayout.addComponent(getButtonsLayout());
        projectSelectionLayout.setComponentAlignment(getButtonsLayout(), Alignment.TOP_RIGHT);

        // Add listeners.
        initProjectSelectionButtonsListeners(vCMBsrcProjectValue, vCMBdstProjectValue);

        return projectSelectionLayout;
    }

    /**
     * This method is used to create pop-up component for EDS selection. The selected EDS are carried over to the destination project without
     * modification.
     * <ul>
     * The method performs the following functions:
     * <li>Create twin column component.</li>
     * <li>Populate the left component with EDS details.</li>
     * <li>Add components to the pop-up component.</li>
     * <li>Add listeners.</li>
     * </ul>
     * 
     * @return The EDS selection pop-up component.
     */
    private VerticalLayout getReconductionWithoutModifLayout() {
        VerticalLayout reconductionWithoutModifLayout = new VerticalLayout();
        reconductionWithoutModifLayout.setMargin(true);
        reconductionWithoutModifLayout.setSpacing(true);

        // Create twin column component.
        TwinColSelect vTCSreconductionSelection = new TwinColSelect(controller.getBundle().getString("eds-popup-duplication-project-twin1"));
        vTCSreconductionSelection.setColumns(20);
        vTCSreconductionSelection.setMultiSelect(true);

        // Populate the left component with EDS details.
        for (EdsEds eds : srcProjectEdses) {
            vTCSreconductionSelection.addItem(eds);
            vTCSreconductionSelection.setItemCaption(eds, eds.getEdsName());
        }

        // Add components to the pop-up component.
        vTCSreconductionSelection.setValue(edsesToReconductWithoutModif);
        reconductionWithoutModifLayout.addComponent(vTCSreconductionSelection);
        reconductionWithoutModifLayout.addComponent(getButtonsLayout());
        reconductionWithoutModifLayout.setComponentAlignment(getButtonsLayout(), Alignment.TOP_RIGHT);

        // Add listeners.
        initEdsToReconductWithoutModifSelectionButtonListeners(vTCSreconductionSelection);

        return reconductionWithoutModifLayout;
    }

    /**
     * This method is used to create pop-up component for EDS selection. The selected EDS are carried over to the destination project with
     * modification.
     * <ul>
     * The method performs the following functions:
     * <li>Create twin column component.</li>
     * <li>Populate the left component with EDS details.</li>
     * <li>Add components to the pop-up component.</li>
     * <li>Add listeners.</li>
     * </ul>
     * 
     * @return The EDS selection pop-up component.
     */
    private VerticalLayout getReconductionWithModifLayout() {
        VerticalLayout reconductionWithModif = new VerticalLayout();
        reconductionWithModif.setMargin(true);
        reconductionWithModif.setSpacing(true);

        // Create twin column component.
        TwinColSelect vTCSreconductionSelection = new TwinColSelect(controller.getBundle().getString("eds-popup-duplication-project-twin2"));
        vTCSreconductionSelection.setColumns(20);
        vTCSreconductionSelection.setMultiSelect(true);

        List<EdsEds> availableEdses = new ArrayList<EdsEds>(srcProjectEdses);
        availableEdses.removeAll(edsesToReconductWithoutModif);

        // Populate the left component with EDS details.
        for (EdsEds eds : availableEdses) {
            vTCSreconductionSelection.addItem(eds);
            vTCSreconductionSelection.setItemCaption(eds, eds.getEdsName());
        }

        // Add components to the pop-up component.
        vTCSreconductionSelection.setValue(edsesToReconductWithModif);
        reconductionWithModif.addComponent(vTCSreconductionSelection);
        reconductionWithModif.addComponent(getButtonsLayout());
        reconductionWithModif.setComponentAlignment(getButtonsLayout(), Alignment.TOP_RIGHT);

        // Add listeners.
        initEdsToReconductWithModifSelectionButtonListeners(vTCSreconductionSelection);

        return reconductionWithModif;
    }

    /**
     * This method is used to create pop-up component for EDS selection. The selected EDS are re-consulted to the destination project.
     * <ul>
     * The method performs the following functions:
     * <li>Create twin column component.</li>
     * <li>Populate the left component with EDS details.</li>
     * <li>Add components to the pop-up component.</li>
     * <li>Add listeners.</li>
     * </ul>
     * 
     * @return The EDS selection pop-up component.
     */
    private VerticalLayout getReconsultationLayout() {
        VerticalLayout reconsultationLayout = new VerticalLayout();
        reconsultationLayout.setMargin(true);
        reconsultationLayout.setSpacing(true);

        // Create twin column component.
        TwinColSelect vTCSreconsultationSelection = new TwinColSelect(controller.getBundle().getString("eds-popup-duplication-project-twin3"));
        vTCSreconsultationSelection.setColumns(20);
        vTCSreconsultationSelection.setMultiSelect(true);

        List<EdsEds> availableEdses = new ArrayList<EdsEds>(srcProjectEdses);
        availableEdses.removeAll(edsesToReconductWithoutModif);
        availableEdses.removeAll(edsesToReconductWithModif);

        // Populate the left component with EDS details.
        for (EdsEds eds : availableEdses) {
            vTCSreconsultationSelection.addItem(eds);
            vTCSreconsultationSelection.setItemCaption(eds, eds.getEdsName());
        }

        // Add components to the pop-up component.
        vTCSreconsultationSelection.setValue(edsesToReconsult);
        reconsultationLayout.addComponent(vTCSreconsultationSelection);
        reconsultationLayout.addComponent(getButtonsLayout());
        reconsultationLayout.setComponentAlignment(getButtonsLayout(), Alignment.TOP_RIGHT);

        // Add listeners.
        initEdsToReconsultSelectionButtonListeners(vTCSreconsultationSelection);

        return reconsultationLayout;
    }

    /**
     * This method is used to create pop-up component for displaying the summary of all the selections made. It uses accordion component to display
     * the summary.
     * 
     * @return The selection summary component.
     */
    private VerticalLayout getSummaryLayout() {
        VerticalLayout summaryLayout = new VerticalLayout();
        summaryLayout.setSpacing(true);
        summaryLayout.setMargin(true);
        summaryLayout.addComponent(new Label(controller.getBundle().getString("eds-popup-duplication-project-recap")));

        // Summarized in an accordion;
        Accordion vTABcontent = new Accordion();
        vTABcontent.setSizeFull();
        vTABcontent.addTab(getListRepresentation(edsesToReconductWithoutModif), controller.getBundle()
                .getString("eds-popup-duplication-project-tab1"));
        vTABcontent.addTab(getListRepresentation(edsesToReconductWithModif), controller.getBundle().getString("eds-popup-duplication-project-tab2"));
        vTABcontent.addTab(getListRepresentation(edsesToReconsult), controller.getBundle().getString("eds-popup-duplication-project-tab3"));

        summaryLayout.addComponent(vTABcontent);
        summaryLayout.addComponent(getButtonsLayout());
        summaryLayout.setComponentAlignment(getButtonsLayout(), Alignment.TOP_RIGHT);

        initSummaryButtonListeners();

        return summaryLayout;
    }

    /**
     * This method is used to return the button layout. Horizontal layout is being used.
     * 
     * @return Layout of the buttons.
     */
    private Layout getButtonsLayout() {
        if (buttonsLayout == null) {
            buttonsLayout = new HorizontalLayout();

            vBTprev = new Button(controller.getBundle().getString("eds-popup-duplication-project-previous"));
            vBTnext = new Button(controller.getBundle().getString("eds-popup-duplication-project-next"));
            vBTcancel = new Button(controller.getBundle().getString("button-cancel"));
            vBTcancel.addListener(new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    close();
                }
            });
            buttonsLayout.setSpacing(true);
            buttonsLayout.setMargin(true);

            HorizontalLayout prevNextLayout = new HorizontalLayout();
            prevNextLayout.addComponent(vBTprev);
            prevNextLayout.addComponent(vBTnext);

            buttonsLayout.addComponent(prevNextLayout);
            buttonsLayout.addComponent(vBTcancel);
        }
        return buttonsLayout;
    }

    /**
     * This method is used to remove all the button listeners.
     */
    private void removeButtonsListeners() {
        if (!vBTprev.getListeners(ClickEvent.class).isEmpty()) {
            vBTprev.removeListener((Button.ClickListener) vBTprev.getListeners(ClickEvent.class).iterator().next());
        }
        if (!vBTnext.getListeners(ClickEvent.class).isEmpty()) {
            vBTnext.removeListener((Button.ClickListener) vBTnext.getListeners(ClickEvent.class).iterator().next());
        }
    }

    /**
     * This method is used to add listeners to next button. The listener does validation of both the projects. It also fetches the EDS data related to
     * the source project.
     * 
     * @param vCMBsrcProjectValue Source project combo-box.
     * @param vCMBdstProjectValue Destination project combo-box.
     */
    private void initProjectSelectionButtonsListeners(final ComboBox vCMBsrcProjectValue, final ComboBox vCMBdstProjectValue) {
        removeButtonsListeners();
        vBTprev.setEnabled(false);
        vBTnext.setEnabled(true);
        vBTnext.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (vCMBdstProjectValue.getValue() == null || vCMBsrcProjectValue.getValue() == null) {
                    showError(controller.getBundle().getString("eds-popup-duplication-project-source-error"));
                } else if (vCMBdstProjectValue.getValue() == vCMBsrcProjectValue.getValue()) {
                    showError(controller.getBundle().getString("eds-popup-duplication-project-dist-error"));
                } else {
                    if (!vCMBsrcProjectValue.getValue().equals(srcProject) || !vCMBdstProjectValue.getValue().equals(dstProject)) {
                        srcProject = (EdsProject) vCMBsrcProjectValue.getValue();
                        dstProject = (EdsProject) vCMBdstProjectValue.getValue();
                        srcProjectEdses.clear();
                        srcProjectEdses.addAll(EDSdao.getInstance().getAllEDSByProject(srcProject));
                        srcProjectEdses.removeAll(EDSdao.getInstance().getAllEDSByProject(dstProject));

                        edsesToReconductWithoutModif.retainAll(srcProjectEdses);
                        edsesToReconductWithModif.retainAll(srcProjectEdses);
                        edsesToReconsult.retainAll(srcProjectEdses);
                    }
                    getContent().removeAllComponents();
                    getContent().addComponent(getReconductionWithoutModifLayout());
                    getContent().setSizeUndefined();
                    center();
                }
            }
        });
    }

    /**
     * This method is used to add listener to the next and previous button on the ReconductWithoutModifSelection pop-up.
     * 
     * @param vTCSreconductionSelection Twin column selection object.
     */
    private void initEdsToReconductWithoutModifSelectionButtonListeners(final TwinColSelect vTCSreconductionSelection) {
        removeButtonsListeners();
        vBTprev.setEnabled(true);
        vBTnext.setEnabled(true);

        vBTprev.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                edsesToReconductWithoutModif.clear();
                edsesToReconductWithoutModif.addAll((Collection<EdsEds>) vTCSreconductionSelection.getValue());

                getContent().removeAllComponents();
                getContent().addComponent(getProjectSelectionLayout());
                getContent().setSizeUndefined();
                center();
            }
        });

        vBTnext.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                edsesToReconductWithoutModif.clear();
                edsesToReconductWithoutModif.addAll((Collection<EdsEds>) vTCSreconductionSelection.getValue());

                edsesToReconductWithModif.removeAll(edsesToReconductWithoutModif);
                edsesToReconsult.removeAll(edsesToReconductWithoutModif);

                getContent().removeAllComponents();
                getContent().addComponent(getReconductionWithModifLayout());
                getContent().setSizeUndefined();
                center();
            }
        });

    }

    /**
     * This method is used to add listener to the next and previous button on the ReconductWithModifSelection pop-up.
     * 
     * @param vTCSreconductionSelection Twin column selection object.
     */
    private void initEdsToReconductWithModifSelectionButtonListeners(final TwinColSelect vTCSreconductionSelection) {
        removeButtonsListeners();
        vBTprev.setEnabled(true);
        vBTnext.setEnabled(true);

        vBTprev.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                edsesToReconductWithModif.clear();
                edsesToReconductWithModif.addAll((Collection<EdsEds>) vTCSreconductionSelection.getValue());
                getContent().removeAllComponents();
                getContent().addComponent(getReconductionWithoutModifLayout());
                getContent().setSizeUndefined();
                center();
            }
        });

        vBTnext.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                edsesToReconductWithModif.clear();
                edsesToReconductWithModif.addAll((Collection<EdsEds>) vTCSreconductionSelection.getValue());

                edsesToReconsult.removeAll(edsesToReconductWithModif);

                getContent().removeAllComponents();
                getContent().addComponent(getReconsultationLayout());
                getContent().setSizeUndefined();
                center();
            }
        });
    }

    /**
     * This method is used to add listener to the next and previous button on the ReconsultSelection pop-up.
     * 
     * @param vTCSreconsultationSelection Twin column selection object.
     */
    private void initEdsToReconsultSelectionButtonListeners(final TwinColSelect vTCSreconsultationSelection) {
        removeButtonsListeners();
        vBTprev.setEnabled(true);
        vBTnext.setEnabled(true);
        vBTnext.setCaption(controller.getBundle().getString("eds-popup-duplication-project-next"));

        vBTprev.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                edsesToReconsult.clear();
                edsesToReconsult.addAll((Collection<EdsEds>) vTCSreconsultationSelection.getValue());

                getContent().removeAllComponents();
                getContent().addComponent(getReconductionWithModifLayout());
                getContent().setSizeUndefined();
                center();
            }
        });

        vBTnext.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                edsesToReconsult.clear();
                edsesToReconsult.addAll((Collection<EdsEds>) vTCSreconsultationSelection.getValue());

                getContent().removeAllComponents();
                getContent().addComponent(getSummaryLayout());
                getContent().setSizeUndefined();
                center();
            }
        });
    }

    /**
     * This method is used to add listener to the next and previous button on the Summary pop-up.
     */
    private void initSummaryButtonListeners() {
        removeButtonsListeners();
        vBTprev.setEnabled(true);
        vBTnext.setEnabled(true);
        vBTnext.setCaption(controller.getBundle().getString("eds-popup-duplication-project-end"));

        vBTprev.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                getContent().removeAllComponents();
                getContent().addComponent(getReconsultationLayout());
                getContent().setSizeUndefined();
                center();
            }
        });

        vBTnext.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                for (EdsEds eds : edsesToReconductWithoutModif) {
                    controller.reconduct(eds, Collections.singletonList(dstProject), false);
                }
                for (EdsEds eds : edsesToReconductWithModif) {
                    controller.reconduct(eds, Collections.singletonList(dstProject), true);
                }
                for (EdsEds eds : edsesToReconsult) {
                    controller.reconsult(eds, dstProject, false);
                }

                showNotification(controller.getBundle().getString("eds-popup-duplication-project-ok"));
                close();
            }
        });
    }

    /**
     * This method is used to create list of all the selected EDS to be displayed on the Summary popup.
     * 
     * @param edses List of EDS to be added.
     * @return List component which displays all the EDS provided.
     */
    private VerticalLayout getListRepresentation(List<EdsEds> edses) {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSizeFull();
        if (edses.isEmpty()) {
            layout.addComponent(new Label("-"));
        } else {
            for (EdsEds eds : edses) {
                layout.addComponent(new Label("- " + eds.getEdsName()));
            }
        }
        return layout;
    }
}
