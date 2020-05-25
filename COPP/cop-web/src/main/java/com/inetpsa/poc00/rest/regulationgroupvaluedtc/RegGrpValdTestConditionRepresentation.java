package com.inetpsa.poc00.rest.regulationgroupvaluedtc;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.RGValuedESDependentTCLRepresentation;


/**
 * The Class RegGrpValdTestConditionRepresentation.
 */
public class RegGrpValdTestConditionRepresentation {
	
	/** The entity id. */
	protected long entityId;
	
	/** The default value. */
	private String defaultValue;
	
	/** The label. */
	private String label;
	
	/** The data type. */
	private String dataType;
	
	/** The rg valued es dep TC lrepresent. */
	@JsonIgnore
	private RGValuedESDependentTCLRepresentation rgValuedEsDepTCLrepresent;
	
	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
	public long getEntityId() {
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
	 * Gets the default value.
	 *
	 * @return the default value
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * Sets the default value.
	 *
	 * @param defaultValue the new default value
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
	 * Gets the data type.
	 *
	 * @return the data type
	 */
	public String getDataType() {
		return dataType;
	}
	
	/**
	 * Sets the data type.
	 *
	 * @param dataType the new data type
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * Gets the rg valued es dep TC lrepresent.
	 *
	 * @return the rg valued es dep TC lrepresent
	 */
	/**
	 * @return the rgValuedEsDepTCLrepresent
	 */
	public RGValuedESDependentTCLRepresentation getRgValuedEsDepTCLrepresent() {
		return rgValuedEsDepTCLrepresent;
	}
	
	/**
	 * Sets the rg valued es dep TC lrepresent.
	 *
	 * @param rgValuedEsDepTCLrepresent the new rg valued es dep TC lrepresent
	 */
	public void setRgValuedEsDepTCLrepresent(
			RGValuedESDependentTCLRepresentation rgValuedEsDepTCLrepresent) {
		this.rgValuedEsDepTCLrepresent = rgValuedEsDepTCLrepresent;
	}
	
}
