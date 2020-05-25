/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.rest.pfstructure;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructure;

/**
 * The Interface PreparationFileStructureFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PreparationFileStructureFinder {

	/**
	 * Gets the PFS list.
	 * 
	 * @return the PFS list
	 */
	PreparationFileStructureRepresentation getPFSList();

	/**
	 * Gets the latest prep file structure.
	 * 
	 * @return the latest prep file structure
	 */
	PreparationFileStructure getLatestPrepFileStructure();

}
