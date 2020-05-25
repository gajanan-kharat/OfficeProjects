/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.conditioningresult;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating ConditioningResult objects.
 */
public interface ConditioningResultFactory extends GenericFactory<ConditioningResult> {

	/**
	 * Creates a new ConditioningResult object.
	 *
	 * @return the archive box
	 */
	ConditioningResult createConditioningResult();

}
