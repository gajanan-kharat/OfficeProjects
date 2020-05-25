/*
 * Creation : Oct 24, 2016
 */
package com.inetpsa.poc00.application.traceability;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.traceability.Traceability;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * The Interface TraceabilityService.
 */
@Service
public interface TraceabilityService {

	/**
	 * Save vehicle file history.
	 *
	 * @param vehicleFile the vehicle file
	 * @param newValue the new value
	 * @param description the description
	 */
	public void saveVehicleFileHistory(VehicleFile vehicleFile, String newValue, String description);

	/**
	 * History for save.
	 *
	 * @param obj the obj
	 * @param screenId the screen id
	 * @return the traceability
	 */
	public Traceability historyForSave(Object obj, String screenId);

	/**
	 * History for delete.
	 *
	 * @param obj the obj
	 * @param screenId the screen id
	 */
	public void historyForDelete(Object obj, String screenId);

	/**
	 * History for update.
	 *
	 * @param oldObject the old object
	 * @param updatedObject the updated object
	 * @param screenId the screen id
	 * @return the traceability
	 */
	public Traceability historyForUpdate(Object oldObject, Object updatedObject, String screenId);

	/**
	 * History for update.
	 *
	 * @param oldObject the old object
	 * @param updatedObject the updated object
	 * @param screenId the screen id
	 * @param ignoreChildCollections the ignore child collections
	 * @return the traceability
	 */
	Traceability historyForUpdate(Object oldObject, Object updatedObject, String screenId, boolean ignoreChildCollections);

}
