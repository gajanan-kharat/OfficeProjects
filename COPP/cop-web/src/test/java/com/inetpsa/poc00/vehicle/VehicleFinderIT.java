/*
 * Creation : Oct 25, 2016
 */
package com.inetpsa.poc00.vehicle;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Logging;
import org.seedstack.seed.it.SeedITRunner;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehicle.VehicleRepository;
import com.inetpsa.poc00.rest.vehicle.VehicleFinder;

/**
 * The Class VehicleFinderIT.
 */
@RunWith(SeedITRunner.class)
public class VehicleFinderIT {

	/** The vehicle finder. */
	@Inject
	private VehicleFinder vehicleFinder;

	/** The vehicle repo. */
	@Inject
	private VehicleRepository vehicleRepo;

	/** The logger. */
	@Logging
	private final Logger logger = Logger.getLogger(VehicleFinderIT.class);
	private String countermark = "Countermark" + Calendar.getInstance().getTime();
	private String chassis = "Chassis" + Calendar.getInstance().getTime();
	private String regNo = "Registration" + Calendar.getInstance().getTime();

	/**
	 * Test injection.
	 */
	@Test
	public void testInjection() {
		Assertions.assertThat(vehicleFinder).isNotNull();

		logger.info("**********vehicleFinder injected successfully*****************");

		Assertions.assertThat(vehicleRepo).isNotNull();

		logger.info("**********VehicleRepository injected successfully*****************");
	}

	/**
	 * Gets the all vehicle by id.
	 *
	 * @return the all vehicle by id
	 */
	@Test
	public void getAllVehicleById() {

		Vehicle vehicleObj = new Vehicle();

		vehicleObj.setCounterMark("TestIT" + Calendar.getInstance().getTime());
		Vehicle newVehicle = vehicleRepo.saveVehicle(vehicleObj);

		Vehicle vehicleSaved = vehicleFinder.getAllVehicleById(newVehicle.getEntityId());
		assertNotNull(vehicleSaved);

		logger.info("**********getAllVehicleById tested successfully*********");
	}

	/**
	 * Gets the vehicle id.
	 *
	 * @return the vehicle id
	 */
	@Test
	public void getVehicleId() {

		Vehicle vehicleObj = new Vehicle();

		vehicleObj.setCounterMark(countermark);
		vehicleObj.setChasisNumber(chassis);
		vehicleObj.setRegistrationNumber(regNo);

		vehicleRepo.saveVehicle(vehicleObj);

		String getVehicleCondition = Constants.VEHICLE_COUNTERMARK + "= '" + countermark + "' and " + Constants.VEHICLE_CHASISNUMBER + "='" + chassis + "' and " + Constants.VEHICLE_REGISTRATIONNUMBER + "='" + regNo + "'";
		Vehicle newVehicle = vehicleFinder.getVehicleId(getVehicleCondition);

		assertNotNull(newVehicle);

		logger.info("********************getVehicleId tested successfully****************");
	}

}
