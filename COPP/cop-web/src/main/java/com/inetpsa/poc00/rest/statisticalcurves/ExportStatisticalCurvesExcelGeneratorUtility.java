package com.inetpsa.poc00.rest.statisticalcurves;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.common.PropertiesLang;
import com.inetpsa.poc00.pollutantorgas.PollutantGasTestResultRepresentation;
import com.inetpsa.poc00.util.CalculationUtil;

/**
 * The Class ExportStatisticalCurvesExcelGeneratorUtility.
 */
public class ExportStatisticalCurvesExcelGeneratorUtility {

	/** The property lang. */
	private static PropertiesLang propertyLang = PropertiesLang.getInstance();

	/** The Constant logger. */
	private final Logger logger = LoggerFactory.getLogger(ExportStatisticalCurvesExcelGeneratorUtility.class);

	/** The Constant EXPORT_SC_SHEET_NAME. */
	private static final String EXPORT_SC_SHEET_NAME = "StatisticalCurves";

	/** The row index. */
	private int rowIndex = 0;

	/** The col index. */
	private int colIndex = 0;

	/** The sc book. */
	private Workbook scBook;

	/**
	 * Write into sc excel.
	 *
	 * @param filePath the file path
	 * @param curvesDataRep the curves data representation
	 * @param choiceList the choice list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void writeIntoScExcel(String filePath, ExportStatisticalCurvesDataRepresentation curvesDataRep, List<String> choiceList) throws IOException {
		logger.info("in writeIntoExcel-------------->");
		try {
			scBook = new HSSFWorkbook();
			Sheet scSheet = scBook.createSheet(EXPORT_SC_SHEET_NAME);
			CellStyle style = scBook.createCellStyle();
			style.setWrapText(true);
			// row1
			setFirstRowOfSCExcelSheet(curvesDataRep, scSheet, style);
			// row2
			setSecondRowOfScExcelSheet(curvesDataRep, scSheet, style);
			// row3
			setThirdRowOfScExcelSheet(curvesDataRep, scSheet, style);
			// row4
			setFourthRowOfScExcelSheet(curvesDataRep, scSheet, style);
			// row5
			Row row5 = setFifthRowOfScExcelSheet(scBook, scSheet, style);
			// row6

			setSixthToNinthRowOnwardsOfScExcelSheet(curvesDataRep, scSheet, choiceList, row5, style);
			autoSizeColumns(scBook);
			scBook.write(new FileOutputStream(filePath));

			logger.info("file created -------------->");
		} catch (Exception workBookException) {
			logger.error("exception while excel creation :{} ", workBookException);
		} finally {
			scBook.close();
		}

	}

	/**
	 * Sets the sixth row of sc excel sheet.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param choiceList the choice list
	 * @param row5 the row5
	 * @param style the style
	 */
	private void setSixthToNinthRowOnwardsOfScExcelSheet(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, List<String> choiceList, Row row5, CellStyle style) {
		logger.info("in setSixthToNinthRowOnwardsOfScExcelSheet-------------->");
		int orderRowCounter = rowIndex + 3;
		setNumberForColumn(curvesDataRep, scSheet, orderRowCounter);
		setResultSetOrderForColumn(curvesDataRep, scSheet, orderRowCounter, style);
		setVehcleChasisNumberForColumn(curvesDataRep, scSheet, orderRowCounter, style);
		setResultSetCreationDateColumn(curvesDataRep, scSheet, orderRowCounter, style);
		setResultsSetOBDTest(curvesDataRep, scSheet, orderRowCounter, style);
		setResultsSetBenchCell(curvesDataRep, scSheet, orderRowCounter, style);
		int column = setPollutantGasTestResultRepresentations(curvesDataRep, scSheet, choiceList, row5, style);
		setVehicleFileStatisticalDecision(curvesDataRep, scSheet, orderRowCounter, column, style);

	}

	/**
	 * Sets the number for column.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param orderRowCounter the order row counter
	 */
	private void setNumberForColumn(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, int orderRowCounter) {
		logger.info("in setNumberForColumn-------------->");
		Row row6;
		for (int setOrderCount = 0; setOrderCount < curvesDataRep.getVehicleChassisNumber().size(); setOrderCount++) {
			row6 = scSheet.getRow(orderRowCounter + setOrderCount);
			if (row6 == null) {
				row6 = scSheet.createRow(orderRowCounter + setOrderCount);
			}
			Cell row6Cell0 = row6.createCell(0);// 0
			row6Cell0.setCellValue(isNull(setOrderCount + 1));
		}
	}

	/**
	 * Sets the vehicle file statistical decision.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param orderRowCounter the order row counter
	 * @param column the column
	 * @param style the style
	 */
	private void setVehicleFileStatisticalDecision(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, int orderRowCounter, int column, CellStyle style) {
		Row row6;

		logger.info("in setVehicleFileStatisticalDecision-------------->");
		for (int vfsdCount = 0; vfsdCount < curvesDataRep.getVehicleFileStatisticalDecision().size(); vfsdCount++) {
			row6 = scSheet.getRow(orderRowCounter + vfsdCount);
			if (row6 == null) {
				row6 = scSheet.createRow(orderRowCounter + vfsdCount);
			}
			Cell row6Cell38 = row6.createCell(column);//
			row6Cell38.setCellValue(isNull(curvesDataRep.getVehicleFileStatisticalDecision().get(vfsdCount)));
			row6Cell38.setCellStyle(style);

		}

	}

	/**
	 * Sets the pollutant gas test results.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param choiceList the choice list
	 * @param row5 the row5
	 * @param style the style
	 * @return the int
	 */
	private int setPollutantGasTestResultRepresentations(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, List<String> choiceList, Row row5, CellStyle style) {

		logger.info("in setPollutantGasTestResultRepresentations-------------->");
		Map<String, Map<String, PollutantGasTestResultRepresentation>> pgtVehicleMap = curvesDataRep.getVehicleBasedPollutantGasTestResult();
		Set<Entry<String, Map<String, PollutantGasTestResultRepresentation>>> pgtEntrySet = pgtVehicleMap.entrySet();
		Map<String, List<Double>> pgVehicleMap = new LinkedHashMap<>();
		int column = getPgDataFromMap(scSheet, pgtEntrySet, choiceList, row5, pgVehicleMap, style);
		setSeventhRowOfScExcelSheet(scSheet, pgVehicleMap, choiceList, style);
		return column;
	}

	/**
	 * Sets the seventh row of sc excel sheet.
	 *
	 * @param scSheet the sc sheet
	 * @param pgVehicleMap the pg vehicle map
	 * @param choiceList the choice list
	 * @param style the style
	 */
	private void setSeventhRowOfScExcelSheet(Sheet scSheet, Map<String, List<Double>> pgVehicleMap, List<String> choiceList, CellStyle style) {
		setCalculationAverageForPg(scSheet, pgVehicleMap, choiceList, style);
		setCalculateDeviationForPg(scSheet, pgVehicleMap, choiceList, style);

	}

	/**
	 * Sets the calculate deviation for pg.
	 *
	 * @param scSheet the sc sheet
	 * @param pgVehicleMap the pg vehicle map
	 * @param choiceList the choice list
	 * @param style the style
	 */
	private void setCalculateDeviationForPg(Sheet scSheet, Map<String, List<Double>> pgVehicleMap, List<String> choiceList, CellStyle style2) {
		logger.info("in setCalculateDeviationForPg-------------->");
		CellStyle style = scBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setWrapText(true);
		Row row10 = scSheet.getRow(7);// orderRowCounter + counter
		if (row10 == null) {
			row10 = scSheet.createRow(7);

		}
		Cell row10CellA = row10.createCell(0);//
		row10CellA.setCellValue(isNull(0));
		row10CellA.setCellStyle(style);

		Cell row10CellB = row10.createCell(1);//
		row10CellB.setCellValue("");
		row10CellB.setCellStyle(style);
		// Ec Type
		Cell row10CellC = row10.createCell(2);//
		row10CellC.setCellValue(isNull(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.ectype")));
		row10CellC.setCellStyle(style);

		int count = 8;
		for (Entry<String, List<Double>> pg : pgVehicleMap.entrySet()) {
			if (choiceList.contains(pg.getKey())) {
				Cell row10CellI = row10.createCell(count);//
				row10CellI.setCellValue(isNull(CalculationUtil.calculateDeviation(pg.getValue())));
				count += 3;
			}
		}

	}

	/**
	 * Sets the calculation average for pg.
	 *
	 * @param scSheet the sc sheet
	 * @param pgVehicleMap the pg vehicle map
	 * @param choiceList the choice list
	 * @param style the style
	 */
	private void setCalculationAverageForPg(Sheet scSheet, Map<String, List<Double>> pgVehicleMap, List<String> choiceList, CellStyle style2) {
		logger.info("in setCalculationAverageForPg-------------->");
		CellStyle style = scBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setWrapText(true);
		Row row9 = scSheet.getRow(6);// orderRowCounter + counter
		if (row9 == null) {
			row9 = scSheet.createRow(6);

		}

		Cell row9CellA = row9.createCell(0);//
		row9CellA.setCellValue(isNull(0));
		row9CellA.setCellStyle(style);

		Cell row9CellB = row9.createCell(1);//
		row9CellB.setCellValue("");
		row9CellB.setCellStyle(style);
		// Moyenne
		Cell row9CellC = row9.createCell(2);//
		row9CellC.setCellValue(isNull(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.average")));
		row9CellC.setCellStyle(style);
		int count = 8;
		for (Entry<String, List<Double>> pg : pgVehicleMap.entrySet()) {
			if (choiceList.contains(pg.getKey())) {
				Cell row9CellI = row9.createCell(count);
				row9CellI.setCellValue(isNull(CalculationUtil.calculateAverage(pg.getValue())));
				count += 3;
			}
		}

	}

	/**
	 * Gets the pg data from map.
	 *
	 * @param scSheet the sc sheet
	 * @param pgtEntrySet the pgt entry set
	 * @param choiceList the choice list
	 * @param row5 the row5
	 * @param pgVehicleMap the pg vehicle map
	 * @param style the style
	 * @return the pg data from map
	 */
	private int getPgDataFromMap(Sheet scSheet, Set<Entry<String, Map<String, PollutantGasTestResultRepresentation>>> pgtEntrySet, List<String> choiceList, Row row5, Map<String, List<Double>> pgVehicleMap, CellStyle style2) {

		int column = 8;

		logger.info("in getPgDataFromMap-------------->");
		for (String pgName : choiceList) {
			int counter = 0;
			setDyanmicPgLabels(row5, pgName, style2);
			List<Double> pgList = new ArrayList<>();
			for (Entry<String, Map<String, PollutantGasTestResultRepresentation>> entry : nullGuard(pgtEntrySet)) {
				Map<String, PollutantGasTestResultRepresentation> pgMap = entry.getValue();
				if (pgMap != null && pgMap.get(pgName) != null && pgMap.get(pgName).getValue() != null) {
					setPgContent(scSheet, pgList, counter, pgMap, pgName, column, style2);
				}
				counter++;
			}
			column += 3;
			pgVehicleMap.put(pgName, pgList);

		}
		CellStyle style = scBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setWrapText(true);
		Cell cellAm = row5.createCell(column);
		cellAm.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.d.global"));
		cellAm.setCellStyle(style);
		return column;
	}

	/**
	 * Sets the pg content.
	 *
	 * @param scSheet the sc sheet
	 * @param pgList the pg list
	 * @param counter the counter
	 * @param pgMap the pg map
	 * @param pgName the pg name
	 * @param column the column
	 * @param style the style
	 */
	private void setPgContent(Sheet scSheet, List<Double> pgList, int counter, Map<String, PollutantGasTestResultRepresentation> pgMap, String pgName, int column, CellStyle style) {
		logger.info("in setPgContent-------------->");
		Row row8 = setRowForLimitesBeforePgResult(scSheet, style);

		Cell row8CellI = row8.createCell(column);//
		if (pgMap.get(pgName).getTvvValuedPollutantLimitRepresentation() != null) {
			row8CellI.setCellValue(isNull(pgMap.get(pgName).getTvvValuedPollutantLimitRepresentation().getMaxDValue()));
			row8CellI.setCellStyle(style);
		}

		Row row11 = setPgRowsLevel(scSheet, counter);
		Cell row11CellI = row11.createCell(column);//
		row11CellI.setCellValue(isNull(pgMap.get(pgName).getValue()));
		row11CellI.setCellStyle(style);
		Cell row11CellJ = row11.createCell(column + 1);//
		row11CellJ.setCellValue(isNull(pgMap.get(pgName).getStatisticalDecision()));
		row11CellJ.setCellStyle(style);
		Cell row11CellK = row11.createCell(column + 2);//
		row11CellK.setCellValue(isNull(pgMap.get(pgName).getStatisticalResult()));
		row11CellK.setCellStyle(style);
		if (pgMap.get(pgName).getValue() != null) {
			pgList.add(Double.valueOf(pgMap.get(pgName).getValue()));
		}

	}

	/**
	 * Sets the dyanmic pg labels.
	 *
	 * @param row5 the row5
	 * @param pgName the pg name
	 * @param style the style
	 */
	private void setDyanmicPgLabels(Row row5, String pgName, CellStyle style2) {
		CellStyle style = scBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setWrapText(true);
		Cell cellI = row5.createCell(colIndex++);
		cellI.setCellValue(pgName);
		cellI.setCellStyle(style);

		Cell cellJ = row5.createCell(colIndex++);
		cellJ.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.d"));
		cellJ.setCellStyle(style);

		Cell cellK = row5.createCell(colIndex++);
		cellK.setCellValue(pgName + "_calc ");
		cellK.setCellStyle(style);

	}

	/**
	 * Sets the pg rows level.
	 *
	 * @param scSheet the sc sheet
	 * @param counter the counter
	 * @return the row
	 */
	private Row setPgRowsLevel(Sheet scSheet, int counter) {
		logger.info("in setPgRowsLevel-------------->");
		Row row11 = scSheet.getRow(8 + counter);// orderRowCounter + counter
		if (row11 == null) {
			row11 = scSheet.createRow(8 + counter);
		}
		return row11;
	}

	/**
	 * Sets the row for limites before pg result.
	 *
	 * @param scSheet the sc sheet
	 * @param style the style
	 * @return the row
	 */
	private Row setRowForLimitesBeforePgResult(Sheet scSheet, CellStyle style2) {
		logger.info("in setRowForLimitesBeforePgResult-------------->");
		CellStyle style = scBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setWrapText(true);
		Row row8;
		row8 = scSheet.getRow(5);// orderRowCounter + counter
		if (row8 == null) {
			row8 = scSheet.createRow(5);
			Cell row8CellA = row8.createCell(0);//
			row8CellA.setCellValue(isNull(0));
			row8CellA.setCellStyle(style);

			Cell row8CellB = row8.createCell(1);//
			row8CellB.setCellValue("");
			row8CellB.setCellStyle(style);

			Cell row8CellC = row8.createCell(2);//
			row8CellC.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.limits"));

			row8CellC.setCellStyle(style);
		}

		return row8;
	}

	/**
	 * Sets the results set bench cell.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param orderRowCounter the order row counter
	 * @param style the style
	 */
	private void setResultsSetBenchCell(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, int orderRowCounter, CellStyle style) {
		logger.info("in setResultsSetBenchCell-------------->");
		Row row6;
		for (int benchCellCount = 0; benchCellCount < curvesDataRep.getResultSetBenchCellList().size(); benchCellCount++) {
			row6 = scSheet.getRow(orderRowCounter + benchCellCount);
			if (row6 == null) {
				row6 = scSheet.createRow(orderRowCounter + benchCellCount);
			}
			Cell row6Cell7 = row6.createCell(7);//
			row6Cell7.setCellValue(isNull(curvesDataRep.getResultSetBenchCellList().get(benchCellCount)));
			row6Cell7.setCellStyle(style);
		}

	}

	/**
	 * Sets the results set obd test.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param orderRowCounter the order row counter
	 * @param style the style
	 */
	private void setResultsSetOBDTest(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, int orderRowCounter, CellStyle style) {

		logger.info("in setResultsSetOBDTest-------------->");
		Row row6;
		for (int oBDTestCount = 0; oBDTestCount < curvesDataRep.getResultSetOBDTestList().size(); oBDTestCount++) {
			row6 = scSheet.getRow(orderRowCounter + oBDTestCount);
			if (row6 == null) {
				row6 = scSheet.createRow(orderRowCounter + oBDTestCount);
			}
			Cell row6Cell6 = row6.createCell(6);//
			row6Cell6.setCellStyle(style);
			String value = curvesDataRep.getResultSetOBDTestList().get(oBDTestCount);
			if (value != null) {
				if ("1".equals(value)) {
					row6Cell6.setCellValue("C");
				}
				if ("0".equals(value)) {
					row6Cell6.setCellValue("NC");
				}
			} else {
				row6Cell6.setCellValue("");
			}

		}

	}

	/**
	 * Sets the result set creation date column.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param orderRowCounter the order row counter
	 * @param style the style
	 */
	private void setResultSetCreationDateColumn(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, int orderRowCounter, CellStyle style) {
		logger.info("in setResultSetCreationDateColumn-------------->");
		Row row6;
		for (int creationDateCount = 0; creationDateCount < curvesDataRep.getResultsSetCreationDate().size(); creationDateCount++) {
			row6 = scSheet.getRow(orderRowCounter + creationDateCount);
			if (row6 == null) {
				row6 = scSheet.createRow(orderRowCounter + creationDateCount);
			}
			Cell row6Cell4 = row6.createCell(4);//
			row6Cell4.setCellValue(isNull(curvesDataRep.getResultsSetCreationDate().get(creationDateCount)));
			row6Cell4.setCellStyle(style);

		}
	}

	/**
	 * Sets the vehcle chasis number for column.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param orderRowCounter the order row counter
	 * @param style the style
	 */
	private void setVehcleChasisNumberForColumn(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, int orderRowCounter, CellStyle style) {
		logger.info("in setVehcleChasisNumberForColumn-------------->");
		Row row6;
		int chasisNumberCount = 0;
		for (String pgName : curvesDataRep.getVehicleBasedPollutantGasTestResult().keySet()) {
			row6 = scSheet.getRow(orderRowCounter + chasisNumberCount);
			if (row6 == null) {
				row6 = scSheet.createRow(orderRowCounter + chasisNumberCount);
			}
			Cell row6Cell2 = row6.createCell(2);// 2
			row6Cell2.setCellValue(isNull(pgName));
			row6Cell2.setCellStyle(style);
			Cell row6Cell3 = row6.createCell(3);// 3
			row6Cell3.setCellValue(isNull(curvesDataRep.getTvvLabel()));
			row6Cell3.setCellStyle(style);
			chasisNumberCount++;
		}

	}

	/**
	 * Sets the result set order for column.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param orderRowCounter the order row counter
	 * @param style
	 */
	private void setResultSetOrderForColumn(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, int orderRowCounter, CellStyle style) {
		logger.info("in setResultSetOrderForColumn-------------->");
		Row row6;

		for (int setOrderCount = 0; setOrderCount < curvesDataRep.getResultSetSetOrderList().size(); setOrderCount++) {

			row6 = scSheet.getRow(orderRowCounter + setOrderCount);
			if (row6 == null) {
				row6 = scSheet.createRow(orderRowCounter + setOrderCount);
			}

			Cell row6Cell1 = row6.createCell(1);// 1
			row6Cell1.setCellValue(isNull(curvesDataRep.getResultSetSetOrderList().get(setOrderCount)));
			row6Cell1.setCellStyle(style);

		}
	}

	/**
	 * Sets the fifth row of sc excel sheet.
	 *
	 * @param scBook the sc book
	 * @param scSheet the new fifth row of sc excel sheet
	 * @param style the style
	 * @return the row
	 */
	private Row setFifthRowOfScExcelSheet(Workbook scBook, Sheet scSheet, CellStyle style2) {
		logger.info("in setFifthRowOfScExcelSheet-------------->");
		Row row5 = scSheet.createRow(rowIndex++);
		CellStyle style = scBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setWrapText(true);

		colIndex = 0;
		Cell cellA = row5.createCell(colIndex++);
		cellA.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.n"));
		cellA.setCellStyle(style);

		Cell cellB = row5.createCell(colIndex++);
		cellB.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.n.essai"));
		cellB.setCellStyle(style);

		Cell cellC = row5.createCell(colIndex++);
		cellC.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.chassis"));
		cellC.setCellStyle(style);

		Cell cellD = row5.createCell(colIndex++);
		cellD.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.tvv"));
		cellD.setCellStyle(style);

		Cell cellE = row5.createCell(colIndex++);
		cellE.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.date"));
		cellE.setCellStyle(style);

		Cell cellF = row5.createCell(colIndex++);
		cellF.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.obd.preparation"));
		cellF.setCellStyle(style);

		Cell cellG = row5.createCell(colIndex++);
		cellG.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.obd.Test"));
		cellG.setCellStyle(style);

		Cell cellH = row5.createCell(colIndex++);
		cellH.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.n.cel"));
		cellH.setCellStyle(style);

		return row5;
	}

	/**
	 * Sets the fourth row of sc excel sheet.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param style the style
	 */
	private void setFourthRowOfScExcelSheet(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, CellStyle style) {
		Row row4 = scSheet.createRow(rowIndex++);

		colIndex = 0;
		Cell row4Cell0 = row4.createCell(colIndex++);
		row4Cell0.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.gearbox"));

		Cell row4Cell1 = row4.createCell(colIndex++);
		row4Cell1.setCellValue(isNull(curvesDataRep.getGearBoxLabel()));

		Cell row4Cell2 = row4.createCell(colIndex++);
		row4Cell2.setCellType(Cell.CELL_TYPE_BLANK);

		Cell row4Cell3 = row4.createCell(colIndex++);
		row4Cell3.setCellType(Cell.CELL_TYPE_BLANK);

		Cell row4Cell4 = row4.createCell(colIndex++);
		row4Cell4.setCellType(Cell.CELL_TYPE_BLANK);

		Cell row4Cell5 = row4.createCell(colIndex++);
		row4Cell5.setCellType(Cell.CELL_TYPE_BLANK);

		Cell row4Cell6 = row4.createCell(colIndex++);
		row4Cell6.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.up"));

		Cell row4Cell7 = row4.createCell(colIndex++);
		row4Cell7.setCellValue(isNull(curvesDataRep.getVehicleFactoryLabel()));
	}

	/**
	 * Sets the third row of sc excel sheet.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param style the style
	 */
	private void setThirdRowOfScExcelSheet(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, CellStyle style) {
		Row row3 = scSheet.createRow(rowIndex++);
		colIndex = 0;
		Cell row3Cell0 = row3.createCell(colIndex++);
		row3Cell0.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.motor"));

		Cell row3Cell1 = row3.createCell(colIndex++);
		row3Cell1.setCellValue(isNull(curvesDataRep.getMoteurLabel()));
		row3Cell1.setCellStyle(style);
		Cell row3Cell2 = row3.createCell(colIndex++);
		row3Cell2.setCellType(Cell.CELL_TYPE_BLANK);
		row3Cell2.setCellStyle(style);

		Cell row3Cell3 = row3.createCell(colIndex++);
		row3Cell3.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.road.law"));
		row3Cell3.setCellStyle(style);

		Cell row3Cell4 = row3.createCell(colIndex++);
		row3Cell4.setCellValue(isNull(curvesDataRep.getPsaReference()));
		row3Cell4.setCellStyle(style);

		Cell row3Cell5 = row3.createCell(colIndex++);
		row3Cell5.setCellType(Cell.CELL_TYPE_BLANK);
		row3Cell5.setCellStyle(style);

		Cell row3Cell6 = row3.createCell(colIndex++);
		row3Cell6.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.genre"));
		row3Cell6.setCellStyle(style);

		Cell row3Cell7 = row3.createCell(colIndex++);
		row3Cell7.setCellValue(isNull(curvesDataRep.getTypeOfTestLabel()));
		row3Cell7.setCellStyle(style);
	}

	/**
	 * Sets the second row of sc excel sheet.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param style the style
	 */
	private void setSecondRowOfScExcelSheet(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, CellStyle style) {
		logger.info("in setSecondRowOfScExcelSheet-------------->");
		Row row2 = scSheet.createRow(rowIndex++);

		colIndex = 0;
		Cell row2Cell0 = row2.createCell(colIndex++);
		row2Cell0.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.familly.stat"));
		row2Cell0.setCellStyle(style);

		Cell row2Cell1 = row2.createCell(colIndex++);
		row2Cell1.setCellValue(isNull(curvesDataRep.getVehicleFamilyLabel() + curvesDataRep.getProjectCodeLabel()));
		row2Cell1.setCellStyle(style);

		Cell row2Cell2 = row2.createCell(colIndex++);
		row2Cell2.setCellType(Cell.CELL_TYPE_BLANK);
		row2Cell2.setCellStyle(style);

		Cell row2Cell3 = row2.createCell(colIndex++);
		row2Cell3.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.inertia"));
		row2Cell3.setCellStyle(style);

		Cell row2Cell4 = row2.createCell(colIndex++);
		row2Cell4.setCellValue(curvesDataRep.getInertiaValue());
		row2Cell4.setCellStyle(style);

		Cell row2Cell5 = row2.createCell(colIndex++);
		row2Cell5.setCellType(Cell.CELL_TYPE_BLANK);
		row2Cell5.setCellStyle(style);

		Cell row2Cell6 = row2.createCell(colIndex++);
		row2Cell6.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.limit.type"));
		row2Cell6.setCellStyle(style);

		Cell row2Cell7 = row2.createCell(colIndex++);
		row2Cell7.setCellValue(isNull(curvesDataRep.getEmissionStandardLabel() + "( " + curvesDataRep.getEmissionStandardVersion() + " )"));
		row2Cell7.setCellStyle(style);
	}

	/**
	 * Sets the first row of sc excel sheet.
	 *
	 * @param curvesDataRep the curves data representation
	 * @param scSheet the sc sheet
	 * @param style the style
	 */
	private void setFirstRowOfSCExcelSheet(ExportStatisticalCurvesDataRepresentation curvesDataRep, Sheet scSheet, CellStyle style) {
		logger.info("in writeIntoExcel-------------->");
		Row row1 = scSheet.createRow(rowIndex++);

		Cell row1Cell0 = row1.createCell(colIndex++);
		row1Cell0.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.tvv"));
		row1Cell0.setCellStyle(style);
		Cell row1Cell1 = row1.createCell(colIndex++);
		row1Cell1.setCellValue(isNull(curvesDataRep.getTvvLabel()));
		row1Cell1.setCellStyle(style);

		Cell row1Cell2 = row1.createCell(colIndex++);
		row1Cell2.setCellType(Cell.CELL_TYPE_BLANK);
		row1Cell2.setCellStyle(style);

		Cell row1Cell3 = row1.createCell(colIndex++);
		row1Cell3.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.calculator"));
		row1Cell3.setCellStyle(style);

		Cell row1Cell4 = row1.createCell(colIndex++);
		row1Cell4.setCellValue(curvesDataRep.getCalculatorValue());
		row1Cell4.setCellStyle(style);

		Cell row1Cell5 = row1.createCell(colIndex++);
		row1Cell5.setCellType(Cell.CELL_TYPE_BLANK);
		row1Cell5.setCellStyle(style);

		Cell row1Cell6 = row1.createCell(colIndex++);
		row1Cell6.setCellValue(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.excel.familly.stat"));
		row1Cell6.setCellStyle(style);

		Cell row1Cell7 = row1.createCell(colIndex++);
		row1Cell7.setCellValue(isNull(curvesDataRep.getRegulationGroupLabel() + curvesDataRep.getStatisticalCalculationRuleLabel()));
		row1Cell7.setCellStyle(style);
	}

	/**
	 * Checks if is null.
	 *
	 * @param date the date
	 * @return the string
	 */
	private String isNull(Date date) {
		if (date == null) {
			return "";
		}

		return new SimpleDateFormat("MM/dd/yyyy").format(date);

	}

	/**
	 * Auto size columns.
	 *
	 * @param workbook the workbook
	 */
	private void autoSizeColumns(Workbook workbook) {
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows() > 0) {
				CellStyle style = workbook.createCellStyle();
				style.setWrapText(true);
				Row row = sheet.getRow(0);
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					cell.setCellStyle(style);
					int columnIndex = cell.getColumnIndex();
					sheet.autoSizeColumn(columnIndex);
				}
			}
		}
	}

	/**
	 * Checks if is null.
	 *
	 * @param value the value
	 * @return the string
	 */
	private String isNull(String value) {

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
	private String isNull(Double value) {

		if (value == null) {
			return "";
		}

		if (value.isNaN()) {
			return String.valueOf(0);
		}

		return String.valueOf(value);

	}

	/**
	 * Checks if is null.
	 *
	 * @param value the value
	 * @return the string
	 */
	private String isNull(Integer value) {

		if (value == null) {
			return "";
		}

		return String.valueOf(value);

	}

	/**
	 * Checks if is null.
	 *
	 * @param value the value
	 * @return the string
	 */
	private String isNull(Long value) {

		if (value == null) {
			return "";
		}

		return String.valueOf(value);

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