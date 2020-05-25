package com.inetpsa.poc00.rest.projectcodefamily;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

/**
 * The Interface ProjectCodeFamilyFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface ProjectCodeFamilyFinder {

	/**
	 * Find all famille data.
	 * 
	 * @return the list
	 */
	List<ProjectCodeFamilyRepresentation> findAllFamilleData();

	/**
	 * Gets the all project families.
	 * 
	 * @return the all project families
	 */
	List<ProjectCodeFamilyRepresentation> getAllProjectFamilies();

	/**
	 * Gets the all project family names.
	 * 
	 * @return the all project family names
	 */
	List<String> getAllProjectFamilyNames();

	/**
	 * Find project code family data by label.
	 * 
	 * @param projectCodeLabel the project code label
	 * @param vehicleFamilyLabel the vehicle family label
	 * @return the list
	 */
	List<ProjectCodeFamilyRepresentation> findProjectCodeFamilyDataByLabel(String projectCodeLabel, String vehicleFamilyLabel);

	/**
	 * Gets the all project families for tvv.
	 * 
	 * @param kmatList the kmat
	 * @return the all project families for tvv
	 */
	List<ProjectCodeFamilyRepresentation> getAllProjectFamiliesForTvv(List<String> kmatList);

}
