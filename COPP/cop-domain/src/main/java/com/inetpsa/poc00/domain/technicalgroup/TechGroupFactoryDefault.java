/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.technicalgroup;

import org.seedstack.business.domain.BaseFactory;


/**
 * Category Factory implementation.
 */

public class TechGroupFactoryDefault extends BaseFactory<TechnicalGroup> implements TechGroupFactory {

	 /**
     * Factory create method.
    *
    * @param name
    * @return the TechnicalGroup
    */
	@Override
	public TechnicalGroup createTechGroup()
	 {
		return new TechnicalGroup();
	}
}
