/*
 * Creation : Jun 8, 2016
 */
package com.inetpsa.poc00.rest.valuedinertia;

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

@Path("/valuedInertia")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class ValuedInertiaResource {

	@Inject
	ValuedInertiaFinder inertiaFinder;

	@Logging
	private Logger logger;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allValues")
	public Response getAllInertiaValues() {
		List<Integer> listToReturn = inertiaFinder.getAllInertiaValues();

		return Response.ok(listToReturn).build();

	}
}
