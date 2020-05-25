/*
 * Creation : Apr 27, 2016
 */
package com.inetpsa.poc00.rest.parameters;

import java.util.ArrayList;
import java.util.List;

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
import com.inetpsa.poc00.common.Constants;

@Path("/ParametersConstantsReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class ParametersConstantDataResource {

	@Logging
	private Logger logger;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dataTypes")
	public Response getDataTypes() {

		logger.info("inside ParametersConstantDataResource ---------->getDataTypes value to UI=========================>");
		List<String> dataTypes = new ArrayList<String>();

		dataTypes.add(Constants.LOWER_SYMBOL_1);
		dataTypes.add(Constants.LOWER_SYMBOL_2);
		dataTypes.add(Constants.HIGHER_SYMBOL_1);
		dataTypes.add(Constants.HIGHER_SYMBOL_2);
		logger.info("sending ParametersConstants value to UI" + dataTypes);
		return Response.ok(dataTypes).build();

	}
}
