package com.inetpsa.eds.application.content.eds.supplyvoltage.preliminary;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
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
 * This class is used to build read view for 'Supply Voltage' for Preliminary stage.
 * 
 * @author Geometric Ltd.
 */
public class TensionAlimentationPreliminaireFormReadView extends A_EdsReadForm {
    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * Variable to store preliminary stage form data.
     */
    private EdsPreliminarySupplyVoltageFormData formData;
    /**
     * Label for 'Minimal voltage of downgraded operation (Initialisation)'.
     */
    private Label vLBLTensionMinimalFonctionnementInitialisation;
    /**
     * Label for 'Minimal voltage of nominal operation (Umin)'.
     */
    private Label vLBLTensionMinimalFonctionnementNominale;
    /**
     * Label for 'Maximum voltage allowed for a normal functioning (Umax)'.
     */
    private Label vLBLTensionMaximaleFonctionnementNominale;
    /**
     * Label for 'Maximum voltage of downgraded operation (Restoration)'.
     */
    private Label vLBLTensionMaximaleFonctionnementRehabilitation;
    /**
     * Label for value of 'Maximum voltage allowed for a normal functioning (Umax)'.
     */
    private Label vLBLValeurTensionMaximaleFonctionnementNominale;
    /**
     * Label for value of 'Maximum voltage of downgraded operation (Restoration)'.
     */
    private Label vLBLValeurTensionMaximaleFonctionnementRehabilitation;
    /**
     * Label for value of 'Minimal voltage of downgraded operation (Initialisation)'.
     */
    private Label vLBLValeurTensionMinimalFonctionnementInitialisation;
    /**
     * Label for value of 'Minimal voltage of nominal operation (Umin)'.
     */
    private Label vLBLValeurTensionMinimalFonctionnementNominale;
    /**
     * Label for data origin value of 'Maximum voltage allowed for a normal functioning (Umax)'.
     */
    private Label vLBLOrigineTensionMaximaleFonctionnementNominale;
    /**
     * Label for data origin value of 'Maximum voltage of downgraded operation (Restoration)'.
     */
    private Label vLBLOrigineTensionMaximaleFonctionnementRehabilitation;
    /**
     * Label for data origin value of 'Minimal voltage of downgraded operation (Initialisation)'.
     */
    private Label vLBLOrigineTensionMinimalFonctionnementInitialisation;
    /**
     * Label for data origin value of 'Minimal voltage of nominal operation (Umin)'.
     */
    private Label vLBLOrigineTensionMinimalFonctionnementNominale;
    /**
     * Table to display all the fields.
     */
    private Table table;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;

    // PUBLIC

    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param data Preliminary stage form data of Supply voltage to be displayed.
     * @param controller EDS application controller object.
     */
    public TensionAlimentationPreliminaireFormReadView(EdsEds eds, EdsPreliminarySupplyVoltageFormData data, EdsApplicationController controller) {
        super(controller);
        this.eds = eds;
        this.formData = data;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Initialization method. This method is used to generate the Read view for Supply voltage form.
     */
    private void init() {
        setSpacing(true);

        table = new Table("");
        table.setWidth("800px");
        table.setPageLength(4);
        vLBLTensionMaximaleFonctionnementNominale = new Label(controller.getBundle().getString("alim-voltage-data-nom-max"));
        vLBLTensionMaximaleFonctionnementNominale.setHeight("25");
        vLBLTensionMaximaleFonctionnementNominale.setWidth("250");
        vLBLTensionMaximaleFonctionnementRehabilitation = new Label(controller.getBundle().getString("alim-voltage-data-degr-max"));
        vLBLTensionMaximaleFonctionnementRehabilitation.setHeight("25");
        vLBLTensionMinimalFonctionnementInitialisation = new Label(controller.getBundle().getString("alim-voltage-data-degr-min"));
        vLBLTensionMinimalFonctionnementInitialisation.setHeight("25");
        vLBLTensionMinimalFonctionnementNominale = new Label(controller.getBundle().getString("alim-voltage-data-degr-nom"));
        vLBLTensionMinimalFonctionnementNominale.setHeight("25");
        vLBLValeurTensionMaximaleFonctionnementNominale = new Label("");
        vLBLValeurTensionMaximaleFonctionnementNominale.setHeight("25");
        vLBLValeurTensionMaximaleFonctionnementRehabilitation = new Label();
        vLBLValeurTensionMinimalFonctionnementInitialisation = new Label();
        vLBLValeurTensionMinimalFonctionnementNominale = new Label();
        vLBLOrigineTensionMaximaleFonctionnementNominale = new Label();
        vLBLOrigineTensionMaximaleFonctionnementRehabilitation = new Label();
        vLBLOrigineTensionMinimalFonctionnementInitialisation = new Label();
        vLBLOrigineTensionMinimalFonctionnementNominale = new Label();
        table.addContainerProperty("Donnée", Label.class, null, controller.getBundle().getString("Pilote-data-tab-title-data"), null, null);
        table.addContainerProperty("Valeur en V", Label.class, null, controller.getBundle().getString("alim-voltage-tab-title-2"), null, null);
        table.addContainerProperty("Origine de la donnée", Label.class, null, controller.getBundle().getString("alim-voltage-tab-title-3"), null,
                null);

        table.addItem(new Object[] { vLBLTensionMaximaleFonctionnementRehabilitation, vLBLValeurTensionMaximaleFonctionnementRehabilitation,
                vLBLOrigineTensionMaximaleFonctionnementRehabilitation }, new Integer(3));

        table.addItem(new Object[] { vLBLTensionMaximaleFonctionnementNominale, vLBLValeurTensionMaximaleFonctionnementNominale,
                vLBLOrigineTensionMaximaleFonctionnementNominale }, new Integer(2));

        table.addItem(new Object[] { vLBLTensionMinimalFonctionnementNominale, vLBLValeurTensionMinimalFonctionnementNominale,
                vLBLOrigineTensionMinimalFonctionnementNominale }, new Integer(1));

        table.addItem(new Object[] { vLBLTensionMinimalFonctionnementInitialisation, vLBLValeurTensionMinimalFonctionnementInitialisation,
                vLBLOrigineTensionMinimalFonctionnementInitialisation }, new Integer(0));

        Resource rsrc = ResourceManager.getInstance().getThemeIconResource("icons/image_tension_alimentation.png");
        Embedded image = new Embedded(null, rsrc);
        image.setType(Embedded.TYPE_IMAGE);
        GridLayout vGLR = new GridLayout(1, 2);
        vGLR.setSpacing(true);
        vGLR.addComponent(table, 0, 0);
        vGLR.addComponent(image, 0, 1);

        addComponent(vGLR);
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
        return "tension-alim-preliminaire-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return TensionAlimentationPreliminaireFormBuilder.ID;
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

        vLBLOrigineTensionMaximaleFonctionnementNominale.setValue(EDSTools.getWordingValueByLocale(
                formData.getEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale(), controller.getApplication().getLocale()));
        if (formData.getEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementNominale() == null) {
            vLBLOrigineTensionMaximaleFonctionnementNominale.setValue("--");
        }
        vLBLOrigineTensionMaximaleFonctionnementRehabilitation.setValue(EDSTools.getWordingValueByLocale(
                formData.getEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation(), controller.getApplication().getLocale()));
        if (formData.getEdsWordingByPsvOrigineDonneeTensionMaximaleFonctionnementRehabilisation() == null) {
            vLBLOrigineTensionMaximaleFonctionnementRehabilitation.setValue("--");
        }

        vLBLOrigineTensionMinimalFonctionnementInitialisation.setValue(EDSTools.getWordingValueByLocale(
                formData.getEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation(), controller.getApplication().getLocale()));

        if (formData.getEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementInitialisation() == null) {
            vLBLOrigineTensionMinimalFonctionnementInitialisation.setValue(("--"));
        }
        vLBLOrigineTensionMinimalFonctionnementNominale.setValue(EDSTools.getWordingValueByLocale(
                formData.getEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale(), controller.getApplication().getLocale()));

        if (formData.getEdsWordingByPsvOrigineDonneeTensionMinimaleFonctionnementNominale() == null) {
            vLBLOrigineTensionMinimalFonctionnementNominale.setValue("--");
        }

        if ("".equals(formData.getPsvValeurTensionMaximaleFonctionnementNominale())) {
            vLBLValeurTensionMaximaleFonctionnementNominale.setValue("--");
        } else {
            vLBLValeurTensionMaximaleFonctionnementNominale.setValue(formData.getPsvValeurTensionMaximaleFonctionnementNominale());
        }

        vLBLValeurTensionMaximaleFonctionnementRehabilitation.setValue(formData.getPsvValeurTensionMaximaleFonctionnementRehabilisation());
        if ("".equals(vLBLValeurTensionMaximaleFonctionnementRehabilitation.getValue())) {
            vLBLValeurTensionMaximaleFonctionnementRehabilitation.setValue("--");
        }

        vLBLValeurTensionMinimalFonctionnementInitialisation.setValue(formData.getPsvValeurTensionMinimaleFonctionnementInitialisation());
        if ("".equals(vLBLValeurTensionMinimalFonctionnementInitialisation.getValue())) {
            vLBLValeurTensionMinimalFonctionnementInitialisation.setValue("--");
        }

        vLBLValeurTensionMinimalFonctionnementNominale.setValue(formData.getPsvValeurTensionMinimaleFonctionnementNominale());
        if ("".equals(vLBLValeurTensionMinimalFonctionnementNominale.getValue())) {
            vLBLValeurTensionMinimalFonctionnementNominale.setValue("--");
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
    }
}
