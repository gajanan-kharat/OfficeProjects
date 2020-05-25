/*
 * Creation : Apr 12, 2016
 */
package com.inetpsa.poc00.emsdeptcl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.emsdeptcl.EmsDepTCLService;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCL;
import com.inetpsa.poc00.domain.esdependent.tcl.EmissionStdDepTCLFactory;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionFinder;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionRepresentation;

/**
 * The Class EmsDepTCLResource.
 */
@Path("/emsDepTCL")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class EmsDepTCLResource {

	/** The ems dep tcl factory. */
	@Inject
	private EmissionStdDepTCLFactory emsDepTCLFactory;

	/** The ems dep tcl assembler. */
	@Inject
	private EmsDepTCLAssembler emsDepTCLAssembler;

	/** The ems dep tcl finder. */
	@Inject
	private EmsDepTCLFinder emsDepTCLFinder;

	/** The generic test condition finder. */
	@Inject
	private GenericTestConditionFinder genericTestConditionFinder;

	/** The emission standard repository. */
	@Inject
	private EmissionStandardRepository emissionStandardRepository;

	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;

	/** The emission standard assembler. */
	@Inject
	private EmissionStandardAssembler emissionStandardAssembler;

	/** The ems dep tdl service. */
	@Inject
	private EmsDepTCLService emsDepTCLService;
	
	/**
	 * Es dependent lists.
	 * 
	 * @param entityId the entity id
	 * @param emsId the ems id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/emsDependentTCLLabel/{entityId}/{emsId}")
	public Response checkEsDependentListsLabel(@PathParam("entityId") long entityId, @PathParam("emsId") String emsId) {

		List<EmsDepTCLRepresentation> emsDepTCLObj = emsDepTCLFinder.getConditionsForLabel(entityId, emsId);
		
		if (emsDepTCLObj.isEmpty()) {
			return Response.ok(emsDepTCLObj).build();
		}
		
		return Response.status(Status.PRECONDITION_FAILED).build();
	}

	/**
	 * Save ems dep tcl changes.
	 * 
	 * @param emsDepTCLRequestDto the ems dep tcl request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/emsDependentTCL")
	public Response saveEmsDepTCLChanges(ManageEmsDepTCLRequestDto emsDepTCLRequestDto) {
		
		boolean changeEmsVersion = emsDepTCLRequestDto.isChangeEmsVersion();
		
		List<EmsDepTCLRepresentation> listToReturn = new ArrayList<>();

		List<EmsDepTCLRepresentation> requestList = emsDepTCLRequestDto.getEmsDepTCLRepresentationList();
		
		if (requestList == null || requestList.isEmpty()) {
			return Response.ok().build();
		}
		
		EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
		emissionStandardAssembler.doMergeAggregateWithDto(emissionStandard, requestList.get(0).getEmissionStandard());
		EmissionStandard newVersionObject = emsDepTCLService.getEmissionStandardObject(requestList.get(0).getEmissionStandard().getEntityId(), changeEmsVersion, emissionStandard);
		
		for (EmsDepTCLRepresentation emsDepTCLRepresentation : requestList) {

			EmissionStdDepTCL emsDepTCL = emsDepTCLFactory.createEmissonStdDepTCL();
			emsDepTCLAssembler.doMergeAggregateWithDto(emsDepTCL, emsDepTCLRepresentation);
			EmsDepTCLRepresentation newEmsDepTCLRepresentation = new EmsDepTCLRepresentation();
			
		  if ("UPDATE".equalsIgnoreCase(emsDepTCLRepresentation.getModifiedFlag())) {
				
				EmissionStdDepTCL newTCLOBJ = emsDepTCLService.updateEmsDepTCL(emsDepTCL,newVersionObject);
				emsDepTCLAssembler.doAssembleDtoFromAggregate(newEmsDepTCLRepresentation, newTCLOBJ);
				listToReturn.add(newEmsDepTCLRepresentation);

			} else if ("INSERT".equalsIgnoreCase(emsDepTCLRepresentation.getModifiedFlag())) {
				
				emsDepTCLService.createEmsDepTCL(newVersionObject, emsDepTCL);
				emsDepTCLAssembler.doAssembleDtoFromAggregate(newEmsDepTCLRepresentation, emsDepTCL);
				listToReturn.add(newEmsDepTCLRepresentation);
				
			} else if ("DELETE".equalsIgnoreCase(emsDepTCLRepresentation.getModifiedFlag())) {
				
				if (emsDepTCLRepresentation.getEntityId() != null && emsDepTCLRepresentation.getEntityId() != 0) {
					emsDepTCLService.deleteEmsDepTCL(emsDepTCLRepresentation.getEntityId());
				}

			} else {
				if (changeEmsVersion) {

					emsDepTCL = emsDepTCLService.copySingleTCL(emsDepTCL, newVersionObject);
					emsDepTCLAssembler.doAssembleDtoFromAggregate(newEmsDepTCLRepresentation, emsDepTCL);
					listToReturn.add(newEmsDepTCLRepresentation);
				} else {
					listToReturn.add(emsDepTCLRepresentation);
				}
			}
		}

		return Response.ok(listToReturn).build();
	}

	/**
	 * Gets the es dependent lists.
	 * 
	 * @param emsId the ems id
	 * @return the es dependent lists
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/emsDependentTCL/{emsId}")
	public Response getEsDependentLists(@PathParam("emsId") String emsId) {
		
		List<EmsDepTCLRepresentation> listToReturn = null;
		
		if (emsId != null) {
			
			long id = Long.parseLong(emsId);
			listToReturn = emsDepTCLFinder.getEMSDepLists(id);
			
			for (EmsDepTCLRepresentation obj : listToReturn) {
				
				List<GenericTestConditionRepresentation> dataList = genericTestConditionFinder.getAllConditionsForEMSDepList(obj.getEntityId());
				obj.setGenericTestCondition(dataList);

				EmissionStandard emissionStandard = emissionStandardRepository.load(obj.getEmissionStandard().getEntityId());

				EmissionStandardRepresentation newEmissionStandardRepresentation = new EmissionStandardRepresentation();
				emissionStandardAssembler.doAssembleDtoFromAggregate(newEmissionStandardRepresentation, emissionStandard);
				obj.setEmissionStandard(newEmissionStandardRepresentation);
			}
		}
		
		return Response.ok(listToReturn).build();
	}

}
