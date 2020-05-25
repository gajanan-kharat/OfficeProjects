/*
 * @(#)Constants.java 1.4 Nov 19, 2014
 * CopyRight : The PSA Company
 * 
 * Creation : Nov 19, 2014
 */
package com.inetpsa.oaz00.ws.scilab.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Class to hold all constants used throughout the application.
 * 
 * @author Geometric ltd.
 */
public class Constants {

    /** The empty string constant. */
    public static final String EMPTY_STRING = "";
    /** The list of mathematical operators */
    public static final String OPR_PLUS = "+";
    public static final String OPR_MINUS = "-";
    public static final String OPR_MUL = "*";
    public static final String OPR_DIV = "/";
    public static final String OPR_POW = "^";

    /** The special symbols, separators and mathematical defined constants */
    public static final String SYMBOL_HASH = "#";
    public static final String SYMBOL_PI = "%pi";
    public static final String SYMBOL_E = "%e";
    public static final String SEPARATOR_COMMA = ",";
    /** The mathematical defined functions */
    public static final String FUN_ABS = "abs";
    public static final String FUN_ASINH = "asinh";
    public static final String FUN_ACOSH = "acosh";
    public static final String FUN_ATANH = "atanh";
    public static final String FUN_ASIN = "asin";
    public static final String FUN_ACOS = "acos";
    public static final String FUN_ATAN = "atan";
    public static final String FUN_SINH = "sinh";
    public static final String FUN_COSH = "cosh";
    public static final String FUN_TANH = "tanh";
    public static final String FUN_SIN = "sin";
    public static final String FUN_COS = "cos";
    public static final String FUN_TAN = "tan";
    public static final String FUN_CEIL = "ceil";
    public static final String FUN_ROUND = "round";
    public static final String FUN_FLOOR = "floor";
    public static final String FUN_FIX = "fix";
    public static final String FUN_EXP = "exp";
    public static final String FUN_INT = "int";
    public static final String FUN_LOG10 = "log10";
    public static final String FUN_LOG = "log";
    public static final String FUN_MIN = "min";
    public static final String FUN_MAX = "max";
    public static final String FUN_MODULO = "modulo";
    public static final String FUN_SQRT = "sqrt";
    /** Set of defined mathematical functions */
    public static final Set<String> setOfFuns = new LinkedHashSet<String>();
    public static final List<String> listOfMathOprs = new ArrayList<String>();
    static {
        // Adding the list of defined mathematical functions into the set
        setOfFuns.add(FUN_ABS);
        setOfFuns.add(FUN_ASINH);
        setOfFuns.add(FUN_ACOSH);
        setOfFuns.add(FUN_ATANH);
        setOfFuns.add(FUN_ASIN);
        setOfFuns.add(FUN_ACOS);
        setOfFuns.add(FUN_ATAN);
        setOfFuns.add(FUN_SINH);
        setOfFuns.add(FUN_COSH);
        setOfFuns.add(FUN_TANH);
        setOfFuns.add(FUN_SIN);
        setOfFuns.add(FUN_COS);
        setOfFuns.add(FUN_TAN);
        setOfFuns.add(FUN_CEIL);
        setOfFuns.add(FUN_ROUND);
        setOfFuns.add(FUN_FLOOR);
        setOfFuns.add(FUN_FIX);
        setOfFuns.add(FUN_EXP);
        setOfFuns.add(FUN_INT);
        setOfFuns.add(FUN_LOG10);
        setOfFuns.add(FUN_LOG);
        setOfFuns.add(FUN_MIN);
        setOfFuns.add(FUN_MAX);
        setOfFuns.add(FUN_MODULO);
        setOfFuns.add(FUN_SQRT);
        // Adding the list of defined mathematical operator in the list
        listOfMathOprs.add(OPR_PLUS);
        listOfMathOprs.add(OPR_MINUS);
        listOfMathOprs.add(OPR_MUL);
        listOfMathOprs.add(OPR_DIV);
        listOfMathOprs.add(OPR_POW);
    }

    /** The protocol which is used for the web service. */
    public static final String PROTOCOL = "http://";

    /** To seperate the file. */
    public static final String URL_SEPERATOR_CHAR = "/";

    /** Properties for half connector business application. */
    private static Properties HALF_CONNECTOR_PROPERTIES;

    /** Properties for external configuration of half connector. */
    private static Properties HALF_CONNECTOR_SERVER_CONFIG_PROPERTIES;
    /** VCOPLMPSA-29485: Code modification START. Date: 11-November-2016 */
    public static final String MU_FUN = "µ = ";
    /** VCOPLMPSA-29485: Code modification END. Date: 11-November-2016 */
    static {
        try {
            HALF_CONNECTOR_PROPERTIES = new Properties();
            // Loading the property file.
            HALF_CONNECTOR_PROPERTIES.load(Constants.class.getResourceAsStream("/toolboxxml-InterfaceOasis.properties"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            HALF_CONNECTOR_SERVER_CONFIG_PROPERTIES = new Properties();
            // File warPath = new File(FunctionalLog.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            // String propertiesPath = warPath.getParent().substring(0, warPath.getParent().indexOf("WEB-INF"));
            // propertiesPath = propertiesPath.replaceAll("%20", "\u0020");
            // propertiesPath = propertiesPath + "ServerConfig.properties";
            File propFile = new File(System.getProperty("OazConfigDir") + File.separator + "ServerConfig.properties");
            // Loading the property file.
            HALF_CONNECTOR_SERVER_CONFIG_PROPERTIES.load(new FileInputStream(propFile));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Method to get the reference to half connector properties.
     * 
     * @return HALF_CONNECTOR_PROPERTIES
     */
    public static Properties getHalfConnectorProperties() {
        return HALF_CONNECTOR_PROPERTIES;
    }

    /**
     * Method to get the reference to half connector server config properties.
     * 
     * @return
     */
    public static Properties getHalfConnectorServerConfigProperties() {
        return HALF_CONNECTOR_SERVER_CONFIG_PROPERTIES;
    }

}
