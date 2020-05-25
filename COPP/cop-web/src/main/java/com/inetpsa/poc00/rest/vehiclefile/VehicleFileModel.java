/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.rest.vehiclefile;

import java.util.List;

/**
 * The Class VehicleFileModel.
 */
public class VehicleFileModel {

    /** The vehicle file ids. */
    private List<Long> vehicleFileIds;

    /**
     * Gets the vehicle file ids.
     *
     * @return the vehicle file ids
     */
    public List<Long> getVehicleFileIds() {
        return vehicleFileIds;
    }

    /**
     * Sets the vehicle file ids.
     *
     * @param vehicleFileIds the new vehicle file ids
     */
    public void setVehicleFileIds(List<Long> vehicleFileIds) {
        this.vehicleFileIds = vehicleFileIds;
    }
}
