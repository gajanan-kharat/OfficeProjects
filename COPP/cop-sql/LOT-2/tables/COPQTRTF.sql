CREATE TABLE COPQTRTF (
  ID 				BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
  USER_ID			BIGINT(20)	  ,
  OBSERVATION		VARCHAR(255)  ,
  RESERVATION		VARCHAR(255)  ,
  REQUESTER			VARCHAR(255)  
);