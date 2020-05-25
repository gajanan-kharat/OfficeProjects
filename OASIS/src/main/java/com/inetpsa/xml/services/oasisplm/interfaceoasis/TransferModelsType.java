
package com.inetpsa.xml.services.oasisplm.interfaceoasis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransferModelsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransferModelsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transferModel" type="{http://xml.inetpsa.com/Services/OasisPLM/InterfaceOasis}TransferModelType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="nb" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransferModelsType", propOrder = {
    "transferModel"
})
public class TransferModelsType {

    @XmlElement(required = true)
    protected List<TransferModelType> transferModel;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nb;

    /**
     * Gets the value of the transferModel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transferModel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransferModel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransferModelType }
     * 
     * 
     */
    public List<TransferModelType> getTransferModel() {
        if (transferModel == null) {
            transferModel = new ArrayList<TransferModelType>();
        }
        return this.transferModel;
    }

    /**
     * Gets the value of the nb property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNb() {
        return nb;
    }

    /**
     * Sets the value of the nb property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNb(BigInteger value) {
        this.nb = value;
    }

}
