/*
 * Creation : Sep 20, 2016
 */
package com.inetpsa.poc00.rest.vehiclefile;

import java.util.List;

import org.seedstack.business.finder.Finder;

import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.rest.vehicle.VehicleModelRepresentation;
import com.inetpsa.poc00.rest.vehicle.VehicleSearchRepresentation;

/**
 * The Interface VehicleFileFinder.
 */
@Finder
public interface VehicleFileFinder {

	/**
	 * Gets the vehicle file.
	 * 
	 * @param vehicleId the vehicle id
	 * @param typeOfTestId the type of test id
	 * @return the vehicle file
	 */

	VehicleFile getVehicleFile(Long vehicleId, Long typeOfTestId);

	/**
	 * Gets the vehicle file list.
	 * 
	 * @param vehicleId the vehicle id
	 * @return the vehicle file list
	 */

	List<VehicleFileRepresentation> getVehicleFileList(Long vehicleId);

	/**
	 * Gets the vehicle count.
	 * 
	 * @param basketId the basket id
	 * @return the vehicle count
	 */

	Long getVehicleCount(Long basketId);

	/**
	 * Gets the vehicle file by baket.
	 * 
	 * @param basketId the basket id
	 * @return the vehicle file by baket
	 */

	List<VehicleFileRepresentation> getVehicleFileByBaket(Long basketId);

	/**
	 * get List of status for vehicle file for search.
	 *
	 * @return VehicleFileStatus List
	 */

	List<VehicleFileStatus> getVehicleFileStatus();

	/**
	 * Search vehicle file.
	 *
	 * @param vehicleSearchRepresentation the vehicle search representation
	 * @return the list
	 */

	List<VehicleFileRepresentation> searchVehicleFile(VehicleSearchRepresentation vehicleSearchRepresentation);

	/**
	 * Gets the vehicle search rep.
	 *
	 * @return the vehicle search rep
	 */

	VehicleModelRepresentation getvehicleSearchRep();

	/**
	 * Gets the vehicle file by id.
	 *
	 * @param vehicleId the vehicle id
	 * @return the vehicle file by id
	 */
	VehicleFileRepresentation getVehicleFileById(Long vehicleId);

	/**
	 * Gets the vehiclefile list.
	 *
	 * @param getVehicleCondition the get vehicle condition
	 * @return the vehiclefile list
	 */
	List<VehicleFile> getVehiclefileList(String getVehicleCondition);

	/**
	 * Gets the all retured vehicle files.
	 *
	 * @return the all retured vehicle files
	 */
	List<VehicleFileRepresentation> getAllReturedVehicleFiles();
}
