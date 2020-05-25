package com.inetpsa.poc00.rest.clasz;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.clasz.Clasz;

/**
 * The Interface ClaszFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface ClaszFinder {

	/**
	 * Gets the all clasz.
	 * 
	 * @return the all clasz
	 */
	public List<ClaszRepresentation> getAllClasz();

	/**
	 * Gets the all clasz for copied pg list.
	 * 
	 * @param entityId the entity id
	 * @return the all clasz for copied pg list
	 */
	public List<Clasz> getAllClaszForCopiedPGList(Long entityId);

	/**
	 * Gets the all clasz for copied fc list.
	 * 
	 * @param entityId the entity id
	 * @return the all clasz for copied fc list
	 */
	public List<Clasz> getAllClaszForCopiedFCList(Long entityId);
	
	/**
	 * Find clasz data by label.
	 * 
	 * @param label the label
	 * @return the list
	 */
	public List<ClaszRepresentation> findClaszDataByLabel(String label);

}
