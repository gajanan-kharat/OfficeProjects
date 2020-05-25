/*
 * Creation : Dec 8, 2016
 */
package com.inetpsa.poc00.rest.resultset;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface PollGasResultSetFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PollGasResultSetFinder {

	/**
	 * Gets the pollutant gas result sets rep.
	 *
	 * @param vehicleFileId the vehicle file id
	 * @return the pollutant gas result sets rep
	 */
	List<PollGasResultSetRepresentation> getPollutantGasResultSetsRep(Long vehicleFileId);
}
