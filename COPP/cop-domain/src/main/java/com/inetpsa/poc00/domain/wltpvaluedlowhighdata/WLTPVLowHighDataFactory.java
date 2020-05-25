
package com.inetpsa.poc00.domain.wltpvaluedlowhighdata;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface WLTPVLowHighDataFactory extends GenericFactory<WLTPVLowHighData> {
    /**
     * Factory create method.
     *
     * @param name
     * @return the WLTP VLow & VHigh Data Object
     */
	WLTPVLowHighData createWLTPVLowHighData();
	
}
