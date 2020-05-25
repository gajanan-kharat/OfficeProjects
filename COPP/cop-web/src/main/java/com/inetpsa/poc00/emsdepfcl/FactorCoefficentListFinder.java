/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;

/**
 * The Interface FactorCoefficentListFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface FactorCoefficentListFinder {

	/**
	 * Gets the all ems dep fcl.
	 * 
	 * @return the all ems dep fcl
	 */
	List<FactorCoefficentListRepresentation> getAllEmsDepFCL();

	/**
	 * Gets the EMS dep lists.
	 * 
	 * @param id the id
	 * @return the EMS dep lists
	 */
	List<FactorCoefficentListRepresentation> getEMSDepLists(long id);

	/**
	 * Gets the emission standard dep lists.
	 * 
	 * @param entityId the entity id
	 * @return the emission standard dep lists
	 */
	List<FactorCoefficentList> getEmissionStandardDepLists(Long entityId);

	/**
	 * Gets the FC app type for label.
	 * 
	 * @param entityId the entity id
	 * @param label the label
	 * @return the FC app type for label
	 */
	List<FactorCoefficentListRepresentation> getFCAppTypeForLabel(long entityId, String label);

	/**
	 * Gets the latest emission standard dep lists.
	 * 
	 * @param entityId the entity id
	 * @return the latest emission standard dep lists
	 */
	List<FactorCoefficentList> getLatestEmissionStandardDepLists(Long entityId);

}
