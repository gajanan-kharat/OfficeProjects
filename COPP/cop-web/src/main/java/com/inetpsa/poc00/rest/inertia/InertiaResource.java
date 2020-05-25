/*
 * Creation : Jun 2, 2016
 */
package com.inetpsa.poc00.rest.inertia;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;


/**
 * The Class InertiaResource.
 */
@Path("/inertia")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class InertiaResource {

	/** The inertia finder. */
	@Inject
	InertiaFinder inertiaFinder;

	/** The logger. */
	@Logging
	private Logger logger;

	/**
	 * Gets the all inertia values.
	 *
	 * @return the all inertia values
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allInertia")
	public Response getAllInertiaValues() {
		List<Integer> listToReturn = inertiaFinder.getAllInertiaValues();

		return Response.ok(listToReturn).build();

	}

}
