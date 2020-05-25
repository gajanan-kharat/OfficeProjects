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
import java.util.HashMap;
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
import com.inetpsa.oaz00.ws.report.services.impl.MonteCarloReportServiceImpl;
import com.inetpsa.oaz00.ws.report.utils.ReportUtility;
import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciTransferModelType;
import com.inetpsa.oaz00.ws.scilab.services.SciCalculationService;
import com.inetpsa.oaz00.ws.scilab.services.impl.MonteCarloSciCalculationServiceImpl;
import com.inetpsa.oaz00.ws.scilab.utils.Constants;
import com.inetpsa.oaz00.ws.scilab.utils.InputOrganizerUtil;
import com.inetpsa.oaz00.ws.scilab.utils.MonteCarloOutputAdapter;
import com.inetpsa.oaz00.ws.strategy.services.StrategyService;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.Calculation;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.LawType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.ResultType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelType;

/**
 * The Class MonteCarloStrategyServiceImpl.
 * 
 * @author U224106
 */
public class MonteCarloStrategyServiceImpl implements StrategyService {

	/** Half connector properties */
	private static Properties HALF_CONNECTOR_PROPERTIES = Constants.getHalfConnectorProperties();

	/** The height of the input Monte Carlo requirementType objects' tree. */
	private int treeHeight = 0;

	/** The execution date. */
	private Date executionDate;

	/** The calculation object as the main input */
	private Calculation calculation;

	/** The Mother requirement. */
	private SciMotherRequirementType motherRequirement;

	/** Map for maintaining levels and corresponding SciMotherRequirementType objects */
	private HashMap<Integer, List<SciMotherRequirementType>> levelsMap = new HashMap<Integer, List<SciMotherRequirementType>>();

	/** List to maintain all of the leaf contributors. */
	private HashSet<SciRequirementType> lowestContributors = new HashSet<SciRequirementType>();

	/** HashSet for maintaining the status of sciMotherRequirement, whether already processed or not. */
	private HashSet<String> motherRqmtProcessingStatus = new HashSet<String>();

	/**
	 * In case of a multi-level Monte Carlo simulation, if an intermediate level contributor has a valid distribution, this contributor is assumed as
	 * a lowest contributor and all its contributors have to be ignored from the check and from the calculation. In such a case, a warning message
	 * will be sent if calculation is completed with success to inform end-user of the limitation done on its data set.
	 **/
	private boolean isMonteCarloLimitedCase = false;

	/** Return status of recursive organizeInputLevels method. */
	private boolean organizeInputLevelsReturnStatus = true;

	/** Hash set to maintain unique impacted object ids. */
	private HashSet<String> impactedObjects = new HashSet<String>();

	/** The report path of the request. */
	private String reportPath = "";

	/** The logger. */
	private static Logger logger = Logger.getLogger(MonteCarloStrategyServiceImpl.class);
	
	/**
	 * The variables exist in the formula
	 */
	private EquationVariables ecVariables = new EquationVariables();

	/**
	 * Instantiates a new Monte Carlo strategy service implementation.
	 * 
	 * @param calculation
	 *            the calculation object.
	 * @param execDate
	 *            the execution date.
	 */
	public MonteCarloStrategyServiceImpl(Calculation calculation, Date execDate) {
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
		// Filter out from the requirements list, the sciMotherRequirementType object(s).
		List<RequirementType> pMotherRequirementsList = InputOrganizerUtil.getPMotherRequirementsList(requirementList);
		// If motherReqiuerementList doesn't exist, report an error and return.
		if (null == pMotherRequirementsList || pMotherRequirementsList.isEmpty()) {
			this.calculation.getResult().setStatus(ResultCodeType.ERRONEOUS_MONTE_CARLO_OUTPUT.value());
			this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("ERRONEOUS_MONTE_CARLO_OUTPUT"));
			// We are deliberately setting the first requirement's id into impacted object so as to ensure proper comma seperation in the for loop
			// below.
			this.calculation.getResult().setImpactedObject(requirementList.get(0).getName());
			requirementList.get(0).setStatus(false);
			// We need to set all of the requirementType's status to false since none of them qualifies to be motherRequirement.
			for (int i = 1; i < requirementList.size(); i++) {
				this.calculation.getResult().setImpactedObject(", " + requirementList.get(i).getName());
				requirementList.get(i).setStatus(false);
			}
			this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
			return;
		}
		// Since this is MonteCarlo, we will have only 1 Root SciMotherRequirementType object. Construct it using get(0)...
		try {
			this.motherRequirement = new SciMotherRequirementType(pMotherRequirementsList.get(0), null, new ArrayList<SciRequirementType>());
			// However, we could have multiple levels of motherRequirementType objects. Construct them all recursively:
			organizeInputLevels(this.motherRequirement);
			if (!organizeInputLevelsReturnStatus) {
				return;
			}
			logger.debug("");
			logger.debug("Total number of leaf contributors is ------> " + lowestContributors.size());
			logger.debug("");
		} catch (SecurityException e) {
			logger.error("Exception with object duplication ", e);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.error("Exception with object duplication ", e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error("Exception with object duplication ", e);
			e.printStackTrace();
		}
		// Get the height (i.e. depth) of tree.
		this.treeHeight = motherRequirement.getDepth();
		// We now populate the levelsMap; for that we put motherRequirement into a list and pass it on to setter.
		List<SciMotherRequirementType> itemsList = new ArrayList<SciMotherRequirementType>();
		itemsList.add(motherRequirement);
		// Call the setter.
		this.setLevelsMap(1, itemsList);
	}

	/**
	 * Method to recursively construct a multi-level Monte-Carlo requirements tree.
	 * 
	 * @param motherRequirement
	 *            the SciMotherRequirement
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void organizeInputLevels(SciMotherRequirementType motherRequirement) throws SecurityException, IllegalArgumentException, IllegalAccessException {
		// Now get the input requirements list from client (for ex. Enovia).
		List<RequirementType> requirementList = this.calculation.getRequirements().getRequirement();
		// Get the input transfer models list from client (for ex. Enovia).
		List<TransferModelType> transferModels = this.calculation.getTransferModels().getTransferModel();
		// Figure out the corresponding transferModel object of this motherRequirement.
		TransferModelType mothersTransferModel = InputOrganizerUtil.getTransferModel(motherRequirement, transferModels);
		// If tranferModel doesn't exist, report an error and return.
		if (null == mothersTransferModel) {
			this.calculation.getResult().setStatus(ResultCodeType.ERRONEOUS_MONTE_CARLO_OUTPUT.value());
			this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("ERRONEOUS_MONTE_CARLO_OUTPUT"));
			this.calculation.getResult().setImpactedObject(motherRequirement.getName());
			this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
			motherRequirement.setStatus(false);
			organizeInputLevelsReturnStatus = false;
			return;
		}
		// If we found one, wrap it in SciTransferModelType class and set it!
		motherRequirement.setTransferModel(new SciTransferModelType(mothersTransferModel));
		// Declare a temporary list to filter and populate contributors belonging to this motherRequirement.
		List<RequirementType> tempPContributorList = new ArrayList<RequirementType>();
		// Now iterate over all the contributors that came from client (for ex. Enovia)
		for (RequirementType contributor : requirementList) {
			// And try identifying the contributors belonging to this motherRequirement.
			// The parentId of a contributor should be equal to the id of a transferModel in order for the contributor to be belonging to this
			// MotherRequirement.
			if (contributor.getParentId().equalsIgnoreCase(mothersTransferModel.getId())) {
				tempPContributorList.add(contributor);
			}
		}
		// Now we iterate over the motherRequirement's contributors to subsequently figure out the intermediate-motherRequirements and the
		// leaf-contributors.
		for (RequirementType childContributor : tempPContributorList) {
			// Try to figure out if this childContributor has a transferModel belonging to it.
			TransferModelType childsTransferModel = InputOrganizerUtil.getTransferModel(childContributor, transferModels);
			// If we found one, then it means this childContributor is actually an "intermediate-motherRequirement".
			if (null != childsTransferModel) {
				// We now check if law type is null so as to ensure that this intermediate contributor doesn't have it's own distribution.
				// Otherwise (i.e. the else part below) we will have to treat it as a lowest contributor and skip any contributors further below it.
				if (childContributor.getLaw() == null) {
					// Construct the "intermediate-motherRequirement". The transferModel is left null on purpose!
					SciMotherRequirementType intermediateSciMotherRequirementType = new SciMotherRequirementType(childContributor, null, new ArrayList<SciRequirementType>());
					// We now add it to motherRequirement.
					motherRequirement.getContributorList().add(intermediateSciMotherRequirementType);
					// Recursion - since there is a possibility that this "intermediate-motherRequirement" could further have
					// "intermediate-motherRequirements".
					organizeInputLevels(intermediateSciMotherRequirementType);
				} else {
					// Else we set the following flag to true and skip all contributors below it.
					isMonteCarloLimitedCase = true;
					// We also construct the so-called leaf contributor.
					SciRequirementType sciChildContributor = new SciRequirementType(childContributor);
					// And add it to motherRequirement.
					motherRequirement.getContributorList().add(sciChildContributor);
					// Also add it to lowestContributors list.
					lowestContributors.add(sciChildContributor);
					logger.debug("");
					logger.debug("Found Monte Carlo limited case. Skipping contributors below the intermediate mother requirement ----> \"" + sciChildContributor.getTitle() + "\" (" + sciChildContributor.getName() + ")");
				}
			} else { // If we are here then it means childsTransferModel is null.
						// We thus have found a leaf contributor. Construct it.
				SciRequirementType sciChildContributor = new SciRequirementType(childContributor);
				// And add it to motherRequirement.
				motherRequirement.getContributorList().add(sciChildContributor);
				// Also add it to lowestContributors list.
				lowestContributors.add(sciChildContributor);
			}
		}
	}

	/**
	 * Method to populate the level's hash map so as to store all of the mother requirements at each level.
	 * 
	 * @param level
	 *            the level of a tree.
	 * @param smrtList
	 *            the list of SciMotherRequirement.
	 */
	private void setLevelsMap(int level, List<SciMotherRequirementType> smrtList) {
		// First thing first, put the list into map.
		this.levelsMap.put(level, smrtList);
		level++;
		// Now declare a new list for this recursive cycle.
		List<SciMotherRequirementType> levelItems = new ArrayList<SciMotherRequirementType>();
		// Iterate over one or more (intermediate) SciMotherRequirementType.
		for (SciMotherRequirementType smrt : smrtList) {
			// If there are no contributors, report error and return.
			if (null == smrt.getContributorList() || smrt.getContributorList().isEmpty()) {
				this.calculation.getResult().setStatus(ResultCodeType.ERRONEOUS_MONTE_CARLO_OUTPUT.value());
				this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("ERRONEOUS_MONTE_CARLO_OUTPUT"));
				for (TransferModelType transferModel : this.calculation.getTransferModels().getTransferModel()) {
					if (transferModel.getParentId().equalsIgnoreCase(smrt.getId())) {
						transferModel.setStatus(false);
						// this.calculation.getResult().setImpactedObject(transferModel.getId());
					}
				}
				this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
				return;
			}
			// Only add the intermediate SciMotherRequirementTypes to the list.
			for (SciRequirementType contributor : smrt.getContributorList()) {
				if (contributor instanceof SciMotherRequirementType) {
					levelItems.add((SciMotherRequirementType) contributor);
				}
			}
		}
		// Put the list into map.
		if (levelItems.size() > 0) {
			this.setLevelsMap(level, levelItems);
		}
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
		// Get the input transfer models list from client (for ex. Enovia).
		List<TransferModelType> transferModels = this.calculation.getTransferModels().getTransferModel();
		// Iterate over each transfer model for checking the formula and reportFormula.

		for (TransferModelType transferModel : transferModels) {
			// Get the formula from transferModel.
			String formula = transferModel.getFormula();
			// Get the reportFormula from transferModel.
			String reportFormula = transferModel.getReportFormula();
			// Check the formula and reportFormula, and report error if any.
			try {
				EquationChecker.checkEquation(formula, reportFormula, ecVariables);
			} catch (EquationException ee) {

				logger.warn("Monte Carlo - Incorrect formula - Formula = " + formula + " - ProjectName = " + calculation.getProjectName() + " - UserId = " + calculation.getUserId(), ee.getCause());
				if(!ee.getErrorStatus().equals(ResultCodeType.INVALID_COMMA_IN_NUMBER.value())) {
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
			// Check the uncertainty and report error, if any.
			if (transferModel.getUncertainty() < 0) {
				// Check whether error was detected or not.
				if (!errorDetected) {
					// Setting the required field in ResultType
					this.calculation.getResult().setStatus(ResultCodeType.INVALID_MONTE_CARLO_INPUT.value());
					this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_MONTE_CARLO_INPUT"));
					// this.calculation.getResult().setImpactedObject(transferModel.getId());
					this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
					// Set the status of tranferModel from transferModelsList to false.
					transferModel.setStatus(false);
					// True, indicates that error was detected.
					errorDetected = true;
				} else {
					// If we are here then it indicates that error is already detected so we are just appending the impacted object to the
					// existing
					// one.
					// this.calculation.getResult().setImpactedObject(this.calculation.getResult().getImpactedObject() + "," +
					// this.motherRequirement.getTransferModel().getId());
				}
			}
		}
		// Check the contributors reference. Note that errorDetected is also passed as a parameter.
		errorDetected = checkContributors(this.motherRequirement, errorDetected);

		// Now we perform the distribution and mandatory fields checks.
		// Iterate over the list of lowerContributors.
		for (SciRequirementType sciContributor : lowestContributors) {
			// Now get the input requirements list from client (for ex. Enovia).
			List<RequirementType> requirementList = this.calculation.getRequirements().getRequirement();
			Double param1 = sciContributor.getParamLaw1();
			Double param2 = sciContributor.getParamLaw2();
			LawType law = sciContributor.getLaw();
			// Below we perform distribution checks.
			try {
				ParametersChecker.checkLawParameters(param1, param2, law);
			} catch (EquationException ee) {
				logger.warn("Monte Carlo - Incorrect parameters - Distribution = " + law + " - Parm 1 = " + param1 + " - Param 2 = " + param2 + " - ProjectName = " + calculation.getProjectName() + " - UserId = " + calculation.getUserId(), ee.getCause());

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
					// existing
					// one.
					this.calculation.getResult().setImpactedObject(this.calculation.getResult().getImpactedObject() + "," + sciContributor.getName());
				}
			}
			// And finally, the mandatory fields check.
			Double lowerValue = sciContributor.getValueInf();
			Double upperValue = sciContributor.getValueSup();
			if (lowerValue == null || lowerValue.equals(Constants.EMPTY_STRING) || upperValue == null || upperValue.equals(Constants.EMPTY_STRING)) {
				// Check whether error was detected or not.
				if (!errorDetected) {
					// Setting the required field in ResultType
					this.calculation.getResult().setStatus(ResultCodeType.INVALID_MONTE_CARLO_INPUT.value());
					this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_MONTE_CARLO_INPUT"));
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
					// If we are here then it indicates that error is already detected so we are just appending the impacted object to the existing
					// one.
					this.calculation.getResult().setImpactedObject(this.calculation.getResult().getImpactedObject() + "," + sciContributor.getName());
					// Setting the status to false.
					for (RequirementType requirement : requirementList) {
						if (requirement.getId().equals(sciContributor.getId())) {
							requirement.setStatus(false);
						}
					}
				}
			}
		}
	}

	/**
	 * Method to recursively check all of the mother-requirements at each level to see if the contributor's references are actually present in the
	 * transfer model's formula.
	 * 
	 * @param motherRequirement
	 *            the SciMotherRequirement.
	 * @param errorDetected
	 *            the status of error detection.
	 * @return boolean, the status of error detection.
	 */
	private boolean checkContributors(SciMotherRequirementType motherRequirement, boolean errorDetected) {
		boolean errorDetectedLocally = ParametersChecker.checkContributors(motherRequirement, this.calculation, errorDetected);
		for (SciRequirementType contributor : motherRequirement.getContributorList()) {
			// If we are here, then this contributor is an intermediate mother requirement.
			if (contributor instanceof SciMotherRequirementType) {
				errorDetectedLocally = checkContributors((SciMotherRequirementType) contributor, errorDetectedLocally);
			}
		}
		return errorDetectedLocally;
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
		if (treeHeight == 1) {
			// Call one level Monte Carlo calculation implementation.
			runOneLevelMonteCarlo(motherRequirement);
		} else {
			// Call multi-level Monte Carlo calculation implementation.
			runMultiLevelMonteCarlo();
		}
	}

	/**
	 * Run one level Monte Carlo calculation.
	 * 
	 * @param pMotherRequirement
	 *            the Mother requirement
	 */
	private void runOneLevelMonteCarlo(SciMotherRequirementType pMotherRequirement) {
		// If the mother requirement is already processed, skip it.
		if (!motherRqmtProcessingStatus.contains(pMotherRequirement.getId())) {
			pMotherRequirement.setPlotPathPrefix(ReportUtility.getRepositoryPath(CalculationType.MONTE_CARLO, ReportUtility.PICTURE, executionDate) + ReportUtility.formateReportDate(executionDate));
			SciCalculationService calculationService = new MonteCarloSciCalculationServiceImpl(pMotherRequirement);
			calculationService.computeInScilab();
			double[] d = calculationService.getScilabData();
			MonteCarloOutputAdapter.createMCScilabResult(pMotherRequirement, d);
			motherRqmtProcessingStatus.add(pMotherRequirement.getId());
		}
	}

	/**
	 * Run Multi level Monte Carlo calculation.
	 */
	private void runMultiLevelMonteCarlo() {
		logger.debug("");
		// Now we iterate over each level starting from bottom to top approach.
		for (int i = treeHeight; i > 0; i--) {
			logger.debug("");
			logger.debug("Processing level " + i + ". Requirements to process ------------------>  ");
			// Get the list of sci mother requirement type objects from the levelsMap.
			List<SciMotherRequirementType> sciMotherRequirementList = levelsMap.get(i);
			if (logger.isDebugEnabled())
				for (SciMotherRequirementType sciMotherRequirementType : sciMotherRequirementList) {
					logger.debug("\"" + sciMotherRequirementType.getTitle() + "\"      ");
				}
			logger.debug("");
			for (SciMotherRequirementType sciMotherRequirementType : sciMotherRequirementList) {
				// We first perform formula and contributors replacement.
				sciMotherRequirementType = performFormulaReplacement(sciMotherRequirementType);
				// And finally call the single level calculation implementation.
				runOneLevelMonteCarlo(sciMotherRequirementType);
			}
		}
	}

	/**
	 * Method to perform formula and contributors replacement per the multi-level Monte Carlo strategy: If the level is not a leaf: a. Compute
	 * absolute formula: Requirement IDs are replaced by child levels’ formulas. b. Run One Level Monte Carlo simulation with this absolute formula.
	 * 
	 * @param sciMotherRequirementType
	 * @return
	 */
	private SciMotherRequirementType performFormulaReplacement(SciMotherRequirementType sciMotherRequirementType) {
		// Hashset to collect the leaf contributors of a given intermediate requirement.
		HashSet<SciRequirementType> leafContributors = new HashSet<SciRequirementType>();
		logger.debug("");
		logger.debug("Original formula of SciMotherRequirement \"" + sciMotherRequirementType.getTitle() + "\" -----> " + sciMotherRequirementType.getTransferModel().getCalculationFormula());
		// Iterate over the intermediate requirement for formula replacements etc.
		for (SciRequirementType lowerContributor : sciMotherRequirementType.getContributorList()) {
			if (lowerContributor instanceof SciMotherRequirementType) {
				String calculationFormula = sciMotherRequirementType.getTransferModel().getCalculationFormula();
				// Perform replacement of contributor name with its actual formula.
				if (calculationFormula.contains(lowerContributor.getCalculationName())) {
					// Parenthesis are added around the actual formula.
					calculationFormula = calculationFormula.replace(lowerContributor.getCalculationName(), " ( " + ((SciMotherRequirementType) lowerContributor).getTransferModel().getCalculationFormula() + " ) ");
					// The formula is set into the sciMotherRequirementType
					sciMotherRequirementType.getTransferModel().setCalculationFormula(calculationFormula);
				}
				// Add the leaf contributors to the hashset.
				leafContributors.addAll(((SciMotherRequirementType) lowerContributor).getContributorList());
			}
		}
		logger.debug("Replaced formula of SciMotherRequirement \"" + sciMotherRequirementType.getTitle() + "\" -----> " + sciMotherRequirementType.getTransferModel().getCalculationFormula());
		logger.debug("");
		// Below we set the leaf contributors to the current sciMotherRequirment type.
		// Thus its previous contributors will be replaced by leaf contributors.
		if (!leafContributors.isEmpty()) {
			// Ensure that if the current (intermediate) sciMotherRequirementType has any leaf contributors, they too get added to the
			// leafContributors hashset.
			for (SciRequirementType lowerContributor : sciMotherRequirementType.getContributorList()) {
				if (!(lowerContributor instanceof SciMotherRequirementType)) {
					leafContributors.add(lowerContributor);
				}
			}
			// Now add all of the leaf contributors collected from all over below this SciMotherRequirementType to it.
			ArrayList<SciRequirementType> leafContributorsList = new ArrayList<SciRequirementType>();
			for (SciRequirementType sciRequirementType : leafContributors) {
				leafContributorsList.add(sciRequirementType);
			}
			sciMotherRequirementType.setContributorList(leafContributorsList);
		}
		return sciMotherRequirementType;
	}

	/**
	 * Generate the report. {@inheritDoc}
	 * 
	 * @throws IOException
	 * @see com.inetpsa.oaz00.ws.strategy.services.StrategyService#generateReport()
	 */
	@Override
	public void generateReport() throws IOException {
		ReportService reportService = new MonteCarloReportServiceImpl(levelsMap, executionDate, calculation.getUserId());
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
		logger.debug(reportPath);
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
		// Getting the mean from MotherRequirement.
		// Check whether the mean is infinite or not.
		if (this.motherRequirement.getMean() == null || this.motherRequirement.getMean().isInfinite()) {
			// setting the mean value to null.
			this.motherRequirement.setMean(null);
			// Setting the status, description, ImpactedObject and level.
			errorDetected = setOutputErrorAttributes(errorDetected);
		}
		// Getting the standardDeviation from MotherRequirement.
		// Check whether the standardDeviation is infinite or not.
		if (this.motherRequirement.getStandardDeviation() == null || this.motherRequirement.getStandardDeviation().isInfinite()) {
			// setting the standardDeviation value to null.
			this.motherRequirement.setStandardDeviation(null);
			// Setting the status, description, ImpactedObject and level.
			errorDetected = setOutputErrorAttributes(errorDetected);
		}
		// Getting the tnc from MotherRequirement.
		// Check whether the tnc is infinite or not.
		if (this.motherRequirement.getTnc() == null || this.motherRequirement.getTnc().isInfinite()) {
			// setting the tnc value to null.
			this.motherRequirement.setTnc(null);
			// Setting the status, description, ImpactedObject and level.
			errorDetected = setOutputErrorAttributes(errorDetected);
		}
		// Getting the lowerValue from MotherRequirement.
		// Check whether the lowerValue is infinite or not.
		if (this.motherRequirement.getValueInf() == null || this.motherRequirement.getValueInf().isInfinite()) {
			// setting the lowerValue value to null.
			this.motherRequirement.setItInf(null);
			// Setting the status, description, ImpactedObject and level.
			errorDetected = setOutputErrorAttributes(errorDetected);
		}
		// Getting the upperValue from MotherRequirement.
		// Check whether the upperValue is infinite or not.
		if (this.motherRequirement.getValueSup() == null || this.motherRequirement.getValueSup().isInfinite()) {
			// setting the upperValue value to null.
			this.motherRequirement.setItSup(null);
			// Setting the status, description, ImpactedObject and level.
			errorDetected = setOutputErrorAttributes(errorDetected);
		}
		// Getting the cap from MotherRequirement.
		// Check whether the cap is infinite or not.
		if (this.motherRequirement.getCap() == null || this.motherRequirement.getCap().isInfinite()) {
			// setting the cap value to null.
			this.motherRequirement.setCap(null);
			// Setting the status, description, ImpactedObject and level.
			errorDetected = setOutputErrorAttributes(errorDetected);
		}
		// Getting the cpk from MotherRequirement.
		// Check whether the cpk is infinite or not.
		if (this.motherRequirement.getCpk() == null || this.motherRequirement.getCpk().isInfinite()) {
			// setting the cpk value to null.
			this.motherRequirement.setCpk(null);
			// Setting the status, description, ImpactedObject and level.
			errorDetected = setOutputErrorAttributes(errorDetected);
		}
		// Getting the centeringMax from MotherRequirement.
		// Check whether the centeringMax is infinite or not.
		if (this.motherRequirement.getCenteringMax() == null || this.motherRequirement.getCenteringMax().isInfinite()) {
			// setting the centeringMax value to null.
			this.motherRequirement.setCenteringMax(null);
			// Setting the status, description, ImpactedObject and level.
			errorDetected = setOutputErrorAttributes(errorDetected);
		}
		// Now we perform the distribution and mandatory fields check.
		for (SciRequirementType sciContributor : lowestContributors) {
			// Getting the lowerCap from contributors list.
			// Check whether the lowerCap is infinite or not.
			if (sciContributor.getCap() == null || sciContributor.getCap().isInfinite()) {
				// setting the lowerCap value to null.
				sciContributor.setCap(null);
				// Setting the status, description, ImpactedObject and level.
				// Check whether error was detected or not.
				if (!errorDetected) {
					this.calculation.getResult().setStatus(ResultCodeType.INFINITE_MONTE_CARLO_OUTPUT.value());
					this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INFINITE_MONTE_CARLO_OUTPUT"));
					this.calculation.getResult().setImpactedObject(sciContributor.getName());
					this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
					// Set the status of requirement from requirementList to false.
					for (RequirementType contributor : this.calculation.getRequirements().getRequirement()) {
						if (sciContributor.getId().equalsIgnoreCase(contributor.getId()))
							contributor.setStatus(false);
					}
					// True indicates that error was detected.
					errorDetected = true;
				} else {
					// If we are here then it indicates that error is already detected so we are just appending the impacted object to the existing
					// one.
					this.calculation.getResult().setImpactedObject(this.calculation.getResult().getImpactedObject() + "," + sciContributor.getName());
				}
			}
			// Getting the lowerCpk from contributors list.
			// Check whether the lowerCpk is infinite or not.
			if (sciContributor.getCpk() == null || sciContributor.getCpk().isInfinite()) {
				// setting the lowerCpk value to null.
				sciContributor.setCpk(null);
				// Check whether error was detected or not.
				if (!errorDetected) {
					// Setting the status, description, ImpactedObject and level.
					this.calculation.getResult().setStatus(ResultCodeType.INFINITE_MONTE_CARLO_OUTPUT.value());
					this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INFINITE_MONTE_CARLO_OUTPUT"));
					this.calculation.getResult().setImpactedObject(sciContributor.getName());
					this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
					// Set the status of requirement from requirementList to false.
					for (RequirementType contributor : this.calculation.getRequirements().getRequirement()) {
						if (sciContributor.getId().equalsIgnoreCase(contributor.getId()))
							contributor.setStatus(false);
					}
					// True indicates that error was detected.
					errorDetected = true;
				} else {
					// If we are here then it indicates that error is already detected so we are just appending the impacted object to the existing
					// one.
					this.calculation.getResult().setImpactedObject(this.calculation.getResult().getImpactedObject() + "," + sciContributor.getName());
				}
			}
		}
		// If error is not detected in the calculation object and the intermediate-motherRequirement has a lawType...
		if (errorDetected == false && isMonteCarloLimitedCase == true) {
			// ...then give this information to the end-user by setting the status, description and level.
			this.calculation.getResult().setStatus(ResultCodeType.MONTE_CARLO_LIMITED.value());
			this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("MONTE_CARLO_LIMITED"));
			this.calculation.getResult().setLevel(CriticalityLevels.WARNING.criticalityValue());
		}
		// Check the status of calculation so as to ensure that the calculation is performed successfully without any error.
		if (this.calculation.getResult().getStatus().longValue() == ResultCodeType.FORMULA_OK.value().longValue()) {
			// Setting the description for MonteCarlo.
			this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("MONTE_CARLO_COMPLETE"));
			this.calculation.getResult().setFormula(null);
			this.calculation.getResult().setImpactedObject(null);
			this.calculation.getResult().setPosition(null);
		}
	}

	/**
	 * Setting the status, description, impactedObject and level of ResultType.
	 * 
	 * @param errorDetected
	 *            the status of error detection.
	 * @return boolean, returning the status of the errorDetected.
	 */
	private boolean setOutputErrorAttributes(boolean errorDetected) {
		// Check whether error was detected or not.
		if (!errorDetected) {
			// Setting the status, description, ImpactedObject and level.
			this.calculation.getResult().setStatus(ResultCodeType.INFINITE_MONTE_CARLO_OUTPUT.value());
			this.calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INFINITE_MONTE_CARLO_OUTPUT"));
			this.calculation.getResult().setImpactedObject(this.motherRequirement.getName());
			impactedObjects.add(this.motherRequirement.getId());
			this.calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
			this.motherRequirement.setStatus(false);
			// True indicates that error was detected.
			errorDetected = true;
		} else {
			// If we are here then it indicates that error is already detected and now we are checking whether the id is present in the
			// impactedObject hash set.
			if (!impactedObjects.contains(this.motherRequirement.getId())) {
				// If not present then append the impactedObject.
				this.calculation.getResult().setImpactedObject(this.calculation.getResult().getImpactedObject() + "," + this.motherRequirement.getName());
				// add the id in the impactedObject hash set.
				impactedObjects.add(this.motherRequirement.getId());
			}
			this.calculation.getResult().setImpactedObject(this.calculation.getResult().getImpactedObject() + "," + this.motherRequirement.getName());
		}
		return errorDetected;
	}

	/**
	 * Prepare the data coming from the Scilab so as to be returned to ENOVIA. {@inheritDoc}
	 * 
	 * @see com.inetpsa.oaz00.ws.strategy.services.StrategyService#prepareOutput()
	 */
	@Override
	public Calculation prepareOutput() {
		List<RequirementType> requirementsFromCalculation = this.calculation.getRequirements().getRequirement();
		// Set the topmost mother requirement as the resultant object into the calculation object.
		for (RequirementType aRequirement : requirementsFromCalculation) {
			// The check after && is double-reinforcement.
			if (aRequirement.getParentId().equalsIgnoreCase(aRequirement.getId()) && aRequirement.getId().equalsIgnoreCase(this.motherRequirement.getId())) {
				// Setting the Mean , StandardDeviation , Cap , Cpk , Tnc , centeringMax , lowerValue and upperValue values of the Original
				// Requirement.
				aRequirement.setMean(motherRequirement.getMean());
				aRequirement.setStandardDeviation(motherRequirement.getStandardDeviation());
				aRequirement.setCap(motherRequirement.getCap());
				aRequirement.setCpk(motherRequirement.getCpk());
				aRequirement.setCenteringMax(motherRequirement.getCenteringMax());
				aRequirement.setTnc(motherRequirement.getTnc());
				aRequirement.setValueInf(motherRequirement.getValueInf());
				aRequirement.setValueSup(motherRequirement.getValueSup());
			} else {
				// Set the Cap and Cpk values of contributors.
				for (SciRequirementType sciContributor : lowestContributors) {
					if (aRequirement.getId().equalsIgnoreCase(sciContributor.getId())) {
						aRequirement.setCap(sciContributor.getCap());
						aRequirement.setCpk(sciContributor.getCpk());
						break;
					}
				}
			}
		}
		// Setting the reportPath in Calculation Object.
		calculation.getResult().setReportPath(reportPath);
		logger.debug(motherRequirement.toString());
		// returning the Calculation Object.
		return this.calculation;
	}

	/**
	 * The main method - Unitary test.
	 * 
	 * @param a
	 *            the arguments
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static void main(String[] a) throws JAXBException, IOException {

		// Monte Carlo calculation
		System.out.println("Start Monte Carlo calculation ...");

		JAXBContext jc = JAXBContext.newInstance(Calculation.class);

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		File scenarioFile = new File("C:\\User\\SIPP\\PLM\\GP5\\Interface OASIS\\GL\\Test data\\Base\\Scenario 4\\Flow1_Scenario4D.xml");
		Calculation calculation = null;
		if (scenarioFile.exists()) {
			calculation = (Calculation) unmarshaller.unmarshal(scenarioFile);
		} else
			System.out.println("File not red");

		StrategyService strategyService = new MonteCarloStrategyServiceImpl(calculation, Calendar.getInstance().getTime());

		strategyService.organizeInput();
		strategyService.checkInput();
		strategyService.runCalculation();
		strategyService.generateReport();
		strategyService.checkOutput();

		strategyService.prepareOutput();

		System.out.println("End Monte Carlo calculation ...");
	}
}
