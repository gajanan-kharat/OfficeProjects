package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import java.io.File;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.composants.Comment;
import com.inetpsa.eds.dao.model.EdsConsSupProfile;
import com.inetpsa.eds.tools.component.ImagePanel;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 * This class defines a Current Profile panel for viewing.
 * 
 * @author jdsantos @ Alter Frame
 */
public class CurrentProfileReadView extends Panel {

    private static final long serialVersionUID = -3403196532872335480L;

    /** Variable to hold value of Controller of EDS application. */
    private EdsApplicationController controller;

    /** Variable to hold value of the profile being edited. */
    private EdsConsSupProfile profile;

    /** Label for the name value. */
    Label labelName;

    /** Label for the type value. */
    Label labelType;

    /** The name value. */
    Label valueName;

    /** The type value. */
    Label valueType;

    /** The panel to hold the comments. */
    Panel commentsPanel;

    /** The comments. */
    Comment comments;

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public CurrentProfileReadView(final EdsApplicationController controller, final EdsConsSupProfile profile) {

        this.profile = profile;
        this.controller = controller;

        labelName = new Label(controller.getBundle().getString("current-profile-label-name"));
        labelType = new Label(controller.getBundle().getString("current-profile-label-type"));
        valueName = new Label();
        valueType = new Label();

        commentsPanel = new Panel(controller.getBundle().getString("current-profile-label-comments"));
        comments = new Comment(controller);

        init();
    }

    /**
     * Initialize the panel components with data.
     */
    private void init() {

        setWidth(100, UNITS_PERCENTAGE);

        valueName.setValue(profile.getCspName());
        valueType.setValue(profileTypeRepresentation(profile.getCspType()));

        comments.setValue(profile.getCspComment());

        labelName.setWidth(200, UNITS_PIXELS);
        labelType.setWidth(200, UNITS_PIXELS);
        valueName.setWidth(400, UNITS_PIXELS);
        valueType.setWidth(400, UNITS_PIXELS);
        commentsPanel.setWidth(100, UNITS_PERCENTAGE);
        comments.setWidth(100, UNITS_PERCENTAGE);
        commentsPanel.addComponent(comments);

        HorizontalLayout row1 = new HorizontalLayout();
        HorizontalLayout row2 = new HorizontalLayout();
        row1.setMargin(false, false, true, false);
        row2.setMargin(false, false, true, false);
        row1.addComponent(labelName);
        row1.addComponent(valueName);
        row2.addComponent(labelType);
        row2.addComponent(valueType);

        addComponent(row1);
        addComponent(row2);
        addComponent(commentsPanel);

        initSubView();
    }

    /**
     * Initialize the sub panel components with data.
     */
    private void initSubView() {
        if ("TABLE_OF_POINTS".equals(profile.getCspType())) {
            addComponent(new CurrentProfileTableOfPointsReadView(controller, profile));
        } else if ("IMAGE".equals(profile.getCspType())) {
            ImagePanel image = new ImagePanel(controller.getBundle().getString("image-title"), controller);
            File ressourceDeLimage = new File(controller.getImageFilePath(profile.getCspImage()));
            if (ressourceDeLimage.exists()) {
                image.addImage(new FileResource(ressourceDeLimage, controller.getApplication()));
            }

            addComponent(image);
        }
    }

    /**
     * Converts the type read from the database in the translatable string and get's the appropriate value from the property file.
     * 
     * @param profileType The type read from the database.
     * @return A String with a readable representation of the profile type.
     */
    private String profileTypeRepresentation(final String profileType) {
        if ("IMAGE".equals(profileType)) {
            return controller.getBundle().getString("current-profile-type-image");
        } else if ("TABLE_OF_POINTS".equals(profileType)) {
            return controller.getBundle().getString("current-profile-type-table_of_points");
        }

        return "";
    }
}
