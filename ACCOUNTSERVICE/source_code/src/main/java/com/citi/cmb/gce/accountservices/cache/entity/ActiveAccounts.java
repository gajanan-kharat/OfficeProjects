package com.citi.cmb.gce.accountservices.cache.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACTIVE_ACCOUNTS")
public class ActiveAccounts implements Serializable{

	@Id
	@Column(name="ACCOUNT_No")
	private String accountNo;
	
	@Column(name="BRANCH_CODE")
	private String branchCd;
	
	@Column(name="CURRENCY")
	private String accountCurrency;
	//Many more fields

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBranchCd() {
		return branchCd;
	}

	public void setBranchCd(String branchCd) {
		this.branchCd = branchCd;
	}

	public String getAccountCurrency() {
		return accountCurrency;
	}

	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}
	
}
