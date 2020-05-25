/*
 * Creation : Nov 28, 2016
 */
/*
 * Creation : Nov 23, 2016
 */
package com.inetpsa.poc00.rest.statisticalsample;

import java.util.Date;
import java.util.Set;

import com.inetpsa.poc00.rest.carfactory.CarFactoryRepresentation;
import com.inetpsa.poc00.rest.resultset.PollGasResultSetRepresentation;
import com.inetpsa.poc00.rest.technicalcase.TechnicalCaseRepresentation;

/**
 * The Class StatisticalSampleRepresentation.
 */
public class StatisticalSampleRepresentation {

	/** The entity id. */
	protected Long entityId;

	/** The statistical sample id. */
	private String statisticalSampleId;

	/** The maximum size. */
	private Integer maximumSize;

	/** The no of elements. */
	private Integer noOfElements;

	/** The standard deviation. */
	private Double standardDeviation;

	/** The global decision. */
	private String globalDecision;

	/** The closed. */
	boolean closed;

	/** The technical case representation. */
	private TechnicalCaseRepresentation technicalCaseRepresentation;

	/** The car factory representation. */
	private CarFactoryRepresentation carFactoryRepresentation;

	/** The pollutant gas result set representation. */
	private Set<PollGasResultSetRepresentation> pollutantGasResultSetRepresentation;

	/** The es label. */
	private String esLabel;

	/** The statistical rule label. */
	private String statisticalRuleLabel;

	/** The min date. */
	private Date minDate;

	/** The max date. */
	private Date maxDate;

	/**
	 * Instantiates a new statistical sample representation.
	 */
	public StatisticalSampleRepresentation() {
		super();
	}

	/**
	 * Instantiates a new statistical sample representation.
	 * 
	 * @param emissionstandardLabel the emissionstandard label
	 * @param carFactoryLabel the car factory label
	 * @param statisticalRuleLabel the statistical rule label
	 */
	public StatisticalSampleRepresentation(String emissionstandardLabel, String carFactoryLabel, String statisticalRuleLabel) {
		this.esLabel = emissionstandardLabel;
		this.carFactoryRepresentation = new CarFactoryRepresentation(carFactoryLabel);
		this.statisticalRuleLabel = statisticalRuleLabel;
	}

	/**
	 * Instantiates a new statistical sample representation.
	 * 
	 * @param entityId the entity id
	 * @param minDate the min date
	 * @param maxDate the max date
	 */
	public StatisticalSampleRepresentation(Long entityId, Date minDate, Date maxDate) {
		this.entityId = entityId;
		this.minDate = minDate;
		this.maxDate = maxDate;
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
	 * Gets the statistical sample id.
	 * 
	 * @return the statistical sample id
	 */
	public String getStatisticalSampleId() {
		return statisticalSampleId;
	}

	/**
	 * Sets the statistical sample id.
	 * 
	 * @param statisticalSampleId the new statistical sample id
	 */
	public void setStatisticalSampleId(String statisticalSampleId) {
		this.statisticalSampleId = statisticalSampleId;
	}

	/**
	 * Gets the maximum size.
	 * 
	 * @return the maximum size
	 */
	public Integer getMaximumSize() {
		return maximumSize;
	}

	/**
	 * Sets the maximum size.
	 * 
	 * @param maximumSize the new maximum size
	 */
	public void setMaximumSize(Integer maximumSize) {
		this.maximumSize = maximumSize;
	}

	/**
	 * Gets the no of elements.
	 * 
	 * @return the no of elements
	 */
	public Integer getNoOfElements() {
		return noOfElements;
	}

	/**
	 * Sets the no of elements.
	 * 
	 * @param noOfElements the new no of elements
	 */
	public void setNoOfElements(Integer noOfElements) {
		this.noOfElements = noOfElements;
	}

	/**
	 * Gets the standard deviation.
	 * 
	 * @return the standard deviation
	 */
	public Double getStandardDeviation() {
		return standardDeviation;
	}

	/**
	 * Sets the standard deviation.
	 * 
	 * @param standardDeviation the new standard deviation
	 */
	public void setStandardDeviation(Double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	/**
	 * Gets the global decision.
	 * 
	 * @return the global decision
	 */
	public String getGlobalDecision() {
		return globalDecision;
	}

	/**
	 * Sets the global decision.
	 * 
	 * @param globalDecision the new global decision
	 */
	public void setGlobalDecision(String globalDecision) {
		this.globalDecision = globalDecision;
	}

	/**
	 * Checks if is closed.
	 * 
	 * @return true, if is closed
	 */
	public boolean isClosed() {
		return closed;
	}

	/**
	 * Sets the closed.
	 * 
	 * @param closed the new closed
	 */
	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	/**
	 * Gets the technical case representation.
	 * 
	 * @return the technical case representation
	 */
	public TechnicalCaseRepresentation getTechnicalCaseRepresentation() {
		return technicalCaseRepresentation;
	}

	/**
	 * Sets the technical case representation.
	 * 
	 * @param technicalCaseRepresentation the new technical case representation
	 */
	public void setTechnicalCaseRepresentation(TechnicalCaseRepresentation technicalCaseRepresentation) {
		this.technicalCaseRepresentation = technicalCaseRepresentation;
	}

	/**
	 * Gets the car factory representation.
	 * 
	 * @return the car factory representation
	 */
	public CarFactoryRepresentation getCarFactoryRepresentation() {
		return carFactoryRepresentation;
	}

	/**
	 * Sets the car factory representation.
	 * 
	 * @param carFactoryRepresentation the new car factory representation
	 */
	public void setCarFactoryRepresentation(CarFactoryRepresentation carFactoryRepresentation) {
		this.carFactoryRepresentation = carFactoryRepresentation;
	}

	/**
	 * Gets the pollutant gas result set representation.
	 * 
	 * @return the pollutant gas result set representation
	 */
	public Set<PollGasResultSetRepresentation> getPollutantGasResultSetRepresentation() {
		return pollutantGasResultSetRepresentation;
	}

	/**
	 * Sets the pollutant gas result set representation.
	 * 
	 * @param pollutantGasResultSetRepresentation the new pollutant gas result set representation
	 */
	public void setPollutantGasResultSetRepresentation(Set<PollGasResultSetRepresentation> pollutantGasResultSetRepresentation) {
		this.pollutantGasResultSetRepresentation = pollutantGasResultSetRepresentation;
	}

	/**
	 * Gets the es label.
	 * 
	 * @return the es label
	 */
	public String getEsLabel() {
		return esLabel;
	}

	/**
	 * Sets the es label.
	 * 
	 * @param esLabel the new es label
	 */
	public void setEsLabel(String esLabel) {
		this.esLabel = esLabel;
	}

	/**
	 * Gets the statistical rule label.
	 * 
	 * @return the statistical rule label
	 */
	public String getStatisticalRuleLabel() {
		return statisticalRuleLabel;
	}

	/**
	 * Sets the statistical rule label.
	 * 
	 * @param statisticalRuleLabel the new statistical rule label
	 */
	public void setStatisticalRuleLabel(String statisticalRuleLabel) {
		this.statisticalRuleLabel = statisticalRuleLabel;
	}

	/**
	 * Gets the min date.
	 * 
	 * @return the min date
	 */
	public Date getMinDate() {
		return minDate;
	}

	/**
	 * Sets the min date.
	 * 
	 * @param minDate the new min date
	 */
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	/**
	 * Gets the max date.
	 * 
	 * @return the max date
	 */
	public Date getMaxDate() {
		return maxDate;
	}

	/**
	 * Sets the max date.
	 * 
	 * @param maxDate the new max date
	 */
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

}
