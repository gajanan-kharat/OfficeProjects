/*
 * Creation : Oct 24, 2016
 */
package com.inetpsa.poc00.commonapp;

/**
 * The Class ConstantsApp.
 */
public class ConstantsApp {

    /** The Constant LDAP ROLE PREFIX. */
    public static final String LDAP_ROLE_PREFIX = "POC";

    /** The Constant VEHICLE_COUNTERMARK. */
    public static final String VEHICLE_COUNTERMARK = "counterMark";

    /** The Constant VEHICLE_CHASISNUMBER. */
    public static final String VEHICLE_CHASISNUMBER = "chasisNumber";

    /** The Constant VEHICLE_REGISTRATIONNUMBER. */
    public static final String VEHICLE_REGISTRATIONNUMBER = "registrationNumber";

    /** The Constant VEHICLEFILESTATUS_LABEL. */
    public static final String VEHICLEFILESTATUS_LABEL = "Received";

    /** The Constant VEHICLEFILE_HISTORY_DESCRIPTION. */
    public static final String VEHICLEFILE_HISTORY_DESCRIPTION = "Réceptionné";

    /** The Constant VEHICLEFILE_SCREEN_ID. */
    public static final String VEHICLEFILE_SCREEN_ID = "VEHICLE_FILE";

    /** The Constant EMS_DEPENDENT_TDL_SCREEN. */
    public static final String EMS_DEPENDENT_TDL_SCREEN = "EMS_DEPENDENT_TDL";

    /** The Constant EMS_DEPENDENT_TCL_SCREEN. */
    public static final String EMS_DEPENDENT_TCL_SCREEN = "EMS_DEPENDENT_TCL";

    /** The Constant EMS_DEPENDENT_FCL_SCREEN. */
    public static final String EMS_DEPENDENT_FCL_SCREEN = "EMS_DEPENDENT_FCL";

    /** The Constant EMS_DEPENDENT_PGL_SCREEN. */
    public static final String EMS_DEPENDENT_PGL_SCREEN = "EMS_DEPENDENT_PGL";

    /** The Constant MVEG_1. */
    public static final String MVEG_1 = "MVEG-1";

    /** The Constant MVEG_2. */
    public static final String MVEG_2 = "MVEG-2";

    /** The Constant WLTP_1. */
    public static final String WLTP_1 = "WLTP-1";

    /** The Constant JAPAN_1. */
    public static final String JAPAN_1 = "JAPAN-1";

    /** The Constant DECISION_MD. */
    public static final String DECISION_MD = "MD";

    /** The Constant DECISION_A. */
    public static final String DECISION_A = "A";

    /** The Constant DECISION_R. */
    public static final String DECISION_R = "R";

    /** The Constant DECISION_I. */
    public static final String DECISION_I = "I";

    /** The Constant RULE. */
    public static final String RULE = "RULE";

    /** The Constant RULE_NAME. */
    public static final String RULE_NAME = "name";

    /** The Constant MAX_NO_OF_ELEMENTS. */
    public static final String MAX_NO_OF_ELEMENTS = "maxElements";

    /** The Constant VALUE. */
    public static final String VALUE = "VALUE";

    /** The Constant VALUE_AN. */
    public static final String VALUE_AN = "VALUE_AN";

    /** The Constant VALUE_BN. */
    public static final String VALUE_BN = "VALUE_BN";

    /** The Constant VALUE_IN. */
    public static final String VALUE_IN = "VALUE_IN";

    /** The Constant NO_OF_ELEMENTS. */
    public static final String NO_OF_ELEMENTS = "ELEMENT_COUNT";

    /** The Constant MAX_ELEMENTS_FOR_MVEG1. */
    public static final Integer MAX_ELEMENTS_FOR_MVEG1 = 32;

    /** The Constant DECISION_I1. */
    public static final String DECISION_I1 = "I1";

    /** The Constant DECISION_AN. */
    public static final String DECISION_AN = "AN";

    /** The Constant DECISION_RN. */
    public static final String DECISION_RN = "RN";

    /** The Constant DECISION_IN. */
    public static final String DECISION_IN = "IN";

    /** The Constant DECISION_I1N. */
    public static final String DECISION_I1N = "I1N";

    /** The Constant DECISION_I2N. */
    public static final String DECISION_I2N = "I2N";

    /** The Constant DECISION_I2. */
    public static final String DECISION_I2 = "I2";

    /** The Constant CO2. */
    public static final String CO2 = "CO2";

    /** The Constant ERROR_NO_STATISTICAL_PARAMS. */
    public static final String ERROR_NO_STATISTICAL_PARAMS = "ERR01";

    /** The Constant ERROR_NO_MAX_LIMIT. */
    public static final String ERROR_NO_MAX_LIMIT = "ERR02";

    /** The Constant ERROR_NO_STATISTICAL_RULE. */
    public static final String ERROR_NO_STATISTICAL_RULE = "ERR03";

    /** The Constant STATISTICAL_RULES_XML. */
    public static final String STATISTICAL_RULES_XML = "statisticalcalculations/StatisicalRulesSchema.xml";

    /** The Constant DRAFT. */
    public static final String DRAFT = "DRAFT";

    /** The Constant VALID. */
    public static final String VALID = "VALID";

    /** The Constant COMMONGENOME_SCREEN_ID. */
    public static final String COMMONGENOME_SCREEN_ID = "COMMON_GENOME";

    /** The Constant CLASS. */
    public static final String CLASS = "class";

    /** The Constant ENTITY_ID. */
    public static final String ENTITY_ID = "entityID";

    /** The Constant HISTORY_MODIFICATION_TYPE_CREATE. */
    public static final String HISTORY_MODIFICATION_TYPE_CREATE = "ajout de";

    /** The Constant HISTORY_MODIFICATION_TYPE_DELETE. */
    public static final String HISTORY_MODIFICATION_TYPE_DELETE = "suppression de";

    /** The Constant HISTORY_MODIFICATION_TYPE_MODIFIED. */
    public static final String HISTORY_MODIFICATION_TYPE_MODIFIED = "modification de";

    /** The Constant TVV_DEPENDENT_TDL_SCREEN. */
    public static final String TVV_DEPENDENT_TDL_SCREEN = "TVV_DEPENDENT_TDL";

    /** The Constant EMSTABLE. */
    public static final String EMSTABLE = "COPQTAES";

    /** The Constant MAX_EMS_VERSION_QUERY. */
    public static final String MAX_EMS_VERSION_QUERY = "SELECT MAX(CAST(T.VERSION AS DECIMAL(10,1))) FROM " + EMSTABLE + " T WHERE T.LABEL = ?";

    /** The Constant TVV_DEPENDENT_TCL_SCREEN. */
    public static final String TVV_DEPENDENT_TCL_SCREEN = "TVV_DEPENDENT_TCL";

    /** The Constant TVV_SCREEN_ID. */
    public static final String TVV_SCREEN_ID = "TVV";

    /** The Constant SPECIFICCOP_SCREEN_ID. */
    public static final String SPECIFICCOP_SCREEN_ID = "SPECIFIC_COP";
    /** The Constant Success string. */
    public static final String SUCCESS = "Success";
    /** The Constant COASTDOWN_SCREEN_ID. */
    public static final String COASTDOWN_SCREEN_ID = "COAST_DOWN";
    /** The Constant Failure string. */
    public static final String FAILURE = "Failure";

    /** The Constant MAX_ELEMENTS_MVEG. */
    public static final Integer MAX_ELEMENTS_MVEG = 32;

    /** The Constant MAX_ELEMENTS_WLTP. */
    public static final Integer MAX_ELEMENTS_WLTP = 16;

    /** The ENQUARANTAINE_TRUE_DESC. */
    public static final String ENQUARANTAINE_TRUE_DESC = "Mis en quarantaine";

    /** The ENQUARANTAINE_TRUE_DESC. */
    public static final String ENQUARANTAINE_FALSE_DESC = "Retiré de la quarantaine";

    /** The INTERGER_VAL_THREE. */
    public static final int INTERGER_VAL_THREE = 3;

    /** The Constant RECEPTIONFILE_DESCRIPTION. */
    public static final String RECEPTIONFILE_DESCRIPTION = "Réception";

    /** The Constant SMTP_HOST. */
    public static final String SMTP_HOST = "mail.smtp.host";

    /** The Constant SMTP_PORT. */
    public static final String SMTP_PORT = "mail.smtp.port";

    /** The Constant VEHICLE_COUNTERMARK_ARCHIVE. */
    public static final String VEHICLE_COUNTERMARK_ARCHIVE = "vf.vehicle.counterMark";

    /** The Constant VEHICLE_CHASISNUMBER_ARCHIVE. */
    public static final String VEHICLE_CHASISNUMBER_ARCHIVE = "vf.vehicle.chasisNumber";

    /** The Constant VEHICLE_REGISTRATIONNUMBER_ARCHIVE. */
    public static final String VEHICLE_REGISTRATIONNUMBER_ARCHIVE = "vf.vehicle.registrationNumber";

    /** The Constant VEHICLE_TYPEOFTEST_ARCHIVE. */
    public static final String VEHICLE_TYPEOFTEST_ARCHIVE = "vf.typeOfTest.entityId";

    /** The Constant VEHICLEFILE_STAUS_ARCHIVED. */
    public static final String VEHICLEFILE_STAUS_ARCHIVED = "Archived";

    /**
     * Instantiates a new constants app.
     */
    private ConstantsApp() {
        super();
    }

}
