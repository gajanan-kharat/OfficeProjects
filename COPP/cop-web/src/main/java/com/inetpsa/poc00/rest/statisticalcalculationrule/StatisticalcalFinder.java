/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.rest.statisticalcalculationrule;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface StatisticalcalFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)

public interface StatisticalcalFinder {

    /**
     * Gets the allstatistical rule.
     *
     * @return the allstatistical rule
     */
    public List<StatisticalCalculationRuleRepresentation> getAllstatisticalRule();

}
