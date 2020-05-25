package com.inetpsa.poc00.rest.typeapprovalarea;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;

/**
 * The Interface TypeApprovalAreaFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TypeApprovalAreaFinder {

	/**
	 * Gets the all type approval area.
	 * 
	 * @return the all type approval area
	 */
	public List<TypeApprovalAreaRepresentation> getAllTypeApprovalArea();

	/**
	 * Gets the all area names.
	 * 
	 * @return the all area names
	 */
	public List<String> getAllAreaNames();

	/**
	 * Find type approval area data by label.
	 * 
	 * @param label the label
	 * @return the list
	 */
	public List<TypeApprovalAreaRepresentation> findTypeApprovalAreaDataByLabel(String label);

	/**
	 * Type approval area data.
	 * 
	 * @return the list
	 */
	List<TypeApprovalAreaRepresentation> getTypeApprovalAreaData();

	/**
	 * Gets the all type approval area for rg.
	 * 
	 * @return the all type approval area for rg
	 */
	public List<TypeApprovalAreaRepresentation> getAllTypeApprovalAreaForRg();

}
