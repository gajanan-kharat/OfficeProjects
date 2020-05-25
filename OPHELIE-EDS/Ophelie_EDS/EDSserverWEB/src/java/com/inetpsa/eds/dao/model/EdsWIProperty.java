/*
 * Creation : 21/09/2015
 */
package com.inetpsa.eds.dao.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class EdsWIProperty implements Serializable {
    private long id;
    private EdsPinConnect wi;
    private double time;
    private double current;

    public EdsWIProperty() {

    }

    @XmlElement(name = "time")
    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @XmlElement(name = "current")
    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    @XmlTransient
    public EdsPinConnect getWi() {
        return wi;
    }

    public void setWi(EdsPinConnect wi) {
        this.wi = wi;
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EdsWIProperty duplicate(EdsPinConnect wi) {
        EdsWIProperty clone = new EdsWIProperty();
        clone.setCurrent(current);
        clone.setTime(time);
        clone.setWi(wi);
        return clone;
    }

}
