//COPYRIGHT PSA Peugeot Citroen 2014
/***********************************************************************************************************************
 *
 * FILE NAME      : PSAFilterDBHandler.java
 *
 * SOCIETE        : PSA
 * PROJET         : TE Structure compare.
 * VERSION        : V1.0
 * DATE           : 05/23/2017
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : This class maintain the list of filters to be added/modified/deleted as requested by the user.
 *                  
 ***********************************************************************************************************************/
package com.psa.PSATEStructCompare;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.psa.DBInterface.JDBInterface;
import com.psa.PSATEStructCompare.PSAFilterPanel.MODE;
import com.psa.PSATEStructCompare.PSAStructUITools.USER_ACCESS;
import com.psa.tools.LaunchDate;
import com.psa.tools.PSAErrorHandler;
import com.psa.tools.PSAMessageDialog;
import com.psa.UniqueCOID.UniqueCOID;

/**
 * Class used to maintain the list of filters to be added, modified or deleted as requested by the user.
 */

public class PSAFilterDBHandler {
    private static Logger logger = Logger.getLogger("PSAFilterDBHandler");
    protected static JDBInterface dbCon = null;
    protected static String m_StrUser = "";
    protected static String m_StrRole = "";
    protected static String m_StrOrganization = "";
    static String m_StrSelObj = "";
    static String m_StrDICOObj = "";

    protected static USER_ACCESS m_userAccess = USER_ACCESS.INVALID;
    public static String strErrorLabel = "";

    // Static block to create JDBInterface object and to store user credentials.
    static {
            dbCon = new JDBInterface();
    }

    /**
     * Constructor of the class.
     * 
     * @param iBatch : int iBatch.
     */
    public PSAFilterDBHandler(int iBatch)
    {
        System.out.println("PSAFilterDBHandler : START Constructor of the class");
        System.out.println("PSAFilterDBHandler : END constructor of the class");

    }
    
    /**
     * Constructor of the class.
     * 
     */
    public PSAFilterDBHandler()
    {
        System.out.println("PSAFilterDBHandler : START Constructor of the class");
        try {
            getUserVPMDetail();
            fetchUserAppAccess();
        } catch (PSAErrorHandler e) {
            strErrorLabel = e.m_StrErrorLabel;
        }
        System.out.println("PSAFilterDBHandler : END constructor of the class");

    }

    /**
     * This function is used to get details of the login user. 1)m_StrUser : User Id 2)m_StrRole : USer Role 3)m_StrOrganization : Organization
     * 4)m_StrParentOrg : Parent Organization
     *
     * @throws PSAErrorHandler Returns the error code and the error label if any error is occurred.
     */
    static void getUserVPMDetail() throws PSAErrorHandler
    {
        System.out.println("[PSAFilterDBHandler::getUserVPMDetail] START");
        // Query to read login user detail.
        String strSQLQuery = "SELECT USER, R.\"name\", O.\"id\" FROM VPMDB2.\"person\" P, VPMDB2.\"role\" R, VPMDB2.\"organization\" O "
                + " WHERE P.\"id\" = USER AND P.\"current_role\" = R.oid AND R.\"belongs_to\" = O.oid";

        System.out.println("[PSAFilterDBHandler::getUserVPMDetail] User Validation Query : " + strSQLQuery);
        // Execute the Query
        int status = dbCon.ExecuteSelect(strSQLQuery);

        // If there is any error,throw it.
        if ((0 != status) || (0 == dbCon.getRowCount())) {
            System.out.println("[PSAFilterDBHandler::getUserVPMDetail] Unable to retrieve user information");
            System.out.println("[PSAFilterDBHandler::getUserVPMDetail] END");
            throw new PSAErrorHandler(-1, "Error.InvalidUser");
        } else {
            m_StrUser = dbCon.getValue(1, 1).trim();
            m_StrRole = dbCon.getValue(1, 2).trim();
            m_StrOrganization = dbCon.getValue(1, 3).trim();
        }

        System.out.println("User ID: " + m_StrUser + ", Role : " + m_StrRole + ", Organization : " + m_StrOrganization);
        System.out.println("[PSAFilterDBHandler::getUserVPMDetail] END");
    }

    /**
     * This function is reading user from the environment variable. check access rights to the user.
     */
    public static void fetchUserAppAccess() throws PSAErrorHandler
    {
        int n_status = 0;
        int n_total = 0;
        // Query to read user login details
        String strSQLQuery = "SELECT GF.REF_ID FROM GESTION.FGESTION GF, GESTION.PART_LIST GP"
                + " WHERE GP.S_REFERENCE = 'STRUCTS_TE' AND GF.$COID = GP.$COID AND GF.REF_VAL = '" + m_StrUser + "'";
        System.out.println("[PSAFilterDBHandler::fetchUserAppAccess] Executing the query: " + strSQLQuery);
        n_status = dbCon.ExecuteSelect(strSQLQuery);

        if (0 != n_status) {
            throw new PSAErrorHandler(-1, "Error.InvalidGestionEnv");
        }
        n_total = dbCon.getRowCount();

        if (n_total == 1) {
            String strUsrAccess = dbCon.getValue(1, 1).trim();
            if (strUsrAccess.equals("ALL")) {
                m_userAccess = USER_ACCESS.ALL;
            } else if (strUsrAccess.equals("READ")) {
                m_userAccess = USER_ACCESS.READ;
            } else if (strUsrAccess.equals("WRITE")) {
                m_userAccess = USER_ACCESS.WRITE;
            }
        }
        else
        {
            m_userAccess = USER_ACCESS.INVALID;
        }

        // clear data from DB interface
        dbCon.clearResultSet();
        dbCon.closeStatement();
    }

    /**
     * Create a filter in database.
     * 
     * @param iFilter : Filter to insert data in database table
     * @throws PSAErrorHandler
     */
    public int createFilter(PSAFilterModel iFilter) throws PSAErrorHandler {
        System.out.println("[PSAFilterDBHandler::CreateFilter] START");

        int iCreateFilterStatus = 0;
        // Check for unique filter name
        String sqlChkDupFtrNameQuery = String.format("SELECT 1 FROM VPMDB2.STRUCTCMP_TE WHERE FILTER_NAME = '" + iFilter.getName() + "'");
        System.out.println("[PSAFilterDBHandler::CreateFilter] Checking for duplicate entries in VPMDB2.STRUCTCMP_TE :" + sqlChkDupFtrNameQuery);

        // execute the query
        int status = dbCon.ExecuteSelect(sqlChkDupFtrNameQuery);
        // if any error comes then throw the error
        if (0 != status) {
            iCreateFilterStatus = -1;
            System.out.println("[PSAFilterDBHandler::CreateFilter] Select query failed in VPMDB2.STRUCTCMP_TE.");
            System.out.println("[PSAFilterDBHandler::CreateFilter] END");
            throw new PSAErrorHandler(-1, "Error.FilterNameSelectionError");
        }
        // get the resulting count of the executed query
        int count = dbCon.getRowCount();
        if (count != 0)
        {
            iCreateFilterStatus = 111;
            
            //Build message and show to end user
            String msg = "Filter name already exists, please provide the different name!";
            msg = PSAFilterUIController.objLanguageHandler.Getlabel("Message.FilterNameExist",msg);
            String title = "Duplicate name Warning";
            title = PSAFilterUIController.objLanguageHandler.Getlabel("Title.DuplicateNameWaring",title);
            PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, msg, title, PSAMessageDialog.BUTTON_OPTION.OK_BUTTON_OPTION, PSAFilterUIController.objLanguageHandler);
        }
        else
        {
            // generating unique ID
            UniqueCOID uniqueCoid = new UniqueCOID();
            iFilter.setId(uniqueCoid.getUniqueCOID());
            // calculating DB value to be stored for Mode Parameter
            String strModeParamDBVal = "";
            String strModeVal = iFilter.getMode();
            if(!strModeVal.equals("DAILY"))
            {
                strModeParamDBVal = getModeParamDBValFromFieldText(iFilter.getMode(), iFilter.getModeParam());
            }

            String sqlInsertQuery = String.format("INSERT INTO VPMDB2.STRUCTCMP_TE VALUES (X'" + iFilter.getId() + "' ,'" + iFilter.getName() + "'"
                    + ", '" + iFilter.getRevision() + "' ,'" + iFilter.getProject() + "' ,'" + iFilter.getDesignation() + "' ,'" + iFilter.getType() 
                    + "' ,'" + iFilter.getMode() + "' ,'" + strModeParamDBVal + "', DATE '" + iFilter.getStartDate()
                    + "',DATE '" + iFilter.getNextLaunchDate() + "',CURRENT TIMESTAMP, USER, CURRENT TIMESTAMP)");
            System.out.println("[PSAFilterDBHandler::CreateFilter] Inserting in the VPMDB2.STRUCTCMP_TE : " + sqlInsertQuery);
            
            // Execute the query
            status = dbCon.ExecuteUpdate(sqlInsertQuery);
            // If any error comes then throw the error.
            if (0 != status) {
                System.out.println("[PSAFilterDBHandler::CreateFilter] Data could not be inserted in VPMDB2.STRUCTCMP_TE.");
                dbCon.Rollback();
                System.out.println("[PSAFilterDBHandler::CreateFilter] END");
                throw new PSAErrorHandler(-1, "Error.FilterCreationError");
            }
            dbCon.Commit();
        }

        System.out.println("[PSAFilterDBHandler::CreateFilter] END");
        return iCreateFilterStatus;
    }

    /**
     * Modify filter in database.
     * 
     * @param iModifiedFilter : Filter to insert modified data in database table
     * @throws PSAErrorHandler
     */

    public void modifyFilter(PSAFilterModel filterToUpdate) throws PSAErrorHandler {
        System.out.println("[PSAFilterDBHandler::ModifyFilter] START");

        // calculating DB value to be stored for Mode Parameter
        String strModeParamDBVal = "";
        String strModeVal = filterToUpdate.getMode();
        if(!strModeVal.equals("DAILY"))
        {
            strModeParamDBVal = getModeParamDBValFromFieldText(filterToUpdate.getMode(), filterToUpdate.getModeParam());
        }

        filterToUpdate.setId(filterToUpdate.getId());
        String sqlUpdateQuery = "UPDATE VPMDB2.STRUCTCMP_TE SET ";
        sqlUpdateQuery += "FILTER_PROJECT = '" + filterToUpdate.getProject() + "', ";
        sqlUpdateQuery += "FILTER_DESIGNATION = '" + filterToUpdate.getDesignation() + "', ";
        sqlUpdateQuery += "FILTER_TYPE = '" + filterToUpdate.getType() + "', ";
        sqlUpdateQuery += "MODE = '" + filterToUpdate.getMode() + "', ";
        sqlUpdateQuery += "MODE_PARAM = '" + strModeParamDBVal + "', ";
        sqlUpdateQuery += "STARTDATE = '" + filterToUpdate.getStartDate() + "', ";
        sqlUpdateQuery += "NEXTLAUNCHDATE = '" + filterToUpdate.getNextLaunchDate() + "', ";
        sqlUpdateQuery += "MODIFIED = CURRENT TIMESTAMP ";
        sqlUpdateQuery += "WHERE ID =X'" + filterToUpdate.getId() + "'";
        System.out.println("[PSAFilterDBHandler::ModifyFilter] Updating in the VPMDB2.STRUCTCMP_TE : " + sqlUpdateQuery);

        // Execute the query
        int status = dbCon.ExecuteUpdate(sqlUpdateQuery);
        // If any error comes then throw the error.
        if (0 != status) {
            System.out.println("[PSAFilterDBHandler::ModifyFilter] Data could not be updated in VPMDB2.STRUCTCMP_TE.");
            dbCon.Rollback();
            System.out.println("[PSAFilterDBHandler::ModifyFilter] END");
            throw new PSAErrorHandler(-1, "Error.FilterModificationError");
        }
        dbCon.Commit();

        System.out.println("[PSAFilterDBHandler::ModifyFilter] END");
    }

    /**
     * Delete selected filters from the database
     * 
     * @param strSelectedPSAFilterIDs : Selected filter objects
     * @param PSAErrorHandler
     */

    public void deleteFilters(String [] strSelectedPSAFilterIDs) throws PSAErrorHandler {
        System.out.println("[PSAFilterDBHandler::DeleteFilters] START");
        String strPSAFilterIDListIN = "";
        for(int i = 0; i < strSelectedPSAFilterIDs.length; i++)
        {
            if(i == (strSelectedPSAFilterIDs.length -1))
            {
                strPSAFilterIDListIN += "X'" + strSelectedPSAFilterIDs[i] + "'";
            }
            else
            {
                strPSAFilterIDListIN += "X'" + strSelectedPSAFilterIDs[i] + "', ";
            }
        }
        String sqlDeleteQuery = String.format("DELETE FROM VPMDB2.STRUCTCMP_TE WHERE ID IN (" + strPSAFilterIDListIN + ")");
        System.out.println("[PSAFilterDBHandler::DeleteFilters] Deleting filter from VPMDB2.STRUCTCMP_TE :" + sqlDeleteQuery);

        // Execute the query
        int status = dbCon.ExecuteDelete(sqlDeleteQuery);
        // If any error comes then throw the error
        if (0 != status) {
            System.out.println("[PSAFilterDBHandler::DeleteFilters] Data could not be deleted from VPMDB2.STRUCTCMP_TE.");
            dbCon.Rollback();
            System.out.println("[PSAFilterDBHandler::DeleteFilters] END");
            throw new PSAErrorHandler(-1, "Error.FilterDeletionError");
        }
        dbCon.Commit();
        
        System.out.println("[PSAFilterDBHandler::DeleteFilters] END");
    }

    /**
     * To update the NEXTLAUNCHDATE for all processed filters
     * 
     * @throws PSAErrorHandler
     */
    public void updateNextLaunchDate(List<PSAFilterModel> filterListToUpdate) throws PSAErrorHandler {
        logger.info("[PSAFilterDBHandler::updateNextLaunchDate] START");

        for (PSAFilterModel filterModel : filterListToUpdate) {

            String strNextLaunchDate = calculateNextLaunchDate(filterModel);

            // Building UPDATE query to update NEXTLAUNCHDATE and MODIFDATE for given FILTER ID
            StringBuilder updateQry = new StringBuilder("UPDATE VPMDB2.STRUCTCMP_TE SET ");
            updateQry.append("NEXTLAUNCHDATE=");
            updateQry.append("'");
            updateQry.append(strNextLaunchDate);
            updateQry.append("' ");
            updateQry.append("WHERE ID=X");
            updateQry.append("'");
            updateQry.append(filterModel.getId());
            updateQry.append("'");
            logger.info("Executing UPDATE query: " + updateQry);
            
            // Execute the UPDATE query
            int status = dbCon.ExecuteUpdate(updateQry.toString());
            // If any error comes then throw the error.
            if (0 != status) {
                logger.info("[PSAFilterDBHandler::updateNextLaunchDate] Data could not be updated in VPMDB2.STRUCTCMP_TE.");
                logger.info("[PSAFilterDBHandler::updateNextLaunchDate] Failed to update filter with ID: " + filterModel.getId());
                logger.info("[PSAFilterDBHandler::updateNextLaunchDate] End");
                dbCon.Rollback();
                continue;
            }
            dbCon.Commit();
            logger.info("[PSAFilterDBHandler::updateNextLaunchDate] Successfully updated Filter with ID:" + filterModel.getId());
        }
        logger.info("[PSAFilterDBHandler::updateNextLaunchDate] END");
    }

    /*
     * To get all filters from the table VPMDB2.STRUCTCMP_TE where Next launch date is today's date
     */
    public List<PSAFilterModel> getFiltersForNextLaunchDate() throws PSAErrorHandler {
        logger.info("[PSAFilterDBHandler::getFiltersForNextLaunchDate] START");
        List<PSAFilterModel> filterList;
        String currentDate = getCurrentDate();
        
        // Query to read all filters data where NEXTLAUNCHDATE is equal to today's date
        String selectQry = "SELECT ID, FILTER_NAME, FILTER_REVISION, FILTER_PROJECT, FILTER_DESIGNATION, FILTER_TYPE, MODE, MODE_PARAM, STARTDATE, NEXTLAUNCHDATE FROM VPMDB2.STRUCTCMP_TE WHERE NEXTLAUNCHDATE="
                + "'" + currentDate + "'";
        logger.info("[PSAFilterDBHandler::getFiltersForNextLaunchDate] EXECUTING SELECT QUERY: " + selectQry);
        
        int status = dbCon.ExecuteSelect(selectQry);
        if (status != 0) {
            logger.info("[PSAFilterDBHandler::getFiltersForNextLaunchDate] Select query failed to execute!");
            throw new PSAErrorHandler(-1, "Error.FilterListError");
        }
        int totalRowCount = dbCon.getRowCount();
        filterList = new ArrayList<PSAFilterModel>(totalRowCount);
        logger.info("[PSAFilterDBHandler::getFiltersForNextLaunchDate] Total rows count = " + totalRowCount);
        for (int index = 1; index <= totalRowCount; index++)
        {
            PSAFilterModel filterModel = new PSAFilterModel();
            filterModel.setId(dbCon.getValue(index, 1).trim());
            filterModel.setName(dbCon.getValue(index, 2).trim());
            filterModel.setRevision(dbCon.getValue(index, 3).trim());
            filterModel.setProject(dbCon.getValue(index, 4).trim());
            filterModel.setDesignation(dbCon.getValue(index, 5).trim());
            filterModel.setType(dbCon.getValue(index, 6).trim());
            filterModel.setMode(dbCon.getValue(index, 7).trim());
            String strModeParamDBValue = dbCon.getValue(index, 8).trim();
            String strModeParamFieldText = getModeParamFieldTextFromDBVal(dbCon.getValue(index, 7).trim(), strModeParamDBValue);
            filterModel.setModeParam(strModeParamFieldText);
            filterModel.setStartDate(dbCon.getValue(index, 9).trim());
            filterModel.setNextLaunchDate(dbCon.getValue(index, 10).trim());
            displayFetchingFilterRow(filterModel);
            filterList.add(filterModel);
        }
        logger.info("[PSAFilterDBHandler::getFiltersForNextLaunchDate] END");
        return filterList;
    }

    /**
     * To get today's date in the format yyyy-MM-dd
     * 
     * @return String
     */
    private String getCurrentDate() {
        logger.info("[PSAFilterDBHandler::getCurrentDate] START");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String todaysDate = dateFormat.format(cal.getTime());
        logger.info("[PSAFilterDBHandler::getCurrentDate] Today's date is: " + todaysDate);
        logger.info("[PSAFilterDBHandler::getCurrentDate] END");
        return todaysDate;
    }

    /**
     * List filter table data from the database
     * 
     * @return List<PSAFilterEntity> : Filter list in STRUCTCMP_TE
     * @param PSAErrorHandler
     */
    public List<PSAFilterModel> getFilterList() throws PSAErrorHandler {
        System.out.println("[PSAFilterDBHandler::getFilterList] START");

        List<PSAFilterModel> arrayListFilter = null;

        // Query to read filter data
        String sqlListQuery = "SELECT ID, FILTER_NAME, FILTER_REVISION, FILTER_PROJECT, FILTER_DESIGNATION, FILTER_TYPE, MODE, MODE_PARAM, STARTDATE, NEXTLAUNCHDATE, CREATED,  USER, MODIFIED FROM VPMDB2.STRUCTCMP_TE";
        System.out.println("Filter list query :" + sqlListQuery);

        // Execute the query
        int status = dbCon.ExecuteSelect(sqlListQuery);

        // if any error comes then throw the error
        if (0 != status) {
            System.out.println("[PSAFilterDBHandler::getFilterList] Unable to retrive filter data!");
            System.out.println("[PSAFilterDBHandler::getFilterList] PSAFilterDBHandler:: getListFilter end");
            throw new PSAErrorHandler(-1, "Error.FilterListError");
        }

        // get the resulting count of executed query
        int count = dbCon.getRowCount();
        if (0 == count) 
        {
            System.out.println("[PSAFilterDBHandler::getFilterList] There is no filter data!");
            JOptionPane.showMessageDialog(null, "There is no filter data!", "No records found", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            System.out.println("[PSAFilterDBHandler::getFilterList] Number of filter data are :" + count);
            arrayListFilter = new ArrayList<PSAFilterModel>(count);

            for (int index = 1; index <= count; index++) 
            {
                PSAFilterModel psaFilterEntity = new PSAFilterModel();
                // ID
                psaFilterEntity.setId(dbCon.getValue(index, 1).trim());
                // FILTER_NAME
                psaFilterEntity.setName(dbCon.getValue(index, 2).trim());
                // FILTER_REVISION
                psaFilterEntity.setRevision(dbCon.getValue(index, 3).trim());
                // FILTER_PROJECT
                psaFilterEntity.setProject(dbCon.getValue(index, 4).trim());
                // FILTER_DESIGNATION
                psaFilterEntity.setDesignation(dbCon.getValue(index, 5).trim());
                // FILTER_TYPE
                psaFilterEntity.setType(dbCon.getValue(index, 6).trim());
                // MODE
                psaFilterEntity.setMode(dbCon.getValue(index, 7).trim());
                // MODE_PARAM
                String strModeParamDBValue = dbCon.getValue(index, 8).trim();
                String strModeParamFieldText = getModeParamFieldTextFromDBVal(dbCon.getValue(index, 7).trim(), strModeParamDBValue);
                psaFilterEntity.setModeParam(strModeParamFieldText);
                // STARTDATE
                psaFilterEntity.setStartDate(dbCon.getValue(index, 9).trim());
                // NEXTLAUNCHDATE
                psaFilterEntity.setNextLaunchDate(dbCon.getValue(index, 10).trim());
                // CREATED
                psaFilterEntity.setCreationDate(dbCon.getValue(index, 11).trim());
                // USER
                psaFilterEntity.setUser(dbCon.getValue(index, 12).trim());
                // MODIFIED
                psaFilterEntity.setModifyDate(dbCon.getValue(index, 13).trim());

                displayFetchingFilterRow(psaFilterEntity);
                arrayListFilter.add(psaFilterEntity);
            }
        }

        System.out.println("[PSAFilterDBHandler::getFilterList] END");
        return arrayListFilter;
    }

    private void displayFetchingFilterRow(PSAFilterModel psaFilterEntity) {
        System.out.println("Filter ROW=>> ID:" + psaFilterEntity.getId() + " NAME:" + psaFilterEntity.getName() + " REV:" + psaFilterEntity.getRevision() 
            + " PROJECT:" + psaFilterEntity.getProject() + " DESIGNATION:" + psaFilterEntity.getDesignation() + " Type:" + psaFilterEntity.getType()
            + " MODE:" + psaFilterEntity.getMode() + " MODE PARAM:" + psaFilterEntity.getModeParam() + " START DATE:" + psaFilterEntity.getStartDate()
            + " LAUNCH DATE: " + psaFilterEntity.getNextLaunchDate() + " MODIF DATE:" + psaFilterEntity.getModifyDate()
            + " CREATE DATE:" + psaFilterEntity.getCreationDate() + " USER:" + psaFilterEntity.getUser());
    }

    /**
     * THIS IS A STATIC METHOD This function closes the connection if it is already opened.
     */
    public static void closeDatabaseConnection() {
        System.out.println("[PSAFilterDBHandler::closeDatabaseConnection] START");
        // Check if the database connection object is not null.
        if (dbCon != null) {
            // Disconnect the database connection.
            dbCon.disConnect();
            // Set the database connection object to null.
            dbCon = null;
        }
        System.out.println("[PSAFilterDBHandler::closeDatabaseConnection] END");
    }

    /**
     * Return user role.
     * 
     * @return String : User Role
     */
    public String getRole() {
        return m_StrRole;
    }

    /**
     * Return user organization.
     * 
     * @return String : User Organization.
     */
    public String getOrganization() {
        return m_StrOrganization;
    }

    /**
     * Return user id.
     * 
     * @return String : User ID
     */
    public String getUser() {
        return m_StrUser;
    }

    /**
     * Return user id.
     * 
     * @return String : User ID
     */
    public USER_ACCESS getUserAppAccess() {
        return m_userAccess;
    }

    public String getModeParamDBValFromFieldText(String strFilterMode, String strModeParamFieldText) {
        System.out.println("[PSAFilterDBHandler::getModeParamDBValFromFieldText] START");
        System.out.println("[PSAFilterDBHandler::getModeParamDBValFromFieldText] Input value - Filter mode=" + strFilterMode);
        System.out.println("[PSAFilterDBHandler::getModeParamDBValFromFieldText] Input value - Mode Param Field Text=" + strModeParamFieldText);
        String strModeParamDBVal = "";
        Hashtable<String, Integer> weekDays;
        Hashtable<String, Integer> weekNumber;
        // Add week days to hashtable.
        weekDays = new Hashtable<String, Integer>();
        weekDays.put("MONDAY", new Integer(1));
        weekDays.put("TUESDAY", new Integer(2));
        weekDays.put("WEDNESDAY", new Integer(3));
        weekDays.put("THURSDAY", new Integer(4));
        weekDays.put("FRIDAY", new Integer(5));
        weekDays.put("SATURDAY", new Integer(6));
        weekDays.put("SUNDAY", new Integer(7));

        // Add week number to hashtable.
        weekNumber = new Hashtable<String, Integer>();
        weekNumber.put("FIRST", new Integer(1));
        weekNumber.put("SECOND", new Integer(2));
        weekNumber.put("THIRD", new Integer(3));
        weekNumber.put("FOURTH", new Integer(4));
        weekNumber.put("LAST", new Integer(5));

        if (strFilterMode.equals("WEEKLY")) {
            String[] strModeParamFieldvalues = strModeParamFieldText.split(",");
            int iModeParamFieldvaluesLength = strModeParamFieldvalues.length;
            for (int i = 0; i < iModeParamFieldvaluesLength; i++) {
                Integer weekDayNo = weekDays.get(strModeParamFieldvalues[i].toUpperCase());
                if (weekDayNo != null) {
                    strModeParamDBVal += weekDayNo.toString();
                }
            }
        } else if (strFilterMode.equals("MONTHLY")) {
            String[] strModeParamFieldValues = strModeParamFieldText.split(",");
            int iModeParamFieldvaluesLength = strModeParamFieldValues.length;
            if (iModeParamFieldvaluesLength > 0) {
                boolean bIsDayOfMonth = true;
                String strDayOfMonth = "-";
                String strDayOfWeekOfMonth = "-";

                // Check if either of the field values belongs to "Day Of Month" type OR "Day Of Week Of Month"
                String[] sWeekNumberToCheck = strModeParamFieldValues[0].split(" ");
                if (sWeekNumberToCheck.length > 1) {
                    Integer weekDayNo = weekDays.get(sWeekNumberToCheck[1]);
                    if (weekDayNo != null) {
                        bIsDayOfMonth = false;
                    }
                }

                if (bIsDayOfMonth) {
                    // "Day Of Month" i.e. 1, 22, 30
                    long lDayOfMonth = 0;
                    for (int i = 0; i < strModeParamFieldValues.length; i++) {
                        String sCurrentFieldDayOfMonth = strModeParamFieldValues[i];
                        long lCurrentDayOfMonth = Long.parseLong(sCurrentFieldDayOfMonth);
                        long lDayOfMonthMaskedValue = 1L << lCurrentDayOfMonth;
                        lDayOfMonth = lDayOfMonth | lDayOfMonthMaskedValue;
                    }

                    if (0 != lDayOfMonth) {
                        strDayOfMonth = String.valueOf(lDayOfMonth);
                    }

                } else {
                    // "Day Of Week Of Month" i.e. "FIRST MONDAY,LAST FRIDAY"
                    long lDayOfWeekOfMonth = 0;
                    for (int i = 0; i < strModeParamFieldValues.length; i++) {
                        String strCurModeParamFieldValue = strModeParamFieldValues[i];
                        String[] strCurFieldValueList = strCurModeParamFieldValue.split(" ");
                        Integer curWeekInMonthNumber = weekNumber.get(strCurFieldValueList[0].toUpperCase());// FIRST
                        Integer curDayOfWeekNumber = weekDays.get(strCurFieldValueList[1].toUpperCase());// MONDAY
                        long lCurDayOfWeekOfMonth = ((curWeekInMonthNumber - 1) * 7) + curDayOfWeekNumber;// like 5, 22, 35.
                        long lDayOfWeekOfMonthMaskedValue = 1L << lCurDayOfWeekOfMonth;
                        lDayOfWeekOfMonth = lDayOfWeekOfMonth | lDayOfWeekOfMonthMaskedValue;
                    }

                    if (0 != lDayOfWeekOfMonth) {
                        strDayOfWeekOfMonth = String.valueOf(lDayOfWeekOfMonth);
                    }
                }
                strModeParamDBVal = strDayOfMonth + "," + strDayOfWeekOfMonth;
            }

        } else {
            // Error : Incorrect mode passed
        }
        System.out.println("[PSAFilterDBHandler::getModeParamDBValFromFieldText] Output value - ModeParamDBVal=" + strModeParamDBVal);
        System.out.println("[PSAFilterDBHandler::getModeParamDBValFromFieldText] END");
        return strModeParamDBVal;
    }

    public String getModeParamFieldTextFromDBVal(String strFilterMode, String strModeParamDBValue) {
        System.out.println("[PSAFilterDBHandler::getModeParamFieldTextFromDBVal] START");
        System.out.println("[PSAFilterDBHandler::getModeParamFieldTextFromDBVal] Input value - Filter mode=" + strFilterMode);
        System.out.println("[PSAFilterDBHandler::getModeParamFieldTextFromDBVal] Input value - Mode Param DB Value=" + strModeParamDBValue);
        String strModeParamFieldTextValue = "";
        int iStrModeParamDBValLength = strModeParamDBValue.length();
        if (0 != iStrModeParamDBValLength) {
            String weekDays[] = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" };
            String weekNumber[] = { "FIRST", "SECOND", "THIRD", "FOURTH", "LAST" };
            if (strFilterMode.equals("WEEKLY")) {
                char[] charWeekNumsValues = new char[strModeParamDBValue.length()];
                strModeParamDBValue.getChars(0, iStrModeParamDBValLength, charWeekNumsValues, 0);
                for (int i = 0; i < iStrModeParamDBValLength; i++) {
                    strModeParamFieldTextValue += weekDays[(int) charWeekNumsValues[i] - 49];
                    if (i != (iStrModeParamDBValLength - 1))
                        strModeParamFieldTextValue += ",";
                }
            } else if (strFilterMode.equals("MONTHLY")) {
                String[] strModeParamDBValues = strModeParamDBValue.split(",");
                int iModeParamDBValuesLength = strModeParamDBValues.length;
                if (iModeParamDBValuesLength == 2) {
                    String strDayOfMonth = strModeParamDBValues[0];
                    String strDayOfWeekOfMonth = strModeParamDBValues[1];
                    String strDayOfMonthFieldValue = "";
                    String strDayOfWeekOfMonthFieldValue = "";

                    // "Day Of Month" i.e. 1, 22, 30, 31
                    if (!strDayOfMonth.equals("-")) {
                        long lDayOfMonth = Long.parseLong(strDayOfMonth);

                        for (int i = 1; i < 32; i++) {
                            long lDayOfMonthMaskValue = 1L << i;
                            long lCurrentDayOfMonth = lDayOfMonth & lDayOfMonthMaskValue;

                            if (0 != lCurrentDayOfMonth) {
                                strDayOfMonthFieldValue += String.valueOf(i);
                                if (i != 31)
                                    strDayOfMonthFieldValue += ",";
                            }
                        }
                        int iLastComma = strDayOfMonthFieldValue.lastIndexOf(',');
                        String strExcludingLastComma = strDayOfMonthFieldValue.substring(0, iLastComma);
                        strModeParamFieldTextValue = strExcludingLastComma;
                    }

                    // "Day Of Week Of Month" i.e. "FIRST MONDAY,LAST FRIDAY"
                    if (!strDayOfWeekOfMonth.equals("-")) {
                        long lDayOfWeekOfMonth = Long.parseLong(strDayOfWeekOfMonth);

                        for (int i = 1; i < 36; i++) {
                            long lDayOfWeekOfMonthMaskValue = 1L << i;
                            long lCurrentDayOfWeekOfMonth = lDayOfWeekOfMonth & lDayOfWeekOfMonthMaskValue;

                            if (0 != lCurrentDayOfWeekOfMonth) {
                                int iCurWeekInMonthNumber = (i / 7) + 1;// FIRST
                                int iCurDayOfWeekNumber = i % 7;// MONDAY
                                String strCurDayOfWeekOfMonthFieldValue = weekNumber[iCurWeekInMonthNumber - 1] + " "
                                        + weekDays[iCurDayOfWeekNumber - 1];
                                strDayOfWeekOfMonthFieldValue += strCurDayOfWeekOfMonthFieldValue;
                                if (i != 35)
                                    strDayOfWeekOfMonthFieldValue += ",";
                            }
                        }
                        int iLastComma = strDayOfWeekOfMonthFieldValue.lastIndexOf(',');
                        String strExcludingLastComma = strDayOfWeekOfMonthFieldValue.substring(0, iLastComma);
                        strModeParamFieldTextValue = strExcludingLastComma;
                    }

                }

            } else {
                // Error : Incorrect mode passed
            }
        }
        System.out
                .println("[PSAFilterDBHandler::getModeParamFieldTextFromDBVal] Output value - ModeParamFieldTextValue=" + strModeParamFieldTextValue);
        System.out.println("[PSAFilterDBHandler::getModeParamFieldTextFromDBVal] END");
        return strModeParamFieldTextValue;
    }

    private boolean isModeParamDayOfMonth(String strModeParamFieldText) {
        boolean bIsDayOfMonth = true;
        Hashtable<String, Integer> weekDays;
        // Add week days to hashtable.
        weekDays = new Hashtable<String, Integer>();
        weekDays.put("MONDAY", new Integer(1));
        weekDays.put("TUESDAY", new Integer(2));
        weekDays.put("WEDNESDAY", new Integer(3));
        weekDays.put("THURSDAY", new Integer(4));
        weekDays.put("FRIDAY", new Integer(5));
        weekDays.put("SATURDAY", new Integer(6));
        weekDays.put("SUNDAY", new Integer(7));

        String[] strModeParamFieldValues = strModeParamFieldText.split(",");
        // Check if either of the field values belongs to "Day Of Month" type OR "Day Of Week Of Month"
        String[] sWeekNumberToCheck = strModeParamFieldValues[0].split(" ");
        if (sWeekNumberToCheck.length > 1) {
            Integer weekDayNo = weekDays.get(sWeekNumberToCheck[1]);
            if (weekDayNo != null) {
                bIsDayOfMonth = false;
            }
        }
        return bIsDayOfMonth;

    }

    private String calculateNextLaunchDate(PSAFilterModel filterModel) {
        System.out.println("[PSAFilterDBHandler::calculateNextLaunchDate] START");
        String strNextLaunchDate = "";
        String strCurrentDate = getCurrentDate();
        String strCurModeValue = filterModel.getMode();
        String strCurModeParamValue = filterModel.getModeParam();
        String[] strCurListNextLaunchDateVals = strCurModeParamValue.split(",");
        int iCurListNextLaunchDateValLength = strCurListNextLaunchDateVals.length;
        List<String> modeParamArrayList = new ArrayList<String>();
        if (iCurListNextLaunchDateValLength > 0) {
            LaunchDate launchDate = new LaunchDate();
            for (int i = 0; i < iCurListNextLaunchDateValLength; i++) {
                modeParamArrayList.add(strCurListNextLaunchDateVals[i]);
            }

            if (strCurModeValue.equals(MODE.DAILY.toString())) {
                strNextLaunchDate = launchDate.nextDayLaunchDate(strCurrentDate);
            }
            if (strCurModeValue.equals(MODE.WEEKLY.toString())) {
                strNextLaunchDate = launchDate.nextWeekDayLaunchDate(modeParamArrayList, strCurrentDate);
            }
            if (strCurModeValue.equals(MODE.MONTHLY.toString())) {
                boolean bIsDayOfMonth = isModeParamDayOfMonth(strCurModeParamValue);
                if (bIsDayOfMonth) {
                    // "Day Of Month" i.e. 1, 22, 30
                    strNextLaunchDate = launchDate.nextMonthLaunchDayOfMonth(modeParamArrayList, strCurrentDate);
                } else {
                    // "Day Of Week Of Month" i.e. "FIRST MONDAY,LAST FRIDAY"
                    strNextLaunchDate = launchDate.nextMonthLaunchDate(modeParamArrayList, strCurrentDate);
                }

            }
        }
        System.out.println("[PSAFilterDBHandler::calculateNextLaunchDate] Calculated Next launch date=" + strNextLaunchDate);
        System.out.println("[PSAFilterDBHandler::calculateNextLaunchDate] END");
        return strNextLaunchDate;

    }

}
