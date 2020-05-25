package com.inetpsa.eds.sap;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * This class read data from input file containing SAP reference data and import in EDS database.
 * 
 * @author priyankamm
 */
public class ReadSapData {
    /**
     * Logger log4j to write messages
     */
    private static final Logger LOGGER = Logger.getLogger(ReadSapData.class);

    /** Name of the configuration file to include in the class path */
    private static final String CONFIG = "config.properties";

    /** Set of SAP reference */
    private Set<String> sapData = new HashSet<String>();

    /** Properties of the configuration file */
    private static Properties prop = new Properties();

    /** Connection to the database */
    private Connection dbConnection;

    static {
        try {
            URL configFileURL = ReadSapData.class.getClassLoader().getResource(CONFIG);
            if (configFileURL == null) {
                LOGGER.error("Unable to find " + CONFIG + "in the classpath");
            }
            InputStream input = new FileInputStream(configFileURL.getFile());
            prop.load(input);
        } catch (IOException e) {
            LOGGER.error("Unable to load configuration file", e);
        }
    }

    /**
     * Provide database connection
     * 
     * @return Connection to the specified database
     */
    public Connection getDBConnection() {
        LOGGER.info("Database connection initialization");

        try {
            Class.forName(prop.getProperty("JDBC_DRIVER"));
        } catch (ClassNotFoundException e) {
            LOGGER.error("The class for JDBC driver defined in configuration file cannot be located", e);
        }

        try {
            if (dbConnection == null) {
                dbConnection = DriverManager.getConnection(prop.getProperty("DB_URL"), prop.getProperty("USER"), prop.getProperty("PASSWORD"));
                dbConnection.setAutoCommit(false);
                LOGGER.info("Database connection initialization successful");
            }
            LOGGER.info("Database connection re-used");
        } catch (SQLException se) {
            // Handle errors for JDBC
            LOGGER.info("Database connection initialization error", se);
        }
        return dbConnection;
    }

    /**
     * Fetch the Sap data from Database
     * 
     * @return Set of String
     */
    public Set<String> getSapData() {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = getDBConnection().createStatement();

            String sql = "SELECT reference_revision FROM OPLQTSAP";
            rs = stmt.executeQuery(sql);
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                String str = rs.getString("reference_revision").trim();
                sapData.add(str);
            }
            LOGGER.info("Records in Database " + sapData.size());
            rs.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            LOGGER.error("Error when reading existing reference in database", se);
        } finally {
            // finally block used to close resources
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    LOGGER.error("Error when closing sql statement", e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.error("Error when closing sql result set", e);
                }
            }

        }
        return sapData;
    }

    /**
     * Read the input file containing SAP data
     */
    private void readFile() {
        sapData = getSapData();
        String sapFilePath = null;
        int maxlines = Integer.parseInt(prop.getProperty("BATCH_SIZE"));

        BufferedReader br = null;
        try {
            LOGGER.info("Read SAP file start");
            sapFilePath = prop.getProperty("sap_file_path");
            FileInputStream fstream = new FileInputStream(sapFilePath);

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);

            br = new BufferedReader(new InputStreamReader(in));

            // File Line By Line
            int count = 0;
            Set<String> newSapData = new HashSet<String>();
            String line = br.readLine();
            while (line != null) {
                if (!line.trim().isEmpty() && !line.startsWith("COMP")) {
                    if (count == maxlines) {
                        count = 0;
                        // Save the reference in database
                        if (!newSapData.isEmpty()) {
                            insertSapData(newSapData);
                            getSapData();
                            newSapData.clear();
                        }

                    }
                    if (count++ < maxlines) {
                        String reference = line.substring(14, 26);

                        if (!sapData.contains(reference)) {
                            newSapData.add(reference);
                            LOGGER.debug("Add new reference \"" + reference + "\"");
                        } else {
                            LOGGER.debug("Database already contains reference \"" + reference + "\"");
                        }
                    }
                }
                line = br.readLine();
            }
            if (!newSapData.isEmpty()) {
                insertSapData(newSapData);
                getSapData();
                newSapData.clear();
            }

            LOGGER.info("Read SAP file completed");
        } catch (IOException e) {
            LOGGER.info("Error when reading SAP file error", e);
        } finally {
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    LOGGER.info("Error when closing database connection", e);
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.info("Error when closing SAP file", e);
                }
            }
        }
    }

    /**
     * Add the Sap data into Database Using Batch
     * 
     * @param newSapData Set of String
     */
    private void insertSapData(Set<String> newSapData) {

        PreparedStatement updateSapData = null;
        try {
            LOGGER.info("Insert reference in database START");
            updateSapData = getDBConnection().prepareStatement("insert into OPLQTSAP values(?,?,?)");
            // create an iterator
            Iterator<String> iterator = newSapData.iterator();

            while (iterator.hasNext()) {
                String data = iterator.next();
                updateSapData.setString(1, data);
                updateSapData.setString(2, data.substring(0, 10));
                updateSapData.setString(3, data.substring(10, 12));
                updateSapData.addBatch();
            }
            updateSapData.executeBatch();
            LOGGER.info("Insert reference in database END");
        } catch (SQLException e) {
            LOGGER.info("Error inserting new SAP reference to database", e);
        } finally {
            // finally block to close resources
            if (updateSapData != null) {
                try {
                    updateSapData.close();
                } catch (SQLException e) {
                    LOGGER.info("Error closing connection to database", e);
                }
            }

        }

    }

    /**
     * Launch the batch
     * 
     * @param args Arguments from the command line
     */
    public static void main(String[] args) {
        LOGGER.info("START BATCH IMPORT SAP REFERENCE");
        ReadSapData readSapData = new ReadSapData();
        readSapData.readFile();
        LOGGER.info("END BATCH IMPORT SAP REFERENCE");
    }
}
