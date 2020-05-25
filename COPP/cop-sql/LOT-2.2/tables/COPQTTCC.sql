-- TEST CONDITION COMMENT
CREATE TABLE COPQTTCC(
        ID                      BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
        USER_ID                 BIGINT(20)  ,
        VEHICLE_FILE_ID         BIGINT(20)  ,
        VLD_TVVDEP_TCL_ID       BIGINT(20) ,
        VLD_ESDEP_TCL_ID        BIGINT(20) ,
        COMMENT                 VARCHAR(255)   ,
        UPDATE_DATE             DATETIME NOT NULL
);