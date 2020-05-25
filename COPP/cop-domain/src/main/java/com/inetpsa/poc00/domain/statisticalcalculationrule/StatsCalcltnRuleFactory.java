
package com.inetpsa.poc00.domain.statisticalcalculationrule;

import org.seedstack.business.domain.GenericFactory;


/**
 * Category Factory interface.
 */

public interface StatsCalcltnRuleFactory extends GenericFactory<StatisticalCalculationRule> {
    /**
     * Factory create method.
     *
     * @param name
     * @return the StatisticalCalculationRule
     */
	StatisticalCalculationRule createStatCalculationRule();
	
}
