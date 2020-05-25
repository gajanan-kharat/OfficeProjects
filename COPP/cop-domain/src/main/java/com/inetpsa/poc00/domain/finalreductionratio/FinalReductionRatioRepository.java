package com.inetpsa.poc00.domain.finalreductionratio;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of FinalReductionRatio.
 */

public interface FinalReductionRatioRepository extends GenericRepository<FinalReductionRatio, Long> {

    /**
     * Saves the FinalReductionRatio.
     * 
     * @param object the FinalReductionRatio to save
     * @return the FinalReductionRatio
     */
    FinalReductionRatio saveReductionRatio(FinalReductionRatio object);

    /**
     * Persists the FinalReductionRatio.
     * 
     * @param object the FinalReductionRatio to persist
     * @return the final reduction ratio
     */
    FinalReductionRatio persistReductionRatio(FinalReductionRatio object);

    /**
     * Delete all categories.
     * 
     * @param id the id
     * @return number of categories deleted
     */
    int deleteAll(long id);

    /**
     * Count the number of categories in the repository.
     * 
     * @return FinalReductionRatio count
     */
    long count();

    /**
     * Gets the all final reduction ration.
     * 
     * @return the all final reduction ration
     */
    public List<FinalReductionRatio> getAllFinalReductionRation();

    public FinalReductionRatio loadFinalReduction(long finalRdId);

}
