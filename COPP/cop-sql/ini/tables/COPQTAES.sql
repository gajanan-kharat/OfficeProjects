CREATE TABLE COPQTAES
(
ID BIGINT(20) UNIQUE AUTO_INCREMENT,
DESCRIPTION VARCHAR(255),
LABEL VARCHAR(255),
VERSION VARCHAR(255),
LCDVCODE_ID BIGINT(20),
TVV_RULE_ID BIGINT(20) UNIQUE DEFAULT NULL,
STATUS_ID BIGINT(20) DEFAULT NULL

);