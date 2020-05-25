package com.inetpsa.poc00.domain.statisticalcalculationrule;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of StatisticalCalculationRule.
 */

public interface StatsCalcltnRuleRepository extends GenericRepository<StatisticalCalculationRule, Long> {

	/**
	 * Saves the GenomeLCDVDictionary.
	 *
	 * @param object the StatisticalCalculationRule to save
	 * @return the StatisticalCalculationRule
	 */
	StatisticalCalculationRule saveStatCalRule(StatisticalCalculationRule object);

	/**
	 * Persists the StatisticalCalculationRule.
	 *
	 * @param object the StatisticalCalculationRule to persist
	 */
	void persistStatCalRule(StatisticalCalculationRule object);

	/**
	 * Delete all categories
	 *
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 *
	 * @return StatisticalCalculationRule count
	 */
	long count();

	StatisticalCalculationRule saveStatCalRule();
}
