/**
 * ChsPin generated by hbm2java
 */
package com.inetpsa.eds.dao.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class ChsPin implements Comparable<ChsPin>, Serializable {
    private String uid;
    private String cavityId;
    private String connectionName;
    private String cavity;
    private String pinType;
    private Chs component;

    public ChsPin() {
    }

    public ChsPin(String cavityId, String connectionName, String cavity, String pinType) {
        this.cavityId = cavityId;
        this.connectionName = connectionName;
        this.cavity = cavity;
        this.pinType = pinType;
    }

    @XmlTransient
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @XmlElement(name = "cavity-id")
    public String getCavityId() {
        return cavityId;
    }

    public void setCavityId(String cavityId) {
        this.cavityId = cavityId;
    }

    @XmlElement(name = "connection-name")
    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    @XmlElement(name = "cavity")
    public String getCavity() {
        return cavity;
    }

    public void setCavity(String cavity) {
        this.cavity = cavity;
    }

    @XmlElement(name = "pin-type")
    public String getPinType() {
        return pinType;
    }

    public void setPinType(String pinType) {
        this.pinType = pinType;
    }

    @XmlTransient
    public Chs getComponent() {
        return this.component;
    }

    public void setComponent(Chs component) {
        this.component = component;
    }

    // Generated by Hibernate Tools
    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof ChsPin))
            return false;
        ChsPin castOther = (ChsPin) other;

        return ((this.getCavityId() == castOther.getCavityId()) || (this.getCavityId() != null && castOther.getCavityId() != null && this
                .getCavityId().equals(castOther.getCavityId())))
                && ((this.getConnectionName() == castOther.getConnectionName()) || (this.getConnectionName() != null
                        && castOther.getConnectionName() != null && this.getConnectionName().equals(castOther.getConnectionName())))
                && ((this.getCavity() == castOther.getCavity()) || (this.getCavity() != null && castOther.getCavity() != null && this.getCavity()
                        .equals(castOther.getCavity())))
                && ((this.getPinType() == castOther.getPinType()) || (this.getPinType() != null && castOther.getPinType() != null && this
                        .getPinType().equals(castOther.getPinType())))
                && ((this.getComponent() == castOther.getComponent()) || (this.getComponent() != null && castOther.getComponent() != null && this
                        .getComponent().equals(castOther.getComponent())));
    }

    // Generated by Hibernate Tools
    public int hashCode() {
        int result = 17;

        result = 37 * result + (getCavityId() == null ? 0 : this.getCavityId().hashCode());
        result = 37 * result + (getConnectionName() == null ? 0 : this.getConnectionName().hashCode());
        result = 37 * result + (getCavity() == null ? 0 : this.getCavity().hashCode());
        result = 37 * result + (getPinType() == null ? 0 : this.getPinType().hashCode());
        result = 37 * result + (getComponent() == null ? 0 : this.getComponent().hashCode());
        return result;
    }

    /**
     * Compare by cavity if equals compares by pinType
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(ChsPin o) {
        int ret = cavity.compareTo(o.cavity);
        if (ret == 0) {
            ret = pinType.compareTo(o.pinType);
        }
        return ret;
    }

}
