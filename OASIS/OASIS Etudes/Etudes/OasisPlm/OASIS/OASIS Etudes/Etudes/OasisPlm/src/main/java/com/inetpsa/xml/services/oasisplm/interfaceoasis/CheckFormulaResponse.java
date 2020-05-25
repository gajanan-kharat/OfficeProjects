
package com.inetpsa.xml.services.oasisplm.interfaceoasis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://xml.inetpsa.com/Services/OasisPLM/InterfaceOasis}checkFormula"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "checkFormula"
})
@XmlRootElement(name = "CheckFormulaResponse")
public class CheckFormulaResponse {

    @XmlElement(namespace = "http://xml.inetpsa.com/Services/OasisPLM/InterfaceOasis", required = true)
    protected CheckFormula checkFormula;

    /**
     * Gets the value of the checkFormula property.
     * 
     * @return
     *     possible object is
     *     {@link CheckFormula }
     *     
     */
    public CheckFormula getCheckFormula() {
        return checkFormula;
    }

    /**
     * Sets the value of the checkFormula property.
     * 
     * @param value
     *     allowed object is
     *     {@link CheckFormula }
     *     
     */
    public void setCheckFormula(CheckFormula value) {
        this.checkFormula = value;
    }

}
