
package com.inetpsa.poc00.domain.category;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface CategoryFactory extends GenericFactory<Category> {
    /**
     * Factory create method.
     *
     * @param name 
     * @return the Category
     */
	Category createCategory();
	
}
