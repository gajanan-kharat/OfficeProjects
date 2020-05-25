package com.inetpsa.eds.dao.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * EdsConsSupProfile refers to table OPLQTCSP
 * 
 * @author jdsantos @ Alter Frame
 */
public class EdsConsSupProfile implements java.io.Serializable {

    private static final long serialVersionUID = 5720016327360710610L;

    /**
     * String Variable to hold the value for Column CSP_ID of Table OPLQTCSP
     */
    private String cspId;

    /**
     * String Variable to hold the value for Column CSP_NAME of Table OPLQTCSP
     */
    private String cspName;

    /**
     * String Variable to hold the value for Column CSP_TYPE of Table OPLQTCSP
     */
    private String cspType;

    /**
     * String Variable to hold the value for Column CSP_COMMENT of Table OPLQTCSP
     */
    private String cspComment;

    /**
     * List of points
     */
    private Set<EdsConsSupProfileListOfPoints> cspListOfPoints = new HashSet<EdsConsSupProfileListOfPoints>(0);

    /*
     * String Variable to hold the value for Column CSP_IMAGE of Table OPLQTCSP
     */
    private String cspImage;

    /**
     * Default constructor.
     */
    public EdsConsSupProfile() {
    }

    public EdsConsSupProfile(final String id) {
        this.cspId = id;
        this.cspName = "";
        this.cspComment = "";
        this.cspListOfPoints = new HashSet<EdsConsSupProfileListOfPoints>(0);
        this.cspImage = "";
    }

    /**
     * Parameterized constructor.
     */
    public EdsConsSupProfile(final String cspId, final String cspName, final String cspType, final Set<EdsConsSupProfileListOfPoints> cspListOfPoints) {
        this.cspId = cspId;
        this.cspName = cspName;
        this.cspType = cspType;
        this.cspComment = "";
        this.cspListOfPoints = cspListOfPoints;
        this.cspImage = "";
    }

    /**
     * Copy constructor.
     */
    public EdsConsSupProfile(final EdsConsSupProfile source) {
        cspId = UUID.randomUUID().toString();
        cspName = source.cspName;
        cspType = source.cspType;
        cspComment = source.cspComment;
        if (source.cspListOfPoints != null)
            cspListOfPoints = new HashSet<EdsConsSupProfileListOfPoints>(source.cspListOfPoints);
        cspImage = source.cspImage;
    }

    /**
     * Getter cspId
     * 
     * @return the cspId
     */
    @XmlElement(name = "id")
    public String getCspId() {
        return cspId;
    }

    /**
     * Getter cspName
     * 
     * @return the cspName
     */
    @XmlElement(name = "name")
    public String getCspName() {
        return cspName;
    }

    /**
     * Getter cspType
     * 
     * @return the cspType
     */
    @XmlElement(name = "type")
    public String getCspType() {
        return cspType;
    }

    /**
     * Getter cspComment
     * 
     * @return the cspComment
     */
    @XmlElement(name = "comment")
    public String getCspComment() {
        return cspComment;
    }

    /**
     * Getter cspImage
     * 
     * @return the cspImage
     */
    @XmlElement(name = "image")
    public String getCspImage() {
        return cspImage;
    }

    /**
     * Setter cspId
     * 
     * @param cspId the cspId to set
     */
    public void setCspId(String cspId) {
        this.cspId = cspId;
    }

    /**
     * Setter cspName
     * 
     * @param cspName the cspName to set
     */
    public void setCspName(String cspName) {
        this.cspName = cspName;
    }

    /**
     * Setter cspType
     * 
     * @param cspType the cspType to set
     */
    public void setCspType(String cspType) {
        this.cspType = cspType;
    }

    /**
     * Setter cspComment
     * 
     * @param cspComment the cspComment to set
     */
    public void setCspComment(String cspComment) {
        this.cspComment = cspComment;
    }

    /**
     * Getter cspListOfPoints
     * 
     * @return the cspListOfPoints
     */
    @XmlTransient
    public Set<EdsConsSupProfileListOfPoints> getCspListOfPoints() {
        return cspListOfPoints;
    }

    /**
     * Setter cspListOfPoints
     * 
     * @param cspListOfPoints the cspListOfPoints to set
     */
    public void setCspListOfPoints(Set<EdsConsSupProfileListOfPoints> cspListOfPoints) {
        this.cspListOfPoints = cspListOfPoints;
    }

    @XmlElement(name = "list-of-points")
    public EdsConsSupProfileListOfPoints getCspListOfPoint() {
        if (cspListOfPoints != null && cspListOfPoints.size() > 0)
            return cspListOfPoints.iterator().next();
        return null;
    }

    public void setCspListOfPoint(EdsConsSupProfileListOfPoints points) {
        cspListOfPoints = new HashSet<EdsConsSupProfileListOfPoints>(0);
        cspListOfPoints.add(points);
    }

    /*
     * Setter cspImage
     * 
     * @param cspImage the cspImage to set
     */
    public void setCspImage(String cspImage) {
        this.cspImage = cspImage;
    }

}
