/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.factorcoeffapplicationtype;

import org.seedstack.business.domain.BaseFactory;

/**
 * Category Factory implementation.
 */

public class FactorCoeffAppFactoryDefault extends BaseFactory<FactorCoeffApplicationType> implements FactorCoeffAppFactory {

	/**
	 * Factory create method.
	 * 
	 * @return the FactorCoefficentsApplicationType
	 */
	@Override
	public FactorCoeffApplicationType createFactorCoefficents() {
		return new FactorCoeffApplicationType();
	}
}
