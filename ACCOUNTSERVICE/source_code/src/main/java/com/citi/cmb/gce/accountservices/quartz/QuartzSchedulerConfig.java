package com.citi.cmb.gce.accountservices.quartz;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.joda.time.LocalDateTime;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.citi.cmb.gce.accountservices.exception.DataException;
import com.citi.cmb.gce.accountservices.quartz.dao.QuartzDao;
import com.citi.cmb.gce.accountservices.quartz.entity.SchedulerJobDetails;
import com.citi.cmb.gce.accountservices.quartz.jpa.SchedulerJobsRepository;
import com.citi.cmb.gce.accountservices.quartz.trigger.CustomCronTriggerFactoryBean;

@Configuration
public class QuartzSchedulerConfig {

	@Autowired
	private Logger logger;
	
	@Autowired
	private SchedulerJobsRepository schedulerJobsRepository;
	
	@Autowired
	@Qualifier("quartzdatasource")
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("quartzTransactionManager")
	private PlatformTransactionManager transactionManager;
	
	@Autowired 
	private ApplicationContext applicationContext;
	
	@Autowired
	private QuartzDao quartzDao;
	
	String schedulerName;
	private TimeZone defaultTimeZone;
	
	@Bean(name="ACCOUNTSERVICES_SCHEDULER")
	public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext context) throws IOException, ClassNotFoundException, DataException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setDataSource(dataSource);
		factory.setTransactionManager(transactionManager);
		factory.setQuartzProperties(quartzProperties());
		factory.setSchedulerName(schedulerName);
		factory.setJobFactory(springBeanJobFactory());
		factory.setTriggers(getJobAndSchedule());
		return factory;
	}
	
	@Bean
	@Transactional("quartzTransactionManager")
	private Trigger[] getJobAndSchedule() throws ClassNotFoundException,DataException{
		List<CronTrigger> listOfTriggers = new ArrayList<>();
		List<SchedulerJobDetails> listOfJobs = new ArrayList<>();
		listOfJobs=schedulerJobsRepository.findAll();
		
		listOfJobs.forEach(job-> {
			if(job.getIsActive()==1) {
				JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
				factoryBean.setBeanName(job.getJobName());
				factoryBean.setJobClass((Class<? extends Job>) Class.forName(job.getJobClass()));
				factoryBean.setDurability(true);
				factoryBean.setApplicationContext(applicationContext);
				factoryBean.setName(job.getJobName());
				factoryBean.setGroup(job.getJobGroup());
				factoryBean.afterPropertiesSet();
				
				CustomCronTriggerFactoryBean triggerFactoryBean = new CustomCronTriggerFactoryBean();
				triggerFactoryBean.setBeanName(job.getTriggerName());
				triggerFactoryBean.setJobDetail(factoryBean.getObject());
				triggerFactoryBean.setName(job.getTriggerName());
				triggerFactoryBean.setGroup(job.getTriggerGroup());
				//if no timezone is mention then use default one
				if(job.getTimeZone()!=null || !job.getTimeZone().isEmpty()) {
					triggerFactoryBean.setTimeZone(TimeZone.getTimeZone(job.getTimeZone()));
				}else {
					triggerFactoryBean.setTimeZone(defaultTimeZone);
				}
				triggerFactoryBean.setStartTime(LocalDateTime.now().toDate());
				if(job.getStopAfterTime()!=null) {
					triggerFactoryBean.setEndTime(LocalDateTime.now().plusMinutes(job.getStopAfterTime().intValue()).toDate());
				}
				triggerFactoryBean.setCronExpression(job.getCronExp());
				triggerFactoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
				try {
					triggerFactoryBean.afterPropertiesSet();
				}catch (ParseException e) {
				}
				CronTrigger object = triggerFactoryBean.getObject();
				listOfTriggers.add(object);
			}else if(job.getIsActive()!=1) {
				int count =quartzDao.checkIfExpiredJobExistsInDb(schedulerName, job.getTriggerName(), job.getTriggerGroup());
				if(count>0) {
					quartzDao.clearQtzTablesForJob(schedulerName, job.getJobName(), job.getJobGroup(), job.getTriggerName(), job.getTriggerGroup());
				}
			}
		});
		
		Trigger[] t = new Trigger[listOfTriggers.size()];
		return listOfTriggers.toArray(t);
	}

	@Bean
	private SpringBeanJobFactory springBeanJobFactory() {
		return new AutowiringSpringBeanJobFactory();
	}

	@Bean
	private Properties quartzProperties() throws IOException {
		PropertiesFactoryBean bean = new PropertiesFactoryBean();
		bean.setLocation(new ClassPathResource("/quartz.properties"));
		bean.afterPropertiesSet();
		this.schedulerName = bean.getObject().getProperty("org.quartz.scheduler.instanceName");
		String tzs = bean.getObject().getProperty("org.quartz.scheduler.default.timezone");
		defaultTimeZone = TimeZone.getTimeZone(tzs);
		return bean.getObject();
	}
}
