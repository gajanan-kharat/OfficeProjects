/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.es;

import org.seedstack.business.domain.BaseFactory;

/**
 * Category Factory implementation.
 */

public class EmissionStandardFactoryDefault extends BaseFactory<EmissionStandard> implements EmissionStandardFactory {

	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the EmissionStandard
	 */

	@Override
	public EmissionStandard createEmissonStandard() {
		return new EmissionStandard();
	}

	@Override
	public EmissionStandard createEmissonStandard(String esLabel, String description, String version) {

		return new EmissionStandard(esLabel, description, version);
	}
}
