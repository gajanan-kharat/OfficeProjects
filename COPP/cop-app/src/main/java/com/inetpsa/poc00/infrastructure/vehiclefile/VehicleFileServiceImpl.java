/*
 * Creation : Dec 2, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.vehiclefile;

import javax.inject.Inject;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.application.vehiclefile.VehicleFileService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.archivebox.ArchiveBoxRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatusRepository;

/**
 * VehicleFileServiceImpl.
 *
 * @author mehaj
 */
public class VehicleFileServiceImpl implements VehicleFileService {

	/** The vehicle file repository. */
	@Inject
	VehicleFileRepository vehicleFileRepository;

	/** The archive box repo. */
	@Inject
	ArchiveBoxRepository archiveBoxRepo;

	/** The vehicle file status repo. */
	@Inject
	VehicleFileStatusRepository vehicleFileStatusRepo;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(VehicleFileServiceImpl.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.vehiclefile.VehicleFileService#saveVehicleFile(com.inetpsa.poc00.domain.vehiclefile.VehicleFile)
	 */
	@Override
	public VehicleFile saveVehicleFile(VehicleFile vehicleFileObj) {
		return vehicleFileRepository.saveVehicle(vehicleFileObj);
	}

	/**
	 * {@inheritDoc}
	 * 
     * @see com.inetpsa.poc00.application.vehiclefile.VehicleFileService#vehicleFileArchive(java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.Long, java.lang.Long)
	 */
	@Override
	public VehicleFile vehicleFileArchive(String chassis, String counterMark, String registration, Long typeOfTestId, Long archiveBoxId) {

		String getVehicleCondition = "";
		if (!"null".equals(counterMark)) {
			getVehicleCondition = getVehicleCondition + ConstantsApp.VEHICLE_COUNTERMARK_ARCHIVE + "='" + counterMark + "' AND ";
		}
		if (!"null".equals(chassis)) {
			getVehicleCondition = getVehicleCondition + ConstantsApp.VEHICLE_CHASISNUMBER_ARCHIVE + "='" + chassis + "' AND ";
		}

		if (!"null".equals(registration)) {
			getVehicleCondition = getVehicleCondition + ConstantsApp.VEHICLE_REGISTRATIONNUMBER_ARCHIVE + "='" + registration + "' AND ";
		}

		getVehicleCondition = getVehicleCondition + ConstantsApp.VEHICLE_TYPEOFTEST_ARCHIVE + "='" + typeOfTestId + "'";
		logger.info("Trying to get VehicleFile with " + getVehicleCondition);

		VehicleFile vehicleFile = vehicleFileRepository.getVehicleFileByLabel(getVehicleCondition);

		if (vehicleFile == null)
			return null;

		VehicleFileStatus vfstatus = vehicleFile.getVehicleFileStatus();
		vehicleFile.setBasket(null);
		vehicleFile.setArchiveBox(archiveBoxRepo.load(archiveBoxId));
		vehicleFile.setVehicleFileStatus(vehicleFileStatusRepo.getVehicleFileStatusbyLabel(ConstantsApp.VEHICLEFILE_STAUS_ARCHIVED));
		vehicleFile.setPreviousVFStatus(vfstatus);

		return vehicleFileRepository.saveVehicleReception(vehicleFile);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.vehiclefile.VehicleFileService#removeVehicleFile(java.lang.Long)
	 */
	@Override
	public VehicleFile removeVehicleFile(Long vehicleFileId) {

		VehicleFile vehicleFile = vehicleFileRepository.loadVehicle(vehicleFileId);

		vehicleFile.setArchiveBox(null);
		vehicleFile.setVehicleFileStatus(vehicleFile.getPreviousVFStatus());
		return vehicleFileRepository.saveVehicleReception(vehicleFile);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.vehiclefile.VehicleFileService#returnVehicleFile(java.lang.Long)
	 */
	@Override
	public VehicleFile returnVehicleFile(Long vehicleFileId) {

		VehicleFile vehicleFile = vehicleFileRepository.load(vehicleFileId);

		VehicleFileStatus previousVfStatus = vehicleFile.getPreviousVFStatus();

		if (previousVfStatus == null) {
			return null;
		}

		vehicleFile.setPreviousVFStatus(vehicleFile.getVehicleFileStatus());
		vehicleFile.setVehicleFileStatus(previousVfStatus);

		vehicleFile = vehicleFileRepository.saveVehicle(vehicleFile);

		return vehicleFile;
	}

}
