package com.inetpsa.poc00.rest.technicalgroup;

import java.util.Date;
import java.util.List;

import com.inetpsa.poc00.rest.regulationgroup.RegulationGroupRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;

/**
 * The Class TechnicalGroupRepresentation.
 */
public class TechnicalGroupRepresentation {

	/** The entity id. */
	protected Long entityId;

	/** The description. */
	private String description;

	/** The label. */
	private String label;

	/** The version. */
	private String version;

	/** The available. */
	private boolean available = false;

	/** The techgroupstatus. */
	private StatusRepresentation techgroupstatus;

	/** The valid. */
	private int valid = 1;

	/** The sampling label. */
	private String samplingLabel;

	/** The creation date. */
	private Date creationDate;

	/** The modification date. */
	private Date modificationDate;

	/** The tg selected. */
	private boolean tgSelected = false;

	/** The display date. */
	private String displayDate;

	/** The add tvv for tg. */
	private boolean addTvvForTg = false;

	/** The worst caseset. */
	private boolean worstCaseset = false;

	/** The show add tvv button. */
	private boolean showAddTvvButton = false;
	
	/** The new version. */
	private boolean newVersion = false;
	
	/** The show tvv button. */
	private boolean showTvvButton;

	/** The tvv representations. */
	private List<TvvRepresentation> tvvRepresentations;

	/** The regulation group id. */
	private Long regulationGroupId;

	/** The regulation group represent. */
	private RegulationGroupRepresentation regulationGroupRepresent;

	/**
	 * Instantiates a new technical group representation.
	 */
	public TechnicalGroupRepresentation() {
			super();
	}

	/**
	 * Instantiates a new technical group representation.
	 * 
	 * @param entityId
	 *            the entity id
	 * @param label
	 *            the label
	 * @param description
	 *            the description
	 * @param version
	 *            the version
	 * @param techgroupstatus
	 *            the techgroupstatus
	 * @param samplingLabel
	 *            the sampling label
	 * @param creationDate
	 *            the creation date
	 * @param modificationDate
	 *            the modification date
	 */
	public TechnicalGroupRepresentation(Long entityId, String label,
			String description, String version,
			StatusRepresentation techgroupstatus, String samplingLabel,
			Date creationDate, Date modificationDate) {
		super();
		this.entityId = entityId;
		this.label = label;
		this.description = description;
		this.version = version;
		this.techgroupstatus = techgroupstatus;
		this.samplingLabel = samplingLabel;

	}

	/**
	 * Instantiates a new technical group representation.
	 * 
	 * @param entityId
	 *            the entity id
	 * @param label
	 *            the label
	 * @param description
	 *            the description
	 * @param version
	 *            the version
	 * @param samplingLabel
	 *            the sampling label
	 * @param statusentityId
	 *            the statusentity id
	 * @param statusGuiLabel
	 *            the status gui label
	 * @param statusLabel
	 *            the status label
	 * @param creationDate
	 *            the creation date
	 * @param modificationDate
	 *            the modification date
	 * @param regulationGroupId
	 *            the regulation group id
	 */
	public TechnicalGroupRepresentation(Long entityId, String label,
			String description, String version, String samplingLabel,
			Long statusentityId, String statusGuiLabel, String statusLabel,
			Date creationDate, Date modificationDate, Long regulationGroupId) {
		this.entityId = entityId;
		this.label = label;
		this.description = description;
		this.version = version;
		this.samplingLabel = samplingLabel;
		this.techgroupstatus = new StatusRepresentation(statusentityId,
				statusGuiLabel, statusLabel);
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.regulationGroupId = regulationGroupId;

	}

	/**
	 * Instantiates a new technical group representation.
	 * 
	 * @param entityId
	 *            the entity id
	 * @param label
	 *            the label
	 * @param description
	 *            the description
	 * @param version
	 *            the version
	 * @param samplingLabel
	 *            the sampling label
	 * @param statusentityId
	 *            the statusentity id
	 * @param statusGuiLabel
	 *            the status gui label
	 * @param statusLabel
	 *            the status label
	 */
	public TechnicalGroupRepresentation(Long entityId, String label,
			String description, String version, String samplingLabel,
			Long statusentityId, String statusGuiLabel, String statusLabel) {
		this.entityId = entityId;
		this.label = label;
		this.description = description;
		this.version = version;
		this.samplingLabel = samplingLabel;
		this.techgroupstatus = new StatusRepresentation(statusentityId,
				statusGuiLabel, statusLabel);

	}

	/**
	 * Gets the creation date.
	 * 
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the modification date.
	 * 
	 * @return the modificationDate
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * Sets the modification date.
	 * 
	 * @param modificationDate
	 *            the modificationDate to set
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * Gets the valid.
	 * 
	 * @return the valid
	 */
	public int getValid() {
		return valid;
	}

	/**
	 * Sets the valid.
	 * 
	 * @param valid
	 *            the valid to set
	 */
	public void setValid(int valid) {
		this.valid = valid;
	}

	/**
	 * Gets the techgroupstatus.
	 * 
	 * @return the techgroupstatus
	 */
	public StatusRepresentation getTechgroupstatus() {
		return techgroupstatus;
	}

	/**
	 * Sets the techgroupstatus.
	 * 
	 * @param techgroupstatus
	 *            the techgroupstatus to set
	 */
	public void setTechgroupstatus(StatusRepresentation techgroupstatus) {
		this.techgroupstatus = techgroupstatus;
	}

	/**
	 * Checks if is available.
	 * 
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * Sets the available.
	 * 
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
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
	 * @param entityId
	 *            the entityId to set
	 */
	public void setEntityId(Long entityId) {
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
	 * @param description
	 *            the description to set
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
	 * @param label
	 *            the label to set
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
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the sampling label.
	 * 
	 * @return the samplingLabel
	 */
	public String getSamplingLabel() {
		return samplingLabel;
	}

	/**
	 * Sets the sampling label.
	 * 
	 * @param samplingLabel
	 *            the samplingLabel to set
	 */
	public void setSamplingLabel(String samplingLabel) {
		this.samplingLabel = samplingLabel;
	}

	/**
	 * Checks if is tg selected.
	 * 
	 * @return the tgSelected
	 */
	public boolean isTgSelected() {
		return tgSelected;
	}

	/**
	 * Sets the tg selected.
	 * 
	 * @param tgSelected
	 *            the tgSelected to set
	 */
	public void setTgSelected(boolean tgSelected) {
		this.tgSelected = tgSelected;
	}

	/**
	 * Gets the display date.
	 * 
	 * @return the displayDate
	 */
	public String getDisplayDate() {
		return displayDate;
	}

	/**
	 * Sets the display date.
	 * 
	 * @param displayDate
	 *            the displayDate to set
	 */
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}

	/**
	 * Checks if is adds the tvv for tg.
	 * 
	 * @return the addTvvForTg
	 */
	public boolean isAddTvvForTg() {
		return addTvvForTg;
	}

	/**
	 * Sets the adds the tvv for tg.
	 * 
	 * @param addTvvForTg
	 *            the addTvvForTg to set
	 */
	public void setAddTvvForTg(boolean addTvvForTg) {
		this.addTvvForTg = addTvvForTg;
	}

	/**
	 * Checks if is worst caseset.
	 * 
	 * @return the worstCaseset
	 */
	public boolean isWorstCaseset() {
		return worstCaseset;
	}

	/**
	 * Sets the worst caseset.
	 * 
	 * @param worstCaseset
	 *            the worstCaseset to set
	 */
	public void setWorstCaseset(boolean worstCaseset) {
		this.worstCaseset = worstCaseset;
	}

	/**
	 * Checks if is show add tvv button.
	 * 
	 * @return the showAddTvvButton
	 */
	public boolean isShowAddTvvButton() {
		return showAddTvvButton;
	}

	/**
	 * Sets the show add tvv button.
	 * 
	 * @param showAddTvvButton
	 *            the showAddTvvButton to set
	 */
	public void setShowAddTvvButton(boolean showAddTvvButton) {
		this.showAddTvvButton = showAddTvvButton;
	}

	/**
	 * Gets the regulation group represent.
	 * 
	 * @return the regulationGroupRepresent
	 */
	public RegulationGroupRepresentation getRegulationGroupRepresent() {
		return regulationGroupRepresent;
	}

	/**
	 * Sets the regulation group represent.
	 * 
	 * @param regulationGroupRepresent
	 *            the regulationGroupRepresent to set
	 */
	public void setRegulationGroupRepresent(
			RegulationGroupRepresentation regulationGroupRepresent) {
		this.regulationGroupRepresent = regulationGroupRepresent;
	}

	/**
	 * Gets the tvv representations.
	 * 
	 * @return the tvvRepresentations
	 */
	public List<TvvRepresentation> getTvvRepresentations() {
		return tvvRepresentations;
	}

	/**
	 * Sets the tvv representations.
	 * 
	 * @param tvvRepresentations
	 *            the tvvRepresentations to set
	 */
	public void setTvvRepresentations(List<TvvRepresentation> tvvRepresentations) {
		this.tvvRepresentations = tvvRepresentations;
	}

	/**
	 * Gets the regulation group id.
	 * 
	 * @return the regulationGroupId
	 */
	public Long getRegulationGroupId() {
		return regulationGroupId;
	}

	/**
	 * Sets the regulation group id.
	 * 
	 * @param regulationGroupId
	 *            the regulationGroupId to set
	 */
	public void setRegulationGroupId(Long regulationGroupId) {
		this.regulationGroupId = regulationGroupId;
	}

	/**
	 * Checks if is new version.
	 *
	 * @return true, if is new version
	 */
	public boolean isNewVersion() {
		return newVersion;
	}

	/**
	 * Sets the new version.
	 *
	 * @param newVersion the new new version
	 */
	public void setNewVersion(boolean newVersion) {
		this.newVersion = newVersion;
	}

	/**
	 * Checks if is show tvv button.
	 *
	 * @return true, if is show tvv button
	 */
	public boolean isShowTvvButton() {
		return showTvvButton;
	}

	/**
	 * Sets the show tvv button.
	 *
	 * @param showTvvButton the new show tvv button
	 */
	public void setShowTvvButton(boolean showTvvButton) {
		this.showTvvButton = showTvvButton;
	}


}
