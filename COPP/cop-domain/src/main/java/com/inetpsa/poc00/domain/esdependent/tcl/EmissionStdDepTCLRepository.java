package com.inetpsa.poc00.domain.esdependent.tcl;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of EmissionStdDTCL.
 */

public interface EmissionStdDepTCLRepository extends GenericRepository<EmissionStdDepTCL, Long> {

    /**
     * Delete all categories.
     * 
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     * 
     * @return EmissionStdDTCL count
     */
    long count();

    /**
     * Saves the EmissionStdDTCL.
     * 
     * @param emsDepTCL the ems dep tcl
     * @return the EmissionStdDTCL
     */
    EmissionStdDepTCL saveEmissonStdDepTCL(EmissionStdDepTCL emsDepTCL);

    /**
     * Delete ems dep tcl.
     * 
     * @param id the id
     */
    void deleteEmsDepTCL(long id);

    /**
     * Gets the latest emission standard dep lists.
     * 
     * @param emsId the ems id
     * @return the latest emission standard dep lists
     */
    List<EmissionStdDepTCL> getLatestEmissionStandardDepLists(Long emsId);

    /**
     * Gets the emission standard dep lists.
     * 
     * @param esEntityId the es entity id
     * @return the emission standard dep lists
     */
    List<EmissionStdDepTCL> getEmissionStandardDepLists(Long esEntityId);

    /**
     * Gets the max version for label.
     * 
     * @param label the label
     * @return the max version for label
     */
    Double getMaxVersionForLabel(String label);

    public EmissionStdDepTCL loadEmsDepTcl(long emsDepTclId);
}
