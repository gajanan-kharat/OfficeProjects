/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobReport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.jobtypes.PSATETaskDetails;
import com.psa.plm.te.jobtypes.PSATimeOutJobs;
import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEMufoConfig;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public abstract class PSATEJobAnalysis {
    enum eJobType {
        None, DETMODIF, PUBSUB, ODN, DWCNIGHT, DWCDAY, DWCREP, DWCSTRUCT, DWCPAR, DWCEFF, USRDEMAND
    };

    private eJobType ejobType = eJobType.None;
    protected HashMap<String, String> mapTaskConfig = new HashMap<String, String>();
    protected HashMap<String, PSATETaskDetails> mapJobTasks = new HashMap<String, PSATETaskDetails>();
    protected ArrayList<String> listErrors = new ArrayList<String>();
    protected String strmufoJobID;
    protected String strMufoIDFolder = "";
    protected String strMufoJobPath = "";
    protected String strExaPath = "";
    protected String strExaProjectName = "";

    // Special processing
    protected String strSpecialKey = "";
    protected ArrayList<String> listSpecialValue = new ArrayList<String>();;

    // JobIDList
    protected ArrayList<String> listJobID = new ArrayList<String>();

    protected boolean bIsUpward = false;

    PSATEJobAnalysis(String strJobID) {
        strmufoJobID = strJobID;
        String strCurJobPATH = System.getenv("MU2_WSHARE");

        String strpathPattern = "(.*)" + System.getenv("MU2_USER") + ".*";
        String strSpoolerPath = PSATEPatternMatcher.execPattern(strpathPattern, strCurJobPATH, 1);

        String strpatternJobDetails = ".*/(?<UserID>\\w+)/(?<JobFolder>.*)";
        String[] strJobDetails = PSATEPatternMatcher.execPattern(strpatternJobDetails, strJobID);

        strMufoIDFolder = strJobDetails[1];
        strMufoJobPath = strSpoolerPath + File.separator + strJobDetails[0] + File.separator + strMufoIDFolder;

        strExaPath = System.getenv("TE_REP_REPORT") + File.separator + System.getenv("ETAT");
    }

    public abstract void Init();

    public abstract void populateJobTaskDetails();

    public static PSATEJobAnalysis GetJobAnalysisObject(String strJobID) {
        PSATEJobAnalysis jobObject = null;
        String strPatternJobName = ".*[/]+(?:\\w+)[/]+([\\w\\.\\-]+)";
        String strJobName = PSATEPatternMatcher.execPattern(strPatternJobName, strJobID, 1);
        if (strJobName.startsWith("DWC")) {
            System.out.println("**Launch needed for DOWN JOB** --> " + strJobName);
            jobObject = new PSATEDwcJobAnalysis(strJobID);
        } else {
            System.out.println("**Launch needed for UPWD JOB** --> " + strJobName);
            jobObject = new PSATEUpJobAnalysis(strJobID);
        }
        return jobObject;
    }

    public Set<String> getSubTaskId() {
        return mapJobTasks.keySet();
    }

    public PSATETaskDetails getsubTask(String strKey) {
        return mapJobTasks.get(strKey);
    }

    public String getProjectId() {
        return strExaProjectName;
    }

    public String getReportPath() {
        // TODO Auto-generated method stub
        return strExaPath;
    }

    private void setMufoJobReqTotal(String strJobType, PSATETaskDetails jobDetails) {
        String strFileTocheck = mapTaskConfig.get(strJobType);
        if (strFileTocheck != null && !strFileTocheck.equals("none")) {
            if (strFileTocheck.contains(strSpecialKey)) {
                jobDetails.nTotalRequired = 0;
                for (String folder : listSpecialValue) {
                    if (bIsUpward)
                        strFileTocheck = strFileTocheck.replace("<RECONFOLDER>", folder);
                    else
                        strFileTocheck = strFileTocheck.replace("<DWCINPUT>", folder);
                    jobDetails.nTotalRequired += PSATEGenericfunctions.getIntendedJobCount(strFileTocheck);
                }
            } else {
                jobDetails.nTotalRequired = PSATEGenericfunctions.getIntendedJobCount(strFileTocheck);
            }
        }

    }

    protected PSATETaskDetails createnSetJobType(String strType) {
        PSATETaskDetails jobDetails = new PSATETaskDetails();

        if (bIsUpward)
            jobDetails.strSubType = PSATEMufoConfig.mapUpJobs.get(strType);
        else
            jobDetails.strSubType = PSATEMufoConfig.mapDwcJobs.get(strType);

        if (jobDetails.strSubType.contains("|")) {
            String[] strSplit = jobDetails.strSubType.split("\\|");
            for (String value : strSplit) {
                setMufoJobReqTotal(value, jobDetails);
            }
        } else {
            setMufoJobReqTotal(jobDetails.strSubType, jobDetails);
        }

        return jobDetails;
    }

    protected ArrayList<String> getMufoJobID(StringBuilder rapportFileContent, String strType) {
        String strSubType = "";
        if (bIsUpward)
            strSubType = PSATEMufoConfig.mapUpJobs.get(strType);
        else
            strSubType = PSATEMufoConfig.mapDwcJobs.get(strType);
        String strPattern = PSATEMufoConfig.getJobTypePattern(strSubType);
        ArrayList<String> arrJobID = PSATEPatternMatcher.getMatchList(strPattern, rapportFileContent.toString(), 1);
        int nFound = arrJobID.size();
        if (nFound > 0) {
            arrJobID = PSATEGenericfunctions.getUniqueList(arrJobID);
            listJobID.addAll(arrJobID);
        }
        return arrJobID;
    }

    protected StringBuilder getRapportFlow() {
        String strRapportFile = this.strMufoJobPath + File.separator + "out" + File.separator + PSATEMufoConfig.STR_RAPPORT_FLOW;
        StringBuilder rapportFileContent = PSATEGenericfunctions.getFileContent(strRapportFile);

        return rapportFileContent;
    }

    protected void processTimeOutJobs(StringBuilder rapportFileContent) {
        ArrayList<String> arrJobID = PSATEPatternMatcher.getMatchList(PSATEMufoConfig.STR_JOBEXECUTED, rapportFileContent.toString(), 1);
        if (arrJobID != null && arrJobID.size() > 0) {
            ArrayList<String> toRemove = new ArrayList<String>();
            for (String jobid : arrJobID) {
                String strPattern = PSATEMufoConfig.STR_JOBVQLIDATETIMEOUT.replace("%JOBID%", jobid);
                String strValue = PSATEPatternMatcher.execPattern(strPattern, rapportFileContent.toString(), 1);
                if (strValue.length() > 0)
                    toRemove.add(jobid);
            }
            arrJobID.removeAll(toRemove);
            if (arrJobID.size() > 0) {
                PSATETaskDetails jobDetails = new PSATETaskDetails();
                jobDetails.strSubType = "TimeOutJob";
                jobDetails.nTotalRequired = arrJobID.size();
                // check the JOBS IDs which are not handled in the previous processing
                for (String jobid : arrJobID) {
                    if (!listJobID.contains(jobid)) {
                        PSATimeOutJobs objtimeOut = new PSATimeOutJobs(jobid);
                        jobDetails.listExaJobDetails = new ArrayList<PSAGenericJob>();
                        jobDetails.listExaJobDetails.add(objtimeOut);
                    }
                }
                if (jobDetails.listExaJobDetails.size() > 0) {
                    mapJobTasks.put("TimeOutJob", jobDetails);
                }
            }
        }
    }
}
