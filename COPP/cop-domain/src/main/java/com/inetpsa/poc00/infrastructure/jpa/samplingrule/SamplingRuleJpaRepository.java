package com.inetpsa.poc00.infrastructure.jpa.samplingrule;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.samplingrule.SamplingRule;
import com.inetpsa.poc00.domain.samplingrule.SamplingRuleRepository;

/**
 * The Class SamplingRuleJpaRepository.
 */
public class SamplingRuleJpaRepository extends BaseJpaRepository<SamplingRule, Long> implements SamplingRuleRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.samplingrule.SamplingRuleRepository#saveSamplingRule(com.inetpsa.poc00.domain.samplingrule.SamplingRule)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public SamplingRule saveSamplingRule(SamplingRule object) {

		return super.save(object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.samplingrule.SamplingRuleRepository#persistSamplingRule(com.inetpsa.poc00.domain.samplingrule.SamplingRule)
	 */
	@Override
	public void persistSamplingRule(SamplingRule object) {
		// NOT IN USE

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.samplingrule.SamplingRuleRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.samplingrule.SamplingRuleRepository#count()
	 */
	@Override
	public long count() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.samplingrule.SamplingRuleRepository#loadSamplingRule(java.lang.Long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public SamplingRule loadSamplingRule(Long samplingId) {

		return super.load(samplingId);
	}

}
