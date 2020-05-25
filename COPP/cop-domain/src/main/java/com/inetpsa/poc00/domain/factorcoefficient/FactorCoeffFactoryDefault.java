/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.factorcoefficient;

import org.seedstack.business.domain.BaseFactory;

import com.inetpsa.poc00.domain.factorcoeffLabel.FactorCoefficentsLabel;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * Category Factory implementation.
 */

public class FactorCoeffFactoryDefault extends BaseFactory<FactorCoefficents> implements FactorCoeffFactory {

	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the FactorCoefficents
	 */
	@Override
	public FactorCoefficents createFactorCoefficents() {
		return new FactorCoefficents();
	}

	@Override
	public FactorCoefficents createFactorCoefficents(double defaultValue, FactorCoefficentsLabel fcLabel, PollutantGasLabel pollutantGasLabel) {
		return new FactorCoefficents(defaultValue, fcLabel, pollutantGasLabel);
	}

	@Override
	public FactorCoefficents createFactorCoefficents(double defaultValue, PollutantGasLabel pgLabel) {
		return new FactorCoefficents(defaultValue, pgLabel);
	}

}
