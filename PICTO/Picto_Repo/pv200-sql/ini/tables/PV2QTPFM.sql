CREATE
    TABLE PV2QTPFM(
		ID BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
        REF_NUM VARCHAR(4) UNIQUE NOT NULL,
		NAME VARCHAR(255) NOT NULL,
		INFO_TYPE VARCHAR(255) NOT NULL,		
		INFO_LABEL_FR VARCHAR(2000) NULL,
		INFO_LABEL_EN VARCHAR(2000) NULL,
		VALIDATION_LEVEL VARCHAR(255) NOT NULL,
		ADMIN_INFO VARCHAR(2000) NULL,		
		FUNCTION_FR  VARCHAR(2000) NULL,
		FUNCTION_EN  VARCHAR(2000) NULL,
		TYPE_ID BIGINT(20)  NULL,
		REF_CHARTE VARCHAR(3) NULL,
		IS_COMMAND TINYINT(1) DEFAULT NULL,
		COMMAND_INFO VARCHAR(255) NULL,	
		IS_RHN_WITNESS TINYINT(1) DEFAULT NULL,	
		IS_RHN_ACTIVATION TINYINT(1) DEFAULT NULL,
		IS_RHN_ALERT TINYINT(1) DEFAULT NULL,
		IS_RHN_DEFAULT TINYINT(1) DEFAULT NULL,	
		RHN_INFO_FR VARCHAR(2000) NULL,
		RHN_INFO_EN VARCHAR(2000) NULL,		
		CATEGORY_ID BIGINT(20) NOT NULL, 
		KEYWORD_FR VARCHAR(2000) NOT NULL,
		KEYWORD_EN VARCHAR(2000) NOT NULL
    );
   