package com.inetpsa.poc00.rest.vehicletechnology;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.vehicletechnology.VehicleTechnology;


/**
 * The Interface VehicleTechnologyFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface VehicleTechnologyFinder {

	/**
	 * Gets the all vehicle technologies.
	 * 
	 * @return the all vehicle technologies
	 */
	public List<VehicleTechnologyRepresentation> getAllVehicleTechnologies();

	/**
	 * Gets the all v technologies for es.
	 * 
	 * @param id the id
	 * @return the all v technologies for es
	 */
	public List<VehicleTechnologyRepresentation> getAllVTechnologiesForES(long id);

	/**
	 * Gets the all v technologies for fc list.
	 * 
	 * @param fcListId the fc list id
	 * @return the all v technologies for fc list
	 */
	public List<VehicleTechnologyRepresentation> getAllVTechnologiesForFCList(long fcListId);

	/**
	 * Gets the all technologies for copied es.
	 * 
	 * @param entityId the entity id
	 * @return the all technologies for copied es
	 */
	public List<VehicleTechnology> getAllTechnologiesForCopiedES(Long entityId);

	/**
	 * Gets the all vt for copied pg list.
	 * 
	 * @param entityId the entity id
	 * @return the all vt for copied pg list
	 */
	public List<VehicleTechnology> getAllVTForCopiedPGList(Long entityId);

	/**
	 * Find vehicle technology data by label.
	 * 
	 * @param label the label
	 * @return the list
	 */
	public List<VehicleTechnologyRepresentation> findVehicleTechnologyDataByLabel(String label);

	/**
	 * Gets the all vt for copied fc list.
	 * 
	 * @param entityId the entity id
	 * @return the all vt for copied fc list
	 */
	public List<VehicleTechnology> getAllVTForCopiedFCList(Long entityId);
}
