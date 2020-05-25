/* FORMULAIRES DROP TABLE*/
drop table EDS_CONSOLIDATED_SUPPLY_VOLTAGE_FORM_DATA if exists cascade constraints;  
drop table EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA if exists cascade constraints;
drop table EDS_MISSION_ACTIVATION_FORM_DATA if exists cascade constraints;
drop table EDS_GLOBAL_CONF if exists cascade constraints;
drop table EDS_EDS if exists cascade constraints;
drop table EDS_PROJECT if exists cascade constraints;
drop table EDS_COMPONENT_TYPE if exists cascade constraints;
drop table EDS_PROJECT_EDS if exists cascade constraints;
drop table EDS_NUMBER_96K if exists cascade constraints;
drop table EDS_EDS_NUMBER_96K if exists cascade constraints;
drop table EDS_PRIMARY_CURENT if exists cascade constraints;
drop table EDS_PROJECT_PRIMARY_CURENT if exists cascade constraints;
drop table EDS_MISSION_PROFIL_FORM_DATA if exists cascade constraints;
drop table EDS_MISSION_PROFIL if exists cascade constraints;
drop table EDS_DEFAILLANCE_VEILLE_REVEIL_FORM_DATA if exists cascade constraints;
drop table EDS_COMPORTEMENT_ROBUSTE_FORM_DATA if exists cascade constraints;
drop table EDS_COMPORTEMENT_CONSOLIDE_FORM_DATA if exists cascade constraints;
drop table EDS_SUPPLY if exists cascade constraints;
drop table EDS_PRIMARY_SUPPLY if exists cascade constraints;
drop table EDS_REPORT if exists cascade constraints;
drop table EDS_COORDINATES if exists cascade constraints;
drop table EDS_WORDING if exists cascade constraints;
drop table EDS_SUBSCRIPTION if exists cascade constraints;
drop table EDS_SUPPLIER if exists cascade constraints;
drop table EDS_PERIMETER if exists cascade constraints;
drop table EDS_EDS_PERIMETER if exists cascade constraints;
drop table EDS_PROJECT_PERIMETER if exists cascade constraints;
drop table EDS_USER if exists cascade constraints;
drop table EDS_ROLE if exists cascade constraints;
drop table EDS_WS_SESSION_TOKEN if exists cascade constraints;
--ROBUST
drop table EDS_ROBUST_CURENT_FORM_DATA if exists cascade constraints;
drop table EDS_PROJECT_ROBUST_CURENT if exists cascade constraints;
drop table EDS_ROBUST_SUPPLY if exists cascade constraints;
drop table EDS_GROUND_ROBUST_CURENT if exists cascade constraints;
drop table EDS_GROUND if exists cascade constraints;

/**Tableaux consommation de courants**/

/** Réseau en veille ou réveilé et organe inactif **/
drop table EDS_RESEAU_VEILLE_REVEILLE_ORGANE_INACTIF if exists cascade constraints;

/** Mode parc **/
drop table EDS_MODE_PARC if exists cascade constraints;

/** Mode montage **/
drop table EDS_MODE_MONTAGE if exists cascade constraints;

/** Courant de mise sous tension **/
drop table EDS_COURANT_MISE_SOUS_TENSION if exists cascade constraints;

/** Courant d'appel à l'activation **/
drop table EDS_COURANT_APPELLE_ACTIVATION if exists cascade constraints;

/** Courant nominal à l'activation **/
drop table EDS_COURANT_NOMINALE_ACTIVATION if exists cascade constraints;

/** Courant couple bloqué / Courant dysfonctionnel **/
drop table EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT if exists cascade constraints;

/**** COURANT CONSOLIDER*****/
drop table EDS_CONSOLIDATE_CURENT_FORM_DATA if exists cascade constraints;
drop table EDS_PROJECT_CONSOLIDATE_CURENT if exists cascade constraints;
drop table EDS_CONSOLIDATE_SUPPLY if exists cascade constraints;
drop table EDS_CONSOLIDATE_SUPPLY_THEORITIC if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_RVROI if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_MODE_PARC if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_MODE_MONTAGE if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_COURANT_MISE_SOUS_TENSION if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CAA if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CNA if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CBCD if exists cascade constraints;
/**** FIN COURANT CONSOLIDER*****/

/**** COURANT CONSOLIDER MESURE*****/
drop table EDS_CONSOLIDATE_SUPPLY_MESURE if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_RVROI if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_MODE_PARC if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_MODE_MONTAGE if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_COURANT_MISE_SOUS_TENSION if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CAA if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CNA if exists cascade constraints;
drop table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CBCD if exists cascade constraints;

/**** FIN COURANT CONSOLIDER MESURE*****/

/**** COURANT MESURE PSA*****/
drop table EDS_PSA_MESURE_CURENT_FORM_DATA if exists cascade constraints;
drop table EDS_PROJECT_PSA_MESURE_CURENT if exists cascade constraints;
drop table EDS_PSA_MESURE_SUPPLY if exists cascade constraints;
drop table EDS_JOIN_PSA_MESURE_SUPPLY_RVROI if exists cascade constraints;
drop table EDS_JOIN_PSA_MESURE_SUPPLY_MODE_PARC if exists cascade constraints;
drop table EDS_JOIN_PSA_MESURE_SUPPLY_MODE_MONTAGE if exists cascade constraints;
drop table EDS_JOIN_PSA_MESURE_SUPPLY_COURANT_MISE_SOUS_TENSION if exists cascade constraints;
drop table EDS_JOIN_PSA_MESURE_SUPPLY_CAA if exists cascade constraints;
drop table EDS_JOIN_PSA_MESURE_SUPPLY_CNA if exists cascade constraints;
drop table EDS_JOIN_PSA_MESURE_SUPPLY_CBCD if exists cascade constraints;
/**** FIN COURANT MESURE PSA*****/

drop table EDS_HIGH_VALIDATION_FORM_DATA if exists cascade constraints;
drop table EDS_LOW_VALIDATION_FORM_DATA if exists cascade constraints;
drop table EDS_VALIDATION if exists cascade constraints;
drop table EDS_CSE_LINE if exists cascade constraints;
drop table EDS_CSE_FORM_DATA if exists cascade constraints;
drop table EDS_ATTACHMENT if exists cascade constraints;
drop table EDS_ATTACHMENT_FORM_DATA if exists cascade constraints;
-- Profil de courant
drop table EDS_CURRENT_PROFILE_FORM_DATA if exists cascade constraints;
drop table EDS_CURRENT_SUPPLY if exists cascade constraints;
drop table EDS_CURRENT_STATEMENT if exists cascade constraints;
-- Infos des dérives
drop table EDS_DRIFT_INFO if exists cascade constraints;

/* FIN FORMULAIRES */

create table EDS_GLOBAL_CONF
(
    CONF_KEY varchar(32) not null,
    CONF_VALUE_TYPE varchar(32) not null,
    CONF_VALUE varchar(512),
    primary key (CONF_KEY)
);

create table EDS_EDS
(
	EDS_ID varchar(36) not null,
	EDS_P_ID varchar(36) not null,	/* Projet lanceur */
	EDS_CT_ID varchar(36) not null,
	EDS_S_ID varchar(36),
	EDS_W_ID varchar(36),
	EDS_ADMIN_ID varchar(36),
	EDS_MANAGER_ID varchar(36),
	EDS_OFFICER_ID varchar(36),
        EDS_DRIFT_VALIDATOR_ID varchar(36),
	EDS_REF varchar(32) not null,
        EDS_MAJOR_VERSION int not null,
        EDS_MINOR_VERSION int not null,
	EDS_CREA_USER_ID varchar(36) not null,
	EDS_CREA_DATE timestamp not null,
	EDS_MODIF_DATE timestamp not null,
	EDS_MODIF_USER_ID varchar(36),
	EDS_DRIFT_VALIDATION_DATE timestamp,
	EDS_NAME varchar(256) not null,
	EDS_VALID_LVL int,
	EDS_SUBTYPE_1 int,
	EDS_SUBTYPE_2 int,
	EDS_IS_BTTBT int not null,
	EDS_STATE int not null,
        EDS_P_LAUNCH_COUNT int,
        EDS_COMMENTS varchar(2048),
	primary key (EDS_ID),
        unique (EDS_REF, EDS_MAJOR_VERSION, EDS_MINOR_VERSION),
);

create table EDS_PROJECT
(
	P_ID varchar(36) not null,
	P_NAME varchar(1024) not null,
	P_DATE_FIRST_STAGE timestamp,
	P_W_ID_FIRST_STAGE varchar(36),
	P_DATE_SECOND_STAGE timestamp,
	P_W_ID_SECOND_STAGE varchar(36),
	P_DATE_THIRD_STAGE timestamp,
	P_W_ID_THIRD_STAGE varchar(36),
	P_DATE_FOURTH_STAGE timestamp,
	P_W_ID_FOURTH_STAGE varchar(36),
        P_INDEX int not null,
	primary key (P_ID),
);

create table EDS_COMPONENT_TYPE
(
	CT_ID varchar(36) not null,
        CT_NAME varchar(36) not null,
        CT_INDEX int not null,
	CT_BTTBT_OK_FORMSET varchar(512),
	CT_BTTBT_KO_FORMSET varchar(512),
	primary key (CT_ID),
        unique (CT_NAME)
);

create table EDS_PROJECT_EDS	/* Projets suiveurs */
(
        PEDS_ID varchar(36) not null,
	PEDS_P_ID varchar(36) not null,
	PEDS_EDS_ID varchar(36) not null,
	PEDS_RECONDUCTION_DATE timestamp,
        PEDS_COUNT int,
        PEDS_RECONDUCT_WITH_MODIF int,
	primary key (PEDS_ID),
        unique (PEDS_P_ID, PEDS_EDS_ID)
);

create table EDS_NUMBER_96K
(
        N_ID varchar(36) not null,
        primary key (N_ID)
);

create table EDS_EDS_NUMBER_96K
(
        EDSN_EDS_ID varchar(36) not null,
        EDSN_N_ID varchar(36) not null,
        primary key (EDSN_EDS_ID, EDSN_N_ID)
);

/* ===== DEBUT DE DECLARATION DES FORMULAIRES ===== */

/* ===== CONSOMATION DE COURANT PRIMAIRE ======*/
create table EDS_PRIMARY_CURENT
(
       PCEDS_ID varchar(36) not null,
       PCEDS_EDS_ID varchar(36) not null,
       PC_COMMENT varchar(1024),
        PC_IMAGE varchar(225),
      primary key (PCEDS_ID)       
);

/*TABLE DE JOINTURE AVEC LA TABLE EDS_PROJECT*/
create table EDS_PROJECT_PRIMARY_CURENT
(
        PPC_P_ID varchar(36) not null,
        PPC_PCEDS_ID varchar(36) not null,
        primary key (PPC_P_ID,PPC_PCEDS_ID)
);

create table EDS_SUPPLY
(
        SEDS_ID varchar(36) not null,
        SEDS_EDS_ID varchar(36) not null,
        S_PCEDS_ID varchar(36) ,
        S_PSEDS_ID varchar(36) ,
        S_RCEDS_ID varchar(36),
        S_RSEDS_ID varchar(36),
        S_CCEDS_ID varchar(36),
        S_CSEDS_ID varchar(36),
        S_PMSEDS_ID varchar(36) ,
        S_PMCEDS_ID varchar(36),
        SEDS_SUPPLY_NAME varchar(225),
        primary key (SEDS_ID),
);

create table EDS_PRIMARY_SUPPLY
(
        PSEDS_ID varchar(36) not null,
        PS_RANK integer,
        PSEDS_SUPPLY_NAME varchar(225),
        PSEDS_SUPPLY_TYPE varchar (25),
        PSEDS_TBT_BT varchar(3),
        PSEDS_I_VEILLE real,
        PSEDS_I_REVEILLE_INACTIF real,   --A voir leur contraite de taille du real
        PSEDS_I_NOM_STAB REAL,
        PSEDS_I_PIRECAS_STAB REAL,

        PSEDS_SUPPLY_NAME_IFORM_BY varchar(1024),
        PSEDS_SUPPLY_TYPE_SUPPLY_NAME_IFORM_BY varchar(1024),
        PSEDS_I_VEILLE_IFORM_BY varchar(1024),
        PSEDS_REVEILLE_IFORM_BY varchar(1024),
        PSEDS_I_NOM_STAB_IFORM_BY varchar(1024),
        PSEDS_I_PIRECAS_STAB_IFORM_BY varchar(1024),

        PSEDS_SUPPLY_NAME_COMMENT varchar(1024),
        PSEDS_SUPPLY_TYPE_SUPPLY_NAME_COMMENT varchar(1024),
        PSEDS_I_VEILLE_COMMENT varchar(1024),
        PSEDS_REVEILLE_COMMENT varchar(1024),
        PSEDS_I_NOM_STAB_COMMENT varchar(1024),
        PSEDS_I_PIRECAS_COMMENT varchar(1024),
        
        primary key (PSEDS_ID),
        
); 

/* ===== CONSOMATION DE COURANT ROBUST ======*/
create table EDS_ROBUST_CURENT_FORM_DATA
(
       RCEDS_ID varchar(36) not null,
       RCEDS_EDS_ID varchar(36) not null,
       RC_COMMENT varchar(1024),
primary key (RCEDS_ID)       
);
create table EDS_PROJECT_ROBUST_CURENT
(
        PRC_P_ID varchar(36) not null,
        PRC_RCEDS_ID varchar(36) not null,
        primary key (PRC_P_ID,PRC_RCEDS_ID)
);
create table EDS_ROBUST_SUPPLY
(
        RSEDS_ID varchar(36) not null,

        RSEDS_SUPPLY_NAME varchar(225),
        RSEDS_SUPPLY_TYPE varchar (25),
        RSEDS_TBT_BT varchar(3),
        RSEDS_U_CARAC_NAME varchar (25),
        RSEDS_I_VEILLE real,
        RSEDS_I_REVEILLE_INACTIF real,   --A voir leur contraite de taille du real
        RSEDS_I_NOM_STAB REAL,
        RSEDS_I_PIRECAS_STAB REAL,
        RSEDS_U_MIN real,
        RSEDS_U_MOY real,
        RSEDS_U_MAX real,
        RSEDS_I_MAX real,
        RSEDS_T_IMAX real,
        RSEDS_U_CARAC_VALUE real,
        RSEDS_SUPPLY_NAME_IFORM_BY varchar(1024),
        RSEDS_SUPPLY_TYPE_SUPPLY_NAME_IFORM_BY varchar(1024),
        RSEDS_I_VEILLE_IFORM_BY varchar(1024),
        RSEDS_REVEILLE_IFORM_BY varchar(1024),
        RSEDS_I_NOM_STAB_IFORM_BY varchar(1024),
        RSEDS_I_PIRECAS_STAB_IFORM_BY varchar(1024),
        RSEDS_U_MIN_INFORM_BY varchar(1024),
        RSEDS_U_moy_INFORM_BY  varchar(1024),
        RSEDS_U_max_INFORM_BY varchar(1024),
        RSEDS_I_MAX_INFORM_BY varchar(1024),
        RSEDS_T_IMAX_INFORM_BY varchar(1024),
        RSEDS_U_CARAC_INFORM_BY varchar(1024),
        RSEDS_SUPPLY_NAME_COMMENT varchar(1024),
        RSEDS_SUPPLY_TYPE_SUPPLY_NAME_COMMENT varchar(1024),
        RSEDS_I_VEILLE_COMMENT varchar(1024),
        RSEDS_REVEILLE_COMMENT varchar(1024),
        RSEDS_I_NOM_STAB_COMMENT varchar(1024),
        RSEDS_I_PIRECAS_COMMENT varchar(1024),
        RSEDS_U_MIN_COMMENT varchar(1024),
        RSEDS_U_moy_COMMENT  varchar(1024),
        RSEDS_U_max_COMMENT varchar(1024),
        RSEDS_I_MAX_COMMENT varchar(1024),
        RSEDS_T_IMAX_COMMENT varchar(1024), 
        RSEDS_U_CARAC_COMMENT varchar(1024),        
        primary key (RSEDS_ID),

        
);
create table EDS_GROUND
(
        GEDS_ID varchar(36) not null,
        G_GRCEDS_ID varchar(36) ,
        G_RCEDS_ID varchar(36) ,
        primary key (GEDS_ID),
);
create table EDS_GROUND_ROBUST_CURENT
(
        GRC_ID varchar(36) not null,
        GSEDS_TITRE_MASSE VARCHAR(1024),
        GSEDS_I_VEILLE real,
        GSEDS_I_REVEILLE_INACTIF real,   
        GSEDS_I_NOM_STAB REAL,
        GSEDS_I_PIRECAS_STAB REAL,
        GSEDS_I_MST REAL,
        GSEDS_I_DELTA_T_MST REAL,

        GSEDS_I_VEILLE_IFORM_BY varchar(1024),
        GSEDS_REVEILLE_IFORM_BY varchar(1024),
        GSEDS_I_NOM_STAB_IFORM_BY varchar(1024),
        GSEDS_I_PIRECAS_STAB_IFORM_BY varchar(1024),

        GSEDS_I_VEILLE_COMMENT varchar(1024),
        GSEDS_REVEILLE_COMMENT varchar(1024),
        GSEDS_I_NOM_STAB_COMMENT varchar(1024),
        GSEDS_I_PIRECAS_COMMENT varchar(1024),
        GSEDS_CHOIX_MASSE VARCHAR(1024),


        primary key (GRC_ID)
); 
/** Tableaux de consommation de courant **/

/*RESEAU VEILLE OU REVEILLE ORGANE INACTIF*/
create table EDS_RESEAU_VEILLE_REVEILLE_ORGANE_INACTIF
(
        RVROIEDS_ID varchar(36) not null,
        RVROIEDS_QCF1 varchar(5),
        RVROIEDS_QCF2 varchar(5),
        RVROIEDS_QCF3 varchar(5),
        RVROIEDS_QCF4 varchar(5),


        RVROIEDS_TMIN_IVEILLE_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMOY_IVEILLE_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMAX_IVEILLE_MOTEUR_NON_TOURANT REAL, 

        RVROIEDS_TMIN_IVEILLE_8H_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMOY_IVEILLE_8H_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMAX_IVEILLE_8H_MOTEUR_NON_TOURANT REAL,

        RVROIEDS_TMIN_IVEILLE_21H_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMOY_IVEILLE_21H_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMAX_IVEILLE_21H_MOTEUR_NON_TOURANT REAL,

        RVROIEDS_TMIN_IVEILLE_30H_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMOY_IVEILLE_30H_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMAX_IVEILLE_30H_MOTEUR_NON_TOURANT REAL,

        RVROIEDS_TMIN_IREVEILLE_INACTIF_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMOY_IREVEILLE_INACTIF_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMAX_IREVEILLE_INACTIF_MOTEUR_NON_TOURANT REAL,
        
        RVROIEDS_TMIN_IMODE_ECO_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMOY_IMODE_ECO_MOTEUR_NON_TOURANT REAL,
        RVROIEDS_TMAX_IMODE_ECO_MOTEUR_NON_TOURANT REAL,

        RVROIEDS_TMIN_IREVEILLE_INACTIF_10V_MOTEUR_TOURANT REAL,
        RVROIEDS_TMIN_IREVEILLE_INACTIF_13V_MOTEUR_TOURANT REAL,
        RVROIEDS_TMIN_IREVEILLE_INACTIF_15V_MOTEUR_TOURANT REAL,
        

        RVROIEDS_TMOY_IREVEILLE_INACTIF_10V_MOTEUR_TOURANT REAL,
        RVROIEDS_TMOY_IREVEILLE_INACTIF_13V_MOTEUR_TOURANT REAL,
        RVROIEDS_TMOY_IREVEILLE_INACTIF_15V_MOTEUR_TOURANT REAL,
        
        RVROIEDS_TMAX_IREVEILLE_INACTIF_10V_MOTEUR_TOURANT REAL,
        RVROIEDS_TMAX_IREVEILLE_INACTIF_13V_MOTEUR_TOURANT REAL,
        RVROIEDS_TMAX_IREVEILLE_INACTIF_15V_MOTEUR_TOURANT REAL,

        RVROIEDS_TMIN_IVEILLE_MOTEUR_NON_TOURANT_COMMENT varchar(1024),
        RVROIEDS_TMIN_IVEILLE_8H_MOTEUR_NON_TOURANT_COMMENT varchar(1024),
        RVROIEDS_TMOY_IVEILLE_21H_MOTEUR_NON_TOURANT_COMMENT varchar(1024),
        RVROIEDS_TMAX_IVEILLE_30H_MOTEUR_NON_TOURANT_COMMENT varchar(1024),
        RVROIEDS_TMIN_IREVEILLE_INACTIF_MOTEUR_NON_TOURANT_COMMENT varchar(1024),
        RVROIEDS_TMIN_IMODE_ECO_MOTEUR_NON_TOURANT_COMMENT varchar(1024),
        RVROIEDS_TMIN_IREVEILLE_INACTIF_10V_MOTEUR_TOURANT_COMMENT varchar(1024),
        RVROIEDS_TMIN_IREVEILLE_INACTIF_13V_MOTEUR_TOURANT_COMMENT varchar(1024),
        RVROIEDS_TMIN_IREVEILLE_INACTIF_15V_MOTEUR_TOURANT_COMMENT varchar(1024),
        
        primary key (RVROIEDS_ID),
);

/*MODE PARC*/
create table EDS_MODE_PARC
(
        MPEDS_ID varchar(36) not null,
        MPEDS_QCF1 varchar(5),
        MPEDS_TMOY_MODE_PARC REAL,
        MPEDS_TMAX_MODE_PARC REAL, 
        MPEDS_TMOY_MODE_PARC_COMMENT varchar(1024),
        primary key (MPEDS_ID),
);

/*MODE MONTAGE*/
create table EDS_MODE_MONTAGE
(
        MMEDS_ID varchar(36) not null,
        MMEDS_QCF1 varchar(5),
        MMEDS_TMOY_MODE_MONTAGE REAL,
        MMEDS_TMOY_MODE_MONTAGE_COMMENT varchar(1024),
        primary key (MMEDS_ID),
);

/*COURANT DE MISE SOUS TENSION*/
create table EDS_COURANT_MISE_SOUS_TENSION
(
        CMSTEDS_ID varchar(36) not null,
      
        CMSTEDS_TPIRECAS_IMST REAL,
        CMSTEDS_TPIRECAS_Dt REAL, 
        CMSTEDS_TPIRECAS_IMST_COMMENT varchar(1024),
        CMSTEDS_TPIRECAS_Dt_COMMENT varchar(1024),
        primary key (CMSTEDS_ID),
);

/*COURANT D'APPEL A L'ACTIVATION */
create table EDS_COURANT_APPELLE_ACTIVATION
(
        CAAEDS_ID varchar(36) not null,
        CAAEDS_QCF1 varchar(5),
        CAAEDS_TMIN_IMAX_PULSE REAL, 
        CAAEDS_TMOY_IMAX_PULSE REAL,
        CAAEDS_TMAX_IMAX_PULSE REAL, 
        CAAEDS_TMOY_IMAX_PULSE_COMMENT varchar(1024),

        CAAEDS_TMIN_DT_PULSE REAL, 
        CAAEDS_TMOY_DT_PULSE REAL,
        CAAEDS_TMAX_DT_PULSE REAL, 
        CAAEDS_TMOY_DT_PULSE_COMMENT varchar(1024),
        primary key (CAAEDS_ID),
);

 /*COURANT NOMINALE A l'ACTIVATION*/
create table EDS_COURANT_NOMINALE_ACTIVATION
(
        CNAEDS_ID varchar(36) not null,
        CNAEDS_P_ID varchar(36),

        CNEDS_QCF1 varchar(5),
        CNEDS_QCF5 varchar(5),
        CNEDS_QCF6 varchar(5),

        CNEDS_NAME varchar(256),

        CNAEDS_TMIN_INOM_STAB_MOTEUR_NON_TOURANT REAL,
        CNAEDS_TMOY_INOM_STAB_MOTEUR_NON_TOURANT REAL,
        CNAEDS_TMAX_INOM_STAB_MOTEUR_NON_TOURANT REAL,
        
        CNAEDS_TMIN_TNOM_STAB_MOTEUR_NON_TOURANT REAL,
        CNAEDS_TMOY_TNOM_STAB_MOTEUR_NON_TOURANT REAL,
        CNAEDS_TMAX_TNOM_STAB_MOTEUR_NON_TOURANT REAL,
        CNAEDS_TMOY_INOM_STAB_MOTEUR_NON_TOURANT_COMMENT varchar(1024),

        
        CNAEDS_TMIN_IPIRE_CAS_MOTEUR_NON_TOURANT REAL,
        CNAEDS_TMOY_IPIRE_CAS_MOTEUR_NON_TOURANT REAL,
        CNAEDS_TMAX_IPIRE_CAS_MOTEUR_NON_TOURANT REAL,
        

        CNAEDS_TMIN_TPIRE_CAS_MOTEUR_NON_TOURANT REAL,
        CNAEDS_TMOY_TPIRE_CAS_MOTEUR_NON_TOURANT REAL,
        CNAEDS_TMAX_TPIRE_CAS_MOTEUR_NON_TOURANT REAL,
        CNAEDS_TMOY_IPIRE_CAS_MOTEUR_NON_TOURANTT_COMMENT varchar(1024),

        CNAEDS_TMIN_INOM_STAB_10_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_INOM_STAB_10_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_INOM_STAB_10_MOTEUR_TOURANT REAL,

        CNAEDS_TMIN_TNOM_STAB_10_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_TNOM_STAB_10_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_TNOM_STAB_10_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_INOM_STAB_10_MOTEUR_TOURANT_COMMENT varchar(1024),

        CNAEDS_TMIN_INOM_STAB_13_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_INOM_STAB_13_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_INOM_STAB_13_MOTEUR_TOURANT REAL,

        CNAEDS_TMIN_TNOM_STAB_13_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_TNOM_STAB_13_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_TNOM_STAB_13_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_INOM_STAB_13_MOTEUR_TOURANT_COMMENT varchar(1024),

        CNAEDS_TMIN_INOM_STAB_15_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_INOM_STAB_15_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_INOM_STAB_15_MOTEUR_TOURANT REAL,

        CNAEDS_TMIN_TNOM_STAB_15_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_TNOM_STAB_15_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_TNOM_STAB_15_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_INOM_STAB_15_MOTEUR_TOURANT_COMMENT varchar(1024),
        
        CNAEDS_TMIN_IPIRE_CAS_10_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_IPIRE_CAS_10_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_IPIRE_CAS_10_MOTEUR_TOURANT REAL,

        CNAEDS_TMIN_TPIRE_CAS_10_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_TPIRE_CAS_10_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_TPIRE_CAS_10_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_IPIRE_CAS_10_MOTEUR_TOURANT_COMMENT varchar(1024),

        CNAEDS_TMIN_IPIRE_CAS_13_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_IPIRE_CAS_13_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_IPIRE_CAS_13_MOTEUR_TOURANT REAL,

        CNAEDS_TMIN_TPIRE_CAS_13_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_TPIRE_CAS_13_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_TPIRE_CAS_13_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_IPIRE_CAS_13_MOTEUR_TOURANT_COMMENT varchar(1024),

        CNAEDS_TMIN_IPIRE_CAS_15_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_IPIRE_CAS_15_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_IPIRE_CAS_15_MOTEUR_TOURANT REAL,

        CNAEDS_TMIN_TPIRE_CAS_15_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_TPIRE_CAS_15_MOTEUR_TOURANT REAL,
        CNAEDS_TMAX_TPIRE_CAS_15_MOTEUR_TOURANT REAL,
        CNAEDS_TMOY_IPIRE_CAS_15_MOTEUR_TOURANT_COMMENT varchar(1024),

        CNAEDS_TMIN_INOM_DEM_CONSOMMATION_DEMARRAGE REAL,
        CNAEDS_TMOY_INOM_DEM_CONSOMMATION_DEMARRAGE REAL,
        CNAEDS_TMAX_INOM_DEM_CONSOMMATION_DEMARRAGE REAL,

        CNAEDS_TMIN_TNOM_DEM_CONSOMMATION_DEMARRAGE REAL,
        CNAEDS_TMOY_TNOM_DEM_CONSOMMATION_DEMARRAGE REAL,
        CNAEDS_TMAX_TNOM_DEM_CONSOMMATION_DEMARRAGE REAL,

        CNAEDS_TMOY_INOM_DEM_CONSOMMATION_DEMARRAGE_COMMENT varchar(1024),
        primary key (CNAEDS_ID),
);


/*COURANT COUPLE BLOQUER / DYSFONCTIONNEMENT*/
create table EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT
(
        CBCDEDS_ID varchar(36) not null,
        CBCDEDS_QCF1 varchar(5),
        CBCDEDS_TMIN_IDYS REAL, 
        CBCDEDS_TMOY_IDYS REAL,
        CBCDEDS_TMAX_IDYS REAL, 
        
        CBCDEDS_TMIN_TDYS REAL, 
        CBCDEDS_TMOY_TDYS REAL,
        CBCDEDS_TMAX_TDYS REAL, 
        CBCDEDS_OCCURENCE_DEFAILLENCE REAL,
        CBCDEDS_OCCURENCE_DEFAILLENCE_TDYS REAL,
        CBCDEDS_TMOY_IDYS_COMMENT varchar(1024),
        CBCDEDS_TMOY_TDYS_COMMENT varchar(1024),
        primary key (CBCDEDS_ID ),
);
/****  Consommation de courant consolidé****/

create table EDS_CONSOLIDATE_CURENT_FORM_DATA
(
       CCEDS_ID varchar(36) not null,
       CCEDS_EDS_ID varchar(36) not null,
       CC_COMMENT varchar(1024),
primary key (CCEDS_ID)       
);

create table EDS_PROJECT_CONSOLIDATE_CURENT
(
        PCC_P_ID varchar(36) not null,
        PCC_CCEDS_ID varchar(36) not null,
        primary key (PCC_P_ID,PCC_CCEDS_ID)
);

create table EDS_CONSOLIDATE_SUPPLY
(
        CSEDS_ID varchar(36) not null,
        CSEDS_CST_ID varchar(36) not null,
        CSMEDS_CSM_ID varchar(36) not null,
        CSEDS_SUPPLY_NAME varchar(225),
        CSEDS_SUPPLY_TYPE varchar (25),
        CSEDS_TBT_BT varchar(3),
        primary key (CSEDS_ID),
        
);

create table EDS_CONSOLIDATE_SUPPLY_THEORITIC
(
        CSTEDS_ID varchar(36) not null,
        CSEDS_COURANT REAL,
        CSEDS_UMIN REAL,
        CSEDS_UMAX REAL,
        CSEDS_TMIN REAL,
        CSEDS_TMAX REAL,
        CSEDS_DUREE REAL,
        CSEDS_OCCURRENCE REAL,
        CSEDS_DT_IMST REAL,
    
        CSEDS_COURANT_IFORM_BY varchar(1024),
        CSEDS_UMIN_IFORM_BY varchar(1024),
        CSEDS_UMAX_IFORM_BY varchar(1024),
        CSEDS_TMIN_IFORM_BY varchar(1024),
        CSEDS_TMAX_IFORM_BY varchar(1024),
        CSEDS_DUREE_IFORM_BY varchar(1024),
        CSEDS_OCCURRENCE_IFORM_BY varchar(1024),
        CSEDS_DT_IMST_IFORM_BY varchar(1024),

        CSEDS_COURANT_COMMENT varchar(1024),
        CSEDS_UMIN_COMMENT varchar(1024),
        CSEDS_UMAX_COMMENT varchar(1024),
        CSEDS_TMIN_COMMENT varchar(1024),
        CSEDS_TMAX_COMMENT varchar(1024),
        CSEDS_DUREE_COMMENT varchar(1024),
        CSEDS_OCCURRENCE_COMMENT varchar(1024),
        CSEDS_DT_IMST_COMMENT varchar(1024),
       
        primary key (CSTEDS_ID),

);

/*RESEAU VEILLE OU REVEILLE ORGANE INACTIF*/
create table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_RVROI
(
        JCSTRVROIEDS_ID varchar(36) not null,
        JCSTRVROIEDS_RVROI_ID varchar(36) not null,
        primary key (JCSTRVROIEDS_ID,JCSTRVROIEDS_RVROI_ID),
);

/*MODE PARC*/
create table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_MODE_PARC
(
        JCSTMPEDS_ID varchar(36) not null,
        JCSTMPEDS_MP_ID varchar(36)not null,
        primary key (JCSTMPEDS_ID,JCSTMPEDS_MP_ID),
);

/*MODE MONTAGE*/
create table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_MODE_MONTAGE
(
        JCSTMMEDS_ID varchar(36) not null,
        JCSTMMEDS_MM_ID varchar(36) not null,
        primary key (JCSTMMEDS_ID,JCSTMMEDS_MM_ID),
);

/*COURANT DE MISE SOUS TENSION*/
create table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_COURANT_MISE_SOUS_TENSION
(
        JCSTCMSTEDS_ID varchar(36) not null,
        JCSTCMSTEDS_CMST_ID varchar(36) not null,
        primary key (JCSTCMSTEDS_ID,JCSTCMSTEDS_CMST_ID),
);

/*COURANT D'APPEL A L'ACTIVATION */
create table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CAA
(
        JCSTCAAEDS_ID varchar(36) not null,
        JCSTCAAEDS_CAA_ID varchar(36) not null,
        primary key (JCSTCAAEDS_ID,JCSTCAAEDS_CAA_ID),
);

 /*COURANT NOMINALE A l'ACTIVATION*/
create table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CNA
(
        JCSTCNAEDS_ID varchar(36) not null,
        JCSTCNAEDS_CNA_ID varchar(36) not null,
        primary key (JCSTCNAEDS_ID,JCSTCNAEDS_CNA_ID),
);


/*COURANT COUPLE BLOQUER / DYSFONCTIONNEMENT*/
create table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CBCD
(
        JCSTCBCDEDS_ID varchar(36) not null,
        JCSTCBCDEDS_CBCD_ID varchar(36) not null,
        primary key (JCSTCBCDEDS_ID,JCSTCBCDEDS_CBCD_ID),
);

/***FIN CONSOMATION COURANT CONSOLIDE***/

/***CONSOMMATION DE COURANT CONSOLIDER PARTIE MESURE****/
create table EDS_CONSOLIDATE_SUPPLY_MESURE
(
        CSMEDS_ID varchar(36) not null,
        CSMEDS_COURANT REAL,
        CSMEDS_UMIN REAL,
        CSMEDS_UMAX REAL,
        CSMEDS_TMIN REAL,
        CSMEDS_TMAX REAL,
        CSMEDS_DUREE REAL,
        CSMEDS_OCCURRENCE REAL,
        CSMEDS_DT_IMST REAL,
        CSMEDS_NOMBRE_PIECE_TESTER REAL,        
        CSMEDS_ECHANTILLON_DE_TEST VARCHAR(25),   

        CSMEDS_COURANT_IFORM_BY varchar(1024),
        CSMEDS_UMIN_IFORM_BY varchar(1024),
        CSMEDS_UMAX_IFORM_BY varchar(1024),
        CSMEDS_TMIN_IFORM_BY varchar(1024),
        CSMEDS_TMAX_IFORM_BY varchar(1024),
        CSMEDS_DUREE_IFORM_BY varchar(1024),
        CSMEDS_OCCURRENCE_IFORM_BY varchar(1024),
        CSMEDS_DT_IMST_IFORM_BY varchar(1024),
        CSMEDS_NOMBRE_PIECE_TESTER_IFORM_BY varchar(1024),
        CSMEDS_ECHANTILLON_DE_TEST_IFORM_BY varchar(1024),

        CSMEDS_COURANT_COMMENT varchar(1024),
        CSMEDS_UMIN_COMMENT varchar(1024),
        CSMEDS_UMAX_COMMENT varchar(1024),
        CSMEDS_TMIN_COMMENT varchar(1024),
        CSMEDS_TMAX_COMMENT varchar(1024),
        CSMEDS_DUREE_COMMENT varchar(1024),
        CSMEDS_OCCURRENCE_COMMENT varchar(1024),
        CSMEDS_DT_IMST_COMMENT varchar(1024),
        CSMEDS_NOMBRE_PIECE_TESTER_COMMENT varchar(1024),
        CSMEDS_ECHANTILLON_DE_TEST__COMMENT varchar(1024),
       
        primary key (CSMEDS_ID),

);

/*RESEAU VEILLE OU REVEILLE ORGANE INACTIF*/
create table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_RVROI
(
        JCSMRVROIEDS_ID varchar(36) not null,
        JCSMRVROIEDS_RVROI_ID varchar(36) not null,
        primary key (JCSMRVROIEDS_ID,JCSMRVROIEDS_RVROI_ID),
);

/*MODE PARC*/
create table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_MODE_PARC
(
        JCSMMPEDS_ID varchar(36) not null,
        JCSMMPEDS_MP_ID varchar(36)not null,
        primary key (JCSMMPEDS_ID,JCSMMPEDS_MP_ID),
);

/*MODE MONTAGE*/
create table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_MODE_MONTAGE
(
        JCSMMMEDS_ID varchar(36) not null,
        JCSMMMEDS_MM_ID varchar(36) not null,
        primary key (JCSMMMEDS_ID,JCSMMMEDS_MM_ID),
);


/*COURANT DE MISE SOUS TENSION*/

create table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_COURANT_MISE_SOUS_TENSION
(
        JCSMCMSTEDS_ID varchar(36) not null,
        JCSMCMSTEDS_CMST_ID varchar(36) not null,
        primary key (JCSMCMSTEDS_ID,JCSMCMSTEDS_CMST_ID),
);

/*COURANT D'APPELLE A L'ACTIVATION */

create table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CAA
(
        JCSMCAAEDS_ID varchar(36) not null,
        JCSMCAAEDS_CAA_ID varchar(36) not null,
        primary key (JCSMCAAEDS_ID,JCSMCAAEDS_CAA_ID),
);

 /*COURANT NOMINALE A l'ACTIVATION*/
create table  EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CNA
(
        JCSMCNAEDS_ID varchar(36) not null,
        JCSMCNAEDS_CNA_ID varchar(36) not null,
        primary key (JCSMCNAEDS_ID,JCSMCNAEDS_CNA_ID),
);


/*COURANT COUPLE BLOQUER / DYSFONCTIONNEMENT*/

create table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CBCD
(
        JCSMCBCDEDS_ID varchar(36) not null,
        JCSMCBCDEDS_CBCD_ID varchar(36) not null,
        primary key (JCSMCBCDEDS_ID,JCSMCBCDEDS_CBCD_ID),
);
/***FIN CONSOMMATION DE COURANT CONSOLIDER PARTIE MESURE****/


/****  Consommation de courant Mesure PSA****/

create table EDS_PSA_MESURE_SUPPLY
(
        PMSEDS_ID varchar(36) not null,
        PMSEDS_EDS_ID varchar(36) not null,
        PMSEDS_SUPPLY_NAME varchar(10),
        PMSEDS_SUPPLY_TYPE varchar (25),
        PMSEDS_TBT_BT varchar(3),

        primary key (PMSEDS_ID),        
);

alter table EDS_PSA_MESURE_SUPPLY add constraint PMSEDS_EDS_ID_FK foreign key (PMSEDS_EDS_ID) references EDS_EDS(EDS_ID);
/*RESEAU VEILLE OU REVEILLE ORGANE INACTIF*/
create table EDS_JOIN_PSA_MESURE_SUPPLY_RVROI
(
        JPMSRVROIEDS_ID varchar(36) not null,
        JPMSRVROIEDS_RVROI_ID varchar(36) not null,
        primary key (JPMSRVROIEDS_ID,JPMSRVROIEDS_RVROI_ID),
);

/*MODE PARC*/
create table EDS_JOIN_PSA_MESURE_SUPPLY_MODE_PARC
(
        JPMSMPEDS_ID varchar(36) not null,
        JPMSMPEDS_MP_ID varchar(36)not null,
        primary key (JPMSMPEDS_ID,JPMSMPEDS_MP_ID),
);

/*MODE MONTAGE*/
create table EDS_JOIN_PSA_MESURE_SUPPLY_MODE_MONTAGE
(
        JPMSMMEDS_ID varchar(36) not null,
        JPMSMMEDS_MM_ID varchar(36) not null,
        primary key (JPMSMMEDS_ID,JPMSMMEDS_MM_ID),
);


/*COURANT DE MISE SOUS TENSION*/

create table EDS_JOIN_PSA_MESURE_SUPPLY_COURANT_MISE_SOUS_TENSION
(
        JPMSCMSTEDS_ID varchar(36) not null,
        JPMSCMSTEDS_CMST_ID varchar(36) not null,
        primary key (JPMSCMSTEDS_ID,JPMSCMSTEDS_CMST_ID),
);

/*COURANT D'APPELLE A L'ACTIVATION */

create table EDS_JOIN_PSA_MESURE_SUPPLY_CAA
(
        JPMSCAAEDS_ID varchar(36) not null,
        JPMSCAAEDS_CAA_ID varchar(36) not null,
        primary key (JPMSCAAEDS_ID,JPMSCAAEDS_CAA_ID),
);

 /*COURANT NOMINALE A l'ACTIVATION*/

create table EDS_JOIN_PSA_MESURE_SUPPLY_CNA
(
        JPMSCNAEDS_ID varchar(36) not null,
        JPMSCNAEDS_CNA_ID varchar(36) not null,
        primary key (JPMSCNAEDS_ID,JPMSCNAEDS_CNA_ID),
);

/*COURANT COUPLE BLOQUER / DYSFONCTIONNEMENT*/

create table EDS_JOIN_PSA_MESURE_SUPPLY_CBCD
(
        JPMSCBCDEDS_ID varchar(36) not null,
        JPMSCBCDEDS_CBCD_ID varchar(36) not null,
        primary key (JPMSCBCDEDS_ID,JPMSCBCDEDS_CBCD_ID),
);
/***FIN CONSOMATION MESURE PSA***/

/* ***** FORMULAIRE TENSION ALIMENTATION CONSOLIDE ***** */
----------------------------------------------------------------------------------------------------------------------------------------------
create table EDS_CONSOLIDATED_SUPPLY_VOLTAGE_FORM_DATA
(
       CSV_ID VARCHAR(36) NOT NULL,
       CSV_VALEUR_TENSION_INITIALISATION VARCHAR(256),
       CSV_ORIGINE_TENSION_NISIALISATION VARCHAR(36),
       CSV_VALEUR_TENSION_MINIMALE_FONCTIONNEMENT_NOMINALE VARCHAR(256),
       CSV_ORIGINE_TENSION_MINIMALE_FONCTIONNEMENT_NOMINALE VARCHAR(36) ,
       CSV_VALEUR_TENSION_MAXIMAL_FONCTIONNEMENT_NOMINALE VARCHAR(256),
       CSV_ORIGINE_TENSION_MAXIMAL_FONCTIONNEMENT_NOMINALE VARCHAR(36) ,
       CSV_VALEUR_TENSION_COUTURE VARCHAR(256),
       CSV_ORIGINE_TENSION_COUPURE VARCHAR(36) ,
       CSV_VALEUR_TENSION_REHABILISATION VARCHAR(256),
       CSV_ORIINE_TENSION_REHABILISATION VARCHAR (36) ,
       CSV_ORIGINE_TENSION_RESET VARCHAR (36) ,
       CSV_VALEUR_TENSION_RESET VARCHAR(256),
       CSV_EDS_ID VARCHAR (36) NOT NULL,
       PRIMARY KEY (CSV_ID)
);

/* ***** FORMULAIRE TENSION ALIMENTATION PRELIMINAIRE ***** */
create table EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA
(
       PSV_ID VARCHAR(36) NOT NULL,
       PSV_VALEUR_TENSION_MINIMALE_FONCTIONNEMENT_INITIALISATION varchar(256),
       PSV_VALEUR_TENSION_MINIMALE_FONCTIONNEMENT_NOMINALE varchar(256),
       PSV_VALEUR_TENSION_MAXIMALE_FONCTIONNEMENT_NOMINALE varchar(256),
       PSV_VALEUR_TENSION_MAXIMALE_FONCTIONNEMENT_REHABILISATION varchar(256),
       PSV_ORIGINE_DONNEE_TENSION_MINIMALE_FONCTIONNEMENT_INITIALISATION VARCHAR(36) ,
       PSV_ORIGINE_DONNEE_TENSION_MINIMALE_FONCTIONNEMENT_NOMINALE VARCHAR(36) ,
       PSV_ORIGINE_DONNEE_TENSION_MAXIMALE_FONCTIONNEMENT_NOMINALE VARCHAR(36) ,
       PSV_ORIGINE_DONNEE_TENSION_MAXIMALE_FONCTIONNEMENT_REHABILISATION VARCHAR(36) ,
       PSV_EDS_ID VARCHAR (36) NOT NULL,
       PRIMARY KEY (PSV_ID)
);

      
/* ***** FORMULAIRE PROFIL D'ACTIVATION ***** */


create table EDS_MISSION_ACTIVATION_FORM_DATA
(
       MAFD_ID VARCHAR(36) NOT NULL,
       MAFD_COMMENTAIRE VARCHAR(2048),
       MAFD_DOC_INFO_URLS VARCHAR (256),
       MAFD_EDS_ID VARCHAR (36) NOT NULL,
       PRIMARY KEY (MAFD_ID)
);

-- RELEVÉ DE COURANT
create table EDS_CURRENT_STATEMENT
(
    CS_ID varchar(36) not null,
    CS_MAFD_ID varchar(36) not null, -- Formulaire de profil d'activation
    CS_SEDS_ID varchar(36) not null, -- Alimentation associée
    CS_NAME varchar(128) not null,
    CS_XY_DATA varchar(4096),
    CS_COMMENT varchar(1024),
    CS_USER_ID varchar(36),

    primary key (CS_ID)
);


/* ***** FORMULAIRE PROFIL DE MISSION ***** */


create table EDS_MISSION_PROFIL
(
       MP_ID varchar(36) not null,
       MP_RANK integer,
       MP_NOMBRE_ACTIVATION integer,       
       MP_UNIT integer,
       MP_COMMENTAIRE VARCHAR(2048),
       MP_DOCS_INFOS_URLS varchar (256),
       MP_NOM_PROFIL varchar (256),
       MP_MPFD_ID varchar(36) not null,
       primary key (MP_ID)
);

create table EDS_MISSION_PROFIL_FORM_DATA
(
       MPFD_ID varchar(36) not null,
       MPFD_EDS_ID varchar(36) not null,
       primary key ( MPFD_ID)
);


/* ***** FORMULAIRE DEFAILLANCE VEILLE REVEIL ***** */

create table EDS_DEFAILLANCE_VEILLE_REVEIL_FORM_DATA
(
      DVRFD_ID VARCHAR (36) NOT NULL,
      DVRFD_OCCURENCE_TOTAL_DEFAILLANCE_ORGANE_CONSOMMATION_PLUS_VINT_MAH VARCHAR(256),
      DVRFD_COURANT_CORRESPONDANT_CONSOMMATION_PLUS_VINT_MAH VARCHAR(256),
      DVRFD_COMMENTAIRE_CONSOMMATION_PLUS_VINT_MAH VARCHAR(2048),
      DVRFD_IS_PLUS_VINT_MAH INT,
      DVRFD_OCCURENCE_TOTAL_DEFAILLANCE_ORGANE_MAINTIEN_RESEAUX VARCHAR(256),
      DVRFD_COURANT_CORRESPONDANT_MAINTIEN_RESEAUX VARCHAR(256),
      DVRFD_COMMENTAIRE_MAINTIEN_RESEAUX VARCHAR(2048),
      DVRFD_IS_MAINTIEN_RESEAUX INT,
      DVRFD_OCCURENCE_TOTAL_DEFAILLANCE_ORGANE_REVEIL_INTEMPESTIF VARCHAR(256),
      DVRFD_COURANT_CORRESPONDANT_REVEIL_INTEMPESTIF VARCHAR(256),
      DVRFD_COMMENTAIRE_REVEIL_INTEMPESTIF VARCHAR(2048),
      DVRFD_IS_REVEIL_INTEMPSIF INT,
      DVRFD_OCCURENCE_TOTAL_DEFAILLANCE_ORGANE_MAINTIEN_ALIMENTAION VARCHAR(256),
      DVRFD_COURANT_CORRESPONDANT_MAINTIEN_ALIMENTAION VARCHAR(256),
      DVRFD_COMMENTAIRE_MAINTIEN_ALIMENTAION VARCHAR(2048),
      DVRFD_IS_MAINTIEN_ALIMENTATION INT,
      DVRFD_OCCURENCE_TOTAL_DEFAILLANCE_ORGANE_REALIMENTATION_ALIMENTATION VARCHAR(256),
      DVRFD_COURANT_CORRESPONDANT_REALIMENTATION_ALIMENTATION VARCHAR(256),
      DVRFD_COMMENTAIRE_REALIMENTATION_ALIMENTATION VARCHAR(2048),
      DVRFD_IS_REALIMENTATION_ALIMENTATION INT,
      DVRFD_EDS_ID VARCHAR (36) NOT NULL,
      primary key (DVRFD_ID)
);

/* ***** FORMULAIRE COMPORTELMENT ROBUSTE ***** */

create table EDS_COMPORTEMENT_ROBUSTE_FORM_DATA
(
      CRFD_ID VARCHAR(36) NOT NULL,
      CRFD_SYNOPTIQUE_FONCTIONNEL_IMAGE VARCHAR(256),
      CRFD_SYNOPTIQUE_FONCTIONNEL_COMMENTAIRE VARCHAR(2048),
      CRFD_SYNOPTIQUE_ELECTRIQUE_IMAGE VARCHAR(256),
      CRFD_SYNOPTIQUE_ELECTRIQUE_COMMENTAIRE VARCHAR(2048),
      CRFD_SYNOPTIQUE_ELECTRIQUE_URLS VARCHAR(256),
      CRFD_SYNOPTIQUE_FONCTIONNEL_URLS VARCHAR(256),
      CRFD_EDS_ID VARCHAR(36) NOT NULL,
      PRIMARY KEY (CRFD_ID)
);

/* ***** FORMULAIRE COMPORTEMENT CONSOLIDE***** */
create table EDS_COMPORTEMENT_CONSOLIDE_FORM_DATA
(
      COCOFD_ID VARCHAR (36) NOT NULL,
      COCOFD_TYPE_PILOTAGE VARCHAR (256),
      COCOFD_PLAGE_FREQUENCE varchar(256),
      COCOFD_MODELE_RLC_IMAGE VARCHAR(256),
      COCOFD_MODELE_RCL_COMMENTAIRE VARCHAR (2048),
      COCOFD_MODELE_SABER_IMAGE VARCHAR (256),
      COCOFD_MODELE_SABER_COMMENTAIRE VARCHAR (2048),
      COCOFD_MODELE_RCL_URLS VARCHAR (256),
      COCOFD_MODELE_SABER_URLS VARCHAR (256),
      COCOFD_EDS_ID VARCHAR (36) NOT NULL,
      PRIMARY KEY (COCOFD_ID)
);


/* ***** FORMULAIRE DE VALIDATION HAUTE ***** */
create table EDS_VALIDATION
(
    V_ID varchar(36) not null,
    V_STATUS int not null,
    V_VALIDATOR_ID varchar(36),
    V_VALIDATION_DATE timestamp,
    V_COMMENT1_ID varchar(36),
    V_COMMENT2 varchar(1024),
    primary key(V_ID)
);

create table EDS_HIGH_VALIDATION_FORM_DATA
(
    HVFD_ID varchar(36) not null,
    HVFD_EDS_ID varchar(36) not null,
    HVFD_RS_PRIMARY_ID varchar(36) not null,
    HVFD_RE_ROBUST_ID varchar(36) not null,
    HVFD_RE_CONSOLIDATE_ID varchar(36) not null,
    HVFD_RE_CLOSED_ID varchar(36) not null,
    HVFD_DBEES_CONSOLIDATE_ID varchar(36) not null,
    HVFD_DBEES_CLOSED_ID varchar(36) not null,
    HVFD_DBEED_CONSOLIDATE_ID varchar(36) not null,
    HVFD_DBEED_CLOSED_ID varchar(36) not null,
    HVFD_CADE_CONSOLIDATE_ID varchar(36) not null,
    HVFD_CADE_CLOSED_ID varchar(36) not null,
    HVFD_SUPPLIER_DATA_ID varchar(36) not null,
    HVFD_STAGE int not null,
    primary key(HVFD_ID),
    unique(HVFD_RS_PRIMARY_ID),
    unique(HVFD_RE_ROBUST_ID),
    unique(HVFD_RE_CONSOLIDATE_ID),
    unique(HVFD_RE_CLOSED_ID),
    unique(HVFD_DBEES_CONSOLIDATE_ID),
    unique(HVFD_DBEES_CLOSED_ID),
    unique(HVFD_DBEED_CONSOLIDATE_ID),
    unique(HVFD_DBEED_CLOSED_ID),
    unique(HVFD_CADE_CONSOLIDATE_ID),
    unique(HVFD_CADE_CLOSED_ID),
    unique(HVFD_SUPPLIER_DATA_ID)
);

/* ***** FORMULAIRE DE VALIDATION BASSE ***** */
create table EDS_LOW_VALIDATION_FORM_DATA
(
    LVFD_ID varchar(36) not null,
    LVFD_EDS_ID varchar(36) not null,
    LVFD_PRELIMINARY_ID varchar(36) not null,
    LVFD_ROBUST_ID varchar(36) not null,
    LVFD_CONSOLIDATED_ID varchar(36) not null,
    LVFD_CLOSED_ID varchar(36) not null,
    LVFD_SUPPLIER_DATA_ID varchar(36) not null,
    LVFD_STAGE int not null,
    primary key(LVFD_ID),
    unique(LVFD_PRELIMINARY_ID),
    unique(LVFD_ROBUST_ID),
    unique(LVFD_CONSOLIDATED_ID),
    unique(LVFD_CLOSED_ID),
    unique(LVFD_SUPPLIER_DATA_ID)
);

/* ***** FORMULAIRE CSE ***** */
create table EDS_CSE_FORM_DATA
(
   CSE_ID varchar(36) not null,
   CSE_EDS_ID varchar(36) not null,
   primary key(CSE_ID)
);

create table EDS_CSE_LINE
(
    CSEL_ID varchar(36) not null,
    CSEL_CSE_ID varchar(36) not null,
    CSEL_NUMBER int not null,
    CSEL_DATANAME varchar(128) not null,
    CSEL_LOWER_BOUND real,
    CSEL_UPPER_BOUND real,
    CSEL_UNIT varchar(32),
    CSEL_COMMENT varchar(256),
    primary key(CSEL_ID)
);


/* ***** FORMULAIRE PILOTE DES DERIVES ***** */

create table EDS_DRIFT_INFO
(
	DI_ID varchar(36) not null,
        DI_EDS_ID varchar(36) not null,
        DI_DATA_TYPE integer not null,
	DI_ALIM_NAME varchar(36) not null,
	DI_REF_STAGE integer not null,
	DI_REF_VALUE real,
	DI_TARGET_STAGE integer not null,
	DI_TARGET_VALUE real,
        primary key( DI_ID )
);

/* ***** FORMULAIRE ... ***** */

create table EDS_ATTACHMENT_FORM_DATA
(
    AFD_ID varchar(36) not null,
    AFD_EDS_ID varchar(36) not null,
    primary key (AFD_ID)
);

create table EDS_ATTACHMENT
(
    A_ID varchar(36) not null,
    A_AFD_ID varchar(36) not null,
    A_LINK varchar(512) not null,
    A_COMMENT varchar(1024),
    primary key (A_ID)
);


/* ===== FIN DE DECLARATION DES FORMULAIRES ===== */

create table EDS_SUPPLIER
(
	S_ID varchar(36) not null,
	S_NAME varchar(1024) not null,
        S_ACTIVE int not null,
	primary key (S_ID),
        unique (S_NAME)
);

create table EDS_PERIMETER
(
	PE_ID varchar(36) not null,
	PE_NAME varchar(1024) not null,
        PE_ACTIVE int not null,
	primary key (PE_ID),
        unique (PE_NAME)
);

create table EDS_EDS_PERIMETER
(
	EDSPE_EDS_ID varchar(36) not null,
	EDSPE_PE_ID varchar(36) not null,
	primary key (EDSPE_EDS_ID, EDSPE_PE_ID)
);

create table EDS_PROJECT_PERIMETER
(
	PPE_P_ID varchar(36) not null,
	PPE_PE_ID varchar(36) not null,
	primary key (PPE_P_ID, PPE_PE_ID)
);

create table EDS_USER
(
	U_ID varchar(36) not null,
	U_S_ID varchar(36),	/* si c'est un utilisateur fournisseur */
	U_PE_ID varchar(36),	/* p�rim�tre si c'est un utilisateur partenaire */
	U_RO_ID varchar(36),
	U_PSA_ID varchar(32) not null,
	U_FIRSTNAME varchar(128) not null,
	U_LASTNAME varchar(128) not null,
	U_SERVICE varchar(256),
        U_CONF clob(16384),
        U_ACTIVE int not null,
	primary key (U_ID),
        unique (U_PSA_ID)
);

create table EDS_ROLE
(
	RO_ID varchar(36) not null,
	RO_NAME varchar(1024) not null,
        RO_ACTIVE int not null,
        RO_TYPE int,
	RO_RIGHTS clob(16384),
        RO_FORM_RIGHTS clob(16384),
	primary key (RO_ID),
        unique (RO_NAME)
);

create table EDS_WORDING
(
	W_ID varchar(36) not null,
	W_VALUE varchar(1024) not null,
	W_TYPE varchar(36) not null,
	W_INDEX int not null,
	primary key (W_ID)
);

create table EDS_SUBSCRIPTION
(
        SUB_ID varchar(36) not null,
        SUB_U_ID varchar(36) not null,
        SUB_EDS_REF varchar(32) not null,
        primary key (SUB_ID),
        unique (SUB_U_ID, SUB_EDS_REF)
);

create table EDS_WS_SESSION_TOKEN
(
        WST_ID varchar(36) not null,
        WST_EXPIRATION_DATE timestamp not null,
        WST_REMOTE_ADDRESS varchar(45) not null,
        WST_U_ID varchar(36),
        primary key (WST_ID)
);

alter table EDS_USER add constraint U_RO_ID_FK foreign key (U_RO_ID) references EDS_ROLE(RO_ID);
alter table EDS_USER add constraint U_PE_ID_FK foreign key (U_PE_ID) references EDS_PERIMETER(PE_ID);
alter table EDS_USER add constraint U_S_ID_FK foreign key (U_S_ID) references EDS_SUPPLIER(S_ID);
alter table EDS_EDS_PERIMETER add constraint EDSPE_EDS_ID_FK foreign key (EDSPE_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_EDS_PERIMETER add constraint EDSPE_PE_ID_FK foreign key (EDSPE_PE_ID) references EDS_PERIMETER(PE_ID);
alter table EDS_PROJECT_PERIMETER add constraint PPE_P_ID_FK foreign key (PPE_P_ID) references EDS_PROJECT(P_ID);
alter table EDS_PROJECT_PERIMETER add constraint PPE_PE_ID_FK foreign key (PPE_PE_ID) references EDS_PERIMETER(PE_ID);

/* FOMRULAIRES  ADD CONSTRAINT*/
alter table EDS_PRIMARY_CURENT add constraint PCEDS_EDS_ID_FK foreign key (PCEDS_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_PROJECT_PRIMARY_CURENT add constraint PPC_P_ID_FK foreign key (PPC_P_ID) references EDS_PROJECT(P_ID);
alter table EDS_PROJECT_PRIMARY_CURENT add constraint PPC_PCEDS_ID_FK foreign key (PPC_PCEDS_ID) references EDS_PRIMARY_CURENT(PCEDS_ID);
alter table EDS_SUPPLY add constraint S_PCEDS_ID_FK foreign key (S_PCEDS_ID) references EDS_PRIMARY_CURENT(PCEDS_ID);
alter table EDS_SUPPLY add constraint S_PSEDS_ID_FK foreign key (S_PSEDS_ID) references EDS_PRIMARY_SUPPLY(PSEDS_ID);
alter table EDS_SUPPLY add constraint SEDS_EDS_ID_FK foreign key (SEDS_EDS_ID) references EDS_EDS(EDS_ID);
---------------------------------------------------------------------------------------------------------------------------------------------------
/* FIN Exemple que j'ai effectuer en ajoutant la table primaty key*/
alter table EDS_CONSOLIDATED_SUPPLY_VOLTAGE_FORM_DATA add constraint CSV_EDS_ID_FK foreign key (CSV_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_CONSOLIDATED_SUPPLY_VOLTAGE_FORM_DATA add constraint CSV_ORIGINE_TENSION_NISIALISATION_FK foreign key (CSV_ORIGINE_TENSION_NISIALISATION) references EDS_WORDING(W_ID);
alter table EDS_CONSOLIDATED_SUPPLY_VOLTAGE_FORM_DATA add constraint CSV_ORIGINE_TENSION_MINIMALE_FONCTIONNEMENT_NOMINALE_FK foreign key ( CSV_ORIGINE_TENSION_MINIMALE_FONCTIONNEMENT_NOMINALE)references EDS_WORDING(W_ID);
alter table EDS_CONSOLIDATED_SUPPLY_VOLTAGE_FORM_DATA add constraint CSV_ORIGINE_TENSION_MAXIMAL_FONCTIONNEMENT_NOMINALE_FK foreign key (CSV_ORIGINE_TENSION_MAXIMAL_FONCTIONNEMENT_NOMINALE) references EDS_WORDING(W_ID);
alter table EDS_CONSOLIDATED_SUPPLY_VOLTAGE_FORM_DATA add constraint CSV_ORIGINE_TENSION_COUPURE_FK foreign key ( CSV_ORIGINE_TENSION_COUPURE) references EDS_WORDING(W_ID);
alter table EDS_CONSOLIDATED_SUPPLY_VOLTAGE_FORM_DATA add constraint CSV_ORIINE_TENSION_REHABILISATION_FK foreign key (CSV_ORIINE_TENSION_REHABILISATION)references EDS_WORDING(W_ID);
alter table EDS_CONSOLIDATED_SUPPLY_VOLTAGE_FORM_DATA add constraint CSV_ORIGINE_TENSION_RESET_FK foreign key (CSV_ORIGINE_TENSION_RESET) references EDS_WORDING(W_ID);
alter table EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA add constraint PSV_EDS_ID_FK foreign key (PSV_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA add constraint PSV_ORIGINE_DONNEE_TENSION_MINIMALE_FONCTIONNEMENT_INITIALISATION_FK foreign key (PSV_ORIGINE_DONNEE_TENSION_MINIMALE_FONCTIONNEMENT_INITIALISATION)references EDS_WORDING(W_ID);
alter table EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA add constraint PSV_ORIGINE_DONNEE_TENSION_MINIMALE_FONCTIONNEMENT_NOMINALE_FK foreign key (PSV_ORIGINE_DONNEE_TENSION_MINIMALE_FONCTIONNEMENT_NOMINALE)references EDS_WORDING(W_ID);
alter table EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA add constraint PSV_ORIGINE_DONNEE_TENSION_MAXIMALE_FONCTIONNEMENT_NOMINALE_FK foreign key (PSV_ORIGINE_DONNEE_TENSION_MAXIMALE_FONCTIONNEMENT_NOMINALE)references EDS_WORDING(W_ID);
alter table EDS_PRELIMINARY_SUPPLY_VOLTAGE_FORM_DATA add constraint PSV_ORIGINE_DONNEE_TENSION_MAXIMALE_FONCTIONNEMENT_REHABILISATION_FK foreign key (PSV_ORIGINE_DONNEE_TENSION_MAXIMALE_FONCTIONNEMENT_REHABILISATION) references EDS_WORDING(W_ID); 
alter table EDS_MISSION_ACTIVATION_FORM_DATA add constraint MAFD_ESD_ID_FK foreign key (MAFD_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_MISSION_PROFIL_FORM_DATA add constraint MPFD_EDS_ID_FK foreign key ( MPFD_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_MISSION_PROFIL add constraint MP_MPFD_ID_FK foreign key ( MP_MPFD_ID) references EDS_MISSION_PROFIL_FORM_DATA(MPFD_ID);
alter table EDS_DEFAILLANCE_VEILLE_REVEIL_FORM_DATA add constraint DVRFD_EDS_ID_FK foreign key ( DVRFD_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_COMPORTEMENT_ROBUSTE_FORM_DATA add constraint CRFD_EDS_ID_FK foreign key ( CRFD_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_COMPORTEMENT_CONSOLIDE_FORM_DATA add constraint COCOFD_EDS_ID_FK foreign key (COCOFD_EDS_ID)references EDS_EDS(EDS_ID);


/* FIN FORMULAIRE  ADD CONSTRAINT*/
alter table EDS_PROJECT_EDS add constraint PEDS_P_ID_FK foreign key (PEDS_P_ID) references EDS_PROJECT(P_ID);
alter table EDS_PROJECT_EDS add constraint PEDS_EDS_ID_FK foreign key (PEDS_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_EDS_NUMBER_96K add constraint EDSN_EDS_ID_FK foreign key (EDSN_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_EDS_NUMBER_96K add constraint EDSN_N_ID_FK foreign key (EDSN_N_ID) references EDS_NUMBER_96K(N_ID);
alter table EDS_EDS add constraint EDS_P_ID_FK foreign key (EDS_P_ID) references EDS_PROJECT(P_ID);
alter table EDS_EDS add constraint EDS_CT_ID_FK foreign key (EDS_CT_ID) references EDS_COMPONENT_TYPE(CT_ID);
alter table EDS_EDS add constraint EDS_S_ID_FK foreign key (EDS_S_ID) references EDS_SUPPLIER(S_ID);
alter table EDS_EDS add constraint EDS_W_ID_FK foreign key (EDS_W_ID) references EDS_WORDING(W_ID);
alter table EDS_EDS add constraint EDS_ADMIN_ID_FK foreign key (EDS_ADMIN_ID) references EDS_USER(U_ID);
alter table EDS_EDS add constraint EDS_MANAGER_ID_FK foreign key (EDS_MANAGER_ID) references EDS_USER(U_ID);
alter table EDS_EDS add constraint EDS_OFFICER_ID_FK foreign key (EDS_OFFICER_ID) references EDS_USER(U_ID);
alter table EDS_EDS add constraint EDS_MODIF_USER_ID_FK foreign key (EDS_MODIF_USER_ID) references EDS_USER(U_ID);
alter table EDS_EDS add constraint EDS_CREA_USER_ID_FK foreign key (EDS_CREA_USER_ID) references EDS_USER(U_ID);
alter table EDS_EDS add constraint EDS_DRIFT_VALIDATOR_ID_FK foreign key (EDS_DRIFT_VALIDATOR_ID) references EDS_USER(U_ID);
alter table EDS_PROJECT add constraint P_W_ID_FIRST_STAGE_FK foreign key (P_W_ID_FIRST_STAGE) references EDS_WORDING(W_ID);
alter table EDS_PROJECT add constraint P_W_ID_SECOND_STAGE_FK foreign key (P_W_ID_SECOND_STAGE) references EDS_WORDING(W_ID);
alter table EDS_PROJECT add constraint P_W_ID_THIRD_STAGE_FK foreign key (P_W_ID_THIRD_STAGE) references EDS_WORDING(W_ID);
alter table EDS_PROJECT add constraint P_W_ID_FOURTH_STAGE_FK foreign key (P_W_ID_FOURTH_STAGE) references EDS_WORDING(W_ID);
alter table EDS_COMPONENT_TYPE add constraint CT_NAME_FK foreign key (CT_NAME) references EDS_WORDING(W_ID);
alter table EDS_SUBSCRIPTION add constraint SUB_U_ID_FK foreign key (SUB_U_ID) references EDS_USER(U_ID);
alter table EDS_WS_SESSION_TOKEN add constraint WST_U_ID_FK foreign key (WST_U_ID) references EDS_USER(U_ID);
--Constraint robust
alter table EDS_ROBUST_CURENT_FORM_DATA add constraint RCEDS_EDS_ID_FK foreign key (RCEDS_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_PROJECT_ROBUST_CURENT add constraint PR_P_ID_FK foreign key (PRC_P_ID) references EDS_PROJECT(P_ID);
alter table EDS_PROJECT_ROBUST_CURENT add constraint PRC_PCEDS_ID_FK foreign key (PRC_RCEDS_ID) references EDS_ROBUST_CURENT_FORM_DATA(RCEDS_ID);
alter table EDS_SUPPLY add constraint S_RCEDS_ID_FK foreign key (S_RCEDS_ID) references EDS_ROBUST_CURENT_FORM_DATA(RCEDS_ID);
alter table EDS_SUPPLY add constraint S_RSEDS_ID_FK foreign key (S_RSEDS_ID) references EDS_ROBUST_SUPPLY(RSEDS_ID);
alter table EDS_GROUND add constraint G_GRCEDS_ID_FK foreign key (G_GRCEDS_ID) references EDS_GROUND_ROBUST_CURENT(GRC_ID);
alter table EDS_GROUND add constraint G_RCEDS_ID_FK foreign key (G_RCEDS_ID) references EDS_ROBUST_CURENT_FORM_DATA(RCEDS_ID);

--end Constraint robust

--COURANT NOMINALE A L'ACTIVATION
alter table EDS_COURANT_NOMINALE_ACTIVATION add constraint EDS_COURANT_NOMINALE_ACTIVATION_P_ID_FK foreign key (CNAEDS_P_ID) references EDS_PROJECT(P_ID);

/*CONTRAINTE COURANT CONSOLIDER*/
alter table EDS_CONSOLIDATE_CURENT_FORM_DATA add constraint CCEDS_EDS_ID_FK foreign key (CCEDS_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_PROJECT_CONSOLIDATE_CURENT add constraint PC_P_ID_FK foreign key (PCC_P_ID) references EDS_PROJECT(P_ID);
alter table EDS_PROJECT_CONSOLIDATE_CURENT add constraint PCC_CCEDS_ID_FK foreign key (PCC_CCEDS_ID) references EDS_CONSOLIDATE_CURENT_FORM_DATA(CCEDS_ID);

alter table EDS_SUPPLY add constraint S_CCEDS_ID_FK foreign key (S_CCEDS_ID) references EDS_CONSOLIDATE_CURENT_FORM_DATA(CCEDS_ID);
alter table EDS_SUPPLY add constraint S_CSEDS_ID_FK foreign key (S_CSEDS_ID) references EDS_CONSOLIDATE_SUPPLY(CSEDS_ID);

alter table EDS_CONSOLIDATE_SUPPLY add constraint CSEDS_CSTEDS_ID_FK foreign key (CSEDS_CST_ID) references EDS_CONSOLIDATE_SUPPLY_THEORITIC(CSTEDS_ID);
-- RESEAU VEILLE OU REVEILLE ORGANE INACTIF
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_RVROI add constraint JCST_RVROI_RVROIEDS_ID_FK foreign key (JCSTRVROIEDS_ID) references EDS_RESEAU_VEILLE_REVEILLE_ORGANE_INACTIF(RVROIEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_RVROI add constraint JCST_RVROI_CST_ID_FK foreign key (JCSTRVROIEDS_RVROI_ID) references EDS_CONSOLIDATE_SUPPLY_THEORITIC(CSTEDS_ID);
--MODE PARC
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_MODE_PARC add constraint JCST_MODE_PARC_MP_ID_FK foreign key (JCSTMPEDS_MP_ID) references EDS_MODE_PARC(MPEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_MODE_PARC add constraint JCST_MODE_PARC_CST_ID_FK foreign key ( JCSTMPEDS_ID) references EDS_CONSOLIDATE_SUPPLY_THEORITIC(CSTEDS_ID);
--MODE MONTAGE
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_MODE_MONTAGE add constraint JCST_MODE_MONTAGE_MM_ID_FK foreign key (JCSTMMEDS_MM_ID) references EDS_MODE_MONTAGE(MMEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_MODE_MONTAGE add constraint JCST_MODE_MONTAGE_CST_ID_FK foreign key ( JCSTMMEDS_ID) references EDS_CONSOLIDATE_SUPPLY_THEORITIC(CSTEDS_ID);

--COURANT DE MISE SOUS TENSION
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_COURANT_MISE_SOUS_TENSION add constraint JCST_COURANT_MISE_SOUS_TENSION_CMST_ID_FK foreign key (JCSTCMSTEDS_CMST_ID) references EDS_COURANT_MISE_SOUS_TENSION(CMSTEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_COURANT_MISE_SOUS_TENSION add constraint JCST_COURANT_MISE_SOUS_TENSION_CST_ID_FK foreign key ( JCSTCMSTEDS_ID ) references EDS_CONSOLIDATE_SUPPLY_THEORITIC(CSTEDS_ID);

-- COURANT D'APPELLE A L'ACTIVATION
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CAA add constraint JCST_COURANT_APPELLE_ACTIVATION_CAA_ID_FK foreign key (JCSTCAAEDS_CAA_ID) references EDS_COURANT_APPELLE_ACTIVATION(CAAEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CAA add constraint JCST_COURANT_APPELLE_ACTIVATION_CST_ID_FK foreign key (JCSTCAAEDS_ID) references EDS_CONSOLIDATE_SUPPLY_THEORITIC(CSTEDS_ID);

--COURANT NOMINALE A L'ACTIVATION
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CNA add constraint JCST_EDS_COURANT_NOMINALE_ACTIVATION_CNA_ID_FK foreign key (JCSTCNAEDS_CNA_ID) references EDS_COURANT_NOMINALE_ACTIVATION(CNAEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CNA add constraint JCST_EDS_COURANT_NOMINALE_ACTIVATION_CST_ID_FK foreign key (JCSTCNAEDS_ID) references EDS_CONSOLIDATE_SUPPLY_THEORITIC(CSTEDS_ID);

--COURANT BLOQUE COURANT DEFAILLANCE
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CBCD add constraint JCST_EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT_ID_FK foreign key (JCSTCBCDEDS_CBCD_ID) references EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT(CBCDEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_THEORITIC_CBCD add constraint JCST_EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT_CST_ID_FK foreign key (JCSTCBCDEDS_ID) references EDS_CONSOLIDATE_SUPPLY_THEORITIC(CSTEDS_ID);

/*FIN CONTRAINTE COURANT CONSOLIDER*/

/*CONTRAINTE COURANT CONSOLIDER PARTIE MESURER*/

alter table EDS_CONSOLIDATE_SUPPLY add constraint CSMEDS_CSMEDS_ID_FK foreign key (CSMEDS_CSM_ID) references EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID);
-- RESEAU VEILLE OU REVEILLE ORGANE INACTIF
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_RVROI add constraint JCSM_RVROI_RVROIEDS_ID_FK foreign key (JCSMRVROIEDS_ID) references EDS_RESEAU_VEILLE_REVEILLE_ORGANE_INACTIF(RVROIEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_RVROI add constraint JCSM_RVROI_CSM_ID_FK foreign key (JCSMRVROIEDS_RVROI_ID) references EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID);
--MODE PARC
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_MODE_PARC add constraint JCSM_MODE_PARC_MP_ID_FK foreign key (JCSMMPEDS_MP_ID) references EDS_MODE_PARC(MPEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_MODE_PARC add constraint JCSM_MODE_PARC_CSM_ID_FK foreign key ( JCSMMPEDS_ID) references EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID);
--MODE MONTAGE
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_MODE_MONTAGE add constraint JCSM_MODE_MONTAGE_MM_ID_FK foreign key (JCSMMMEDS_MM_ID) references EDS_MODE_MONTAGE(MMEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_MODE_MONTAGE add constraint JCSM_MODE_MONTAGE_CSM_ID_FK foreign key ( JCSMMMEDS_ID) references EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID);

--COURANT DE MISE SOUS TENSION
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_COURANT_MISE_SOUS_TENSION add constraint JCSM_COURANT_MISE_SOUS_TENSION_CMST_ID_FK foreign key (JCSMCMSTEDS_CMST_ID) references EDS_COURANT_MISE_SOUS_TENSION(CMSTEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_COURANT_MISE_SOUS_TENSION add constraint JCSM_COURANT_MISE_SOUS_TENSION_CSM_ID_FK foreign key ( JCSMCMSTEDS_ID ) references EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID);

-- COURANT D'APPELLE A L'ACTIVATION
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CAA add constraint JCSM_COURANT_APPELLE_ACTIVATION_CAA_ID_FK foreign key (JCSMCAAEDS_CAA_ID) references  EDS_COURANT_APPELLE_ACTIVATION(CAAEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CAA add constraint JCSM_COURANT_APPELLE_ACTIVATION_CSM_ID_FK foreign key (JCSMCAAEDS_ID) references EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID);

--COURANT NOMINALE A L'ACTIVATION
alter table  EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CNA add constraint JCSM_EDS_COURANT_NOMINALE_ACTIVATION_MESURE_CNA_ID_FK foreign key (JCSMCNAEDS_CNA_ID) references EDS_COURANT_NOMINALE_ACTIVATION(CNAEDS_ID);
alter table  EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CNA add constraint JCSM_EDS_COURANT_NOMINALE_ACTIVATION_MESURE_CSM_ID_FK foreign key (JCSMCNAEDS_ID) references EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID);

--COURANT BLOQUE COURANT DEFAILLANCE
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CBCD add constraint JCSM_EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT_MESURE_ID_FK foreign key (JCSMCBCDEDS_CBCD_ID) references EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT(CBCDEDS_ID);
alter table EDS_JOIN_CONSOLIDATE_SUPPLY_MESURE_CBCD add constraint JCSM_EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT_MESURE_CSM_ID_FK foreign key (JCSMCBCDEDS_ID) references EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID);

/*FIN CONTRAINTE COURANT CONSOLIDER PARTIE MESURER*/

-- Contraintes relevé de courant
alter table EDS_CURRENT_STATEMENT add constraint EDS_CURRENT_STATEMENT_MAFD_ID_FK foreign key ( CS_MAFD_ID ) references EDS_MISSION_ACTIVATION_FORM_DATA(MAFD_ID);
alter table EDS_CURRENT_STATEMENT add constraint EDS_CURRENT_STATEMENT_SEDS_ID_FK foreign key ( CS_SEDS_ID ) references EDS_SUPPLY(SEDS_ID);
alter table EDS_CURRENT_STATEMENT add constraint EDS_CURRENT_STATEMENT_USER_ID_FK foreign key ( CS_USER_ID ) references EDS_USER(U_ID);
/* VALIDATION HAUTE */
alter table EDS_VALIDATION add constraint V_VALIDATOR_ID_FK foreign key (V_VALIDATOR_ID) references EDS_USER(U_ID);
alter table EDS_VALIDATION add constraint V_COMMENT1_ID_FK foreign key (V_COMMENT1_ID) references EDS_WORDING(W_ID);
/*CONTRAINTE COURANT MESURE PSA*/

alter table EDS_SUPPLY add constraint S_PMSEDS_ID_FK foreign key (S_PMSEDS_ID) references EDS_PSA_MESURE_SUPPLY(PMSEDS_ID);

-- RESEAU VEILLE OU REVEILLE ORGANE INACTIF
alter table EDS_JOIN_PSA_MESURE_SUPPLY_RVROI add constraint JPMST_RVROI_RVROIEDS_ID_FK foreign key (JPMSRVROIEDS_ID) references EDS_RESEAU_VEILLE_REVEILLE_ORGANE_INACTIF(RVROIEDS_ID);
alter table EDS_JOIN_PSA_MESURE_SUPPLY_RVROI add constraint JPMST_RVROI_PMST_ID_FK foreign key (JPMSRVROIEDS_RVROI_ID) references EDS_PSA_MESURE_SUPPLY(PMSEDS_ID);
--MODE PARC
alter table EDS_JOIN_PSA_MESURE_SUPPLY_MODE_PARC add constraint JPMST_MODE_PARC_MP_ID_FK foreign key (JPMSMPEDS_MP_ID) references EDS_MODE_PARC(MPEDS_ID);
alter table EDS_JOIN_PSA_MESURE_SUPPLY_MODE_PARC add constraint JPMST_MODE_PARC_PMST_ID_FK foreign key ( JPMSMPEDS_ID) references EDS_PSA_MESURE_SUPPLY(PMSEDS_ID);
--MODE MONTAGE
alter table EDS_JOIN_PSA_MESURE_SUPPLY_MODE_MONTAGE add constraint JPMST_MODE_MONTAGE_MM_ID_FK foreign key (JPMSMMEDS_MM_ID) references EDS_MODE_MONTAGE(MMEDS_ID);
alter table EDS_JOIN_PSA_MESURE_SUPPLY_MODE_MONTAGE add constraint JPMST_MODE_MONTAGE_PMST_ID_FK foreign key ( JPMSMMEDS_ID) references EDS_PSA_MESURE_SUPPLY(PMSEDS_ID);
--COURANT DE MISE SOUS TENSION
alter table EDS_JOIN_PSA_MESURE_SUPPLY_COURANT_MISE_SOUS_TENSION add constraint JPMST_COURANT_MISE_SOUS_TENSION_CMST_ID_FK foreign key (JPMSCMSTEDS_CMST_ID) references EDS_COURANT_MISE_SOUS_TENSION(CMSTEDS_ID);
alter table EDS_JOIN_PSA_MESURE_SUPPLY_COURANT_MISE_SOUS_TENSION add constraint JPMST_COURANT_MISE_SOUS_TENSION_PMST_ID_FK foreign key ( JPMSCMSTEDS_ID ) references EDS_PSA_MESURE_SUPPLY(PMSEDS_ID);
-- COURANT D'APPELLE A L'ACTIVATION
alter table EDS_JOIN_PSA_MESURE_SUPPLY_CAA add constraint JPMST_COURANT_APPELLE_ACTIVATION_CAA_ID_FK foreign key (JPMSCAAEDS_CAA_ID) references EDS_COURANT_APPELLE_ACTIVATION(CAAEDS_ID);
alter table EDS_JOIN_PSA_MESURE_SUPPLY_CAA add constraint JPMST_COURANT_APPELLE_ACTIVATION_PMST_ID_FK foreign key (JPMSCAAEDS_ID) references EDS_PSA_MESURE_SUPPLY(PMSEDS_ID);
--COURANT NOMINALE A L'ACTIVATION
alter table EDS_JOIN_PSA_MESURE_SUPPLY_CNA add constraint JPMST_EDS_COURANT_NOMINALE_ACTIVATION_CNA_ID_FK foreign key (JPMSCNAEDS_CNA_ID) references EDS_COURANT_NOMINALE_ACTIVATION(CNAEDS_ID);
alter table EDS_JOIN_PSA_MESURE_SUPPLY_CNA add constraint JPMST_EDS_COURANT_NOMINALE_ACTIVATION_PMST_ID_FK foreign key (JPMSCNAEDS_ID) references EDS_PSA_MESURE_SUPPLY(PMSEDS_ID);

--COURANT BLOQUE COURANT DEFAILLANCE
alter table EDS_JOIN_PSA_MESURE_SUPPLY_CBCD add constraint JPMST_EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT_ID_FK foreign key (JPMSCBCDEDS_CBCD_ID) references EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT(CBCDEDS_ID);
alter table EDS_JOIN_PSA_MESURE_SUPPLY_CBCD add constraint JPMST_EDS_COURANT_BLOQUE_COURANT_DYSFONCTIONNEMENT_PMST_ID_FK foreign key (JPMSCBCDEDS_ID) references EDS_PSA_MESURE_SUPPLY(PMSEDS_ID);

/*FIN CONTRAINTE COURANT MESURE PSA*/

alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_EDS_ID_FK foreign key (HVFD_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_RS_PRIMARY_ID_FK foreign key (HVFD_RS_PRIMARY_ID) references EDS_VALIDATION(V_ID);
alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_RE_ROBUST_ID_FK foreign key (HVFD_RE_ROBUST_ID) references EDS_VALIDATION(V_ID);
alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_RE_CONSOLIDATE_ID_FK foreign key (HVFD_RE_CONSOLIDATE_ID) references EDS_VALIDATION(V_ID);
alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_RE_CLOSED_ID_FK foreign key (HVFD_RE_CLOSED_ID) references EDS_VALIDATION(V_ID);
alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_DBEES_CONSOLIDATE_ID_FK foreign key (HVFD_DBEES_CONSOLIDATE_ID) references EDS_VALIDATION(V_ID);
alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_DBEES_CLOSED_ID_FK foreign key (HVFD_DBEES_CLOSED_ID) references EDS_VALIDATION(V_ID);
alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_DBEED_CONSOLIDATE_ID_FK foreign key (HVFD_DBEED_CONSOLIDATE_ID) references EDS_VALIDATION(V_ID);
alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_DBEED_CLOSED_ID_FK foreign key (HVFD_DBEED_CLOSED_ID) references EDS_VALIDATION(V_ID);
alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_CADE_CONSOLIDATE_ID_FK foreign key (HVFD_CADE_CONSOLIDATE_ID) references EDS_VALIDATION(V_ID);
alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_CADE_CLOSED_ID_FK foreign key (HVFD_CADE_CLOSED_ID) references EDS_VALIDATION(V_ID);
alter table EDS_HIGH_VALIDATION_FORM_DATA add constraint HVFD_SUPPLIER_DATA_ID_FK foreign key (HVFD_SUPPLIER_DATA_ID) references EDS_VALIDATION(V_ID);

/* VALIDATION BASSE */
alter table EDS_LOW_VALIDATION_FORM_DATA add constraint LVFD_EDS_ID_FK foreign key (LVFD_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_LOW_VALIDATION_FORM_DATA add constraint LVFD_PRELIMINARY_ID_FK foreign key (LVFD_PRELIMINARY_ID) references EDS_VALIDATION(V_ID);
alter table EDS_LOW_VALIDATION_FORM_DATA add constraint LVFD_ROBUST_ID_FK foreign key (LVFD_ROBUST_ID) references EDS_VALIDATION(V_ID);
alter table EDS_LOW_VALIDATION_FORM_DATA add constraint LVFD_CONSOLIDATED_ID_FK foreign key (LVFD_CONSOLIDATED_ID) references EDS_VALIDATION(V_ID);
alter table EDS_LOW_VALIDATION_FORM_DATA add constraint LVFD_CLOSED_ID_FK foreign key (LVFD_CLOSED_ID) references EDS_VALIDATION(V_ID);
alter table EDS_LOW_VALIDATION_FORM_DATA add constraint LVFD_SUPPLIER_DATA_ID_FK foreign key (LVFD_SUPPLIER_DATA_ID) references EDS_VALIDATION(V_ID);

/* CSE */
alter table EDS_CSE_FORM_DATA add constraint CSE_EDS_ID_FK foreign key (CSE_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_CSE_LINE add constraint CSEL_CSE_ID_FK foreign key (CSEL_CSE_ID) references EDS_CSE_FORM_DATA(CSE_ID);

/* PILOTE DERIVES */

alter table EDS_DRIFT_INFO add constraint DI_EDS_ID_FK foreign key (DI_EDS_ID) references EDS_EDS(EDS_ID);

/* ATTACHMENT */
alter table EDS_ATTACHMENT_FORM_DATA add constraint AFD_EDS_ID_FK foreign key (AFD_EDS_ID) references EDS_EDS(EDS_ID);
alter table EDS_ATTACHMENT add constraint A_AFD_ID_FK foreign key (A_AFD_ID) references EDS_ATTACHMENT_FORM_DATA(AFD_ID);

/* OPTIMISATION */
create index X_EDS_VERSION on EDS_EDS (EDS_REF, EDS_STATE, EDS_MAJOR_VERSION desc, EDS_MINOR_VERSION desc);
create index X_USER_PSA_ACTIVE on EDS_USER (U_PSA_ID, U_ACTIVE desc);
create index X_USER_SUBSCRIPTIONS on EDS_SUBSCRIPTION (SUB_U_ID, SUB_EDS_REF);
create index X_WORDING_TYPES on EDS_WORDING (W_ID, W_TYPE);

/* INITIALISATION */
insert into EDS_ROLE values
(
	'59abd4b0-ed1e-11e0-be50-0800200c9aaa',
	'Administrateur',
        1,
        null,
	'app-dashboard-menu-abort-eds;app-eds-menu-export-eds;app-eds-menu-export-tab-view;app-project-menu-subscribe-eds;app-eds-freeze-eds;app-eds-menu-export-eds;app-eds-menu-export-tab-view;app-project-menu-subscribe-eds;app-eds-modify-setter-project;app-eds-reconduct-eds-with-modif;app-eds-reconduct-eds-without-modif;app-eds-reconsult-eds;app-eds-remove-follower-project;app-eds-see-primary-organs;app-eds-see-secondary-organs;app-eds-validate-drift;app-eds-validate-high-level-1;app-eds-validate-high-level-2;app-eds-validate-high-level-3;app-eds-validate-high-level-4;app-eds-validate-high-level-5;app-eds-validate-low-level;app-eds-validate-supplier-data;app-general-access-admin;app-general-access-dashboard;app-general-access-eds;app-general-access-project;app-project-menu-abort-eds;app-project-menu-export-eds;app-project-menu-export-simulation;app-eds-menu-export-tab-view;app-project-menu-new-eds;app-project-menu-recover-eds;app-project-menu-subscribe-eds;app-eds-subscribe-eds;',
        'form-read-activation-profile;form-read-consolidated-behavior;form-read-consolidated-current-consumption;form-read-consolidated-supply-voltage;form-read-drift-driver-current-consumption;form-read-generic-data;form-read-measured-current-consumption;form-read-mission-profile;form-read-preliminary-current-consumption;form-read-preliminary-supply-voltage;form-read-robust-behavior;form-read-robust-current-consumption;form-read-standby-wakeup-failure;form-write-activation-profile;form-write-consolidated-behavior;form-write-consolidated-current-consumption;form-write-consolidated-supply-voltage;form-write-drift-driver-current-consumption;form-write-generic-data;form-write-measured-current-consumption;form-write-mission-profile;form-write-preliminary-current-consumption;form-write-preliminary-supply-voltage;form-write-robust-behavior;form-write-robust-current-consumption;form-write-standby-wakeup-failure;form-write-high-validation;form-read-high-validation;form-write-cse;form-read-cse;form-read-attachments;form-write-attachments'
);
insert into EDS_ROLE values
(
	'096ea676-1589-44a7-bb85-095691ccf39d',
	'Fournisseur',
        1,
        1,
	'',
        'form-read-generic-data;form-read-attachments;'
);
insert into EDS_ROLE values
(
	'2e4f182a-a489-4dec-9609-67097ba05952',
	'Consultant',
        1,
        null,
	'',
        'form-read-generic-data;form-read-attachments;'
);
insert into EDS_ROLE values
(
	'b9ee3150-6f43-42b0-b209-44ba71a97181',
	'Partenaire',
        1,
        2,
	'',
        'form-read-generic-data;form-read-attachments;'
);
insert into EDS_ROLE values
(
	'242d3233-a1e0-493f-b4e2-99f290f53542',
	'Conception Architecture',
        1,
        null,
	'',
        'form-read-generic-data;form-read-attachments;'
);
insert into EDS_ROLE values
(
	'c78a7ae8-9b65-442a-b850-9e69eab91091',
	'Responsable Architecture',
        1,
        null,
	'',
        'form-read-generic-data;form-read-attachments;'
);

-- Fournisseurs
INSERT INTO PUBLIC.EDS_SUPPLIER(S_ID, S_NAME, S_ACTIVE) VALUES
('e97a8f4f-22a9-4219-b3d0-b9bca39674ab', 'HELLA', 1),
('4210d50c-a4ad-407f-bfde-af54b69f17da', 'VALEO', 1),
('cbbe3c6f-91d8-46c9-982c-867b43a309bb', 'CONTINENTAL', 1),
('2ad59602-7e5a-4e23-8d01-6ddbdff6cad5', 'AUTOLIV', 1),
('a6a60748-0123-420d-96ac-b838a605b762', 'VISTEON', 1),
('fb8b9edd-be58-4b71-9cb0-c3e52653e488', 'FAURECIA', 1),
('4ab8efa6-b88a-4822-a11e-84dddcff41a9', 'YAZAKI', 1),
('54395d34-22a2-47f0-bcaa-fa2b4f6ffb06', 'MGI COUTIER', 1),
('c0fcea92-0b7c-4668-9b3f-11dd0723d073', 'DELPHI', 1),
('3107bc52-b403-4a79-8c80-9640c78ce4b5', 'BOSCH', 1); 

-- Partenaires
INSERT INTO PUBLIC.EDS_PERIMETER(PE_ID, PE_NAME, PE_ACTIVE) VALUES
('10c4cc53-6266-44f3-a473-0cac18fada55', 'Joint-Venture DPCA', 1),
('142f8e63-aac7-4416-aced-a6643f0962d8', 'Collaboration FIAT', 1),
('93a2dd1e-27bd-4861-bc5e-b491b6d20281', 'Collaboration TOYOTA', 1),
('f052f7e3-45b9-40cd-9428-bc0b8cdbe7d5', 'Joint-Venture CAPSA', 1),
('2a51fbfa-a980-4313-9de8-24b8191b8439', 'Joint-Venture BPCE', 1),
('77ed5f32-7754-4ee1-8876-b40aee4dc719', 'Alliance GM', 1);

-- Utilisateurs
INSERT INTO PUBLIC.EDS_USER(U_ID, U_S_ID, U_PE_ID, U_RO_ID, U_PSA_ID, U_FIRSTNAME, U_LASTNAME, U_SERVICE, U_CONF, U_ACTIVE) VALUES
('59abd4b0-ed1e-11e0-be50-0800200c9a66', NULL, NULL, '59abd4b0-ed1e-11e0-be50-0800200c9aaa', 'U123456', 'Fabien', 'Vauchelles', NULL, NULL, 1),
('a6b3439d-fa70-46a6-bd99-347cd3021031', NULL, NULL, '59abd4b0-ed1e-11e0-be50-0800200c9aaa', 'E123456', 'Chang', 'CHANG', NULL, NULL, 1),
('59abd4b0-ed1e-11e0-be50-0800200c9a77', NULL, NULL, '59abd4b0-ed1e-11e0-be50-0800200c9aaa', 'U250331', 'Johann', 'Grandvuilemin', 'DRD/DSEE/MOST/OS2E', NULL, 1),
('c158e157-c056-4503-8080-38eb44e262aa', NULL, NULL, '59abd4b0-ed1e-11e0-be50-0800200c9aaa', 'U375452', 'Eloi', 'KESTLER', NULL, NULL, 1),
('435bf4ca-5b5d-4ff0-8b6c-63e1bdae1926', NULL, NULL, '242d3233-a1e0-493f-b4e2-99f290f53542', 'U379566', 'Daniel', 'CHEVILLARD', NULL, NULL, 1),
('afa1592b-b791-4e99-a4e1-fdb539762c54', NULL, NULL, '242d3233-a1e0-493f-b4e2-99f290f53542', 'U237508', 'Mario', 'VOLANTI', NULL, NULL, 1),
('bb00bc10-1e08-4a4c-9221-1b71756c1998', NULL, NULL, '242d3233-a1e0-493f-b4e2-99f290f53542', 'U226674', 'Philippe', 'MOULEDOUS', NULL, NULL, 1),
('9c5a86c4-9894-4f27-9680-2af9f380da6d', NULL, NULL, '242d3233-a1e0-493f-b4e2-99f290f53542', 'U246552', 'Kam-wah', 'LAI', NULL, NULL, 1),
('4575f1ce-1da6-41fc-a1a5-453452f5ac92', NULL, NULL, 'c78a7ae8-9b65-442a-b850-9e69eab91091', 'P769581', 'Emmanuel', 'ROCHE', NULL, NULL, 1),
('601e6ccd-6e8e-4540-85ae-926c24b4051a', NULL, NULL, 'c78a7ae8-9b65-442a-b850-9e69eab91091', 'E416262', 'Nicolas', 'WATELET', NULL, NULL, 1),
('732e7e08-2498-45aa-898c-ede09ec0fd27', NULL, NULL, '59abd4b0-ed1e-11e0-be50-0800200c9aaa', 'U364998', 'David', 'NOUVELET', NULL, NULL, 1),
('732e7e08-2498-45aa-898c-ede09ec0fd20', NULL, NULL, '59abd4b0-ed1e-11e0-be50-0800200c9aaa', 'U427635', 'Nassim', 'AIT BRAHAM', NULL, NULL, 1),
('d1b38b7e-e5b2-424f-a92c-869029eb70e8', NULL, NULL, '59abd4b0-ed1e-11e0-be50-0800200c9aaa', 'U337372', STRINGDECODE('Fran\u00e7ois'), 'GRAMOND', NULL, NULL, 1); 

-- Libellés
INSERT INTO PUBLIC.EDS_WORDING(W_ID, W_VALUE, W_TYPE, W_INDEX) VALUES
('5w4fg89h-08de-4676-9f47-b2ea3b6dbce5', 'fra:+PERM;eng:+PERM', 'alim', 1),
('101673cc-a1a4-4e5d-bfe0-2b291da07670', 'Jep-8s', 'milestone', 1),
('10f41779-3f52-4adc-8e3d-f1ac586b4bee', 'Jep', 'milestone', 1),
('074afcbe-281a-4c68-89ea-68e582829850', 'JRO', 'milestone', 1),
('efbc33fa-2f8a-48dd-bf6b-d8a6bd9ecae0', 'JMP', 'milestone', 1),
('5ae9288a-97c3-45f8-a3cb-0de1cd7ab422', 'fra:+CPC;eng:+CPC', 'alim', 2),
('e9e8e412-150d-4a73-8597-ceb7b1f9d00a', 'fra:+CAN;eng:+CAN', 'alim', 3),
('0a60a9d2-b1f6-4c56-9c87-ad04358b289d', 'fra:+APC;eng:+APC', 'alim', 4),
('be362bd2-ef2a-45bb-af54-6ba3d19a37bc', 'fra:Norme B195510;eng:Norme B195510', 'data-origin', 1),
('4f35f0e0-99ad-4773-a510-29d9262e6240', 'fra:Norme ISO-26262;eng:Norme ISO-26262', 'data-origin', 2),
('e1050b41-af0d-42d3-8655-3017954d86f7', 'fra:Cycle de roulage ECE15;eng:Cycle de roulage ECE15', 'data-origin', 3),
('98ff9270-cceb-4700-b8a7-54a6c353ce09', 'fra:Tout est conforme.;eng:Tout est conforme.', 'comment', 1),
('020b0b6f-8a30-47e4-b3c2-733e248ab2be', STRINGDECODE('fra:Il manque des informations mais il est possible de continuer \u00e0 travailler.;eng:Information missing but the work can still be done'), 'comment', 2),
('867098b2-a56d-4141-b11d-3334dec62e82', STRINGDECODE('fra:Donn\u00e9es pr\u00e9sentes mais partiellement exploitables.;eng:Data available but partially usable'), 'comment', 3),
('3d38f122-8c51-41cb-ad87-e8a6d5d484d6', STRINGDECODE('fra:Donn\u00e9es pr\u00e9sentes mais non exploitables;eng:Donn\u00e9es pr\u00e9sentes mais non exploitables'), 'comment', 4),
('1a565ad6-b12b-47a0-aea6-80f0f3c26344', STRINGDECODE('fra:Donn\u00e9es non pr\u00e9sentes.;eng:Donn\u00e9es non pr\u00e9sentes.'), 'comment', 5),
('f5034dc6-943d-4fbb-bbb2-3cc98f6799cc', 'fra:AAS;eng:AAS', 'organ-family', 1),
('49aaef75-f125-478e-b365-caa6a92dad75', 'fra:EMF;eng:EMF', 'organ-family', 6),
('c0454afa-e85a-48b2-93bb-1e54269e7102', 'fra:ALARM;eng:ALARM', 'organ-family', 2),
('45d5d800-5d44-4be1-b310-c0fb856f2563', 'fra:ARTIV;eng:ARTIV', 'organ-family', 3),
('bd1b6b12-2d39-4137-b947-5cb7c24aa757', 'fra:CLIM;eng:CLIM', 'organ-family', 4),
('59c64a53-2346-4e7f-9667-96aa3627aaaa', 'fra:FREIN;eng:FREIN', 'organ-family', 5),
('1461e19c-4b1f-4573-af6d-565220be5098', 'fra:RADIO;eng:RADIO', 'organ-family', 7),
('fb9aed00-326d-4999-a3f2-96c934382478', 'fra:DSG;eng:DSG', 'organ-family', 8),
('9d40d01f-e5ed-4ef5-a430-b2f7aaf15ec0', 'fra:VTH;eng:VTH', 'organ-family', 9),
('ed7bf17b-bbaa-45a3-bcd6-8fb8d63a3654', 'fra:RETRO;eng:RETRO', 'organ-family', 10),
('f13eea78-1a9b-4de7-9637-34f75f65bda5', 'fra:CAPTEUR;eng:CAPTEUR', 'organ-family', 11),
('7d3665ff-4e35-4150-b754-d8f0a6c1bd3a', 'fra:ECU;eng:ECU', 'organ-family', 12),
('7d089ba7-b44b-4d6e-acd2-217aac8b5e83', 'fra:ADML;eng:ADML', 'organ-family', 13),
('986a5e7d-3a9a-4bd9-ba69-59e981e81053', 'fra:Simple;eng:Simple', 'component-type', 1),
('b9191faf-53c0-4eb0-abe6-e7c901a1ecbb', 'fra:Moyen;eng:Mean', 'component-type', 1),
('8247bc29-08de-4676-9f47-b2ea3b6dbce5', 'fra:Complexe;eng:Complex', 'component-type', 1); 

-- Modèle de fiche
INSERT INTO PUBLIC.EDS_COMPONENT_TYPE(CT_ID, CT_NAME, CT_INDEX, CT_BTTBT_OK_FORMSET, CT_BTTBT_KO_FORMSET) VALUES
('3bd851e2-960c-4287-8eb2-efb008d092e5', '986a5e7d-3a9a-4bd9-ba69-59e981e81053', 1, 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-robust-cc eds-consolidate-cc eds-psa-measure-cc eds-version-history', 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-robust-cc eds-consolidate-cc eds-psa-measure-cc eds-version-history'),
('91af56c2-1a55-43fc-9711-855aff2fbfac', 'b9191faf-53c0-4eb0-abe6-e7c901a1ecbb', 2, 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-tension-alimentation-preliminaire eds-robust-cc eds-consolidate-cc eds-tension-alimentation-consolide eds-cse eds-defaillance-veille-reveil eds-psa-measure-cc eds-version-history eds-attachments', 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-tension-alimentation-preliminaire eds-robust-cc eds-consolidate-cc eds-tension-alimentation-consolide eds-cse eds-defaillance-veille-reveil eds-psa-measure-cc eds-version-history eds-attachments'),
('c9f0bb30-1b94-40c2-8c8a-91a2fd1729dd', '8247bc29-08de-4676-9f47-b2ea3b6dbce5', 3, 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-tension-alimentation-preliminaire eds-robust-cc eds-comportement-mission eds-profil-mission eds-consolidate-cc eds-tension-alimentation-consolide eds-comportement-consolide eds-profil-activation eds-cse eds-defaillance-veille-reveil eds-psa-measure-cc eds-version-history eds-attachments', 'eds-generic-data eds-high-validation eds-driver-drifts-cc eds-primary-cc eds-tension-alimentation-preliminaire eds-robust-cc eds-comportement-mission eds-profil-mission eds-consolidate-cc eds-tension-alimentation-consolide eds-comportement-consolide eds-profil-activation eds-cse eds-defaillance-veille-reveil eds-psa-measure-cc eds-version-history eds-attachments'); 

-- Projets
INSERT INTO PUBLIC.EDS_PROJECT(P_ID, P_NAME, P_DATE_FIRST_STAGE, P_W_ID_FIRST_STAGE, P_DATE_SECOND_STAGE, P_W_ID_SECOND_STAGE, P_DATE_THIRD_STAGE, P_W_ID_THIRD_STAGE, P_DATE_FOURTH_STAGE, P_W_ID_FOURTH_STAGE, P_INDEX) VALUES
('49e67af9-58d5-4192-8afb-1322506cca94', 'A9', TIMESTAMP '2012-11-06 09:54:15.941', '101673cc-a1a4-4e5d-bfe0-2b291da07670', TIMESTAMP '2012-11-06 09:54:15.941', '10f41779-3f52-4adc-8e3d-f1ac586b4bee', TIMESTAMP '2012-11-06 09:54:15.941', '074afcbe-281a-4c68-89ea-68e582829850', TIMESTAMP '2012-11-06 09:54:15.941', 'efbc33fa-2f8a-48dd-bf6b-d8a6bd9ecae0', 1),
('f783fdb2-52bd-42bb-aa3d-83d9197b9360', 'T8', TIMESTAMP '2012-11-06 09:54:15.941', '101673cc-a1a4-4e5d-bfe0-2b291da07670', TIMESTAMP '2012-11-06 09:54:15.941', '10f41779-3f52-4adc-8e3d-f1ac586b4bee', TIMESTAMP '2012-11-06 09:54:15.941', '074afcbe-281a-4c68-89ea-68e582829850', TIMESTAMP '2012-11-06 09:54:15.941', 'efbc33fa-2f8a-48dd-bf6b-d8a6bd9ecae0', 4),
('3cd6591d-228e-4f6b-a5e1-2821adf37a6b', 'T75', TIMESTAMP '2012-11-06 09:54:15.941', '074afcbe-281a-4c68-89ea-68e582829850', TIMESTAMP '2012-11-06 09:54:15.941', '10f41779-3f52-4adc-8e3d-f1ac586b4bee', TIMESTAMP '2012-11-06 09:54:15.941', '074afcbe-281a-4c68-89ea-68e582829850', TIMESTAMP '2012-11-06 09:54:15.941', '074afcbe-281a-4c68-89ea-68e582829850', 2),
('f68adea1-c4f9-4374-95e7-6e6527bef9cf', 'B81', TIMESTAMP '2012-11-06 09:54:15.941', '101673cc-a1a4-4e5d-bfe0-2b291da07670', TIMESTAMP '2012-11-06 09:54:15.941', '10f41779-3f52-4adc-8e3d-f1ac586b4bee', TIMESTAMP '2012-11-06 09:54:15.941', '074afcbe-281a-4c68-89ea-68e582829850', TIMESTAMP '2012-11-06 09:54:15.941', 'efbc33fa-2f8a-48dd-bf6b-d8a6bd9ecae0', 3),
('4f8fb19a-a085-4adb-a76a-cdfa85b0d8b2', 'W2', NULL, '101673cc-a1a4-4e5d-bfe0-2b291da07670', NULL, '10f41779-3f52-4adc-8e3d-f1ac586b4bee', NULL, '074afcbe-281a-4c68-89ea-68e582829850', NULL, 'efbc33fa-2f8a-48dd-bf6b-d8a6bd9ecae0', 5),
('d4b2be99-7a7c-42f0-9446-941e3d2028a4', 'P8', NULL, '101673cc-a1a4-4e5d-bfe0-2b291da07670', NULL, '10f41779-3f52-4adc-8e3d-f1ac586b4bee', NULL, '074afcbe-281a-4c68-89ea-68e582829850', NULL, 'efbc33fa-2f8a-48dd-bf6b-d8a6bd9ecae0', 6),
('41c1c89b-33c7-4f6f-a121-c3a311dd7f8f', 'K0', NULL, '101673cc-a1a4-4e5d-bfe0-2b291da07670', NULL, '10f41779-3f52-4adc-8e3d-f1ac586b4bee', NULL, '074afcbe-281a-4c68-89ea-68e582829850', NULL, 'efbc33fa-2f8a-48dd-bf6b-d8a6bd9ecae0', 7),
('812067ae-22b8-422c-ba15-5c9e584e8ac1', 'A7', NULL, '101673cc-a1a4-4e5d-bfe0-2b291da07670', NULL, '10f41779-3f52-4adc-8e3d-f1ac586b4bee', NULL, '074afcbe-281a-4c68-89ea-68e582829850', NULL, 'efbc33fa-2f8a-48dd-bf6b-d8a6bd9ecae0', 9),
('4181884d-ae52-4589-9c52-6a96e5f960eb', 'A56', NULL, '101673cc-a1a4-4e5d-bfe0-2b291da07670', NULL, '10f41779-3f52-4adc-8e3d-f1ac586b4bee', NULL, '074afcbe-281a-4c68-89ea-68e582829850', NULL, 'efbc33fa-2f8a-48dd-bf6b-d8a6bd9ecae0', 8);        

insert into EDS_GLOBAL_CONF values
(
    'server_image_path',
    'string',
    'tmp/vaadin-images/'
);

insert into EDS_GLOBAL_CONF values
(
    'server_bind_adress',
    'string',
    'http://vvc4i4:8080/EDSserverWEB/'
);
insert into EDS_GLOBAL_CONF values
(
    'access_lock_timeout',
    'integer',
    '3600'
);