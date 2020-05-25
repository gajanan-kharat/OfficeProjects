/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.summary;

import java.util.HashMap;
import java.util.Properties;

import com.psa.plm.te.jobReport.PSATEJobAnalysis;
import com.psa.plm.te.mail.PSAJobSendMail;
import com.psa.plm.te.mail.PSAJobStatusHtmlTable;
import com.psa.plm.te.processReport.PSATEErrorCSVOperator;
import com.psa.plm.te.processReport.PSATESummaryCSVOperator;
import com.psa.plm.te.processReport.PSATETechnicalObject;

public class PSATEJobStatusController {
    static Properties propfile = null;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("*********Usage*********");
            System.out.println("com.psa.plm.te.summary.PSATEJobStatusController <mufo_job_ID>");
            System.exit(1);
        }

        System.out.println("*******Starting the processing for : " + args[0]);

        PSAJobSendMail mail = new PSAJobSendMail();

        PSATEJobAnalysis objMufoJob = PSATEJobAnalysis.GetJobAnalysisObject(args[0]);
        objMufoJob.Init();
        objMufoJob.populateJobTaskDetails();

        System.out.println("*******Details of the job populated*******");

        // Create the technical objects
        HashMap<String, PSATETechnicalObject> mapTechObject = PSATETechnicalObject.createTechnicalObject(objMufoJob);

        if (mapTechObject.size() == 0) {
            System.out.println("No job details found for processing !!!!");
            return;
        }

        // Create the output csv file
        System.out.println("*******Creation of Summary CSV*******");
        PSATESummaryCSVOperator objcsv = new PSATESummaryCSVOperator("Synthese.csv");
        if (objcsv.InitCSVfile(mapTechObject.keySet()) == 0) {
            objcsv.storeReturnStatus(mapTechObject);
            objcsv.closefile();
            mail.setSummaryCSV(objcsv.getCSVPath());
        }

        // Create the error csv file
        System.out.println("*******Creation of Error Messages CSV*******");
        PSATEErrorCSVOperator objErrcsv = new PSATEErrorCSVOperator("Message_d_erreur.csv");
        if (objErrcsv.InitCSVfile() == 0) {
            objErrcsv.storeErrorDetails(mapTechObject);
            objErrcsv.closefile();
            mail.setErrorCSV(objErrcsv.getCSVPath());
        }

        // Generate HTML table for mail
        System.out.println("*******Creation of HTML table*******");
        PSAJobStatusHtmlTable htmlTable = new PSAJobStatusHtmlTable();
        htmlTable.createRetCodeTable(mapTechObject);

        // Get the JobStatus to set in subject line
        System.out.println("*******Compute job status*******");
        String strStatusExec = PSATETechnicalObject.GetExecutionStatus(mapTechObject);
        String strReturncodeStatus = PSATETechnicalObject.GetReturnCodeStatus(mapTechObject);

        // Get the TO in the mail..
        if (System.getenv("MU2_PARAM_EMAIL") != null) {
            System.out.println("*******Sending mail to : " + System.getenv("MU2_PARAM_EMAIL"));
            mail.seToId(System.getenv("MU2_PARAM_EMAIL"));
            // Set the details and send the mail.
            mail.setSubjectLine(objMufoJob.getProjectId(), objMufoJob.getReportPath(), strStatusExec, strReturncodeStatus);
            mail.sendMail(htmlTable.getTableData(), "text/html; charset=UTF-8");
        }
        System.out.println("*******Process Complete*******");
    }
}
