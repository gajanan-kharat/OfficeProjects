/*
 * Creation : Jun 8, 2016
 */
package com.inetpsa.poc00.rest.valuedcoastdown;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

/**
 * The Class ValuedCoastDownResource.
 */
@Path("/valuedCoastdown")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class ValuedCoastDownResource {

	/** The coastdown finder. */
	@Inject
	ValuedCoastDownFinder coastdownFinder;

	/**
	 * Gets the all valued coastdown labels.
	 * 
	 * @return the all valued coastdown labels
	 */
	@GET
	@Path("/coastDownLabels")
	public Response getAllValuedCoastdownLabels() {

		List<String> valuedCoastDownList;

		valuedCoastDownList = coastdownFinder.getAllCoastdownLabels();
		return Response.ok(valuedCoastDownList).build();
	}
}
