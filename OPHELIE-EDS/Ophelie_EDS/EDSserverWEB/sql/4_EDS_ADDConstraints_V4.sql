ALTER TABLE EDS_LOW_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_9BA7D UNIQUE(LVFD_CONSOLIDATED_ID);          
ALTER TABLE SCHEMA_VERSION ADD CONSTRAINT SCHEMA_VERSION_SCRIPT_UNIQUE UNIQUE(SCRIPT);          
ALTER TABLE EDS_COMPONENT_TYPE ADD CONSTRAINT CONSTRAINT_B49 UNIQUE(CT_NAME);   
ALTER TABLE EDS_PERIMETER ADD CONSTRAINT CONSTRAINT_9A9 UNIQUE(PE_NAME);        
ALTER TABLE EDS_LOW_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_9BA UNIQUE(LVFD_PRELIMINARY_ID);             
ALTER TABLE EDS_LOW_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_9BA7D4 UNIQUE(LVFD_CLOSED_ID);               
ALTER TABLE EDS_EDS ADD CONSTRAINT CONSTRAINT_BAD UNIQUE(EDS_REF, EDS_MAJOR_VERSION, EDS_MINOR_VERSION);        
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_3790 UNIQUE(HVFD_RE_ROBUST_ID);             
ALTER TABLE EDS_LOW_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_9BA7D47 UNIQUE(LVFD_SUPPLIER_DATA_ID);       
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_3790F3 UNIQUE(HVFD_RE_CLOSED_ID);           
ALTER TABLE EDS_LOW_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_9BA7 UNIQUE(LVFD_ROBUST_ID); 
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_3790F39 UNIQUE(HVFD_DBEES_CONSOLIDATE_ID);  
ALTER TABLE EDS_PROJECT_EDS ADD CONSTRAINT CONSTRAINT_AE UNIQUE(PEDS_P_ID, PEDS_EDS_ID);        
ALTER TABLE EDS_SUPPLIER ADD CONSTRAINT CONSTRAINT_A45 UNIQUE(S_NAME);          
ALTER TABLE EDS_SUBSCRIPTION ADD CONSTRAINT CONSTRAINT_696 UNIQUE(SUB_U_ID, SUB_EDS_REF);       
ALTER TABLE EDS_ROLE ADD CONSTRAINT CONSTRAINT_9FDC4 UNIQUE(RO_NAME);           
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_379 UNIQUE(HVFD_RS_PRIMARY_ID);             
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_3790F UNIQUE(HVFD_RE_CONSOLIDATE_ID);       
ALTER TABLE EDS_USER ADD CONSTRAINT CONSTRAINT_9FD UNIQUE(U_PSA_ID);            
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_3790F391_5 UNIQUE(HVFD_SUPPLIER_DATA_ID);   
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_3790F391_3 UNIQUE(HVFD_CADE_CONSOLIDATE_ID);
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_3790F391_4 UNIQUE(HVFD_CADE_CLOSED_ID);     
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_3790F391_1 UNIQUE(HVFD_DBEED_CONSOLIDATE_ID);               
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_3790F391_2 UNIQUE(HVFD_DBEED_CLOSED_ID);    
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT CONSTRAINT_3790F391_0 UNIQUE(HVFD_DBEES_CLOSED_ID);    
ALTER TABLE EDS_EDS ADD CONSTRAINT EDS_AFF_USER_ID_FK FOREIGN KEY(EDS_AFF_USER_ID) REFERENCES EDS_USER(U_ID) ;    
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_RE_CLOSED_ID_FK FOREIGN KEY(HVFD_RE_CLOSED_ID) REFERENCES EDS_VALIDATION(V_ID) ;    
ALTER TABLE EDS_MISSION_ACTN_FORM_DATA ADD CONSTRAINT MAFD_ESD_ID_FK FOREIGN KEY(MAFD_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;        
ALTER TABLE EDS_J_PSA_MES_SUP_CRT_MST ADD CONSTRAINT JPMST_CRT_MISE_S_U_CMST_FK FOREIGN KEY(JPMSCMSTEDS_CMST_ID) REFERENCES EDS_COURANT_MISE_SOUS_TENSION(CMSTEDS_ID) ;           
ALTER TABLE EDS_PREL_SUP_VOLT_FORM_DATA ADD CONSTRAINT PSV_O_D_U_MIN_FCT_INIT_FK FOREIGN KEY(PSV_OR_DATA_TEN_MIN_FCT_INIT) REFERENCES EDS_WORDING(W_ID) ;         
ALTER TABLE EDS_CMPTT_ROB_FORM_DATA ADD CONSTRAINT CRFD_EDS_ID_FK FOREIGN KEY(CRFD_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;           
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_EDS_ID_FK FOREIGN KEY(HVFD_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;     
ALTER TABLE EDS_EDS_PERIMETER ADD CONSTRAINT EDSPE_PE_ID_FK FOREIGN KEY(EDSPE_PE_ID) REFERENCES EDS_PERIMETER(PE_ID) ;            
ALTER TABLE EDS_MISSION_PROFIL_FORM_DATA ADD CONSTRAINT MPFD_EDS_ID_FK FOREIGN KEY(MPFD_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;      
ALTER TABLE EDS_J_PSA_MES_SUP_CRT_CYCLE ADD CONSTRAINT JPMST_EDS_CC_PMST_ID_FK FOREIGN KEY(JPMSCCDEDS_ID) REFERENCES EDS_PSA_MESURE_SUPPLY(PMSEDS_ID) ;           
ALTER TABLE EDS_J_CONS_SUP_TH_MODE_MONT ADD CONSTRAINT JCST_MODE_MONTAGE_MM_ID_FK FOREIGN KEY(JCSTMMEDS_MM_ID) REFERENCES EDS_MODE_MONTAGE(MMEDS_ID) ;            
ALTER TABLE EDS_USER ADD CONSTRAINT U_S_ID_FK FOREIGN KEY(U_S_ID) REFERENCES EDS_SUPPLIER(S_ID) ; 
ALTER TABLE EDS_J_CONS_GROUND_CBCD ADD CONSTRAINT JCG_CRT_LOK_CRT_DYSFCT_FK FOREIGN KEY(JCGCBCDEDS_CBCD_ID) REFERENCES EDS_CRT_BLOQUE_CRT_DYSF(CBCDEDS_ID) ;      
ALTER TABLE EDS_EDS_PERIMETER ADD CONSTRAINT EDSPE_EDS_ID_FK FOREIGN KEY(EDSPE_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;               
ALTER TABLE EDS_J_CONS_SUP_TH_CBCD ADD CONSTRAINT JCST_CRT_LOK_CRT_DYS_FK FOREIGN KEY(JCSTCBCDEDS_CBCD_ID) REFERENCES EDS_CRT_BLOQUE_CRT_DYSF(CBCDEDS_ID) ;       
ALTER TABLE EDS_J_CONS_SUP_MES_MODE_PARC ADD CONSTRAINT JCSM_MODE_PARC_CSM_ID_FK FOREIGN KEY(JCSMMPEDS_ID) REFERENCES EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID) ;  
ALTER TABLE EDS_J_PSA_MES_SUP_RVROI ADD CONSTRAINT JPMST_RVROI_PMST_ID_FK FOREIGN KEY(JPMSRVROIEDS_RVROI_ID) REFERENCES EDS_PSA_MESURE_SUPPLY(PMSEDS_ID) ;        
ALTER TABLE EDS_J_PSA_MES_SUP_CRT_MST ADD CONSTRAINT JPMST_CRT_MISE_S_U_PMST_FK FOREIGN KEY(JPMSCMSTEDS_ID) REFERENCES EDS_PSA_MESURE_SUPPLY(PMSEDS_ID) ;         
ALTER TABLE EDS_PREL_SUP_VOLT_FORM_DATA ADD CONSTRAINT PSV_EDS_ID_FK FOREIGN KEY(PSV_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;         
ALTER TABLE EDS_EDS ADD CONSTRAINT EDS_MANAGER_ID_FK FOREIGN KEY(EDS_MANAGER_ID) REFERENCES EDS_USER(U_ID) ;      
ALTER TABLE EDS_PROJECT_EDS ADD CONSTRAINT PEDS_P_ID_FK FOREIGN KEY(PEDS_P_ID) REFERENCES EDS_PROJECT(P_ID) ;     
ALTER TABLE EDS_PROJECT ADD CONSTRAINT P_W_ID_FIRST_STAGE_FK FOREIGN KEY(P_W_ID_FIRST_STAGE) REFERENCES EDS_WORDING(W_ID) ;       
ALTER TABLE EDS_SUPPLY ADD CONSTRAINT S_PSEDS_ID_FK FOREIGN KEY(S_PSEDS_ID) REFERENCES EDS_PRIMARY_SUPPLY(PSEDS_ID) ;             
ALTER TABLE EDS_J_PSA_MES_SUP_RVROI ADD CONSTRAINT JPMST_RVROI_RVROIEDS_ID_FK FOREIGN KEY(JPMSRVROIEDS_ID) REFERENCES EDS_RVROI(RVROIEDS_ID) ;    
ALTER TABLE EDS_EDS ADD CONSTRAINT EDS_DRIFT_VALIDATOR_ID_FK FOREIGN KEY(EDS_DRIFT_VALIDATOR_ID) REFERENCES EDS_USER(U_ID) ;      
ALTER TABLE EDS_J_CONS_SUP_TH_CAA ADD CONSTRAINT JCST_CRT_APPEL_ACT_CST_FK FOREIGN KEY(JCSTCAAEDS_ID) REFERENCES EDS_CONS_SUP_TH(CSTEDS_ID) ;     
ALTER TABLE EDS_EDS ADD CONSTRAINT EDS_W_ID_FK FOREIGN KEY(EDS_W_ID) REFERENCES EDS_WORDING(W_ID) ;               
ALTER TABLE EDS_J_PSA_MES_SUP_MODE_MONT ADD CONSTRAINT JPMST_MODE_MTG_PMST_FK FOREIGN KEY(JPMSMMEDS_ID) REFERENCES EDS_PSA_MESURE_SUPPLY(PMSEDS_ID) ;             
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_RE_ROBUST_ID_FK FOREIGN KEY(HVFD_RE_ROBUST_ID) REFERENCES EDS_VALIDATION(V_ID) ;    
ALTER TABLE EDS_PROJECT_CONS_CURENT ADD CONSTRAINT PCC_CCEDS_ID_FK FOREIGN KEY(PCC_CCEDS_ID) REFERENCES EDS_CONS_CURENT_FORM_DATA(CCEDS_ID) ;     
ALTER TABLE EDS_LOW_VALIDATION_FORM_DATA ADD CONSTRAINT LVFD_EDS_ID_FK FOREIGN KEY(LVFD_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;      
ALTER TABLE EDS_J_CONS_SUP_MES_MODE_MONT ADD CONSTRAINT JCSM_MODE_MONTAGE_MM_ID_FK FOREIGN KEY(JCSMMMEDS_MM_ID) REFERENCES EDS_MODE_MONTAGE(MMEDS_ID) ;           
ALTER TABLE EDS_J_CONS_SUP_TH_RVROI ADD CONSTRAINT JCST_RVROI_RVROIEDS_ID_FK FOREIGN KEY(JCSTRVROIEDS_ID) REFERENCES EDS_RVROI(RVROIEDS_ID) ;     
ALTER TABLE EDS_J_CONS_SUP_TH_CNA ADD CONSTRAINT JCST_CRT_NOM_ACT_CNA_FK FOREIGN KEY(JCSTCNAEDS_CNA_ID) REFERENCES EDS_CRT_NOMINALE_ACTIVATION(CNAEDS_ID) ;       
ALTER TABLE EDS_J_CONS_GROUND_CAA ADD CONSTRAINT JCG_CRT_APPEL_ACT_CST_FK FOREIGN KEY(JCGCAAEDS_ID) REFERENCES EDS_GROUND_CONSOLIDATE(GEDS_ID) ;  
ALTER TABLE EDS_USER ADD CONSTRAINT U_RO_ID_FK FOREIGN KEY(U_RO_ID) REFERENCES EDS_ROLE(RO_ID) ;  
ALTER TABLE EDS_J_CONS_SUP_TH_RVROI ADD CONSTRAINT JCST_RVROI_CST_ID_FK FOREIGN KEY(JCSTRVROIEDS_RVROI_ID) REFERENCES EDS_CONS_SUP_TH(CSTEDS_ID) ;
ALTER TABLE EDS_SUPPLY ADD CONSTRAINT S_PCEDS_ID_FK FOREIGN KEY(S_PCEDS_ID) REFERENCES EDS_PRIMARY_CURENT(PCEDS_ID) ;             
ALTER TABLE EDS_PROJECT ADD CONSTRAINT P_W_ID_FOURTH_STAGE_FK FOREIGN KEY(P_W_ID_FOURTH_STAGE) REFERENCES EDS_WORDING(W_ID) ;     
ALTER TABLE EDS_J_CONS_SUP_MES_CAA ADD CONSTRAINT JCSM_CRT_APPEL_ACT_CAA_FK FOREIGN KEY(JCSMCAAEDS_CAA_ID) REFERENCES EDS_CRT_APPELLE_ACTIVATION(CAAEDS_ID) ;     
ALTER TABLE EDS_PROJECT ADD CONSTRAINT P_W_ID_SECOND_STAGE_FK FOREIGN KEY(P_W_ID_SECOND_STAGE) REFERENCES EDS_WORDING(W_ID) ;     
ALTER TABLE EDS_WS_SESSION_TOKEN ADD CONSTRAINT WST_U_ID_FK FOREIGN KEY(WST_U_ID) REFERENCES EDS_USER(U_ID) ;     
ALTER TABLE EDS_J_PSA_MES_SUP_MODE_MONT ADD CONSTRAINT JPMST_MODE_MONTAGE_MM_ID_FK FOREIGN KEY(JPMSMMEDS_MM_ID) REFERENCES EDS_MODE_MONTAGE(MMEDS_ID) ;           
ALTER TABLE EDS_CSE_FORM_DATA ADD CONSTRAINT CSE_EDS_ID_FK FOREIGN KEY(CSE_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;   
ALTER TABLE EDS_J_PSA_MES_SUP_CBCD ADD CONSTRAINT JPMST_CRT_LOK_CRT_DYS_FK FOREIGN KEY(JPMSCBCDEDS_CBCD_ID) REFERENCES EDS_CRT_BLOQUE_CRT_DYSF(CBCDEDS_ID) ;      
ALTER TABLE EDS_EDS ADD CONSTRAINT EDS_P_ID_FK FOREIGN KEY(EDS_P_ID) REFERENCES EDS_PROJECT(P_ID) ;               
ALTER TABLE EDS_VALIDATION ADD CONSTRAINT V_COMMENT1_ID_FK FOREIGN KEY(V_COMMENT1_ID) REFERENCES EDS_WORDING(W_ID) ;              
ALTER TABLE EDS_ATTACHMENT_FORM_DATA ADD CONSTRAINT AFD_EDS_ID_FK FOREIGN KEY(AFD_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;            
ALTER TABLE EDS_J_PSA_MES_SUP_CNA ADD CONSTRAINT JPMST_CRT_NOM_ACT_PMST_FK FOREIGN KEY(JPMSCNAEDS_ID) REFERENCES EDS_PSA_MESURE_SUPPLY(PMSEDS_ID) ;               
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_DBEES_CONS_FK FOREIGN KEY(HVFD_DBEES_CONSOLIDATE_ID) REFERENCES EDS_VALIDATION(V_ID) ;              
ALTER TABLE EDS_EDS ADD CONSTRAINT EDS_CT_ID_FK FOREIGN KEY(EDS_CT_ID) REFERENCES EDS_COMPONENT_TYPE(CT_ID) ;     
ALTER TABLE EDS_CSE_LINE ADD CONSTRAINT CSEL_CSE_ID_FK FOREIGN KEY(CSEL_CSE_ID) REFERENCES EDS_CSE_FORM_DATA(CSE_ID) ;            
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_RS_PRIMARY_ID_FK FOREIGN KEY(HVFD_RS_PRIMARY_ID) REFERENCES EDS_VALIDATION(V_ID) ;  
ALTER TABLE EDS_SUPPLY ADD CONSTRAINT S_CCEDS_ID_FK FOREIGN KEY(S_CCEDS_ID) REFERENCES EDS_CONS_CURENT_FORM_DATA(CCEDS_ID) ;      
ALTER TABLE EDS_CRT_NOMINALE_ACTIVATION ADD CONSTRAINT CEEDS_ID_FK FOREIGN KEY(CNAEDS_CE_ID) REFERENCES EDS_COURANT_EFFICACE(CEEDS_ID) ;          
ALTER TABLE EDS_EDS_NUMBER_96K ADD CONSTRAINT EDSN_N_ID_FK FOREIGN KEY(EDSN_N_ID) REFERENCES EDS_NUMBER_96K(N_ID) ;               
ALTER TABLE EDS_CONSOLIDATE_SUPPLY ADD CONSTRAINT CSMEDS_QCF_FK FOREIGN KEY(CSEDS_QCF) REFERENCES EDS_QCF(QCF_ID) ;               
ALTER TABLE EDS_DVR_FORM_DATA ADD CONSTRAINT DVRFD_EDS_ID_FK FOREIGN KEY(DVRFD_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;               
ALTER TABLE EDS_MISSION_PROFIL ADD CONSTRAINT MP_MPFD_ID_FK FOREIGN KEY(MP_MPFD_ID) REFERENCES EDS_MISSION_PROFIL_FORM_DATA(MPFD_ID) ;            
ALTER TABLE EDS_PROJECT_COURANT_CYCLE ADD CONSTRAINT PCC_P_ID_FK FOREIGN KEY(PCC_P_ID) REFERENCES EDS_PROJECT(P_ID) ;             
ALTER TABLE EDS_EDS ADD CONSTRAINT EDS_ADMIN_ID_FK FOREIGN KEY(EDS_ADMIN_ID) REFERENCES EDS_USER(U_ID) ;          
ALTER TABLE EDS_PRIMARY_CURENT ADD CONSTRAINT PCEDS_EDS_ID_FK FOREIGN KEY(PCEDS_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;              
ALTER TABLE EDS_LOW_VALIDATION_FORM_DATA ADD CONSTRAINT LVFD_ROBUST_ID_FK FOREIGN KEY(LVFD_ROBUST_ID) REFERENCES EDS_VALIDATION(V_ID) ;           
ALTER TABLE EDS_CURRENT_STATEMENT ADD CONSTRAINT CUR_STAT_USER_FK FOREIGN KEY(CS_USER_ID) REFERENCES EDS_USER(U_ID) ;             
ALTER TABLE EDS_J_PSA_MES_SUP_MODE_PARC ADD CONSTRAINT JPMST_MODE_PARC_MP_ID_FK FOREIGN KEY(JPMSMPEDS_MP_ID) REFERENCES EDS_MODE_PARC(MPEDS_ID) ; 
ALTER TABLE EDS_CMPT_CONS_FORM_DATA ADD CONSTRAINT COCOFD_EDS_ID_FK FOREIGN KEY(COCOFD_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;       
ALTER TABLE EDS_J_CONS_SUP_MES_CRT_MST ADD CONSTRAINT JCSM_CRT_MISE_S_U_CMST_FK FOREIGN KEY(JCSMCMSTEDS_CMST_ID) REFERENCES EDS_COURANT_MISE_SOUS_TENSION(CMSTEDS_ID) ;           
ALTER TABLE EDS_SUBSCRIPTION ADD CONSTRAINT SUB_U_ID_FK FOREIGN KEY(SUB_U_ID) REFERENCES EDS_USER(U_ID) ;         
ALTER TABLE EDS_PREL_SUP_VOLT_FORM_DATA ADD CONSTRAINT PSV_O_D_U_MAX_FCT_NOM_FK FOREIGN KEY(PSV_OR_DATA_TEN_MAX_FCT_NOM) REFERENCES EDS_WORDING(W_ID) ;           
ALTER TABLE EDS_J_PSA_MES_SUP_MODE_PARC ADD CONSTRAINT JPMST_MODE_PARC_PMST_ID_FK FOREIGN KEY(JPMSMPEDS_ID) REFERENCES EDS_PSA_MESURE_SUPPLY(PMSEDS_ID) ;         
ALTER TABLE EDS_PRIMARY_SUPPLY ADD CONSTRAINT PSEDS_W_ID_FK FOREIGN KEY(PSEDS_W_ID) REFERENCES EDS_WORDING(W_ID) ;
ALTER TABLE EDS_LOW_VALIDATION_FORM_DATA ADD CONSTRAINT LVFD_SUPPLIER_DATA_ID_FK FOREIGN KEY(LVFD_SUPPLIER_DATA_ID) REFERENCES EDS_VALIDATION(V_ID) ;             
ALTER TABLE EDS_DRIFT_INFO ADD CONSTRAINT DI_EDS_ID_FK FOREIGN KEY(DI_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;        
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_CADE_CONSOLIDATE_ID_FK FOREIGN KEY(HVFD_CADE_CONSOLIDATE_ID) REFERENCES EDS_VALIDATION(V_ID) ;      
ALTER TABLE EDS_J_PSA_MES_SUP_CNA ADD CONSTRAINT JPMST_CRT_NOM_ACT_CNA_FK FOREIGN KEY(JPMSCNAEDS_CNA_ID) REFERENCES EDS_CRT_NOMINALE_ACTIVATION(CNAEDS_ID) ;      
ALTER TABLE EDS_J_CONS_SUP_TH_CNA ADD CONSTRAINT JCST_CRT_NOM_ACT_CST_FK FOREIGN KEY(JCSTCNAEDS_ID) REFERENCES EDS_CONS_SUP_TH(CSTEDS_ID) ;       
ALTER TABLE EDS_ATTACHMENT ADD CONSTRAINT A_AFD_ID_FK FOREIGN KEY(A_AFD_ID) REFERENCES EDS_ATTACHMENT_FORM_DATA(AFD_ID) ;         
ALTER TABLE EDS_LOW_VALIDATION_FORM_DATA ADD CONSTRAINT LVFD_PRELIMINARY_ID_FK FOREIGN KEY(LVFD_PRELIMINARY_ID) REFERENCES EDS_VALIDATION(V_ID) ; 
ALTER TABLE EDS_J_CONS_GROUND_CRT_MST ADD CONSTRAINT JCG_CRT_MISE_S_U_CMST_FK FOREIGN KEY(JCGCMSTEDS_CMST_ID) REFERENCES EDS_COURANT_MISE_SOUS_TENSION(CMSTEDS_ID) ;              
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_SUPPLIER_DATA_ID_FK FOREIGN KEY(HVFD_SUPPLIER_DATA_ID) REFERENCES EDS_VALIDATION(V_ID) ;            
ALTER TABLE EDS_J_CONS_SUP_TH_CRT_MST ADD CONSTRAINT JCST_CRT_MISE_S_U_CST_FK FOREIGN KEY(JCSTCMSTEDS_ID) REFERENCES EDS_CONS_SUP_TH(CSTEDS_ID) ; 
ALTER TABLE EDS_CONSOLIDATE_SUPPLY ADD CONSTRAINT CSEDS_W_ID_FK FOREIGN KEY(CSEDS_W_ID) REFERENCES EDS_WORDING(W_ID) ;            
ALTER TABLE EDS_PROJECT_PERIMETER ADD CONSTRAINT PPE_P_ID_FK FOREIGN KEY(PPE_P_ID) REFERENCES EDS_PROJECT(P_ID) ; 
ALTER TABLE EDS_J_CONS_SUP_TH_MODE_PARC ADD CONSTRAINT JCST_MODE_PARC_CST_ID_FK FOREIGN KEY(JCSTMPEDS_ID) REFERENCES EDS_CONS_SUP_TH(CSTEDS_ID) ; 
ALTER TABLE EDS_J_CONS_SUP_TH_CBCD ADD CONSTRAINT JCST_CRT_DYSF_CST_FK FOREIGN KEY(JCSTCBCDEDS_ID) REFERENCES EDS_CONS_SUP_TH(CSTEDS_ID) ;        
ALTER TABLE EDS_GROUND_CONSOLIDATE ADD CONSTRAINT GC_RCEDS_ID_FK FOREIGN KEY(GC_RCEDS_ID) REFERENCES EDS_GROUND_CONSOLIDATE_CURENT(GCC_ID) ;      
ALTER TABLE EDS_PROJECT_PRIMARY_CURENT ADD CONSTRAINT PPC_P_ID_FK FOREIGN KEY(PPC_P_ID) REFERENCES EDS_PROJECT(P_ID) ;            
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_CADE_CLOSED_ID_FK FOREIGN KEY(HVFD_CADE_CLOSED_ID) REFERENCES EDS_VALIDATION(V_ID) ;
ALTER TABLE EDS_J_CONS_GROUND_CNA ADD CONSTRAINT JCG_CRT_NOM_ACT_CNA_FK FOREIGN KEY(JCGCNAEDS_CNA_ID) REFERENCES EDS_CRT_NOMINALE_ACTIVATION(CNAEDS_ID) ;         
ALTER TABLE EDS_SUPPLY ADD CONSTRAINT SEDS_EDS_ID_FK FOREIGN KEY(SEDS_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;        
ALTER TABLE EDS_PREL_SUP_VOLT_FORM_DATA ADD CONSTRAINT PSV_O_D_U_MIN_FCT_NOM_FK FOREIGN KEY(PSV_OR_DATA_TEN_MIN_FCT_NOM) REFERENCES EDS_WORDING(W_ID) ;           
ALTER TABLE EDS_J_CONS_SUP_MES_RVROI ADD CONSTRAINT JCSM_RVROI_CSM_ID_FK FOREIGN KEY(JCSMRVROIEDS_RVROI_ID) REFERENCES EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID) ; 
ALTER TABLE EDS_J_PSA_MES_SUP_CAA ADD CONSTRAINT JPMST_CRT_APL_ACT_CAA_FK FOREIGN KEY(JPMSCAAEDS_CAA_ID) REFERENCES EDS_CRT_APPELLE_ACTIVATION(CAAEDS_ID) ;       
ALTER TABLE EDS_CONSOLIDATE_SUPPLY ADD CONSTRAINT CSEDS_CSTEDS_ID_FK FOREIGN KEY(CSEDS_CST_ID) REFERENCES EDS_CONS_SUP_TH(CSTEDS_ID) ;            
ALTER TABLE EDS_CONS_CURENT_FORM_DATA ADD CONSTRAINT CCEDS_EDS_ID_FK FOREIGN KEY(CCEDS_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;       
ALTER TABLE EDS_EDS_NUMBER_96K ADD CONSTRAINT EDSN_EDS_ID_FK FOREIGN KEY(EDSN_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;
ALTER TABLE EDS_PROJECT_ROBUST_CURENT ADD CONSTRAINT PR_P_ID_FK FOREIGN KEY(PRC_P_ID) REFERENCES EDS_PROJECT(P_ID) ;              
ALTER TABLE EDS_LOW_VALIDATION_FORM_DATA ADD CONSTRAINT LVFD_CLOSED_ID_FK FOREIGN KEY(LVFD_CLOSED_ID) REFERENCES EDS_VALIDATION(V_ID) ;           
ALTER TABLE EDS_PROJECT_COURANT_CYCLE ADD CONSTRAINT PCCC_CCEDS_ID_FK FOREIGN KEY(PCC_CCEDS_ID) REFERENCES EDS_COURANT_CYCLE(CCEDS_ID) ;          
ALTER TABLE EDS_J_PSA_MES_SUP_CRT_CYCLE ADD CONSTRAINT JPMST_EDS_CC_ID_FK FOREIGN KEY(JPMSCCEDS_CC_ID) REFERENCES EDS_COURANT_CYCLE(CCEDS_ID) ;   
ALTER TABLE EDS_USER ADD CONSTRAINT U_PE_ID_FK FOREIGN KEY(U_PE_ID) REFERENCES EDS_PERIMETER(PE_ID) ;             
ALTER TABLE EDS_EDS ADD CONSTRAINT EDS_CREA_USER_ID_FK FOREIGN KEY(EDS_CREA_USER_ID) REFERENCES EDS_USER(U_ID) ;  
ALTER TABLE EDS_EDS ADD CONSTRAINT EDS_OFFICER_ID_FK FOREIGN KEY(EDS_OFFICER_ID) REFERENCES EDS_USER(U_ID) ;      
ALTER TABLE EDS_COMPONENT_TYPE ADD CONSTRAINT CT_NAME_FK FOREIGN KEY(CT_NAME) REFERENCES EDS_WORDING(W_ID) ;      
ALTER TABLE EDS_SUPPLY ADD CONSTRAINT S_CSEDS_ID_FK FOREIGN KEY(S_CSEDS_ID) REFERENCES EDS_CONSOLIDATE_SUPPLY(CSEDS_ID) ;         
ALTER TABLE EDS_J_CONS_SUP_MES_CRT_MST ADD CONSTRAINT JCSM_CRT_MISE_S_U_CSM_FK FOREIGN KEY(JCSMCMSTEDS_ID) REFERENCES EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID) ;  
ALTER TABLE EDS_J_CONS_SUP_MES_CNA ADD CONSTRAINT JCSM_CRT_NOM_ACT_CSM_FK FOREIGN KEY(JCSMCNAEDS_ID) REFERENCES EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID) ;        
ALTER TABLE EDS_J_CONS_GROUND_CAA ADD CONSTRAINT JCG_CRT_APPEL_ACT_CAA_FK FOREIGN KEY(JCGCAAEDS_CAA_ID) REFERENCES EDS_CRT_APPELLE_ACTIVATION(CAAEDS_ID) ;        
ALTER TABLE EDS_PREL_SUP_VOLT_FORM_DATA ADD CONSTRAINT PSV_O_D_U_MAX_FCT_REH_FK FOREIGN KEY(PSV_OR_DATA_TEN_MAX_FCT_REHAB) REFERENCES EDS_WORDING(W_ID) ;         
ALTER TABLE EDS_ROBUST_SUPPLY ADD CONSTRAINT RSEDS_W_ID_FK FOREIGN KEY(RSEDS_W_ID) REFERENCES EDS_WORDING(W_ID) ; 
ALTER TABLE EDS_J_CONS_SUP_TH_MODE_MONT ADD CONSTRAINT JCST_MODE_MONTAGE_CST_ID_FK FOREIGN KEY(JCSTMMEDS_ID) REFERENCES EDS_CONS_SUP_TH(CSTEDS_ID) ;              
ALTER TABLE EDS_VALIDATION ADD CONSTRAINT V_VALIDATOR_ID_FK FOREIGN KEY(V_VALIDATOR_ID) REFERENCES EDS_USER(U_ID) ;               
ALTER TABLE EDS_J_CONS_GROUND_CNA ADD CONSTRAINT JCG_CRT_NOM_ACT_CST_FK FOREIGN KEY(JCGCNAEDS_ID) REFERENCES EDS_GROUND_CONSOLIDATE(GEDS_ID) ;    
ALTER TABLE EDS_J_CONS_SUP_MES_CNA ADD CONSTRAINT JCSM_CRT_NOM_ACT_MES_CNA_FK FOREIGN KEY(JCSMCNAEDS_CNA_ID) REFERENCES EDS_CRT_NOMINALE_ACTIVATION(CNAEDS_ID) ;  
ALTER TABLE EDS_PROJECT_EDS ADD CONSTRAINT PEDS_EDS_ID_FK FOREIGN KEY(PEDS_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;   
ALTER TABLE EDS_CONS_CURENT_FORM_DATA ADD CONSTRAINT PCC_CCM_ID_FK FOREIGN KEY(PCC_CCM_ID) REFERENCES EDS_GROUND_CONSOLIDATE_CURENT(GCC_ID) ;     
ALTER TABLE EDS_CONSOLIDATE_SUPPLY ADD CONSTRAINT CSMEDS_CSMEDS_ID_FK FOREIGN KEY(CSMEDS_CSM_ID) REFERENCES EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID) ;            
ALTER TABLE EDS_J_CONS_SUP_MES_MODE_MONT ADD CONSTRAINT JCSM_MODE_MONTAGE_CSM_ID_FK FOREIGN KEY(JCSMMMEDS_ID) REFERENCES EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID) ;               
ALTER TABLE EDS_J_PSA_MES_SUP_CBCD ADD CONSTRAINT JPMST_CRT_BLO_DYSF_PMST_FK FOREIGN KEY(JPMSCBCDEDS_ID) REFERENCES EDS_PSA_MESURE_SUPPLY(PMSEDS_ID) ;            
ALTER TABLE EDS_GROUND ADD CONSTRAINT G_GRCEDS_ID_FK FOREIGN KEY(G_GRCEDS_ID) REFERENCES EDS_GROUND_ROBUST_CURENT(GRC_ID) ;       
ALTER TABLE EDS_J_CONS_SUP_TH_CRT_MST ADD CONSTRAINT JCST_CRT_MISE_S_U_CMST_FK FOREIGN KEY(JCSTCMSTEDS_CMST_ID) REFERENCES EDS_COURANT_MISE_SOUS_TENSION(CMSTEDS_ID) ;            
ALTER TABLE EDS_CURRENT_STATEMENT ADD CONSTRAINT CURRENT_STAT_SFK FOREIGN KEY(CS_SEDS_ID) REFERENCES EDS_SUPPLY(SEDS_ID) ;        
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_RE_CONSOLIDATE_ID_FK FOREIGN KEY(HVFD_RE_CONSOLIDATE_ID) REFERENCES EDS_VALIDATION(V_ID) ;          
ALTER TABLE EDS_LOW_VALIDATION_FORM_DATA ADD CONSTRAINT LVFD_CONSOLIDATED_ID_FK FOREIGN KEY(LVFD_CONSOLIDATED_ID) REFERENCES EDS_VALIDATION(V_ID) ;               
ALTER TABLE EDS_EDS ADD CONSTRAINT EDS_S_ID_FK FOREIGN KEY(EDS_S_ID) REFERENCES EDS_SUPPLIER(S_ID) ;              
ALTER TABLE EDS_EDS ADD CONSTRAINT EDS_MODIF_USER_ID_FK FOREIGN KEY(EDS_MODIF_USER_ID) REFERENCES EDS_USER(U_ID) ;
ALTER TABLE EDS_J_CONS_SUP_MES_CBCD ADD CONSTRAINT JCSM_CRT_LOK_CRT_DYS_MES_FK FOREIGN KEY(JCSMCBCDEDS_CBCD_ID) REFERENCES EDS_CRT_BLOQUE_CRT_DYSF(CBCDEDS_ID) ;  
ALTER TABLE EDS_J_CONS_SUP_MES_CAA ADD CONSTRAINT JCSM_CRT_APPEL_ACT_CSM_FK FOREIGN KEY(JCSMCAAEDS_ID) REFERENCES EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID) ;      
ALTER TABLE EDS_J_CONS_SUP_MES_MODE_PARC ADD CONSTRAINT JCSM_MODE_PARC_MP_ID_FK FOREIGN KEY(JCSMMPEDS_MP_ID) REFERENCES EDS_MODE_PARC(MPEDS_ID) ; 
ALTER TABLE EDS_J_CONS_SUP_TH_CAA ADD CONSTRAINT JCST_CRT_APPELLE_ACT_CAA_FK FOREIGN KEY(JCSTCAAEDS_CAA_ID) REFERENCES EDS_CRT_APPELLE_ACTIVATION(CAAEDS_ID) ;    
ALTER TABLE EDS_CURRENT_STATEMENT ADD CONSTRAINT CURRENT_STATEMENT_MAFD_FK FOREIGN KEY(CS_MAFD_ID) REFERENCES EDS_MISSION_ACTN_FORM_DATA(MAFD_ID) ;               
ALTER TABLE EDS_PSA_MESURE_SUPPLY ADD CONSTRAINT PMSEDS_QCF_FK FOREIGN KEY(PMSEDS_QCF) REFERENCES EDS_QCF(QCF_ID) ;               
ALTER TABLE EDS_J_CONS_GROUND_CRT_MST ADD CONSTRAINT JCG_CRT_MISE_S_U_CST_FK FOREIGN KEY(JCGCMSTEDS_ID) REFERENCES EDS_GROUND_CONSOLIDATE(GEDS_ID) ;              
ALTER TABLE EDS_PROJECT_ROBUST_CURENT ADD CONSTRAINT PRC_PCEDS_ID_FK FOREIGN KEY(PRC_RCEDS_ID) REFERENCES EDS_ROBUST_CURENT_FORM_DATA(RCEDS_ID) ; 
ALTER TABLE EDS_PROJECT_CONS_CURENT ADD CONSTRAINT PC_P_ID_FK FOREIGN KEY(PCC_P_ID) REFERENCES EDS_PROJECT(P_ID) ;
ALTER TABLE EDS_CONSD_SUP_VOLT_FORM_DATA ADD CONSTRAINT CSV_EDS_ID_FK FOREIGN KEY(CSV_EDS_ID) REFERENCES EDS_EDS(EDS_ID) ;        
ALTER TABLE EDS_PROJECT_PERIMETER ADD CONSTRAINT PPE_PE_ID_FK FOREIGN KEY(PPE_PE_ID) REFERENCES EDS_PERIMETER(PE_ID) ;            
ALTER TABLE EDS_GROUND ADD CONSTRAINT G_RCEDS_ID_FK FOREIGN KEY(G_RCEDS_ID) REFERENCES EDS_ROBUST_CURENT_FORM_DATA(RCEDS_ID) ;    
ALTER TABLE EDS_PROJECT_PRIMARY_CURENT ADD CONSTRAINT PPC_PCEDS_ID_FK FOREIGN KEY(PPC_PCEDS_ID) REFERENCES EDS_PRIMARY_CURENT(PCEDS_ID) ;         
ALTER TABLE EDS_SUPPLY ADD CONSTRAINT S_RCEDS_ID_FK FOREIGN KEY(S_RCEDS_ID) REFERENCES EDS_ROBUST_CURENT_FORM_DATA(RCEDS_ID) ;    
ALTER TABLE EDS_SUPPLY ADD CONSTRAINT S_PMSEDS_ID_FK FOREIGN KEY(S_PMSEDS_ID) REFERENCES EDS_PSA_MESURE_SUPPLY(PMSEDS_ID) ;       
ALTER TABLE EDS_SUPPLY ADD CONSTRAINT S_RSEDS_ID_FK FOREIGN KEY(S_RSEDS_ID) REFERENCES EDS_ROBUST_SUPPLY(RSEDS_ID) ;              
ALTER TABLE EDS_PROJECT ADD CONSTRAINT P_W_ID_THIRD_STAGE_FK FOREIGN KEY(P_W_ID_THIRD_STAGE) REFERENCES EDS_WORDING(W_ID) ;       
ALTER TABLE EDS_J_PSA_MES_SUP_CAA ADD CONSTRAINT JPMST_CRT_APPEL_ACT_PMST_FK FOREIGN KEY(JPMSCAAEDS_ID) REFERENCES EDS_PSA_MESURE_SUPPLY(PMSEDS_ID) ;             
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_DBEES_CLOSED_ID_FK FOREIGN KEY(HVFD_DBEES_CLOSED_ID) REFERENCES EDS_VALIDATION(V_ID) ;              
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_DBEED_CLOSED_ID_FK FOREIGN KEY(HVFD_DBEED_CLOSED_ID) REFERENCES EDS_VALIDATION(V_ID) ;              
ALTER TABLE EDS_J_CONS_SUP_MES_RVROI ADD CONSTRAINT JCSM_RVROI_RVROIEDS_ID_FK FOREIGN KEY(JCSMRVROIEDS_ID) REFERENCES EDS_RVROI(RVROIEDS_ID) ;    
ALTER TABLE EDS_J_CONS_SUP_TH_MODE_PARC ADD CONSTRAINT JCST_MODE_PARC_MP_ID_FK FOREIGN KEY(JCSTMPEDS_MP_ID) REFERENCES EDS_MODE_PARC(MPEDS_ID) ;  
ALTER TABLE EDS_J_CONS_GROUND_CBCD ADD CONSTRAINT JCG_CRT_LOK_CRT_DYS_CST_FK FOREIGN KEY(JCGCBCDEDS_ID) REFERENCES EDS_GROUND_CONSOLIDATE(GEDS_ID) ;              
ALTER TABLE EDS_J_CONS_SUP_MES_CBCD ADD CONSTRAINT JCSM_CRT_DYSF_MES_CSM_FK FOREIGN KEY(JCSMCBCDEDS_ID) REFERENCES EDS_CONSOLIDATE_SUPPLY_MESURE(CSMEDS_ID) ;     
ALTER TABLE EDS_HIGH_VALIDATION_FORM_DATA ADD CONSTRAINT HVFD_DBEED_CONS_FK FOREIGN KEY(HVFD_DBEED_CONSOLIDATE_ID) REFERENCES EDS_VALIDATION(V_ID) ;              
ALTER TABLE EDS_ROBUST_CURENT_FORM_DATA ADD CONSTRAINT RCEDS_EDS_ID_FK FOREIGN KEY (RCEDS_EDS_ID) REFERENCES EDS_EDS (EDS_ID);
