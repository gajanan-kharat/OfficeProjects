package com.inetpsa.poc00.domain.status;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of Status.
 */

public interface StatusRepository extends GenericRepository<Status, Long> {

    /**
     * Saves the Status.
     * 
     * @param object the Status to save
     * @return the Status
     */
    Status saveStatus(Status object);

    /**
     * Persists the Status.
     * 
     * @param object the Status to persist
     */
    void persistStatus(Status object);

    /**
     * Delete all categories.
     * 
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     * 
     * @return Status count
     */
    long count();

    /**
     * Delete status.
     * 
     * @param statusid the statusid
     * @return the long
     */
    long deleteStatus(Long statusid);

    /**
     * Gets the all status.
     * 
     * @return the all status
     */
    public List<Status> getAllStatus();

    public Status getStatus(String statusLabel);

	/**
	 * Gets the status for emission standard.
	 *
	 * @param draft the draft
	 * @return the status for emission standard
	 */
	Status getStatusForEmissionStandard(String draft);
}
