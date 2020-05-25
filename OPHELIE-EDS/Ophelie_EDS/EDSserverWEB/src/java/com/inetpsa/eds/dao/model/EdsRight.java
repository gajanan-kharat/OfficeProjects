/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inetpsa.eds.dao.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.application.content.eds.activationprofile.ProfilActivationFormBuilder;
import com.inetpsa.eds.application.content.eds.attachments.AttachmentsFormBuilder;
import com.inetpsa.eds.application.content.eds.behavior.consolidate.ComportementConsolideFormBuilder;
import com.inetpsa.eds.application.content.eds.behavior.robust.ComportementRobusteFormBuilder;
import com.inetpsa.eds.application.content.eds.connectivity.association.ConnectivityFormBuilder;
import com.inetpsa.eds.application.content.eds.cse.CSEFormBuilder;
import com.inetpsa.eds.application.content.eds.currentconsumption.consolidate.ConsolidateFormBuilder;
import com.inetpsa.eds.application.content.eds.currentconsumption.driftdriver.DriverDriftsFormBuilder;
import com.inetpsa.eds.application.content.eds.currentconsumption.preliminary.PrimaryFormBuilder;
import com.inetpsa.eds.application.content.eds.currentconsumption.psameasure.PsaMeasureFormBuilder;
import com.inetpsa.eds.application.content.eds.currentconsumption.robust.RobustFormBuilder;
import com.inetpsa.eds.application.content.eds.genericdata.GenericDataFormBuilder;
import com.inetpsa.eds.application.content.eds.missionprofile.ProfilMissionFormBuilder;
import com.inetpsa.eds.application.content.eds.standbyreactivationfailure.DefaillanceVeilleReveilFormBuilder;
import com.inetpsa.eds.application.content.eds.supplyvoltage.consolidate.TensionAlimentationConsolideFormBuilder;
import com.inetpsa.eds.application.content.eds.supplyvoltage.preliminary.TensionAlimentationPreliminaireFormBuilder;
import com.inetpsa.eds.application.content.eds.validation.HighValidationFormBuilder;
import com.inetpsa.eds.application.content.eds.versionhistory.VersionHistoryFormBuilder;

/**
 * This class provide Eds Right
 * 
 * @author Geometric Ltd.
 */
public class EdsRight {
    // PUBLIC
    // application rights
    // General rights
    /**
     * Constant to hold value of APP_GENERAL_ACCESS_ADMIN
     */
    public static final String APP_GENERAL_ACCESS_ADMIN = "app-general-access-admin";
    /**
     * Constant to hold value of APP_GENERAL_ACCESS_PROJECT
     */
    public static final String APP_GENERAL_ACCESS_PROJECT = "app-general-access-project";
    /**
     * Constant to hold value of APP_GENERAL_ACCESS_EDS
     */
    public static final String APP_GENERAL_ACCESS_EDS = "app-general-access-eds";
    /**
     * Constant to hold value of APP_GENERAL_ACCESS_DASHBOARD
     */
    public static final String APP_GENERAL_ACCESS_DASHBOARD = "app-general-access-dashboard";
    /**
     * Constant to hold value of APP_GENERAL_BE_EDS_ADMIN
     */
    public static final String APP_GENERAL_BE_EDS_ADMIN = "app-general-be-eds-admin";
    /**
     * Constant to hold value of APP_GENERAL_BE_EDS_OFFICER
     */
    public static final String APP_GENERAL_BE_EDS_OFFICER = "app-general-be-eds-officer";
    /**
     * Constant to hold value of APP_GENERAL_BE_EDS_MANAGER
     */
    public static final String APP_GENERAL_BE_EDS_MANAGER = "app-general-be-eds-manager";
    /**
     * Constant to hold value of APP_GENERAL_BE_EDS_NOTIFICATION_CD
     */
    public static final String APP_GENERAL_BE_EDS_NOTIFICATION_CD = "app-general-be-eds-notifisation-cd";
    /**
     * Constant to hold value of APP_GENERAL_BE_EDS_AFFECTATION
     */
    public static final String APP_GENERAL_BE_EDS_AFFECTATION = "app-general-be-eds-affectation";

    // Ex_conn_101 - Gestion database choice
    /**
     * Constant to hold value of APP_GENERAL_ACCESS_GS_DATABASE
     */
    public static final String APP_GENERAL_ACCESS_GS_DATABASE = "app-general-access-gs-database";
    // End of Ex_conn_101

    // Project menu Rights
    /**
     * Constant to hold value of APP_PROJECT_MENU_CREATE_EDS
     */
    public static final String APP_PROJECT_MENU_CREATE_EDS = "app-project-menu-new-eds";
    /**
     * Constant to hold value of APP_PROJECT_MENU_SUBSCRIBE_EDS
     */
    public static final String APP_PROJECT_MENU_SUBSCRIBE_EDS = "app-project-menu-subscribe-eds";
    /**
     * Constant to hold value of APP_PROJECT_MENU_ABORT_EDS
     */
    public static final String APP_PROJECT_MENU_ABORT_EDS = "app-project-menu-abort-eds";
    /**
     * Constant to hold value of APP_PROJECT_MENU_RECOVER_EDS
     */
    public static final String APP_PROJECT_MENU_RECOVER_EDS = "app-project-menu-recover-eds";
    /**
     * Constant to hold value of APP_PROJECT_MENU_EXPORT_EDS
     */
    public static final String APP_PROJECT_MENU_EXPORT_EDS = "app-project-menu-export-eds";
    /**
     * Constant to hold value of APP_PROJECT_MENU_EXPORT_SIMULATION
     */
    public static final String APP_PROJECT_MENU_EXPORT_SIMULATION = "app-project-menu-export-simulation";
    /**
     * Constant to hold value of APP_PROJECT_MENU_EXPORT_TAB_VIEW
     */
    public static final String APP_PROJECT_MENU_EXPORT_TAB_VIEW = "app-eds-menu-export-tab-view";
    // EDS menu rights
    /**
     * Constant to hold value of APP_EDS_MENU_SUBSCRIBE_EDS
     */
    public static final String APP_EDS_MENU_SUBSCRIBE_EDS = "app-project-menu-subscribe-eds";
    /**
     * Constant to hold value of APP_EDS_MENU_EXPORT_EDS
     */
    public static final String APP_EDS_MENU_EXPORT_EDS = "app-eds-menu-export-eds";
    /**
     * Constant to hold value of APP_EDS_MENU_EXPORT_TAB_VIEW
     */
    public static final String APP_EDS_MENU_EXPORT_TAB_VIEW = "app-eds-menu-export-tab-view";
    /**
     * Constant to hold value of APP_EDS_MENU_EXPORT_PLM_TAB_VIEW
     */
    public static final String APP_EDS_MENU_EXPORT_PLM_TAB_VIEW = "app-eds-menu-export-plm-tab-view";
    // Dashboard menu rights
    /**
     * Constant to hold value of APP_DASHBOARD_MENU_SUBSCRIBE_EDS
     */
    public static final String APP_DASHBOARD_MENU_SUBSCRIBE_EDS = "app-project-menu-subscribe-eds";
    /**
     * Constant to hold value of APP_DASHBOARD_MENU_ABORT_EDS
     */
    public static final String APP_DASHBOARD_MENU_ABORT_EDS = "app-dashboard-menu-abort-eds";
    /**
     * Constant to hold value of APP_DASHBOARD_MENU_EXPORT_EDS
     */
    public static final String APP_DASHBOARD_MENU_EXPORT_EDS = "app-eds-menu-export-eds";

    // Ex_Gene_31
    /**
     * Constant to hold value of APP_GENERAL_ACCESS_ADMIN_PROJECT
     */
    public static final String APP_GENERAL_ACCESS_ADMIN_PROJECT = "app-general-access-admin-project";
    /**
     * Constant to hold value of APP_GENERAL_ACCESS_ADMIN_ACCESS
     */
    public static final String APP_GENERAL_ACCESS_ADMIN_ACCESS = "app-general-access-admin-access";
    /**
     * Constant to hold value of APP_GENERAL_ACCESS_ADMIN_RIGHTS
     */
    public static final String APP_GENERAL_ACCESS_ADMIN_RIGHTS = "app-general-access-admin-rights";
    /**
     * Constant to hold value of APP_GENERAL_ACCESS_ADMIN_RIGHTS
     */
    public static final String APP_GENERAL_ACCESS_ADMIN_LABEL = "app-general-access-admin-label";
    /**
     * Constant to hold value of APP_GENERAL_ACCESS_ADMIN_USER
     */
    public static final String APP_GENERAL_ACCESS_ADMIN_USER = "app-general-access-admin-user";
    /**
     * Constant to hold value of APP_GENERAL_ACCESS_ADMIN_USER
     */
    public static final String APP_GENERAL_ACCESS_ADMIN_CARD = "app-general-access-admin-card";

    /**
     * Constant to hold value of APP_DASHBOARD_MENU_EXPORT_TAB_VIEW
     */
    public static final String APP_DASHBOARD_MENU_EXPORT_TAB_VIEW = "app-eds-menu-export-tab-view";
    // Eds data rights
    // public static final String APP_EDS_VALIDATE_LOW_LEVEL = "app-eds-validate-low-level";
    // Ex_conn_100
    /**
     * Constant to hold value of APP_EDS_NAME_MODIFICATION
     */
    public static final String APP_EDS_NAME_MODIFICATION = "app-eds-name-modification";
    // End of Ex_conn_100

    /**
     * Constant to hold value of APP_EDS_VALIDATE_LOW_CLOTURE
     */
    public static final String APP_EDS_VALIDATE_LOW_CLOTURE = "app-eds-validate-low-cloture";
    /**
     * Constant to hold value of APP_EDS_VALIDATE_LOW_CONSOLIDE
     */
    public static final String APP_EDS_VALIDATE_LOW_CONSOLIDE = "app-eds-validate-low-consolide";
    /**
     * Constant to hold value of APP_EDS_VALIDATE_PRELIMINAIRE
     */
    public static final String APP_EDS_VALIDATE_PRELIMINAIRE = "app-eds-validate-preliminaire";
    /**
     * Constant to hold value of APP_EDS_VALIDATE_ROBUSTE
     */
    public static final String APP_EDS_VALIDATE_ROBUSTE = "app-eds-validate-robuste";
    /**
     * Constant to hold value of APP_EDS_VALIDATE_HIGH_LEVEL_3
     */
    public static final String APP_EDS_VALIDATE_HIGH_LEVEL_3 = "app-eds-validate-high-level-3";
    /**
     * Constant to hold value of APP_EDS_VALIDATE_HIGH_LEVEL_4
     */
    public static final String APP_EDS_VALIDATE_HIGH_LEVEL_4 = "app-eds-validate-high-level-4";
    /**
     * Constant to hold value of APP_EDS_VALIDATE_HIGH_LEVEL_5
     */
    public static final String APP_EDS_VALIDATE_HIGH_LEVEL_5 = "app-eds-validate-high-level-5";
    /**
     * Constant to hold value of APP_EDS_VALIDATE_DRIFT
     */
    public static final String APP_EDS_VALIDATE_DRIFT = "app-eds-validate-drift";
    /**
     * Constant to hold value of APP_EDS_SUBSCRIBE_EDS
     */
    public static final String APP_EDS_SUBSCRIBE_EDS = "app-project-menu-subscribe-eds";
    /**
     * Constant to hold value of APP_EDS_FREEZE_EDS
     */
    public static final String APP_EDS_FREEZE_EDS = "app-eds-freeze-eds";
    /**
     * Constant to hold value of APP_EDS_RECONDUCT_EDS_WITHOUT_MODIF
     */
    public static final String APP_EDS_RECONDUCT_EDS_WITHOUT_MODIF = "app-eds-reconduct-eds-without-modif";
    /**
     * Constant to hold value of APP_EDS_RECONDUCT_EDS_WITH_MODIF
     */
    public static final String APP_EDS_RECONDUCT_EDS_WITH_MODIF = "app-eds-reconduct-eds-with-modif";
    /**
     * Constant to hold value of APP_EDS_RECONSULT_EDS
     */
    public static final String APP_EDS_RECONSULT_EDS = "app-eds-reconsult-eds";
    /**
     * Constant to hold value of APP_EDS_REMOVE_FOLLOWER_PROJECT
     */
    public static final String APP_EDS_REMOVE_FOLLOWER_PROJECT = "app-eds-remove-follower-project";
    /**
     * Constant to hold value of APP_EDS_MODIFY_SETTER_PROJECT
     */
    public static final String APP_EDS_MODIFY_SETTER_PROJECT = "app-eds-modify-setter-project";
    /**
     * Constant to hold value of APP_EDS_VALIDATE_SUPPLIER_DATA
     */
    public static final String APP_EDS_VALIDATE_SUPPLIER_DATA = "app-eds-validate-supplier-data";
    /**
     * Constant to hold value of APP_EDS_SEE_PRIMARY_ORGANS
     */
    public static final String APP_EDS_SEE_PRIMARY_ORGANS = "app-eds-see-primary-organs";
    /**
     * Constant to hold value of APP_EDS_SEE_SECONDARY_ORGANS
     */
    public static final String APP_EDS_SEE_SECONDARY_ORGANS = "app-eds-see-secondary-organs";
    /**
     * Constant to hold value of APP_EDS_TYPE_ORGANS
     */
    public static final String APP_EDS_TYPE_ORGANS = "app-eds-see-type-organe";
    // Form rights
    /**
     * Constant to hold value of FORM_READ_GENERIC_DATA
     */
    public static final String FORM_READ_GENERIC_DATA = "form-read-generic-data";
    /**
     * Constant to hold value of FORM_WRITE_GENERIC_DATA
     */
    public static final String FORM_WRITE_GENERIC_DATA = "form-write-generic-data";
    /**
     * Constant to hold value of FORM_READ_PRELIM_CURRENT_CUNSUMPTION
     */
    public static final String FORM_READ_PRELIM_CURRENT_CUNSUMPTION = "form-read-preliminary-current-consumption";
    /**
     * Constant to hold value of FORM_WRITE_PRELIM_CURRENT_CUNSUMPTION
     */
    public static final String FORM_WRITE_PRELIM_CURRENT_CUNSUMPTION = "form-write-preliminary-current-consumption";
    /**
     * Constant to hold value of FORM_READ_ROBUST_CURRENT_CUNSUMPTION
     */
    public static final String FORM_READ_ROBUST_CURRENT_CUNSUMPTION = "form-read-robust-current-consumption";
    /**
     * Constant to hold value of FORM_WRITE_ROBUST_CURRENT_CUNSUMPTION
     */
    public static final String FORM_WRITE_ROBUST_CURRENT_CUNSUMPTION = "form-write-robust-current-consumption";
    /**
     * Constant to hold value of FORM_READ_CONSOLIDATED_CURRENT_CUNSUMPTION
     */
    public static final String FORM_READ_CONSOLIDATED_CURRENT_CUNSUMPTION = "form-read-consolidated-current-consumption";
    /**
     * Constant to hold value of FORM_WRITE_CONSOLIDATED_CURRENT_CUNSUMPTION
     */
    public static final String FORM_WRITE_CONSOLIDATED_CURRENT_CUNSUMPTION = "form-write-consolidated-current-consumption";
    /**
     * Constant to hold value of FORM_READ_MEASURED_CURRENT_CUNSUMPTION
     */
    public static final String FORM_READ_MEASURED_CURRENT_CUNSUMPTION = "form-read-measured-current-consumption";
    /**
     * Constant to hold value of FORM_WRITE_MEASURED_CURRENT_CUNSUMPTION
     */
    public static final String FORM_WRITE_MEASURED_CURRENT_CUNSUMPTION = "form-write-measured-current-consumption";
    /**
     * Constant to hold value of FORM_READ_DRIFT_DRIVER_CURRENT_CUNSUMPTION
     */
    public static final String FORM_READ_DRIFT_DRIVER_CURRENT_CUNSUMPTION = "form-read-drift-driver-current-consumption";
    /**
     * Constant to hold value of FORM_WRITE_DRIFT_DRIVER_CURRENT_CUNSUMPTION
     */
    public static final String FORM_WRITE_DRIFT_DRIVER_CURRENT_CUNSUMPTION = "form-write-drift-driver-current-consumption";
    /**
     * Constant to hold value of FORM_READ_PRELIM_SUPPLY_VOLTAGE
     */
    public static final String FORM_READ_PRELIM_SUPPLY_VOLTAGE = "form-read-preliminary-supply-voltage";
    /**
     * Constant to hold value of FORM_WRITE_PRELIM_SUPPLY_VOLTAGE
     */
    public static final String FORM_WRITE_PRELIM_SUPPLY_VOLTAGE = "form-write-preliminary-supply-voltage";
    /**
     * Constant to hold value of FORM_READ_CONSOLIDATED_SUPPLY_VOLTAGE
     */
    public static final String FORM_READ_CONSOLIDATED_SUPPLY_VOLTAGE = "form-read-consolidated-supply-voltage";
    /**
     * Constant to hold value of FORM_WRITE_CONSOLIDATED_SUPPLY_VOLTAGE
     */
    public static final String FORM_WRITE_CONSOLIDATED_SUPPLY_VOLTAGE = "form-write-consolidated-supply-voltage";
    /**
     * Constant to hold value of FORM_READ_STANDBY_WAKEUP_FAILURE
     */
    public static final String FORM_READ_STANDBY_WAKEUP_FAILURE = "form-read-standby-wakeup-failure";
    /**
     * Constant to hold value of FORM_WRITE_STANDBY_WAKEUP_FAILURE
     */
    public static final String FORM_WRITE_STANDBY_WAKEUP_FAILURE = "form-write-standby-wakeup-failure";
    /**
     * Constant to hold value of FORM_READ_ROBUST_BEHAVIOR
     */
    public static final String FORM_READ_ROBUST_BEHAVIOR = "form-read-robust-behavior";
    /**
     * Constant to hold value of FORM_WRITE_ROBUST_BEHAVIOR
     */
    public static final String FORM_WRITE_ROBUST_BEHAVIOR = "form-write-robust-behavior";
    /**
     * Constant to hold value of FORM_READ_CONSOLIDATED_BEHAVIOR
     */
    public static final String FORM_READ_CONSOLIDATED_BEHAVIOR = "form-read-consolidated-behavior";
    /**
     * Constant to hold value of FORM_WRITE_CONSOLIDATED_BEHAVIOR
     */
    public static final String FORM_WRITE_CONSOLIDATED_BEHAVIOR = "form-write-consolidated-behavior";
    /**
     * Constant to hold value of FORM_READ_MISSION_PROFILE
     */
    public static final String FORM_READ_MISSION_PROFILE = "form-read-mission-profile";
    /**
     * Constant to hold value of FORM_WRITE_MISSION_PROFILE
     */
    public static final String FORM_WRITE_MISSION_PROFILE = "form-write-mission-profile";
    /**
     * Constant to hold value of FORM_READ_ACTIVATION_PROFILE
     */
    public static final String FORM_READ_ACTIVATION_PROFILE = "form-read-activation-profile";
    /**
     * Constant to hold value of FORM_WRITE_ACTIVATION_PROFILE
     */
    public static final String FORM_WRITE_ACTIVATION_PROFILE = "form-write-activation-profile";
    /**
     * Constant to hold value of FORM_READ_HIGH_VALIDATION
     */
    public static final String FORM_READ_HIGH_VALIDATION = "form-read-high-validation";
    /**
     * Constant to hold value of FORM_WRITE_HIGH_VALIDATION
     */
    public static final String FORM_WRITE_HIGH_VALIDATION = "form-write-high-validation";
    /**
     * Constant to hold value of FORM_READ_CSE
     */
    public static final String FORM_READ_CSE = "form-read-cse";
    /**
     * Constant to hold value of FORM_WRITE_CSE
     */
    public static final String FORM_WRITE_CSE = "form-write-cse";
    /**
     * Constant to hold value of FORM_READ_ATTACHMENTS
     */
    public static final String FORM_READ_ATTACHMENTS = "form-read-attachments";
    /**
     * Constant to hold value of FORM_WRITE_ATTACHMENTS
     */
    public static final String FORM_WRITE_ATTACHMENTS = "form-write-attachments";
    /**
     * Constant to hold value of FORM_READ_VERSION_HISTORY
     */
    public static final String FORM_READ_VERSION_HISTORY = "form-read-version-history";

    /**
     * Constant to hold value of FORM_READ_ASSOCIATION_FC
     */
    public static final String FORM_READ_ASSOCIATION_FC = "form-read-association-fc";

    /**
     * Constant to hold value of FORM_WRITE_ASSOCIATION_FC
     */
    public static final String FORM_WRITE_ASSOCIATION_FC = "form-write-association-fc";

    /**
     * Constant to hold value of APP_EDS_RESET_EDS
     */
    public static final String APP_EDS_RESET_EDS = "app-eds-reset-eds";

    /**
     * Constant to hold value of APP_EDS_CHECK_REPORT
     */
    public static final String APP_EDS_CHECK_REPORT = "app-eds-check-report";

    /**
     * Function to get list of Application general rights
     * 
     * @return List of Application general rights
     */
    public static List<String> getApplicationGeneralRights() {
        return APP_GENERAL_RIGHTS;
    }

    /**
     * Function to get list of Application Project menu rights
     * 
     * @return Application project menu rights
     */
    public static List<String> getApplicationProjectMenuRights() {
        return APP_PROJECT_MENU_RIGHTS;
    }

    /**
     * Function to get list of Application Eds Menu rights
     * 
     * @return Application Eds Menu rights
     */
    public static List<String> getApplicationEdsMenuRights() {
        return APP_EDS_MENU_RIGHTS;
    }

    /**
     * Function to get Application Dashboard Menu rights
     * 
     * @return Application Dashboard Menu rights
     */
    public static List<String> getApplicationDashboardMenuRights() {
        return APP_DASHBOARD_MENU_RIGHTS;
    }

    /**
     * Function to get Application Eds rights
     * 
     * @return Application Eds rights
     */
    public static List<String> getApplicationEdsRights() {
        return APP_EDS_RIGHTS;
    }

    /**
     * Function to check if user has sufficient rights
     * 
     * @param user Eds User
     * @param rightId Right ID
     * @return check if User has sufficient rights
     */
    public static boolean hasSufficientRights(EdsUser user, String rightId) {
        return user.getEdsRole().getAllAppRights().contains(rightId) || user.getEdsRole().getAllFormRights().contains(rightId);
    }

    /**
     * Function Returns Form read rights
     * 
     * @return Map of Form builder and String
     */
    public static Map<I_FormBuilder, String> getFormReadRights() {
        return FORM_READ_RIGHTS;
    }

    /**
     * Function Returns Form write rights
     * 
     * @return Map of Form builder and String
     */
    public static Map<I_FormBuilder, String> getFormWriteRights() {
        return FORM_WRITE_RIGHTS;
    }

    // PROTECTED
    // PRIVATE
    /**
     * Constant to hold List for APP_GENERAL_RIGHTS
     */
    private static final List<String> APP_GENERAL_RIGHTS = new ArrayList<String>();
    /**
     * Constant to hold List for APP_PROJECT_MENU_RIGHTS
     */
    private static final List<String> APP_PROJECT_MENU_RIGHTS = new ArrayList<String>();
    /**
     * Constant to hold List for APP_EDS_MENU_RIGHTS
     */
    private static final List<String> APP_EDS_MENU_RIGHTS = new ArrayList<String>();
    /**
     * Constant to hold List for APP_DASHBOARD_MENU_RIGHTS
     */
    private static final List<String> APP_DASHBOARD_MENU_RIGHTS = new ArrayList<String>();
    /**
     * Constant to hold List for APP_EDS_RIGHTS
     */
    private static final List<String> APP_EDS_RIGHTS = new ArrayList<String>();
    /**
     * Constant to hold LinkedHashMap for FORM_READ_RIGHTS
     */
    private static final LinkedHashMap<I_FormBuilder, String> FORM_READ_RIGHTS = new LinkedHashMap<I_FormBuilder, String>();
    /**
     * Constant to hold LinkedHashMap for FORM_WRITE_RIGHTS
     */
    private static final LinkedHashMap<I_FormBuilder, String> FORM_WRITE_RIGHTS = new LinkedHashMap<I_FormBuilder, String>();

    static {
        // General rights
        APP_GENERAL_RIGHTS.add(APP_GENERAL_ACCESS_ADMIN);
        APP_GENERAL_RIGHTS.add(APP_GENERAL_ACCESS_PROJECT);
        APP_GENERAL_RIGHTS.add(APP_GENERAL_ACCESS_EDS);
        APP_GENERAL_RIGHTS.add(APP_GENERAL_ACCESS_DASHBOARD);
        APP_GENERAL_RIGHTS.add(APP_GENERAL_BE_EDS_ADMIN);
        APP_GENERAL_RIGHTS.add(APP_GENERAL_BE_EDS_OFFICER);
        APP_GENERAL_RIGHTS.add(APP_GENERAL_BE_EDS_MANAGER);
        APP_GENERAL_RIGHTS.add(APP_GENERAL_BE_EDS_NOTIFICATION_CD);
        APP_GENERAL_RIGHTS.add(APP_GENERAL_BE_EDS_AFFECTATION);
        APP_GENERAL_RIGHTS.add(APP_DASHBOARD_MENU_ABORT_EDS);
        APP_GENERAL_RIGHTS.add(APP_DASHBOARD_MENU_EXPORT_EDS);
        APP_GENERAL_RIGHTS.add(APP_GENERAL_ACCESS_GS_DATABASE); // Ex_conn_101 - GS bilan import

        APP_GENERAL_RIGHTS.add(APP_GENERAL_ACCESS_ADMIN_PROJECT); // "Gestion des projets"
        APP_GENERAL_RIGHTS.add(APP_GENERAL_ACCESS_ADMIN_ACCESS); // "Gestion des accès"
        APP_GENERAL_RIGHTS.add(APP_GENERAL_ACCESS_ADMIN_RIGHTS); // "Gestion des droits"
        APP_GENERAL_RIGHTS.add(APP_GENERAL_ACCESS_ADMIN_LABEL); // "Gestion des libellés"
        APP_GENERAL_RIGHTS.add(APP_GENERAL_ACCESS_ADMIN_USER); // "Gestion des utilisateur"
        APP_GENERAL_RIGHTS.add(APP_GENERAL_ACCESS_ADMIN_CARD); // "Gestion des fiches"

        // Project menu rights
        APP_PROJECT_MENU_RIGHTS.add(APP_PROJECT_MENU_CREATE_EDS);
        APP_PROJECT_MENU_RIGHTS.add(APP_PROJECT_MENU_SUBSCRIBE_EDS);
        // APP_PROJECT_MENU_RIGHTS.add( APP_PROJECT_MENU_ABORT_EDS );
        APP_PROJECT_MENU_RIGHTS.add(APP_PROJECT_MENU_RECOVER_EDS);
        APP_PROJECT_MENU_RIGHTS.add(APP_PROJECT_MENU_EXPORT_EDS);
        APP_PROJECT_MENU_RIGHTS.add(APP_PROJECT_MENU_EXPORT_SIMULATION);
        APP_PROJECT_MENU_RIGHTS.add(APP_PROJECT_MENU_EXPORT_TAB_VIEW);

        // Eds Menu rights
        // APP_EDS_MENU_RIGHTS.add( APP_EDS_MENU_SUBSCRIBE_EDS );
        // APP_EDS_MENU_RIGHTS.add( APP_EDS_MENU_EXPORT_EDS );
        APP_EDS_MENU_RIGHTS.add(APP_EDS_MENU_EXPORT_TAB_VIEW);
        APP_EDS_MENU_RIGHTS.add(APP_EDS_MENU_EXPORT_PLM_TAB_VIEW);

        // Dashboard menu rights
        // APP_DASHBOARD_MENU_RIGHTS.add( APP_DASHBOARD_MENU_SUBSCRIBE_EDS );
        APP_DASHBOARD_MENU_RIGHTS.add(APP_DASHBOARD_MENU_EXPORT_TAB_VIEW);

        // Eds data right
        APP_EDS_RIGHTS.add(APP_EDS_NAME_MODIFICATION); // Ex_conn_100 - EDS name modification
        APP_EDS_RIGHTS.add(APP_EDS_VALIDATE_PRELIMINAIRE);
        APP_EDS_RIGHTS.add(APP_EDS_VALIDATE_ROBUSTE);
        APP_EDS_RIGHTS.add(APP_EDS_VALIDATE_LOW_CONSOLIDE);
        APP_EDS_RIGHTS.add(APP_EDS_VALIDATE_LOW_CLOTURE);
        APP_EDS_RIGHTS.add(APP_EDS_VALIDATE_HIGH_LEVEL_3);
        APP_EDS_RIGHTS.add(APP_EDS_VALIDATE_HIGH_LEVEL_4);
        APP_EDS_RIGHTS.add(APP_EDS_VALIDATE_HIGH_LEVEL_5);
        APP_EDS_RIGHTS.add(APP_EDS_FREEZE_EDS);
        APP_EDS_RIGHTS.add(APP_EDS_SUBSCRIBE_EDS);
        APP_EDS_RIGHTS.add(APP_EDS_VALIDATE_DRIFT);
        APP_EDS_RIGHTS.add(APP_EDS_RECONDUCT_EDS_WITHOUT_MODIF);
        APP_EDS_RIGHTS.add(APP_EDS_RECONDUCT_EDS_WITH_MODIF);
        APP_EDS_RIGHTS.add(APP_EDS_RECONSULT_EDS);
        APP_EDS_RIGHTS.add(APP_EDS_REMOVE_FOLLOWER_PROJECT);
        APP_EDS_RIGHTS.add(APP_EDS_MODIFY_SETTER_PROJECT);
        APP_EDS_RIGHTS.add(APP_EDS_VALIDATE_SUPPLIER_DATA);
        APP_EDS_RIGHTS.add(APP_EDS_SEE_PRIMARY_ORGANS);
        APP_EDS_RIGHTS.add(APP_EDS_SEE_SECONDARY_ORGANS);
        APP_EDS_RIGHTS.add(APP_EDS_TYPE_ORGANS);
        APP_EDS_RIGHTS.add(APP_EDS_RESET_EDS);
        APP_EDS_RIGHTS.add(APP_EDS_CHECK_REPORT);

        GenericDataFormBuilder genericDataFormBuilder = new GenericDataFormBuilder();
        PrimaryFormBuilder primaryFormBuilder = new PrimaryFormBuilder();
        RobustFormBuilder robustFormBuilder = new RobustFormBuilder();
        ConsolidateFormBuilder consolidateFormBuilder = new ConsolidateFormBuilder();
        PsaMeasureFormBuilder psaMeasureFormBuilder = new PsaMeasureFormBuilder();
        DriverDriftsFormBuilder driverDriftsFormBuilder = new DriverDriftsFormBuilder();
        TensionAlimentationPreliminaireFormBuilder tensionAlimentationPreliminaireFormBuilder = new TensionAlimentationPreliminaireFormBuilder();
        TensionAlimentationConsolideFormBuilder tensionAlimentationConsolideFormBuilder = new TensionAlimentationConsolideFormBuilder();
        DefaillanceVeilleReveilFormBuilder defaillanceVeilleReveilFormBuilder = new DefaillanceVeilleReveilFormBuilder();
        ComportementConsolideFormBuilder comportementConsolideFormBuilder = new ComportementConsolideFormBuilder();
        ComportementRobusteFormBuilder comportementRobusteFormBuilder = new ComportementRobusteFormBuilder();
        ProfilMissionFormBuilder profilMissionFormBuilder = new ProfilMissionFormBuilder();
        ProfilActivationFormBuilder profilActivationFormBuilder = new ProfilActivationFormBuilder();
        HighValidationFormBuilder highValidationFormBuilder = new HighValidationFormBuilder();
        CSEFormBuilder cseFormBuilder = new CSEFormBuilder();
        AttachmentsFormBuilder attachmentsFormBuilder = new AttachmentsFormBuilder();
        VersionHistoryFormBuilder versionHistoryFormBuilder = new VersionHistoryFormBuilder();
        ConnectivityFormBuilder connectivityFormBuilder = new ConnectivityFormBuilder();

        // Reading rights form
        FORM_READ_RIGHTS.put(genericDataFormBuilder, FORM_READ_GENERIC_DATA);
        FORM_READ_RIGHTS.put(primaryFormBuilder, FORM_READ_PRELIM_CURRENT_CUNSUMPTION);
        FORM_READ_RIGHTS.put(robustFormBuilder, FORM_READ_ROBUST_CURRENT_CUNSUMPTION);
        FORM_READ_RIGHTS.put(consolidateFormBuilder, FORM_READ_CONSOLIDATED_CURRENT_CUNSUMPTION);
        FORM_READ_RIGHTS.put(psaMeasureFormBuilder, FORM_READ_MEASURED_CURRENT_CUNSUMPTION);
        FORM_READ_RIGHTS.put(driverDriftsFormBuilder, FORM_READ_DRIFT_DRIVER_CURRENT_CUNSUMPTION);
        FORM_READ_RIGHTS.put(tensionAlimentationPreliminaireFormBuilder, FORM_READ_PRELIM_SUPPLY_VOLTAGE);
        FORM_READ_RIGHTS.put(tensionAlimentationConsolideFormBuilder, FORM_READ_CONSOLIDATED_SUPPLY_VOLTAGE);
        FORM_READ_RIGHTS.put(defaillanceVeilleReveilFormBuilder, FORM_READ_STANDBY_WAKEUP_FAILURE);
        FORM_READ_RIGHTS.put(comportementConsolideFormBuilder, FORM_READ_CONSOLIDATED_BEHAVIOR);
        FORM_READ_RIGHTS.put(comportementRobusteFormBuilder, FORM_READ_ROBUST_BEHAVIOR);
        FORM_READ_RIGHTS.put(profilMissionFormBuilder, FORM_READ_MISSION_PROFILE);
        FORM_READ_RIGHTS.put(profilActivationFormBuilder, FORM_READ_ACTIVATION_PROFILE);
        FORM_READ_RIGHTS.put(highValidationFormBuilder, FORM_READ_HIGH_VALIDATION);
        FORM_READ_RIGHTS.put(cseFormBuilder, FORM_READ_CSE);
        FORM_READ_RIGHTS.put(attachmentsFormBuilder, FORM_READ_ATTACHMENTS);
        FORM_READ_RIGHTS.put(versionHistoryFormBuilder, FORM_READ_VERSION_HISTORY);
        FORM_READ_RIGHTS.put(connectivityFormBuilder, FORM_READ_ASSOCIATION_FC);

        // Writing rights form
        FORM_WRITE_RIGHTS.put(genericDataFormBuilder, FORM_WRITE_GENERIC_DATA);
        FORM_WRITE_RIGHTS.put(primaryFormBuilder, FORM_WRITE_PRELIM_CURRENT_CUNSUMPTION);
        FORM_WRITE_RIGHTS.put(robustFormBuilder, FORM_WRITE_ROBUST_CURRENT_CUNSUMPTION);
        FORM_WRITE_RIGHTS.put(consolidateFormBuilder, FORM_WRITE_CONSOLIDATED_CURRENT_CUNSUMPTION);
        FORM_WRITE_RIGHTS.put(psaMeasureFormBuilder, FORM_WRITE_MEASURED_CURRENT_CUNSUMPTION);
        FORM_WRITE_RIGHTS.put(driverDriftsFormBuilder, FORM_WRITE_DRIFT_DRIVER_CURRENT_CUNSUMPTION);
        FORM_WRITE_RIGHTS.put(tensionAlimentationPreliminaireFormBuilder, FORM_WRITE_PRELIM_SUPPLY_VOLTAGE);
        FORM_WRITE_RIGHTS.put(tensionAlimentationConsolideFormBuilder, FORM_WRITE_CONSOLIDATED_SUPPLY_VOLTAGE);
        FORM_WRITE_RIGHTS.put(defaillanceVeilleReveilFormBuilder, FORM_WRITE_STANDBY_WAKEUP_FAILURE);
        FORM_WRITE_RIGHTS.put(comportementConsolideFormBuilder, FORM_WRITE_CONSOLIDATED_BEHAVIOR);
        FORM_WRITE_RIGHTS.put(comportementRobusteFormBuilder, FORM_WRITE_ROBUST_BEHAVIOR);
        FORM_WRITE_RIGHTS.put(profilMissionFormBuilder, FORM_WRITE_MISSION_PROFILE);
        FORM_WRITE_RIGHTS.put(profilActivationFormBuilder, FORM_WRITE_ACTIVATION_PROFILE);
        FORM_WRITE_RIGHTS.put(highValidationFormBuilder, FORM_WRITE_HIGH_VALIDATION);
        FORM_WRITE_RIGHTS.put(cseFormBuilder, FORM_WRITE_CSE);
        FORM_WRITE_RIGHTS.put(attachmentsFormBuilder, FORM_WRITE_ATTACHMENTS);
        FORM_WRITE_RIGHTS.put(connectivityFormBuilder, FORM_WRITE_ASSOCIATION_FC);
    }

    /**
     * Default constructor
     */
    private EdsRight() {
        init();
    }

    /**
     * Initialize EdsRight
     */
    private void init() {
    }
}
