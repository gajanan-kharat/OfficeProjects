package com.inetpsa.poc00.domain.tvvdependent;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of dictionary.
 */

public interface TvvDepTDLRepository extends GenericRepository<TvvDepTDL, Long> {

    /**
     * Save tvv dep tdl.
     * 
     * @param object the object
     * @return the tvv dep tdl
     */
    TvvDepTDL saveTvvDepTDL(TvvDepTDL object);

    /**
     * Persist tvv dep tdl.
     * 
     * @param object the object
     */
    void persistTvvDepTDL(TvvDepTDL object);

    /**
     * Delete all.
     * 
     * @return the long
     */
    long deleteAll();

    /**
     * Count.
     * 
     * @return the long
     */
    long count();

    /**
     * Delete tvv dep tdl.
     * 
     * @param entityId the entity id
     */
    void deleteTvvDepTDL(long entityId);

    /**
     * Creates the new version.
     * 
     * @param tvvDepTDL the tvv dep tdl
     * @return the tvv dep tdl
     */
    TvvDepTDL createNewVersion(TvvDepTDL tvvDepTDL);

    /**
     * Gets the all tvv dependent list by latest ver.
     *
     * @return the all tvv dependent list by latest ver
     */
    List<TvvDepTDL> getAllTvvDependentListByLatestVer();

    public TvvDepTDL loadTvvDepTDL(long tvvDepTDLId);
}
