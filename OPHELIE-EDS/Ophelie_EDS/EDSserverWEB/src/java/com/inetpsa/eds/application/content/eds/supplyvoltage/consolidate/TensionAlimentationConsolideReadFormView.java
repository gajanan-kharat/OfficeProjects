package com.inetpsa.eds.application.content.eds.supplyvoltage.consolidate;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.model.EdsConsolidatedSupplyVoltageFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsPreliminarySupplyVoltageFormData;
import com.inetpsa.eds.tools.EDSTools;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

/**
 * This class is used to build read view for 'Supply Voltage' for Consolidated stage.
 * 
 * @author Geometric Ltd.
 */
public class TensionAlimentationConsolideReadFormView extends A_EdsReadForm {
    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * Variable to store consolidated stage form data.
     */
    private EdsConsolidatedSupplyVoltageFormData formData;
    /**
     * Label for 'Consolidated supply voltage'.
     */
    private Label vLBLTensionAlimentationConsolide;
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
    private Label vLBLTensionMaximaleFonctionnementnominale;
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
     * Label for value of 'Minimal voltage of downgraded operation (Initialisation)'.
     */
    private Label vLBLValeurTensionInitialisation;
    /**
     * Label for value of 'Minimal voltage of nominal operation (Umin)'.
     */
    private Label vLBLValeurTensionMinimalFonctionnementNominale;
    /**
     * Label for value of 'Maximum voltage allowed for a normal functioning (Umax)'.
     */
    private Label vLBLValeurTensionMaximaleFonctionnementNominale;
    /**
     * Label for value of 'High disconnection voltage'.
     */
    private Label vLBLValeurTensionCoupure;
    /**
     * Label for value of 'Maximum voltage of downgraded operation (Restoration)'.
     */
    private Label vLBLValeurTensionRehabilisation;
    /**
     * Label for value of 'Low disconnection voltage'.
     */
    private Label vLBLValeurTensionReset;
    /**
     * Table to display all the fields.
     */
    private Table table;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store preliminary stage form data.
     */
    private EdsPreliminarySupplyVoltageFormData prelimFormData;

    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param data Consolidated stage form data of Supply voltage to be displayed.
     * @param vPreliminaire Preliminary stage form data.
     * @param controller EDS application controller object.
     */
    public TensionAlimentationConsolideReadFormView(EdsEds eds, EdsConsolidatedSupplyVoltageFormData data,
            EdsPreliminarySupplyVoltageFormData vPreliminaire, EdsApplicationController controller) {
        super(controller);
        this.formData = data;
        this.eds = eds;
        this.controller = controller;
        this.prelimFormData = vPreliminaire;
        init();
    }

    /**
     * Initialization method. This method is used to generate the Read view for Supply voltage form.
     */
    private void init() {
        vLBLTensionAlimentationConsolide = new Label(controller.getBundle().getString("Admin-droit-form-volt-conso"));
        vLBLTensionAlimentationConsolide.addStyleName("h1");
        vLBLTensionInitialisation = new Label(controller.getBundle().getString("alim-voltage-data-degr-min"));
        vLBLTensionMinimalFonctionnementNominale = new Label(controller.getBundle().getString("alim-voltage-data-degr-nom"));
        vLBLTensionMinimalFonctionnementNominale.setHeight("25");
        vLBLTensionMaximaleFonctionnementnominale = new Label(controller.getBundle().getString("alim-voltage-data-nom-max"));
        vLBLTensionMaximaleFonctionnementnominale.setHeight("25");
        vLBLTensionCoupure = new Label(controller.getBundle().getString("alim-voltage-data-cut-high-cons"));
        vLBLTensionCoupure.setHeight("25");
        vLBLTensionRehabilisation = new Label(controller.getBundle().getString("alim-voltage-data-degr-max"));
        vLBLTensionRehabilisation.setHeight("25");
        vLBLTensionReset = new Label(controller.getBundle().getString("alim-voltage-data-Reset-cons"));
        vLBLTensionReset.setHeight("25");
        table = new Table("");
        table.setSortDisabled(true);
        table.setHeight("210px");
        vLBLValeurTensionInitialisation = new Label();
        vLBLValeurTensionMinimalFonctionnementNominale = new Label();
        vLBLValeurTensionMaximaleFonctionnementNominale = new Label();
        vLBLValeurTensionCoupure = new Label();
        vLBLValeurTensionRehabilisation = new Label();
        vLBLValeurTensionReset = new Label();
        table.addContainerProperty("Donnée", Label.class, null, controller.getBundle().getString("Pilote-data-tab-title-data"), null, null);
        table.addContainerProperty("Valeur en V", Label.class, null, controller.getBundle().getString("alim-voltage-tab-title-2"), null, null);
        table.setColumnWidth("Donnée", 450);

        table.addItem(new Object[] { vLBLTensionCoupure, vLBLValeurTensionCoupure }, new Integer(3));

        table.addItem(new Object[] { vLBLTensionRehabilisation, vLBLValeurTensionRehabilisation }, new Integer(4));

        table.addItem(new Object[] { vLBLTensionMaximaleFonctionnementnominale, vLBLValeurTensionMaximaleFonctionnementNominale }, new Integer(2));

        table.addItem(new Object[] { vLBLTensionMinimalFonctionnementNominale, vLBLValeurTensionMinimalFonctionnementNominale }, new Integer(1));

        table.addItem(new Object[] { vLBLTensionInitialisation, vLBLValeurTensionInitialisation }, new Integer(0));

        table.addItem(new Object[] { vLBLTensionReset, vLBLValeurTensionReset }, new Integer(5));

        table.setPageLength(5);

        Resource rsrc = ResourceManager.getInstance().getThemeIconResource("icons/image_tension_alimentation.png");
        Embedded image = new Embedded(null, rsrc);
        image.setType(Embedded.TYPE_IMAGE);
        GridLayout vPImage = new GridLayout();
        vPImage.addComponent(image);

        GridLayout vHR = new GridLayout(1, 2);
        vHR.addComponent(table, 0, 0);
        vHR.addComponent(image, 0, 1);
        vHR.setSpacing(true);

        addComponent(vHR);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "menu-app-voltage";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "tension-alim-conso-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return TensionAlimentationConsolideFormBuilder.ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getEdsRef()
     */
    @Override
    public String getEdsRef() {
        return getEds().getEdsRef();
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

        if ("".equals(formData.getCsvValeurTensionInitialisation())) {
            vLBLValeurTensionInitialisation.setValue("--");
        } else {
            vLBLValeurTensionInitialisation.setValue(formData.getCsvValeurTensionInitialisation());
        }

        if ("".equals(formData.getCsvValeurTensionMinimaleFonctionnementNominale())) {
            vLBLValeurTensionMinimalFonctionnementNominale.setValue("--");
        } else {
            vLBLValeurTensionMinimalFonctionnementNominale.setValue(formData.getCsvValeurTensionMinimaleFonctionnementNominale());
        }

        if ("".equals(formData.getCsvValeurTensionMaximalFonctionnementNominale())) {
            vLBLValeurTensionMaximaleFonctionnementNominale.setValue("--");
        } else {
            vLBLValeurTensionMaximaleFonctionnementNominale.setValue(formData.getCsvValeurTensionMaximalFonctionnementNominale());
        }

        if ("".equals(formData.getCsvValeurTensionCouture())) {
            vLBLValeurTensionCoupure.setValue("--");
        } else {
            vLBLValeurTensionCoupure.setValue(formData.getCsvValeurTensionCouture());
        }

        if ("".equals(formData.getCsvValeurTensionRehabilisation())) {
            vLBLValeurTensionRehabilisation.setValue("--");
        } else {
            vLBLValeurTensionRehabilisation.setValue(formData.getCsvValeurTensionRehabilisation());
        }

        if ("".equals(formData.getCsvValeurTensionReset())) {
            vLBLValeurTensionReset.setValue("--");
        } else {
            vLBLValeurTensionReset.setValue(formData.getCsvValeurTensionReset());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        formData = controller.getFormDataModel(eds, formData.getClass());
        prelimFormData = controller.getFormDataModel(eds, prelimFormData.getClass());
    }
}
