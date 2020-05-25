package com.inetpsa.eds.dao.model;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * This class is used for Export Admin
 * 
 * @author Geometric Ltd
 */
public class EdsEdsAdmin extends EdsEds implements java.io.Serializable {

    public EdsEdsAdmin() {
        super();
    }

    /**
     * Sring Variable to hold the value of EDS URL
     */
    private String url;

    /**
     * Function returns set of EdsNumber96FCT
     * 
     * @return set of EdsNumber96FCT
     */
    @XmlElement(name = "number96kdef")
    @XmlElementWrapper(name = "numbers96kdef")
    public Set<EdsSAPReference> getEdsNumber96fcts() {
        return super.getEdsNumber96fcts();
    }

    /**
     * Function returns set of EdsNumber96FNR
     * 
     * @return set of EdsNumber96FNR
     */
    @XmlElement(name = "number96kreal")
    @XmlElementWrapper(name = "numbers96kreal")
    public Set<EdsSAPReference> getEdsNumber96fnrs() {
        return super.getEdsNumber96fnrs();
    }

    /**
     * Function return the value of URL
     * 
     * @return the url
     */
    @XmlElement(name = "URL")
    public String getUrl() {
        return url;
    }

    /**
     * Function to define value of URL
     * 
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
