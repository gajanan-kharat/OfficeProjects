package com.inetpsa.poc00.domain.inertia;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of Inertia.
 */

public interface InertiaRepository extends GenericRepository<Inertia, Long> {

	/**
	 * Saves the Inertia.
	 *
	 * @param object the Inertia to save
	 * @return the Inertia
	 */
	Inertia saveInertia(Inertia object);

	/**
	 * Delete all categories.
	 *
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 *
	 * @return Inertia count
	 */
	long count();

	/**
	 * Gets the inertia obj.
	 * 
	 * @param value the value
	 * @return the inertia obj
	 */
	Inertia getInertiaByValue(Integer value);

	/**
	 * Gets the inertia obj.
	 *
	 * @param value the value
	 * @return the inertia obj
	 */
	public List<Inertia> getinertiaObj(int value);

}
