package com.citi.cmb.gce.accountservices.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.citi.cmb.gce.accountservices.cache.entity.ActiveAccounts;
import com.citi.cmb.gce.accountservices.cache.repository.ActiveAccountsRepository;

@Component
public class AccountCache {

	public static Set<ActiveAccounts> configMap = new HashSet<>();

	@Autowired
	private ActiveAccountsRepository accountsRepository;

	@Autowired
	Logger logger;

	@PostConstruct
	@Scheduled(fixedDelay = 600000)
	public void retrieveConfig() {
		List<ActiveAccounts> listOfActiveAccounts = new ArrayList<>();
		logger.info("Before update cache size is: " + configMap.size());

		listOfActiveAccounts = accountsRepository.findByBranchcd("996");
		listOfActiveAccounts.forEach(account -> {
			configMap.add(account);
		});
		logger.info("After update cache size is: " + configMap.size());
	}
}
