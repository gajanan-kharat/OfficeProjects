package com.citi.cmb.gce.accountservices.postrestriction.audit.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citi.cmb.gce.accountservices.postrestriction.audit.entity.AuditEntity;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, BigInteger>{
}
