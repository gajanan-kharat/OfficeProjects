CREATE TABLE  COPQTREC (
   ID BIGINT(20) 		UNIQUE NOT NULL AUTO_INCREMENT,
   LABEL 				VARCHAR(255) DEFAULT NULL,
   VERSION 				VARCHAR(255) DEFAULT NULL,
   DESCRIPTION  		VARCHAR(255) DEFAULT NULL,
   REGULATIONGROUP_ID 	BIGINT(20) DEFAULT NULL  
)