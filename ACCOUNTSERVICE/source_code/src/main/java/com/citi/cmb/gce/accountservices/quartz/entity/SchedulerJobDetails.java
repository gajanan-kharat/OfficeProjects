package com.citi.cmb.gce.accountservices.quartz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="AS_SCHEDULED_JOBS")
public class SchedulerJobDetails {
	
	@Id
	@SequenceGenerator(name="QUARTZ_SCHEDULAR_SEQ", sequenceName="QUARTZ_SCHEDULAR_SEQ",initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="QUARTZ_SCHEDULAR_SEQ")
	@Column(name="JOBID")
	private long jobId;
	
	@Column(name="JOB_NAME")
	private String jobName;
	
	@Column(name="JOB_GROUP")
	private String jobGroup;
	
	@Column(name="JOB_CLASS")
	private String jobClass;
	
	@Column(name="TRIGGER_NAME")
	private String triggerName;
	
	@Column(name="TRIGGER_GROUP")
	private String triggerGroup;
	
	@Column(name="CRON_EXP")
	private String cronExp;
	
	@Column(name="TIMEZONE")
	private String timeZone;

	@Column(name="CREATION_DATE")
	private Date creationDate;
	
	@Column(name="LASTUPDATED_DATE")
	private Date lastUpdatedDate;
	
	@Column(name="IS_ACTIVE")
	private int isActive;
	
	@Column(name="COMMENTs")
	private String comments;
	
	@Column(name="STOP_AFTER_TIME")
	private Long stopAfterTime;

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getCronExp() {
		return cronExp;
	}

	public void setCronExp(String cronExp) {
		this.cronExp = cronExp;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getStopAfterTime() {
		return stopAfterTime;
	}

	public void setStopAfterTime(Long stopAfterTime) {
		this.stopAfterTime = stopAfterTime;
	}
	
}
