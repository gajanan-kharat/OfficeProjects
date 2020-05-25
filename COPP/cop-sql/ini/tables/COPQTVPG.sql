CREATE TABLE COPQTVPG
(
ID 					BIGINT(20) 	UNIQUE NOT NULL AUTO_INCREMENT,
MAXDVALUE 			DOUBLE(49,4),
MAXDVALUERULE 		VARCHAR(255),
MINDVALUE 			DOUBLE(49,4),
MINDVALUERULE 		VARCHAR(255),
LIMITVALUE 			VARCHAR(255),
LABEL 				VARCHAR(255),
LABEL_ID 			BIGINT(20),
PGLIST_ID 			BIGINT(20),
POLLUTANT_ID 		BIGINT(20)
)