package com.inetpsa.poc00.rest.tvvvaluedpgl;

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
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGLRepository;

@Path("/tvvValuedEsDepPGL")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TvvValuedEsDepPGLResource {

	@Inject
	private TvvValuedEsDepPGLRepository tvvValuedEsDepPGLRepository;

	@Inject
	private TvvValuedEsDepPGLAssembler tvvValuedEsDepPGLAssembler;

	@Inject
	private TvvValuedEsDepPGLFinder tvvValuedEsDepPGLFinder;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allEsDepPGL/{tvvId}/{emsId}")
	public Response getEsDependentLists(@PathParam("tvvId") String tvvId, @PathParam("emsId") String emsId) {
		List<TvvValuedEsDepPGLRepresentation> listToReturn = null;
		if (emsId != null && tvvId != null) {

			long tvvID = Long.parseLong(tvvId);
			long emsID = Long.parseLong(emsId);
			List<TvvValuedEsDepPGLRepresentation> list = tvvValuedEsDepPGLFinder.getAllValuedPGL(tvvID, emsID);
			listToReturn = new ArrayList<TvvValuedEsDepPGLRepresentation>();

			for (TvvValuedEsDepPGLRepresentation obj : list) {
				TvvValuedEsDepPGL pglObj = tvvValuedEsDepPGLRepository.load(obj.getEntityId());
				TvvValuedEsDepPGLRepresentation pglRepresentation = new TvvValuedEsDepPGLRepresentation();
				tvvValuedEsDepPGLAssembler.doAssembleDtoFromAggregate(pglRepresentation, pglObj);
				pglRepresentation.setTypeOfList(Constants.TVV_VALUED_ES_DEP_PGL);
				listToReturn.add(pglRepresentation);
			}

			return Response.ok(listToReturn).build();
		}
		return Response.ok(listToReturn).build();

	}
}
