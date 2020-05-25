package com.inetpsa.poc00.coastdownmigration;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.inetpsa.poc00.application.CoastDownService;
import com.inetpsa.poc00.genomeBatch.utility.GenomeUtil;

/**
 * Class GenomeBatchTasklet - read the Genome files from the directory and process them to get values and save the
 * objects.
 * 
 * @author ankurp
 */

public class CoastDownTasklet implements Tasklet {

	/** The coastDown service. */
	private CoastDownService coastDownService;

	/** List to store the XML file path. */
	private static List<File> result = new ArrayList<File>();

	/** Logger log4j to write messages. */
	private static Logger logger = LoggerFactory.getLogger(CoastDownTasklet.class);

	/** The coast down file directory. */
	private String coastDownFileDirectory;

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException {

		logger.info("========================================================================================================================");
		logger.info("**************************************************   BATCH STARTED   ***************************************************");
		logger.info("========================================================================================================================\n");

		logger.info("BATCH START TIME : {}", new Date());

		File file = null;

		// DIRECTORY WHERE COAST DOWN EXCEL FILE IS LOCATED
		try {

			logger.info("*** COAST DOWN Directory : {} ", coastDownFileDirectory);

			file = new File(coastDownFileDirectory);

			logger.debug("File Object : {}", file.toString());

			if (file.exists()) {

				logger.info("\n*** Provided Coast Down file Directory Exists");

				searchCoastDownInputFile(file);
			} else {
				logger.error("\n*** Provided Coast Down Input File Directory does not Exists");
				logger.error("\n*** INPUT FILES ARE NOT AVAILABLE, STATUS : FAILED ");

			}

			if (result.isEmpty()) {

				logger.error("\n*** Coast Down File is not available, Provided path, {} ", file.toString());
				logger.error("\n*** FILES ARE NOT PROCESSED, STATUS : FAILED ");

			} else {

				emptyFileCheck();

				DateTime batchStartTime = GenomeUtil.getTime();

				coastDownService.csvProcessor(result.get(0), coastDownFileDirectory);

				GenomeUtil.timeDuration(batchStartTime, GenomeUtil.getTime(), "Batch Execution");
			}
		} catch (Exception e) {
			logger.error("\n*** Exception Occured, Method : execute() , Class : ", CoastDownTasklet.class);
			throw new IllegalArgumentException(e);
		}
		
		logger.info("BATCH END TIME : {}", new Date());

		logger.info("========================================================================================================================");
		logger.info("*************************************************   BATCH COMPLETED   **************************************************");
		logger.info("========================================================================================================================\n");

		return RepeatStatus.FINISHED;
	}

	/**
	 * Static Method : SearchCoastDownFile ,Searching  Coast Down CSV File in input location.
	 * 
	 * @param file - File to Search
	 * @return void
	 */
	public static void searchCoastDownInputFile(File file) {

		logger.info("\n*** START : In Method : Searching Coast Down Excel Input File, File : {} ", file.toString());

		File[] paths = null;

		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				// Search for files if its directory
				searchCoastDownInputFile(files[i]);
			} else {
				// Filter with given .extension files
				paths = file.listFiles(filter);

				if (!result.containsAll(Arrays.asList(paths))) {
					result.addAll(Arrays.asList(paths));
				}

			}

		}

		logger.info("\n*** END : Searching Coast Down Excel Input File, No. of File : {} \n", paths != null ? paths.length : "Paths Object is null");

		if (paths != null && paths.length > 0)
			for (File obj : paths) {
				logger.info("*** " + obj.getAbsolutePath());
			}

		// Added Blank line for log file formatting
		logger.info(" ");

	}

	/**
	 * Create a FileFilter that matches ".csv" files
	 */
	private static FileFilter filter = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isFile() && file.getName().endsWith(".csv");
		}
	};

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
	 * Gets the coast down file directory.
	 * 
	 * @return the coastDownFileDirectory
	 */
	public String getCoastDownFileDirectory() {
		return coastDownFileDirectory;
	}

	/**
	 * Sets the coast down file directory.
	 * 
	 * @param coastDownFileDirectory the coastDownFileDirectory to set
	 */
	public void setCoastDownFileDirectory(String coastDownFileDirectory) {
		this.coastDownFileDirectory = coastDownFileDirectory;
	}

	/**
	 * Gets the coast down service.
	 * 
	 * @return the coastDownService
	 */
	public CoastDownService getCoastDownService() {
		return coastDownService;
	}

	/**
	 * Sets the coast down service.
	 * 
	 * @param coastDownService the coastDownService to set
	 */
	public void setCoastDownService(CoastDownService coastDownService) {
		this.coastDownService = coastDownService;
	}

}