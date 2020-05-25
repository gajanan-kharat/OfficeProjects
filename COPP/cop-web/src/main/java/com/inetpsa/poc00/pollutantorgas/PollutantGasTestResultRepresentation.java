/*
 * Creation : Nov 24, 2016
 */
/**
 * 
 */
package com.inetpsa.poc00.pollutantorgas;

import com.inetpsa.poc00.rest.tvvvaluedpgl.TvvValuedPollutantLimitRepresentation;

/**
 * Description
 * 
 * @author mehaj
 */
public class PollutantGasTestResultRepresentation {

	private Long entityId;

	private String value;

	private String statisticalDecision;
	private Long statisticalResult;

	private TvvValuedPollutantLimitRepresentation tvvValuedPollutantLimitRepresentation;

	/**
	 * Getter entityId
	 * 
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Setter entityId
	 * 
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Getter value
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter value
	 * 
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Getter tvvValuedPollutantLimitRepresentation
	 * 
	 * @return the tvvValuedPollutantLimitRepresentation
	 */
	public TvvValuedPollutantLimitRepresentation getTvvValuedPollutantLimitRepresentation() {
		return tvvValuedPollutantLimitRepresentation;
	}

	/**
	 * Setter tvvValuedPollutantLimitRepresentation
	 * 
	 * @param tvvValuedPollutantLimitRepresentation the tvvValuedPollutantLimitRepresentation to set
	 */
	public void setTvvValuedPollutantLimitRepresentation(TvvValuedPollutantLimitRepresentation tvvValuedPollutantLimitRepresentation) {
		this.tvvValuedPollutantLimitRepresentation = tvvValuedPollutantLimitRepresentation;
	}

	/**
	 * Getter statisticalDecision
	 * 
	 * @return the statisticalDecision
	 */
	public String getStatisticalDecision() {
		return statisticalDecision;
	}

	/**
	 * Setter statisticalDecision
	 * 
	 * @param statisticalDecision the statisticalDecision to set
	 */
	public void setStatisticalDecision(String statisticalDecision) {
		this.statisticalDecision = statisticalDecision;
	}

	public Long getStatisticalResult() {
		return statisticalResult;
	}

	public void setStatisticalResult(Long statisticalResult) {
		this.statisticalResult = statisticalResult;
	}

}
