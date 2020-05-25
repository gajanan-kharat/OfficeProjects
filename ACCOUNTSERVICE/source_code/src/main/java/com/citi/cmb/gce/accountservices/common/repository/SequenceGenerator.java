package com.citi.cmb.gce.accountservices.common.repository;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceGenerator {
	
	@Autowired
	@Qualifier(value="accountServicesEntityManager")
	EntityManager entityManager;
	
	public BigDecimal getStatusServiceRefNumber() {
		Query query = entityManager.createNativeQuery("select SEQ_STATUS_SERVICE_GEN.nextval from dual");
		BigDecimal seqValue =(BigDecimal) query.getSingleResult();
		return seqValue;
	}

}
