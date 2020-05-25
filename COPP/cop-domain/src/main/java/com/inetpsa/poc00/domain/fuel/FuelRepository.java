package com.inetpsa.poc00.domain.fuel;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.domain.fueltype.FuelType;

/**
 * Repository interface of Fuel.
 */

public interface FuelRepository extends GenericRepository<Fuel, Long> {

    /**
     * Saves the Fuel.
     * 
     * @param object the Fuel to save
     * @return the Fuel
     */
    FuelType saveFuel(FuelType object);

    /**
     * Delete all categories.
     * 
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     * 
     * @return Fuel count
     */
    long count();

    /**
     * Delete fuel.
     * 
     * @param id the id
     * @return the int
     */
    @Transactional
    int deleteFuel(Long id);

    /**
     * Save fuel.
     * 
     * @param fuelObject the fuel object
     * @return the fuel
     */
    Fuel saveFuel(Fuel fuelObject);

    /**
     * Gets the all fuel.
     * 
     * @return the all fuel
     */
    public List<Fuel> getAllFuel();

    /**
     * Gets the all fuel for copied fc list.
     *
     * @param entityId the entity id
     * @return the all fuel for copied fc list
     */
    List<Fuel> getAllFuelForCopiedFCList(Long entityId);

    /**
     * Gets the all fuel for copied pg list.
     *
     * @param entityId the entity id
     * @return the all fuel for copied pg list
     */
    List<Fuel> getAllFuelForCopiedPGList(Long entityId);

    /**
     * Gets the fuel by label.
     *
     * @param label the label
     * @return the fuel by label
     */
    public List<Fuel> getFuelByLabel(String label);

    public Fuel loadFuel(long fuelId);
}
