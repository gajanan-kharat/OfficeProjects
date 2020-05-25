package com.citi.cmb.gce.accountservices.msg.vo;

import java.io.Serializable;
import java.util.Date;

public class MessageVO implements Serializable{

	private String custAccNo;
	
	private String branch;
	
	private String giwBranch;
	
	private String currency;
	
	private String customerNo;
	
	private String gfcid;
	
	private Date accOpenDate;
	
	private Date lastUpdtedStatus;

	public String getCustAccNo() {
		return custAccNo;
	}

	public void setCustAccNo(String custAccNo) {
		this.custAccNo = custAccNo;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getGiwBranch() {
		return giwBranch;
	}

	public void setGiwBranch(String giwBranch) {
		this.giwBranch = giwBranch;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getGfcid() {
		return gfcid;
	}

	public void setGfcid(String gfcid) {
		this.gfcid = gfcid;
	}

	public Date getAccOpenDate() {
		return accOpenDate;
	}

	public void setAccOpenDate(Date accOpenDate) {
		this.accOpenDate = accOpenDate;
	}

	public Date getLastUpdtedStatus() {
		return lastUpdtedStatus;
	}

	public void setLastUpdtedStatus(Date lastUpdtedStatus) {
		this.lastUpdtedStatus = lastUpdtedStatus;
	}
	
}
