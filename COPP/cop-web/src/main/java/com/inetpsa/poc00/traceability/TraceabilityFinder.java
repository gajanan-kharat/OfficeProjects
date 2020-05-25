package com.inetpsa.poc00.traceability;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface TraceabilityFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TraceabilityFinder {

	/**
	 * Gets the all history.
	 * 
	 * @return the all history
	 */
	public List<TraceabilityRepresentation> getAllHistory();

	/**
	 * Gets the all history.
	 * 
	 * @param screenId the screen id
	 * @return the all history by screenId
	 */
	List<TraceabilityRepresentation> getAllHistory(String screenId);

	/**
	 * Gets the vehicle file history.
	 * 
	 * @param vehicleFileId the vehicle file id
	 * @return the vehicle file history
	 */
	List<TraceabilityRepresentation> getVehicleFileHistory(Long vehicleFileId);

	/**
	 * Gets the tvv history.
	 *
	 * @param tvvEntityId the tvv entity id
	 * @return the tvv history
	 */
	List<TraceabilityRepresentation> getTvvHistory(Long tvvEntityId);

}
