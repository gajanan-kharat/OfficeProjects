package com.inetpsa.poc00.export.bce;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.factorcoeffList.FactorCoefficentList;
import com.inetpsa.poc00.domain.pollutantgaslimitlist.PollutantGasLimitList;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.rest.tvv.TvvFinder;

/**
 * The Class ExportBCEResource.
 */
@Path("/export")
@JpaUnit(Config.JPA_UNIT)
@Transactional
public class ExportBCEResource {

	/** The tvv finder. */
	@Inject
	TvvFinder tvvFinder;

	/** The temp path. */
	@Configuration("com.inetpsa.cop.export.exportbce.tempfiledirectory")
	private String tempPath;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ExportBCEResource.class);

	/** The file path with name. */
	private String filePathWithName;
	
	/**
	 * Export file.
	 * 
	 * @return the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GET
	@Path("/exportBCE")
	@Produces("application/vnd.ms-excel")
	public Response exportFile() {

		logger.info("**********     STARTED : Excel File Generation, {}      **********", new Date());
		
		logger.info("*****************  {} ", tempPath);

		List<ExportBCERepresentation> dataList = new ArrayList<>();

		String fileName = "ExportBCE_" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".xls";
		
		filePathWithName = tempPath + fileName;

		// Fetching Tvv Data
		List<TVV> tvvData = tvvFinder.getAllTVVData();

		// Converting tvvData into ExportBCE presentation
		prepareDataToImport(tvvData, dataList);

		// Creating Excel file with dataList
		prepareExcelSheet(dataList);

		// Creating Response and Adding File to Response
		ResponseBuilder response = Response.ok(new File(filePathWithName), "application/vnd.ms-excel");

		// Setting Response header
		response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		logger.info("**********     COMPLETED : Excel File Generation, {}     **********", new Date());

		return response.build();

	}

	/**
	 * Prepare excel sheet.
	 * 
	 * @param dataList the data list
	 */
	private void prepareExcelSheet(List<ExportBCERepresentation> dataList) {

		// Work Book
		Workbook wb = new HSSFWorkbook();

		// Excel Sheet
		Sheet personSheet = wb.createSheet(Constants.EXPORT_BCE_SHEET_NAME);

		// Header Row
		Row headerRow = personSheet.createRow(0);

		// Generating Header Row
		ExportBCEUtil.generateHeaderRow(headerRow, wb);

		int rowIndex = 1;

		for (ExportBCERepresentation data : dataList) {

			creatingRow(rowIndex, data, personSheet);

			rowIndex = rowIndex + 1;
		}

		FileOutputStream fileOut;

		try {

			File file = new File(filePathWithName);
			fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {

			logger.error("Issue in File for ExportBCEResource", e);

		} catch (IOException e) {

			logger.error("IO Exception", e);
		}

	}

	/**
	 * Creating row.
	 * 
	 * @param rowIndex the row index
	 * @param data the data
	 * @param personSheet the person sheet
	 */
	public void creatingRow(int rowIndex, ExportBCERepresentation data, Sheet personSheet) {

		Row dataRow = personSheet.createRow(rowIndex);
		
		int index = 0;
		
		Cell cell0 = dataRow.createCell(index++);
		cell0.setCellValue(isNull(data.getRegulationGroup()));

		Cell cell1 = dataRow.createCell(index++);
		cell1.setCellValue(isNull(data.getTechnicalGroup()));

		Cell cell2 = dataRow.createCell(index++);
		cell2.setCellValue(isNull(data.getTypeApprovalAreaLabel()));

		Cell cell3 = dataRow.createCell(index++);
		cell3.setCellValue(isNull(data.getCountryLabel()));

		Cell cell4 = dataRow.createCell(index++);
		cell4.setCellValue(isNull(data.getVehicleFamilyLabel()));

		Cell cell5 = dataRow.createCell(index++);
		cell5.setCellValue(isNull(data.getEngineLabel()));

		Cell cell6 = dataRow.createCell(index++);
		cell6.setCellValue(isNull(data.getGearBoxLabel()));

		Cell cell7 = dataRow.createCell(index++);
		cell7.setCellValue(isNull(data.getTvvLabel()));

		Cell cell8 = dataRow.createCell(index++);
		cell8.setCellValue(isNull(data.getBodyWorkLabel()));

		Cell cell9 = dataRow.createCell(index++);
		cell9.setCellValue(isNull(data.getEnginePowerCV()));

		Cell cell10 = dataRow.createCell(index++);
		cell10.setCellValue(isNull(data.getEnginePowerkW()));

		Cell cell11 = dataRow.createCell(index++);
		cell11.setCellValue(isNull(data.getEngineTorqueNm()));

		Cell cell12 = dataRow.createCell(index++);
		if (data.getInertialValue() == null) {
			cell12.setCellValue("");
		} else {
			cell12.setCellValue(data.getInertialValue());
		}

		Cell cell13 = dataRow.createCell(index++);
		cell13.setCellValue(isNull(data.getTvvValuedTDValueAncien()));

		Cell cell14 = dataRow.createCell(index++);
		cell14.setCellValue(isNull(data.getTvvValuedTDValueActuel()));

		// Particularité : Igonre this Column
		Cell cell15 = dataRow.createCell(index++);
		cell15.setCellValue("");

		Cell cell16 = dataRow.createCell(index++);
		cell16.setCellValue(isNull(data.getTvvValuedTDValueRemarques()));

		Cell cell17 = dataRow.createCell(index++);
		cell17.setCellValue(isNull(data.getValuedCDPSARefLabel()));

		Cell cell18 = dataRow.createCell(index++);
		cell18.setCellValue(isNull(data.getMaxDefaultValTvvValPGL()));

		Cell cell19 = dataRow.createCell(index++);
		cell19.setCellValue(isNull(data.getTvvValuedTCCycle()));

		Cell cell20 = dataRow.createCell(index++);
		cell20.setCellValue(isNull(data.getEmissionStandardLabel()));

		Cell cell21 = dataRow.createCell(index++);
		cell21.setCellValue(isNull(data.getEmissionStandardVersion()));

		Cell cell22 = dataRow.createCell(index++);
		cell22.setCellValue(isNull(data.getFuelLabel()));

		Cell cell23 = dataRow.createCell(index++);
		cell23.setCellValue(isNull(data.getTheoricalf0()));

		Cell cell24 = dataRow.createCell(index++);
		cell24.setCellValue(isNull(data.getTheoricalf1()));

		Cell cell25 = dataRow.createCell(index++);
		cell25.setCellValue(isNull(data.getTheoricalf2()));

		Cell cell26 = dataRow.createCell(index++);
		cell26.setCellValue(isNull(data.getComputedBenchf0()));

		Cell cell27 = dataRow.createCell(index++);
		cell27.setCellValue(isNull(data.getUserBenchf0()));

		Cell cell28 = dataRow.createCell(index++);
		cell28.setCellValue(isNull(data.getComputedBenchf1()));

		Cell cell29 = dataRow.createCell(index++);
		cell29.setCellValue(isNull(data.getUserBenchf1()));

		Cell cell30 = dataRow.createCell(index++);
		cell30.setCellValue(isNull(data.getComputedBenchf2()));

		Cell cell31 = dataRow.createCell(index++);
		cell31.setCellValue(isNull(data.getUserBenchf2()));

		Cell cell32 = dataRow.createCell(index++);
		cell32.setCellValue(isNull(data.getTvvValdTCDefaultValPente()));

		// FD Forfaitaire
		Cell cell33 = dataRow.createCell(index++);
		cell33.setCellValue("");

		Cell cell34 = dataRow.createCell(index++);
		cell34.setCellValue(isNull(data.getTvvValdFCDefaultValCO()));

		Cell cell35 = dataRow.createCell(index++);
		cell35.setCellValue(isNull(data.getTvvValdFCDefaultValHC()));

		Cell cell36 = dataRow.createCell(index++);
		cell36.setCellValue(isNull(data.getTvvValdFCDefaultValNMHC()));

		Cell cell37 = dataRow.createCell(index++);
		cell37.setCellValue(isNull(data.getTvvValdFCDefaultValNOX()));

		Cell cell38 = dataRow.createCell(index++);
		cell38.setCellValue(isNull(data.getTvvValdFCDefaultValHCNOX()));

		Cell cell39 = dataRow.createCell(index++);
		cell39.setCellValue(isNull(data.getTvvValdFCDefaultValPartMasse()));

		Cell cell40 = dataRow.createCell(index++);
		cell40.setCellValue(isNull(data.getTvvValdFCDefaultValPartNombre()));

		Cell cell41 = dataRow.createCell(index++);
		cell41.setCellValue(isNull(data.getTvvValdFCDefaultValCO2()));

		// Coef Evol
		Cell cell42 = dataRow.createCell(index++);
		cell42.setCellValue("");

		Cell cell43 = dataRow.createCell(index++);
		cell43.setCellValue(isNull(data.getTvvValdFCDefaultValCoefEvolCO()));

		Cell cell44 = dataRow.createCell(index++);
		cell44.setCellValue(isNull(data.getTvvValdFCDefaultValCoefEvolHC()));

		Cell cell45 = dataRow.createCell(index++);
		cell45.setCellValue(isNull(data.getTvvValdFCDefaultValCoefEvolNMHC()));

		Cell cell46 = dataRow.createCell(index++);
		cell46.setCellValue(isNull(data.getTvvValdFCDefaultValCoefEvolNOX()));

		Cell cell47 = dataRow.createCell(index++);
		cell47.setCellValue(isNull(data.getTvvValdFCDefaultValCoefEvolHCNOX()));

		Cell cell48 = dataRow.createCell(index++);
		cell48.setCellValue(isNull(data.getTvvValdFCDefaultValCoefEvolPartMasse()));

		Cell cell49 = dataRow.createCell(index++);
		cell49.setCellValue(isNull(data.getTvvValdFCDefaultValCoefEvolPartNombre()));

		Cell cell50 = dataRow.createCell(index++);
		cell50.setCellValue(isNull(data.getTvvValdFCDefaultValCoefEvolCO2()));

		// Coef FAP
		Cell cell51 = dataRow.createCell(index++);
		cell51.setCellValue("");

		Cell cell52 = dataRow.createCell(index++);
		cell52.setCellValue(isNull(data.getTvvValdFCDefaultValCoefFAPCO()));

		Cell cell53 = dataRow.createCell(index++);
		cell53.setCellValue(isNull(data.getTvvValdFCDefaultValCoefFAPHC()));

		Cell cell54 = dataRow.createCell(index++);
		cell54.setCellValue(isNull(data.getTvvValdFCDefaultValCoefFAPNOX()));

		Cell cell55 = dataRow.createCell(index++);
		cell55.setCellValue(isNull(data.getTvvValdFCDefaultValCoefFAPHCNOX()));

		Cell cell56 = dataRow.createCell(index++);
		cell56.setCellValue(isNull(data.getTvvValdFCDefaultValCoefFAPPartMasse()));

		Cell cell57 = dataRow.createCell(index++);
		cell57.setCellValue(isNull(data.getTvvValdFCDefaultValCoefFAPPartNbre()));

		Cell cell58 = dataRow.createCell(index++);
		cell58.setCellValue(isNull(data.getTvvValdFCDefaultValCoefFAPCO2()));

		Cell cell59 = dataRow.createCell(index++);
		cell59.setCellValue(isNull(data.getTvvValdPGMaxDefaultValHC()));

		Cell cell60 = dataRow.createCell(index++);
		cell60.setCellValue(isNull(data.getTvvValdPGMaxDefaultValNMHC()));

		Cell cell61 = dataRow.createCell(index++);
		cell61.setCellValue(isNull(data.getTvvValdPGMaxDefaultValNOX()));

		Cell cell62 = dataRow.createCell(index++);
		cell62.setCellValue(isNull(data.getTvvValdPGMaxDefaultValCO()));

		Cell cell63 = dataRow.createCell(index++);
		cell63.setCellValue(isNull(data.getTvvValdPGMaxDefaultValHCNOX()));

		Cell cell64 = dataRow.createCell(index++);
		cell64.setCellValue(isNull(data.getTvvValdPGMaxDefaultValPartMasse()));

		Cell cell65 = dataRow.createCell(index++);
		cell65.setCellValue(isNull(data.getTvvValdPGMaxDefaultValPartNombre()));

		Cell cell66 = dataRow.createCell(index++);
		cell66.setCellValue(isNull(data.getTvvValdTDValEOBD()));

		Cell cell67 = dataRow.createCell(index++);
		cell67.setCellValue(isNull(data.getTvvValdTDValIUPR()));

		Cell cell68 = dataRow.createCell(index++);
		cell68.setCellValue(isNull(data.getTvvValdNSoftAncien()));

		Cell cell69 = dataRow.createCell(index++);
		cell69.setCellValue(isNull(data.getTvvValdNSoftActuel()));

		Cell cell70 = dataRow.createCell(index++);
		cell70.setCellValue(isNull(data.getTvvValdTDValEOBDRemarques()));

		Cell cell71 = dataRow.createCell(index++);
		cell71.setCellValue(isNull(data.getTvvValuedTCMLog()));

		Cell cell72 = dataRow.createCell(index++);
		cell72.setCellValue(isNull(data.getTvvValdTDValueCalclr()));

		Cell cell73 = dataRow.createCell(index++);
		cell73.setCellValue(isNull(data.getFuelInjectionTypeLabel()));

		// Opacite
		Cell cell74 = dataRow.createCell(index++);
		cell74.setCellValue(isNull(data.getTvvValuedTCDefaultValue()));

		Cell cell75 = dataRow.createCell(index++);
		cell75.setCellValue(isNull(data.getTvvValdTCValueRegime()));

		Cell cell76 = dataRow.createCell(index++);
		cell76.setCellValue(isNull(data.getTvvValdTCDefValueSOCA()));

		Cell cell77 = dataRow.createCell(index++);
		cell77.setCellValue(isNull(data.getTvvValdTCDefValSOCAAvant()));

		Cell cell78 = dataRow.createCell(index++);
		cell78.setCellValue(isNull(data.getTvvValdTCValuePocketBSI()));

		Cell cell79 = dataRow.createCell(index++);
		cell79.setCellValue(isNull(data.getTvvValdTCValManchon()));

		// APPRENTISSAGE PILOTE
		Cell cell80 = dataRow.createCell(index++);
		cell80.setCellValue(isNull(data.getTvvValdTCValAppren()));

		Cell cell81 = dataRow.createCell(index++);
		cell81.setCellValue(isNull(data.getTvvValdTCValPhase1()));

		Cell cell82 = dataRow.createCell(index++);
		cell82.setCellValue(isNull(data.getTvvValdTCValPhase2()));

		Cell cell83 = dataRow.createCell(index++);
		cell83.setCellValue(isNull(data.getTvvValdTCValPhase1()));

		Cell cell84 = dataRow.createCell(index++);
		cell84.setCellValue(isNull(data.getTvvValdTCValPhase2()));

		Cell cell85 = dataRow.createCell(index++);
		cell85.setCellValue(isNull(data.getTvvValdTCValPhase3()));

		Cell cell86 = dataRow.createCell(index++);
		cell86.setCellValue(isNull(data.getTvvValdTCValDebitDLS()));

		Cell cell87 = dataRow.createCell(index++);
		cell87.setCellValue(isNull(data.getTvvValdTCValPhasesSAC()));

		Cell cell88 = dataRow.createCell(index++);
		cell88.setCellValue(isNull(data.getTvvValdTCValPhasesParti()));

		Cell cell89 = dataRow.createCell(index++);
		cell89.setCellValue(isNull(data.getTvvValdTCValObsrvPrepa()));

		Cell cell90 = dataRow.createCell(index++);
		cell90.setCellValue(isNull(data.getTvvValdTCValObsrvTest()));

		Cell cell91 = dataRow.createCell(index++);
		cell91.setCellValue(isNull(data.getTvvValdTDValPollu()));

		Cell cell92 = dataRow.createCell(index++);
		cell92.setCellValue(isNull(data.getTvvValdTDValConso()));

		Cell cell93 = dataRow.createCell(index++);
		cell93.setCellValue(isNull(data.getTvvValdTDValEtapeEmission()));

		Cell cell94 = dataRow.createCell(index++);
		cell94.setCellValue(isNull(data.getTvvValdTDValEtapeOBD()));

		Cell cell95 = dataRow.createCell(index++);
		cell95.setCellValue(isNull(data.getTvvValueTDValObsrv1()));

		Cell cell96 = dataRow.createCell(index++);
		cell96.setCellValue(isNull(data.getTvvValueTDValObsrv2()));

		Cell cell97 = dataRow.createCell(index++);
		cell97.setCellValue(isNull(data.getCategoryLabel()));

		Cell cell98 = dataRow.createCell(index++);
		cell98.setCellValue(isNull(data.getClassLabel()));

		Cell cell99 = dataRow.createCell(index++);
		cell99.setCellValue(isNull(data.getStatusLabel()));

		Cell cell100 = dataRow.createCell(index++);
		cell100.setCellValue(isNull(data.getTestNatureLabel()));

		Cell cell101 = dataRow.createCell(index++);
		cell101.setCellValue(isNull(data.getVehicleTechnology()));

		Cell cell102 = dataRow.createCell(index++);
		cell102.setCellValue(isNull(data.getSamplingRule()));

		Cell cell103 = dataRow.createCell(index++);
		cell103.setCellValue(isNull(data.getCarMakerBrandLabel()));

		Cell cell104 = dataRow.createCell(index++);
		cell104.setCellValue(isNull(data.getFactory()));

		Cell cell105 = dataRow.createCell(index);
		cell105.setCellValue(isNull(data.getFinalReductionRationlabel()));

	}

	/**
	 * Prepare data to import.
	 * 
	 * @param tvvData the tvv data
	 * @param dataList the data list
	 */
	public void prepareDataToImport(List<TVV> tvvData, List<ExportBCERepresentation> dataList) {

		for (TVV temp : tvvData) {

			ExportBCERepresentation tvv = new ExportBCERepresentation();

			if (temp != null && temp.getTechnicalCase() != null && temp.getTechnicalCase().getTechnicalGroup() != null && temp.getTechnicalCase().getTechnicalGroup().getRegulationGroup() != null) {

				// Regulation Group
				tvv.setRegulationGroup(temp.getTechnicalCase().getTechnicalGroup().getRegulationGroup().getLabel());

				if (temp.getTechnicalCase().getTechnicalGroup().getRegulationGroup().getTypeApprovalArea() != null) {
					// Type Approval Area
					tvv.setTypeApprovalAreaLabel(temp.getTechnicalCase().getTechnicalGroup().getRegulationGroup().getTypeApprovalArea().getLabel());
				}

			}

			if (temp != null && temp.getTechnicalCase() != null && temp.getTechnicalCase().getTechnicalGroup() != null) {
				// Technical Group
				tvv.setTechnicalGroup(temp.getTechnicalCase().getTechnicalGroup().getLabel());
			}

			// Country (Pays) Label
			// tvv.setCountryLabel(temp.getTechnicalCase().getTechnicalGroup().getRegulationGroup().get)

			if (temp.getProjectCodeFamily() != null) {
				// Vehicle Family Label from Project Code Family
				tvv.setVehicleFamilyLabel(temp.getProjectCodeFamily().getVehicleFamilyLabel());
			}

			if (temp.getEngine() != null) {

				// Engine Label
				tvv.setEngineLabel(temp.getEngine().getEngineLabel());
				// Engine CV.
				tvv.setEnginePowerCV(temp.getEngine().getPowerCv());
				// Engine KW
				tvv.setEnginePowerkW(temp.getEngine().getPowerKw());
				// Engine Torque NM
				tvv.setEngineTorqueNm(temp.getEngine().getTorque());
			}

			if (temp.getGearBox() != null) {
				// Gear Box Label
				tvv.setGearBoxLabel(temp.getGearBox().getLabel());
			}

			// Techinical Database TVV (Tvv Label)
			tvv.setTvvLabel(temp.getLabel());

			if (temp.getBodyWork() != null) {
				// Body Work Label
				tvv.setBodyWorkLabel(temp.getBodyWork().getLabel());
			}

			if (temp.getTvvValuedCoastDown() != null) {

				if (temp.getTvvValuedCoastDown().getInertia() != null) {
					// Value Inertia
					tvv.setInertialValue(temp.getTvvValuedCoastDown().getInertia().getValue());
				}

				// Valued Coast Down
				tvv.setValuedCDPSARefLabel(temp.getTvvValuedCoastDown().getpSAReference());

				// Valued Coast Down property
				tvv.setTheoricalf0(temp.getTvvValuedCoastDown().getTheoricalBenchF0());
				tvv.setTheoricalf1(temp.getTvvValuedCoastDown().getTheoricalBenchF1());
				tvv.setTheoricalf2(temp.getTvvValuedCoastDown().getTheoricalBenchF2());
				tvv.setComputedBenchf0(temp.getTvvValuedCoastDown().getComputedBenchF0());
				tvv.setUserBenchf0(temp.getTvvValuedCoastDown().getUserBenchF0());
				tvv.setComputedBenchf1(temp.getTvvValuedCoastDown().getComputedBenchF1());
				tvv.setUserBenchf1(temp.getTvvValuedCoastDown().getUserBenchF1());
				tvv.setComputedBenchf2(temp.getTvvValuedCoastDown().getComputedBenchF2());
				tvv.setUserBenchf2(temp.getTvvValuedCoastDown().getUserBenchF2());

			}
			
			// Set vehicle Technology
			if(temp.getVehicalTechnology() != null) {
				tvv.setVehicleTechnology(temp.getVehicalTechnology().getLabel());
			}
			
			// Set Category
			if(temp.getCategory() != null) {
				tvv.setCategoryLabel(temp.getCategory().getLabel());
			}

			if (temp.getTechnicalCase() != null && temp.getTechnicalCase().getEmissionStandard() != null) {

				// Limite
				tvv.setEmissionStandardLabel(temp.getTechnicalCase().getEmissionStandard().getEsLabel());
				// Version
				tvv.setEmissionStandardVersion(temp.getTechnicalCase().getEmissionStandard().getVersion());

				if (temp.getTechnicalCase().getEmissionStandard().getFuelInjectionTypes() != null) {
					// Fuel Injection Type
					String fuelInjectionTypes = StringUtils.join(temp.getTechnicalCase().getEmissionStandard().getFuelInjectionTypes().toArray(), ",");
					tvv.setFuelInjectionTypeLabel(fuelInjectionTypes);
				}

				// START : Classes
				StringBuilder classes = new StringBuilder("");
				if (temp.getTechnicalCase().getEmissionStandard().getFclists() != null) {
					for (FactorCoefficentList obj : temp.getTechnicalCase().getEmissionStandard().getFclists())
						if (obj.getClasses() != null) {
							classes.append(StringUtils.join(obj.getClasses().toArray(), ",")).append(",");
							break;
						}
				}

				if (temp.getTechnicalCase().getEmissionStandard().getPgLists() != null) {
					for (PollutantGasLimitList obj : temp.getTechnicalCase().getEmissionStandard().getPgLists())
						if (obj.getClasses() != null) {
							classes.append(StringUtils.join(obj.getClasses().toArray(), ","));
							break;
						}
				}

				tvv.setClassLabel(classes.toString());
				// END : Classes

			}

			if (temp.getFuel() != null) {
				// Fuel Label
				tvv.setFuelLabel(temp.getFuel().getLabel());
			}

			if (temp.getStatus() != null) {

				// Status
				tvv.setStatusLabel(temp.getStatus().getLabel());

				if (temp.getStatus().getTestNatures() != null) {
					// TestNature
					String testNatures = StringUtils.join(temp.getStatus().getTestNatures().toArray(), ",");
					tvv.setTestNatureLabel(testNatures);
				}

			}

			// Sampling Rule : Setting Empty
			tvv.setSamplingRule("");

			// Car Maker and Brand
			if (temp.getCarBrandSet() != null) {
				String carMakerBrand = StringUtils.join(temp.getCarBrandSet(), ",");
				tvv.setCarMakerBrandLabel(carMakerBrand);
			}

			// Factory : This is not associated with TVV so ignorig it
			tvv.setFactory("");

			if (temp.getFinalReductionRatio() != null) {
				// Final Reduction Ration
				tvv.setFinalReductionRationlabel(temp.getFinalReductionRatio().getLabel());
			}

			// First List : TvvValuedEsDepTDL
			ExportBCEUtil.prepareTvvValuedESDepTDL(temp, tvv);

			// Second List : TvvValuedTvvDepTDL
			ExportBCEUtil.prepareTvvValuedTvvDepTDL(temp, tvv);

			// Third List : TvvValuedTvvDepTCL
			ExportBCEUtil.prepareTvvValuedTvvDepTCL(temp, tvv);

			// Fourth List : TvvValuedEsDepTCL
			ExportBCEUtil.prepareTvvValuedEsDepTCL(temp, tvv);

			// Fourth List : TvvValuedEsDepFCL
			ExportBCEUtil.prepareTvvValuedEsDepFCL(temp, tvv);

			// Sixth List : TvvValuedEsDepPGL
			ExportBCEUtil.prepareTvvValuedEsDepPGL(temp, tvv);

			dataList.add(tvv);

		}

	}
	
	/**
	 * Checks if is null.
	 * 
	 * @param value the value
	 * @return the string
	 */
	public static String isNull(String value) {

		if (value == null) {
			return "";
		}

		return value;

	}

	/**
	 * Checks if is null.
	 * 
	 * @param value the value
	 * @return the string
	 */
	public static String isNull(Double value) {

		if (value == null) {
			return "";
		}

		return String.valueOf(value);

	}

	
	
}
