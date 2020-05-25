package com.inetpsa.poc00.domain.tvvdependent;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of dictionary.
 */

public interface TvvDepTCLRepository extends GenericRepository<TvvDepTCL, Long> {

    /**
     * Save tvv dep tcl.
     * 
     * @param object the object
     * @return the tvv dep tcl
     */
    TvvDepTCL saveTvvDepTCL(TvvDepTCL object);

    /**
     * Persist tvv dep tcl.
     * 
     * @param object the object
     */
    void persistTvvDepTCL(TvvDepTCL object);

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
     * Delete tvv dep tcl.
     * 
     * @param id the id
     */
    void deleteTvvDepTCL(long id);

    /**
     * Gets the all tvv dependent lists.
     *
     * @return the all tvv dependent lists
     */
    List<TvvDepTCL> getAllTvvDependentLists();

    public TvvDepTCL loadTvvDepTcl(long tvvDepTclId);

}
