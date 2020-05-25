package com.inetpsa.eds.application.content.eds.currentconsumption.robust;

import java.io.File;
import java.util.ArrayList;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.composants.Comment;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsGround;
import com.inetpsa.eds.dao.model.EdsPrimaryCurent;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.inetpsa.eds.dao.model.EdsRobustSupply;
import com.inetpsa.eds.dao.model.EdsRobustSupplyUseCase;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.component.ImagePanel;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide component for reading current consumption of robust stage
 * 
 * @author Geometric Ltd.
 */
public class RobustFormReadView extends A_EdsReadForm {
    // PROTECTED
    // PRIVATE

    /**
     * UID
     */
    private static final long serialVersionUID = -908991746680691118L;
    /**
     * Variable to hold value of EdsRobustCurentFormData
     */
    private EdsRobustCurentFormData edsRobustCurentFormData;
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of EdsApplicationController
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsPrimaryCurent
     */
    private EdsPrimaryCurent edsPrimaryCurent;
    /**
     * Variable to hold value of TabSheet for Supply
     */
    private TabSheet vTSAlimentation;
    /**
     * Variable to hold value of VerticalLayout
     */
    private VerticalLayout vGLMasse;
    /**
     * Variable to hold value of GridLayout for Supply
     */
    private GridLayout vGLAlimentation;
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
     * Parameterized constructor
     * 
     * @param eds Object of EdsEds
     * @param edsRobustCurentFormData Object of edsRobustCurentFormData
     * @param edsPrimaryCurent Object of edsPrimaryCurent
     * @param controller controller of EDS application
     */
    public RobustFormReadView(EdsEds eds, EdsRobustCurentFormData edsRobustCurentFormData, EdsPrimaryCurent edsPrimaryCurent,
            EdsApplicationController controller) {
        super(controller);
        this.eds = eds;
        this.edsRobustCurentFormData = edsRobustCurentFormData;
        this.edsPrimaryCurent = edsPrimaryCurent;
        this.controller = controller;
        init();
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
        return RobustFormBuilder.ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshViewData()
     */
    @Override
    public void refreshViewData() {
        removTab(0, vTSAlimentation.getComponentCount() - 1);
        vTAlimentation.removeAllItems();
        vLCommentaire.setValue(edsRobustCurentFormData.getRcComment());

        // Show Primary stage image only if there's no image set on Robust stage
        final String imageFilename = (edsRobustCurentFormData.getRcImage() != null) ? edsRobustCurentFormData.getRcImage() : edsPrimaryCurent
                .getPcImage();
        File ressourceDeLimage = new File(controller.getImageFilePath(imageFilename));
        if (ressourceDeLimage.exists()) {
            vPImage.addImage(new FileResource(ressourceDeLimage, getApplication()));
        }

        int i = 0;
        for (EdsSupply alim : edsRobustCurentFormData.getEdsSupplies()) {
            vTAlimentation.addItem(new Object[] { alim.getEdsRobustSupply().getRsedsSupplyName(),
                    alim.getEdsRobustSupply().getWording().getValueByLocale(controller.getApplication().getLocale()) }, new Integer(i));
            addAlimentation(alim, alim.getEdsRobustSupply().toString());
            i++;
        }

        // MASSES
        vGLMasse.removeAllComponents();
        for (EdsGround masse : edsRobustCurentFormData.getEdsGrounds()) {
            vGLMasse.addComponent(new GroundRobusteCurent(masse.getEdsGroundRobustCurent(), controller, edsRobustCurentFormData));
        }

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
        edsRobustCurentFormData = controller.getFormDataModel(eds, edsRobustCurentFormData.getClass());
        edsPrimaryCurent = controller.getFormDataModel(eds, edsPrimaryCurent.getClass());
    }

    /**
     * Initialize component for reading current consumption of robust stage
     */
    private void init() {
        vTSAlimentation = new TabSheet();
        vTSAlimentation.setWidth("100%");

        vGLAlimentation = new GridLayout(2, 2);
        vGLAlimentation.setWidth("100%");
        vGLAlimentation.setMargin(true);
        vGLAlimentation.setSpacing(true);
        vGLAlimentation.setColumnExpandRatio(0, 0.5f);
        vGLAlimentation.setColumnExpandRatio(1, 0.5f);

        // Supply table
        vTAlimentation = new Table();
        vTAlimentation.setWidth("100%");
        vTAlimentation.setSelectable(true);
        vTAlimentation.addContainerProperty(controller.getBundle().getString("current-conso-list-alim-title"), Label.class, null);
        vTAlimentation.addContainerProperty(controller.getBundle().getString("current-conso-list-alim-type"), Label.class, null);
        vGLAlimentation.addComponent(vTAlimentation, 0, 0);

        // Image
        vPImage = new ImagePanel(controller.getBundle().getString("image-title"), controller);
        vPImage.setSizeFull();
        vPImage.setScrollable(true);
        vGLAlimentation.addComponent(vPImage, 1, 0);

        // Comment
        vPCommentaire = new Panel(controller.getBundle().getString("eds-comnent"));
        vPCommentaire.setWidth("100%");

        this.vLCommentaire = new Comment(controller);

        vPCommentaire.addComponent(vLCommentaire);
        vGLAlimentation.addComponent(vPCommentaire, 0, 1, 1, 1);
        vTSAlimentation.addTab(vGLAlimentation, controller.getBundle().getString("current-conso-list-alim-title"));

        vGLMasse = new VerticalLayout();
        vTSAlimentation.addTab(vGLMasse, controller.getBundle().getString("current-conso-tab-mass-rob-title"));

        addComponent(vTSAlimentation);
    }

    /**
     * This method returns list of EdsSupply
     * 
     * @param alim List of EdsSupply
     * @return list of EdsSupply
     */
    public ArrayList<EdsSupply> getListDesAlimPreliminaire(ArrayList<EdsSupply> alim) {
        ArrayList<EdsSupply> list = new ArrayList<EdsSupply>();
        list.addAll(alim);
        return list;
    }

    /**
     * Adds a new tab sheet of a Power Supply use case data
     * 
     * @param useCase
     * @param name
     * @param layout
     */
    private void addAlimentationUseCase(EdsRobustSupplyUseCase useCase, String name, GridLayout layout) {
        EdsRobustSupply edsRS = useCase.getRobustSupply();
        RobusteTable table = new RobusteTable(controller);
        int i = 15;

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-list-alim-modif-name"), edsRS.getRsedsSupplyName(),
                edsRS.getRsedsSupplyNameIformBy(), edsRS.getRsedsSupplyNameComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-list-alim-modif-type"),
                edsRS.getWording().getValueByLocale(controller.getApplication().getLocale()), edsRS.getRsedsSupplyTypeSupplyNameIformBy(),
                edsRS.getRsedsSupplyTypeSupplyNameComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-veil"),
                EDSTools.convertFloatToString(useCase.getRsucIVeille()), useCase.getRsucIVeilleIformBy(), useCase.getRsucIVeilleComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-rev"),
                EDSTools.convertFloatToString(useCase.getRsucIReveilInactif()), useCase.getRsucIReveilIformBy(), useCase.getRsucIReveilComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-nom"),
                EDSTools.convertFloatToString(useCase.getRsucINomStab()), useCase.getRsucINomStabIformBy(), useCase.getRsucINomStabComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Tnom-stab"),
                EDSTools.convertFloatToString(useCase.getRsucTNomStab()), useCase.getRsucTnomStabInformBy(), useCase.getRsucTnomStabComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-pire"),
                EDSTools.convertFloatToString(useCase.getRsucIPirecasStab()), useCase.getRsucIPirecasStabIformBy(), useCase.getRsucIPirecasComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Tpire-cas"),
                EDSTools.convertFloatToString(useCase.getRsucTPireCas()), useCase.getRsucTpireCasInformBy(), useCase.getRsucTpireCasComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Idysf"),
                EDSTools.convertFloatToString(useCase.getRsucIdysf()), useCase.getRsucIdysfInformBy(), useCase.getRsucIdysfComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Tdysf"),
                EDSTools.convertFloatToString(useCase.getRsucTdysf()), useCase.getRsucTdysfInformBy(), useCase.getRsucTdysfComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Imst"),
                EDSTools.convertFloatToString(useCase.getRsucImst()), useCase.getRsucImstInformBy(), useCase.getRsucImstComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Tmst"),
                EDSTools.convertFloatToString(useCase.getRsucTmst()), useCase.getRsucTmstInformBy(), useCase.getRsucTmstComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-pic"),
                EDSTools.convertFloatToString(useCase.getRsucIMax()), useCase.getRsucIMaxInformBy(), useCase.getRsucIMaxComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Tpic"),
                EDSTools.convertFloatToString(useCase.getRsucTIMax()), useCase.getRsucTIMaxInformBy(), useCase.getRsucTIMaxComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-veil-parc"),
                EDSTools.convertFloatToString(useCase.getRsucIConsoParc()), useCase.getRsucIConsoParcInformBy(), useCase.getRsucIConsoParcComment());

        if (edsRS.getRsedsTbtBt().equals("BT")) {
            i += 3;
            table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Umin"),
                    EDSTools.convertFloatToString(useCase.getRsucUMin()), useCase.getRsucUMinInformBy(), useCase.getRsucUMinComment());

            table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Umoy"),
                    EDSTools.convertFloatToString(useCase.getRsucUMoy()), useCase.getRsucUMoyInformBy(), useCase.getRsucUMoyComment());

            table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Umax"),
                    EDSTools.convertFloatToString(useCase.getRsucUMax()), useCase.getRsucUMaxInformBy(), useCase.getRsucUMaxComment());

            if (edsRS.getRsedsUcaracName() != null && !edsRS.getRsedsUcaracName().equals("")) {
                i++;
                table.addOrderToContainer(table, edsRS.getRsedsUcaracName() + " (V)", EDSTools.convertFloatToString(edsRS.getRsedsUcaracValue()),
                        edsRS.getRsedsUcaracInformBy(), edsRS.getRsedsUcaracComment());
            }
        }

        table.setPageLength(i);
        layout.addComponent(new Label("<br/><h3> " + name + " <h3/>", Label.CONTENT_XHTML));
        layout.addComponent(table);
    }

    /**
     * This method add tab sheet for power supply
     * 
     * @param alim EdsSupply
     * @param name Name for supply
     */
    private void addAlimentation(EdsSupply alim, String name) {
        EdsRobustSupply edsRS = alim.getEdsRobustSupply();
        GridLayout layout = new GridLayout();
        RobusteTable table = new RobusteTable(controller);
        int i = 15;

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-list-alim-modif-name"), edsRS.getRsedsSupplyName(),
                edsRS.getRsedsSupplyNameIformBy(), edsRS.getRsedsSupplyNameComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-list-alim-modif-type"),
                edsRS.getWording().getValueByLocale(controller.getApplication().getLocale()), edsRS.getRsedsSupplyTypeSupplyNameIformBy(),
                edsRS.getRsedsSupplyTypeSupplyNameComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-veil"),
                EDSTools.convertFloatToString(edsRS.getRsedsIVeille()), edsRS.getRsedsIVeilleIformBy(), edsRS.getRsedsIVeilleComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-rev"),
                EDSTools.convertFloatToString(edsRS.getRsedsIReveilleInactif()), edsRS.getRsedsReveilleIformBy(), edsRS.getRsedsReveilleComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-nom"),
                EDSTools.convertFloatToString(edsRS.getRsedsINomStab()), edsRS.getRsedsINomStabIformBy(), edsRS.getRsedsINomStabComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Tnom-stab"),
                EDSTools.convertFloatToString(edsRS.getRsedsTNomStab()), edsRS.getRsedsTnomStabInformBy(), edsRS.getRsedsTnomStabComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-prem-pire"),
                EDSTools.convertFloatToString(edsRS.getRsedsIPirecasStab()), edsRS.getRsedsIPirecasStabIformBy(), edsRS.getRsedsIPirecasComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Tpire-cas"),
                EDSTools.convertFloatToString(edsRS.getRsedsTPireCas()), edsRS.getRsedsTpireCasInformBy(), edsRS.getRsedsTpireCasComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Idysf"),
                EDSTools.convertFloatToString(edsRS.getRsedsIdysf()), edsRS.getRsedsIdysfInformBy(), edsRS.getRsedsIdysfComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Tdysf"),
                EDSTools.convertFloatToString(edsRS.getRsedsTdysf()), edsRS.getRsedsTdysfInformBy(), edsRS.getRsedsTdysfComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Imst"),
                EDSTools.convertFloatToString(edsRS.getRsedsImst()), edsRS.getRsedsImstInformBy(), edsRS.getRsedsImstComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Tmst"),
                EDSTools.convertFloatToString(edsRS.getRsedsTmst()), edsRS.getRsedsTmstInformBy(), edsRS.getRsedsTmstComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-pic"),
                EDSTools.convertFloatToString(edsRS.getRsedsIMax()), edsRS.getRsedsIMaxInformBy(), edsRS.getRsedsIMaxComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Tpic"),
                EDSTools.convertFloatToString(edsRS.getRsedsTIMax()), edsRS.getRsedsTIMaxInformBy(), edsRS.getRsedsTIMaxComment());

        table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-veil-parc"),
                EDSTools.convertFloatToString(edsRS.getRsedsIConsoParc()), edsRS.getRsedsIConsoParcInformBy(), edsRS.getRsedsIConsoParcComment());

        if (edsRS.getRsedsTbtBt().equals("BT")) {
            i += 3;
            table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Umin"),
                    EDSTools.convertFloatToString(edsRS.getRsedsUMin()), edsRS.getRsedsUMinInformBy(), edsRS.getRsedsUMinComment());

            table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Umoy"),
                    EDSTools.convertFloatToString(edsRS.getRsedsUMoy()), edsRS.getRsedsUMoyInformBy(), edsRS.getRsedsUMoyComment());

            table.addOrderToContainer(table, controller.getBundle().getString("current-conso-tab-data-rob-Umax"),
                    EDSTools.convertFloatToString(edsRS.getRsedsUMax()), edsRS.getRsedsUMaxInformBy(), edsRS.getRsedsUMaxComment());

            if (edsRS.getRsedsUcaracName() != null && !edsRS.getRsedsUcaracName().equals("")) {
                i++;
                table.addOrderToContainer(table, edsRS.getRsedsUcaracName() + " (V)", EDSTools.convertFloatToString(edsRS.getRsedsUcaracValue()),
                        edsRS.getRsedsUcaracInformBy(), edsRS.getRsedsUcaracComment());
            }
        }

        table.setPageLength(i);
        layout.setMargin(true);
        layout.addComponent(new Label("<br/><h3> " + edsRS.getRsedsUseCaseName() + " <h3/>", Label.CONTENT_XHTML));
        layout.addComponent(table);
        layout.setWidth("100%");

        // Add also the supply's use case
        for (EdsRobustSupplyUseCase useCase : edsRS.getUseCases()) {
            addAlimentationUseCase(useCase, useCase.getRsucName(), layout);
        }

        vTSAlimentation.addTab(layout, name, null, vTSAlimentation.getComponentCount() - 1);
    }

    /**
     * This method removes tab
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
        return "conso-robuste-title";
    }
}
