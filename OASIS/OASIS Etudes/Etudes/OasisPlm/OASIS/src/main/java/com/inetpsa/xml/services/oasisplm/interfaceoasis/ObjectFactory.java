
package com.inetpsa.xml.services.oasisplm.interfaceoasis;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.inetpsa.xml.services.oasisplm.interfaceoasis package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ServiceException_QNAME = new QName("http://xml.inetpsa.com/Services/OasisPLM/InterfaceOasis", "ServiceException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.inetpsa.xml.services.oasisplm.interfaceoasis
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CalculationResponse }
     * 
     */
    public CalculationResponse createCalculationResponse() {
        return new CalculationResponse();
    }

    /**
     * Create an instance of {@link CalculationRequest }
     * 
     */
    public CalculationRequest createCalculationRequest() {
        return new CalculationRequest();
    }

    /**
     * Create an instance of {@link CheckFormulaRequest }
     * 
     */
    public CheckFormulaRequest createCheckFormulaRequest() {
        return new CheckFormulaRequest();
    }

    /**
     * Create an instance of {@link RequirementsType }
     * 
     */
    public RequirementsType createRequirementsType() {
        return new RequirementsType();
    }

    /**
     * Create an instance of {@link RequirementType }
     * 
     */
    public RequirementType createRequirementType() {
        return new RequirementType();
    }

    /**
     * Create an instance of {@link CheckFormulaResponse }
     * 
     */
    public CheckFormulaResponse createCheckFormulaResponse() {
        return new CheckFormulaResponse();
    }

    /**
     * Create an instance of {@link TransferModelType }
     * 
     */
    public TransferModelType createTransferModelType() {
        return new TransferModelType();
    }

    /**
     * Create an instance of {@link Calculation }
     * 
     */
    public Calculation createCalculation() {
        return new Calculation();
    }

    /**
     * Create an instance of {@link CheckFormula }
     * 
     */
    public CheckFormula createCheckFormula() {
        return new CheckFormula();
    }

    /**
     * Create an instance of {@link ResultType }
     * 
     */
    public ResultType createResultType() {
        return new ResultType();
    }

    /**
     * Create an instance of {@link TransferModelsType }
     * 
     */
    public TransferModelsType createTransferModelsType() {
        return new TransferModelsType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xml.inetpsa.com/Services/OasisPLM/InterfaceOasis", name = "ServiceException")
    public JAXBElement<Object> createServiceException(Object value) {
        return new JAXBElement<Object>(_ServiceException_QNAME, Object.class, null, value);
    }

}
