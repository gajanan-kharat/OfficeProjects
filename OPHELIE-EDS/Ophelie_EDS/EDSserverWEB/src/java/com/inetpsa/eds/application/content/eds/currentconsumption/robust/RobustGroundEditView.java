package com.inetpsa.eds.application.content.eds.currentconsumption.robust;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.vaadin.dialogs.ConfirmDialog;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsGround;
import com.inetpsa.eds.dao.model.EdsGroundRobustCurent;
import com.inetpsa.eds.dao.model.EdsRobustCurentFormData;
import com.inetpsa.eds.dao.model.EdsRobustSupply;
import com.inetpsa.eds.tools.EDSTools;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide component for editing view of Current consumption ground tab of robust stage
 * 
 * @author Geometric Ltd.
 */
public class RobustGroundEditView extends A_EdsEditForm implements Property.ValueChangeListener {
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
     * Variable to hold value of VerticalLayout for ground
     */
    private VerticalLayout grounLayout;
    /**
     * Variable to hold value of VerticalLayout for Mass
     */
    private VerticalLayout vVLMasse;
    /**
     * Variable to hold value of ChoixMasse for other
     */
    private ChoixMasse other;
    /**
     * Variable to hold value of ChoixMasse for total
     */
    private ChoixMasse total;
    /**
     * Variable to hold value of ChoixMasse for distinct
     */
    private ChoixMasse destinct;
    /**
     * Variable to hold value of OptionGroup
     */
    private OptionGroup vOGChoix;
    /**
     * Variable to hold value of ChoixMasse for last choice
     */
    private ChoixMasse lastChoice;
    /**
     * Variable to hold list of EdsRobustSupply
     */
    private ArrayList<EdsRobustSupply> robustSupplysList;
    /**
     * Variable to hold list of GroundEditView
     */
    private ArrayList<GroundEditView> groundEditViewsList = new ArrayList<GroundEditView>();
    /**
     * Variable to hold list of EdsGround
     */
    private ArrayList<EdsGround> edsGroundsList = new ArrayList<EdsGround>();

    /**
     * Default Constructor
     */
    public RobustGroundEditView() {
        init();
    }

    /**
     * Parameterized Constructor
     * 
     * @param controller Controller of EDS application
     * @param edsRobustCurentFormData Object of EdsRobustCurentFormData
     * @param eds Object of EdsEds
     */
    public RobustGroundEditView(EdsApplicationController controller, EdsRobustCurentFormData edsRobustCurentFormData, EdsEds eds) {
        this.edsRobustCurentFormData = edsRobustCurentFormData;
        this.eds = eds;
        this.controller = controller;
        init();
    }

    /**
     * This method returns list of GroundEditView
     * 
     * @return list of GroundEditView
     */
    public ArrayList<GroundEditView> getGroundEditViewsList() {
        return groundEditViewsList;
    }

    /**
     * This method set list of GroundEditView
     * 
     * @param groundEditViewsList list of GroundEditView to be set
     */
    public void setGroundEditViewsList(ArrayList<GroundEditView> groundEditViewsList) {
        this.groundEditViewsList = groundEditViewsList;
    }

    /**
     * Initialize Robust Ground Edit view
     */
    private void init() {

        other = new ChoixMasse(controller.getBundle().getString("current-conso-tab-mass-rob-choose3"), 2);
        destinct = new ChoixMasse(controller.getBundle().getString("current-conso-tab-mass-rob-choose2"), 1);
        total = new ChoixMasse(controller.getBundle().getString("current-conso-tab-mass-rob-choose1"), 0);

        robustSupplysList = new ArrayList<EdsRobustSupply>();
        grounLayout = new VerticalLayout();
        grounLayout.setSpacing(true);
        grounLayout.setMargin(true);

        Label vLTitre = new Label(controller.getBundle().getString("current-conso-tab-mass-rob-title2"));
        vLTitre.addStyleName("h2");
        grounLayout.addComponent(vLTitre);

        vOGChoix = new OptionGroup(controller.getBundle().getString("current-conso-tab-mass-rob-quest"));
        vOGChoix.addItem(total);
        vOGChoix.addItem(destinct);
        vOGChoix.addItem(other);
        vOGChoix.setNullSelectionAllowed(true);
        vOGChoix.setImmediate(true);
        grounLayout.addComponent(vOGChoix);

        vVLMasse = new VerticalLayout();
        grounLayout.addComponent(vVLMasse);
        vOGChoix.setValue(null);
        discardChanges();

        vOGChoix.addListener(this);
        addComponent(grounLayout);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        removeAllGround();

        for (EdsGround gev : edsRobustCurentFormData.getEdsGrounds()) {

            GroundEditView groundView = new GroundEditView(controller, gev.getEdsGroundRobustCurent(), null, gev, edsRobustCurentFormData) {
                public void buttonClick(ClickEvent event) {
                    edsGroundsList.remove(this.getEdsGround());
                    groundEditViewsList.remove(this);
                    vVLMasse.removeComponent(this);
                }
            };

            switch (edsRobustCurentFormData.getRcChoixMasse()) {
            case 0:
                groundView.setEnabled(false);
                groundView.isDelete(false);
                vOGChoix.select(total);
                break;
            case 1:
                groundView.setEnabled(false);
                groundView.isDelete(false);
                vOGChoix.select(destinct);
                break;
            case 2:
                groundView.setEnabled(true);
                groundView.isDelete(true);
                vOGChoix.select(other);
                break;
            default:
                break;
            }

            edsGroundsList.add(gev);
            groundEditViewsList.add(groundView);
            vVLMasse.addComponent(groundView);
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

            for (GroundEditView editView : groundEditViewsList) {
                editView.commitChanges();
            }
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
    public Collection<Object> getAllItemsToSave() {
        List<Object> list = new ArrayList<Object>();
        for (GroundEditView view : groundEditViewsList) {
            list.addAll(view.getAllItemsToSave());
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete ()
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
        edsRobustCurentFormData = controller.getFormDataModel(eds, edsRobustCurentFormData.getClass());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        for (GroundEditView editView : groundEditViewsList) {
            if (!editView.isValid()) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method add specified EdsRobustSupply to list
     * 
     * @param ers EdsRobustSupply to add
     */
    public void addRobusteSuplys(EdsRobustSupply ers) {
        robustSupplysList.add(ers);
    }

    /**
     * This method remove specified EdsRobustSupply from list
     * 
     * @param ers EdsRobustSupply to be removed
     */
    public void removeRobusteSuplys(EdsRobustSupply ers) {
        robustSupplysList.remove(ers);
    }

    /**
     * This method clears list of EdsRobustSupply
     */
    public void removeAllRobusteSuplys() {
        robustSupplysList.clear();
    }

    /**
     * This method returns list of EdsGround
     * 
     * @return list of EdsGround
     */
    public ArrayList<EdsGround> getEdsGroundsList() {
        return edsGroundsList;
    }

    /**
     * This method set the list of EdsGround
     * 
     * @param edsGroundsList list of EdsGround to set
     */
    public void setEdsGroundsList(ArrayList<EdsGround> edsGroundsList) {
        this.edsGroundsList = edsGroundsList;
    }

    /**
     * This method Notifies this listener that the Property's value has changed.
     * 
     * @param event Value change event
     */
    public void valueChange(ValueChangeEvent event) {

        ChoixMasse o = (ChoixMasse) event.getProperty().getValue();
        if (o == lastChoice) {
            return;
        }

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
                                addOthers();
                                lastChoice = (ChoixMasse) vOGChoix.getValue();
                            } else {
                                vOGChoix.setValue(lastChoice);
                                vOGChoix.handleError(null);
                            }
                        }
                    });

            break;
        default:
        }

    }

    /**
     * This method remove all ground
     */
    private void removeAllGround() {
        vVLMasse.removeAllComponents();
        edsGroundsList.clear();
        groundEditViewsList.clear();
    }

    /**
     * This method add ground which is the sum of all supply powers
     */
    private void addToAll() {
        ArrayList<Float> iVeille = new ArrayList<Float>();
        ArrayList<Float> iReveille = new ArrayList<Float>();
        ArrayList<Float> iNom = new ArrayList<Float>();
        ArrayList<Float> tNom = new ArrayList<Float>();
        ArrayList<Float> iPireCas = new ArrayList<Float>();
        ArrayList<Float> tPireCas = new ArrayList<Float>();
        ArrayList<Float> iDysf = new ArrayList<Float>();
        ArrayList<Float> tDysf = new ArrayList<Float>();
        ArrayList<Float> iMst = new ArrayList<Float>();
        ArrayList<Float> tMst = new ArrayList<Float>();
        ArrayList<Float> iPic = new ArrayList<Float>();
        ArrayList<Float> tIPic = new ArrayList<Float>();
        Boolean ok = true;
        for (EdsRobustSupply es : robustSupplysList) {

            iVeille.add(es.getRsedsIVeille());
            iReveille.add(es.getRsedsIReveilleInactif());
            iNom.add(es.getRsedsINomStab());
            tNom.add(es.getRsedsTNomStab());
            iPireCas.add(es.getRsedsIPirecasStab());
            tPireCas.add(es.getRsedsTPireCas());
            iDysf.add(es.getRsedsIdysf());
            tDysf.add(es.getRsedsTdysf());
            iMst.add(es.getRsedsImst());
            tMst.add(es.getRsedsTmst());
            iPic.add(es.getRsedsIMax());
            tIPic.add(es.getRsedsTIMax());
        }

        EdsGroundRobustCurent groundRobustCurent = new EdsGroundRobustCurent(UUID.randomUUID().toString());
        EdsGround edsGround = new EdsGround(UUID.randomUUID().toString(), edsRobustCurentFormData, groundRobustCurent);

        GroundEditView groundView = new GroundEditView(controller, groundRobustCurent, null, edsGround, edsRobustCurentFormData) {
            public void buttonClick(ClickEvent event) {
            }
        };

        groundView.setValue(controller.getBundle().getString("current-conso-tab-mass-rob-choose1"),
                controller.getBundle().getString("current-conso-tab-mass-rob-choose-total"), EDSTools.convertFloatToVide(somme(iVeille)),
                EDSTools.convertFloatToVide(somme(iReveille)), EDSTools.convertFloatToVide(somme(iNom)), EDSTools.convertFloatToVide(somme(tNom)),
                EDSTools.convertFloatToVide(somme(iPireCas)), EDSTools.convertFloatToVide(somme(tPireCas)),
                EDSTools.convertFloatToVide(somme(iDysf)), EDSTools.convertFloatToVide(somme(tDysf)), EDSTools.convertFloatToVide(somme(iMst)),
                EDSTools.convertFloatToVide(somme(tMst)), EDSTools.convertFloatToVide(somme(iPic)), EDSTools.convertFloatToVide(somme(tIPic)));

        groundView.setEnabled(false);
        edsGroundsList.add(edsGround);
        groundEditViewsList.add(groundView);
        vVLMasse.addComponent(groundView);

    }

    /**
     * This method add is called after selecting Ground monitor of the device "other"
     */
    private void addOthers() {
        Button addGround = new Button(controller.getBundle().getString("current-conso-tab-mass-rob-button"));
        vVLMasse.addComponent(addGround);
        addGround.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                addGround();
            }
        });
    }

    /**
     * This method add ground correspond to distinct power supply
     */
    private void addToDistinct() {
        for (EdsRobustSupply es : robustSupplysList) {

            EdsGroundRobustCurent groundRobustCurent = new EdsGroundRobustCurent(UUID.randomUUID().toString());
            EdsGround edsGround = new EdsGround(UUID.randomUUID().toString(), edsRobustCurentFormData, groundRobustCurent);

            GroundEditView groundView = new GroundEditView(controller, groundRobustCurent, es, edsGround, edsRobustCurentFormData) {
                public void buttonClick(ClickEvent event) {
                }
            };

            groundView.setValue(controller.getBundle().getString("current-conso-tab-mass-rob-choose2"),
                    controller.getBundle().getString("current-conso-tab-mass-rob-title") + " " + es.getRsedsSupplyName(),
                    EDSTools.convertFloatToVide(es.getRsedsIVeille()), EDSTools.convertFloatToVide(es.getRsedsIReveilleInactif()),
                    EDSTools.convertFloatToVide(es.getRsedsINomStab()), EDSTools.convertFloatToVide(es.getRsedsTNomStab()),
                    EDSTools.convertFloatToVide(es.getRsedsIPirecasStab()), EDSTools.convertFloatToVide(es.getRsedsTPireCas()),
                    EDSTools.convertFloatToVide(es.getRsedsIdysf()), EDSTools.convertFloatToVide(es.getRsedsTdysf()),
                    EDSTools.convertFloatToVide(es.getRsedsImst()), EDSTools.convertFloatToVide(es.getRsedsTmst()),
                    EDSTools.convertFloatToVide(es.getRsedsIMax()), EDSTools.convertFloatToVide(es.getRsedsTIMax()));

            groundView.setEnabled(false);
            edsGroundsList.add(edsGround);
            groundEditViewsList.add(groundView);
            vVLMasse.addComponent(groundView);

        }
    }

    /**
     * This method sum up the power supply
     * 
     * @param value List of power supply in Float
     * @return Sum of power supply
     */
    private Float somme(ArrayList<Float> value) {
        Float result = Float.NaN;
        for (Float f : value) {
            if (f != null && !f.isNaN()) {
                if (result.isNaN()) {
                    result = 0f;
                }
                result += f;
            }

        }
        if (result.isNaN()) {
            result = null;
        }
        return result;
    }

    /**
     * This method add New ground
     */
    private void addGround() {
        EdsGroundRobustCurent groundRobustCurent = new EdsGroundRobustCurent(UUID.randomUUID().toString());
        EdsGround edsGround = new EdsGround(UUID.randomUUID().toString(), edsRobustCurentFormData, groundRobustCurent);

        GroundEditView groundView = new GroundEditView(controller, groundRobustCurent, null, edsGround, edsRobustCurentFormData) {
            /*
             * (non-Javadoc)
             * 
             * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui. Button.ClickEvent)
             */
            public void buttonClick(ClickEvent event) {
                edsGroundsList.remove(this.getEdsGround());
                groundEditViewsList.remove(this);
                vVLMasse.removeComponent(this);
            }
        };

        groundView.setValue(controller.getBundle().getString("current-conso-tab-mass-rob-choose3"), "", "", "", "", "", "", "", "", "", "", "", "",
                "");

        groundView.setEnabled(true);
        groundView.isDelete(true);
        edsGroundsList.add(edsGround);
        groundEditViewsList.add(groundView);
        vVLMasse.addComponent(groundView);
    }

    /**
     * This method return id of selected option
     * 
     * @return id of selected option
     */
    public Integer getchoixMasse() {
        if ((ChoixMasse) vOGChoix.getValue() == null) {
            return null;
        }
        return ((ChoixMasse) vOGChoix.getValue()).getId();
    }
}
