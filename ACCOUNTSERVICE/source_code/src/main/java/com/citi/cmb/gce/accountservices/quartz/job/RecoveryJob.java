package com.citi.cmb.gce.accountservices.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class RecoveryJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//Call REST service consume 
	}

}
