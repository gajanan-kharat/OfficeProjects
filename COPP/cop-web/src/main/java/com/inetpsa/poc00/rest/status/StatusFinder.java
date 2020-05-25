package com.inetpsa.poc00.rest.status;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;

import com.inetpsa.poc00.domain.status.Status;

/**
 * The Interface StatusFinder.
 */
@Finder
@Transactional
public interface StatusFinder {

    /**
     * Gets the all status.
     * 
     * @return the all status
     */
    public List<StatusRepresentation> getAllStatus();

    /**
     * Gets the status for emission standard.
     * 
     * @param draft the draft
     * @return the status for emission standard
     */
    public Status getStatusForEmissionStandard(String draft);

    /**
     * Gets the status for label.
     * 
     * @param statusLabel the status label
     * @return the status for label
     */

    StatusRepresentation getStatusForLabel(String statusLabel);

    /**
     * Find status by label.
     * 
     * @param statusLabel the status label
     * @return the status
     */
    public Status findStatusByLabel(String statusLabel);

    /**
     * Gets the all status natures for tvv.
     * 
     * @return the all status natures for tvv
     */
    public List<Status> getAllStatusNaturesForTvv();

}
