package com.inetpsa.poc00.rest.codecisionparameters;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.codecisionparameters.CODecisionParameters;

/**
 * The Interface CODecisionParametersFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface CODecisionParametersFinder {

	/**
	 * Gets the all co decision parameter.
	 *
	 * @return the all co decision parameter
	 */
	List<CODecisionParameters> getAllCODecisionParameter();

	/**
	 * Gets the CO decision parameter by label.
	 *
	 * @param label the label
	 * @return the CO decision parameter by label
	 */
	List<CODecisionParameters> getCODecParamByFuelTypeLabel(String label);

}
