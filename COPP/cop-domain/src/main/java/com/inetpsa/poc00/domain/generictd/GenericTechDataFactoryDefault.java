/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.generictd;

import org.seedstack.business.domain.BaseFactory;

/**
 * Category Factory implementation.
 */

public class GenericTechDataFactoryDefault extends BaseFactory<GenericTechnicalData> implements GenericTechDataFactory {

	/**
	 * Factory create method.
	 * 
	 * @param name the dictionary name
	 * @return the dictionary
	 */
	@Override
	public GenericTechnicalData createGenericTechData() {
		return new GenericTechnicalData();
	}

	@Override
	public GenericTechnicalData createGenericTechData(String dataType, String defaultValue, String label, String unitValue) {

		return new GenericTechnicalData(dataType, defaultValue, label, unitValue);
	}

	@Override
	public GenericTechnicalData createGenericTechData(String dataType, String defaultValue, String label) {

		return new GenericTechnicalData(dataType, defaultValue, label);
	}
}
