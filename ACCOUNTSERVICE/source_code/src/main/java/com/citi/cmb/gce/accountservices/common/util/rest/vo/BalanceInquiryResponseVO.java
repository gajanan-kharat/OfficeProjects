package com.citi.cmb.gce.accountservices.common.util.rest.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BalanceInquiryResponseVO {

	private String accountNo;
	private String branchId;

	private String errorCode;
	private String errorMsg;

	private String earmarkReferenceNumber;
	
	// Other required fields

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getEarmarkReferenceNumber() {
		return earmarkReferenceNumber;
	}

	public void setEarmarkReferenceNumber(String earmarkReferenceNumber) {
		this.earmarkReferenceNumber = earmarkReferenceNumber;
	}

}
