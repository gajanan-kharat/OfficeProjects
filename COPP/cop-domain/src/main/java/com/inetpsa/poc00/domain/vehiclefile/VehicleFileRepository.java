package com.inetpsa.poc00.domain.vehiclefile;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface VehicleFileRepository.
 */
public interface VehicleFileRepository extends GenericRepository<VehicleFile, Long> {

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.domain.Repository#save(org.seedstack.business.domain.AggregateRoot)
     */

    VehicleFile saveVehicle(VehicleFile obj);

    /**
     * Load vehicle.
     *
     * @param Id the id
     * @return the vehicle file
     */
    VehicleFile loadVehicle(Long id);

    /**
     * Save vehicle reception.
     *
     * @param id the id
     * @return the vehicle file
     */
    VehicleFile saveVehicleReception(VehicleFile id);

    /**
     * Gets the vehicle file count.
     *
     * @param basketId the basket id
     * @return the vehicle file count
     */
    Long getVehicleFileCount(Long basketId);

    /**
     * Gets the all vehicle file.
     *
     * @return the all vehicle file
     */
    public List<Long> getAllVehicleFile();

    /**
     * Gets the vehicle file by label.
     *
     * @param vehicleCondition the vehicle condition
     * @param typeOfTestId the type of test id
     * @return the vehicle file by label
     */
    public VehicleFile getVehicleFileByLabel(String vehicleCondition);

}
