/*
 * Creation : Apr 12, 2016
 */
package com.inetpsa.poc00.emsdeptdl;

import java.net.URISyntaxException;
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

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.status.Status;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.emsdeptdl.EmsDepTDLService;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.es.EmissionStandardRepository;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDL;
import com.inetpsa.poc00.domain.esdependent.tdl.EmissionStdDepTDLFactory;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataRepresentation;

/**
 * The Class EmsDepTDLResource.
 */
@Path("/emsDepTDL")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class EmsDepTDLResource {

	/** The ems dep tdl factory. */
	@Inject
	private EmissionStdDepTDLFactory emsDepTDLFactory;

	/** The ems dep tdl assembler. */
	@Inject
	private EmsDepTDLAssembler emsDepTDLAssembler;

	/** The ems dep tdl finder. */
	@Inject
	private EmsDepTDLFinder emsDepTDLFinder;

	/** The generic data finder. */
	@Inject
	private GenericTechnicalDataFinder genericDataFinder;

	/** The emission standard factory. */
	@Inject
	private EmissionStandardFactory emissionStandardFactory;

	/** The emission standard assembler. */
	@Inject
	EmissionStandardAssembler emissionStandardAssembler;

	/** The emission standard repository. */
	@Inject
	EmissionStandardRepository emissionStandardRepository;

	/** The ems dep tdl service. */
	@Inject
	private EmsDepTDLService emsDepTDLService;
	
	/** The Constant logger. */
	@Inject
	private static final Logger logger = LoggerFactory.getLogger(EmsDepTDLResource.class);

	/**
	 * Adds a GenomeLCDVDictionary.
	 * 
	 * @param entityId the entity id
	 * @param emsId the ems id
	 * @return the new customer
	 * @throws URISyntaxException if an URI error occurs
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/emsDependentTDLLabel/{entityId}/{emsId}")
	public Response checkEmsDependentTDLLabel(@PathParam("entityId") Long entityId, @PathParam("emsId") String emsId) throws URISyntaxException {

		Long entity = emsDepTDLFinder.checkLabel(emsId, entityId);

		if (entity != null) {
			return Response.status(Status.ERROR).build();
		}
		
		return Response.ok().build();

	}

	/**
	 * Save ems dep tdl changes.
	 * 
	 * @param emsDepTDLRequestDto the ems dep tdl request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/emsDependentTDL")
	public Response saveEmsDepTDLChanges(ManageEmsDepTDLRequestDto emsDepTDLRequestDto) {
		
		boolean changeEmsVersion = emsDepTDLRequestDto.isChangeEmsVersion();
		
		List<EmsDepTDLRepresentation> listToReturn = new ArrayList<>();

		List<EmsDepTDLRepresentation> requestList = emsDepTDLRequestDto.getEmsDepTDLRepresentationList();
	
		if (requestList == null || requestList.isEmpty()) {
			return Response.ok().build();
		}
		
		EmissionStandard emissionStandard = emissionStandardFactory.createEmissonStandard();
		emissionStandardAssembler.doMergeAggregateWithDto(emissionStandard, requestList.get(0).getEmissionStandard());
		EmissionStandard newVersionObject = emsDepTDLService.getEmissionStandardObject(requestList.get(0).getEmissionStandard().getEntityId(), changeEmsVersion, emissionStandard);
		
		for (EmsDepTDLRepresentation emsDepTDLRepresentation : requestList) {

			EmissionStdDepTDL emsDepTDL = emsDepTDLFactory.createEmissonStdDepTDL();
			emsDepTDLAssembler.doMergeAggregateWithDto(emsDepTDL, emsDepTDLRepresentation);
			EmsDepTDLRepresentation newEmsDepTDLRepresentation = new EmsDepTDLRepresentation();

			if ("UPDATE".equalsIgnoreCase(emsDepTDLRepresentation.getModifiedFlag())) {
				
				EmissionStdDepTDL newTDLOBJ = emsDepTDLService.updateEmsDepTDL(emsDepTDL,newVersionObject);
				emsDepTDLAssembler.doAssembleDtoFromAggregate(newEmsDepTDLRepresentation, newTDLOBJ);
				listToReturn.add(newEmsDepTDLRepresentation);

			} else if ("INSERT".equalsIgnoreCase(emsDepTDLRepresentation.getModifiedFlag())) {

				emsDepTDLService.createEmsDepTDL(newVersionObject, emsDepTDL);
				emsDepTDLAssembler.doAssembleDtoFromAggregate(newEmsDepTDLRepresentation, emsDepTDL);
				listToReturn.add(newEmsDepTDLRepresentation);
				
			} else if ("DELETE".equalsIgnoreCase(emsDepTDLRepresentation.getModifiedFlag())) {
				if (emsDepTDLRepresentation.getEntityId() != null) {

					emsDepTDLService.deleteEmsDepTDL(emsDepTDLRepresentation.getEntityId());
				}
				
			} else {
				
				if (changeEmsVersion) {
					
					emsDepTDL = emsDepTDLService.copySingleTDL(emsDepTDL, newVersionObject);
					emsDepTDLAssembler.doAssembleDtoFromAggregate(newEmsDepTDLRepresentation, emsDepTDL);
					listToReturn.add(newEmsDepTDLRepresentation);
					
				} else

					listToReturn.add(emsDepTDLRepresentation);
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
	@Path("/emsDependentTDL/{emsId}")
	public Response getEsDependentLists(@PathParam("emsId") String emsId) {
		List<EmsDepTDLRepresentation> listToReturn = null;
		if (emsId != null) {
			long id = Long.parseLong(emsId);
			listToReturn = emsDepTDLFinder.getEMSDepLists(id);
			for (EmsDepTDLRepresentation obj : listToReturn) {
				List<GenericTechnicalDataRepresentation> dataList = genericDataFinder.getAllDataForEMSDepList(obj.getEntityId());
				obj.setGenericTechnicalData(dataList);

				EmissionStandard emissionStandard = emissionStandardRepository.load(obj.getEmissionStandard().getEntityId());

				EmissionStandardRepresentation newEmissionStandardRepresentation = new EmissionStandardRepresentation();
				emissionStandardAssembler.doAssembleDtoFromAggregate(newEmissionStandardRepresentation, emissionStandard);
				obj.setEmissionStandard(newEmissionStandardRepresentation);

			}

		}
		return Response.ok(listToReturn).build();

	}
}
