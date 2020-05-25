package com.citi.cmb.gce.accountservices.quartz.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.citi.cmb.gce.accountservices.quartz.entity.QuartzAudit;
import com.citi.cmb.gce.accountservices.quartz.jpa.QuartzAuditRepositoy;

public class QuartzAuditDaoImpl implements QuartzAuditDao {

	@Autowired
	private QuartzAuditRepositoy auditRepositoy;

	@Override
	public void saveAudit(QuartzAudit quartzAudit) {
		auditRepositoy.saveAndFlush(quartzAudit);
	}
}
