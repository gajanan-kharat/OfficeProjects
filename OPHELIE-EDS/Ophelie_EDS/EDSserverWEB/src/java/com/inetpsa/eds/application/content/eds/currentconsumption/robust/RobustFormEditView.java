package com.inetpsa.eds.application.content.eds.currentconsumption.robust;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.vaadin.dialogs.ConfirmDialog;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver.SupplyDriftController;
import com.inetpsa.eds.application.popup.RobustAdditionalInfos;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsDriftInfo;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsGround;
import com.inetpsa.eds.dao.model.EdsPrimaryCurent;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.inetpsa.eds.dao.model.EdsRobustSupply;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.upload.UploadPicture;
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
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.Window.Notification;

/**
 * This class provide component for editing view of Current consumption of robust stage
 * 
 * @author Geometric Ltd.
 */
public class RobustFormEditView extends A_EdsEditForm implements Property.ValueChangeListener, TabSheet.SelectedTabChangeListener,
        TabSheet.CloseHandler {
    // PRIVE
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of EdsPrimaryCurentFormData
     */
    private EdsPrimaryCurent edsPrimaryFormData;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of RobustGroundEditView
     */
    private RobustGroundEditView groundEditView;
    /**
     * Variable to hold value of list of EdsRobustSupply
     */
    private ArrayList<EdsRobustSupply> edsRobustSupplys;
    /**
     * Variable to hold value of list of EdsSupply
     */
    private ArrayList<EdsSupply> edsSupplysList;
    /**
     * Variable to hold value of List of EdsGround
     */
    private ArrayList<EdsGround> edsGroundsList;
    /**
     * Variable to hold value of List of Object to delete
     */
    private ArrayList<Object> objectsToDelete;
    /**
     * Variable to hold value of List of Object to save
     */
    private ArrayList<Object> objectsToSave;
    /**
     * Variable to hold value of List of RobustSupplyEditView
     */
    private ArrayList<RobustSupplyEditView> robustSupplyList;
    /**
     * Variable to hold value of EdsRobustCurentFormData
     */
    private EdsRobustCurentFormData edsRobustCurentFormData;
    /**
     * Variable to hold value of GridLayout
     */
    private GridLayout vGLAlimRobuste;
    /**
     * Variable to hold value of GridLayout
     */
    private GridLayout vGLAlimDest;
    /**
     * Variable to hold value of Tabsheet
     */
    private TabSheet vTSRobuste;
    /**
     * Variable to hold ListSelect for power supply type
     */
    private ListSelect vLSTypedAlim;
    /**
     * Variable to hold value of Label for power supply name
     */
    private Label vLAlimName;
    /**
     * Variable to hold value of TextField for power supply name
     */
    private TextField vTFAlimName;
    /**
     * Variable to hold value of Label for power supply comparison
     */
    private Label vLAlimCompare;
    /**
     * Variable to hold value of Combo box for power supply name comparison
     */
    private ComboBox vCBAlimCompare;
    /**
     * Variable to hold value of Label for BT_TBT
     */
    private Label vLAlimBT_TBT;
    /**
     * Variable to hold value of Cobo box for BT_TBT
     */
    private ComboBox vCBAlimBT_TBT;
    /**
     * Variable to hold value of ImagePanel
     */
    private UploadPicture VIPRobuste;
    /**
     * Variable to hold value of Button to add power supply
     */
    private Button vBAddAlim;
    /**
     * Variable to hold value of TwinColSelect to renew power supply
     */
    private TwinColSelect vTSRenewAlim;
    /**
     * Variable to hold value of Button to renew power supply
     */
    private Button vBRenewAlim;
    /**
     * Variable to hold value of TextArea for comment
     */
    private TextArea vTAComment;
    /**
     * Variable to hold value of Label for Destination power supply
     */
    private Label vLAlimDist;
    /**
     * Variable to hold value of Combo box for Destination power supply
     */
    private ComboBox vCBAlimDist;
    /**
     * Variable to hold value of Label for voltage name
     */
    private Label vLAlimU;
    /**
     * Variable to hold value of TextField for voltage name
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
     * @param eds object of EdsEds
     * @param edsRobustCurentFormData Object of EdsRobustCurentFormData
     * @param edsPrimaryFormData Object of EdsPrimaryCurent
     * @param controller Controller of EDS application
     */
    public RobustFormEditView(EdsEds eds, EdsRobustCurentFormData edsRobustCurentFormData, EdsPrimaryCurent edsPrimaryFormData,
            EdsApplicationController controller) {
        this.eds = eds;
        this.edsRobustCurentFormData = edsRobustCurentFormData;
        this.edsPrimaryFormData = edsPrimaryFormData;
        this.controller = controller;

        init();
    }

    /**
     * Initialize Edit view for current consumption of robust stage
     */
    private void init() {
        this.objectsToDelete = new ArrayList<Object>();
        this.objectsToSave = new ArrayList<Object>();
        this.robustSupplyList = new ArrayList<RobustSupplyEditView>();
        this.edsRobustSupplys = new ArrayList<EdsRobustSupply>();
        this.edsSupplysList = new ArrayList<EdsSupply>();
        this.edsGroundsList = new ArrayList<EdsGround>();

        this.user = controller.getAuthenticatedUser().getUFirstname() + " " + controller.getAuthenticatedUser().getULastname();

        vTSRobuste = new TabSheet();
        vTSRobuste.setWidth("100%");

        vGLAlimRobuste = new GridLayout(4, 12);
        vGLAlimRobuste.setColumnExpandRatio(0, 0f);
        vGLAlimRobuste.setColumnExpandRatio(1, 0f);
        vGLAlimRobuste.setColumnExpandRatio(2, 0f);
        vGLAlimRobuste.setColumnExpandRatio(3, 1f);

        vGLAlimRobuste.setWidth("100%");
        vGLAlimRobuste.setSpacing(true);
        vGLAlimRobuste.setMargin(true);
        vTSRobuste.addTab(vGLAlimRobuste, controller.getBundle().getString("current-conso-list-alim-modif-manage"));

        Label title1 = new Label(controller.getBundle().getString("current-conso-list-alim-recond-title"));
        title1.addStyleName("h2");
        title1.setWidth("250");
        vGLAlimRobuste.addComponent(title1, 0, 0, 2, 0);

        vTSRenewAlim = new TwinColSelect();
        vTSRenewAlim.setWidth("100%");
        vTSRenewAlim.setLeftColumnCaption(controller.getBundle().getString("current-conso-list-alim-recond-left"));
        vTSRenewAlim.setRightColumnCaption(controller.getBundle().getString("current-conso-list-alim-recond-title"));
        vGLAlimRobuste.addComponent(vTSRenewAlim, 0, 1, 2, 1);

        vBRenewAlim = new Button(controller.getBundle().getString("current-conso-list-alim-recond-button"));
        vBRenewAlim.addListener(new Button.ClickListener() {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui. Button.ClickEvent)
             */
            public void buttonClick(Button.ClickEvent event) {
                reconduire();
            }
        });
        vGLAlimRobuste.addComponent(vBRenewAlim, 2, 2);
        vGLAlimRobuste.setComponentAlignment(vBRenewAlim, Alignment.TOP_RIGHT);

        Label title2 = new Label(controller.getBundle().getString("current-conso-list-alim-new"), Label.CONTENT_XHTML);
        title2.addStyleName("h2");
        vGLAlimRobuste.addComponent(title2, 0, 3, 2, 3);

        vLSTypedAlim = new ListSelect(controller.getBundle().getString("current-conso-list-alim-modif-type"));
        vLSTypedAlim.setWidth("150");
        vLSTypedAlim.setHeight("100%");
        vGLAlimRobuste.addComponent(vLSTypedAlim, 0, 4, 0, 8);

        vLAlimName = new Label(controller.getBundle().getString("current-conso-list-alim-modif-name") + " :");
        vLAlimName.setWidth("250");
        vGLAlimRobuste.addComponent(vLAlimName, 1, 4);

        vTFAlimName = new TextField();
        vTFAlimName.setWidth("200");
        vGLAlimRobuste.addComponent(vTFAlimName, 2, 4);

        vLAlimCompare = new Label(controller.getBundle().getString("current-conso-list-alim-compare") + ":");
        vGLAlimRobuste.addComponent(vLAlimCompare, 1, 5);

        vCBAlimCompare = new ComboBox();
        vCBAlimCompare.setWidth("200");

        vGLAlimRobuste.addComponent(vCBAlimCompare, 2, 5);

        vLAlimBT_TBT = new Label(controller.getBundle().getString("current-conso-list-alim-modif-BT-TBT"));
        vGLAlimRobuste.addComponent(vLAlimBT_TBT, 1, 6);

        vCBAlimBT_TBT = new ComboBox();
        vCBAlimBT_TBT.setImmediate(true);
        vCBAlimBT_TBT.setWidth("200");
        vCBAlimBT_TBT.addListener(this);
        for (int i = 0; i < BT_TBT.length; i++) {
            vCBAlimBT_TBT.addItem(BT_TBT[i]);
        }

        vGLAlimRobuste.addComponent(vCBAlimBT_TBT, 2, 6);

        vBAddAlim = new Button(controller.getBundle().getString("current-conso-list-alim-BT-button"));
        vBAddAlim.addListener(new Button.ClickListener() {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui. Button.ClickEvent)
             */
            public void buttonClick(Button.ClickEvent event) {
                addNewAlim();
            }
        });
        vGLAlimRobuste.addComponent(vBAddAlim, 2, 8);
        vGLAlimRobuste.setComponentAlignment(vBAddAlim, Alignment.MIDDLE_RIGHT);

        // Destination organ
        vGLAlimDest = new GridLayout(2, 2);
        vGLAlimDest.setWidth("100%");
        vGLAlimDest.setSpacing(true);
        vGLAlimRobuste.addComponent(vGLAlimDest, 1, 7, 2, 7);

        vLAlimDist = new Label(controller.getBundle().getString("current-conso-list-alim-BT-dest"));
        vLAlimDist.setWidth("210");
        vGLAlimDest.addComponent(vLAlimDist, 0, 0);
        vCBAlimDist = new ComboBox();
        vCBAlimDist.setWidth("200");
        for (int i = 0; i < ORGANE_DIST.length; i++) {
            vCBAlimDist.addItem(ORGANE_DIST[i]);
        }
        vGLAlimDest.addComponent(vCBAlimDist, 1, 0);
        vLAlimU = new Label(controller.getBundle().getString("current-conso-list-alim-BT-intitul"));

        vGLAlimDest.addComponent(vLAlimU, 0, 1);

        vTFAlimU = new TextField();
        vTFAlimU.setWidth("200");
        vGLAlimDest.addComponent(vTFAlimU, 1, 1);

        vGLAlimDest.setVisible(false);

        /**
         * *************************************
         */

        // differentiate the filename between primary and robust (-rc)
        VIPRobuste = new UploadPicture(eds.getEdsId(), "organ-pattern-rc", controller);
        VIPRobuste.setHeight("100%");
        vGLAlimRobuste.addComponent(VIPRobuste, 3, 0, 3, 11);

        vTAComment = new TextArea();
        vTAComment.setInputPrompt(controller.getBundle().getString("generic-data-com-no-com"));
        vTAComment.setMaxLength(2048);
        vTAComment.setWidth("100%");
        vTAComment.setHeight("150");
        vGLAlimRobuste.addComponent(vTAComment, 0, 9, 2, 9);

        vTSRobuste.addListener(this);
        vTSRobuste.setCloseHandler(this);

        groundEditView = new RobustGroundEditView(controller, edsRobustCurentFormData, eds);

        vTSRobuste.addTab(groundEditView, controller.getBundle().getString("current-conso-tab-mass-rob-title"));

        vTFAlimName.setRequired(true);
        vCBAlimCompare.setRequired(true);
        vCBAlimBT_TBT.setRequired(true);
        vCBAlimDist.setRequired(true);

        addComponent(vTSRobuste);

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
        robustSupplyList.clear();
        edsRobustSupplys.clear();
        edsSupplysList.clear();
        edsGroundsList.clear();

        for (EdsWording wording : EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.ALIM)) {
            vLSTypedAlim.addItem(wording);
            vLSTypedAlim.setItemCaption(wording, wording.getValueByLocale(controller.getApplication().getLocale()));

        }

        vTAComment.setValue(edsRobustCurentFormData.getRcComment() == null ? "" : edsRobustCurentFormData.getRcComment());

        // In edit view we don't show the primary stage image even if a robust stage image is not set.
        File ressourceDeLimage = new File(controller.getImageFilePath(edsRobustCurentFormData.getRcImage()));
        if (ressourceDeLimage.exists()) {
            VIPRobuste.addImage(new FileResource(ressourceDeLimage, getApplication()), edsRobustCurentFormData.getRcImage());
        }

        removTab(1, vTSRobuste.getComponentCount() - 1);
        vCBAlimCompare.removeAllItems();
        vTSRenewAlim.removeAllItems();
        for (EdsSupply es : edsPrimaryFormData.getEdsSupplies()) {
            vCBAlimCompare.addItem(es);
            vTSRenewAlim.addItem(es);
        }

        groundEditView.removeAllRobusteSuplys();
        for (EdsSupply es : edsRobustCurentFormData.getEdsSupplies()) {

            RobustSupplyEditView supplyEditView = new RobustSupplyEditView(controller, es.getEdsPrimarySupply(), es.getEdsRobustSupply(), es, false);
            if (es.getEdsRobustSupply() != null) {
                Tab t = vTSRobuste.addTab(supplyEditView, es + "(" + es.getEdsRobustSupply().getRsedsTbtBt() + ")");
                t.setClosable(true);
                robustSupplyList.add(supplyEditView);
                edsSupplysList.add(es);
                groundEditView.addRobusteSuplys(es.getEdsRobustSupply());

            }

        }

        for (EdsGround es : edsRobustCurentFormData.getEdsGrounds()) {
            edsGroundsList.add(es);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        if (isValid()) {
            for (RobustSupplyEditView view : robustSupplyList) {
                view.commitChanges();
            }

            edsRobustCurentFormData.setRcComment(vTAComment.getValue().toString());

            groundEditView.commitChanges();

            edsRobustCurentFormData.getEdsGrounds().clear();
            edsRobustCurentFormData.getEdsGrounds().addAll(groundEditView.getEdsGroundsList());

            edsRobustCurentFormData.getEdsSupplies().clear();
            edsRobustCurentFormData.getEdsSupplies().addAll(edsSupplysList);

            edsRobustCurentFormData.setRcChoixMasse(groundEditView.getchoixMasse());

            edsRobustCurentFormData.setRcImage(VIPRobuste.getImName());

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
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
        List list = new ArrayList();
        list.add(edsRobustCurentFormData);
        list.addAll(objectsToSave);
        list.add(eds);
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        for (RobustSupplyEditView view : robustSupplyList) {
            if (!view.isValid()) {
                return false;
            }
        }
        if (!groundEditView.isValid()) {
            return false;
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
        ArrayList<Object> toDelete = new ArrayList<Object>(objectsToDelete);

        for (RobustSupplyEditView supply : robustSupplyList)
            toDelete.addAll(supply.getAllItemsToDelete());

        return toDelete;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {

        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());

        edsRobustCurentFormData = controller.getFormDataModel(eds, edsRobustCurentFormData.getClass());
        edsPrimaryFormData = controller.getFormDataModel(eds, edsPrimaryFormData.getClass());
    }

    /**
     * This method add new Power supply tab
     */
    private void addNewAlim() {
        if (!isValidAlim()) {
            return;

        }

        if (getAlim(vTFAlimName.getValue().toString()) != 0) {
            showNotification("", MessageFormat.format(controller.getBundle().getString("alim-exist"), (String) vTFAlimName.getValue()));

        } else {
            EdsSupply es = (EdsSupply) vCBAlimCompare.getValue();

            RobustSupplyEditView robustForm = null;
            EdsRobustSupply robustSupply = new EdsRobustSupply(UUID.randomUUID().toString());
            robustSupply.setRsedsRef(EDSdao.getInstance().generateAlimRef());
            robustSupply.setRsedsUseCaseName("-"); // Default use case value
            robustSupply.setRsedsSupplyName(vTFAlimName.getValue().toString());
            robustSupply.setRsedsTbtBt(vCBAlimBT_TBT.getValue().toString());
            robustSupply.setWording((EdsWording) vLSTypedAlim.getValue());
            robustSupply.setRsedsSupplyNameIformBy(user);
            robustSupply.setRsedsSupplyTypeSupplyNameIformBy(user);
            robustSupply.setRsedsOrgane((String) vCBAlimDist.getValue());
            robustSupply.setRsedsUcaracName(vTFAlimU.getValue().toString());

            EdsSupply edsAlimentationJointure = new EdsSupply(UUID.randomUUID().toString(), eds, null, robustSupply, es.getEdsPrimarySupply(), null,
                    vTFAlimName.getValue().toString(), es.getSedsDriftComment());

            robustSupply.setRsedsUcaracName(vTFAlimU.getValue().toString());
            robustForm = new RobustSupplyEditView(controller, null, robustSupply, edsAlimentationJointure, false);

            robustSupplyList.add(robustForm);
            edsSupplysList.add(edsAlimentationJointure);
            edsRobustSupplys.add(robustSupply);
            Tab alimentationajouter = vTSRobuste.addTab(robustForm, robustSupply.toString());

            alimentationajouter.setClosable(true);
        }

    }

    /**
     * This method is used to renew Power supply
     */
    private void reconduire() {

        Collection<EdsSupply> edsSupplys = (Collection<EdsSupply>) vTSRenewAlim.getValue();
        for (final EdsSupply alim : edsSupplys) {
            final int i = getAlim(alim.toString());
            if (i != 0) {
                final int index = robustSupplyList.indexOf((RobustSupplyEditView) vTSRobuste.getTab(i).getComponent());

                ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("pop-warning-title"), controller.getBundle()
                        .getString("alim-reconduct-error"), controller.getBundle().getString("consolid-qcf-oui"),
                        controller.getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                            public void onClose(ConfirmDialog cd) {
                                if (cd.isConfirmed()) {
                                    edsRobustSupplys.remove(alim.getEdsRobustSupply());
                                    edsSupplysList.remove(alim);
                                    robustSupplyList.remove(index);
                                    vTSRobuste.removeTab(vTSRobuste.getTab(i));
                                    reconduire(alim);
                                }
                            }
                        });
            } else {

                reconduire(alim);
            }
        }
    }

    /**
     * This method renew the specified power supply
     * 
     * @param es EdsSupply object
     */
    private void reconduire(final EdsSupply es) {
        final EdsRobustSupply robustSupply = new EdsRobustSupply(UUID.randomUUID().toString());
        robustSupply.setRsedsTbtBt(es.getEdsPrimarySupply().getPsedsTbtBt());
        robustSupply.setRsedsSupplyName(es.getEdsPrimarySupply().getPsedsSupplyName());
        robustSupply.setRsedsRef(es.getEdsPrimarySupply().getPsedsRef());
        robustSupply.setRsedsUseCaseName("-");
        es.setEdsRobustSupply(robustSupply);
        if ("BT".equals(es.getEdsPrimarySupply().getPsedsTbtBt())) {
            RobustAdditionalInfos additionalInfos = new RobustAdditionalInfos(controller) {
                public void buttonClick(Button.ClickEvent event) {
                    if (this.isValid()) {
                        robustSupply.setRsedsUcaracName(this.getUcaracName());
                        robustSupply.setRsedsOrgane(this.getOrgane());
                        this.close();
                        createView(es);
                    }
                }
            };
            additionalInfos.show();
        } else {
            createView(es);
        }

    }

    /**
     * This method create view for specified power supply
     * 
     * @param es Object of EdsSupply
     */
    private void createView(EdsSupply es) {
        RobustSupplyEditView robustForm = new RobustSupplyEditView(controller, es.getEdsPrimarySupply(), es.getEdsRobustSupply(), es, true);

        robustSupplyList.add(robustForm);
        edsSupplysList.add(es);
        edsRobustSupplys.add(es.getEdsRobustSupply());
        // String AlimName = es.getEdsPrimarySupply().getPsedsSupplyName() +
        // " (" + es.getEdsPrimarySupply().
        // getPsedsTbtBt() + ")";

        Tab t = vTSRobuste.addTab(robustForm, es.getEdsRobustSupply().toString());
        vTSRobuste.setSelectedTab(robustForm);
        t.setClosable(true);
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
     * This method is called Selected (shown) tab in tab sheet has has been changed.
     * 
     * @param event Selected tab change event
     */
    public void selectedTabChange(SelectedTabChangeEvent event) {
    }

    /**
     * This method is Called when a user has pressed the close icon of a tab in the client side widget.
     * 
     * @param tabsheet the TabSheet to which the tab belongs to
     * @param tabContent the component that corresponds to the tab whose close button was clicked
     */
    public void onTabClose(final TabSheet tabsheet, final Component tabContent) {
        final RobustSupplyEditView view = (RobustSupplyEditView) tabContent;

        if (view.getEdsSupply().getEdsConsolidateSupply() != null) {
            controller.getUserWindow().showError(controller.getBundle().getString("delete-supply-cons-title"),
                    controller.getBundle().getString("delete-supply-cons-mess"));
        } else {
            // Check that the power is not referenced in the following stages

            boolean isReferenced = EDSdao.getInstance().isRobustSupplyReferenced(view.getEdsRobustSupply(), view.getEdsSupply());

            if (isReferenced) {
                controller.getUserWindow().showError(controller.getBundle().getString("delete-supply-cons-title"),
                        controller.getBundle().getString("delete-supply-cons-delete-next"));
            } else {
                ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("pop-warning-title"),
                        MessageFormat.format(controller.getBundle().getString("alim-delete"), view),
                        controller.getBundle().getString("eds-delete-btn"), controller.getBundle().getString("button-cancel"),
                        new ConfirmDialog.Listener() {
                            public void onClose(ConfirmDialog cd) {
                                if (cd.isConfirmed()) {
                                    EdsSupply supplyToRemove = view.getEdsSupply();
                                    // Power Supply renewed
                                    if (edsPrimaryFormData.getEdsSupplies().contains(supplyToRemove)) {
                                        objectsToDelete.add(supplyToRemove.getEdsRobustSupply());
                                        supplyToRemove.setEdsRobustSupply(null);
                                        objectsToSave.add(supplyToRemove);
                                    }
                                    // Creates power at this stage
                                    else {
                                        supplyToRemove.setEdsPrimarySupply(null);
                                        objectsToDelete.add(supplyToRemove);
                                    }
                                    edsSupplysList.remove(view.getEdsSupply());
                                    tabsheet.removeComponent(tabContent);
                                    robustSupplyList.remove(view);
                                }
                            }
                        });
            }
        }
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
     * This method check for values entered for new power supply are valid
     * 
     * @return check if valid power supply
     */
    private Boolean isValidAlim() {
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
        } else if ("BT".equals(vCBAlimBT_TBT.getValue()) && vCBAlimDist.getValue() == null) {
            showNotification(controller.getBundle().getString("current-conso-list-alim-BT-dest"),
                    controller.getBundle().getString("current-conso-rob-rec-msg"));
            return false;
        }

        return true;
    }

    /**
     * This method get the tab count for power supply
     * 
     * @param name Name of power supply
     * @return tabs count
     */
    private int getAlim(String name) {
        int n = 0;
        for (RobustSupplyEditView view : robustSupplyList) {
            if (view.toString().equals(name)) {
                n = vTSRobuste.getTabPosition(vTSRobuste.getTab(view));
                return n;
            }
        }

        return n;
    }

    /**
     * This method removes tab
     * 
     * @param begin Start index
     * @param end End index
     */
    private void removTab(int begin, int end) {
        for (int i = end; i > begin; i--) {
            vTSRobuste.removeTab(vTSRobuste.getTab(i));
        }
    }
}
