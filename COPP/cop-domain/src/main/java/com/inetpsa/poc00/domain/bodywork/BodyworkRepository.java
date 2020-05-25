package com.inetpsa.poc00.domain.bodywork;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of Bodywork.
 */

public interface BodyworkRepository extends GenericRepository<Bodywork, Long> {

    /**
     * Saves the BodyWork.
     * 
     * @param object the BodyWork to save
     * @return the BodyWork
     */
    Bodywork saveBodywork(Bodywork object);

    /**
     * Persists the BodyWork.
     *
     * @param object the BodyWork to persist
     * @return the bodywork
     */
    Bodywork persistBodywork(Bodywork object);

    /**
     * Delete all categories.
     *
     * @param id the id
     * @return number of categories deleted
     */
    int deleteAll(long id);

    /**
     * Gets the all body work.
     *
     * @return the all body work
     */
    public List<Bodywork> getAllBodyWork();

    public Bodywork loadBodyWork(long bodyWorkId);

}
