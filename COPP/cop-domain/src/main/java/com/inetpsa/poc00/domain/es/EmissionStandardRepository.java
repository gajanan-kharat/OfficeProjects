package com.inetpsa.poc00.domain.es;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of EmissionStandard.
 */

public interface EmissionStandardRepository extends GenericRepository<EmissionStandard, Long> {

    /**
     * Persists the EmissionStandard.
     * 
     * @param object the object
     * @return the emission standard
     */
    EmissionStandard persistEmissionStandard(EmissionStandard object);

    /**
     * Delete all categories.
     * 
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     * 
     * @return EmissionStandard count
     */
    long count();

    /**
     * Save emission standard.
     * 
     * @param emissionStandard the emission standard
     * @return the emission standard
     */
    EmissionStandard saveEmissionStandard(EmissionStandard emissionStandard);

    /**
     * Delete emission standard.
     * 
     * @param entityId the entity id
     * @return the int
     */
    int deleteEmissionStandard(long entityId);

    /**
     * Delete all.
     * 
     * @param ruleId the rule id
     * @return the long
     */
    int deleteAll(long ruleId);

    /**
     * Save emission std.
     * 
     * @param emissionStandard the emission standard
     * @return the emission standard
     */
    EmissionStandard saveEmissionStd(EmissionStandard emissionStandard);

    /**
     * Gets the all emission standards.
     * 
     * @return the all emission standards
     */
    public List<EmissionStandard> getAllEmissionStandards();

    /**
     * Gets the emission standard by label.
     *
     * @param esLabel the es label
     * @return the emission standard by label
     */
    public List<EmissionStandard> getEmissionStandardByLabel(String esLabel);

    /**
     * Gets the max version for label.
     * 
     * @param label the label
     * @return the max version for label
     */
    Double getMaxVersionForLabel(String label);

    EmissionStandard loadEmission(long emissionId);

}
