package com.inetpsa.eds.dao.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class Chs {
    private String componentId;
    private String partNumber;
    private String description;
    private String userRef1;
    private String userRef2;
    private Set<ChsPin> cavities;
    private String describeCav;
    private Set<EdsEds> edsChs = new HashSet<EdsEds>(0);
    @Transient
    private Map<String, Integer> pinDetails;

    public Chs() {
    }

    public Chs(String componentId, String partNumber, String description, String userRef1, String userRef2, Set<ChsPin> cavities) {
        this.componentId = componentId;
        this.partNumber = partNumber;
        this.description = description;
        this.userRef1 = userRef1;
        this.userRef2 = userRef2;
        this.cavities = cavities;
    }

    @XmlTransient
    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    @XmlElement(name = "part-number")
    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "user-ref-1")
    public String getUserRef1() {
        return userRef1;
    }

    public void setUserRef1(String userRef1) {
        this.userRef1 = userRef1;
    }

    @XmlElement(name = "user-ref-2")
    public String getUserRef2() {
        return userRef2;
    }

    public void setUserRef2(String userRef2) {
        this.userRef2 = userRef2;
    }

    @XmlElement(name = "cavities")
    public Set<ChsPin> getCavities() {
        return cavities;
    }

    public void setCavities(Set<ChsPin> cavities) {
        this.cavities = cavities;
        initDetails();
    }

    /**
     * Hashmap to help generate the columns in the "Association FC" node
     */
    private void initDetails() {
        pinDetails = new HashMap<String, Integer>();
        String conName;
        int conCount;
        for (ChsPin pin : cavities) {
            conName = pin.getConnectionName();
            if (!pinDetails.containsKey(pin.getConnectionName())) {
                pinDetails.put(conName, 0);
            }
            conCount = pinDetails.get(conName) + 1;
            pinDetails.put(conName, conCount);
        }
    }

    @XmlElement(name = "cavity-description")
    public String getDescribeCav() {
        return describeCav;
    }

    public void setDescribeCav(String describeCav) {
        this.describeCav = describeCav;
    }

    @XmlTransient
    public Map<String, Integer> getPinDetails() {
        if (pinDetails != null) {
            initDetails();
        }
        return pinDetails;
    }

    public void setPinDetails(Map<String, Integer> pinDetails) {
        this.pinDetails = pinDetails;
    }

    @XmlTransient
    public Set<EdsEds> getEdsChs() {
        return edsChs;
    }

    public void setEdsChs(Set<EdsEds> edsChs) {
        this.edsChs = edsChs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((componentId == null) ? 0 : componentId.hashCode());
        result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Chs other = (Chs) obj;
        if (componentId == null) {
            if (other.componentId != null)
                return false;
        } else if (!componentId.equals(other.componentId))
            return false;
        if (partNumber == null) {
            if (other.partNumber != null)
                return false;
        } else if (!partNumber.equals(other.partNumber))
            return false;
        return true;
    }

}
