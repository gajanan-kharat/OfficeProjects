
package com.inetpsa.poc00.domain.carbrand;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface CarBrandFactory extends GenericFactory<CarBrand> {
    /**
     * Factory create method.
     *
     * @param 
     * @return the CarBrand
     */
	CarBrand createCarBrand();
	
}
