package com.inetpsa.eds.application.content.eds.connectivity.association;

import java.util.ResourceBundle;

import com.inetpsa.eds.application.ChsController;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.chs.ConnectPartPanel;
import com.inetpsa.eds.tools.filter.ChsFilterPanel;
import com.inetpsa.eds.tools.filter.EdsChsConnectView;
import com.inetpsa.eds.tools.table.CHSTable;
import com.inetpsa.eds.tools.table.EmbaseCHSTable;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;

/**
 * This class represents 'Association FC'
 * 
 * @author Joao Costa @ Alter Frame
 */
public class AssociationFormReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param controller EDS application controller object.
     */
    public AssociationFormReadView(EdsApplicationController controller, EdsEds eds) {
        super(controller);
        this.controller = controller;
        this.bundle = controller.getBundle();
        this.eds = eds;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "connectivity-association-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "connectivity-association-title-in";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return ConnectivityFormBuilder.ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        return eds.getEdsRef();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEds()
     */
    @Override
    public EdsEds getEds() {
        return eds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {

        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());

    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Main layout of the view.
     */
    private GridLayout mainLayout;

    /**
     * EDS details.
     */
    private EdsEds eds;

    /**
     * Layout for the filter panel.
     */
    private Layout filterPanel;

    private ConnectPartPanel partPanel;

    private ChsController chsController;

    private CHSTable table;

    private EmbaseCHSTable embaseTable;

    private EdsChsConnectView selectedWire;

    private ChsFilterPanel chsFilterPanel;

    private static final String PANEL_HEIGHT = "250px";
    // private Comment vLBLcomments;
    // /**
    // * Resource bundle to read configuration file.
    // */
    private ResourceBundle bundle;

    /**
     * Initialization method.
     */
    private void init() {
        chsController = new ChsController(eds, controller);
        mainLayout = new GridLayout(20, 5);
        mainLayout.setWidth("100%");
        mainLayout.setMargin(false);
        mainLayout.setSpacing(true);

        table = new CHSTable(controller, chsController);
        table.setSizeFull();

        chsFilterPanel = new ChsFilterPanel(chsController, controller);

        embaseTable = new EmbaseCHSTable(controller, chsController);
        embaseTable.setSizeFull();
        selectedWire = new EdsChsConnectView(controller, chsController);
        Panel topPanel = new Panel(controller.getBundle().getString("con-selected-wire"));

        topPanel.getContent().addComponent(selectedWire);
        topPanel.setHeight(PANEL_HEIGHT);

        mainLayout.addComponent(chsFilterPanel, 0, 0, 14, 1);
        mainLayout.addComponent(topPanel, 15, 0, 19, 1);
        mainLayout.addComponent(table, 0, 2, 17, 4);
        mainLayout.addComponent(embaseTable, 18, 2, 19, 4);

        addComponent(mainLayout);
        chsController.init();
    }

}
