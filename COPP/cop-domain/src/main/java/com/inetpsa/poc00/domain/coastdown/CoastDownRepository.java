package com.inetpsa.poc00.domain.coastdown;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of CoastDown.
 */

public interface CoastDownRepository extends GenericRepository<CoastDown, Long> {

	/**
	 * Saves the CoastDown.
	 * 
	 * @param object the CoastDown to save
	 * @return the CoastDown
	 */
	CoastDown saveCoastDown(CoastDown object);

	/**
	 * Persists the CoastDown.
	 * 
	 * @param version the version
	 * @param inertiaValue the inertia value
	 * @param pSAReference the SA reference
	 */
	void persistCoastDown(String version, Long inertiaValue, String pSAReference);

	/**
	 * Delete all categories.
	 * 
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return CoastDown count
	 */
	long count();

	/**
	 * Gets the coast down.
	 * 
	 * @param psaReference the psa reference
	 * @param inertiaValue the inertia value
	 * @param version the version
	 * @return the coast down
	 */
	public CoastDown getCoastDown(String psaReference, Integer inertiaValue, String version);

	/**
	 * Gets the coast down by latest version.
	 * 
	 * @return the coast down by latest version
	 */
	List<CoastDown> getCoastDownByLatestVersion();

	/**
	 * Gets the max version forcoastdown.
	 *
	 * @param psaRef the psa ref
	 * @param inertiaValue the inertia value
	 * @return the max version forcoastdown
	 */
	public CoastDown getMaxVersionForcoastdown(String psaRef, int inertiaValue);
}
