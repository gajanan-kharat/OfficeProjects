/*
 * Creation : Apr 12, 2016
 */
package com.inetpsa.poc00.emsdeptdl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;

/**
 * The Interface EmsDepTDLFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface EmsDepTDLFinder {

	/**
	 * Gets the all ems dep tdl.
	 * 
	 * @return the all ems dep tdl
	 */
	List<EmsDepTDLRepresentation> getAllEmsDepTDL();

	/**
	 * Gets the eMS dep lists.
	 * 
	 * @param id the id
	 * @return the eMS dep lists
	 */
	List<EmsDepTDLRepresentation> getEMSDepLists(long id);

	/**
	 * Gets the emission standard dep lists.
	 * 
	 * @param entityId the entity id
	 * @return the emission standard dep lists
	 */
	List<EmissionStdDepTDL> getEmissionStandardDepLists(Long entityId);

	/**
	 * Check label.
	 * 
	 * @param esLabel the es label
	 * @param entityId the entity id
	 * @return the long
	 */
	Long checkLabel(String esLabel, Long entityId);

	/**
	 * Gets the latest emission standard dep lists.
	 * 
	 * @param emsId the ems id
	 * @return the latest emission standard dep lists
	 */
	List<EmissionStdDepTDL> getLatestEmissionStandardDepLists(Long emsId);
}
