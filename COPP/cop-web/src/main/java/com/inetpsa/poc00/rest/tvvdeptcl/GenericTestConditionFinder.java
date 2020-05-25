/*
 * Creation : Apr 1, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.mandatorydata.MandatoryDataRepresentation;

/**
 * The Interface GenericTestConditionFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface GenericTestConditionFinder {

	/**
	 * Gets the all conditions for list.
	 * 
	 * @param entityId the entity id
	 * @return the all conditions for list
	 */
	List<GenericTestConditionRepresentation> getAllConditionsForList(long entityId);

	/**
	 * Gets the all conditions for ems dep list.
	 * 
	 * @param entityId the entity id
	 * @return the all conditions for ems dep list
	 */
	List<GenericTestConditionRepresentation> getAllConditionsForEMSDepList(long entityId);

	/**
	 * Gets the all generic conditions for list.
	 * 
	 * @param entityId the entity id
	 * @return the all generic conditions for list
	 */
	List<GenericTestCondition> getAllGenericConditionsForList(long entityId);

	/**
	 * Gets the all generic conditions for ems list.
	 * 
	 * @param entityId the entity id
	 * @return the all generic conditions for ems list
	 */
	List<GenericTestCondition> getAllGenericConditionsForEmsList(long entityId);

	/**
	 * Gets the all generic test condition data.
	 * 
	 * @return the all generic test condition data
	 */
	List<MandatoryDataRepresentation> getAllGenericTestConditionData(String emissionStandardVersion, long emsId);

	/**
	 * Gets the all tvv generic test condition data.
	 * 
	 * @return the all tvv generic test condition data
	 */
	List<MandatoryDataRepresentation> getAllTvvGenericTestConditionData(long tvvDepTCLId);

	/**
	 * Gets the all tvv generic test condition data.
	 * 
	 * @param label the label
	 * @param tvvDepTCLId the tvv dep tcl id
	 * @return the all tvv generic test condition data
	 */
	List<GenericTestConditionRepresentation> getGenericTestLabel(String label, long tvvDepTCLId);

}
