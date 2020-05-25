package com.inetpsa.poc00.domain.wltpvaluedlowhighdata;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of WLTPVLowHighData.
 */

public interface WLTPVLowHighDataRepository extends GenericRepository<WLTPVLowHighData, Long> {

    /**
     * Saves the WLTPVLowHighData.
     *
     * @param object the WLTPVLowHighData to save
     * @return the WLTPVLowHighData
     */
   public WLTPVLowHighData saveWLTPVLowHighData(WLTPVLowHighData object);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return WLTPVLowHighData count
     */
    long count();
    
}
