package com.inetpsa.eds.application.content.eds.genericdata;

import java.text.DateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.composants.Comment;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.I_ValidationFormData;
import com.inetpsa.eds.dao.model.Chs;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsNumber96k;
import com.inetpsa.eds.dao.model.EdsPerimeter;
import com.inetpsa.eds.dao.model.EdsProjectEds;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.dao.model.EdsSAPReference;
import com.inetpsa.eds.dao.model.EdsSupplier;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.uri.FragmentManager;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * This class represents 'Generic data' in read view.
 * 
 * @author Geometric Ltd.
 */
public class GenericDataFormReadView extends A_EdsReadForm {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param controller EDS application controller object.
     */
    public GenericDataFormReadView(EdsApplicationController controller, EdsEds eds) {
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
        return "generic-data-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "generic-data-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return GenericDataFormBuilder.ID;
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
        I_ValidationFormData validationFormData = null;
        switch (eds.getEdsValidLvl()) {
        case EdsEds.VALIDATION_LVL_HIGH:
            validationFormData = (I_ValidationFormData) controller.getFormDataModel(eds, EdsHighValidationFormData.class);
            break;

        case EdsEds.VALIDATION_LVL_LOW:
            validationFormData = (I_ValidationFormData) controller.getFormDataModel(eds, EdsLowValidationFormData.class);
            break;
        }
        if (eds.getEdsComments() != null) {
            vLBLcomments.setValue(eds.getEdsComments());
        } else {
            vLBLcomments.setValue(bundle.getString("generic-data-com-no-com"));
        }
        chronogram.refreshView(validationFormData, eds.getEdsProject());
        refreshRecordInfoData();
        refreshContactInfoData();
        refreshProjectInfoData();
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
     * Variable to store EDS chronogram details.
     */
    private EdsChronogram chronogram;
    /**
     * EDS details.
     */
    private EdsEds eds;
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
     * Label for comments.
     */
    private Comment vLBLcomments;
    /**
     * Resource bundle to read configuration file.
     */
    private ResourceBundle bundle;
    // record info
    /**
     * Label for 'Modification date' value.
     */
    private Label vLBLmodifDateValue;
    /**
     * Label for 'Device name' value.
     */
    private Label vLBLnameValue;
    /**
     * Label for 'Device description' value.
     */
    private Label vLBLdescriptionValue;
    /**
     * Label for 'Validation level' value.
     */
    private Label vLBLvalidationLevelValue;
    /**
     * Label for 'Device stage' value.
     */
    private Label vLBLcomponentStageValue;
    /**
     * Label for 'Subtype 1' value.
     */
    private Label vLBLsubtype1Value;
    /**
     * Label for 'Subtype 2' value.
     */
    private Label vLBLsubtype2Value;
    /**
     * Label for 'Device type (Battery & Alternator balance) ' value.
     */
    private Label vLBLcomponentTypeNameValue;
    /**
     * Label for 'EDS model' value.
     */
    private Label vLBLorganFamilyValue;
    /**
     * Label for 'EDS model' value.
     */
    private Label vLBLsubAltFuncValue;
    /**
     * Label for 'EDS model' value.
     */
    private Label vLBLsubBatFuncValue;
    /**
     * Label for 'EDS model' value.
     */
    private Label vLBLsubAltSysValue;
    /**
     * Label for 'EDS model' value.
     */
    private Label vLBLsubBatSysValue;
    /**
     * Layout for numbers.
     */
    private VerticalLayout numbersLayout;
    // contact info
    /**
     * Label for 'Sheet administrator' value.
     */
    private Label vLBLadminValue;
    /**
     * Label for 'Development leader' value.
     */
    private Label vLBLofficerValue;
    /**
     * Label for 'Device development department' value.
     */
    private Label vLBLserviceValue;
    /**
     * Label for 'PSA leader' value.
     */
    private Label vLBLmanagerValue;
    /**
     * Label for 'Assignment' value.
     */
    private Label vLBLaffectationValue;
    /**
     * Label for 'Supplier' value.
     */
    private Label vLBLsupplierValue;
    /**
     * Label for 'Device connected to LV / HV network' value.
     */
    private Label vLBLbttbt;
    /**
     * Label for 'Initializer project' value.
     */
    private Label vLBLprojectSetterCount;
    /**
     * Layout for project partners.
     */
    private VerticalLayout projectPerimetersLayout;
    /**
     * Layout for EDS partners.
     */
    private VerticalLayout edsPerimetersLayout;
    // project info
    /**
     * Layout for follower projects.
     */
    private VerticalLayout followerProjectsLayout;
    /**
     * Layout for Chs projects.
     */
    private VerticalLayout chsProjectsLayout;
    /**
     * Link for 'Initializer project'.
     */
    private Link vLNKprojectSetter;

    private HorizontalLayout sapReferenceLayoutFct;

    private HorizontalLayout sapReferenceLayoutFnr;

    /**
     * Initialization method.
     */
    private void init() {
        this.mainLayout = new GridLayout(2, 5);
        this.mainLayout.setWidth("100%");

        this.vLBLcomments = new Comment(controller);

        this.recordInfoLayout = getRecordInfos();
        this.contactInfoLayout = getContactsInfos();
        this.projectInfoLayout = getProjectInfos();

        Panel commentsPanel = new Panel(bundle.getString("eds-comment-label"));
        commentsPanel.addComponent(vLBLcomments);
        Panel recordPanel = new Panel(bundle.getString("generic-data-organe-title"), recordInfoLayout);
        Panel contactPanel = new Panel(bundle.getString("generic-data-represent-title"), contactInfoLayout);
        Panel projectInfoPanel = new Panel(bundle.getString("generic-data-link-title"), projectInfoLayout);

        contactPanel.setHeight("100%");
        commentsPanel.setWidth("100%");

        this.chronogram = new EdsChronogram(controller);

        mainLayout.addComponent(chronogram, 0, 0, 1, 0);
        mainLayout.addComponent(recordPanel);
        mainLayout.addComponent(contactPanel);
        mainLayout.addComponent(projectInfoPanel, 0, 2, 1, 2);
        mainLayout.addComponent(commentsPanel, 0, 4, 1, 4);

        addComponent(mainLayout);
    }

    /**
     * This method generates Record info component. This method generates the UI for 'Device-EDS' panel.
     * 
     * @return Record info Component.
     */
    private Layout getRecordInfos() {
        GridLayout layout = new GridLayout(2, 19);
        layout.setSpacing(true);
        layout.setMargin(true);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-ref") + " :"));
        Label vLBLref = new Label(eds.toExplicitRef());
        vLBLref.setSizeUndefined();
        layout.addComponent(vLBLref, 1, 0);

        Date creaDate = eds.getEdsCreaDate();
        if (creaDate == null) {
            creaDate = new Date();
        }
        layout.addComponent(new Label(bundle.getString("eds-creation-date") + ":"));
        layout.addComponent(new Label(DateFormat.getDateInstance().format(creaDate)), 1, 1);

        Date modifDate = eds.getEdsModifDate();
        if (modifDate == null) {
            modifDate = new Date();
            eds.setEdsModifDate(modifDate);
        }
        layout.addComponent(new Label(bundle.getString("eds-modif-date") + " :"));
        vLBLmodifDateValue = new Label(DateFormat.getDateInstance().format(modifDate));
        vLBLmodifDateValue.setSizeUndefined();
        layout.addComponent(vLBLmodifDateValue, 1, 2);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-name") + " :"));
        this.vLBLnameValue = new Label(eds.getEdsName());
        layout.addComponent(vLBLnameValue, 1, 3);

        // Ex_conn_95 - EDS description
        layout.addComponent(new Label(bundle.getString("generic-data-organe-desc") + " :"));
        this.vLBLdescriptionValue = new Label(eds.getEdsDescription());
        layout.addComponent(vLBLdescriptionValue, 1, 4);
        // End of Ex_conn_95

        layout.addComponent(new Label(bundle.getString("generic-data-organe-level") + " :"));
        vLBLvalidationLevelValue = new Label(controller.getBundle().getString(EdsEds.getValidationLevelText(eds.getEdsValidLvl())));
        vLBLvalidationLevelValue.setSizeUndefined();
        layout.addComponent(vLBLvalidationLevelValue, 1, 5);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-state") + " : "));
        vLBLcomponentStageValue = new Label(getEdsStageText());
        vLBLcomponentStageValue.setSizeUndefined();
        layout.addComponent(vLBLcomponentStageValue, 1, 6);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-connect")));
        this.vLBLbttbt = new Label();
        setBTTBTvalue(eds.getEdsIsBttbt());
        layout.addComponent(vLBLbttbt, 1, 7);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-type-1") + " :"));
        vLBLsubtype1Value = new Label(getSubType1Text());
        vLBLsubtype1Value.setSizeUndefined();
        layout.addComponent(vLBLsubtype1Value, 1, 8);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-type-2") + " :"));
        vLBLsubtype2Value = new Label(getSubType2Text());
        vLBLsubtype2Value.setSizeUndefined();
        layout.addComponent(vLBLsubtype2Value, 1, 9);

        layout.addComponent(new Label(bundle.getString("menu-project-tab-model") + " :"));
        vLBLcomponentTypeNameValue = new Label(eds.getEdsComponentType().getLocaleCtName(controller.getApplication().getLocale()));
        vLBLcomponentTypeNameValue.setSizeUndefined();
        layout.addComponent(vLBLcomponentTypeNameValue, 1, 10);

        vLBLorganFamilyValue = new Label("");
        vLBLorganFamilyValue.setSizeUndefined();

        vLBLsubAltFuncValue = new Label("");
        vLBLsubAltFuncValue.setSizeUndefined();

        vLBLsubBatFuncValue = new Label("");
        vLBLsubBatFuncValue.setSizeUndefined();

        vLBLsubAltSysValue = new Label("");
        vLBLsubAltSysValue.setSizeUndefined();

        vLBLsubBatSysValue = new Label("");
        vLBLsubBatSysValue.setSizeUndefined();

        if (controller.userHasSufficientRights(EdsRight.APP_EDS_TYPE_ORGANS)) {
            layout.addComponent(new Label(bundle.getString("Admin-lib-ongle-organe-family") + " :"));
            layout.addComponent(vLBLorganFamilyValue, 1, 11);

            layout.addComponent(new Label(bundle.getString("Admin-lib-ongle-sf-alt") + " :"));
            layout.addComponent(vLBLsubAltFuncValue, 1, 12);

            layout.addComponent(new Label(bundle.getString("Admin-lib-ongle-sf-bat") + " :"));
            layout.addComponent(vLBLsubBatFuncValue, 1, 13);

            layout.addComponent(new Label(bundle.getString("Admin-lib-ongle-ss-alt") + " :"));
            layout.addComponent(vLBLsubAltSysValue, 1, 14);

            layout.addComponent(new Label(bundle.getString("Admin-lib-ongle-ss-bat") + " :"));
            layout.addComponent(vLBLsubBatSysValue, 1, 15);

        }
        if (!eds.getEdsNumber96ks().isEmpty()) {
            this.numbersLayout = new VerticalLayout();
            numbersLayout.setSpacing(true);
            numbersLayout.setSizeUndefined();

            layout.addComponent(new Label(bundle.getString("generic-data-organe-96") + " :"));
            refresh96numbers();
            layout.addComponent(numbersLayout, 1, 16);
        }

        layout.addComponent(new Label(bundle.getString("generic-data-organe-96-fct") + " :"), 0, 17);
        sapReferenceLayoutFct = new HorizontalLayout();
        refreshSapReference(sapReferenceLayoutFct, eds.getEdsNumber96fcts());
        layout.addComponent(sapReferenceLayoutFct, 1, 17);

        layout.addComponent(new Label(bundle.getString("generic-data-organe-96-fnr") + " :"), 0, 18);
        sapReferenceLayoutFnr = new HorizontalLayout();
        refreshSapReference(sapReferenceLayoutFnr, eds.getEdsNumber96fnrs());
        layout.addComponent(sapReferenceLayoutFnr, 1, 18);

        return layout;
    }

    private void refreshSapReference(HorizontalLayout sapReferenceLayout, Set<EdsSAPReference> edsNumber96fcts) {

        for (EdsSAPReference edsSAPReference : edsNumber96fcts) {
            Label vLBLprojectName = new Label(edsSAPReference.getReferenceRevisionLabel());
            vLBLprojectName.setWidth("90px");

            sapReferenceLayout.setSpacing(true);
            sapReferenceLayout.addComponent(vLBLprojectName);
        }
    }

    /**
     * This method generates Contacts info component. This method generates the UI for 'User' panel.
     * 
     * @return Contact info Component.
     */
    private Layout getContactsInfos() {
        GridLayout layout = new GridLayout(2, 7);
        layout.setSpacing(true);
        layout.setMargin(true);

        vLBLadminValue = new Label(getUserInfo(eds.getEdsUserByEdsAdminId()));
        vLBLadminValue.setSizeUndefined();
        layout.addComponent(new Label(bundle.getString("generic-data-represent") + " :"));
        layout.addComponent(vLBLadminValue, 1, 0);

        layout.addComponent(new Label(bundle.getString("menu-EDS-charge-dev") + " :"));
        vLBLofficerValue = new Label(getUserInfo(eds.getEdsUserByEdsOfficerId()));
        vLBLofficerValue.setSizeUndefined();
        layout.addComponent(vLBLofficerValue, 1, 1);

        layout.addComponent(new Label(bundle.getString("generic-data-dev-service")));
        vLBLserviceValue = new Label(getServiceText());
        vLBLserviceValue.setSizeUndefined();
        layout.addComponent(vLBLserviceValue, 1, 2);

        layout.addComponent(new Label(bundle.getString("generic-data-represent-responsible") + " :"));
        vLBLmanagerValue = new Label(getUserInfo(eds.getEdsUserByEdsManagerId()));
        vLBLmanagerValue.setSizeUndefined();
        layout.addComponent(vLBLmanagerValue, 1, 3);

        layout.addComponent(new Label(bundle.getString("generic-data-affectation") + " :"));
        vLBLaffectationValue = new Label(getUserInfo(eds.getEdsUserByEdsAffectationUserId()));
        vLBLaffectationValue.setSizeUndefined();
        layout.addComponent(vLBLaffectationValue, 1, 4);

        layout.addComponent(new Label(bundle.getString("generic-data-represent-FNR") + " :"));
        vLBLsupplierValue = new Label(getSupplierName());
        vLBLsupplierValue.setSizeUndefined();
        layout.addComponent(vLBLsupplierValue, 1, 5);

        this.projectPerimetersLayout = new VerticalLayout();
        projectPerimetersLayout.setSpacing(true);
        projectPerimetersLayout.setSizeUndefined();

        layout.addComponent(new Label(bundle.getString("generic-data-represent-partner-proj") + " :"));
        layout.addComponent(projectPerimetersLayout, 1, 6);

        this.edsPerimetersLayout = new VerticalLayout();
        edsPerimetersLayout.setSpacing(true);
        edsPerimetersLayout.setSizeUndefined();

        layout.addComponent(new Label(bundle.getString("generic-data-represent-partner-EDS") + " :"));
        layout.addComponent(edsPerimetersLayout, 1, 7);
        refreshPerimeters();

        return layout;
    }

    /**
     * This method generates Project info component. This method generates the UI for 'Project links' panel.
     * 
     * @return Project info Component.
     */
    private Layout getProjectInfos() {
        GridLayout projectLayout = new GridLayout(2, 3);
        projectLayout.setSpacing(true);
        projectLayout.setMargin(true);

        projectLayout.addComponent(new Label(bundle.getString("filter-project-launcher") + " : "));

        vLNKprojectSetter = new Link(eds.getEdsProject().getPName(), new ExternalResource("#"
                + FragmentManager.formatURLFragmentForProject(eds.getEdsProject().getPId())));
        vLNKprojectSetter.setWidth("100px");
        vLBLprojectSetterCount = new Label();

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.addComponent(vLNKprojectSetter);
        layout.addComponent(new Label("<b>x</b>", Label.CONTENT_XHTML));
        layout.addComponent(vLBLprojectSetterCount);
        projectLayout.addComponent(layout, 1, 0);

        projectLayout.addComponent(new Label(bundle.getString("generic-data-link-follow") + " : "));
        followerProjectsLayout = new VerticalLayout();
        followerProjectsLayout.setSpacing(true);
        projectLayout.addComponent(followerProjectsLayout, 1, 1);

        projectLayout.addComponent(new Label(bundle.getString("generic-data-chs") + " : "));
        chsProjectsLayout = new VerticalLayout();
        chsProjectsLayout.setSpacing(true);
        projectLayout.addComponent(chsProjectsLayout, 1, 2);
        refreshProjectInfoData();

        return projectLayout;
    }

    /**
     * This method is used to refresh record info panel.
     */
    private void refreshRecordInfoData() {
        vLBLmodifDateValue.setValue(DateFormat.getDateInstance().format(eds.getEdsModifDate()));
        vLBLnameValue.setValue(eds.getEdsName());
        vLBLdescriptionValue.setValue(eds.getEdsDescription());
        vLBLvalidationLevelValue.setValue(controller.getBundle().getString(EdsEds.getValidationLevelText(eds.getEdsValidLvl())));
        vLBLcomponentStageValue.setValue(getEdsStageText());
        vLBLorganFamilyValue.setValue(EDSTools.getWordingValueByLocale(eds.getEdsOrganFamily(), getApplication().getLocale()));
        vLBLsubAltFuncValue.setValue(EDSTools.getWordingValueByLocale(eds.getEdsAltSubFct(), getApplication().getLocale()));
        vLBLsubBatFuncValue.setValue(EDSTools.getWordingValueByLocale(eds.getEdsBatSubFct(), getApplication().getLocale()));
        vLBLsubAltSysValue.setValue(EDSTools.getWordingValueByLocale(eds.getEdsAltSubSys(), getApplication().getLocale()));
        vLBLsubBatSysValue.setValue(EDSTools.getWordingValueByLocale(eds.getEdsBatSubSys(), getApplication().getLocale()));
        setBTTBTvalue(eds.getEdsIsBttbt());
        vLBLsubtype1Value.setValue(getSubType1Text());
        vLBLsubtype2Value.setValue(getSubType2Text());
        vLBLcomponentTypeNameValue.setValue(eds.getEdsComponentType().getLocaleCtName(controller.getApplication().getLocale()));
        if (!eds.getEdsNumber96ks().isEmpty()) {
            refresh96numbers();
        }

        sapReferenceLayoutFct.removeAllComponents();
        refreshSapReference(sapReferenceLayoutFct, eds.getEdsNumber96fcts());
        sapReferenceLayoutFnr.removeAllComponents();
        refreshSapReference(sapReferenceLayoutFnr, eds.getEdsNumber96fnrs());
    }

    /**
     * This method is used to refresh contact info panel.
     */
    private void refreshContactInfoData() {
        vLBLadminValue.setValue(getUserInfo(eds.getEdsUserByEdsAdminId()));
        vLBLofficerValue.setValue(getUserInfo(eds.getEdsUserByEdsOfficerId()));
        vLBLmanagerValue.setValue(getUserInfo(eds.getEdsUserByEdsManagerId()));
        vLBLserviceValue.setValue(getServiceText());
        vLBLsupplierValue.setValue(getSupplierName());
        refreshPerimeters();
    }

    /**
     * This method is used to refresh project info panel.
     */
    private void refreshProjectInfoData() {
        vLBLprojectSetterCount.setValue(eds.getEdsPLaunchCount() + "(" + bundle.getString("generic-data-occurence-number") + ")");
        vLNKprojectSetter.setCaption(eds.getEdsProject().getPName());
        vLNKprojectSetter.setResource(new ExternalResource("#" + FragmentManager.formatURLFragmentForProject(eds.getEdsProject().getPId())));
        refreshFollowerProjects();
        refreshChsProjects();
    }

    /**
     * This method is used to retrieve Stage name.
     * 
     * @return Stage name.
     */
    private String getEdsStageText() {
        int stage = controller.retrieveEdsStage(eds, eds.getEdsProject()); // TODO : Mettre le projet courant
        // au lieu de pojet lanceur
        return EdsApplicationController.getStageText(stage);
    }

    /**
     * This method is used to retrieve the Subtype 1 value.
     * 
     * @return Subtype 1 value.
     */
    private String getSubType1Text() {
        String subType1;
        Integer subTypeValue = eds.getEdsSubtype1();
        if (subTypeValue == null) {
            subTypeValue = -1;
        }
        switch (subTypeValue) {
        case EdsApplicationController.ST1_BT:
            subType1 = EdsApplicationController.ST1_BT_TEXT;
            break;
        case EdsApplicationController.ST1_TBT:
            subType1 = EdsApplicationController.ST1_TBT_TEXT;
            break;
        default:
            subType1 = "-";
            break;
        }
        return subType1;
    }

    /**
     * This method is used to retrieve the Subtype2 value.
     * 
     * @return Subtype2 value.
     */
    private String getSubType2Text() {
        String subType2;
        Integer subTypeValue2 = eds.getEdsSubtype2();
        if (subTypeValue2 == null) {
            subTypeValue2 = -1;
        }
        switch (subTypeValue2) {
        case EdsApplicationController.ST2_GMP:
            subType2 = EdsApplicationController.ST2_GMP_TEXT;
            break;
        case EdsApplicationController.ST2_LAS:
            subType2 = EdsApplicationController.ST2_LAS_TEXT;
            break;
        default:
            subType2 = "-";
            break;
        }
        return subType2;
    }

    /**
     * This method is used to retrieve the User info.
     * 
     * @param user User detals.
     * @return User info [FirstName] [LastName] [PSAId]
     */
    private String getUserInfo(EdsUser user) {
        String userInfo = "-";
        if (user != null) {
            userInfo = user.getUFirstname() + " " + user.getULastname() + " " + user.getUPsaId();
        }
        return userInfo;
    }

    /**
     * This method is used to retrieve value for 'Device development department'.
     * 
     * @return value for 'Device development department'.
     */
    private String getServiceText() {
        String service = "-";
        if (eds.getEdsUserByEdsOfficerId() != null) {
            service = eds.getEdsUserByEdsOfficerId().getUService();
        }
        return service;
    }

    /**
     * This method is used to retrieve the supplier name.
     * 
     * @return Name of the supplier.
     */
    private String getSupplierName() {
        EdsSupplier supplier = eds.getEdsSupplier();
        String supplierName = "-";
        if (supplier != null) {
            supplierName = supplier.getSName();
        }
        return supplierName;
    }

    /**
     * This method is used to refresh the Partners list.
     */
    private void refreshPerimeters() {
        // Partner projects
        projectPerimetersLayout.removeAllComponents();
        for (EdsPerimeter perimeter : EdsApplicationController.getAllAuthorizedPerimeters(eds, false)) {
            projectPerimetersLayout.addComponent(new Label(perimeter.getPeName()));
        }

        // Partner record
        edsPerimetersLayout.removeAllComponents();
        Set<EdsPerimeter> sortedEdsPerimeter = new TreeSet<EdsPerimeter>(new Comparator<EdsPerimeter>() {
            public int compare(EdsPerimeter p1, EdsPerimeter p2) {
                return p1.getPeName().compareTo(p2.getPeName());
            }
        });
        sortedEdsPerimeter.addAll(eds.getEdsPerimeters());
        for (EdsPerimeter perimeter : sortedEdsPerimeter) {
            edsPerimetersLayout.addComponent(new Label(perimeter.getPeName()));
        }
    }

    /**
     * This method is used to refresh the 96k part-numbers.
     */
    private void refresh96numbers() {
        numbersLayout.removeAllComponents();
        for (EdsNumber96k number : (Set<EdsNumber96k>) eds.getEdsNumber96ks()) {
            numbersLayout.addComponent(new Label(number.getNValue()));
        }
    }

    /**
     * This method is used to refresh the Follower projects.
     */
    private void refreshFollowerProjects() {
        followerProjectsLayout.removeAllComponents();
        if (eds.getEdsProjectEdses().isEmpty()) {
            followerProjectsLayout.addComponent(new Label("-"));
        } else {
            for (EdsProjectEds edsProjectEds : (Set<EdsProjectEds>) eds.getEdsProjectEdses()) {
                followerProjectsLayout.addComponent(addFollowerProject(edsProjectEds));
            }
        }
        followerProjectsLayout.setSizeUndefined();
    }

    /**
     * This method is used to refresh the Chs projects.
     */
    private void refreshChsProjects() {
        chsProjectsLayout.removeAllComponents();
        if (eds.getEdsChs().isEmpty()) {
            chsProjectsLayout.addComponent(new Label("-"));
        } else {
            for (Chs chs : eds.getEdsChs()) {
                chsProjectsLayout.addComponent(new Label(chs.getPartNumber()));
            }
        }
        chsProjectsLayout.setSizeUndefined();
    }

    /**
     * This method sets the value for 'Device connected to LV / HV network?' question.
     * 
     * @param isBTTBT value for 'Device connected to LV / HV network?' question.
     */
    private void setBTTBTvalue(int isBTTBT) {
        if (isBTTBT == EdsEds.IS_NOT_BTTBT) {
            vLBLbttbt.setValue(bundle.getString("consolid-qcf-non"));
        } else {
            vLBLbttbt.setValue(bundle.getString("consolid-qcf-oui"));
        }
    }

    /**
     * This method is used to add follower projects.
     * 
     * @param value Project to be added.
     * @return component containing follower project.
     */
    private Component addFollowerProject(final EdsProjectEds value) {
        Label vLBLcount = new Label();
        vLBLcount.setWidth("20px");
        vLBLcount.setValue(value.getPedsCount().toString());
        Link vLNKprojectName = new Link(value.getEdsProject().getPName(), new ExternalResource("#"
                + FragmentManager.formatURLFragmentForProject(value.getEdsProject().getPId())));
        vLNKprojectName.setWidth("100px");
        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.addComponent(vLNKprojectName);
        layout.addComponent(new Label("<b>x</b>", Label.CONTENT_XHTML));
        layout.addComponent(vLBLcount);

        return layout;
    }
}
