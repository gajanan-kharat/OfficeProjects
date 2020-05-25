/*
 * Author: U224106
 * Creation: 18 déc. 2014
 */
package com.inetpsa.oaz00.ws.strategy.services.impl;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
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
import com.inetpsa.oaz00.ws.report.services.impl.SpecITReportServiceImpl;
import com.inetpsa.oaz00.ws.report.utils.ReportUtility;
import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.services.SciCalculationService;
import com.inetpsa.oaz00.ws.scilab.services.impl.SpecITSciCalculationServiceImpl;
import com.inetpsa.oaz00.ws.scilab.utils.Constants;
import com.inetpsa.oaz00.ws.scilab.utils.InputOrganizerUtil;
import com.inetpsa.oaz00.ws.scilab.utils.SciObjectInputAdapter;
import com.inetpsa.oaz00.ws.scilab.utils.SpecITOutputAdapter;
import com.inetpsa.oaz00.ws.strategy.services.StrategyService;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.Calculation;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.LawType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.ResultType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelType;

/**
 * The Class SpecITStrategyServiceImpl.
 * 
 * @author U224106
 */
public class SpecITStrategyServiceImpl implements StrategyService {

    /** Half connector properties */
    private static Properties HALF_CONNECTOR_PROPERTIES = Constants.getHalfConnectorProperties();

    /** The execution date. */
    private Date executionDate;

    /** The calculation object as the main input */
    private Calculation calculation;

    /** The Mother list. */
    private List<SciMotherRequirementType> motherList = new ArrayList<SciMotherRequirementType>();

    /** The Contributor list. */
    private List<SciRequirementType> contributorList = new ArrayList<SciRequirementType>();

    /** The Standard mode of TI Specification with 1000 samples */
    private final static int STANDARD_MODE = 0;

    /** Hash set to maintain unique impacted object ids. */
    private HashSet<String> impactedObjects = new HashSet<String>();

    /** The graph path prefix. */
    private String graphPathPrefix;

    /** The number of iterations done. */
    private int nbIter = 0;

    /** The level of error */
    private int errorLevel;

    /** The report path of the request. */
    private String reportPath = "";

    /** The logger. */
    private static Logger logger = Logger.getLogger(SpecITStrategyServiceImpl.class);
    
    /**
	 * The variables exist in the formula
	 */
	private EquationVariables ecVariables = new EquationVariables();

    /**
     * Instantiates a new TI Specification strategy service implementation.
     * 
     * @param calculation the calculation
     */
    public SpecITStrategyServiceImpl(Calculation calculation, Date execDate) {
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
        this.graphPathPrefix = ReportUtility.getRepositoryPath(CalculationType.SPEC_IT, ReportUtility.PICTURE, executionDate)
                + ReportUtility.formateReportDate(executionDate);
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
        // If motherReqiuerementList doesn't exist, report an error and return.
        if (null == pMotherRequirementsList || pMotherRequirementsList.isEmpty()) {
            this.calculation.getResult().setStatus(ResultCodeType.ERRONEOUS_SPEC_IT_OUTPUT.value());
            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("ERRONEOUS_SPEC_IT_OUTPUT"));
            this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
            return;
        }
        // Iterate over the sciMotherRequirementType object(s) to...
        for (RequirementType pMotherRequirement : pMotherRequirementsList) {
            // ...figure out the corresponding transferModel object, and...
            TransferModelType pTransferModel = InputOrganizerUtil.getTransferModel(pMotherRequirement, transferModels);
            // If tranferModel doesn't exist, report an error and return.
            if (null == pTransferModel) {
                this.calculation.getResult().setStatus(ResultCodeType.ERRONEOUS_SPEC_IT_OUTPUT.value());
                this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("ERRONEOUS_SPEC_IT_OUTPUT"));
                this.calculation.getResult().setImpactedObject(pMotherRequirement.getName());
                this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
                pMotherRequirement.setStatus(false);
                return;
            }
            // ...the corresponding contributors for the transferModel object.
            List<RequirementType> pContributorList = InputOrganizerUtil.getPContributorsList(requirementList, pTransferModel);
            // If there are no contributors, report error and return.
            if (null == pContributorList || pContributorList.isEmpty()) {
                this.calculation.getResult().setStatus(ResultCodeType.ERRONEOUS_SPEC_IT_OUTPUT.value());
                this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("ERRONEOUS_SPEC_IT_OUTPUT"));
                // this.calculation.getResult().setImpactedObject(pTransferModel.getId());
                this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
                pTransferModel.setStatus(false);
                return;
            }
            // Now instantiate the actual sciMotherRequirementType object and...
            SciMotherRequirementType aSciMotherRequirementType = SciObjectInputAdapter.createScilabMotherRequirementType(pMotherRequirement,
                    pTransferModel, pContributorList);
            // add it to the "motherList" belonging to this instance.
            this.motherList.add(aSciMotherRequirementType);
        }
        // Build the Contributor list by removing duplicate contributors over several mother requirements
        contributorList = SciObjectInputAdapter.createScilabRequirementListFromMotherRequirements(motherList);
    }

    /**
     * Check the provided input. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.strategy.services.StrategyService#checkInput()
     */
    @Override
    public void checkInput() {
        // Status of error detection.
        boolean errorDetected = false;
        // Now get the input requirements list from client (for ex. Enovia).
        List<RequirementType> requirementList = this.calculation.getRequirements().getRequirement();
        // Get the input transfer models list from client (for ex. Enovia).
        List<TransferModelType> transferModels = this.calculation.getTransferModels().getTransferModel();
        // Iterate over motherList and get the mother reqiurement.
        for (SciMotherRequirementType aSciMotherRequirementType : this.motherList) {
            // Getting the formula and the uncertainty from tranfer model list.
            String formula = aSciMotherRequirementType.getTransferModel().getFormula();
            String reportFormula = aSciMotherRequirementType.getTransferModel().getReportFormula();
            // Check the formula and reportFormula, and report error if any.
            try {
                EquationChecker.checkEquation(formula, reportFormula, ecVariables);
            } catch (EquationException ee) {
                logger.warn("Spec IT - Incorrect formula - Formula = " + formula + " - ProjectName = " + calculation.getProjectName()
                        + " - UserId = " + calculation.getUserId(), ee.getCause());

                // Check whether error was detected or not.
                if (!errorDetected && !ee.getErrorStatus().equals(ResultCodeType.INVALID_COMMA_IN_NUMBER.value())) {
                    // Setting the required field in ResultType
                    this.calculation.getResult().setStatus(ee.getErrorStatus());
                    this.calculation.getResult().setDescription(ee.getErrorDescription());
                    this.calculation.getResult().setFormula(reportFormula);
                    // this.calculation.getResult().setImpactedObject(aSciMotherRequirementType.getTransferModel().getId());
                    this.calculation.getResult().setPosition(ee.getPosition());
                    this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
                    // Set the status of tranferModel from transferModelsList to false.
                    for (TransferModelType transferModelType : transferModels) {
                        if (transferModelType.getId().equals(aSciMotherRequirementType.getTransferModel().getId())) {
                            transferModelType.setStatus(false);
                        }
                    }
                    // True, indicates that error was detected.
                    errorDetected = true;
                }
            }
            // Check the contributors reference.Note that errorDetected is also passed as a parameter.
            errorDetected = ParametersChecker.checkContributors(aSciMotherRequirementType, this.calculation, errorDetected);
            // Getting the unCertainty from the transferModel.
            Double unCertainty = aSciMotherRequirementType.getTransferModel().getUncertainty();
            // Check the unCertainty and report error, if any.
            if (unCertainty == null || unCertainty.equals(Constants.EMPTY_STRING)) {
                // Check whether error was detected or not.
                if (!errorDetected) {
                    // Setting the required field in ResultType
                    this.calculation.getResult().setStatus(ResultCodeType.INVALID_SPEC_IT_INPUT.value());
                    this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_SPEC_IT_INPUT"));
                    // this.calculation.getResult().setImpactedObject(aSciMotherRequirementType.getTransferModel().getId());
                    this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
                    // Set the status of tranferModel from transferModelsList to false.
                    for (TransferModelType transferModelType : transferModels) {
                        if (transferModelType.getId().equals(aSciMotherRequirementType.getTransferModel().getId())) {
                            transferModelType.setStatus(false);
                        }
                    }
                    // True, indicates that error was detected.
                    errorDetected = true;
                } else {
                    // If we are here then it indicates that error is already detected so we are just appending the impacted object to the
                    // existing
                    // one.
                    // this.calculation.getResult().setImpactedObject(this.calculation.getResult().getImpactedObject() + "," +
                    // aSciMotherRequirementType.getTransferModel().getId());
                }
            }
            // Getting lowerValue, upperValue,TNC and CAP from the motherRequirement.
            Double lowerValue = aSciMotherRequirementType.getValueInf();
            Double uppperValue = aSciMotherRequirementType.getValueSup();
            Double tnc = aSciMotherRequirementType.getTnc();
            Double cap = aSciMotherRequirementType.getCap();
            Double offCentering = aSciMotherRequirementType.getCenteringMax();
            // Check for at least one of three attributes must be not null and not empty.
            if (tnc == null && cap == null && offCentering == null) {
                // Setting the error status, description, impactedObject and level.
                errorDetected = specITErrorDescription(errorDetected, aSciMotherRequirementType, requirementList);
            }
            // Check whether CAP and TNC value is less than zero or not.
            if ((tnc != null || cap != null)) {
                if (tnc != null) {
                    if (tnc < 0) {
                        errorDetected = specITErrorDescription(errorDetected, aSciMotherRequirementType, requirementList);
                    }
                }
                if (cap != null) {
                    if (cap < 0) {
                        errorDetected = specITErrorDescription(errorDetected, aSciMotherRequirementType, requirementList);
                    }
                }
            }

            // Check the lowerValue, upperTNC, CAP and offCentering and report error, if any.
            if (lowerValue == null || lowerValue.equals(Constants.EMPTY_STRING) || uppperValue == null || uppperValue.equals(Constants.EMPTY_STRING)) {
                errorDetected = specITErrorDescription(errorDetected, aSciMotherRequirementType, requirementList);
            }
            // Getting the contributors from motherReqiurement.
            for (SciRequirementType sciContributor : aSciMotherRequirementType.getContributorList()) {
                Double param1 = sciContributor.getParamLaw1();
                Double param2 = sciContributor.getParamLaw2();
                LawType law = sciContributor.getLaw();
                // Below we perform distribution checks.
                try {
                    ParametersChecker.checkLawParameters(param1, param2, law);
                } catch (EquationException ee) {
                    logger.warn("Spec IT - Incorrect parameters - Distribution = " + law + " - Parm 1 = " + param1 + " - Param 2 = " + param2
                            + " - ProjectName = " + calculation.getProjectName() + " - UserId = " + calculation.getUserId(), ee.getCause());

                    // Check whether error was detected or not.
                    if (!errorDetected) {
                        // Setting the required field in ResultType
                        this.calculation.getResult().setStatus(ee.getErrorStatus());
                        this.calculation.getResult().setDescription(ee.getErrorDescription());
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
                        // existing one.
                        this.calculation.getResult().setImpactedObject(
                                this.calculation.getResult().getImpactedObject() + "," + sciContributor.getName());
                    }

                }
                // And finally, the mandatory fields check.
                Double lowValue = sciContributor.getValueInf();
                Double upValue = sciContributor.getValueSup();
                if (lowValue == null || lowValue.equals(Constants.EMPTY_STRING) || upValue == null || upValue.equals(Constants.EMPTY_STRING)) {
                    // Check whether error was detected or not.
                    if (!errorDetected) {
                        // Setting the required field in ResultType
                        this.calculation.getResult().setStatus(ResultCodeType.INVALID_SPEC_IT_INPUT.value());
                        this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_SPEC_IT_INPUT"));
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
                        // existing one.
                        this.calculation.getResult().setImpactedObject(
                                this.calculation.getResult().getImpactedObject() + "," + sciContributor.getName());
                        for (RequirementType requirement : requirementList) {
                            if (requirement.getId().equals(sciContributor.getId())) {
                                requirement.setStatus(false);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Setting the error status, description, impactedObject and level.
     * 
     * @param errorDetected
     * @param aSciMotherRequirementType
     * @param requirementList
     */
    public boolean specITErrorDescription(boolean errorDetected, SciMotherRequirementType aSciMotherRequirementType,
            List<RequirementType> requirementList) {
        // Check whether error was detected or not.
        if (!errorDetected) {
            // Setting the required field in ResultType
            this.calculation.getResult().setStatus(ResultCodeType.INVALID_SPEC_IT_INPUT.value());
            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_SPEC_IT_INPUT"));
            this.calculation.getResult().setImpactedObject(aSciMotherRequirementType.getName());
            this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
            // Set the status of requirement from requirementList to false.
            for (RequirementType requirement : requirementList) {
                if (requirement.getId().equals(aSciMotherRequirementType.getId())) {
                    requirement.setStatus(false);
                }
            }
            errorDetected = true;
            // True, indicates that error was detected.
        } else {
            // If we are here then it indicates that error is already detected so we are just appending the impacted object to the
            // existing
            // one.
            this.calculation.getResult().setImpactedObject(
                    this.calculation.getResult().getImpactedObject() + "," + aSciMotherRequirementType.getName());
        }
        return errorDetected;
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

        SciCalculationService calculationService = new SpecITSciCalculationServiceImpl(motherList, contributorList, graphPathPrefix);

        collectResults(STANDARD_MODE, calculationService, graphPathPrefix);

    }

    /**
     * Collect results.
     * 
     * @param mode the mode
     * @param calculationService the calculation service
     * @param graphPathPrefix the graph path prefix
     */
    private void collectResults(int mode, SciCalculationService calculationService, String graphPathPrefix) {
        calculationService.computeInScilab();
        errorLevel = ((SpecITSciCalculationServiceImpl) calculationService).getSpecITErrorLevel();
        if (errorLevel == 1 || errorLevel == 2) {
            if (errorLevel == 2 && contributorList.size() < 10 && mode == STANDARD_MODE) {
                ((SpecITSciCalculationServiceImpl) calculationService).startExceptionalMode();
                collectResults(++mode, calculationService, graphPathPrefix);
            } else {
                logger.warn("Spec IT - Unreachable objectives - ErroLevel = " + errorLevel + " - ProjectName = " + calculation.getProjectName()
                        + " - UserId = " + calculation.getUserId());

                calculation.getResult().setStatus(ResultCodeType.SPEC_IT_UNREACHABLE.value());
                calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("SPEC_IT_UNREACHABLE"));
                calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
            }
        } else if (errorLevel == 3) {
            logger.warn("Spec IT - Objectives not improvable - ErroLevel = " + errorLevel + " - ProjectName = " + calculation.getProjectName()
                    + " - UserId = " + calculation.getUserId());

            calculation.getResult().setStatus(ResultCodeType.SPEC_IT_NON_IMPROVABLE.value());
            calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("SPEC_IT_NON_IMPROVABLE"));
            calculation.getResult().setLevel(CriticalityLevels.WARNING.criticalityValue());
        } else if (errorLevel == -1) {
            logger.error("Spec IT - Unexpected error during Scilab calculation - ErroLevel = " + errorLevel + " - ProjectName = "
                    + calculation.getProjectName() + " - UserId = " + calculation.getUserId());

            calculation.getResult().setStatus(ResultCodeType.ERRONEOUS_SPEC_IT_OUTPUT.value());
            calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("ERRONEOUS_SPEC_IT_OUTPUT"));
            calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
        } else {
            String[] prestNames = ((SpecITSciCalculationServiceImpl) calculationService).getSpecITPrestationsNames();
            double[] prestRes = ((SpecITSciCalculationServiceImpl) calculationService).getSpecITPrestationsResults();
            String[] contribNames = ((SpecITSciCalculationServiceImpl) calculationService).getSpecITContributorsNames();
            double[] contribRes = ((SpecITSciCalculationServiceImpl) calculationService).getSpecITContributorsResults();
            nbIter = SpecITOutputAdapter.createSpecITScilabResult(motherList, contributorList, prestRes, contribRes, prestNames, contribNames,
                    graphPathPrefix);
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

        if (errorLevel == 0) {
            ReportService reportService = new SpecITReportServiceImpl(motherList, graphPathPrefix, nbIter, executionDate, calculation.getUserId());
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
            // Now appending the project name in the reportPath.
            reportPath = reportPath + Constants.getHalfConnectorServerConfigProperties().getProperty("webProjectName");
            // Finally, appending of the file name for URL.
            reportPath = reportPath + Constants.URL_SEPERATOR_CHAR + "reports?report=" + fileName;
        }
    }

    /**
     * Checks the output coming from Scilab. {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.strategy.services.StrategyService#checkOutput()
     */
    @Override
    public void checkOutput() {
        if (errorLevel == 0) {
            // Status of error detection
            boolean errorDetected = false;
            // Iterate over motherList and get the mother reqiurement.
            for (SciMotherRequirementType aSciMotherRequirementType : this.motherList) {
                // Getting the mean from motherRequirement list.
                // Check whether the mean is isfinite or not.
                // if (null == aSciMotherRequirementType.getMean() || aSciMotherRequirementType.getMean().isInfinite()) {
                // // setting the mean value to null.
                // aSciMotherRequirementType.setMean(null);
                // // Setting the status, description, ImpactedObject and level.
                // errorDetected = setOutputErrorAttributes(aSciMotherRequirementType, errorDetected);
                // }
                // Getting the standardDeviation from motherRequirement list.
                // Check whether the standardDeviation is isfinite or not.
                // if (null == aSciMotherRequirementType.getStandardDeviation() || aSciMotherRequirementType.getStandardDeviation().isInfinite()) {
                // // setting the standardDeviation value to null.
                // aSciMotherRequirementType.setStandardDeviation(null);
                // // Setting the status, description, ImpactedObject and level.
                // errorDetected = setOutputErrorAttributes(aSciMotherRequirementType, errorDetected);
                // }
                // Getting the cap from MotherRequirement.
                // Check whether the cap is isfinite or not.
                if (!(aSciMotherRequirementType.getCap() == null)) {
                    if (aSciMotherRequirementType.getCap().isInfinite()) {
                        // setting the cap value to null.
                        aSciMotherRequirementType.setCap(null);
                        // Setting the status, description, ImpactedObject and level.
                        errorDetected = setOutputErrorAttributes(aSciMotherRequirementType, errorDetected);
                    }
                }
                // Getting the cpk from MotherRequirement.
                // Check whether the cpk is isfinite or not.
                // if (null == aSciMotherRequirementType.getCpk() || aSciMotherRequirementType.getCpk().isInfinite()) {
                // // setting the cpk value to null.
                // aSciMotherRequirementType.setCpk(null);
                // // Setting the status, description, ImpactedObject and level.
                // errorDetected = setOutputErrorAttributes(aSciMotherRequirementType, errorDetected);
                // }
                // Getting the tnc from MotherRequirement.
                // Check whether the tnc is isfinite or not.
                if (!(aSciMotherRequirementType.getTnc() == null)) {
                    if (aSciMotherRequirementType.getTnc().isInfinite()) {
                        // setting the tnc value to null.
                        aSciMotherRequirementType.setTnc(null);
                        // Setting the status, description, ImpactedObject and level.
                        errorDetected = setOutputErrorAttributes(aSciMotherRequirementType, errorDetected);
                    }
                }
                // Getting the centeringMax from MotherRequirement.
                // Check whether the centeringMax is isfinite or not.
                // if (null == aSciMotherRequirementType.getCenteringMax() || aSciMotherRequirementType.getCenteringMax().isInfinite()) {
                // // setting the centeringMax value to null.
                // aSciMotherRequirementType.setCenteringMax(null);
                // // Setting the status, description, ImpactedObject and level.
                // errorDetected = setOutputErrorAttributes(aSciMotherRequirementType, errorDetected);
                // }
                // Getting the contributors from motherReqiurement.
                for (SciRequirementType sciContributor : aSciMotherRequirementType.getContributorList()) {
                    // Getting the lowerValue from contributors list.
                    // Check whether the lowerValue is isfinite or not.
                    if (sciContributor.getValueInf().isInfinite()) {
                        // setting the lowerValue value to null.
                        sciContributor.setValueInf(null);
                        if (!errorDetected) {
                            // Setting the status, description, ImpactedObject and level.
                            this.calculation.getResult().setStatus(ResultCodeType.INFINITE_SPEC_IT_OUTPUT.value());
                            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INFINITE_SPEC_IT_OUTPUT"));
                            this.calculation.getResult().setImpactedObject(sciContributor.getName());
                            this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
                            // Set the status of requirement from requirementList to false.
                            for (RequirementType requirement : this.calculation.getRequirements().getRequirement()) {
                                if (requirement.getId().equals(sciContributor.getId())) {
                                    requirement.setStatus(false);
                                }
                            }
                            // True, indicates that error was detected.
                            errorDetected = true;
                        } else
                            // If we are here then it indicates that error is already detected so we are just appending the impacted object to the
                            // existing one.
                            this.calculation.getResult().setImpactedObject(
                                    this.calculation.getResult().getImpactedObject() + "," + sciContributor.getName());
                    }
                    // Getting the upperValue from contributors list.
                    // Check whether the upperValue is isfinite or not.
                    if (sciContributor.getValueSup().isInfinite()) {
                        // setting the upperValue value to null.
                        sciContributor.setValueSup(null);
                        // Check whether error was detected or not.
                        if (!errorDetected) {
                            // Setting the status, description, ImpactedObject and level.
                            this.calculation.getResult().setStatus(ResultCodeType.INFINITE_SPEC_IT_OUTPUT.value());
                            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INFINITE_SPEC_IT_OUTPUT"));
                            this.calculation.getResult().setImpactedObject(sciContributor.getName());
                            this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
                            // Set the status of requirement from requirementList to false.
                            for (RequirementType requirement : this.calculation.getRequirements().getRequirement()) {
                                if (requirement.getId().equals(sciContributor.getId())) {
                                    requirement.setStatus(false);
                                }
                            }
                            // True, indicates that error was detected.
                            errorDetected = true;
                        } else {
                            // If we are here then it indicates that error is already detected so we are just appending the impacted object to the
                            // existing one.
                            this.calculation.getResult().setImpactedObject(
                                    this.calculation.getResult().getImpactedObject() + "," + sciContributor.getName());
                        }
                    }
                }
            }
            // Check whether the calculation is performed successfully or not.
            if (!errorDetected) {
                // Status of tnc detection.
                boolean tncStatusDetected = false;
                // Getting the RequirementType from the list of motherRequirement.
                for (SciMotherRequirementType aSciMotherRequirementType : this.motherList) {
                    // Getting the RequirementType from the requirement list provided.
                    for (RequirementType requirement : this.calculation.getRequirements().getRequirement()) {
                        // Check the id of RequirementType and SciMotherRequirementType. If equal then...
                        if (requirement.getId().equalsIgnoreCase(aSciMotherRequirementType.getId())) {
                            // Getting the original tnc value from requirement list and check the value whether it is less than 100 or not.
                            if (!(requirement.getTnc() == null)) {
                                if (requirement.getTnc() < 100) {
                                    if (!tncStatusDetected) {
                                        // A warning message will be sent to Enovia to inform end-user that he may confuse TNC unit with percentage.
                                        this.calculation.getResult().setStatus(ResultCodeType.SPEC_IT_TNC_INPUT.value());
                                        this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("SPEC_IT_TNC_INPUT"));
                                        this.calculation.getResult().setImpactedObject(aSciMotherRequirementType.getName());
                                        this.calculation.getResult().setLevel(CriticalityLevels.INFORMATION.criticalityValue());
                                        tncStatusDetected = true;
                                    } else {
                                        this.calculation.getResult().setImpactedObject(
                                                this.calculation.getResult().getImpactedObject() + "," + aSciMotherRequirementType.getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // Check the status of calculation so as to ensure that the calculation is performed successfully without any error.
        if (this.calculation.getResult().getStatus().longValue() == ResultCodeType.FORMULA_OK.value().longValue()) {
            // Setting the description for SpecIT.
            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("SPEC_IT_COMPLETE"));
            this.calculation.getResult().setFormula(null);
            this.calculation.getResult().setImpactedObject(null);
            this.calculation.getResult().setPosition(null);
        }
    }

    /**
     * Setting the status, description, impactedObject and level of ResultType.
     * 
     * @param aSciMotherRequirementType takes the SciMotherRequirementType as an argument.
     * @param errorDetected the status of error detection.
     * @return boolean the status of error detection.
     */
    private boolean setOutputErrorAttributes(SciMotherRequirementType aSciMotherRequirementType, boolean errorDetected) {
        // Check whether error was detected or not.
        if (!errorDetected) {
            this.calculation.getResult().setStatus(ResultCodeType.INFINITE_SPEC_IT_OUTPUT.value());
            this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INFINITE_SPEC_IT_OUTPUT"));
            this.calculation.getResult().setImpactedObject(aSciMotherRequirementType.getName());
            impactedObjects.add(aSciMotherRequirementType.getId());
            this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
            errorDetected = true;
            // True, indicates that error was detected.
        } else {
            // If we are here then it indicates that error is already detected and now we are checking whether the id is present in the
            // impactedObject hash set.
            if (!impactedObjects.contains(aSciMotherRequirementType.getId())) {
                // If not present then append the impactedObject.
                this.calculation.getResult().setImpactedObject(
                        this.calculation.getResult().getImpactedObject() + "," + aSciMotherRequirementType.getName());
                // add the id in the impactedObject hash set.
                impactedObjects.add(aSciMotherRequirementType.getId());
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
        // Getting the mother requirement provided.
        for (SciMotherRequirementType motherRequirement : this.motherList) {
            // Set the topmost mother requirement as the resultant object into the calculation object.
            for (RequirementType aRequirement : requirementsFromCalculation) {
                // The check after && is double-reinforcement.
                if (aRequirement.getParentId().equalsIgnoreCase(aRequirement.getId())
                        && aRequirement.getId().equalsIgnoreCase(motherRequirement.getId())) {
                    // Setting the Cap, Cpk, Tnc and centeringMax values of the OriginalMotherRequirement.
                    aRequirement.setCap(motherRequirement.getCap());
                    aRequirement.setCpk(motherRequirement.getCpk());
                    aRequirement.setTnc(motherRequirement.getTnc());
                    aRequirement.setCenteringMax(motherRequirement.getCenteringMax());
                } else {
                    // Set the lowerValue and upperValue of contributors.
                    for (SciRequirementType sciContributor : motherRequirement.getContributorList()) {
                        if (aRequirement.getId().equalsIgnoreCase(sciContributor.getId())) {
                            aRequirement.setValueInf(sciContributor.getValueInf());
                            aRequirement.setValueSup(sciContributor.getValueSup());
                        }
                    }
                }
            }
            logger.debug(motherRequirement.toString());
        }
        // Setting the reportPath in Calculation Object.
        if (errorLevel == 0)
            calculation.getResult().setReportPath(reportPath);
        // returning the Calculation Object.
        return this.calculation;
    }

    /**
     * The main method - Unitary test.
     * 
     * @param args the arguments
     * @throws JAXBException
     * @throws IOException
     */
    public static void main(String[] args) throws JAXBException, IOException {

        // TI Specification calculation
        System.out.println("Start TI Specification calculation  ...");

        JAXBContext jc = JAXBContext.newInstance(Calculation.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File scenarioFile = new File("C:\\User\\SIPP\\PLM\\GP5\\Interface OASIS\\GL\\Test data\\Base\\Scenario 5\\Flow1_Scenario5B1.xml");
        Calculation calculation = null;
        if (scenarioFile.exists()) {
            calculation = (Calculation) unmarshaller.unmarshal(scenarioFile);
        }

        StrategyService strategyService = new SpecITStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

        strategyService.organizeInput();
        strategyService.checkInput();
        strategyService.runCalculation();
        strategyService.generateReport();
        strategyService.checkOutput();

        strategyService.prepareOutput();

        System.out.println("End TI Specification calculation ...");
    }
}
