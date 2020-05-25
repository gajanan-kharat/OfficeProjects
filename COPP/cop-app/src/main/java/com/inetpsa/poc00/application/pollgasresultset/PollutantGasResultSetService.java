/*
 * Creation : Nov 23, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.application.pollgasresultset;

import java.util.List;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;

/**
 * TvvValuedPollGasLimitService Interface.
 *
 * @author mehaj
 */
@Service
public interface PollutantGasResultSetService {

	/**
	 * Save pollutant gas result set.
	 *
	 * @param pollutantGasResultSet the pollutant gas result set
	 * @return the pollutant gas result set
	 */
	PollutantGasResultSet savePollutantGasResultSet(PollutantGasResultSet pollutantGasResultSet);

	/**
	 * Creates the pollutant gas result set.
	 *
	 * @param vehicleFileObj the vehicle file obj
	 * @param tvvObj the tvv obj
	 * @param resultSetListSize the result set list size
	 * @return the list
	 */
	List<PollutantGasResultSet> createPollutantGasResultSet(VehicleFile vehicleFileObj, TVV tvvObj, int resultSetListSize);

	/**
	 * Gets the poll gas result set.
	 *
	 * @param vehicleFileObj the vehicle file obj
	 * @return the poll gas result set
	 */
	List<PollutantGasResultSet> getPollGasResultSet(VehicleFile vehicleFileObj);

	/**
	 * Save pollutant gas result set values.
	 *
	 * @param pollGasResultSetToSave the poll gas result set to save
	 * @return the pollutant gas result set
	 */
	PollutantGasResultSet savePollutantGasResultSetValues(PollutantGasResultSet pollGasResultSetToSave);

	/**
	 * Change qurantine flag.
	 *
	 * @param entityId the entity id
	 * @param inQuarantine the in quarantine
	 */
	void changeQurantineFlag(Long entityId, boolean inQuarantine);
}
