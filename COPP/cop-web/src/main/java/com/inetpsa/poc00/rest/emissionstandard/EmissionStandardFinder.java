/*
 * Creation : Apr 7, 2016
 */
package com.inetpsa.poc00.rest.emissionstandard;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface EmissionStandardFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface EmissionStandardFinder {

	/**
	 * Gets the all emission standards.
	 * 
	 * @return the all emission standards
	 */
	List<EmissionStandardRepresentation> getAllEmissionStandards();

	/**
	 * Gets the max version for label.
	 * 
	 * @param label the label
	 * @return the max version for label
	 */
	Double getMaxVersionForLabel(String label);

	/**
	 * Find all reglementation data.
	 * 
	 * @return the list
	 */
	List<EmissionStandardRepresentation> findAllReglementationData();

	/**
	 * Gets the emission standards with label.
	 * 
	 * @param label the label
	 * @return the emission standards with label
	 */
	List<EmissionStandardRepresentation> getEmissionStandardsWithLabel(String label);

	/**
	 * Gets the all emission standard names.
	 * 
	 * @return the all emission standard names
	 */
	List<String> getAllEmissionStandardNames();

	/**
	 * Emission standard data.
	 * 
	 * @return the list
	 */
	List<EmissionStandardRepresentation> emissionStandardData();

	/**
	 * Gets the all emission standards for tvv.
	 * 
	 * @return the all emission standards for tvv
	 */
	List<EmissionStandardRepresentation> getAllEmissionStandardsForTVV();

	/**
	 * Gets the emission standard for rg.
	 * 
	 * @return the emission standard for rg
	 */
	List<EmissionStandardRepresentation> getEmissionStandardForRG();

}
