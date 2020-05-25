package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import java.io.File;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsConsSupProfile;
import com.inetpsa.eds.tools.upload.UploadPicture;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.themes.Runo;

/**
 * This class defines a Current Profile Image panel for editing.
 * 
 * @author jdsantos @ Alter Frame
 */
public class CurrentProfileImageEditView extends UploadPicture {

    private static final long serialVersionUID = 5053781617093670213L;

    /** Variable to hold value of Controller of EDS application. */
    private EdsApplicationController controller;

    /** The profile to which this image refers. */
    private EdsConsSupProfile profile;

    /** The filename sufix. */
    private static final String SUFFIX = "profile-image";

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public CurrentProfileImageEditView(final EdsConsSupProfile profile, final EdsApplicationController controller) {
        super(profile.getCspId(), SUFFIX, controller);

        this.controller = controller;
        this.profile = profile;

        init();
    }

    /**
     * Initialize the panel components with data, or empty if no profile exists.
     */
    private void init() {

        setWidth(100, UNITS_PERCENTAGE);

        setPanelStyleName(Runo.PANEL_LIGHT);
        setPanelCaption(null);

        File imageResource = new File(controller.getImageFilePath(profile.getCspImage()));

        if (imageResource.exists() && imageResource.isFile()) {
            addImage(new FileResource(imageResource, controller.getApplication()), profile.getCspImage());
        }
    }

}
