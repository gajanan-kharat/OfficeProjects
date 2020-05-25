package com.inetpsa.eds.ws.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * This class binds EdsProjectResponse bean to XML
 * 
 * @author Geometric Ltd.
 */
@XmlRootElement(name = "project")
@XmlSeeAlso({ EdsResponse.class })
@XmlType(propOrder = { "projectName", "edses" })
public class EdsProjectResponse {
    // PUBLIC
    /**
     * Default Constructor
     */
    public EdsProjectResponse() {
        init();
    }

    /**
     * Function to get projectName
     * 
     * @return the Value of projectName
     */
    @XmlElement(name = "project-name")
    public String getProjectName() {
        return projectName;
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
     * Function to get edses
     * 
     * @return the Value of edses
     */
    @XmlElementWrapper
    @XmlAnyElement
    public List<EdsResponse> getEdses() {
        return edses;
    }

    /**
     * Function to set edses
     * 
     * @param edses List of Eds Response
     */
    public void setEdses(List<EdsResponse> edses) {
        this.edses = edses;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Method to initialize arraylist of EdsResponse
     */
    private void init() {
        this.edses = new ArrayList<EdsResponse>();
    }

    /**
     * String variable to hold value of name of project
     */
    private String projectName;
    /**
     * List for storing EdsResponse
     */
    private List<EdsResponse> edses;
}
