package com.inetpsa.poc00.rest.lambdadecisionparameters;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.lambdadecisionparameters.LambdaDecisionParameters;

/**
 * The Interface LambdaDecisionParametersFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface LambdaDecisionParametersFinder {

	/**
	 * Gets the all lambda decision parameter.
	 *
	 * @return the all lambda decision parameter
	 */
	List<LambdaDecisionParameters> getAllLambdaDecisionParameter();

	/**
	 * Gets the lambda decision parameter by label.
	 *
	 * @param label the label
	 * @return the lambda decision parameter by label
	 */
	List<LambdaDecisionParameters> getLambdaDecisionParameterByLabel(String label);

}
