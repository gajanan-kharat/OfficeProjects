CREATE TABLE COPQTVTD (
  ID BIGINT(20)			 	UNIQUE NOT NULL AUTO_INCREMENT,
  DATATYPE 					VARCHAR(255),
  VALUE 					VARCHAR(255),
  LABEL 					VARCHAR(255) ,
  ESDEPENDENT_DATALIST_ID 	BIGINT(20),
  VALUED_DATALIST_ID 		BIGINT(20),
  UNIT_ID 					BIGINT(20),
  GENERIC_DATA_ID 			BIGINT(20)
);

