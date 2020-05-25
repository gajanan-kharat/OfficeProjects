/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.esdependent.tdl;

import org.seedstack.business.domain.BaseFactory;

/**
 * Category Factory implementation.
 */

public class EmissionStdDepTDLFactoryDefault extends BaseFactory<EmissionStdDepTDL> implements EmissionStdDepTDLFactory {

	/**
	 * Factory create method.
	 * 
	 * @return the EmissonStdDepTDL
	 */
	@Override
	public EmissionStdDepTDL createEmissonStdDepTDL() {
		return new EmissionStdDepTDL();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLFactory#createEmissonStdDepTDL(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public EmissionStdDepTDL createEmissonStdDepTDL(String label, String description, String version) {
		return new EmissionStdDepTDL(label, description, version);
	}
}
