package com.inetpsa.poc00.rest.bodywork;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;


/**
 * The Interface BodyWorkFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface BodyWorkFinder {

	/**
	 * Find all silhouette data.
	 *
	 * @return the list
	 */
	List<BodyWorkRepresentation> findAllSilhouetteData();

	/**
	 * Find all body work data.
	 *
	 * @return the list
	 */
	List<BodyWorkRepresentation> findAllBodyWorkData();

	/**
	 * Gets the all body work names.
	 *
	 * @return the all body work names
	 */
	List<String> getAllBodyWorkNames();

	/**
	 * Find body work data by label.
	 *
	 * @param silhoutteLable the silhoutte lable
	 * @return the list
	 */
	List<BodyWorkRepresentation> findBodyWorkDataByLabel(String silhoutteLable);

	/**
	 * Gets the all body work data for tvv.
	 *
	 * @param kmatList the kmat list
	 * @return the all body work data for tvv
	 */
	List<BodyWorkRepresentation> getAllBodyWorkDataForTvv(List<String> kmatList);

}
