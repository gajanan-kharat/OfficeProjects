/*
 * Creation : Dec 2, 2016
 */
package com.inetpsa.poc00.rest.statisticalrulesparameters;

import com.inetpsa.poc00.rest.pollutantgaslabel.PollutantGasLabelRepresentation;
import com.inetpsa.poc00.rest.statisticalcalculationrule.StatisticalCalculationRuleRepresentation;

/**
 * The Class StatisticalParameterRepresentation.
 */
public class StatisticalParameterRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The uncertainity factor. */
	private Double uncertainityFactor;

	/** The limit1. */
	private Double limit1;

	/** The limit2. */
	private Double limit2;

	/** The limit3. */
	private Double limit3;

	/** The limit4. */
	private Double limit4;

	/** The sample maximum size. */
	private Integer sampleMaximumSize;

	/** The pollutant gas label rep. */
	private PollutantGasLabelRepresentation pollutantGasLabelRep;

	/** The statistical calculation rule rep. */
	private StatisticalCalculationRuleRepresentation statisticalCalculationRuleRep;

	/** The pgl entity id. */
	private Long pglEntityId;

	/** The scr entity id. */
	private Long scrEntityId;

	/**
	 * Instantiates a new statistical parameter representation.
	 */
	public StatisticalParameterRepresentation() {
			super();
	}

	/**
	 * Instantiates a new statistical parameter representation.
	 *
	 * @param entityId the entity id
	 * @param uncertainityFactor the uncertainity factor
	 * @param limit1 the limit1
	 * @param limit2 the limit2
	 * @param PGLabelId the PG label id
	 */
	public StatisticalParameterRepresentation(Long entityId, Double uncertainityFactor, Double limit1, Double limit2, Long PGLabelId) {
		this.entityId = entityId;
		this.uncertainityFactor = uncertainityFactor;
		this.limit1 = limit1;
		this.limit2 = limit2;
		this.pollutantGasLabelRep = new PollutantGasLabelRepresentation(PGLabelId);

	}

	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
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
	 * Gets the pollutant gas label rep.
	 *
	 * @return the pollutant gas label rep
	 */
	public PollutantGasLabelRepresentation getPollutantGasLabelRep() {
		return pollutantGasLabelRep;
	}

	/**
	 * Sets the pollutant gas label rep.
	 *
	 * @param pollutantGasLabelRep the new pollutant gas label rep
	 */
	public void setPollutantGasLabelRep(PollutantGasLabelRepresentation pollutantGasLabelRep) {
		this.pollutantGasLabelRep = pollutantGasLabelRep;
	}

	/**
	 * Gets the statistical calculation rule rep.
	 *
	 * @return the statistical calculation rule rep
	 */
	public StatisticalCalculationRuleRepresentation getStatisticalCalculationRuleRep() {
		return statisticalCalculationRuleRep;
	}

	/**
	 * Sets the statistical calculation rule rep.
	 *
	 * @param statisticalCalculationRuleRep the new statistical calculation rule rep
	 */
	public void setStatisticalCalculationRuleRep(StatisticalCalculationRuleRepresentation statisticalCalculationRuleRep) {
		this.statisticalCalculationRuleRep = statisticalCalculationRuleRep;
	}

	/**
	 * Gets the pgl entity id.
	 *
	 * @return the pgl entity id
	 */
	public Long getPglEntityId() {
		return pglEntityId;
	}

	/**
	 * Sets the pgl entity id.
	 *
	 * @param pglEntityId the new pgl entity id
	 */
	public void setPglEntityId(Long pglEntityId) {
		this.pglEntityId = pglEntityId;
	}

	/**
	 * Gets the scr entity id.
	 *
	 * @return the scr entity id
	 */
	public Long getScrEntityId() {
		return scrEntityId;
	}

	/**
	 * Sets the scr entity id.
	 *
	 * @param scrEntityId the new scr entity id
	 */
	public void setScrEntityId(Long scrEntityId) {
		this.scrEntityId = scrEntityId;
	}

}
