package com.inetpsa.eds.application.content.eds.missionprofile;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsMissionProfil;
import com.inetpsa.eds.dao.model.EdsMissionProfilFormData;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.*;
import com.vaadin.ui.TabSheet.Tab;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * This class represents 'Mission Profile' read view.
 * 
 * @author Geometric Ltd.
 */
public class ProfilMissionFormReadView extends A_EdsReadForm {
    /**
     * Tab sheet to view profile sheets.
     */
    private TabSheet t;
    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;
    /**
     * Variable to store EDS mission profile data.
     */
    private EdsMissionProfilFormData formData;
    /**
     * Variable to store EDS Mission profile read view.
     */
    private ProfilMissionReadView readview;
    /**
     * List of all the Profiles data.
     */
    private List<ProfilMissionReadView> readViews;

    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param formData EDS mission profile data.
     * @param controller EDS application controller object.
     */
    public ProfilMissionFormReadView(EdsEds eds, EdsMissionProfilFormData formData, EdsApplicationController controller) {
        super(controller);
        this.eds = eds;
        this.formData = formData;
        this.controller = controller;

        // init
        setSpacing(true);

        readViews = new ArrayList<ProfilMissionReadView>();
        t = new TabSheet();
        t.setWidth("100%");
        addComponent(t);
        for (EdsMissionProfil profil : new TreeSet<EdsMissionProfil>(formData.getEdsMissionProfils())) {
            readview = new ProfilMissionReadView(eds, profil, controller);
            t.addTab(readview, profil.getMpNomProfil(), null, profil.getMpRank());
            readViews.add(readview);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "menu-app-mission-profil";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "menu-app-mission-profil";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return ProfilMissionFormBuilder.ID;
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
        readViews.clear();
        removeAllComponents();

        if (!formData.getEdsMissionProfils().isEmpty()) {
            t = new TabSheet();
            addComponent(t);

            for (EdsMissionProfil profil : new TreeSet<EdsMissionProfil>(formData.getEdsMissionProfils())) {
                ProfilMissionReadView view = new ProfilMissionReadView(eds, profil, controller);

                Tab tabNew = t.addTab(view, (String) profil.getMpNomProfil(), null, profil.getMpRank());
                readViews.add(view);
            }

            if (formData.getEdsMissionProfils().isEmpty()) {
                t.removeAllComponents();
            }
        } else {
            HorizontalLayout vLYTblank = new HorizontalLayout();
            vLYTblank.setHeight("24px");
            vLYTblank.setSpacing(true);
            Label vLBLblank = new Label(controller.getBundle().getString("Activ-profil-no-profil"));
            Label vLBLblankIcon = new Label();
            vLBLblankIcon.setWidth("24px");
            vLBLblankIcon.setHeight("24px");
            vLBLblankIcon.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/blank.png"));
            vLYTblank.addComponent(vLBLblankIcon);
            vLYTblank.addComponent(vLBLblank);
            vLYTblank.setComponentAlignment(vLBLblank, Alignment.BOTTOM_LEFT);
            addComponent(vLYTblank);
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
