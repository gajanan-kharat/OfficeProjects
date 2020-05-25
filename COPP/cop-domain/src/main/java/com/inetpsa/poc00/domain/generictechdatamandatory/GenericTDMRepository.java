package com.inetpsa.poc00.domain.generictechdatamandatory;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of GenericTechDataMandatory.
 */

public interface GenericTDMRepository extends GenericRepository<GenericTechDataMandatory, Long> {

    /**
     * Saves the GenericTechDataMandatory.
     *
     * @param object the GenericTechDataMandatory to save
     * @return the GenericTechDataMandatory
     */
    GenericTechDataMandatory saveGenericTDM(GenericTechDataMandatory object);

    /**
     * Persists the GenericTechDataMandatory.
     *
     * @param object the GenericTechDataMandatory to persist
     */
    void persistGenericTDM(GenericTechDataMandatory object);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return GenericTechDataMandatory count
     */
    long count();
}
