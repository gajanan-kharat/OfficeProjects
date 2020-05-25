/*
 * Creation : May 29, 2017
 */
package com.psa.PSATEStructCompare;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.psa.tools.PSAErrorHandler;
import com.psa.tools.PSALanguageHandler;
import com.psa.tools.PSAMessageDialog;
import com.psa.PSATEStructCompare.PSAStructUITools.USER_ACCESS;

public class PSAFilterUIController {
    private static Logger logger = Logger.getLogger("PSAStructCompareUIController");
    public static PSALanguageHandler objLanguageHandler;
    private static String m_StringModuleName = "PSATEStructCompare"; // Module Name
    private static PSAFilterPanel.ACTION filterAction;
    private static PSAFilterDBHandler filterDBHandler = new PSAFilterDBHandler();
    protected static List<PSAFilterModel> arrayListfilters = new ArrayList<PSAFilterModel>();
    protected static PSAFilterTableDataModel filterDataModel = new PSAFilterTableDataModel();
    protected static JScrollPane scrollPaneTable = new JScrollPane();
    protected static JTable tableFilter = new JTable();
    static PSAFilterModel selectedFilter;
    
    private PSAFilterUIController() {
        logger.info("PSAFilterUIController: START constructor");
        logger.info("PSAFilterUIController: END constructor");
    }

    public static void main(String[] args) {
        logger.info("[PSAFilterUIController:main] START");
            try {
                Init();
                
                PSAFilterMainUIDialog psaStructCompareUI = new PSAFilterMainUIDialog();
                psaStructCompareUI.dummy();
            } catch (PSAErrorHandler e) {
                // Close DB connections if any
                PSAFilterDBHandler.closeDatabaseConnection();
                displayMessage(e.m_StrErrorLabel);
                System.exit(e.ErrorCode);
            }

        logger.info("[PSAFilterUIController:main] END");
    }

    public static PSAFilterDBHandler getFilterDBHandler() {
        return filterDBHandler;
    }

    public static void setFilterDBHandler(PSAFilterDBHandler filterDBHandler) {
        PSAFilterUIController.filterDBHandler = filterDBHandler;
    }

    public static PSAFilterPanel.ACTION getFilterAction() {
        return filterAction;
    }

    public static void setFilterAction(PSAFilterPanel.ACTION filterAction) {
        PSAFilterUIController.filterAction = filterAction;
    }

    /**
     * It is used to initialize the language and database handler
     * 
     * @param String [] args Parameters
     */
    public static void Init() throws PSAErrorHandler {
        System.out.println("[PSAFilterUIController:Init] START");

        // Create Langauage Handler
        objLanguageHandler = new PSALanguageHandler();

        // Load Language File.
        Object[] error = objLanguageHandler.LoadLanguage(m_StringModuleName);

        // Language file loading failure handling
        if (false == ((Boolean) error[0]).booleanValue()) {
            JOptionPane.showMessageDialog(null, error[1]);
            System.out.println("[PSAFilterUIController:Init] END");
            System.exit(0);
        }

        if (PSAFilterDBHandler.strErrorLabel.length() > 0) {
            displayMessage(PSAFilterDBHandler.strErrorLabel);
            System.exit(0);
        }

        USER_ACCESS userAccess = filterDBHandler.getUserAppAccess();
        String strCurrentuser = filterDBHandler.getUser();
        System.out.println("[PSAFilterUIController:main] Access for this user is " + userAccess);
        if (userAccess == USER_ACCESS.INVALID)
        {
            String errorMsg = "Access denied for user : " + strCurrentuser + "!!!";
            System.out.println("[PSAFilterUIController:main] " + errorMsg);
            if(errorMsg.length() > 0)
            {
                @SuppressWarnings("unused")
                PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, errorMsg, objLanguageHandler);
                System.out.println("[PSAFilterUIController:main] END");
                System.exit(0);
            }
        }
        
        System.out.println("[PSAFilterUIController::Init] END");
    }

    /**
     * It used to display the message on message dialog.
     * 
     * @param ierror_msg_label : String
     */
    public static void displayMessage(String ierror_msg_label) {
        if (null != ierror_msg_label) {
            String error_msg = "";
            error_msg = objLanguageHandler.Getlabel(ierror_msg_label, error_msg);
            @SuppressWarnings("unused")
            PSAMessageDialog.OPTION opt = new PSAMessageDialog().showMessageDialog(null, error_msg, objLanguageHandler);
        }
    }

}
