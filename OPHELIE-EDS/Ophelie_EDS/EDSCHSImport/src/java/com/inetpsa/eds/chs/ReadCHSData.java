package com.inetpsa.eds.chs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * This class read data from input file containing CHS reference data and import in EDS database.
 * 
 * @author gvillerez
 */
public class ReadCHSData {

    /**
     * Logger log4j to write messages
     */
    private static final Logger LOGGER = Logger.getLogger(ReadCHSData.class);

    /** Name of the configuration file to include in the class path */
    private static final String CONFIG = "config.properties";

    /** Properties of the configuration file */
    private static Properties prop = new Properties();

    /** SHA 1 */
    private static MessageDigest cript;

    /** Connection to the database */
    private Connection dbConnection;

    static {
        try {
            URL configFileURL = ReadCHSData.class.getClassLoader().getResource(CONFIG);

            if (configFileURL == null) {
                LOGGER.error("Unable to find " + CONFIG + "in the classpath");
            }
            InputStream input = new FileInputStream(new File(configFileURL.toURI()));
            prop.load(input);
            cript = MessageDigest.getInstance("SHA-1");
        } catch (IOException e) {
            LOGGER.error("Unable to load configuration file", e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
                dbConnection.setAutoCommit(true);
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
     * CHS simple model used with the batch import.
     * 
     * @author G-VILLEREZ
     */
    private class CHS {
        public String componentID;
        public String intepn;
        public String descript;
        public String userf1;
        public String userf2;
        public boolean existInDatabase;
        public String describeCavities;

        public List<Cavity> cavities;

        public CHS(String componentID, String intepn, String descript, String userf1, String userf2, String describeCavities) {
            this.componentID = componentID;
            this.intepn = intepn;
            this.descript = descript;
            this.userf1 = userf1;
            this.userf2 = userf2;
            this.describeCavities = describeCavities;
            this.cavities = new ArrayList<ReadCHSData.Cavity>();
            this.existInDatabase = true;
        }
    }

    /**
     * CHS's cavity simple model used with the batch import.
     * 
     * @author G-VILLEREZ
     */
    private class Cavity implements Comparable<Cavity> {
        public String cavityID;
        public String connName;
        public String cavity;
        public String pinType;

        public Cavity(String cavityID, String connName, String cavity, String pinType) {
            this.cavityID = cavityID;
            this.connName = connName;
            this.cavity = cavity;
            this.pinType = pinType;
        }

        /**
         * Compare by cavity if equals compares by pinType
         * 
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        public int compareTo(Cavity o) {
            int ret = cavity.compareTo(o.cavity);
            if (ret == 0) {
                ret = pinType.compareTo(o.pinType);
            }
            return ret;
        }
    }

    // EDS database
    private static final String EDS_CHS_TABLE = "OPLQTCHS";
    private static final String EDS_CHS_CAVITY_TABLE = "OPLQTCAV";

    private static final String EDS_CHS_COMPONEN_ID_COLUMN = "COMPONENT_ID";
    private static final String EDS_CHS_INTEPN_COLUMN = "PART_NUMBER";
    private static final String EDS_CHS_DESCRIPT_COLUMN = "DESCRIPTION";
    private static final String EDS_CHS_USERF1_COLUMN = "USER_REF1";
    private static final String EDS_CHS_USERF2_COLUMN = "USER_REF2";
    private static final String EDS_CHS_DESCIBRE_CAV_COLUMN = "DESCRIBE_CAV";

    private static final String EDS_CHS_CAVITY_CAVITY_ID_COLUMN = "CAVITY_ID";
    private static final String EDS_CHS_CAVITY_CONN_NAME_COLUMN = "CONNECTION_NAME";
    private static final String EDS_CHS_CAVITY_CAVITY_COLUMN = "CAVITY";
    private static final String EDS_CHS_CAVITY_PINTYPE_COLUMN = "PIN_TYPE";

    // CSV row
    private static final String CSV_SEPARATOR = ";";

    private static final int LDC_SIZE_MIN = 6;
    private static final int LDC_COLUMN_INTEPN = 0;
    private static final int LDC_COLUMN_DESCRIPT = 1;
    private static final int LDC_COLUMN_USERF1 = 2;
    private static final int LDC_COLUMN_USERF2 = 3;
    private static final int LDC_COLUMN_COMPONEN_ID = 5;

    private static final int LC_SIZE_MIN = 6;
    private static final int LC_COLUMN_COMPONEN_ID = 0;
    private static final int LC_COLUMN_CONN_NAME = 2;
    private static final int LC_COLUMN_CAVITY_ID = 3;
    private static final int LC_COLUMN_CAVITY = 4;
    private static final int LC_COLUMN_PINTYPE = 5;

    // Running values
    /**
     * Map containing the imported CHS values.
     */
    private HashMap<String, CHS> CHSfile = new HashMap<String, CHS>();

    /**
     * Map containing the imported CHS values.
     */
    private HashMap<String, CHS> CHSdatabase = new HashMap<String, CHS>();

    /**
     * Map containing the merged CHS values.
     */
    private HashMap<String, CHS> CHSmerge = new HashMap<String, CHS>();

    /**
     * Read the input file containing CHS data.
     */
    private void importFile() {

        LOGGER.info("Import CHS file start");

        // Get the file to import
        try {

            // Step 1 : create all the CHS used
            BufferedReader br = new BufferedReader(new FileReader(prop.getProperty("chs_file_path_list_device_current")));
            String line;
            int lineIndex = 0;

            while ((line = br.readLine()) != null) {
                String[] lineParsed = line.split(CSV_SEPARATOR);

                if (lineParsed.length < LDC_SIZE_MIN) {
                    LOGGER.info("Line " + lineIndex + " in file " + prop.getProperty("chs_file_path_list_device_current") + " ignored");
                    continue; // Ignore line with not enough values
                }

                if (lineIndex++ <= 0)
                    continue; // Ignore first line (headers)

                String ID = lineParsed[LDC_COLUMN_COMPONEN_ID];
                String intepn = lineParsed[LDC_COLUMN_INTEPN];
                String descript = lineParsed[LDC_COLUMN_DESCRIPT];
                String userf1 = lineParsed[LDC_COLUMN_USERF1];
                String userf2 = lineParsed[LDC_COLUMN_USERF2];

                CHSfile.put(ID, new CHS(ID, intepn, descript, userf1, userf2, ""));
            }
            br.close();

            LOGGER.info("Records of CHS in File : " + CHSfile.size());

            // Step 2 : read all the cavities and link them with the right CHS
            br = new BufferedReader(new FileReader(prop.getProperty("chs_file_path_list_cavity")));
            lineIndex = 0;

            while ((line = br.readLine()) != null) {
                String[] lineParsed = line.split(CSV_SEPARATOR);

                if (lineParsed.length < LC_SIZE_MIN) {
                    LOGGER.info("Line " + lineIndex + " in file " + prop.getProperty("chs_file_path_list_cavity") + " ignored");
                    continue; // Ignore line with not enough values
                }

                if (lineIndex++ <= 0)
                    continue; // Ignore first line (headers)

                String ID = lineParsed[LC_COLUMN_COMPONEN_ID];
                String cavityID = lineParsed[LC_COLUMN_CAVITY_ID];
                String connName = lineParsed[LC_COLUMN_CONN_NAME];
                String cavity = lineParsed[LC_COLUMN_CAVITY];
                String pinType = lineParsed[LC_COLUMN_PINTYPE];

                CHS chs = CHSfile.get(ID);

                if (chs != null) // Only add if the CHS ref exists
                    chs.cavities.add(new Cavity(cavityID, connName, cavity, pinType));
            }
            br.close();
            // Generate Helper Column ( SHA-1 Hex String)
            CHS chs;
            StringBuilder builder;
            StringBuffer sb;
            for (String key : CHSfile.keySet()) {
                builder = new StringBuilder();
                chs = CHSfile.get(key);
                Collections.sort(chs.cavities);
                for (Cavity cav : chs.cavities) {
                    builder.append(cav.cavity);
                    builder.append('_');
                    builder.append(cav.pinType);
                    builder.append('#');
                }
                cript.reset();
                cript.update(builder.toString().getBytes());
                byte[] byteData = cript.digest();
                sb = new StringBuffer();
                for (int i = 0; i < byteData.length; i++) {
                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                }

                chs.describeCavities = sb.toString();
            }
        } catch (Exception e) {
            LOGGER.info("Error when importing CSV file", e);
        }

        LOGGER.info("Import done from the CSV file");
    }

    /**
     * Read the database containing CHS data.
     */
    private void importDatabase() {
        LOGGER.info("Import CHS from database start");

        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Step 1 : create all the CHS used
            stmt = getDBConnection().createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + EDS_CHS_TABLE);

            // Extract data from result set
            while (rs.next()) {
                String ID = getTrimmedSqlString(rs, EDS_CHS_COMPONEN_ID_COLUMN);
                String intepn = getTrimmedSqlString(rs, EDS_CHS_INTEPN_COLUMN);
                String descript = getTrimmedSqlString(rs, EDS_CHS_DESCRIPT_COLUMN);
                String userf1 = getTrimmedSqlString(rs, EDS_CHS_USERF1_COLUMN);
                String userf2 = getTrimmedSqlString(rs, EDS_CHS_USERF2_COLUMN);
                String describeCav = getTrimmedSqlString(rs, EDS_CHS_DESCIBRE_CAV_COLUMN);
                if (describeCav != null) {
                    describeCav = describeCav.trim();
                }
                CHSdatabase.put(ID, new CHS(ID, intepn, descript, userf1, userf2, describeCav));
            }
            rs.close();

            // Step 2 : read all the cavities and link them with the right CHS
            rs = stmt.executeQuery("SELECT * FROM " + EDS_CHS_CAVITY_TABLE);

            while (rs.next()) {
                String ID = getTrimmedSqlString(rs, EDS_CHS_COMPONEN_ID_COLUMN);
                String cavityID = getTrimmedSqlString(rs, EDS_CHS_CAVITY_CAVITY_ID_COLUMN);
                String connName = getTrimmedSqlString(rs, EDS_CHS_CAVITY_CONN_NAME_COLUMN);
                String cavity = getTrimmedSqlString(rs, EDS_CHS_CAVITY_CAVITY_COLUMN);
                String pinType = getTrimmedSqlString(rs, EDS_CHS_CAVITY_PINTYPE_COLUMN);
                CHS chs = CHSdatabase.get(ID);

                if (chs != null)
                    chs.cavities.add(new Cavity(cavityID, connName, cavity, pinType));
            }
            rs.close();

            LOGGER.info("Records in Database : " + CHSdatabase.size());
        } catch (SQLException se) {
            // Handle errors for JDBC
            LOGGER.error("Error when reading existing reference in database", se);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                LOGGER.error("Error when closing database", e);
            }
        }
    }

    /**
     * Get trimmed string from result set
     * 
     * @param rs the result set
     * @param columnLabel the label for the column specified with the SQL AS clause. If the SQL AS clause was not specified, then the label is the
     *            name of the column
     * @return the trimmed string or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    private static String getTrimmedSqlString(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        if (value != null) {
            return value.trim();
        }
        return value;
    }

    /**
     * Merge all the value. Value only existing in the database will be dropped here, and stay unmodified in the database. Only updated and new CHSs
     * will be present.
     */
    private void merge() {
        // Iterate on the imported values : ignore deleted values from database
        for (CHS importedChs : CHSfile.values()) {
            if (CHSdatabase.containsKey(importedChs.componentID)) {
                // Update
                CHSmerge.put(importedChs.componentID, importedChs);
            } else {
                // Insert
                importedChs.existInDatabase = false;
                CHSmerge.put(importedChs.componentID, importedChs);
            }
        }

        LOGGER.info(CHSmerge.size() + " records merged");
    }

    /**
     * Export the merged values to the database. The merged CHSs existing into the database will be updated, and the new CHS will be inserted into the
     * database. All the cavities of existing CHS will be deleted and re-inserted to avoid ghost cavities.
     */
    private void exportDatabase() {
        PreparedStatement deleteStatementCavity = null;
        PreparedStatement updateStatementCHS = null;
        PreparedStatement insertStatementCHS = null;
        PreparedStatement insertStatementCavity = null;

        int cavityNumber = 0;

        LOGGER.info("Inserting records into the database...");

        try {

            // Deletion statements
            deleteStatementCavity = getDBConnection().prepareStatement(
                    "delete from " + EDS_CHS_CAVITY_TABLE + " where " + EDS_CHS_COMPONEN_ID_COLUMN + " = ?");

            // Update statements
            // String describeCav = rs.getString(EDS_CHS_DESCIBRE_CAV_COLUMN).trim();
            updateStatementCHS = getDBConnection().prepareStatement(
                    "update " + EDS_CHS_TABLE + " SET " + EDS_CHS_INTEPN_COLUMN + " =?, " + EDS_CHS_DESCRIPT_COLUMN + " =?, " + EDS_CHS_USERF1_COLUMN
                            + " =?, " + EDS_CHS_USERF2_COLUMN + " =?, " + EDS_CHS_DESCIBRE_CAV_COLUMN + " =? where " + EDS_CHS_COMPONEN_ID_COLUMN
                            + " = ?");

            // Insertion statements
            insertStatementCHS = getDBConnection().prepareStatement("insert into " + EDS_CHS_TABLE + " values(?,?,?,?,?,?)");
            insertStatementCavity = getDBConnection().prepareStatement("insert into " + EDS_CHS_CAVITY_TABLE + " values(?,?,?,?,?,?)");

            for (CHS chs : CHSmerge.values()) {
                if (chs.existInDatabase) {
                    // Delete cavities
                    deleteStatementCavity.setString(1, chs.componentID);
                    deleteStatementCavity.addBatch();

                    // Update CHS
                    updateStatementCHS.setString(1, chs.intepn);
                    updateStatementCHS.setString(2, chs.descript);
                    updateStatementCHS.setString(3, chs.userf1);
                    updateStatementCHS.setString(4, chs.userf2);
                    updateStatementCHS.setString(5, chs.describeCavities);
                    updateStatementCHS.setString(6, chs.componentID);
                    updateStatementCHS.addBatch();
                } else {
                    // Insert into the database (new record)
                    insertStatementCHS.setString(1, chs.componentID);
                    insertStatementCHS.setString(2, chs.intepn);
                    insertStatementCHS.setString(3, chs.descript);
                    insertStatementCHS.setString(4, chs.userf1);
                    insertStatementCHS.setString(5, chs.userf2);
                    insertStatementCHS.setString(6, chs.describeCavities);
                    insertStatementCHS.addBatch();
                }

                // Common : add all the cavities
                for (Cavity c : chs.cavities) {
                    cavityNumber++;

                    insertStatementCavity.setString(1, UUID.randomUUID().toString());
                    insertStatementCavity.setString(2, c.cavityID);
                    insertStatementCavity.setString(3, c.connName);
                    insertStatementCavity.setString(4, c.cavity);
                    insertStatementCavity.setString(5, c.pinType);
                    insertStatementCavity.setString(6, chs.componentID);
                    insertStatementCavity.addBatch();
                }
            }

            // Run all the deletions
            deleteStatementCavity.executeBatch();

            // Run all the updates
            updateStatementCHS.executeBatch();

            // Run all the inserts
            insertStatementCHS.executeBatch();
            insertStatementCavity.executeBatch();

            LOGGER.info(CHSmerge.size() + " CHS records inserted/updated into the database");
            LOGGER.info(cavityNumber + " cavity records inserted into the database");
        } catch (SQLException e) {
            LOGGER.error("Error inserting reference to database", e);
        } finally {
            try {
                if (deleteStatementCavity != null)
                    deleteStatementCavity.close();

                if (updateStatementCHS != null)
                    updateStatementCHS.close();

                if (insertStatementCHS != null)
                    insertStatementCHS.close();

                if (insertStatementCavity != null)
                    insertStatementCavity.close();
            } catch (SQLException e) {
                LOGGER.info("Error closing connection to database", e);
            }
        }

    }

    /**
     * Launch the batch
     * 
     * @param args Arguments from the command line
     */
    public static void main(String[] args) {
        LOGGER.info("START BATCH IMPORT CHS REFERENCE");
        ReadCHSData reader = new ReadCHSData();
        reader.importFile(); // Import all the CHS from the excel file
        reader.importDatabase(); // Import all the CHS from the database
        reader.merge();
        reader.exportDatabase();
        LOGGER.info("END BATCH IMPORT CHS REFERENCE");
    }
}
