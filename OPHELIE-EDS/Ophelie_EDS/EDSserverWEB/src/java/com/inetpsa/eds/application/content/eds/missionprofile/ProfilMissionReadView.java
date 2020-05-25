package com.inetpsa.eds.application.content.eds.missionprofile;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.composants.Comment;

import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsMissionProfil;
import com.inetpsa.eds.tools.docinfo.LienDocInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * This class represents 'Mission Profile' edit view data for each tab-sheet.
 * 
 * @author Geometric Ltd.
 */
public class ProfilMissionReadView extends VerticalLayout {
    /**
     * EDS details.
     */
    private EdsEds eds;
    /**
     * Variable to store EDS mission profile data.
     */
    private EdsMissionProfil profil;
    // PUBLIC
    /**
     * Layout of the main panel.
     */
    private GridLayout mainLayout;
    /**
     * Layout for Doc info.
     */
    private VerticalLayout vVLDocInfos;
    /**
     * Label for 'Activation number'.
     */
    private Label vLNbreActivation;
    /**
     * Label for Units.
     */
    private Label vLBLUnit;
    /**
     * Label for Notes.
     */
    private Comment vLBLContenuCommentaire;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;

    /**
     * Parameterized constructor.
     * 
     * @param eds EDS details.
     * @param profil EDS mission profile data data.
     * @param controller EDS application controller object.
     */
    public ProfilMissionReadView(EdsEds eds, EdsMissionProfil profil, EdsApplicationController controller) {

        this.profil = profil;
        this.eds = eds;
        this.controller = controller;
        init();
    }

    /**
     * Initialization method. This method is used to create the form in read view for Mission profile.
     */
    private void init() {
        mainLayout = new GridLayout(1, 6);
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setWidth("100%");

        HorizontalLayout vHL = new HorizontalLayout();
        vHL.setSpacing(true);
        Label vLActivation = new Label(controller.getBundle().getString("Activ-profil-nombre") + " :");
        vLActivation.addStyleName("h2");
        vHL.addComponent(vLActivation);

        vLNbreActivation = new Label();
        mainLayout.addComponent(vHL, 0, 1);
        vHL.addComponent(vLNbreActivation);
        vLNbreActivation.setHeight("22");
        vHL.setComponentAlignment(vLNbreActivation, Alignment.BOTTOM_LEFT);

        Label vLCommentaire = new Label(controller.getBundle().getString("eds-comnent") + " :");
        vLCommentaire.addStyleName("h2");
        mainLayout.addComponent(vLCommentaire, 0, 2);

        vLBLContenuCommentaire = new Comment(controller);
        this.vLBLContenuCommentaire.setValue(profil.getMpCommentaire());

        mainLayout.addComponent(vLBLContenuCommentaire, 0, 3);

        Label vLDocInfo = new Label(controller.getBundle().getString("comp-rob-doc-join"));
        vLDocInfo.addStyleName("h2");
        mainLayout.addComponent(vLDocInfo, 0, 4);

        vVLDocInfos = new VerticalLayout();
        mainLayout.addComponent(vVLDocInfos, 0, 5);
        addComponent(mainLayout);
        refreshViewData();
    }

    /**
     * This method is used to validate the URL provided.
     * 
     * @param v URL.
     * @return true if URL is valid, else false.
     */
    private boolean isURL(String v) {
        try {
            URL url = new URL(v);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * This method is used to retrieve the Form name.
     * 
     * @return Form name.
     */
    public String getFormName() {
        return "menu-app-mission-profil";
    }

    /**
     * This method is used to retrieve the title.
     * 
     * @return Empty string.
     */
    public String getFormTitle() {
        return "";
    }

    /**
     * This method is used to retrieve the ID
     * 
     * @return ID.
     */
    public String getID() {
        return "eds-profil-mission";
    }

    /**
     * This method is used to retrieve the EDS reference number.
     * 
     * @return EDS reference.
     */
    public String getEdsRef() {
        return eds.getEdsRef();
    }

    /**
     * This method is used to retrieve the EDS details.
     * 
     * @return EDS details.
     */
    public EdsEds getEds() {

        return eds;
    }

    /**
     * This method is used to refresh data on current view.
     */
    public void refreshViewData() {

        vLBLContenuCommentaire.setValue(profil.getMpCommentaire());

        vLNbreActivation.setValue(profil.getMpNombreActivation() + " " + controller.getBundle().getString("Activ-profil-multi") + " "
                + convertIntoString(profil.getMpUnit()));
        ArrayList<URL> urls = new ArrayList<URL>();
        if (profil.getMpDocsInfosUrls() != null) {
            for (String v : profil.getMpDocsInfosUrls().split(";")) {
                URL url;
                try {
                    url = new URL(v);
                    urls.add(url);

                    vVLDocInfos.addComponent(new LienDocInfo(url, controller));
                } catch (MalformedURLException ex) {
                }

            }
        }

    }

    /**
     * This method is used to refresh items.
     */
    public void refreshItems() {
        // DO NOTHING
    }

    /**
     * This method is used to retrieve the measure/unit using the integer.
     * 
     * @param i Int value of Measure/Unit.
     * @return Measure/Unit.
     */
    private String convertIntoString(int i) {
        String unit = "";
        switch (i) {
        case 0:
            unit = controller.getBundle().getString("Activ-profil-hour");
            break;
        case 1:
            unit = controller.getBundle().getString("Activ-profil-day");
            break;
        case 2:
            unit = controller.getBundle().getString("Activ-profil-year");
            break;
        }

        return unit;
    }
}
