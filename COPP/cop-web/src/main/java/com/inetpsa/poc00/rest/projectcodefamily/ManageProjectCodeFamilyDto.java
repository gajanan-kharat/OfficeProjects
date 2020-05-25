package com.inetpsa.poc00.rest.projectcodefamily;

import java.util.List;


/**
 * The Class ManageProjectCodeFamilyDto.
 */
public class ManageProjectCodeFamilyDto {
	
	/** The project code family representation list. */
	private List<ProjectCodeFamilyRepresentation> projectCodeFamilyRepresentationList;

	/**
	 * Gets the project code family representation list.
	 *
	 * @return the project code family representation list
	 */
	public List<ProjectCodeFamilyRepresentation> getProjectCodeFamilyRepresentationList() {
		return projectCodeFamilyRepresentationList;
	}

	/**
	 * Sets the project code family representation list.
	 *
	 * @param projectCodeFamilyRepresentationList the new project code family representation list
	 */
	public void setProjectCodeFamilyRepresentationList(
			List<ProjectCodeFamilyRepresentation> projectCodeFamilyRepresentationList) {
		this.projectCodeFamilyRepresentationList = projectCodeFamilyRepresentationList;
	}

	

}
