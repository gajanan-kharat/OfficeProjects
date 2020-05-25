package com.inetpsa.eds.application.content.eds.behavior;

import java.io.File;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.tools.docinfo.ComposantDocInfos;
import com.inetpsa.eds.tools.upload.UploadPicture;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextArea;

/**
 * This class provide component for Behavior of EDS at respective stages
 * 
 * @author Geometric Ltd.
 */
public class ComportementComponent extends GridLayout {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param eds Eds details
     * @param type Type of image
     * @param controller Controller of Eds application
     */
    public ComportementComponent(EdsEds eds, String type, EdsApplicationController controller) {
        this.eds = eds;
        this.type = type;
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of PopupView
     */
    private PopupView vLPopupView;
    /**
     * Variable to hold value of UploadPicture
     */
    private UploadPicture uploadPicture;
    /**
     * Variable to hold value of TextArea
     */
    private TextArea vTAComent;
    /**
     * Variable to hold value of EdsEds
     */
    private EdsEds eds;
    /**
     * Variable to hold value of Images type
     */
    private String type;
    /**
     * Variable to hold value of ComposantDocInfos
     */
    private ComposantDocInfos docInfos;
    /**
     * Variable to hold value of controller of EDS applicationS
     */
    private EdsApplicationController controller;

    /**
     * Initialize behavior Component
     */
    private void init() {
        setColumns(2);
        setRows(5);
        setSpacing(true);
        setMargin(true);
        setSizeFull();

        uploadPicture = new UploadPicture(eds.getEdsId(), type, controller);
        uploadPicture.setWidth("500px");
        addComponent(uploadPicture, 0, 1, 1, 1);

        Label vLvom = new Label(controller.getBundle().getString("eds-comnent") + " :");
        vLvom.addStyleName("h3");
        addComponent(vLvom, 0, 2, 1, 2);

        vTAComent = new TextArea();
        vTAComent.setWidth("500");
        vTAComent.setHeight("200");
        vTAComent.setMaxLength(2048);
        addComponent(vTAComent, 0, 3, 1, 3);

        docInfos = new ComposantDocInfos(controller);
        addComponent(docInfos, 0, 4, 1, 4);
    }

    /**
     * This method set title and description for popup
     * 
     * @param title Title for popup view
     * @param description Description for popup view
     */
    public void setPopupView(String title, String description) {
        Label l = new Label(description);
        vLPopupView = new PopupView(title, l);
        this.addComponent(vLPopupView, 0, 0);

    }

    /**
     * This method set image to Behavior component of EDS
     * 
     * @param image Image to set
     */
    public void setImage(String image) {
        File ressource = new File(controller.getImageFilePath(image));
        if (ressource.exists()) {
            uploadPicture.addImage(new FileResource(ressource, getApplication()), image);
        }
    }

    /**
     * This method returns the image name attached to Behavior component of EDS
     * 
     * @return Name of image
     */
    public String getImage() {
        return uploadPicture.getImName();
    }

    /**
     * This method set the comment
     * 
     * @param com Comment
     */
    public void setCom(String com) {
        if (com == null) {
            com = "";
        }
        vTAComent.setValue(com);
    }

    /**
     * This method returns the comment
     * 
     * @return Comment
     */
    public String getCom() {
        return (String) vTAComent.getValue();
    }

    /**
     * This method set the URL
     * 
     * @param url URL
     */
    public void setURL(String url) {
        docInfos.setValue(url);
    }

    /**
     * This method returns the URL
     * 
     * @return URL
     */
    public String getURLs() {
        return docInfos.getValue();
    }
}
