/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.inetpsa.poc00.domain.factorcoeffList;

import java.util.Set;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;

/**
 * Category Factory implementation.
 */

public class FactorCoeffListFactoryDefault extends BaseFactory<FactorCoefficentList> implements FactorCoeffListFactory {

    /**
     * Factory create method.
     * 
     * @return the FactorCoefficentsList
     */
    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public FactorCoefficentList createFactorCoeffList() {
        return new FactorCoefficentList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListFactory#createFactorCoeffList(java.lang.String, java.lang.String,
     * java.lang.String, java.util.Set)
     */
    @Override
    public FactorCoefficentList createFactorCoeffList(String label, String description, String version,
            Set<FactorCoeffApplicationType> fcApplicationTypes) {
        return new FactorCoefficentList(label, description, version, fcApplicationTypes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.poc00.domain.factorcoeffList.FactorCoeffListFactory#createFactorCoeffList(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public FactorCoefficentList createFactorCoeffList(String label, String description, String version) {
        return new FactorCoefficentList(label, description, version);
    }
}
