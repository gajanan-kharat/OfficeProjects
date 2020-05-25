/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.inetpsa.eds.application.content.eds.currentconsumption.preliminary;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.vaadin.dialogs.ConfirmDialog;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver.SupplyDriftController;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsDriftInfo;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPrimaryCurent;
import com.inetpsa.eds.dao.model.EdsPrimarySupply;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.upload.UploadPicture;
import com.vaadin.data.util.ListSet;
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
import com.vaadin.ui.Window.Notification;

/**
 * This class provide component for editing view of Current consumption of preliminary stage
 * 
 * @author Geometric Ltd.
 */
public class PrimaryFormEditView extends A_EdsEditForm implements TabSheet.SelectedTabChangeListener, TabSheet.CloseHandler {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     * @param eds object of EdsEds
     * @param edsPrimaryFormData Object of EdsPrimaryCurent
     * @param edsRobustCurentFormData Object of EdsRobustCurentFormData
     */
    public PrimaryFormEditView(EdsApplicationController controller, EdsEds eds, EdsPrimaryCurent edsPrimaryFormData,
            EdsRobustCurentFormData edsRobustCurentFormData) {
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
     * Variable to hold value of list of PrimarySupplyFormEditView
     */
    private ListSet<PrimarySupplyFormEditView> primarySupplyList;
    /**
     * Variable to hold value of list of EdsSupply
     */
    private ArrayList<EdsSupply> edsSupplysList;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of UploadPicture
     */
    private UploadPicture loadOrganPatterns;
    /**
     * Variable to hold value of Tabsheet
     */
    private TabSheet vTSPrimary;
    /**
     * Variable to hold value of GridLayout
     */
    private GridLayout vGLPrimary;
    /**
     * Variable to hold ListSelect for power supply type
     */
    private ListSelect vLSAlimType;
    /**
     * Variable to hold value of Label for power supply name
     */
    private Label vLAlimName;
    /**
     * Variable to hold value of TextField for power supply name
     */
    private TextField vTFAlimName;
    /**
     * Variable to hold value of Label for BT/TBT
     */
    private Label vLBT_TBT;
    /**
     * Variable to hold value of Combo box for BT and TBT
     */
    private ComboBox vCBBT_TBT;
    /**
     * Variable to hold value of Button to add power supply
     */
    private Button vBAddAlim;
    /**
     * Variable to hold value of TextArea for comment
     */
    private TextArea vTAComment;
    /**
     * Constant to hold value of String array for BT_TBT
     */
    private static final String[] BT_TBT = new String[] { "BT", "TBT" };

    /**
     * Initialize Edit view for current consumption of preliminary stage
     */
    private void init() {
        vTSPrimary = new TabSheet();
        vTSPrimary.setWidth("100%");

        vGLPrimary = new GridLayout(4, 5);
        vGLPrimary.setWidth("100%");
        vGLPrimary.setSpacing(true);
        vGLPrimary.setMargin(true);

        vGLPrimary.setColumnExpandRatio(0, 0f);
        vGLPrimary.setColumnExpandRatio(1, 0f);
        vGLPrimary.setColumnExpandRatio(2, 0f);
        vGLPrimary.setColumnExpandRatio(3, 1f);

        vGLPrimary.setRowExpandRatio(0, 0f);
        vGLPrimary.setRowExpandRatio(1, 0f);
        vGLPrimary.setRowExpandRatio(2, 0f);
        vGLPrimary.setRowExpandRatio(3, 0f);
        vGLPrimary.setRowExpandRatio(4, 1f);

        vLSAlimType = new ListSelect(controller.getBundle().getString("current-conso-list-alim-modif-type"));
        vLSAlimType.setRequired(true);
        vLSAlimType.setWidth("150");
        vLSAlimType.setHeight("300");
        vGLPrimary.addComponent(vLSAlimType, 0, 0, 0, 3);

        vLAlimName = new Label(controller.getBundle().getString("current-conso-list-alim-modif-name") + " : ");
        vLAlimName.setWidth("200");
        vGLPrimary.addComponent(vLAlimName, 1, 0);

        vTFAlimName = new TextField();
        vTFAlimName.setWidth("200");
        vGLPrimary.addComponent(vTFAlimName, 2, 0);

        // differentiate the filename between primary and robust (-pc)
        loadOrganPatterns = new UploadPicture(eds.getEdsId(), "organ-pattern-pc", controller);
        loadOrganPatterns.setWidth("100%");
        vGLPrimary.addComponent(loadOrganPatterns, 3, 0, 3, 4);

        vLBT_TBT = new Label(controller.getBundle().getString("current-conso-list-alim-modif-BT-TBT"));
        vGLPrimary.addComponent(vLBT_TBT, 1, 1);

        vCBBT_TBT = new ComboBox();
        vCBBT_TBT.setWidth("200");
        for (int i = 0; i < BT_TBT.length; i++) {
            vCBBT_TBT.addItem(BT_TBT[i]);
        }
        vGLPrimary.addComponent(vCBBT_TBT, 2, 1);

        vBAddAlim = new Button(controller.getBundle().getString("current-conso-list-alim-BT-button"));
        vBAddAlim.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                AddNewAlim();
            }
        });
        vGLPrimary.addComponent(vBAddAlim, 2, 2);

        vTAComment = new TextArea(controller.getBundle().getString("eds-comnent"));
        vTAComment.setWidth("100%");
        vTAComment.setHeight("150");
        vGLPrimary.addComponent(vTAComment, 1, 3, 2, 3);

        vGLPrimary.setComponentAlignment(vTAComment, Alignment.BOTTOM_LEFT);

        vTSPrimary.addListener(this);
        vTSPrimary.setCloseHandler(this);
        vTSPrimary.addTab(vGLPrimary, controller.getBundle().getString("current-conso-list-alim-modif-manage"));

        vTFAlimName.setRequired(true);
        vCBBT_TBT.setRequired(true);
        addComponent(vTSPrimary);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {

        primarySupplyList = new ListSet<PrimarySupplyFormEditView>();
        edsSupplysList = new ListSet<EdsSupply>();

        for (EdsWording wording : EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.ALIM)) {
            vLSAlimType.addItem(wording);
            vLSAlimType.setItemCaption(wording, wording.getValueByLocale(controller.getApplication().getLocale()));
        }

        File ressourceDeLimage = new File(controller.getImageFilePath(edsPrimaryFormData.getPcImage()));
        if (ressourceDeLimage.exists()) {

            loadOrganPatterns.addImage(new FileResource(ressourceDeLimage, getApplication()), edsPrimaryFormData.getPcImage());
        }

        removTab(0, vTSPrimary.getComponentCount() - 1);
        for (EdsSupply es : edsPrimaryFormData.getEdsSupplies()) {
            PrimarySupplyFormEditView editView = new PrimarySupplyFormEditView(controller, es, es.getEdsPrimarySupply());

            primarySupplyList.add(editView);
            edsSupplysList.add(es);
            Tab t = vTSPrimary.addTab(editView, es.getEdsPrimarySupply() + "(" + es.getEdsPrimarySupply().getPsedsTbtBt() + ")");
            t.setClosable(true);
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
            int i = 1;
            for (PrimarySupplyFormEditView view : primarySupplyList) {
                view.getEdsPrimarySupply().setPsRank(i);
                view.commitChanges();
                i++;
            }

            edsPrimaryFormData.setPcComment(vTAComment.getValue() == null ? "" : vTAComment.getValue().toString());

            edsPrimaryFormData.setPcImage(loadOrganPatterns.getImName());

            edsPrimaryFormData.setEdsSupplies(new HashSet(edsSupplysList));

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
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
        List list = new ArrayList();
        list.add(edsPrimaryFormData);
        list.add(eds);
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        for (PrimarySupplyFormEditView view : primarySupplyList) {
            if (!view.isValid()) {
                return false;
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {

        eds = EDSdao.getInstance().getEdsByRef(eds.getEdsRef());

        edsPrimaryFormData = controller.getFormDataModel(eds, edsPrimaryFormData.getClass());
    }

    /**
     * This method is Called when a user has pressed the close icon of a tab in the client side widget.
     * 
     * @param tabsheet the TabSheet to which the tab belongs to
     * @param tabContent the component that corresponds to the tab whose close button was clicked
     */
    public void onTabClose(final TabSheet tabsheet, final Component tabContent) {
        final PrimarySupplyFormEditView view = (PrimarySupplyFormEditView) tabContent;
        // Check that the power is not extended in the following stages
        if (view.getEdsSupply().getEdsRobustSupply() != null) {
            controller.getUserWindow().showError(controller.getBundle().getString("delete-supply-cons-title"),
                    controller.getBundle().getString("delete-supply-rob-mess"));
        } else {
            // Check that the power is not referenced in the following stages

            boolean isReferenced = EDSdao.getInstance().isPreliminarySupplyReferenced(view.getEdsPrimarySupply(), view.getEdsSupply());

            if (isReferenced) {
                controller.getUserWindow().showError(controller.getBundle().getString("delete-supply-cons-title"),
                        controller.getBundle().getString(" delete-supply-cons-delete-next"));
            } else {
                ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("pop-warning-title"),
                        MessageFormat.format(controller.getBundle().getString("alim-delete"), view),
                        controller.getBundle().getString("eds-delete-btn"), controller.getBundle().getString("button-cancel"),
                        new ConfirmDialog.Listener() {
                            public void onClose(ConfirmDialog cd) {
                                if (cd.isConfirmed()) {
                                    tabsheet.removeComponent(tabContent);
                                    edsSupplysList.remove(view.getEdsSupply());
                                }
                            }
                        });
            }
        }
    }

    /**
     * This method is called when Selected (shown) tab in tab sheet has has been changed.
     * 
     * @param event the selected tab change event.
     */
    public void selectedTabChange(SelectedTabChangeEvent event) {
    }

    /**
     * This method will add new power supply
     */
    private void AddNewAlim() {
        if (isValidAlim()) {

            if (exist((String) vTFAlimName.getValue())) {
                showNotification("", MessageFormat.format(controller.getBundle().getString("alim-exist"), (String) vTFAlimName.getValue()));
                return;
            }

            // Preliminary
            EdsPrimarySupply edsPrimarySupply = new EdsPrimarySupply(UUID.randomUUID().toString());
            edsPrimarySupply.setPsedsSupplyName(vTFAlimName.getValue().toString());
            edsPrimarySupply.setPsedsTbtBt(vCBBT_TBT.getValue().toString());
            edsPrimarySupply.setPsedsSupplyNameIformBy(controller.getAuthenticatedUser().getUFirstname() + " "
                    + controller.getAuthenticatedUser().getULastname());
            edsPrimarySupply.setPsedsRef(EDSdao.getInstance().generateAlimRef());
            edsPrimarySupply.setWording((EdsWording) vLSAlimType.getValue());

            EdsSupply edsSupplyJoin = new EdsSupply(UUID.randomUUID().toString(), eds, null, null, edsPrimarySupply, null, vTFAlimName.getValue()
                    .toString(), null);

            PrimarySupplyFormEditView primaryForm = new PrimarySupplyFormEditView(controller, edsSupplyJoin, edsPrimarySupply);

            Tab t = vTSPrimary.addTab(primaryForm, (String) vTFAlimName.getValue() + "(" + (String) vCBBT_TBT.getValue() + ")");
            t.setClosable(true);
            primarySupplyList.add(primaryForm);
            edsSupplysList.add(edsSupplyJoin);
        }
    }

    /**
     * This method checks if power supply added is valid
     * 
     * @return checks if power supply added is valid
     */
    private Boolean isValidAlim() {
        if (vTFAlimName.getValue() == null || vTFAlimName.getValue().equals("")) {
            showNotification(controller.getBundle().getString("current-conso-list-alim-modif-name"),
                    controller.getBundle().getString("alim-name-error"));
            return false;
        } else if (vCBBT_TBT.getValue() == null || vCBBT_TBT.getValue().equals("")) {
            showNotification(controller.getBundle().getString("current-conso-list-alim-modif-BT-TBT"),
                    controller.getBundle().getString("alim-bt-tbt-error"));
            return false;
        } else if (vLSAlimType.getValue() == null || vLSAlimType.getValue().equals("")) {
            showNotification(controller.getBundle().getString("current-conso-list-alim-modif-type"),
                    controller.getBundle().getString("alim-type-error"));
            return false;
        }

        return true;
    }

    /**
     * This method removes the tab
     * 
     * @param begin Start index
     * @param end End index
     */
    private void removTab(int begin, int end) {
        for (int i = end; i > begin; i--) {
            vTSPrimary.removeTab(vTSPrimary.getTab(i));
        }
    }

    /**
     * This method shows notification
     * 
     * @param title Title of notification
     * @param message Message of notification
     */
    private void showNotification(String title, String message) {
        getWindow().showNotification(title, message, Notification.TYPE_ERROR_MESSAGE);
    }

    /**
     * This method checks if power supply name already present in list
     * 
     * @param name Name to check in list
     * @return checks if power supply name already present in list
     */
    private boolean exist(String name) {
        for (PrimarySupplyFormEditView view : primarySupplyList) {
            if (view.toString().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
