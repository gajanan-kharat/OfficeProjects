package com.inetpsa.poc00.domain.vehiclefile;

import org.seedstack.business.domain.GenericFactory;

/**
 * A factory for creating VehicleFile objects.
 */
public interface VehicleFileFactory extends GenericFactory<VehicleFile> {

    VehicleFile createVehicleFile();

}
