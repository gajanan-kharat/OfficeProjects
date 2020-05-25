/*
 * Creation : Oct 24, 2016
 */
package com.inetpsa.poc00.vehicle;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Logging;
import org.seedstack.seed.it.SeedITRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.application.vehicle.VehicleService;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestRepository;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehicle.VehicleRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;

/**
 * The Class VehicleIT.
 */
@RunWith(SeedITRunner.class)
public class VehicleIT {

	/** The vehicle service. */
	@Inject
	private VehicleService vehicleService;

	/** The type test repo. */
	@Inject
	private TypeOfTestRepository typeTestRepo;
	@Inject
	private VehicleRepository vehicleRepo;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(VehicleIT.class);

	private VehicleFileRepository vehicleFileRepo;

	/**
	 * Creates the vehicle.
	 */
	@Test
	public void createVehicle() {

		Vehicle vehicleObj = new Vehicle();

		vehicleObj.setCounterMark("testCount" + Calendar.getInstance().getTime());
		vehicleObj.setChasisNumber("testChassis" + Calendar.getInstance().getTime());
		vehicleObj.setRegistrationNumber("testReg" + Calendar.getInstance().getTime());

		assertNotNull(vehicleService);
		TypeOfTest typeTestObj = new TypeOfTest();

		typeTestObj.setLabel("TypeTest1");
		TypeOfTest newTypeTest = typeTestRepo.saveTypeOfTest(typeTestObj);
		assertNotNull(newTypeTest);

		VehicleFile vehicleFile = vehicleService.createVehicle(vehicleObj, newTypeTest.getEntityId());
		assertNotNull(vehicleFile);
		logger.info("**********Vehicle Create tested successfully*********");

	}

}
