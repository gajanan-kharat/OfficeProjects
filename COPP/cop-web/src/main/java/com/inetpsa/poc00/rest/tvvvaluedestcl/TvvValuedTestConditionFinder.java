/*
 * Creation : Jun 15, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedestcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition;

/**
 * The Interface TvvValuedTestConditionFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TvvValuedTestConditionFinder {

	/**
	 * Gets the all valued condition for list.
	 * 
	 * @param listId the list id
	 * @return the all valued condition for list
	 */
	List<TvvValuedTestCondition> getAllValuedConditionForList(long listId);

	/**
	 * Gets the all valued condition for ems dep list.
	 * 
	 * @param listId the list id
	 * @return the all valued condition for ems dep list
	 */
	List<TvvValuedTestCondition> getAllValuedConditionForEmsDepList(long listId);

}
