package com.inetpsa.poc00.domain.traceability;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of Traceability.
 */

public interface TraceabilityRepository extends GenericRepository<Traceability, Long> {

    /**
     * Saves the Traceability.
     *
     * @param object the Traceability to save
     * @return the Traceability
     */
    Traceability saveTraceability(Traceability object);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return Traceability count
     */
    long count();
}
