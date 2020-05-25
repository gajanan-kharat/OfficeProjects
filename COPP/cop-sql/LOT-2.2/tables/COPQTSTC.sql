-- SPECIAL TEST CONDITION
CREATE TABLE COPQTSTC(
        ID                  BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
        VEHICLE_FILE_ID     BIGINT(20)  ,
        USER_ID             BIGINT(20)  ,
        UNIT_ID             BIGINT(20)  ,
        LABEL               VARCHAR(255),
        VALUE               VARCHAR(255),
        CREATION_DATE       DATETIME NOT NULL
);