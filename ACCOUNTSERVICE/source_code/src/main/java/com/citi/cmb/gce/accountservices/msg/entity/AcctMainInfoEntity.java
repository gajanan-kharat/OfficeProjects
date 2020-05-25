package com.citi.cmb.gce.accountservices.msg.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name="ACCOUNT_STATUS_INFO")
public class AcctMainInfoEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private AcctMainInfoEntityId id;
	
	@Column(name="GIW_BRANCH")
	private String giwBranch;
	
	@Column(name="CURRENCY")
	private String currency;
	
	@Column(name="CUSTOMER_NO")
	private String customerNo;
	
	@Column(name="GFCID")
	private String gfcid;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ACC_OPEN_DATE")
	private Date accOpenDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_UPDATED_DATE")
	private Date lastUpdtedStatus;
	
	//Other field values
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public AcctMainInfoEntityId getId() {
		return id;
	}

	public void setId(AcctMainInfoEntityId id) {
		this.id = id;
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
