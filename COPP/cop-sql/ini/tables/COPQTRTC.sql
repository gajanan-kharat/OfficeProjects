CREATE TABLE COPQTRTC (
   ID BIGINT(20) 		UNIQUE NOT NULL AUTO_INCREMENT,
   LABEL 				VARCHAR(255) DEFAULT NULL,
   DEFAULT_VALUE 		VARCHAR(255) DEFAULT NULL,
   DATA_TYPE 			VARCHAR(255) DEFAULT NULL,
   RGVALUEDTCL_ID  		BIGINT(20) DEFAULT NULL
)