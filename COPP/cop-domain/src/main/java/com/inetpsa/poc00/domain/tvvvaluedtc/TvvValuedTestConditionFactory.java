package com.inetpsa.poc00.domain.tvvvaluedtc;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */

public interface TvvValuedTestConditionFactory extends GenericFactory<TvvValuedTestCondition> {
	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the TvvValuedTestCondtn
	 */
	TvvValuedTestCondition createTvvValuedTestCondition();

	TvvValuedTestCondition createTvvValuedTestCondition(TvvValuedTestCondition genericData);

}
