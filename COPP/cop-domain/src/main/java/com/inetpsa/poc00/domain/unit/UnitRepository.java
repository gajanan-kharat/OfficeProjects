package com.inetpsa.poc00.domain.unit;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of Unit.
 */

public interface UnitRepository extends GenericRepository<Unit, Long> {

    /**
     * Saves the Unit.
     *
     * @param object the Unit to save
     * @return the Unit
     */
    Unit saveUnit(Unit object);

    /**
     * Persists the Unit.
     *
     * @param object the Unit to persist
     */
    void persistUnit(Unit object);

    /**
     * Delete all categories
     *
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     *
     * @return Unit count
     */
    long count();
}
