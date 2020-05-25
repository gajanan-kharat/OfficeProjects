/*
 * Author: U224106
 * Creation: 25 août 2014
 */
package com.inetpsa.oaz00.ws.checker.model;

import java.math.BigInteger;

/**
 * The Enum ResultCodeType.
 * 
 * @author U224106
 */
public enum ResultCodeType {

	/** Analyzed formula is conforming calculation rules. */
	FORMULA_OK(BigInteger.valueOf(0)),
	/** Analyzed formula is empty. */
	EMPTY_FORMULA(BigInteger.valueOf(1)),
	/**
	 * The first character of the formula cannot be a Separator or an Operator except + and -. The last character of the formula cannot be a Separator
	 * or an Operator.
	 */
	INVALID_FORMULA(BigInteger.valueOf(2)),
	/**
	 * The number of opening brackets is different from the number of closing ones: a distinction is made between parentheses () and square brackets
	 * [].
	 */
	BRACKETS_INCONSISTENT(BigInteger.valueOf(3)),
	/** A block of brackets is empty. */
	EMPTY_BRACKETS_BLOCK(BigInteger.valueOf(4)),
	/** An opening bracket must follow either nothing, an opening bracket, a Function, an Operator or a Separator. */
	INVALID_BRACKET_POSITION(BigInteger.valueOf(5)),
	/** An Operator cannot start a Block. An Operator cannot end a Block. An Operator cannot be followed by another Operator. */
	INVALID_OPERATOR_POSITION(BigInteger.valueOf(6)),
	/** A Separator cannot be set just after an opening bracket, a Separator or an Operator. A Separator cannot be set just before Operators * and /. */
	INVALID_SEPARATOR_POSITION(BigInteger.valueOf(7)),
	/** Two Contributors references or a Contributor reference and a Constant must be separated by a Separator or an Operator. */
	MISSING_OPERATOR_OR_SEPARATOR(BigInteger.valueOf(8)),
	/** A Function must be followed by a brackets block. */
	INVALID_FUNCTION(BigInteger.valueOf(9)),
	/** More than one decimal dot in a sequence of numbers is incorrect. */
	INVALID_NUMBER_FORMAT(BigInteger.valueOf(10)),
	/** A contributor reference is not in the list of Transfer model contributor’s. */
	INVALID_CONTRIBUTOR_REFERENCE(BigInteger.valueOf(11)),
	/**	Comma in between numbers */
	INVALID_COMMA_IN_NUMBER(BigInteger.valueOf(12)),
	/** The normal distribution incomplete. */
	NORMAL_DISTRIBUTION_INCOMPLETE(BigInteger.valueOf(100)),
	/** The normal distribution parameters. */
	NORMAL_DISTRIBUTION_PARAMETERS(BigInteger.valueOf(110)),
	/** The normal by range distribution incomplete. */
	NORMAL_BY_RANGE_DISTRIBUTION_INCOMPLETE(BigInteger.valueOf(200)),
	/** The normal by range distribution parameters. */
	NORMAL_BY_RANGE_DISTRIBUTION_PARAMETERS(BigInteger.valueOf(210)),
	/** The log normal distribution incomplete. */
	LOG_NORMAL_DISTRIBUTION_INCOMPLETE(BigInteger.valueOf(300)),
	/** The log normal distribution parameters. */
	LOG_NORMAL_DISTRIBUTION_PARAMETERS(BigInteger.valueOf(310)),
	/** The uniform distribution incomplete. */
	UNIFORM_DISTRIBUTION_INCOMPLETE(BigInteger.valueOf(400)),
	/** The uniform distribution parameters. */
	UNIFORM_DISTRIBUTION_PARAMETERS(BigInteger.valueOf(410)),
	/** The binomial distribution incomplete. */
	BINOMIAL_DISTRIBUTION_INCOMPLETE(BigInteger.valueOf(500)),
	/** The binomial distribution parameters. */
	BINOMIAL_DISTRIBUTION_PARAMETERS(BigInteger.valueOf(510)),
	/** The poisson distribution incomplete. */
	POISSON_DISTRIBUTION_INCOMPLETE(BigInteger.valueOf(600)),
	/** The poisson distribution parameters. */
	POISSON_DISTRIBUTION_PARAMETERS(BigInteger.valueOf(610)),
	/** The exponential distribution incomplete. */
	EXPONENTIAL_DISTRIBUTION_INCOMPLETE(BigInteger.valueOf(700)),
	/** The exponential distribution parameters. */
	EXPONENTIAL_DISTRIBUTION_PARAMETERS(BigInteger.valueOf(710)),
	/** The weibull distribution incomplete. */
	WEIBULL_DISTRIBUTION_INCOMPLETE(BigInteger.valueOf(800)),
	/** The weibull distribution parameters. */
	WEIBULL_DISTRIBUTION_PARAMETERS(BigInteger.valueOf(810)),
	/** The Rayleigh distribution incomplete. */
	RAYLEIGH_DISTRIBUTION_INCOMPLETE(BigInteger.valueOf(900)),
	/** The Rayleigh distribution parameters. */
	RAYLEIGH_DISTRIBUTION_PARAMETERS(BigInteger.valueOf(910)),
	/** The Unknown distribution. */
	UNKNOWN_DISTRIBUTION(BigInteger.valueOf(1000)),
	/** Invalid Monte Carlo input. */
	INVALID_MONTE_CARLO_INPUT(BigInteger.valueOf(2000)),
	/** Limitation done on Monte Carlo input data set */
	MONTE_CARLO_LIMITED(BigInteger.valueOf(2010)),
	/** The TI Specification unreachable objectives. */
	INFINITE_MONTE_CARLO_OUTPUT(BigInteger.valueOf(2020)),
	/** If an unexpected error occurs during Scilab calculation, a generic error message will be sent to Enovia. */
	ERRONEOUS_MONTE_CARLO_OUTPUT(BigInteger.valueOf(2030)),
	/** The first found error has to be sent to Enovia if non-conformity is detected. */
	INVALID_SPEC_IT_INPUT(BigInteger.valueOf(3000)),
	/**
	 * If TNC value is detected lower than 100 and if calculation is completed with success, a warning message will be sent to Enovia to inform
	 * end-user that he may confuse TNC unit with percentage.
	 */
	SPEC_IT_TNC_INPUT(BigInteger.valueOf(3010)),
	/**
	 * If numerical outputs are infinite, they must be replaced by null value and an error message will be sent to Enovia to inform end-user that
	 * infinite values have been cleared.
	 */
	INFINITE_SPEC_IT_OUTPUT(BigInteger.valueOf(3020)),
	/** If Mother Requirements’ objectives are not reachable by the calculation, an error message will be sent to Enovia. */
	SPEC_IT_UNREACHABLE(BigInteger.valueOf(3030)),
	/** If Mother Requirements’ tolerances are already optimal, a warning message will be sent to Enovia. */
	SPEC_IT_NON_IMPROVABLE(BigInteger.valueOf(3040)),
	/** If an unexpected error occurs during Scilab calculation, a generic error message will be sent to Enovia. */
	ERRONEOUS_SPEC_IT_OUTPUT(BigInteger.valueOf(3050)),
	/** The first found error has to be sent to Enovia if non-conformity is detected with following message format. */
	INVALID_ARITH_STAT_INPUT(BigInteger.valueOf(4000)),
	/**
	 * If Original TNC value is detected lower than 100 and if calculation is completed with success, a warning message will be sent to Enovia to
	 * inform end-user that he may confuse TNC unit with percentage.
	 */
	ARITH_STAT_TNC_INPUT(BigInteger.valueOf(4010)),
	/**
	 * If numerical outputs are infinite, they must be replaced by null value and an error message will be sent to Enovia to inform end-user that
	 * infinite values have been cleared.
	 */
	INFINITE_ARITH_STAT_OUTPUT(BigInteger.valueOf(4020)),
	/** If an unexpected error occurs during Scilab calculation, a generic error message will be sent to Enovia. */
	ERRONEOUS_ARITH_STAT_OUTPUT(BigInteger.valueOf(4030)),
	/** The first found error has to be sent to Enovia if non-conformity is detected with following message format. */
	INVALID_SEMI_QUAD_INPUT(BigInteger.valueOf(5000)),
	/**
	 * If Original TNC value is detected lower than 100 and if calculation is completed with success, a warning message will be sent to Enovia to
	 * inform end-user that he may confuse TNC unit with percentage
	 */
	SEMI_QUAD_TNC_INPUT(BigInteger.valueOf(5010)),
	/**
	 * If numerical outputs are infinite, they must be replaced by null value and an error message will be sent to Enovia to inform end-user that
	 * infinite values have been cleared.
	 */
	INFINITE_SEMI_QUAD_OUTPUT(BigInteger.valueOf(5020)),
	/** If an unexpected error occurs during Scilab calculation, a generic error message will be sent to Enovia */
	ERRONEOUS_SEMI_QUAD_OUTPUT(BigInteger.valueOf(5030));

	/** The value. */
	private final BigInteger value;

	/**
	 * Instantiates a new result code type.
	 * 
	 * @param v
	 *            value to be set.
	 */
	ResultCodeType(BigInteger v) {
		value = v;
	}

	/**
	 * Value.
	 * 
	 * @return the big integer
	 */
	public BigInteger value() {
		return value;
	}

	/**
	 * From value.
	 * 
	 * @param v
	 *            the v
	 * @return the result code type
	 */
	public static ResultCodeType fromValue(BigInteger v) {
		for (ResultCodeType c : ResultCodeType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v.toString());
	}

}
