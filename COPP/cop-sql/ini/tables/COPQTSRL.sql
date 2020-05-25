CREATE 
	TABLE COPQTSRL (
	ID 						BIGINT(20) UNIQUE NOT NULL AUTO_INCREMENT,
	AMOUNT_OR_PERCENTAGE 	VARCHAR(45) 	DEFAULT NULL ,
	AMOUNT_TYPE 			VARCHAR(45) 	DEFAULT NULL ,
	DESCRIPTION 			VARCHAR(150) 	DEFAULT NULL ,
	FREQUENCY 				INT(11) 		DEFAULT NULL ,
	HIGHER_LIMIT 			INT(11) 		DEFAULT NULL ,
	HIGHER_SYMBOL 			VARCHAR(45) 	DEFAULT NULL ,
	LOWER_LIMIT 			INT(11) 		DEFAULT NULL ,
	LOWER_SYMBOL 			VARCHAR(45) 	DEFAULT NULL ,
	NEEDED_AMOUNT 			DOUBLE 			DEFAULT NULL ,
	LABEL 					VARCHAR(45) 	DEFAULT NULL ,
	VERSION 				VARCHAR(45) 	DEFAULT NULL
	);
