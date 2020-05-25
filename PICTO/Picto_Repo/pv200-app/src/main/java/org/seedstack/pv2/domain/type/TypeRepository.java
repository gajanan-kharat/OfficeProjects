package org.seedstack.pv2.domain.type;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of category.
 */
public interface TypeRepository extends GenericRepository<Type, Long> {

	/**
	 * Saves the type.
	 *
	 * @param type
	 *            the type to save
	 * @return the Type
	 */
	Type saveType(Type type);

	/**
	 * Persists the type.
	 *
	 * @param type
	 *            the type to persist
	 */
	void persistType(Type type);
	
	/**
	 * Get all type by name
	 *
	 * @param type
	 *            the type to persist
	 *  @return the Type
	 */

	Type findAllTypeByName(String type); 
	}
