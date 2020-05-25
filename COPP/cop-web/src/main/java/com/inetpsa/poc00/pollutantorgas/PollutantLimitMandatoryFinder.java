/*
 * Creation : May 3, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;


/**
 * The Interface PollutantLimitMandatoryFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PollutantLimitMandatoryFinder {

	/**
	 * Gets the mandatory data for limit.
	 *
	 * @param entityId the entity id
	 * @return the mandatory data for limit
	 */
	public List<PollutantLimitMandatoryRepresentation> getMandatoryDataForLimit(long entityId);
}
