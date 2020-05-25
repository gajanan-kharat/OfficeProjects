package com.citi.cmb.gce.accountservices.quartz.trigger;

import java.text.ParseException;
import java.util.Date;

import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

public class CustomCronTriggerFactoryBean extends CronTriggerFactoryBean {

	private Date endTime;
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public void afterPropertiesSet() throws ParseException {
		super.afterPropertiesSet();
		
		if(super.getObject()!=null) {
			CronTriggerImpl object =(CronTriggerImpl) super.getObject();
			object.setEndTime(endTime);
		}
	}
}
