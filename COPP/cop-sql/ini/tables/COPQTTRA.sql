CREATE
    TABLE COPQTTRA(
	TRACEABILITY_ID			BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,    
	USER_ID 			    VARCHAR(255) ,
	USER_PROFILE 			VARCHAR(255) ,
	DESCRIPTION 	  		VARCHAR(4000) ,
	NEW_VALUE				VARCHAR(4000) ,
	OLD_VALUE				VARCHAR(4000) ,
	SCREEN_ID				VARCHAR(255),
	CREATION_DATE		    DATETIME NOT NULL ,
	UPDATE_DATE			    DATETIME NOT NULL
);