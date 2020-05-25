/*
 * Creation : Dec 9, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.resultset;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetFactory;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetRepository;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehicle.VehicleRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.rest.resultset.PollGasResultSetFinder;
import com.inetpsa.poc00.rest.resultset.PollGasResultSetRepresentation;

/**
 * TODO : Description
 * 
 * @author mehaj
 */
@RunWith(SeedITRunner.class)
public class PollGasResultSetFinderIT {
    @Inject
    PollGasResultSetFinder pollGasResultSetFinder;
    /** The vehicle repo. */
    @Inject
    private VehicleRepository vehicleRepo;
    /** The vehicle file repo. */
    @Inject
    private VehicleFileRepository vehicleFileRepo;
    @Inject
    PollutantGasResultSetFactory pollGasResultSetFactory;
    @Inject
    PollutantGasResultSetRepository pollutantGasResultSetRepository;

    @Test
    public void getPollutantGasResultSetsRepTest() {
        Vehicle vehicleObj = new Vehicle();

        vehicleObj.setDescription("test Dsc1");

        Vehicle newVehicle = vehicleRepo.saveVehicle(vehicleObj);
        assertNotNull(newVehicle);
        VehicleFile vehicleFileObj = new VehicleFile();

        vehicleFileObj.setVehicle(newVehicle);

        VehicleFile vehFileSaved = vehicleFileRepo.saveVehicle(vehicleFileObj);
        PollutantGasResultSet pollGasResultSet = pollGasResultSetFactory.createPollutantGasResultSet();
        pollGasResultSet.setVehicleFile(vehFileSaved);
        pollutantGasResultSetRepository.savePollutantGasResultSet(pollGasResultSet);
        List<PollGasResultSetRepresentation> pollGasRepList = pollGasResultSetFinder.getPollutantGasResultSetsRep(vehFileSaved.getEntityId());
        Assert.assertNotEquals(0, pollGasRepList.size());
    }
}
