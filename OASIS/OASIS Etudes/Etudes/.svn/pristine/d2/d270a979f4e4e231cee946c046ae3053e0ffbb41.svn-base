/*
 * Author: U224106
 * Creation: 18 déc. 2014
 */
package com.inetpsa.oaz00.ws.server.services;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.inetpsa.cxl.core.fault.ServiceException;
import com.inetpsa.oaz00.ws.checker.formula.services.EquationChecker;
import com.inetpsa.oaz00.ws.checker.formula.services.EquationException;
import com.inetpsa.oaz00.ws.checker.formula.services.EquationVariables;
import com.inetpsa.oaz00.ws.checker.model.CriticalityLevels;
import com.inetpsa.oaz00.ws.checker.model.ResultCodeType;
import com.inetpsa.oaz00.ws.scilab.utils.Constants;
import com.inetpsa.oaz00.ws.scilab.utils.FunctionalLog;
import com.inetpsa.oaz00.ws.strategy.services.StrategyService;
import com.inetpsa.oaz00.ws.strategy.services.impl.ArithStatStrategyServiceImpl;
import com.inetpsa.oaz00.ws.strategy.services.impl.MonteCarloStrategyServiceImpl;
import com.inetpsa.oaz00.ws.strategy.services.impl.SemiQuadStrategyServiceImpl;
import com.inetpsa.oaz00.ws.strategy.services.impl.SpecITStrategyServiceImpl;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.Calculation;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CheckFormula;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.ResultType;

/**
 * The Class InterfaceOasisImpl.
 * 
 * @author U224106
 */
public class InterfaceOasisBiz {

    /** The strategy service. */
    private StrategyService strategyService = null;

    /** The logger. */
    private static Logger logger = Logger.getLogger(InterfaceOasisBiz.class);

    /**
     * FLOW 1 - Calculation.
     * 
     * @param calculationRequest calculationRequest
     * @return return
     * @throws ServiceException ServiceException
     */
    public Calculation calculation(Calculation calculationRequest) {
        // ******** Identify Calculation strategy ********
        switch (calculationRequest.getCalculationType()) {
        case MONTE_CARLO:
            strategyService = new MonteCarloStrategyServiceImpl(calculationRequest, Calendar.getInstance().getTime());
            break;
        case SPEC_IT:
            strategyService = new SpecITStrategyServiceImpl(calculationRequest, Calendar.getInstance().getTime());
            break;
        case ARITHMETIC:
            strategyService = new ArithStatStrategyServiceImpl(calculationRequest, Calendar.getInstance().getTime());
            break;
        case SEMI_QUADRATIC:
            strategyService = new SemiQuadStrategyServiceImpl(calculationRequest, Calendar.getInstance().getTime());
            break;
        default:
            break;
        }
        if (strategyService != null) {
            // Organize the input data
            strategyService.organizeInput();
            // strategyService.prepareData();
            // Check if there were any errors in organize input.
            if (calculationRequest.getResult().getStatus().longValue() == ResultCodeType.FORMULA_OK.value().longValue()) {
                // Perform input checks only if there were no errors in organizing.
                strategyService.checkInput();
            } else
                logger.warn("CalculationRequest structure is KO - ProjectName = " + calculationRequest.getProjectName() + " - UserId = " + calculationRequest.getUserId());
            // Check if there were any errors from check input.
            if (calculationRequest.getResult().getStatus().longValue() == ResultCodeType.FORMULA_OK.value().longValue()) {
                // Perform calculation only if there were no errors from input check.
                strategyService.runCalculation();
                // If the calculation status is OK then only do further operations
                if (calculationRequest.getResult().getStatus().longValue() == ResultCodeType.FORMULA_OK.value().longValue()) {
                    try {
                        // Generate XLS reports.
                        strategyService.generateReport();
                    } catch (IOException e) {
                        logger.error("Error during Report generation - ProjectName = " + calculationRequest.getProjectName() + " - UserId = " + calculationRequest.getUserId(), e);
                    }
                    // Now perform check output.
                    strategyService.checkOutput();
                    // And finally, prepare the output.
                    calculationRequest = strategyService.prepareOutput();
                    if (calculationRequest.getResult().getStatus().longValue() == ResultCodeType.FORMULA_OK.value().longValue()) {
                        calculationRequest.getResult().setLevel(CriticalityLevels.INFORMATION.criticalityValue());
                    }
                }
            } else
                logger.warn("CalculationRequest inputs are KO - ProjectName = " + calculationRequest.getProjectName() + " - UserId = " + calculationRequest.getUserId());
            return calculationRequest;
        }
        logger.warn("No strategy defined - ProjectName = " + calculationRequest.getProjectName() + " - UserId = " + calculationRequest.getUserId());
        return null;
    }

    /**
     * FLOW 2 - CheckFormula.
     * 
     * @param checkformulaRequest checkformulaRequest
     * @return return
     * @throws ServiceException ServiceException
     */
    public CheckFormula checkFormula(CheckFormula checkformulaRequest) {
        if (null == checkformulaRequest.getResult()) {
            checkformulaRequest.setResult(new ResultType());
        }
        // Getting the formula from transfer model.
        String formula = checkformulaRequest.getTransferModel().getFormula();
        String reportFormula = checkformulaRequest.getTransferModel().getReportFormula();
        FunctionalLog.writeLogDetails(null, null, null, formula);
        FunctionalLog.writeLogDetails(null, null, null, reportFormula);

        // Check the formula and report error, if any.
        EquationVariables ecVariables = new EquationVariables();
        try {
            BigInteger result = EquationChecker.checkEquation(formula, reportFormula, ecVariables);
            if (result == ResultCodeType.FORMULA_OK.value()) {
                checkformulaRequest.getResult().setStatus(result);
                checkformulaRequest.getResult().setDescription(Constants.getHalfConnectorProperties().getProperty("FORMULA_OK"));
                checkformulaRequest.getResult().setFormula(null);
                checkformulaRequest.getResult().setImpactedObject(null);
                checkformulaRequest.getResult().setPosition(null);
                checkformulaRequest.getResult().setLevel(CriticalityLevels.INFORMATION.criticalityValue());
                checkformulaRequest.getTransferModel().setStatus(true);
            }
        } catch (EquationException ee) {
            logger.warn("Incorrect formula - Formula = " + formula + " - UserId = " + checkformulaRequest.getUserId());
            // Setting the required field in ResultType
            checkformulaRequest.getResult().setStatus(ee.getErrorStatus());
            checkformulaRequest.getResult().setDescription(ee.getErrorDescription());
            checkformulaRequest.getResult().setFormula(reportFormula);
            checkformulaRequest.getResult().setPosition(ee.getPosition());
            if (ee.getErrorStatus().equals(ResultCodeType.INVALID_COMMA_IN_NUMBER.value())) {
                checkformulaRequest.getResult().setLevel(CriticalityLevels.WARNING.criticalityValue());
            } else {
                checkformulaRequest.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
            }
            checkformulaRequest.getTransferModel().setStatus(false);
        }
        return checkformulaRequest;
    }
}
