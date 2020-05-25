package com.inetpsa.eds.application.content.eds.currentconsumption.robust;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.vaadin.dialogs.ConfirmDialog;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.InfosButton;
import com.inetpsa.eds.application.content.eds.composants.TypeAlimentation;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.ConsumptionTableEdit;
import com.inetpsa.eds.application.content.eds.currentconsumption.tables.RobustSupplyUseCaseFormEditView;
import com.inetpsa.eds.dao.model.EdsPrimarySupply;
import com.inetpsa.eds.dao.model.EdsRobustSupply;
import com.inetpsa.eds.dao.model.EdsRobustSupplyUseCase;
import com.inetpsa.eds.dao.model.EdsSupply;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class provide component for editing view of Current consumption supply tab of Robust stage
 * 
 * @author Clément Fleury @ Alter Frame
 */
public class RobustSupplyEditView extends A_EdsEditForm implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // PRIVATE
    /**
     * Variable to hold value of EdsSupply
     */
    private EdsSupply edsSupply;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsPrimarySupply
     */
    private EdsPrimarySupply edsPrimarySupply;
    /**
     * Variable to hold value of EdsRobustSupply
     */
    private EdsRobustSupply edsRobustSupply;
    /**
     * Variable to hold value of boolean for renewal of supply
     */
    private boolean reconduire;

    // Elements
    /**
     * Variable to hold value of ConsumptionTableEdit
     */
    private ConsumptionTableEdit robusteTableEdit;
    /**
     * Variable to hold value of TextField for power supply name
     */
    private TextField vTFName;
    /**
     * Variable to hold value of TypeAlimentation
     */
    private TypeAlimentation typeAlimentation;

    // Default Use Case

    /**
     * Variable to hold value of Button
     */
    private Button vBAdd;
    /**
     * Variable to hold value of VerticalLayout
     */
    private VerticalLayout vLUseCase;
    /**
     * Variable to hold list of CourantAppelActivationFormEditView
     */
    private ArrayList<RobustSupplyUseCaseFormEditView> useCaseEditViewsList;
    /**
     * Variable to hold list of CourantAppelActivationFormEditView to delete
     */
    private ArrayList<RobustSupplyUseCaseFormEditView> useCaseEditViewsListToDelete;

    /**
     * Variable to hold value of TextField for default UseCase name
     */
    private TextField vTFUCName;
    /**
     * Variable to hold value of TextField for Iasleep (A)
     */
    private TextField vTFIveille;
    /**
     * Variable to hold value of Label for Iasleep (A) Edited By
     */
    private Label vLIveilleRenseigner;
    /**
     * Variable to hold value of TextField for Iasleep (A) Notes
     */
    private TextField vTFIveilleCom;
    /**
     * Variable to hold value of TextField for Iawake mode non functioning mode(A)
     */
    private TextField vTFIreveille;
    /**
     * Variable to hold value of Label for Iawake mode non functioning mode(A) Edited By
     */
    private Label vLIreveilleRenseigner;
    /**
     * Variable to hold value of TextField for Iawake mode non functioning mode(A) Notes
     */
    private TextField vTFIreveilleCom;
    /**
     * Variable to hold value of TextField for Inom Stab (A)
     */
    private TextField vTFInomStab;
    /**
     * Variable to hold value of Label for Inom Stab (A) Edited By
     */
    private Label vLInomStabRenseigner;
    /**
     * Variable to hold value of TextField for Inom Stab (A) Notes
     */
    private TextField vTFInomStabCom;
    /**
     * Variable to hold value of TextField for Iworst stab (A)
     */
    private TextField vTFIpirecas;
    /**
     * Variable to hold value of Label for Iworst stab (A) Edited By
     */
    private Label vLIpirecasRenseigner;
    /**
     * Variable to hold value of TextField for Iworst stab (A) Notes
     */
    private TextField vTFIpirecasCom;
    /**
     * Variable to hold value of TextField for Umin (V)
     */
    private TextField vTFUmin;
    /**
     * Variable to hold value of Label for Umin (V) Edited By
     */
    private Label vLUminRenseigner;
    /**
     * Variable to hold value of TextField for Umin (V) notes
     */
    private TextField vTFUminCom;
    /**
     * Variable to hold value of TextField for Umed(V)
     */
    private TextField vTFUmoy;
    /**
     * Variable to hold value of Label for Umed(V) Edited By
     */
    private Label vLUmoyRenseigner;
    /**
     * Variable to hold value of TextField for Umed(V) Notes
     */
    private TextField vTFUmoyCom;
    /**
     * Variable to hold value of TextField for Umax(V)
     */
    private TextField vTFUmax;
    /**
     * Variable to hold value of Label for Umax(V) Edited By
     */
    private Label vLUmaxRenseigner;
    /**
     * Variable to hold value of TextField for Umax(V) Notes
     */
    private TextField vTFUmaxCom;
    /**
     * Variable to hold value of TextField for Ipeak (A) (Inrush Current)
     */
    private TextField vTFImax;
    /**
     * Variable to hold value of Label for Ipeak (A) (Inrush Current) Edited By
     */
    private Label vLImaxRenseigner;
    /**
     * Variable to hold value of TextField for Ipeak (A) (Inrush Current) Note
     */
    private TextField vTFImaxCom;
    /**
     * Variable to hold value of TextField for Tpeak (s) (inrush current duration)
     */
    private TextField vTFTImax;
    /**
     * Variable to hold value of Label for Tpeak (s) (inrush current duration) Edited By
     */
    private Label vLTImaxRenseigner;
    /**
     * Variable to hold value of TextField for Tpeak (s) (inrush current duration) Notes
     */
    private TextField vTFTImaxCom;
    /**
     * Variable to hold value of TextField for Robust table
     */
    private TextField vTFCarac;
    /**
     * Variable to hold value of TextField for Robust table Edited by
     */
    private Label vLCaracRenseigner;
    /**
     * Variable to hold value of TextField for Robust table notes
     */
    private TextField vTFCaracCom;
    /**
     * Variable to hold value of TextField for Title of Robust table
     */
    private Label vLCaracTitle;
    /**
     * Variable to hold value of TextField for Ipark asleep (A)
     */
    private TextField vTFTIConsoParc;
    /**
     * Variable to hold value of Label for Ipark asleep (A) Edited By
     */
    private Label vLIConsoParcRenseigner;
    /**
     * Variable to hold value of TextField for Ipark asleep (A) Notes
     */
    private TextField vTFTIConsoParcCom;
    /**
     * Variable to hold value of String representing User
     */
    private String user;
    /**
     * Variable to hold value of InfosButton
     */
    private InfosButton infosButton;
    /**
     * List of use case to delete
     */
    private Collection<EdsRobustSupplyUseCase> useCaseToDelete;

    // New values
    /**
     * Variable to hold value of TextField for Tnom stab
     */
    private TextField vTnomStab;
    /**
     * Variable to hold value of Label for Tnom stab Edited By
     */
    private Label vTnomStabRenseigner;
    /**
     * Variable to hold value of TextField for Tnom stab Notes
     */
    private TextField vTnomStabCom;
    /**
     * Variable to hold value of TextField for TpireCas
     */
    private TextField vTpireCas;
    /**
     * Variable to hold value of Label for TpireCas stab Edited By
     */
    private Label vTpireCasRenseigner;
    /**
     * Variable to hold value of TextField for TpireCas Notes
     */
    private TextField vTpireCasCom;
    /**
     * Variable to hold value of TextField for Idysf
     */
    private TextField vIdysf;
    /**
     * Variable to hold value of Label for Idysf stab Edited By
     */
    private Label vIdysfRenseigner;
    /**
     * Variable to hold value of TextField for Tdysf Notes
     */
    private TextField vIdysfCom;
    /**
     * Variable to hold value of TextField for Idysf
     */
    private TextField vTdysf;
    /**
     * Variable to hold value of Label for Tdysf stab Edited By
     */
    private Label vTdysfRenseigner;
    /**
     * Variable to hold value of TextField for Tdysf Notes
     */
    private TextField vTdysfCom;
    /**
     * Variable to hold value of TextField for Imst
     */
    private TextField vImst;
    /**
     * Variable to hold value of Label for Imst stab Edited By
     */
    private Label vImstRenseigner;
    /**
     * Variable to hold value of TextField for Imst Notes
     */
    private TextField vImstCom;
    /**
     * Variable to hold value of TextField for Tmst
     */
    private TextField vTmst;
    /**
     * Variable to hold value of Label for Tmst stab Edited By
     */
    private Label vTmstRenseigner;
    /**
     * Variable to hold value of TextField for Tmst Notes
     */
    private TextField vTmstCom;

    /**
     * Default constructor
     */
    public RobustSupplyEditView() {
        init();

    }

    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     * @param edsPrimarySupply object of EdsPrimarySupply
     * @param edsRobustSupply object of EdsRobustSupply
     * @param edsSupply Object of EdsSupply
     * @param reconduire Check to renew
     */
    public RobustSupplyEditView(EdsApplicationController controller, EdsPrimarySupply edsPrimarySupply, EdsRobustSupply edsRobustSupply,
            EdsSupply edsSupply, boolean reconduire) {

        this.controller = controller;
        this.edsPrimarySupply = edsPrimarySupply;
        this.edsRobustSupply = edsRobustSupply;
        this.edsSupply = edsSupply;
        this.reconduire = reconduire;
        init();
    }

    private void createRobustSupplyPanel(final EdsRobustSupplyUseCase useCase) {
        final RobustSupplyUseCaseFormEditView useCaseFormEditView = new RobustSupplyUseCaseFormEditView(useCase, controller) {
            // remove button
            public void buttonClick(ClickEvent event) {
                // confirm dialog
                ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("pop-warning-title"), controller.getBundle()
                        .getString("eds-supply-usecase-remove-message"), controller.getBundle().getString("eds-supply-usecase-remove-oui"),
                        controller.getBundle().getString("eds-supply-usecase-remove-non"), new ConfirmDialog.Listener() {
                            public void onClose(ConfirmDialog cd) {
                                if (cd.isConfirmed()) {
                                    removeUseCase();
                                }
                            }
                        });

            }

            public void removeUseCase() {
                vLUseCase.removeComponent(this);
                useCaseEditViewsList.remove(this);
                edsRobustSupply.getUseCases().remove(useCase);

                useCaseEditViewsListToDelete.add(this);
            }
        };

        addUseCase(useCaseFormEditView, useCase.isRsucRemove());
    }

    /**
     * Initialize Robust Supply Edit View
     */
    private void init() {

        // current user
        this.user = controller.getAuthenticatedUser().getUFirstname() + " " + controller.getAuthenticatedUser().getULastname();

        // list of Use Case Edit views
        useCaseEditViewsList = new ArrayList<RobustSupplyUseCaseFormEditView>();

        // list of Use Case Edit view deleted
        useCaseEditViewsListToDelete = new ArrayList<RobustSupplyUseCaseFormEditView>();

        // global visual config
        setSpacing(true);
        setMargin(true);

        // Supply Name Field
        GridLayout g = new GridLayout(2, 2);
        HorizontalLayout hlName = new HorizontalLayout();
        hlName.setSpacing(true);
        Label vLName = new Label(controller.getBundle().getString("current-conso-list-alim-modif-name") + " :");
        hlName.addComponent(vLName);
        vTFName = new TextField();
        vTFName.setEnabled(false);
        hlName.addComponent(vTFName);
        g.addComponent(hlName, 0, 0);

        typeAlimentation = new TypeAlimentation(controller);
        g.setWidth("100%");
        g.addComponent(typeAlimentation, 0, 1);

        infosButton = new InfosButton(controller);
        g.addComponent(infosButton, 1, 1);
        g.setComponentAlignment(infosButton, Alignment.MIDDLE_RIGHT);

        addComponent(g);

        // separator
        addComponent(new Label("<br /><hr /><br />", Label.CONTENT_XHTML));

        // Default UseCase
        HorizontalLayout hlUCName = new HorizontalLayout();
        hlUCName.setSpacing(true);
        Label vLUCName = new Label(controller.getBundle().getString("current-conso-list-alim-modif-usecase-name") + " :");
        hlUCName.addComponent(vLUCName);
        vTFUCName = new TextField();
        vTFUCName.setRequired(true);
        vTFUCName.setRequiredError(MessageFormat.format(controller.getBundle().getString("eds-field-not-empty"), vLName.getValue().toString()));
        hlUCName.addComponent(vTFUCName);
        addComponent(hlUCName);

        int i = 13;

        // Edit data in a Table
        robusteTableEdit = new ConsumptionTableEdit(controller);
        robusteTableEdit.setWidth("100%");
        robusteTableEdit.setPageLength(i);

        vTFIveille = new TextField();
        vTFIveilleCom = new TextField();
        vLIveilleRenseigner = new Label();

        vTFIreveille = new TextField();
        vTFIreveilleCom = new TextField();
        vLIreveilleRenseigner = new Label();

        vTFInomStab = new TextField();
        vTFInomStabCom = new TextField();
        vLInomStabRenseigner = new Label();

        vTFIpirecas = new TextField();
        vTFIpirecasCom = new TextField();
        vLIpirecasRenseigner = new Label();

        vTFImax = new TextField();
        vTFImaxCom = new TextField();
        vLImaxRenseigner = new Label();

        vTFTImax = new TextField();
        vTFTImaxCom = new TextField();
        vLTImaxRenseigner = new Label();

        vTFTIConsoParc = new TextField();
        vLIConsoParcRenseigner = new Label();
        vTFTIConsoParcCom = new TextField();

        // New values
        vTnomStab = new TextField();
        vTnomStabRenseigner = new Label();
        vTnomStabCom = new TextField();

        vTpireCas = new TextField();
        vTpireCasRenseigner = new Label();
        vTpireCasCom = new TextField();

        vIdysf = new TextField();
        vIdysfRenseigner = new Label();
        vIdysfCom = new TextField();

        vTdysf = new TextField();
        vTdysfRenseigner = new Label();
        vTdysfCom = new TextField();

        vImst = new TextField();
        vImstRenseigner = new Label();
        vImstCom = new TextField();

        vTmst = new TextField();
        vTmstRenseigner = new Label();
        vTmstCom = new TextField();

        vTFIveilleCom.setMaxLength(128);
        vTFIreveilleCom.setMaxLength(128);
        vTFInomStabCom.setMaxLength(128);
        vTFIpirecasCom.setMaxLength(128);
        vTFTIConsoParcCom.setMaxLength(128);
        // New values
        vTnomStabCom.setMaxLength(128);
        vTpireCasCom.setMaxLength(128);
        vIdysfCom.setMaxLength(128);
        vTdysfCom.setMaxLength(128);
        vImstCom.setMaxLength(128);
        vTmstCom.setMaxLength(128);

        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-veil"), vTFIveille,
                vLIveilleRenseigner, vTFIveilleCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-rev"), vTFIreveille,
                vLIreveilleRenseigner, vTFIreveilleCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-nom"), vTFInomStab,
                vLInomStabRenseigner, vTFInomStabCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tnom-stab"), vTnomStab,
                vTnomStabRenseigner, vTnomStabCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-prem-pire"), vTFIpirecas,
                vLIpirecasRenseigner, vTFIpirecasCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tpire-cas"), vTpireCas,
                vTpireCasRenseigner, vTpireCasCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Idysf"), vIdysf,
                vIdysfRenseigner, vIdysfCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tdysf"), vTdysf,
                vTdysfRenseigner, vTdysfCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Imst"), vImst,
                vImstRenseigner, vImstCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tmst"), vTmst,
                vTmstRenseigner, vTmstCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-pic"), vTFImax,
                vLImaxRenseigner, vTFImaxCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Tpic"), vTFTImax,
                vLTImaxRenseigner, vTFTImaxCom);
        robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-veil-parc"),
                vTFTIConsoParc, vLIConsoParcRenseigner, vTFTIConsoParcCom);

        // if Supply is of BT type
        if (edsRobustSupply.getRsedsTbtBt().equals("BT")) {
            i += 3;

            vTFUmin = new TextField();
            vTFUminCom = new TextField();
            vLUminRenseigner = new Label();

            vTFUmoy = new TextField();
            vTFUmoyCom = new TextField();
            vLUmoyRenseigner = new Label();

            vTFUmax = new TextField();
            vTFUmaxCom = new TextField();
            vLUmaxRenseigner = new Label();

            vTFCarac = new TextField();
            vTFCaracCom = new TextField();
            vLCaracRenseigner = new Label();
            vLCaracTitle = new Label(edsRobustSupply.getRsedsUcaracName() + " (V)");

            vTFUmin.setMaxLength(128);
            vTFUmoyCom.setMaxLength(128);
            vTFUmaxCom.setMaxLength(128);
            vTFCaracCom.setMaxLength(128);

            robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Umin"), vTFUmin,
                    vLUminRenseigner, vTFUminCom);

            robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Umoy"), vTFUmoy,
                    vLUmoyRenseigner, vTFUmoyCom);

            robusteTableEdit.addOrderToContainer(robusteTableEdit, controller.getBundle().getString("current-conso-tab-data-rob-Umax"), vTFUmax,
                    vLUmaxRenseigner, vTFUmaxCom);

            if (edsRobustSupply.getRsedsUcaracName() != null && !edsRobustSupply.getRsedsUcaracName().equals("")) {
                i++;
                robusteTableEdit.addOrderToContainer(robusteTableEdit, vLCaracTitle, vTFCarac, vLCaracRenseigner, vTFCaracCom);
            }

        }

        robusteTableEdit.setPageLength(i);
        discardChanges();

        addComponent(robusteTableEdit);

        // Other Use cases
        vLUseCase = new VerticalLayout();
        vLUseCase.setSpacing(true);
        vLUseCase.setWidth("100%");
        addComponent(vLUseCase);

        // Add button
        vBAdd = new Button();
        vBAdd.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png "));
        vBAdd.setStyleName(BaseTheme.BUTTON_LINK);
        vBAdd.setDescription(controller.getBundle().getString("eds-add-tab"));
        vBAdd.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -2252198359566834506L;

            public void buttonClick(ClickEvent event) {
                final EdsRobustSupplyUseCase useCase = new EdsRobustSupplyUseCase(UUID.randomUUID().toString());
                useCase.setRobustSupply(edsRobustSupply);
                useCase.setRsucRemove(true);
                edsRobustSupply.getUseCases().add(useCase);

                createRobustSupplyPanel(useCase);
            }
        });
        addComponent(vBAdd);

        for (EdsRobustSupplyUseCase useCase : edsRobustSupply.getUseCases()) {
            createRobustSupplyPanel(useCase);
        }
    }

    /**
     * This method add a Use Case
     * 
     * @param useCaseFormEditView Object of RobustSupplyUseCaseFormEditView
     * @param b Boolean to check of RobustSupplyUseCaseFormEditView already exist
     */
    public void addUseCase(RobustSupplyUseCaseFormEditView useCaseFormEditView, boolean b) {
        if (b) {
            vLUseCase.addComponent(useCaseFormEditView);
        } else {
            vLUseCase.addComponent(useCaseFormEditView, 0);
        }

        useCaseEditViewsList.add(useCaseFormEditView);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        // if the supply has been reconducted
        if (reconduire) {
            vTFName.setValue(edsPrimarySupply.getPsedsSupplyName());

            typeAlimentation.setValue(edsPrimarySupply.getWording());
            typeAlimentation.setCommentaire(edsPrimarySupply.getPsedsSupplyTypeSupplyNameComment() == null ? "" : edsPrimarySupply
                    .getPsedsIPirecasComment());
            typeAlimentation.setUser(edsPrimarySupply.getPsedsSupplyTypeSupplyNameIformBy());

            vTFUCName.setValue(edsRobustSupply.getRsedsUseCaseName() == null ? "" : edsRobustSupply.getRsedsUseCaseName());

            vTFIveille.setValue(EDSTools.convertFloatToVide(edsPrimarySupply.getPsedsIVeille()));
            vTFIveilleCom.setValue(edsPrimarySupply.getPsedsIVeilleComment() == null ? "" : edsPrimarySupply.getPsedsIVeilleComment());
            vLIveilleRenseigner.setValue(edsPrimarySupply.getPsedsIVeilleIformBy());

            vTFIreveille.setValue(EDSTools.convertFloatToVide(edsPrimarySupply.getPsedsIReveilleInactif()));
            vTFIreveilleCom.setValue(edsPrimarySupply.getPsedsReveilleComment() == null ? "" : edsPrimarySupply.getPsedsReveilleComment());
            vLIreveilleRenseigner.setValue(edsPrimarySupply.getPsedsReveilleIformBy());

            vTFInomStab.setValue(EDSTools.convertFloatToVide(edsPrimarySupply.getPsedsINomStab()));
            vTFInomStabCom.setValue(edsPrimarySupply.getPsedsINomStabComment() == null ? "" : edsPrimarySupply.getPsedsINomStabComment());
            vLInomStabRenseigner.setValue(edsPrimarySupply.getPsedsINomStabIformBy());

            vTFIpirecas.setValue(EDSTools.convertFloatToVide(edsPrimarySupply.getPsedsIPirecasStab()));
            vTFIpirecasCom.setValue(edsPrimarySupply.getPsedsIPirecasComment() == null ? "" : edsPrimarySupply.getPsedsIPirecasComment());
            vLIpirecasRenseigner.setValue(edsPrimarySupply.getPsedsIPirecasStabIformBy());
        } else {
            vTFName.setValue(edsRobustSupply.getRsedsSupplyName());

            typeAlimentation.setValue(edsRobustSupply.getWording());
            typeAlimentation.setCommentaire(edsRobustSupply.getRsedsSupplyTypeSupplyNameComment() == null ? "" : edsRobustSupply
                    .getRsedsSupplyTypeSupplyNameComment());
            typeAlimentation.setUser(edsRobustSupply.getRsedsSupplyTypeSupplyNameIformBy() == null ? "" : edsRobustSupply
                    .getRsedsSupplyTypeSupplyNameIformBy());

            vTFUCName.setValue(edsRobustSupply.getRsedsUseCaseName() == null ? "" : edsRobustSupply.getRsedsUseCaseName());

            vTFIveille.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsIVeille()));
            vTFIveilleCom.setValue(edsRobustSupply.getRsedsIVeilleComment() == null ? "" : edsRobustSupply.getRsedsIVeilleComment());
            vLIveilleRenseigner.setValue(edsRobustSupply.getRsedsIVeilleIformBy() == null ? "" : edsRobustSupply.getRsedsIVeilleIformBy());

            vTFIreveille.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsIReveilleInactif()));
            vTFIreveilleCom.setValue(edsRobustSupply.getRsedsReveilleComment() == null ? "" : edsRobustSupply.getRsedsReveilleComment());
            vLIreveilleRenseigner.setValue(edsRobustSupply.getRsedsReveilleIformBy() == null ? "" : edsRobustSupply.getRsedsReveilleIformBy());

            vTFInomStab.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsINomStab()));
            vTFInomStabCom.setValue(edsRobustSupply.getRsedsINomStabComment() == null ? "" : edsRobustSupply.getRsedsINomStabComment());
            vLInomStabRenseigner.setValue(edsRobustSupply.getRsedsINomStabIformBy() == null ? "" : edsRobustSupply.getRsedsINomStabIformBy());

            vTFIpirecas.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsIPirecasStab()));
            vTFIpirecasCom.setValue(edsRobustSupply.getRsedsIPirecasComment() == null ? "" : edsRobustSupply.getRsedsIPirecasComment());
            vLIpirecasRenseigner.setValue(edsRobustSupply.getRsedsIPirecasStabIformBy() == null ? "" : edsRobustSupply.getRsedsIPirecasStabIformBy());

            vTFImax.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsIMax()));
            vTFImaxCom.setValue(edsRobustSupply.getRsedsIMaxComment() == null ? "" : edsRobustSupply.getRsedsIMaxComment());
            vLImaxRenseigner.setValue(edsRobustSupply.getRsedsIMaxInformBy() == null ? "" : edsRobustSupply.getRsedsIMaxInformBy());

            vTFTImax.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsTIMax()));
            vTFTImaxCom.setValue(edsRobustSupply.getRsedsTIMaxComment() == null ? "" : edsRobustSupply.getRsedsTIMaxComment());
            vLTImaxRenseigner.setValue(edsRobustSupply.getRsedsTIMaxInformBy() == null ? "" : edsRobustSupply.getRsedsTIMaxInformBy());

            vTFTIConsoParc.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsIConsoParc()));
            vTFTIConsoParcCom.setValue(edsRobustSupply.getRsedsIConsoParcComment() == null ? "" : edsRobustSupply.getRsedsIConsoParcComment());
            vLIConsoParcRenseigner.setValue(edsRobustSupply.getRsedsTIMaxInformBy() == null ? "" : edsRobustSupply.getRsedsIConsoParcInformBy());

            // New values
            vTnomStab.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsTNomStab()));
            vTnomStabCom.setValue(edsRobustSupply.getRsedsTnomStabComment() == null ? "" : edsRobustSupply.getRsedsTnomStabComment());
            vTnomStabRenseigner.setValue(edsRobustSupply.getRsedsTnomStabInformBy() == null ? "" : edsRobustSupply.getRsedsTnomStabInformBy());

            vTpireCas.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsTPireCas()));
            vTpireCasCom.setValue(edsRobustSupply.getRsedsTpireCasComment() == null ? "" : edsRobustSupply.getRsedsTpireCasComment());
            vTpireCasRenseigner.setValue(edsRobustSupply.getRsedsTpireCasInformBy() == null ? "" : edsRobustSupply.getRsedsTpireCasInformBy());

            vIdysf.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsIdysf()));
            vIdysfCom.setValue(edsRobustSupply.getRsedsIdysfComment() == null ? "" : edsRobustSupply.getRsedsIdysfComment());
            vIdysfRenseigner.setValue(edsRobustSupply.getRsedsIdysfInformBy() == null ? "" : edsRobustSupply.getRsedsIdysfInformBy());

            vTdysf.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsTdysf()));
            vTdysfCom.setValue(edsRobustSupply.getRsedsTdysfComment() == null ? "" : edsRobustSupply.getRsedsTdysfComment());
            vTdysfRenseigner.setValue(edsRobustSupply.getRsedsTdysfInformBy() == null ? "" : edsRobustSupply.getRsedsTdysfInformBy());

            vImst.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsImst()));
            vImstCom.setValue(edsRobustSupply.getRsedsImstComment() == null ? "" : edsRobustSupply.getRsedsImstComment());
            vImstRenseigner.setValue(edsRobustSupply.getRsedsImstInformBy() == null ? "" : edsRobustSupply.getRsedsImstInformBy());

            vTmst.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsTmst()));
            vTmstCom.setValue(edsRobustSupply.getRsedsImstComment() == null ? "" : edsRobustSupply.getRsedsTmstComment());
            vTmstRenseigner.setValue(edsRobustSupply.getRsedsImstInformBy() == null ? "" : edsRobustSupply.getRsedsTmstInformBy());

            // if Supply is of BT type
            if (edsRobustSupply.getRsedsTbtBt().equals("BT")) {
                vTFUmin.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsUMin()));
                vTFUminCom.setValue(edsRobustSupply.getRsedsUMinComment() == null ? "" : edsRobustSupply.getRsedsUMinComment());
                vLUminRenseigner.setValue(edsRobustSupply.getRsedsUMinInformBy() == null ? "" : edsRobustSupply.getRsedsUMinInformBy());

                vTFUmoy.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsUMoy()));
                vTFUmoyCom.setValue(edsRobustSupply.getRsedsUMoyComment() == null ? "" : edsRobustSupply.getRsedsUMoyComment());
                vLUmoyRenseigner.setValue(edsRobustSupply.getRsedsUMoyInformBy() == null ? "" : edsRobustSupply.getRsedsUMoyInformBy());

                vTFUmax.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsUMax()));
                vTFUmaxCom.setValue(edsRobustSupply.getRsedsUMaxComment() == null ? "" : edsRobustSupply.getRsedsUMaxComment());
                vLUmaxRenseigner.setValue(edsRobustSupply.getRsedsUMaxInformBy() == null ? "" : edsRobustSupply.getRsedsUMaxInformBy());

                vTFCarac.setValue(EDSTools.convertFloatToVide(edsRobustSupply.getRsedsUcaracValue()));
                vTFCaracCom.setValue(edsRobustSupply.getRsedsUcaracComment() == null ? "" : edsRobustSupply.getRsedsUcaracComment());
                vLCaracRenseigner.setValue(edsRobustSupply.getRsedsUcaracInformBy() == null ? "" : edsRobustSupply.getRsedsUcaracInformBy());
            }
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        if (isValid()) {
            // commit use cases
            for (RobustSupplyUseCaseFormEditView useCaseEditView : useCaseEditViewsList) {
                useCaseEditView.commitChanges();
            }

            if (!(edsRobustSupply.getRsedsSupplyName() == null ? "" : edsRobustSupply.getRsedsSupplyName()).equals(vTFName.getValue().toString())) {
                edsRobustSupply.setRsedsSupplyNameIformBy(user);
            }

            if (!(edsRobustSupply.getWording() == null ? "" : edsRobustSupply.getWording().toString()).equals(typeAlimentation.getValue())) {
                edsRobustSupply.setRsedsSupplyTypeSupplyNameIformBy(user);
            }

            if (!(edsRobustSupply.getRsedsIVeille() == null ? "" : edsRobustSupply.getRsedsIVeille().toString()).equals(vTFIveille.getValue()
                    .toString())) {
                edsRobustSupply.setRsedsIVeilleIformBy(user);
            }

            if (!(edsRobustSupply.getRsedsIReveilleInactif() == null ? "" : edsRobustSupply.getRsedsIReveilleInactif().toString())
                    .equals(vTFIreveille.getValue().toString())) {
                edsRobustSupply.setRsedsReveilleIformBy(user);
            }

            if (!(edsRobustSupply.getRsedsINomStab() == null ? "" : edsRobustSupply.getRsedsINomStab().toString()).equals(vTFInomStab.getValue()
                    .toString())) {
                edsRobustSupply.setRsedsINomStabIformBy(user);
            }

            if (!(edsRobustSupply.getRsedsIPirecasStab() == null ? "" : edsRobustSupply.getRsedsIPirecasStab().toString()).equals(vTFIpirecas
                    .getValue().toString())) {
                edsRobustSupply.setRsedsIPirecasStabIformBy(user);
            }

            if (!(edsRobustSupply.getRsedsIMax() == null ? "" : edsRobustSupply.getRsedsIMax().toString()).equals(vTFImax.getValue().toString())) {
                edsRobustSupply.setRsedsIMaxInformBy(user);
            }
            if (!(edsRobustSupply.getRsedsTIMax() == null ? "" : edsRobustSupply.getRsedsTIMax().toString()).equals(vTFTImax.getValue().toString())) {
                edsRobustSupply.setRsedsTIMaxInformBy(user);
            }
            if (!(edsRobustSupply.getRsedsIConsoParc() == null ? "" : edsRobustSupply.getRsedsIConsoParc().toString()).equals(vTFTImax.getValue()
                    .toString())) {
                edsRobustSupply.setRsedsIConsoParcInformBy(user);
            }

            // New values
            if (!(edsRobustSupply.getRsedsTNomStab() == null ? "" : edsRobustSupply.getRsedsTNomStab().toString()).equals(vTnomStab.getValue()
                    .toString())) {
                edsRobustSupply.setRsedsTnomStabInformBy(user);
            }
            if (!(edsRobustSupply.getRsedsTPireCas() == null ? "" : edsRobustSupply.getRsedsTPireCas().toString()).equals(vTpireCas.getValue()
                    .toString())) {
                edsRobustSupply.setRsedsTpireCasInformBy(user);
            }
            if (!(edsRobustSupply.getRsedsIdysf() == null ? "" : edsRobustSupply.getRsedsIdysf().toString()).equals(vIdysf.getValue().toString())) {
                edsRobustSupply.setRsedsIdysfInformBy(user);
            }
            if (!(edsRobustSupply.getRsedsTdysf() == null ? "" : edsRobustSupply.getRsedsTdysf().toString()).equals(vTdysf.getValue().toString())) {
                edsRobustSupply.setRsedsTdysfInformBy(user);
            }
            if (!(edsRobustSupply.getRsedsImst() == null ? "" : edsRobustSupply.getRsedsImst().toString()).equals(vImst.getValue().toString())) {
                edsRobustSupply.setRsedsImstInformBy(user);
            }
            if (!(edsRobustSupply.getRsedsTmst() == null ? "" : edsRobustSupply.getRsedsTmst().toString()).equals(vTmst.getValue().toString())) {
                edsRobustSupply.setRsedsTmstInformBy(user);
            }

            if (edsRobustSupply.getRsedsTbtBt().equals("BT")) {
                if (!(edsRobustSupply.getRsedsUMin() == null ? "" : edsRobustSupply.getRsedsUMin().toString()).equals(vTFUmin.getValue().toString())) {
                    edsRobustSupply.setRsedsUMinInformBy(user);
                }

                if (!(edsRobustSupply.getRsedsUMoy() == null ? "" : edsRobustSupply.getRsedsUMoy().toString()).equals(vTFUmoy.getValue().toString())) {
                    edsRobustSupply.setRsedsUMoyInformBy(user);
                }

                if (!(edsRobustSupply.getRsedsUMax() == null ? "" : edsRobustSupply.getRsedsUMax().toString()).equals(vTFUmax.getValue().toString())) {
                    edsRobustSupply.setRsedsUMaxInformBy(user);
                }

                if (!(edsRobustSupply.getRsedsUcaracValue() == null ? "" : edsRobustSupply.getRsedsUcaracValue().toString()).equals(vTFCarac
                        .getValue().toString())) {
                    edsRobustSupply.setRsedsUcaracInformBy(user);
                }

                edsRobustSupply.setRsedsUMinComment(vTFUminCom.getValue().toString());
                edsRobustSupply.setRsedsUMoyComment(vTFUmoyCom.getValue().toString());
                edsRobustSupply.setRsedsUMaxComment(vTFUmaxCom.getValue().toString());
                edsRobustSupply.setRsedsUcaracComment(vTFCaracCom.getValue().toString());

            }

            edsRobustSupply.setRsedsSupplyName(vTFName.getValue().toString());
            edsRobustSupply.setWording(typeAlimentation.getValue());
            edsRobustSupply.setRsedsSupplyTypeSupplyNameComment(typeAlimentation.getCommentaire());
            edsRobustSupply.setRsedsUseCaseName(vTFUCName.getValue().toString());
            edsRobustSupply.setRsedsIVeilleComment(vTFIveilleCom.getValue().toString());
            edsRobustSupply.setRsedsReveilleComment(vTFIreveilleCom.getValue().toString());
            edsRobustSupply.setRsedsINomStabComment(vTFInomStabCom.getValue().toString());
            edsRobustSupply.setRsedsIPirecasComment(vTFIpirecasCom.getValue().toString());
            edsRobustSupply.setRsedsIMaxComment(vTFImaxCom.getValue().toString());
            edsRobustSupply.setRsedsTIMaxComment(vTFTImaxCom.getValue().toString());
            edsRobustSupply.setRsedsIConsoParcComment(vTFTIConsoParcCom.getValue().toString());
            // New values
            edsRobustSupply.setRsedsTnomStabComment(vTnomStabCom.getValue().toString());
            edsRobustSupply.setRsedsTpireCasComment(vTpireCasCom.getValue().toString());
            edsRobustSupply.setRsedsIdysfComment(vIdysfCom.getValue().toString());
            edsRobustSupply.setRsedsTdysfComment(vTdysfCom.getValue().toString());
            edsRobustSupply.setRsedsImstComment(vImstCom.getValue().toString());
            edsRobustSupply.setRsedsTmstComment(vTmstCom.getValue().toString());

            edsRobustSupply.setRsedsIVeille(EDSTools.convertStringToFloat(vTFIveille.getValue().toString()));
            edsRobustSupply.setRsedsIReveilleInactif(EDSTools.convertStringToFloat(vTFIreveille.getValue().toString()));
            edsRobustSupply.setRsedsINomStab(EDSTools.convertStringToFloat(vTFInomStab.getValue().toString()));
            edsRobustSupply.setRsedsIPirecasStab(EDSTools.convertStringToFloat(vTFIpirecas.getValue().toString()));
            edsRobustSupply.setRsedsIMax(EDSTools.convertStringToFloat(vTFImax.getValue().toString()));
            edsRobustSupply.setRsedsTIMax(EDSTools.convertStringToFloat(vTFTImax.getValue().toString()));
            edsRobustSupply.setRsedsIConsoParc(EDSTools.convertStringToFloat(vTFTIConsoParc.getValue().toString()));
            // New values
            edsRobustSupply.setRsedsTNomStab(EDSTools.convertStringToFloat(vTnomStab.getValue().toString()));
            edsRobustSupply.setRsedsTPireCas(EDSTools.convertStringToFloat(vTpireCas.getValue().toString()));
            edsRobustSupply.setRsedsIdysf(EDSTools.convertStringToFloat(vIdysf.getValue().toString()));
            edsRobustSupply.setRsedsTdysf(EDSTools.convertStringToFloat(vTdysf.getValue().toString()));
            edsRobustSupply.setRsedsImst(EDSTools.convertStringToFloat(vImst.getValue().toString()));
            edsRobustSupply.setRsedsTmst(EDSTools.convertStringToFloat(vTmst.getValue().toString()));

            if (edsRobustSupply.getRsedsTbtBt().equals("BT")) {
                edsRobustSupply.setRsedsUMin(EDSTools.convertStringToFloat(vTFUmin.getValue().toString()));
                edsRobustSupply.setRsedsUMoy(EDSTools.convertStringToFloat(vTFUmoy.getValue().toString()));
                edsRobustSupply.setRsedsUMax(EDSTools.convertStringToFloat(vTFUmax.getValue().toString()));
                edsRobustSupply.setRsedsUcaracValue(EDSTools.convertStringToFloat(vTFCarac.getValue().toString()));
            }

            return true;
        }
        return false;

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        // use case name not empty
        if (vTFUCName.getValue().toString().equals("")) {
            showNotification(controller.getBundle().getString("eds-supply-usecase-name-error-message"), "");
            return false;
        }

        try {
            Float.parseFloat(vTFIveille.getValue().toString().equals("") ? "0" : vTFIveille.getValue().toString());
            Float.parseFloat(vTFIreveille.getValue().toString().equals("") ? "0" : vTFIreveille.getValue().toString());
            Float.parseFloat(vTFInomStab.getValue().toString().equals("") ? "0" : vTFInomStab.getValue().toString());
            Float.parseFloat(vTFIpirecas.getValue().toString().equals("") ? "0" : vTFIpirecas.getValue().toString());
            Float.parseFloat(vTFImax.getValue().toString().equals("") ? "0" : vTFImax.getValue().toString());
            Float.parseFloat(vTFTImax.getValue().toString().equals("") ? "0" : vTFTImax.getValue().toString());
            Float.parseFloat(vTFTIConsoParc.getValue().toString().equals("") ? "0" : vTFTIConsoParc.getValue().toString());
            // New values
            Float.parseFloat(vTnomStab.getValue().toString().equals("") ? "0" : vTnomStab.getValue().toString());
            Float.parseFloat(vTpireCas.getValue().toString().equals("") ? "0" : vTpireCas.getValue().toString());
            Float.parseFloat(vIdysf.getValue().toString().equals("") ? "0" : vIdysf.getValue().toString());
            Float.parseFloat(vTdysf.getValue().toString().equals("") ? "0" : vTdysf.getValue().toString());
            Float.parseFloat(vImst.getValue().toString().equals("") ? "0" : vImst.getValue().toString());
            Float.parseFloat(vTmst.getValue().toString().equals("") ? "0" : vTmst.getValue().toString());

            if (edsRobustSupply.getRsedsTbtBt().equals("BT")) {
                Float.parseFloat(vTFUmin.getValue().toString().equals("") ? "0" : vTFUmin.getValue().toString());
                Float.parseFloat(vTFUmax.getValue().toString().equals("") ? "0" : vTFUmax.getValue().toString());
                Float.parseFloat(vTFUmoy.getValue().toString().equals("") ? "0" : vTFUmoy.getValue().toString());
                Float.parseFloat(vTFCarac.getValue().toString().equals("") ? "0" : vTFCarac.getValue().toString());
            }

            // validate use cases
            for (RobustSupplyUseCaseFormEditView useCaseEditView : useCaseEditViewsList) {
                if (!useCaseEditView.isValid()) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            showNotification(controller.getBundle().getString("eds-format-nombre-title"), controller.getBundle().getString("alim-format-nombre-msg")
                    + " " + this.toString());
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        Collection<Object> toEdit = new HashSet<Object>();

        for (RobustSupplyUseCaseFormEditView view : useCaseEditViewsList)
            toEdit.addAll(view.getAllItemsToSave());

        toEdit.add(edsRobustSupply);

        return toEdit;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection<?> getAllItemsToDelete() {
        Collection<Object> toDelete = new HashSet<Object>();

        for (RobustSupplyUseCaseFormEditView view : useCaseEditViewsListToDelete)
            toDelete.add(view.getUseCase());

        return toDelete;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        // DO NOTHING
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
     * This method returns EdsSupply
     * 
     * @return EdsSupply
     */
    public EdsSupply getEdsSupply() {
        return edsSupply;
    }

    /**
     * This method set EdsSupply
     * 
     * @param edsSupply EdsSupply to be set
     */
    public void setEdsSupply(EdsSupply edsSupply) {
        this.edsSupply = edsSupply;
    }

    /**
     * This method returns EdsPrimarySupply
     * 
     * @return EdsPrimarySupply
     */
    public EdsPrimarySupply getEdsPrimarySupply() {
        return edsPrimarySupply;
    }

    /**
     * This method set EdsPrimarySupply
     * 
     * @param edsPrimarySupply EdsPrimarySupply to be set
     */
    public void setEdsPrimarySupply(EdsPrimarySupply edsPrimarySupply) {
        this.edsPrimarySupply = edsPrimarySupply;
    }

    /**
     * This method returns EdsRobustSupply
     * 
     * @return EdsRobustSupply
     */
    public EdsRobustSupply getEdsRobustSupply() {
        return edsRobustSupply;
    }

    /**
     * This method set EdsRobustSupply
     * 
     * @param edsRobustSupply EdsRobustSupply to be set
     */
    public void setEdsRobustSupply(EdsRobustSupply edsRobustSupply) {
        this.edsRobustSupply = edsRobustSupply;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return edsRobustSupply.getRsedsSupplyName();
    }

    /**
     * This method removes EdsRobustSuppy
     */
    public void remove() {
        this.edsRobustSupply = null;
        edsSupply.setEdsRobustSupply(null);
    }

}
