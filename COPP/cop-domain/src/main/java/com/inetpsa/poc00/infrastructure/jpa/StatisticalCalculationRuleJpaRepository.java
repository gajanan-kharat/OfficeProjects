/*
 * Creation : Nov 28, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatisticalCalculationRule;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatsCalcltnRuleRepository;

public class StatisticalCalculationRuleJpaRepository extends BaseJpaRepository<StatisticalCalculationRule, Long> implements StatsCalcltnRuleRepository {

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public StatisticalCalculationRule saveStatCalRule(StatisticalCalculationRule object) {

		return super.save(object);
	}

	@Override
	public void persistStatCalRule(StatisticalCalculationRule object) {
		// TODO Auto-generated method stub

	}

	@Override
	public long deleteAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public StatisticalCalculationRule saveStatCalRule() {
		// TODO Auto-generated method stub
		return null;
	}

}
