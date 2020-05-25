package com.inetpsa.eds.application.content.eds.behavior.robust;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.composants.Comment;
import com.inetpsa.eds.dao.model.EdsComportementRobusteFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.docinfo.LienDocInfo;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide component for reading behavior in robust stage
 * 
 * @author Geometric Ltd.
 */
public class ComportementRobusteReadView extends A_EdsReadForm {
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of EdsComportementRobusteFormData
     */
    private EdsComportementRobusteFormData formData;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of GridLayout
     */
    private GridLayout mainLayout;
    /**
     * Variable to hold value of TabSheet
     */
    private TabSheet vTS;
    /**
     * Variable to hold value of Comment for functional synoptic
     */
    private Comment vLCommentaireF;
    /**
     * Variable to hold value of Comment for electrical synoptic
     */
    private Comment vLCommentaireE;
    /**
     * Variable to hold value of GridLayout for functional synoptic
     */
    private GridLayout vGLFonctionnel;
    /**
     * Variable to hold value of GridLayout for electrical synoptic
     */
    private GridLayout vGLElectrique;
    /**
     * Variable to hold value of VerticalLayout for Function synoptic URL
     */
    private VerticalLayout vVLDocInfoFURLs;
    /**
     * Variable to hold value of VerticalLayout for electrical synoptic URL
     */
    private VerticalLayout vVLDocInfoEURLs;
    /**
     * Variable to hold value of Panel for Function synoptic comment
     */
    private Panel vPCommentaireF;
    /**
     * Variable to hold value of Panel for electrical synoptic comment
     */
    private Panel vPCommentaireE;
    /**
     * Variable to hold value of Embedded for electrical synoptic image
     */
    private Embedded imageElectrique = null;
    /**
     * Variable to hold value of Embedded for Functional synoptic image
     */
    private Embedded imageFonctionnel = null;
    /**
     * Variable to hold value of Panel for electrical synoptic image
     */
    private Panel vPimageElectrique;
    /**
     * Variable to hold value of Panel for Functional synoptic image
     */
    private Panel vPimageFonctionnel;
    /**
     * Variable to hold value of Panel for electrical synoptic Link
     */
    private Panel vPLinkF;
    /**
     * Variable to hold value of Panel for Functional synoptic Link
     */
    private Panel vPLinkE;

    /**
     * Parameterized Constructor
     * 
     * @param eds Eds Details
     * @param robuste form data for Eds Robust stage behavior
     * @param controller Controller of EDS application
     */
    public ComportementRobusteReadView(EdsEds eds, EdsComportementRobusteFormData robuste, EdsApplicationController controller) {
        super(controller);
        this.eds = eds;
        this.formData = robuste;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE

    /**
     * Initialize Behavior of robust stage read view
     */
    private void init() {
        setSpacing(true);

        mainLayout = new GridLayout(1, 2);
        mainLayout.setWidth("100%");

        vTS = new TabSheet();

        // Robust Functional behavior
        vGLFonctionnel = new GridLayout(2, 2);
        vGLFonctionnel.setMargin(true);
        vGLFonctionnel.setSpacing(true);

        vPCommentaireF = new Panel(controller.getBundle().getString("eds-comnent"));
        vPCommentaireF.setWidth("700px");
        vLCommentaireF = new Comment(controller);

        vPCommentaireF.addComponent(vLCommentaireF);
        vGLFonctionnel.addComponent(vPCommentaireF, 1, 0);

        vPimageFonctionnel = new Panel(controller.getBundle().getString("image-title"));
        vPimageFonctionnel.setWidth("600px");

        vGLFonctionnel.addComponent(vPimageFonctionnel, 0, 0);

        vPLinkF = new Panel(controller.getBundle().getString("comp-rob-doc-join"));
        vVLDocInfoFURLs = new VerticalLayout();
        vPLinkF.addComponent(vVLDocInfoFURLs);

        vGLFonctionnel.addComponent(vPLinkF, 0, 1, 1, 1);

        vTS.addTab(vGLFonctionnel, controller.getBundle().getString("comp-rob-syn-fonct"));

        // Robust Electrical behavior

        vGLElectrique = new GridLayout(2, 2);
        vGLElectrique.setMargin(true);
        vGLElectrique.setSpacing(true);
        vTS.addTab(vGLElectrique, controller.getBundle().getString("comp-rob-syn-elec"));

        vPCommentaireE = new Panel(controller.getBundle().getString("eds-comnent"));
        vPCommentaireE.setWidth("700px");

        this.vLCommentaireE = new Comment(controller);

        vPCommentaireE.addComponent(vLCommentaireE);
        vGLElectrique.addComponent(vPCommentaireE, 1, 0);

        vPimageElectrique = new Panel(controller.getBundle().getString("comp-rob-syn-elec"));
        vPimageElectrique.setWidth("600px");

        vGLElectrique.addComponent(vPimageElectrique, 0, 0);

        vPLinkE = new Panel(controller.getBundle().getString("comp-rob-doc-info"));
        vVLDocInfoEURLs = new VerticalLayout();
        vPLinkE.addComponent(vVLDocInfoEURLs);

        vGLElectrique.addComponent(vPLinkE, 0, 1, 1, 1);

        mainLayout.addComponent(this.vTS, 0, 1);

        addComponent(mainLayout);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormName()
     */
    @Override
    public String getFormName() {
        return "menu-app-comp";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getFormTitle()
     */
    @Override
    public String getFormTitle() {
        return "comp-rob-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return ComportementRobusteFormBuilder.ID;
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

        vLCommentaireF.setValue(formData.getCrfdSynoptiqueFonctionnelCommentaire());
        vPimageFonctionnel.removeAllComponents();

        File rsrcF = new File(controller.getImageFilePath(formData.getCrfdSynoptiqueFonctionnelImage()));
        System.out.println(formData.getCrfdSynoptiqueFonctionnelImage());
        imageFonctionnel = new Embedded(null, new FileResource(rsrcF, getApplication()));
        imageFonctionnel.setType(Embedded.TYPE_IMAGE);
        imageFonctionnel.addStyleName("picture");
        if (rsrcF.exists() && !formData.getCrfdSynoptiqueFonctionnelImage().equals("")) {
            vPimageFonctionnel.addComponent(imageFonctionnel);
        } else {
            vPimageFonctionnel.addComponent(new Label(controller.getBundle().getString("comp-rob-mess-no-syn")));
        }

        vVLDocInfoFURLs.removeAllComponents();
        vVLDocInfoFURLs.requestRepaint();
        if (formData.getCrfdSynoptiqueFonctionnelUrls() != null) {

            String[] t = formData.getCrfdSynoptiqueFonctionnelUrls().split(";");
            ArrayList<URL> listURL = new ArrayList<URL>();
            for (int i = 0; i < t.length; i++) {
                URL url = null;
                try {
                    url = new URL(t[i].toString());
                    listURL.add(url);

                    vVLDocInfoFURLs.addComponent(new LienDocInfo(url, controller));
                } catch (MalformedURLException ex) {
                }

            }
        }

        vLCommentaireE.setValue(formData.getCrfdSynoptiqueElectriqueCommentaire());
        vPimageElectrique.removeAllComponents();
        File rsrcE = new File(controller.getImageFilePath(formData.getCrfdSynoptiqueElectriqueImage()));
        imageElectrique = new Embedded(null, new FileResource(rsrcE, getApplication()));
        imageElectrique.setType(Embedded.TYPE_IMAGE);
        imageElectrique.addStyleName("picture");
        if (rsrcE.exists() && !formData.getCrfdSynoptiqueElectriqueImage().equals("")) {
            vPimageElectrique.addComponent(imageElectrique);
        } else {
            vPimageElectrique.addComponent(new Label(controller.getBundle().getString("comp-rob-mess-no-syn")));
        }

        vVLDocInfoEURLs.removeAllComponents();
        vVLDocInfoEURLs.requestRepaint();
        if (formData.getCrfdSynoptiqueElectriqueUrls() != null) {
            String[] t = formData.getCrfdSynoptiqueElectriqueUrls().split(";");
            ArrayList<URL> listURL = new ArrayList<URL>();
            for (int i = 0; i < t.length; i++) {
                URL url = null;
                try {
                    url = new URL(t[i].toString());

                    listURL.add(url);

                    vVLDocInfoEURLs.addComponent(new LienDocInfo(url, controller));
                } catch (MalformedURLException ex) {
                }
            }
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

    /**
     * This method check if URL is valid
     * 
     * @param v String for URL
     * @return Check if URL is valid
     */
    private boolean isURL(String v) {
        try {
            URL url = new URL(v);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
