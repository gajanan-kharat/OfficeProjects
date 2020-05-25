/*
 * Creation : Nov 18, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.rest.resulttestpollutant;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.ws.rs.core.Response.Status;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.pollgasresultset.PollutantGasResultSetService;
import com.inetpsa.poc00.application.pollutantgastestresult.PollutantgasTestResultService;
import com.inetpsa.poc00.application.statisticalcalculations.CalculationService;
import com.inetpsa.poc00.application.vehiclefile.VehicleFileService;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetFactory;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSetRepository;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository;
import com.inetpsa.poc00.domain.vehicle.VehicleRepository;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFileRepository;
import com.inetpsa.poc00.domain.vehiclefilestatus.VehicleFileStatus;
import com.inetpsa.poc00.pollutantorgas.PollutantGasTestResultRepresentation;
import com.inetpsa.poc00.rest.resultset.PollGasResultSetAssembler;
import com.inetpsa.poc00.rest.resultset.PollGasResultSetFinder;
import com.inetpsa.poc00.rest.resultset.PollGasResultSetListRepresentation;
import com.inetpsa.poc00.rest.resultset.PollGasResultSetRepresentation;
import com.inetpsa.poc00.rest.resultset.PollGasResultSetUtilityHelper;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileAssembler;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileFinder;
import com.inetpsa.poc00.rest.vehiclefile.VehicleFileRepresentation;
import com.inetpsa.poc00.rest.vehiclefilestatus.VehicleFileStatusFinder;
import com.inetpsa.poc00.traceability.TraceabilityResource;

/**
 * The class ResultPollutantResource.
 * 
 * @author mehaj
 */
@Path("/resulttestpollutant")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class PollGasResultSetResource {

	/** The vehicle repository. */
	@Inject
	VehicleRepository vehicleRepository;

	/** The tvv valued es dep pgl finder. */
	@Inject
	PollGasResultSetFinder pollGasResultSetFinder;

	/** The pollutant gas result set repository. */
	@Inject
	PollutantGasResultSetRepository pollutantGasResultSetRepository;

	/** The vehicle file repository. */
	@Inject
	VehicleFileRepository vehicleFileRepository;

	/** The tvv valued poll gas limit service. */
	@Inject
	PollutantGasResultSetService pollutantGasResultSetService;

	/** The calculation service. */
	@Inject
	CalculationService calculationService;

	/** The poll gas result set assembler. */
	@Inject
	PollGasResultSetAssembler pollGasResultSetAssembler;

	/** The pollutantgas test result service. */
	@Inject
	PollutantgasTestResultService pollutantgasTestResultService;

	/** The vehicle file status finder. */
	@Inject
	VehicleFileStatusFinder vehicleFileStatusFinder;

	/** The vehicle file finder. */
	@Inject
	VehicleFileFinder vehicleFileFinder;

	/** The vehicle file service. */
	@Inject
	VehicleFileService vehicleFileService;

	/** The vehicle file assembler. */
	@Inject
	VehicleFileAssembler vehicleFileAssembler;

	/** The pollutant gas result set factory. */
	@Inject
	PollutantGasResultSetFactory pollutantGasResultSetFactory;

	/** the TraceabilityResource. */
	@Inject
	TraceabilityResource traceabilityResource;

	/** the StatisticalSampleRepository. */
	@Inject
	StatisticalSampleRepository statisticalSampleRepository;

	/** The Constant TEMP_PATH. */
	@Configuration("com.inetpsa.cop.export.exportbce.tempfiledirectory")
	private static String tempPath;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(PollGasResultSetResource.class);

	/**
	 * Pollutant gas result set.
	 * 
	 * @param vehicleFileId the vehicle file id
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/resultpollutant/{vehicleFileId}")
	public Response getPollGasResultSetList(@PathParam("vehicleFileId") long vehicleFileId) {
		logger.info("Loading Result set for vhicle file id : " + vehicleFileId);
		PollGasResultSetListRepresentation pollutantGasResultSetList = getResultSetData(vehicleFileId);
		logger.info("Get result set data successful ");
		return Response.ok(pollutantGasResultSetList).build();
	}

	/**
	 * Gets the vehiclefiledata.
	 * 
	 * @param vehicleFileId the vehicle file id
	 * @return the vehiclefiledata
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/vehiclefiledata/{vehicleFileId}")
	public Response getvehiclefiledata(@PathParam("vehicleFileId") long vehicleFileId) {
		logger.info("Get vehiclefile data ");
		VehicleFileRepresentation vf = vehicleFileFinder.getVehicleFileById(vehicleFileId);
		logger.info("Get vehicle file data successful ");
		return Response.ok(vf).build();
	}

	/**
	 * Save result set.
	 * 
	 * @param pollGasResultSetRepresentationlist the poll gas result set representationlist
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/resultpollutant")
	public Response saveResultSet(List<PollGasResultSetRepresentation> pollGasResultSetRepresentationlist) {
		logger.info("In save result set");
		for (PollGasResultSetRepresentation pollGasResultSetRepresentation : pollGasResultSetRepresentationlist) {
			PollutantGasResultSet pollGasResultSetToSave;
			if (pollGasResultSetRepresentation.getEntityId() != null) {
				pollGasResultSetToSave = pollutantGasResultSetRepository.load(pollGasResultSetRepresentation.getEntityId());
			} else
				pollGasResultSetToSave = new PollutantGasResultSet();
			pollGasResultSetAssembler.doMergeAggregateWithDto(pollGasResultSetToSave, pollGasResultSetRepresentation);
			pollutantGasResultSetService.savePollutantGasResultSetValues(pollGasResultSetToSave);
		}
		VehicleFile vehFileObj = vehicleFileRepository.loadVehicle(pollGasResultSetRepresentationlist.get(0).getVehiclefileId());
		if (vehFileObj.getSantorinReference() != pollGasResultSetRepresentationlist.get(0).getReferenceSantorin()) {
			vehFileObj.setSantorinReference(pollGasResultSetRepresentationlist.get(0).getReferenceSantorin());

			vehicleFileService.saveVehicleFile(vehFileObj);
			logger.info("Vehicle file data successfully saved ");
		}
		PollGasResultSetListRepresentation pollutantGasResultSetList = getResultSetData(vehFileObj.getEntityId());
		logger.info("Result set data successfully saved");
		return Response.ok(pollutantGasResultSetList).build();
	}

	/**
	 * Statistical calculation.
	 *
	 * @param pollGasResultSetRepresentationList the poll gas result set representation list
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/statisticaldecision")
	public Response statisticalCalculation(List<PollGasResultSetRepresentation> pollGasResultSetRepresentationList) {
		logger.info("In statistical calculation");
		PollGasResultSetRepresentation pollGasResultSetRepresentation = new PollGasResultSetRepresentation();
		for (PollGasResultSetRepresentation pgResultSetRep : pollGasResultSetRepresentationList) {
			if (pgResultSetRep.isKeptInStatisticalSample()) {
				PollutantGasResultSet resultSetObj = pollutantGasResultSetRepository.load(pgResultSetRep.getEntityId());
				resultSetObj.setKeptInStatSample(true);
				pollutantGasResultSetService.savePollutantGasResultSet(resultSetObj);
				PollutantGasResultSet modifiedResultSet = calculationService.calculateDecision(resultSetObj);
				if (modifiedResultSet.getVehicleFile().getStatisticalDecision().equals(ConstantsApp.ERROR_NO_STATISTICAL_PARAMS) || modifiedResultSet.getVehicleFile().getStatisticalDecision().equals(ConstantsApp.ERROR_NO_MAX_LIMIT)) {
					VehicleFile vehicleFileToSave = modifiedResultSet.getVehicleFile();
					vehicleFileToSave.setStatisticalDecision("");
					vehicleFileRepository.saveVehicle(vehicleFileToSave);
					resultSetObj.setKeptInStatSample(false);
					pollutantGasResultSetService.savePollutantGasResultSet(resultSetObj);
					return Response.status(Status.PRECONDITION_FAILED).build();
				}
				if (modifiedResultSet.getVehicleFile().getStatisticalDecision().equals(ConstantsApp.ERROR_NO_STATISTICAL_RULE)) {

					VehicleFile vehicleFileToSave = modifiedResultSet.getVehicleFile();
					vehicleFileToSave.setStatisticalDecision("");
					vehicleFileRepository.saveVehicle(vehicleFileToSave);

					resultSetObj.setKeptInStatSample(false);
					pollutantGasResultSetService.savePollutantGasResultSet(resultSetObj);

					return Response.status(Status.NOT_ACCEPTABLE).build();

				}
				pollGasResultSetAssembler.doAssembleDtoFromAggregate(pollGasResultSetRepresentation, modifiedResultSet);
			} else {
				PollutantGasResultSet resultSetObj = pollutantGasResultSetRepository.load(pgResultSetRep.getEntityId());
				if (resultSetObj.getKeptInStatSample()) {
					resultSetObj.setKeptInStatSample(false);
					pollutantGasResultSetService.savePollutantGasResultSet(resultSetObj);
				}
			}
		}
		logger.info("Calculation completed successfully");
		return Response.ok(pollGasResultSetRepresentation).build();
	}

	/**
	 * Qurantine pollutant gas result set.
	 * 
	 * @param pgResultSet the pg result set
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/inqurantine")
	public Response changeInQuarantineValue(PollGasResultSetRepresentation pgResultSet) {
		logger.info("Updating InQuarantineValue");
		pollutantGasResultSetService.changeQurantineFlag(pgResultSet.getEntityId(), pgResultSet.isInQuarantine());
		logger.info("InQuarantineValue Updated successfully ");
		return Response.ok().build();
	}

	/**
	 * Requestcountertest.
	 * 
	 * @param vehicleFileId the vehicle file id
	 * @return the response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/countertest/{vehicleFileId}")
	public Response changeVehicleFileStatus(@PathParam("vehicleFileId") long vehicleFileId) {
		logger.info("Updating vehicle file status ");
		VehicleFile vf = vehicleFileRepository.load(vehicleFileId);
		VehicleFileStatus vfs = vehicleFileStatusFinder.getVehicleFileStatusbyLabel(Constants.VEHICLEFILESTATUS_PREPARATIONCOMPLETE);
		vf.setVehicleFileStatus(vfs);
		VehicleFile vehicleFileSaved = vehicleFileService.saveVehicleFile(vf);
		VehicleFileRepresentation vehicleFileRep = new VehicleFileRepresentation();
		vehicleFileAssembler.doAssembleDtoFromAggregate(vehicleFileRep, vehicleFileSaved);
		logger.info("vehicle file status Updated successfully ");
		return Response.ok(vehicleFileRep).build();
	}

	/**
	 * Generate resultset pdf.
	 *
	 * @param pgResultSet the pg result set
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/resultsetpdf")
	public Response generateResultsetPDF(PollGasResultSetRepresentation pgResultSet) {
		PollutantGasResultSet resultSetObj = pollutantGasResultSetRepository.load(pgResultSet.getEntityId());
		String filePathWithName = tempPath + Constants.RESULTSET_PDF_FILENAME + "_" + new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()) + Constants.PDF_FILE;
		PollGasResultSetUtilityHelper.generatePDF(resultSetObj, filePathWithName);
		return Response.ok().build();
	}

	/**
	 * Download resultset pdf.
	 *
	 * @return the response
	 */
	@GET
	@Path("/resultsetpdfExport")
	@Produces("application/pdf")
	public Response downloadResultsetPDF() {

		String filePath = tempPath + Constants.RESULTSET_PDF_FILENAME + "_" + new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()) + Constants.PDF_FILE;
		String fileName = Constants.RESULTSET_PDF_FILENAME + "_" + new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()) + Constants.PDF_FILE;
		File file = new File(filePath);
		return Response.ok(file, "application/pdf").header("Content-Disposition", "attachment; filename=\"" + new File(fileName)).build();

	}

	/**
	 * Gets the result set data.
	 *
	 * @param vehicleFileId the vehicle file id
	 * @return the result set data
	 */
	private PollGasResultSetListRepresentation getResultSetData(Long vehicleFileId) {
		PollGasResultSetListRepresentation resultsetListForResponse = new PollGasResultSetListRepresentation();
		VehicleFile vehicleFileObj = vehicleFileRepository.load(vehicleFileId);
		List<PollGasResultSetRepresentation> pollutantGasResultSetList = new ArrayList<>();
		List<PollutantGasResultSet> listResultsetObj = pollutantGasResultSetService.getPollGasResultSet(vehicleFileObj);
		for (PollutantGasResultSet pollGasResultSetObj : listResultsetObj) {
			PollGasResultSetRepresentation pollGasResultSetRepresentation = new PollGasResultSetRepresentation();
			pollGasResultSetAssembler.doAssembleDtoFromAggregate(pollGasResultSetRepresentation, pollGasResultSetObj);
			pollutantGasResultSetList.add(pollGasResultSetRepresentation);
		}
		for (PollGasResultSetRepresentation pollGasResultSetRepresentation : pollutantGasResultSetList) {
			for (PollutantGasTestResultRepresentation pollutantGasTestResult : pollGasResultSetRepresentation.getPollutantGasTestResultRepresentationList()) {
				if (pollutantGasTestResult.getTvvValuedPollutantLimitRepresentation().getPgLabel().getLabel() == Constants.PGLABEL_CO2 && vehicleFileObj.getVehicle().getTechnicalCase().getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule().getLabel() == Constants.STATISTICAL_CALC_RULE_WLTP1) {
					pollutantGasTestResult.getTvvValuedPollutantLimitRepresentation().setMaxDValue(vehicleFileObj.getVehicle().getmCO2I());
				}
			}
			if (!pollGasResultSetRepresentation.isValidated()) {
				pollGasResultSetRepresentation.setIsCellEnabled(true);
				break;
			}

		}
		VehicleFileRepresentation vf = vehicleFileFinder.getVehicleFileById(vehicleFileId);
		resultsetListForResponse.setListResultSet(pollutantGasResultSetList);
		resultsetListForResponse.setVehicleFileRepresentation(vf);
		return resultsetListForResponse;
	}

	/**
	 * Generate resultset pdf.
	 *
	 * @param pgResultSet the pg result set
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/keptinstatisticalsample")
	public Response changeKeptInStatisticalSampleFlag(List<PollGasResultSetRepresentation> pgResultSetList) {
		for (PollGasResultSetRepresentation pollGasResultSetRepresentation : pgResultSetList) {
			PollutantGasResultSet resultSetObj = pollutantGasResultSetRepository.load(pollGasResultSetRepresentation.getEntityId());
			for (PollutantGasTestResult pollGasTestResult : resultSetObj.getPollutantGasTestResult()) {
				if (pollGasTestResult.getStatisticalDecision() != null) {
					resultSetObj.setKeptInStatSample(pollGasResultSetRepresentation.isKeptInStatisticalSample());
					pollutantGasResultSetService.savePollutantGasResultSet(resultSetObj);
					break;
				}
			}

		}

		return Response.ok().build();
	}
}