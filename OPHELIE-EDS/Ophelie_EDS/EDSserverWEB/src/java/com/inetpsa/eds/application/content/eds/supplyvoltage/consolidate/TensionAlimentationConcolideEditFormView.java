package com.inetpsa.eds.application.content.eds.supplyvoltage.consolidate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsEditForm;

import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.model.EdsConsolidatedSupplyVoltageFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPreliminarySupplyVoltageFormData;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import java.util.Collection;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.util.Collections;
import com.inetpsa.eds.dao.model.EdsWording;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import java.util.Date;
import java.util.List;

/**
 * This class is used to build edit view for 'Supply Voltage' for Consolidated stage.
 * 
 * @author Geometric Ltd.
 */
public class TensionAlimentationConcolideEditFormView extends A_EdsEditForm {
    /**
     * Variable to store preliminary stage form data.
     */
    EdsPreliminarySupplyVoltageFormData prelimFormData;

    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param data Consolidated stage form data of Supply voltage to be displayed.
     * @param vPreliminaire Preliminary stage form data.
     * @param controller EDS application controller object.
     */
    public TensionAlimentationConcolideEditFormView(EdsEds eds, EdsConsolidatedSupplyVoltageFormData data,
            EdsPreliminarySupplyVoltageFormData vPreliminaire, EdsApplicationController controller) {
        this.eds = eds;
        this.formData = data;
        this.prelimFormData = vPreliminaire;
        this.controller = controller;
        init();
    }

    /**
     * Label for 'Minimal voltage of downgraded operation (Initialisation)'.
     */
    private Label vLBLTensionInitialisation;
    /**
     * Label for 'Minimal voltage of nominal operation (Umin)'.
     */
    private Label vLBLTensionMinimalFonctionnementNominale;
    /**
     * Label for 'Maximum voltage allowed for a normal functioning (Umax)'.
     */
    private Label vLBLTensionMaximaleFonctionnementNominale;
    /**
     * Label for 'High disconnection voltage'.
     */
    private Label vLBLTensionCoupure;
    /**
     * Label for 'Maximum voltage of downgraded operation (Restoration)'.
     */
    private Label vLBLTensionRehabilisation;
    /**
     * Label for 'Low disconnection voltage'.
     */
    private Label vLBLTensionReset;
    /**
     * Text field for 'Maximum voltage allowed for a normal functioning (Umax)'.
     */
    private TextField vTXTTensionMaximaleFonctionnementNominale;
    /**
     * Text field for 'High disconnection voltage'.
     */
    private TextField vTXTTensionCoupure;
    /**
     * Text field for 'Minimal voltage of downgraded operation (Initialisation)'.
     */
    private TextField vTXTTensionInitialisation;
    /**
     * Text field for 'Minimal voltage of nominal operation (Umin)'.
     */
    private TextField vTXTTensionMinimalFonctionnementNominale;
    /**
     * Text field for 'Maximum voltage of downgraded operation (Restoration)'.
     */
    private TextField vTXTTensionRehabilisation;
    /**
     * Text field for 'Low disconnection voltage'.
     */
    private TextField vTXTTensionReset;
    /**
     * Table to display all the fields.
     */
    private Table table;
    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * Variable to store Consolidated Supply voltage from data.
     */
    private EdsConsolidatedSupplyVoltageFormData formData;
    /**
     * Button for 'Data transfer'.
     */
    private Button vBUTReportDonnees;
    /**
     * List of EDS wordings.
     */
    private List<EdsWording> OriginDonnee;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;

    /**
     * Initialization method. This method is used to generate the Edit view for Supply voltage form.
     */
    private void init() {
        vLBLTensionRehabilisation = new Label(controller.getBundle().getString("alim-voltage-data-degr-max"));
        table = new Table("");
        // table.setWidth( "100%" );
        table.setHeight("210px");

        vLBLTensionReset = new Label(controller.getBundle().getString("alim-voltage-data-Reset-cons"));
        vLBLTensionMaximaleFonctionnementNominale = new Label(controller.getBundle().getString("alim-voltage-data-nom-max"));
        vLBLTensionCoupure = new Label(controller.getBundle().getString("alim-voltage-data-cut-high-cons"));
        vLBLTensionInitialisation = new Label(controller.getBundle().getString("alim-voltage-data-degr-min"));
        vLBLTensionMinimalFonctionnementNominale = new Label(controller.getBundle().getString("alim-voltage-data-degr-nom"));

        vTXTTensionRehabilisation = new TextField();
        vTXTTensionRehabilisation.setNullRepresentation("");
        vTXTTensionRehabilisation.setWidth("200px");
        vTXTTensionReset = new TextField();
        vTXTTensionReset.setNullRepresentation("");
        vTXTTensionReset.setWidth("200px");
        vTXTTensionMaximaleFonctionnementNominale = new TextField();
        vTXTTensionMaximaleFonctionnementNominale.setNullRepresentation("");
        vTXTTensionMaximaleFonctionnementNominale.setWidth("200px");
        vTXTTensionCoupure = new TextField();
        vTXTTensionCoupure.setNullRepresentation("");
        vTXTTensionCoupure.setWidth("200px");
        vTXTTensionInitialisation = new TextField();
        vTXTTensionInitialisation.setNullRepresentation("");
        vTXTTensionInitialisation.setWidth("200px");
        vTXTTensionMinimalFonctionnementNominale = new TextField();
        vTXTTensionMinimalFonctionnementNominale.setNullRepresentation("");
        vTXTTensionMinimalFonctionnementNominale.setWidth("200px");

        table.addContainerProperty("Donnée", Label.class, null, controller.getBundle().getString("Pilote-data-tab-title-data"), null, null);
        table.addContainerProperty("Valeur", TextField.class, null, controller.getBundle().getString("current-conso-tab-gen-val"), null, null);

        table.setColumnWidth("Donnée", 450);
        table.addItem(new Object[] { vLBLTensionCoupure, vTXTTensionCoupure }, new Integer(3));

        table.addItem(new Object[] { vLBLTensionRehabilisation, vTXTTensionRehabilisation }, new Integer(4));

        table.addItem(new Object[] { vLBLTensionMaximaleFonctionnementNominale, vTXTTensionMaximaleFonctionnementNominale }, new Integer(2));

        table.addItem(new Object[] { vLBLTensionMinimalFonctionnementNominale, vTXTTensionMinimalFonctionnementNominale }, new Integer(1));

        table.addItem(new Object[] { vLBLTensionInitialisation, vTXTTensionInitialisation }, new Integer(0));

        table.addItem(new Object[] { vLBLTensionReset, vTXTTensionReset }, new Integer(5));
        table.setPageLength(3);
        Resource rsrc = ResourceManager.getInstance().getThemeIconResource("icons/image_tension_alimentation.png");
        Embedded image = new Embedded(null, rsrc);
        image.setType(Embedded.TYPE_IMAGE);
        GridLayout vGLH = new GridLayout(1, 3);
        vGLH.setSpacing(true);
        // vGLH.setWidth( "100%" );

        vBUTReportDonnees = new Button(controller.getBundle().getString("alim-voltage-cons-button"));

        vGLH.addComponent(table, 0, 0);
        vGLH.addComponent(vBUTReportDonnees, 0, 1);
        vGLH.setComponentAlignment(vBUTReportDonnees, Alignment.MIDDLE_RIGHT);
        vGLH.addComponent(image, 0, 2);
        addComponent(vGLH);

        vBUTReportDonnees.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                final Window subwindow;
                Label vLABWindow = new Label(controller.getBundle().getString("alim-voltage-mess"));
                Button vBUTOUI = new Button(controller.getBundle().getString("consolid-qcf-oui"));
                Button vBUTNON = new Button(controller.getBundle().getString("consolid-qcf-non"));
                HorizontalLayout vHRSUBW = new HorizontalLayout();
                vHRSUBW.addComponent(vBUTOUI);
                vHRSUBW.addComponent(vBUTNON);
                subwindow = new Window(controller.getBundle().getString("alim-voltage-cons-button"));
                subwindow.setPositionX(700);
                subwindow.setPositionY(400);
                subwindow.addComponent(vLABWindow);
                subwindow.addComponent(vHRSUBW);
                subwindow.setWidth("600");

                getWindow().addWindow(subwindow);
                vBUTOUI.addListener(new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        vTXTTensionInitialisation.setValue(prelimFormData.getPsvValeurTensionMinimaleFonctionnementInitialisation());
                        vTXTTensionMaximaleFonctionnementNominale.setValue(prelimFormData.getPsvValeurTensionMaximaleFonctionnementNominale());
                        vTXTTensionMinimalFonctionnementNominale.setValue(prelimFormData.getPsvValeurTensionMinimaleFonctionnementNominale());
                        vTXTTensionRehabilisation.setValue(prelimFormData.getPsvValeurTensionMaximaleFonctionnementRehabilisation());
                        subwindow.setVisible(false);
                    }
                });
                vBUTNON.addListener(new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        subwindow.setVisible(false);
                    }
                });

            }
        });

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        if (!("".equals(vTXTTensionMaximaleFonctionnementNominale.getValue())) && vTXTTensionMaximaleFonctionnementNominale.getValue() != null) {
            try {
                float a = new Float(vTXTTensionMaximaleFonctionnementNominale.toString());
                if (a < 0) {
                    getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                    return false;
                }

            } catch (Exception e) {
                getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                return false;
            }
        }
        if (!("".equals(vTXTTensionReset.getValue())) && vTXTTensionReset.getValue() != null) {
            try {
                float a = new Float(vTXTTensionReset.toString());
                if (a < 0) {
                    getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                    return false;
                }

            } catch (Exception e) {
                getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                return false;
            }
        }
        if (!("".equals(vTXTTensionRehabilisation.getValue())) && vTXTTensionRehabilisation.getValue() != null) {
            try {
                float a = new Float(vTXTTensionRehabilisation.getValue().toString());
                if (a < 0) {
                    getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                    return false;
                }

            } catch (Exception e) {
                getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                return false;
            }
        }
        if (!("".equals(vTXTTensionMinimalFonctionnementNominale.getValue())) && vTXTTensionMinimalFonctionnementNominale.getValue() != null) {
            try {
                float a = new Float(vTXTTensionMinimalFonctionnementNominale.getValue().toString());
                if (a < 0) {
                    getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                    return false;
                }

            } catch (Exception e) {
                getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                return false;
            }
        }
        if (!("".equals(vTXTTensionInitialisation.getValue())) && vTXTTensionInitialisation.getValue() != null) {
            try {
                float a = new Float(vTXTTensionInitialisation.getValue().toString());
                if (a < 0) {
                    getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                    return false;
                }

            } catch (Exception e) {
                getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                return false;
            }
        }
        if (!("".equals(vTXTTensionCoupure.getValue())) && vTXTTensionCoupure.getValue() != null) {
            try {
                float a = new Float(vTXTTensionCoupure.getValue().toString());
                if (a < 0) {
                    getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                    return false;
                }

            } catch (Exception e) {
                getWindow().showNotification(controller.getBundle().getString("suply-voltage-error-massage"));
                return false;
            }
        }

        return true;

        /*
         * if ( vTXTTensionMaximaleFonctionnementNominale.getValue().toString().equals( "" ) ) { vTXTTensionMaximaleFonctionnementNominale.setValue(
         * "9999" ); } if ( vTXTTensionReset.getValue().toString().equals( "" ) ) { vTXTTensionReset.setValue( "9999" ); } if (
         * vTXTTensionRéhabilisation.getValue().toString().equals( "" ) ) { vTXTTensionRéhabilisation.setValue( "9999" ); } if (
         * vTXTTensionMinimalFonctionnementNominale.getValue().toString().equals( "" ) ) { vTXTTensionMinimalFonctionnementNominale.setValue( "9999"
         * ); } if ( vTXTTensionInitialisation.getValue().toString().equals( "" ) ) { vTXTTensionInitialisation.setValue( "9999" ); } if (
         * vTXTTensionCoupure.getValue().toString().equals( "" ) ) { vTXTTensionCoupure.setValue( "9999" ); } try { float a = new Float(
         * vTXTTensionReset.toString() ); float b = new Float( vTXTTensionRéhabilisation.toString() ); float c = new Float(
         * vTXTTensionMaximaleFonctionnementNominale.toString() ); float d = new Float( vTXTTensionCoupure.toString() ); float e = new Float(
         * vTXTTensionInitialisation.toString() ); float f = new Float( vTXTTensionMinimalFonctionnementNominale.toString() ); if ( a < 0 || b < 0 ||
         * c < 0 || d < 0 || e < 0 || f < 0 ) { getWindow().showNotification(
         * "Un ou plusieurs champs ne respectent pas le format imposé (nombres réels positifs)" ); return false; } else { return true; } } catch(
         * Exception e ) { getWindow().showNotification( "Un ou plusieurs champs ne respectent pas le format imposé (nombres réels positifs exeption)"
         * ); return false; }
         */

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        if (isValid()) {

            formData.setCsvValeurTensionCouture((String) vTXTTensionCoupure.getValue());
            formData.setCsvValeurTensionInitialisation((String) vTXTTensionInitialisation.getValue());
            formData.setCsvValeurTensionMaximalFonctionnementNominale((String) vTXTTensionMaximaleFonctionnementNominale.getValue());
            formData.setCsvValeurTensionMinimaleFonctionnementNominale((String) vTXTTensionMinimalFonctionnementNominale.getValue());
            formData.setCsvValeurTensionRehabilisation((String) vTXTTensionRehabilisation.getValue());
            formData.setCsvValeurTensionReset((String) vTXTTensionReset.getValue());
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
        vTXTTensionCoupure.setValue(formData.getCsvValeurTensionCouture());

        vTXTTensionInitialisation.setValue(formData.getCsvValeurTensionInitialisation());

        vTXTTensionMaximaleFonctionnementNominale.setValue(formData.getCsvValeurTensionMaximalFonctionnementNominale());

        vTXTTensionMinimalFonctionnementNominale.setValue((formData.getCsvValeurTensionMinimaleFonctionnementNominale()));

        vTXTTensionRehabilisation.setValue(formData.getCsvValeurTensionRehabilisation());

        vTXTTensionReset.setValue(formData.getCsvValeurTensionReset());

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToSave()
     */
    @Override
    public Collection<Object> getAllItemsToSave() {
        return Collections.singleton((Object) formData);
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
        formData = controller.getFormDataModel(eds, formData.getClass());
        prelimFormData = controller.getFormDataModel(eds, prelimFormData.getClass());
    }
}
