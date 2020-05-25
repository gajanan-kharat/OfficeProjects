/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.generictc;

import org.seedstack.business.domain.BaseFactory;

/**
 * Category Factory implementation.
 */

public class GenericTestConditionFactoryDefault extends BaseFactory<GenericTestCondition> implements GenericTestConditionFactory {

	/**
	 * Factory create method.
	 * 
	 * @param name
	 * @return the GenericTestCondition
	 */
	@Override
	public GenericTestCondition createGenericTestCondtn() {
		return new GenericTestCondition();
	}

	@Override
	public GenericTestCondition createGenericTestCondition(String dataType, String defaultValue, String label) {
		// TODO Auto-generated method stub
		return new GenericTestCondition(dataType, defaultValue, label);
	}

	@Override
	public GenericTestCondition createGenericTestCondition(String dataType, String defaultValue, String label, String value) {
		return new GenericTestCondition(dataType, defaultValue, label, value);
	}
}
