package com.citi.cmb.gce.accountservices.quartz.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citi.cmb.gce.accountservices.quartz.entity.QuartzAudit;

public interface QuartzAuditRepositoy extends JpaRepository<QuartzAudit, Long>{

}
