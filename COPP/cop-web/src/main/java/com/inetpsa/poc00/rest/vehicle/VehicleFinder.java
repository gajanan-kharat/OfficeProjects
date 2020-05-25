/*
 * Creation : Aug 26, 2016
 */
package com.inetpsa.poc00.rest.vehicle;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.vehicle.Vehicle;

/**
 * The Interface VehicleFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface VehicleFinder {

    /**
     * Gets the all vehicle by id.
     * 
     * @param vehicleId the vehicle id
     * @return the all vehicle by id
     */
    Vehicle getAllVehicleById(Long vehicleId);

    /**
     * Gets the vehicle id.
     *
     * @param vehicleCondition the vehicle condition
     * @return the vehicle id
     */
    Vehicle getVehicleId(String vehicleCondition);

    /**
     * Gets the vehicle by vehicle file id.
     *
     * @param vehicleFileId the vehicle file id
     * @return the vehicle by vehicle file id
     */
    Vehicle getVehicleByVehicleFileId(Long vehicleFileId);

    /**
     * Gets the all vehicle.
     *
     * @return the all vehicle
     */
    public List<String> getModelYear();

}
