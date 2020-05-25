package com.inetpsa.poc00.rest.tvvvaluedtcl;

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
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTCLRepository;
import com.inetpsa.poc00.domain.tvvvaluedtcl.TvvValuedTvvDepTCL;
import com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericDataRepresentation;

@Path("/tvvValuedTCL")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TvvValuedTvvDepTCLResource {

	@Inject
	private TvvValuedTCLFinder tvvValuedTCLFinder;

	@Inject
	private TvvValuedTCLAssembler tvvValuedTCLAssembler;

	@Inject
	private TvvValuedTCLRepository tvvValuedTCLRepository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allValuedTCL/{tvvId}")
	public Response getAllValuedTCL(@PathParam("tvvId") String tvvId) {

		if (tvvId != null && tvvId.length() > 0) {
			long tvvID = Long.parseLong(tvvId);
			List<TvvValuedTvvDepTCLRepresentation> list = tvvValuedTCLFinder.getAllTvvValuedTCL(tvvID);
			List<TvvValuedTvvDepTCLRepresentation> listToReturn = new ArrayList<TvvValuedTvvDepTCLRepresentation>();

			ValuedGenericDataRepresentation entity;
			for (TvvValuedTvvDepTCLRepresentation obj : list) {
				TvvValuedTvvDepTCL tclObj = tvvValuedTCLRepository.load(obj.getEntityId());
				TvvValuedTvvDepTCLRepresentation tclRepresentation = new TvvValuedTvvDepTCLRepresentation();
				tvvValuedTCLAssembler.doAssembleDtoFromAggregate(tclRepresentation, tclObj);
				tclRepresentation.setTypeOfList(Constants.TVV_VALUED_TCL);
				listToReturn.add(tclRepresentation);
			}

			return Response.ok(listToReturn).build();
		}
		return Response.ok().build();

	}
}
