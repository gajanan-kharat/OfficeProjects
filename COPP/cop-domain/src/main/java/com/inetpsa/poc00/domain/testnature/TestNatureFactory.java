
package com.inetpsa.poc00.domain.testnature;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface TestNatureFactory extends GenericFactory<TestNature> {
    /**
     * Factory create method.
     *
     * @param name
     * @return the TestNature
     */
	TestNature createTestNature();
	
}
