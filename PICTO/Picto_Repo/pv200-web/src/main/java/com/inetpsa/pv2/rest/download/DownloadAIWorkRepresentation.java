/*
 * Creation : May 24, 2016
 */
package com.inetpsa.pv2.rest.download;

/**
 * The Class DownloadAIWorkRepresentation.
 */
public class DownloadAIWorkRepresentation {

    /** The user id. */
    private Long userId;

    /** The picto id. */
    private Long pictoId;

    /** The work admin list. */
    private String workAdminList;

    /** The work admin list not null check. */
    private boolean workAdmins;

    /** The keep local version. */
    private boolean keepLocalVersion;

    /** The download DB version. */
    private boolean downloadDBVersion;

    /** The disable local version. */
    private boolean disableLocalVersion;

    /** The open local vrsn. */
    private boolean openLocalVrsn;

    /** The picto name. */
    private String pictoName;

    /**
     * Gets the user id.
     * 
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     * 
     * @param userId the new user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the picto id.
     * 
     * @return the picto id
     */
    public Long getPictoId() {
        return pictoId;
    }

    /**
     * Sets the picto id.
     * 
     * @param pictoId the new picto id
     */
    public void setPictoId(Long pictoId) {
        this.pictoId = pictoId;
    }

    /**
     * Checks if is download DB version.
     * 
     * @return true, if is download DB version
     */
    public boolean isDownloadDBVersion() {
        return downloadDBVersion;
    }

    /**
     * Sets the download DB version.
     * 
     * @param downloadDBVersion the new download DB version
     */
    public void setDownloadDBVersion(boolean downloadDBVersion) {
        this.downloadDBVersion = downloadDBVersion;
    }

    /**
     * Getter pictoName.
     * 
     * @return the pictoName
     */
    public String getPictoName() {
        return pictoName;
    }

    /**
     * Setter pictoName.
     * 
     * @param pictoName the pictoName to set
     */
    public void setPictoName(String pictoName) {
        this.pictoName = pictoName;
    }

    /**
     * Getter disableLocalVersion.
     * 
     * @return the disableLocalVersion
     */
    public boolean isDisableLocalVersion() {
        return disableLocalVersion;
    }

    /**
     * Setter disableLocalVersion.
     * 
     * @param disableLocalVersion the disableLocalVersion to set
     */
    public void setDisableLocalVersion(boolean disableLocalVersion) {
        this.disableLocalVersion = disableLocalVersion;
    }

    /**
     * Getter keepLocalVersion.
     * 
     * @return the keepLocalVersion
     */
    public boolean isKeepLocalVersion() {
        return keepLocalVersion;
    }

    /**
     * Setter keepLocalVersion.
     * 
     * @param keepLocalVersion the keepLocalVersion to set
     */
    public void setKeepLocalVersion(boolean keepLocalVersion) {
        this.keepLocalVersion = keepLocalVersion;
    }

    /**
     * Getter workAdminList.
     * 
     * @return the workAdminList
     */
    public String getWorkAdminList() {
        return workAdminList;
    }

    /**
     * Setter workAdminList.
     * 
     * @param workAdminList the workAdminList to set
     */
    public void setWorkAdminList(String workAdminList) {
        this.workAdminList = workAdminList;
    }

    /**
     * Getter openLocalVrsn.
     * 
     * @return the openLocalVrsn
     */
    public boolean isOpenLocalVrsn() {
        return openLocalVrsn;
    }

    /**
     * Setter openLocalVrsn.
     * 
     * @param openLocalVrsn the openLocalVrsn to set
     */
    public void setOpenLocalVrsn(boolean openLocalVrsn) {
        this.openLocalVrsn = openLocalVrsn;
    }

    /**
     * Getter workAdmins
     * 
     * @return the workAdmins
     */
    public boolean isWorkAdmins() {
        return workAdmins;
    }

    /**
     * Setter workAdmins
     * 
     * @param workAdmins the workAdmins to set
     */
    public void setWorkAdmins(boolean workAdmins) {
        this.workAdmins = workAdmins;
    }
}
