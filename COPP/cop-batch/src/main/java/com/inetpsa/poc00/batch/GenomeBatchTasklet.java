package com.inetpsa.poc00.batch;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.inetpsa.poc00.application.GenomeService;
import com.inetpsa.poc00.constant.genomeconstant.GenomeConstant;
import com.inetpsa.poc00.genomeBatch.utility.GenomeUtil;

/**
 * Class GenomeBatchTasklet - read the Genome files from the directory and process them to get values and save
 * the objects.
 * 
 * @author ankurp
 */

public class GenomeBatchTasklet implements Tasklet, GenomeConstant {

	/** The genome service. */
	private GenomeService genomeService;

	/** Logger log4j to write messages. */
	private static Logger logger = LoggerFactory.getLogger(GenomeBatchTasklet.class);

	/** List to store the XML file path. */
	private static List<File> result = new ArrayList<File>();

	/** Map to store the XML file path. */
	private static Map<String, File> fileMap = new HashMap<String, File>();

	/** Holding the directory path for the list of XML. */
	private String genomeFileDirectory;
	
	/** The Constant NUMBER_OF_GENOMEFILES. */
	public static final int NUMBER_OF_GENOMEFILES = 4;

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		logger.info("========================================================================================================================");
		logger.info("**************************************************   BATCH STARTED   ***************************************************");
		logger.info("========================================================================================================================\n");

		logger.info("BATCH START TIME : {}", new Date());

		File file = null;

		// DIRECTORY WHERE GENOME FILES ARE LOCATED
		try {

			logger.info("*** Genome File Source Directory : {} ", genomeFileDirectory);

			file = new File(genomeFileDirectory);

			logger.debug("File Object : {}", file.toString());

			if (file.exists()) {

				logger.info("\n*** Provided Genome Input File Directory Exists");

				searchGenomeFiles(file);
			} else {
				logger.error("\n*** Provided Genome Input File Directory does not Exists");
				logger.error("\n*** INPUT FILES ARE NOT AVAILABLE, STATUS : FAILED ");

			}

			if (result.isEmpty()) {

				logger.error("\n*** Genome Files are not available, Provided path, {} " + file.toString());
				logger.error("\n*** FILES ARE NOT PROCESSED, STATUS : FAILED ");

			} else if (result.size() < NUMBER_OF_GENOMEFILES) {
				logger.error("\n*** Less than 4 file found, Available Genome Files are : {}" + file.toString());
				logger.error("\n*** FILES ARE NOT PROCESSED, STATUS : FAILED ");

			} else {

				emptyFileCheck();

				DateTime batchStartTime = GenomeUtil.getTime();

				genomeService.fileProcessor(fileMap);

				GenomeUtil.timeDuration(batchStartTime, GenomeUtil.getTime(), "Batch Execution");
			}

		} catch (IllegalArgumentException e) {

			logger.error("\n*** Exception Occured, Method : execute() , Class : ", GenomeBatchTasklet.class);
			throw new IllegalArgumentException(e);
		}

		logger.info("BATCH END TIME : {}", new Date());

		logger.info("========================================================================================================================");
		logger.info("*************************************************   BATCH COMPLETED   **************************************************");
		logger.info("========================================================================================================================\n");

		return RepeatStatus.FINISHED;
	}

	/**
	 * emptyFileCheck ,Check for the empty input File and File Size.
	 *
	 * @return void
	 */
	private void emptyFileCheck() {

		for (File emptyCheck : result) {
			File fileObject = new File(emptyCheck.getAbsolutePath());

			if (fileObject.length() <= 0) {
				logger.error("\n*** Empty File, Absolute Path : {}", fileObject.getAbsoluteFile());

			} else {
				logger.info("*** File Name : {}, File Length in KB : {}", fileObject.getName(), fileObject.length());
			}

		}
	}

	/**
	 * Static Method : SearchGenomeFiles ,Searching Genome File in input location.
	 *
	 * @param file - File to Search
	 * @return void
	 */
	public static void searchGenomeFiles(File file) {

		logger.info("\n*** START : In Method : SearchGenomeFiles, File : {} ", file.toString());

		File[] paths = null;

		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				// Search for files if its directory
				searchGenomeFiles(files[i]);
			} else {
				// Filter with given .extension files
				paths = file.listFiles(filter);

				if (!result.containsAll(Arrays.asList(paths))) {
					result.addAll(Arrays.asList(paths));
				}

			}

		}

		logger.info("\n*** END : Searching Genome Files in Directory, No. of File : {} \n", paths != null ? paths.length : "Paths Object is null");

		if (paths != null && paths.length > 0)
			for (File obj : paths) {
				logger.info("*** " + obj.getAbsolutePath());
			}

		/**
		 * Converting List of File into Map
		 */
		for (File fileObj : result) {

			if (fileObj.getName().contains(TVV_FILENAME))
				fileMap.put(TVV_FILE_ID, fileObj);

			if (fileObj.getName().contains(FAMILY_FILENAME))
				fileMap.put(FAMILY_DIC_FILE_ID, fileObj);

			if (fileObj.getName().contains(GENERALDICTIONARY_FILENAME))
				fileMap.put(GENERAL_FILE_ID, fileObj);

			if (fileObj.getName().contains(KMATFAM_FILENAME))
				fileMap.put(KMAT_FAM_FILE_ID, fileObj);
		}

		// Added Blank line for log file formatting
		logger.info(" ");

	}

	/**
	 * Create a FileFilter that matches ".dat" files
	 */
	private static FileFilter filter = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isFile() && file.getName().endsWith(".dat");
		}
	};

	/**
	 * Gets the genome service.
	 *
	 * @return the genome service
	 */
	public GenomeService getGenomeService() {
		return genomeService;
	}

	/**
	 * Sets the genome service.
	 *
	 * @param genomeService the new genome service
	 */
	public void setGenomeService(GenomeService genomeService) {
		this.genomeService = genomeService;
	}
	
	/**
	 * Gets the value of the genomeFileDirectory property.
	 * 
	 * @return possible object is {@String}
	 */
	public String getGenomeFileDirectory() {
		return genomeFileDirectory;
	}

	/**
	 * Sets the value of the genomeFileDirectory property.
	 * 
	 * @param genomeFileDirectory allowed object is {@String }
	 */
	public void setGenomeFileDirectory(String genomeFileDirectory) {
		this.genomeFileDirectory = genomeFileDirectory;
	}

}