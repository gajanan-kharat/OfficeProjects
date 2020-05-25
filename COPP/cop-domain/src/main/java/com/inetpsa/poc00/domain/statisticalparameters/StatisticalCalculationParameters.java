/*
 * Creation : Dec 2, 2016
 */
package com.inetpsa.poc00.domain.statisticalparameters;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.pollutantgaslabel.PollutantGasLabel;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatisticalCalculationRule;

/**
 * The Class StatisticalCalculationParameters.
 */
@Entity
@Table(name = "COPQTSCP")
public class StatisticalCalculationParameters extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long entityId;

	/** The uncertainity factor. */
	@Column(name = "UNCERTAINTY_FACTOR")
	private Double uncertainityFactor;

	/** The limit1. */
	@Column(name = "LIMIT1")
	private Double limit1;

	/** The limit2. */
	@Column(name = "LIMIT2")
	private Double limit2;

	/** The limit3. */
	@Column(name = "LIMIT3")
	private Double limit3;

	/** The limit4. */
	@Column(name = "LIMIT4")
	private Double limit4;

	/** The sample maximum size. */
	@Column(name = "SAMPLE_MAXIMUM_SIZE")
	private Integer sampleMaximumSize;

	/** The pollutant gas label. */
	@ManyToOne
	@JoinColumn(name = "POLLUTANT_GAS_ID")
	private PollutantGasLabel pollutantGasLabel;

	/** The statistical calculation rule. */
	@ManyToOne
	@JoinColumn(name = "STATISTICAL_RULE_ID")
	private StatisticalCalculationRule statisticalCalculationRule;

	/**
	 * Gets the uncertainity factor.
	 * 
	 * @return the uncertainity factor
	 */
	public Double getUncertainityFactor() {
		return uncertainityFactor;
	}

	/**
	 * Sets the uncertainity factor.
	 * 
	 * @param uncertainityFactor the new uncertainity factor
	 */
	public void setUncertainityFactor(Double uncertainityFactor) {
		this.uncertainityFactor = uncertainityFactor;
	}

	/**
	 * Gets the limit1.
	 * 
	 * @return the limit1
	 */
	public Double getLimit1() {
		return limit1;
	}

	/**
	 * Sets the limit1.
	 * 
	 * @param limit1 the new limit1
	 */
	public void setLimit1(Double limit1) {
		this.limit1 = limit1;
	}

	/**
	 * Gets the limit2.
	 * 
	 * @return the limit2
	 */
	public Double getLimit2() {
		return limit2;
	}

	/**
	 * Sets the limit2.
	 * 
	 * @param limit2 the new limit2
	 */
	public void setLimit2(Double limit2) {
		this.limit2 = limit2;
	}

	/**
	 * Gets the limit3.
	 * 
	 * @return the limit3
	 */
	public Double getLimit3() {
		return limit3;
	}

	/**
	 * Sets the limit3.
	 * 
	 * @param limit3 the new limit3
	 */
	public void setLimit3(Double limit3) {
		this.limit3 = limit3;
	}

	/**
	 * Gets the limit4.
	 * 
	 * @return the limit4
	 */
	public Double getLimit4() {
		return limit4;
	}

	/**
	 * Sets the limit4.
	 * 
	 * @param limit4 the new limit4
	 */
	public void setLimit4(Double limit4) {
		this.limit4 = limit4;
	}

	/**
	 * Gets the sample maximum size.
	 * 
	 * @return the sample maximum size
	 */
	public Integer getSampleMaximumSize() {
		return sampleMaximumSize;
	}

	/**
	 * Sets the sample maximum size.
	 * 
	 * @param sampleMaximumSize the new sample maximum size
	 */
	public void setSampleMaximumSize(Integer sampleMaximumSize) {
		this.sampleMaximumSize = sampleMaximumSize;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
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
	 * Gets the pollutant gas label.
	 * 
	 * @return the pollutant gas label
	 */
	public PollutantGasLabel getPollutantGasLabel() {
		return pollutantGasLabel;
	}

	/**
	 * Sets the pollutant gas label.
	 * 
	 * @param pollutantGasLabel the new pollutant gas label
	 */
	public void setPollutantGasLabel(PollutantGasLabel pollutantGasLabel) {
		this.pollutantGasLabel = pollutantGasLabel;
	}

	/**
	 * Gets the statistical calculation rule.
	 * 
	 * @return the statistical calculation rule
	 */
	public StatisticalCalculationRule getStatisticalCalculationRule() {
		return statisticalCalculationRule;
	}

	/**
	 * Sets the statistical calculation rule.
	 * 
	 * @param statisticalCalculationRule the new statistical calculation rule
	 */
	public void setStatisticalCalculationRule(StatisticalCalculationRule statisticalCalculationRule) {
		this.statisticalCalculationRule = statisticalCalculationRule;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.domain.BaseEntity#toString()
	 */
	@Override
	public String toString() {
		return "UNCERTAINTY_FACTOR : " + uncertainityFactor + " limit1 : " + limit1 + " limit2 : " + limit2 + " limit3 : " + limit3 + " limit4 : " + limit4;
	}

}
