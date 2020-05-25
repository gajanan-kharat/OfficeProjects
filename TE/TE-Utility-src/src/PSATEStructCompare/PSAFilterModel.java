/*
 * Creation : May 31, 2017
 */
package com.psa.PSATEStructCompare;

public class PSAFilterModel {
    private String id;
    private String name;
    private String revision;
    private String project;
    private String designation;
    private String type;
    private String mode;
    private String modeParam;
    private String user;
    private String startDate;
    private String nextLaunchDate;
    private String creationDate;
    private String modifyDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getModeParam() {
        return modeParam;
    }

    public void setModeParam(String modeParam) {
        this.modeParam = modeParam;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getNextLaunchDate() {
        return nextLaunchDate;
    }

    public void setNextLaunchDate(String nextLaunchDate) {
        this.nextLaunchDate = nextLaunchDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String string) {
        this.creationDate = string;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String string) {
        this.modifyDate = string;
    }

    /**
     * It copies value from parameter object to invoking object.
     * 
     * @param iPsaFilter : PSAFilter
     */
    public void copy(PSAFilterModel psaFilter) {
        id = psaFilter.getId();
        name = psaFilter.getName();
        revision = psaFilter.getRevision();
        project = psaFilter.getProject();
        designation = psaFilter.getDesignation();
        type = psaFilter.getType();
        mode = psaFilter.getMode();
        creationDate = psaFilter.getCreationDate();
        modeParam = psaFilter.getModeParam();
        startDate = psaFilter.getStartDate();
        modifyDate = psaFilter.getModifyDate();
        nextLaunchDate = psaFilter.getNextLaunchDate();
        user = psaFilter.getUser();
    }

}
