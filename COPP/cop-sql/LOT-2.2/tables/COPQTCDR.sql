-- CONDITIONING RESULT
CREATE TABLE COPQTCDR(
        ID                  BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
        USER_ID             BIGINT(20)  ,
        VALUE               VARCHAR(255),
        COMMENT             VARCHAR(255),
        CREATION_DATE       DATETIME NOT NULL,
        UPDATION_DATE       DATETIME NOT NULL
);