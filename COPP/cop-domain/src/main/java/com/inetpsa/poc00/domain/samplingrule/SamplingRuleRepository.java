
package com.inetpsa.poc00.domain.samplingrule;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of SamplingRule.
 */

public interface SamplingRuleRepository extends GenericRepository<SamplingRule, Long> {

    /**
     * Saves the SamplingRule.
     *
     * @param object the SamplingRule to save
     * @return the SamplingRule
     */
    SamplingRule saveSamplingRule(SamplingRule object);

    SamplingRule loadSamplingRule(Long samplingId);

    /**
     * Persists the SamplingRule.
     *
     * @param object the SamplingRule to persist
     */
    void persistSamplingRule(SamplingRule object);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return SamplingRule count
     */
    long count();

}
