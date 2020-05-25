/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.tools;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class PSATECSVFileCreator {
    private String _strFileName = "";
    private Writer _csvWriter;

    public PSATECSVFileCreator(String strFile) {
        _strFileName = strFile;
    }

    public int PrepareCSVFile() {
        int nRet = 0;
        try {
            _csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(_strFileName), "ISO-8859-1"));
        } catch (Exception e) {
            System.out.println("Error to open file " + e);
            nRet = -1;
        }

        return nRet;
    }

    public void writeHeader(String strHeader) {
        try {
            _csvWriter.write(strHeader + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Writing the header failed !!!");
        }
    }

    public void writeLine(String strLine) {
        try {
            _csvWriter.write(strLine + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Writing the line failed !!!");
        }
    }

    public void closeFile() {
        try {
            if (_csvWriter != null)
                _csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in closing the file ..");
        }
    }
}
