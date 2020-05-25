package com.inetpsa.poc00.rest.lambdadecisionparameters;

/**
 * The Class LambdaDecisionParametersRepresentation.
 */
public class LambdaDecisionParametersRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The higher limit. */
	private double higherLimit;

	/** The higher symbol. */
	private String higherSymbol;

	/** The lower limit. */
	private double lowerLimit;

	/** The lower symbol. */
	private String lowerSymbol;

	/** The fuel type label. */
	private String fuelTypeLabel;

	/** The edited. */
	private boolean edited = false;

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
	 * Gets the higher limit.
	 *
	 * @return the higher limit
	 */
	public double getHigherLimit() {
		return higherLimit;
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
		return higherSymbol;
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
		return lowerLimit;
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
		return lowerSymbol;
	}

	/**
	 * Sets the lower symbol.
	 *
	 * @param lowerSymbol the new lower symbol
	 */
	public void setLowerSymbol(String lowerSymbol) {
		this.lowerSymbol = lowerSymbol;
	}

	/**
	 * Gets the fuel type label.
	 *
	 * @return the fuel type label
	 */
	public String getFuelTypeLabel() {
		return fuelTypeLabel;
	}

	/**
	 * Sets the fuel type label.
	 *
	 * @param fuelTypeLabel the new fuel type label
	 */
	public void setFuelTypeLabel(String fuelTypeLabel) {
		this.fuelTypeLabel = fuelTypeLabel;
	}

	/**
	 * Checks if is edited.
	 *
	 * @return true, if is edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Sets the edited.
	 *
	 * @param edited the new edited
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LambdaDecisionParametersRepresentation [highLimit=" + higherLimit + ", highSymbol=" + higherSymbol + ", lowLimit=" + lowerLimit + ", lowSymbol=" + lowerSymbol + ", fuelLabel=" + fuelTypeLabel + "]";
	}

}
