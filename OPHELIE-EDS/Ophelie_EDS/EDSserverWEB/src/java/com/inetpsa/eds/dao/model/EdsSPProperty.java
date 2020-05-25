package com.inetpsa.eds.dao.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class EdsSPProperty implements Serializable {
    private long id;
    private EdsPinConnect wi;
    private int type;
    private Double value;
    private String user;
    private String comment;

    public EdsSPProperty() {

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
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @XmlElement(name = "value")
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @XmlElement(name = "user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @XmlElement(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public EdsSPProperty duplicate(EdsPinConnect wi) {
        EdsSPProperty clone = new EdsSPProperty();
        clone.setComment(comment);
        clone.setType(type);
        clone.setUser(user);
        clone.setValue(value);
        clone.setWi(wi);
        return clone;
    }

}
