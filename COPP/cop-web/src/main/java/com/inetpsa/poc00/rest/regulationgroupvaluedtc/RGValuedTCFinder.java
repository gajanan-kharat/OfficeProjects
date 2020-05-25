package com.inetpsa.poc00.rest.regulationgroupvaluedtc;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface RGValuedTCFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface RGValuedTCFinder {

	/**
	 * Gets the RG valued test condition.
	 * 
	 * @return the RG valued test condition
	 */
	List<RegGrpValdTestConditionRepresentation> getRGValuedTestCondition();
}
