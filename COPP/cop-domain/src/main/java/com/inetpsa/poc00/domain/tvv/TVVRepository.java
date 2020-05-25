package com.inetpsa.poc00.domain.tvv;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of TechDBTVV.
 */

public interface TVVRepository extends GenericRepository<TVV, Long> {

    /**
     * Saves the TechDBTVV.
     * 
     * @param object the TechDBTVV to save
     * @return the TechDBTVV
     */
    TVV saveTVV(TVV object);

    /**
     * Persists the TechDBTVV.
     * 
     * @param object the TechDBTVV to persist
     */
    void persistTVV(TVV object);

    /**
     * Delete all categories.
     * 
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     * 
     * @return TechDBTVV count
     */
    long count();

    /**
     * Gets the all tvv label.
     * 
     * @return the all tvv label
     */
    List<String> getAllTVVLabel();

    /**
     * Gets the max version for label.
     *
     * @param label the label
     * @return the max version for label
     */
    double getMaxVersionForLabel(String label);

    /**
     * Find tvv by category id.
     *
     * @param categoryId the category id
     * @return the list
     */
    public List<TVV> findTvvByCategoryId(Long categoryId);

    public TVV loadTVV(long tvvId);

    public void deleteTvv(long tvvId);
}
