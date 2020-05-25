/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.tvvdependent;

import org.seedstack.business.domain.BaseFactory;

/**
 * TvvDepTDL Factory implementation.
 */

public class TvvDepTDLFactoryDefault extends BaseFactory<TvvDepTDL> implements TvvDepTDLFactory {

	/**
	 * Factory create method.
	 * 
	 * @param name the dictionary name
	 * @return the dictionary
	 */
	@Override
	public TvvDepTDL createTvvDepTDL() {
		return new TvvDepTDL();
	}

	@Override
	public TvvDepTDL createTvvDepTDL(String label, String description, String version) {

		return new TvvDepTDL(label, description, version);
	}

	@Override
	public TvvDepTDL createTvvDepTDL(TvvDepTDL tvvDepTDL) {
		return new TvvDepTDL(tvvDepTDL);
	}
}
