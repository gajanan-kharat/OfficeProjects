/*
 * Creation : Dec 2, 2016
 */
package com.inetpsa.poc00.rest.statisticalrulesparameters;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface StatisticalParametersFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface StatisticalParametersFinder {

	/**
	 * 
	 * @param statisticalRuleId the statistical rule id
	 * @return the statistical parameter list
	 */
	List<StatisticalParameterRepresentation> getStatisticalParameterList(Long statisticalRuleId);

}
