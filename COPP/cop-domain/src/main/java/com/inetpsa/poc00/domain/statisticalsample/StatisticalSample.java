/*
 * Creation : Nov 28, 2016
 */
/*
 * Creation : Nov 23, 2016
 */
package com.inetpsa.poc00.domain.statisticalsample;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.factory.CarFactory;
import com.inetpsa.poc00.domain.pollutantgasresultset.PollutantGasResultSet;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.typeoftest.TypeOfTest;

/**
 * The Class StatisticalSample.
 */
@Entity
@Table(name = "COPQTSSL")
public class StatisticalSample extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long entityId;

	/** The maximum size. */
	@Column(name = "MAXIMUM_SIZE")
	Integer maximumSize;

	/** The no of elements. */
	@Column(name = "NO_OF_ELEMENTS")
	Integer noOfElements = 0;

	/** The standard deviation. */
	@Column(name = "STANDARD_DEVIATION")
	Double standardDeviation;

	/** The global decision. */
	@Column(name = "GLOBAL_DECISION")
	String globalDecision;

	/** The closed. */
	@Column(name = "CLOSED")
	Boolean closed;

	/** The technical case. */
	@ManyToOne
	@JoinColumn(name = "TECHNICALCASE_ID")
	private TechnicalCase technicalCase;

	/** The car factory. */
	@ManyToOne
	@JoinColumn(name = "FACTORY_ID")
	private CarFactory carFactory;

	@ManyToOne
	@JoinColumn(name = "TYPE_OF_TEST_ID")
	private TypeOfTest testType;

	/** The pollutant gas result set. */

	@OneToMany(mappedBy = "statisticalSample")
	private Set<PollutantGasResultSet> pollutantGasResultSet;

	public StatisticalSample() {
		super();
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
	public Boolean isClosed() {
		return closed;
	}

	/**
	 * Sets the closed.
	 *
	 * @param closed the new closed
	 */
	public void setClosed(Boolean closed) {
		this.closed = closed;
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
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the technical case.
	 *
	 * @return the technical case
	 */
	public TechnicalCase getTechnicalCase() {
		return technicalCase;
	}

	/**
	 * Sets the technical case.
	 *
	 * @param technicalCase the new technical case
	 */
	public void setTechnicalCase(TechnicalCase technicalCase) {
		this.technicalCase = technicalCase;
	}

	/**
	 * Gets the car factory.
	 *
	 * @return the car factory
	 */
	public CarFactory getCarFactory() {
		return carFactory;
	}

	/**
	 * Sets the car factory.
	 *
	 * @param carFactory the new car factory
	 */
	public void setCarFactory(CarFactory carFactory) {
		this.carFactory = carFactory;
	}

	/**
	 * Gets the pollutant gas result set.
	 *
	 * @return the pollutant gas result set
	 */
	public Set<PollutantGasResultSet> getPollutantGasResultSet() {
		return pollutantGasResultSet;
	}

	/**
	 * Sets the pollutant gas result set.
	 *
	 * @param pollutantGasResultSet the new pollutant gas result set
	 */
	public void setPollutantGasResultSet(Set<PollutantGasResultSet> pollutantGasResultSet) {
		this.pollutantGasResultSet = pollutantGasResultSet;
	}

	public TypeOfTest getTestType() {
		return testType;
	}

	public void setTestType(TypeOfTest testType) {
		this.testType = testType;
	}
}
