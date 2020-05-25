/*
 * Creation : Apr 12, 2016
 */
package com.inetpsa.poc00.emsdeptcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;

/**
 * The Interface EmsDepTCLFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface EmsDepTCLFinder {

	/**
	 * Gets the all ems dep tcl.
	 * 
	 * @return the all ems dep tcl
	 */
	List<EmsDepTCLRepresentation> getAllEmsDepTCL();

	/**
	 * Gets the eMS dep lists.
	 * 
	 * @param id the id
	 * @return the eMS dep lists
	 */
	List<EmsDepTCLRepresentation> getEMSDepLists(long id);

	/**
	 * Gets the emission standard dep lists.
	 * 
	 * @param entityId the entity id
	 * @return the emission standard dep lists
	 */
	List<EmissionStdDepTCL> getEmissionStandardDepLists(Long entityId);

	/**
	 * Gets the conditions for label.
	 * 
	 * @param entityId the entity id
	 * @param emsId the ems id
	 * @return the conditions for label
	 */
	List<EmsDepTCLRepresentation> getConditionsForLabel(long entityId, String emsId);

	/**
	 * Gets the latest emission standard dep lists.
	 * 
	 * @param entityId the entity id
	 * @return the latest emission standard dep lists
	 */
	List<EmissionStdDepTCL> getLatestEmissionStandardDepLists(Long entityId);
}
