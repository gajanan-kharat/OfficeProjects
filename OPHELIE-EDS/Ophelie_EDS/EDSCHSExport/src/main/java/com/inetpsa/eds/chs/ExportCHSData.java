package com.inetpsa.eds.chs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This class read and export CHS reference data from CHS database to output file
 * 
 * @author Patrick MADELA
 */
public final class ExportCHSData {
    /**
     * Logger log4j to write messages
     */
    private static final Logger LOGGER = Logger.getLogger(ExportCHSData.class);

    /** Column name for INTEPN */
    private static final String CHS_COMPONEN_INTEPN_COLUMN = "INTEPN";

    /** Column name for DESCRIPT */
    private static final String CHS_COMPONEN_DESCRIPT_COLUMN = "DESCRIPT";

    /** Column name for USERF##1 */
    private static final String CHS_COMPONEN_USERF1_COLUMN = "USERF##1";

    /** Column name for USERF##2 */
    private static final String CHS_COMPONEN_USERF2_COLUMN = "USERF##2";

    /** Column name for CAVITYQT */
    private static final String CHS_COMPONEN_CAVITYQT_COLUMN = "CAVITYQT";

    /** Column name for COMPONEN_ID */
    private static final String CHS_COMPONEN_COMPONEN_ID_COLUMN = "COMPONEN_ID";

    /** Column name for CAVITY_ID */
    private static final String CHS_CAVITY_CAVITY_ID_COLUMN = "CAVITY_ID";

    /** Column name for CONN_NAME */
    private static final String CHS_CAVITY_CONN_NAME_COLUMN = "CONN_NAME";

    /** Column name for CAVITY */
    private static final String CHS_CAVITY_CAVITY_COLUMN = "CAVITY";

    /** Column name for PINYPE */
    private static final String CHS_CAVITY_PINTYPE_COLUMN = "PINTYPE";

    /** Name of the configuration file to include in the class path */
    private static final String CONFIG = "config.properties";

    /** CSV column separator */
    private static final String CSV_SEPARATOR = ";";

    /** Line separator in files */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /** Properties of the configuration file */
    private static Properties configuration = new Properties();

    /** List of device current */
    private final List<CHS> deviceCurrents = new ArrayList<CHS>();

    /** List of cavities */
    private final List<Cavity> cavities = new ArrayList<Cavity>();

    /** Connection to the database */
    private Connection dbConnection;

    static {
        try {
            URL configFileURL = ExportCHSData.class.getClassLoader().getResource(CONFIG);

            if (configFileURL == null) {
                LOGGER.error("Unable to find " + CONFIG + "in the classpath");
            }
            InputStream input = new FileInputStream(new File(configFileURL.toURI()));
            configuration.load(input);
        } catch (IOException e) {
            LOGGER.error("Unable to load configuration file", e);
        } catch (URISyntaxException e) {
            LOGGER.error("Unable to load configuration file", e);
        }
    }

    /**
     * An util class must have a private constructor
     */
    private ExportCHSData() {

    }

    /**
     * Launch the batch
     * 
     * @param args Arguments from the command line
     */
    public static void main(String[] args) {
        LOGGER.info("START BATCH EXPORT CHS REFERENCE");
        ExportCHSData exporter = new ExportCHSData();
        exporter.export();
        LOGGER.info("END BATCH EXPORT CHS REFERENCE");
    }

    /**
     * Export CHS data to CSV files
     */
    private void export() {
        deviceCurrents.clear();
        cavities.clear();
        Statement stmt = null;
        try {
            stmt = getDBConnection().createStatement();
            LOGGER.info("Read datas from CHS database start");
            // Step 1 : read all device current
            loadDeviceCurrent(stmt);
            // Step 2 : read all the cavities
            loadCavities(stmt);
            // Step 3 : write the csv files
            writeFiles();
        } catch (SQLException se) {
            // Handle errors for JDBC
            LOGGER.error("Error when reading datas from CHS database", se);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                LOGGER.error("Error when closing database", e);
            }
        }

    }

    /**
     * Load the devices current from CHS database
     * 
     * @param stmt statement used to execute query
     * @throws SQLException if a database access error occurs, this method is called on a closed Statement or the given SQL statement produces
     *             anything other than a single ResultSet object
     */
    private void loadDeviceCurrent(Statement stmt) throws SQLException {
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT CHS.Componen.INTEPN, CHS.Componen.DESCRIPT, CHS.Componen.USERF##1, CHS.Componen.USERF##2, CHS.Componen.CAVITYQT, CHS.Componen.COMPONEN_ID");
            sql.append(" FROM CHS.Componen");
            sql.append(" WHERE CHS.Componen.PNStatus = 'Current' AND CHS.Componen.GRPNAME = 'Device'");
            LOGGER.debug("Execute: " + sql.toString());
            rs = stmt.executeQuery(sql.toString());

            // Extract data from result set
            while (rs.next()) {

                String intepn = rs.getString(CHS_COMPONEN_INTEPN_COLUMN).trim();
                String descript = rs.getString(CHS_COMPONEN_DESCRIPT_COLUMN).trim();
                String userf1 = rs.getString(CHS_COMPONEN_USERF1_COLUMN).trim();
                String userf2 = rs.getString(CHS_COMPONEN_USERF2_COLUMN).trim();
                String describeCav = rs.getString(CHS_COMPONEN_CAVITYQT_COLUMN);
                if (describeCav != null) {
                    describeCav = describeCav.trim();
                }
                String componenId = rs.getString(CHS_COMPONEN_COMPONEN_ID_COLUMN).trim();
                deviceCurrents.add(new CHS(intepn, descript, userf1, userf2, describeCav, componenId));
            }
            LOGGER.info("Device current in Database : " + deviceCurrents.size());
        } catch (SQLException se) {
            throw new SQLException("Error when reading device current", se);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                LOGGER.error("Error when closing request", e);
            }
        }
    }

    /**
     * Load the cavities from CHS database
     * 
     * @param stmt statement used to execute query
     * @throws SQLException if a database access error occurs, this method is called on a closed Statement or the given SQL statement produces
     *             anything other than a single ResultSet object
     */
    private void loadCavities(Statement stmt) throws SQLException {
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT CHS.Componen.COMPONEN_ID, CHS.Componen.INTEPN,  CHS.LibraryFootPrintPinMapping.CONN_NAME, CHS.LibraryFootPrintPinMapping.CAVITY_ID, CHS.LibraryFootPrintPinMapping.PIN_ID, CHS.IRREGCAV.CAVITY, CHS.IRREGCAV.PINTYPE");
            sql.append(" FROM CHS.Componen, CHS.LibraryDeviceFootprint, CHS.LibraryFootPrintPinMapping, CHS.IRREGCAV");
            sql.append(" WHERE CHS.Componen.COMPONEN_ID =  CHS.LibraryDeviceFootprint.COMPONEN_ID");
            sql.append(" AND CHS.LibraryDeviceFootprint.LibraryDeviceFootprint_ID = CHS.LibraryFootPrintPinMapping.LibraryDeviceFootprint_ID");
            sql.append(" AND CHS.IRREGCAV.IRREGCAV_ID = CHS.LibraryFootPrintPinMapping.pin_ID");
            sql.append(" AND CHS.Componen.PNStatus = 'Current'");
            sql.append(" AND CHS.Componen.GRPNAME = 'Device'");
            LOGGER.debug("Execute: " + sql.toString());
            rs = stmt.executeQuery(sql.toString());

            while (rs.next()) {
                String id = rs.getString(CHS_COMPONEN_COMPONEN_ID_COLUMN).trim();
                String intepn = rs.getString(CHS_COMPONEN_INTEPN_COLUMN).trim();
                String connName = rs.getString(CHS_CAVITY_CONN_NAME_COLUMN).trim();
                String cavityId = rs.getString(CHS_CAVITY_CAVITY_ID_COLUMN).trim();
                String cavity = rs.getString(CHS_CAVITY_CAVITY_COLUMN).trim();
                String pinType = rs.getString(CHS_CAVITY_PINTYPE_COLUMN).trim();

                cavities.add(new Cavity(id, intepn, connName, cavityId, cavity, pinType));
            }
            LOGGER.info("Cavities in Database : " + cavities.size());
        } catch (SQLException se) {
            throw new SQLException("Error when reading cavities", se);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                LOGGER.error("Error when closing request", e);
            }
        }
    }

    /**
     * Write the output csv files
     */
    private void writeFiles() {
        writeDeviceCurrent();
        writeCavities();
    }

    /**
     * Write the output csv for device current
     */
    private void writeDeviceCurrent() {
        LOGGER.info("Write devices current to file");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(configuration.getProperty("chs_file_path_list_device_current")));
            bw.write(CHS.getHeader());
            for (CHS deviceCurrent : deviceCurrents) {
                bw.write(deviceCurrent.getLine());
            }
        } catch (IOException e) {
            LOGGER.error("Unable to write devices current file", e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    LOGGER.error("Error when closing writer", e);
                }
            }
        }
    }

    /**
     * Write the output csv for cavities
     */
    private void writeCavities() {
        LOGGER.info("Write cavities to file");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(configuration.getProperty("chs_file_path_list_cavity")));
            bw.write(Cavity.getHeader());
            for (Cavity cavity : cavities) {
                bw.write(cavity.getLine());
            }
        } catch (IOException e) {
            LOGGER.error("Unable to write cavities file", e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    LOGGER.error("Error when closing writer", e);
                }
            }
        }
    }

    /**
     * Provide database connection
     * 
     * @return Connection to the specified database
     */
    private Connection getDBConnection() {
        LOGGER.info("Database connection initialization");
        try {
            if (dbConnection == null) {
                Class.forName(configuration.getProperty("JDBC_DRIVER"));
                dbConnection = DriverManager.getConnection(configuration.getProperty("DB_URL"), configuration.getProperty("USER"),
                        configuration.getProperty("PASSWORD"));
                dbConnection.setAutoCommit(true);
                LOGGER.info("Database connection initialization successful");
            } else {
                LOGGER.info("Database connection re-used");
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            LOGGER.info("Database connection initialization error", se);
        } catch (ClassNotFoundException e) {
            LOGGER.error("The class for JDBC driver defined in configuration file cannot be located", e);
        }
        return dbConnection;
    }

    /**
     * CHS's device current simple model
     */
    private static final class CHS {
        private final String intepn;
        private final String descript;
        private final String userf1;
        private final String userf2;
        private final String cavityqt;
        private final String componenId;

        private CHS(String intepn, String descript, String userf1, String userf2, String cavityqt, String componenId) {
            this.intepn = intepn;
            this.descript = descript;
            this.userf1 = userf1;
            this.userf2 = userf2;
            this.cavityqt = cavityqt;
            this.componenId = componenId;
        }

        private String getLine() {
            StringBuilder builder = new StringBuilder();
            builder.append(intepn).append(CSV_SEPARATOR);
            builder.append(descript).append(CSV_SEPARATOR);
            builder.append(userf1).append(CSV_SEPARATOR);
            builder.append(userf2).append(CSV_SEPARATOR);
            builder.append(cavityqt).append(CSV_SEPARATOR);
            builder.append(componenId).append(LINE_SEPARATOR);
            return builder.toString();
        }

        private static String getHeader() {
            StringBuilder builder = new StringBuilder();
            builder.append(CHS_COMPONEN_INTEPN_COLUMN).append(CSV_SEPARATOR);
            builder.append(CHS_COMPONEN_DESCRIPT_COLUMN).append(CSV_SEPARATOR);
            builder.append(CHS_COMPONEN_USERF1_COLUMN).append(CSV_SEPARATOR);
            builder.append(CHS_COMPONEN_USERF2_COLUMN).append(CSV_SEPARATOR);
            builder.append(CHS_COMPONEN_CAVITYQT_COLUMN).append(CSV_SEPARATOR);
            builder.append(CHS_COMPONEN_COMPONEN_ID_COLUMN).append(LINE_SEPARATOR);
            return builder.toString();
        }
    }

    /**
     * CHS's cavity simple model.
     */
    private static final class Cavity {
        private final String id;
        private final String intepn;
        private final String connName;
        private final String cavityId;
        private final String cavity;
        private final String pinType;

        private Cavity(String id, String intepn, String connName, String cavityId, String cavity, String pinType) {
            this.id = id;
            this.intepn = intepn;
            this.connName = connName;
            this.cavityId = cavityId;
            this.cavity = cavity;
            this.pinType = pinType;
        }

        private String getLine() {
            StringBuilder builder = new StringBuilder();
            builder.append(id).append(CSV_SEPARATOR);
            builder.append(intepn).append(CSV_SEPARATOR);
            builder.append(connName).append(CSV_SEPARATOR);
            builder.append(cavityId).append(CSV_SEPARATOR);
            builder.append(cavity).append(CSV_SEPARATOR);
            builder.append(pinType).append(LINE_SEPARATOR);
            return builder.toString();
        }

        private static String getHeader() {
            StringBuilder builder = new StringBuilder();
            builder.append(CHS_COMPONEN_COMPONEN_ID_COLUMN).append(CSV_SEPARATOR);
            builder.append(CHS_COMPONEN_INTEPN_COLUMN).append(CSV_SEPARATOR);
            builder.append(CHS_CAVITY_CONN_NAME_COLUMN).append(CSV_SEPARATOR);
            builder.append(CHS_CAVITY_CAVITY_ID_COLUMN).append(CSV_SEPARATOR);
            builder.append(CHS_CAVITY_CAVITY_COLUMN).append(CSV_SEPARATOR);
            builder.append(CHS_CAVITY_PINTYPE_COLUMN).append(LINE_SEPARATOR);
            return builder.toString();
        }
    }
}
