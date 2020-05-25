/*
 * Creation : 22 mai 2015
 */
package com.inetpsa.eds.dao.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * EdsConsSupProfileListOfPoints refers to table OPLQTSPP
 * 
 * @author gvillerez @ Alter Frame
 */
public class EdsConsSupProfileListOfPoints implements java.io.Serializable {

    private static final long serialVersionUID = 8463133562823522628L;

    /**
     * String Variable to hold the value for Column CSPP_ID of Table OPLQTCSP
     */
    private String csppId;

    /**
     * String Variable to hold the value for Column CSPP_XY_DATA of Table OPLQTSPP
     */
    private String csppXyData;

    /**
     * Float Variable to hold the value for Column CSPP_MESURE_TEMP of Table OPLQTSPP
     */
    private String csppMesureTemp;

    /**
     * Float Variable to hold the value for Column CSPP_Y_AXIS_VALUE of Table OPLQTSPP
     */
    private String csppYAxisValue;

    /**
     * Float Variable to hold the value for Column CSPP_TIME_AXIS_VALUE of Table OPLQTSPP
     */
    private String csppTimeAxisValue;

    /**
     * Float Variable to hold the value for Column CSPP_SOURCE_IMPEDANCE of Table OPLQTSPP
     */
    private String csppSourceImpedance;

    /**
     * 
     */
    private EdsConsSupProfile profile;

    /**
     * Parameterized constructor.
     * 
     * @param csppId
     * @param csppXyData
     * @param csppMesureTemp
     * @param csppYAxisValue
     * @param csppTimeAxisValue
     * @param csppSourceImpedance
     */
    public EdsConsSupProfileListOfPoints(String csppId, String csppXyData, String csppMesureTemp, String csppYAxisValue, String csppTimeAxisValue,
            String csppSourceImpedance, EdsConsSupProfile profile) {
        this.csppId = csppId;
        this.csppXyData = csppXyData;
        this.csppMesureTemp = csppMesureTemp;
        this.csppYAxisValue = csppYAxisValue;
        this.csppTimeAxisValue = csppTimeAxisValue;
        this.csppSourceImpedance = csppSourceImpedance;
        this.profile = profile;
    }

    /**
     * Empty constructor.
     */
    public EdsConsSupProfileListOfPoints(EdsConsSupProfile profile) {
        this.csppId = UUID.randomUUID().toString();
        this.csppXyData = "";
        this.profile = profile;
    }

    /**
     * Empty constructor.
     */
    public EdsConsSupProfileListOfPoints() {
        this.csppId = UUID.randomUUID().toString();
        this.csppXyData = "";
        this.profile = null;
    }

    /**
     * Copy constructor.
     * 
     * @param source
     */
    public EdsConsSupProfileListOfPoints(EdsConsSupProfileListOfPoints source) {
        this.csppId = UUID.randomUUID().toString();
        this.csppXyData = source.csppXyData;
        this.csppMesureTemp = source.csppMesureTemp;
        this.csppYAxisValue = source.csppYAxisValue;
        this.csppTimeAxisValue = source.csppTimeAxisValue;
        this.csppSourceImpedance = source.csppSourceImpedance;
        this.profile = source.profile;
    }

    /**
     * Getter csppId
     * 
     * @return the csppId
     */
    @XmlElement(name = "id")
    public String getCsppId() {
        return csppId;
    }

    /**
     * Setter csppId
     * 
     * @param csppId the csppId to set
     */
    public void setCsppId(String csppId) {
        this.csppId = csppId;
    }

    /**
     * Getter csppXyData
     * 
     * @return the csppXyData
     */
    @XmlElement(name = "xy-data")
    public String getCsppXyData() {
        return csppXyData;
    }

    /**
     * Setter csppXyData
     * 
     * @param csppXyData the csppXyData to set
     */
    public void setCsppXyData(String csppXyData) {
        this.csppXyData = csppXyData;
    }

    /**
     * Getter csppMesureTemp
     * 
     * @return the csppMesureTemp
     */
    @XmlElement(name = "mesure-temp")
    public String getCsppMesureTemp() {
        return csppMesureTemp;
    }

    /**
     * Setter csppMesureTemp
     * 
     * @param csppMesureTemp the csppMesureTemp to set
     */
    public void setCsppMesureTemp(String csppMesureTemp) {
        this.csppMesureTemp = csppMesureTemp;
    }

    /**
     * Getter csppYAxisValue
     * 
     * @return the csppYAxisValue
     */
    @XmlElement(name = "y-axis-value")
    public String getCsppYAxisValue() {
        return csppYAxisValue;
    }

    /**
     * Setter csppYAxisValue
     * 
     * @param csppYAxisValue the csppYAxisValue to set
     */
    public void setCsppYAxisValue(String csppYAxisValue) {
        this.csppYAxisValue = csppYAxisValue;
    }

    /**
     * Getter csppTimeAxisValue
     * 
     * @return the csppTimeAxisValue
     */
    @XmlElement(name = "time-axis-value")
    public String getCsppTimeAxisValue() {
        return csppTimeAxisValue;
    }

    /**
     * Setter csppTimeAxisValue
     * 
     * @param csppTimeAxisValue the csppTimeAxisValue to set
     */
    public void setCsppTimeAxisValue(String csppTimeAxisValue) {
        this.csppTimeAxisValue = csppTimeAxisValue;
    }

    /**
     * Getter csppSourceImpedance
     * 
     * @return the csppSourceImpedance
     */
    @XmlElement(name = "source-impedance")
    public String getCsppSourceImpedance() {
        return csppSourceImpedance;
    }

    /**
     * Setter csppSourceImpedance
     * 
     * @param csppSourceImpedance the csppSourceImpedance to set
     */
    public void setCsppSourceImpedance(String csppSourceImpedance) {
        this.csppSourceImpedance = csppSourceImpedance;
    }

    /**
     * Getter profile
     * 
     * @return the profile
     */
    @XmlTransient
    public EdsConsSupProfile getProfile() {
        return profile;
    }

    /**
     * Setter profile
     * 
     * @param profile the profile to set
     */
    public void setProfile(EdsConsSupProfile profile) {
        this.profile = profile;
    }
}
