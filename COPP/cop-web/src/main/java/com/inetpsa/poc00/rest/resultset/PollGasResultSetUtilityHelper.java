/*
 * Creation : Dec 12, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.rest.resultset;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.common.PropertiesLang;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckList;
import com.inetpsa.poc00.domain.preparationresult.PreparationResult;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * PollGasResultSetUtilityHelper
 * 
 * @author mehaj
 */

public class PollGasResultSetUtilityHelper extends PdfPageEventHelper {

	/** The property lang. */
	static PropertiesLang propertyLang = PropertiesLang.getInstance();

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(PollGasResultSetUtilityHelper.class);

	public static void generatePDF(PollutantGasResultSet resultSet, String tempPath) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.PDF_DATE_FORMAT);
		String pdfPath = tempPath;
		List<PollutantGasResultSet> listPollutantGasResultSet = resultSet.getVehicleFile().getPgResultSet();
		List<PollutantGasTestResult> listPollutantGasTestResult = resultSet.getPollutantGasTestResult();
		VehicleFile vehiclefileObj = resultSet.getVehicleFile();
		Document document = new Document();
		try {
			File file = new File(pdfPath);
			PdfWriter writer;
			writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			PollGasResultSetUtilityHelper event = new PollGasResultSetUtilityHelper();
			writer.setPageEvent(event);
			PdfPTable masterTable = new PdfPTable(1);
			masterTable.setWidthPercentage(100);
			masterTable.getDefaultCell().setBorder(0);

			// table to set header values STARTS----------
			PdfPTable mainTable1 = new PdfPTable(3);
			mainTable1.setWidthPercentage(100);
			PdfPCell header1Cell = new PdfPCell();
			Phrase headerCell11 = new Phrase(propertyLang.getProperty("cop.resultset.pdf.header11"), getFont(8, Font.NORMAL));
			Paragraph headerCell11Para = new Paragraph(headerCell11);
			headerCell11Para.setAlignment(Element.ALIGN_LEFT);
			header1Cell.addElement(headerCell11Para);
			Phrase headerCell12 = new Phrase(propertyLang.getProperty("cop.resultset.pdf.header12"), getFont(8, Font.NORMAL));
			Paragraph headerCell12Para = new Paragraph(headerCell12);
			headerCell12Para.setAlignment(Element.ALIGN_LEFT);
			header1Cell.addElement(headerCell12Para);
			Phrase headerCell13 = new Phrase(propertyLang.getProperty("cop.resultset.pdf.header13"), getFont(8, Font.NORMAL));
			Paragraph headerCell13Para = new Paragraph(headerCell13);
			headerCell13Para.setAlignment(Element.ALIGN_LEFT);
			header1Cell.addElement(headerCell13Para);
			header1Cell.setPaddingLeft(7);
			makeBorderExpRight(header1Cell);
			mainTable1.addCell(header1Cell);

			PdfPCell headerCell2 = new PdfPCell(new Phrase(propertyLang.getProperty("cop.resultset.pdf.header2"), getFont(12, Font.BOLD)));
			headerCell2.setPaddingRight(10);
			headerCell2.setColspan(1);
			setHeaderCellAlignment(headerCell2);
			makeBorderExpRight(headerCell2);
			mainTable1.addCell(headerCell2);
			PdfPCell headerCell3 = new PdfPCell();
			Phrase p1 = new Phrase(propertyLang.getProperty("cop.resultset.pdf.header3"), getFont(12, Font.NORMAL));
			Paragraph p2 = new Paragraph(p1);
			p2.setAlignment(Element.ALIGN_CENTER);
			headerCell3.addElement(p2);
			Phrase ph1 = new Phrase(propertyLang.getProperty("cop.resultset.pdf.header31") + "\n Date :" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + "  " + propertyLang.getProperty("cop.resultset.pdf.hour") + new SimpleDateFormat("HH'h'mm").format(new Date()), getFont(8, Font.NORMAL));
			Paragraph ph2 = new Paragraph(ph1);
			ph2.setAlignment(Element.ALIGN_CENTER);
			headerCell3.addElement(ph2);
			headerCell3.setPaddingBottom(5);
			makeBorderBold(headerCell3);
			mainTable1.addCell(headerCell3);
			masterTable.addCell(mainTable1);
			// table to set header values ENDS----------

			// table to set vehicle details STARTS----------
			PdfPTable mainTable2 = new PdfPTable(4);
			mainTable2.setWidthPercentage(100);
			mainTable2.setSpacingBefore(4);
			mainTable2.getDefaultCell().setBorder(1);
			PdfPCell detailsHeader = new PdfPCell(new Phrase(propertyLang.getProperty("cop.resultset.pdf.table2.header"), getFont(12, Font.BOLD)));
			makeBorderBold(detailsHeader);
			detailsHeader.setColspan(4);
			detailsHeader.setPaddingBottom(7);
			detailsHeader.setPaddingTop(7);
			setHeaderCellAlignment(detailsHeader);
			mainTable2.addCell(detailsHeader);

			LinkedHashMap<String, String> vehicleDetailsMap = new LinkedHashMap<>();
			vehicleDetailsMap = setVehicleFileValues(vehicleDetailsMap, vehiclefileObj, resultSet);
			Iterator it = vehicleDetailsMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				PdfPCell detailsCol1 = new PdfPCell(new Phrase(pair.getKey().toString()));
				detailsCol1.setColspan(1);
				detailsCol1.setBorderWidthTop(0);
				detailsCol1.setHorizontalAlignment(Element.ALIGN_LEFT);
				detailsCol1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				detailsCol1.setPaddingLeft(5);
				PdfPCell detailsCol2 = new PdfPCell(new Phrase(pair.getValue().toString(), getFont(11, Font.BOLD)));
				detailsCol2.setColspan(1);
				setHeaderCellAlignment(detailsCol2);
				detailsCol1.setBorderWidthBottom(0);
				mainTable2.addCell(detailsCol1);
				mainTable2.addCell(detailsCol2);
			}
			PdfPCell detailsColLast = new PdfPCell();

			detailsColLast.setColspan(4);
			mainTable2.addCell(detailsColLast);
			masterTable.addCell(mainTable2);
			// table to set vehicle details ENDS----------

			// table to set result set update date STARTS----------
			PdfPTable mainTable3 = new PdfPTable(3);
			mainTable3.setWidthPercentage(100);
			mainTable3.setSpacingBefore(10);
			for (int i = 0; i < listPollutantGasResultSet.size(); i++) {
				PdfPCell resultSetCell1 = new PdfPCell(new Phrase(propertyLang.getProperty("cop.resultset.pdf.creatindate") + (i + 1) + ":  " + dateFormat.format(listPollutantGasResultSet.get(i).getCreationDate()), getFont(10, Font.NORMAL)));
				makeBorderExpRight(resultSetCell1);
				resultSetCell1.setColspan(1);
				resultSetCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				resultSetCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				resultSetCell1.setPaddingLeft(8);
				resultSetCell1.setPaddingBottom(5);
				if ((i + 1) == 3) {
					makeBorderBold(resultSetCell1);
				}
				mainTable3.addCell(resultSetCell1);
			}
			for (int i = 0; i < 3 - listPollutantGasResultSet.size(); i++) {
				PdfPCell resultSetCell1 = new PdfPCell(new Phrase(propertyLang.getProperty("cop.resultset.pdf.creatindate") + (i + 1 + listPollutantGasResultSet.size()) + ":  ", getFont(10, Font.NORMAL)));
				if ((i + 1 + listPollutantGasResultSet.size()) == 3) {
					makeBorderBold(resultSetCell1);
				} else
					makeBorderExpRight(resultSetCell1);
				resultSetCell1.setColspan(1);
				resultSetCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				resultSetCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				resultSetCell1.setPaddingLeft(8);
				resultSetCell1.setPaddingBottom(5);
				mainTable3.addCell(resultSetCell1);
			}
			masterTable.addCell(mainTable3);
			// table to set result set update date ENDS----------

			// table to set result set update date STARTS----------
			PdfPTable mainTable4 = new PdfPTable(3);
			mainTable4.setWidthPercentage(100);
			mainTable4.setSpacingBefore(10);
			String testObd = "";
			if (resultSet.getObdTest() != null) {
				testObd = resultSet.getObdTest();
			}
			String benchcell = "";
			if (resultSet.getBenchCell() != null) {
				benchcell = resultSet.getBenchCell();
			}
			PdfPCell testOBD = new PdfPCell(new Phrase(propertyLang.getProperty("cop.resultset.pdf.testobd") + "    " + testObd, getFont(10, Font.NORMAL)));
			makeBorderExpRight(testOBD);
			testOBD.setColspan(1);
			testOBD.setHorizontalAlignment(Element.ALIGN_LEFT);
			testOBD.setVerticalAlignment(Element.ALIGN_MIDDLE);
			testOBD.setPaddingLeft(8);
			mainTable4.addCell(testOBD);

			PdfPCell cellule = new PdfPCell(new Phrase(propertyLang.getProperty("cop.resultset.pdf.benchcell") + "    " + benchcell, getFont(10, Font.NORMAL)));

			makeBorderExpRight(cellule);
			cellule.setLeft(10);
			cellule.setColspan(1);
			cellule.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellule.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellule.setPaddingLeft(8);
			cellule.setPaddingBottom(5);
			mainTable4.addCell(cellule);
			String vehFileStatDecision = "";
			if (vehiclefileObj.getStatisticalDecision() != null) {
				vehFileStatDecision = vehiclefileObj.getStatisticalDecision();
			}
			PdfPCell decisionGlobal = new PdfPCell(new Phrase(propertyLang.getProperty("cop.resultset.pdf.decisionglobal") + "    " + vehFileStatDecision, getFont(10, Font.NORMAL)));
			makeBorderBold(decisionGlobal);
			decisionGlobal.setColspan(1);
			decisionGlobal.setHorizontalAlignment(Element.ALIGN_LEFT);
			decisionGlobal.setVerticalAlignment(Element.ALIGN_MIDDLE);
			decisionGlobal.setPaddingLeft(8);
			mainTable4.addCell(decisionGlobal);

			masterTable.addCell(mainTable4);

			// table to set result set update date ENDS----------
			// table to set pollutant values STARTS----------
			PdfPTable mainTableValues = new PdfPTable(4);
			mainTableValues.setWidthPercentage(100);
			mainTableValues.getDefaultCell().setBorder(PdfPCell.BOX);
			mainTableValues.setSpacingBefore(10);
			PdfPCell pollutantLabel = new PdfPCell(new Phrase(propertyLang.getProperty("cop.resultset.pdf.pollutant")));
			pollutantLabel.setColspan(1);
			setHeaderCellAlignment(pollutantLabel);
			mainTableValues.addCell(pollutantLabel);

			PdfPCell result = new PdfPCell(new Phrase(propertyLang.getProperty("cop.resultset.pdf.result")));
			result.setPadding(10);
			result.setColspan(1);
			setHeaderCellAlignment(result);
			mainTableValues.addCell(result);

			PdfPCell limit = new PdfPCell(new Phrase(propertyLang.getProperty("cop.resultset.pdf.limit")));
			limit.setColspan(1);
			setHeaderCellAlignment(limit);
			mainTableValues.addCell(limit);

			PdfPCell decision = new PdfPCell(new Phrase(propertyLang.getProperty("cop.resultset.pdf.decision")));

			decision.setColspan(1);
			setHeaderCellAlignment(decision);
			mainTableValues.addCell(decision);

			// table to set pollutant values ENDS----------

			// table to set pollutant data STARTS----------

			for (int i = 0; i < listPollutantGasTestResult.size(); i++) {
				if ((listPollutantGasTestResult.get(i).getTvvValuedPollGasLimit().getPgLabel().getLabel() == Constants.PGLABEL_CO2) && (vehiclefileObj.getVehicle().getTechnicalCase().getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule() != null)
						&& (vehiclefileObj.getVehicle().getTechnicalCase().getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule().getLabel() == Constants.STATISTICAL_CALC_RULE_WLTP1)) {

					listPollutantGasTestResult.get(i).getTvvValuedPollGasLimit().setMaxDValue((double) vehiclefileObj.getVehicle().getmCO2I());

				}
				PdfPCell pollutantLabelVal = new PdfPCell(new Phrase(listPollutantGasTestResult.get(i).getTvvValuedPollGasLimit().getPgLabel().getLabel()));
				pollutantLabelVal.setColspan(1);
				pollutantLabelVal.setHorizontalAlignment(Element.ALIGN_LEFT);
				pollutantLabelVal.setPaddingLeft(7);
				pollutantLabelVal.setVerticalAlignment(Element.ALIGN_MIDDLE);
				mainTableValues.addCell(pollutantLabelVal);
				String testResultValue = "";
				if (listPollutantGasTestResult.get(i).getValue() != null)
					testResultValue = listPollutantGasTestResult.get(i).getValue().replace(".", ",");
				PdfPCell resultVal = new PdfPCell(new Phrase(testResultValue, getFont(12, Font.BOLD)));
				resultVal.setColspan(1);
				resultVal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				resultVal.setVerticalAlignment(Element.ALIGN_MIDDLE);
				resultVal.setPaddingRight(7);
				mainTableValues.addCell(resultVal);
				String maxDVal = "";
				if (listPollutantGasTestResult.get(i).getTvvValuedPollGasLimit().getMaxDValue() != null)
					maxDVal = listPollutantGasTestResult.get(i).getTvvValuedPollGasLimit().getMaxDValue().toString().replace(".", ",");
				PdfPCell limitVal = new PdfPCell(new Phrase(maxDVal, getFont(12, Font.BOLD)));

				limitVal.setColspan(1);
				limitVal.setHorizontalAlignment(Element.ALIGN_RIGHT);
				limitVal.setVerticalAlignment(Element.ALIGN_MIDDLE);
				limitVal.setPaddingRight(7);
				mainTableValues.addCell(limitVal);

				PdfPCell decisionVal = new PdfPCell(new Phrase(listPollutantGasTestResult.get(i).getStatisticalDecision(), getFont(12, Font.BOLD)));
				decisionVal.setColspan(1);
				setHeaderCellAlignment(decisionVal);
				mainTableValues.addCell(decisionVal);
			}
			masterTable.addCell(mainTableValues);
			// table to set pollutant data ENDS----------
			document.add(masterTable);

			document.close();
		} catch (DocumentException e) {
			logger.error("Document Exception , {}", e);
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException Exception , {}", e);
		}

	}

	/**
	 * Sets the header cell alignment.
	 * 
	 * @param cell the new header cell alignment
	 */
	private static void setHeaderCellAlignment(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	}

	/**
	 * Gets the font.
	 * 
	 * @param size the size
	 * @param characterStyle the character style
	 * @return the font
	 */
	private static void makeBorderExpRight(PdfPCell cell) {
		cell.setBorderWidthLeft(1);
		cell.setBorderWidthBottom(1);
		cell.setBorderWidthTop(1);
		cell.setBorderWidthRight(0);
	}

	/**
	 * Gets the font.
	 * 
	 * @param size the size
	 * @param characterStyle the character style
	 * @return the font
	 */
	private static Font getFont(int size, int characterStyle) {
		return new Font(FontFamily.HELVETICA, size, characterStyle);
	}

	/**
	 * make border bold
	 * 
	 * @param PdfPCell
	 */
	private static void makeBorderBold(PdfPCell cell) {
		cell.setBorderWidthLeft(1);
		cell.setBorderWidthBottom(1);
		cell.setBorderWidthTop(1);
		cell.setBorderWidthRight(1);
	}

	/**
	 * Sets the vehicle file values.
	 *
	 * @param hm the hm
	 * @param vf the vf
	 * @param resultSet the result set
	 * @return the linked hash map
	 */
	private static LinkedHashMap<String, String> setVehicleFileValues(LinkedHashMap<String, String> hm, VehicleFile vf, PollutantGasResultSet resultSet) {
		String prepResultValue = "";
		String engineNum = "";
		String prNum = "";
		String cataFapConcat = "";
		String typeApprovalArea = "";
		String inertia = "";
		String userName = "";

		if (resultSet.getVehicleFile().getPreparationFile() != null && resultSet.getVehicleFile().getPreparationFile().getUserPrepFile() != null && resultSet.getVehicleFile().getPreparationFile().getUserPrepFile().getFirstName() != null) {
			userName = resultSet.getVehicleFile().getPreparationFile().getUserPrepFile().getFirstName();
		}

		hm.put(propertyLang.getProperty("cop.resultset.pdf.username"), userName);

		if (vf.getVehicle().getTechnicalCase().getTechnicalGroup() != null && vf.getVehicle().getTechnicalCase().getTechnicalGroup().getRegulationGroup() != null && vf.getVehicle().getTechnicalCase().getTechnicalGroup().getRegulationGroup().getTypeApprovalArea() != null) {
			typeApprovalArea = vf.getVehicle().getTechnicalCase().getTechnicalGroup().getRegulationGroup().getTypeApprovalArea().getLabel();

		}
		hm.put(propertyLang.getProperty("cop.resultset.pdf.typeapproval"), typeApprovalArea);

		if (vf.getVehicle().getChasisNumber() != null) {
			hm.put(propertyLang.getProperty("cop.resultset.pdf.chassisnum"), vf.getVehicle().getChasisNumber());
		} else
			hm.put(propertyLang.getProperty("cop.resultset.pdf.chassisnum"), "");
		hm.put(propertyLang.getProperty("cop.resultset.pdf.ems"), vf.getVehicle().getTechnicalCase().getEmissionStandard().getEsLabel() + " " + vf.getVehicle().getTechnicalCase().getEmissionStandard().getVersion());
		if (vf.getVehicle().getTechnicalCase().getTvv().getProjectCodeFamily() != null) {
			hm.put(propertyLang.getProperty("cop.resultset.pdf.familylabel"), vf.getVehicle().getTechnicalCase().getTvv().getProjectCodeFamily().getProjectCodeLabel());
		} else {
			hm.put(propertyLang.getProperty("cop.resultset.pdf.familylabel"), "");
		}
		if (vf.getVehicle().getTechnicalCase().getTvv().getTvvValuedCoastDown() != null && vf.getVehicle().getTechnicalCase().getTvv().getTvvValuedCoastDown().getInertia() != null) {
			inertia = new StringBuilder().append(vf.getVehicle().getTechnicalCase().getTvv().getTvvValuedCoastDown().getInertia().getValue()).toString();

		}
		hm.put(propertyLang.getProperty("cop.resultset.pdf.inertie"), inertia);
		hm.put(propertyLang.getProperty("cop.resultset.pdf.tvv"), vf.getVehicle().getTechnicalCase().getTvv().getLabel());
		if (vf.getVehicle().getTechnicalCase().getTvv().getTvvValuedCoastDown() != null) {
			hm.put("Loi de route:", vf.getVehicle().getTechnicalCase().getTvv().getTvvValuedCoastDown().getpSAReference());
		} else
			hm.put(propertyLang.getProperty("cop.resultset.pdf.coastdown"), "");
		hm.put(propertyLang.getProperty("cop.resultset.pdf.enginelabel"), vf.getVehicle().getTechnicalCase().getTvv().getEngine().getEngineLabel());
		hm.put(propertyLang.getProperty("cop.resultset.pdf.fuellabel"), vf.getVehicle().getTechnicalCase().getTvv().getFuel().getLabel());
		String co2LimitValue = "";
		for (PollutantGasTestResult testResult : resultSet.getPollutantGasTestResult()) {
			if (testResult.getTvvValuedPollGasLimit().getPgLabel().getLabel().equalsIgnoreCase(Constants.PGLABEL_CO2)) {

				if ((vf.getVehicle().getTechnicalCase().getTechnicalGroup() != null && vf.getVehicle().getTechnicalCase().getTechnicalGroup().getRegulationGroup() != null && vf.getVehicle().getTechnicalCase().getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule() != null)
						&& (vf.getVehicle().getTechnicalCase().getTechnicalGroup().getRegulationGroup().getStatisticalCalculationRule().getLabel().equalsIgnoreCase(Constants.STATISTICAL_CALC_RULE_WLTP1))) {
					co2LimitValue = vf.getVehicle().getmCO2I().toString();
				} else if (testResult.getTvvValuedPollGasLimit().getMaxDValue() != null) {
					co2LimitValue = testResult.getTvvValuedPollGasLimit().getMaxDValue().toString();
				}
				break;
			}
		}
		hm.put(propertyLang.getProperty("cop.resultset.pdf.limit.co2"), co2LimitValue);
		hm.put(propertyLang.getProperty("cop.resultset.pdf.gearbox.label"), vf.getVehicle().getTechnicalCase().getTvv().getGearBox().getLabel());
		if (vf.getPreparationFile() != null) {
			for (PreparationCheckList pcl : vf.getPreparationFile().getPreparationCheckList()) {
				for (PreparationResult pr : pcl.getPreparationResultList()) {
					if ((Constants.PREPRESULT_LABEL_KM).equalsIgnoreCase(pr.getLabel()) && pr.getValue() != null) {
						prepResultValue = pr.getValue();
					}
					if ((Constants.PREPRESULT_LABEL_ENGINE).equalsIgnoreCase(pr.getLabel()) && pr.getValue() != null) {
						engineNum = pr.getValue();
					}
					if ((Constants.PREPRESULT_LABEL_PR).equalsIgnoreCase(pr.getLabel()) && pr.getValue() != null) {
						prNum = pr.getValue();
					}
					if ((Constants.PREPRESULT_LABEL_CATA).equalsIgnoreCase(pr.getLabel()) && pr.getValue() != null) {
						cataFapConcat = cataFapConcat + " " + pr.getValue();
					}
					if ((Constants.PREPRESULT_LABEL_FAP).equalsIgnoreCase(pr.getLabel()) && pr.getValue() != null) {
						cataFapConcat = cataFapConcat + " " + pr.getValue();
					}

				}
			}
		}

		hm.put(propertyLang.getProperty("cop.resultset.pdf.prepresult.kmlabel.value"), prepResultValue);
		if (vf.getVehicle().getCarfactory() != null) {
			hm.put(propertyLang.getProperty("cop.resultset.pdf.factory.label"), vf.getVehicle().getCarfactory().getLabel());
		} else
			hm.put(propertyLang.getProperty("cop.resultset.pdf.factory.label"), "");

		String tvvLabel = vf.getVehicle().getTechnicalCase().getTvv().getLabel();

		String indice = "";
		for (int i = 0; i < tvvLabel.length(); i++) {
			if (i == 3 || i == 4 || i == 5) {
				indice = indice + tvvLabel.charAt(i);
			}

		}

		hm.put(propertyLang.getProperty("cop.resultset.pdf.tvvlabel.concatval"), indice);
		if (vf.getVehicle().getTechnicalCase().getTvv().getBodyWork() != null) {
			hm.put("Silhouette:", vf.getVehicle().getTechnicalCase().getTvv().getBodyWork().getLabel());
		} else
			hm.put(propertyLang.getProperty("cop.resultset.pdf.bodywork"), "");
		hm.put(propertyLang.getProperty("cop.resultset.pdf.prepresult.moturelabel.value"), engineNum);
		hm.put(propertyLang.getProperty("cop.resultset.pdf.typeoftest.label"), vf.getTypeOfTest().getLabel());
		hm.put(propertyLang.getProperty("cop.resultset.pdf.prepresult.prlabel.value"), prNum);
		hm.put(propertyLang.getProperty("cop.resultset.pdf.country.label"), "");
		hm.put(propertyLang.getProperty("cop.resultset.pdf.prepresult.caoncatlabel"), cataFapConcat);
		hm.put(propertyLang.getProperty("cop.resultset.pdf.modelyear"), vf.getVehicle().getModelYear());
		return hm;
	}
}
