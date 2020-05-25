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
import com.inetpsa.oaz00.ws.report.utils.MonteCarloReportLabelsEnum;
import com.inetpsa.oaz00.ws.report.utils.ReportRowHeights;
import com.inetpsa.oaz00.ws.report.utils.ReportStyles;
import com.inetpsa.oaz00.ws.report.utils.ReportUtility;
import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.services.SciConnectorAbstractService;
import com.inetpsa.oaz00.ws.scilab.utils.SciObjectInputAdapter;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.LawType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelType;

/**
 * The Class Monte carlo report service implementation.
 * 
 * @author U224106
 */
public class MonteCarloReportServiceImpl implements ReportService {

    /** The Constant NB_COL. */
    private final static int NB_COL = 10;

    /** The Constant LABELS_ENUM_CLASSNAME. */
    private final static String LABELS_ENUM_CLASSNAME = MonteCarloReportLabelsEnum.class.getName();

    /** The levels map. */
    private HashMap<Integer, List<SciMotherRequirementType>> levelsMap;

    /** The execution date. */
    private Date executionDate;

    /** The author. */
    private String author;

    /**
     * Instantiates a new monte carlo report service impl.
     * 
     * @param pExecutionDate the execution date
     * @param levelsMap the levels map
     * @param userId the user id
     */
    public MonteCarloReportServiceImpl(HashMap<Integer, List<SciMotherRequirementType>> levelsMap, Date pExecutionDate, String userId) {
        super();
        this.levelsMap = levelsMap;
        this.executionDate = pExecutionDate;
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
        coreProps.setKeywords("MONTE CARLO SIMULATION");

        // ########### PARAM SHEET ###########
        ReportUtility.generateParameterSheet(wb, CalculationType.MONTE_CARLO);

        // ########### SUMMARY SHEET ###########
        XSSFSheet summarySheet = ReportUtility.getSheet(wb, ReportUtility.SUMMARY_SHEET);

        // Generate the Standard Report Header
        int rowIdx = ReportUtility.generateSheetHeader(summarySheet, NB_COL, true, true, LABELS_ENUM_CLASSNAME, executionDate);

        // Generate the Requirements Tree Section
        Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
        styles.put("rowHeaderStyle", ReportUtility.createCellStyle(wb, ReportStyles.ROW_HEADER, true, true, true, true));
        styles.put("firstColHeaderStyle", ReportUtility.createCellStyle(wb, ReportStyles.COL_HEADER, true, true, true, false));
        styles.put("colHeaderStyle", ReportUtility.createCellStyle(wb, ReportStyles.COL_HEADER, true, false, true, false));
        styles.put("lastColHeaderStyle", ReportUtility.createCellStyle(wb, ReportStyles.COL_HEADER, true, false, true, true));
        styles.put("motherStyle", ReportUtility.createCellStyle(wb, ReportStyles.BOLD, true, true, true, true));
        styles.put("referenceStyle", ReportUtility.createCellStyle(wb, ReportStyles.REFERENCE, true, true, true, true));
        styles.put("contributorStyle", ReportUtility.createCellStyle(wb, ReportStyles.DEFAULT, true, true, true, true));
        styles.put("contributorCodeStyle", ReportUtility.createCellStyle(wb, ReportStyles.HIGHLIGHT, true, true, true, true));

        // #### 'Requirement structure' title ####
        rowIdx = ReportUtility.createSection(summarySheet, rowIdx, NB_COL, LABELS_ENUM_CLASSNAME, "TREE");

        // #### Top border row ####
        XSSFRow row = ReportUtility.createRow(summarySheet, rowIdx++, NB_COL, false, ReportRowHeights.DEFAULT);
        for (int colIndex = 1; colIndex < NB_COL - 1; colIndex++) {
            XSSFCellStyle cellStyle = ReportUtility.createCellStyle(wb, ReportStyles.DEFAULT, true, colIndex == 1, false, colIndex == NB_COL - 2);
            row.createCell(colIndex).setCellStyle(cellStyle);
        }

        // #### Prepare the number of rows to create: two rows per requirements - 1 ####
        List<SciMotherRequirementType> motherReqList = levelsMap.get(1);
        int colIdx = (5 - (levelsMap.size() - levelsMap.size() / 2));
        rowIdx = ReportUtility.generateRequirementTree(summarySheet, rowIdx++, colIdx, NB_COL, motherReqList, new HashMap<String, XSSFCellStyle>(),
                new ArrayList<Integer>());

        // #### Bottom border row ####
        row = ReportUtility.createRow(summarySheet, rowIdx++, NB_COL, true, ReportRowHeights.DEFAULT);
        for (int colIndex = 1; colIndex < NB_COL - 1; colIndex++) {
            XSSFCellStyle cellStyle = ReportUtility.createCellStyle(wb, ReportStyles.DEFAULT, true, false, true, false);
            row.createCell(colIndex).setCellStyle(cellStyle);
        }

        // ########### REQUIREMENT SHEETS ###########
        for (int i = levelsMap.size(); i > 0; i--) {
            // Get the list of sci mother requirement type objects from the levelsMap.
            List<SciMotherRequirementType> sciMotherRequirementList = levelsMap.get(new Integer(i));
            for (SciMotherRequirementType sciMotherRequirement : sciMotherRequirementList) {
                // We first perform formula and contributors replacement.
                sciMotherRequirement = ReportUtility.performFormulaReplacement(sciMotherRequirement, true);
                // And finally create Requirement sheet.
                // ########### SUMMARY SHEET ###########
                if (wb.getSheet(SciConnectorAbstractService.normalizeFileName(sciMotherRequirement.getCode())) == null) {
                    XSSFSheet resultSheet = ReportUtility.getSheet(wb, SciConnectorAbstractService.normalizeFileName(sciMotherRequirement.getCode()));
                    wb.setSheetOrder(SciConnectorAbstractService.normalizeFileName(sciMotherRequirement.getCode()), 2);

                    // Generate the Standard Report Header
                    rowIdx = ReportUtility.generateSheetHeader(resultSheet, NB_COL, false, false, LABELS_ENUM_CLASSNAME, executionDate);
                    generateResultsSection(resultSheet, sciMotherRequirement, rowIdx++, styles);
                }
            }
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
        String reportPath = ReportUtility.getRepositoryPath(CalculationType.MONTE_CARLO, ReportUtility.REPORT, executionDate)
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
        ReportUtility.createMergedCells(sheet, rowIndex - 1, rowIndex - 1, 3, 8, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "DESIGNATION"), true);
        // ## Values ##
        ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_VALUE);
        // 'Customer Requirement' column
        ReportUtility.createMergedCells(sheet, rowIndex - 1, rowIndex - 1, 1, 2, styles.get("referenceStyle"), motherReq.getCode(), false);
        // 'Designation' column
        ReportUtility.createMergedCells(sheet, rowIndex - 1, rowIndex - 1, 3, 8, styles.get("referenceStyle"), motherReq.getTitle(), false);

        // ## Column headers ##
        XSSFRow rowLabels = ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_HEADER);
        // 'Lower limit' column
        ReportUtility.createFormulaCell(rowLabels, 1, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "LOW_VALUE"),
                styles.get("firstColHeaderStyle"));
        // 'Upper limit' column
        ReportUtility.createFormulaCell(rowLabels, 2, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "UP_VALUE"),
                styles.get("colHeaderStyle"));
        // 'Mean' column
        ReportUtility.createFormulaCell(rowLabels, 3, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "MEAN"),
                styles.get("lastColHeaderStyle"));
        // 'Standard deviation' column
        ReportUtility.createFormulaCell(rowLabels, 4, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "STANDARD_DEV"),
                styles.get("firstColHeaderStyle"));
        // 'Cp' column
        ReportUtility
                .createFormulaCell(rowLabels, 5, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "CAP"), styles.get("colHeaderStyle"));
        // 'Cpk' column
        ReportUtility
                .createFormulaCell(rowLabels, 6, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "CPK"), styles.get("colHeaderStyle"));
        // 'Real off-centering' column
        ReportUtility.createFormulaCell(rowLabels, 7, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "OFF_CENTERING"),
                styles.get("colHeaderStyle"));
        // 'PPM' column
        ReportUtility.createFormulaCell(rowLabels, 8, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "PPM"),
                styles.get("lastColHeaderStyle"));

        // ## Values ##
        XSSFRow rowValues = ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_VALUE);
        // 'Lower limit' column
        ReportUtility.createNumericCell(rowValues, 1, motherReq.getValueInf(), styles.get("motherStyle"));
        // 'Upper limit' column
        ReportUtility.createNumericCell(rowValues, 2, motherReq.getValueSup(), styles.get("motherStyle"));
        // 'Mean' column
        ReportUtility.createNumericCell(rowValues, 3, motherReq.getMean(), styles.get("motherStyle"));
        // 'Standard deviation' column
        ReportUtility.createNumericCell(rowValues, 4, motherReq.getStandardDeviation(), styles.get("motherStyle"));
        // 'Cp' column
        ReportUtility.createNumericCell(rowValues, 5, motherReq.getCap(), styles.get("motherStyle"));
        // 'Cpk' column
        ReportUtility.createNumericCell(rowValues, 6, motherReq.getCpk(), styles.get("motherStyle"));
        // 'Real off-centering' column
        ReportUtility.createNumericCell(rowValues, 7, motherReq.getCenteringMax(), styles.get("motherStyle"));
        // 'PPM' column
        ReportUtility.createNumericCell(rowValues, 8, motherReq.getTnc(), styles.get("motherStyle"));
        return rowIndex;
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
        // Contributor Designation Lower Limit Upper Limit Distribution Parameter 1 Parameter 2

        int rowIndex = rowIdx;
        // ## Column header ##
        XSSFRow rowLabels = ReportUtility.createRow(sheet, rowIndex, NB_COL, false, ReportRowHeights.TABLE_HEADER);
        // 'Contributor' column
        ReportUtility.createFormulaCell(rowLabels, 1, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "CONTRIBUTOR"),
                styles.get("rowHeaderStyle"));
        // 'Designation' column
        ReportUtility.createFormulaCell(rowLabels, 2, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "DESIGNATION"),
                styles.get("firstColHeaderStyle"));
        // 'Lower limit' column
        ReportUtility.createFormulaCell(rowLabels, 3, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "LOW_VALUE"),
                styles.get("colHeaderStyle"));
        // 'Upper limit' column
        ReportUtility.createFormulaCell(rowLabels, 4, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "UP_VALUE"),
                styles.get("colHeaderStyle"));
        // 'Distribution' column
        ReportUtility.createFormulaCell(rowLabels, 5, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "DISTRIBUTION"),
                styles.get("colHeaderStyle"));
        // 'Parameter 1' column
        ReportUtility.createFormulaCell(rowLabels, 6, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "PARAM_1"),
                styles.get("colHeaderStyle"));
        // 'Parameter 2' column
        ReportUtility.createFormulaCell(rowLabels, 7, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "PARAM_2"),
                styles.get("lastColHeaderStyle"));

        // ## Column values ##
        for (Iterator<SciRequirementType> iterator = contributorList.iterator(); iterator.hasNext();) {
            SciRequirementType contributor = iterator.next();
            rowIndex++;
            int colIdx = 1;
            XSSFRow row = ReportUtility.createRow(sheet, rowIndex, NB_COL, false, ReportRowHeights.TABLE_VALUE);

            // 'Code' value
            ReportUtility.createStringCell(row, colIdx++, contributor.getCode(), styles.get("contributorCodeStyle"));
            // 'Designation' value
            ReportUtility.createStringCell(row, colIdx++, contributor.getTitle(), styles.get("contributorStyle"));
            // 'Lower limit' value
            ReportUtility.createNumericCell(row, colIdx++, contributor.getValueInf(), styles.get("contributorStyle"));
            // 'Upper limit' value
            ReportUtility.createNumericCell(row, colIdx++, contributor.getValueSup(), styles.get("contributorStyle"));
            // 'Distribution' value
            ReportUtility.createFormulaCell(row, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, contributor.getLaw().value()),
                    styles.get("contributorStyle"));
            // 'Parameter 1' value
            ReportUtility.createParameterCell(row, colIdx++, contributor.getLaw(), contributor.getParamLaw1(), 1, paramStyles);
            // 'Parameter 2' value
            ReportUtility.createParameterCell(row, colIdx, contributor.getLaw(), contributor.getParamLaw2(), 2, paramStyles);
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

        rowIndex = rowIndex + 20;

        for (int i = rowIdx + 2; i < rowIndex; i++)
            ReportUtility.createRow(sheet, i, NB_COL, false, ReportRowHeights.DEFAULT);

        XSSFWorkbook wb = sheet.getWorkbook();

        boolean pictureSet = false;
        if (motherReq.getPlotPath() != null && !motherReq.getPlotPath().isEmpty()) {

            try {
                // FileInputStream obtains input bytes from the image file
                BufferedImage bufferedImage = ImageIO.read(new File(motherReq.getPlotPath()));

                // this writes the bufferedImage into a byte array called resultingBytes
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteArrayOut);
                // Get the contents of an OutputStream as a byte[].
                byte[] bytes = byteArrayOut.toByteArray();

                // Adds a picture to the workbook
                int distributionPlot = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
                // close the input stream
                byteArrayOut.close();

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
                XSSFPicture pict = drawing.createPicture(anchor, distributionPlot);
                // Reset the image to the original size
                pict.resize(1.0);
                pictureSet = true;
            } catch (IOException e) {
                // Auto-generated catch block
                e.printStackTrace();
            }
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
            pict.resize(0.75);
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

        RequirementType motherRequirement = new RequirementType();
        motherRequirement.setName("Y");
        motherRequirement.setId("01");
        motherRequirement.setTitle("Prestation 1");
        motherRequirement.setCode("01A01");
        motherRequirement.setReferenceRequirement(true);
        motherRequirement.setTnc(new Double(0.5));
        motherRequirement.setCpk(new Double(1.2));

        TransferModelType transferModel = new TransferModelType();
        transferModel.setFormula("#C-1# + #C-2#*cosd(#C-3#) + #S-1#");
        transferModel.setReportFormula("01A01_C1 + 01A01_C2*cosd(01A01_C3) + S-01A01");

        List<RequirementType> contributorList = new LinkedList<RequirementType>();
        RequirementType contributor = new RequirementType();

        contributor.setName("C-1");
        contributor.setId("11");
        contributor.setCode("01A01_C1");
        contributor.setLaw(LawType.NORMAL_STD);
        contributor.setParamLaw1(new Double(0.0));
        contributor.setParamLaw2(new Double(1.0));
        contributorList.add(contributor);

        contributor = new RequirementType();
        contributor.setName("C-2");
        contributor.setId("12");
        contributor.setCode("01A01_C2");
        contributor.setLaw(LawType.NORMAL_PLAGE);
        contributor.setParamLaw1(new Double(0.0));
        contributor.setParamLaw2(new Double(1.0));
        contributorList.add(contributor);

        contributor = new RequirementType();
        contributor.setName("C-3");
        contributor.setId("13");
        contributor.setCode("01A01_C3");
        contributor.setLaw(LawType.NORMAL_STD);
        contributor.setParamLaw1(new Double(0.0));
        contributor.setParamLaw2(new Double(1.0));
        contributorList.add(contributor);

        RequirementType subMotherRequirement = new RequirementType();
        subMotherRequirement.setName("S-1");
        subMotherRequirement.setId("101");
        subMotherRequirement.setTitle("Sub Prestation 1");
        subMotherRequirement.setCode("S-01A01");
        subMotherRequirement.setTnc(new Double(0.5));
        subMotherRequirement.setCpk(new Double(1.2));

        TransferModelType subTransferModel = new TransferModelType();
        subTransferModel.setFormula("#C-5# / #C-6#");
        subTransferModel.setReportFormula("01A01_C5 / 01A01_C6");

        List<RequirementType> subContributorList = new LinkedList<RequirementType>();
        RequirementType subContributor = new RequirementType();

        subContributor.setName("C-5");
        subContributor.setId("15");
        subContributor.setCode("01A01_C5");
        subContributor.setLaw(LawType.NORMAL_STD);
        subContributor.setParamLaw1(new Double(0.0));
        subContributor.setParamLaw2(new Double(1.0));
        subContributorList.add(subContributor);

        subContributor = new RequirementType();
        subContributor.setName("C-6");
        subContributor.setId("16");
        subContributor.setCode("01A01_C6");
        subContributor.setLaw(LawType.NORMAL_STD);
        subContributor.setParamLaw1(new Double(0.0));
        subContributor.setParamLaw2(new Double(1.0));
        subContributorList.add(subContributor);

        SciMotherRequirementType scilabSubMotherRequirement = SciObjectInputAdapter.createScilabMotherRequirementType(subMotherRequirement,
                subTransferModel, subContributorList);

        SciMotherRequirementType scilabMotherRequirement = SciObjectInputAdapter.createScilabMotherRequirementType(motherRequirement, transferModel,
                contributorList);
        scilabMotherRequirement.getContributorList().add(scilabSubMotherRequirement);

        contributor = new RequirementType();
        contributor.setName("C-7");
        contributor.setId("17");
        contributor.setCode("01A01_C7");
        contributor.setLaw(LawType.NORMAL_STD);
        contributor.setParamLaw1(new Double(0.0));
        contributor.setParamLaw2(new Double(1.0));

        SciRequirementType sContributor = SciObjectInputAdapter.createScilabRequirementType(contributor);
        scilabMotherRequirement.getContributorList().add(sContributor);
        scilabMotherRequirement.setPlotPath("C:\\TEMP\\oasis_tmp\\MonteCarlo_20141201-183206_Prest_UR-0000001.gif");

        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 1981);
        date.set(Calendar.MONTH, 5);
        date.set(Calendar.DAY_OF_MONTH, 8);

        HashMap<Integer, List<SciMotherRequirementType>> levelsMap = new HashMap<Integer, List<SciMotherRequirementType>>();
        List<SciMotherRequirementType> level1 = new ArrayList<SciMotherRequirementType>();
        level1.add(scilabMotherRequirement);
        levelsMap.put(new Integer(1), level1);
        List<SciMotherRequirementType> level2 = new ArrayList<SciMotherRequirementType>();
        level2.add(scilabSubMotherRequirement);
        levelsMap.put(new Integer(2), level2);

        MonteCarloReportServiceImpl service = new MonteCarloReportServiceImpl(levelsMap, date.getTime(), "SIBERT");
        System.out.println(service.generateReport());
    }
}
