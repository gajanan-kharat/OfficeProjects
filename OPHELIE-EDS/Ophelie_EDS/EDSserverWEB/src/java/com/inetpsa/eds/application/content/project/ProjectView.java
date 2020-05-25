package com.inetpsa.eds.application.content.project;

import java.text.MessageFormat;
import java.util.List;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.tools.filter.EdsFilterManager;
import com.inetpsa.eds.tools.filter.normal.EdsNormalFilter;
import com.inetpsa.eds.tools.filter.project.EdsProjectFilter;
import com.inetpsa.eds.tools.table.EdsTable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * This class corresponds to the view associated with a project menu. It displays a list of sheets belonging to the EDS project.
 * 
 * @author Geometric Ltd.
 */
public class ProjectView extends VerticalLayout {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     * @param project project details.
     */
    public ProjectView(EdsApplicationController controller, EdsProject project) {
        super();
        this.controller = controller;
        this.project = project;
        init();
    }

    /**
     * This method is used to refresh the EDS project list menu.
     */
    public void refresh() {
        EDSdao dao = EDSdao.getInstance();

        List<EdsEds> edsList = dao.executeEdsCriteria(filterManager.buildQuery());

        Label vLBLnewTitle = new Label(MessageFormat.format(controller.getBundle().getString("eds-home-table-label"), project.getPName(),
                edsList.size()));
        vLBLnewTitle.setStyleName("h1");

        replaceComponent(vLBLtitle, vLBLnewTitle);
        vLBLtitle = vLBLnewTitle;

        table.setEdsList(edsList);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store project details.
     */
    private EdsProject project;
    /**
     * Label for the list title.
     */
    private Label vLBLtitle;
    /**
     * Filter manager for filtering projects.
     */
    private EdsFilterManager filterManager;
    /**
     * EDS table.
     */
    private EdsTable table;

    /**
     * Initialization parameter. This method is used to display the list of projects.
     */
    private void init() {
        setSpacing(true);

        vLBLtitle = new Label();

        table = new EdsTable(controller);

        table.setWidth("100%");
        table.setHeight("70%");

        this.filterManager = new EdsFilterManager(controller);
        this.filterManager.addFilter(new EdsProjectFilter(project, controller)); // Filter the project.
        this.filterManager.addFilter(new EdsNormalFilter(controller)); // Filter the latest version of
                                                                       // the EDS cards.

        addComponent(vLBLtitle);
        addComponent(table);
        setComponentAlignment(table, Alignment.TOP_CENTER);
    }
}
