package com.inetpsa.poc00.domain.fuelinjectiontype;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of FuelInjectionType.
 */

public interface FuelInjctnTypeRepository extends GenericRepository<FuelInjectionType, Long> {

	/**
	 * Saves the FuelInjectionType.
	 * 
	 * @param object the FuelInjectionType to save
	 * @return the FuelInjectionType
	 */
	FuelInjectionType saveFuelInjctnType(FuelInjectionType object);

	/**
	 * Delete all categories.
	 *
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return FuelInjectionType count
	 */
	long count();

	/**
	 * Delete fuel injctn type.
	 *
	 * @param id the id
	 * @return the int
	 */
	int deleteFuelInjctnType(Long id);

	/**
	 * Gets the all fi type for copied fc list.
	 *
	 * @param entityId the entity id
	 * @return the all fi type for copied fc list
	 */
	List<FuelInjectionType> getAllFITypeForCopiedFCList(Long entityId);

	/**
	 * Gets the all fi type for copied pg list.
	 *
	 * @param entityId the entity id
	 * @return the all fi type for copied pg list
	 */
	List<FuelInjectionType> getAllFITypeForCopiedPGList(Long entityId);
}
