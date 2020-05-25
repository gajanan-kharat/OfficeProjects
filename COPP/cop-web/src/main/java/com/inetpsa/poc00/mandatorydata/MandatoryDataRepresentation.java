/*
 * Creation : Apr 27, 2016
 */
package com.inetpsa.poc00.mandatorydata;

import java.util.List;

import com.inetpsa.poc00.rest.status.StatusNatureRepresentation;

/**
 * The Class MandatoryDataRepresentation.
 */
public class MandatoryDataRepresentation {

	/** The technical data. */
	private static String technicalData = "Donnée Technique";

	/** The tech condition data. */
	private static String techConditionData = "Condition d’essai";

	/** The technical limit data. */
	private static String technicalLimitData = "Limite";

	/** The entity id. */
	private Long entityId;

	/** The label. */
	// Technical data label
	private String label;

	/** The es dep list label. */
	// TCL or TDL list Label
	private String esDepListLabel;

	/** The eslabel. */
	// parent Label
	private String eslabel;

	/** The object type. */
	// use for saving
	private String objectType;

	/** The mandatory flag list. */
	List<StatusNatureRepresentation> mandatoryFlagList;

	/** The mandatory id values. */
	private String mandatoryIdValues;

	/**
	 * Instantiates a new mandatory data representation.
	 *
	 * @param entityId the entity id
	 * @param tdLabel the td label
	 * @param emsDepTDLLabel the ems dep TDL label
	 * @param emsDepTDLESlabel the ems dep TDLE slabel
	 * @param objectType the object type
	 * @param mandatoryIdValues the mandatory id values
	 */
	public MandatoryDataRepresentation(Long entityId, String tdLabel, String emsDepTDLLabel, String emsDepTDLESlabel, String objectType, String mandatoryIdValues) {

		this.entityId = entityId;
		this.label = tdLabel;
		this.esDepListLabel = emsDepTDLLabel;
		this.eslabel = emsDepTDLESlabel;
		this.objectType = objectType;
		this.mandatoryIdValues = mandatoryIdValues;
	}

	/**
	 * Instantiates a new mandatory data representation.
	 *
	 * @param entityId the entity id
	 * @param tdLabel the td label
	 * @param tvvDepTDLLabel the tvv dep TDL label
	 */
	public MandatoryDataRepresentation(Long entityId, String tdLabel, String tvvDepTDLLabel) {

		this.entityId = entityId;
		this.label = tdLabel;
		this.esDepListLabel = tvvDepTDLLabel;
	}

	/**
	 * Instantiates a new mandatory data representation.
	 */
	public MandatoryDataRepresentation() {
		// Default Constructor
	}

	/**
	 * Gets the technical data.
	 *
	 * @return the technical data
	 */
	public static String getTechnicalData() {
		return technicalData;
	}

	/**
	 * Sets the technical data.
	 *
	 * @param technicalData the new technical data
	 */
	public static void setTechnicalData(String technicalData) {
		MandatoryDataRepresentation.technicalData = technicalData;
	}

	/**
	 * Gets the tech condition data.
	 *
	 * @return the tech condition data
	 */
	public static String getTechConditionData() {
		return techConditionData;
	}

	/**
	 * Sets the tech condition data.
	 *
	 * @param techConditionData the new tech condition data
	 */
	public static void setTechConditionData(String techConditionData) {
		MandatoryDataRepresentation.techConditionData = techConditionData;
	}

	/**
	 * Gets the technical limit data.
	 *
	 * @return the technical limit data
	 */
	public static String getTechnicalLimitData() {
		return technicalLimitData;
	}

	/**
	 * Sets the technical limit data.
	 *
	 * @param technicalLimitData the new technical limit data
	 */
	public static void setTechnicalLimitData(String technicalLimitData) {
		MandatoryDataRepresentation.technicalLimitData = technicalLimitData;
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
	 * Gets the es dep list label.
	 *
	 * @return the es dep list label
	 */
	public String getEsDepListLabel() {
		return esDepListLabel;
	}

	/**
	 * Sets the es dep list label.
	 *
	 * @param esDepListLabel the new es dep list label
	 */
	public void setEsDepListLabel(String esDepListLabel) {
		this.esDepListLabel = esDepListLabel;
	}

	/**
	 * Gets the eslabel.
	 *
	 * @return the eslabel
	 */
	public String getEslabel() {
		return eslabel;
	}

	/**
	 * Sets the eslabel.
	 *
	 * @param eslabel the new eslabel
	 */
	public void setEslabel(String eslabel) {
		this.eslabel = eslabel;
	}

	/**
	 * Gets the object type.
	 *
	 * @return the object type
	 */
	public String getObjectType() {
		return objectType;
	}

	/**
	 * Sets the object type.
	 *
	 * @param objectType the new object type
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/**
	 * Gets the mandatory flag list.
	 *
	 * @return the mandatory flag list
	 */
	public List<StatusNatureRepresentation> getMandatoryFlagList() {
		return mandatoryFlagList;
	}

	/**
	 * Sets the mandatory flag list.
	 *
	 * @param mandatoryFlagList the new mandatory flag list
	 */
	public void setMandatoryFlagList(List<StatusNatureRepresentation> mandatoryFlagList) {
		this.mandatoryFlagList = mandatoryFlagList;
	}

	/**
	 * Gets the mandatory id values.
	 *
	 * @return the mandatory id values
	 */
	public String getMandatoryIdValues() {
		return mandatoryIdValues;
	}

	/**
	 * Sets the mandatory id values.
	 *
	 * @param mandatoryIdValues the new mandatory id values
	 */
	public void setMandatoryIdValues(String mandatoryIdValues) {
		this.mandatoryIdValues = mandatoryIdValues;
	}

}
