package com.inetpsa.eds.dao.model;

import com.google.gson.Gson;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections.set.ListOrderedSet;
import org.hibernate.Hibernate;

/**
 * Class to ease the reading of the blob user configuration.
 * 
 * @author Geometric Ltd.
 */
public class UserConfig implements Serializable {
    // PUBLIC
    /**
     * Default Constructor
     */
    public UserConfig() {
        init();
    }

    /**
     * Function to get lastViewedEdses
     * 
     * @return the Value of lastViewedEdses
     */
    public ListOrderedSet getLastViewedEdses() {
        return lastViewedEdses;
    }

    /**
     * Function to set lastViewedEdses
     * 
     * @param lastViewedEdses
     */
    public void setLastViewedEdses(ListOrderedSet lastViewedEdses) {
        this.lastViewedEdses = lastViewedEdses;
    }

    /**
     * Function to get lastViewedProjects
     * 
     * @return the Value of lastViewedProjects
     */
    public ListOrderedSet getLastViewedProjects() {
        return lastViewedProjects;
    }

    /**
     * Function to set lastViewedProjects
     * 
     * @param lastViewedProjects
     */
    public void setLastViewedProjects(ListOrderedSet lastViewedProjects) {
        this.lastViewedProjects = lastViewedProjects;
    }

    /**
     * Function to get lastSubscribedEdses
     * 
     * @return the Value of lastSubscribedEdses
     */
    public ListOrderedSet getLastSubscribedEdses() {
        return lastSubscribedEdses;
    }

    /**
     * Function to set lastSubscribedEdses
     * 
     * @param lastSubscribedEdses
     */
    public void setLastSubscribedEdses(ListOrderedSet lastSubscribedEdses) {
        this.lastSubscribedEdses = lastSubscribedEdses;
    }

    /**
     * Function to get lastViewedEdsesLimit
     * 
     * @return the Value of lastViewedEdsesLimit
     */
    public int getLastViewedEdsesLimit() {
        return lastViewedEdsesLimit;
    }

    /**
     * Function to set lastViewedEdsesLimit
     * 
     * @param lastViewedEdsesLimit
     */
    public void setLastViewedEdsesLimit(int lastViewedEdsesLimit) {
        this.lastViewedEdsesLimit = lastViewedEdsesLimit;
    }

    /**
     * Function to get lastViewedProjectsLimit
     * 
     * @return the Value of lastViewedProjectsLimit
     */
    public int getLastViewedProjectsLimit() {
        return lastViewedProjectsLimit;
    }

    /**
     * Function to set lastViewedProjectsLimit
     * 
     * @param lastViewedProjectsLimit
     */
    public void setLastViewedProjectsLimit(int lastViewedProjectsLimit) {
        this.lastViewedProjectsLimit = lastViewedProjectsLimit;
    }

    /**
     * Function to get lastSubscribedEdsesLimit
     * 
     * @return the Value of lastSubscribedEdsesLimit
     */
    public int getLastSubscribedEdsesLimit() {
        return lastSubscribedEdsesLimit;
    }

    /**
     * Function to set lastSubscribedEdsesLimit
     * 
     * @param lastSubscribedEdsesLimit
     */
    public void setLastSubscribedEdsesLimit(int lastSubscribedEdsesLimit) {
        this.lastSubscribedEdsesLimit = lastSubscribedEdsesLimit;
    }

    /**
     * Function to get mailAddress
     * 
     * @return the Value of mailAddress
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * Function to set mailAddress
     * 
     * @param mailAddress
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * Function to get notifyModification
     * 
     * @return the Value of notifyModification
     */
    public boolean isNotifyModification() {
        return notifyModification;
    }

    /**
     * Function to set notifyModification
     * 
     * @param notifyModification
     */
    public void setNotifyModification(boolean notifyModification) {
        this.notifyModification = notifyModification;
    }

    /**
     * Function to get notifyStatus
     * 
     * @return the Value of notifyStatus
     */
    public boolean isNotifyStatus() {
        return notifyStatus;
    }

    /**
     * Function to set notifyStatus
     * 
     * @param notifyStatus
     */
    public void setNotifyStatus(boolean notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    /**
     * Function to get notifyVersionning
     * 
     * @return the Value of notifyVersionning
     */
    public boolean isNotifyVersionning() {
        return notifyVersionning;
    }

    /**
     * Function to set notifyVersionning
     * 
     * @param notifyVersionning
     */
    public void setNotifyVersionning(boolean notifyVersionning) {
        this.notifyVersionning = notifyVersionning;
    }

    /**
     * Function to get preferredLanguage
     * 
     * @return the Value of preferredLanguage
     */
    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * Function to set preferredLanguage
     * 
     * @param preferredLanguage
     */
    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    /**
     * Function to set lastViewedProjects
     * 
     * @param projectID
     */
    public void addViewedProject(String projectID) {
        if (!lastViewedProjects.contains(projectID)) {
            if (lastViewedProjects.size() == lastViewedProjectsLimit) {
                lastViewedProjects.remove(0);
            }
            lastViewedProjects.add(projectID);
        }
    }

    /**
     * Function to set lastViewedProjects
     * 
     * @param edsID
     */
    public void addViewedEds(String edsID) {
        if (!lastViewedEdses.contains(edsID)) {
            if (lastViewedEdses.size() == lastViewedEdsesLimit) {
                lastViewedEdses.remove(0);
            }
            lastViewedEdses.add(edsID);
        }
    }

    /**
     * Function to set lastViewedProjects
     * 
     * @param edsRef
     */
    public void addSubscribedEds(String edsRef) {
        if (!lastSubscribedEdses.contains(edsRef)) {
            if (lastSubscribedEdses.size() == lastSubscribedEdsesLimit) {
                lastSubscribedEdses.remove(0);
            }
            lastSubscribedEdses.add(edsRef);
        }
    }

    /**
     * Function to get userConfig
     * 
     * @param clob Clob
     * @return the Value of userConfig
     */
    public static UserConfig fromClob(Clob clob) {
        UserConfig userConfig = new UserConfig();
        try {
            userConfig = JSON_HANDLER.fromJson(clob.getCharacterStream(), UserConfig.class);
        } catch (SQLException ex) {
            Logger.getLogger(UserConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userConfig;
    }

    /**
     * Function to get createClob
     * 
     * @param userConfig User configuration
     * @return the Value of createClob
     */
    public static Clob toClob(UserConfig userConfig) {
        return Hibernate.createClob(JSON_HANDLER.toJson(userConfig));
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold value of JSON_HANDLER
     */
    private static final Gson JSON_HANDLER = new Gson();
    /**
     * Integer Variable to hold value for last viewed projects limit
     */
    private int lastViewedProjectsLimit;
    /**
     * Integer Variable to hold value for last viewed Edses limit
     */
    private int lastViewedEdsesLimit;
    /**
     * Integer Variable to hold value for last viewed subscribed Edses limit
     */
    private int lastSubscribedEdsesLimit;
    /**
     * Variable to hold value for mail address
     */
    private String mailAddress;
    /**
     * Variable to hold value for Preferred language
     */
    private String preferredLanguage;
    /**
     * Boolean Variable to hold value for notifying modification
     */
    private boolean notifyModification;
    /**
     * Boolean Variable to hold value for notifying status
     */
    private boolean notifyStatus;
    /**
     * Boolean Variable to hold value for notifying versioning
     */
    private boolean notifyVersionning;
    /**
     * Variable to hold value of ListOrderedSet for last viewed projects
     */
    private ListOrderedSet lastViewedProjects;
    /**
     * Variable to hold value of ListOrderedSet for last viewed edses
     */
    private ListOrderedSet lastViewedEdses;
    /**
     * Variable to hold value of ListOrderedSet for last viewed subscribed edses
     */
    private ListOrderedSet lastSubscribedEdses;

    /**
     * Initialize reading of the blob user configuration.
     */
    private void init() {
        this.lastViewedProjectsLimit = 5;
        this.lastViewedEdsesLimit = 5;
        this.lastSubscribedEdsesLimit = 5;
        this.lastViewedProjects = new ListOrderedSet();
        this.lastViewedEdses = new ListOrderedSet();
        this.lastSubscribedEdses = new ListOrderedSet();
        this.notifyModification = false;
        this.notifyStatus = false;
        this.notifyVersionning = false;
    }
}
