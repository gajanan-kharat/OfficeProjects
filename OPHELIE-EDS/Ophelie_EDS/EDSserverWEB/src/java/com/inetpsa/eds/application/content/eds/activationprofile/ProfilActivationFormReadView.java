package com.inetpsa.eds.application.content.eds.activationprofile;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.composants.Comment;
import com.inetpsa.eds.dao.model.EdsCurrentStatement;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsMissionActivationFormData;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;

/**
 * This class represents 'Activation Profile' read view.
 * 
 * @author Geometric Ltd.
 */
public class ProfilActivationFormReadView extends A_EdsReadForm {
    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * Variable to store EDS application controller data.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store Mission activation form data.
     */
    private EdsMissionActivationFormData data;

    // PUBLIC

    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param data Activation form data.
     * @param controller EDS application controller object.
     */
    public ProfilActivationFormReadView(EdsEds eds, EdsMissionActivationFormData data, EdsApplicationController controller) {
        super(controller);
        this.eds = eds;
        this.data = data;
        this.controller = controller;
        init();
    }

    // PROTECTED
    /**
     * Label for 'Realization conditions of the activation profile'.
     */
    private Label vLBLSousTitre;
    /**
     * Comment for 'Realization conditions of the activation profile' value.
     */
    private Comment vLBLValeurCommentaire;
    /**
     * Tab-sheet for adding all the tabs/charts.
     */
    private TabSheet vTScontainer;
    /**
     * Layout of main panel.
     */
    private GridLayout mainLayout;

    /**
     * Initialization method. This method is used to create one default tab and other charts tab.
     */
    private void init() {
        setSpacing(true);

        vTScontainer = new TabSheet();

        mainLayout = new GridLayout(1, 4);
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setWidth("100%");

        vLBLSousTitre = new Label(controller.getBundle().getString("profil-activation-condition-activ") + " :");
        vLBLSousTitre.addStyleName("h2");
        mainLayout.addComponent(vLBLSousTitre, 0, 0);

        vLBLValeurCommentaire = new Comment(controller);

        mainLayout.addComponent(vLBLValeurCommentaire, 0, 1);

        vTScontainer.addTab(mainLayout, controller.getBundle().getString("Activ-profil-gen"));
        addComponent(vTScontainer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "Activ-profil-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "Activ-profil-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return ProfilActivationFormBuilder.ID;
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
        vTScontainer.removeAllComponents();

        if (data.getMafdCommentaire() != null) {
            vLBLValeurCommentaire.setValue((String) data.getMafdCommentaire());
            vTScontainer.addTab(mainLayout, controller.getBundle().getString("Activ-profil-gen"));
        }
        for (EdsCurrentStatement statement : data.getEdsCurrentStatements()) {
            CurrentStatementReadView readView = new CurrentStatementReadView(statement, controller);
            vTScontainer.addTab(readView, statement.getCsName() + " (" + statement.getEdsSupplyName() + ")");
        }
        if (vTScontainer.getComponentCount() == 0) {
            HorizontalLayout vLYTblank = new HorizontalLayout();
            vLYTblank.setHeight("54px");
            vLYTblank.setSpacing(true);
            vLYTblank.setMargin(true);
            Label vLBLblank = new Label(controller.getBundle().getString("Activ-profil-no-profil"));
            Label vLBLblankIcon = new Label();
            vLBLblankIcon.setWidth("24px");
            vLBLblankIcon.setHeight("24px");
            vLBLblankIcon.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/blank.png"));
            vLYTblank.addComponent(vLBLblankIcon);
            vLYTblank.addComponent(vLBLblank);
            vLYTblank.setComponentAlignment(vLBLblank, Alignment.BOTTOM_LEFT);
            vTScontainer.addTab(vLYTblank, controller.getBundle().getString("Activ-profil-gen"));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#refreshItems()
     */
    @Override
    public void refreshItems() {
        data = controller.getFormDataModel(eds, EdsMissionActivationFormData.class);
    }
}
