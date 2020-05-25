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
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;
import com.inetpsa.poc00.genomeBatch.utility.GenomeUtil;

/**
 * This Process the TVV File records for Genome Batch.
 */
public class TVVFile implements GenomeConstant {

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(TVVFile.class);

	/** The line. */
	private String line;

	/**
	 * getTvvRuleFile ,Process TVV Rule input file.
	 *
	 * @param file - input TVV input File
	 * @param tvvRuleRepo the tvv rule repo
	 * @return List<GenomeTVVRule>
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void getTvvRuleFile(File file,GenomeTVVRuleRepository tvvRuleRepo) throws IOException {

		logger.info("========================================================================================================================");
		logger.info("\n*** STARTED : Processing for TVV File");

		GenomeTVVRule tvvRule;

		List<GenomeTVVRule> tvvRuleList = null;

		try(FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {

			// FOR HEADER VARIABLES, HEADER FLAG
			Boolean headerRead = Boolean.TRUE;

			String kmat;

			tvvRuleList = new ArrayList<>();

			for (; ((line = br.readLine()) != null) && br.markSupported();) {

				// FOR HEADER
				if (!"".equals(line.trim()) && line.startsWith(STARTWITH_E) && line.substring(0, const_10).contains(NAME_OF_TREATMENT) && headerRead) {

					DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);

					logger.debug("\n*** Header of TVV File : " + df.parse(line.substring(const_10, 24).trim()));

					headerRead = Boolean.FALSE;

					continue;
				}

				// FOR RECORD TYPE E1
				if (!"".equals(line.trim()) && line.startsWith(STARTWITH_E1) && line.substring(0, const_10).contains(E1_TVV_RECORD_TYPE)) {

					logger.debug("**** START OF SET ****");
					logger.debug("Line :  {}", line);

					kmat = line.substring(28, 46).trim();

					List<String> e2Records = new ArrayList<>();

					getE2RecordsList(br, e2Records);

					// FOR RECORD TYPE E2
					for (String e2Line : e2Records) {

						tvvRule = new GenomeTVVRule();

						tvvRule.setRuleId(e2Line.substring(const_10, 15).trim());
						tvvRule.setLcdvCodeName(e2Line.substring(15, 45).trim());
						tvvRule.setLcdvCodeValue(e2Line.substring(45, 75).trim());
						tvvRule.setKmat(kmat);

						tvvRuleList.add(tvvRule);
					}

					logger.debug("**** END OF SET ****");
				} else {
					logger.info("In TVV File, These lines are not Expected : {} ", line);
				}
			}

		} catch (FileNotFoundException e) {
			logger.error("\n*** File does not Exists :", e);
		} catch (IOException e) {
			logger.error("\n*** IO Exception : ",e);
		} catch (ParseException e) {
			logger.error("\n*** Parsing Issue : ", e);
		} 

		logger.info("\n*** END : Processing for TVV File, Contains TVV Rule List of Size : {}", tvvRuleList != null ? tvvRuleList.size() : "tvvRuleList Object is Null");
		logger.info("========================================================================================================================");

		if(tvvRuleList!= null &&  !tvvRuleList.isEmpty()) {
			
				DateTime batchStartTime = GenomeUtil.getTime();
			
				tvvRuleRepo.saveOrUpdateGenomeTVVRule(tvvRuleList);
				
				GenomeUtil.timeDuration(batchStartTime, GenomeUtil.getTime(), "Save TVV File");
			}

	}

	/**
	 * Process E2 Record Type of the Inpyt TVV File.
	 *
	 * @param br - BufferReader Object
	 * @param e2Records - List to maintain E2 Record type
	 * @return void
	 * @throws IOException Signals that an I/O exception has occurred.
	 */

	private void getE2RecordsList(BufferedReader br, List<String> e2Records) throws IOException {

		String e2line;

		br.mark(BUFFER_MARK);

		while ((e2line = br.readLine()) != null) {

			if (e2line.trim().isEmpty()) {
				br.mark(BUFFER_MARK);

			} else if (e2line.contains(E2_TVV_RECORD_TYPE)) {
				e2Records.add(e2line);
				br.mark(BUFFER_MARK);

			} else if (e2line.contains(E1_TVV_RECORD_TYPE)) {
				br.reset();
				return;
			} else {
				continue;
			}
		}
	}

}
