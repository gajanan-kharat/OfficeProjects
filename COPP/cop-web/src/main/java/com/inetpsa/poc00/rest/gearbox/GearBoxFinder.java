package com.inetpsa.poc00.rest.gearbox;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

/**
 * The Interface GearBoxFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface GearBoxFinder {

	/**
	 * Find all gear box data.
	 *
	 * @return the list
	 */
	List<GearBoxRepresentation> findAllGearBoxData();

	/**
	 * Gets the all gear box data.
	 *
	 * @return the all gear box data
	 */
	List<GearBoxRepresentation> getAllGearBoxData();

	/**
	 * Gets the all gear box names.
	 *
	 * @return the all gear box names
	 */
	List<String> getAllGearBoxNames();

	/**
	 * Gets the all gear box values for tvv.
	 *
	 * @param kmatList the kmat list
	 * @return the all gear box values for tvv
	 */
	List<GearBoxRepresentation> getAllGearBoxValuesForTvv(List<String> kmatList);

}
