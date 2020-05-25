/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.tvvvaluedtc;

import org.seedstack.business.domain.BaseFactory;

public class TvvValuedTestConditionDefault extends BaseFactory<TvvValuedTestCondition> implements TvvValuedTestConditionFactory {

	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the return new TvvValuedTestCondition();
	 */

	@Override
	public com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition createTvvValuedTestCondition() {
		return new TvvValuedTestCondition();
	}

	@Override
	public TvvValuedTestCondition createTvvValuedTestCondition(TvvValuedTestCondition tvvValuedTestCondition) {
		return new TvvValuedTestCondition(tvvValuedTestCondition);
	}
}
