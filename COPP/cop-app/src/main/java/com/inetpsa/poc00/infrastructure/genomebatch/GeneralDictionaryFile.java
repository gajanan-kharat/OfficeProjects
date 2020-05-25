package com.inetpsa.poc00.infrastructure.genomebatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
 * This Process the General Dictionary File records for Genome Batch.
 */
public class GeneralDictionaryFile implements GenomeConstant {

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(GeneralDictionaryFile.class);

	/**
	 * getGeneralDictionaryFile ,Process General Dictionary input file.
	 *
	 * @param file - Input General Dictionary File
	 * @param dictionaryRepo the dictionary repo
	 * @return GenomeLCDVDictionaryDto
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void getGeneralDictionaryFile(File file,GenomeLCDVDictionaryRepository dictionaryRepo) throws IOException {

		logger.info("========================================================================================================================");

		logger.info("\n*** Processing Started for General Dictionary File, {}", GeneralDictionaryFile.class);

		GenomeLCDVDictionaryDto lcdvDic = getGenomeLCDVDictionaryDto();

		try(FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {

			String line;

			List<GenomeLCDVCodeDto> lcdvCodeList = new ArrayList<GenomeLCDVCodeDto>();

			// FOR HEADER VARIABLES, HEADER FLAG
			Boolean headerFlag = Boolean.TRUE;

			while ((line = br.readLine()) != null && br.markSupported()) {

				// FOR HEADER
				if (headerFlag && !"".equals(line.trim()) && line.startsWith(STARTWITH_E) && line.substring(0, const_10).contains(NAME_OF_TREATMENT)) {

					headerFlag = Boolean.FALSE;

					DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);

					logger.info("\n*** Header of General Dictionary File : " + df.parse(line.substring(const_10, 24).trim()));

					continue;
				}

				GenomeLCDVCodeDto lcdvCode = new GenomeLCDVCodeDto();
				lcdvCode.setGenomeLCDVDictionaryDto(lcdvDic);

				if (!"".equals(line.trim()) && line.contains(E3_RECORD_TYPE)) {

					logger.debug("\n **** STARTING OF SET ****");

					resolveRecordTypeE34(br, line, lcdvCode);

					if ((line = br.readLine()) != null && !"".equals(line.trim()) && line.contains(E5_RECORD_TYPE)) {

						List<GenomeLCDVCodeValueDto> lcdvCodeValueList = resolveRecordTypeE56(br, line, lcdvCode);

						lcdvCode.setGenomeLcdvCodeValueListDto(lcdvCodeValueList);

						lcdvCodeList.add(lcdvCode);

					} else {
						logger.error("\n*** There is no E5 Record Type exists for E3 & E4 Set : {}", line);
						
					}

					lcdvDic.setGenomeLcdvCodeListDto(lcdvCodeList); // Associated with dictionary Code

					logger.debug("\n **** END OF SET ****");

				} else {
					logger.debug("\n*** E3 Record Expected");
					
				}

			}
		} catch (FileNotFoundException e) {

			logger.error("\n*** File Not Found Exception : ", e);
		} catch (IOException e) {

			logger.error("\n*** IO Exception : ", e);
		} catch (ParseException e) {
			
			logger.error("\n*** Issue while Parsing : {}", e);
		} 

		logger.info("\n*** END : Processing for General Dictionary File, Contains lCDV Dictionary Object  :  {}", GeneralDictionaryFile.class);
		logger.info("========================================================================================================================");

		if (lcdvDic != null) {
			
			DateTime batchStartTime = GenomeUtil.getTime();
			
			dictionaryRepo.saveorUpdateGenomeLCDVDictionary(lcdvDic, GENERAL_FILE_ID); 
		 
			GenomeUtil.timeDuration(batchStartTime, GenomeUtil.getTime(), "Save General Dictionary File Data");
		}

	}

	/**
	 * resolveRecordTypeE34 ,Process E3 and E4 Record type of input File.
	 *
	 * @param br - BufferReader
	 * @param line - current line
	 * @param lcdvCodeNameObj - Genome LCDV Code DTO
	 * @return void
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void resolveRecordTypeE34(BufferedReader br, String line, GenomeLCDVCodeDto lcdvCodeNameObj) throws IOException {

		// FOR RECORD TYPE E3
		if (line.substring(0, const_10).contains(E3_RECORD_TYPE)) {

			lcdvCodeNameObj.setCodeName(line.substring(const_10, 40).trim());

			br.mark(BUFFER_MARK); 
			
			// to Check if E3 has corresponding E4
			if ((line = br.readLine()) != null && line.substring(0, const_10).contains(E4_RECORD_TYPE)) {
				br.reset();
			} else {
				br.reset();
				logger.info("No E4 For this E3 : {}", line);

			}

			for (int count = 0; (line = br.readLine()) != null && line.startsWith(STARTWITH_E4); count++) {
				br.mark(BUFFER_MARK);

				if (Z2_IDENTIFIER == line.charAt(const_10)) {

					lcdvCodeNameObj.setZ2Label(line.substring(11).trim());
				} else if (FR_IDENTIFIER == line.charAt(const_10)) {

					lcdvCodeNameObj.setFrLabel(line.substring(11).trim());
				} else if (count >= 2) {

					logger.info("Only 2 Record Type can occur for a E3 Record Type, Around Line : {} ", line);
				} else {
					logger.info("Unexpected Character occurs : {}", line.charAt(const_10));

				}
			}

			br.reset();
		}
	}

	/**
	 * resolveRecordTypeE56 ,Process E5 and E6 Record type of input File.
	 *
	 * @param br - BufferReader
	 * @param line - Current line to read
	 * @param lcdvCode - GenomeLCDVCodeDto Object
	 * @return List<GenomeLCDVCodeValueDto>
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private List<GenomeLCDVCodeValueDto> resolveRecordTypeE56(BufferedReader br, String line, GenomeLCDVCodeDto lcdvCode) throws IOException {

		List<GenomeLCDVCodeValueDto> lcdvCodeValueList = new ArrayList<GenomeLCDVCodeValueDto>();

		Boolean firstTimeExecutionFlag = Boolean.TRUE;

		while (firstTimeExecutionFlag || (line = br.readLine()) != null && !"".equals(line.trim()) && line.contains(E5_RECORD_TYPE)) {

			firstTimeExecutionFlag = Boolean.FALSE;

			GenomeLCDVCodeValueDto genomeLcdvCodeValue = new GenomeLCDVCodeValueDto();
			genomeLcdvCodeValue.setGenomeLcdvCodeDto(lcdvCode);

			genomeLcdvCodeValue.setLcdvCodeValue(line.substring(40, 70).trim());

			br.mark(BUFFER_MARK); 
			
			// to Check if E5 has corresponding E6
			if ((line = br.readLine()) != null && line.substring(0, const_10).contains(E6_RECORD_TYPE)) {

				br.reset();
			} else {
				br.reset();
				logger.info("No E6 For this E5 : {}", line);
			}

			// Take from E6 Line
			while ((line = br.readLine()) != null && line.startsWith(STARTWITH_E6) && line.substring(0, const_10).contains(E6_RECORD_TYPE)) {
				br.mark(BUFFER_MARK);

				if (Z2_IDENTIFIER == line.charAt(const_10)) {

					genomeLcdvCodeValue.setZ2Label(line.substring(11, 100).trim());
				} else if (FR_IDENTIFIER == line.charAt(const_10)) {

					genomeLcdvCodeValue.setFrLabel(line.substring(11, 100).trim());
				} else {
					logger.info("Unexpected Character occurs : {}", line.charAt(const_10));
				}
			}

			lcdvCodeValueList.add(genomeLcdvCodeValue);

			if (line != null)
				br.reset();
		}

		if (line != null)
			br.reset();

		return lcdvCodeValueList;

	}

	/**
	 * getGenomeLCDVDictionaryDto ,Default Dictionary Object Creation.
	 *
	 * @return the genome lcdv dictionary dto
	 */
	public GenomeLCDVDictionaryDto getGenomeLCDVDictionaryDto() {
		return new GenomeLCDVDictionaryDto(DEFAULT_FRLABEL_GENERALDIC_FILE, DEFAULT_DICVAL_GENERALDIC_FILE, DEFAUTL_KMAT_GENERALDIC_FILE);
	}

}
