/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.poc00.emsdepfcl;

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

/**
 * The Class FactorCoeffApplicationTypeResource.
 */
@Path("/fcAppType")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class FactorCoeffApplicationTypeResource {

	/** The fc app type finder. */
	@Inject
	private FactorCoeffApplicationTypeFinder fcAppTypeFinder;

	/**
	 * Gets the ems dependent TCL.
	 *
	 * @return the ems dependent TCL
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/FactorCoeffApplicationTypes")
	public Response getAllFCApplicationTypes() {
		List<FactorCoeffApplicationTypeRepresentation> listToReturn = fcAppTypeFinder.getAllApplicationTypes();

		return Response.ok(listToReturn).build();

	}

}
