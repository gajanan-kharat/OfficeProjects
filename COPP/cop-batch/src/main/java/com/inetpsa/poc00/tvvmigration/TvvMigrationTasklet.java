package com.inetpsa.poc00.tvvmigration;

import java.io.File;
import java.io.FileFilter;
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

import com.inetpsa.poc00.application.TvvMigrationService;
import com.inetpsa.poc00.genomeBatch.utility.GenomeUtil;

/**
 * The Class TvvMigrationTasklet.
 */
public class TvvMigrationTasklet implements Tasklet {

	/** The TvvMigration service. */
	private TvvMigrationService tvvMigrationService;

	/** List to store the XML file path. */
	private static List<File> result = new ArrayList<File>();

	/** The tvv migration file directory. */
	private String tvvMigrationFileDirectory;

	/** Logger log4j to write messages. */
	private static Logger logger = LoggerFactory.getLogger(TvvMigrationTasklet.class);

	private String logLine = "========================================================================================================================";

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		logger.info(logLine);
		logger.info("**************************************************   BATCH STARTED   ***************************************************");
		logger.info(logLine);

		logger.info("### BATCH START TIME : {}\n", new Date());

		File file = null;

		try {

			file = new File(tvvMigrationFileDirectory);

			if (file.exists()) {

				logger.info("*** Provided Input Directory Exists   : {}", tvvMigrationFileDirectory);

				searchTvvInputFile(file);
			} else {
				logger.error("\n*** Provided TVV Migration Input File Directory does not Exists");
				logger.error("\n*** INPUT FILES ARE NOT AVAILABLE, STATUS : FAILED ");

			}

			if (result.isEmpty()) {

				logger.error("\n*** TVV Migration Input File is not available, Provided path, {} ", file.toString());
				logger.error("\n*** FILES ARE NOT PROCESSED, STATUS : FAILED ");

			} else {

				emptyFileCheck();

				DateTime batchStartTime = GenomeUtil.getTime();

				tvvMigrationService.tvvCSVProcessor(result.get(0), tvvMigrationFileDirectory);

				GenomeUtil.timeDuration(batchStartTime, GenomeUtil.getTime(), "Batch Execution");
			}
		} catch (Exception e) {
			logger.error("\n*** Exception Occured, Method : execute() , Class : ", TvvMigrationTasklet.class);
			throw new IllegalArgumentException(e);
		}

		logger.info("### BATCH END TIME : {}", new Date());

		logger.info(logLine);
		logger.info("*************************************************   BATCH COMPLETED   **************************************************");
		logger.info(logLine);

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
				logger.info("*** File Name                         : {} ", fileObject.getName());
				logger.info("*** File Length in KB                 : {} ", fileObject.length());
			}

		}
	}

	/**
	 * Search tvv input file.
	 *
	 * @param file the file
	 */
	public static void searchTvvInputFile(File file) {

		logger.info("*** Searching Input File in Directory : {}", file.toString());

		File[] paths;

		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				// Search for files if its directory
				searchTvvInputFile(files[i]);
			} else {
				// Filter with given .extension files
				paths = file.listFiles(filter);

				if (!result.containsAll(Arrays.asList(paths))) {
					result.addAll(Arrays.asList(paths));
				}

			}

		}
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
	 * Gets the tvv migration file directory.
	 * 
	 * @return the tvvMigrationFileDirectory
	 */
	public String getTvvMigrationFileDirectory() {
		return tvvMigrationFileDirectory;
	}

	/**
	 * Sets the tvv migration file directory.
	 * 
	 * @param tvvMigrationFileDirectory the tvvMigrationFileDirectory to set
	 */
	public void setTvvMigrationFileDirectory(String tvvMigrationFileDirectory) {
		this.tvvMigrationFileDirectory = tvvMigrationFileDirectory;
	}

	/**
	 * Gets the tvv migration service.
	 *
	 * @return the tvvMigrationService
	 */
	public TvvMigrationService getTvvMigrationService() {
		return tvvMigrationService;
	}

	/**
	 * Sets the tvv migration service.
	 *
	 * @param tvvMigrationService the tvvMigrationService to set
	 */
	public void setTvvMigrationService(TvvMigrationService tvvMigrationService) {
		this.tvvMigrationService = tvvMigrationService;
	}

}
