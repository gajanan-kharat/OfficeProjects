/*
 * Author: U224106
 * Creation: 18 déc. 2014
 */
package com.inetpsa.oaz00.ws.server.services;

import java.util.Calendar;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.log4j.Logger;

import com.inetpsa.oaz00.ws.scilab.utils.FunctionalLog;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationRequest;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationResponse;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CheckFormulaRequest;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CheckFormulaResponse;

@WebService(targetNamespace = "http://oaz.inetpsa.com/InterfaceOasis", serviceName = "InterfaceOasis", endpointInterface = "com.inetpsa.xml.services.oasisplm.interfaceoasis.InterfaceOasis", wsdlLocation = "WEB-INF/wsdl/InterfaceOasis.wsdl")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
/**
 * The Class InterfaceOasisImpl.
 * 
 * @author
 */
public class InterfaceOasisImpl implements com.inetpsa.xml.services.oasisplm.interfaceoasis.InterfaceOasisPortType {

    private com.inetpsa.oaz00.ws.server.services.InterfaceOasisBiz calculator = new com.inetpsa.oaz00.ws.server.services.InterfaceOasisBiz();

    /** The Constant PREFIX. */
    private static final String CHECK_FORMULA_MODULE_PREFIX = "CHECK_FORMULA";

    private static Logger logger = Logger.getLogger(InterfaceOasisImpl.class);

    /**
     * Instantiates a new InterfaceOasisImpl service implementation.
     */
    public InterfaceOasisImpl() {
        logger.trace("Log OAZ :: TRACE OK");
        logger.debug("Log OAZ :: DEBUG OK");
        logger.info("Log OAZ :: INFO OK");
        logger.warn("Log OAZ :: WARN OK");
        logger.error("Log OAZ :: ERROR OK");
        logger.fatal("Log OAZ :: FATAL OK");
    }

    /**
     * Calculation response. {@inheritDoc}
     * 
     * @see com.inetpsa.xml.services.oasisplm.interfaceoasis.InterfaceOasisPortType#calculation(com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationRequest)
     */
    @WebMethod
    public CalculationResponse calculation(CalculationRequest calculationRequest) {
        // Calendar instance for timestamp
        Calendar now = Calendar.getInstance();
        // Get the start_Date for the request.
        String startDate = now.get(Calendar.YEAR) + "." + now.get(Calendar.DAY_OF_YEAR) + "." + now.get(Calendar.HOUR_OF_DAY) + "." + now.get(Calendar.MINUTE);
        logger.info("--The calculation method - called on server side.");
        CalculationResponse response = new CalculationResponse();
        // Delegate to the InterfaceOasisBiz class.
        response.setCalculation(calculator.calculation(calculationRequest.getCalculation()));
        // Do the logging.
        String projectName = calculationRequest.getCalculation().getProjectName();
        CalculationType module = calculationRequest.getCalculation().getCalculationType();
        String userId = calculationRequest.getCalculation().getUserId();
        // Creating the log file and inserting the information of a request.
        FunctionalLog.writeLogDetails(startDate, userId, projectName, module.toString());
        return response;
    }

    /**
     * CheckFormula response {@inheritDoc}
     * 
     * @see com.inetpsa.xml.services.oasisplm.interfaceoasis.InterfaceOasisPortType#checkFormula(com.inetpsa.xml.services.oasisplm.interfaceoasis.CheckFormulaRequest)
     */
    @WebMethod
    public CheckFormulaResponse checkFormula(CheckFormulaRequest checkformulaRequest) {
        FunctionalLog.writeLogDetails(null, null, null, "*********************START SOAP SERVICE****************************");
        // Calendar instance for timestamp
        Calendar now = Calendar.getInstance();
        // Get the start_Date for the request.
        String start_Date = now.get(Calendar.YEAR) + "." + now.get(Calendar.DAY_OF_YEAR) + "." + now.get(Calendar.HOUR_OF_DAY) + "." + now.get(Calendar.MINUTE);
        logger.info("--The CheckFormula method - called on server side.");
        CheckFormulaResponse response = new CheckFormulaResponse();
        // Delegate to the InterfaceOasisBiz class.
        response.setCheckFormula(calculator.checkFormula(checkformulaRequest.getCheckFormula()));
        String userId = checkformulaRequest.getCheckFormula().getUserId();
        // Creating the log file and inserting the information of a request.
        FunctionalLog.writeLogDetails(start_Date, userId, null, CHECK_FORMULA_MODULE_PREFIX);
        FunctionalLog.writeLogDetails(start_Date, userId, null, "****************END SOAP SERVICE*******************************");
        return response;
    }

}
