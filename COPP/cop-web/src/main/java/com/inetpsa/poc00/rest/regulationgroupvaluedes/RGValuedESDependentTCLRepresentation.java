package com.inetpsa.poc00.rest.regulationgroupvaluedes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupRepresentation;
import com.inetpsa.poc00.rest.regulationgroupvaluedtc.RegGrpValdTestConditionRepresentation;


/**
 * The Class RGValuedESDependentTCLRepresentation.
 */
public class RGValuedESDependentTCLRepresentation {
	
	/** The entity id. */
	protected long entityId;
	
	/** The description. */
	private String description;
	
	/** The label. */
	private String label;
	
	/** The version. */
	private String version;
	
	/** The rg valued generic test conditionrepresent. */
	private List<RegGrpValdTestConditionRepresentation> rgValuedGenericTestConditionrepresent;
	
	/** The regulation grouprepresent. */
	@JsonIgnore
	private RegulationGroupRepresentation regulationGrouprepresent;
	
	/** The edited values. */
	private int editedValues;
	
	/**
	 * Instantiates a new RG valued ES dependent TCL representation.
	 */
	public RGValuedESDependentTCLRepresentation() {
		super();
	}

	/**
	 * Instantiates a new RG valued ES dependent TCL representation.
	 *
	 * @param entityId the entity id
	 * @param description the description
	 * @param label the label
	 * @param version the version
	 * @param rgValuedGenericTestConditionrepresent the rg valued generic test conditionrepresent
	 */
	public RGValuedESDependentTCLRepresentation(long entityId, String description, String label, String version, List<RegGrpValdTestConditionRepresentation> rgValuedGenericTestConditionrepresent) {
		super();
		this.entityId = entityId;
		this.description = description;
		this.label = label;
		this.version = version;
		this.rgValuedGenericTestConditionrepresent = rgValuedGenericTestConditionrepresent;

	}


	/**
	 * Gets the edited values.
	 *
	 * @return the edited values
	 */
	public int getEditedValues() {
		return editedValues;
	}

	/**
	 * Sets the edited values.
	 *
	 * @param editedValues the new edited values
	 */
	public void setEditedValues(int editedValues) {
		this.editedValues = editedValues;
	}

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
	 * Gets the rg valued generic test conditionrepresent.
	 *
	 * @return the rg valued generic test conditionrepresent
	 */
	/**
	 * @return the rgValuedGenericTestConditionrepresent
	 */
	public List<RegGrpValdTestConditionRepresentation> getRgValuedGenericTestConditionrepresent() {
		return rgValuedGenericTestConditionrepresent;
	}

	/**
	 * Sets the rg valued generic test conditionrepresent.
	 *
	 * @param rgValuedGenericTestConditionrepresent the new rg valued generic test conditionrepresent
	 */
	public void setRgValuedGenericTestConditionrepresent(List<RegGrpValdTestConditionRepresentation> rgValuedGenericTestConditionrepresent) {
		this.rgValuedGenericTestConditionrepresent = rgValuedGenericTestConditionrepresent;
	}

	/**
	 * Gets the regulation grouprepresent.
	 *
	 * @return the regulation grouprepresent
	 */
	public RegulationGroupRepresentation getRegulationGrouprepresent() {
		return regulationGrouprepresent;
	}

	/**
	 * Sets the regulation grouprepresent.
	 *
	 * @param regulationGrouprepresent the new regulation grouprepresent
	 */
	public void setRegulationGrouprepresent(RegulationGroupRepresentation regulationGrouprepresent) {
		this.regulationGrouprepresent = regulationGrouprepresent;
	}

}
