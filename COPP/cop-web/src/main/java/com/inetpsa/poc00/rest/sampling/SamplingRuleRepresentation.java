package com.inetpsa.poc00.rest.sampling;

import java.util.List;


/**
 * The Class SamplingRuleRepresentation.
 */
public class SamplingRuleRepresentation {

	/** The entity id. */
	protected Long entityId;

	/** The amt or percent. */
	private String amtOrPercent;

	/** The amt type. */
	private String amtType;

	/** The description. */
	private String description;

	/** The frequency. */
	private Integer frequency;

	/** The higher limit. */
	private Integer higherLimit;

	/** The higher symbol. */
	private String higherSymbol;

	/** The lower limit. */
	private Integer lowerLimit;

	/** The lower symbol. */
	private String lowerSymbol;

	/** The needed amt. */
	private Double neededAmt;

	/** The label. */
	private String label;

	/** The version. */
	private String version;

	/** The enable lower limit. */
	private boolean enableLowerLimit = false;

	/** The enable lower symbol. */
	private boolean enableLowerSymbol = false;

	/** The enable higher limit. */
	private boolean enableHigherLimit = false;

	/** The enable higher symbol. */
	private boolean enableHigherSymbol = false;

	/** The data modified. */
	private boolean dataModified = false;

	/** The new data added. */
	private boolean newDataAdded = false;
	
	private List<String> descriptions;

	/**
	 * Instantiates a new edits the sampling rule representation.
	 */
	public SamplingRuleRepresentation() {
		super();
	}

	/**
	 * Instantiates a new edits the sampling rule representation.
	 * 
	 * @param label the label
	 */
	public SamplingRuleRepresentation(String label) {
		this.label = label;
	}

	/**
	 * Instantiates a new edits the sampling rule representation.
	 * 
	 * @param entityId the entity id
	 * @param amtOrPercent the amt or percent
	 * @param amtType the amt type
	 * @param description the description
	 * @param frequency the frequency
	 * @param higherLimit the higher limit
	 * @param higherSymbol the higher symbol
	 * @param lowerLimit the lower limit
	 * @param lowerSymbol the lower symbol
	 * @param neededAmt the needed amt
	 * @param label the label
	 * @param version the version
	 */
	public SamplingRuleRepresentation(Long entityId, String amtOrPercent, String amtType, String description, Integer frequency, Integer higherLimit, String higherSymbol, Integer lowerLimit, String lowerSymbol, Double neededAmt, String label, String version) {

		super();
		this.entityId = entityId;
		this.amtOrPercent = amtOrPercent;
		this.amtType = amtType;
		this.description = description;
		this.frequency = frequency;
		this.higherLimit = higherLimit;
		this.higherSymbol = higherSymbol;
		this.lowerLimit = lowerLimit;
		this.lowerSymbol = lowerSymbol;
		this.neededAmt = neededAmt;
		this.label = label;
		this.version = version;
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
	 * Gets the amt or percent.
	 * 
	 * @return the amt or percent
	 */
	public String getAmtOrPercent() {
		return amtOrPercent;
	}

	/**
	 * Sets the amt or percent.
	 * 
	 * @param amtOrPercent the new amt or percent
	 */
	public void setAmtOrPercent(String amtOrPercent) {
		this.amtOrPercent = amtOrPercent;
	}

	/**
	 * Gets the amt type.
	 * 
	 * @return the amt type
	 */
	public String getAmtType() {
		return amtType;
	}

	/**
	 * Sets the amt type.
	 * 
	 * @param amtType the new amt type
	 */
	public void setAmtType(String amtType) {
		this.amtType = amtType;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the frequency.
	 * 
	 * @return the frequency
	 */
	public Integer getFrequency() {
		return frequency;
	}

	/**
	 * Sets the frequency.
	 * 
	 * @param frequency the new frequency
	 */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
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
	 * Gets the higher limit.
	 * 
	 * @return the higher limit
	 */
	public Integer getHigherLimit() {
		return higherLimit;
	}

	/**
	 * Sets the higher limit.
	 * 
	 * @param higherLimit the new higher limit
	 */
	public void setHigherLimit(Integer higherLimit) {
		this.higherLimit = higherLimit;
	}

	/**
	 * Gets the lower limit.
	 * 
	 * @return the lower limit
	 */
	public Integer getLowerLimit() {
		return lowerLimit;
	}

	/**
	 * Sets the lower limit.
	 * 
	 * @param lowerLimit the new lower limit
	 */
	public void setLowerLimit(Integer lowerLimit) {
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
	 * Gets the needed amt.
	 * 
	 * @return the needed amt
	 */
	public Double getNeededAmt() {
		return neededAmt;
	}

	/**
	 * Sets the needed amt.
	 * 
	 * @param neededAmt the new needed amt
	 */
	public void setNeededAmt(Double neededAmt) {
		this.neededAmt = neededAmt;
	}

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Checks if is enable lower limit.
	 * 
	 * @return the enableLowerLimit
	 */
	public boolean isEnableLowerLimit() {
		return enableLowerLimit;
	}

	/**
	 * Sets the enable lower limit.
	 * 
	 * @param enableLowerLimit the enableLowerLimit to set
	 */
	public void setEnableLowerLimit(boolean enableLowerLimit) {
		this.enableLowerLimit = enableLowerLimit;
	}

	/**
	 * Checks if is enable lower symbol.
	 * 
	 * @return the enableLowerSymbol
	 */
	public boolean isEnableLowerSymbol() {
		return enableLowerSymbol;
	}

	/**
	 * Sets the enable lower symbol.
	 * 
	 * @param enableLowerSymbol the enableLowerSymbol to set
	 */
	public void setEnableLowerSymbol(boolean enableLowerSymbol) {
		this.enableLowerSymbol = enableLowerSymbol;
	}

	/**
	 * Checks if is enable higher limit.
	 * 
	 * @return the enableHigherLimit
	 */
	public boolean isEnableHigherLimit() {
		return enableHigherLimit;
	}

	/**
	 * Sets the enable higher limit.
	 * 
	 * @param enableHigherLimit the enableHigherLimit to set
	 */
	public void setEnableHigherLimit(boolean enableHigherLimit) {
		this.enableHigherLimit = enableHigherLimit;
	}

	/**
	 * Checks if is enable higher symbol.
	 * 
	 * @return the enableHigherSymbol
	 */
	public boolean isEnableHigherSymbol() {
		return enableHigherSymbol;
	}

	/**
	 * Sets the enable higher symbol.
	 * 
	 * @param enableHigherSymbol the enableHigherSymbol to set
	 */
	public void setEnableHigherSymbol(boolean enableHigherSymbol) {
		this.enableHigherSymbol = enableHigherSymbol;
	}

	/**
	 * Checks if is data modified.
	 * 
	 * @return the dataModified
	 */
	public boolean isDataModified() {
		return dataModified;
	}

	/**
	 * Sets the data modified.
	 * 
	 * @param dataModified the dataModified to set
	 */
	public void setDataModified(boolean dataModified) {
		this.dataModified = dataModified;
	}

	/**
	 * Checks if is new data added.
	 * 
	 * @return the newDataAdded
	 */
	public boolean isNewDataAdded() {
		return newDataAdded;
	}

	/**
	 * Sets the new data added.
	 * 
	 * @param newDataAdded the newDataAdded to set
	 */
	public void setNewDataAdded(boolean newDataAdded) {
		this.newDataAdded = newDataAdded;
	}

	/**
	 * @return the descriptions
	 */
	public List<String> getDescriptions() {
		return descriptions;
	}

	/**
	 * @param descriptions the descriptions to set
	 */
	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

	/**
	 * @return the isHigherLimitRequired
	 */

}