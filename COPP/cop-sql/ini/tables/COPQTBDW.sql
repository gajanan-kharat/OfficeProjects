CREATE TABLE COPQTBDW (
  ID           BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
  LABEL        VARCHAR(255) DEFAULT NULL,
  TVV_RULE_ID  BIGINT(20) UNIQUE DEFAULT NULL
  );