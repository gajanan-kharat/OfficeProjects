package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.vaadin.dialogs.ConfirmDialog;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver.SupplyDriftController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.ConsolidateSupplyEdsTension;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsConsolidateSupply;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyMesure;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyTheoritic;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsCourantMiseSousTension;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsDriftInfo;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsGroundConsolidateCurent;
import com.inetpsa.eds.dao.model.EdsModeParc;
import com.inetpsa.eds.dao.model.EdsPrimaryCurent;
import com.inetpsa.eds.dao.model.EdsReseauVeilleReveilleOrganeInactif;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.inetpsa.eds.dao.model.EdsRobustSupplyUseCase;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.component.ImagePanel;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.Window.Notification;

/**
 * This class provide component for editing view of Current consumption of consolidate stage
 * 
 * @author Geometric Ltd.
 */
public class ConsolidateFormEditView extends A_EdsEditForm implements Property.ValueChangeListener, TabSheet.CloseHandler {
    // PRIVE
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of EdsRobustCurentFormData
     */
    private EdsRobustCurentFormData edsRobustCurentFormData;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsConsolidateCurentFormData
     */
    private EdsConsolidateCurentFormData edsConsolidateCurentFormData;
    /**
     * Variable to hold value of EdsPrimaryCurrent
     */
    private EdsPrimaryCurent edsPrimaryFormData;
    /**
     * Variable to hold value of EdsGroundConsolidateCurent
     */
    private EdsGroundConsolidateCurent groundConsolidateCurent;
    /**
     * Variable to hold value of ConsolidateGroundEditView
     */
    private ConsolidateGroundEditView groundEditView;
    /**
     * Variable to hold value of TabSheet
     */
    private TabSheet vTSConsolide;
    /**
     * Variable to hold value of GridLayout
     */
    private GridLayout vGLAlimentation;
    /**
     * Variable to hold value of list of EdsConsolidateSupply
     */
    private ArrayList<EdsConsolidateSupply> edsConsolidateSupplys;
    /**
     * Variable to hold value of list of EdsSupply
     */
    private ArrayList<EdsSupply> edsSupplysList;
    /**
     * Variable to hold value of List of ConsolidateSupplyEditView
     */
    private ArrayList<ConsolidateSupplyEditView> consolidateSupplyList;
    /**
     * Variable to hold value of List of Object to delete
     */
    private ArrayList<Object> objectsToDelete;
    /**
     * Variable to hold value of List of Object to save
     */
    private ArrayList<Object> objectsToSave;
    /**
     * Variable to hold value of GridLayout
     */
    private GridLayout vGLAlimDest;
    /**
     * Variable to hold value of ListSelect of power supply type
     */
    private ListSelect vLSTypedAlim;
    /**
     * Variable to hold value of Label for power supply Name
     */
    private Label vLAlimName;
    /**
     * Variable to hold value of TextField for power supply Name
     */
    private TextField vTFAlimName;
    /**
     * Variable to hold value of Label for comparing power supply
     */
    private Label vLAlimCompare;
    /**
     * Variable to hold value of Combo box for comparing power supply
     */
    private ComboBox vCBAlimCompare;
    /**
     * Variable to hold value of Label for power supply BT_TBT
     */
    private Label vLAlimBT_TBT;
    /**
     * Variable to hold value of Combo box for power supply BT_TBT
     */
    private ComboBox vCBAlimBT_TBT;
    /**
     * Variable to hold value of ImagePanel
     */
    private ImagePanel VIPConsolide;
    /**
     * Variable to hold value of Button to add Al power supply im
     */
    private Button vBAddAlim;
    /**
     * Variable to hold value of TwinColSelect
     */
    private TwinColSelect vTSRenewAlim;
    /**
     * Variable to hold value of Button to Renew power supply
     */
    private Button vBRenewAlim;
    /**
     * Variable to hold value of TextArea for comment
     */
    private TextArea vTAComment;
    /**
     * Variable to hold value of Label
     */
    private Label vLAlimDist;
    /**
     * Variable to hold value of ComboBox
     */
    private ComboBox vCBAlimDist;
    /**
     * Variable to hold value of Label
     */
    private Label vLAlimU;
    /**
     * Variable to hold value of TextField
     */
    private TextField vTFAlimU;
    /**
     * Constant to hold value of String array for BT_TBT
     */
    private static final String[] BT_TBT = new String[] { "BT", "TBT" };
    /**
     * Constant to hold value of String array for ORGANE_DIST
     */
    private static final String[] ORGANE_DIST = new String[] { "HY", "VE", "HY+VE" };
    /**
     * Variable to hold value of user
     */
    private String user;

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     * @param eds object of EdsEds
     * @param edsConsolidateCurentFormData Object of EdsConsolidateCurentFormData
     * @param edsPrimaryCurrentFormData Object of EdsPrimaryCurent
     */
    public ConsolidateFormEditView(EdsApplicationController controller, EdsEds eds, EdsConsolidateCurentFormData edsConsolidateCurentFormData,
            EdsPrimaryCurent edsPrimaryCurrentFormData) {

        this.eds = eds;
        this.edsConsolidateCurentFormData = edsConsolidateCurentFormData;
        this.edsPrimaryFormData = edsPrimaryCurrentFormData;
        this.controller = controller;
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     * @param eds object of EdsEds
     * @param edsConsolidateCurentFormData Object of EdsConsolidateCurentFormData
     * @param edsRobustCurentFormData Object of EdsRobustCurentFormData
     * @param edsPrimaryCurrentFormData Object of EdsPrimaryCurent
     */
    ConsolidateFormEditView(EdsApplicationController controller, EdsEds eds, EdsConsolidateCurentFormData edsConsolidateCurentFormData,
            EdsRobustCurentFormData edsRobustCurentFormData, EdsPrimaryCurent edsPrimaryCurrentFormData) {
        this.eds = eds;
        this.edsConsolidateCurentFormData = edsConsolidateCurentFormData;
        this.edsPrimaryFormData = edsPrimaryCurrentFormData;
        this.edsRobustCurentFormData = edsRobustCurentFormData;
        this.controller = controller;
        init();
    }

    /**
     * Initialize Edit view for current consumption of consolidate stage
     */
    private void init() {
        this.objectsToDelete = new ArrayList<Object>();
        this.objectsToSave = new ArrayList<Object>();
        this.user = controller.getAuthenticatedUser().getUFirstname() + " " + controller.getAuthenticatedUser().getULastname();

        vTSConsolide = new TabSheet();
        vTSConsolide.setWidth("100%");

        vGLAlimentation = new GridLayout(4, 9);
        vGLAlimentation.setColumnExpandRatio(0, 0f);
        vGLAlimentation.setColumnExpandRatio(1, 0f);
        vGLAlimentation.setColumnExpandRatio(2, 0f);
        vGLAlimentation.setColumnExpandRatio(3, 1f);

        vGLAlimentation.setWidth("100%");
        vGLAlimentation.setSpacing(true);
        vGLAlimentation.setMargin(true);
        // Add tab power supply manager
        vTSConsolide.addTab(vGLAlimentation, controller.getBundle().getString("current-conso-list-alim-modif-manage"));

        Label title1 = new Label(controller.getBundle().getString("current-conso-list-alim-recond-title"));
        title1.addStyleName("h2");
        title1.setWidth("250");
        vGLAlimentation.addComponent(title1, 0, 0, 2, 0);

        vTSRenewAlim = new TwinColSelect();
        vTSRenewAlim.setWidth("100%");
        vTSRenewAlim.setLeftColumnCaption(controller.getBundle().getString("current-conso-list-alim-recond-left"));
        vTSRenewAlim.setRightColumnCaption(controller.getBundle().getString("current-conso-list-alim-recond-title"));
        vGLAlimentation.addComponent(vTSRenewAlim, 0, 1, 2, 1);

        vBRenewAlim = new Button(controller.getBundle().getString("current-conso-list-alim-recond-button"));
        vBRenewAlim.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Collection<EdsSupply> edsSupplys = (Collection<EdsSupply>) vTSRenewAlim.getValue();
                for (final EdsSupply es : edsSupplys) {
                    final int i = getAlim(es.toString());
                    if (i != 0) {
                        ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("pop-warning-title"), controller
                                .getBundle().getString("alim-reconduct-error"), controller.getBundle().getString("consolid-qcf-oui"), controller
                                .getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                            public void onClose(ConfirmDialog cd) {
                                if (cd.isConfirmed()) {
                                    edsConsolidateSupplys.remove(es.getEdsConsolidateSupply());
                                    edsSupplysList.remove(es);
                                    consolidateSupplyList.remove((ConsolidateSupplyEditView) vTSConsolide.getTab(i).getComponent());
                                    vTSConsolide.removeTab(vTSConsolide.getTab(i));
                                    reconduire(es);
                                }
                            }
                        });
                    } else {
                        reconduire(es);
                    }

                }
            }
        });
        vGLAlimentation.addComponent(vBRenewAlim, 2, 2);
        vGLAlimentation.setComponentAlignment(vBRenewAlim, Alignment.TOP_RIGHT);

        Label title2 = new Label(controller.getBundle().getString("current-conso-list-alim-new"), Label.CONTENT_XHTML);
        title2.addStyleName("h2");
        vGLAlimentation.addComponent(title2, 0, 3, 2, 3);

        vLSTypedAlim = new ListSelect(controller.getBundle().getString("current-conso-list-alim-modif-type"));
        vLSTypedAlim.setWidth("150");
        vLSTypedAlim.setHeight("100%");
        vGLAlimentation.addComponent(vLSTypedAlim, 0, 4, 0, 8);

        vLAlimName = new Label(controller.getBundle().getString("current-conso-list-alim-modif-name") + " :");
        vLAlimName.setWidth("250");
        vGLAlimentation.addComponent(vLAlimName, 1, 4);

        vTFAlimName = new TextField();
        vTFAlimName.setWidth("200");
        vGLAlimentation.addComponent(vTFAlimName, 2, 4);

        vLAlimCompare = new Label(controller.getBundle().getString("current-conso-list-alim-compare") + " :");
        vGLAlimentation.addComponent(vLAlimCompare, 1, 5);

        vCBAlimCompare = new ComboBox();
        vCBAlimCompare.setWidth("200");
        vGLAlimentation.addComponent(vCBAlimCompare, 2, 5);

        vLAlimBT_TBT = new Label(controller.getBundle().getString("current-conso-list-alim-modif-BT-TBT") + " :");
        vGLAlimentation.addComponent(vLAlimBT_TBT, 1, 6);

        vCBAlimBT_TBT = new ComboBox();
        vCBAlimBT_TBT.setWidth("200");
        vCBAlimBT_TBT.setImmediate(true);

        vCBAlimBT_TBT.addListener(this);
        for (int i = 0; i < BT_TBT.length; i++) {
            vCBAlimBT_TBT.addItem(BT_TBT[i]);
        }

        vGLAlimentation.addComponent(vCBAlimBT_TBT, 2, 6);

        vBAddAlim = new Button(controller.getBundle().getString("current-conso-list-alim-BT-button"));
        vBAddAlim.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                addConsolidate();
            }
        });
        vGLAlimentation.addComponent(vBAddAlim, 2, 8);
        vGLAlimentation.setComponentAlignment(vBAddAlim, Alignment.MIDDLE_RIGHT);

        // Destination organ
        vGLAlimDest = new GridLayout(2, 2);
        vGLAlimDest.setWidth("100%");
        vGLAlimDest.setSpacing(true);
        vGLAlimentation.addComponent(vGLAlimDest, 1, 7, 2, 7);

        vLAlimDist = new Label(controller.getBundle().getString("current-conso-list-alim-BT-dest") + " :");
        vLAlimDist.setWidth("245");
        vGLAlimDest.addComponent(vLAlimDist, 0, 0);
        vCBAlimDist = new ComboBox();
        vCBAlimDist.setWidth("200");
        for (int i = 0; i < ORGANE_DIST.length; i++) {
            vCBAlimDist.addItem(ORGANE_DIST[i]);
        }
        vGLAlimDest.addComponent(vCBAlimDist, 1, 0);
        vLAlimU = new Label(controller.getBundle().getString("current-conso-list-alim-BT-intitul") + " :");
        vGLAlimDest.addComponent(vLAlimU, 0, 1);

        vTFAlimU = new TextField();
        vTFAlimU.setWidth("200");
        vGLAlimDest.addComponent(vTFAlimU, 1, 1);

        vGLAlimDest.setVisible(false);

        /**
         * *************************************
         */
        VIPConsolide = new ImagePanel(controller.getBundle().getString("image-title"), controller);
        VIPConsolide.setHeight("100%");
        vGLAlimentation.addComponent(VIPConsolide, 3, 0, 3, 4);

        vTAComment = new TextArea();
        vTAComment.setInputPrompt(controller.getBundle().getString("generic-data-com-no-com"));
        vTAComment.setMaxLength(2048);
        vTAComment.setWidth("100%");
        vTAComment.setHeight("150");
        vGLAlimentation.addComponent(vTAComment, 3, 5, 3, 8);

        vTFAlimName.setRequired(true);
        vCBAlimCompare.setRequired(true);
        vCBAlimBT_TBT.setRequired(true);
        vCBAlimDist.setRequired(true);

        vTSConsolide.setCloseHandler(this);

        groundConsolidateCurent = edsConsolidateCurentFormData.getEdsGroundConsolidateCurent();
        groundEditView = new ConsolidateGroundEditView(controller, edsConsolidateCurentFormData, groundConsolidateCurent, eds);

        vTSConsolide.addTab(groundEditView);

        addComponent(vTSConsolide);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        if (isValid()) {
            for (ConsolidateSupplyEditView view : consolidateSupplyList) {
                view.commitChanges();
            }

            edsConsolidateCurentFormData.setCcComment(vTAComment.getValue().toString());

            groundEditView.commitChanges();
            edsConsolidateCurentFormData.setEdsGroundConsolidateCurent(groundConsolidateCurent);
            edsConsolidateCurentFormData.setEdsSupplies(new HashSet(edsSupplysList));
            boolean driftsFound = false;
            for (EdsSupply supply : edsSupplysList) {
                SupplyDriftController tmpController = new SupplyDriftController(supply, eds, controller);
                List<EdsDriftInfo> driftInfos = tmpController.getAllDriftInfos();
                driftInfos.removeAll(eds.getEdsDriftInfos());

                for (EdsDriftInfo edsDriftInfo : driftInfos) {
                    if (edsDriftInfo.isAlert() || edsDriftInfo.isWarning()) {
                        eds.setEdsHasDrift(1);
                        driftsFound = true;
                        break;
                    }
                }
                if (driftsFound) {
                    break;
                }
            }
            if (!driftsFound) {
                eds.setEdsHasDrift(0);
            }
            eds.setEdsModifDate(new Date());
            return true;
        } else {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {

        objectsToDelete.clear();
        objectsToSave.clear();
        edsConsolidateSupplys = new ArrayList<EdsConsolidateSupply>();
        edsSupplysList = new ArrayList<EdsSupply>();
        consolidateSupplyList = new ArrayList<ConsolidateSupplyEditView>();
        groundEditView.clear();

        for (EdsWording wording : EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.ALIM)) {

            vLSTypedAlim.addItem(wording);
            vLSTypedAlim.setItemCaption(wording, wording.getValueByLocale(controller.getApplication().getLocale()));
        }

        EDSdao.getInstance().refreshDetachedDBObject(edsRobustCurentFormData);

        vTAComment.setValue(edsConsolidateCurentFormData.getCcComment() == null ? "" : edsConsolidateCurentFormData.getCcComment());

        // Show Primary stage image only if there's no image set on Robust stage
        final String imageFilename = (edsRobustCurentFormData.getRcImage() != null) ? edsRobustCurentFormData.getRcImage() : edsPrimaryFormData
                .getPcImage();
        File ressourceDeLimage = new File(controller.getImageFilePath(imageFilename));
        if (ressourceDeLimage.exists()) {
            VIPConsolide.addImage(new FileResource(ressourceDeLimage, getApplication()));
        }

        vCBAlimCompare.removeAllItems();
        vTSRenewAlim.removeAllItems();
        for (EdsSupply supply : edsRobustCurentFormData.getEdsSupplies()) {
            vCBAlimCompare.addItem(supply);
            vTSRenewAlim.addItem(supply);

        }

        removTab(1, vTSConsolide.getComponentCount() - 1);
        for (EdsSupply supply : edsConsolidateCurentFormData.getEdsSupplies()) {
            EdsConsolidateSupply consolidateSupply = supply.getEdsConsolidateSupply();

            if (consolidateSupply != null) {
                ConsolidateSupplyEditView editView = new ConsolidateSupplyEditView(controller, consolidateSupply,
                        consolidateSupply.getEdsConsolidateSupplyTheoritic(), consolidateSupply.getEdsConsolidateSupplyMesure(), supply);

                edsSupplysList.add(supply);
                edsConsolidateSupplys.add(consolidateSupply);
                consolidateSupplyList.add(editView);
                groundEditView.add(consolidateSupply);
                // editView.discardChanges();
                Tab t = vTSConsolide.addTab(editView, consolidateSupply + " (" + consolidateSupply.getCsedsTbtBt() + ")");

                t.setClosable(true);

            }
        }

        groundEditView.discardChanges();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
        List list = new ArrayList();
        list.add(edsConsolidateCurentFormData);
        list.addAll(objectsToSave);
        list.add(eds);
        return list;
    }

    /**
     * Notifies this listener that the Property's value has changed.
     * 
     * @param event value change event object
     */
    public void valueChange(ValueChangeEvent event) {

        if ("BT".equals(event.getProperty().toString())) {

            vGLAlimDest.setVisible(true);
        } else {

            vGLAlimDest.setVisible(false);
        }

    }

    /**
     * This method Add tab sheet for new power supply
     */
    private void addConsolidate() {
        if (!isCompletAlim()) {
            return;
        }
        if (exist(vTFAlimName.getValue().toString())) {
            showNotification("", MessageFormat.format(controller.getBundle().getString("alim-exist"), (String) vTFAlimName.getValue()));
            return;
        }

        EdsSupply es = (EdsSupply) vCBAlimCompare.getValue();

        EdsConsolidateSupplyTheoritic ecst = new EdsConsolidateSupplyTheoritic(UUID.randomUUID().toString());
        EdsConsolidateSupplyMesure ecsm = new EdsConsolidateSupplyMesure(UUID.randomUUID().toString());

        EdsConsolidateSupply consolidateSupply = new EdsConsolidateSupply(UUID.randomUUID().toString(), ecsm, ecst);
        consolidateSupply.setCsedsSupplyName(vTFAlimName.getValue().toString());
        consolidateSupply.setWording((EdsWording) vLSTypedAlim.getValue());
        consolidateSupply.setCsedsTbtBt(vCBAlimBT_TBT.getValue().toString());
        consolidateSupply.setCsedsRef(EDSdao.getInstance().generateAlimRef());

        EdsSupply supply = new EdsSupply(UUID.randomUUID().toString(), eds, null, es.getEdsRobustSupply(), es.getEdsPrimarySupply(),
                consolidateSupply, vTFAlimName.getValue().toString(), es.getSedsDriftComment());

        ConsolidateSupplyEditView editView = new ConsolidateSupplyEditView(controller, consolidateSupply, ecst, ecsm, supply);

        consolidateSupplyList.add(editView);
        edsConsolidateSupplys.add(consolidateSupply);
        edsSupplysList.add(supply);
        groundEditView.add(consolidateSupply);
        Tab t = vTSConsolide.addTab(editView, consolidateSupply + " (" + vCBAlimBT_TBT.getValue().toString() + ")");
        t.setClosable(true);
    }

    /**
     * This method renew EdsSupply specified
     * 
     * @param es object of EdsSupply
     */
    private void reconduire(EdsSupply es) {

        EdsConsolidateSupplyTheoritic ecst = new EdsConsolidateSupplyTheoritic(UUID.randomUUID().toString());
        EdsConsolidateSupplyMesure ecsm = new EdsConsolidateSupplyMesure(UUID.randomUUID().toString());

        EdsConsolidateSupply consolidateSupply = new EdsConsolidateSupply(UUID.randomUUID().toString(), ecsm, ecst);
        consolidateSupply.setCsedsRef(es.getEdsRobustSupply().getRsedsRef());
        consolidateSupply.setCsedsSupplyName(es.getEdsRobustSupply().getRsedsSupplyName());
        consolidateSupply.setCsedsTbtBt(es.getEdsRobustSupply().getRsedsTbtBt());
        consolidateSupply.setWording(es.getEdsRobustSupply().getWording());

        if (es.getEdsRobustSupply().getRsedsIConsoParc() != null && es.getEdsRobustSupply().getRsedsIConsoParc() != 0) {
            consolidateSupply.getEdsQcf().setQcf2(1);
        }

        ConsolidateSupplyEditView editView = new ConsolidateSupplyEditView(controller, consolidateSupply, ecst, ecsm, es);

        // Genirc Data : Tensions Table;
        (ecst.getConsolidateSupplyEdsTensions().iterator().next()).setOperatingModeName(es.getEdsRobustSupply().getRsedsUseCaseName());
        (ecst.getConsolidateSupplyEdsTensions().iterator().next()).setCsEdsUmin(es.getEdsRobustSupply().getRsedsUMin());
        (ecst.getConsolidateSupplyEdsTensions().iterator().next()).setCsEdsUmoy(es.getEdsRobustSupply().getRsedsUMoy());
        (ecst.getConsolidateSupplyEdsTensions().iterator().next()).setCsEdsUmax(es.getEdsRobustSupply().getRsedsUMax());

        // Default Tmoy value to 23
        ecst.setCsedsTmoy(23F);

        // Asleep or awake network and inactive device table
        (ecst.getEdsReseauVeilleReveilleOrganeInactifs().iterator().next()).setRvroiedsName(es.getEdsRobustSupply().getRsedsUseCaseName());
        (ecst.getEdsReseauVeilleReveilleOrganeInactifs().iterator().next()).setRvroiedsTmoyIveilleMoteurNonTourant(es.getEdsRobustSupply()
                .getRsedsIVeille());
        (ecst.getEdsReseauVeilleReveilleOrganeInactifs().iterator().next()).setRvroiedsTmoyIreveilleInactif13vMoteurTourant(es.getEdsRobustSupply()
                .getRsedsIReveilleInactif());

        // Activation Nominal Current
        (ecst.getEdsCourantNominaleActivations().iterator().next()).setCnaedsName(es.getEdsRobustSupply().getRsedsUseCaseName());
        (ecst.getEdsCourantNominaleActivations().iterator().next()).setCnaedsImoyStab13Mt(es.getEdsRobustSupply().getRsedsINomStab());
        (ecst.getEdsCourantNominaleActivations().iterator().next()).setCnaedsTmoyStab13Mt(es.getEdsRobustSupply().getRsedsTNomStab());
        (ecst.getEdsCourantNominaleActivations().iterator().next()).setCnaedsImoyPireCas13Mt(es.getEdsRobustSupply().getRsedsIPirecasStab());
        (ecst.getEdsCourantNominaleActivations().iterator().next()).setCnaedsTmoyPireCas13Mt(es.getEdsRobustSupply().getRsedsTPireCas());

        // Powered on Current Table
        (ecst.getEdsCourantMiseSousTensions().iterator().next()).setCmstedsName(es.getEdsRobustSupply().getRsedsUseCaseName());
        (ecst.getEdsCourantMiseSousTensions().iterator().next()).setCmstedsTpirecasImst(es.getEdsRobustSupply().getRsedsImst());
        (ecst.getEdsCourantMiseSousTensions().iterator().next()).setCmstedsTpirecasDt(es.getEdsRobustSupply().getRsedsTmst());

        // Blocked couple current / Current Dysfunctional
        (ecst.getEdsCourantBloqueCourantDysfonctionnements().iterator().next()).setCbcdedsTitre(es.getEdsRobustSupply().getRsedsUseCaseName());
        (ecst.getEdsCourantBloqueCourantDysfonctionnements().iterator().next()).setCbcdedsTmoyIdys(es.getEdsRobustSupply().getRsedsIdysf());
        (ecst.getEdsCourantBloqueCourantDysfonctionnements().iterator().next()).setCbcdedsTmoyTdys(es.getEdsRobustSupply().getRsedsTdysf());

        // Park mode
        if (consolidateSupply.getEdsQcf().getQcf2() == 1) {
            (ecst.getEdsModeParcs().iterator().next()).setMpedsName(es.getEdsRobustSupply().getRsedsUseCaseName());
            (ecst.getEdsModeParcs().iterator().next()).setMpedsTmoyModeParc(es.getEdsRobustSupply().getRsedsIConsoParc());
        }

        // Activation Inrush current
        (ecst.getEdsCourantAppelleActivations().iterator().next()).setCaaedsTitre(es.getEdsRobustSupply().getRsedsUseCaseName());
        (ecst.getEdsCourantAppelleActivations().iterator().next()).setCaaedsTmoyImaxPulse(es.getEdsRobustSupply().getRsedsIMax());
        (ecst.getEdsCourantAppelleActivations().iterator().next()).setCaaedsTmoyDtPulse(es.getEdsRobustSupply().getRsedsTIMax());

        // Carry over the different functioning mode's data if there are any.
        carryOverSupplyOperatingModes(es, ecst, consolidateSupply);

        editView.discardChanges();

        es.setEdsConsolidateSupply(consolidateSupply);
        consolidateSupplyList.add(editView);
        edsConsolidateSupplys.add(consolidateSupply);
        edsSupplysList.add(es);
        groundEditView.add(consolidateSupply);
        Tab t = vTSConsolide.addTab(editView, consolidateSupply + " (" + es.getEdsRobustSupply().getRsedsTbtBt() + ")");
        t.setClosable(true);
    }

    /**
     * This method check if power supply details are filled properly
     * 
     * @return check if Power supply details are filled properly
     */
    private Boolean isCompletAlim() {
        if (vTFAlimName.getValue() == null || vTFAlimName.getValue().equals("")) {
            showNotification(controller.getBundle().getString("current-conso-list-alim-modif-name"),
                    controller.getBundle().getString("alim-name-error"));
            return false;
        } else if (vCBAlimBT_TBT.getValue() == null || vCBAlimBT_TBT.getValue().equals("")) {
            showNotification(controller.getBundle().getString("current-conso-list-alim-modif-BT-TBT"),
                    controller.getBundle().getString("alim-bt-tbt-error"));
            return false;
        } else if (vLSTypedAlim.getValue() == null || vLSTypedAlim.getValue().equals("")) {
            showNotification(controller.getBundle().getString("current-conso-list-alim-modif-type"),
                    controller.getBundle().getString("alim-type-error"));
            return false;
        } else if (vCBAlimCompare.getValue() == null) {
            showNotification(controller.getBundle().getString("current-conso-list-alim-compare"),
                    controller.getBundle().getString("alim-compare-error"));
            return false;
        }
        return true;
    }

    /**
     * This method show notification
     * 
     * @param title Title of Notification
     * @param message Message of Notification
     */
    private void showNotification(String title, String message) {

        getWindow().showNotification(title, message, Notification.TYPE_ERROR_MESSAGE);
    }

    /**
     * This method check if Power supply name already exist
     * 
     * @param name Power supply name to check
     * @return check if Power supply name already exist
     */
    private boolean exist(String name) {
        for (EdsConsolidateSupply ecs : edsConsolidateSupplys) {
            if (name.equals(ecs.getCsedsSupplyName())) {
                return true;
            }

        }
        return false;
    }

    /**
     * This method removes tab
     * 
     * @param begin Start index
     * @param end End index
     */
    private void removTab(int begin, int end) {
        for (int i = end; i > begin; i--) {
            vTSConsolide.removeTab(vTSConsolide.getTab(i));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        for (ConsolidateSupplyEditView view : consolidateSupplyList) {
            if (!view.isValid()) {
                return false;
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete ()
     */
    @Override
    public Collection getAllItemsToDelete() {

        return new ArrayList(objectsToDelete);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {

        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());

        edsConsolidateCurentFormData = controller.getFormDataModel(eds, edsConsolidateCurentFormData.getClass());

        edsRobustCurentFormData = controller.getFormDataModel(eds, edsRobustCurentFormData.getClass());

        edsPrimaryFormData = controller.getFormDataModel(eds, edsPrimaryFormData.getClass());

    }

    /**
     * This method is Called when a user has pressed the close icon of a tab in the client side widget.
     * 
     * @param tabsheet the TabSheet to which the tab belongs to
     * @param tabContent the component that corresponds to the tab whose close button was clicked
     */
    public void onTabClose(final TabSheet tabsheet, final Component tabContent) {
        final ConsolidateSupplyEditView view = (ConsolidateSupplyEditView) tabContent;
        ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("pop-warning-title"),
                MessageFormat.format(controller.getBundle().getString("alim-delete"), view), controller.getBundle().getString("eds-delete-btn"),
                controller.getBundle().getString("button-cancel"), new ConfirmDialog.Listener() {
                    public void onClose(ConfirmDialog cd) {
                        if (cd.isConfirmed()) {
                            EdsSupply supplyToRemove = view.getEdsSupply();
                            // Power supply renewed
                            if (edsRobustCurentFormData.getEdsSupplies().contains(supplyToRemove)) {
                                objectsToDelete.add(supplyToRemove.getEdsConsolidateSupply());
                                supplyToRemove.setEdsConsolidateSupply(null);
                                objectsToSave.add(supplyToRemove);
                            } else {
                                supplyToRemove.setEdsPrimarySupply(null);
                                supplyToRemove.setEdsRobustSupply(null);
                                objectsToDelete.add(supplyToRemove);
                            }
                            consolidateSupplyList.remove(view);
                            edsSupplysList.remove(view.getEdsSupply());
                            tabsheet.removeComponent(tabContent);

                        }
                    }
                });

    }

    /**
     * This method get the tab count for power supply
     * 
     * @param name Name of power supply
     * @return tabs count
     */
    private int getAlim(String name) {
        int n = 0;
        for (ConsolidateSupplyEditView view : consolidateSupplyList) {
            if (view.toString().equals(name)) {
                n = vTSConsolide.getTabPosition(vTSConsolide.getTab(view));
                return n;
            }
        }

        return n;
    }

    /**
     * Carries over the data of the different operating modes of a supply and creates views accordingly if at least one data is provided in the
     * previews stage (Robust).
     * 
     * @param es EdsSupply
     * @param ecst EdsConsolidateSupplyTheoritic
     */
    private void carryOverSupplyOperatingModes(EdsSupply es, EdsConsolidateSupplyTheoritic ecst, EdsConsolidateSupply consolidateSupply) {
        // Fetch the data from Robuste Stage by use case
        for (EdsRobustSupplyUseCase ersuc : es.getEdsRobustSupply().getUseCases()) {

            // Generic Data : Tensions Table
            Float uMin = ersuc.getRsucUMin();
            Float uMoy = ersuc.getRsucUMoy();
            Float uMax = ersuc.getRsucUMax();

            if (uMin != null || uMoy != null || uMax != null) {
                ConsolidateSupplyEdsTension csEdsU = new ConsolidateSupplyEdsTension();
                csEdsU.setOperatingModeName(ersuc.getRsucName());
                csEdsU.setCsEdsUmin(uMin);
                csEdsU.setCsEdsUmoy(uMoy);
                csEdsU.setCsEdsUmax(uMax);
                ecst.getConsolidateSupplyEdsTensions().add(csEdsU);
            }

            // Add data to asleep or awake and inactive device block
            Float iAsleep = ersuc.getRsucIVeille();
            Float iAwakeNonFunctioningMode = ersuc.getRsucIReveilInactif();

            if (iAsleep != null || iAwakeNonFunctioningMode != null) {
                EdsReseauVeilleReveilleOrganeInactif asleepAwakeNetworkInactvieDevice = new EdsReseauVeilleReveilleOrganeInactif(UUID.randomUUID()
                        .toString());
                asleepAwakeNetworkInactvieDevice.setRvroiedsName(ersuc.getRsucName());
                asleepAwakeNetworkInactvieDevice.setRvroiedsTmoyIveilleMoteurNonTourant(iAsleep);
                asleepAwakeNetworkInactvieDevice.setRvroiedsTmoyIreveilleInactif13vMoteurTourant(iAwakeNonFunctioningMode);
                ecst.getEdsReseauVeilleReveilleOrganeInactifs().add(asleepAwakeNetworkInactvieDevice);
            }

            // Add data to the Activation Nominal Current block
            Float iNomStab = ersuc.getRsucINomStab();
            Float tNomStab = ersuc.getRsucTNomStab();
            Float iWorstCaseStab = ersuc.getRsucIPirecasStab();
            Float tWorstCaseStab = ersuc.getRsucTPireCas();

            if (iNomStab != null || iWorstCaseStab != null || tNomStab != null || tWorstCaseStab != null) {
                EdsCourantNominaleActivation activationNominalCurrent = new EdsCourantNominaleActivation(UUID.randomUUID().toString());

                activationNominalCurrent.setCnaedsName(ersuc.getRsucName());
                activationNominalCurrent.setCnaedsImoyStab13Mt(iNomStab);
                activationNominalCurrent.setCnaedsTmoyStab13Mt(tNomStab);
                activationNominalCurrent.setCnaedsImoyPireCas13Mt(iWorstCaseStab);
                activationNominalCurrent.setCnaedsTmoyPireCas13Mt(tWorstCaseStab);
                ecst.getEdsCourantNominaleActivations().add(activationNominalCurrent);
            }

            if (consolidateSupply.getEdsQcf().getQcf2() == 1) {
                Float iConsoParc = ersuc.getRsucIConsoParc();
                if (iConsoParc != null) {
                    EdsModeParc edsModeParc = new EdsModeParc(UUID.randomUUID().toString());
                    edsModeParc.setMpedsName(ersuc.getRsucName());
                    edsModeParc.setMpedsTmoyModeParc(iConsoParc);
                    ecst.getEdsModeParcs().add(edsModeParc);
                }

            }

            // Activation Inrush current
            Float iMax = ersuc.getRsucIMax();
            Float tMax = ersuc.getRsucTIMax();
            if (iMax != null || tMax != null) {
                EdsCourantAppelleActivation activationInrushCurrent = new EdsCourantAppelleActivation(UUID.randomUUID().toString());
                activationInrushCurrent.setCaaedsTitre(ersuc.getRsucName());
                activationInrushCurrent.setCaaedsTmoyImaxPulse(iMax);
                activationInrushCurrent.setCaaedsTmoyDtPulse(tMax);
                ecst.getEdsCourantAppelleActivations().add(activationInrushCurrent);
            }

            // Powered on Current Table
            Float iOnPlugging = ersuc.getRsucImst();
            Float tOnPlugging = ersuc.getRsucTmst();
            if (iOnPlugging != null || tOnPlugging != null) {

                EdsCourantMiseSousTension currentOnPlugging = new EdsCourantMiseSousTension(UUID.randomUUID().toString());
                currentOnPlugging.setCmstedsName(ersuc.getRsucName());
                currentOnPlugging.setCmstedsTpirecasImst(iOnPlugging);
                currentOnPlugging.setCmstedsTpirecasDt(tOnPlugging);
                ecst.getEdsCourantMiseSousTensions().add(currentOnPlugging);
            }

            // Add data to the Blocked couple current/Dysfubctional current Table
            Float iDysfunctional = ersuc.getRsucIdysf();
            Float tDisfunctional = ersuc.getRsucTdysf();
            if (iDysfunctional != null || tDisfunctional != null) {
                EdsCourantBloqueCourantDysfonctionnement blockedOrDysfunctionalCurrent = new EdsCourantBloqueCourantDysfonctionnement(UUID
                        .randomUUID().toString());
                blockedOrDysfunctionalCurrent.setCbcdedsTitre(ersuc.getRsucName());
                blockedOrDysfunctionalCurrent.setCbcdedsTmoyIdys(iDysfunctional);
                blockedOrDysfunctionalCurrent.setCbcdedsTmoyTdys(tDisfunctional);
                ecst.getEdsCourantBloqueCourantDysfonctionnements().add(blockedOrDysfunctionalCurrent);
            }

        }
    }
}
