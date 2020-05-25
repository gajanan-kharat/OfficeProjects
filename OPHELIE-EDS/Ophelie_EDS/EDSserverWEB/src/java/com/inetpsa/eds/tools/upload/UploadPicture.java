package com.inetpsa.eds.tools.upload;

import java.io.File;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;

import com.inetpsa.eds.application.EdsApplicationController;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Window.Notification;

/**
 * THis class perform uploading of images.
 * 
 * @author Geometric Ltd.
 */
public class UploadPicture extends CustomComponent implements Upload.SucceededListener, Upload.FailedListener, Upload.Receiver,
        Upload.ProgressListener, Upload.StartedListener {
    // PUBLIC

    /**
     * Parameterized constructor
     * 
     * @param prefix A String to be used as a base for the image file name. Formerly an instance of EdsEds so the Id would be used as the base name.
     * @param type Type of image
     * @param controller Controller of EDS application
     */
    public UploadPicture(final String prefix, String type, EdsApplicationController controller) {
        this.prefix = prefix;
        this.type = type;
        this.imName = prefix + "_" + type;
        this.controller = controller;
        init();

    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of panel
     */
    private Panel vPmain;
    /**
     * Variable to hold value of GridLayout
     */
    private GridLayout vGLGeneralPic;
    /**
     * Variable to hold value of GridLayout
     */
    private GridLayout vGLPic;
    /**
     * Variable to hold value of image name
     */
    private String imName;
    /**
     * Variable to hold value of image temp name
     */
    private String imNameTmp;
    /**
     * Constant to hold value of String upload
     */
    public final static String UPLOAD = "Upload/";
    /**
     * Variable to hold value of Controller Of Eds application
     */
    private EdsApplicationController controller;
    /**
     * Variable to hold value of the base to derive the image file name.
     */
    private String prefix;
    /**
     * Variable to hold value of Type of image
     */
    private String type;
    /**
     * Variable to hold value of File of picture
     */
    private File picFile;
    /**
     * Variable to hold value of label
     */
    private Label vLNoPic;
    /**
     * Variable to hold value of maxSize of image
     */
    protected long maxSize = 1000000; // In bytes. 100Kb = 100000
    /**
     * Variable to hold value of ProgressIndicator
     */
    protected ProgressIndicator progressIndicator; // May be null
    /**
     * Variable to hold value of content length
     */
    protected Long contentLength;
    /**
     * Variable to hold value of Upload
     */
    protected Upload upload;
    /**
     * Variable to hold value of HorizontalLayout
     */
    protected HorizontalLayout processingLayout;
    /**
     * List to hold value of extensions of images
     */
    private ArrayList<String> extentions = new ArrayList<String>();

    /**
     * Initialize UploadPicture
     */
    private void init() {
        extentions.add("jpg");
        extentions.add("jpeg");
        extentions.add("gif");
        extentions.add("png");
        extentions.add("bmp");
        extentions.add("tif");

        vPmain = new Panel(controller.getBundle().getString("image-title"));
        setCompositionRoot(vPmain);
        vGLGeneralPic = new GridLayout(2, 2);

        vGLGeneralPic.setWidth("100%");
        vGLGeneralPic.setHeight("450px");
        vGLGeneralPic.setColumnExpandRatio(1, 1f);
        vGLGeneralPic.setColumnExpandRatio(0, 0f);
        final Button delButton = new Button(controller.getBundle().getString("image-button-suppr"), new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                vGLPic.removeAllComponents();
                vGLPic.addComponent(vLNoPic);
                vGLPic.setComponentAlignment(vLNoPic, Alignment.MIDDLE_CENTER);
                setImName("");
            }
        });

        delButton.setImmediate(true);
        vGLGeneralPic.addComponent(delButton, 1, 1);

        upload = new Upload("", this);
        upload.addListener((Upload.SucceededListener) this);
        upload.addListener((Upload.FailedListener) this);
        upload.addListener((Upload.ProgressListener) this);
        upload.addListener((Upload.StartedListener) this);

        upload.setImmediate(true);

        upload.setButtonCaption(controller.getBundle().getString("image-button-import"));
        vGLGeneralPic.addComponent(upload, 0, 1);
        vGLGeneralPic.setComponentAlignment(upload, Alignment.BOTTOM_LEFT);

        vGLGeneralPic.setComponentAlignment(delButton, Alignment.BOTTOM_LEFT);

        vGLPic = new GridLayout();
        vGLPic.setWidth("95%");
        vGLPic.setHeight("400px");
        vLNoPic = new Label(controller.getBundle().getString("image-no-image"));
        vLNoPic.addStyleName("h2");
        vGLPic.addComponent(vLNoPic);
        vLNoPic.setWidth("210px");
        vGLPic.setComponentAlignment(vLNoPic, Alignment.MIDDLE_CENTER);
        vGLPic.addStyleName("picture-layout");

        vGLGeneralPic.addComponent(vGLPic, 0, 0, 1, 0);
        vGLGeneralPic.setComponentAlignment(vGLPic, Alignment.MIDDLE_CENTER);

        vPmain.addComponent(vGLGeneralPic);

    }

    protected void setPanelCaption(String caption) {
        vPmain.setCaption(caption);
    }

    protected void setPanelStyleName(String style) {
        vPmain.setStyleName(style);
    }

    /**
     * This method called when upload is successful.
     * 
     * @param event Upload successful event
     */
    public void uploadSucceeded(SucceededEvent event) {

        File image = new File(controller.getImageFilePath(imName));
        if (image.exists() && isValidExtention(imName)) {

            FileResource sourcedelimage = new FileResource(image, getApplication());
            vGLPic.removeAllComponents();
            Embedded embedded = new Embedded("", sourcedelimage);
            embedded.addStyleName("picture");
            embedded.setType(Embedded.TYPE_IMAGE);

            vGLPic.addComponent(embedded);
            vGLPic.setComponentAlignment(embedded, Alignment.MIDDLE_CENTER);
            upload.setEnabled(true);
        }
    }

    /**
     * This method is called when upload fails.
     * 
     * @param event upload failed event
     */
    public void uploadFailed(FailedEvent event) {

        upload.interruptUpload();
        upload.setEnabled(true);
    }

    /**
     * This method is called for new upload arrives
     * 
     * @param filename the desired filename of the upload
     * @param mimeType the MIME type of the uploaded file.
     * @return Stream to which upload file written
     */
    public OutputStream receiveUpload(String filename, String mimeType) {

        if (mimeType.contains("image") && isValidExtention(filename)) {
            int i = filename.lastIndexOf(".") + 1;
            String extention = filename.substring(i);
            this.imName = prefix + "_" + type;
            setImName(imName + "." + extention);
            upload.setEnabled(true);
            return controller.getImageOutputStream(imName);
        }

        // throw new Error();
        return null;

    }

    /**
     * This method shows the update progress.
     * 
     * @param readBytes byte transfered
     * @param contentLength length of content
     */
    public void updateProgress(long readBytes, long contentLength) {
        this.contentLength = contentLength;
        if (maxSize < contentLength) {
            upload.interruptUpload();
            showNotification(controller.getBundle().getString("file-size-error-title"),
                    MessageFormat.format(controller.getBundle().getString("file-size-error-message"), contentLength / 1000000, maxSize / 1000000));
        }

    }

    /**
     * This method returns image name
     * 
     * @return image name
     */
    public String getImName() {
        return imName;
    }

    /**
     * This method set Image name
     * 
     * @param imName Image name
     */
    public void setImName(String imName) {
        this.imName = imName;
    }

    /**
     * This method add image
     * 
     * @param sourcedelimage Source of embedded image
     * @param filename File name
     */
    public void addImage(FileResource sourcedelimage, String filename) {

        vGLPic.removeAllComponents();
        Embedded embedded = new Embedded("", sourcedelimage);
        setImName(filename);
        embedded.addStyleName("picture");
        embedded.setType(Embedded.TYPE_IMAGE);
        vGLPic.addComponent(embedded);
        vGLPic.setComponentAlignment(embedded, Alignment.MIDDLE_CENTER);
        vGLPic.setComponentAlignment(embedded, Alignment.MIDDLE_CENTER);

    }

    /**
     * This method shows the notification messages
     * 
     * @param message Message
     * @param detail Details of notification
     */
    protected void showNotification(String message, String detail) {
        getWindow().showNotification(message, detail, Notification.TYPE_ERROR_MESSAGE);
    }

    /**
     * This method check if extension is valid
     * 
     * @param name Name of extension
     * @return check if extension is valid
     */
    private boolean isValidExtention(String name) {
        int i = name.lastIndexOf(".") + 1;
        String extention = name.substring(i);
        if (extentions.contains(extention.toLowerCase())) {
            return true;
        }
        upload.setEnabled(true);
        showNotification("", MessageFormat.format(controller.getBundle().getString("file-ext-error-message"), extention));

        return false;
    }

    /**
     * This method is called when upload started
     * 
     * @param event Upload started event
     */
    public void uploadStarted(Upload.StartedEvent event) {
        if (!isValidExtention(event.getFilename())) {
            upload.interruptUpload();
            upload.setEnabled(true);
        }

    }
}
