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
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionary;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryRepository;
import com.inetpsa.poc00.genomeBatch.utility.GenomeUtil;

/**
 * This Process the KMAT FAM Dictionary File records for Genome Batch.
 */
public class KmatFamDictionaryFile implements GenomeConstant {

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(KmatFamDictionaryFile.class);

	/**
	 * getKmatFamDictionaryFile - Process KMAT FAM Dictionary input file.
	 *
	 * @param file - KMAT FAM Dictionary input file
	 * @param dictionaryRepo the dictionary repo
	 * @return List<GenomeLCDVDictionary>
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void getKmatFamDictionaryFile(File file,GenomeLCDVDictionaryRepository dictionaryRepo) throws  IOException {

		logger.info("========================================================================================================================");
		logger.info("\n*** STARTED : Processing for KMAT FAM DICTIONARY File");

		List<GenomeLCDVDictionary> lcdvDicList = null;

		GenomeLCDVDictionary lcdvDic = null;

		try(FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {

			String line;

			// FOR HEADER VARIABLES, HEADER FLAG
			Boolean headerRead = Boolean.TRUE;

			lcdvDicList = new ArrayList<>();

			while ((line = br.readLine()) != null) {
				// FOR HEADER
				if (!"".equals(line.trim()) && line.substring(0, 9).contains(NAME_OF_TREATMENT) && headerRead) {
					DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
					logger.info("\n*** Header of Kmat Fam Dictionary File : " + df.parse(line.substring(9, 23)));
					headerRead = Boolean.FALSE;
					continue;
				}

				// FOR RECORD TYPE
				if (!"".equals(line.trim()) && line.substring(0, const_10).contains(KMAT_FAM_RECORD_TYPE)) {

					lcdvDic = new GenomeLCDVDictionary();
					lcdvDic.setKmat(line.substring(const_10, 28).trim());
					lcdvDic.setDictionaryValue(line.substring(28, 47).trim());

					lcdvDicList.add(lcdvDic);
				} else {
					logger.info("Not expected : {}", line);
				}
			}

		} catch (FileNotFoundException e) {
			logger.error("\n*** File does not Exists",e);

		} catch (IOException e) {
			logger.error("\n*** IO Exception Issue",e);

		} catch (ParseException e) {
			logger.error("\n*** Parsing Issue",e);

		} 

		logger.info("\n*** END : Processing for KMAT FAM DICTIONARY File, Contains LCDV Dictionary List of Size : {}", lcdvDicList != null ? lcdvDicList.size() : null);
		logger.info("========================================================================================================================");

		if (lcdvDicList != null && !lcdvDicList.isEmpty()) {
			
			DateTime batchStartTime = GenomeUtil.getTime();
			
			dictionaryRepo.saveOrUpdateGenomeDictionary(lcdvDicList);
			
			GenomeUtil.timeDuration(batchStartTime, GenomeUtil.getTime(), "Save KMAT_FAM Dictionary File Data");
		}

	}

}
