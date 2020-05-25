/*
 * Author: U224106
 * Creation: 3 déc. 2014
 */
package com.inetpsa.oaz00.ws.report.services.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.inetpsa.oaz00.ws.report.services.ReportService;
import com.inetpsa.oaz00.ws.report.utils.ReportRowHeights;
import com.inetpsa.oaz00.ws.report.utils.ReportStyles;
import com.inetpsa.oaz00.ws.report.utils.ReportUtility;
import com.inetpsa.oaz00.ws.report.utils.SpecITReportLabelsEnum;
import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.services.SciConnectorAbstractService;
import com.inetpsa.oaz00.ws.scilab.utils.SciObjectInputAdapter;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.LawType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelType;

/**
 * The Class Spec it report service implementation.
 * 
 * @author U224106
 */
public class SpecITReportServiceImpl implements ReportService {

    /** The Constant NB_COL. */
    private final static int NB_COL = 11;

    /** The Constant LABELS_ENUM_CLASSNAME. */
    private final static String LABELS_ENUM_CLASSNAME = SpecITReportLabelsEnum.class.getName();

    /** The mother list. */
    private List<SciMotherRequirementType> motherList;

    /** The execution date. */
    private Date executionDate;

    /** The graph path prefix. */
    private String graphPathPrefix;

    /** The nb iter. */
    private int nbIter;

    /** The author. */
    private String author;

    /**
     * Instantiates a new spec it report service impl.
     * 
     * @param pMotherList the mother list
     * @param pExecutionDate the execution date
     * @param graphPathPrefix the graph path prefix
     * @param nbIter the nb iter
     * @param userId the user id
     */
    public SpecITReportServiceImpl(List<SciMotherRequirementType> pMotherList, String graphPathPrefix, int nbIter, Date pExecutionDate, String userId) {
        super();
        this.motherList = pMotherList;
        this.executionDate = pExecutionDate;
        this.graphPathPrefix = graphPathPrefix;
        this.nbIter = nbIter;
        this.author = userId;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.oaz00.ws.report.services.ReportService#generateReport()
     */
    public String generateReport() throws IOException {

        XSSFWorkbook wb = new XSSFWorkbook();
        POIXMLProperties xmlProps = wb.getProperties();
        POIXMLProperties.CoreProperties coreProps = xmlProps.getCoreProperties();
        coreProps.setCategory("OASIS");
        coreProps.setCreator(author);
        coreProps.setCreated(executionDate.toString());
        coreProps.setModified(executionDate.toString());
        coreProps.setKeywords("TOLERANCE INTERVAL SPECIFICATION SIMULATION");

        // ########### PARAM SHEET ###########
        ReportUtility.generateParameterSheet(wb, CalculationType.SPEC_IT);

        // ########### SUMMARY SHEET ###########
        XSSFSheet summarySheet = ReportUtility.getSheet(wb, ReportUtility.SUMMARY_SHEET);

        // Generate the Standard Report Header
        int rowIdx = ReportUtility.generateSheetHeader(summarySheet, NB_COL, true, true, LABELS_ENUM_CLASSNAME, executionDate);

        // Generate the Requirements Tree Section
        Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
        styles.put("rowHeaderStyle", ReportUtility.createCellStyle(summarySheet.getWorkbook(), ReportStyles.ROW_HEADER, true, true, true, true));
        styles.put("firstColHeaderStyle", ReportUtility.createCellStyle(summarySheet.getWorkbook(), ReportStyles.COL_HEADER, true, true, true, false));
        styles.put("colHeaderStyle", ReportUtility.createCellStyle(summarySheet.getWorkbook(), ReportStyles.COL_HEADER, true, false, true, false));
        styles.put("lastColHeaderStyle", ReportUtility.createCellStyle(summarySheet.getWorkbook(), ReportStyles.COL_HEADER, true, false, true, true));
        styles.put("motherStyle", ReportUtility.createCellStyle(summarySheet.getWorkbook(), ReportStyles.BOLD, true, true, true, true));
        styles.put("referenceStyle", ReportUtility.createCellStyle(summarySheet.getWorkbook(), ReportStyles.REFERENCE, true, true, true, true));
        styles.put("contributorStyle", ReportUtility.createCellStyle(summarySheet.getWorkbook(), ReportStyles.DEFAULT, true, true, true, true));
        styles.put("contributorCodeStyle", ReportUtility.createCellStyle(summarySheet.getWorkbook(), ReportStyles.HIGHLIGHT, true, true, true, true));

        // #### 'Requirement structure' title ####
        rowIdx = ReportUtility.createSection(summarySheet, rowIdx, NB_COL, LABELS_ENUM_CLASSNAME, "TREE");

        // #### Top border row ####
        XSSFRow row = ReportUtility.createRow(summarySheet, rowIdx++, NB_COL, false, ReportRowHeights.DEFAULT);
        for (int colIndex = 1; colIndex < NB_COL - 1; colIndex++) {
            XSSFCellStyle cellStyle = ReportUtility.createCellStyle(wb, ReportStyles.DEFAULT, true, colIndex == 1, false, colIndex == NB_COL - 2);
            row.createCell(colIndex).setCellStyle(cellStyle);
        }

        // #### Prepare the number of rows to create: two rows per requirements - 1 ####
        rowIdx = ReportUtility.generateRequirementTree(summarySheet, rowIdx++, 4, NB_COL, motherList, new HashMap<String, XSSFCellStyle>(),
                new ArrayList<Integer>());

        // #### Bottom border row ####
        row = ReportUtility.createRow(summarySheet, rowIdx++, NB_COL, true, ReportRowHeights.DEFAULT);
        for (int colIndex = 1; colIndex < NB_COL - 1; colIndex++) {
            XSSFCellStyle cellStyle = ReportUtility.createCellStyle(wb, ReportStyles.DEFAULT, true, false, true, false);
            row.createCell(colIndex).setCellStyle(cellStyle);
        }

        // ########### REQUIREMENT SHEETS ###########
        for (SciMotherRequirementType sciMotherRequirement : motherList) {
            // We first perform formula and contributors replacement.
            sciMotherRequirement = ReportUtility.performFormulaReplacement(sciMotherRequirement, false);
            // And finally create Requirement sheet.
            // ########### SUMMARY SHEET ###########
            XSSFSheet resultSheet = ReportUtility.getSheet(wb, SciConnectorAbstractService.normalizeFileName(sciMotherRequirement.getCode()));
            wb.setSheetOrder(SciConnectorAbstractService.normalizeFileName(sciMotherRequirement.getCode()), 2);

            // Generate the Standard Report Header
            rowIdx = ReportUtility.generateSheetHeader(resultSheet, NB_COL, false, false, LABELS_ENUM_CLASSNAME, executionDate);
            generateResultsSection(resultSheet, sciMotherRequirement, rowIdx++, styles);
        }

        // First sheet active and PARAM SHEET hidden
        wb.getCreationHelper().createFormulaEvaluator().evaluateAll();

        for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
            XSSFSheet xsheet = wb.getSheetAt(sheetNum);
            xsheet.setSelected(false);
            xsheet.enableLocking();
        }

        wb.setActiveSheet(1);
        // Change Hidden statut to Workbook.SHEET_STATE_VERY_HIDDEN to avoid sheet selection issue in interactive Excel UI
        // wb.setSheetHidden(0, Workbook.SHEET_STATE_HIDDEN);
        wb.setSheetHidden(0, Workbook.SHEET_STATE_VERY_HIDDEN);
        wb.setSelectedTab(1);
        wb.lockStructure();

        // Write the output to a file
        String reportPath = ReportUtility.getRepositoryPath(CalculationType.SPEC_IT, ReportUtility.REPORT, executionDate)
                + ReportUtility.formateReportDate(executionDate) + ReportUtility.REPORT_EXTENSION;
        FileOutputStream fileOut = new FileOutputStream(reportPath);
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();

        return reportPath;
    }

    /**
     * Generate results section.
     * 
     * @param sheet the sheet
     * @param motherReq the mother req
     * @param rowIdx the row idx
     * @param styles the styles
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void generateResultsSection(XSSFSheet sheet, SciMotherRequirementType motherReq, int rowIdx, Map<String, XSSFCellStyle> styles)
            throws IOException {

        int rowIndex = rowIdx;
        // #### Distribution Parameters Styles ####
        Map<ReportUtility.ParamType, XSSFCellStyle> paramStyles = new HashMap<ReportUtility.ParamType, XSSFCellStyle>();
        paramStyles.put(ReportUtility.ParamType.ALPHA,
                ReportUtility.createParCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, ReportUtility.ParamType.ALPHA));
        paramStyles.put(ReportUtility.ParamType.BETA,
                ReportUtility.createParCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, ReportUtility.ParamType.BETA));
        paramStyles.put(ReportUtility.ParamType.LAMBDA,
                ReportUtility.createParCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, ReportUtility.ParamType.LAMBDA));
        paramStyles.put(ReportUtility.ParamType.MU,
                ReportUtility.createParCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, ReportUtility.ParamType.MU));
        paramStyles.put(ReportUtility.ParamType.SIGMA,
                ReportUtility.createParCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, ReportUtility.ParamType.SIGMA));
        paramStyles.put(ReportUtility.ParamType.N,
                ReportUtility.createParCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, ReportUtility.ParamType.N));
        paramStyles.put(ReportUtility.ParamType.P,
                ReportUtility.createParCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, ReportUtility.ParamType.P));

        // #### 'Numerical result' title ####
        rowIndex = ReportUtility.createSection(sheet, rowIndex, NB_COL, LABELS_ENUM_CLASSNAME, "RESULTS");

        // #### 'Free Play' Part ####
        rowIndex = generateMotherRequirementBlock(sheet, motherReq, styles, rowIndex++);

        // #### Formula ####
        rowIndex = ReportUtility.generateCompleteFormulaBlock(sheet, rowIndex, NB_COL, motherReq, LABELS_ENUM_CLASSNAME, styles);

        // #### Contributor list ####
        rowIndex = generateContributorList(sheet, motherReq.getContributorList(), styles, paramStyles, rowIndex);
        ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.DEFAULT);

        // #### Distribution plot ####
        rowIndex = generatePictureSection(sheet, motherReq, rowIndex);

        ReportUtility.createRow(sheet, rowIndex, NB_COL, true, ReportRowHeights.DEFAULT);
    }

    /**
     * Generate mother requirement block.
     * 
     * @param sheet the sheet
     * @param motherReq the mother req
     * @param styles the styles
     * @param rowIdx the row idx
     * @return the int
     */
    private int generateMotherRequirementBlock(XSSFSheet sheet, SciMotherRequirementType motherReq, Map<String, XSSFCellStyle> styles, int rowIdx) {

        int rowIndex = rowIdx;
        // #### Mother Requirement details ####
        // ## Column headers ##
        ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_HEADER);
        // 'Customer Requirement' column
        ReportUtility.createMergedCells(sheet, rowIndex - 1, rowIndex - 1, 1, 2, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "PRESTATION"), true);
        // 'Designation' column
        ReportUtility.createMergedCells(sheet, rowIndex - 1, rowIndex - 1, 3, 9, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "DESIGNATION"), true);
        // ## Values ##
        ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_VALUE);
        // 'Customer Requirement' column
        ReportUtility.createMergedCells(sheet, rowIndex - 1, rowIndex - 1, 1, 2, styles.get("referenceStyle"), motherReq.getCode(), false);
        // 'Designation' column
        ReportUtility.createMergedCells(sheet, rowIndex - 1, rowIndex - 1, 3, 9, styles.get("referenceStyle"), motherReq.getTitle(), false);

        // ## Objectives ##
        ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_HEADER);
        // 'Objectives' column
        ReportUtility.createMergedCells(sheet, rowIndex - 1, rowIndex - 1, 1, 5, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "OBJECTIVES"), true);
        // 'Optimized values' column
        ReportUtility.createMergedCells(sheet, rowIndex - 1, rowIndex - 1, 6, 9, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "OPTIMIZED_VALUES"), true);

        // ## Column headers ##
        int colIdx = 1;
        XSSFRow rowLabels = ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_HEADER);
        // 'Lower limit' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "LOW_VALUE"),
                styles.get("firstColHeaderStyle"));
        // 'Upper limit' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "UP_VALUE"),
                styles.get("colHeaderStyle"));
        // 'Cp' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "CAP"),
                styles.get("colHeaderStyle"));
        // 'Real off-centering' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "OFF_CENTERING"),
                styles.get("colHeaderStyle"));
        // 'PPM' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "PPM"),
                styles.get("lastColHeaderStyle"));
        // 'Cp' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "CAP"),
                styles.get("firstColHeaderStyle"));
        // 'Cpk' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "CPK"),
                styles.get("colHeaderStyle"));
        // 'Real off-centering' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "OFF_CENTERING"),
                styles.get("colHeaderStyle"));
        // 'PPM' column
        ReportUtility.createFormulaCell(rowLabels, colIdx, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "PPM"),
                styles.get("lastColHeaderStyle"));

        // ## Values ##
        colIdx = 1;
        XSSFRow rowValues = ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_VALUE);
        // 'Lower limit' column
        ReportUtility.createNumericCell(rowValues, colIdx++, motherReq.getValueInf(), styles.get("motherStyle"));
        // 'Upper limit' column
        ReportUtility.createNumericCell(rowValues, colIdx++, motherReq.getValueSup(), styles.get("motherStyle"));
        // 'Cp Objective' column
        displayObjective(rowValues, colIdx++, motherReq.getCapObjective(), styles.get("motherStyle"));
        // 'Real off-centering Objective' column
        displayObjective(rowValues, colIdx++, motherReq.getCenteringMaxObjective(), styles.get("motherStyle"));
        // 'PPM Objective' column
        displayObjective(rowValues, colIdx++, motherReq.getTncObjective(), styles.get("motherStyle"));
        // 'Cp' column
        ReportUtility.createNumericCell(rowValues, colIdx++, motherReq.getCap(), styles.get("motherStyle"));
        // 'Cpk' column
        ReportUtility.createNumericCell(rowValues, colIdx++, motherReq.getCpk(), styles.get("motherStyle"));
        // 'Real off-centering' column
        ReportUtility.createNumericCell(rowValues, colIdx++, motherReq.getCenteringMax(), styles.get("motherStyle"));
        // 'PPM' column
        ReportUtility.createNumericCell(rowValues, colIdx, motherReq.getTnc(), styles.get("motherStyle"));
        return rowIndex;
    }

    /**
     * Display objective.
     * 
     * @param row the row
     * @param colIdx the col idx
     * @param objective the objective
     * @param cellStyle the cell style
     */
    private void displayObjective(XSSFRow row, int colIdx, Double objective, XSSFCellStyle cellStyle) {
        if (objective == null || objective.isNaN())
            ReportUtility.createStringCell(row, colIdx, "-", cellStyle);
        else
            ReportUtility.createNumericCell(row, colIdx, objective, cellStyle);
    }

    /**
     * Generate contributor list.
     * 
     * @param sheet the sheet
     * @param contributorList the contributor list
     * @param styles the styles
     * @param paramStyles the param styles
     * @param rowIdx the row idx
     * @return the int
     */
    private int generateContributorList(XSSFSheet sheet, List<SciRequirementType> contributorList, Map<String, XSSFCellStyle> styles,
            Map<ReportUtility.ParamType, XSSFCellStyle> paramStyles, int rowIdx) {

        int rowIndex = rowIdx;
        // ## Objectives ##
        ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_HEADER);
        // 'Objectives' column
        ReportUtility.createMergedCells(sheet, rowIndex - 1, rowIndex - 1, 2, 7, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "OBJECTIVES"), true);
        // 'Optimized values' column
        ReportUtility.createMergedCells(sheet, rowIndex - 1, rowIndex - 1, 8, 9, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "OPTIMIZED_VALUES"), true);

        // ## Column header ##
        int colIdx = 1;
        XSSFRow rowLabels = ReportUtility.createRow(sheet, rowIndex, NB_COL, false, ReportRowHeights.TABLE_HEADER);
        // 'Contributor' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "CONTRIBUTOR"),
                styles.get("rowHeaderStyle"));
        // 'Designation' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "DESIGNATION"),
                styles.get("firstColHeaderStyle"));
        // 'Lower limit' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "LOW_VALUE"),
                styles.get("colHeaderStyle"));
        // 'Upper limit' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "UP_VALUE"),
                styles.get("colHeaderStyle"));
        // 'Distribution' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "DISTRIBUTION"),
                styles.get("colHeaderStyle"));
        // 'Parameter 1' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "PARAM_1"),
                styles.get("colHeaderStyle"));
        // 'Parameter 2' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "PARAM_2"),
                styles.get("lastColHeaderStyle"));
        // 'Lower limit' column
        ReportUtility.createFormulaCell(rowLabels, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "LOW_VALUE"),
                styles.get("firstColHeaderStyle"));
        // 'Upper limit' column
        ReportUtility.createFormulaCell(rowLabels, colIdx, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "UP_VALUE"),
                styles.get("lastColHeaderStyle"));

        // ## Column values ##
        for (Iterator<SciRequirementType> iterator = contributorList.iterator(); iterator.hasNext();) {
            SciRequirementType contributor = iterator.next();
            rowIndex++;
            colIdx = 1;
            XSSFRow row = ReportUtility.createRow(sheet, rowIndex, NB_COL, false, ReportRowHeights.TABLE_VALUE);

            // 'Code' value
            ReportUtility.createStringCell(row, colIdx++, contributor.getCode(), styles.get("contributorCodeStyle"));
            // 'Designation' value
            ReportUtility.createStringCell(row, colIdx++, contributor.getTitle(), styles.get("contributorStyle"));
            // 'Lower limit objective' value
            ReportUtility.createNumericCell(row, colIdx++, contributor.getValueInfObjective(), styles.get("contributorStyle"));
            // 'Upper limit objective' value
            ReportUtility.createNumericCell(row, colIdx++, contributor.getValueSupObjective(), styles.get("contributorStyle"));
            // 'Distribution' value
            ReportUtility.createFormulaCell(row, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, contributor.getLaw().value()),
                    styles.get("contributorStyle"));
            // 'Parameter 1' value
            ReportUtility.createParameterCell(row, colIdx++, contributor.getLaw(), contributor.getParamLaw1(), 1, paramStyles);
            // 'Parameter 2' value
            ReportUtility.createParameterCell(row, colIdx++, contributor.getLaw(), contributor.getParamLaw2(), 2, paramStyles);
            // 'Lower limit' value
            ReportUtility.createNumericCell(row, colIdx++, contributor.getValueInf(), styles.get("contributorStyle"));
            // 'Upper limit' value
            ReportUtility.createNumericCell(row, colIdx, contributor.getValueSup(), styles.get("contributorStyle"));
        }
        return ++rowIndex;
    }

    /**
     * Generate picture section.
     * 
     * @param sheet the sheet
     * @param motherReq the mother req
     * @param rowIdx the row idx
     * @return the int
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private int generatePictureSection(XSSFSheet sheet, SciMotherRequirementType motherReq, int rowIdx) throws IOException {
        int rowIndex = rowIdx;
        // #### 'Numerical result' title ####
        rowIndex = ReportUtility.createSection(sheet, rowIndex, NB_COL, LABELS_ENUM_CLASSNAME, "DISTRIBUTION", "&B9&\" - \"&D9");

        rowIndex = rowIndex + 18;

        for (int i = rowIdx + 2; i < rowIndex; i++)
            ReportUtility.createRow(sheet, i, NB_COL, false, ReportRowHeights.DEFAULT);

        XSSFWorkbook wb = sheet.getWorkbook();

        boolean pictureSet = false;

        try {
            // FileInputStream obtains input bytes from the image file
            BufferedImage bufferedNuageImage = ImageIO.read(new File(graphPathPrefix + "_Prest_" + motherReq.getCalculationName() + "_IT"
                    + (nbIter - 1) + "_Nuage.gif"));
            BufferedImage bufferedHistoImage = ImageIO.read(new File(graphPathPrefix + "_Prest_" + motherReq.getCalculationName() + "_IT"
                    + (nbIter - 1) + "_Hist.gif"));

            // this writes the bufferedImage into a byte array called resultingBytes
            ByteArrayOutputStream byteArrayNuageOut = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayHistoOut = new ByteArrayOutputStream();
            ImageIO.write(bufferedNuageImage, "png", byteArrayNuageOut);
            ImageIO.write(bufferedHistoImage, "png", byteArrayHistoOut);
            // Get the contents of an OutputStream as a byte[].
            byte[] nuageBytes = byteArrayNuageOut.toByteArray();
            byte[] histoBytes = byteArrayHistoOut.toByteArray();

            // Adds a picture to the workbook
            int distributionNuagePlot = wb.addPicture(nuageBytes, Workbook.PICTURE_TYPE_JPEG);
            int distributionHistoPlot = wb.addPicture(histoBytes, Workbook.PICTURE_TYPE_JPEG);
            // close the input stream
            byteArrayNuageOut.close();
            byteArrayHistoOut.close();

            // Returns an object that handles instantiating concrete classes
            XSSFCreationHelper helper = wb.getCreationHelper();

            // Creates the top-level drawing patriarch.
            XSSFDrawing drawing = sheet.createDrawingPatriarch();

            // Create an anchor that is attached to the worksheet
            XSSFClientAnchor nuageAnchor = helper.createClientAnchor();
            XSSFClientAnchor histoAnchor = helper.createClientAnchor();
            // set top-left corner for the image
            nuageAnchor.setCol1(1);
            nuageAnchor.setRow1(rowIdx + 2);
            histoAnchor.setCol1(6);
            histoAnchor.setRow1(rowIdx + 2);

            // Creates a picture
            XSSFPicture nuage = drawing.createPicture(nuageAnchor, distributionNuagePlot);
            XSSFPicture histo = drawing.createPicture(histoAnchor, distributionHistoPlot);
            // Reset the image to the original size
            nuage.resize(0.87);
            histo.resize(0.87);
            pictureSet = true;
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        if (!pictureSet) {
            // FileInputStream obtains input bytes from the image file
            InputStream inputStream = ReportUtility.class.getResourceAsStream("/blank.png");
            // Get the contents of an InputStream as a byte[].
            byte[] bytes = IOUtils.toByteArray(inputStream);
            // Adds a picture to the workbook
            int logoPicIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            // close the input stream
            inputStream.close();

            // Returns an object that handles instantiating concrete classes
            XSSFCreationHelper helper = wb.getCreationHelper();

            // Creates the top-level drawing patriarch.
            XSSFDrawing drawing = sheet.createDrawingPatriarch();

            // Create an anchor that is attached to the worksheet
            XSSFClientAnchor anchor = helper.createClientAnchor();
            // set top-left corner for the image
            anchor.setCol1(3);
            anchor.setRow1(rowIdx + 2);

            // Creates a picture
            XSSFPicture pict = drawing.createPicture(anchor, logoPicIdx);
            // Reset the image to the original size
            pict.resize(0.65);
        }
        return rowIndex;
    }

    /**
     * The main method.
     * 
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SecurityException the security exception
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void main(String[] args) throws IOException, SecurityException, IllegalArgumentException {

        RequirementType motherRequirement1 = new RequirementType();
        motherRequirement1.setName("Y");
        motherRequirement1.setId("01");
        motherRequirement1.setTitle("Prestation 1");
        motherRequirement1.setCode("01A01");
        motherRequirement1.setReferenceRequirement(true);
        motherRequirement1.setTnc(new Double(0.5));
        motherRequirement1.setCpk(new Double(1.2));

        TransferModelType transferModel1 = new TransferModelType();
        transferModel1.setFormula("#C-1# + #C-2#*cosd(#C-3#) + #S-1#");
        transferModel1.setReportFormula("01A01_C1 + 01A01_C2*cosd(01A01_C3) + S-01A01");

        List<RequirementType> contributorList1 = new LinkedList<RequirementType>();
        RequirementType contributor1 = new RequirementType();

        contributor1.setName("C-1");
        contributor1.setId("11");
        contributor1.setCode("01A01_C1");
        contributor1.setValueInf(new Double(1.0));
        contributor1.setValueSup(new Double(1.0));
        contributor1.setLaw(LawType.NORMAL_STD);
        contributor1.setParamLaw1(new Double(0.0));
        contributor1.setParamLaw2(new Double(1.0));
        contributorList1.add(contributor1);

        contributor1 = new RequirementType();
        contributor1.setName("C-2");
        contributor1.setId("12");
        contributor1.setCode("01A01_C2");
        contributor1.setValueInf(new Double(1.0));
        contributor1.setValueSup(new Double(1.0));
        contributor1.setLaw(LawType.NORMAL_PLAGE);
        contributor1.setParamLaw1(new Double(0.0));
        contributor1.setParamLaw2(new Double(1.0));
        contributorList1.add(contributor1);

        contributor1 = new RequirementType();
        contributor1.setName("C-3");
        contributor1.setId("13");
        contributor1.setCode("01A01_C3");
        contributor1.setValueInf(new Double(1.0));
        contributor1.setValueSup(new Double(1.0));
        contributor1.setLaw(LawType.NORMAL_STD);
        contributor1.setParamLaw1(new Double(0.0));
        contributor1.setParamLaw2(new Double(1.0));
        contributorList1.add(contributor1);

        RequirementType motherRequirement2 = new RequirementType();
        motherRequirement2.setName("S-1");
        motherRequirement2.setId("101");
        motherRequirement2.setTitle("Sub Prestation 1");
        motherRequirement2.setCode("S-01A01");
        motherRequirement2.setReferenceRequirement(true);
        motherRequirement2.setTnc(new Double(0.5));
        motherRequirement2.setCpk(new Double(1.2));

        TransferModelType transferModel2 = new TransferModelType();
        transferModel2.setFormula("#C-2# + #C-5# / #C-6#");
        transferModel2.setReportFormula("01A01_C2 + 01A01_C5 / 01A01_C6");

        List<RequirementType> contributorList2 = new LinkedList<RequirementType>();
        RequirementType contributor2 = new RequirementType();

        contributor2.setName("C-2");
        contributor2.setId("12");
        contributor2.setCode("01A01_C2");
        contributor2.setValueInf(new Double(1.0));
        contributor2.setValueSup(new Double(1.0));
        contributor2.setLaw(LawType.NORMAL_PLAGE);
        contributor2.setParamLaw1(new Double(0.0));
        contributor2.setParamLaw2(new Double(1.0));
        contributorList2.add(contributor2);

        contributor2 = new RequirementType();
        contributor2.setName("C-5");
        contributor2.setId("15");
        contributor2.setCode("01A01_C5");
        contributor2.setValueInf(new Double(1.0));
        contributor2.setValueSup(new Double(1.0));
        contributor2.setLaw(LawType.NORMAL_STD);
        contributor2.setParamLaw1(new Double(0.0));
        contributor2.setParamLaw2(new Double(1.0));
        contributorList2.add(contributor2);

        contributor2 = new RequirementType();
        contributor2.setName("C-6");
        contributor2.setId("16");
        contributor2.setCode("01A01_C6");
        contributor2.setValueInf(new Double(1.0));
        contributor2.setValueSup(new Double(1.0));
        contributor2.setLaw(LawType.NORMAL_STD);
        contributor2.setParamLaw1(new Double(0.0));
        contributor2.setParamLaw2(new Double(1.0));
        contributorList2.add(contributor2);

        SciMotherRequirementType scilabMotherRequirement1 = SciObjectInputAdapter.createScilabMotherRequirementType(motherRequirement1,
                transferModel1, contributorList1);
        SciMotherRequirementType scilabMotherRequirement2 = SciObjectInputAdapter.createScilabMotherRequirementType(motherRequirement2,
                transferModel2, contributorList2);

        scilabMotherRequirement1.setPlotPath("C:\\TEMP\\oasis_tmp\\MonteCarlo_20141201-183206_Prest_UR-0000001.gif");

        List<SciMotherRequirementType> motherList = new ArrayList<SciMotherRequirementType>();
        motherList.add(scilabMotherRequirement1);
        motherList.add(scilabMotherRequirement2);

        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 2014);
        date.set(Calendar.MONTH, 11);
        date.set(Calendar.DAY_OF_MONTH, 2);
        date.set(Calendar.HOUR_OF_DAY, 19);
        date.set(Calendar.MINUTE, 49);
        date.set(Calendar.SECOND, 9);

        SpecITReportServiceImpl service = new SpecITReportServiceImpl(motherList, ReportUtility.getRepositoryPath(CalculationType.SPEC_IT,
                ReportUtility.PICTURE, date.getTime()) + ReportUtility.formateReportDate(date.getTime()), 2, date.getTime(), "SIBERT");
        System.out.println(service.generateReport());
    }
}
