/*
 * Creation : Sep 22, 2016
 */
package com.inetpsa.poc00.rest.vehiclefilestatus;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;

/**
 * The Interface VehicleFileStatusFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface VehicleFileStatusFinder {

    /**
     * Gets the vehicle file statusby label.
     *
     * @param label the label
     * @return the vehicle file statusby label
     */
    public VehicleFileStatus getVehicleFileStatusbyLabel(String label);

    /**
     * Gets the vehicle file status list.
     *
     * @return the vehicle file status list
     */
    List<VehicleFileStatusRepresentation> getVehicleFileStatusList();

}
