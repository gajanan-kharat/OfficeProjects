/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface FactorCoeffApplicationTypeFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface FactorCoeffApplicationTypeFinder {

	/**
	 * Gets the all application types.
	 * 
	 * @return the all application types
	 */
	public List<FactorCoeffApplicationTypeRepresentation> getAllApplicationTypes();

}
