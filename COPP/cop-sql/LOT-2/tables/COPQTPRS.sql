CREATE TABLE COPQTPRS (
  ID 					BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
  VEHICLE_FILE_ID		BIGINT(20)	 ,
  BENCH_CELL			VARCHAR(255) ,
  IN_QUARANTINE			BOOLEAN 	 ,
  KEPT_IN_STAT_SAMPLE	BOOLEAN		 ,
  OBD_TEST				VARCHAR(255) ,
  SET_ORDER				INTEGER		 ,
  CREATION_DATE 		DATETIME     ,
  UPDATE_DATE 			DATETIME     ,
  STATISTICAL_ORDER     INTEGER      ,
  SAMPLE_ID             BIGINT(20)
);