/*
 * Creation : May 25, 2016
 */
package com.inetpsa.poc00.rest.tvv;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The Interface TvvFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TvvFinder {

	/**
	 * Gets the TV vs with label.
	 * 
	 * @param label the label
	 * @return the TV vs with label
	 */
	List<TvvRepresentation> getTVVsWithLabel(String label);

	/**
	 * Find tv vs by label.
	 * 
	 * @param searchLabel the search label
	 * @return the list
	 */
	List<TvvRepresentation> findTVVsByLabel(String searchLabel);

	/**
	 * Find by criteria.
	 * 
	 * @param searchRepresentation the search representation
	 * @return the list
	 */
	List<TvvRepresentation> findByCriteria(TvvSearchRepresentation searchRepresentation);

	/**
	 * Gets the max version for label.
	 * 
	 * @param tvvLabel the tvv label
	 * @return the max version for label
	 */
	Double getMaxVersionForLabel(String tvvLabel);

	/**
	 * Gets the all tvv data.
	 * 
	 * @return the all tvv data
	 */
	List<TVV> getAllTVVData();

	/**
	 * Gets the tvvfor vehicle.
	 * 
	 * @param testType the test type
	 * @param tvvLabel the tvv label
	 * @return the tvvfor vehicle
	 */
	public List<TvvRepresentation> getTvvforVehicle(String testType, String tvvLabel);

	/**
	 * Find tvv by category id.
	 * 
	 * @param categoryId the category id
	 * @return the list
	 */
	public List<TVV> findTvvByCategoryId(Long categoryId);

	TVV getTvvForVehicleFile(long vFileId);

}
