
package com.inetpsa.poc00.rest.statisticalcurves;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.common.PropertiesLang;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSample;
import com.inetpsa.poc00.domain.statisticalsample.StatisticalSampleRepository;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.vehicle.Vehicle;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.inetpsa.poc00.pollutantorgas.PollutantGasTestResultRepresentation;
import com.inetpsa.poc00.rest.statisticalsample.StatisticalSampleRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvAssembler;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedEsDepPGLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedPollutantLimitRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedpgl.ValuedPollutantGasLimitFinder;
import com.inetpsa.poc00.rest.tvvvaluedtdl.TvvValuedTvvDepTDLRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedtdl.ValuedGenericDataRepresentation;
import com.inetpsa.poc00.rest.vehicle.VehicleAssembler;
import com.itextpdf.text.DocumentException;

/**
 * The Class ExportStatisticalCurvesDataResource.
 */
/**
 * 
 * 
 * @author ankurk
 *
 */
@Path("/ExportStatisticalCurveReference")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class ExportStatisticalCurvesDataResource {

	/** The property lang. */
	private static PropertiesLang propertyLang = PropertiesLang.getInstance();

	/** The tvv assembler. */
	@Inject
	private TvvAssembler tvvAssembler;

	/** The valued pollutant gas limit finder. */
	@Inject
	private ValuedPollutantGasLimitFinder valuedPollutantGasLimitFinder;

	/** The export statistical curves data representation assembler. */
	@Inject
	private ExportStatisticalCurvesDataAssembler exportStatCurvesDataAssembler;

	/** The statistical sample repository. */
	@Inject
	private StatisticalSampleRepository statisticalSampleRepository;

	/** The vehicle assembler. */
	@Inject
	VehicleAssembler vehicleAssembler;

	/** The logger. */
	@Logging
	private Logger logger;

	/** The temp path. */

	@Configuration("com.inetpsa.cop.export.exportbce.tempfiledirectory")
	private String tempPath;

	/** The pdf file name. */
	private String pdfFileName;

	/** The excel file name. */
	private String excelFileName;

	/** The format. */
	private static String format = new SimpleDateFormat("yyMMddHHmmss").format(new Date());

	/** The stat curves data repre. */
	private ExportStatisticalCurvesDataRepresentation statCurvesDataRepre;

	/*
	 * 
	 * 
	 * 
	 * 
	 * public methods
	 * 
	 * 
	 * 
	 */

	/**
	 * Gets the data before import.
	 *
	 * @param sampleRep the sample rep
	 * @return the data before import
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ExportStatisticalCurve")
	public Response getDataBeforeImport(StatisticalSampleRepresentation sampleRep) {
		statCurvesDataRepre = new ExportStatisticalCurvesDataRepresentation();
		List<String> pgGasLabelList = new ArrayList<>();
		logger.info("sampleRep.getEntityId()----------->" + sampleRep.getEntityId());
		statCurvesDataRepre.setSampleEntityId(sampleRep.getEntityId());
		StatisticalSample statisticalSample = statisticalSampleRepository.getStatisticalSampleForStatCurves(sampleRep.getEntityId());

		if (statisticalSample != null) {
			// tc
			TechnicalCase technicalCase = statisticalSample.getTechnicalCase();
			if (technicalCase != null) {
				setTechnicalCaseData(technicalCase, statCurvesDataRepre);
				// tvv
				TvvRepresentation tvvRepresentation = tvvAssembler.assembleDtoFromAggregate(technicalCase.getTvv());
				List<TvvValuedTvvDepTDLRepresentation> tdlList = tvvRepresentation.getTvvValuedTvvDepTDL();
				for (TvvValuedTvvDepTDLRepresentation tdl : nullGuard(tdlList)) {
					setCalculatorValue(statCurvesDataRepre, tdl);

				}
				setTvvRepresntationData(tvvRepresentation, statCurvesDataRepre);

				setPgLabelList(pgGasLabelList, tvvRepresentation);
			}
		}

		statCurvesDataRepre.setPgGasLabelList(pgGasLabelList);
		return Response.ok(statCurvesDataRepre).build();
	}

	/**
	 * Generate excel file.
	 *
	 * @param dto the dto
	 * @return the response
	 */
	@POST
	@Path("/ExportSCExcel")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generateExcelFile(ManageExportStatisticalCurvesDataRepresentationDto dto) {

		logger.info("**********     STARTED : Excel File Generation, {}      **********", new Date());
		logger.info("*****************  {} ", tempPath);

		excelFileName = "ExportSC_" + format + ".xls";
		String filePathWithName = tempPath + excelFileName;
		logger.info("******************arraylist------------>***************  {} ", dto.getChoiceList());
		// Creating Excel file with dataList
		statCurvesDataRepre = dto.getStatCurveData();
		try {
			prepareDataToImport(statCurvesDataRepre);
			new ExportStatisticalCurvesExcelGeneratorUtility().writeIntoScExcel(filePathWithName, statCurvesDataRepre, dto.getChoiceList());
		} catch (IOException statisticalCurvesException) {
			logger.error("error in excel generation {}", statisticalCurvesException);
		}

		logger.info("response excel generation");
		// Creating Response and Adding File to Response
		ResponseBuilder response = Response.ok(new File(filePathWithName), "application/vnd.ms-excel");

		// Setting Response header
		response.header(Constants.CONTENT_DIPOSITION, Constants.ATTACHMENT_FILENAME + excelFileName + "\"");
		logger.info("**********     COMPLETED : Excel File Generation, {}     **********", new Date());

		return response.build();
	}

	/**
	 * Download stat curve excel.
	 *
	 * @return the response
	 */
	@GET
	@Path("/exportStatCurveExcel")
	@Produces("application/vnd.ms-excel")
	public Response downloadStatCurveExcel() {
		excelFileName = "ExportSC_" + format + ".xls";
		File file = new File(tempPath + excelFileName);
		return Response.ok(file, "application/vnd.ms-excel").header("Content-Disposition", "attachment; filename=\"" + excelFileName).build();

	}

	/**
	 * Export pdf file.
	 *
	 * @param dto the dto
	 * @return the response
	 */
	@POST
	@Path("/ExportSCPdf")
	@Produces("application/pdf")
	public Response generatePdfFile(ManageExportStatisticalCurvesDataRepresentationDto dto) {

		logger.info("**********     STARTED : PDF File Generation, {}      **********", new Date());

		statCurvesDataRepre = dto.getStatCurveData();
		pdfFileName = "ExportPDF_" + format + ".pdf";
		String filePathWithName = tempPath + pdfFileName;
		try {
			prepareDataToImport(statCurvesDataRepre);
			new ExportStatisticalCurvePdfGeneratorUtility().createStatisticalCurvePdf(filePathWithName, statCurvesDataRepre, dto.getChoiceList());
		} catch (IOException | DocumentException statisticalCurvesException) {
			logger.error("error in pdf generation {}", statisticalCurvesException);
		}

		logger.info("response pdf generation");

		// Creating Response and Adding File to Response
		ResponseBuilder response = Response.ok(new File(filePathWithName), "application/pdf");

		// Setting Response header
		response.header(Constants.CONTENT_DIPOSITION, Constants.ATTACHMENT_FILENAME + pdfFileName + "\"");

		logger.info("**********     COMPLETED : PDF File Generation, {}     **********", new Date());

		return response.build();

	}

	/**
	 * Download stat curve pdf.
	 *
	 * @return the response
	 */
	@GET
	@Path("/exportStatCurvePdf")
	@Produces("application/pdf")
	public Response downloadStatCurvePDF() {
		pdfFileName = "ExportPDF_" + format + ".pdf";
		File file = new File(tempPath + pdfFileName);
		return Response.ok(file, "application/pdf").header("Content-Disposition", "attachment; filename=\"" + pdfFileName).build();

	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * private methods
	 * 
	 * 
	 * 
	 */

	/**
	 * Sets the pg label list.
	 *
	 * @param pgGasLabelList the pg gas label list
	 * @param tvvRepresentation the tvv representation
	 */
	private void setPgLabelList(List<String> pgGasLabelList, TvvRepresentation tvvRepresentation) {
		List<TvvValuedEsDepPGLRepresentation> tvvValuedEsDepPGLRepresentationsList = tvvRepresentation.getTvvValuedEsDepPGL();
		List<TvvValuedPollutantLimitRepresentation> pgLabelRepresentationList = null;
		for (TvvValuedEsDepPGLRepresentation valuedEsDepPGLRepresentation : nullGuard(tvvValuedEsDepPGLRepresentationsList)) {

			pgLabelRepresentationList = exportStatCurvesDataAssembler.fromTvvValuedPollutantGasLimitToTvvValuedPollutantLimitRepresentation(valuedPollutantGasLimitFinder.getAllLimitsForEmsDepList(valuedEsDepPGLRepresentation.getEntityId()));
		}

		for (TvvValuedPollutantLimitRepresentation tvvpgl : nullGuard(pgLabelRepresentationList)) {

			if (tvvpgl.getPgLabel() != null)
				pgGasLabelList.add(tvvpgl.getPgLabel().getLabel());
			else
				pgGasLabelList.add("");
		}
	}

	/**
	 * Sets the statistical curves based on statistical sample.
	 *
	 * @param statCurvesDataRepre the sample
	 * @return the response
	 */

	private void prepareDataToImport(ExportStatisticalCurvesDataRepresentation statCurvesDataRepre) {

		try {
			StatisticalSample statisticalSample = statisticalSampleRepository.getStatisticalSampleForStatCurves(statCurvesDataRepre.getSampleEntityId());

			// sample
			statCurvesDataRepre.setTypeOfTestLabel(statisticalSample.getTestType().getLabel());
			statCurvesDataRepre.setVehicleFactoryLabel(statisticalSample.getCarFactory().getLabel());
			setPollutantGasResultMap(statisticalSample, statCurvesDataRepre);
			List<String> vfStatDecisionList = new ArrayList<>();
			for (VehicleFile vf : statisticalSampleRepository.getVehicleFile(statCurvesDataRepre.getSampleEntityId())) {
				vfStatDecisionList.add(vf.getStatisticalDecision());
			}
			statCurvesDataRepre.setVehicleFileStatisticalDecision(vfStatDecisionList);

			// pg
			Set<PollutantGasResultSet> pgResultList = new HashSet<>(statisticalSampleRepository.getPollutantGasResultSet(statCurvesDataRepre.getSampleEntityId()));
			for (PollutantGasResultSet resultSet : nullGuard(pgResultList)) {
				resultSet.getPollutantGasTestResult().size();
			}
			setPgResultSet(pgResultList, statCurvesDataRepre);

			logger.info("all data set---------------->");
		} catch (Exception statisticalCurvesDataException) {
			logger.error("Exception While Importing Data : ", statisticalCurvesDataException);

		}

	}

	/**
	 * Export excel file.
	 *
	 * @param statCurvesDataRepre the stat curves data repre
	 * @param tdl the tdl
	 * @return the response
	 */

	/**
	 * Sets the preparation check list label.
	 *
	 * @param statCurvesDataRepre the stat curves data repre
	 * @param tdl the tdl
	 */
	private void setCalculatorValue(ExportStatisticalCurvesDataRepresentation statCurvesDataRepre, TvvValuedTvvDepTDLRepresentation tdl) {
		if (propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.mechanicalmachine").equalsIgnoreCase(tdl.getLabel())) {
			for (ValuedGenericDataRepresentation vgd : nullGuard(tdl.getValuedGenericData())) {
				if (propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.mark").equalsIgnoreCase(vgd.getLabel())) {
					statCurvesDataRepre.setCalculatorValue(vgd.getDefaultValue());
				}
			}

		}
	}

	/**
	 * Sets the technical case data.
	 *
	 * @param technicalCase the technical case
	 * @param statCurvesDataRepre the stat curves data repre
	 */
	private void setTechnicalCaseData(TechnicalCase technicalCase, ExportStatisticalCurvesDataRepresentation statCurvesDataRepre) {

		statCurvesDataRepre.setRegulationGroupLabel(technicalCase.getTechnicalGroup().getRegulationGroup().getLabel());
		statCurvesDataRepre.setStatisticalCalculationRuleLabel(technicalCase.getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule().getLabel());
		statCurvesDataRepre.setEmissionStandardLabel(technicalCase.getEmissionStandard().getEsLabel());
		statCurvesDataRepre.setEmissionStandardVersion(technicalCase.getEmissionStandard().getVersion());
	}

	/**
	 * Sets the pollutant gas result map.
	 *
	 * @param statisticalSample the technical case
	 * @param statCurvesDataRepre the stat curves data repre
	 */
	private void setPollutantGasResultMap(StatisticalSample statisticalSample, ExportStatisticalCurvesDataRepresentation statCurvesDataRepre) {

		Map<String, Map<String, PollutantGasTestResultRepresentation>> vehiclePgLabelBasedTestResultMap = new LinkedHashMap<>();
		List<String> vehcileChassisNumberList = new ArrayList<>();
		List<Vehicle> vlist = statisticalSampleRepository.getVehicleBasedOnPgResult(statisticalSample.getEntityId());
		if (vlist != null && !vlist.isEmpty()) {
			Set<Vehicle> vSet = new HashSet<>(vlist);
			getPgBasedOnVehicle(vehiclePgLabelBasedTestResultMap, vehcileChassisNumberList, vSet);

		}
		statCurvesDataRepre.setVehicleChassisNumber(vehcileChassisNumberList);
		statCurvesDataRepre.setVehicleBasedPollutantGasTestResult(vehiclePgLabelBasedTestResultMap);
	}

	/**
	 * Gets the pg based on vehicle.
	 *
	 * @param vehiclePgLabelBasedTestResultMap the vehicle pg label based test result map
	 * @param vehcileChassisNumberList the vehcile chassis number list
	 * @param vSet the v set
	 * @return the pg based on vehicle
	 */
	private void getPgBasedOnVehicle(Map<String, Map<String, PollutantGasTestResultRepresentation>> vehiclePgLabelBasedTestResultMap, List<String> vehcileChassisNumberList, Set<Vehicle> vSet) {
		for (Vehicle vehicle : nullGuard(vSet)) {
			logger.info("vehicle.getChasisNumber()----------->" + vehicle.getChasisNumber());
			if (vehicle.getChasisNumber() != null) {
				vehcileChassisNumberList.add(vehicle.getChasisNumber());
			} else
				vehcileChassisNumberList.add("########");
			List<PollutantGasTestResult> pgtrList = statisticalSampleRepository.getPollutantGasTestForVehicles(vehicle.getEntityId());
			Map<String, PollutantGasTestResultRepresentation> pgLabelBasedPollutantGasTestResultMap = new LinkedHashMap<>();

			if (!pgtrList.isEmpty()) {
				List<PollutantGasTestResultRepresentation> pgtRepsList = exportStatCurvesDataAssembler.fromPGTestToPGTestRepresentation(pgtrList);

				for (PollutantGasTestResultRepresentation pgtrRepresentation : nullGuard(pgtRepsList)) {

					pgLabelBasedPollutantGasTestResultMap.put(pgtrRepresentation.getTvvValuedPollutantLimitRepresentation().getPgLabel().getLabel(), pgtrRepresentation);

				}
			}
			setKeyForPgMap(vehiclePgLabelBasedTestResultMap, vehicle, pgLabelBasedPollutantGasTestResultMap);

		}
	}

	/**
	 * Sets the key for pg map.
	 *
	 * @param vehiclePgLabelBasedTestResultMap the vehicle pg label based test result map
	 * @param vehicle the vehicle
	 * @param pgLabelBasedPollutantGasTestResultMap the pg label based pollutant gas test result map
	 */
	private void setKeyForPgMap(Map<String, Map<String, PollutantGasTestResultRepresentation>> vehiclePgLabelBasedTestResultMap, Vehicle vehicle, Map<String, PollutantGasTestResultRepresentation> pgLabelBasedPollutantGasTestResultMap) {
		if (vehicle.getRegistrationNumber() != null)
			vehiclePgLabelBasedTestResultMap.put(vehicle.getRegistrationNumber(), pgLabelBasedPollutantGasTestResultMap);
		else {
			if (vehicle.getChasisNumber() != null)
				vehiclePgLabelBasedTestResultMap.put(vehicle.getChasisNumber(), pgLabelBasedPollutantGasTestResultMap);
			else {
				if (vehicle.getCounterMark() != null)
					vehiclePgLabelBasedTestResultMap.put(vehicle.getCounterMark(), pgLabelBasedPollutantGasTestResultMap);
			}
		}
	}

	/**
	 * Sets the tvv represntation data.
	 *
	 * @param tvvRepresentation the tvv representation
	 * @param statCurvesDataRepre the stat curves data repre
	 */
	private void setTvvRepresntationData(TvvRepresentation tvvRepresentation, ExportStatisticalCurvesDataRepresentation statCurvesDataRepre) {
		logger.info("in setTvvRepresntationDataForStatisticalCurvesRepresentation method--------------------------------------------------->");
		statCurvesDataRepre.setTvvLabel(tvvRepresentation.getLabel());
		statCurvesDataRepre.setVehicleFamilyLabel(tvvRepresentation.getProjectCodeFamily().getVehicleFamilyLabel());
		statCurvesDataRepre.setProjectCodeLabel(tvvRepresentation.getProjectCodeFamily().getProjectCodeLabel());
		statCurvesDataRepre.setMoteurLabel(tvvRepresentation.getEngine().getDisplayLabel());
		statCurvesDataRepre.setGearBoxLabel(tvvRepresentation.getGearBox().getGearBoxLabel());
		if (tvvRepresentation.getValuedCoastDown() != null) {
			statCurvesDataRepre.setPsaReference(tvvRepresentation.getValuedCoastDown().getpSAReference());
			statCurvesDataRepre.setInertiaValue(tvvRepresentation.getValuedCoastDown().getInertia_value());
		}
	}

	/**
	 * Sets the pg result set.
	 *
	 * @param pgResultList the pg result list
	 * @param statCurvesDataRepre the stat curves data repre
	 */
	private void setPgResultSet(Set<PollutantGasResultSet> pgResultList, ExportStatisticalCurvesDataRepresentation statCurvesDataRepre) {
		logger.info("in setPgResultSetForStatisticalCurvesRepresentation method--------------------------------------------------->");
		List<String> benchCellList = new ArrayList<>();
		List<String> oBDTestList = new ArrayList<>();
		List<Integer> orderList = new ArrayList<>();
		List<Date> creationDateList = new ArrayList<>();
		for (PollutantGasResultSet pollutantGasResult : nullGuard(pgResultList)) {
			benchCellList.add(pollutantGasResult.getBenchCell());
			oBDTestList.add(pollutantGasResult.getObdTest());
			orderList.add(pollutantGasResult.getSetOrder());
			creationDateList.add(pollutantGasResult.getCreationDate());

		}
		if (!creationDateList.isEmpty()) {
			Date latestDate = Collections.max(creationDateList);
			Date oldestDate = Collections.min(creationDateList);
			statCurvesDataRepre.setDebutDate(oldestDate);
			statCurvesDataRepre.setFinDate(latestDate);
		}
		statCurvesDataRepre.setResultSetBenchCellList(benchCellList);
		statCurvesDataRepre.setResultSetOBDTestList(oBDTestList);
		statCurvesDataRepre.setResultSetSetOrderList(orderList);
		statCurvesDataRepre.setResultsSetCreationDate(creationDateList);
	}

	/**
	 * Null guard.
	 *
	 * @param <T> the generic type
	 * @param iterable the iterable
	 * @return the iterable
	 */
	private static <T> Iterable<T> nullGuard(Iterable<T> iterable) {
		if (iterable == null)
			return Collections.<T> emptyList();

		return iterable;
	}

}