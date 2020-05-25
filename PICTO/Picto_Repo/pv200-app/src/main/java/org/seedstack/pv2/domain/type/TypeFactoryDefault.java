package org.seedstack.pv2.domain.type;

import org.seedstack.business.domain.BaseFactory;

/**
 * Type Factory implementation.
 */
public class TypeFactoryDefault extends BaseFactory<Type> implements
		TypeFactory {

	@Override
	public Type createType(Long id, String typeLable, Type parentType) {
		return new Type(id, typeLable, parentType);
	}

}
