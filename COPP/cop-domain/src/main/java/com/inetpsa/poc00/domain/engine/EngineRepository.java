package com.inetpsa.poc00.domain.engine;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of Engine.
 */

public interface EngineRepository extends GenericRepository<Engine, Long> {

    /**
     * Saves the Engine.
     * 
     * @param object the Engine to save
     * @return the Engine
     */
    Engine saveEngine(Engine object);

    /**
     * Persists the Engine.
     *
     * @param object the Engine to persist
     * @return the engine
     */
    Engine persistEngine(Engine object);

    /**
     * Delete all categories.
     *
     * @param id the id
     * @return number of categories deleted
     */
    int deleteAll(Long id);

    /**
     * Count the number of categories in the repository.
     * 
     * @return Engine count
     */
    long count();

    /**
     * Gets the all engine.
     *
     * @return the all engine
     */
    public List<Engine> getAllEngine();

    public Engine loadEngine(long engineId);
}
