package com.inetpsa.poc00.rest.factorcoeffapptype;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface FactorCoeffAppTypeFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface FactorCoeffAppTypeFinder {

	/**
	 * Gets the all factor coeff application type.
	 * 
	 * @return the all factor coeff application type
	 */
	List<FactorCoeffAppTypeRepresentation> getAllFactorCoeffApplicationType();

	/**
	 * Find factor coeff application type data by label.
	 * 
	 * @param label the label
	 * @return the list
	 */
	public List<FactorCoeffAppTypeRepresentation> findFactorCoeffApplicationTypeDataByLabel(String label);

}
