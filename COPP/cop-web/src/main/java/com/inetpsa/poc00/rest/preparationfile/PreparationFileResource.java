package com.inetpsa.poc00.rest.preparationfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.security.SecuritySupport;
import org.seedstack.seed.security.principals.Principals;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckList;
import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckListFactory;
import com.inetpsa.poc00.domain.preparationfile.PreparationFile;
import com.inetpsa.poc00.domain.preparationfile.PreparationFileFactory;
import com.inetpsa.poc00.domain.preparationfile.PreparationFileRepository;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructure;
import com.inetpsa.poc00.domain.preparationfilestructure.PreparationFileStructureRepository;
import com.inetpsa.poc00.domain.preparationresult.PreparationResultFactory;
import com.inetpsa.poc00.domain.user.UserRepository;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehicle.VehicleFactory;
import com.inetpsa.poc00.domain.vehicle.VehicleRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatusRepository;
import com.inetpsa.poc00.rest.pfstructure.PreparationFileStructureFinder;
import com.inetpsa.poc00.rest.vehicle.VehicleAssembler;
import com.inetpsa.poc00.rest.vehicle.VehicleRepresentation;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileAssembler;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * The Class PreparationFileResource.
 */
@Path("/preparationFile")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class PreparationFileResource {

	/** The prep file finder. */
	@Inject
	PreparationFileFinder prepFileFinder;

	/** The prep file assembler. */
	@Inject
	PreparationFileAssembler prepFileAssembler;

	/** The prep file repo. */
	@Inject
	PreparationFileRepository prepFileRepo;

	/** The prep file factory. */
	@Inject
	PreparationFileFactory prepFileFactory;

	/** The perp file struct finder. */
	@Inject
	PreparationFileStructureFinder perpFileStructFinder;

	/** The prep check list factory. */
	@Inject
	PreparationCheckListFactory prepCheckListFactory;

	/** The vehicle file repo. */
	@Inject
	VehicleFileRepository vehicleFileRepo;

	/** The prep result factory. */
	@Inject
	PreparationResultFactory prepResultFactory;

	/** The vehicle file status repo. */
	@Inject
	VehicleFileStatusRepository vehicleFileStatusRepo;

	/** The prep file struct repo. */
	@Inject
	PreparationFileStructureRepository prepFileStructRepo;

	/** The vehicle file assembler. */
	@Inject
	VehicleFileAssembler vehicleFileAssembler;

	/** The security support. */
	@Inject
	private SecuritySupport securitySupport;

	/** The user repo. */
	@Inject
	private UserRepository userRepo;

	/** The history service. */
	@Inject
	private TraceabilityService historyService;

	/** The vehicle assembler. */
	@Inject
	private VehicleAssembler vehicleAssembler;

	/** The vehicle factory. */
	@Inject
	private VehicleFactory vehicleFactory;

	/** The vehicle repo. */
	@Inject
	private VehicleRepository vehicleRepo;

	/** The temp path. */
	@Configuration("com.inetpsa.cop.export.exportbce.tempfiledirectory")
	private String tempPath;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(PreparationFileResource.class);

	/**
	 * Gets the preparation file.
	 * 
	 * @param vehicleFileId the vehicle file id
	 * @return the preparation file
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/preparationfilesdata/{vehicleFileId}")
	public Response getPreparationFile(@PathParam("vehicleFileId") Long vehicleFileId) {

		PreparationFile preparationFile = prepFileFinder.findPrepFileByVehicleFileId(vehicleFileId);

		PreparationFileRepresentation prepFileRepresentation = new PreparationFileRepresentation();

		if (preparationFile != null) {

			prepFileAssembler.doAssembleDtoFromAggregate(prepFileRepresentation, preparationFile);
			PreparationFileUtil.filterCheckListByFuelType(prepFileRepresentation);
		} else {

			VehicleFile vehicleFile = vehicleFileRepo.loadVehicle(vehicleFileId);
			if (vehicleFile != null) {
				prepFileAssembler.setHeaderData(prepFileRepresentation, vehicleFile);
			}

		}

		return Response.ok(prepFileRepresentation).build();

	}

	/**
	 * Save preparation file.
	 * 
	 * @param prepFileRepresentation the prep file representation
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/preparationfile")
	public Response savePreparationFile(PreparationFileRepresentation prepFileRepresentation) {

		vehicleUpdate(prepFileRepresentation.getVehicleFileRepresentation().getVehicleRepresentation());

		PreparationFile preparationFile = prepFileFactory.createPreparationFile();

		prepFileAssembler.doMergeAggregateWithDto(preparationFile, prepFileRepresentation);

		String userId = securitySupport.getSimplePrincipalByName(Principals.IDENTITY).getPrincipal();

		logger.info("Saving Preparation File by User : {}", userId);

		preparationFile.setUserPrepFile(userRepo.getUserById(userId));

		prepFileRepo.savePreparationFile(preparationFile);

		return Response.ok().build();

	}

	/**
	 * Creates the preparation file.
	 * 
	 * @param vehicleFileId the vehicle file id
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/PreparationFile/{vehicleFileId}")
	public Response createPreparationFile(@PathParam("vehicleFileId") Long vehicleFileId) {

		logger.info("Creating Preparation File for vehicle File Id : {}", vehicleFileId);

		VehicleFile vehicleFile = changeVehicleFileStatus(vehicleFileId, "Preparation in Progress");

		PreparationFile preparationFile = prepFileFactory.createPreparationFile();

		preparationFile.setVehicleFile(vehicleFile);

		PreparationFileStructure prepFileStructure = perpFileStructFinder.getLatestPrepFileStructure();

		preparationFile.setPrepFileStructure(prepFileStructure);

		/*
		 * PreparationCheckList
		 * 
		 * 1) Copy From Preparation File Structure
		 */
		PreparationFileUtil util = new PreparationFileUtil(prepResultFactory, prepCheckListFactory);

		List<PreparationCheckList> pclList = util.copyGenericPrepCheckList(preparationFile, prepFileStructure);

		preparationFile.setPreparationCheckList(pclList);

		/* 2) By Default Preparation Check List */
		List<PreparationCheckList> defaultPrepCheckList = util.getDefaultPreparationCheckList(preparationFile);

		preparationFile.getPreparationCheckList().addAll(defaultPrepCheckList);

		logger.debug("Saving Preparation File : {}", preparationFile);

		/* 3) Setting User in Preparation File */
		String userId = securitySupport.getSimplePrincipalByName(Principals.IDENTITY).getPrincipal();

		logger.info("Saving Preparation File by User : {}", userId);

		preparationFile.setUserPrepFile(userRepo.getUserById(userId));

		preparationFile = prepFileRepo.savePreparationFile(preparationFile);

		logger.info("Preparation File Saved, Id : {}", preparationFile.getEntityId());

		PreparationFileRepresentation prepFileRepresentation = new PreparationFileRepresentation();

		prepFileAssembler.doAssembleDtoFromAggregate(prepFileRepresentation, preparationFile);

		PreparationFileUtil.filterCheckListByFuelType(prepFileRepresentation);

		return Response.ok(prepFileRepresentation).build();
	}

	/**
	 * Vehicle file status.
	 * 
	 * @param vehicleFileId the vehicle file id
	 * @param vehicleFileStatus the vehicle file status
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/vehiclefilestatus/{vehicleFileId}/{vehicleFileStatus}")
	public Response vehicleFileStatus(@PathParam("vehicleFileId") Long vehicleFileId, @PathParam("vehicleFileStatus") String vehicleFileStatus) {

		changeVehicleFileStatus(vehicleFileId, vehicleFileStatus);
		return Response.ok().build();
	}

	/**
	 * Change vehicle file status.
	 * 
	 * @param vehicleFileId the vehicle file id
	 * @param vehicleFileStatus the vehicle file status
	 * @return the vehicle file
	 */
	private VehicleFile changeVehicleFileStatus(Long vehicleFileId, String vehicleFileStatus) {

		logger.info("Changing vehicle File Status, Vehicle File Id : {}, VehicleFileStatus : {}", vehicleFileId, vehicleFileStatus);

		VehicleFile vehicleFile = vehicleFileRepo.load(vehicleFileId);

		VehicleFileStatus vfStatus = vehicleFileStatusRepo.getVehicleFileStatusbyLabel(vehicleFileStatus);

		vehicleFile.setVehicleFileStatus(vfStatus);

		historyService.saveVehicleFileHistory(vehicleFile, null, vfStatus.getLabel());

		logger.info("Vehicle File status has been changed : {}", vehicleFileStatus);

		return vehicleFileRepo.saveVehicle(vehicleFile);

	}

	/**
	 * Vehicle update.
	 *
	 * @param vehicleRepresentation the vehicle representation
	 */
	private void vehicleUpdate(VehicleRepresentation vehicleRepresentation) {

		Vehicle vehicle = vehicleFactory.createVehicle();
		vehicleAssembler.doMergeAggregateWithDto(vehicle, vehicleRepresentation);
		Vehicle newVehicle = vehicleRepo.updateVehicle(vehicle);
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/pdf")
	@Path("/ReceptionFilePdfGenerate/{vehicleFileId}")
	public Response genreatePdfPreparationLists(@PathParam("vehicleFileId") Long vehicleFileId) {

		VehicleFile vehicleFile = vehicleFileRepo.load(vehicleFileId);

		PreparationFilePrintDto printDto = PreparationFilePdfUtil.preparePrintData(vehicleFile);

		String fileName = "Parametrage_Fiche_Prepa_" + new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()) + Constants.PDF_FILE;

		String filePathWithName = tempPath + fileName;

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(filePathWithName));
			document.open();
			document.add(PreparationFilePdfUtil.createFirstTable(printDto));
			document.close();

		} catch (FileNotFoundException | DocumentException e) {
			logger.error("Error while creating Pdf", e);
		}

		File file = new File(filePathWithName);

		return Response.ok(file, "application/pdf").header("Content-Disposition", "attachment; filename=\"" + new File(fileName)).build();

	}

}