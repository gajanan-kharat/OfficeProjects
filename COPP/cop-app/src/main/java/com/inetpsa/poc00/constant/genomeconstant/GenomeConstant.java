package com.inetpsa.poc00.constant.genomeconstant;

/**
 * This interface contains the Constants being used in Genome Batch Code.
 */
public interface GenomeConstant {

	/** The Constant TVV_FILENAME. */
	// FILE NAME
	 String TVV_FILENAME = "TVV.dat";
	
	/** The Constant FAMILY_FILENAME. */
	 String FAMILY_FILENAME = "FAMILY_DICTIONARY.dat";
	
	/** The Constant GENERALDICTIONARY_FILENAME. */
	 String GENERALDICTIONARY_FILENAME = "GENERAL_DICTIONARY.dat";
	
	/** The Constant KMATFAM_FILENAME. */
	 String KMATFAM_FILENAME = "KMAT_FAM_DICTIONARY.dat";

	/** The Constant TVV_FILE_ID. */
	// FILE ID
	 String TVV_FILE_ID = "TVV";
	
	/** The Constant FAMILY_DIC_FILE_ID. */
	 String FAMILY_DIC_FILE_ID = "FAMILY";
	
	/** The Constant KMAT_FAM_FILE_ID. */
	 String KMAT_FAM_FILE_ID = "KMATFAM";
	
	/** The Constant GENERAL_FILE_ID. */
	 String GENERAL_FILE_ID = "GENERAL";

	/** The Constant NAME_OF_TREATMENT. */
	// HEADER FOR FILE CONTENT
	 String NAME_OF_TREATMENT = "EDI_DC40";

	/** The Constant DATE_FORMAT. */
	// DATE FORMAT
	 String DATE_FORMAT = "yyyyMMddHHmmss";
	
	/** The Constant DATE_FORMAT_2. */
	 String DATE_FORMAT_2 = "MM/dd/yyyy HH:mm:ss";

	/** The Constant E1_RECORD_TYPE. */
	// RECORD TYPE
	 String E1_RECORD_TYPE = "E1CLASSE";
	
	/** The Constant E2_RECORD_TYPE. */
	 String E2_RECORD_TYPE = "E2DESICL";
	
	/** The Constant E3_RECORD_TYPE. */
	 String E3_RECORD_TYPE = "E3CARACT";
	
	/** The Constant E4_RECORD_TYPE. */
	 String E4_RECORD_TYPE = "E4DESICA";
	
	/** The Constant E5_RECORD_TYPE. */
	 String E5_RECORD_TYPE = "E5VALEU";
	
	/** The Constant E6_RECORD_TYPE. */
	 String E6_RECORD_TYPE = "E6DESIVA";

	/** The Constant STARTWITH_E. */
	// RECORD TYPE STARTS WITH
	 String STARTWITH_E = "E";
	
	/** The Constant STARTWITH_E1. */
	 String STARTWITH_E1 = "E1";
	
	/** The Constant STARTWITH_E2. */
	 String STARTWITH_E2 = "E2";
	
	/** The Constant STARTWITH_E3. */
	 String STARTWITH_E3 = "E3";
	
	/** The Constant STARTWITH_E4. */
	 String STARTWITH_E4 = "E4";
	
	/** The Constant STARTWITH_E5. */
	 String STARTWITH_E5 = "E5";
	
	/** The Constant STARTWITH_E6. */
	 String STARTWITH_E6 = "E6";

	/** The Constant E1_TVV_RECORD_TYPE. */
	// TVV RECORD TYPE
	 String E1_TVV_RECORD_TYPE = "E1TABCONT";
	
	/** The Constant E2_TVV_RECORD_TYPE. */
	 String E2_TVV_RECORD_TYPE = "E2TABCONT";

	/** The Constant KMAT_FAM_RECORD_TYPE. */
	// KMAT FAM RECORD TYPE
	 String KMAT_FAM_RECORD_TYPE = "L1KMATDICO";

	/** The Constant DEFAULT_FRLABEL_GENERALDIC_FILE. DEFAULT VALUE FOR GENERAL DICTIONARY FILE OBJECT */
	 String DEFAULT_FRLABEL_GENERALDIC_FILE = "Dictionnaire général";
	
	/** The Constant DEFAUTL_KMAT_GENERALDIC_FILE. */
	 String DEFAUTL_KMAT_GENERALDIC_FILE = "ZZZZ";
	
	/** The Constant DEFAULT_DICVAL_GENERALDIC_FILE. */
	 String DEFAULT_DICVAL_GENERALDIC_FILE = "DICO_ZZZZ";

	/** The Constant FR_IDENTIFIER. */
	 char FR_IDENTIFIER = 'F';
	
	/** The Constant Z2_IDENTIFIER. */
	 char Z2_IDENTIFIER = '(';
	 
	 /** The buffer mark. */
 	int BUFFER_MARK = 150;
 	
	/** The line max length. */
	int LINE_MAX_LENGTH = 100;
	
	/** The const_10. */
	int const_10 = 10;
	
	/** The csv file format. */
	String CSV_FILE_FORMAT = ".csv";

}
