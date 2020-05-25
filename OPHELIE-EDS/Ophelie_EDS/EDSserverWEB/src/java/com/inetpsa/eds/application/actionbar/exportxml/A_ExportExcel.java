/*
 * Creation : 26 mai 2015
 */
package com.inetpsa.eds.application.actionbar.exportxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Abstract excel export class, based on the one from com.inetpsa.outils.importexport.exports.
 * 
 * @author G-VILLEREZ
 */
public abstract class A_ExportExcel {

    /**
     * Excel export exception.
     * 
     * @author G-VILLEREZ
     */
    public class ExcelExportException extends Exception {

        private static final long serialVersionUID = -2253408133619854248L;

        public ExcelExportException(String message) {
            super(message);
        }

    }

    // PUBLIC
    /**
     * Export constructor.
     * 
     * @param source The source file, this file will be copied and used as a template.
     * @param destination The destination file. If the file already exist it will be overwritten.
     */
    public A_ExportExcel(File source, OutputStream destination) {
        this.source = source;
        this.destination = destination;
        init();
    }

    /**
     * Write the content into the destination file, using the template file as template.
     * 
     * @throws IOException
     * @throws InvalidFormatException
     * @throws ExcelExportException
     */
    public void write() throws IOException, InvalidFormatException, ExcelExportException {
        InputStream is = new FileInputStream(source);
        workbook = WorkbookFactory.create(is);
        evaluator = workbook.getCreationHelper().createFormulaEvaluator();

        writeContent();

        workbook.write(destination);
    }

    // PROTECTED
    protected Workbook workbook;
    protected FormulaEvaluator evaluator;
    protected File source;
    protected OutputStream destination;
    private DataFormat format;

    /**
     * Abstract method used to write the content of the Excel file.
     * 
     * @throws IOException
     * @throws ExcelExportException
     */
    protected abstract void writeContent() throws IOException, ExcelExportException;

    /**
     * Return a cell from the sheet.
     * 
     * @param sheet The sheet to use.
     * @param colNum Column number.
     * @param rowNum Row number.
     * @return The cell
     */
    protected Cell getCell(Sheet sheet, int colNum, int rowNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }

        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }

        return cell;
    }

    /**
     * Copy a zone style to another zone.
     * 
     * @param sheet The sheet to use.
     * @param zoneCol The start column zone.
     * @param zoneRow The start row zone.
     * @param zoneHeight The height of the zone.
     * @param zoneWidth The width of the zone.
     * @param destCol The destination column.
     * @param destRow The destination row.
     */
    protected void copyZoneStyle(Sheet sheet, int zoneCol, int zoneRow, int zoneHeight, int zoneWidth, int destCol, int destRow) {
        for (int i = 0; i < zoneWidth; i++) {
            for (int j = 0; j < zoneHeight; j++) {
                applyCellStyle(sheet, destCol + i, destRow + j, getCellStyle(sheet, zoneCol + i, zoneRow + j));
            }
        }
    }

    /**
     * @param sheet
     * @param colNum1
     * @param rowNum1
     * @param colNum2
     * @param rowNum2
     */
    protected void copyCellStyle(Sheet sheet, int colNum1, int rowNum1, int colNum2, int rowNum2) {
        applyCellStyle(sheet, colNum2, rowNum2, getCellStyle(sheet, colNum1, rowNum1));
    }

    /**
     * Apply a given style to a cell.
     * 
     * @param sheet The sheet to use.
     * @param colNum The column to use.
     * @param rowNum The row to use.
     * @param style The cell style to apply.
     */
    protected void applyCellStyle(Sheet sheet, int colNum, int rowNum, CellStyle style) {
        getCell(sheet, colNum, rowNum).setCellStyle(style);
    }

    /**
     * Get the style used by a cell.
     * 
     * @param sheet The sheet to use.
     * @param colNum The column number.
     * @param rowNum the row number.
     * @return
     */
    protected CellStyle getCellStyle(Sheet sheet, int colNum, int rowNum) {
        return getCell(sheet, colNum, rowNum).getCellStyle();
    }

    /**
     * Write in a cell a given value.
     * 
     * @param sheet The sheet to use.
     * @param colNum Column number.
     * @param rowNum Row number.
     * @param value Value to write.
     * @throws ExcelExportException
     */
    protected void writeCell(Sheet sheet, int colNum, int rowNum, Object value) throws ExcelExportException {
        writeCell(getCell(sheet, colNum, rowNum), value);
    }

    /**
     * Write a row with the given values.
     * 
     * @param sheet The sheet to use.
     * @param rowNum The row number.
     * @param colStart The column where to start.
     * @param rowContent The row content.
     * @throws ExcelExportException
     */
    protected void writeRow(Sheet sheet, int rowNum, int colStart, Object[] rowContent) throws ExcelExportException {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }

        for (int colNum = 0; colNum < rowContent.length; colNum++) {
            Cell cell = row.getCell(colStart + colNum);
            if (cell == null) {
                cell = row.createCell(colStart + colNum);
            }

            writeCell(cell, rowContent[colNum]);
        }
    }

    protected String getCellStringValue(Sheet sheet, int rowNum, int colNum) {
        return getCell(sheet, colNum, rowNum).getStringCellValue();
    }

    // PRIVATE
    private void init() {
        this.workbook = null;
        this.evaluator = null;
    }

    /**
     * Write in the given cell.
     * 
     * @param cell The cell where to write.
     * @param value What to write in the cell. Supported type are string, float, integer, double, date and boolean. Any other type will throw an
     *            ExcelExportException exception.
     * @throws ExcelExportException
     */
    protected void writeCell(Cell cell, Object value) throws ExcelExportException {
        if (value == null) {
            cell.setCellValue((String) null);
        } else {
            String type = getShortClassName(value.getClass().getName()).toLowerCase();
            if (type.equals("string")) {
                cell.setCellValue((String) value);
            } else if (type.equals("float")) {
                cell.setCellValue(BigDecimal.valueOf((Float) value).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
            } else if (type.equals("integer")) {
                cell.setCellValue((Integer) value);
            } else if (type.equals("double")) {
                cell.setCellValue(BigDecimal.valueOf((Double) value).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
            } else if (type.equals("date")) {
                cell.setCellValue((Date) value);
            } else if (type.equals("boolean")) {
                cell.setCellValue((Boolean) value);
            } else {
                throw new ExcelExportException("type " + type + " is not recognized. Please convert before");
            }
        }
    }

    /**
     * Utils class, use to get the classname from a string.
     * 
     * @param className
     * @return
     */
    public static String getShortClassName(String className) {
        int ind = className.lastIndexOf(".");
        if (ind < 0) {
            return className;
        }

        return className.substring(ind + 1);
    }

}
