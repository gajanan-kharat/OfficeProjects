package com.inetpsa.eds.application.content.eds.genericdata;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import com.inetpsa.dbelec.model.componentname.ComponentName;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsComponentType;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsNumber96k;
import com.inetpsa.eds.dao.model.EdsPerimeter;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsProjectEds;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.dao.model.EdsSAPReference;
import com.inetpsa.eds.dao.model.EdsSupplier;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.mail.EdsMailAdviceBuilder;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class represents 'Generic data' edit view.
 * 
 * @author Geometric Ltd.
 */
public class GenericDataFormEditView extends A_EdsEditForm {
    /** Row number for reference fct */
    private static final int ROW_REF_FCT = 18;

    /** Row number for reference fnr */
    private static final int ROW_REF_FNR = 19;

    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param controller EDS application controller object.
     */
    public GenericDataFormEditView(EdsEds eds, EdsApplicationController controller) {
        this.eds = eds;
        this.controller = controller;
        this.bundle = controller.getBundle();
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        boolean isValid = true;
        String errorMsg = "";
        for (AbstractField field : fieldsToCheck) {
            if (!field.isValid()) {
                isValid = false;
                errorMsg += "<br />" + field.getRequiredError();
            }
        }
        if (!isValid) {
            getWindow().showNotification(bundle.getString("pop-error-title"), errorMsg, Notification.TYPE_ERROR_MESSAGE);

        }
        return isValid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        if (isValid()) {
            eds.setEdsComponentType((EdsComponentType) vCMBcomponentTypeValue.getValue());
            eds.setEdsOrganFamily((EdsWording) vCMBorganFamilyValue.getValue());
            eds.setEdsAltSubFct((EdsWording) vCMBsFAltValue.getValue());
            eds.setEdsBatSubFct((EdsWording) vCMBsFBatValue.getValue());
            eds.setEdsAltSubSys((EdsWording) vCMBsSAltValue.getValue());
            eds.setEdsBatSubSys((EdsWording) vCMBsSBatValue.getValue());
            eds.setEdsComments((String) vTAcomments.getValue());
            eds.setEdsName((String) vCMBnameValue.getValue());
            eds.setEdsDescription((String) vTFdescriptionValue.getValue());
            newNumbersToSave.clear();
            eds.setEdsSubtype1((Integer) vCMBsubType1.getValue());
            eds.setEdsSubtype2((Integer) vCMBsubType2.getValue());
            eds.setEdsSupplier((EdsSupplier) vCMBsupplierValue.getValue());
            eds.setEdsUserByEdsAdminId((EdsUser) vCMBadminValue.getValue());
            eds.setEdsUserByEdsManagerId((EdsUser) vCMBmanagerValue.getValue());
            eds.setEdsUserByEdsOfficerId((EdsUser) vCMBofficerValue.getValue());
            if (eds.getEdsValidLvl() != null && !eds.getEdsValidLvl().equals(vCMBvalidationLevel.getValue())) {
                EDSdao dao = EDSdao.getInstance();
                if (eds.getEdsValidLvl() == EdsEds.VALIDATION_LVL_LOW) {

                    dao.deleteDetachedDBObject(dao.getFormData(eds.getEdsId(), EdsLowValidationFormData.class));

                } else if (eds.getEdsValidLvl() == EdsEds.VALIDATION_LVL_HIGH) {

                    dao.deleteDetachedDBObject(dao.getFormData(eds.getEdsId(), EdsHighValidationFormData.class));

                }
            }
            eds.setEdsValidLvl((Integer) vCMBvalidationLevel.getValue());
            eds.setEdsIsBttbt(getBTTBTvalue());
            eds.setEdsModifDate(new Date());
            eds.setEdsPLaunchCount(Integer.parseInt(vTFprojectSetterCount.getValue().toString()));
            for (EdsProjectEds epe : edsProjectEdsesCountMap.keySet()) {
                epe.setPedsCount(Integer.parseInt((String) edsProjectEdsesCountMap.get(epe).getValue()));
            }
            Set<EdsPerimeter> perimetersSet = new HashSet<EdsPerimeter>();
            for (ComboBox vCMB : vCMBperimeters) {
                if (vCMB.getValue() != null) {
                    perimetersSet.add((EdsPerimeter) vCMB.getValue());
                }
            }
            eds.setEdsPerimeters(perimetersSet);
            for (EdsProjectEds epe : edsProjectEdsesToDeleteList) {
                EdsApplicationController.removeAllSpecificFormData(epe.getEdsEds(), epe.getEdsProject());
            }
            eds.getEdsProjectEdses().removeAll(edsProjectEdsesToDeleteList);
            if (vCMBprojectSetter.getValue() != null) {
                eds.setEdsProject((EdsProject) vCMBprojectSetter.getValue());
            }

            eds.setEdsNumber96fcts(new HashSet<EdsSAPReference>(listSelectedSAPReferencesFCT));
            eds.setEdsNumber96fnrs(new HashSet<EdsSAPReference>(listSelectedSAPReferencesFNR));
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        addComboBoxesDefaultValues();

        vTAcomments.setValue(eds.getEdsComments());
        vCMBnameValue.setValue(eds.getEdsName());
        vTFdescriptionValue.setValue(eds.getEdsDescription());
        vCMBcomponentTypeValue.setValue(eds.getEdsComponentType());
        vCMBorganFamilyValue.setValue(eds.getEdsOrganFamily());
        vCMBsFAltValue.setValue(eds.getEdsAltSubFct());
        vCMBsFBatValue.setValue(eds.getEdsBatSubFct());
        vCMBsSAltValue.setValue(eds.getEdsAltSubSys());
        vCMBsSBatValue.setValue(eds.getEdsBatSubSys());
        if (!eds.getEdsNumber96ks().isEmpty()) {
            initNumbersValue();
        }

        vCMBsubType1.setValue(eds.getEdsSubtype1());
        vCMBsubType2.setValue(eds.getEdsSubtype2());
        vCMBsupplierValue.setValue(eds.getEdsSupplier());
        vCMBadminValue.setValue(eds.getEdsUserByEdsAdminId());
        vCMBmanagerValue.setValue(eds.getEdsUserByEdsManagerId());
        vCMBofficerValue.setValue(eds.getEdsUserByEdsOfficerId());
        vCMBvalidationLevel.setValue(eds.getEdsValidLvl());
        setBTTBTvalue(eds.getEdsIsBttbt());
        initPerimetersValue();
        initLauncherProject();
        initFollowerProjectsValue();
        GridLayout layout = (GridLayout) vCMBvalidationLevel.getParent();
        removeUnSavedValue(listSelectedSAPReferencesFCT, eds.getEdsNumber96fcts(), layout, ROW_REF_FCT);
        removeUnSavedValue(listSelectedSAPReferencesFNR, eds.getEdsNumber96fnrs(), layout, ROW_REF_FNR);

    }

    private void removeUnSavedValue(List<EdsSAPReference> listSelectedSAPReferences, Set<EdsSAPReference> savedDataSet, GridLayout layout, int row) {
        int sizeUnSaved = listSelectedSAPReferences.size();
        int sizeSaved = savedDataSet.size();

        while (sizeUnSaved != sizeSaved) {
            sizeUnSaved--;
            int index = sizeUnSaved;
            EdsSAPReference data = listSelectedSAPReferences.remove(index);
            listSAPReferences.add(data);
            vCMBsRefFCTValue.addItem(data);
            vCMBsRefFNRValue.addItem(data);

            HorizontalLayout sapReferenceLayout = (HorizontalLayout) layout.getComponent(3, row);
            index = index * 2;

            Component label = sapReferenceLayout.getComponent(index);
            Component button = sapReferenceLayout.getComponent(index + 1);
            sapReferenceLayout.removeComponent(label);
            sapReferenceLayout.removeComponent(button);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        List<Object> itemsToSave = new ArrayList<Object>(newNumbersToSave);
        itemsToSave.add(eds);
        return itemsToSave;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {

        List<Object> itemsToDelete = new ArrayList<Object>();
        return itemsToDelete;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
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
     * Accordion panel for main layout.
     */
    private Accordion accordion;
    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * List of all EDS users.
     */
    private List<EdsUser> users;
    /**
     * List of suppliers.
     */
    private List<EdsSupplier> suppliers;
    /**
     * List of component types.
     */
    private List<EdsComponentType> componentTypes;
    /**
     * List of EDS wordings.
     */
    private List<EdsWording> organsFamilies;
    /**
     * List of EDS wordings.
     */
    private List<EdsWording> subFuncAlt;
    /**
     * List of EDS wordings.
     */
    private List<EdsWording> subSysAlt;
    /**
     * List of EDS wordings.
     */
    private List<EdsWording> subFuncBat;
    /**
     * List of EDS wordings.
     */
    private List<EdsWording> subSysBat;
    /**
     * List of partners.
     */
    private List<EdsPerimeter> perimeters;
    /**
     * List of projects.
     */
    private List<EdsProject> projects;
    /**
     * List of setter projects.
     */
    private List<EdsProject> seterProjects;
    /**
     * Layout for record info panel.
     */
    private Layout recordInfoLayout;
    /**
     * Layout for contact info panel.
     */
    private Layout contactInfoLayout;
    /**
     * Layout for project info panel.
     */
    private Layout projectInfoLayout;
    /**
     * Text area for comments.
     */
    private TextArea vTAcomments;
    /**
     * Resource bundle object for retrieving properties.
     */
    private ResourceBundle bundle;
    /**
     * Pop-up window for adding 96k Part-number.
     */
    private Window subwindow;
    // record info
    /**
     * Combo box for device name.
     */
    private ComboBox vCMBnameValue;
    /**
     * Text field for device description.
     */
    private TextField vTFdescriptionValue;
    /**
     * Combo-box for validation level.
     */
    private ComboBox vCMBvalidationLevel;
    /**
     * Combo-box for sub-type 1.
     */
    private ComboBox vCMBsubType1;
    /**
     * Combo-box for sub-type 2.
     */
    private ComboBox vCMBsubType2;
    /**
     * Combo-box for EDS model.
     */
    private ComboBox vCMBcomponentTypeValue;
    /**
     * Combo-box for Device type.
     */
    private ComboBox vCMBorganFamilyValue;
    /**
     * Combo-box for Device type.
     */
    private ComboBox vCMBsFAltValue;
    /**
     * Combo-box for sub alter function.
     */
    private ComboBox vCMBsSAltValue;
    /**
     * Combo-box for Device type.
     */
    private ComboBox vCMBsFBatValue;
    /**
     * Combo-box for Device type.
     */
    private ComboBox vCMBsSBatValue;
    /**
     * List of 96k part-numbers.
     */
    private List<EdsNumber96k> edsNumber96ks;
    /**
     * Layout for 96k part-numbers.
     */
    private VerticalLayout numbersLayout;
    // contact info
    /**
     * Combo-box for Sheet administrator.
     */
    private ComboBox vCMBadminValue;
    /**
     * Combo-box for Development leader.
     */
    private ComboBox vCMBofficerValue;
    /**
     * Label for service value.
     */
    private Label vLBLserviceValue;
    /**
     * Combo-box for PSA leader.
     */
    private ComboBox vCMBmanagerValue;
    /**
     * Combo-box for Supplier.
     */
    private ComboBox vCMBsupplierValue;
    /**
     * Option group for 'Device connected to LV / HV network?'.
     */
    private OptionGroup vOGbttbt;
    /**
     * List of partners.
     */
    private List<ComboBox> vCMBperimeters;
    /**
     * Layout for EDS partners.
     */
    private VerticalLayout edsPerimetersLayout;
    /**
     * Layout for project partners.
     */
    private VerticalLayout projectPerimetersLayout;
    // project info
    /**
     * Label for 'Initializer project'.
     */
    private Label vLBLprojectSetter;
    /**
     * Combo-box for 'Initializer project'.
     */
    private ComboBox vCMBprojectSetter;
    /**
     * Text field for 'Number of occurrence by vehicle project'.
     */
    private TextField vTFprojectSetterCount;
    /**
     * Layout for follower projects.
     */
    private VerticalLayout followerProjectsLayout;
    /**
     * List of EDS projects.
     */
    private List<EdsProjectEds> edsProjectEdsesToDeleteList;
    /**
     * Map of project and text field.
     */
    private HashMap<EdsProjectEds, TextField> edsProjectEdsesCountMap;
    // Validation
    /**
     * List of abstract fields.
     */
    private List<AbstractField> fieldsToCheck;
    // Objets à sauvegarder
    /**
     * List of 96k Part-numbers.
     */
    private List<EdsNumber96k> newNumbersToSave;
    /**
     * List of 96k Part-numbers to be deleted.
     */
    private List<EdsNumber96k> newNumbersToDelete;
    /**
     * List of SAP reference.
     */
    private List<EdsSAPReference> listSAPReferences;
    /**
     * List of SAP reference for FCT.
     */
    private List<EdsSAPReference> listSelectedSAPReferencesFCT;
    /**
     * List of SAP reference for FNR.
     */
    private List<EdsSAPReference> listSelectedSAPReferencesFNR;

    /**
     * Combo-box for Reference FCT.
     */
    private ComboBox vCMBsRefFCTValue;
    /**
     * Combo-box for Reference FNR.
     */
    private ComboBox vCMBsRefFNRValue;

    /**
     * Initialization method.
     */
    private void init() {
        this.vTAcomments = new TextArea();
        vTAcomments.setSizeFull();
        vTAcomments.setImmediate(true);
        vTAcomments.setNullRepresentation("");
        vTAcomments.setValue(eds.getEdsComments());

        this.accordion = new Accordion();

        this.users = EDSdao.getInstance().getAllUsers();
        this.componentTypes = EDSdao.getInstance().getAllComponentTypes();
        this.organsFamilies = EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.ORGAN_FAMILY);

        this.subFuncAlt = EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.SF_ALTERNANTEUR);
        this.subFuncBat = EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.SF_BATTERIE);
        this.subSysAlt = EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.SS_ALTERNANTEUR);
        this.subSysBat = EDSdao.getInstance().getAllActiveWordingsByType(EdsWording.SS_BATTERIE);

        this.suppliers = EDSdao.getInstance().getAllSuppliers();
        this.perimeters = EDSdao.getInstance().getAllPerimeters();
        this.projects = EDSdao.getInstance().getAllProjects();
        this.seterProjects = EDSdao.getInstance().getAllProjects();
        this.listSAPReferences = EDSdao.getInstance().getAllSAPReferenceRevision();
        this.listSelectedSAPReferencesFCT = new ArrayList<EdsSAPReference>();
        this.listSelectedSAPReferencesFNR = new ArrayList<EdsSAPReference>();

        this.fieldsToCheck = new ArrayList<AbstractField>();
        this.newNumbersToSave = new ArrayList<EdsNumber96k>();
        this.newNumbersToDelete = new ArrayList<EdsNumber96k>();

        // A enlever plus tard

        this.recordInfoLayout = getRecordInfos();
        this.contactInfoLayout = getContactsInfos();
        this.projectInfoLayout = getProjectInfos();

        accordion.addTab(recordInfoLayout, bundle.getString("generic-data-organe-title"));
        accordion.addTab(contactInfoLayout, bundle.getString("generic-data-represent-title"));
        accordion.addTab(projectInfoLayout, bundle.getString("generic-data-link-title"));
        accordion.addTab(vTAcomments, bundle.getString("eds-comment-label"));

        addComponent(accordion);
        discardChanges();
    }

    /**
     * This method generates Record info component. This method generates the UI for 'Device-EDS' panel.
     * 
     * @return Record info Component.
     */
    private Layout getRecordInfos() {
        final GridLayout layout = new GridLayout(4, 20);
        layout.setSpacing(true);
        layout.setMargin(true);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-ref") + " :"), 0, 0);
        layout.addComponent(new Label(eds.getEdsRef()), 1, 0);

        Date creaDate = eds.getEdsCreaDate();
        if (creaDate == null) {
            creaDate = new Date();
        }
        layout.addComponent(new Label(bundle.getString("eds-creation-date") + ":"), 0, 1);
        layout.addComponent(new Label(DateFormat.getDateInstance().format(creaDate)), 1, 1);

        Date modifDate = eds.getEdsModifDate();
        if (modifDate == null) {
            modifDate = new Date();
        }
        layout.addComponent(new Label(bundle.getString("eds-modif-date") + " :"), 0, 2);
        layout.addComponent(new Label(DateFormat.getDateInstance().format(modifDate)), 1, 2);

        // Ex_conn_94 - EDS name field
        vCMBnameValue = new ComboBox();
        vCMBnameValue.setNullSelectionAllowed(false);
        vCMBnameValue.setRequired(true);
        vCMBnameValue.setRequiredError(bundle.getString("generic-data-validation-error"));
        vCMBnameValue.setImmediate(true);

        fieldsToCheck.add(vCMBnameValue);

        vCMBnameValue.addItem("-"); // Empty choice
        if (controller.getDbelec() != null) {
            for (ComponentName name : controller.getDbelec().getComponentsName().getAll()) {
                vCMBnameValue.addItem(name.getID_2());
                vCMBnameValue.setItemCaption(name.getID_2(), name.getID_2());
            }
        }

        layout.addComponent(new Label(bundle.getString("generic-data-organe-name")), 0, 3);
        layout.addComponent(vCMBnameValue, 1, 3);
        fieldsToCheck.add(vCMBnameValue);

        if (!controller.userHasSufficientRights(EdsRight.APP_EDS_NAME_MODIFICATION))
            vCMBnameValue.setEnabled(false); // Lock if no sufficient rights

        // Ex_conn_94 - EDS description field
        layout.addComponent(new Label(bundle.getString("generic-data-organe-desc") + " :"), 0, 4);
        this.vTFdescriptionValue = new TextField();
        vTFdescriptionValue.setNullRepresentation("");
        vTFdescriptionValue.setNullSettingAllowed(true);
        vTFdescriptionValue.setValue(eds.getEdsName());
        layout.addComponent(vTFdescriptionValue, 1, 4);

        if (!controller.userHasSufficientRights(EdsRight.APP_EDS_NAME_MODIFICATION))
            vTFdescriptionValue.setEnabled(false); // Lock if no sufficient rights

        // End of Ex_conn_94

        vCMBvalidationLevel = new ComboBox();
        vCMBvalidationLevel.setNullSelectionAllowed(false);
        vCMBvalidationLevel.setRequired(true);
        vCMBvalidationLevel.setRequiredError(bundle.getString("generic-data-validation-error"));
        fieldsToCheck.add(vCMBvalidationLevel);

        vCMBvalidationLevel.addItem(EdsEds.VALIDATION_LVL_HIGH);
        vCMBvalidationLevel.setItemCaption(EdsEds.VALIDATION_LVL_HIGH,
                controller.getBundle().getString(EdsEds.getValidationLevelText(EdsEds.VALIDATION_LVL_HIGH)));
        vCMBvalidationLevel.addItem(EdsEds.VALIDATION_LVL_LOW);
        vCMBvalidationLevel.setItemCaption(EdsEds.VALIDATION_LVL_LOW,
                controller.getBundle().getString(EdsEds.getValidationLevelText(EdsEds.VALIDATION_LVL_LOW)));
        vCMBvalidationLevel.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (vCMBvalidationLevel.getValue() != null && !vCMBvalidationLevel.getValue().equals(eds.getEdsValidLvl())) {
                    controller.showWarning(bundle.getString("eds-pop-warning-title"), bundle.getString("generic-data-organe-val-warning"), 5000);
                }
            }
        });
        vCMBvalidationLevel.setImmediate(true);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-level")), 0, 5);
        layout.addComponent(vCMBvalidationLevel, 1, 5);

        int stage = controller.retrieveEdsStage(eds, eds.getEdsProject()); // TODO : Mettre le curenteds
        // courant au lieu du projet
        // lanceur
        String stageText = EdsApplicationController.getStageText(stage);
        layout.addComponent(new Label(bundle.getString("generic-data-organe-state")), 0, 6);
        layout.addComponent(new Label(stageText), 1, 6);

        vCMBsubType1 = new ComboBox();
        vCMBsubType1.addItem(EdsApplicationController.ST1_BT);
        vCMBsubType1.setItemCaption(EdsApplicationController.ST1_BT, EdsApplicationController.ST1_BT_TEXT);
        vCMBsubType1.addItem(EdsApplicationController.ST1_TBT);
        vCMBsubType1.setItemCaption(EdsApplicationController.ST1_TBT, EdsApplicationController.ST1_TBT_TEXT);

        // TBT/BT
        this.vOGbttbt = new OptionGroup();
        vOGbttbt.setNullSelectionAllowed(false);

        vOGbttbt.addItem(1);
        vOGbttbt.setItemCaption(1, bundle.getString("consolid-qcf-oui"));
        vOGbttbt.addItem(0);
        vOGbttbt.setItemCaption(0, bundle.getString("consolid-qcf-non"));
        setBTTBTvalue(eds.getEdsIsBttbt());
        layout.addComponent(new Label(bundle.getString("generic-data-organe-connect")), 0, 7);
        layout.addComponent(vOGbttbt, 1, 7);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-type-1")), 0, 8);
        layout.addComponent(vCMBsubType1, 1, 8);
        vCMBsubType2 = new ComboBox();
        vCMBsubType2.addItem(EdsApplicationController.ST2_GMP);
        vCMBsubType2.setItemCaption(EdsApplicationController.ST2_GMP, EdsApplicationController.ST2_GMP_TEXT);
        vCMBsubType2.addItem(EdsApplicationController.ST2_LAS);
        vCMBsubType2.setItemCaption(EdsApplicationController.ST2_LAS, EdsApplicationController.ST2_LAS_TEXT);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-type-2")), 0, 9);
        layout.addComponent(vCMBsubType2, 1, 9);

        vCMBcomponentTypeValue = new ComboBox();
        vCMBcomponentTypeValue.setNullSelectionAllowed(false);
        vCMBcomponentTypeValue.setRequired(true);
        vCMBcomponentTypeValue.setRequiredError(bundle.getString("generic-data-model-error"));
        fieldsToCheck.add(vCMBcomponentTypeValue);

        for (EdsComponentType componentType : componentTypes) {
            vCMBcomponentTypeValue.addItem(componentType);
            vCMBcomponentTypeValue.setItemCaption(componentType, componentType.getLocaleCtName(controller.getApplication().getLocale()));
        }
        layout.addComponent(new Label(bundle.getString("menu-project-tab-model")), 0, 10);
        layout.addComponent(vCMBcomponentTypeValue, 1, 10);

        vCMBorganFamilyValue = new ComboBox();
        vCMBorganFamilyValue.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);
        vCMBsFAltValue = new ComboBox();
        vCMBsFAltValue.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);
        vCMBsFBatValue = new ComboBox();
        vCMBsFBatValue.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);
        vCMBsSAltValue = new ComboBox();
        vCMBsSAltValue.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);
        vCMBsSBatValue = new ComboBox();
        vCMBsSBatValue.setFilteringMode(AbstractSelect.Filtering.FILTERINGMODE_CONTAINS);

        for (EdsWording organFamily : organsFamilies) {
            vCMBorganFamilyValue.addItem(organFamily);
            vCMBorganFamilyValue.setItemCaption(organFamily, EDSTools.getWordingValueByLocale(organFamily, controller.getApplication().getLocale()));
        }
        for (EdsWording sFAlt : subFuncAlt) {
            vCMBsFAltValue.addItem(sFAlt);
            vCMBsFAltValue.setItemCaption(sFAlt, EDSTools.getWordingValueByLocale(sFAlt, controller.getApplication().getLocale()));
        }

        for (EdsWording sFAlt : subFuncAlt) {
            vCMBsFAltValue.addItem(sFAlt);
            vCMBsFAltValue.setItemCaption(sFAlt, EDSTools.getWordingValueByLocale(sFAlt, controller.getApplication().getLocale()));
        }

        for (EdsWording sFBat : subFuncBat) {
            vCMBsFBatValue.addItem(sFBat);
            vCMBsFBatValue.setItemCaption(sFBat, EDSTools.getWordingValueByLocale(sFBat, controller.getApplication().getLocale()));
        }

        for (EdsWording sSAlt : subSysAlt) {
            vCMBsSAltValue.addItem(sSAlt);
            vCMBsSAltValue.setItemCaption(sSAlt, EDSTools.getWordingValueByLocale(sSAlt, controller.getApplication().getLocale()));
        }

        for (EdsWording sSBat : subSysBat) {
            vCMBsSBatValue.addItem(sSBat);
            vCMBsSBatValue.setItemCaption(sSBat, EDSTools.getWordingValueByLocale(sSBat, controller.getApplication().getLocale()));
        }

        if (controller.userHasSufficientRights(EdsRight.APP_EDS_TYPE_ORGANS)) {
            layout.addComponent(new Label(bundle.getString("Admin-lib-ongle-organe-family")), 0, 11);
            layout.addComponent(vCMBorganFamilyValue, 1, 11);

            layout.addComponent(new Label(bundle.getString("Admin-lib-ongle-sf-alt")), 0, 12);
            layout.addComponent(vCMBsFAltValue, 1, 12);

            layout.addComponent(new Label(bundle.getString("Admin-lib-ongle-sf-bat")), 0, 13);
            layout.addComponent(vCMBsFBatValue, 1, 13);

            layout.addComponent(new Label(bundle.getString("Admin-lib-ongle-ss-alt")), 0, 14);
            layout.addComponent(vCMBsSAltValue, 1, 14);

            layout.addComponent(new Label(bundle.getString("Admin-lib-ongle-ss-bat")), 0, 15);
            layout.addComponent(vCMBsSBatValue, 1, 15);

        }
        if (!eds.getEdsNumber96ks().isEmpty()) {
            this.edsNumber96ks = new ArrayList<EdsNumber96k>();
            this.numbersLayout = new VerticalLayout();
            numbersLayout.setSpacing(true);
            numbersLayout.setSizeUndefined();

            layout.addComponent(numbersLayout, 1, 16);

            layout.addComponent(new Label(bundle.getString("generic-data-organe-96") + " :"), 0, 16);
            initNumbersValue();
        }

        layout.addComponent(new Label());

        layout.addComponent(new Label(bundle.getString("generic-data-organe-96-fct") + " :"), 0, ROW_REF_FCT);
        vCMBsRefFCTValue = new ComboBox();
        initSAPReferenceValues(vCMBsRefFCTValue);
        layout.addComponent(vCMBsRefFCTValue, 1, ROW_REF_FCT);
        Button buttonOK1 = new Button(bundle.getString("Validation-OK"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                addRemoveSAPReference(layout, ROW_REF_FCT, listSelectedSAPReferencesFCT, vCMBsRefFCTValue.getValue(), true);
            }

        });

        layout.addComponent(buttonOK1, 2, ROW_REF_FCT);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-96-fnr") + " :"), 0, ROW_REF_FNR);
        vCMBsRefFNRValue = new ComboBox();
        initSAPReferenceValues(vCMBsRefFNRValue);
        layout.addComponent(vCMBsRefFNRValue, 1, ROW_REF_FNR);
        initSelectedSAPReference(layout, ROW_REF_FCT, listSelectedSAPReferencesFCT, eds.getEdsNumber96fcts());
        initSelectedSAPReference(layout, ROW_REF_FNR, listSelectedSAPReferencesFNR, eds.getEdsNumber96fnrs());

        Button buttonOK2 = new Button(bundle.getString("Validation-OK"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                addRemoveSAPReference(layout, ROW_REF_FNR, listSelectedSAPReferencesFNR, vCMBsRefFNRValue.getValue(), true);
            }
        });

        layout.addComponent(buttonOK2, 2, ROW_REF_FNR);

        return layout;
    }

    private void initSelectedSAPReference(GridLayout layout, int row, List<EdsSAPReference> listSelectedSAPReferences,
            Set<EdsSAPReference> edsNumber96fcts) {
        if (listSelectedSAPReferences != null) {
            for (EdsSAPReference edsSAPReference : edsNumber96fcts) {
                addRemoveSAPReference(layout, row, listSelectedSAPReferences, edsSAPReference, false);
            }
        }
    }

    /**
     * Add remove functionality for FCT and FNR SAP references
     * 
     * @param layout
     * @param row
     * @param listSelectedSAPReferences
     * @param enableOverride
     */
    private void addRemoveSAPReference(final GridLayout layout, int row, final List<EdsSAPReference> listSelectedSAPReferences,
            Object sapReferenceRevision, boolean enableOverride) {

        if (sapReferenceRevision != null) {
            HorizontalLayout sapReferenceLayout;
            sapReferenceLayout = (HorizontalLayout) layout.getComponent(3, row);
            if (sapReferenceLayout == null)
                sapReferenceLayout = new HorizontalLayout();
            else
                layout.removeComponent(3, row);

            listSAPReferences.remove(sapReferenceRevision);
            vCMBsRefFCTValue.removeItem(sapReferenceRevision);
            vCMBsRefFNRValue.removeItem(sapReferenceRevision);

            listSelectedSAPReferences.add((EdsSAPReference) sapReferenceRevision);

            Label vLBLprojectName = new Label(((EdsSAPReference) sapReferenceRevision).getReferenceRevisionLabel());
            vLBLprojectName.setWidth("90px");

            sapReferenceLayout.setSpacing(true);
            sapReferenceLayout.addComponent(vLBLprojectName);

            Button vBDelete = new Button("", new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    Button button = event.getButton();
                    EdsSAPReference data = (EdsSAPReference) button.getData();
                    listSAPReferences.add(data);
                    vCMBsRefFCTValue.addItem(data);
                    vCMBsRefFNRValue.addItem(data);

                    int index = listSelectedSAPReferences.indexOf(data);
                    listSelectedSAPReferences.remove(data);

                    index = index * 2;
                    Component parent = button.getParent();
                    Component label = ((HorizontalLayout) parent).getComponent(index);
                    ((HorizontalLayout) parent).removeComponent(label);
                    ((HorizontalLayout) parent).removeComponent(button);
                }
            });

            vBDelete.setData(sapReferenceRevision);
            vBDelete.setImmediate(true);
            vBDelete.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/delete-filter.png "));
            vBDelete.setStyleName(BaseTheme.BUTTON_LINK);
            int stage = controller.retrieveEdsStage(eds, eds.getEdsProject());
            if (!enableOverride) {
                if (row == ROW_REF_FCT && stage > EdsApplicationController.ROBUST_STAGE)
                    vBDelete.setEnabled(false);
                if (row == ROW_REF_FNR && stage > EdsApplicationController.SOLID_STAGE)
                    vBDelete.setEnabled(false);
            }
            sapReferenceLayout.addComponent(vBDelete);

            layout.addComponent(sapReferenceLayout, 3, row);
        }
    }

    /**
     * This method is used to add 96k part-number. It checks the existing part-numbers and allows to add accordingly.
     */
    private void queryNew96KNumber() {
        subwindow = new Window(bundle.getString("generic-data-organe-96"));

        subwindow.setWidth("350");
        GridLayout contentLayout = new GridLayout(2, 2);

        final TextField pName = new TextField();
        pName.setWidth("200");
        contentLayout.addComponent(pName, 1, 0);

        Label l = new Label(bundle.getString("generic-data-organe-96"));
        l.setWidth("100");
        contentLayout.addComponent(l, 0, 0);

        Button button = new Button(bundle.getString("Admin-proj-validate-button"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (pName.getValue() != null && pName.getValue() != "") {
                    if (!containsRef(edsNumber96ks, pName.getValue().toString())) {
                        EdsNumber96k edsNumber96k = new EdsNumber96k(UUID.randomUUID().toString(), pName.getValue().toString());
                        numbersLayout.addComponent(add96NumberField(edsNumber96k));
                        subwindow.setVisible(false);
                    } else {
                        getWindow().showNotification(
                                MessageFormat.format(controller.getBundle().getString("eds-exist-value-error"), pName.getValue().toString()));
                    }

                } else {
                    getWindow().showNotification(bundle.getString("eds-no-value-error"));
                }
            }
        });

        contentLayout.addComponent(button, 0, 1, 1, 1);

        contentLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);

        contentLayout.setSpacing(true);

        subwindow.setModal(true);
        subwindow.center();
        subwindow.addComponent(contentLayout);
        getApplication().getMainWindow().addWindow(subwindow);
    }

    /**
     * This method generates Contacts info component. This method generates the UI for 'User' panel.
     * 
     * @return Contact info Component.
     */
    private Layout getContactsInfos() {
        // Hard data to test

        GridLayout layout = new GridLayout(2, 8);
        layout.setSpacing(true);
        layout.setMargin(true);

        this.vCMBadminValue = new ComboBox();
        vCMBadminValue.setNullSelectionAllowed(false);
        vCMBadminValue.setRequired(true);
        vCMBadminValue.setRequiredError(bundle.getString("generic-data-organe-error"));
        fieldsToCheck.add(vCMBadminValue);

        for (EdsUser user : users) {
            if (EdsRight.hasSufficientRights(user, EdsRight.APP_GENERAL_BE_EDS_ADMIN) || user.equals(eds.getEdsUserByEdsAdminId())) {
                vCMBadminValue.addItem(user);
                vCMBadminValue.setItemCaption(user, user.toFullIdentity());
            }
        }
        layout.addComponent(new Label(bundle.getString("generic-data-represent") + " :"));
        layout.addComponent(vCMBadminValue, 1, 0);

        this.vCMBofficerValue = new ComboBox();

        for (EdsUser user : users) {
            if (EdsRight.hasSufficientRights(user, EdsRight.APP_GENERAL_BE_EDS_OFFICER) || user.equals(eds.getEdsUserByEdsOfficerId())) {
                vCMBofficerValue.addItem(user);
                vCMBofficerValue.setItemCaption(user, user.toFullIdentity());
            }
        }
        vCMBofficerValue.setImmediate(true);

        vCMBofficerValue.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (vCMBofficerValue.getValue() == null) {
                    vLBLserviceValue.setValue("");
                } else {
                    vLBLserviceValue.setValue(((EdsUser) vCMBofficerValue.getValue()).getUService());
                }
            }
        });

        layout.addComponent(new Label(bundle.getString("menu-EDS-charge-dev") + " :"));
        HorizontalLayout officerLayout = new HorizontalLayout();
        officerLayout.setSpacing(true);
        officerLayout.addComponent(vCMBofficerValue);

        Button vBMail = new Button("", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (vCMBofficerValue.getValue() != null) {
                    controller.sendAdviceMail((EdsUser) vCMBofficerValue.getValue(), null, eds, EdsMailAdviceBuilder.EDS_CD_NOTIFICATION, false);
                    getWindow().showNotification("", bundle.getString("mail-cd-notification-succes"));
                } else {
                    getWindow().showNotification("", bundle.getString("mail-cd-notification-error"));
                }
            }
        });
        vBMail.setImmediate(true);
        vBMail.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/mail.png"));
        vBMail.setStyleName(BaseTheme.BUTTON_LINK);
        vBMail.setDescription(bundle.getString("mail-cd-notification-tooltype"));

        officerLayout.addComponent(vBMail);
        if (!controller.userHasSufficientRights(EdsRight.APP_GENERAL_BE_EDS_NOTIFICATION_CD)) {
            vBMail.setVisible(false);
        }

        layout.addComponent(officerLayout, 1, 1);

        this.vLBLserviceValue = new Label("-");
        layout.addComponent(new Label(bundle.getString("generic-data-dev-service")));
        layout.addComponent(vLBLserviceValue, 1, 2);

        this.vCMBmanagerValue = new ComboBox();

        for (EdsUser user : users) {
            if (EdsRight.hasSufficientRights(user, EdsRight.APP_GENERAL_BE_EDS_MANAGER) || user.equals(eds.getEdsUserByEdsManagerId())) {
                vCMBmanagerValue.addItem(user);
                vCMBmanagerValue.setItemCaption(user, user.toFullIdentity());
            }
        }
        for (EdsUser user : (Collection<EdsUser>) vCMBmanagerValue.getVisibleItemIds()) {
            vCMBmanagerValue.setItemCaption(user, user.toFullIdentity());
        }
        layout.addComponent(new Label(bundle.getString("generic-data-represent-responsible") + " :"));
        layout.addComponent(vCMBmanagerValue, 1, 3);

        this.vCMBsupplierValue = new ComboBox();
        for (EdsSupplier supplier : suppliers) {
            vCMBsupplierValue.addItem(supplier);
            vCMBsupplierValue.setItemCaption(supplier, supplier.getSName());
        }
        layout.addComponent(new Label(bundle.getString("generic-data-represent-FNR") + " :"));
        layout.addComponent(vCMBsupplierValue, 1, 4);

        this.vCMBperimeters = new ArrayList<ComboBox>();

        this.projectPerimetersLayout = new VerticalLayout();
        projectPerimetersLayout.setSpacing(true);
        projectPerimetersLayout.setSizeUndefined();

        layout.addComponent(new Label(bundle.getString("generic-data-represent-partner-proj") + " :"));
        layout.addComponent(projectPerimetersLayout, 1, 5);

        this.vCMBperimeters = new ArrayList<ComboBox>();
        this.edsPerimetersLayout = new VerticalLayout();
        edsPerimetersLayout.setSpacing(true);
        edsPerimetersLayout.setSizeUndefined();

        layout.addComponent(new Label(bundle.getString("generic-data-represent-partner-EDS") + " :"));
        layout.addComponent(edsPerimetersLayout, 1, 6);
        initPerimetersValue();
        Button jBTaddPerimeter = new Button(bundle.getString("generic-data-represent-partner-add"));
        jBTaddPerimeter.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                edsPerimetersLayout.addComponent(addPerimeterComboBox(null, true));
            }
        });
        layout.addComponent(jBTaddPerimeter, 1, 7);

        return layout;
    }

    /**
     * This method generates Project info component. This method generates the UI for 'Project links' panel.
     * 
     * @return Project info Component.
     */
    private Layout getProjectInfos() {
        GridLayout projectLayout = new GridLayout(2, 2);
        projectLayout.setSpacing(true);
        projectLayout.setMargin(true);

        projectLayout.addComponent(new Label(bundle.getString("filter-project-launcher") + " : "));
        vCMBprojectSetter = new ComboBox();

        vLBLprojectSetter = new Label(eds.getEdsProject().getPName());
        vLBLprojectSetter.setWidth("100px");
        vTFprojectSetterCount = new TextField();
        vTFprojectSetterCount.setWidth("20px");
        vTFprojectSetterCount.addValidator(new IntegerValidator(MessageFormat.format(bundle.getString("generic-data-occurence-error"), eds
                .getEdsProject().getPName())));
        vTFprojectSetterCount
                .setRequiredError(MessageFormat.format(bundle.getString("generic-data-occurence-error"), eds.getEdsProject().getPName()));
        fieldsToCheck.add(vTFprojectSetterCount);

        // Label vLBLprojectName = new Label( eds.getEdsProject().getPName() );
        // vLBLprojectName.setWidth( "100px" );
        vTFprojectSetterCount.setValue(eds.getEdsPLaunchCount().toString());

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        if (!controller.userHasSufficientRights(EdsRight.APP_GENERAL_BE_EDS_ADMIN)) {
            layout.addComponent(vLBLprojectSetter);
        } else {
            layout.addComponent(vCMBprojectSetter);
        }
        layout.addComponent(new Label("<b>x</b>", Label.CONTENT_XHTML));

        HorizontalLayout projectSetterCountLayout = new HorizontalLayout();
        projectSetterCountLayout.setSpacing(true);
        projectSetterCountLayout.addComponent(vTFprojectSetterCount);
        projectSetterCountLayout.addComponent(new Label("(" + bundle.getString("generic-data-occurence-number") + ")"));

        layout.addComponent(projectSetterCountLayout);
        projectLayout.addComponent(layout, 1, 0);

        projectLayout.addComponent(new Label(bundle.getString("generic-data-link-follow") + " : "));

        this.edsProjectEdsesToDeleteList = new ArrayList<EdsProjectEds>();
        this.edsProjectEdsesCountMap = new HashMap<EdsProjectEds, TextField>();
        followerProjectsLayout = new VerticalLayout();
        followerProjectsLayout.setSpacing(true);
        projectLayout.addComponent(followerProjectsLayout, 1, 1);
        initLauncherProject();
        initFollowerProjectsValue();

        return projectLayout;
    }

    // private Component add96NumberField( String id ,
    // String value ,
    // final boolean removable )
    // {
    // final TextField96N jTXT96number = new TextField96N();
    // jTXT96number.setValue( value );
    // jTXT96number.setId( id );
    // vTXT96numbers.add( jTXT96number );
    //
    // // if ( !removable )
    // // {
    // // return jTXT96number;
    // // }
    // // else
    // // {
    // HorizontalLayout layout = new HorizontalLayout();
    // layout.setSpacing( true );
    // layout.addComponent( jTXT96number );
    //
    // Button vBDelete = new Button( "" ,
    // new Button.ClickListener()
    // {
    // public void buttonClick( ClickEvent event )
    // {
    // if ( removable )
    // {
    // numbersLayout.removeComponent( jTXT96number.getParent() );
    // }
    //
    // vTXT96numbers.remove( jTXT96number );
    // newNumbersToDelete.add( new EdsNumber96k( jTXT96number.getId() ,
    // (String) jTXT96number.getValue() ) );
    // jTXT96number.setValue( "" );
    // jTXT96number.setId( "" );
    // }
    // } );
    //
    // vBDelete.setImmediate( true );
    // vBDelete.setIcon( ResourceManager.getInstance().getThemeIconResource( "icons/erase.png " ) );
    // vBDelete.setStyleName( BaseTheme.BUTTON_LINK );
    // layout.addComponent( vBDelete );
    // return layout;
    // // }
    // }
    /**
     * This method is used to add 96k part-number.
     * 
     * @param edsNumber96k part number to be added.
     * @return Component for part-number.
     */
    private Component add96NumberField(EdsNumber96k edsNumber96k) {

        EdsNumber96kEditView number96kEditView = new EdsNumber96kEditView(edsNumber96k);
        edsNumber96ks.add(edsNumber96k);
        return number96kEditView;
    }

    /**
     * This method is used to create Partners combo-box.
     * 
     * @param value Partner details.
     * @param removable true if is allowed to remove.
     * @return Component for Partners.
     */
    private Component addPerimeterComboBox(EdsPerimeter value, boolean removable) {
        final ComboBox vCMBperimeter = new ComboBox();
        for (EdsPerimeter perimeter : perimeters) {
            vCMBperimeter.addItem(perimeter);
            vCMBperimeter.setItemCaption(perimeter, perimeter.getPeName());
        }

        if (value != null && !vCMBperimeter.containsId(value)) {
            vCMBperimeter.addItem(value);
            vCMBperimeter.setItemCaption(value, value.getPeName());
        }

        vCMBperimeters.add(vCMBperimeter);
        vCMBperimeter.setValue(value);
        if (!removable) {
            return vCMBperimeter;
        } else {
            Layout layout = new HorizontalLayout();
            layout.addComponent(vCMBperimeter);
            layout.addComponent(new Button("-", new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    edsPerimetersLayout.removeComponent(vCMBperimeter.getParent());
                    vCMBperimeters.remove(vCMBperimeter);
                }
            }));
            return layout;
        }
    }

    /**
     * This method is used to create the follower project component.
     * 
     * @param value project to be added.
     * @param removable If project is removable. Then a delete icon will appear.
     * @return Component for follower project.
     */
    private Component addFollowerProject(final EdsProjectEds value, boolean removable) {
        final TextField vTFcount = new TextField();
        vTFcount.setWidth("20px");
        vTFcount.addValidator(new IntegerValidator(MessageFormat.format(bundle.getString("generic-data-occurence-error"), value.getEdsProject()
                .getPName())));
        vTFcount.setRequiredError(MessageFormat.format(bundle.getString("generic-data-occurence-error"), value.getEdsProject().getPName()));
        fieldsToCheck.add(vTFcount);
        vTFcount.setValue(value.getPedsCount().toString());

        Label vLBLprojectName = new Label(value.getEdsProject().getPName());
        vLBLprojectName.setWidth("100px");

        edsProjectEdsesCountMap.put(value, vTFcount);

        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.addComponent(vLBLprojectName);
        layout.addComponent(new Label("<b>x</b>", Label.CONTENT_XHTML));
        layout.addComponent(vTFcount);

        if (removable && controller.userHasSufficientRights(EdsRight.APP_EDS_REMOVE_FOLLOWER_PROJECT)) {

            Button vBDelete = new Button("", new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    edsProjectEdsesToDeleteList.add(value);
                    followerProjectsLayout.removeComponent(layout);
                    edsProjectEdsesCountMap.remove(value);
                    fieldsToCheck.remove(vTFcount);
                }
            });

            vBDelete.setImmediate(true);
            vBDelete.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/erase.png "));
            vBDelete.setStyleName(BaseTheme.BUTTON_LINK);
            layout.addComponent(vBDelete);

        }
        return layout;
    }

    /**
     * This method is used to initialize follower projects value.
     */
    private void initFollowerProjectsValue() {
        followerProjectsLayout.removeAllComponents();
        edsProjectEdsesToDeleteList.clear();
        fieldsToCheck.removeAll(edsProjectEdsesCountMap.values());
        edsProjectEdsesCountMap.clear();
        if (eds.getEdsProjectEdses().isEmpty()) {
            followerProjectsLayout.addComponent(new Label("-"));
        } else {
            for (EdsProjectEds edsProjectEds : (Set<EdsProjectEds>) eds.getEdsProjectEdses()) {
                followerProjectsLayout.addComponent(addFollowerProject(edsProjectEds, true));
            }
        }
        followerProjectsLayout.setSizeUndefined();
    }

    /**
     * This method is used to initialize the launcher project.
     */
    private void initLauncherProject() {

        this.seterProjects = EDSdao.getInstance().getAllProjects();

        for (EdsProjectEds project : eds.getEdsProjectEdses()) {
            seterProjects.remove(project.getEdsProject());
        }
        vCMBprojectSetter.removeAllItems();
        for (EdsProject p : seterProjects) {
            vCMBprojectSetter.addItem(p);
            vCMBprojectSetter.setItemCaption(p, p.getPName());
        }

        vCMBprojectSetter.setValue(eds.getEdsProject());
    }

    /**
     * This method is used to initialize the number values.
     */
    private void initNumbersValue() {
        numbersLayout.removeAllComponents();
        edsNumber96ks.clear();
        if (!eds.getEdsNumber96ks().isEmpty()) {

            boolean removable = false;
            for (EdsNumber96k number : (Set<EdsNumber96k>) eds.getEdsNumber96ks()) {
                numbersLayout.addComponent(add96NumberField(number));

                removable = true;
            }
        }
    }

    /**
     * This method is used to initialize Partner values.
     */
    private void initPerimetersValue() {
        projectPerimetersLayout.removeAllComponents();
        for (EdsPerimeter perimeter : EdsApplicationController.getAllAuthorizedPerimeters(eds, false)) {
            projectPerimetersLayout.addComponent(new Label(perimeter.getPeName()));
        }

        edsPerimetersLayout.removeAllComponents();
        vCMBperimeters.clear();
        if (eds.getEdsPerimeters().isEmpty()) {
            edsPerimetersLayout.addComponent(addPerimeterComboBox(null, false));
        } else {
            boolean removable = false;
            Set<EdsPerimeter> sortedEdsPerimeter = new TreeSet<EdsPerimeter>(new Comparator<EdsPerimeter>() {
                public int compare(EdsPerimeter p1, EdsPerimeter p2) {
                    return p1.getPeName().compareTo(p2.getPeName());
                }
            });
            sortedEdsPerimeter.addAll(eds.getEdsPerimeters());
            for (EdsPerimeter perimeter : sortedEdsPerimeter) {
                edsPerimetersLayout.addComponent(addPerimeterComboBox(perimeter, removable));
                removable = true;
            }
        }
    }

    private void initSAPReferenceValues(ComboBox refFCTBox) {
        refFCTBox.setNullSelectionAllowed(true);
        refFCTBox.setImmediate(true);
        refFCTBox.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
        for (EdsSAPReference referenceRevision : listSAPReferences) {
            refFCTBox.addItem(referenceRevision);
            refFCTBox.setItemCaption(referenceRevision, referenceRevision.getReferenceRevisionLabel().trim());
        }
    }

    /**
     * This method is used to retrieve the answer of 'Device connected to LV / HV network'.
     * 
     * @return value of 'Device connected to LV / HV network' question.
     */
    private Integer getBTTBTvalue() {
        return Integer.parseInt(vOGbttbt.getValue().toString());
    }

    /**
     * This method is used to set 'Device connected to LV / HV network' value.
     * 
     * @param value answer of 'Device connected to LV / HV network' value to be set.
     */
    private void setBTTBTvalue(int value) {

        vOGbttbt.setValue(value);

    }

    /**
     * Adding default values if they have been inactivated or deleted from the administration.
     */
    private void addComboBoxesDefaultValues() {
        if (eds.getEdsComponentType() != null && !vCMBcomponentTypeValue.containsId(eds.getEdsComponentType())) {
            vCMBcomponentTypeValue.addItem(eds.getEdsComponentType());
            vCMBcomponentTypeValue.setItemCaption(eds.getEdsComponentType(),
                    eds.getEdsComponentType().getLocaleCtName(controller.getApplication().getLocale()));
        }
        if (eds.getEdsOrganFamily() != null && !vCMBorganFamilyValue.containsId(eds.getEdsOrganFamily())) {
            vCMBorganFamilyValue.addItem(eds.getEdsOrganFamily());
            vCMBorganFamilyValue.setItemCaption(eds.getEdsOrganFamily(),
                    EDSTools.getWordingValueByLocale(eds.getEdsOrganFamily(), controller.getApplication().getLocale()));
        }
        if (eds.getEdsAltSubFct() != null && !vCMBsFAltValue.containsId(eds.getEdsAltSubFct())) {
            vCMBsFAltValue.addItem(eds.getEdsAltSubFct());
            vCMBsFAltValue.setItemCaption(eds.getEdsAltSubFct(),
                    EDSTools.getWordingValueByLocale(eds.getEdsAltSubFct(), controller.getApplication().getLocale()));
        }

        if (eds.getEdsBatSubFct() != null && !vCMBsFBatValue.containsId(eds.getEdsBatSubFct())) {
            vCMBsFBatValue.addItem(eds.getEdsBatSubFct());
            vCMBsFBatValue.setItemCaption(eds.getEdsBatSubFct(),
                    EDSTools.getWordingValueByLocale(eds.getEdsBatSubFct(), controller.getApplication().getLocale()));
        }

        if (eds.getEdsAltSubSys() != null && !vCMBsSAltValue.containsId(eds.getEdsAltSubSys())) {
            vCMBsSAltValue.addItem(eds.getEdsAltSubSys());
            vCMBsSAltValue.setItemCaption(eds.getEdsAltSubSys(),
                    EDSTools.getWordingValueByLocale(eds.getEdsAltSubSys(), controller.getApplication().getLocale()));
        }

        if (eds.getEdsBatSubSys() != null && !vCMBsSBatValue.containsId(eds.getEdsBatSubSys())) {
            vCMBsSBatValue.addItem(eds.getEdsBatSubSys());
            vCMBsSBatValue.setItemCaption(eds.getEdsBatSubFct(),
                    EDSTools.getWordingValueByLocale(eds.getEdsBatSubSys(), controller.getApplication().getLocale()));
        }

        if (eds.getEdsSupplier() != null && !vCMBsupplierValue.containsId(eds.getEdsSupplier())) {
            vCMBsupplierValue.addItem(eds.getEdsSupplier());
            vCMBsupplierValue.setItemCaption(eds.getEdsSupplier(), eds.getEdsSupplier().getSName());
        }
        if (eds.getEdsUserByEdsAdminId() != null && !vCMBadminValue.containsId(eds.getEdsUserByEdsAdminId())) {
            vCMBadminValue.addItem(eds.getEdsUserByEdsAdminId());
            vCMBadminValue.setItemCaption(eds.getEdsUserByEdsAdminId(), eds.getEdsUserByEdsAdminId().toFullIdentity());
        }
        if (eds.getEdsUserByEdsManagerId() != null && !vCMBmanagerValue.containsId(eds.getEdsUserByEdsManagerId())) {
            vCMBmanagerValue.addItem(eds.getEdsUserByEdsManagerId());
            vCMBmanagerValue.setItemCaption(eds.getEdsUserByEdsManagerId(), eds.getEdsUserByEdsManagerId().toFullIdentity());
        }
        if (eds.getEdsUserByEdsOfficerId() != null && !vCMBofficerValue.containsId(eds.getEdsUserByEdsOfficerId())) {
            vCMBofficerValue.addItem(eds.getEdsUserByEdsOfficerId());
            vCMBofficerValue.setItemCaption(eds.getEdsUserByEdsOfficerId(), eds.getEdsUserByEdsOfficerId().toFullIdentity());
        }
    }

    /**
     * This method is used to check if the part number already exists in the list.
     * 
     * @param edsNumber96ks list of part-numbers.
     * @param pName part-number to be found out.
     * @return true if part-number already exists, else false.
     */
    private boolean containsRef(List<EdsNumber96k> edsNumber96ks, String pName) {
        for (EdsNumber96k number96k : edsNumber96ks) {
            if (number96k.getNValue().equals(pName)) {
                return true;
            }

        }
        return false;
    }

}
