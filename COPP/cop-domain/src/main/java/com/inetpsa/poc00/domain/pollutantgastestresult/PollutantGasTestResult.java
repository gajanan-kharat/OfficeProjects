/*
 * 
 */
package com.inetpsa.poc00.domain.pollutantgastestresult;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.tvvvaluedpglimit.TvvValuedPollutantGasLimit;

/**
 * The Class PollutantGasTestResult.
 */
@Entity
@Table(name = "COPQTPTR")
public class PollutantGasTestResult extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long entityId;

	/** The retest. */
	@Column(name = "RETEST")
	private Boolean retest;

	/** The statistical result. */
	@Column(name = "STATISTICAL_RESULT")
	private Long statisticalResult;

	/** The statistical decision. */
	@Column(name = "STATISTICAL_DECISION")
	private String statisticalDecision;

	/** The value. */
	@Column(name = "VALUE")
	private String value;

	/** The creation date. */
	@Column(name = "CREATION_DATE")
	private Date creationDate = new Date();

	/** The update date. */
	@Column(name = "UPDATE_DATE")
	private Date updateDate = new Date();

	/** The pollutant gas result set. */
	@ManyToOne
	@JoinColumn(name = "POLLGAS_RESULT_SET_ID")
	private PollutantGasResultSet pollutantGasResultSet;

	/** The tvv valued pol gas limit. */
	@ManyToOne
	@JoinColumn(name = "TVV_Valued_POLL_GAS_LIMIT")
	private TvvValuedPollutantGasLimit tvvValuedPollGasLimit;

	/**
	 * Sets the last update.
	 */
	@PreUpdate
	public void setLastUpdate() {
		this.updateDate = new Date();
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entityId
	 */
	@Override
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the pollutant gas result set.
	 * 
	 * @return the pollutantGasResultSet
	 */
	public PollutantGasResultSet getPollutantGasResultSet() {
		return pollutantGasResultSet;
	}

	/**
	 * Sets the pollutant gas result set.
	 * 
	 * @param pollutantGasResultSet the pollutantGasResultSet to set
	 */
	public void setPollutantGasResultSet(PollutantGasResultSet pollutantGasResultSet) {
		this.pollutantGasResultSet = pollutantGasResultSet;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the retest.
	 * 
	 * @return the retest
	 */
	public Boolean getRetest() {
		return retest;
	}

	/**
	 * Sets the retest.
	 * 
	 * @param retest the retest to set
	 */
	public void setRetest(Boolean retest) {
		this.retest = retest;
	}

	/**
	 * Gets the statistical result.
	 * 
	 * @return the statisticalResult
	 */
	public Long getStatisticalResult() {
		return statisticalResult;
	}

	/**
	 * Sets the statistical result.
	 * 
	 * @param statisticalResult the statisticalResult to set
	 */
	public void setStatisticalResult(Long statisticalResult) {
		this.statisticalResult = statisticalResult;
	}

	/**
	 * Gets the statistical decision.
	 * 
	 * @return the statisticalDecision
	 */
	public String getStatisticalDecision() {
		return statisticalDecision;
	}

	/**
	 * Sets the statistical decision.
	 * 
	 * @param statisticalDecision the statisticalDecision to set
	 */
	public void setStatisticalDecision(String statisticalDecision) {
		this.statisticalDecision = statisticalDecision;
	}

	/**
	 * Gets the creation date.
	 * 
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the update date.
	 * 
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * Gets the tvv valued poll gas limit.
	 * 
	 * @return the tvvValuedPollGasLimit
	 */
	public TvvValuedPollutantGasLimit getTvvValuedPollGasLimit() {
		return tvvValuedPollGasLimit;
	}

	/**
	 * Sets the tvv valued poll gas limit.
	 * 
	 * @param tvvValuedPollGasLimit the tvvValuedPollGasLimit to set
	 */
	public void setTvvValuedPollGasLimit(TvvValuedPollutantGasLimit tvvValuedPollGasLimit) {
		this.tvvValuedPollGasLimit = tvvValuedPollGasLimit;
	}

}
