/*
 * Creation : Jun 17, 2016
 */
package com.inetpsa.poc00.domain.valuedinertia;

import org.seedstack.business.domain.BaseFactory;

public class ValuedInertiaFactoryDefault extends BaseFactory<ValuedInertia> implements ValuedInertiaFactory {

	@Override
	public ValuedInertia createValuedInertia() {

		return new ValuedInertia();
	}

}
