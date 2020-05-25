package com.citi.cmb.gce.accountservices.msg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class AcctMainInfoEntityId implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name="CUST_AC_NO")
	private String custAccNo;
	
	@NotNull
	@Column(name="BRANCH")
	private String branch;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custAccNo == null) ? 0 : custAccNo.hashCode());
		result = prime * result + ((branch == null ? 0 : branch.hashCode()));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj)
			return true;
		if(obj==null)
			return false;
		if(this.getClass()!=obj.getClass())
			return false;
		
		AcctMainInfoEntityId other = (AcctMainInfoEntityId)obj;
		
		if(other.custAccNo==null) {
			if(this.custAccNo!=null)
				return false;
		}else if(!other.custAccNo.equals(this.custAccNo))
			return false;
		
		if(other.branch==null) {
			if(this.branch!=null)
				return false;
		}else if(!other.branch.equals(this.branch))
			return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

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
	
}
