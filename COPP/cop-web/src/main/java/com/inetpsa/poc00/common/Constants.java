/*
 * Creation : Apr 7, 2016
 */
package com.inetpsa.poc00.common;

/**
 * The Class Constants.
 */
public class Constants {
	/** The Constant LDAP ROLE PREFIX. */
	public static final String LDAP_ROLE_PREFIX = "POC";

	/** The Constant INSERT. */
	public static final String INSERT = "INSERT";

	/** The Constant UPDATE. */
	public static final String UPDATE = "UPDATE";

	/** The Constant DELETE. */
	public static final String DELETE = "DELETE";

	/** The Constant NO_CHANGE. */
	public static final String NO_CHANGE = "NO_CHANGE";

	/** The Constant TDL. */
	public static final String TDL = "TDL";

	/** The Constant TCL. */
	public static final String TCL = "TCL";

	/** The Constant FCL. */
	public static final String FCL = "FCL";

	/** The Constant PLL. */
	public static final String PLL = "PLL";

	/** The Constant DRAFT. */
	public static final String DRAFT = "DRAFT";

	/** The Constant VALID. */
	public static final String VALID = "VALID";

	/** The Constant EMSTABLE. */
	public static final String EMSTABLE = "COPQTAES";

	/** The Constant EMS_DEP_TDL_TABLE. */
	public static final String EMS_DEP_TDL_TABLE = "COPQTEDL";

	/** The Constant EMS_DEP_TCL_TABLE. */
	public static final String EMS_DEP_TCL_TABLE = "COPQTECL";

	/** The Constant EMS_DEP_FCL_TABLE. */
	public static final String EMS_DEP_FCL_TABLE = "COPQTFLS";

	/** The Constant EMS_DEP_PLL_TABLE. */
	public static final String EMS_DEP_PLL_TABLE = "COPQTPLL";

	/** The Constant TEHNICAL_GROUP_TABLE. */
	public static final String TEHNICAL_GROUP_TABLE = "COPQTTGP";

	/** The Constant REGULATION_GROUP_TABLE. */
	public static final String REGULATION_GROUP_TABLE = "COPQTTRG";

	/** The Constant MAX_EMS_VERSION_QUERY. */
	public static final String MAX_EMS_VERSION_QUERY = "SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM " + EMSTABLE + " T WHERE T.LABEL = ?";

	/** The Constant MAX_EMS_DEP_TDL_VERSION_QUERY. */
	public static final String MAX_EMS_DEP_TDL_VERSION_QUERY = "SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM " + EMS_DEP_TDL_TABLE + " T WHERE T.LABEL = ?";

	/** The Constant MAX_EMS_DEP_TCL_VERSION_QUERY. */
	public static final String MAX_EMS_DEP_TCL_VERSION_QUERY = "SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM " + EMS_DEP_TCL_TABLE + " T WHERE T.LABEL = ?";

	/** The Constant MAX_EMS_DEP_FCL_VERSION_QUERY. */
	public static final String MAX_EMS_DEP_FCL_VERSION_QUERY = "SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM " + EMS_DEP_FCL_TABLE + " T WHERE T.LABEL = ?";

	/** The Constant MAX_EMS_DEP_PLL_VERSION_QUERY. */
	public static final String MAX_EMS_DEP_PLL_VERSION_QUERY = "SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM " + EMS_DEP_PLL_TABLE + " T WHERE T.LABEL = ?";

	/** The Constant EMS_DEP_PLL_Label_QUERY. */
	public static final String EMS_DEP_PLL_LABEL_QUERY = "SELECT T.ID FROM " + EMS_DEP_TDL_TABLE + " T WHERE T.LABEL = ?1 AND T.EMS_ID=?2";

	/** The Constant MAX_TEHNICAL_GROUP_TABLE_VERSION_QUERY. */
	public static final String MAX_TEHNICAL_GROUP_TABLE_VERSION_QUERY = "SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM " + TEHNICAL_GROUP_TABLE + " T WHERE T.LABEL= ?";

	/** The Constant MAX_REGULATION_GROUP_TABLE_VERSION_QUERY. */
	public static final String MAX_REGULATION_GROUP_TABLE_VERSION_QUERY = "SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM " + REGULATION_GROUP_TABLE + " T WHERE T.LABEL = ?";

	/** The Constant QUESTIONMARK. */
	// Characters Used for Search
	public static final char QUESTIONMARK = '?';

	/** The Constant TILDE_QUESTIONMARK. */
	public static final String TILDE_QUESTIONMARK = "~?";

	/** The Constant UNDERSCORE. */
	public static final char UNDERSCORE = '_';

	/** The Constant ASTERIC. */
	public static final char ASTERIC = '*';

	/** The Constant WILDCARD_PERCENTAGE. */
	public static final char WILDCARD_PERCENTAGE = '%';

	/** The Constant MIN_CO2_LIMIT. */
	public static final String MIN_CO2_LIMIT = "Lim_CO2_Mini";

	/** The Constant MAX_CO2_LIMIT. */
	public static final String MAX_CO2_LIMIT = "Lim_CO2_Maxi";

	/** The Constant TVV_VALUED_TDL. */
	public static final String TVV_VALUED_TDL = "TvvValuedTDL";

	/** The Constant TVV_VALUED_TCL. */
	public static final String TVV_VALUED_TCL = "TvvValuedTCL";

	/** The Constant TVV_VALUED_ES_DEP_TDL. */
	public static final String TVV_VALUED_ES_DEP_TDL = "TvvValuedEsDepTDL";

	/** The Constant TVV_VALUED_ES_DEP_TCL. */
	public static final String TVV_VALUED_ES_DEP_TCL = "TvvValuedEsDepTCL";

	/** The Constant TVV_VALUED_ES_DEP_FCL. */
	public static final String TVV_VALUED_ES_DEP_FCL = "TvvValuedEsDepFCL";

	/** The Constant TVV_VALUED_ES_DEP_PGL. */
	public static final String TVV_VALUED_ES_DEP_PGL = "TvvValuedEsDepPGL";

	/** The Constant TVV_TABLE. */
	public static final String TVV_TABLE = "COPQTTVV";

	/** The Constant MAX_TVV_VERSION_QUERY. */
	public static final String MAX_TVV_VERSION_QUERY = "SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM " + TVV_TABLE + " T WHERE T.LABEL = ?";

	/** The Constant DATATYPE_INT. */
	public static final String DATATYPE_INT = "INTEGER";

	/** The Constant DATATYPE_STRING. */
	public static final String DATATYPE_STRING = "STRING";

	/** The Constant DATATYPE_BOOLEAN. */
	public static final String DATATYPE_BOOLEAN = "BOOLEAN";

	/** The Constant DATATYPE_REAL. */
	public static final String DATATYPE_REAL = "REAL";

	/** The Constant DATATYPE_FLOAT. */
	public static final String DATATYPE_FLOAT = "FLOAT";

	/** The Constant DATATYPE_DATE. */
	public static final String DATATYPE_DATE = "DATE";

	/** The Constant DATATYPE_HOUR. */
	public static final String DATATYPE_HOUR = "HOUR";

	/** The Constant LIMIT_CO2. */
	public static final String LIMIT_CO2 = "Lim_CO2";

	/** The technical data. */
	public static final String TECHNICALDATA = "Donnée Technique";

	/** The tech condition data. */
	public static final String TESTCONDITIONDATA = "Condition d’essai";

	/** The technical limit data. */
	public static final String POLLUTANTLIMITDATA = "Limite";

	/** The Constant COASTDOWN_SCREEN_ID. */
	public static final String COASTDOWN_SCREEN_ID = "COAST_DOWN";

	/** The Constant SPECIFICCOP_SCREEN_ID. */
	public static final String SPECIFICCOP_SCREEN_ID = "SPECIFIC_COP";

	/** The Constant COMMONGENOME_SCREEN_ID. */
	public static final String COMMONGENOME_SCREEN_ID = "COMMON_GENOME";

	/** The Constant SAMPLINGRULE_SCREEN_ID. */
	public static final String SAMPLINGRULE_SCREEN_ID = "SAMPLING_RULE";

	/** The Constant EXPORT_BCE_SHEET_NAME. */
	public static final String EXPORT_BCE_SHEET_NAME = "EXPORT_BCE";

	/** The Constant HISTORY_MODIFICATION_TYPE_CREATE. */
	public static final String HISTORY_MODIFICATION_TYPE_CREATE = "ajout de";

	/** The Constant HISTORY_MODIFICATION_TYPE_DELETE. */
	public static final String HISTORY_MODIFICATION_TYPE_DELETE = "suppression de";

	/** The Constant HISTORY_MODIFICATION_TYPE_MODIFIED. */
	public static final String HISTORY_MODIFICATION_TYPE_MODIFIED = "modification de";

	/** The Constant CLASS. */
	public static final String CLASS = "class";

	/** The Constant ENTITY_ID. */
	public static final String ENTITY_ID = "entityID";

	/** The Constant MANDATORY_TVVDEPTDL_ID. */
	public static final String MANDATORY_TVVSTURCTURE_TDLIST_ID = "TVV_DEP_TDL";

	/** The Constant MANDATORY_TVVDEPTCL_ID. */
	public static final String MANDATORY_TVVSTURCTURE_TCLIST_ID = "TVV_DEP_TCL";

	/** The Constant TVV_DEPENDENT_TDL_SCREEN. */
	public static final String TVV_DEPENDENT_TDL_SCREEN = "TVV_DEPENDENT_TDL";

	/** The Constant TVV_DEPENDENT_TCL_SCREEN. */
	public static final String TVV_DEPENDENT_TCL_SCREEN = "TVV_DEPENDENT_TCL";

	/** The Constant TVV_SCREEN_ID. */
	public static final String TVV_SCREEN_ID = "TVV";

	/** The Constant EMS_DEPENDENT_TDL_SCREEN. */
	public static final String EMS_DEPENDENT_TDL_SCREEN = "EMS_DEPENDENT_TDL";

	/** The Constant EMS_DEPENDENT_TCL_SCREEN. */
	public static final String EMS_DEPENDENT_TCL_SCREEN = "EMS_DEPENDENT_TCL";

	/** The Constant EMS_DEPENDENT_FCL_SCREEN. */
	public static final String EMS_DEPENDENT_FCL_SCREEN = "EMS_DEPENDENT_FCL";

	/** The Constant EMS_DEPENDENT_PGL_SCREEN. */
	public static final String EMS_DEPENDENT_PGL_SCREEN = "EMS_DEPENDENT_PGL";

	/** The Constant VEHICLE_COUNTERMARK. */
	public static final String VEHICLE_COUNTERMARK = "counterMark";

	/** The Constant VEHICLE_CHASISNUMBER. */
	public static final String VEHICLE_CHASISNUMBER = "chasisNumber";

	/** The Constant VEHICLE_REGISTRATIONNUMBER. */
	public static final String VEHICLE_REGISTRATIONNUMBER = "registrationNumber";

	/** The Constant VEHICLE_TVVSTATUS_VALID. */
	public static final String VEHICLE_TVVSTATUS_VALID = "Valid";

	/** The Constant VEHICLEFILESTATUS_LABEL. */
	public static final String VEHICLEFILESTATUS_LABEL = "Received";

	/** The Constant VEHICLE_TVVSTATUS_RECEIVED. */
	public static final String VEHICLEFILESTATUS_ARCHIVED = "Archived";

	/** The Constant VEHICLE_TVVSTATUS_Preparation complete. */
	public static final String VEHICLEFILESTATUS_PREPARATIONCOMPLETE = "Preparation complete";

	/** The Constant VEHICLE_TVVSTATUS_Preparation complete. */
	public static final String VF_STATUS_PREP_IN_PROGRESS = "Preparation in progress";

	public static final String VEHICLEFILESTATUS_RETURNED = "Returned";

	/** The Constant VEHICLEFILESTATUS_TO_RETURN. */
	public static final String VEHICLEFILESTATUS_TO_RETURN = "To Return";

	/** The Constant VEHICLEFILESTATUS_EVACOP_COMPLETE. */
	public static final String VEHICLEFILESTATUS_EVACOP_COMPLETE = "EVA COP test complete";

	/** The Constant PGLABEL_CO2. */
	public static final String PGLABEL_CO2 = "CO2";

	/** The Constant VEHICLEFILE_SCREEN_ID. */
	public static final String VEHICLEFILE_SCREEN_ID = "VEHICLE_FILE";

	/** The Constant VEHICLEFILE_HISTORY_DESCRIPTION. */
	public static final String VEHICLEFILE_HISTORY_DESCRIPTION = "Réceptionné";

	/** The Constant RECEPTIONFILE_SCREEN_ID. */
	public static final String RECEPTIONFILE_SCREEN_ID = "RECEPTION_FILE";

	/** The Constant RECEPTIONFILE_DESCRIPTION. */
	public static final String RECEPTIONFILE_DESCRIPTION = "Réception";

	/** The Constant LOWER_SYMBOL_1. */
	public static final String LOWER_SYMBOL_1 = ">";

	/** The Constant LOWER_SYMBOL_2. */
	public static final String LOWER_SYMBOL_2 = ">=";

	/** The Constant HIGHER_SYMBOL_1. */
	public static final String HIGHER_SYMBOL_1 = "<";

	/** The Constant HIGHER_SYMBOL_2. */
	public static final String HIGHER_SYMBOL_2 = "<=";

	/** The Constant FRENCH. */
	public static final String FRENCH = "fr";

	/** The Constant ENGLISH. */
	public static final String ENGLISH = "en";

	// Constants for Result Set PDF
	/** PREPRESULT_LABEL_KM. */
	public static final String PREPRESULT_LABEL_KM = "KM après prépa";

	/** PREPRESULT_LABEL_ENGINE. */
	public static final String PREPRESULT_LABEL_ENGINE = "N° Moteur";

	/** PREPRESULT_LABEL_PR. */
	public static final String PREPRESULT_LABEL_PR = "N° de PR";

	/** PREPRESULT_LABEL_CATA. */
	public static final String PREPRESULT_LABEL_CATA = "N° CATA";

	/** PREPRESULT_LABEL_FAP. */
	public static final String PREPRESULT_LABEL_FAP = "N° FAP";

	/** The ENQUARANTAINE_TRUE_DESC. */
	public static final String ENQUARANTAINE_TRUE_DESC = "Mis en quarantaine";

	/** The ENQUARANTAINE_TRUE_DESC. */
	public static final String ENQUARANTAINE_FALSE_DESC = "Retiré de la quarantaine";

	/** Date format. */
	public static final String PDF_DATE_FORMAT = "dd/MM/yyyy HH:mm:SS";

	/** The Constant STATISTICAL_PARAM_SCREEN_ID. */
	public static final String STATISTICAL_PARAM_SCREEN_ID = "STATISTICAL_PARAM";

	/** The Constant STATISTICAL_CALC_RULE_WLTP1. */
	public static final String STATISTICAL_CALC_RULE_WLTP1 = "WLTP-1";

	/** The Constant RESULTSET_PDF_FILENAME. */
	public static final String RESULTSET_PDF_FILENAME = "Fiche_résultats_emissions";

	/** The Constant DATEFORMAT. */
	public static final String DATEFORMAT = "yyMMddHHmm";

	/** The Constant DATEFORMAT. */
	public static final String PDF_FILE = ".pdf";

	/** The Constant HC. */
	public static final String HC = "HC";

	/** The Constant CO. */
	public static final String CO = "CO";

	/** The Constant CO2. */
	public static final String CO2 = "CO2";

	/** The Constant NOX. */
	public static final String NOX = "NOX";

	/** The Constant HC_NOX. */
	public static final String HC_NOX = "HC+NOX";

	/** The Constant ALDEHYDES. */
	public static final String ALDEHYDES = "Aldehydes";

	/** The Constant NMHC. */
	public static final String NMHC = "NMHC";

	/** The Constant FD. */
	public static final String FD = "FD";

	/** The Constant COEF_EVOL. */
	public static final String COEF_EVOL = "Coef Evol";

	/** The Constant COEF_FAP. */
	public static final String COEF_FAP = "Coef FAP";

	/** The Constant PART_MASSE. */
	public static final String PART_MASSE = "PART Masse";

	/** The Constant PART_NOMBRE. */
	public static final String PART_NOMBRE = "PART Nombre";

	/** The Constant CYCLE. */
	public static final String CYCLE = "Cycle";

	/** The Constant PENTE. */
	public static final String PENTE = "Pente";

	/** The Constant MLOG. */
	public static final String MLOG = "MLOG";

	/** The Constant REGIME. */
	public static final String REGIME = "Regime";

	/** The Constant POCKET_BSI. */
	public static final String POCKET_BSI = "Pocket BSI";

	/** The Constant MANCHON. */
	public static final String MANCHON = "Manchon";

	/** The Constant APPRENTISSAGE_. */
	public static final String APPRENTISSAGE_PILOTE = "APPRENTISSAGE PILOTE";

	/** The Constant Préparation. */
	public static final String PREPARATION = "Préparation";

	/** The Constant SOC. */
	public static final String SOC_PREPA = "SOC Prepa";

	/** The Constant SOC. */
	public static final String SOC_AVANT_TEST = "SOC Avant test";

	/** The Constant Opacite. */
	public static final String OPACITE = "Opacite";

	/** The Constant Aspiration. */
	public static final String ASPIRATION = "Aspiration";

	/** The Constant PHASE_1. */
	public static final String PHASE_1 = "PHASE 1";

	/** The Constant PHASE_2. */
	public static final String PHASE_2 = "PHASE 2";

	/** The Constant PHASE_3. */
	public static final String PHASE_3 = "PHASE 3";

	/** The Constant Débit_DLS. */
	public static final String DEBIT_DLS = "Débit DLS";

	/** The Constant Phases_SAC. */
	public static final String PHASES_SAC = "Phases SAC";

	/** The Constant Phases_PARTICULES. */
	public static final String PHASES_PARTICULES = "Phases PARTICULES";

	/** The Constant Catalyseur_ancien. */
	public static final String CATALYSEUR_ANCIEN = "Catalyseur ancien";

	/** The Constant Catalyseur_actuel. */
	public static final String CATALYSEUR_ACTUEL = "Catalyseur actuel";

	/** The Constant Remarques. */
	public static final String REMARQUES = "Remarques";

	/** The Constant Calculateur. */
	public static final String CALCULATEUR = "Calculateur";

	/** The Constant PV_UTAC_POLLU. */
	public static final String PV_UTAC_POLLU = "PV UTAC POLLU";

	/** The Constant PV_UTAC_CONSO. */
	public static final String PV_UTAC_CONSO = "PV UTAC CONSO";

	/** The Constant Etape_Emissions. */
	public static final String ETAPE_EMISSIONS = "Etape Emissions";

	/** The Constant Etape_OBD. */
	public static final String ETAPE_OBD = "Etape OBD";

	/** The Constant Observations1. */
	public static final String OBSERVATIONS1 = "Observations 1";

	/** The Constant Observations2. */
	public static final String OBSERVATIONS2 = "Observations 2";

	/** The Constant EOBD. */
	public static final String EOBD = "EOBD";

	/** The Constant IUPR. */
	public static final String IUPR = "IUPR";

	/** The Constant Num_Soft_OBD_Ancien. */
	public static final String NUM_SOFT_OBD_ANCIEN = "Num_Soft_OBD_Ancien";

	/** The Constant Num_Soft_OBD_Actuel. */
	public static final String NUM_SOFT_OBD_ACTUEL = "Num_Soft_OBD_Actuel";

	/** The Constant Remarques_EOBD. */
	public static final String REMARQUES_EOBD = "Remarques EOBD";

	/** The Constant P_N. */
	public static final String PARTICULES_NOMBRE = "Particules_Nombre";

	/** The Constant P_M. */
	public static final String PARTICULES_MASSE = "Particules_Masse";

	/** The Constant CO2_GPL. */
	public static final String CO2_GPL = "CO2_GPL";

	/** The Constant LIMITES. */
	public static final String LIMITES = "Limites";

	/** The Constant RESPONSE_HEADER_CONTENT_DIPOSITION. */
	public static final String CONTENT_DIPOSITION = "Content-Disposition";

	/** The Constant RESPONSE_HEADER_ATTACHMENT_FILENAME. */
	public static final String ATTACHMENT_FILENAME = "attachment; filename=\"";

	/** The Constant ESSENCE. */
	public static final String ESSENCE = "ESSENCE";

	/** The Constant CONTROL_TECH_ESSENCE. */
	public static final String CONTROL_TECH_ESSENCE = "Contrôle technique ESSENCE";

	/** The Constant DIESEL. */
	public static final String DIESEL = "DIESEL";

	/** The Constant CONTROL_TECH_DIESEL. */
	public static final String CONTROL_TECH_DIESEL = "Contrôle technique DIESEL";

	/** The Constant FAIL. */
	public static final String FAIL = "FAIL";

	/** The Constant EMAIL_DATE_FORMAT. */
	public static final String EMAIL_DATE_FORMAT = "dd/MM/YYYY hh:mm";

	/** The Constant VEHICLE_COUNTERMARK_ARCHIVE. */
	public static final String VEHICLE_COUNTERMARK_ARCHIVE = "vf.vehicle.counterMark";

	/** The Constant VEHICLE_CHASISNUMBER_ARCHIVE. */
	public static final String VEHICLE_CHASISNUMBER_ARCHIVE = "vf.vehicle.chasisNumber";

	/** The Constant VEHICLE_REGISTRATIONNUMBER_ARCHIVE. */
	public static final String VEHICLE_REGISTRATIONNUMBER_ARCHIVE = "vf.vehicle.registrationNumber";

	/**
	 * Instantiates a new constants.
	 */

	private Constants() {
		super();
	}

}
