/*
 * Creation : Apr 27, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptcl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionFactory;
import com.inetpsa.poc00.domain.generictc.GenericTestConditionRepository;

/**
 * The Class GenericTestConditionResource.
 */
@Path("/genericCondition")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class GenericTestConditionResource {

	/** The generic test condition factory. */
	@Inject
	private GenericTestConditionFactory genericTestConditionFactory;
	
	/** The generic test condition assembler. */
	@Inject
	private GenericTestConditionAssembler genericTestConditionAssembler;
	
	/** The generic test condition repository. */
	@Inject
	GenericTestConditionRepository genericTestConditionRepository;

	/**
	 * Update generic condtion mandatory.
	 *
	 * @param requestDto the request dto
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateGenericCondtionMandatory")
	public Response updateGenericCondtionMandatory(ManageGenericTestConditionRequestDto requestDto) {

		List<GenericTestConditionRepresentation> listToReturn = new ArrayList<GenericTestConditionRepresentation>();
		List<GenericTestConditionRepresentation> genericDataList = requestDto.getGenericConditionList();
		for (GenericTestConditionRepresentation dataRepresentation : genericDataList) {
			GenericTestCondition entity = genericTestConditionFactory.createGenericTestCondtn();
			genericTestConditionAssembler.doMergeAggregateWithDto(entity, dataRepresentation);
			entity = genericTestConditionRepository.saveGenericTestCondition(entity);
			GenericTestConditionRepresentation data = new GenericTestConditionRepresentation();
			genericTestConditionAssembler.doAssembleDtoFromAggregate(data, entity);
			listToReturn.add(data);

		}
		return Response.ok(listToReturn).build();

	}
}
