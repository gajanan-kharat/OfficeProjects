/*
 * Creation : Sep 12, 2016
 */
package com.inetpsa.poc00.domain.tirebrand;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface TireBrandRepository.
 */

public interface TireBrandRepository extends GenericRepository<TireBrand, Long> {

	/**
	 * Save tire brand.
	 *
	 * @param tireBrand the tire brand
	 * @return the tire brand
	 */

	TireBrand saveTireBrand(TireBrand tireBrand);

	/**
	 * Persist tire brand.
	 *
	 * @param tireBrand the tire brand
	 */
	void persistTireBrand(TireBrand tireBrand);

	/**
	 * Delete all.
	 *
	 * @return the int
	 */
	int deleteAll();

	/**
	 * Count.
	 *
	 * @return the long
	 */
	long count();

	/**
	 * Gets the all tire brands.
	 *
	 * @return the all tire brands
	 */
	List<TireBrand> getAllTireBrands();

	/**
	 * Delete tire brand.
	 *
	 * @param id the id
	 * @return the int
	 */

	public int deleteTireBrand(Long id);

	/**
	 * Gets the tire brand by label.
	 *
	 * @param label the label
	 * @return the tire brand by label
	 */
	public List<TireBrand> getTireBrandByLabel(String label);

}
