package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.application.content.eds.composants.MyTextArea;
import com.inetpsa.eds.application.content.eds.composants.MyTextField;
import com.inetpsa.eds.application.content.eds.currentconsumption.robust.ChoixMasse;
import com.inetpsa.eds.dao.model.EdsConsolidateCurentFormData;
import com.inetpsa.eds.dao.model.EdsConsolidateSupply;
import com.inetpsa.eds.dao.model.EdsConsolidateSupplyTheoritic;
import com.inetpsa.eds.dao.model.EdsCourantAppelleActivation;
import com.inetpsa.eds.dao.model.EdsCourantBloqueCourantDysfonctionnement;
import com.inetpsa.eds.dao.model.EdsCourantNominaleActivation;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsGroundConsolidate;
import com.inetpsa.eds.dao.model.EdsGroundConsolidateCurent;
import com.vaadin.data.Property;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.vaadin.dialogs.ConfirmDialog;

/**
 * This class provide component for editing view of Current consumption ground tab of consolidate stage
 * 
 * @author Geometric Ltd.
 */
public class ConsolidateGroundEditView extends A_EdsEditForm implements Property.ValueChangeListener, TabSheet.CloseHandler {
    // PUBLIC
    /**
     * Default constructor
     */
    public ConsolidateGroundEditView() {
        init();
    }

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     * @param edsConsolidateCurentFormData Object of EdsConsolidateCurentFormData
     * @param groundConsolidateCurent object of EdsGroundConsolidateCurent
     * @param eds Object of EdsEds
     */
    public ConsolidateGroundEditView(EdsApplicationController controller, EdsConsolidateCurentFormData edsConsolidateCurentFormData,
            EdsGroundConsolidateCurent groundConsolidateCurent, EdsEds eds) {
        setCaption(controller.getBundle().getString("current-conso-tab-mass-rob-title"));
        this.eds = eds;
        this.controller = controller;
        this.edsConsolidateCurentFormData = edsConsolidateCurentFormData;
        this.groundConsolidateCurent = groundConsolidateCurent;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of EdsConsolidateCurentFormData
     */
    private EdsConsolidateCurentFormData edsConsolidateCurentFormData;
    /**
     * Variable to hold value of EdsGroundConsolidateCurent
     */
    private EdsGroundConsolidateCurent groundConsolidateCurent;
    /**
     * Variable to hold value of VerticalLayout
     */
    private VerticalLayout mainLayout;
    /**
     * Variable to hold value of TabSheet
     */
    private TabSheet vVLMasse;
    /**
     * Variable to hold value of ChoixMasse
     */
    private ChoixMasse other;
    /**
     * Variable to hold value of ChoixMasse
     */
    private ChoixMasse total;
    /**
     * Variable to hold value of ChoixMasse
     */
    private ChoixMasse destinct;
    /**
     * Variable to hold value of ChoixMasse
     */
    private ChoixMasse lastChoice;
    /**
     * Variable to hold value of OptionGroup
     */
    private OptionGroup vOGChoix;
    /**
     * Variable to hold value of MyTextField
     */
    private MyTextField vTXTEEConst;
    /**
     * Variable to hold list of EdsGroundConsolidate
     */
    private ArrayList<EdsGroundConsolidate> edsGroundsList = new ArrayList<EdsGroundConsolidate>();
    /**
     * Variable to hold value of list of GroundConsolidateEditView
     */
    private ArrayList<GroundConsolidateEditView> groundEditViewsList = new ArrayList<GroundConsolidateEditView>();
    /**
     * Variable to hold value of list of EdsConsolidateSupply
     */
    private ArrayList<EdsConsolidateSupply> edsConsolidateSupplys;
    /**
     * Variable to hold value of Button to add
     */
    private Button addGround;
    /**
     * Variable to hold value of MyTextField
     */
    private MyTextField vTXTMName;
    /**
     * Variable to hold value of HorizontalLayout
     */
    private HorizontalLayout hLAddGround;

    /**
     * Initialize component for editing view of Current consumption ground tab of consolidate stage form
     */
    private void init() {
        edsConsolidateSupplys = new ArrayList<EdsConsolidateSupply>();

        setSpacing(true);
        mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setWidth("100%");

        other = new ChoixMasse(controller.getBundle().getString("current-conso-tab-mass-rob-choose3"), 2);
        destinct = new ChoixMasse(controller.getBundle().getString("current-conso-tab-mass-rob-choose2"), 1);
        total = new ChoixMasse(controller.getBundle().getString("current-conso-tab-mass-rob-choose1"), 0);

        Label vLTitre = new Label(controller.getBundle().getString("current-conso-tab-mass-rob-title2"));
        vLTitre.addStyleName("h2");
        mainLayout.addComponent(vLTitre);

        vOGChoix = new OptionGroup(controller.getBundle().getString("current-conso-tab-mass-rob-quest"));
        vOGChoix.addItem(total);
        vOGChoix.addItem(destinct);
        vOGChoix.addItem(other);
        vOGChoix.setNullSelectionAllowed(true);
        vOGChoix.setImmediate(true);
        vOGChoix.setWidth("100%");
        mainLayout.addComponent(vOGChoix);

        vOGChoix.setValue(null);
        vOGChoix.addStyleName("separator");
        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        vl.setWidth("100%");
        vl.addStyleName("separator");
        vl.addComponent(new Label(controller.getBundle().getString("eds-ee-contituant") + " : "));
        vTXTEEConst = new MyTextField();
        vTXTEEConst.setColumns(20);
        vl.addComponent(vTXTEEConst);

        mainLayout.addComponent(vl);
        hLAddGround = new HorizontalLayout();

        hLAddGround.setSpacing(true);
        vTXTMName = new MyTextField();
        vTXTMName.setWidth("300");
        hLAddGround.addComponent(vTXTMName);
        hLAddGround.setComponentAlignment(vTXTMName, Alignment.MIDDLE_CENTER);
        addGround = new Button(controller.getBundle().getString("current-conso-tab-mass-rob-button"));
        // addGround.setIcon( ResourceManager.getInstance().getThemeIconResource( "icons/activate.png " ) );
        addGround.setDescription(controller.getBundle().getString("current-conso-tab-mass-rob-button"));
        addGround.addStyleName("gras");

        addGround.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

                addGround();
            }
        });

        hLAddGround.addComponent(addGround);
        hLAddGround.setComponentAlignment(addGround, Alignment.MIDDLE_CENTER);
        mainLayout.addComponent(hLAddGround);
        hLAddGround.setVisible(false);
        vVLMasse = new TabSheet();
        vVLMasse.setCloseHandler(this);
        mainLayout.addComponent(vVLMasse);

        addComponent(mainLayout);

        vOGChoix.addListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        for (GroundConsolidateEditView editView : groundEditViewsList) {
            editView.commitChanges();
        }

        if (vOGChoix.getValue() != null) {
            groundConsolidateCurent.setGccChoixMasse(((ChoixMasse) vOGChoix.getValue()).getId());
        }

        groundConsolidateCurent.setGccUmax(vTXTEEConst.getFloat());

        groundConsolidateCurent.getEdsGroundConsolidates().clear();
        groundConsolidateCurent.getEdsGroundConsolidates().addAll(edsGroundsList);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        removeAllGround();

        hLAddGround.setVisible(false);
        vTXTEEConst.setValue(groundConsolidateCurent.getGccUmax());
        if (groundConsolidateCurent.getGccChoixMasse() == null) {
            return;
        }
        int choise = groundConsolidateCurent.getGccChoixMasse();

        switch (choise) {
        case EdsGroundConsolidateCurent.SOMME_ALIM:
            lastChoice = total;
            setGround(false);
            break;
        case EdsGroundConsolidateCurent.COPIE_ALIM:
            lastChoice = destinct;
            setGround(false);
            break;
        case EdsGroundConsolidateCurent.AUTRE:
            lastChoice = other;
            setGround(true);
            hLAddGround.setVisible(true);
            break;
        }
        vOGChoix.setValue(lastChoice);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection getAllItemsToSave() {
        return Collections.EMPTY_SET;
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
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        edsConsolidateCurentFormData = controller.getFormDataModel(eds, edsConsolidateCurentFormData.getClass());
    }

    /**
     * This method removes all Ground
     */
    private void removeAllGround() {
        vVLMasse.removeAllComponents();
        edsGroundsList.clear();
        groundEditViewsList.clear();

    }

    /**
     * This method returns list of EdsGroundConsolidate
     * 
     * @return list of EdsGroundConsolidate
     */
    public ArrayList<EdsGroundConsolidate> getEdsGroundsList() {
        return edsGroundsList;
    }

    /**
     * This method set EdsGroundList
     * 
     * @param edsGroundsList list of EdsGroundConsolidate to set
     */
    public void setEdsGroundsList(ArrayList<EdsGroundConsolidate> edsGroundsList) {
        this.edsGroundsList = edsGroundsList;
    }

    /**
     * Notifies this listener that the Property's value has changed.
     * 
     * @param event value change event object
     */
    public void valueChange(Property.ValueChangeEvent event) {
        ChoixMasse o = (ChoixMasse) event.getProperty().getValue();
        if (o == lastChoice) {
            return;
        }
        hLAddGround.setVisible(false);
        switch (o.getId()) {
        case 0:
            ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("current-conso-tab-mass-rob-Alert-title"),
                    controller.getBundle().getString("eds-ground-advice"), controller.getBundle().getString("consolid-qcf-oui"), controller
                            .getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                        public void onClose(ConfirmDialog cd) {
                            if (cd.isConfirmed()) {
                                removeAllGround();
                                addToAll();
                                lastChoice = (ChoixMasse) vOGChoix.getValue();
                            } else {
                                vOGChoix.setValue(lastChoice);
                            }
                        }
                    });
            break;
        case 1:
            ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("current-conso-tab-mass-rob-Alert-title"),
                    controller.getBundle().getString("eds-ground-advice"), controller.getBundle().getString("consolid-qcf-oui"), controller
                            .getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                        public void onClose(ConfirmDialog cd) {
                            if (cd.isConfirmed()) {
                                removeAllGround();
                                addToDistinct();
                                lastChoice = (ChoixMasse) vOGChoix.getValue();
                            } else {
                                vOGChoix.setValue(lastChoice);
                            }
                        }
                    });
            break;
        case 2:
            ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("current-conso-tab-mass-rob-Alert-title"),
                    controller.getBundle().getString("eds-ground-advice"), controller.getBundle().getString("consolid-qcf-oui"), controller
                            .getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {
                        public void onClose(ConfirmDialog cd) {
                            if (cd.isConfirmed()) {
                                removeAllGround();
                                hLAddGround.setVisible(true);

                                lastChoice = (ChoixMasse) vOGChoix.getValue();
                            } else {
                                vOGChoix.setValue(lastChoice);
                            }
                        }
                    });
            break;
        }
    }

    /**
     * This method add each ground for distinct power supply
     */
    private void addToDistinct() {
        for (EdsConsolidateSupply ecs : edsConsolidateSupplys) {

            EdsConsolidateSupplyTheoritic ecst = ecs.getEdsConsolidateSupplyTheoritic();
            EdsGroundConsolidate edsGround = new EdsGroundConsolidate(UUID.randomUUID().toString());
            edsGround.setGedsTitle(controller.getBundle().getString("current-conso-tab-mass-rob-title") + " " + ecs.getCsedsSupplyName());
            for (EdsCourantAppelleActivation ecaa : ecst.getEdsCourantAppelleActivations()) {
                EdsCourantAppelleActivation newEcaa = new EdsCourantAppelleActivation(ecaa);
                edsGround.getEdsCourantAppelleActivations().add(newEcaa);
            }
            for (EdsCourantBloqueCourantDysfonctionnement ecbcd : ecst.getEdsCourantBloqueCourantDysfonctionnements()) {
                EdsCourantBloqueCourantDysfonctionnement newEcbcd = new EdsCourantBloqueCourantDysfonctionnement(ecbcd);
                edsGround.getEdsCourantBloqueCourantDysfonctionnements().add(newEcbcd);
            }

            for (EdsCourantNominaleActivation ecna : ecst.getEdsCourantNominaleActivations()) {
                EdsCourantNominaleActivation newEcna = new EdsCourantNominaleActivation(ecna);
                edsGround.getEdsCourantNominaleActivations().add(newEcna);
            }

            GroundConsolidateEditView groundView = new GroundConsolidateEditView(controller, edsGround, false);

            edsGroundsList.add(edsGround);
            groundEditViewsList.add(groundView);
            vVLMasse.addTab(groundView, edsGround.getGedsTitle());
        }
    }

    /**
     * This method add ground which is sum for all power supply
     */
    private void addToAll() {

        if (edsConsolidateSupplys.isEmpty()) {
            return;
        }
        Set<EdsCourantAppelleActivation> ecaas = new HashSet<EdsCourantAppelleActivation>();
        Set<EdsCourantNominaleActivation> ecnas = new HashSet<EdsCourantNominaleActivation>();
        Set<EdsCourantBloqueCourantDysfonctionnement> ecbcds = new HashSet<EdsCourantBloqueCourantDysfonctionnement>();

        for (EdsConsolidateSupply ecs : edsConsolidateSupplys) {
            EdsConsolidateSupplyTheoritic ecst = ecs.getEdsConsolidateSupplyTheoritic();
            ecaas.addAll(ecst.getEdsCourantAppelleActivations());
            ecnas.addAll(ecst.getEdsCourantNominaleActivations());
            ecbcds.addAll(ecst.getEdsCourantBloqueCourantDysfonctionnements());
        }

        EdsGroundConsolidate edsGround = new EdsGroundConsolidate(UUID.randomUUID().toString());
        edsGround.setGedsTitle(controller.getBundle().getString("alim-sum"));
        EdsCourantAppelleActivation ecaa = new EdsCourantAppelleActivation(UUID.randomUUID().toString());
        ecaa.setSomme(ecaas);
        edsGround.getEdsCourantAppelleActivations().add(ecaa);

        EdsCourantBloqueCourantDysfonctionnement ecbcd = new EdsCourantBloqueCourantDysfonctionnement(UUID.randomUUID().toString());
        ecbcd.setSomme(ecbcds);
        edsGround.getEdsCourantBloqueCourantDysfonctionnements().add(ecbcd);

        EdsCourantNominaleActivation ecna = new EdsCourantNominaleActivation(UUID.randomUUID().toString());
        ecna.setSomme(ecnas);
        edsGround.getEdsCourantNominaleActivations().add(ecna);

        GroundConsolidateEditView groundView = new GroundConsolidateEditView(controller, edsGround, false);

        edsGroundsList.add(edsGround);
        groundEditViewsList.add(groundView);
        vVLMasse.addTab(groundView, edsGround.getGedsTitle());
    }

    /**
     * This method add Ground
     */
    private void addGround() {

        if (vTXTMName.getValue() == "") {
            getWindow().showNotification(controller.getBundle().getString("ground-name-error"));
            vTXTMName.focus();
            return;
        }

        if (groundConsolidateCurent.exist(vTXTMName.getString())) {
            getWindow().showNotification(controller.getBundle().getString("ground-name-exist"));
            vTXTMName.focus();
            return;
        }
        EdsGroundConsolidate edsGround = new EdsGroundConsolidate(UUID.randomUUID().toString());
        edsGround.setGedsTitle(vTXTMName.getString());
        EdsCourantAppelleActivation ecaa = new EdsCourantAppelleActivation(UUID.randomUUID().toString());
        edsGround.getEdsCourantAppelleActivations().add(ecaa);

        EdsCourantBloqueCourantDysfonctionnement ecbcd = new EdsCourantBloqueCourantDysfonctionnement(UUID.randomUUID().toString());
        edsGround.getEdsCourantBloqueCourantDysfonctionnements().add(ecbcd);

        EdsCourantNominaleActivation ecna = new EdsCourantNominaleActivation(UUID.randomUUID().toString());
        edsGround.getEdsCourantNominaleActivations().add(ecna);

        GroundConsolidateEditView groundView = new GroundConsolidateEditView(controller, edsGround, true);

        edsGroundsList.add(edsGround);
        groundEditViewsList.add(groundView);
        Tab t = vVLMasse.addTab(groundView, edsGround.getGedsTitle());
        t.setClosable(true);

    }

    /**
     * This method Set Ground tab
     * 
     * @param b Boolean to set tab
     */
    private void setGround(boolean b) {

        for (EdsGroundConsolidate edsGround : groundConsolidateCurent.getEdsGroundConsolidates()) {
            GroundConsolidateEditView groundView = new GroundConsolidateEditView(controller, edsGround, b) {
                public void buttonClick(Button.ClickEvent event) {
                }
            };

            edsGroundsList.add(edsGround);
            groundEditViewsList.add(groundView);
            Tab t = vVLMasse.addTab(groundView, edsGround.getGedsTitle());
            t.setClosable(b);
        }
    }

    /**
     * This method provide window when page is under construction
     */
    private void construction() {
        getWindow().showNotification("Page en cours de construction......");
    }

    /**
     * This method add EdsConsolidateSupply item to list
     * 
     * @param ecs Object of EdsConsolidateSupply to add
     */
    public void add(EdsConsolidateSupply ecs) {
        edsConsolidateSupplys.add(ecs);
    }

    /**
     * This method clears the EdsConsolidateSupply list
     */
    public void clear() {
        edsConsolidateSupplys.clear();
    }

    /**
     * This method is Called when a user has pressed the close icon of a tab in the client side widget.
     * 
     * @param tabsheet the TabSheet to which the tab belongs to
     * @param tabContent the component that corresponds to the tab whose close button was clicked
     */
    public void onTabClose(TabSheet tabsheet, final Component tabContent) {
        final GroundConsolidateEditView view = (GroundConsolidateEditView) tabContent;
        ConfirmDialog.show(getApplication().getMainWindow(), controller.getBundle().getString("pop-warning-title"), MessageFormat.format(controller
                .getBundle().getString(""), view), controller.getBundle().getString("consolid-qcf-oui"),
                controller.getBundle().getString("consolid-qcf-non"), new ConfirmDialog.Listener() {

                    /*
                     * (non-Javadoc)
                     * 
                     * @see org.vaadin.dialogs.ConfirmDialog.Listener#onClose(org.vaadin.dialogs.ConfirmDialog)
                     */
                    public void onClose(ConfirmDialog cd) {
                        if (cd.isConfirmed()) {
                            edsGroundsList.remove(view.getEdsGround());
                            groundEditViewsList.remove(view);
                            vVLMasse.removeComponent(tabContent);
                        }
                    }
                });
    }
}
