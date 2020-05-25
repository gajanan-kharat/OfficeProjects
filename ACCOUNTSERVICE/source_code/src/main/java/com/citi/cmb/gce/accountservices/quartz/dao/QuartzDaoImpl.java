package com.citi.cmb.gce.accountservices.quartz.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.citi.cmb.gce.accountservices.common.util.AccountServiceConfigCache;
import com.citi.cmb.gce.accountservices.exception.ASExceptionConstants;
import com.citi.cmb.gce.accountservices.exception.DataException;

@PropertySource(ignoreResourceNotFound=true, value="classpath:quartz.properties")
@Component("quartzDao")
public class QuartzDaoImpl implements QuartzDao {

	@Autowired
	Logger logger;
	
	@Autowired
	@Qualifier("quartzJdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	@Value("${org.quartz.jobStore.tablePrefix}")
	private String tablePrefix;
	
	@Override
	public boolean clearAllQrtzTables() {
		Boolean isAllDelete=false;
		try {
			int statusFiredTriggers = deleteAllFromFiredTriggers();
			int statusSimpleTriggers = deleteAllFromSimpleTriggers();
			int statusSimppropTriggers = deleteAllFromSimpropTriggers();
			int statusCronsTriggers = deleteAllFromCronTriggers();
			int statusBlobTriggers = deleteAllFromBlobTriggers();
			int statusQtzTriggers = deleteAllFromQtzTriggers();
			int statusJobDetails = deleteAllFromJobDetails();
			int statusJobCalenders = deleteAllFromJobCalenders();
			int statusPausedTriggerGroup = deleteAllFromPausedTriggerGrps();
			int statusQrtzLocks = deleteAllFromQrtzLocks();
			int statusQrtzScheduler = deleteAllFromQrtzSchedulerState();
			if (statusFiredTriggers >= 0 && statusSimpleTriggers >= 0 && statusSimppropTriggers >= 0
					&& statusCronsTriggers >= 0 && statusCronsTriggers >= 0 && statusBlobTriggers >= 0
					&& statusQtzTriggers >= 0 && statusJobDetails >= 0 && statusJobCalenders >= 0
					&& statusPausedTriggerGroup >= 0 && statusQrtzLocks >= 0 && statusQrtzScheduler >= 0) {
				isAllDelete = true;
			} 
		} catch (DataAccessException dae) {
			String errorCode = ASExceptionConstants.QS100;
			String errorMsg = AccountServiceConfigCache.exceptionMap.get(errorCode);
			logger.error(errorMsg,dae);
		}
		return isAllDelete;
	}
	
	@Override
	public boolean clearQtzTablesForJob(String schedulerName, String jobName, String jobGroup, String triggerName,
			String triggerGroup) throws DataAccessException {
		Boolean isAllDelete = false;
		try {
			int triggerFromFiredTriggers = deleteTriggerFromFiredTriggers(schedulerName, jobName, jobGroup);
			int triggerFromSimpleTriggers = deleteTriggerFromSimpleTriggers(schedulerName, triggerName, triggerGroup);
			int triggerFromSimpropTriggers = deleteTriggerFromSimpropTriggers(schedulerName, triggerName, triggerGroup);
			int triggerFromCronTriggers = deleteTriggerFromCronTriggers(schedulerName, triggerName, triggerGroup);
			int triggerFromBlobTriggers = deleteTriggerFromBlobTriggers(schedulerName, triggerName, triggerGroup);
			int triggerFromQtzTriggers = deleteTriggerFromQtzTriggers(schedulerName, jobName, jobGroup);
			int jobFromJobDetails = deleteJobFromJobDetails(schedulerName, jobName, jobGroup);
			int allFromJobCalenders = deleteAllFromJobCalenders();
			int allFromPausedTriggerGrps = deleteAllFromPausedTriggerGrps();
			int allFromQrtzLocks = deleteAllFromQrtzLocks();
			int allFromQrtzSchedulerState = deleteAllFromQrtzSchedulerState();
			if (triggerFromFiredTriggers >= 0 && triggerFromSimpleTriggers >= 0 && triggerFromSimpropTriggers >= 0
					&& triggerFromCronTriggers >= 0 && triggerFromBlobTriggers >= 0 && triggerFromQtzTriggers >= 0
					&& jobFromJobDetails >= 0 && allFromJobCalenders >= 0 && allFromPausedTriggerGrps >= 0
					&& allFromQrtzLocks >= 0 && allFromQrtzSchedulerState >= 0) {
				isAllDelete = true;
			} 
		} catch (DataAccessException e) {
			String errorCode = ASExceptionConstants.QS100;
			String errorMsg = AccountServiceConfigCache.exceptionMap.get(errorCode);
			logger.error(errorMsg, e);
			//throw new DataException(errorCode, errorMsg, e, null);
		}
		return isAllDelete;
	}
	
	@Override
	public int deleteAllFromFiredTriggers() throws DataAccessException{
		String tempQuery= "DELETE FROM $tablePrefix"+"FIRED_TRIGGERS";
		tempQuery = tempQuery.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(tempQuery);
		return status;
	}

	@Override
	public int deleteAllFromSimpleTriggers() {
		String tempQuery= "DELETE FROM $tablePrefix"+"SIMPLE_TRIGGERS";
		tempQuery = tempQuery.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(tempQuery);
		return status;
	}

	@Override
	public int deleteAllFromSimpropTriggers() throws DataAccessException{
		String tempQuery= "DELETE FROM $tablePrefix"+"SIMPROP_TRIGGERS";
		tempQuery = tempQuery.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(tempQuery);
		return status;
	}

	@Override
	public int deleteAllFromCronTriggers() {
		String tempQuery= "DELETE FROM $tablePrefix"+"CRON_TRIGGERS";
		tempQuery = tempQuery.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(tempQuery);
		return status;
	}

	@Override
	public int deleteAllFromBlobTriggers() {
		String tempQuery= "DELETE FROM $tablePrefix"+"BLOB_TRIGGERS";
		tempQuery = tempQuery.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(tempQuery);
		return status;
	}

	@Override
	public int deleteAllFromQtzTriggers() {
		String tempQuery= "DELETE FROM $tablePrefix"+"TRIGGERS";
		tempQuery = tempQuery.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(tempQuery);
		return status;
	}

	@Override
	public int deleteAllFromJobDetails() throws DataAccessException{
		String tempQuery= "DELETE FROM $tablePrefix"+"JOB_DETAILS";
		tempQuery = tempQuery.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(tempQuery);
		return status;
	}

	@Override
	public int deleteAllFromJobCalenders() throws DataAccessException{
		String tempQuery= "DELETE FROM $tablePrefix"+"CALENDERS";
		tempQuery = tempQuery.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(tempQuery);
		return status;
	}

	@Override
	public int deleteAllFromPausedTriggerGrps() throws DataAccessException{
		String tempQuery= "DELETE FROM $tablePrefix"+"PAUSED_TRIGGER_GROUP";
		tempQuery = tempQuery.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(tempQuery);
		return status;
	}

	@Override
	public int deleteAllFromQrtzLocks() {
		String tempQuery= "DELETE FROM $tablePrefix"+"LOCKS";
		tempQuery = tempQuery.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(tempQuery);
		return status;
	}

	@Override
	public int deleteAllFromQrtzSchedulerState() {
		String tempQuery= "DELETE FROM $tablePrefix"+"SCHEDULER_STATE";
		tempQuery = tempQuery.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(tempQuery);
		return status;
	}

	@Override
	public int deleteTriggerFromFiredTriggers(String schedulerName, String jobName, String jobGroup) {
		String query= "DELETE FROM $tablePrefix"+"FIRED_TRIGGERS WHERE SCHED_NAME=? AND JOB_NAME=? AND JOB_GROUP=? ";
		query= query.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(query, new Object[] {schedulerName, jobName,jobGroup});
		return status;
	}
	
	@Override
	public int deleteTriggerFromSimpropTriggers(String schedulerName, String triggerName, String triggerGroup) {
		String query= "DELETE FROM $tablePrefix"+"SIMPROP_TRIGGERS WHERE SCHED_NAME=? AND JOB_NAME=? AND JOB_GROUP=? ";
		query= query.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(query, new Object[] {schedulerName, triggerName,triggerGroup});
		return status;
	}

	@Override
	public int deleteTriggerFromCronTriggers(String schedulerName, String triggerName, String triggerGroup) throws DataAccessException{
		String query= "DELETE FROM $tablePrefix"+"CRON_TRIGGERS WHERE SCHED_NAME=? AND JOB_NAME=? AND JOB_GROUP=? ";
		query= query.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(query, new Object[] {schedulerName, triggerName,triggerGroup});
		return status;
	}

	@Override
	public int deleteTriggerFromQtzTriggers(String schedulerName, String jobName, String jobGroup) throws DataAccessException{
		String query= "DELETE FROM $tablePrefix"+"TRIGGERS WHERE SCHED_NAME=? AND JOB_NAME=? AND JOB_GROUP=? ";
		query= query.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(query, new Object[] {schedulerName, jobName,jobGroup});
		return status;
	}

	@Override
	public int deleteJobFromJobDetails(String schedulerName, String jobName, String jobGroup) throws DataAccessException{
		String query= "DELETE FROM $tablePrefix"+"JOB_DETAILS WHERE SCHED_NAME=? AND JOB_NAME=? AND JOB_GROUP=? ";
		query= query.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(query, new Object[] {schedulerName, jobName,jobGroup});
		return status;
	}

	@Override
	public int deleteTriggerFromBlobTriggers(String schedulerName, String triggerName, String triggerGroup) throws DataAccessException{
		String query= "DELETE FROM $tablePrefix"+"BLOB_TRIGGERS WHERE SCHED_NAME=? AND JOB_NAME=? AND JOB_GROUP=? ";
		query= query.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(query, new Object[] {schedulerName, triggerName,triggerGroup});
		return status;
	}

	@Override
	public int deleteTriggerFromSimpleTriggers(String schedulerName, String triggerName, String triggerGroup) {
		String query= "DELETE FROM $tablePrefix"+"SIMPLE_TRIGGERS WHERE SCHED_NAME=? AND JOB_NAME=? AND JOB_GROUP=? ";
		query= query.replace("$tablePrefix", tablePrefix);
		int status = jdbcTemplate.update(query, new Object[] {schedulerName, triggerName,triggerGroup});
		return status;
	}

	@Override
	public int checkIfExpiredJobExistsInDb(String schedulerName, String triggerName, String triggerGroup) {
		// TODO Auto-generated method stub
		return 0;
	}

}
