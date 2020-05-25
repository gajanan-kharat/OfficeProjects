/*
 * Creation : Apr 29, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface GenericConditionMandatoryFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface GenericConditionMandatoryFinder {

	/**
	 * Gets the manadatory list for condition.
	 *
	 * @param entityId the entity id
	 * @return the manadatory list for condition
	 */
	List<GenericConditionMandatoryRepresentation> getManadatoryListForCondition(long entityId);

}
