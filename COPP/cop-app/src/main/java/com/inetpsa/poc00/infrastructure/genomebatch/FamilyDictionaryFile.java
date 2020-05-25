package com.inetpsa.poc00.infrastructure.genomebatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.constant.genomeconstant.GenomeConstant;
import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeDto;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValueDto;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryDto;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryRepository;
import com.inetpsa.poc00.genomeBatch.utility.GenomeUtil;

/**
 * This Process the Family Dictionary File records for Genome Batch.
 */
public class FamilyDictionaryFile implements GenomeConstant {

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(FamilyDictionaryFile.class);

	/** The line. */
	private String line;

	/**
	 * Get the List of GenomeLCDVDictionaryDto from Family Dictionary input File for Batch Processing.
	 * 
	 * @param file the file
	 * @param dictionaryRepo the dictionary repo
	 * @return the List of GenomeLCDVDictionaryDto to save
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void getFamilyDictionaryFile(File file, GenomeLCDVDictionaryRepository dictionaryRepo) throws IOException {

		logger.info("========================================================================================================================");
		logger.info("*** Processing Started for Family Dictionary File, {}", FamilyDictionaryFile.class);

		List<GenomeLCDVDictionaryDto> genomeLcdvDictionaryList = new ArrayList<GenomeLCDVDictionaryDto>();

		try(FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {

			// FOR HEADER VARIABLES, HEADER FLAG
			Boolean headerFlag = Boolean.TRUE;

			while ((line = br.readLine()) != null && br.markSupported()) {

				// FOR HEADER
				if (headerFlag && !"".equals(line.trim()) && line.startsWith(STARTWITH_E) && line.substring(0, const_10).contains(NAME_OF_TREATMENT)) {

					DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);

					logger.debug("\n*** Header of Family Dictionary File : " + df.parse(line.substring(const_10, 24).trim()));

					headerFlag = Boolean.FALSE;

					continue;
				}

				// Get Dictionary Object from E1 and E2
				if (!"".equals(line.trim()) && line.contains(E1_RECORD_TYPE)) {

					GenomeLCDVDictionaryDto genomeLcdvDictionary = new GenomeLCDVDictionaryDto();

					resolveRecordTypeE12(br, genomeLcdvDictionary);

					genomeLcdvDictionaryList.add(genomeLcdvDictionary);

				}
			}
		} catch (IOException e) {
			logger.error("\n*** IO Exception : {} ", e);

		} catch (ParseException e) {
			logger.error("\n*** Parse Exception : {} ", e);

		} 

		logger.info("\n*** END : Processing for Family Dictionary File, Returing lcdvDicList of Size : {} , {}", genomeLcdvDictionaryList != null ? genomeLcdvDictionaryList.size() : null, FamilyDictionaryFile.class);
		logger.info("========================================================================================================================");

		if (genomeLcdvDictionaryList != null && !genomeLcdvDictionaryList.isEmpty()) {

			DateTime batchStartTime = GenomeUtil.getTime();

			dictionaryRepo.saveGenomeLCDVDictionary(genomeLcdvDictionaryList, FAMILY_DIC_FILE_ID);

			GenomeUtil.timeDuration(batchStartTime, GenomeUtil.getTime(), "Save Family Dictionary File Data");
		}

	}

	/**
	 * Read the E1 and E2 record type from file and process further.
	 * 
	 * @param br - BufferReader, Object through which lines of file read
	 * @param genomeLcdvDictionary - GenomeLCDVDictionaryDto Object
	 * @return void
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void resolveRecordTypeE12(BufferedReader br, GenomeLCDVDictionaryDto genomeLcdvDictionary) throws IOException {

		Boolean firstTimeExecutionFlag = Boolean.TRUE;

		List<GenomeLCDVCodeDto> genomeLcdvCodeSet = new ArrayList<>();

		outerloop: while (firstTimeExecutionFlag || (line = br.readLine()) != null && !"".equals(line.trim()) && line.contains(E1_RECORD_TYPE)) {
			firstTimeExecutionFlag = Boolean.FALSE;

			genomeLcdvDictionary.setDictionaryValue(line.substring(const_10, 28).trim());

			br.mark(BUFFER_MARK);

			while ((line = br.readLine()) != null && line.startsWith(STARTWITH_E2) && line.substring(0, const_10).contains(E2_RECORD_TYPE)) {
				br.mark(BUFFER_MARK);

				if (FR_IDENTIFIER == line.charAt(const_10)) {
					genomeLcdvDictionary.setFrLabel(line.substring(11, LINE_MAX_LENGTH).trim());

				} else {
					logger.debug("FR_Identifier Not Found");
					logger.info("\n*** Dictionary_Label_C2 is not Available");
				}

				// Checking for continuously E1 & E2
				if (line != null && (line = br.readLine()) != null && !"".equals(line.trim()) && line.contains(E1_RECORD_TYPE)) {
					br.reset();
					logger.info("Continuously E1 & E2 Record Type Occurred : {}", line);
					break outerloop;
				}

			}

			br.reset();
		}

		// Check if E3 is present
		br.mark(BUFFER_MARK);

		if (line != null && line.contains(E3_RECORD_TYPE)) {

			br.reset();
			resolveRecordTypeE34(br, genomeLcdvCodeSet, genomeLcdvDictionary);
		} else {

			br.reset();
			logger.info("\n*** E3 Record is missing, Expected E3 Record Type : {}", line);

		}

		// Set Code Object
		genomeLcdvDictionary.setGenomeLcdvCodeListDto(genomeLcdvCodeSet);

	}

	/**
	 * Read the E3 and E4 record type from file and process further.
	 * 
	 * @param br - BufferReader the Object through which line of file read,
	 * @param genomeLcdvCodeList - Data will be put in this list of GenomeLCDVDictionaryDto
	 * @param genomeLcdvDictionary - GenomeLCDVDictionaryDto Object
	 * @return void
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void resolveRecordTypeE34(BufferedReader br, List<GenomeLCDVCodeDto> genomeLcdvCodeList, GenomeLCDVDictionaryDto genomeLcdvDictionary) throws IOException {

		Boolean firstTimeExecutionFlag = Boolean.TRUE;
		GenomeLCDVCodeDto genomeLcdvCode;

		while (firstTimeExecutionFlag || (line = br.readLine()) != null && !"".equals(line.trim()) && line.contains(E3_RECORD_TYPE)) {

			firstTimeExecutionFlag = Boolean.FALSE;

			genomeLcdvCode = new GenomeLCDVCodeDto();
			genomeLcdvCode.setGenomeLCDVDictionaryDto(genomeLcdvDictionary);

			genomeLcdvCode.setCodeName(line.substring(const_10, 40).trim());

			br.mark(BUFFER_MARK);

			// TAKE FROM E4
			while ((line = br.readLine()) != null && line.startsWith(STARTWITH_E4) && line.substring(0, const_10).contains(E4_RECORD_TYPE)) {
				br.mark(BUFFER_MARK);

				if (Z2_IDENTIFIER == line.charAt(const_10)) {

					genomeLcdvCode.setZ2Label(line.substring(11, LINE_MAX_LENGTH).trim());
				} else if (FR_IDENTIFIER == line.charAt(const_10)) {

					genomeLcdvCode.setFrLabel(line.substring(11, LINE_MAX_LENGTH).trim());
				} else {

					logger.info("\n*** Proper identifier is not present for E4 Record Type");
				}
			}

			br.reset();

			genomeLcdvCodeList.add(genomeLcdvCode);

			List<GenomeLCDVCodeValueDto> genomeLcdvCodeValueSet = new ArrayList<>();
			genomeLcdvCode.setGenomeLcdvCodeValueListDto(genomeLcdvCodeValueSet);

			br.reset();

			if ((line = br.readLine()) != null && !"".equals(line.trim()) && line.contains(E5_RECORD_TYPE)) {

				resolveRecordTypeE56(br, genomeLcdvCodeValueSet, genomeLcdvCode);

				if (line != null && line.startsWith(STARTWITH_E1))
					break;

			} else {
				logger.info("\n*** E5 Record is missing, Expected E5 Record Type : {}", line);

			}
		}
	}

	/**
	 * Read the E5 and E6 record type from file and process further.
	 * 
	 * @param br - BufferReader the Object through which line of file read,
	 * @param lcdvCodeValueList - Data will be put in this list of GenomeLCDVDictionaryDto
	 * @param genomeLcdvCode - lcdv code Value list for passed genomelcdvCode
	 * @return List<GenomeLCDVCodeValueDto>
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private List<GenomeLCDVCodeValueDto> resolveRecordTypeE56(BufferedReader br, List<GenomeLCDVCodeValueDto> lcdvCodeValueList, GenomeLCDVCodeDto genomeLcdvCode) throws IOException {

		Boolean firstTimeExecutionFlag = Boolean.TRUE;

		while (firstTimeExecutionFlag || (line = br.readLine()) != null && !"".equals(line.trim()) && line.contains(E5_RECORD_TYPE)) {

			if (line.trim().isEmpty()) {
				continue;
			}

			firstTimeExecutionFlag = Boolean.FALSE;

			GenomeLCDVCodeValueDto genomeLcdvCodeValue = new GenomeLCDVCodeValueDto();
			genomeLcdvCodeValue.setGenomeLcdvCodeDto(genomeLcdvCode);

			genomeLcdvCodeValue.setLcdvCodeValue(line.substring(40, 70).trim());

			br.mark(BUFFER_MARK); // to Check if E5 has corresponding E6

			if ((line = br.readLine()) != null && line.substring(0, const_10).contains(E6_RECORD_TYPE)) {
				br.reset();
			} else {
				br.reset();
				logger.info("\n*** No E6 For this E5 : {}", line);
			}

			// Take from E6 Line
			while ((line = br.readLine()) != null && line.startsWith(STARTWITH_E6) && line.substring(0, const_10).contains(E6_RECORD_TYPE)) {

				if (line.trim().isEmpty())
					continue;

				br.mark(BUFFER_MARK);

				if (Z2_IDENTIFIER == line.charAt(const_10)) {
					genomeLcdvCodeValue.setZ2Label(line.substring(11).trim());

				} else if (FR_IDENTIFIER == line.charAt(const_10)) {
					genomeLcdvCodeValue.setFrLabel(line.substring(11).trim());

				} else {
					logger.info("\n*** Unexpected Character occurs : {}", line.charAt(const_10));

				}
			}

			if (line != null) {
				br.reset();
			}

			lcdvCodeValueList.add(genomeLcdvCodeValue);

			if (line != null && line.startsWith(STARTWITH_E1))
				break;

		}

		if (line != null)
			br.reset();

		return lcdvCodeValueList;

	}
}