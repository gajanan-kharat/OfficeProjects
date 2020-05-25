/*
 * Creation : Feb 2, 2017
 */
package com.inetpsa.poc00.domain.expertiseresult;

import org.seedstack.business.domain.BaseFactory;

/**
 * The Class ExpertiseResultFactoryDefault.
 */
public class ExpertiseResultFactoryDefault extends BaseFactory<ExpertiseResult> implements ExpertiseResultFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.expertiseresult.ExpertiseResultFactory#createExpertiseResult()
	 */
	@Override
	public ExpertiseResult createExpertiseResult() {

		return new ExpertiseResult();
	}

}
