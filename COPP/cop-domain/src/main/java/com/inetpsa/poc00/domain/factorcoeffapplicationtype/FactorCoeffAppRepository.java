package com.inetpsa.poc00.domain.factorcoeffapplicationtype;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of FactorCoeffApplicationType.
 */

public interface FactorCoeffAppRepository extends GenericRepository<FactorCoeffApplicationType, Long> {

    /**
     * Saves the FactorCoeffApplicationType.
     * 
     * @param object the FactorCoeffApplicationType to save
     * @return the FactorCoeffApplicationType
     */
    FactorCoeffApplicationType saveFactorCoeffAppType(FactorCoeffApplicationType object);

    /**
     * Delete all categories.
     * 
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     * 
     * @return FactorCoeffApplicationType count
     */
    long count();

    /**
     * Delete factor coeff app type.
     * 
     * @param id the id
     * @return the int
     */
    int deleteFactorCoeffAppType(Long id);

    /**
     * getFactorCoeffAppTypeDataByLabel
     * 
     * @param String factorCoeffAppTypeLabel
     * @return FactorCoeffApplicationType
     */
    List<FactorCoeffApplicationType> getFactorCoeffAppTypeDataByLabel(String factorCoeffAppTypeLabel);

    /**
     * Updates the FactorCoeffApplicationType.
     * 
     * @param object the FactorCoeffApplicationType to update
     * @return the FactorCoeffApplicationType
     */
    FactorCoeffApplicationType persistFactorCoeffAppType(FactorCoeffApplicationType object);

    public FactorCoeffApplicationType loadFactorCoeffAppType(long factorCoeffAppId);
}
