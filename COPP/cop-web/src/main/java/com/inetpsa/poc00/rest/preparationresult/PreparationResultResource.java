package com.inetpsa.poc00.rest.preparationresult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;

/**
 * The Class PreparationResultResource.
 */
@Path("/preparationResult")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class PreparationResultResource {

	/**
	 * Gets the all pollutant data.
	 * 
	 * @return the all pollutant data
	 */
	@GET
	@Path("/AllPreparationResult")
	public Response getAllPollutantData() {

		return Response.ok().build();
	}

}
