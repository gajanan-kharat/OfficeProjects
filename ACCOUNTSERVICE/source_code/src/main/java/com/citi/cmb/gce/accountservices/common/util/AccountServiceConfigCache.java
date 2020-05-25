package com.citi.cmb.gce.accountservices.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.citi.cmb.gce.accountservices.common.entity.AccountServicesConfig;
import com.citi.cmb.gce.accountservices.common.repository.AccountServicesConfigRepository;
import com.citi.cmb.gce.accountservices.exception.ExceptionProperties;

@Component
public class AccountServiceConfigCache {

	@Autowired
	private Logger logger;
	
	@Autowired
	private ExceptionProperties exceptionProperties;
	
	public static Map<String,String> exceptionMap=new HashMap<String,String>();
	
	public static Map<String,String> accountServiceConfigMap=new HashMap<String,String>();
	
	@Autowired
	private AccountServicesConfigRepository accountServicesConfigRepository;
	
	@Scheduled(fixedRate=600000)
	public void retrieveAccountServiceConfigMap() {
		List<AccountServicesConfig> list = accountServicesConfigRepository.findAll();
		list.forEach((element) -> {
			accountServiceConfigMap.put(element.getConfigName(), element.getConfigValue());
		});
	}
	
	public String getAccountServiceConfig(String configName) {
		return accountServiceConfigMap.get(configName);
	}
	
	@PostConstruct
	public void retrieveException() {
		logger.debug("Updating account services exception cache");
		exceptionMap=exceptionProperties.getErrorCodes();
	}
}
