
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
 *         &lt;element name="calculationType" type="{http://xml.inetpsa.com/Services/OasisPLM/InterfaceOasis}CalculationType"/>
 *         &lt;element name="requirements" type="{http://xml.inetpsa.com/Services/OasisPLM/InterfaceOasis}RequirementsType"/>
 *         &lt;element name="transferModels" type="{http://xml.inetpsa.com/Services/OasisPLM/InterfaceOasis}TransferModelsType"/>
 *         &lt;element name="result" type="{http://xml.inetpsa.com/Services/OasisPLM/InterfaceOasis}ResultType" minOccurs="0"/>
 *         &lt;element name="projectName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "calculationType",
    "requirements",
    "transferModels",
    "result",
    "projectName",
    "userId"
})
@XmlRootElement(name = "calculation")
public class Calculation {

    @XmlElement(required = true)
    protected CalculationType calculationType;
    @XmlElement(required = true)
    protected RequirementsType requirements;
    @XmlElement(required = true)
    protected TransferModelsType transferModels;
    protected ResultType result;
    @XmlElement(required = true)
    protected String projectName;
    @XmlElement(required = true)
    protected String userId;

    /**
     * Gets the value of the calculationType property.
     * 
     * @return
     *     possible object is
     *     {@link CalculationType }
     *     
     */
    public CalculationType getCalculationType() {
        return calculationType;
    }

    /**
     * Sets the value of the calculationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CalculationType }
     *     
     */
    public void setCalculationType(CalculationType value) {
        this.calculationType = value;
    }

    /**
     * Gets the value of the requirements property.
     * 
     * @return
     *     possible object is
     *     {@link RequirementsType }
     *     
     */
    public RequirementsType getRequirements() {
        return requirements;
    }

    /**
     * Sets the value of the requirements property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequirementsType }
     *     
     */
    public void setRequirements(RequirementsType value) {
        this.requirements = value;
    }

    /**
     * Gets the value of the transferModels property.
     * 
     * @return
     *     possible object is
     *     {@link TransferModelsType }
     *     
     */
    public TransferModelsType getTransferModels() {
        return transferModels;
    }

    /**
     * Sets the value of the transferModels property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransferModelsType }
     *     
     */
    public void setTransferModels(TransferModelsType value) {
        this.transferModels = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link ResultType }
     *     
     */
    public ResultType getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultType }
     *     
     */
    public void setResult(ResultType value) {
        this.result = value;
    }

    /**
     * Gets the value of the projectName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the value of the projectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectName(String value) {
        this.projectName = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

}
