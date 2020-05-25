package com.inetpsa.poc00.rest.opacitydecisionparameters;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.opacitydecisionparameters.OpacityDecisionParameters;

/**
 * The Interface OpacityDecisionParametersFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface OpacityDecisionParametersFinder {

	/**
	 * Gets the all opacity decision parameter.
	 *
	 * @return the all opacity decision parameter
	 */
	List<OpacityDecisionParameters> getAllOpacityDecisionParameter();

	/**
	 * Gets the opacity decision parameter by label.
	 *
	 * @param label the label
	 * @return the opacity decision parameter by label
	 */
	List<OpacityDecisionParameters> getOpacityDecisionParameterByLabel(String label);

}
