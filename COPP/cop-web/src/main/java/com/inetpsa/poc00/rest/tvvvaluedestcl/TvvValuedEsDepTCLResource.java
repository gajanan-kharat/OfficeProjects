package com.inetpsa.poc00.rest.tvvvaluedestcl;

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
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCL;
import com.inetpsa.poc00.domain.tvvvaluedesdeptcl.TvvValuedEsDepTCLRepository;

/**
 * The Class TvvValuedEsDepTCLResource.
 */
@Path("/tvvValuedEsDepTCL")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class TvvValuedEsDepTCLResource {

	/** The tvv valued es dep tcl assembler. */
	@Inject
	private TvvValuedEsDepTCLAssembler tvvValuedEsDepTCLAssembler;

	/** The tvv valued es dep tcl repository. */
	@Inject
	private TvvValuedEsDepTCLRepository tvvValuedEsDepTCLRepository;

	/** The tvv valued es dep tcl finder. */
	@Inject
	private TvvValuedEsDepTCLFinder tvvValuedEsDepTCLFinder;

	/**
	 * Gets the es dependent tcl.
	 * 
	 * @param tvvId the tvv id
	 * @param emsId the ems id
	 * @return the es dependent tcl
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allEsDepTCL/{tvvId}/{emsId}")
	public Response getEsDependentTCL(@PathParam("tvvId") String tvvId, @PathParam("emsId") String emsId) {
		List<TvvValuedEsDepTCLRepresentation> listToReturn = null;
		if (emsId != null && tvvId != null) {

			long tvvID = Long.parseLong(tvvId);
			long emsID = Long.parseLong(emsId);
			List<TvvValuedEsDepTCLRepresentation> list = tvvValuedEsDepTCLFinder.getAllValuedTDL(tvvID, emsID);
			listToReturn = new ArrayList<TvvValuedEsDepTCLRepresentation>();

			for (TvvValuedEsDepTCLRepresentation obj : list) {
				TvvValuedEsDepTCL tclObj = tvvValuedEsDepTCLRepository.load(obj.getEntityId());
				TvvValuedEsDepTCLRepresentation tclRepresentation = new TvvValuedEsDepTCLRepresentation();
				tvvValuedEsDepTCLAssembler.doAssembleDtoFromAggregate(tclRepresentation, tclObj);
				tclRepresentation.setTypeOfList(Constants.TVV_VALUED_ES_DEP_TCL);
				listToReturn.add(tclRepresentation);
			}

			return Response.ok(listToReturn).build();
		}
		return Response.ok(listToReturn).build();

	}

}
