
package com.inetpsa.poc00.domain.typeapprovalarea;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface TypeApprovalAreaFactory extends GenericFactory<TypeApprovalArea> {
    /**
     * Factory create method.
     *
     * @param name
     * @return the TypeApprovalArea
     */
	TypeApprovalArea createTypeApprovalArea();
	
}
