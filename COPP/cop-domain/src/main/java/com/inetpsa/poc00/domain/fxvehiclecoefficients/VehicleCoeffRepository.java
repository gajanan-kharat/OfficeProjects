package com.inetpsa.poc00.domain.fxvehiclecoefficients;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of VehicleCoefficents.
 */

public interface VehicleCoeffRepository extends GenericRepository<VehicleCoefficents, Long> {

    /**
     * Saves the VehicleCoefficents.
     * 
     * @param object the VehicleCoefficents to save
     * @return the VehicleCoefficents
     */
    VehicleCoefficents saveVehicleCoefficents(VehicleCoefficents object);

    /**
     * Persists the VehicleCoefficents.
     * 
     * @param object the VehicleCoefficents to persist
     */
    void persistVehicleCoefficents(VehicleCoefficents object);

    /**
     * Delete all categories
     * 
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     * 
     * @return VehicleCoefficents count
     */
    long count();

    void deleteVehicleCoefficents(Long id);

    public VehicleCoefficents loadVehicleCoeff(long vehicleCoeffId);
}
