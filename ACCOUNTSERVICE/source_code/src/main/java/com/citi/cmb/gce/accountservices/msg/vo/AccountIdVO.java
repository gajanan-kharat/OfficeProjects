package com.citi.cmb.gce.accountservices.msg.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AccountIdVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountNo;
	private String branchId;
	private String accountBic;
	private String altBalAccountNo;
	private String altBalBranchId;
	private String altBalAcctBic;
	private String balanceSource;
	private String currencyCode;
	private String countryCode;
	private boolean pureHeaderFlag;
	private Date bussinessDate;
	private String flexInternalBranchCode;
	private String fetchStaleBalance = "N";

	private BigDecimal requestedEarmarkAmt;
	private String flexibusCluster;
	private boolean retrySweepFlag;
	private String earmarkRefNo;
	private char drainPoolIndicator = 'N';

	public AccountIdVO() {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result +
				((accountNo==null) ? 0 : accountNo.hashCode());
		result = prime * result +
				((branchId==null) ? 0 : branchId.hashCode());
		result = prime * result +
				((accountBic==null) ? 0 : accountBic.hashCode());
		result = prime * result +
				((altBalAccountNo==null) ? 0 : altBalAccountNo.hashCode());
		result = prime * result +
				((altBalBranchId==null) ? 0 : altBalBranchId.hashCode());
		result = prime * result +
				((altBalAcctBic==null) ? 0 : altBalAcctBic.hashCode());
		result = prime * result +
				((balanceSource==null) ? 0 : balanceSource.hashCode());
		result = prime * result +
				((currencyCode==null) ? 0 : currencyCode.hashCode());
		result = prime * result +
				((countryCode==null) ? 0 : countryCode.hashCode());
		result = prime * result +
				((pureHeaderFlag) ? 1231 : 1237);
		result = prime * result +
				((bussinessDate==null) ? 0 : bussinessDate.hashCode());
		result = prime * result +
				((flexInternalBranchCode==null) ? 0 : flexInternalBranchCode.hashCode());
		result = prime * result +
				((fetchStaleBalance==null) ? 0 : fetchStaleBalance.hashCode());
		result = prime * result +
				((requestedEarmarkAmt==null) ? 0 : requestedEarmarkAmt.hashCode());
		result = prime * result +
				((flexibusCluster==null) ? 0 : flexibusCluster.hashCode());
		result = prime * result +
				((retrySweepFlag) ? 1231 : 1237 );
		result = prime * result +
				((earmarkRefNo==null) ? 0 : earmarkRefNo.hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj)
			return true;
		if(obj==null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		
		AccountIdVO other = (AccountIdVO)obj;
		if(this.accountNo == null) {
			if(other.accountNo != null)
				return false;
		}else if(!this.accountNo.equals(other.accountNo))
			return false;
		
		if(this.branchId == null) {
			if(other.branchId != null)
				return false;
		}else if(!this.branchId.equals(other.branchId))
			return false;
		
		if(this.pureHeaderFlag!=other.pureHeaderFlag)
			return false;
		
		if(this.accountBic == null) {
			if(other.accountBic != null)
				return false;
		}else if(!this.accountBic.equals(other.accountBic))
			return false;
		
		if(this.altBalAccountNo == null) {
			if(other.altBalAccountNo != null)
				return false;
		}else if(!this.altBalAccountNo.equals(other.altBalAccountNo))
			return false;
		
		if(this.altBalBranchId == null) {
			if(other.altBalBranchId != null)
				return false;
		}else if(!this.altBalBranchId.equals(other.altBalBranchId))
			return false;
		
		if(this.altBalAcctBic == null) {
			if(other.altBalAcctBic != null)
				return false;
		}else if(!this.altBalAcctBic.equals(other.altBalAcctBic))
			return false;
		
		if(this.balanceSource == null) {
			if(other.balanceSource != null)
				return false;
		}else if(!this.balanceSource.equals(other.balanceSource))
			return false;
		
		if(this.currencyCode == null) {
			if(other.currencyCode != null)
				return false;
		}else if(!this.currencyCode.equals(other.currencyCode))
			return false;
		
		if(this.countryCode == null) {
			if(other.countryCode != null)
				return false;
		}else if(!this.countryCode.equals(other.countryCode))
			return false;
		
		if(this.bussinessDate == null) {
			if(other.bussinessDate != null)
				return false;
		}else if(!this.bussinessDate.equals(other.bussinessDate))
			return false;
		
		if(this.flexInternalBranchCode == null) {
			if(other.flexInternalBranchCode != null)
				return false;
		}else if(!this.flexInternalBranchCode.equals(other.flexInternalBranchCode))
			return false;
		
		if(this.fetchStaleBalance == null) {
			if(other.fetchStaleBalance != null)
				return false;
		}else if(!this.fetchStaleBalance.equals(other.fetchStaleBalance))
			return false;
		
		if(this.requestedEarmarkAmt == null) {
			if(other.requestedEarmarkAmt != null)
				return false;
		}else if(!this.requestedEarmarkAmt.equals(other.requestedEarmarkAmt))
			return false;
		
		if(this.flexibusCluster == null) {
			if(other.flexibusCluster != null)
				return false;
		}else if(!this.flexibusCluster.equals(other.flexibusCluster))
			return false;
		
		if(this.earmarkRefNo == null) {
			if(other.earmarkRefNo != null)
				return false;
		}else if(!this.earmarkRefNo.equals(other.earmarkRefNo))
			return false;
		
		if(this.retrySweepFlag != other.retrySweepFlag) 
			return false;
		
		return true;
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

	public String getAccountBic() {
		return accountBic;
	}

	public void setAccountBic(String accountBic) {
		this.accountBic = accountBic;
	}

	public String getAltBalAccountNo() {
		return altBalAccountNo;
	}

	public void setAltBalAccountNo(String altBalAccountNo) {
		this.altBalAccountNo = altBalAccountNo;
	}

	public String getAltBalBranchId() {
		return altBalBranchId;
	}

	public void setAltBalBranchId(String altBalBranchId) {
		this.altBalBranchId = altBalBranchId;
	}

	public String getAltBalAcctBic() {
		return altBalAcctBic;
	}

	public void setAltBalAcctBic(String altBalAcctBic) {
		this.altBalAcctBic = altBalAcctBic;
	}

	public String getBalanceSource() {
		return balanceSource;
	}

	public void setBalanceSource(String balanceSource) {
		this.balanceSource = balanceSource;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public boolean isPureHeaderFlag() {
		return pureHeaderFlag;
	}

	public void setPureHeaderFlag(boolean pureHeaderFlag) {
		this.pureHeaderFlag = pureHeaderFlag;
	}

	public Date getBussinessDate() {
		return bussinessDate;
	}

	public void setBussinessDate(Date bussinessDate) {
		this.bussinessDate = bussinessDate;
	}

	public String getFlexInternalBranchCode() {
		return flexInternalBranchCode;
	}

	public void setFlexInternalBranchCode(String flexInternalBranchCode) {
		this.flexInternalBranchCode = flexInternalBranchCode;
	}

	public String getFetchStaleBalance() {
		return fetchStaleBalance;
	}

	public void setFetchStaleBalance(String fetchStaleBalance) {
		this.fetchStaleBalance = fetchStaleBalance;
	}

	public BigDecimal getRequestedEarmarkAmt() {
		return requestedEarmarkAmt;
	}

	public void setRequestedEarmarkAmt(BigDecimal requestedEarmarkAmt) {
		this.requestedEarmarkAmt = requestedEarmarkAmt;
	}

	public String getFlexibusCluster() {
		return flexibusCluster;
	}

	public void setFlexibusCluster(String flexibusCluster) {
		this.flexibusCluster = flexibusCluster;
	}

	public boolean isRetrySweepFlag() {
		return retrySweepFlag;
	}

	public void setRetrySweepFlag(boolean retrySweepFlag) {
		this.retrySweepFlag = retrySweepFlag;
	}

	public String getEarmarkRefNo() {
		return earmarkRefNo;
	}

	public void setEarmarkRefNo(String earmarkRefNo) {
		this.earmarkRefNo = earmarkRefNo;
	}

	public char getDrainPoolIndicator() {
		return drainPoolIndicator;
	}

	public void setDrainPoolIndicator(char drainPoolIndicator) {
		this.drainPoolIndicator = drainPoolIndicator;
	}

}
