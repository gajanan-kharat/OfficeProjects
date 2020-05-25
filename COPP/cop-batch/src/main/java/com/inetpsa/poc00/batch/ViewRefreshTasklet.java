package com.inetpsa.poc00.batch;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.inetpsa.poc00.application.ViewRefreshService;
import com.inetpsa.poc00.genomeBatch.utility.GenomeUtil;

/**
 * The Class ViewRefreshTasklet.
 */
public class ViewRefreshTasklet implements Tasklet {

	/** The Refresh view service. */
	private ViewRefreshService viewRefreshService;

	/** Logger log4j to write messages. */
	private static Logger logger = LoggerFactory.getLogger(ViewRefreshTasklet.class);

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		logger.info("========================================================================================================================");
		logger.info("**********************************************  VIEW REFRESHING STARTED   **********************************************");
		logger.info("========================================================================================================================\n");

		DateTime batchStartTime = GenomeUtil.getTime();
		
		viewRefreshService.refreshPerfView();
		
		GenomeUtil.timeDuration(batchStartTime, GenomeUtil.getTime(), "Batch Execution");
		
		logger.info("========================================================================================================================");
		logger.info("***********************************************  VIEW REFRESHING END   *************************************************");
		logger.info("========================================================================================================================\n");


		return RepeatStatus.FINISHED;
	}

	/**
	 * Gets the view refresh service.
	 * 
	 * @return the viewRefreshService
	 */
	public ViewRefreshService getViewRefreshService() {
		return viewRefreshService;
	}

	/**
	 * Sets the view refresh service.
	 * 
	 * @param viewRefreshService the viewRefreshService to set
	 */
	public void setViewRefreshService(ViewRefreshService viewRefreshService) {
		this.viewRefreshService = viewRefreshService;
	}

}
