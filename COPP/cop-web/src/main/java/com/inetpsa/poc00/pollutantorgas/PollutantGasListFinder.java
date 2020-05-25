/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.pollutantorgas;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;

/**
 * The Interface PollutantGasListFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface PollutantGasListFinder {

	/**
	 * Gets the all ems dep pgl.
	 * 
	 * @return the all ems dep pgl
	 */
	public List<PollutantGasLimitListRepresentation> getAllEmsDepPGL();

	/**
	 * Gets the EMS dep lists.
	 * 
	 * @param id the id
	 * @return the EMS dep lists
	 */
	public List<PollutantGasLimitListRepresentation> getEMSDepLists(long id);

	/**
	 * Gets the emission standard dep lists.
	 * 
	 * @param entityId the entity id
	 * @return the emission standard dep lists
	 */
	public List<PollutantGasLimitList> getEmissionStandardDepLists(Long entityId);

	/**
	 * Gets the PG list for label.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 * @return the PG list for label
	 */
	List<PollutantGasLimitListRepresentation> getPGListForLabel(long entityId, String label);

	/**
	 * Gets the latest emission standard dep lists.
	 * 
	 * @param entityId the entity id
	 * @return the latest emission standard dep lists
	 */
	public List<PollutantGasLimitList> getLatestEmissionStandardDepLists(Long entityId);

}
