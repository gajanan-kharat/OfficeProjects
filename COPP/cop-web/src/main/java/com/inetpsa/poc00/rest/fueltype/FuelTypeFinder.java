package com.inetpsa.poc00.rest.fueltype;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface FuelTypeFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface FuelTypeFinder {

	/**
	 * Find all carburant data.
	 *
	 * @return the list
	 */
	public List<FuelTypeRepresentation> findAllCarburantData();

	/**
	 * Find all fuel type data.
	 *
	 * @return the list
	 */
	List<FuelTypeRepresentation> findAllFuelTypeData();

	/**
	 * Find fuel type data by label.
	 *
	 * @param label the label
	 * @return the list
	 */
	List<FuelTypeRepresentation> findFuelTypeDataByLabel(String label);

	/**
	 * Find all fuel type label.
	 *
	 * @return the list
	 */
	public List<String> findAllFuelTypeLabel();

}
