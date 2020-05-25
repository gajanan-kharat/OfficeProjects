package com.inetpsa.poc00.domain.opacitydecisionparameters;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.domain.fueltype.FuelType;

/**
 * The persistent class for the copqtodp database table.
 */
@Entity
@Table(name = "COPQTODP")
public class OpacityDecisionParameters extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	protected Long entityId;

	/** The higher limit. */
	@Column(name = "HIGHER_LIMIT")
	private double higherLimit;

	/** The higher symbol. */
	@Column(name = "HIGHER_SYMBOL")
	private String higherSymbol;

	/** The lower limit. */
	@Column(name = "LOWER_LIMIT")
	private double lowerLimit;

	/** The lower symbol. */
	@Column(name = "LOWER_SYMBOL")
	private String lowerSymbol;

	/** The fuel type. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FUELTYPE_ID")
	private FuelType fuelType;

	/**
	 * Instantiates a new opacity decision parameters.
	 */
	public OpacityDecisionParameters() {
		super();
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
	 * Gets the higher limit.
	 *
	 * @return the higher limit
	 */
	public double getHigherLimit() {
		return this.higherLimit;
	}

	/**
	 * Sets the higher limit.
	 *
	 * @param higherLimit the new higher limit
	 */
	public void setHigherLimit(double higherLimit) {
		this.higherLimit = higherLimit;
	}

	/**
	 * Gets the higher symbol.
	 *
	 * @return the higher symbol
	 */
	public String getHigherSymbol() {
		return this.higherSymbol;
	}

	/**
	 * Sets the higher symbol.
	 *
	 * @param higherSymbol the new higher symbol
	 */
	public void setHigherSymbol(String higherSymbol) {
		this.higherSymbol = higherSymbol;
	}

	/**
	 * Gets the lower limit.
	 *
	 * @return the lower limit
	 */
	public double getLowerLimit() {
		return this.lowerLimit;
	}

	/**
	 * Sets the lower limit.
	 *
	 * @param lowerLimit the new lower limit
	 */
	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	/**
	 * Gets the lower symbol.
	 *
	 * @return the lower symbol
	 */
	public String getLowerSymbol() {
		return this.lowerSymbol;
	}

	/**
	 * Sets the lower symbol.
	 *
	 * @param lowerSymbol the new lower symbol
	 */
	public void setLowerSymbol(String lowerSymbol) {
		this.lowerSymbol = lowerSymbol;
	}

	public FuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

	@Override
	public String toString() {
		return "OpacityDecisionParameters [entityId=" + entityId + ", higherLimit=" + higherLimit + ", higherSymbol=" + higherSymbol + ", lowerLimit=" + lowerLimit + ", lowerSymbol=" + lowerSymbol + ", fuelType=" + fuelType + "]";
	}

}