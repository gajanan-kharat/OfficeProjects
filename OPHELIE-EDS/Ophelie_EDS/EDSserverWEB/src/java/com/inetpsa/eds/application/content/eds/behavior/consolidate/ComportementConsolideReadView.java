package com.inetpsa.eds.application.content.eds.behavior.consolidate;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.A_EdsReadForm;
import com.inetpsa.eds.application.content.eds.composants.Comment;
import com.inetpsa.eds.dao.model.EdsComportementConsolideFormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.component.ImagePanel;
import com.inetpsa.eds.tools.docinfo.LienDocInfo;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * This class provide component for reading behavior in consolidate stage
 * 
 * @author Geometric Ltd.
 */
public class ComportementConsolideReadView extends A_EdsReadForm {
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of EdsComportementConsolideFormData
     */
    private EdsComportementConsolideFormData data;
    /**
     * Variable to hold value of controller of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Parameterized Constructor
     * 
     * @param eds Eds Details
     * @param data form data for Eds Consolidate stage behavior
     * @param controller Controller of EDS application
     */
    public ComportementConsolideReadView(EdsEds eds, EdsComportementConsolideFormData data, EdsApplicationController controller) {
        super(controller);
        this.eds = eds;
        this.data = data;
        this.controller = controller;
        init();
    }

    /**
     * Variable to hold value of ImagePanel for RLC Image
     */
    private ImagePanel vPImageRLC;
    /**
     * Variable to hold value of Panel for RLC comment
     */
    private Panel vPCommentRLC;
    /**
     * Variable to hold value of Panel for RLC doc info
     */
    private Panel vPDocInfosRLC;
    /**
     * Variable to hold value of VerticalLayout for RLC URLs
     */
    private VerticalLayout vVLDocInfoRclURLs;
    /**
     * Variable to hold value of VerticalLayout for Saber URLs
     */
    private VerticalLayout vVLDocInfoSaberURLs;
    /**
     * Variable to hold value of ImagePanel for Saber Image
     */
    private ImagePanel vPImageSABER;
    /**
     * Variable to hold value of Panel for Saber comment
     */
    private Panel vPCommentSABER;
    /**
     * Variable to hold value of Panel for Saber Doc info
     */
    private Panel vPDocInfosSABER;
    /**
     * Variable to hold value of GridLayout
     */
    private GridLayout mainLayout;
    /**
     * Variable to hold value of GridLayout for RLC
     */
    private GridLayout vGLRcl;
    /**
     * Variable to hold value of GridLayout for Saber
     */
    private GridLayout vGLSaber;
    /**
     * Variable to hold value of TabSheet
     */
    private TabSheet vTS;
    /**
     * Variable to hold value of label for control type
     */
    private Label vLBLTypePilotage;
    /**
     * Variable to hold value of label for Frequency control change
     */
    private Label vLBLPlagePilotage;
    /**
     * Variable to hold value of label for control type value
     */
    private Label vLBLValeurTypePilotage;
    /**
     * Variable to hold value of label for frequency control change value
     */
    private Label vLBLValeurPlagePilotage;
    /**
     * Variable to hold value of Comment for RLC
     */
    private Comment vLBLCommentRLC;
    /**
     * Variable to hold value of Comment for Saber
     */
    private Comment vLBLCommentSABER;
    /**
     * Variable to hold array of String
     */
    private String[] t;

    /**
     * Initialize Behavior of consolidate stage read view
     */
    private void init() {
        setSpacing(true);

        mainLayout = new GridLayout(2, 3);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        mainLayout.setWidth("100%");
        mainLayout.setColumnExpandRatio(1, 0.8f);
        mainLayout.setColumnExpandRatio(0, 0.2f);

        vLBLTypePilotage = new Label(controller.getBundle().getString("comp-consolide-type-pilotage") + " :");
        vLBLTypePilotage.addStyleName("h3");
        mainLayout.addComponent(vLBLTypePilotage, 0, 0);

        vLBLValeurTypePilotage = new Label(data.getCocofdTypePilotage() == null ? "--" : data.getCocofdTypePilotage());
        vLBLValeurTypePilotage.addStyleName("h3");
        mainLayout.addComponent(vLBLValeurTypePilotage, 1, 0);

        vLBLPlagePilotage = new Label(controller.getBundle().getString("comp-consolide-freq-plage") + ":");
        vLBLPlagePilotage.addStyleName("h3");
        mainLayout.addComponent(vLBLPlagePilotage, 0, 1);
        vLBLValeurPlagePilotage = new Label(data.getCocofdPlageFrequence() == null ? "--" : data.getCocofdPlageFrequence());
        vLBLValeurPlagePilotage.addStyleName("h3");
        mainLayout.addComponent(vLBLValeurPlagePilotage, 1, 1);

        vTS = new TabSheet();

        /**
         * ***Model RLC*****
         */
        vGLRcl = new GridLayout(2, 2);
        vGLRcl.setMargin(true);
        vGLRcl.setSpacing(true);
        vGLRcl.setWidth("100%");

        vPImageRLC = new ImagePanel(controller.getBundle().getString("image-title"), controller);

        vGLRcl.addComponent(vPImageRLC, 0, 0);

        vPCommentRLC = new Panel(controller.getBundle().getString("eds-comnent"));
        vLBLCommentRLC = new Comment(controller);

        vPCommentRLC.addComponent(vLBLCommentRLC);
        vGLRcl.addComponent(vPCommentRLC, 1, 0);

        vPDocInfosRLC = new Panel(controller.getBundle().getString("comp-rob-doc-join"));
        vVLDocInfoRclURLs = new VerticalLayout();
        vPDocInfosRLC.addComponent(vVLDocInfoRclURLs);

        vGLRcl.addComponent(vPDocInfosRLC, 0, 1, 1, 1);

        vTS.addTab(vGLRcl, controller.getBundle().getString("comp-consolide-rlc-model"));

        /**
         * ***Model SABER*****
         */
        vGLSaber = new GridLayout(2, 2);
        vGLSaber.setMargin(true);
        vGLSaber.setSpacing(true);
        vGLSaber.setWidth("100%");
        vPImageSABER = new ImagePanel(controller.getBundle().getString("comp-consolide-saber-shema"), controller);

        vGLSaber.addComponent(vPImageSABER, 0, 0);

        vPCommentSABER = new Panel(controller.getBundle().getString("eds-comnent"));
        vLBLCommentSABER = new Comment(controller);

        vPCommentSABER.addComponent(vLBLCommentSABER);
        vGLSaber.addComponent(vPCommentSABER, 1, 0);

        vPDocInfosSABER = new Panel(controller.getBundle().getString("doc-info-incrustation"));
        vVLDocInfoSaberURLs = new VerticalLayout();
        vPDocInfosSABER.addComponent(vVLDocInfoSaberURLs);

        vGLSaber.addComponent(vPDocInfosSABER, 0, 1, 1, 1);

        vTS.addTab(vGLSaber, controller.getBundle().getString("comp-consolide-saber-model"));

        mainLayout.addComponent(vTS, 0, 2, 1, 2);
        addComponent(mainLayout);
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
        return "comp-cons-title";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsReadForm#getID()
     */
    @Override
    public String getID() {
        return ComportementConsolideFormBuilder.ID;
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
        vLBLValeurPlagePilotage.setValue(data.getCocofdPlageFrequence());
        vLBLValeurTypePilotage.setValue(data.getCocofdTypePilotage());

        vLBLCommentRLC.setValue(data.getCocofdModeleRclCommentaire());

        File rsRLC = new File(controller.getImageFilePath(data.getCocofdModeleRlcImage()));
        if (rsRLC.exists()) {
            vPImageRLC.addImage(new FileResource(rsRLC, getApplication()));
        }

        vVLDocInfoRclURLs.removeAllComponents();
        vVLDocInfoRclURLs.requestRepaint();
        if ((data.getCocofdModeleRclUrls()) != null) {
            t = data.getCocofdModeleRclUrls().split(";");
            ArrayList<URL> listURL = new ArrayList<URL>();
            for (int i = 0; i < t.length; i++) {
                URL url = null;
                try {
                    url = new URL(t[i].toString());
                    listURL.add(url);

                    vVLDocInfoRclURLs.addComponent(new LienDocInfo(url, controller));
                } catch (MalformedURLException ex) {
                }
            }
        }

        vLBLCommentSABER.setValue(data.getCocofdModeleSaberCommentaire());

        vPImageSABER.removeAllComponents();
        File vRscSABER = new File(controller.getImageFilePath(data.getCocofdModeleSaberImage()));
        if (vRscSABER.exists()) {
            vPImageSABER.addImage(new FileResource(vRscSABER, getApplication()));
        }

        vVLDocInfoSaberURLs.removeAllComponents();
        vVLDocInfoSaberURLs.requestRepaint();
        if ((data.getCocofdModeleSaberUrls()) != null) {
            t = data.getCocofdModeleSaberUrls().split(";");
            ArrayList<URL> listURL = new ArrayList<URL>();
            for (int i = 0; i < t.length; i++) {
                URL url = null;
                try {
                    url = new URL(t[i].toString());
                    listURL.add(url);
                    vVLDocInfoSaberURLs.addComponent(new LienDocInfo(url, controller));
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
        data = controller.getFormDataModel(eds, data.getClass());
    }
}
