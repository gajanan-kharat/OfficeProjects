package com.citi.cmb.gce.accountservices.common.util;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.citi.cmb.gce.accountservices.msg.vo.AccountIdVO;


public class BalanceInquiryRequestWrapper implements Serializable{

	private static final long serialVersionUID = 1L;

	private ArrayList<AccountIdVO> accountIdVOList;
	private String srcSystem;
	private String balanceServiceRefNo;
	public ArrayList<AccountIdVO> getAccountIdVOList() {
		return accountIdVOList;
	}
	public void setAccountIdVOList(ArrayList<AccountIdVO> accountIdVOList) {
		this.accountIdVOList = accountIdVOList;
	}
	public String getSrcSystem() {
		return srcSystem;
	}
	public void setSrcSystem(String srcSystem) {
		this.srcSystem = srcSystem;
	}
	public String getBalanceServiceRefNo() {
		return balanceServiceRefNo;
	}
	public void setBalanceServiceRefNo(String balanceServiceRefNo) {
		this.balanceServiceRefNo = balanceServiceRefNo;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
