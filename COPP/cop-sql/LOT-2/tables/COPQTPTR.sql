CREATE TABLE COPQTPTR (
  ID 						BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
  POLLGAS_RESULT_SET_ID		BIGINT(20)   ,
  TVV_Valued_POLL_GAS_LIMIT BIGINT(20)   ,
  VALUE 					VARCHAR(255) ,
  RETEST 					BOOLEAN      ,
  STATISTICAL_RESULT		DOUBLE		 ,
  STATISTICAL_DECISION  	VARCHAR(255) ,
  CREATION_DATE 			DATE         ,
  UPDATE_DATE 				DATE 
);