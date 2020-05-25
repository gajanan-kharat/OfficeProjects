/*
 * Creation : Dec 30, 2016
 */
package com.inetpsa.poc00.application.projectcodefamily;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;

/**
 * The Interface ProjectCodeFamilyService.
 */
@Service
public interface ProjectCodeFamilyService {

	/**
	 * Save project family.
	 *
	 * @param projectCode the project code
	 * @return the project code family
	 */
	public ProjectCodeFamily saveProjectFamily(ProjectCodeFamily projectCode);

	/**
	 * Delete project family.
	 *
	 * @param pCFId the CF id
	 * @return true, if successful
	 */
	public boolean deleteProjectFamily(Long pCFId);
}
