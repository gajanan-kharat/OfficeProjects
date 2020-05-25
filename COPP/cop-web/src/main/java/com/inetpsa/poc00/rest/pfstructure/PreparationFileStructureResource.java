/*
 * Creation : Oct 13, 2016
 */
package com.inetpsa.poc00.rest.pfstructure;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.genericpreparationchecklist.GenericPreparationChecklistFactory;
import com.inetpsa.poc00.domain.genericpreparationchecklist.GenericPreparationChecklistRepository;
import com.inetpsa.poc00.domain.genericpreparationitem.GenericPreparationItemFactory;
import com.inetpsa.poc00.domain.genericpreparationitem.GenericPreparationItemRepository;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructure;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructureFactory;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructureRepository;
import com.inetpsa.poc00.rest.genericpreparationchecklist.GenericPreparationCheckListAssembler;
import com.inetpsa.poc00.rest.genericpreparationchecklist.GenericPreparationCheckListFinder;
import com.inetpsa.poc00.rest.genericpreparationchecklist.GenericPreparationCheckListRepresentation;
import com.inetpsa.poc00.rest.genericpreparationitem.PreparationItemAssembler;
import com.inetpsa.poc00.rest.genericpreparationitem.PreparationItemFinder;

/**
 * The Class PreparationFileStructureResource.
 */
@Path("/pfStructure")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class PreparationFileStructureResource {

	/** The pfs finder. */
	@Inject
	private PreparationFileStructureFinder pfsFinder;

	/** The pfs assembler. */
	@Inject
	private PreparationFileStructureAssembler pfsAssembler;

	/** The pfs factory. */
	@Inject
	private PreparationFileStructureFactory pfsFactory;

	/** The pfs repo. */
	@Inject
	private PreparationFileStructureRepository pfsRepo;

	/** The gpc finder. */
	@Inject
	private GenericPreparationCheckListFinder gpcFinder;

	/** The gpc factory. */
	@Inject
	private GenericPreparationChecklistFactory gpcFactory;

	/** The gpc repo. */
	@Inject
	private GenericPreparationChecklistRepository gpcRepo;

	/** The gpc assembler. */
	@Inject
	private GenericPreparationCheckListAssembler gpcAssembler;

	/** The gpi factory. */
	@Inject
	private GenericPreparationItemFactory gpiFactory;

	/** The gpi repo. */
	@Inject
	private GenericPreparationItemRepository gpiRepo;

	/** The preparation item finder. */
	@Inject
	private PreparationItemFinder preparationItemFinder;

	/** The preparation item assembler. */
	@Inject
	private PreparationItemAssembler preparationItemAssembler;

	/**
	 * Gets the preparation structure.
	 *
	 * @return the preparation structure
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/preparationstructure")
	public Response getPreparationStructure() {

		PreparationFileStructureRepresentation pfsRepObj = pfsFinder.getPFSList();
		if (pfsRepObj != null) {
			List<GenericPreparationCheckListRepresentation> gpcList = gpcFinder.getGPCListbypfsId(pfsRepObj.getEntityId());

			for (GenericPreparationCheckListRepresentation gpcObj : gpcList) {

				gpcObj.setPreparationItems(preparationItemFinder.getPreparationItembygpcId(gpcObj.getEntityId()));
			}
			pfsRepObj.setPreparationChecklists(gpcList);
		}
		return Response.ok(pfsRepObj).build();
	}

	/**
	 * Save preparation structure.
	 *
	 * @param pfsRep the pfs rep
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/preparationstructure")
	public Response savePreparationStructure(PreparationFileStructureRepresentation pfsRep) {

		PreparationFileStructure pfsObj = pfsFactory.createPreparationFileStructure();

		pfsAssembler.doMergeAggregateWithDto(pfsObj, pfsRep);

		pfsRepo.savePfs(pfsObj);

		return Response.ok().build();

	}

}
