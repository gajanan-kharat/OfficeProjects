package com.inetpsa.eds.application.content.eds.currentconsumption.preliminary;

import java.io.File;
import java.util.ArrayList;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.composants.Comment;
import com.inetpsa.eds.application.content.eds.currentconsumption.robust.RobusteTable;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPrimaryCurent;
import com.inetpsa.eds.dao.model.EdsPrimarySupply;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.component.ImagePanel;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;

/**
 * This class provide component for reading current consumption of consolidate stage
 * 
 * @author Geometric Ltd.
 */
public class PrimaryFormReadView extends A_EdsReadForm {

    /**
     * Variable to hold list of EdsSupply
     */
    public ArrayList<EdsSupply> supplyListAfterRefresh;

    /**
     * Parameterized constructor
     * 
     * @param eds Object of EdsEds
     * @param edsPrimaryFormData Object of edsPrimaryFormData
     * @param controller Controller of EDS application
     */
    public PrimaryFormReadView(EdsEds eds, EdsPrimaryCurent edsPrimaryFormData, EdsApplicationController controller) {
        super(controller);
        this.eds = eds;
        this.edsPrimaryFormData = edsPrimaryFormData;
        this.controller = controller;
        init();
    }

    // PRIVATE
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of EdsPrimaryCurent
     */
    private EdsPrimaryCurent edsPrimaryFormData;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of TabSheet for power supply
     */
    private TabSheet vTSAlimentation;
    /**
     * Variable to hold value of GridLayout for power supply
     */
    private GridLayout vGLAliementation;
    /**
     * Variable to hold value of Table for power supply
     */
    private Table vTAlimentation;
    /**
     * Variable to hold value of Panel for comment
     */
    private Panel vPCommentaire;
    /**
     * Variable to hold value of Comment
     */
    private Comment vLCommentaire;
    /**
     * Variable to hold value of ImagePanel
     */
    private ImagePanel vPImage;

    /**
     * Initialize component for reading current consumption of consolidate stage
     */
    private void init() {
        vTSAlimentation = new TabSheet();
        vTSAlimentation.setWidth("100%");

        vGLAliementation = new GridLayout(2, 2);
        vGLAliementation.setWidth("100%");
        vGLAliementation.setMargin(true);
        vGLAliementation.setSpacing(true);
        vGLAliementation.setColumnExpandRatio(0, 0.5f);
        vGLAliementation.setColumnExpandRatio(1, 0.5f);

        // Supplies table
        vTAlimentation = new Table();
        vTAlimentation.setWidth("100%");
        vTAlimentation.setSelectable(true);
        vTAlimentation.addContainerProperty(controller.getBundle().getString("current-conso-list-alim-title"), Label.class, null);
        vTAlimentation.addContainerProperty(controller.getBundle().getString("current-conso-list-alim-type"), Label.class, null);
        vGLAliementation.addComponent(vTAlimentation, 0, 0);

        // Image
        vPImage = new ImagePanel("Illustration", controller);
        vPImage.setSizeFull();
        vPImage.setScrollable(true);
        vGLAliementation.addComponent(vPImage, 1, 0);

        // Comment
        vPCommentaire = new Panel(controller.getBundle().getString("eds-comnent"));
        vPCommentaire.setWidth("100%");

        this.vLCommentaire = new Comment(controller);

        vPCommentaire.addComponent(vLCommentaire);
        vGLAliementation.addComponent(vPCommentaire, 0, 1, 1, 1);
        vTSAlimentation.addTab(vGLAliementation, controller.getBundle().getString("current-conso-list-alim-title"));

        addComponent(vTSAlimentation);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "menu-app-current-cons";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return PrimaryFormBuilder.ID;
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

        vTAlimentation.removeAllItems();

        vLCommentaire.setValue(edsPrimaryFormData.getPcComment());

        File ressourceDeLimage = new File(controller.getImageFilePath(edsPrimaryFormData.getPcImage()));
        if (ressourceDeLimage.exists()) {
            vPImage.addImage(new FileResource(ressourceDeLimage, getApplication()));
        }

        int i = 0;
        removTab(0, vTSAlimentation.getComponentCount());
        for (EdsSupply alim : edsPrimaryFormData.getEdsSupplies()) {
            vTAlimentation.addItem(
                    new Object[] { alim.getEdsPrimarySupply().getPsedsSupplyName(),
                            (alim.getEdsPrimarySupply().getWording()).getValueByLocale(controller.getApplication().getLocale()) }, new Integer(i));
            addAlimentation(alim, alim.getEdsPrimarySupply() + "(" + alim.getEdsPrimarySupply().getPsedsTbtBt() + ")");

            i++;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        edsPrimaryFormData = controller.getFormDataModel(eds, edsPrimaryFormData.getClass());
    }

    /**
     * This method new supply tab
     * 
     * @param alim Object of EdsSupply
     * @param name Name given to tab
     */
    private void addAlimentation(EdsSupply alim, String name) {

        EdsPrimarySupply edsPS = alim.getEdsPrimarySupply();

        GridLayout layout = new GridLayout();
        RobusteTable table = new RobusteTable(controller);
        table.setPageLength(6);
        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-list-alim-modif-name"), edsPS.getPsedsSupplyName(),
                edsPS.getPsedsSupplyNameIformBy(), edsPS.getPsedsSupplyNameComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-list-alim-modif-type"), edsPS.getWording().toString(),
                edsPS.getPsedsSupplyTypeSupplyNameIformBy(), edsPS.getPsedsSupplyTypeSupplyNameComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-veil"),
                EDSTools.convertFloatToString(edsPS.getPsedsIVeille()), edsPS.getPsedsIVeilleIformBy(), edsPS.getPsedsIVeilleComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-rev"),
                EDSTools.convertFloatToString(edsPS.getPsedsIReveilleInactif()), edsPS.getPsedsReveilleIformBy(), edsPS.getPsedsReveilleComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-nom"),
                EDSTools.convertFloatToString(edsPS.getPsedsINomStab()), edsPS.getPsedsINomStabIformBy(), edsPS.getPsedsINomStabComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-pire"),
                EDSTools.convertFloatToString(edsPS.getPsedsIPirecasStab()), edsPS.getPsedsIPirecasStabIformBy(), edsPS.getPsedsIPirecasComment());

        layout.setMargin(true);
        layout.addComponent(table);
        layout.setWidth("100%");

        vTSAlimentation.addTab(layout, name);
    }

    /**
     * This method remove tab
     * 
     * @param begin Start index
     * @param end End index
     */
    private void removTab(int begin, int end) {
        for (int i = end - 1; i > begin; i--) {
            vTSAlimentation.removeTab(vTSAlimentation.getTab(i));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "conso-preliminaire-title";
    }
}
