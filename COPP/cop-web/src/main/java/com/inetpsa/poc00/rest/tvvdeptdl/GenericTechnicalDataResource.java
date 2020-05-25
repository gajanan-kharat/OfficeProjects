/*
 * Creation : Apr 27, 2016
 */
package com.inetpsa.poc00.rest.tvvdeptdl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.generictd.GenericTechDataFactory;
import com.inetpsa.poc00.domain.generictd.GenericTechDataRepository;

/**
 * The Class GenericTechnicalDataResource.
 */
@Path("/genericData")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class GenericTechnicalDataResource {

	/** The generic technical data factory. */
	@Inject
	private GenericTechDataFactory genericTechnicalDataFactory;

	/** The generic tech data assembler. */
	@Inject
	private GenericTechnicalDataAssembler genericTechDataAssembler;

	/** The generic technical data repository. */
	@Inject
	GenericTechDataRepository genericTechnicalDataRepository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dataTypes")
	public Response getDataTypes() {
		List<String> dataTypes = new ArrayList<String>();
		dataTypes.add(Constants.DATATYPE_BOOLEAN);
		dataTypes.add(Constants.DATATYPE_INT);
		dataTypes.add(Constants.DATATYPE_REAL);
		dataTypes.add(Constants.DATATYPE_STRING);
		return Response.ok(dataTypes).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pfsdataTypes")
	public Response getDataTypesPFS() {
		List<String> dataTypes = new ArrayList<String>();
		dataTypes.add(Constants.DATATYPE_BOOLEAN);
		dataTypes.add(Constants.DATATYPE_INT);
		dataTypes.add(Constants.DATATYPE_STRING);
		dataTypes.add(Constants.DATATYPE_FLOAT);
		dataTypes.add(Constants.DATATYPE_DATE);
		dataTypes.add(Constants.DATATYPE_HOUR);
		return Response.ok(dataTypes).build();

	}
}
