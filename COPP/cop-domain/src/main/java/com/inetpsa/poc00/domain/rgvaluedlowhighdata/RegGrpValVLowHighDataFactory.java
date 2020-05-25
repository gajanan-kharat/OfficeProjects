
package com.inetpsa.poc00.domain.rgvaluedlowhighdata;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface RegGrpValVLowHighDataFactory extends GenericFactory<RegGrpValVLowHighData> {
    /**
     * Factory create method.
     *
     * @param name
     * @return the Regular Group Valued VLow & VHigh Data Object
     */
	RegGrpValVLowHighData createRegGrpValVLowHighData();
	
}
