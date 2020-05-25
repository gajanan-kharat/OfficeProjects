package com.inetpsa.poc00.domain.vehiclefile;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

/**
 * The Class VehicleFileFactoryImpl.
 */
public class VehicleFileFactoryImpl extends BaseFactory<VehicleFile> implements VehicleFileFactory {

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public VehicleFile createVehicleFile() {

        return new VehicleFile();
    }

}
