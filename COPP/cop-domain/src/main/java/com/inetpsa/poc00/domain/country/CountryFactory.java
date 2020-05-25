
package com.inetpsa.poc00.domain.country;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface CountryFactory extends GenericFactory<Country> {
    /**
     * Factory create method.
     *
     * @param name
     * @return the Country
     */
	Country createCountry();
	
}
