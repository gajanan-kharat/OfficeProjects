/*
 * Creation : 11 juin 2015
 */
package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsConsSupProfile;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

/**
 * Generic panel displaying a list of current profile.
 * 
 * @author G-VILLEREZ
 */
public class CurrentProfilesEditView extends Panel {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = 4596363183511279395L;

    /**
     * Variable to hold a list of CurrentProfileEditView instances to save
     */
    private ArrayList<CurrentProfileEditView> profileViews;

    /**
     * Variable to hold a list of CurrentProfileEditView instances to delete
     */
    private ArrayList<CurrentProfileEditView> profilesToDelete;

    /**
     * The vertical layout that will hold the several profiles.
     */
    private final VerticalLayout vLProfiles = new VerticalLayout();

    /**
     * EDS controller.
     */
    private EdsApplicationController controller;

    /**
     * Set of current profiles.
     */
    private Set<EdsConsSupProfile> edsConsSupProfiles;

    /**
     * Public constructor.
     * 
     * @param controller The EDS controller.
     * @param edsConsSupProfiles The set of current profile to edit in the view.
     */
    public CurrentProfilesEditView(EdsApplicationController controller, Set<EdsConsSupProfile> edsConsSupProfiles) {
        super(controller.getBundle().getString("current-profile-title"));

        this.controller = controller;
        this.edsConsSupProfiles = edsConsSupProfiles;

        init();
    }

    /**
     * Initialize Current Profiles Edit view
     */
    private void init() {
        profileViews = new ArrayList<CurrentProfileEditView>();
        profilesToDelete = new ArrayList<CurrentProfileEditView>();

        setWidth(100, UNITS_PERCENTAGE);

        for (EdsConsSupProfile profile : edsConsSupProfiles) {

            CurrentProfileEditView profileView = createProfileComponent(profile);

            vLProfiles.addComponent(profileView);
            profileViews.add(profileView);
        }

        // Add button
        Button vBAdd = new Button();
        vBAdd.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png "));
        vBAdd.setStyleName(BaseTheme.BUTTON_LINK);
        vBAdd.setDescription(controller.getBundle().getString("eds-add-tab"));
        vBAdd.addListener(new Button.ClickListener() {

            private static final long serialVersionUID = -8716875634906266333L;

            public void buttonClick(ClickEvent event) {

                CurrentProfileEditView profileView = createProfileComponent(new EdsConsSupProfile(UUID.randomUUID().toString()));
                vLProfiles.addComponent(profileView);
                profileViews.add(profileView);
            }
        });
        addComponent(vLProfiles);
        addComponent(vBAdd);
    }

    /**
     * Creates a new CurrentProfileEditView, handling the unimplemented button event.
     * 
     * @param profile The EdsConsSupProfile instance for the new CurrentProfileEditView
     * @return a new a new CurrentProfileEditView instance.
     */
    public CurrentProfileEditView createProfileComponent(final EdsConsSupProfile profile) {

        CurrentProfileEditView profileView = new CurrentProfileEditView(controller, profile) {

            private static final long serialVersionUID = 5238597871680466985L;

            @Override
            public void buttonClick(ClickEvent event) {
                removeProfileComponent(this, vLProfiles);
            }

        };

        return profileView;
    }

    /**
     * Action when remove button is pressed. We remove the component (so it is no longer visible), we remove it from the list where we get the
     * profiles to save, and add it to the delete list.
     */
    public void removeProfileComponent(CurrentProfileEditView toRemove, VerticalLayout holder) {
        holder.removeComponent(toRemove);
        profileViews.remove(toRemove);
        profilesToDelete.add(toRemove);
    }

    /**
     * This method save the data into respective database tables
     */
    public void commit() {
        if (isValid()) {
            for (CurrentProfileEditView profileView : profileViews) {
                profileView.commitChanges();
                edsConsSupProfiles.add(profileView.getProfile());
            }

            for (CurrentProfileEditView toDelete : profilesToDelete) {
                // This will delete the profile from the database
                edsConsSupProfiles.remove(toDelete.getProfile());
            }
        }
    }

    /**
     * This method check if panels added are valid
     * 
     * @return check if panels added are valid
     */
    public boolean isValid() {
        for (CurrentProfileEditView view : profileViews) {
            if (!view.isValid()) {
                return false;
            }
        }

        return true;
    }

    /**
     * This method roll back changes to original data
     */
    public void discard() {
        // TODO
    }
}
