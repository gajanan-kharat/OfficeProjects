/*
 * Creation : Jun 10, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedtdl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTDLRepository;
import com.inetpsa.poc00.domain.tvvvaluedtdl.TvvValuedTvvDepTDL;

@Path("/tvvValuedTDL")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TvvValuedTDLResource {
	@Inject
	private TvvValuedTDLFinder tvvValuedTDLFinder;
	@Inject
	private TvvValuedTDLRepository tvvValuedTDLRepository;
	@Inject
	private TvvValuedTDLAssembler tvvValuedTDLAssembler;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allValuedTDL/{tvvId}")
	public Response getAllTvvValuedTDL(@PathParam("tvvId") String tvvId) {
		if (tvvId != null && tvvId.length() > 0) {
			long tvvID = Long.parseLong(tvvId);
			List<TvvValuedTvvDepTDLRepresentation> list = tvvValuedTDLFinder.getAllTvvValuedTDL(tvvID);
			List<TvvValuedTvvDepTDLRepresentation> listToReturn = new ArrayList<TvvValuedTvvDepTDLRepresentation>();

			ValuedGenericDataRepresentation entity;
			for (TvvValuedTvvDepTDLRepresentation obj : list) {
				TvvValuedTvvDepTDL tdlObj = tvvValuedTDLRepository.load(obj.getEntityId());
				TvvValuedTvvDepTDLRepresentation tdlRepresentation = new TvvValuedTvvDepTDLRepresentation();
				tvvValuedTDLAssembler.doAssembleDtoFromAggregate(tdlRepresentation, tdlObj);
				tdlRepresentation.setTypeOfList(Constants.TVV_VALUED_TDL);
				listToReturn.add(tdlRepresentation);
			}

			return Response.ok(listToReturn).build();
		} else
			return Response.ok().build();

	}
}
