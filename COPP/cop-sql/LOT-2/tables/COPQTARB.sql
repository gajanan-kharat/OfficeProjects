CREATE 
    TABLE COPQTARB(
        ID 				        BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
        IS_OPEN                 BOOLEAN     ,
        FUEL_ID                 BIGINT(20)  ,
        PROJECT_CODE_FAMILY_ID  BIGINT(20)  ,
        TYPE_OF_TEST_ID         BIGINT(20)  ,
        ARCHIVE_BOX_NUMBER      BIGINT(20) UNIQUE,
        CLOSING_DATE            DATETIME   , 
        OPENING_DATE            DATETIME NOT NULL,   
        MODEL_YEAR              VARCHAR(255)
);