package com.inetpsa.poc00.domain.rgvaluedtestcondition;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of RegGrpValdTestCondition.
 */

public interface RegGrpValdTestCondtnRepository extends GenericRepository<RegGrpValdTestCondition, Long> {

    /**
     * Saves the RegGrpValdTestCondition.
     *
     * @param object the RegGrpValdTestCondition to save
     * @return the RegGrpValdTestCondition
     */
    RegGrpValdTestCondition saveRegGrpValdTestCondition(RegGrpValdTestCondition object);

    /**
     * Persists the RegGrpValdTestCondition.
     *
     * @param object the RegGrpValdTestCondition to persist
     */
    void persistRegGrpValdTestCondition(RegGrpValdTestCondition object);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return RegGrpValdTestCondition count
     */
    long count();
}
