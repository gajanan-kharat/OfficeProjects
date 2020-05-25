/*
 * Creation : Jan 4, 2017
 */
package com.inetpsa.poc00.application.factorcoeffapptype;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;

/**
 * The Interface FactorCoeffAppTypeService.
 */
@Service
public interface FactorCoeffAppTypeService {

	/**
	 * Save factor coeff application type.
	 * 
	 * @param factorCoeffApplicationObj the factor coeff application obj
	 * @return the string
	 */
	public String saveFactorCoeffApplicationType(FactorCoeffApplicationType factorCoeffApplicationObj);

	/**
	 * Delete factor coeff application type.
	 * 
	 * @param factorCoeffApplicationTypeId the factor coeff application type id
	 * @return the string
	 */
	String deleteFactorCoeffApplicationType(long factorCoeffApplicationTypeId);
}
