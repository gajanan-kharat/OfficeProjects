/*
 * Creation : Jun 14, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtdl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;

/**
 * The Interface ValuedGenericDataFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface ValuedGenericDataFinder {

	/**
	 * Gets the all valued data for list.
	 * 
	 * @param listId the list id
	 * @return the all valued data for list
	 */
	public List<TvvValuedTechData> getAllValuedDataForList(long listId);

	/**
	 * Gets the all valued data for ems dep list.
	 * 
	 * @param listId the list id
	 * @return the all valued data for ems dep list
	 */
	public List<TvvValuedTechData> getAllValuedDataForEmsDepList(long listId);

}
