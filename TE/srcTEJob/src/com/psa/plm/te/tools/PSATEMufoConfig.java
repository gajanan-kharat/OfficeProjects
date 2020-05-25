/*
 * Creation : 29 juin 2016
 */
package com.psa.plm.te.tools;

import java.util.ArrayList;
import java.util.HashMap;

public class PSATEMufoConfig {
    public static final String STR_RAPPORT_FLOW = "rapport_flow.txt";

    // Get the JOB ID
    public static final String STR_PATTERN_JOBID = "(?m)^%JOBTYPE%:(?:_?)(\\d{5,}_\\d{1,}).*Debut.*";
    // Get the Parent JOB ID
    public static final String STR_PARENT_JOBID = "(?m)^%JOBTYPE%:%JOBID%;(?:\\:?\\s?)Parent\\sJobID:\\s(\\d+_\\d+)";
    // Get the JOB Details
    public static final String STR_PATTERN_JOBDETAILS = "(?m)^%JOBTYPE%:(?:_?)%JOBID%;(?:\\:?\\s?)([\\w\\s]+):([A-Z|a-z 0-9 :\\./]+)";
    // Get the LAME/VM details
    public static final String STR_LAME_JOBDETAILS = "(?m)^%JOBID%:\\w+:(?<LAME>\\w+).*DEBUT:\\s+?(?<STARTTIME>.*),\\sFIN:(?<ENDTIME>.*),\\sCPU.*";

    // Needed JOBIDS to check timeout jobs
    public static final String STR_JOBEXECUTED = "(?m)^(\\d+_\\d+):\\w+:(?:\\w+).*DEBUT:\\s+?(?:.*),\\sFIN:(?:.*),\\sCPU.*";
    public static final String STR_JOBVQLIDATETIMEOUT = "(?m)^([\\w]+):(?:_?)(:?%JOBID%).*Debut.*";

    // Get the configuration details set for mufo paths
    public static final String STR_PATTERN_CONFIGPATH = "^(?<JOBPATH>.*)\\|(?<FINDOPTION>\\w+)=(?<EXPRESSION>.*)";

    // For VPMExchangeAutomation Parser
    public static final String STR_STATUS_BLOCK = ".*\\<[P]\\>(.*)\\<[/][P]\\>\\<HR\\>\\<P.*";
    public static final String STR_HTML_ERROR_DETAILS = "(?:\\<TD.*?\\>)(.*?)(?:\\</TD\\>)+";
    public static final String STR_ERROR_MESSAGE_PATTERN = "\\<StatusDetailInfo\\>(?<Message>\\w.*?)\\</StatusDetailInfo\\>";
    public static final String STR_ERROR_MESSASE_TRANSACTIONXML = "(?:\\<te\\:Error\\>)(?:\\<te\\:Message\\>)(?<Message>.*?)(?:\\</te\\:Message\\>).*?(?:\\<te\\:LocalID\\>)(?<LocalID>.*?)(?:\\</te\\:LocalID\\>).*?(?:\\<te\\:Severity\\>)(?<Severity>.*?)(?:\\</te\\:Severity\\>)(?:\\</te\\:Error\\>)";

    // For the P.S.A XML
    // parsing to get the list of KO object and its first global message
    public static final String STR_ERROR_DETAILS_PSA_XFLOW_XML = "\\<Object\\sIdentifier=\"\\w+\\s\\w+\\s\\w+\\s[\\w\\$]+\"\\sType=\"([\\w \\(\\)\\-\\>]+)\"\\sStatus=\"KO\"\\>(?:<StatusDetailInfo\\sLevel=\"\\w+\"\\sType=\"Specific\"\\>\\[ID\\]\\s)(.*?)(?:\\<[/]StatusDetailInfo\\>)(?:.*?)Type=\"Global\"\\>(.*?)(?:\\<[/]StatusDetailInfo\\>)";
    // parsing the Global status present in the XFLOX XML
    public static final String STR_GLOBAL_STATUS_XFLOW_XML = "\\<GlobalStatus\\s+Status=\"(\\w+)\"/\\>";

    public static final String STR_BA_ERROR_LOG = "(?m)^\\d+(?:.*mxJPO\\w+\\s\\-\\s\\w+\\s\\:\\s)(.*)";

    // Mail Details
    // Test User Details
    public static final String mail_test_from = "ccaotest@mpsa.com";
    public static final String mail_test_user = "inetpsa/mwplsf99";
    public static final String mail_test_password = "mwp99lsf";
    // Prod User Details
    public static final String mail_prod_from = "ccao@mpsa.com";
    public static final String mail_prod_user = "inetpsa/mzplsf99";
    public static final String mail_prod_password = "mzp99lsf";
    // SMTP details
    public static final String mail_host = "smtp.inetpsa.com";
    public static final String mail_transport_protocol = "smtp";
    public static final String mail_prod_smtp_host = "smtp-auth.mpsa.com";// "smtp-ipfilter.mpsa.com";
    public static final String mail_test_smtp_host = "smtp-auth.preprod.mpsa.com";
    public static final String mail_smtp_port = "25";

    // HTML Style
    public static final String strTableCSS = "<style type=\"text/css\">\n"
            + ".tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}\n"
            + ".tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}\n"
            + ".tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}\n"
            + ".tg .tg-2ktp{font-size:16px;vertical-align:top}" + ".tg .tg-b7b8{background-color:#f9f9f9;vertical-align:top}\n"
            + ".tg .tg-dzk6{background-color:#f9f9f9;text-align:center;vertical-align:top}\n" + ".tg .tg-yw4l{vertical-align:top}\n"
            + ".tg .tg-ya56{text-align:center;vertical-align:top}\n" + "</style>\n";

    // Mail Static Details
    public static final String strMailMessage = "<p>\n" + "Bonjour,\n" + "</br>\n" + "</br>\n" + "<pre>\n" + "Synthèse du job TE :\n"
            + "•   <a href=\"#REPRORTLINK#\">#PROJECT_NAME#</a>\n" + "    ?   Complétude de l'envoi : #EXECSTATUS#\n"
            + "    ?   Résultat : #RETSTATUS#\n" + "</pre>\n" + "</p>\n";

    // CSV Separator
    public static final String CSV_SEPARATER = ";";
    public static final String CSV_TOTALNEEDED = "Complétude;Nombre de jobs prévus";
    public static final String CSV_TOTALEXECUTED = "Complétude;Nombres de jobs réalisés";
    public static final String CSV_CODERETURN = "Code retour ";
    public static final String CSV_RESULT = "Résultat";
    public static final String CSV_Error_NumJob = "n° du job";
    public static final String CSV_Error_JOBID = "Occurrences";
    public static final String CSV_Error_Message = "Message d'erreur";
    public static final String CSV_Error_Parts = "Références concernées";
    // Error code to be skipped in CSV
    public static final ArrayList<String> listCSVRetCodeToIgnore = new ArrayList<String>() {
        {
            add("99");
        }
    };
    // Processing to be skipped in CSV...
    public static final ArrayList<String> listToSkipInCSV = new ArrayList<String>() {
        {
            add("TimeOutJob");
            add("plm_te_psa");
            add("plm_tf_detmodif");
            add("plm_te_launchreconciliation");
            add("plm_te_fixation_fixation");
        }
    };

    // CSV header files..
    public static final HashMap<String, String> mapCSVHeader = new HashMap<String, String>() {
        {
            put("plm_te_upward_catdrawing", "catdraw");
            put("plm_te_upward_model", "model");
            put("plm_te_upward_catpart", "catpart");
            put("plm_te_upward_catpart_Simulation", "pre-catpart");
            put("plm_te_deletepsa", "delette");
            put("plm_tf_detmodif", "detmodif");
            put("plm_tf_effectivity", "diversite");
            put("plm_te_fixation_fixation", "fixation");
            put("plm_te_upward_full", "full");
            put("plm_te_upward_instance", "instance");
            put("plm_te_posttraitement_INSTANCIATION", "instanciation");
            put("plm_te_launchreconciliation", "launchreconciliation");
            put("plm_te_mastership", "mastership");
            put("plm_te_posttraitement_MATRIXPOS", "matrixpos");
            put("plm_te_upward_model", "model");
            put("plm_te_psa", "psa");
            put("plm_te_prepare_rep", "PrepareRep");
            put("plm_te_reconciliation", "reconciliation");
            put("plm_te_upward_reference", "reference");
            put("plm_tf_dwc", "tf-dwc");
            put("plm_te_dwc", "te-dwc");
            put("plm_te_exalead_downward", "exalead-downward");
            put("plm_tf_divexp", "tf-divexp");
            put("plm_tf_divimp", "tf-divimp");
            put("plm_te_exalead_PSAApplicationEffectivite", "exalead-PSAApplicationEffectivite");
            put("plm_te_prepare_dwcrep", "prepare-dwcrep");
            put("plm_te_dwc_init_reprelaunch", "dwc-init-reprelaunch");
            put("plm_te_dwc_relaunchrep", "te-reprelaunch");
            put("plm_te_dwc_simulation", "dwc_simulation");
            put("plm_te_dwc_tref", "dwc_terminal-ref");
            put("plm_te_dwc_ntref", "dwc_non-terminal-ref");
            put("plm_te_dwc_inst", "dwc_instance");
            put("TimeOutJob", "timeout");
        }
    };

    public static final ArrayList<String> listOrderforCSV = new ArrayList<String>() {
        {
            add("plm_te_upward_full");
            add("plm_te_upward_reference");
            add("plm_te_upward_instance");
            add("plm_te_upward_catpart_Simulation");
            add("plm_te_upward_catpart");
            add("plm_te_upward_model");
            add("plm_te_upward_catdrawing");
            add("plm_te_mastership");
            add("plm_te_deletepsa");
            add("plm_tf_effectivity");
            add("plm_te_reconciliation");
            add("plm_te_posttraitement_INSTANCIATION");
            add("plm_te_posttraitement_MATRIXPOS");
            add("plm_te_dwc");
            add("plm_tf_divexp");
            add("plm_tf_divimp");
            add("plm_te_dwc_init_reprelaunch");
            add("plm_te_dwc_relaunchrep");
            add("plm_te_dwc_simulation");
            add("plm_te_dwc_tref");
            add("plm_te_dwc_ntref");
            add("plm_te_dwc_inst");
        }
    };

    public static final HashMap<String, String> mapUpJobs = new HashMap<String, String>() {
        /**
		 * 
		 */
        private static final long serialVersionUID = 1L;

        {
            put("PSACATDrawingJob", "plm_te_upward_catdrawing");
            put("PSACATPartJob", "plm_te_upward_catpart");
            put("PSACATPartSimulationJob", "plm_te_upward_catpart_Simulation");
            put("PSADeleteJob", "plm_te_deletepsa");
            put("PSADetModifJob", "plm_tf_detmodif");
            put("PSAEffectivityJob", "plm_tf_effectivity");
            put("PSAFixationJob", "plm_te_fixation_fixation");
            put("PSAFullJob", "plm_te_upward_full");
            put("PSAInstanceJob", "plm_te_upward_instance");
            put("PSAInstantiationJob", "plm_te_posttraitement_INSTANCIATION");
            put("PSALaunchReconciliationJob", "plm_te_launchreconciliation");
            put("PSAMastershipJob", "plm_te_mastership");
            put("PSAMatrixPosJob", "plm_te_posttraitement_MATRIXPOS");
            put("PSAModelJob", "plm_te_upward_model");
            put("PSAPrdStrtAnlysJob", "plm_te_psa");
            put("PSAPrepareRepUpJob", "plm_te_prepare_rep");
            put("PSAReconciliationJob", "plm_te_reconciliation");
            put("PSAReferenceJob", "plm_te_upward_reference");
        }
    };

    public static final HashMap<String, String> mapDwcJobs = new HashMap<String, String>() {
        /**
		 * 
		 */
        private static final long serialVersionUID = 1L;

        {
            put("PSATfDwcJob", "plm_tf_dwc");
            put("PSATeDwcJob", "plm_te_dwc");
            put("PSATeExaDwcJob", "plm_te_exalead_downward");
            put("PSATfDivExpJob", "plm_tf_divexp");
            put("PSATfDivImpJob", "plm_tf_divimp");
            put("PSAExaAppEffJob", "plm_te_exalead_PSAApplicationEffectivite");
            put("PSAPrepareRepDwcJob", "plm_te_prepare_dwcrep");
            put("PSATeDwcRepRelaunchJob", "plm_te_dwc_relaunchrep");
            put("PSATeDwcInitRepRelaunchJob", "plm_te_dwc_init_reprelaunch");
            put("PSATeDwcSimulationJob", "plm_te_dwc_simulation");
            put("PSATeDwcTerminalJob", "plm_te_dwc_tref");
            put("PSATeDwcNonTerminalJob", "plm_te_dwc_ntref");
            put("PSATeDwcInstanceJob", "plm_te_dwc_inst");
        }
    };

    // For Mufo JOBS the path where the files needed to be checked for UPWARD FLOW
    public static final HashMap<String, String> mapUpJobPath = new HashMap<String, String>() {
        {
            put("plm_te_upward_catdrawing", "<JOBPATH>\\data\\<PSAFOLDER>\\cao|expf=catdr.*vpmuuid_lst");
            put("plm_te_upward_catpart", "<JOBPATH>\\data\\simulation\\cao|expf=catpart.*vpmuuid_lst");
            put("plm_te_upward_catpart_Simulation", "<JOBPATH>\\data\\<PSAFOLDER>\\cao_for_simul|expf=catpart.*vpmuuid_lst");
            put("plm_te_deletepsa", "<JOBPATH>\\data\\<PSAFOLDER>\\delete|expf=delete.*vpmuuid_lst");
            put("plm_tf_detmodif", "none");
            put("plm_tf_effectivity", "<JOBPATH>\\data\\<PSAFOLDER>\\diversite|expf=effectivity.*csv");
            put("plm_te_fixation_fixation", "<JOBPATH>\\data\\ProductStructureAnalysis|expf=.*fixation.csv");
            put("plm_te_upward_full", "<JOBPATH>\\data\\<PSAFOLDER>\\full|expf=full.*vpmuuid_lst");
            put("plm_te_upward_instance", "<JOBPATH>\\data\\<PSAFOLDER>\\instance|expf=instance.*vpmuuid_lst");
            put("plm_te_posttraitement_INSTANCIATION", "<JOBPATH>\\data\\reconciliation\\<RECONFOLDER>|expf=INSTANCIATION_output.xml");
            put("plm_te_launchreconciliation", "none");
            put("plm_te_mastership", "<JOBPATH>\\data\\ID_LIST|expf=ID_LIST.*xml");
            put("plm_te_posttraitement_MATRIXPOS", "<JOBPATH>\\data\\reconciliation\\<RECONFOLDER>|expf=MATRIXPOS_output_.*xml");
            put("plm_te_upward_model", "<JOBPATH>\\data\\<PSAFOLDER>\\cao|expf=model.*vpmuuid_lst");
            put("plm_te_psa", "<JOBPATH>\\data\\ProductStructureAnalysis|expf=Report_XFLOW.*xml");
            put("plm_te_exalead_PSAProductStructureAnalysis", "<JOBPATH>\\data\\ProductStructureAnalysis|expf=Report_XFLOW.*xml");
            put("plm_te_prepare_rep", "<JOBPATH>\\data\\simulation\\FromCATBatchStarter|expf=DBDI.*txt");
            put("plm_te_reconciliation", "<JOBPATH>\\data\\<PSAFOLDER>\\diagonal|expf=diagonal.*xml");
            put("plm_te_upward_reference", "<JOBPATH>\\data\\<PSAFOLDER>\\reference|expf=reference.*vpmuuid_lst");
        }
    };

    public static final HashMap<String, String> mapDwnSpecialInput = new HashMap<String, String>() {
        {
            put("DWCREP:plm_te_dwc", "dwc_representation");
            put("DWCSTRUCT:plm_te_dwc", "te_dwc");
            put("DWCDAY:plm_te_dwc", "te_dwc");
            put("DWCNIGHT:plm_te_dwc", "te_dwc");
            put("DWCPAR:plm_te_dwc_simulation", "dwc_simu");
            put("DWCPAR:plm_te_dwc_tref", "Terminal");
            put("DWCPAR:plm_te_dwc_ntref", "NonTerminal");
            put("DWCPAR:plm_te_dwc_inst", "Instance");
        }
    };

    public static final HashMap<String, String> mapDwnJobPath = new HashMap<String, String>() {
        {
            put("plm_tf_dwc", "none");
            // Depending on the job its te_dwc or dwc_representation (only for REP)
            put("plm_te_dwc", "<JOBPATH>\\data\\<DWCINPUT>|expf=.*\\.xml");
            put("plm_te_exalead_downward", "<JOBPATH>\\data|expf=cmd_exalead_\\d+_\\d+.xml");
            put("plm_tf_divexp", "<JOBPATH>\\data\\te_dwc|expf=.*xml");
            put("plm_tf_divimp", "<JOBPATH>\\data|expf=cmd_tf_divimp_\\d+_\\d+.xml");
            put("plm_te_exalead_PSAApplicationEffectivite", "<JOBPATH>\\data|expf=.*exalead_\\d+_\\d+.xml");
            put("plm_te_prepare_dwcrep", "<JOBPATH>\\data\\representation|expf=.*xml");
            put("plm_te_dwc_init_reprelaunch", "<JOBPATH>\\data\\redoRep\\|expl2=\\d{5,}_\\d{1,}¤.*xml");
            put("plm_te_dwc_relaunchrep", "<JOBPATH>\\data\\relaunchRep\\|expl2=\\d{5,}_\\d{1,}¤.*xml");
            put("plm_te_dwc_simulation", "<JOBPATH>\\data\\te_dwc|expf=.*.xml");
            put("plm_te_dwc_tref", "<JOBPATH>\\data\\Terminal|expf=.*.xml");
            put("plm_te_dwc_ntref", "<JOBPATH>\\data\\NonTerminal|expf=.*.xml");
            put("plm_te_dwc_inst", "<JOBPATH>\\data\\Instance|expf=.*.xml");
        }
    };

    public static final ArrayList<String> listPSASubFolders = new ArrayList<String>() {
        {
            add("cao");
            add("delete");
            add("diagonal");
            add("diversite");
            add("full");
            add("instance");
            add("reference");
        }
    };

    public static String getJobTypePattern(String strJobType) {
        return STR_PATTERN_JOBID.replace("%JOBTYPE%", strJobType);
    }

    public static String getJobDetailsPattern(String strJobType, String strJobID) {
        return STR_PATTERN_JOBDETAILS.replace("%JOBTYPE%", strJobType).replace("%JOBID%", strJobID);
    }

    public static String getParentJobIDPattern(String strJobType, String strJobID) {
        return STR_PARENT_JOBID.replace("%JOBTYPE%", strJobType).replace("%JOBID%", strJobID);
    }

}
