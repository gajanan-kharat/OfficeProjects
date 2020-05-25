/*
 * Creation : Dec 2, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.application.receptionfile;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.receptionfile.ReceptionFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Interface ReceptionFileService.
 */
@Service
public interface ReceptionFileService {

	/**
	 * Delete reception file.
	 *
	 * @param id the id
	 * @return the int
	 */
	int deleteReceptionFile(Long id);

	/**
	 * Save reception file.
	 *
	 * @param receptionfile the receptionfile
	 * @param vehicleFileId the vehicle file id
	 * @param parkingLotId the parking lot id
	 * @param parkingNumber the parking number
	 * @return the vehicle file
	 */
	VehicleFile saveReceptionFile(ReceptionFile receptionfile, Long vehicleFileId, Long parkingLotId, String parkingNumber);
}
