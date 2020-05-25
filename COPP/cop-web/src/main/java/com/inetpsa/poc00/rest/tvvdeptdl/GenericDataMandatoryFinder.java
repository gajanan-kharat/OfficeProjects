/*
 * Creation : Apr 29, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface GenericDataMandatoryFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface GenericDataMandatoryFinder {

	/**
	 * Gets the mandatory list for data.
	 * 
	 * @param data_id the data id
	 * @return the mandatory list for data
	 */
	List<GenericTechDataMandatoryRepresentation> getMandatoryListForData(long dataId);
}
