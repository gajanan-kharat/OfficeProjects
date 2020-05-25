package com.inetpsa.poc00.domain.factorcoeffList;

import java.util.Set;

import org.seedstack.business.domain.GenericFactory;

import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;

/**
 * A factory for creating FactorCoeffList objects.
 */
public interface FactorCoeffListFactory extends GenericFactory<FactorCoefficentList> {

	/**
	 * Factory create method.
	 * 
	 * @return the FactorCoefficentsList
	 */
	FactorCoefficentList createFactorCoeffList();

	/**
	 * Creates a new FactorCoeffList object.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 * @param fcApplicationTypes the fc application types
	 * @return the factor coefficent list
	 */
	FactorCoefficentList createFactorCoeffList(String label, String description, String version, Set<FactorCoeffApplicationType> fcApplicationTypes);

	/**
	 * Creates a new FactorCoeffList object.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 * @return the factor coefficent list
	 */
	FactorCoefficentList createFactorCoeffList(String label, String description, String version);

}
