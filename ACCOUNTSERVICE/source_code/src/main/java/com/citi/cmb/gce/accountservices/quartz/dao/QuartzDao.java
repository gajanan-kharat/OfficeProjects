package com.citi.cmb.gce.accountservices.quartz.dao;

import com.citi.cmb.gce.accountservices.exception.DataException;

public interface QuartzDao {

	int deleteAllFromFiredTriggers();
	
	int deleteAllFromSimpleTriggers();
	
	int deleteAllFromSimpropTriggers();
	
	int deleteAllFromCronTriggers();
	
	int deleteAllFromBlobTriggers();
	
	int deleteAllFromQtzTriggers();
	
	int deleteAllFromJobDetails();
	
	int deleteAllFromJobCalenders();
	
	int deleteAllFromPausedTriggerGrps();
	
	int deleteAllFromQrtzLocks();
	
	int deleteAllFromQrtzSchedulerState();
	
	boolean clearAllQrtzTables();
	
	int deleteTriggerFromSimpropTriggers(String schedulerName, String triggerName, String triggerGroup);
	
	int deleteTriggerFromCronTriggers(String schedulerName, String triggerName, String triggerGroup);
	
	int deleteTriggerFromQtzTriggers(String schedulerName, String jobName, String jobGroup);
	
	int deleteJobFromJobDetails(String schedulerName, String jobName, String jobGroup);
	
	int deleteTriggerFromFiredTriggers(String schedulerName, String jobName, String jobGroup);
	
	boolean clearQtzTablesForJob(String schedulerName, String jobName, String jobGroup, String triggerName, String triggerGroup) throws DataException;
	
	int deleteTriggerFromBlobTriggers(String schedulerName, String triggerName, String triggerGroup);
	
	int deleteTriggerFromSimpleTriggers(String schedulerName, String triggerName, String triggerGroup);
	
	int checkIfExpiredJobExistsInDb(String schedulerName, String triggerName, String triggerGroup);
	
}
