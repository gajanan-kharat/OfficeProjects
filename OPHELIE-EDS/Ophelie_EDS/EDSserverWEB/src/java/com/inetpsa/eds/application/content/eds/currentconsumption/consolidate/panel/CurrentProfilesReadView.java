/*
 * Creation : 11 juin 2015
 */
package com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.panel;

import java.util.Set;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.EdsConsSupProfile;
import com.vaadin.ui.Panel;

/**
 * Generic panel displaying a list of current profile.
 * 
 * @author G-VILLEREZ
 */
public class CurrentProfilesReadView extends Panel {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -7314000683877280559L;

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
    public CurrentProfilesReadView(EdsApplicationController controller, Set<EdsConsSupProfile> edsConsSupProfiles) {
        super(controller.getBundle().getString("current-profile-title"));

        this.controller = controller;
        this.edsConsSupProfiles = edsConsSupProfiles;

        init();
    }

    /**
     * Init the view.
     */
    private void init() {
        setWidth(100, UNITS_PERCENTAGE);

        for (EdsConsSupProfile profile : edsConsSupProfiles) {
            CurrentProfileReadView profileView = new CurrentProfileReadView(controller, profile);
            addComponent(profileView);
        }
    }

}
