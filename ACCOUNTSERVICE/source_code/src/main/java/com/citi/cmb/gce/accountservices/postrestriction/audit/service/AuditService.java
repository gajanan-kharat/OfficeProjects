package com.citi.cmb.gce.accountservices.postrestriction.audit.service;

import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.citi.cmb.gce.accountservices.postrestriction.audit.entity.AuditEntity;
import com.citi.cmb.gce.accountservices.postrestriction.audit.repository.AuditRepository;

public class AuditService {
	
	@Autowired
	private Logger logger;
	
	@Autowired
	private AuditRepository auditRepository;
	
	@Async("threadPoolExecutor")
	public void persistAuditMessage(String xmlMessage) {
		logger.info("Persisting audit xml message into data base!!");
		AuditEntity auditEntity = new AuditEntity();
		auditEntity.setMessage(xmlMessage);
		auditEntity.setCreationDate(new Date());
		
		auditRepository.saveAndFlush(auditEntity);
		logger.info("Saved message successfully!!");
	}
}
