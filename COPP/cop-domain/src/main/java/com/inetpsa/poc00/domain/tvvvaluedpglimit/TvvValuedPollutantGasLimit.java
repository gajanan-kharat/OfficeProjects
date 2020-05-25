/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedpglimit;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgastestresult.PollutantGasTestResult;
import com.inetpsa.poc00.domain.tvvvaluedpgl.TvvValuedEsDepPGL;

/**
 * The Class TvvValuedPollutantGasLimit.
 */
@Entity
@Table(name = "COPQTVPG")
public class TvvValuedPollutantGasLimit extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long entityId;

	/** The max d value. */
	@Column(name = "MAXDVALUE")
	private Double maxDValue;

	/** The max d val rule. */
	@Column(name = "MAXDVALUERULE")
	private String maxDValRule;

	/** The min d value. */
	@Column(name = "MINDVALUE")
	private Double minDValue;

	/** The min d val rule. */
	@Column(name = "MINDVALUERULE")
	private String minDValRule;

	/** The value. */
	@Column(name = "LIMITVALUE")
	private String value;

	/** The pg label. */
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "LABEL_ID", nullable = false, updatable = false, insertable = true)
	private PollutantGasLabel pgLabel;

	/** The tvv valued es dep pgl. */
	@ManyToOne
	@JoinColumn(name = "PGLIST_ID", nullable = false, updatable = false, insertable = true)
	private TvvValuedEsDepPGL tvvValuedEsDepPGL;

	/** The pollutant limit. */
	@OneToOne
	@JoinColumn(name = "POLLUTANT_ID")
	private PollutantGasLimit pollutantLimit;

	/** The pollutant Gas test result. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tvvValuedPollGasLimit")
	private List<PollutantGasTestResult> pgTestResult = new ArrayList<>();

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public TvvValuedPollutantGasLimit() {
		super();
	}

	/**
	 * Instantiates a new tvv valued pollutant gas limit.
	 * 
	 * @param pollutantGasLabel the pollutant gas label
	 * @param maxDValRule the max d val rule
	 * @param maxDValue the max d value
	 * @param minDValRule the min d val rule
	 * @param minDValue the min d value
	 */
	public TvvValuedPollutantGasLimit(PollutantGasLabel pollutantGasLabel, String maxDValRule, Double maxDValue, String minDValRule, Double minDValue) {

		this.maxDValRule = maxDValRule;
		this.maxDValue = maxDValue;
		this.minDValRule = minDValRule;
		this.minDValue = minDValue;
		this.pgLabel = pollutantGasLabel;

	}

	/**
	 * Instantiates a new tvv valued pollutant gas limit.
	 * 
	 * @param pollutantGasLimit the pollutant gas limit
	 */
	public TvvValuedPollutantGasLimit(TvvValuedPollutantGasLimit pollutantGasLimit) {
		this.entityId = 0;
		this.maxDValRule = pollutantGasLimit.maxDValRule;
		this.maxDValue = pollutantGasLimit.maxDValue;
		this.minDValRule = pollutantGasLimit.minDValRule;
		this.minDValue = pollutantGasLimit.minDValue;
		this.pgLabel = new PollutantGasLabel(pollutantGasLimit.pgLabel);
		this.value = pollutantGasLimit.value;
		if (pollutantGasLimit.getPollutantLimit() != null) {
			this.pollutantLimit = new PollutantGasLimit(pollutantGasLimit.getPollutantLimit());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.domain.BaseEntity#getEntityId()
	 */
	@Override
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the max d value.
	 * 
	 * @return the max d value
	 */
	public Double getMaxDValue() {
		return maxDValue;
	}

	/**
	 * Sets the max d value.
	 * 
	 * @param maxDValue the new max d value
	 */
	public void setMaxDValue(Double maxDValue) {
		this.maxDValue = maxDValue;
	}

	/**
	 * Gets the max d val rule.
	 * 
	 * @return the max d val rule
	 */
	public String getMaxDValRule() {
		return maxDValRule;
	}

	/**
	 * Sets the max d val rule.
	 * 
	 * @param maxDValRule the new max d val rule
	 */
	public void setMaxDValRule(String maxDValRule) {
		this.maxDValRule = maxDValRule;
	}

	/**
	 * Gets the min d value.
	 * 
	 * @return the min d value
	 */
	public Double getMinDValue() {
		return minDValue;
	}

	/**
	 * Sets the min d value.
	 * 
	 * @param minDValue the new min d value
	 */
	public void setMinDValue(Double minDValue) {
		this.minDValue = minDValue;
	}

	/**
	 * Gets the min d val rule.
	 * 
	 * @return the min d val rule
	 */
	public String getMinDValRule() {
		return minDValRule;
	}

	/**
	 * Sets the min d val rule.
	 * 
	 * @param minDValRule the new min d val rule
	 */
	public void setMinDValRule(String minDValRule) {
		this.minDValRule = minDValRule;
	}

	/**
	 * Gets the pg label.
	 * 
	 * @return the pg label
	 */
	public PollutantGasLabel getPgLabel() {
		return pgLabel;
	}

	/**
	 * Sets the pg label.
	 * 
	 * @param pgLabel the new pg label
	 */
	public void setPgLabel(PollutantGasLabel pgLabel) {
		this.pgLabel = pgLabel;
	}

	/**
	 * Gets the tvv valued es dep pgl.
	 * 
	 * @return the tvv valued es dep pgl
	 */
	public TvvValuedEsDepPGL getTvvValuedEsDepPGL() {
		return tvvValuedEsDepPGL;
	}

	/**
	 * Sets the tvv valued es dep pgl.
	 * 
	 * @param tvvValuedEsDepPGL the new tvv valued es dep pgl
	 */
	public void setTvvValuedEsDepPGL(TvvValuedEsDepPGL tvvValuedEsDepPGL) {
		this.tvvValuedEsDepPGL = tvvValuedEsDepPGL;
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
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the pollutant limit.
	 * 
	 * @return the pollutant limit
	 */
	public PollutantGasLimit getPollutantLimit() {
		return pollutantLimit;
	}

	/**
	 * Sets the pollutant limit.
	 * 
	 * @param pollutantLimit the new pollutant limit
	 */
	public void setPollutantLimit(PollutantGasLimit pollutantLimit) {
		this.pollutantLimit = pollutantLimit;
	}

	/**
	 * to String implementation.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {

		if (pgLabel != null) {
			return pgLabel.getLabel();
		}

		return DomainConstants.EMPTY;
	}

}
