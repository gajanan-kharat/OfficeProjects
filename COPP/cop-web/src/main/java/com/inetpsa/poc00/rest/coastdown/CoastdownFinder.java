package com.inetpsa.poc00.rest.coastdown;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface CoastdownFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface CoastdownFinder {

	/**
	 * Gets the all coastdown representation.
	 * 
	 * @return the all coastdown representation
	 */
	public List<CoastdownRepresentation> getAllCoastdownRepresentation();

	/**
	 * Gets the all coastdown labels.
	 * 
	 * @return the all coastdown labels
	 */
	public List<String> getAllCoastdownLabels();

	/**
	 * Gets the coastdown tvv.
	 * 
	 * @return the coastdown tvv
	 */
	public List<CoastdownRepresentation> getCoastdownTvv();

}
