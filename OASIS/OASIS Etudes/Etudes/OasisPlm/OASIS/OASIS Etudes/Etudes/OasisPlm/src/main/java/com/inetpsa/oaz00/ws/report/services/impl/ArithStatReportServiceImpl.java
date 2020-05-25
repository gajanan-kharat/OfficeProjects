/*
 * Author: U224106
 * Creation: 3 déc. 2014
 */
package com.inetpsa.oaz00.ws.report.services.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.inetpsa.oaz00.ws.report.services.ReportService;
import com.inetpsa.oaz00.ws.report.utils.ArithStatReportLabelsEnum;
import com.inetpsa.oaz00.ws.report.utils.ReportRowHeights;
import com.inetpsa.oaz00.ws.report.utils.ReportStyles;
import com.inetpsa.oaz00.ws.report.utils.ReportUtility;
import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.oaz00.ws.scilab.utils.SciObjectInputAdapter;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.RequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.TransferModelType;

/**
 * The Class Arith stat report service implementation.
 * 
 * @author U224106
 */
public class ArithStatReportServiceImpl implements ReportService {

    /** The Constant NB_COL. */
    private final static int NB_COL = 12;

    /** The Constant LABELS_ENUM_CLASSNAME. */
    private final static String LABELS_ENUM_CLASSNAME = ArithStatReportLabelsEnum.class.getName();

    /** The max row idx. */
    private static int maxRowIdx = 20;

    /** The mother requirement. */
    private SciMotherRequirementType motherRequirement;

    /** The project name. */
    private String projectName;

    /** The execution date. */
    private Date executionDate;

    /** The author. */
    private String author;

    /**
     * Instantiates a new arith stat report service impl.
     * 
     * @param pMotherRequirement the mother requirement
     * @param pProjectName the project name
     * @param pExecutionDate the execution date
     * @param userId the user id
     */
    public ArithStatReportServiceImpl(SciMotherRequirementType pMotherRequirement, String pProjectName, Date pExecutionDate, String userId) {
        super();
        this.motherRequirement = pMotherRequirement;
        this.projectName = pProjectName;
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
        coreProps.setKeywords("ARITHMETIC STATISTICAL SIMULATION");

        // ########### PARAM SHEET ###########
        ReportUtility.generateParameterSheet(wb, CalculationType.ARITHMETIC);

        // ########### SUMMARY SHEET ###########
        XSSFSheet summarySheet = ReportUtility.getSheet(wb, ReportUtility.SUMMARY_SHEET);

        // Generate the Standard Report Header
        int rowIdx = ReportUtility.generateSheetHeader(summarySheet, NB_COL, true, true, LABELS_ENUM_CLASSNAME, executionDate);

        // Generate the 'Project' Section - Rows #7 & #8
        rowIdx = ReportUtility.generateProjectSection(summarySheet, rowIdx, NB_COL, projectName, LABELS_ENUM_CLASSNAME);

        // Generate the 'Numerical results' section - Rows #9 & following
        rowIdx = generateResultsSection(summarySheet, rowIdx);

        // ########### PICTURE SHEET ###########
        XSSFSheet imageSheet = ReportUtility.getSheet(wb, ReportUtility.IMAGE_SHEET);

        // Generate the Standard Report Header
        rowIdx = ReportUtility.generateSheetHeader(imageSheet, NB_COL, true, false, LABELS_ENUM_CLASSNAME, executionDate);
        ReportUtility.createRow(imageSheet, rowIdx++, NB_COL, false, ReportRowHeights.DEFAULT);
        ReportUtility.createSection(imageSheet, rowIdx++, NB_COL, LABELS_ENUM_CLASSNAME, "PICTURE");

        for (int i = rowIdx; i < maxRowIdx; i++) {
            ReportUtility.createRow(imageSheet, i, NB_COL, i == (maxRowIdx - 1), ReportRowHeights.TABLE_VALUE);
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
        String reportPath = ReportUtility.getRepositoryPath(CalculationType.ARITHMETIC, ReportUtility.REPORT, executionDate)
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
     * @param rowIdx the row idx
     * @return the int
     */
    private int generateResultsSection(XSSFSheet sheet, int rowIdx) {

        int rowIndex = rowIdx;

        // #### Cell Styles ####
        Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
        styles.put("rowHeaderStyle", ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.ROW_HEADER, true, true, true, true));
        styles.put("firstColHeaderStyle", ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.COL_HEADER, true, true, true, false));
        styles.put("colHeaderStyle", ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.COL_HEADER, true, false, true, false));
        styles.put("lastColHeaderStyle", ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.COL_HEADER, true, false, true, true));
        styles.put("motherStyle", ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.BOLD, true, true, true, true));
        styles.put("referenceStyle", ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.REFERENCE, true, true, true, true));
        styles.put("contributorStyle", ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, true, true, true, true));
        styles.put("contributorCodeStyle", ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.HIGHLIGHT, true, true, true, true));
        // #### 'Numerical result' title ####
        rowIndex = ReportUtility.createSection(sheet, rowIndex, NB_COL, LABELS_ENUM_CLASSNAME, "RESULTS");

        // #### 'Free Play' Part ####
        rowIndex = generateMotherRequirementBlock(sheet, rowIndex, styles);

        // #### Results Part ####
        rowIndex = generateResultsBlock(sheet, rowIndex, styles);

        // #### Formula ####
        rowIndex = ReportUtility.generateSimpleFormulaBlock(sheet, rowIndex, NB_COL, motherRequirement.getTransferModel().getReportFormula(),
                LABELS_ENUM_CLASSNAME, styles);

        // #### Contributor list ####
        rowIndex = generateContributorList(sheet, rowIndex, styles);

        ReportUtility.createRow(sheet, ++rowIndex, NB_COL, true, ReportRowHeights.DEFAULT);
        return rowIndex;
    }

    /**
     * Generate mother requirement block.
     * 
     * @param sheet the sheet
     * @param rowIdx the row idx
     * @param styles the styles
     * @return the int
     */
    private int generateMotherRequirementBlock(XSSFSheet sheet, int rowIdx, Map<String, XSSFCellStyle> styles) {
        int rowIndex = rowIdx;
        XSSFRow rowHeader = ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_HEADER);
        XSSFRow rowValues = ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_VALUE);
        ReportUtility.createMergedCells(sheet, rowHeader.getRowNum(), rowValues.getRowNum(), 1, 2, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "FREE_PLAY"), true);

        // ## Column headers ##
        // 'Vector' column
        ReportUtility.createFormulaCell(rowHeader, 3, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "VECTOR"),
                styles.get("firstColHeaderStyle"));
        // 'Designation' column
        ReportUtility.createMergedCells(sheet, rowHeader.getRowNum(), rowHeader.getRowNum(), 4, 7, styles.get("colHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "DESIGNATION"), true);
        // 'Nominal' column
        ReportUtility.createFormulaCell(rowHeader, 8, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "NOMINAL"),
                styles.get("colHeaderStyle"));
        // 'Off-Centering' column
        ReportUtility.createFormulaCell(rowHeader, 9, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "OFF_CENTERING"),
                styles.get("colHeaderStyle"));
        // 'PPM' column
        ReportUtility.createFormulaCell(rowHeader, 10, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "PPM"),
                styles.get("lastColHeaderStyle"));

        // ## Column values ##
        // 'Vector' value
        ReportUtility.createStringCell(rowValues, 3, motherRequirement.getCode(), styles.get("referenceStyle"));
        // 'Designation' value
        ReportUtility.createMergedCells(sheet, rowValues.getRowNum(), rowValues.getRowNum(), 4, 7, styles.get("referenceStyle"),
                motherRequirement.getTitle(), false);
        // 'Nominal' value
        ReportUtility.createNumericCell(rowValues, 8, motherRequirement.getNominal(), styles.get("motherStyle"));
        // 'Off-Centering' value
        ReportUtility.createNumericCell(rowValues, 9, motherRequirement.getCenteringMax(), styles.get("motherStyle"));
        // 'PPM' value
        ReportUtility.createNumericCell(rowValues, 10, motherRequirement.getTnc(), styles.get("motherStyle"));

        return rowIndex;
    }

    /**
     * Generate results block.
     * 
     * @param sheet the sheet
     * @param rowIdx the row idx
     * @param styles the styles
     * @return the int
     */
    private int generateResultsBlock(XSSFSheet sheet, int rowIdx, Map<String, XSSFCellStyle> styles) {
        int rowIndex = rowIdx;
        XSSFRow rowHeader = ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_HEADER);
        XSSFRow rowArith = ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_VALUE);
        XSSFRow rowStat = ReportUtility.createRow(sheet, rowIndex++, NB_COL, false, ReportRowHeights.TABLE_VALUE);

        int colIdx = 5;
        // ## Column headers ##
        // 'Lower limit' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "LOW_VALUE"),
                styles.get("firstColHeaderStyle"));
        // 'Upper limit' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "UP_VALUE"),
                styles.get("colHeaderStyle"));
        // 'Lower TI' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "LOW_TI"),
                styles.get("colHeaderStyle"));
        // 'Upper TI' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "UP_TI"),
                styles.get("colHeaderStyle"));
        // 'Tolerance interval' column
        ReportUtility.createFormulaCell(rowHeader, colIdx, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "TI"),
                styles.get("lastColHeaderStyle"));

        colIdx = 5;
        // ## 'Worst case' values ##
        // 'Worst case' row header
        ReportUtility.createMergedCells(sheet, rowArith.getRowNum(), rowArith.getRowNum(), 3, 4, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "ARITH"), true);
        // 'Lower limit' value
        ReportUtility.createNumericCell(rowArith, colIdx++, motherRequirement.getArithmeticalValueInf(), styles.get("motherStyle"));
        // 'Upper limit' value
        ReportUtility.createNumericCell(rowArith, colIdx++, motherRequirement.getArithmeticalValueSup(), styles.get("motherStyle"));
        // 'Lower TI' value
        ReportUtility.createNumericCell(rowArith, colIdx++, motherRequirement.getArithmeticalITInf(), styles.get("motherStyle"));
        // 'Upper TI' value
        ReportUtility.createNumericCell(rowArith, colIdx++, motherRequirement.getArithmeticalITSup(), styles.get("motherStyle"));
        // 'Tolerance interval' value
        ReportUtility.createNumericCell(rowArith, colIdx, motherRequirement.getArithmeticalIT(), styles.get("motherStyle"));

        colIdx = 5;
        // ## 'Statistical' values ##
        // 'Statistical' row header
        ReportUtility.createMergedCells(sheet, rowStat.getRowNum(), rowStat.getRowNum(), 3, 4, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "STAT"), true);
        // 'Lower limit' value
        ReportUtility.createNumericCell(rowStat, colIdx++, motherRequirement.getStatisticalValueInf(), styles.get("motherStyle"));
        // 'Upper limit' value
        ReportUtility.createNumericCell(rowStat, colIdx++, motherRequirement.getStatisticalValueSup(), styles.get("motherStyle"));
        // 'Lower TI' value
        ReportUtility.createNumericCell(rowStat, colIdx++, motherRequirement.getStatisticalITInf(), styles.get("motherStyle"));
        // 'Upper TI' value
        ReportUtility.createNumericCell(rowStat, colIdx++, motherRequirement.getStatisticalITSup(), styles.get("motherStyle"));
        // 'Tolerance interval' value
        ReportUtility.createNumericCell(rowStat, colIdx, motherRequirement.getStatisticalIT(), styles.get("motherStyle"));

        return rowIndex;
    }

    /**
     * Generate contributor list.
     * 
     * @param sheet the sheet
     * @param rowIdx the row idx
     * @param styles the styles
     * @return the int
     */
    private int generateContributorList(XSSFSheet sheet, int rowIdx, Map<String, XSSFCellStyle> styles) {
        int rowIndex = rowIdx;
        int colIdx = 2;
        // ## Column header ##
        XSSFRow rowHeader = ReportUtility.createRow(sheet, rowIndex, NB_COL, false, ReportRowHeights.TABLE_HEADER);
        // 'Contributor' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "CONTRIBUTOR"),
                styles.get("rowHeaderStyle"));
        // 'Designation' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "DESIGNATION"),
                styles.get("firstColHeaderStyle"));
        // 'Nominal' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "NOMINAL"),
                styles.get("colHeaderStyle"));
        // 'Lower limit' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "LOW_VALUE"),
                styles.get("colHeaderStyle"));
        // 'Upper limit' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "UP_VALUE"),
                styles.get("colHeaderStyle"));
        // 'Lower TI' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "LOW_TI"),
                styles.get("colHeaderStyle"));
        // 'Upper TI' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "UP_TI"),
                styles.get("colHeaderStyle"));
        // 'Tolerance interval' column
        ReportUtility.createFormulaCell(rowHeader, colIdx++, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "TI"),
                styles.get("colHeaderStyle"));
        // 'Off-Centering' column
        ReportUtility.createFormulaCell(rowHeader, colIdx, ReportUtility.getLabelSwitchFormula(LABELS_ENUM_CLASSNAME, "OFF_CENTERING"),
                styles.get("lastColHeaderStyle"));

        // ## Column values ##
        for (Iterator<SciRequirementType> iterator = motherRequirement.getContributorList().iterator(); iterator.hasNext();) {
            SciRequirementType contributor = iterator.next();
            colIdx = 2;
            XSSFRow row = ReportUtility.createRow(sheet, ++rowIndex, NB_COL, false, ReportRowHeights.TABLE_VALUE);

            // 'Code' value
            ReportUtility.createStringCell(row, colIdx++, contributor.getCode(), styles.get("contributorCodeStyle"));
            // 'Designation' value
            ReportUtility.createStringCell(row, colIdx++, contributor.getTitle(), styles.get("contributorStyle"));
            // 'Nominal' value
            ReportUtility.createNumericCell(row, colIdx++, contributor.getNominal(), styles.get("contributorStyle"));
            // 'Lower limit' value
            ReportUtility.createNumericCell(row, colIdx++, contributor.getValueInf(), styles.get("contributorStyle"));
            // 'Upper limit' value
            ReportUtility.createNumericCell(row, colIdx++, contributor.getValueSup(), styles.get("contributorStyle"));
            // 'Lower TI' value
            ReportUtility.createFormulaCell(row, colIdx++, "F" + (rowIndex + 1) + "-E" + (rowIndex + 1), styles.get("contributorStyle"));
            // 'Upper TI' value
            ReportUtility.createFormulaCell(row, colIdx++, "G" + (rowIndex + 1) + "-E" + (rowIndex + 1), styles.get("contributorStyle"));
            // 'Tolerance interval' value
            ReportUtility.createFormulaCell(row, colIdx++, "G" + (rowIndex + 1) + "-F" + (rowIndex + 1), styles.get("contributorStyle"));
            // 'Off-Centering' value
            ReportUtility.createNumericCell(row, colIdx, contributor.getCenteringMax(), styles.get("contributorStyle"));
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
        motherRequirement.setTnc(new Double(0.5));

        TransferModelType transferModel = new TransferModelType();
        transferModel.setFormula("C1 + C2*cosd(C3)");

        List<RequirementType> contributorList = new LinkedList<RequirementType>();
        RequirementType contributor = new RequirementType();

        contributor.setName("C1");
        contributor.setId("11");
        contributor.setCode("CODE");
        contributor.setNominal(new Double(10.0));
        contributor.setValueInf(new Double(0.0));
        contributor.setValueSup(new Double(12.0));
        contributorList.add(contributor);

        contributor = new RequirementType();
        contributor.setName("C2");
        contributor.setId("12");
        contributor.setNominal(new Double(8.0));
        contributor.setValueInf(new Double(4.0));
        contributor.setValueSup(new Double(10.0));
        contributorList.add(contributor);

        contributor = new RequirementType();
        contributor.setName("C3");
        contributor.setId("13");
        contributor.setNominal(new Double(30.0));
        contributor.setValueInf(new Double(29.5));
        contributor.setValueSup(new Double(31.0));
        contributorList.add(contributor);

        SciMotherRequirementType scilabMotherRequirement = SciObjectInputAdapter.createScilabMotherRequirementType(motherRequirement, transferModel,
                contributorList);

        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 1981);
        date.set(Calendar.MONTH, 6);
        date.set(Calendar.DAY_OF_MONTH, 8);

        ArithStatReportServiceImpl service = new ArithStatReportServiceImpl(scilabMotherRequirement, "Report Generation", date.getTime(), "SIBERT");
        System.out.println(service.generateReport());
    }
}
