CREATE TABLE COPQTENG (
  ENGINE_ID         BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
  TVV_RULE_ID_B0C   BIGINT(20) UNIQUE DEFAULT NULL ,
  TVV_RULE_ID_DOC   BIGINT(20) DEFAULT NULL ,
  FUELINJECTION_ID  BIGINT(20) DEFAULT NULL ,
  FUELTYPE_ID       INT(11) DEFAULT NULL ,
  LABEL             VARCHAR(255) DEFAULT NULL ,
  LABEL_DEROGATION  VARCHAR(255) DEFAULT NULL ,
  POWER_CV          VARCHAR(255) DEFAULT NULL ,
  POWER_KW          VARCHAR(255) DEFAULT NULL ,
  TORQUE            VARCHAR(255) DEFAULT NULL 
  
);
