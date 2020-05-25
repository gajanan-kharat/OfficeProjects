package com.inetpsa.eds.tools.component;

import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * This Class provide panel for Image
 * 
 * @author Geometric Ltd.
 */
public class ImagePanel extends Panel implements ClickListener {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param caption Caption for image
     * @param controller Controller of EDS application
     */
    public ImagePanel(String caption, EdsApplicationController controller) {
        super(caption);
        this.controller = controller;
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Embedded image
     */
    private Embedded image;

    /**
     * Variable to hold value of Embedded image view
     */
    private Embedded imageView;

    /**
     * Variable to hold value of Window
     */
    private Window subwindow;
    /**
     * Variable to hold value of Controller Of EDS application
     */
    private EdsApplicationController controller;

    /**
     * Initialize ImagePanel
     */
    private void init() {
        subwindow = new Window(controller.getBundle().getString("image-title"));
        VerticalLayout layout = (VerticalLayout) subwindow.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeUndefined();
        subwindow.setModal(true);

        imageView = new Embedded();

        image = new Embedded();

    }

    /**
     * This method is called when image is clicked
     * 
     * @param event An event containing information about the click.
     * @see com.vaadin.event.MouseEvents.ClickListener#click(com.vaadin.event.MouseEvents.ClickEvent)
     */
    public void click(ClickEvent event) {
        getWindow().addWindow(subwindow);
    }

    /**
     * This method add image to view
     * 
     * @param fileResource source of image file
     */
    public void addImage(FileResource fileResource) {
        image.setSource(fileResource);
        image.setType(Embedded.TYPE_IMAGE);
        image.addStyleName("picture");
        image.addListener(this);
        VerticalLayout layout = (VerticalLayout) getContent();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeUndefined();
        addComponent(image);
        layout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);

        imageView.setSource(fileResource);
        imageView.setType(Embedded.TYPE_IMAGE);
        subwindow.addComponent(imageView);

    }
}
