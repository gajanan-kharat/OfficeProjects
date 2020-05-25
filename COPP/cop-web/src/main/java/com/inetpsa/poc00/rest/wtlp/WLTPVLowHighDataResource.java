package com.inetpsa.poc00.rest.wtlp;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.google.inject.Inject;
import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighData;
import com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighDataFactory;
import com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighDataRepository;

/**
 * The Class WLTPVLowHighDataResource.
 */
@Path("/WLTPVLowHighData")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class WLTPVLowHighDataResource {

	/** The wltp assembler. */
	@Inject
	private WLTPVLowHighDataAssembler wltpAssembler;

	/** The wltp factory. */
	@Inject
	private WLTPVLowHighDataFactory wltpFactory;

	/** The wltp repository. */
	@Inject
	private WLTPVLowHighDataRepository wltpRepository;

	/**
	 * Save WLTPV low high data.
	 * 
	 * @param wltpDto the wltp dto
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/WLTPVLowHighData")
	public Response saveWLTPVLowHighData(WLTPVLowHighDataRepresentation wltpDto) {
		WLTPVLowHighDataRepresentation wltpRepresent = new WLTPVLowHighDataRepresentation();
		WLTPVLowHighData wltpEntity = wltpFactory.createWLTPVLowHighData();
		wltpAssembler.doMergeAggregateWithDto(wltpEntity, wltpDto);
		WLTPVLowHighData wltp = wltpRepository.saveWLTPVLowHighData(wltpEntity);
		wltpAssembler.doAssembleDtoFromAggregate(wltpRepresent, wltp);
		return Response.ok(wltpRepresent).build();
	}
}
