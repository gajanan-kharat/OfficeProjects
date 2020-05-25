package com.citi.cmb.gce.accountservices.aop;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggingAspect {

	@Autowired
	private Logger logger;

	@Pointcut("within(com.citi.cmb.gce.accountservices.cache.*)")
	public void anyMethodinCache() {
	}

	@Pointcut("within(com.citi.cmb.gce.accountservices.msg.controller.*)")
	public void anyMethodInController() {
	}
	
	@Pointcut("within(com.citi.cmb.gce.accountservices.msg.listener.*)")
	public void anyMethodInListener() {
	}
	
	@Before(value = "anyMethodinCache() || anyMethodInController() "
			+ "|| anyMethodInListener()")
	public void loggingAdviseBeforeAnyMethod(Joinpoint joinpoint) {
		String methodName = joinpoint.toString();
		String shortName = joinpoint.toString().substring(methodName.indexOf(""), methodName.length() - 1);
		logger.info("Entering method: " + shortName);
	}

	@After(value = "anyMethodinCache()|| anyMethodInController() "
			+ "|| anyMethodInListener()")
	public void loggingAdviceAfterAnyMethod(Joinpoint joinpoint) {
		String methodName = joinpoint.toString();
		String shortName = joinpoint.toString().substring(methodName.indexOf(""), methodName.length() - 1);
		logger.info("Exiting method: " + shortName);
	}

}
