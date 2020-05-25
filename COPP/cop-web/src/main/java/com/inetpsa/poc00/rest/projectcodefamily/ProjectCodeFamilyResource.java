package com.inetpsa.poc00.rest.projectcodefamily;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.projectcodefamily.ProjectCodeFamilyService;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamily;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyFactory;
import com.inetpsa.poc00.domain.projectcodefamily.ProjectCodeFamilyRepository;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The Class ProjectCodeFamilyResource.
 */
@Path("/FamilleReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class ProjectCodeFamilyResource {

	/** The project code family finder. */
	@Inject
	ProjectCodeFamilyFinder projectCodeFamilyFinder;

	/** The project code family factory. */
	@Inject
	ProjectCodeFamilyFactory projectCodeFamilyFactory;

	/** The project code family assembler. */
	@Inject
	ProjectCodeFamilyAssembler projectCodeFamilyAssembler;

	/** The project code family repository. */
	@Inject
	ProjectCodeFamilyRepository projectCodeFamilyRepository;

	@Inject
	ProjectCodeFamilyService projectCodeFamilyService;

	/** The trace resource. */
	@Inject
	TraceabilityResource traceResource;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the famille data.
	 * 
	 * @param hsr the hsr
	 * @param info the info
	 * @return the famille data
	 */
	@GET
	@Path("/Famillies")
	public Response getFamilleData() {
		List<ProjectCodeFamilyRepresentation> projectFamilyList;

		projectFamilyList = projectCodeFamilyFinder.findAllFamilleData();

		return Response.ok(projectFamilyList).build();

	}

	/**
	 * Save famille data.
	 * 
	 * @param projectCodeFamilyDto the project code family dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Famillies")
	public Response saveFamilleData(ManageProjectCodeFamilyDto projectCodeFamilyDto) {

		logger.info("trying to save data in projectCodeFamily table");

		ProjectCodeFamilyRepresentation projectCodeFamilyRepresentationResponse = projectCodeFamilyDto.getProjectCodeFamilyRepresentationList().get(0);
		for (ProjectCodeFamilyRepresentation projectCodeFamilyRepresentation : projectCodeFamilyDto.getProjectCodeFamilyRepresentationList()) {

			ProjectCodeFamily projectCodeFamily = projectCodeFamilyFactory.createPCFamily();
			projectCodeFamilyAssembler.doMergeAggregateWithDto(projectCodeFamily, projectCodeFamilyRepresentation);

			ProjectCodeFamily pcfResponse = projectCodeFamilyService.saveProjectFamily(projectCodeFamily);
			if (pcfResponse == null) {
				projectCodeFamilyRepresentation.setFamilleBOC(null);
				return Response.ok(projectCodeFamilyRepresentation).build();
			}
			projectCodeFamilyRepresentationResponse = projectCodeFamilyRepresentation;
		}
		logger.info("Sucessfully saved ProjectCodeFamily data");
		return Response.ok(projectCodeFamilyRepresentationResponse).build();

	}

	/**
	 * Delete famille data.
	 * 
	 * @param projectCodeFamilyDto the project code family dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Familly")
	public Response deleteFamilleData(ManageProjectCodeFamilyDto projectCodeFamilyDto) {

		logger.info("trying to delete data from ProjectCodeFamily table");
		for (ProjectCodeFamilyRepresentation projectCodeFamilyRepresentation : projectCodeFamilyDto.getProjectCodeFamilyRepresentationList()) {
			boolean deleted = projectCodeFamilyService.deleteProjectFamily(projectCodeFamilyRepresentation.getEntityId());
			if (!deleted)
				Response.status(Response.Status.PRECONDITION_FAILED).build();
		}

		return Response.ok().build();

	}

	/**
	 * Gets the all project families.
	 * 
	 * @return the all project families
	 */
	@GET
	@Path("/AllProjectFamilies")
	public Response getAllProjectFamilies() {
		List<ProjectCodeFamilyRepresentation> projectFamilyList;

		projectFamilyList = projectCodeFamilyFinder.getAllProjectFamilies();

		return Response.ok(projectFamilyList).build();

	}

	/**
	 * Gets the all project family names.
	 * 
	 * @return the all project family names
	 */
	@GET
	@Path("projectFamilyNames")
	public Response getAllProjectFamilyNames() {
		List<String> projectFamilyList;

		projectFamilyList = projectCodeFamilyFinder.getAllProjectFamilyNames();

		return Response.ok(projectFamilyList).build();

	}
}
