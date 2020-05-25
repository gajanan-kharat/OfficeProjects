package com.citi.cmb.gce.accountservices.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	private transient AutowireCapableBeanFactory beanFactory;

	@Override
	public void setApplicationContext(ApplicationContext context) {
		beanFactory = context.getAutowireCapableBeanFactory();
		applicationContext = context;
	}

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		final Object jobInstance = super.createJobInstance(bundle);
		beanFactory.autowireBean(jobInstance);
		return jobInstance;
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
