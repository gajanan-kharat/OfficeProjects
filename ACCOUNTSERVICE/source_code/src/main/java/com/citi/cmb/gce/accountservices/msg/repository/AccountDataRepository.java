package com.citi.cmb.gce.accountservices.msg.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.citi.cmb.gce.accountservices.msg.entity.AcctMainInfoEntity;
import com.citi.cmb.gce.accountservices.msg.entity.AcctMainInfoEntityId;

@Repository
public interface AccountDataRepository extends JpaRepository<AcctMainInfoEntity, AcctMainInfoEntityId> {

	@Query("select id from AcctMainInfoEntity id where id.currency IN (:currenct)")
	public ArrayList<AcctMainInfoEntity>findByCurrency(@Param("currency") String currency);
}
