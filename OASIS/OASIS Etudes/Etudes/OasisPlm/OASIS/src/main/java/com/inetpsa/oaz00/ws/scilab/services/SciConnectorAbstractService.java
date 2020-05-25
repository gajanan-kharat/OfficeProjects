/*
 * Author: U224106
 * Creation: 18 déc. 2014
 */
package com.inetpsa.oaz00.ws.scilab.services;

import java.util.List;

import javasci.SciDoubleArray;
import javasci.SciString;
import javasci.SciStringArray;
import javasci.Scilab;

import org.apache.log4j.Logger;

import com.inetpsa.oaz00.ws.scilab.utils.Constants;

/**
 * The Class ScilabConnector.
 * 
 * @author U224106
 */
public abstract class SciConnectorAbstractService implements SciCalculationService {

    private static final String DEFAULT_STACK_SIZE = "5000000";

    /** The debug mode. */
    private int debug = 0;

    /** The Scilab temporary directory. */
    protected String sciTmpDir;

    /** The logger. */
    private static Logger logger = Logger.getLogger(SciConnectorAbstractService.class);

    /**
     * Instantiates a new Scilab connector.
     */
    public SciConnectorAbstractService() {
        initialize();
    }

    /**
     * Initialize.
     */
    private void initialize() {
        String systTmpDir = System.getenv("TMPDIR");
        if (null == systTmpDir || systTmpDir.isEmpty()) {
            logger.fatal("SCILAB systTmpDir is empty. Please set the TMPDIR environment variable to something like \\TEMP\\oasis_tmp.");
            System.exit(1);
        }
        String stacksize = DEFAULT_STACK_SIZE;
        stacksize = Constants.getHalfConnectorServerConfigProperties().getProperty("stacksize");
        // Initialization of Scilab
        String cmd = "stacksize(" + stacksize + ");global Glob;";
        Scilab.Exec(cmd, debug);

        // Overwriting default Scilab temporary directory by the specific one
        SciString sciTmpD = new SciString("SCITMPDIR");
        new SciString("TMPDIR", systTmpDir);
        Scilab.Exec("SCITMPDIR=TMPDIR");
        sciTmpDir = sciTmpD.getData();

    }

    /**
     * Gets the System temporary directory.
     * 
     * @return the System temporary directory
     */
    public String getTmpDir() {
        return sciTmpDir;
    }

    /**
     * Evaluate command.
     * 
     * @param job the job
     * @return true, if successful
     */
    public boolean evalCommand(String job) {
        return Scilab.ExecLong(job);
    }

    /**
     * Clean variable.
     * 
     * @param varName the variable name
     * @return true, if successful
     */
    public boolean cleanVariable(String varName) {
        return Scilab.ExecLong("clear(''" + varName + "'')");
    }

    /**
     * Clean variables.
     * 
     * @param varNames the variable names
     * @return true, if successful
     */
    public boolean cleanVariables(String[] varNames) {
        boolean res = true;
        for (int i = 0; i < varNames.length; i++) {
            res = res && Scilab.ExecLong("clear(''" + varNames[i] + "'')");
        }
        return res;
    }

    /**
     * Convert an array of Double into a double array.
     * 
     * @param dArray the d array
     * @return the double[]
     */
    public static double[] doubleArray(Double[] dArray) {
        double[] res = new double[dArray.length];

        for (int i = 0; i < dArray.length; i++) {
            Double bg = dArray[i];
            if (bg == null)
                res[i] = Double.NaN;
            else
                res[i] = bg.doubleValue();
        }

        return res;
    }

    /**
     * Convert an array of Double into a Scilab double array.
     * 
     * @param name the name
     * @param dArray the array of Double
     * @return the SciDoubleArray
     */
    public static SciDoubleArray scilabDoubleArray(String name, Double[] dArray) {
        return new SciDoubleArray(name, dArray.length, 1, doubleArray(dArray));
    }

    /**
     * Convert a List of String into a Scilab String array.
     * 
     * @param name the name
     * @param list the list of String
     * @return the SciStringArray
     */
    public static SciStringArray scilabStringArray(String name, List<String> list) {
        Object[] oArray = list.toArray();
        String[] res = new String[oArray.length];
        if (list.size() > 0) {
            for (int i = 0; i < oArray.length; i++) {
                res[i] = oArray[i].toString();
            }
        }
        return scilabStringArray(name, res);
    }

    /**
     * Convert an array of String into a Scilab String array.
     * 
     * @param name the name
     * @param sArray the array of String
     * @return the SciStringArray
     */
    public static SciStringArray scilabStringArray(String name, String[] sArray) {
        boolean ok = true;

        // Scilab Bug when string exceeds the max allowed length
        for (int i = 0; i < sArray.length; i++) {
            if (sArray[i].length() > 400) {
                ok = false;
                break;
            }
        }
        if (ok) {
            SciStringArray sS = new SciStringArray(name, sArray.length, 1, sArray);
            return sS;
        }
        Scilab.Exec("matTmpJava=[]");

        // Initialization of the returned Matrix
        SciStringArray sS = new SciStringArray(name, sArray.length, 1);

        for (int i = 0; i < sArray.length; i++) {
            // Reinitialization of the varTmp variable
            Scilab.Exec("varTmpJava=''''");
            String sTmp = sArray[i];
            while (sTmp.length() > 400) {
                Scilab.Exec("varTmpJava=varTmpJava+''" + sTmp.substring(0, 400) + "''");
                sTmp = sTmp.substring(400);
            }
            Scilab.Exec("varTmpJava=varTmpJava+''" + sTmp + "''");
            Scilab.Exec("matTmpJava(" + (i + 1) + ")=varTmpJava");
        }
        Scilab.Exec(name + "=matTmpJava;clear matTmpJava;clear varTmpJava");
        sS.getData();
        return sS;
    }

    /**
     * Normalize file name to prevent OS exceptions.
     * 
     * @param fileName the file name
     * @return the string
     */
    public static String normalizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9\\._]+", "_");
    }

}