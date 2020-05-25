/*
 * Creation : Oct 21, 2016
 */
package com.inetpsa.poc00.infrastructure.vehicle;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.application.vehicle.VehicleService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTestRepository;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehicle.VehicleRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatusRepository;

/**
 * The Class VehicleServiceImpl.
 */
public class VehicleServiceImpl implements VehicleService {

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

	/** The vehicle repo. */
	@Inject
	private VehicleRepository vehicleRepo;

	/** The vehicle file repo. */
	@Inject
	private VehicleFileRepository vehicleFileRepo;

	/** The vehicle file statu repo. */
	@Inject
	private VehicleFileStatusRepository vehicleFileStatuRepo;

	/** The type of test repo. */
	@Inject
	private TypeOfTestRepository typeOfTestRepo;

	/** The traceability service. */
	@Inject
	private TraceabilityService traceabilityService;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.vehicle.VehicleService#createVehicle(com.inetpsa.poc00.domain.vehicle.Vehicle,
	 *      java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public VehicleFile createVehicle(Vehicle vehicleObj, Long typeOfTestId) {
		VehicleFile vehicleFileObj = null;
		boolean vehicleCheck = true;

		if (vehicleObj.getCounterMark() != null && !"".equals(vehicleObj.getCounterMark())) {
			logger.info("Cehcking if vehicle alredy present with counterMark : " + vehicleObj.getCounterMark());
			String condition = ConstantsApp.VEHICLE_COUNTERMARK + "='" + vehicleObj.getCounterMark() + "'";

			List<Vehicle> countermarkCheck = vehicleRepo.checkVehicle(condition);

			if (!countermarkCheck.isEmpty()) {
				vehicleCheck = false;
			}
		}

		if (vehicleObj.getChasisNumber() != null && !"".equals(vehicleObj.getChasisNumber()) && vehicleCheck) {
			logger.info("Cehcking if vehicle alredy present with chesis : " + vehicleObj.getChasisNumber());
			String condition = ConstantsApp.VEHICLE_CHASISNUMBER + "='" + vehicleObj.getChasisNumber() + "'";

			List<Vehicle> chasisCheck = vehicleRepo.checkVehicle(condition);
			if (!chasisCheck.isEmpty()) {
				vehicleCheck = false;
			}
		}

		if (vehicleObj.getRegistrationNumber() != null && !"".equals(vehicleObj.getRegistrationNumber()) && vehicleCheck) {
			logger.info("Cehcking if vehicle alredy present with registrationNumber :" + vehicleObj.getRegistrationNumber());
			String condition = ConstantsApp.VEHICLE_REGISTRATIONNUMBER + "='" + vehicleObj.getRegistrationNumber() + "'";

			List<Vehicle> registraionCheck = vehicleRepo.checkVehicle(condition);
			if (!registraionCheck.isEmpty()) {
				vehicleCheck = false;
			}
		}
		if (vehicleCheck) {
			logger.info("-----------Creating VehicleFile---------------");

			Vehicle newVehicle = vehicleRepo.saveVehicle(vehicleObj);
			vehicleFileObj = createVehicleFile(typeOfTestId, newVehicle);
		}

		return vehicleFileObj;
	}

	/**
	 * Creates the vehicle file.
	 *
	 * @param typeOfTest the type of test
	 * @param vehicleObj the vehicle obj
	 * @return
	 * @return the list
	 */
	@Transactional
	private VehicleFile createVehicleFile(Long typeOfTest, Vehicle vehicleObj) {
		VehicleFile vehicleFileObj = new VehicleFile();
		vehicleFileObj.setVehicle(vehicleObj);
		vehicleFileObj.setTypeOfTest(typeOfTestRepo.load(typeOfTest));
		VehicleFileStatus vehicleFileStatus = vehicleFileStatuRepo.getVehicleFileStatusbyLabel(ConstantsApp.VEHICLEFILESTATUS_LABEL);

		vehicleFileObj.setVehicleFileStatus(vehicleFileStatus);
		vehicleFileObj.setCreationDate(Calendar.getInstance().getTime());
		vehicleFileObj = vehicleFileRepo.saveVehicle(vehicleFileObj);

		return vehicleFileObj;
	}

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public Vehicle saveVehicle(Vehicle vehicleObj) {
		return vehicleRepo.updateVehicle(vehicleObj);

	}
}
