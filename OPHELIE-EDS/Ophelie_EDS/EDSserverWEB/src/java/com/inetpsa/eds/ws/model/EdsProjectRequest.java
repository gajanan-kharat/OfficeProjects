package com.inetpsa.eds.ws.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class bind EdsProjectRequest bean to XML
 * 
 * @author Geometric Ltd.
 */
@XmlRootElement
public class EdsProjectRequest {

    // PUBLIC
    /**
     * Default Constructor
     */
    public EdsProjectRequest() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param tokenID Token ID
     * @param projectName Name of project
     */
    public EdsProjectRequest(String tokenID, String projectName) {
        this.tokenID = tokenID;
        this.projectName = projectName;
    }

    /**
     * Function to get projectName
     * 
     * @return the Value of projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Function to get tokenID
     * 
     * @return the Value of tokenID
     */
    public String getTokenID() {
        return tokenID;
    }

    /**
     * Function to set projectName
     * 
     * @param projectName Name of Project
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Function to set tokenID
     * 
     * @param tokenID Token ID
     */
    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    // PROTECTED
    // PRIVATE
    /**
     * String variable to hold value of tokenID
     */
    private String tokenID;
    /**
     * String variable to hold value of Name of project
     */
    private String projectName;

    /**
     * Initialize EDS project request
     */
    private void init() {
    }
}
