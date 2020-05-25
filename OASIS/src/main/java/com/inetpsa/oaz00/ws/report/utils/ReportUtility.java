/*
 * Author: U224106
 * Creation: 18 déc. 2014
 */
package com.inetpsa.oaz00.ws.report.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;

import com.inetpsa.oaz00.ws.scilab.model.SciMotherRequirementType;
import com.inetpsa.oaz00.ws.scilab.model.SciRequirementType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.CalculationType;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.LawType;

/**
 * The Class ReportUtility.
 * 
 * @author U224106
 */
public class ReportUtility {

    /** The param sheet. */
    public static String PARAM_SHEET = "Param";

    /** The summary sheet. */
    public static String SUMMARY_SHEET = "Summary";

    /** The image sheet. */
    public static String IMAGE_SHEET = "Image";

    /** The lang en. */
    private static String LANG_EN = "English";

    /** The lang fr. */
    private static String LANG_FR = "Français";

    /** The Constant PICTURE. */
    public final static String PICTURE = "Pictures";

    /** The Constant REPORT. */
    public final static String REPORT = "Reports";

    /** The Constant MONTE_CARLO_PREFIX. */
    public final static String MONTE_CARLO_PREFIX = "MonteCarlo_";

    /** The Constant SPEC_IT_PREFIX. */
    public final static String SPEC_IT_PREFIX = "SpecIT_";

    /** The Constant ARITHMETIC_PREFIX. */
    public final static String ARITHMETIC_PREFIX = "ArithStat_";

    /** The Constant SEMI_QUADRATIC_PREFIX. */
    public final static String SEMI_QUADRATIC_PREFIX = "SemiQuad_";

    /** The Constant REPORT_EXTENSION. */
    public final static String REPORT_EXTENSION = ".xlsx";

    /** The logger. */
    private static Logger logger = Logger.getLogger(ReportUtility.class);

    /**
     * The Enum ParamType.
     * <p>
     * These items are required to display distribution parameters' value
     * </p>
     * 
     * @author U224106
     */
    public static enum ParamType {

        /** The alpha. */
        ALPHA,
        /** The beta. */
        BETA,
        /** The lambda. */
        LAMBDA,
        /** The mu. */
        MU,
        /** The sigma. */
        SIGMA,
        /** The n. */
        N,
        /** The p. */
        P
    };

    /**
     * Gets the sheet.
     * 
     * @param wb the wb
     * @param sheetName the sheet name
     * @return the sheet
     */
    public static XSSFSheet getSheet(XSSFWorkbook wb, String sheetName) {
        XSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setPrintGridlines(false);
        sheet.setDisplayGridlines(false);

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(false);
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);
        printSetup.setFitWidth((short) 1);
        printSetup.setFitHeight((short) 0);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        // Always select the Title of the sheet
        sheet.setActiveCell("B4");

        sheet.getFooter().setRight(HeaderFooter.fontSize((short) 16) + "Page " + HeaderFooter.page() + " / " + HeaderFooter.numPages());

        return sheet;
    }

    /**
     * Generate parameter sheet.
     * 
     * @param wb the wb
     * @param type the type
     */
    public static void generateParameterSheet(Workbook wb, CalculationType type) {

        Sheet sheet = wb.createSheet(PARAM_SHEET);
        sheet.setSelected(false);
        switch (type) {
        case MONTE_CARLO:
            for (MonteCarloReportLabelsEnum label : MonteCarloReportLabelsEnum.values()) {
                Row row = sheet.createRow((short) label.ordinal());
                row.createCell(0).setCellValue(label.french());
                row.createCell(1).setCellValue(label.english());
            }
            break;

        case SPEC_IT:
            for (SpecITReportLabelsEnum label : SpecITReportLabelsEnum.values()) {
                Row row = sheet.createRow((short) label.ordinal());
                row.createCell(0).setCellValue(label.french());
                row.createCell(1).setCellValue(label.english());
            }
            break;

        case ARITHMETIC:
            for (ArithStatReportLabelsEnum label : ArithStatReportLabelsEnum.values()) {
                Row row = sheet.createRow((short) label.ordinal());
                row.createCell(0).setCellValue(label.french());
                row.createCell(1).setCellValue(label.english());
            }
            break;

        case SEMI_QUADRATIC:
            for (SemiQuadReportLabelsEnum label : SemiQuadReportLabelsEnum.values()) {
                Row row = sheet.createRow((short) label.ordinal());
                row.createCell(0).setCellValue(label.french());
                row.createCell(1).setCellValue(label.english());
            }
            break;

        default:
            break;
        }
    }

    /**
     * Generate sheet header.
     * 
     * @param sheet the sheet
     * @param nbCol the nb col
     * @param shortSecondCol the short second col
     * @param withLanguage the with language
     * @param labelsEnumClassName the labels enum class name
     * @param executionDate the execution date
     * @return the int
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static int generateSheetHeader(XSSFSheet sheet, int nbCol, boolean shortSecondCol, boolean withLanguage, String labelsEnumClassName,
            Date executionDate) throws IOException {

        int rowIdx = 0;
        XSSFWorkbook wb = sheet.getWorkbook();
        resizeColumns(sheet, nbCol, shortSecondCol);

        createRow(sheet, rowIdx++, nbCol, false, ReportRowHeights.DEFAULT);

        // PSA LOGO
        createRow(sheet, rowIdx++, nbCol, false, ReportRowHeights.STANDARD);
        // FileInputStream obtains input bytes from the image file
        InputStream inputStream = ReportUtility.class.getResourceAsStream("/logo_PSA.jpg");
        // Get the contents of an InputStream as a byte[].
        byte[] bytes = IOUtils.toByteArray(inputStream);
        // Adds a picture to the workbook
        int logoPicIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
        // close the input stream
        inputStream.close();

        // Returns an object that handles instantiating concrete classes
        XSSFCreationHelper helper = wb.getCreationHelper();

        // Creates the top-level drawing patriarch.
        XSSFDrawing drawing = sheet.createDrawingPatriarch();

        // Create an anchor that is attached to the worksheet
        XSSFClientAnchor anchor = helper.createClientAnchor();
        // set top-left corner for the image
        anchor.setCol1(nbCol - 3);
        anchor.setRow1(1);

        // Creates a picture
        XSSFPicture pict = drawing.createPicture(anchor, logoPicIdx);
        // Reset the image to the original size
        pict.resize(1.57);

        createRow(sheet, rowIdx++, nbCol, false, ReportRowHeights.DEFAULT);
        XSSFRow row = createRow(sheet, rowIdx++, nbCol, false, ReportRowHeights.TITLE);

        // Create a merged cell region with TITLE style and put content in it. Rows are 0 based.
        XSSFCellStyle titleStyle = createCellStyle(wb, ReportStyles.TITLE, true, true, true, true);
        createMergedCells(sheet, row.getRowNum(), row.getRowNum(), 1, nbCol - 2, titleStyle, getLabelSwitchFormula(labelsEnumClassName, "TITLE"),
                true);

        createRow(sheet, rowIdx++, nbCol, false, ReportRowHeights.DEFAULT);

        if (withLanguage) {
            Row rowLanguage = createRow(sheet, rowIdx++, nbCol, false, ReportRowHeights.STANDARD);
            XSSFCellStyle labelsStyle = createCellStyle(wb, ReportStyles.SOFT_GREY, true, true, true, true);
            XSSFCellStyle languageCellStyle = createCellStyle(wb, ReportStyles.DEFAULT, true, true, true, true);
            languageCellStyle.setLocked(false);

            // Language
            createFormulaCell(rowLanguage, 2, getLabelSwitchFormula(labelsEnumClassName, "LANGUAGE"), labelsStyle);
            createMergedCells(sheet, rowLanguage.getRowNum(), rowLanguage.getRowNum(), 3, 4, languageCellStyle, LANG_EN, false);
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(new String[] { LANG_EN,
                    LANG_FR });
            CellRangeAddressList addressList = new CellRangeAddressList(rowLanguage.getRowNum(), rowLanguage.getRowNum(), 3, 4);
            XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
            validation.setShowErrorBox(true);
            sheet.addValidationData(validation);

            // Date
            createFormulaCell(rowLanguage, nbCol - 4 - (nbCol / 11), getLabelSwitchFormula(labelsEnumClassName, "DATE"), labelsStyle);
            createDateCell(sheet, rowLanguage.getRowNum(), rowLanguage.getRowNum(), nbCol - 3 - (nbCol / 11), nbCol - 2 - (nbCol / 11), executionDate);

            createRow(sheet, rowIdx++, nbCol, false, ReportRowHeights.DEFAULT);
        }
        return rowIdx;
    }

    /**
     * Generate project section.
     * 
     * @param sheet the sheet
     * @param rowIdx the row idx
     * @param nbCol the nb col
     * @param projectName the project name
     * @param labelsEnumClassname the labels enum classname
     * @return the int
     */
    public static int generateProjectSection(XSSFSheet sheet, int rowIdx, int nbCol, String projectName, String labelsEnumClassname) {
        int rowIndex = rowIdx;
        XSSFRow row = ReportUtility.createRow(sheet, rowIndex++, nbCol, false, ReportRowHeights.TABLE_HEADER);
        XSSFCellStyle rowHeaderStyle = ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.ROW_HEADER, true, true, true, true);
        ReportUtility.createMergedCells(sheet, row.getRowNum(), row.getRowNum(), 1, 2, rowHeaderStyle,
                ReportUtility.getLabelSwitchFormula(labelsEnumClassname, "PROJECT"), true);

        XSSFCellStyle rowContentStyle = ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, true, true, true, true);
        ReportUtility.createMergedCells(sheet, row.getRowNum(), row.getRowNum(), 3, nbCol - 2, rowContentStyle, projectName, false);

        // Create an empty row then
        ReportUtility.createRow(sheet, rowIndex++, nbCol, false, ReportRowHeights.DEFAULT);
        return rowIndex;

    }

    /**
     * Generate simple formula block.
     * 
     * @param sheet the sheet
     * @param rowIdx the row idx
     * @param nbCol the nb col
     * @param formula the formula
     * @param labelsEnumClassname the labels enum classname
     * @param styles the styles
     * @return the int
     */
    public static int generateSimpleFormulaBlock(XSSFSheet sheet, int rowIdx, int nbCol, String formula, String labelsEnumClassname,
            Map<String, XSSFCellStyle> styles) {
        int rowIndex = rowIdx;
        ReportUtility.createRow(sheet, rowIndex++, nbCol, false, ReportRowHeights.DEFAULT);
        ReportUtility.createRow(sheet, rowIndex, nbCol, false, ReportRowHeights.FORMULA);
        ReportUtility.createMergedCells(sheet, rowIndex, rowIndex, 1, 2, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(labelsEnumClassname, "FORMULA"), true);
        ReportUtility.createMergedCells(sheet, rowIndex, rowIndex, 3, nbCol - 2, styles.get("contributorStyle"), formula, false);
        return ++rowIndex;
    }

    /**
     * Generate complete formula block.
     * 
     * @param sheet the sheet
     * @param rowIdx the row idx
     * @param nbCol the nb col
     * @param motherReq the mother req
     * @param labelsEnumClassname the labels enum classname
     * @param styles the styles
     * @return the int
     */
    public static int generateCompleteFormulaBlock(XSSFSheet sheet, int rowIdx, int nbCol, SciMotherRequirementType motherReq,
            String labelsEnumClassname, Map<String, XSSFCellStyle> styles) {
        int rowIndex = rowIdx;
        ReportUtility.createRow(sheet, rowIndex++, nbCol, false, ReportRowHeights.DEFAULT);
        XSSFRow rowFormula = ReportUtility.createRow(sheet, rowIndex++, nbCol, false, ReportRowHeights.TITLE);
        XSSFRow rowUncertainty = ReportUtility.createRow(sheet, rowIndex++, nbCol, false, ReportRowHeights.TITLE);
        // 'Formula' label
        ReportUtility.createMergedCells(sheet, rowFormula.getRowNum(), rowFormula.getRowNum(), 1, 2, styles.get("rowHeaderStyle"),
                ReportUtility.getLabelSwitchFormula(labelsEnumClassname, "FORMULA"), true);
        // 'Uncertainty' label
        ReportUtility.createFormulaCell(rowUncertainty, 1, ReportUtility.getLabelSwitchFormula(labelsEnumClassname, "RESIDUAL_STD_DEV"),
                styles.get("firstColHeaderStyle"));
        // 'Uncertainty' value
        ReportUtility.createNumericCell(rowUncertainty, 2, motherReq.getTransferModel().getUncertainty(), styles.get("motherStyle"));
        // 'Formula' value
        ReportUtility.createMergedCells(sheet, rowFormula.getRowNum(), rowUncertainty.getRowNum(), 3, nbCol - 2, styles.get("contributorStyle"),
                motherReq.getTransferModel().getReportFormula(), false);

        ReportUtility.createRow(sheet, rowIndex++, nbCol, false, ReportRowHeights.DEFAULT);
        return rowIndex;
    }

    /**
     * Resize columns.
     * 
     * @param sheet the sheet
     * @param nbCol the nb col
     * @param shortSecondCol the short second col
     */
    private static void resizeColumns(Sheet sheet, int nbCol, boolean shortSecondCol) {
        for (int i = 0; i < nbCol; i++) {
            if (i == 0 || (i == 1 && shortSecondCol) || i == nbCol - 1)
                sheet.setColumnWidth(i, calculateColWidth(5));
            else
                sheet.setColumnWidth(i, calculateColWidth(18));
        }
    }

    /**
     * Calculate col width.
     * 
     * @param width the width
     * @return the int
     */
    public static int calculateColWidth(int width) {
        if (width > 254)
            return 65280; // Maximum allowed column width.
        if (width > 1) {
            int floor = (int) (Math.floor(((double) width) / 5));
            int factor = (30 * floor);
            int value = 450 + factor + ((width - 1) * 250);
            return value;
        }
        return 450; // default to column size 1 if zero, one or negative number is passed.
    }

    /**
     * Creates the row.
     * 
     * @param sheet the sheet
     * @param rowIdx the row idx
     * @param nbCol the nb col
     * @param isLastRow the is last row
     * @param rowHeight the row height
     * @return the XSSF row
     */
    public static XSSFRow createRow(XSSFSheet sheet, int rowIdx, int nbCol, boolean isLastRow, ReportRowHeights rowHeight) {
        XSSFRow row = sheet.createRow((short) rowIdx);
        row.setHeightInPoints(rowHeight.getHeight());
        for (int colIdx = 0; colIdx < nbCol; colIdx++) {
            XSSFCellStyle cellStyle = createCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, rowIdx == 0, colIdx == 0, isLastRow,
                    colIdx == nbCol - 1);
            row.createCell(colIdx).setCellStyle(cellStyle);
        }
        return row;
    }

    /**
     * Creates the cell style.
     * 
     * @param wb the wb
     * @param style the style
     * @param top the top
     * @param left the left
     * @param bottom the bottom
     * @param right the right
     * @return the XSSF cell style
     */
    public static XSSFCellStyle createCellStyle(XSSFWorkbook wb, ReportStyles style, boolean top, boolean left, boolean bottom, boolean right) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setFillForegroundColor(style.getColor());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);

        // Create a new font and alter it.
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) style.getFontSize());
        font.setItalic(style.isItalic());
        font.setBold(style.isBold());
        cellStyle.setFont(font);
        if (top) {
            cellStyle.setBorderColor(BorderSide.TOP, style.getBorderColor());
            cellStyle.setBorderTop(style.getBorderSize());
        }
        if (left) {
            cellStyle.setBorderColor(BorderSide.LEFT, style.getBorderColor());
            cellStyle.setBorderLeft(style.getBorderSize());
        }
        if (bottom) {
            cellStyle.setBorderColor(BorderSide.BOTTOM, style.getBorderColor());
            cellStyle.setBorderBottom(style.getBorderSize());
        }
        if (right) {
            cellStyle.setBorderColor(BorderSide.RIGHT, style.getBorderColor());
            cellStyle.setBorderRight(style.getBorderSize());
        }

        return cellStyle;
    }

    /**
     * Creates the par cell style.
     * 
     * @param wb the wb
     * @param style the style
     * @param paramType the param type
     * @return the XSSF cell style
     */
    public static XSSFCellStyle createParCellStyle(XSSFWorkbook wb, ReportStyles style, ParamType paramType) {
        XSSFCellStyle cellStyle = createCellStyle(wb, style, true, true, true, true);
        DataFormat format = wb.createDataFormat();
        switch (paramType) {
        case ALPHA:
            cellStyle.setDataFormat(format.getFormat("\\α\\ \\=\\ #0.0#"));
            break;

        case BETA:
            cellStyle.setDataFormat(format.getFormat("\\β\\ \\=\\ #0.0#"));
            break;

        case LAMBDA:
            cellStyle.setDataFormat(format.getFormat("\\λ\\ \\=\\ #0.0#"));
            break;

        case MU:
            cellStyle.setDataFormat(format.getFormat("\\µ\\ \\=\\ #0.0#"));
            break;

        case SIGMA:
            cellStyle.setDataFormat(format.getFormat("\\σ\\ \\=\\ #0.0#"));
            break;

        case N:
            cellStyle.setDataFormat(format.getFormat("\\n\\ \\=\\ #0.0#"));
            break;

        case P:
        default:
            cellStyle.setDataFormat(format.getFormat("\\p\\ \\=\\ #0.0#"));
            break;
        }

        return cellStyle;
    }

    /**
     * Creates the section.
     * 
     * @param sheet the sheet
     * @param rowIdx the row idx
     * @param nbCol the nb col
     * @param labelsEnumClass the labels enum class
     * @param labelTitle the label title
     * @return the int
     */
    public static int createSection(XSSFSheet sheet, int rowIdx, int nbCol, String labelsEnumClass, String labelTitle) {
        return createSection(sheet, rowIdx, nbCol, labelsEnumClass, labelTitle, "");
    }

    /**
     * Creates the section.
     * 
     * @param sheet the sheet
     * @param rowIdx the row idx
     * @param nbCol the nb col
     * @param labelsEnumClass the labels enum class
     * @param labelTitle the label title
     * @param formulaComplement the formula complement
     * @return the int
     */
    public static int createSection(XSSFSheet sheet, int rowIdx, int nbCol, String labelsEnumClass, String labelTitle, String formulaComplement) {
        ReportUtility.createRow(sheet, rowIdx, nbCol, false, ReportRowHeights.SECTION);
        XSSFCellStyle sectionStyle = ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.SECTION, true, true, true, true);
        ReportUtility.createMergedCells(sheet, rowIdx, rowIdx, 1, nbCol - 2, sectionStyle,
                ReportUtility.getLabelSwitchFormula(labelsEnumClass, labelTitle) + formulaComplement, true);

        createRow(sheet, ++rowIdx, nbCol, false, ReportRowHeights.DEFAULT);
        return ++rowIdx;
    }

    /**
     * Creates the merged cells.
     * 
     * @param sheet the sheet
     * @param firstRow the first row
     * @param lastRow the last row
     * @param firstCol the first col
     * @param lastCol the last col
     * @param cellStyle the cell style
     * @param content the content
     * @param isFormula the is formula
     */
    public static void createMergedCells(XSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, XSSFCellStyle cellStyle,
            String content, boolean isFormula) {
        for (int rowIdx = firstRow; rowIdx < lastRow + 1; rowIdx++) {
            XSSFRow row = sheet.getRow(rowIdx);
            for (int colIdx = firstCol; colIdx < lastCol + 1; colIdx++) {
                if (isFormula) {
                    createFormulaCell(row, colIdx, content, cellStyle);
                } else
                    CellUtil.createCell(row, colIdx, content, cellStyle);
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     * Creates the formula cell.
     * 
     * @param row the row
     * @param colIdx the col idx
     * @param formula the formula
     * @param cellStyle the cell style
     */
    public static void createFormulaCell(Row row, int colIdx, String formula, XSSFCellStyle cellStyle) {
        Cell c = CellUtil.createCell(row, colIdx, "");
        if (cellStyle != null)
            c.setCellStyle(cellStyle);
        c.setCellType(Cell.CELL_TYPE_FORMULA);
        c.setCellFormula(formula);
    }

    /**
     * Creates the date cell.
     * 
     * @param sheet the sheet
     * @param firstRow the first row
     * @param lastRow the last row
     * @param firstCol the first col
     * @param lastCol the last col
     * @param date the date
     */
    public static void createDateCell(XSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, Date date) {
        XSSFCellStyle dateCellStyle = createCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, true, true, true, true);
        CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));
        for (int rowIdx = firstRow; rowIdx < lastRow + 1; rowIdx++) {
            XSSFRow row = sheet.getRow(rowIdx);
            for (int colIdx = firstCol; colIdx < lastCol + 1; colIdx++) {
                Cell c = CellUtil.createCell(row, colIdx, "", dateCellStyle);
                c.setCellValue(date);
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     * Creates the numeric cell.
     * 
     * @param row the row
     * @param colIdx the col idx
     * @param content the content
     * @param cellStyle the cell style
     */
    public static void createNumericCell(Row row, int colIdx, Double content, XSSFCellStyle cellStyle) {
        Cell c = CellUtil.createCell(row, colIdx, "");
        if (cellStyle != null)
            c.setCellStyle(cellStyle);
        if (content != null && !content.isNaN() && !content.isInfinite()) {
            c.setCellType(Cell.CELL_TYPE_NUMERIC);
            c.setCellValue(content.doubleValue());
        }
    }

    /**
     * Creates the string cell.
     * 
     * @param row the row
     * @param colIdx the col idx
     * @param content the content
     * @param cellStyle the cell style
     */
    public static void createStringCell(Row row, int colIdx, String content, XSSFCellStyle cellStyle) {
        Cell c = CellUtil.createCell(row, colIdx, "");
        if (cellStyle != null)
            c.setCellStyle(cellStyle);
        if (content != null)
            c.setCellValue(content);
    }

    /**
     * Creates the parameter cell.
     * 
     * @param row the row
     * @param colIdx the col idx
     * @param distribution the distribution
     * @param content the content
     * @param paramIdx the param idx
     * @param styles the styles
     */
    public static void createParameterCell(Row row, int colIdx, LawType distribution, Double content, int paramIdx,
            Map<ParamType, XSSFCellStyle> styles) {
        XSSFCellStyle paramStyle;
        switch (paramIdx) {
        case 1:
            switch (distribution) {
            case NORMAL_PLAGE:
            case UNIFORM:
            case WEIBULL:
                paramStyle = styles.get(ParamType.ALPHA);
                break;

            case BINOMIAL:
                paramStyle = styles.get(ParamType.N);
                break;

            case POISSON:
            case EXPO:
                paramStyle = styles.get(ParamType.LAMBDA);
                break;

            case RAYLEIGH:
                paramStyle = styles.get(ParamType.SIGMA);
                break;

            case NORMAL_STD:
            case LOG:
            default:
                paramStyle = styles.get(ParamType.MU);
                break;
            }
            break;

        case 2:
            switch (distribution) {
            case NORMAL_PLAGE:
            case UNIFORM:
            case WEIBULL:
                paramStyle = styles.get(ParamType.BETA);
                break;

            case BINOMIAL:
                paramStyle = styles.get(ParamType.P);
                break;

            case NORMAL_STD:
            case LOG:
            default:
                paramStyle = styles.get(ParamType.SIGMA);
                break;
            }
            break;

        default:
            paramStyle = styles.get(ParamType.P);
            break;
        }

        Cell c = CellUtil.createCell(row, colIdx, "");
        if (paramStyle != null)
            c.setCellStyle(paramStyle);
        if (content != null) {
            c.setCellType(Cell.CELL_TYPE_NUMERIC);
            c.setCellValue(content.doubleValue());
        }
    }

    /**
     * Gets the label.
     * 
     * @param labelsEnumClass the labels enum class
     * @param labelTitle the label title
     * @return the label
     */
    @SuppressWarnings("unchecked")
    public static Enum<?> getLabel(String labelsEnumClass, String labelTitle) {
        Enum<?> label = null;
        try {
            @SuppressWarnings("rawtypes")
            final Class<Enum> cl = (Class<Enum>) Class.forName(labelsEnumClass);
            label = Enum.valueOf(cl, labelTitle);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return label;
    }

    /**
     * Gets the label switch formula.
     * 
     * @param labelsEnumClass the labels enum class
     * @param labelTitle the label title
     * @return the label switch formula
     */
    public static String getLabelSwitchFormula(String labelsEnumClass, String labelTitle) {
        Enum<?> formula = getLabel(labelsEnumClass, labelTitle);
        int rowIdx = formula.ordinal() + 1;
        return "IF(\'" + SUMMARY_SHEET + "\'!D6=\"English\",Param!B" + rowIdx + ",Param!A" + rowIdx + ")";
    }

    /**
     * Gets the repository path.
     * 
     * @param calculationType the calculation type
     * @param type the type
     * @return the repository path
     */
    public static String getRepositoryPath(CalculationType calculationType, String type, Date executionDate) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String today = df.format(executionDate);
        String basicPath = System.getenv("TMPDIR") + File.separator + today + File.separator + type + File.separator;
        File directory = new File(basicPath);
        if (!directory.exists())
            directory.mkdirs();
        switch (calculationType) {
        case MONTE_CARLO:

            return basicPath + MONTE_CARLO_PREFIX;

        case SPEC_IT:

            return basicPath + SPEC_IT_PREFIX;

        case ARITHMETIC:

            return basicPath + ARITHMETIC_PREFIX;

        case SEMI_QUADRATIC:

            return basicPath + SEMI_QUADRATIC_PREFIX;

        default:
            break;
        }
        return "";
    }

    /**
     * Generate requirement tree.
     * 
     * @param sheet the sheet
     * @param rowIdx the row idx
     * @param colIdx the col idx
     * @param nbCol the nb col
     * @param motherReqList the mother req list
     * @param styles the styles
     * @param childLinks the child links
     * @return the int
     */
    public static int generateRequirementTree(XSSFSheet sheet, int rowIdx, int colIdx, int nbCol, List<SciMotherRequirementType> motherReqList,
            Map<String, XSSFCellStyle> styles, List<Integer> childLinks) {

        if (styles.isEmpty()) {
            styles.put("reference", ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.REFERENCE, true, true, true, true));
            styles.put("basic", ReportUtility.createCellStyle(sheet.getWorkbook(), ReportStyles.CONTRIBUTOR, true, true, true, true));
        }

        int colIndex = colIdx;
        for (Iterator<SciMotherRequirementType> iterator = motherReqList.iterator(); iterator.hasNext();) {
            SciMotherRequirementType motherReq = iterator.next();

            childLinks.add(new Integer(colIndex + 1));
            if (motherReq.isReferenceRequirement())
                rowIdx = createRequirementTreeCell(sheet, rowIdx, colIndex++, nbCol, motherReq.getCode(), styles.get("reference"), childLinks);
            else
                rowIdx = createRequirementTreeCell(sheet, rowIdx, colIndex++, nbCol, motherReq.getCode(), styles.get("basic"), childLinks);

            List<SciRequirementType> requirements = motherReq.getContributorList(true);
            for (Iterator<SciRequirementType> itContributors = requirements.iterator(); itContributors.hasNext();) {
                SciRequirementType contributor = itContributors.next();
                if (!itContributors.hasNext())
                    childLinks.remove(new Integer(colIndex));
                if (contributor instanceof SciMotherRequirementType) {
                    List<SciMotherRequirementType> motherContribList = new ArrayList<SciMotherRequirementType>();
                    motherContribList.add((SciMotherRequirementType) contributor);
                    rowIdx = generateRequirementTree(sheet, rowIdx, colIndex, nbCol, motherContribList, styles, childLinks);
                } else {
                    if (contributor.isReferenceRequirement())
                        rowIdx = createRequirementTreeCell(sheet, rowIdx, colIndex, nbCol, contributor.getCode(), styles.get("reference"), childLinks);
                    else
                        rowIdx = createRequirementTreeCell(sheet, rowIdx, colIndex, nbCol, contributor.getCode(), styles.get("basic"), childLinks);
                }
            }
            colIndex = colIdx;
        }

        return rowIdx;
    }

    /**
     * Creates the requirement tree cell.
     * 
     * @param sheet the sheet
     * @param rowIdx the row idx
     * @param colIdx the col idx
     * @param nbCol the nb col
     * @param reqCode the req code
     * @param style the style
     * @param childLinks the child links
     * @return the int
     */
    public static int createRequirementTreeCell(XSSFSheet sheet, int rowIdx, int colIdx, int nbCol, String reqCode, XSSFCellStyle style,
            List<Integer> childLinks) {
        XSSFRow row = createRow(sheet, rowIdx++, nbCol, false, ReportRowHeights.STANDARD);
        for (int colIndex = 1; colIndex < nbCol - 1; colIndex++) {
            XSSFCellStyle cellStyle = createCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, false,
                    colIndex == 1 || childLinks.contains(new Integer(colIndex)), false, colIndex == nbCol - 2);
            row.createCell(colIndex).setCellStyle(cellStyle);
        }

        createStringCell(row, colIdx, reqCode, style);

        row = createRow(sheet, rowIdx++, nbCol, false, ReportRowHeights.DEFAULT);
        for (int colIndex = 1; colIndex < nbCol - 1; colIndex++) {
            XSSFCellStyle cellStyle = createCellStyle(sheet.getWorkbook(), ReportStyles.DEFAULT, false,
                    colIndex == 1 || childLinks.contains(new Integer(colIndex)), false, colIndex == nbCol - 2);
            row.createCell(colIndex).setCellStyle(cellStyle);
        }

        return rowIdx;
    }

    /**
     * Formate report date.
     * 
     * @param paramDate the param date
     * @return the string
     */
    public static String formateReportDate(Date paramDate) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
        return df.format(paramDate);
    }

    /**
     * Method to perform formula and contributors replacement per the multi-level Monte Carlo strategy:
     * <p>
     * If the level is not a leaf:<br>
     * a. Compute absolute formula: Requirement IDs are replaced by child levels’ formulas.<br>
     * b. Run One Level Monte Carlo simulation with this absolute formula.
     * </p>
     * 
     * @param sciMotherRequirementType the sci mother requirement type
     * @param isMonteCarlo the is monte carlo
     * @return the sci mother requirement type
     */
    public static SciMotherRequirementType performFormulaReplacement(SciMotherRequirementType sciMotherRequirementType, boolean isMonteCarlo) {
        // Hashset to collect the leaf contributors of a given intermediate requirement.
        HashSet<SciRequirementType> leafContributors = new HashSet<SciRequirementType>();
        logger.debug("Original report formula of SciMotherRequirement \"" + sciMotherRequirementType.getTitle() + "\" -----> "
                + sciMotherRequirementType.getTransferModel().getReportFormula());
        // Iterate over the intermediate requirement for formula replacements etc.
        for (SciRequirementType lowerContributor : sciMotherRequirementType.getContributorList(isMonteCarlo)) {
            if (lowerContributor instanceof SciMotherRequirementType) {
                String reportFormula = sciMotherRequirementType.getTransferModel().getReportFormula();
                // Perform replacement of contributor code with its actual formula.
                if (reportFormula.contains(lowerContributor.getCode())) {
                    // Parenthesis are added around the actual formula.
                    reportFormula = reportFormula.replace(lowerContributor.getCode(), " ( "
                            + ((SciMotherRequirementType) lowerContributor).getTransferModel().getReportFormula() + " ) ");
                    // The formula is set into the sciMotherRequirementType
                    sciMotherRequirementType.getTransferModel().setReportFormula(reportFormula);
                }
                // Add the leaf contributors to the hashset.
                leafContributors.addAll(((SciMotherRequirementType) lowerContributor).getContributorList());
            }
        }
        logger.debug("Replaced report formula of SciMotherRequirement \"" + sciMotherRequirementType.getTitle() + "\" -----> "
                + sciMotherRequirementType.getTransferModel().getReportFormula());
        // Below we set the leaf contributors to the current sciMotherRequirment type.
        // Thus its previous contributors will be replaced by leaf contributors.
        if (!leafContributors.isEmpty()) {
            // Ensure that if the current (intermediate) sciMotherRequirementType has any leaf contributors, they too get added to the
            // leafContributors hashset.
            for (SciRequirementType lowerContributor : sciMotherRequirementType.getContributorList()) {
                if (!(lowerContributor instanceof SciMotherRequirementType)) {
                    leafContributors.add(lowerContributor);
                }
            }
            // Now add all of the leaf contributors collected from all over below this SciMotherRequirementType to it.
            ArrayList<SciRequirementType> leafContributorsList = new ArrayList<SciRequirementType>();
            for (SciRequirementType sciRequirementType : leafContributors) {
                leafContributorsList.add(sciRequirementType);
            }
            sciMotherRequirementType.setContributorList(leafContributorsList);
        }
        return sciMotherRequirementType;
    }

}
