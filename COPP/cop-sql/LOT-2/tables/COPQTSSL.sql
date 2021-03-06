CREATE TABLE COPQTSSL (
  ID 					BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
  MAXIMUM_SIZE			INT(11),
  NO_OF_ELEMENTS		INT(11),
  STANDARD_DEVIATION	DOUBLE(49,4),
  GLOBAL_DECISION		VARCHAR(255),
  CLOSED				BOOLEAN,
  TECHNICALCASE_ID		BIGINT(20),
  FACTORY_ID			BIGINT(20),
  TYPE_OF_TEST_ID       BIGINT(20)
 
);
