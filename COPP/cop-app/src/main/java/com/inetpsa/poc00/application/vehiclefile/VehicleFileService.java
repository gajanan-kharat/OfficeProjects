/*
 * Creation : Dec 2, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.application.vehiclefile;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * VehicleFileService.
 *
 * @author mehaj
 */
@Service
public interface VehicleFileService {

    /**
     * Save vehicle file.
     *
     * @param vehicleFileObj the vehicle file obj
     * @return the vehicle file
     */
    VehicleFile saveVehicleFile(VehicleFile vehicleFileObj);

    /**
     * Vehicle file archive.
     *
     * @param chassis the chassis
     * @param counterMark the counter mark
     * @param registration the registration
     * @param typeOfTestId the type of test id
     * @param archiveBoxId the archive box id
     * @return the vehicle file
     */
    public VehicleFile vehicleFileArchive(String chassis, String counterMark, String registration, Long typeOfTestId, Long archiveBoxId);

    /**
     * Removes the vehicle file.
     *
     * @param vehicleFileId the vehicle file id
     * @return the vehicle file
     */
    public VehicleFile removeVehicleFile(Long vehicleFileId);

	/**
	 * Return vehicle file.
	 *
	 * @param vehicleFileId the vehicle file id
	 * @return the vehicle file
	 */
	VehicleFile returnVehicleFile(Long vehicleFileId);;
}
