package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate;

import java.io.File;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.composants.Comment;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPrimaryCurent;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.inetpsa.eds.dao.model.EdsSupply;
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
public class ConsolidateFormReadView extends A_EdsReadForm {
    // PRIVE

    /**
     * Variable to hold value of EdsRobustCurentFormData
     */
    private EdsRobustCurentFormData edsRobustCurentFormData;
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of EdsConsolidateCurentFormData
     */
    private EdsConsolidateCurentFormData edsConsolidateCurentFormData;
    /**
     * Variable to hold value of EdsApplicationController
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsPrimaryCurent
     */
    private EdsPrimaryCurent edsPrimaryCurrentFormData;
    /**
     * Variable to hold value of TabSheet for Supply
     */
    private TabSheet vTSAlimentation;
    /**
     * Variable to hold value of GridLayout for supply
     */
    private GridLayout vGLAliementation;
    /**
     * Variable to hold value of Table for Supply
     */
    private Table vTAlimentation;
    /**
     * Variable to hold value of Panel for Comment
     */
    private Panel vPCommentaire;
    /**
     * Variable to hold value of comment
     */
    private Comment vLCommentaire;
    /**
     * Variable to hold value of ImagePanle
     */
    private ImagePanel vPImage;
    /**
     * Variable to hold value of ConsolidateGroundReadView
     */
    private ConsolidateGroundReadView groundReadView;

    // PUBLIC
    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     * @param eds Object of EdsEds
     * @param edsConsolidateCurentFormData Object of EdsConsolidateCurentFormData
     * @param edsRobustCurentFormData Object of EdsRobustCurentFormData
     * @param edsPrimaryCurrentFormData Object of EdsPrimaryCurent
     */
    public ConsolidateFormReadView(EdsApplicationController controller, EdsEds eds, EdsConsolidateCurentFormData edsConsolidateCurentFormData,
            EdsRobustCurentFormData edsRobustCurentFormData, EdsPrimaryCurent edsPrimaryCurrentFormData) {
        super(controller);
        this.controller = controller;
        this.eds = eds;
        this.edsConsolidateCurentFormData = edsConsolidateCurentFormData;
        this.edsRobustCurentFormData = edsRobustCurentFormData;
        this.edsPrimaryCurrentFormData = edsPrimaryCurrentFormData;
        init();
    }

    /**
     * Initialize component for reading current consumption of consolidate stage form
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

        // Supply table
        vTAlimentation = new Table();
        vTAlimentation.setWidth("100%");
        vTAlimentation.setSelectable(true);
        vTAlimentation.addContainerProperty(controller.getBundle().getString("current-conso-list-alim-title"), Label.class, null);
        vTAlimentation.addContainerProperty(controller.getBundle().getString("current-conso-list-alim-type"), Label.class, null);
        vGLAliementation.addComponent(vTAlimentation, 0, 0);

        // Image
        vPImage = new ImagePanel(controller.getBundle().getString("image-title"), controller);
        vPImage.setSizeFull();
        vPImage.setScrollable(true);
        vGLAliementation.addComponent(vPImage, 1, 0);

        // Comments
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
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {

        removTab(0, vTSAlimentation.getComponentCount());
        vTAlimentation.removeAllItems();

        vLCommentaire.setValue(edsConsolidateCurentFormData.getCcComment());

        // Show Primary stage image only if there's no image set on Robust stage
        final String imageFilename = (edsRobustCurentFormData.getRcImage() != null) ? edsRobustCurentFormData.getRcImage()
                : edsPrimaryCurrentFormData.getPcImage();
        File ressourceDeLimage = new File(controller.getImageFilePath(imageFilename));
        if (ressourceDeLimage.exists()) {
            vPImage.addImage(new FileResource(ressourceDeLimage, getApplication()));
        }

        groundReadView = new ConsolidateGroundReadView(controller, edsConsolidateCurentFormData.getEdsGroundConsolidateCurent());
        vTSAlimentation.addTab(groundReadView);

        int i = 0;
        for (EdsSupply alimentationC : edsConsolidateCurentFormData.getEdsSupplies()) {
            // alimentationC.getEdsConsolidateSupply().getWording().setLocale(
            // controller.getApplication().getLocale());

            vTAlimentation.addItem(new Object[] { alimentationC.getEdsConsolidateSupply().getCsedsSupplyName(),
                    alimentationC.getEdsConsolidateSupply().getWording().getValueByLocale(controller.getApplication().getLocale()) }, new Integer(i));
            addAlimentation(alimentationC, alimentationC.getEdsConsolidateSupply().getCsedsTbtBt());
            i++;
        }

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
        return ConsolidateFormBuilder.ID;
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
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {

        edsPrimaryCurrentFormData = controller.getFormDataModel(eds, edsPrimaryCurrentFormData.getClass());
        edsRobustCurentFormData = controller.getFormDataModel(eds, edsRobustCurentFormData.getClass());
        edsConsolidateCurentFormData = controller.getFormDataModel(eds, edsConsolidateCurentFormData.getClass());
    }

    /**
     * This method add supply table
     * 
     * @param alimentation Object of EdsSupply
     * @param CsedsTbtBt String to hold value BT/TBT
     */
    private void addAlimentation(EdsSupply alimentation, String CsedsTbtBt) {
        ConsolidateSupplyReadView consolidateSupplyReadView = new ConsolidateSupplyReadView(alimentation.getEdsConsolidateSupply(), controller);

        vTSAlimentation.addTab(consolidateSupplyReadView, alimentation + "(" + CsedsTbtBt + ")", null);
    }

    /**
     * This method Remove tab
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
        return "conso-cons-title-2";
    }
}
