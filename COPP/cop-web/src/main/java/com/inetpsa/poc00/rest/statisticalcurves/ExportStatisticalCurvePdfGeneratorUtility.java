package com.inetpsa.poc00.rest.statisticalcurves;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.chart.JFreeChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.common.PropertiesLang;
import com.inetpsa.poc00.pollutantorgas.PollutantGasTestResultRepresentation;
import com.inetpsa.poc00.util.CalculationUtil;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * The Class ExportStatisticalCurvePdfGeneratorUtility.
 */
public class ExportStatisticalCurvePdfGeneratorUtility {

	/** The Constant DATE_PATTERN. */
	public static final String DATE_PATTERN = "dd/MM/yyyy";

	/** The Constant TIME_PATTERN. */
	public static final String TIME_PATTERN = "HH'h'mm";

	/** The Constant FONT_SIZE. */
	public static final int FONT_SIZE = 8;

	/** The bold. */
	public static final Font BOLD_FONT = new Font(Font.FontFamily.TIMES_ROMAN, FONT_SIZE, Font.BOLD);

	/** The italic. */
	public static final Font ITALIC_FONT = new Font(Font.FontFamily.TIMES_ROMAN, FONT_SIZE, Font.ITALIC);

	/** The normal. */
	public static final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, FONT_SIZE, Font.NORMAL);

	/** The chart width. */
	private int chartWidth = 550;

	/** The chart height. */
	private int chartHeight = 250;

	/** The property lang. */
	private static PropertiesLang propertyLang = PropertiesLang.getInstance();

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ExportStatisticalCurvePdfGeneratorUtility.class);

	/**
	 * Creates the pdf.
	 * 
	 * @param dest the dest
	 * @param curvesDataRep the curves data rep
	 * @param choiceList the choice list
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws DocumentException the document exception
	 */
	public void createStatisticalCurvePdf(String dest, ExportStatisticalCurvesDataRepresentation curvesDataRep, List<String> choiceList) throws IOException, DocumentException {

		logger.info(" ** START : Statistical Curve PDF Generation ** ");

		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
		SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_PATTERN);

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));

		BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		BaseFont boldFont = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);

		document.open();

		PdfContentByte canvas = createFirstHeaderRow(curvesDataRep, formatter, timeFormatter, writer, baseFont, boldFont);

		createSecondHeaderRow(curvesDataRep, canvas);

		final List<String> seriesName = Arrays.asList(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.value"), propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.limit"), propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.limit08"), propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.averageplus2s"),
				propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.averageminus2s"));

		String chartTitle = propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.charttitle");

		PdfPTable table = prepareChartData(curvesDataRep, chartTitle, seriesName, canvas, choiceList);

		document.add(table);

		logger.info(" ** END : Statistical Curve PDF Generation ** ");

		document.close();
	}

	/**
	 * Creates the second header row.
	 *
	 * @param curvesDataRep the curves data rep
	 * @param canvas the canvas
	 * @throws DocumentException the document exception
	 */
	private void createSecondHeaderRow(ExportStatisticalCurvesDataRepresentation curvesDataRep, PdfContentByte canvas) throws DocumentException {

		PdfPTable secondRow = new PdfPTable(3);
		secondRow.setTotalWidth(480);

		LinkedHashMap<String, String> map1 = new LinkedHashMap<>();
		map1.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.tvv"), curvesDataRep.getTvvLabel());
		map1.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.family"), curvesDataRep.getVehicleFamilyLabel());
		map1.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.motor"), curvesDataRep.getMoteurLabel());
		map1.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.bv"), curvesDataRep.getGearBoxLabel());

		LinkedHashMap<String, String> map2 = new LinkedHashMap<>();
		map2.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.calculator"), curvesDataRep.getCalculatorValue());

		if (curvesDataRep.getInertiaValue() != 0) {
			map2.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.inertia"), String.valueOf(curvesDataRep.getInertiaValue()));
		} else {
			map2.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.inertia"), "");
		}

		map2.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.road.law"), curvesDataRep.getPsaReference());

		LinkedHashMap<String, String> map3 = new LinkedHashMap<>();
		map3.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.familly.stat"), curvesDataRep.getRegulationGroupLabel() + curvesDataRep.getStatisticalCalculationRuleLabel());
		map3.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.limit.type"), curvesDataRep.getEmissionStandardLabel() + "( " + curvesDataRep.getEmissionStandardVersion() + " )");
		map3.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.genre"), curvesDataRep.getTypeOfTestLabel());
		map3.put(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.up"), curvesDataRep.getVehicleFactoryLabel());

		secondRow.addCell(getBoldCell(map1));
		secondRow.addCell(getFirstCell(map2));
		secondRow.addCell(getFirstCell(map3));

		secondRow.completeRow();
		secondRow.writeSelectedRows(0, -1, 65, 765, canvas);

	}

	/**
	 * Creates the first header row.
	 *
	 * @param curvesDataRep the curves data rep
	 * @param formatter the formatter
	 * @param timeFormatter the time formatter
	 * @param writer the writer
	 * @param baseFont the base font
	 * @param boldFont the bold font
	 * @return the pdf content byte
	 */
	private PdfContentByte createFirstHeaderRow(ExportStatisticalCurvesDataRepresentation curvesDataRep, SimpleDateFormat formatter, SimpleDateFormat timeFormatter, PdfWriter writer, BaseFont baseFont, BaseFont boldFont) {

		float llx = 65;
		float lly = 770;
		float urx = 205;
		float ury = 810;

		PdfContentByte canvas = writer.getDirectContent();

		Rectangle rect1 = new Rectangle(llx, lly, urx, ury);
		rect1.setBorder(Rectangle.BOX);
		rect1.setBorderWidth(0.75f);
		canvas.rectangle(rect1);
		canvas.setFontAndSize(baseFont, FONT_SIZE);
		canvas.beginText();
		canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.directionresearch"), llx + 10, 800, 0);
		canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.anddevelopment"), llx + 10, 788, 0);
		canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.drd"), llx + 10, 776, 0);

		canvas.endText();

		llx = llx + (140) + 5;
		lly = 770;
		urx = urx + 5 + 80;
		ury = 810;

		Rectangle rect2 = new Rectangle(llx, lly, urx, ury);
		rect2.setBorder(Rectangle.BOX);
		rect2.setBorderColor(BaseColor.BLACK);
		rect2.setBorderWidth(0.75f);
		canvas.setFontAndSize(boldFont, 11);
		canvas.rectangle(rect2);

		canvas.beginText();

		canvas.showTextAligned(PdfContentByte.ALIGN_CENTER, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.curves"), llx + 10 + 30, 800, 0);
		canvas.showTextAligned(PdfContentByte.ALIGN_CENTER, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.of"), llx + 10 + 30, 788, 0);
		canvas.showTextAligned(PdfContentByte.ALIGN_CENTER, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.pollutants"), llx + 10 + 30, 776, 0);
		canvas.endText();

		llx = llx + 10 + 65 + 10;
		lly = 770;
		urx = urx + 85 + 5 + 10;
		ury = 810;

		Rectangle rect3 = new Rectangle(llx, lly, urx, ury);
		rect3.setBorder(Rectangle.BOX);
		rect3.setBorderColor(BaseColor.BLACK);
		rect3.setBorderWidth(0.75f);
		canvas.rectangle(rect3);
		canvas.setFontAndSize(baseFont, FONT_SIZE);
		canvas.beginText();
		canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.reference.period"), llx + 10, 800, 0);

		if (curvesDataRep.getDebutDate() != null) {
			canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.start") + "					" + formatter.format(curvesDataRep.getDebutDate()), llx + 10, 788, 0);
		}

		if (curvesDataRep.getFinDate() != null) {
			canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.end") + "						" + formatter.format(curvesDataRep.getFinDate()), llx + 10, 776, 0);
		}

		canvas.endText();

		llx = llx + 100;
		lly = 770;
		urx = urx + 155;
		ury = 810;

		Rectangle rect4 = new Rectangle(llx, lly, urx, ury);
		rect4.setBorder(Rectangle.BOX);
		rect4.setBorderColor(BaseColor.BLACK);
		rect4.setBorderWidth(0.75f);
		canvas.rectangle(rect4);
		canvas.beginText();
		canvas.setFontAndSize(baseFont, FONT_SIZE + 2f);
		canvas.showTextAligned(PdfContentByte.ALIGN_CENTER, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.psapeugeotcitroen"), llx + 70, 800, 0);
		canvas.setFontAndSize(baseFont, FONT_SIZE);
		canvas.showTextAligned(PdfContentByte.ALIGN_CENTER, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.mulhouse.site"), llx + 70, 788, 0);
		canvas.showTextAligned(PdfContentByte.ALIGN_CENTER, propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.date") + "	" + formatter.format(new Date()) + " 			 " + propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.hour") + "	" + timeFormatter.format(new Date()), llx + 70, 776, 0);
		canvas.endText();

		return canvas;
	}

	/**
	 * Prepare chart.
	 * 
	 * @param dataListforPg the data listfor pg
	 * @param vehicleNames the vehicle names
	 * @param seriesName the series name
	 * @param chartTitle the chart title
	 * @param showLegend the show legend
	 * @param canvas the canvas
	 * @return the pdf template
	 */
	private PdfTemplate prepareChart(List<Map<Integer, Long>> dataListforPg, Set<String> vehicleNames, List<String> seriesName, String chartTitle, Boolean showLegend, PdfContentByte canvas) {

		logger.info("** Start : Chart Generation **");

		JFreeChart chart = ExportStatisticalCurveLineChart.generateLineChart(dataListforPg, seriesName, vehicleNames, chartTitle, showLegend);
		PdfTemplate template = canvas.createTemplate(chartWidth, chartHeight);
		Graphics2D graphics2d = template.createGraphics(chartWidth, chartHeight, new DefaultFontMapper());
		Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, chartWidth, chartHeight);
		chart.draw(graphics2d, rectangle2d);
		graphics2d.dispose();

		logger.info("** END : Chart Generation **");

		return template;

	}

	/**
	 * Prepare data.
	 *
	 * @param curvesDataRep the curves data rep
	 * @param chartTitle the chart title
	 * @param seriesName the series name
	 * @param canvas the canvas
	 * @param choiceList the choice list
	 * @return the list
	 * @throws BadElementException the bad element exception
	 */
	private PdfPTable prepareChartData(ExportStatisticalCurvesDataRepresentation curvesDataRep, String chartTitle, List<String> seriesName, PdfContentByte canvas, List<String> choiceList) throws BadElementException {

		Map<String, Double> dataSetForLimite = new HashMap<>();
		Map<String, Double> dataSetForMoyenne = new HashMap<>();
		Map<String, Double> dataSetFoEcart = new LinkedHashMap<>();

		float x = 10;
		float y = 550;

		Set<String> vehicleNames = curvesDataRep.getVehicleBasedPollutantGasTestResult().keySet();

		Map<String, Map<String, PollutantGasTestResultRepresentation>> pgtVehicleMap = curvesDataRep.getVehicleBasedPollutantGasTestResult();
		Set<Entry<String, Map<String, PollutantGasTestResultRepresentation>>> pgtEntrySet = pgtVehicleMap.entrySet();
		int xAxis = 0;
		List<Map<Integer, Long>> pgListCollection;
		Boolean showLegend = true;
		Map<String, List<Double>> pgMap = new LinkedHashMap<>();

		PdfPTable masterTable = new PdfPTable(1);
		masterTable.setTotalWidth(y);
		masterTable.setWidthPercentage(100);
		masterTable.getDefaultCell().setBorder(0);

		PdfPTable chartTable = new PdfPTable(1);
		chartTable.setTotalWidth(y);
		chartTable.setWidthPercentage(100);
		chartTable.getDefaultCell().setBorder(0);

		for (int i = 0; i < 6; i++) {
			chartTable.addCell(" ");
		}

		for (String pgName : choiceList) {

			List<Double> pgList = new ArrayList<>();
			pgListCollection = prepareChartDataForPg(pgtEntrySet, dataSetForLimite, xAxis, pgList, pgName);

			chartTable.addCell(Image.getInstance(prepareChart(pgListCollection, vehicleNames, seriesName, chartTitle + pgName, showLegend, canvas)));

			pgMap.put(pgName, pgList);

			showLegend = true;
		}

		/* BELOW TWO METHODS ARE FOR DATA TABLE EXISTS AT BOTTOM OF THIS PDF */
		setDataSetForMoyenne(dataSetForMoyenne, pgMap);
		setDataSetFoEcart(dataSetFoEcart, pgMap, dataSetForMoyenne);

		masterTable.addCell(chartTable);

		PdfPTable dataSetTable = generatePollGasDataTable(dataSetFoEcart, dataSetForLimite, dataSetForMoyenne, choiceList, y);

		masterTable.addCell(dataSetTable);

		PdfTemplate template1 = canvas.createTemplate(chartWidth, chartHeight);
		masterTable.writeSelectedRows(1, -1, x, y, template1);
		canvas.addTemplate(template1, x, y);

		return masterTable;

	}

	/**
	 * Generate poll gas data table.
	 *
	 * @param dataSetFoEcart the data set fo ecart
	 * @param dataSetForLimite the data set for limite
	 * @param dataSetForMoyenne the data set for moyenne
	 * @param choiceList the choice list
	 * @param y the y
	 * @return the pdf p table
	 */
	// TODO : CHANGE THE APPROACH, REMOVE THE NUMBER OF FOR_LOOP, REVIEW COMMENT
	private PdfPTable generatePollGasDataTable(Map<String, Double> dataSetFoEcart, Map<String, Double> dataSetForLimite, Map<String, Double> dataSetForMoyenne, List<String> choiceList, float y) {

		PdfPTable dataSetTable = new PdfPTable(choiceList.size() + 1);
		dataSetTable.setTotalWidth(y);
		dataSetTable.setWidthPercentage(100);

		dataSetTable.addCell("");
		for (String pgName : choiceList) {

			PdfPCell cell = new PdfPCell(new Phrase(pgName, NORMAL_FONT));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(0.75f);

			dataSetTable.addCell(cell);
		}

		dataSetTable.addCell(new PdfPCell(new Phrase(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.limits"), NORMAL_FONT)));

		for (String pgName : choiceList) {
			logger.info("limies---->" + pgName);

			PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(dataSetForLimite.get(pgName)), NORMAL_FONT));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(0.75f);

			dataSetTable.addCell(cell);
		}
		dataSetTable.addCell(new PdfPCell(new Phrase(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.average"), NORMAL_FONT)));

		for (String pgName : choiceList) {
			logger.info("Moyenne---->" + pgName);
			PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(dataSetForMoyenne.get(pgName)), NORMAL_FONT));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);

			dataSetTable.addCell(cell);
		}
		dataSetTable.addCell(new PdfPCell(new Phrase(propertyLang.getProperty("cop.statisticalsample.statisticalcurve.pdf.ectype"), NORMAL_FONT)));

		for (String pgName : choiceList) {
			logger.info("Ecart---->" + pgName);
			PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(dataSetFoEcart.get(pgName)), NORMAL_FONT));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(0.75f);

			dataSetTable.addCell(cell);
		}

		return dataSetTable;
	}

	/**
	 * Prepare chart data for hc.
	 * 
	 * @param pgtEntrySet the pgt entry set
	 * @param dataSetForLimite the data set for limite
	 * @param xAxis the x axis
	 * @param pgList the hc list
	 * @param pgName the pg name
	 * @return the list
	 */
	private List<Map<Integer, Long>> prepareChartDataForPg(Set<Entry<String, Map<String, PollutantGasTestResultRepresentation>>> pgtEntrySet, Map<String, Double> dataSetForLimite, int xAxis, List<Double> pgList, String pgName) {

		logger.info("Prepare Chart Data For PG	: " + pgName);

		Map<Integer, Long> moyennePlus2sDataMap = new HashMap<>();
		Map<Integer, Long> moyenneMinus2sDataMap = new HashMap<>();

		List<Map<Integer, Long>> pgListCollection = getPgDataForVehicle(pgName, dataSetForLimite, pgtEntrySet, xAxis, pgList);

		setAverageForPg(pgList, moyennePlus2sDataMap, moyenneMinus2sDataMap, pgListCollection);

		pgListCollection.add(moyennePlus2sDataMap);
		pgListCollection.add(moyenneMinus2sDataMap);

		return pgListCollection;

	}

	/**
	 * Gets the pg data for vehicle.
	 * 
	 * @param pgName the pg name
	 * @param dataSetForLimite the data set for limite
	 * @param pgtEntrySet the pgt entry set
	 * @param xAxis the x axis
	 * @param pgList the pg list
	 * @return the pg data for vehicle
	 */
	private List<Map<Integer, Long>> getPgDataForVehicle(String pgName, Map<String, Double> dataSetForLimite, Set<Entry<String, Map<String, PollutantGasTestResultRepresentation>>> pgtEntrySet, int xAxis, List<Double> pgList) {

		logger.info("GET PG DATA FOR VEHICLE : " + pgName);

		Map<Integer, Long> valeurDataMap = new HashMap<>();
		Map<Integer, Long> limiteDataMap = new HashMap<>();
		Map<Integer, Long> limite08DataMap = new HashMap<>();

		List<Map<Integer, Long>> pgListCollection = new ArrayList<>();

		for (Entry<String, Map<String, PollutantGasTestResultRepresentation>> entry : nullGuard(pgtEntrySet)) {
			Map<String, PollutantGasTestResultRepresentation> pgMap = entry.getValue();
			if (pgMap != null && pgMap.get(pgName) != null && pgMap.get(pgName).getValue() != null) {

				dataSetForLimite.put(pgName, pgMap.get(pgName).getTvvValuedPollutantLimitRepresentation().getMaxDValue());
				valeurDataMap.put(xAxis, (long) Double.parseDouble(pgMap.get(pgName).getValue()));

				limiteDataMap.put(xAxis, pgMap.get(pgName).getTvvValuedPollutantLimitRepresentation().getMaxDValue().longValue());
				limite08DataMap.put(xAxis, Math.round(pgMap.get(pgName).getTvvValuedPollutantLimitRepresentation().getMaxDValue() * 0.8));

				pgList.add(Double.valueOf(pgMap.get(pgName).getValue()));

			}
			xAxis++;
		}
		pgListCollection.add(valeurDataMap);
		pgListCollection.add(limiteDataMap);
		pgListCollection.add(limite08DataMap);

		return pgListCollection;
	}

	/**
	 * Sets the average for pg.
	 * 
	 * @param pgList the hc list
	 * @param moyennePlus2sDataMap the moyenne plus2s data map
	 * @param moyenneMinus2sDataMap the moyenne minus2s data map
	 * @param pgListCollection the pg list collection
	 */
	private void setAverageForPg(List<Double> pgList, Map<Integer, Long> moyennePlus2sDataMap, Map<Integer, Long> moyenneMinus2sDataMap, List<Map<Integer, Long>> pgListCollection) {
		Map<Integer, Long> valueMap = pgListCollection.get(0);
		for (Entry<Integer, Long> entry : valueMap.entrySet()) {
			moyennePlus2sDataMap.put(entry.getKey(), Math.round(CalculationUtil.calculateAverage(pgList) + (2 * entry.getValue())));
			moyenneMinus2sDataMap.put(entry.getKey(), Math.round(CalculationUtil.calculateAverage(pgList) - (2 * entry.getValue())));
		}
	}

	/**
	 * Sets the data set for moyenne.
	 * 
	 * @param dataSetForMoyenne the data set for moyenne
	 * @param pgMap the pg map
	 */
	private void setDataSetForMoyenne(Map<String, Double> dataSetForMoyenne, Map<String, List<Double>> pgMap) {
		for (Entry<String, List<Double>> pgSet : pgMap.entrySet()) {
			if (pgSet.getValue() != null && !pgSet.getValue().isEmpty())
				dataSetForMoyenne.put(pgSet.getKey(), CalculationUtil.calculateAverage(pgSet.getValue()));
		}
	}

	/**
	 * Sets the data set fo ecart.
	 *
	 * @param dataSetFoEcart the data set fo ecart
	 * @param pgMap the pg map
	 * @param dataSetForMoyenne the data set for moyenne
	 */
	private void setDataSetFoEcart(Map<String, Double> dataSetFoEcart, Map<String, List<Double>> pgMap, Map<String, Double> dataSetForMoyenne) {
		for (Entry<String, List<Double>> pgSet : pgMap.entrySet()) {
			if (pgSet.getValue() != null && !pgSet.getValue().isEmpty()) {
				List<Double> pgList = pgSet.getValue();
				dataSetFoEcart.put(pgSet.getKey(), CalculationUtil.calculateDeviation(pgList));
			}
		}
	}

	/**
	 * Gets the first cell.
	 * 
	 * @param objectMap the object map
	 * @return the first cell
	 * @throws DocumentException the document exception
	 */
	private PdfPCell getFirstCell(Map<String, String> objectMap) throws DocumentException {

		PdfPTable inner = new PdfPTable(2);
		inner.setWidths(new int[] { 2, 4 });
		inner.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		inner.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

		PdfPCell temp;

		for (Map.Entry<String, String> map : objectMap.entrySet()) {
			temp = new PdfPCell(new Phrase(map.getKey() + ":", ITALIC_FONT));
			temp.setBorder(0);
			inner.addCell(temp);

			temp = new PdfPCell(new Phrase(map.getValue(), NORMAL_FONT));
			temp.setBorder(0);
			inner.addCell(temp);
		}

		PdfPCell cell = new PdfPCell(inner);
		cell.setBorderWidth(0.75f);
		cell.setFixedHeight(50f);

		return cell;
	}

	/**
	 * Gets the bold cell.
	 * 
	 * @param objectMap the object map
	 * @return the bold cell
	 * @throws DocumentException the document exception
	 */
	private PdfPCell getBoldCell(Map<String, String> objectMap) throws DocumentException {

		PdfPTable inner = new PdfPTable(2);
		inner.setWidths(new int[] { 2, 4 });
		inner.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		inner.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

		PdfPCell temp;

		for (Map.Entry<String, String> map : objectMap.entrySet()) {
			temp = new PdfPCell(new Phrase(map.getKey() + ":", ITALIC_FONT));
			temp.setBorder(0);
			inner.addCell(temp);

			temp = new PdfPCell(new Phrase(map.getValue(), BOLD_FONT));
			temp.setBorder(0);
			inner.addCell(temp);
		}

		PdfPCell cell = new PdfPCell(inner);
		cell.setBorderWidth(0.75f);
		cell.setFixedHeight(50f);

		return cell;
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