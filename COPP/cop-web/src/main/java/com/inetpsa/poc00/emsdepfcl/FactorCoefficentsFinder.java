/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.factorcoefficient.FactorCoefficents;


/**
 * The Interface FactorCoefficentsFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface FactorCoefficentsFinder {

	/**
	 * Gets the all data for EMS dep list.
	 *
	 * @param entityId the entity id
	 * @return the all data for EMS dep list
	 */
	public List<FactorCoefficentsRepresentation> getAllDataForEMSDepList(long entityId);

	/**
	 * Gets the all F actors for dep list.
	 *
	 * @param entityId the entity id
	 * @return the all F actors for dep list
	 */
	List<FactorCoefficents> getAllFActorsForDepList(long entityId);

}
