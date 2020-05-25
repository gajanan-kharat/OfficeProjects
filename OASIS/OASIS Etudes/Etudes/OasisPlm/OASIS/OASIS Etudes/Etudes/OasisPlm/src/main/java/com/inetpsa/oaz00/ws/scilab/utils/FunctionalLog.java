/*
 * @(#)FunctionalLog.java 1.4 October 10, 2014
 * CopyRight : The PSA Company
 */

package com.inetpsa.oaz00.ws.scilab.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Contains the detailed information of the logFile.
 * 
 * @author Geometric Ltd.
 */
public class FunctionalLog {

    /** Half connector properties */
    private static Properties HALF_CONNECTOR_LOG_PROPERTIES;

    public FunctionalLog() {
        HALF_CONNECTOR_LOG_PROPERTIES = new Properties();
    }

    /** The log file name. */
    private static final String LOG_FILE_NAME_PREFIX = "oasis_plm_";
    private static final String LOG_FILE_NAME_EXTENSION_PREFIX = ".appli";
    private static final String LOG_MESSAGE_WITH_MAIN_AS_MODULE_NAME = "MAIN";
    /** The host name */
    private String hostName;
    /** The user id */
    private String userID;
    /** The server states. */
    private String serverState;
    /** The application */
    private String application;
    /** The project name. */
    private String projectName;
    /** The start date. */
    private String startDate;
    /** The end date. */
    private String endDate;
    /** The module. */
    private String module;

    /**
     * Get the hostName.
     * 
     * @return String.
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Set the hostName.
     * 
     * @param hostName
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Get the userId.
     * 
     * @return String.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Set userId.
     * 
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Get serverState
     * 
     * @return String.
     */
    public String getServerState() {
        return serverState;
    }

    /**
     * Set the serverState.
     * 
     * @param serverState
     */
    public void setServerState(String serverState) {
        this.serverState = serverState;
    }

    /**
     * Get application.
     * 
     * @return String.
     */
    public String getApplication() {
        return application;
    }

    /**
     * Set application.
     * 
     * @param application
     */
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * Get project name.
     * 
     * @return String.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Set project name.
     * 
     * @param projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Get startDate
     * 
     * @return String
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Set startDate.
     * 
     * @param startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Get end Date.
     * 
     * @return String.
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * set endDate
     * 
     * @param endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Get module.
     * 
     * @return String.
     */
    public String getModule() {
        return module;
    }

    /**
     * Set module.
     * 
     * @param module
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return hostName + ";" + userID + ";" + serverState + ";" + application + ";" + projectName + ";;;" + startDate + ";" + endDate + ";" + module;
    }

    /**
     * Create a log file and insert information regarding every request.
     * 
     * @return nothing.
     */
    public static File writeLogDetails(String startDate, String projectName, String userId, String module) {
        try {
            // Creating LogFileData object to set the attributes.
            FunctionalLog data = new FunctionalLog();
            InetAddress address = InetAddress.getLocalHost();
            // File warPath = new File(FunctionalLog.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            // String propertiesPath = warPath.getParent().substring(0, warPath.getParent().indexOf("WEB-INF"));
            // propertiesPath = propertiesPath.replaceAll("%20", "\u0020");
            // propertiesPath = propertiesPath + "ServerConfig.properties";
            File propFile = new File(System.getProperty("OazConfigDir") + File.separator + "ServerConfig.properties");
            HALF_CONNECTOR_LOG_PROPERTIES.load(new FileInputStream(propFile));
            // Getting the hostName from address and set the hostName of LogFileData.
            data.setHostName(address.getHostName());
            // Setting the userId.
            data.setUserID(userId);
            // Get the server_state value from property file.
            String server_state = HALF_CONNECTOR_LOG_PROPERTIES.getProperty("server_state");
            // Set the server_state of LogFileData.
            data.setServerState(server_state);
            // Get the application value from property file.
            String application = HALF_CONNECTOR_LOG_PROPERTIES.getProperty("application");
            // Set the application of LogFileData.
            data.setApplication(application);
            // Setting the projectName.
            data.setProjectName(projectName);
            // Set the start_Date of LogFileData.
            data.setStartDate(startDate);
            Calendar now = Calendar.getInstance();
            // Get the start_Date
            String end_Date = now.get(Calendar.YEAR) + "." + now.get(Calendar.DAY_OF_YEAR) + "." + now.get(Calendar.HOUR_OF_DAY) + "."
                    + now.get(Calendar.MINUTE);
            // Set the end_Date of LogFileData.
            data.setEndDate(end_Date);
            // Get the log_dir value from property file.
            String path = HALF_CONNECTOR_LOG_PROPERTIES.getProperty("log_dir");
            File logDirectory = new File(path);
            if (!logDirectory.exists()) {
                logDirectory.mkdirs();
            }
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            // get current date.
            Date date = now.getTime();
            String currentDate = dateFormat.format(date);
            String fileName = LOG_FILE_NAME_PREFIX + currentDate + LOG_FILE_NAME_EXTENSION_PREFIX;
            // Give the path of the file.
            File logFile = new File(path, fileName);
            // Check if the log file for today exists or not.
            if (!logFile.exists()) {
                // If not, Create a brand new one.
                logFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(logFile, true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWriter);
            // Setting the Module.
            data.setModule(LOG_MESSAGE_WITH_MAIN_AS_MODULE_NAME);
            bufferWritter.write(data.toString());
            bufferWritter.newLine();
            // Setting the Module again.
            data.setModule(module.toString());
            bufferWritter.write(data.toString());
            bufferWritter.newLine();
            bufferWritter.close();
            return logFile;
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
