CREATE TABLE COPQTVPL
(

ID BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
LABEL VARCHAR(255),
VERSION VARCHAR(255),
DESCRIPTION VARCHAR(255),
EMS_ID BIGINT(20),
TVV_ID BIGINT(20),
STATUS VARCHAR(45)

)