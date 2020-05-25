
package com.inetpsa.poc00.infrastructure.receptionfile;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.receptionfile.ReceptionFileService;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.parkinglot.ParkingLot;
import com.inetpsa.poc00.domain.parkinglot.ParkingLotRepository;
import com.inetpsa.poc00.domain.receptionfile.ReceptionFile;
import com.inetpsa.poc00.domain.receptionfile.ReceptionFileRepository;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehicle.VehicleRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;

/**
 * The Class ReceptionFileServiceImpl.
 */
public class ReceptionFileServiceImpl implements ReceptionFileService {

	/** The tire brand repository. */
	@Inject
	ReceptionFileRepository receptionFileRepository;
	/** The parking lot repo. */
	@Inject
	ParkingLotRepository parkingLotRepo;

	/** The vehicle file repo. */
	@Inject
	VehicleFileRepository vehicleFileRepo;

	/** The vehicle repo. */
	@Inject
	VehicleRepository vehicleRepo;

	@Inject
	TraceabilityService historyService;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.SendEmailService.ReceptionFileService#deleteReceptionFile(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public int deleteReceptionFile(Long id) {

		int deletedRows = receptionFileRepository.deleteReceptionFile(id);
		if (deletedRows > 0) {

			return deletedRows;
		}

		return -1;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.receptionfile.ReceptionFileService#saveReceptionFile(com.inetpsa.poc00.domain.receptionfile.ReceptionFile,
	 *      java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)

	public VehicleFile saveReceptionFile(ReceptionFile receptionfile, Long vehicleFileId, Long parkingLotId, String parkingNumber) {

		ReceptionFile updatedReceptionFile = receptionFileRepository.saveReceptionFile(receptionfile);

		VehicleFile vehicleFile = vehicleFileRepo.load(vehicleFileId);
		vehicleFile.setReceptionFile(updatedReceptionFile);
		vehicleFileRepo.saveVehicleReception(vehicleFile);

		ParkingLot plot = new ParkingLot();
		plot.setEntityId(parkingLotId);
		plot.setParkingNumber(parkingNumber);

		ParkingLot updatedParkingLot = parkingLotRepo.saveParkingLot(plot);

		Vehicle vehicle = vehicleFile.getVehicle();
		vehicle.setParkingLot(updatedParkingLot);
		vehicleRepo.saveVehicle(vehicle);
		historyService.saveVehicleFileHistory(vehicleFile, null, ConstantsApp.RECEPTIONFILE_DESCRIPTION);

		return vehicleFile;

	}

}
