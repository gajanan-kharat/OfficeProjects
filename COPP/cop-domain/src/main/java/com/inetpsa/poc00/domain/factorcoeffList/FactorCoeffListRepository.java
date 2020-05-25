package com.inetpsa.poc00.domain.factorcoeffList;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;


/**
 * Repository interface of FactorCoeffList.
 */

public interface FactorCoeffListRepository extends GenericRepository<FactorCoefficentList, Long> {

    /**
     * Saves the FactorCoeffList.
     * 
     * @param object the FactorCoeffList to save
     * @return the FactorCoeffList
     */
    FactorCoefficentList saveFactorCoeffList(FactorCoefficentList object);

    /**
     * Persists the FactorCoeffList.
     * 
     * @param object the FactorCoeffList to persist
     */
    void persistFactorCoeffList(FactorCoefficentList object);

    /**
     * Delete all categories.
     * 
     * @return number of categories deleted
     */
    long deleteAll();

    /**
     * Count the number of categories in the repository.
     * 
     * @return FactorCoeffList count
     */
    long count();

    /**
     * Deletefactor coefficient list.
     * 
     * @param entityId the entity id
     */
    void deletefactorCoefficientList(long entityId);

    /**
     * Gets the latest emission standard dep lists.
     * 
     * @param emsId the ems id
     * @return the latest emission standard dep lists
     */
    List<FactorCoefficentList> getLatestEmissionStandardDepLists(Long emsId);

    /**
     * Gets the emission standard dep lists.
     * 
     * @param esEntityId the es entity id
     * @return the emission standard dep lists
     */
    List<FactorCoefficentList> getEmissionStandardDepLists(Long esEntityId);

    /**
     * Gets the max version for label.
     * 
     * @param label the label
     * @return the max version for label
     */
    Double getMaxVersionForLabel(String label);

    /**
     * Load factor coeff list.
     *
     * @param factorCoeffListId the factor coeff list id
     * @return the factor coefficent list
     */
    public FactorCoefficentList loadFactorCoeffList(long factorCoeffListId);


	
	
	/**
	 * Gets the all used pg labels.
	 *
	 * @param emissionStdId the emission std id
	 * @return the all used pg labels
	 */
	List<PollutantGasLabel> getAllUsedPgLabels(Long emissionStdId);
}
