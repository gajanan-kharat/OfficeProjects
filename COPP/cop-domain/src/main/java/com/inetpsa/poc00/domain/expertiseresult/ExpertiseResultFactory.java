/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.expertiseresult;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating ExpertiseResult objects.
 */
public interface ExpertiseResultFactory extends GenericFactory<ExpertiseResult> {

	/**
	 * Creates a new ExpertiseResult object.
	 *
	 * @return the expertise result
	 */
	ExpertiseResult createExpertiseResult();

}
