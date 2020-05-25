/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.typeapprovalarea;

import org.seedstack.business.domain.BaseFactory;


/**
 * Category Factory implementation.
 */

public class TypeApproalAreaFactoryDefault extends BaseFactory<TypeApprovalArea> implements TypeApprovalAreaFactory {

	 /**
     * Factory create method.
    *
    * @param name 
    * @return the TypeApprovalArea
    */
	@Override
	public TypeApprovalArea createTypeApprovalArea()
	 {
		return new TypeApprovalArea();
	}
}
