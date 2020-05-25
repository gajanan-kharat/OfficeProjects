package com.citi.cmb.gce.accountservices.common.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citi.cmb.gce.accountservices.common.entity.AccountServicesConfig;

@Repository
@CacheConfig(cacheNames="AccountServicesConfig")
public interface AccountServicesConfigRepository extends JpaRepository<AccountServicesConfig, Long> {

	@Cacheable
	List<AccountServicesConfig> findAll();
	
	@Cacheable
	AccountServicesConfig findByConfigName(String configName);
}
