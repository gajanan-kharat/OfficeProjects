package com.citi.cmb.gce.accountservices.quartz.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citi.cmb.gce.accountservices.quartz.entity.SchedulerJobDetails;

@Repository
public interface SchedulerJobsRepository extends JpaRepository<SchedulerJobDetails, Long> {
}
