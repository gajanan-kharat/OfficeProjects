package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsConsSupProfile;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Property;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class defines a Current Profile panel for editing.
 * 
 * @author jdsantos @ Alter Frame
 */
public abstract class CurrentProfileEditView extends HorizontalLayout implements ClickListener {

    private static final long serialVersionUID = 5053781617093670213L;

    /** The single instance for ProfileType: Image. */
    private static ProfileType TYPE_IMAGE;

    /** The single instance for ProfileType: Table of points. */
    private static ProfileType TYPE_TABLE_OF_POINTS;

    /** Possible options for current profile types. */
    private List<ProfileType> typeOptions = Arrays.asList(getType(ProfileType.IMAGE), getType(ProfileType.TABLE_OF_POINTS));

    /** Variable to hold value of the profile being edited. */
    private EdsConsSupProfile profile;

    /** Variable to hold value of Controller of EDS application. */
    private EdsApplicationController controller;

    /** Label for the name field. */
    private Label profileNameLabel;

    /** Label for the type combo box. */
    private Label profileTypeLabel;

    /** The name text field. */
    private TextField profileNameValue;

    /** The type combo box. */
    private ComboBox profileTypeValue;

    /** The comment box. */
    private TextArea comments;

    /** The Remove button **/
    private Button removeButton;

    /** The table of point view **/
    private CurrentProfileTableOfPointsEditView tableOfPointView;

    /** The varibale row used to keep an image or a table of points, according to the type selection. **/
    private HorizontalLayout variableRow;

    /** The image componenet. **/
    private CurrentProfileImageEditView imageComponent;

    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public CurrentProfileEditView(final EdsApplicationController controller, final EdsConsSupProfile profile) {

        this.controller = controller;

        if (profile != null) {
            this.profile = profile;
        } else {

            this.profile = new EdsConsSupProfile(UUID.randomUUID().toString());
        }

        profileNameLabel = new Label(controller.getBundle().getString("current-profile-label-name"));
        profileTypeLabel = new Label(controller.getBundle().getString("current-profile-label-type"));

        profileNameValue = new TextField();
        profileTypeValue = new ComboBox(null, typeOptions);

        comments = new TextArea(controller.getBundle().getString("current-profile-label-comments"));
        comments.setNullSettingAllowed(true);
        comments.setNullRepresentation("");

        init();
    }

    /**
     * Initialize the panel components with data, or empty if no profile exists.
     */
    private void init() {

        setWidth(100, UNITS_PERCENTAGE);

        Panel profilePanel = new Panel();

        profileNameValue.setValue(profile.getCspName());
        profileTypeValue.setValue(getType(profile.getCspType()));
        comments.setValue(profile.getCspComment());

        profileNameLabel.setWidth(200, UNITS_PIXELS);
        profileTypeLabel.setWidth(200, UNITS_PIXELS);
        profileNameValue.setWidth(400, UNITS_PIXELS);
        profileTypeValue.setWidth(400, UNITS_PIXELS);
        profileNameValue.setRequired(true);
        profileTypeValue.setRequired(true);
        comments.setWidth(100, UNITS_PERCENTAGE);

        profileTypeValue.setImmediate(true); // so the listener is triggered immediately.
        profileTypeValue.addListener(changeListener);

        HorizontalLayout row1 = new HorizontalLayout();
        HorizontalLayout row2 = new HorizontalLayout();
        row1.setMargin(false, false, true, false);
        row2.setMargin(false, false, true, false);
        row1.addComponent(profileNameLabel);
        row1.addComponent(profileNameValue);
        row2.addComponent(profileTypeLabel);
        row2.addComponent(profileTypeValue);

        variableRow = new HorizontalLayout();
        variableRow.setWidth(100, UNITS_PERCENTAGE);
        variableRow.setMargin(true, false, false, false);

        profilePanel.addComponent(row1);
        profilePanel.addComponent(row2);
        profilePanel.addComponent(comments);
        profilePanel.addComponent(variableRow);

        profilePanel.setWidth(100, UNITS_PERCENTAGE);

        removeButton = new Button();
        removeButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/erase.png"));
        removeButton.setStyleName(BaseTheme.BUTTON_LINK);
        removeButton.setWidth(16, UNITS_PIXELS);
        removeButton.setDescription(controller.getBundle().getString("btn-delete-tab"));
        removeButton.addListener(this);

        addComponent(profilePanel);
        addComponent(removeButton);

        setExpandRatio(profilePanel, 1);
        setExpandRatio(removeButton, 0);
        setComponentAlignment(removeButton, Alignment.MIDDLE_LEFT);

        updateSubView();
    }

    /**
     * This method saves the changes to Databases
     */
    public boolean commitChanges() {

        if (isValid()) {
            if (profile == null)
                profile = new EdsConsSupProfile(UUID.randomUUID().toString());

            profile.setCspName((String) profileNameValue.getValue());
            profile.setCspType(((ProfileType) profileTypeValue.getValue()).toPersistableString());
            profile.setCspComment((String) comments.getValue());

            if (TYPE_IMAGE.equals(profileTypeValue.getValue()) && imageComponent != null) {
                // We no longer want to keep information regarding the Table of Points
                // TODO

                // Keep the image filename
                profile.setCspImage(imageComponent.getImName());

            } else if (TYPE_TABLE_OF_POINTS.equals(profileTypeValue.getValue()) && tableOfPointView != null) {
                // We no longer want to keep the image filename
                profile.setCspImage("");

                // collect table of points data to be persisted.
                tableOfPointView.commitChanges();
            }

            return true;
        }

        return false;
    }

    /**
     * This method checks if the mandatory fields have data.
     * 
     * @return boolean true if all mandatory fields have data, false otherwise.
     */
    boolean isValid() {
        if (profileNameValue.getValue() == "") {
            getWindow().showNotification(controller.getBundle().getString("current-profile-modif"),
                    controller.getBundle().getString("profile-name-error"), Notification.TYPE_ERROR_MESSAGE);
            return false;
        }
        if (profileTypeValue.getValue() == null) {
            getWindow().showNotification(controller.getBundle().getString("current-profile-modif"),
                    controller.getBundle().getString("profile-type-error"), Notification.TYPE_ERROR_MESSAGE);
            return false;
        }

        if (TYPE_TABLE_OF_POINTS.equals(profileTypeValue.getValue()) && !tableOfPointView.isValid()) {
            getWindow().showNotification(controller.getBundle().getString("current-profile-modif"),
                    controller.getBundle().getString("profile-table-error"), Notification.TYPE_ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Get this panel profile instance.
     * 
     * @return the profile
     */
    public EdsConsSupProfile getProfile() {
        return profile;
    }

    /**
     * Gets the single instance of ProfileType for the provided type.
     */
    private ProfileType getType(final String type) {
        if (ProfileType.IMAGE.equals(type)) {
            if (TYPE_IMAGE == null) {
                TYPE_IMAGE = new ProfileType(type);
            }
            return TYPE_IMAGE;
        } else if (ProfileType.TABLE_OF_POINTS.equals(type)) {
            if (TYPE_TABLE_OF_POINTS == null) {
                TYPE_TABLE_OF_POINTS = new ProfileType(type);
            }
            return TYPE_TABLE_OF_POINTS;
        }

        return null;
    }

    /**
     * Inner class to define the possible profile types: - Property string that allows translated text representation in the UI - Text representation
     * for database persistence
     * 
     * @author jdsantos @ ALter Frame
     */
    private class ProfileType {

        static final String IMAGE = "IMAGE";
        static final String TABLE_OF_POINTS = "TABLE_OF_POINTS";

        private String translatable;
        private String persistable;

        public ProfileType(final String type) {
            this.persistable = type;

            if (IMAGE.equals(type)) {
                this.translatable = "current-profile-type-image";
            } else if (TABLE_OF_POINTS.equals(type)) {
                this.translatable = "current-profile-type-table_of_points";
            }
        }

        public String toString() {
            return controller.getBundle().getString(translatable);
        }

        String toPersistableString() {
            return persistable;
        }
    }

    /** Value change listener for the type combo box. */
    Property.ValueChangeListener changeListener = new Property.ValueChangeListener() {
        private static final long serialVersionUID = 6698177528043478626L;

        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            updateSubView();
        }
    };

    private void updateSubView() {
        variableRow.removeAllComponents();
        if (TYPE_IMAGE.equals(profileTypeValue.getValue())) {
            tableOfPointView = null;
            imageComponent = new CurrentProfileImageEditView(profile, controller);
            variableRow.addComponent(imageComponent);
        } else if (TYPE_TABLE_OF_POINTS.equals(profileTypeValue.getValue())) {
            tableOfPointView = new CurrentProfileTableOfPointsEditView(controller, profile);
            // variableRow.addComponent(new Label("&nbsp;", Label.CONTENT_XHTML));
            variableRow.addComponent(tableOfPointView);

            tableOfPointView.setWidth(100, UNITS_PERCENTAGE);
            tableOfPointView.setStyleName("margin-top");
        } else {
            tableOfPointView = null;
        }
    }
}
