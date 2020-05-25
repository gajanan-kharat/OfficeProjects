package org.seedstack.pv2.domain.type;

import org.seedstack.business.domain.GenericFactory;

/**
 * Type Factory interface.
 */
public interface TypeFactory extends GenericFactory<Type> {

	/**
	 * Type factory create method
	 * 
	 * @param id
	 * @param typeLable
	 * @param parentType
	 * @return Type
	 */
	Type createType(Long id, String typeLable, Type parentType);

}
