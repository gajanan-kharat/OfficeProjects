/*
 * Creation : Mar 29, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.mandatorydata.MandatoryDataRepresentation;

/**
 * The Interface GenericTechnicalDataFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface GenericTechnicalDataFinder {

	/**
	 * Gets the all data for list.
	 * 
	 * @param entityId the entity id
	 * @return the all data for list
	 */
	List<GenericTechnicalDataRepresentation> getAllDataForList(long entityId);

	/**
	 * Gets the all data for ems dep list.
	 * 
	 * @param entityId the entity id
	 * @return the all data for ems dep list
	 */
	List<GenericTechnicalDataRepresentation> getAllDataForEMSDepList(long entityId);

	/**
	 * Gets the all generic data for list.
	 * 
	 * @param entityId the entity id
	 * @return the all generic data for list
	 */
	List<GenericTechnicalData> getAllGenericDataForList(long entityId);

	/**
	 * Gets the all generic data for ems list.
	 * 
	 * @param entityId the entity id
	 * @return the all generic data for ems list
	 */
	List<GenericTechnicalData> getAllGenericDataForEmsList(long entityId);

	/**
	 * Gets the all generic technical data.
	 * 
	 * @return the all generic technical data
	 */
	List<MandatoryDataRepresentation> getAllGenericTechnicalData(String emsVersion, long emsId);

	/**
	 * Gets the all tvv generic technical data.
	 * 
	 * @return the all tvv generic technical data
	 */
	List<MandatoryDataRepresentation> getAllTvvGenericTechnicalData(long tvvDepTDLId);

	/**
	 * Gets the generic data label.
	 * 
	 * @param label the label
	 * @param tvvDepTDL the tvv dep tdl
	 * @return the generic data label
	 */
	List<GenericTechnicalDataRepresentation> getGenericDataLabel(String label, long tvvDepTDL);

}