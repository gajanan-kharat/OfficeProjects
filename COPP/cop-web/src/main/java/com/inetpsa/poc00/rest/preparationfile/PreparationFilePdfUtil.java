/*
 * Creation : Feb 20, 2017
 */
package com.inetpsa.poc00.rest.preparationfile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.preparationchecklist.PreparationCheckList;
import com.inetpsa.poc00.domain.preparationresult.PreparationResult;
import com.inetpsa.poc00.domain.vehiclefile.VehicleFile;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * The Class PreparationFilePdfUtil.
 */
public class PreparationFilePdfUtil {

	/** The bold_10. */
	static Font bold10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

	/** The bold_9. */
	static Font bold9 = new Font(Font.FontFamily.TIMES_ROMAN, 8.5f, Font.BOLD);

	/** The normal_10. */
	static Font normal10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

	/** The normal_ white. */
	static Font normalWhite = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.WHITE);

	/** The count. */
	static int count = 1;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(PreparationFileResource.class);

	/** The Constant DATE_PATTERN. */
	public static final String DATE_PATTERN = "dd/MM/yyyy";

	/**
	 * Creates our first table.
	 *
	 * @param vehicleFile the vehicle file
	 * @return our first table
	 */
	public static PdfPTable createFirstTable(PreparationFilePrintDto printDto) {

		PdfPTable table = new PdfPTable(6);
		table.setSplitLate(false);
		table.setWidthPercentage(100);

		// FIRST ROW -- STARTS
		table.addCell(getFirstRow());
		// FIRST ROW -- ENDS

		// SECOND ROW -- STARTS
		PdfPCell temp = new PdfPCell(getSecondRow(printDto));
		temp.setColspan(6);
		table.addCell(temp);
		// SECOND ROW -- ENDS

		// THIRD ROW -- STARTS
		PdfPCell temp2 = new PdfPCell(getThirdRow(printDto));
		temp2.setColspan(6);
		table.addCell(temp2);
		// THIRD ROW -- ENDS

		// FOURTH ROW -- STARTS
		PdfPCell temp3 = new PdfPCell(getFourthRow1());
		table.addCell(temp3);

		PdfPCell temp4 = new PdfPCell(new Phrase(printDto.getProjectFamilyLabel(), normal10));
		temp4.setColspan(4);
		setAlignment(temp4);
		table.addCell(temp4);
		// FOURTH ROW -- ENDS

		// FIFTH ROW -- STARTS
		Font normalBlue = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLUE);
		PdfPCell temp5 = new PdfPCell(new Phrase("N� VIN ", normalBlue));
		setAlignment(temp5);
		table.addCell(temp5);

		normalBlue = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLUE);
		PdfPCell temp6 = new PdfPCell(new Phrase(printDto.getVin(), normalBlue));
		temp6.setColspan(2);
		table.addCell(temp6);

		Font boldOrange = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.ORANGE);
		PdfPCell temp7 = new PdfPCell(new Phrase("TVV ", boldOrange));
		setAlignment(temp7);
		table.addCell(temp7);

		boldOrange = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.ORANGE);
		PdfPCell temp8 = new PdfPCell(new Phrase(printDto.getTvvLabel(), boldOrange));
		temp8.setColspan(2);
		table.addCell(temp8);
		// FIFTH ROW -- ENDS

		// SIXTH ROW -- STARTS
		PdfPCell temp9 = new PdfPCell(new Phrase("RELEVE TVV SUR PLAQUE CHASSIS VEHICULE AVEC VARIANTE", bold10));
		temp9.setColspan(6);
		table.addCell(temp9);
		// SIXTH ROW -- END

		// SEVENTH ROW -- STARTS
		getSeventhRow(table, printDto);
		// SEVENTH ROW -- ENDS

		// EIGHT ROW -- STARTS
		table.addCell(new Phrase("UP D'ORIGINE ", bold10));
		table.addCell(new Phrase(printDto.getVehicleFactory(), normal10));
		table.addCell(new Phrase("AM ", bold10));
		table.addCell(new Phrase(printDto.getModelYear(), normal10));
		table.addCell(new Phrase("Pays dest ", bold10));
		table.addCell(new Phrase(printDto.getDestinationCountry(), normal10));
		// EIGHT ROW -- ENDS

		// NINTH ROW -- STARTS
		for (PreparationCheckList pcl : printDto.getPreparationFile().getPreparationCheckList()) {
			PdfPCell prepListCell = new PdfPCell(getPreparationCells(pcl));
			prepListCell.setColspan(6);
			table.addCell(prepListCell);
		}
		// NINTH ROW -- ENDS

		// TENTH ROW -- STARTS
		// BLANK ROW -- COMPLETE BLACK ROW
		PdfPCell temp10 = new PdfPCell(new Phrase(" "));
		temp10.setBorderWidth(.75f);
		temp10.setBackgroundColor(BaseColor.BLACK);
		temp10.setColspan(6);
		table.addCell(temp10);
		// TENTH ROW -- ENDS

		// ELEVENTH ROW -- STARTS
		PdfPCell temp11 = new PdfPCell(getObservationRow(printDto));
		temp11.setBorderWidth(.75f);
		temp11.setColspan(6);
		table.addCell(temp11);
		// ELEVENTH ROW -- ENDS

		// TWELTH ROW -- STARTS
		PdfPCell temp12 = new PdfPCell(new Phrase(" "));
		temp12.setFixedHeight(10);
		temp12.setColspan(6);
		temp12.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
		table.addCell(temp12);
		// TWELTH ROW -- ENDS

		// Last ROW
		getLastRow(table, printDto);
		// LAST ROW

		return table;
	}

	/**
	 * Gets the fourth row1.
	 *
	 * @return the fourth row1
	 */
	public static PdfPCell getFourthRow1() {

		PdfPCell fourthRow = new PdfPCell(new Phrase("Type de vehicule ", bold10));
		setAlignment(fourthRow);
		fourthRow.setColspan(2);
		return fourthRow;
	}

	/**
	 * Gets the third row.
	 *
	 * @param printDto the vehicle file
	 * @return the third row
	 */
	public static PdfPTable getThirdRow(PreparationFilePrintDto printDto) {

		PdfPTable thirdRow = new PdfPTable(5);
		thirdRow.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		thirdRow.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

		try {
			thirdRow.setWidths(new float[] { 2.5f, 3f, 1.5f, 2f, 3f });
		} catch (DocumentException e) {
			logger.error("Error while setting Pdf Cell width", e);
		}

		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);

		thirdRow.addCell(new Phrase("PREPARATEUR ", bold10));

		thirdRow.addCell(new Phrase(printDto.getUserName(), normal10));
		thirdRow.addCell(new Phrase("Signature ", bold10));
		thirdRow.getDefaultCell().setBorderWidthLeft(0);
		thirdRow.addCell(new Phrase(" ", normal10));

		PdfPTable dateTable = new PdfPTable(2);
		dateTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		dateTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		dateTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

		dateTable.addCell(new Phrase("Pr�pa.", bold10));
		dateTable.addCell(new Phrase(formatter.format(new Date()), normal10));
		dateTable.addCell(new Phrase("Impr.", bold10));
		dateTable.addCell(new Phrase(formatter.format(new Date()), normal10));

		thirdRow.addCell(dateTable);

		return thirdRow;
	}

	/**
	 * Gets the second row.
	 *
	 * @param vehicleFile the vehicle file
	 * @return the second row
	 */
	public static PdfPTable getSecondRow(PreparationFilePrintDto printDto) {

		PdfPTable secondRow = new PdfPTable(6);

		Font normalWhite = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.WHITE);
		PdfPCell firstCol = new PdfPCell(new Phrase("Partie 0", normalWhite));
		setAlignment(firstCol);
		BaseColor myColor = new BaseColor(0, 0, 0);
		firstCol.setBackgroundColor(myColor);
		secondRow.addCell(firstCol);

		normalWhite = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
		PdfPCell secondCol = new PdfPCell(new Phrase("IDENTIFICATION DU VEHICULE", normalWhite));
		setAlignment(secondCol);
		secondCol.setColspan(3);
		myColor = new BaseColor(204, 255, 204);
		secondCol.setBackgroundColor(myColor);
		secondRow.addCell(secondCol);

		PdfPCell thirdCol = new PdfPCell(new Phrase(printDto.getVehicleIdentificaiton(), bold10));
		setAlignment(thirdCol);
		secondRow.addCell(thirdCol);

		PdfPCell fourthCol = new PdfPCell(new Phrase(printDto.getVehicleIdentificaiton()));
		setAlignment(fourthCol);
		secondRow.addCell(fourthCol);

		return secondRow;
	}

	/**
	 * Gets the first row.
	 *
	 * @return the first row
	 */
	public static PdfPCell getFirstRow() {

		PdfPCell firstRow = new PdfPCell(new Phrase("PREPARATION DES VEHICULES AU TEST D'EMISSIONS", bold10));
		firstRow.setHorizontalAlignment(Element.ALIGN_CENTER);
		firstRow.setVerticalAlignment(Element.ALIGN_MIDDLE);

		BaseColor myColor = new BaseColor(255, 255, 151);
		firstRow.setBackgroundColor(myColor);

		firstRow.setColspan(6);
		return firstRow;
	}

	/**
	 * Sets the alignment.
	 *
	 * @param cell the cell
	 * @return the pdf p cell
	 */
	public static PdfPCell setAlignment(PdfPCell cell) {
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		return cell;
	}

	/**
	 * Creates the empty space.
	 *
	 * @return the pdf p cell
	 */
	public static PdfPCell createEmptySpace() {
		PdfPCell emptyCell = new PdfPCell(new Phrase(" "));
		emptyCell.setBorder(0);
		return emptyCell;
	}

	/**
	 * Gets the observation row.
	 *
	 * @return the observation row
	 */
	public static PdfPTable getObservationRow(PreparationFilePrintDto printDto) {

		PdfPTable observation = new PdfPTable(2);
		observation.getDefaultCell().setBorderWidthRight(PdfPCell.NO_BORDER);

		try {
			observation.setWidths(new float[] { 0.8f, 4f });
		} catch (DocumentException e) {
			logger.error("Error while setting width of Cells in PDF", e);
		}

		observation.addCell(new Phrase("Observations : ", bold10));

		observation.getDefaultCell().setBorderWidthLeft(PdfPCell.NO_BORDER);
		observation.addCell(new Phrase(printDto.getPreparationListComments(), normal10));

		return observation;
	}

	/**
	 * Gets the seventh row.
	 *
	 * @param table the table
	 * @return the seventh row
	 */
	public static void getSeventhRow(PdfPTable table, PreparationFilePrintDto printDto) {
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(new Phrase("TYPE MOTEUR ", bold10));
		table.addCell(new Phrase(printDto.getEngineLable(), normal10));
		table.addCell(new Phrase("INDICE ", bold10));
		table.addCell(new Phrase(printDto.getIndice(), normal10));
		table.addCell(new Phrase("Type de BV ", bold10));
		table.addCell(new Phrase(printDto.getGearBoxLabel(), normal10));
	}

	/**
	 * Gets the last row.
	 *
	 * @param table the table
	 * @return the last row
	 */
	private static void getLastRow(PdfPTable table, PreparationFilePrintDto printDto) {

		PdfPTable lastRowTable = new PdfPTable(7);

		try {
			lastRowTable.setWidths(new float[] { 4f, .5f, .5f, 2f, .5f, .5f, 2f });
		} catch (DocumentException e) {
			logger.error("Error during setting width of Cells in PDF", e);
		}

		PdfPCell temp13 = new PdfPCell(new Phrase("DECISION FIN DE PREPARATION", bold10));
		temp13.setBackgroundColor(new BaseColor(255, 255, 151));
		setAlignment(temp13);
		lastRowTable.addCell(temp13);

		Image arrow = null;
		Image check = null;
		try {
			arrow = Image.getInstance("src/main/webapp/fragments/cop/images/Arrow.png");

			arrow.scaleAbsolute(50f, 50f);
			arrow.setAlignment(Image.MIDDLE);

			check = Image.getInstance("src/main/webapp/fragments/cop/images/Check.png");
			check.scaleAbsolute(50f, 50f);
			check.setAlignment(Image.MIDDLE);

		} catch (BadElementException | IOException e) {
			logger.error("Error Whilte loading images", e);
		}

		lastRowTable.getDefaultCell().setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.BOTTOM);
		setAlignment(lastRowTable.getDefaultCell());
		lastRowTable.addCell(arrow);

		lastRowTable.getDefaultCell().setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.BOTTOM | PdfPCell.TOP);
		setAlignment(lastRowTable.getDefaultCell());

		if (printDto.getIsPrepComplete()) {
			lastRowTable.addCell(check);
		} else {
			lastRowTable.addCell("");
		}

		setAlignment(lastRowTable.getDefaultCell());
		lastRowTable.getDefaultCell().setBackgroundColor(new BaseColor(204, 255, 204));
		lastRowTable.addCell(new Phrase("OK POUR TEST", bold10));

		lastRowTable.getDefaultCell().setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.BOTTOM);
		setAlignment(lastRowTable.getDefaultCell());
		lastRowTable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
		lastRowTable.addCell(arrow);

		lastRowTable.getDefaultCell().setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.BOTTOM | PdfPCell.TOP);
		setAlignment(lastRowTable.getDefaultCell());
		lastRowTable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);

		if (printDto.getIsInExpertise()) {
			lastRowTable.addCell(check);
		} else {
			lastRowTable.addCell("");
		}

		setAlignment(lastRowTable.getDefaultCell());
		lastRowTable.getDefaultCell().setBackgroundColor(BaseColor.RED);
		lastRowTable.addCell(new Phrase("EXPERTISE ", bold10));

		table.getDefaultCell().setColspan(6);
		table.getDefaultCell().setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.BOTTOM);
		table.addCell(lastRowTable);
	}

	/**
	 * Gets the preparation cells.
	 *
	 * @param pl2 the pl2
	 * @return the preparation cells
	 */
	private static PdfPTable getPreparationCells(PreparationCheckList pl2) {

		PdfPTable listTable = new PdfPTable(2);
		listTable.setSplitLate(false);

		try {
			listTable.setWidths(new float[] { 0.80f, 4f });
		} catch (DocumentException e) {
			logger.error("Error in setting Pdf Cell width ", e);
		}

		Font normalWhite = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.WHITE);
		PdfPCell firstCol = new PdfPCell(new Phrase("Partie " + count++, normalWhite));
		firstCol.setBorderWidth(.75f);
		setAlignment(firstCol);
		BaseColor myColor = new BaseColor(0, 0, 0);
		firstCol.setBackgroundColor(myColor);

		listTable.addCell(firstCol);

		PdfPCell secondCol = new PdfPCell(new Phrase(pl2.getLabel(), bold10));
		myColor = new BaseColor(204, 255, 204);
		secondCol.setBackgroundColor(myColor);
		secondCol.setBorderWidth(.75f);
		setAlignment(secondCol);
		listTable.addCell(secondCol);

		listTable.getDefaultCell().setColspan(2);
		listTable.addCell(getInnerTable(pl2.getPreparationResultList()));

		return listTable;
	}

	/**
	 * Gets the inner table.
	 *
	 * @param list the list
	 * @return the inner table
	 */
	private static PdfPTable getInnerTable(List<PreparationResult> list) {

		PdfPTable innerTable = new PdfPTable(2);
		try {
			innerTable.setWidths(new float[] { 1f, 2f });
		} catch (DocumentException e) {
			logger.error("Issue in Setting Pdf Cell width ", e);
		}

		innerTable.getDefaultCell().setBorder(0);

		setAlignment(innerTable.getDefaultCell());

		PdfPCell innerCell;

		int listSizeCheck = 1;

		for (PreparationResult pr : list) {

			innerTable.addCell(new Phrase("Label ", normal10));
			innerCell = new PdfPCell(new Phrase(pr.getLabel(), normal10));
			setAlignment(innerCell);
			innerTable.addCell(innerCell);

			innerTable.addCell("");
			innerTable.addCell("");

			innerTable.addCell(new Phrase("Value ", normal10));
			innerCell = new PdfPCell(new Phrase(pr.getValue(), normal10));
			setAlignment(innerCell);
			innerTable.addCell(innerCell);

			innerTable.addCell("");
			innerTable.addCell("");

			innerTable.addCell(new Phrase("Unit ", normal10));
			innerCell = new PdfPCell(new Phrase(pr.getUnit(), normal10));
			setAlignment(innerCell);
			innerTable.addCell(innerCell);

			if (listSizeCheck++ != list.size()) {
				innerTable.addCell(" ");
				innerTable.addCell(" ");
			}
		}

		return innerTable;
	}

	public static PreparationFilePrintDto preparePrintData(VehicleFile vehicleFile) {

		PreparationFilePrintDto printDto = new PreparationFilePrintDto();

		if (vehicleFile != null && vehicleFile.getVehicle() != null) {

			printDto.setChasisNumber(vehicleFile.getVehicle().getChasisNumber());
			printDto.setCounterMark(vehicleFile.getVehicle().getCounterMark());
			printDto.setRegistrationNumber(vehicleFile.getVehicle().getRegistrationNumber());
			printDto.setVin(vehicleFile.getVehicle().getVin());
			printDto.setModelYear(vehicleFile.getVehicle().getModelYear());

			if (Constants.VEHICLEFILESTATUS_EVACOP_COMPLETE.equalsIgnoreCase(vehicleFile.getVehicleFileStatus().getLabel()) || Constants.VEHICLEFILESTATUS_PREPARATIONCOMPLETE.equalsIgnoreCase(vehicleFile.getVehicleFileStatus().getLabel())) {
				printDto.setIsPrepComplete(Boolean.TRUE);
			} else if ("In expertise".equalsIgnoreCase(vehicleFile.getVehicleFileStatus().getLabel())) {
				printDto.setIsInExpertise(Boolean.TRUE);
			}

			if (vehicleFile.getVehicle().getDestinationCountry() != null) {
				printDto.setDestinationCountry(vehicleFile.getVehicle().getDestinationCountry().getLabel());
			}

			if (vehicleFile.getPreparationFile() != null) {
				printDto.setPreparationFile(vehicleFile.getPreparationFile());
			}

			if (vehicleFile.getVehicle().getCounterMark() != null) {
				printDto.setVehicleIdentificaiton(vehicleFile.getVehicle().getCounterMark());

			} else if (vehicleFile.getVehicle().getChasisNumber() != null) {
				printDto.setVehicleIdentificaiton(vehicleFile.getVehicle().getChasisNumber());

			} else if (vehicleFile.getVehicle().getRegistrationNumber() != null) {
				printDto.setVehicleIdentificaiton(vehicleFile.getVehicle().getRegistrationNumber());

			}

			if (vehicleFile.getVehicle().getTechnicalCase() != null && vehicleFile.getVehicle().getTechnicalCase().getTvv() != null) {

				printDto.setTvvLabel(vehicleFile.getVehicle().getTechnicalCase().getTvv().getLabel());

				if (vehicleFile.getVehicle().getTechnicalCase().getTvv().getProjectCodeFamily() != null) {
					printDto.setProjectFamilyLabel(vehicleFile.getVehicle().getTechnicalCase().getTvv().getProjectCodeFamily().getProjectCodeLabel() + " " + vehicleFile.getVehicle().getTechnicalCase().getTvv().getProjectCodeFamily().getVehicleFamilyLabel());
				}

				if (vehicleFile.getVehicle().getTechnicalCase().getTvv().getGearBox() != null) {
					printDto.setGearBoxLabel(vehicleFile.getVehicle().getTechnicalCase().getTvv().getGearBox().getLabel());
				}

				if (vehicleFile.getVehicle().getTechnicalCase().getTvv().getEngine() != null) {
					printDto.setEngineLable(vehicleFile.getVehicle().getTechnicalCase().getTvv().getEngine().getEngineLabel());
				}

				if (vehicleFile.getVehicle().getTechnicalCase().getTvv().getFactorySet() != null && !vehicleFile.getVehicle().getTechnicalCase().getTvv().getFactorySet().isEmpty()) {

					StringBuilder carfactoryLabel = new StringBuilder();

					Set<CarFactory> carFactory = vehicleFile.getVehicle().getTechnicalCase().getTvv().getFactorySet();
					for (CarFactory cf : carFactory) {
						carfactoryLabel.append(cf.getLabel()).append(" ");
					}

					printDto.setVehicleFactory(carfactoryLabel.toString());

				}

			}

		}

		if (vehicleFile != null && vehicleFile.getPreparationFile() != null && vehicleFile.getPreparationFile().getPreparationCheckList() != null) {

			StringBuilder comment = new StringBuilder();

			for (PreparationCheckList pcl : vehicleFile.getPreparationFile().getPreparationCheckList()) {
				for (PreparationResult pr : pcl.getPreparationResultList()) {
					comment.append(pr.getComment()).append(", ");
				}
			}

			printDto.setPreparationListComments(comment.toString());

		}

		return printDto;

	}

}
