package com.inetpsa.pv2.batch;


import org.seedstack.pv2.application.PictoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
/**
 * This data migration class which implements Tasklet. 
 * @author 
 *
 */
public class PictoDataMigration implements Tasklet {
	
	private PictoService pictoService;
	
	/**
	 * Logger log4j to write messages
	 */
	private static Logger logger = LoggerFactory
			.getLogger(PictoDataMigration.class);
	
	
	/**
	 * This is execute method to get repeat status.
	 * @param contribution, chunkContext
	 * @throws Exception
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		logger.info("********************************************************************************************");
		logger.info("********************************** PICTO DATA MIGRATION STARTED *****************************");
		logger.info("********************************************************************************************");

		pictoService.startDataMigartion();
		
		logger.info("********************************************************************************************");
		logger.info("********************************** DATA MIGRATION COMPLETED **************************************");
		logger.info("********************************************************************************************");
		return RepeatStatus.FINISHED;
	}


	/**
	 * @return the pictoService
	 */
	public PictoService getPictoService() {
		return pictoService;
	}


	/**
	 * @param pictoService the pictoService to set
	 */
	public void setPictoService(PictoService pictoService) {
		this.pictoService = pictoService;
	}
	
	

}
