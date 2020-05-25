/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.pollutantgaslimit;

import org.seedstack.business.domain.BaseFactory;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;

/**
 * The Class PollutantGasLimitFactoryDefault.
 */
public class PollutantGasLimitFactoryDefault extends BaseFactory<PollutantGasLimit> implements PollutantGasLimitFactory {

	/**
	 * Factory create method.
	 * 
	 * @return the PollutantGasLimit
	 */
	@Override
	public PollutantGasLimit createPollutantGasLimit() {
		return new PollutantGasLimit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitFactory#createPollutantGasLimits(com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel,
	 *      java.lang.String, java.lang.Double, java.lang.String, java.lang.Double)
	 */
	@Override
	public PollutantGasLimit createPollutantGasLimits(PollutantGasLabel pollutantGasLabel, String maxDValRule, Double maxDValue, String minDValRule, Double minDValue) {
		return new PollutantGasLimit(pollutantGasLabel, maxDValRule, maxDValue, minDValRule, minDValue);
	}
}
