package com.inetpsa.eds.dao.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * EdsBsxPin class.
 */
@XmlType
public class EdsBsxPin implements Serializable, Comparable {

    /**
     * String Variable to hold the value for Column BSXPIN_UID of Table OPLQTBPN
     */
    private String uId;

    /**
     * String Variable to hold the value for Column BSXPIN_TYPE of Table OPLQTBPN
     */
    private String type;

    /**
     * String Variable to hold the value for Column BSXPIN_NAME of Table OPLQTBPN
     */
    private String name;

    private EdsBsx edsBsx;

    /**
     * Default Constructor
     */
    public EdsBsxPin() {
    }

    /**
     * Parameterized Constructor
     */
    public EdsBsxPin(EdsBsxPin pin) {
        this(pin.uId, pin.name);
    }

    /**
     * Parameterized Constructor
     */
    public EdsBsxPin(String uId, String name) {
        this.uId = uId;
        this.name = name;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "pin-uid")
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @XmlElement(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public EdsBsx getEdsBsx() {
        return edsBsx;
    }

    public void setEdsBsx(EdsBsx edsBsx) {
        this.edsBsx = edsBsx;
    }

    /**
     * Test if the given pin name is the same name of the actual pin. '-' will be automatically transformed to '_', in order to be used with CHS
     * names. The test will not be case sensitive.
     * 
     * @param pinName The pin name.
     * @return True if the name are the same, false otherwise.
     */
    public boolean isSamePinAs(String pinName) {
        if (pinName == null)
            return false;

        return pinName.replace('-', '_').toLowerCase().equals(getName().toLowerCase());
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof EdsBsxPin))
            return 0;
        else
            return getName().compareToIgnoreCase(((EdsBsxPin) o).getName());
    }
}
