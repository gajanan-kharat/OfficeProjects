package com.inetpsa.poc00.rest.fuel;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.fuel.Fuel;

/**
 * The Interface FuelFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface FuelFinder {

	/**
	 * Gets the all fuel.
	 * 
	 * @return the all fuel
	 */
	List<FuelRepresentation> getAllFuel();

	/**
	 * Gets the all fuels.
	 * 
	 * @return the all fuels
	 */
	List<FuelRepresentation> getAllFuels();

	/**
	 * Gets the all fuels for es.
	 * 
	 * @param emsId the ems id
	 * @return the all fuels for es
	 */
	List<FuelRepresentation> getAllFuelsForES(long emsId);

	/**
	 * Gets the all fuels for fc list.
	 * 
	 * @param entityId the entity id
	 * @return the all fuels for fc list
	 */
	List<FuelRepresentation> getAllFuelsForFCList(long entityId);

	/**
	 * Gets the all fuels for copied es.
	 * 
	 * @param entityId the entity id
	 * @return the all fuels for copied es
	 */
	List<Fuel> getAllFuelsForCopiedES(Long entityId);

	/**
	 * Gets the all fuel names.
	 * 
	 * @return the all fuel names
	 */
	List<String> getAllFuelNames();

	/**
	 * Gets the all fuel for copied pg list.
	 * 
	 * @param entityId the entity id
	 * @return the all fuel for copied pg list
	 */
	List<Fuel> getAllFuelForCopiedPGList(Long entityId);

	/**
	 * Fuel data.
	 * 
	 * @return the list
	 */
	List<FuelRepresentation> getFuelData();

	/**
	 * Gets the all fuel for copied fc list.
	 * 
	 * @param entityId the entity id
	 * @return the all fuel for copied fc list
	 */
	List<Fuel> getAllFuelForCopiedFCList(Long entityId);

	List<FuelRepresentation> getAllFuelDataForTVV(List<String> kmatList);

}
