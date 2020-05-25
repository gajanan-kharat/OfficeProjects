package com.inetpsa.poc00.rest.engine;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;


/**
 * The Interface EngineFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface EngineFinder {

	/**
	 * Find all moture data.
	 *
	 * @return the list
	 */
	List<EngineRepresentation> findAllMotureData();

	/**
	 * Find engine data by label.
	 *
	 * @param label the label
	 * @return the list
	 */
	List<EngineRepresentation> findEngineDataByLabel(String label);

	/**
	 * Find all engine data.
	 *
	 * @return the list
	 */
	List<EngineRepresentation> findAllEngineData();

	/**
	 * Find all engine names.
	 *
	 * @return the list
	 */
	List<String> findAllEngineNames();

	/**
	 * Gets the all engine data.
	 *
	 * @return the all engine data
	 */
	List<EngineRepresentation> getAllEngineData();

	/**
	 * Gets the all engines for tvv.
	 *
	 * @param kmatList the kmat list
	 * @return the all engines for tvv
	 */
	List<EngineRepresentation> getAllEnginesForTvv(List<String> kmatList);
}
