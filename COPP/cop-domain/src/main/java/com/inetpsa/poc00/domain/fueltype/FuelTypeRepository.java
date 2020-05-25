package com.inetpsa.poc00.domain.fueltype;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of FuelType.
 */

public interface FuelTypeRepository extends GenericRepository<FuelType, Long> {

    /**
     * Saves the FuelType.
     * 
     * @param object the FuelType to save
     * @return the FuelType
     */
    FuelType saveFuelType(FuelType object);

    /**
     * Persists the FuelType.
     *
     * @param object the FuelType to persist
     * @return the fuel type
     */
    FuelType persistFuelType(FuelType object);

    /**
     * Delete all categories.
     *
     * @param id the id
     * @return number of categories deleted
     */
    int deleteAll(long id);

    public FuelType loadFuelType(long fuelTypeId);
}
