package com.inetpsa.poc00.domain.rgvaluedlowhighdata;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of RegGrpValVLowHighData.
 */

public interface RegGrpValVLowHighDataRepository extends GenericRepository<RegGrpValVLowHighData, Long> {

    /**
     * Saves the RegGrpValVLowHighData.
     *
     * @param object the RegGrpValVLowHighData to save
     * @return the RegGrpValVLowHighData
     */
    RegGrpValVLowHighData saveRegGrpValVLowHighData(RegGrpValVLowHighData object);

    /**
     * Persists the RegGrpValVLowHighData.
     *
     * @param object the RegGrpValVLowHighData to persist
     */
    void persistRegGrpValVLowHighData(RegGrpValVLowHighData object);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return RegGrpValVLowHighData count
     */
    long count();
}
