/*
 * @(#)ParametersChecker.java October 01, 2014
 * CopyRight : The PSA Company
 */
package com.inetpsa.oaz00.ws.checker.formula.services;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inetpsa.oaz00.ws.checker.model.CriticalityLevels;
import com.inetpsa.oaz00.ws.checker.model.ResultCodeType;
import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.utils.Constants;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.Calculation;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.LawType;

/**
 * Utility class for checking parameters, mandatory fields etc.
 * 
 * @author Geometric Ltd.
 * 
 */
public class ParametersChecker {

	/** Half connector properties */
	private static Properties HALF_CONNECTOR_PROPERTIES = Constants.getHalfConnectorProperties();

	/** The Scilab constants checker. */
	private static EquationConstants ecConstants = new EquationConstants();

	/** The Scilab functions checker. */
	private static EquationFunctions ecFunctions = new EquationFunctions();

	/**
	 * Check the contributors reference whether it is not in the list of Transfer model contributor’s.
	 * 
	 * @param motherRequirement
	 *            MotherRequirement from the RequirementList.
	 * 
	 * @param calculation
	 *            Calcution object
	 * 
	 * @return true if present else false.
	 */
	public static boolean checkContributors(SciMotherRequirementType motherRequirement, Calculation calculation, boolean errorDetected) {
		// Hash set to collect all of the valid contributor-names.
		Set<String> contributorNames = new HashSet<String>();
		// Get the contributors name from motherRequirement.
		for (SciRequirementType sciContributor : motherRequirement.getContributorList()) {
			// Populate the hash set.
			contributorNames.add(sciContributor.getName());
		}
		// The calculation formula.
		String calculationFormula = motherRequirement.getTransferModel().getFormula();
		// The report formula.
		String reportFormula = motherRequirement.getTransferModel().getReportFormula();
		// Get the variable-names (i.e. contributor-names) present in the formula.
		Pattern pattern = Pattern.compile("[#][A-Z]+(-)[0-9]+[#]");
		Matcher matcher = pattern.matcher(calculationFormula);
		while (matcher.find()) {
			String var = matcher.group().replace("#", "");
			// Check whether the names is a function or constant...
			if (!ecFunctions.isFunction(var) && !ecConstants.isConstant(var)) {
				// ...and if not, search for it in the hash set to figure out if it is a valid contributor.
				if (!contributorNames.contains(var)) {
					// If the name is not present, it's an error - bad contributor name in the calculation formula!
					if (!errorDetected) {
						calculation.getResult().setStatus(ResultCodeType.INVALID_CONTRIBUTOR_REFERENCE.value());
						calculation.getResult().setDescription(HALF_CONNECTOR_PROPERTIES.getProperty("INVALID_CONTRIBUTOR_REFERENCE"));
						calculation.getResult().setFormula(reportFormula);
						// Now we need to figure out the position of the invalid contributor in the report formula and set the position in the result
						// object.
						calculation.getResult().setPosition(BigInteger.valueOf(getIndexOfErrorFromReportFormula(var, calculationFormula, reportFormula) + 1));
						calculation.getResult().setLevel(CriticalityLevels.ERROR.criticalityValue());
						return true;
					}
				}
			}
		}
		return errorDetected;
	}

	/**
	 * Method to get the corresponding error-index from the report-formula, for the invalid-contributor-reference in the calculation formula.
	 * 
	 * @param var
	 * @param calculationFormula
	 * @param reportFormula
	 * @return
	 */
	private static int getIndexOfErrorFromReportFormula(String var, String calculationFormula, String reportFormula) {
		// First we figure out the index or position of the invalid contributor in the calculation formula.
		int indexOfVar = calculationFormula.indexOf(var);
		// We then get a sub-string of the formula, till the error position, that is.
		String subStringOfCalcFormula = calculationFormula.substring(0, indexOfVar);
		// The following pattern is for tokenizing contributor names like #RK-108#, numbers, parenthesis, special symbols, white-spaces etc.
		Pattern pattern = Pattern.compile("[#][A-Z]+[-][0-9]+[#]|[a-zA-Z_0-9\\.]+|[\\+\\*-/;,\\^]|[\\(\\)]|[\\[\\]]|[\\s]+");
		// Now we tokenize the calculation formula.
		Matcher matcher = pattern.matcher(subStringOfCalcFormula);
		// tokens counter.
		int tokensInSubStringOfCalcFormula = 0;
		while (matcher.find()) {
			tokensInSubStringOfCalcFormula++;
		}
		// And now finally we tokenize the report formula.
		matcher = pattern.matcher(reportFormula);
		// We now loop over only till "tokensInSubStringOfCalcFormula" to get the corresponding sub-string of report formula.
		int tokensCount = 0;
		// We use a string builder since it is cheap compared to StringBuffer which is thread-safe and so bit expensive.
		StringBuilder subStringOfReportFormula = new StringBuilder();
		// we loop till "tokensInSubStringOfCalcFormula"
		while (matcher.find() && tokensCount < tokensInSubStringOfCalcFormula) {
			// Get the matching token.
			String nextGroup = matcher.group();
			// Add it to string builder.
			subStringOfReportFormula.append(nextGroup);
			tokensCount++;
		}
		// Finally simply return the length of the newly built "sub-string" of the report formula!
		return subStringOfReportFormula.length();
	}

	/**
	 * Check law parameters.
	 * 
	 * @param param1
	 *            the first parameter.
	 * @param param2
	 *            the second parameter.
	 * @param lawType
	 *            the law type.
	 * @return BigInteger, If distribution checks perform successfully return FORMULA_OK value as bigInteger.
	 * @throws EquationException
	 *             the exception
	 * @throws IOException
	 */

	public static BigInteger checkLawParameters(Double param1, Double param2, LawType lawType) throws EquationException {

		// No distribution
		if (lawType == null)
			// If null, throw EquationExeception by giving status and description.
			throw new EquationException(ResultCodeType.UNKNOWN_DISTRIBUTION.value(), HALF_CONNECTOR_PROPERTIES.getProperty("UNKNOWN_DISTRIBUTION"));

		// NORMAL STANDARD distribution: Parameter 2 > 0
		if (lawType.equals(LawType.NORMAL_STD)) {
			if (param1 == null || param2 == null) {
				// If null, throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.NORMAL_DISTRIBUTION_INCOMPLETE.value(), HALF_CONNECTOR_PROPERTIES.getProperty("NORMAL_DISTRIBUTION_INCOMPLETE"));
			}
			// Check the second parameter
			if (param2 <= 0)
				// parameter2 is less than 0,throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.NORMAL_DISTRIBUTION_PARAMETERS.value(), HALF_CONNECTOR_PROPERTIES.getProperty("NORMAL_DISTRIBUTION_PARAMETERS"));
		}

		// NORMAL_PLAGE distribution: Parameter 1 < Parameter 2
		if (lawType.equals(LawType.NORMAL_PLAGE)) {
			if (param1 == null || param2 == null) {
				// If null, throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.NORMAL_BY_RANGE_DISTRIBUTION_INCOMPLETE.value(), HALF_CONNECTOR_PROPERTIES.getProperty("NORMAL_BY_RANGE_DISTRIBUTION_INCOMPLETE"));
			}
			if (param1 >= param2)
				// If parameter1 > parameter2,throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.NORMAL_BY_RANGE_DISTRIBUTION_PARAMETERS.value(), HALF_CONNECTOR_PROPERTIES.getProperty("NORMAL_BY_RANGE_DISTRIBUTION_PARAMETERS"));
		}

		// LOG-NORMAL distribution: Parameter 2 > 0
		if (lawType.equals(LawType.LOG)) {
			if (param1 == null || param2 == null) {
				// If null, throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.LOG_NORMAL_DISTRIBUTION_INCOMPLETE.value(), HALF_CONNECTOR_PROPERTIES.getProperty("LOG_NORMAL_DISTRIBUTION_INCOMPLETE"));
			}
			// Check the second parameter
			if (param2 <= 0)
				// parameter2 is less than 0,throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.LOG_NORMAL_DISTRIBUTION_PARAMETERS.value(), HALF_CONNECTOR_PROPERTIES.getProperty("LOG_NORMAL_DISTRIBUTION_PARAMETERS"));
		}

		// UNIFORM distribution: Parameter 1 <= Parameter 2
		if (lawType.equals(LawType.UNIFORM)) {
			if (param1 == null || param2 == null) {
				// If null, throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.UNIFORM_DISTRIBUTION_INCOMPLETE.value(), HALF_CONNECTOR_PROPERTIES.getProperty("UNIFORM_DISTRIBUTION_INCOMPLETE"));
			}
			if (param1 > param2)
				// If parameter1 > parameter2,throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.UNIFORM_DISTRIBUTION_PARAMETERS.value(), HALF_CONNECTOR_PROPERTIES.getProperty("UNIFORM_DISTRIBUTION_PARAMETERS"));
		}

		// BINOMIAL distribution: Parameter 1 is an integer >= 1 and 0 < Parameter 2 < 1
		if (lawType.equals(LawType.BINOMIAL)) {
			if (param1 == null || param2 == null) {
				// If null, throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.BINOMIAL_DISTRIBUTION_INCOMPLETE.value(), HALF_CONNECTOR_PROPERTIES.getProperty("BINOMIAL_DISTRIBUTION_INCOMPLETE"));
			}
			// The regex in the if clause below ensures that param1 is an integer, even though it can have a period and trailing zeros.
			if (!(param1.toString().matches("^[-+]?[\\d]+[\\.][0]+")) || param1.intValue() < 1 || param2 < 0 || param2 > 1)
				// If parameter1 < 1 or parameter2 is less than 0 or parameter2 is greater than 1 ,throw EquationExeception by giving status and
				// description.
				throw new EquationException(ResultCodeType.BINOMIAL_DISTRIBUTION_PARAMETERS.value(), HALF_CONNECTOR_PROPERTIES.getProperty("BINOMIAL_DISTRIBUTION_PARAMETERS"));
		}

		// POISSON distribution
		if (lawType.equals(LawType.POISSON)) {
			if (param1 == null) {
				// If null, throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.POISSON_DISTRIBUTION_INCOMPLETE.value(), HALF_CONNECTOR_PROPERTIES.getProperty("POISSON_DISTRIBUTION_INCOMPLETE"));
			}
			// Check the first parameter
			if (param1 <= 0)
				// parameter1 is less than 0,throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.POISSON_DISTRIBUTION_PARAMETERS.value(), HALF_CONNECTOR_PROPERTIES.getProperty("POISSON_DISTRIBUTION_PARAMETERS"));
		}

		// EXPONENTIAL distribution: it is a special Weibull distribution type
		if (lawType.equals(LawType.EXPO)) {
			if (param1 == null) {
				// If null, throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.EXPONENTIAL_DISTRIBUTION_INCOMPLETE.value(), HALF_CONNECTOR_PROPERTIES.getProperty("EXPONENTIAL_DISTRIBUTION_INCOMPLETE"));
			}
			// Check the first parameter
			if (param1 <= 0)
				// parameter1 is less than 0,throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.EXPONENTIAL_DISTRIBUTION_PARAMETERS.value(), HALF_CONNECTOR_PROPERTIES.getProperty("EXPONENTIAL_DISTRIBUTION_PARAMETERS"));
		}

		// WEIBULL distribution: Parameter 1 & Parameter 2 > 0
		if (lawType.equals(LawType.WEIBULL)) {
			if (param1 == null || param2 == null) {
				// If null, throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.WEIBULL_DISTRIBUTION_INCOMPLETE.value(), HALF_CONNECTOR_PROPERTIES.getProperty("WEIBULL_DISTRIBUTION_INCOMPLETE"));
			}
			if (param1 <= 0 || param2 <= 0)
				// If parameter1 or parameter2 is less than 0,throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.WEIBULL_DISTRIBUTION_PARAMETERS.value(), HALF_CONNECTOR_PROPERTIES.getProperty("WEIBULL_DISTRIBUTION_PARAMETERS"));
		}

		// Rayleigh distribution:
		if (lawType.equals(LawType.RAYLEIGH)) {
			if (param1 == null) {
				// If null, throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.RAYLEIGH_DISTRIBUTION_INCOMPLETE.value(), HALF_CONNECTOR_PROPERTIES.getProperty("RAYLEIGH_DISTRIBUTION_INCOMPLETE"));
			}
			if (param1 <= 0)
				// parameter1 is less than 0,throw EquationExeception by giving status and description.
				throw new EquationException(ResultCodeType.RAYLEIGH_DISTRIBUTION_PARAMETERS.value(), HALF_CONNECTOR_PROPERTIES.getProperty("RAYLEIGH_DISTRIBUTION_PARAMETERS"));
		}
		// If successful return.
		return ResultCodeType.FORMULA_OK.value();
	}

}
