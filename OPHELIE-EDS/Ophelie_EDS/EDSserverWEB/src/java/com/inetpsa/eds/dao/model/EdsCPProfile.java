package com.inetpsa.eds.dao.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class EdsCPProfile implements Serializable {
    private long id;
    private EdsPinConnect wi;
    private Double type;
    private Double value;

    public EdsCPProfile() {

    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlTransient
    public EdsPinConnect getWi() {
        return wi;
    }

    public void setWi(EdsPinConnect wi) {
        this.wi = wi;
    }

    @XmlElement(name = "type")
    public Double getType() {
        return type;
    }

    public void setType(Double type) {
        this.type = type;
    }

    @XmlElement(name = "value")
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public EdsCPProfile duplicate(EdsPinConnect wi) {
        EdsCPProfile clone = new EdsCPProfile();
        clone.setType(type);
        clone.setValue(value);
        clone.setWi(wi);
        return clone;
    }

}
