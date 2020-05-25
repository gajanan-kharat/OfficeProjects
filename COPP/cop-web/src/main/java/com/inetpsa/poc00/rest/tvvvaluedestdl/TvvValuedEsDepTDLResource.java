/*
 * Creation : Jun 13, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedestdl;

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
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDL;
import com.inetpsa.poc00.domain.tvvvaluedesdeptdl.TvvValuedEsDepTDLRepository;

@Path("/tvvValuedEsDepTDL")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TvvValuedEsDepTDLResource {
	@Inject
	private TvvValuedEsDepTDLFinder tvvValuedEsDepTDLFinder;
	@Inject
	private TvvValuedEsDepTDLRepository tvvValuedEsDepTDLRepository;
	@Inject
	private TvvValuedEsDepTDLAssembler tvvValuedEsDepTDLAssembler;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allEsDepTDL/{tvvId}/{emsId}")
	public Response getEsDependentLists(@PathParam("tvvId") String tvvId, @PathParam("emsId") String emsId) {
		List<TvvValuedEsDepTDLRepresentation> listToReturn = null;
		if (emsId != null && tvvId != null) {

			long tvvID = Long.parseLong(tvvId);
			long emsID = Long.parseLong(emsId);
			List<TvvValuedEsDepTDLRepresentation> list = tvvValuedEsDepTDLFinder.getAllValuedTDL(tvvID, emsID);
			listToReturn = new ArrayList<TvvValuedEsDepTDLRepresentation>();

			for (TvvValuedEsDepTDLRepresentation obj : list) {
				TvvValuedEsDepTDL tdlObj = tvvValuedEsDepTDLRepository.load(obj.getEntityId());
				TvvValuedEsDepTDLRepresentation tdlRepresentation = new TvvValuedEsDepTDLRepresentation();
				tvvValuedEsDepTDLAssembler.doAssembleDtoFromAggregate(tdlRepresentation, tdlObj);
				tdlRepresentation.setTypeOfList(Constants.TVV_VALUED_ES_DEP_TDL);
				listToReturn.add(tdlRepresentation);
			}

			return Response.ok(listToReturn).build();
		}
		return Response.ok(listToReturn).build();

	}
}
