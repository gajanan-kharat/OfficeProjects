package com.citi.cmb.gce.accountservices.cache.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.citi.cmb.gce.accountservices.cache.entity.ActiveAccounts;

@RepositoryRestResource(exported= false)
@CacheConfig(cacheNames="accountCacheConfig")
public interface ActiveAccountsRepository extends JpaRepository<ActiveAccounts, String>{

	@Cacheable
	@Query("select id from ActiveAccounts where id.branchCd NOT IN :branchCd")
	public List<ActiveAccounts> findByBranchcd(@Param("branchCd") String branchCd);
}
