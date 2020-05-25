/*
 * Author: U224106
 * Creation: 18 déc. 2014
 */
package com.inetpsa.oaz00.ws.strategy.services.impl;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.inetpsa.oaz00.ws.checker.formula.services.EquationChecker;
import com.inetpsa.oaz00.ws.checker.formula.services.EquationException;
import com.inetpsa.oaz00.ws.checker.formula.services.EquationVariables;
import com.inetpsa.oaz00.ws.checker.formula.services.ParametersChecker;
import com.inetpsa.oaz00.ws.checker.model.CriticalityLevels;
import com.inetpsa.oaz00.ws.checker.model.ResultCodeType;
import com.inetpsa.oaz00.ws.report.services.ReportService;
import com.inetpsa.oaz00.ws.report.services.impl.SemiQuadReportServiceImpl;
import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.services.SciCalculationService;
import com.inetpsa.oaz00.ws.scilab.services.impl.SemiQuadSciCalculationServiceImpl;
import com.inetpsa.oaz00.ws.scilab.utils.Constants;
import com.inetpsa.oaz00.ws.scilab.utils.InputOrganizerUtil;
import com.inetpsa.oaz00.ws.scilab.utils.SciObjectInputAdapter;
import com.inetpsa.oaz00.ws.scilab.utils.SemiQuadOutputAdapter;
import com.inetpsa.oaz00.ws.strategy.services.StrategyService;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.Calculation;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.ResultType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelType;

/**
 * The Class SemiQuadStrategyServiceImpl.
 * 
 * @author U224106
 */
public class SemiQuadStrategyServiceImpl implements StrategyService {

    /** Half connector properties */
    private static Properties HALF_CONNECTOR_PROPERTIES = Constants.getHalfConnectorProperties();

    /** The execution date. */
    private Date executionDate;

    /** The calculation object as the main input */
    private Calculation calculation;

    /** The Mother requirement. */
    private SciMotherRequirementType motherRequirement;

    /** The report path of the request. */
    private String reportPath = "";

    /** Hash set to maintain unique impacted object ids. */
    private HashSet<String> impactedObjects = new HashSet<String>();

    /** The logger. */
    private static Logger logger = Logger.getLogger(SemiQuadStrategyServiceImpl.class);

    /**
     * The variables exist in the formula
     */
    private EquationVariables ecVariables = new EquationVariables();

    /**
     * Instantiates a new Semi Quadratic strategy service implementation.
     * 
     * @param calculation the calculation
     */
    public SemiQuadStrategyServiceImpl(Calculation calculation, Date execDate) {
        this.calculation = calculation;
        this.executionDate = execDate;
        if (null == this.calculation.getResult()) {
            this.calculation.setResult(new ResultType());
        }
        if (null == this.calculation.getResult().getStatus()) {
            this.calculation.getResult().setStatus(ResultCodeType.FORMULA_OK.value());
        }
        for (RequirementType requirementType : this.calculation.getRequirements().getRequirement()) {
            requirementType.setStatus(true);
        }
        for (TransferModelType transferModelType : this.calculation.getTransferModels().getTransferModel()) {
            transferModelType.setStatus(true);
        }
    }

    /**
     * Organize the raw data given by client (Ex: ENOVIA). {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.strategy.services.StrategyService#organizeInput()
     */
    @Override
    public void organizeInput() {
        // Get the input requirements list from client (for ex. Enovia).
        List<RequirementType> requirementList = this.calculation.getRequirements().getRequirement();
        // Get the input transfer models list from client (for ex. Enovia).
        List<TransferModelType> transferModels = this.calculation.getTransferModels().getTransferModel();
        // Filter out from the requirements list, the sciMotherRequirementType object(s).
        List<RequirementType> pMotherRequirementsList = InputOrganizerUtil.getPMotherRequirementsList(requirementList);
        if (pMotherRequirementsList == null) {
            this.calculation.getResult().setStatus(ResultCodeType.ERRONEOUS_SEMI_QUAD_OUTPUT.value());
            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("ERRONEOUS_SEMI_QUAD_OUTPUT"));
            this.calculation.getResult().setLevel(CriticalityLevels.ERROR.toString());
            return;
        }
        // Get the first sciMotherRequirementType object to...
        RequirementType pMotherRequirement = pMotherRequirementsList.get(0);
        // ...figure out the corresponding transferModel object, and...
        TransferModelType pTransferModel = InputOrganizerUtil.getTransferModel(pMotherRequirement, transferModels);
        if (pTransferModel == null) {
            this.calculation.getResult().setStatus(ResultCodeType.ERRONEOUS_SEMI_QUAD_OUTPUT.value());
            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("ERRONEOUS_SEMI_QUAD_OUTPUT"));
            this.calculation.getResult().setImpactedObject(pMotherRequirement.getName());
            this.calculation.getResult().setLevel(CriticalityLevels.ERROR.toString());
            pMotherRequirement.setStatus(false);
            return;
        }
        // ...the corresponding contributors for the transferModel object.
        List<RequirementType> pContributorList = InputOrganizerUtil.getPContributorsList(requirementList, pTransferModel);
        if (pContributorList == null) {
            this.calculation.getResult().setStatus(ResultCodeType.ERRONEOUS_SEMI_QUAD_OUTPUT.value());
            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("ERRONEOUS_SEMI_QUAD_OUTPUT"));
            // this.calculation.getResult().setImpactedObject(pTransferModel.getId());
            this.calculation.getResult().setLevel(CriticalityLevels.ERROR.toString());
            pTransferModel.setStatus(false);
            return;
        }
        // Now instantiate the actual sciMotherRequirementType object and...
        // assign it to the "motherRequirement" belonging to this instance.
        this.motherRequirement = SciObjectInputAdapter.createScilabMotherRequirementType(pMotherRequirement, pTransferModel, pContributorList);
    }

    /**
     * Check the provided input. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.strategy.services.StrategyService#checkInput()
     */
    @Override
    public void checkInput() {
        // Status of error detection
        boolean errorDetected = false;
        // Now get the input requirements list from client (for ex. Enovia).
        List<RequirementType> requirementList = this.calculation.getRequirements().getRequirement();
        // Get the input transfer models list from client (for ex. Enovia).
        List<TransferModelType> transferModels = this.calculation.getTransferModels().getTransferModel();
        // Iterate over each transfer model for checking the formula and reportFormula.
        for (TransferModelType transferModel : transferModels) {
            // Get the formula from transferModel.
            String formula = transferModel.getFormula();
            // Get the reportFormula from transferModel.
            String reportFormula = transferModel.getReportFormula();
            // Check the formula and report error, if any.
            try {
                EquationChecker.checkEquation(formula, reportFormula, ecVariables);
            } catch (EquationException ee) {
                logger.warn("Semi Quad - Incorrect formula - Formula = " + formula + " - ProjectName = " + calculation.getProjectName()
                        + " - UserId = " + calculation.getUserId(), ee.getCause());
                if (!ee.getErrorStatus().equals(ResultCodeType.INVALID_COMMA_IN_NUMBER.value())) {
                    // Setting the required field in ResultType
                    this.calculation.getResult().setStatus(ee.getErrorStatus());
                    this.calculation.getResult().setDescription(ee.getErrorDescription());
                    this.calculation.getResult().setFormula(reportFormula);
                    // this.calculation.getResult().setImpactedObject(transferModel.getId());
                    this.calculation.getResult().setPosition(ee.getPosition());
                    this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
                    // Set the status of tranferModel from transferModelsList to false.
                    transferModel.setStatus(false);
                    // True, indicates that error was detected.
                    errorDetected = true;
                }
            }
            /** VCOPLMPSA-37828: Code modification START. Date: 08-November-2016 */
            // Check the contributors reference.Note that errorDetected is also passed as a parameter.
            errorDetected = ParametersChecker.checkContributors(this.motherRequirement, this.calculation, errorDetected);
            /** VCOPLMPSA-37828: Code modification START. Date: 08-November-2016 */
        }
        // Getting TNC and CPK from the motherRequirement.
        Double tnc = this.motherRequirement.getTnc();
        Double cpk = this.motherRequirement.getCpk();
        // Check the TNC and CPK report error, if any.
        if (tnc == null || tnc.equals(Constants.EMPTY_STRING) || tnc < 0 || cpk == null || cpk.equals(Constants.EMPTY_STRING) || cpk < 0) {
            // Check whether error was detected or not.
            if (!errorDetected) {
                // Setting the required field in ResultType
                this.calculation.getResult().setStatus(ResultCodeType.INVALID_SEMI_QUAD_INPUT.value());
                this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_SEMI_QUAD_INPUT"));
                this.calculation.getResult().setImpactedObject(this.motherRequirement.getName());
                this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
                // Set the status of requirement from requirementList to false.
                for (RequirementType requirement : requirementList) {
                    if (requirement.getId().equals(this.motherRequirement.getId())) {
                        requirement.setStatus(false);
                    }
                }
                // True, indicates that error was detected.
                errorDetected = true;
            } else {
                // If we are here then it indicates that error is already detected so we are just appending the impacted object to the existing
                // one.
                this.calculation.getResult()
                        .setImpactedObject(this.calculation.getResult().getImpactedObject() + "," + this.motherRequirement.getName());
            }
        }
        // Getting the contributors from motherReqiurement.
        for (SciRequirementType sciContributor : this.motherRequirement.getContributorList()) {
            Double nominalValue = sciContributor.getNominal();
            Double lowerValue = sciContributor.getValueInf();
            Double upperValue = sciContributor.getValueSup();
            Double mean = sciContributor.getMean();
            Double standardDeviation = sciContributor.getStandardDeviation();
            // And finally, the mandatory fields check.
            if (nominalValue == null || nominalValue.equals(Constants.EMPTY_STRING) || lowerValue == null || lowerValue.equals(Constants.EMPTY_STRING)
                    || upperValue == null || upperValue.equals(Constants.EMPTY_STRING) || mean == null || mean.equals(Constants.EMPTY_STRING)
                    || standardDeviation == null || standardDeviation.equals(Constants.EMPTY_STRING)) {
                // Check whether error was detected or not.
                if (!errorDetected) {
                    // Setting the required field in ResultType
                    this.calculation.getResult().setStatus(ResultCodeType.INVALID_SEMI_QUAD_INPUT.value());
                    this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_SEMI_QUAD_INPUT"));
                    this.calculation.getResult().setImpactedObject(sciContributor.getName());
                    this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
                    // Set the status of requirement from requirementList to false.
                    for (RequirementType requirement : requirementList) {
                        if (requirement.getId().equals(sciContributor.getId())) {
                            requirement.setStatus(false);
                        }
                    }
                    // True, indicates that error was detected.
                    errorDetected = true;
                } else {
                    // If we are here then it indicates that error is already detected so we are just appending the impacted object to the
                    // existing
                    // one.
                    this.calculation.getResult().setImpactedObject(this.calculation.getResult().getImpactedObject() + "," + sciContributor.getName());
                }
            }
        }
    }

    /**
     * Prepare the data. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.strategy.services.StrategyService#prepareData()
     */
    @Override
    public void prepareData() {

    }

    /**
     * Running the calculation object. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.strategy.services.StrategyService#runCalculation()
     */
    @Override
    public void runCalculation() {

        SciCalculationService calculationService = new SemiQuadSciCalculationServiceImpl(motherRequirement, ecVariables);

        calculationService.computeInScilab();

        double[] d = calculationService.getScilabData();
        // Added check for length of the scilab calculation output data for the issue VCOPLMPSA-28520
        if (d.length == 0) {
            this.calculation.getResult().setStatus(ResultCodeType.ERRONEOUS_SEMI_QUAD_OUTPUT.value());
            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("ERRONEOUS_SEMI_QUAD_OUTPUT"));
            this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
        } else {
            SemiQuadOutputAdapter.createSemiQuadScilabResult(motherRequirement, d);
        }
    }

    /**
     * Generate the report. {@inheritDoc}
     * 
     * @throws IOException
     * @see com.inetpsa.oaz00.ws.strategy.services.StrategyService#generateReport()
     */
    @Override
    public void generateReport() throws IOException {
        ReportService reportService = new SemiQuadReportServiceImpl(motherRequirement, calculation.getProjectName(), executionDate,
                calculation.getUserId());
        // Gives the full path of a report file.
        String reportFileNamePath = reportService.generateReport();
        // Extracting the file name from the path of a report file.
        String fileName = reportFileNamePath.substring(reportFileNamePath.lastIndexOf(File.separator) + 1);
        // Getting the host name and the port number of tomcat from the properties file for generating the url.
        String port = Constants.EMPTY_STRING;
        if (!(Constants.getHalfConnectorServerConfigProperties().getProperty("port").equals("80"))) {
            port = ":" + Constants.getHalfConnectorServerConfigProperties().getProperty("port");
        }
        reportPath = Constants.PROTOCOL + InetAddress.getLocalHost().getHostName() + port + Constants.URL_SEPERATOR_CHAR;
        reportPath = reportPath + Constants.getHalfConnectorServerConfigProperties().getProperty("webProjectName");
        // Finally, appending of the file name for URL.
        reportPath = reportPath + Constants.URL_SEPERATOR_CHAR + "reports?report=" + fileName;
    }

    /**
     * Checks the output coming from Scilab. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.strategy.services.StrategyService#checkOutput()
     */
    @Override
    public void checkOutput() {
        // Status of error detection
        boolean errorDetected = false;
        // Getting the nominalValue from MotherRequirement.
        // Check whether the nominalValue is isfinite or not.
        if (this.motherRequirement.getNominal().isInfinite()) {
            // setting the nominalValue value to null.
            this.motherRequirement.setNominal(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the ArithmeticalTI from MotherRequirement.
        // Check whether the ArithmeticalIT is isfinite or not.
        if (this.motherRequirement.getArithmeticalIT().isInfinite()) {
            // setting the ArithmeticalIT value to null.
            this.motherRequirement.setStandardDeviation(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the ArithmeticalLowerIT from MotherRequirement.
        // Check whether the ArithmeticalLowerIT is isfinite or not.
        if (this.motherRequirement.getArithmeticalITInf().isInfinite()) {
            // setting the ArithmeticalLowerIT value to null.
            this.motherRequirement.setArithmeticalITInf(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the ArithmeticalUpperIT from MotherRequirement.
        // Check whether the ArithmeticalUpperIT is isfinite or not.
        if (this.motherRequirement.getArithmeticalITSup().isInfinite()) {
            // setting the ArithmeticalUpperIT value to null.
            this.motherRequirement.setArithmeticalITSup(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the ArithmeticalLowerValue from MotherRequirement.
        // Check whether the ArithmeticalLowerValue is isfinite or not.
        if (this.motherRequirement.getArithmeticalValueInf().isInfinite()) {
            // setting the ArithmeticalLowerValue value to null.
            this.motherRequirement.setArithmeticalValueInf(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the ArithmeticalUpperValue from MotherRequirement.
        // Check whether the ArithmeticalUpperValue is isfinite or not.
        if (this.motherRequirement.getArithmeticalValueSup().isInfinite()) {
            // setting the ArithmeticalUpperValue value to null.
            this.motherRequirement.setArithmeticalValueSup(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the StatisticalIT from MotherRequirement.
        // Check whether the StatisticalIT is isfinite or not.
        if (this.motherRequirement.getStatisticalIT().isInfinite()) {
            // setting the StatisticalIT value to null.
            this.motherRequirement.setStatisticalIT(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the StatisticalLowerIT from MotherRequirement.
        // Check whether the StatisticalLowerIT is isfinite or not.
        if (this.motherRequirement.getStatisticalITInf().isInfinite()) {
            // setting the StatisticalLowerIT value to null.
            this.motherRequirement.setStatisticalITInf(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the StatisticalUpperIT from MotherRequirement.
        // Check whether the StatisticalUpperIT is isfinite or not.
        if (this.motherRequirement.getStatisticalITSup().isInfinite()) {
            // setting the StatisticalUpperIT value to null.
            this.motherRequirement.setStatisticalITSup(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the StatisticalLowerValue from MotherRequirement.
        // Check whether the StatisticalLowerValue is isfinite or not.
        if (this.motherRequirement.getStatisticalValueInf().isInfinite()) {
            // setting the StatisticalLowerValue value to null.
            this.motherRequirement.setStatisticalValueInf(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the StatisticalUpperValue from MotherRequirement.
        // Check whether the StatisticalUpperValue is isfinite or not.
        if (this.motherRequirement.getStatisticalValueSup().isInfinite()) {
            // setting the StatisticalUpperValue value to null.
            this.motherRequirement.setStatisticalValueSup(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the Semi-QuadraticIT from MotherRequirement.
        // Check whether the Semi-QuadraticIT is isfinite or not.
        if (this.motherRequirement.getSemiQuadraticIT().isInfinite()) {
            // setting the Semi-QuadraticIT value to null.
            this.motherRequirement.setSemiQuadraticIT(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the Semi-QuadraticLowerIT from MotherRequirement.
        // Check whether the Semi-QuadraticLowerIT is isfinite or not.
        if (this.motherRequirement.getSemiQuadraticITInf().isInfinite()) {
            // setting the Semi-QuadraticLowerIT value to null.
            this.motherRequirement.setSemiQuadraticITInf(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the Semi-QuadraticUpperIT from MotherRequirement.
        // Check whether the Semi-QuadraticUpperIT is isfinite or not.
        if (this.motherRequirement.getSemiQuadraticITSup().isInfinite()) {
            // setting the Semi-QuadraticUpperIT value to null.
            this.motherRequirement.setSemiQuadraticITSup(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the Semi-QuadraticLowerValue from MotherRequirement.
        // Check whether the Semi-QuadraticLowerValue is isfinite or not.
        if (this.motherRequirement.getSemiQuadraticValueInf().isInfinite()) {
            // setting the Semi-QuadraticLowerValue value to null.
            this.motherRequirement.setSemiQuadraticValueInf(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the Semi-QuadraticUpperValue from MotherRequirement.
        // Check whether the Semi-QuadraticUpperValue is isfinite or not.
        if (this.motherRequirement.getSemiQuadraticValueSup().isInfinite()) {
            // setting the Semi-QuadraticUpperValue value to null.
            this.motherRequirement.setSemiQuadraticValueSup(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Getting the centeringMax from MotherRequirement.
        // Check whether the centeringMax is isfinite or not.
        if (this.motherRequirement.getCenteringMax().isInfinite()) {
            // setting the centeringMax value to null.
            this.motherRequirement.setCenteringMax(null);
            // Setting the status, description, impactedObject and level.
            errorDetected = setOutputErrorAttributes(errorDetected);
        }
        // Check whether the calculation is performed successfully or not.
        if (!errorDetected) {
            for (RequirementType requirement : this.calculation.getRequirements().getRequirement()) {
                if (requirement.getId().equalsIgnoreCase(this.motherRequirement.getId())) {
                    // Getting the original tnc value from requirement list and check value whether it is less than 100 or not.
                    if (requirement.getTnc() < 100) {
                        // A warning message will be sent to Enovia to inform end-user that he may confuse TNC unit with percentage.
                        this.calculation.getResult().setStatus(ResultCodeType.SEMI_QUAD_TNC_INPUT.value());
                        this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("SEMI_QUAD_TNC_INPUT"));
                        this.calculation.getResult().setImpactedObject(this.motherRequirement.getName());
                        this.calculation.getResult().setLevel(CriticalityLevels.INFORMATION.criticalityValue());
                        break;
                    }
                }
            }
        }
        // Check the status of calculation so as to ensure that the calculation is performed successfully without any error.
        if (this.calculation.getResult().getStatus().longValue() == ResultCodeType.FORMULA_OK.value().longValue()) {
            // Setting the description for SemiQuad.
            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("SEMI_QUAD_COMPLETE"));
            this.calculation.getResult().setFormula(null);
            this.calculation.getResult().setImpactedObject(null);
            this.calculation.getResult().setPosition(null);
        }
    }

    /**
     * Setting the status , description , ImpactedObject and level of ResultType.
     */
    private boolean setOutputErrorAttributes(boolean errorDetected) {
        // Check whether error was detected or not.
        if (!errorDetected) {
            // Setting the status, description, impactedObject and level.
            this.calculation.getResult().setStatus(ResultCodeType.INFINITE_SEMI_QUAD_OUTPUT.value());
            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INFINITE_SEMI_QUAD_OUTPUT"));
            this.calculation.getResult().setImpactedObject(this.motherRequirement.getName());
            impactedObjects.add(this.motherRequirement.getId());
            this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
            this.motherRequirement.setStatus(false);
            // True, indicates that error was detected.
            errorDetected = true;
        } else {
            // If we are here then it indicates that error is already detected and now we are checking whether the id is present in the
            // impactedObject hash set.
            if (!impactedObjects.contains(this.motherRequirement.getId())) {
                // If not present then append the impactedObject.
                this.calculation.getResult()
                        .setImpactedObject(this.calculation.getResult().getImpactedObject() + "," + this.motherRequirement.getName());
                // add the id in the impactedObject hash set.
                impactedObjects.add(this.motherRequirement.getId());
            }
        }
        return errorDetected;
    }

    /**
     * Prepare the data coming from the Scilab. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.strategy.services.StrategyService#prepareOutput()
     */
    @Override
    public Calculation prepareOutput() {
        List<RequirementType> requirementsFromCalculation = this.calculation.getRequirements().getRequirement();
        // Set the topmost mother requirement as the resultant object into the calculation object.
        for (RequirementType aRequirement : requirementsFromCalculation) {
            // The check after && is double-reinforcement.
            if (aRequirement.getParentId().equalsIgnoreCase(aRequirement.getId())
                    && aRequirement.getId().equalsIgnoreCase(this.motherRequirement.getId())) {
                // Setting the nominal value of the OriginalMotherRequirement.
                aRequirement.setNominal(motherRequirement.getNominal());
                // Setting the CenteringMax value of the OriginalMotherRequirement.
                aRequirement.setCenteringMax(motherRequirement.getCenteringMax());
                // Setting the ArithmeticalIT ,ArithmeticalITInf , ArithmeticalITSup ,ArithmeticalValueInf and ArithmeticalValueSup values of the
                // OriginalMotherRequirement.
                aRequirement.setArithmeticalIT(motherRequirement.getArithmeticalIT());
                aRequirement.setArithmeticalITInf(motherRequirement.getArithmeticalITInf());
                aRequirement.setArithmeticalITSup(motherRequirement.getArithmeticalITSup());
                aRequirement.setArithmeticalValueInf(motherRequirement.getArithmeticalValueInf());
                aRequirement.setArithmeticalValueSup(motherRequirement.getArithmeticalValueSup());
                // Setting the SemiQuadraticIT ,SemiQuadraticITInf ,SemiQuadraticITSup ,SemiQuadraticValueInf and SemiQuadraticValueSupvalues of the
                // OriginalMotherRequirement.
                aRequirement.setSemiQuadraticIT(motherRequirement.getSemiQuadraticIT());
                aRequirement.setSemiQuadraticITInf(motherRequirement.getSemiQuadraticITInf());
                aRequirement.setSemiQuadraticITSup(motherRequirement.getSemiQuadraticITSup());
                aRequirement.setSemiQuadraticValueInf(motherRequirement.getSemiQuadraticValueInf());
                aRequirement.setSemiQuadraticValueSup(motherRequirement.getSemiQuadraticValueSup());
                // Setting the StatisticalIT ,StatisticalITInf ,StatisticalITSup ,StatisticalValueInf and StatisticalValueSup values of the
                // OriginalMotherRequirement.
                aRequirement.setStatisticalIT(motherRequirement.getStatisticalIT());
                aRequirement.setStatisticalITInf(motherRequirement.getStatisticalITInf());
                aRequirement.setStatisticalITSup(motherRequirement.getStatisticalITSup());
                aRequirement.setStatisticalValueInf(motherRequirement.getStatisticalValueInf());
                aRequirement.setStatisticalValueSup(motherRequirement.getStatisticalValueSup());
                break;
            }
        }
        // Setting the reportPath in Calculation Object.
        calculation.getResult().setReportPath(reportPath);
        logger.debug(motherRequirement.toString());
        // returning the Calculation object.
        return this.calculation;
    }

    /**
     * The main method - Unitary test.
     * 
     * @param args the arguments
     * @throws IOException
     * @throws JAXBException
     */
    public static void main(String[] args) throws IOException, JAXBException {

        // Semi Quadratic calculation
        System.out.println("Start Semi Quadratic ...");

        JAXBContext jc = JAXBContext.newInstance(Calculation.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File scenarioFile = new File("C:\\User\\SIPP\\PLM\\GP5\\Interface OASIS\\GL\\Test data\\Base\\Scenario 7\\Flow1_Scenario7B.xml");
        // File scenarioFile = new File("E:\\Flow1_Scenario6B.xml");
        Calculation calculation = null;
        if (scenarioFile.exists()) {
            calculation = (Calculation) unmarshaller.unmarshal(scenarioFile);
        }

        Date executionDate = Calendar.getInstance().getTime();

        SemiQuadStrategyServiceImpl sqssImpl = new SemiQuadStrategyServiceImpl(calculation, executionDate);
        sqssImpl.organizeInput();
        sqssImpl.runCalculation();
        sqssImpl.generateReport();

        System.out.println(sqssImpl.motherRequirement.toString());
        System.out.println("End Semi Quadratic ...");
    }
}
