package com.inetpsa.poc00.domain.esdependent.tdl;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of EmissonStdDepTDL.
 */

public interface EmissionStdDepTDLRepository extends GenericRepository<EmissionStdDepTDL, Long> {

    /**
     * Saves the EmissonStdDepTDL.
     * 
     * @param object the EmissonStdDepTDL to save
     * @return the EmissonStdDepTDL
     */
    EmissionStdDepTDL saveEmissonStdDepTDL(EmissionStdDepTDL object);

    /**
     * Delete all categories.
     * 
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     * 
     * @return EmissonStdDepTDL count
     */
    long count();

    /**
     * Delete ems dep tdl.
     * 
     * @param entityId the entity id
     */
    void deleteEmsDepTDL(long entityId);

    /**
     * Gets the latest emission standard dep lists.
     * 
     * @param emsId the ems id
     * @return the latest emission standard dep lists
     */
    List<EmissionStdDepTDL> getLatestEmissionStandardDepLists(Long emsId);

    /**
     * Gets the emission standard dep lists.
     * 
     * @param esEntityId the es entity id
     * @return the emission standard dep lists
     */
    List<EmissionStdDepTDL> getEmissionStandardDepLists(Long esEntityId);

    /**
     * Gets the max version for label.
     * 
     * @param label the label
     * @return the max version for label
     */
    Double getMaxVersionForLabel(String label);

    public EmissionStdDepTDL loadEmsDepTdl(long emsDepTdlId);

}
