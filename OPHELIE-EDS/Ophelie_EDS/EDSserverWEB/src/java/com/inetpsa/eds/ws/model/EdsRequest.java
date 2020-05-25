package com.inetpsa.eds.ws.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class bind EdsRequest bean to XML
 * 
 * @author Geometric Ltd.
 */
@XmlRootElement
public class EdsRequest {
    // PUBLIC
    /**
     * Default Constructor
     */
    public EdsRequest() {
    }

    /**
     * Parameterized Constructor
     * 
     * @param tokenID Token Id
     * @param edsRef Reference to EDS
     */
    public EdsRequest(String tokenID, String edsRef) {
        this.tokenID = tokenID;
        this.edsRef = edsRef;
    }

    /**
     * Function to get edsRef
     * 
     * @return the Value of edsRef
     */
    public String getEdsRef() {
        return edsRef;
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
     * Function to set edsRef
     * 
     * @param edsRef Reference to EDS
     */
    public void setEdsRef(String edsRef) {
        this.edsRef = edsRef;
    }

    /**
     * Function to set tokenID
     * 
     * @param tokenID token ID
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
     * String variable to hold value of Eds reference
     */
    private String edsRef;

    /**
     * Initialize EDS request
     */
    private void init() {
    }
}
