package com.inetpsa.poc00.rest.regulationgroup;

import java.util.Date;
import java.util.List;

import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.RGValuedESDependentTCLRepresentation;
import com.inetpsa.poc00.rest.statisticalcalculationrule.StatisticalCalculationRuleRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentation;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupRepresentation;
import com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaRepresentation;
import com.inetpsa.poc00.rest.wtlp.WLTPVLowHighDataRepresentation;

/**
 * The Class RegulationGroupRepresentation.
 */
public class RegulationGroupRepresentation {

	/** The entity id. */
	protected Long entityId;

	/** The description. */
	private String description;

	/** The label. */
	private String label;

	/** The version. */
	private String version;

	/** The creation date. */
	private Date creationDate;

	/** The modification date. */
	private Date modificationDate;

	/** The regulationgroupstatus. */
	private StatusRepresentation regulationgroupstatus;

	/** The valid. */
	private int valid;

	/** The add tg for rg. */
	private boolean addTgForRg;

	/** The show add tg. */
	private boolean showAddTg;

	/** The new rg version. */
	private boolean newRgVersion;

	/** The type approval area. */
	private TypeApprovalAreaRepresentation typeApprovalArea;

	/** The emission standardfor rg. */
	private EmissionStandardRepresentation emissionStandardforRg;

	/** The technical groups. */
	private TechnicalGroupRepresentation technicalGroups;

	/** The rg valued es dep TCL. */
	private List<RGValuedESDependentTCLRepresentation> rgValuedEsDepTCL;

	/** The wltp representation. */
	private WLTPVLowHighDataRepresentation wltpRepresentation;

	/** The display date. */
	private String displayDate;

	/** The statistical rule representation. */
	private StatisticalCalculationRuleRepresentation statisticalRuleRepresentation;

	/** The old statistical object. */
	private StatisticalCalculationRuleRepresentation oldStatisticalObject;

	/**
	 * Instantiates a new regulation group representation.
	 */

	public RegulationGroupRepresentation() {
		super();
	}

	/**
	 * Instantiates a new regulation group representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param label the label
	 * @param version the version
	 * @param creationDate the creation date
	 * @param modificationDate the modification date
	 * @param regulationstatus the regulationstatus
	 */
	public RegulationGroupRepresentation(Long entityId, String description, String label, String version, Date creationDate, Date modificationDate, StatusRepresentation regulationstatus) {
		super();
		this.entityId = entityId;
		this.description = description;
		this.label = label;
		this.version = version;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.regulationgroupstatus = regulationstatus;
	}

	/**
	 * Instantiates a new regulation group representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param label the label
	 * @param version the version
	 * @param creationDate the creation date
	 * @param modificationDate the modification date
	 * @param statusentityId the statusentity id
	 * @param statusGuiLabel the status gui label
	 * @param statusLabel the status label
	 */
	public RegulationGroupRepresentation(Long entityId, String description, String label, String version, Date creationDate, Date modificationDate, Long statusentityId, String statusGuiLabel, String statusLabel) {
		super();
		this.entityId = entityId;
		this.description = description;
		this.label = label;
		this.version = version;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.regulationgroupstatus = new StatusRepresentation(statusentityId, statusGuiLabel, statusLabel);
	}

	/** The available. */
	private boolean available = false;

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
	 * Gets the creation date.
	 * 
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the modification date.
	 * 
	 * @return the modification date
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * Sets the modification date.
	 * 
	 * @param modificationDate the new modification date
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * Sets the regulationstatus.
	 * 
	 * @param regulationgroupstatus the new regulationstatus
	 */
	public void setRegulationstatus(StatusRepresentation regulationgroupstatus) {
		this.regulationgroupstatus = regulationgroupstatus;
	}

	/**
	 * Checks if is available.
	 * 
	 * @return true, if is available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * Sets the available.
	 * 
	 * @param available the new available
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	/**
	 * Gets the regulationgroupstatus.
	 * 
	 * @return the regulationgroupstatus
	 */
	public StatusRepresentation getRegulationgroupstatus() {
		return regulationgroupstatus;
	}

	/**
	 * Sets the regulationgroupstatus.
	 * 
	 * @param regulationgroupstatus the new regulationgroupstatus
	 */
	public void setRegulationgroupstatus(StatusRepresentation regulationgroupstatus) {
		this.regulationgroupstatus = regulationgroupstatus;
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
	 * @param valid the new valid
	 */
	public void setValid(int valid) {
		this.valid = valid;
	}

	/**
	 * Checks if is show add tg.
	 * 
	 * @return true, if is show add tg
	 */
	public boolean isShowAddTg() {
		return showAddTg;
	}

	/**
	 * Sets the show add tg.
	 * 
	 * @param showAddTg the new show add tg
	 */
	public void setShowAddTg(boolean showAddTg) {
		this.showAddTg = showAddTg;
	}

	/**
	 * Gets the type approval area.
	 * 
	 * @return the type approval area
	 */
	public TypeApprovalAreaRepresentation getTypeApprovalArea() {
		return typeApprovalArea;
	}

	/**
	 * Sets the type approval area.
	 * 
	 * @param typeApprovalArea the new type approval area
	 */
	public void setTypeApprovalArea(TypeApprovalAreaRepresentation typeApprovalArea) {
		this.typeApprovalArea = typeApprovalArea;
	}

	/**
	 * Gets the emission standardfor rg.
	 * 
	 * @return the emission standardfor rg
	 */
	public EmissionStandardRepresentation getEmissionStandardforRg() {
		return emissionStandardforRg;
	}

	/**
	 * Gets the technical groups.
	 * 
	 * @return the technical groups
	 */
	public TechnicalGroupRepresentation getTechnicalGroups() {
		return technicalGroups;
	}

	/**
	 * Sets the technical groups.
	 * 
	 * @param technicalGroups the new technical groups
	 */
	public void setTechnicalGroups(TechnicalGroupRepresentation technicalGroups) {
		this.technicalGroups = technicalGroups;
	}

	/**
	 * Sets the emission standardfor rg.
	 * 
	 * @param emissionStandardforRg the new emission standardfor rg
	 */
	public void setEmissionStandardforRg(EmissionStandardRepresentation emissionStandardforRg) {
		this.emissionStandardforRg = emissionStandardforRg;
	}

	/**
	 * Gets the rg valued es dep TCL.
	 * 
	 * @return the rg valued es dep TCL
	 */
	public List<RGValuedESDependentTCLRepresentation> getRgValuedEsDepTCL() {
		return rgValuedEsDepTCL;
	}

	/**
	 * Sets the rg valued es dep TCL.
	 * 
	 * @param rgValuedEsDepTCL the new rg valued es dep TCL
	 */
	public void setRgValuedEsDepTCL(List<RGValuedESDependentTCLRepresentation> rgValuedEsDepTCL) {
		this.rgValuedEsDepTCL = rgValuedEsDepTCL;
	}

	/**
	 * Gets the wltp representation.
	 * 
	 * @return the wltp representation
	 */
	public WLTPVLowHighDataRepresentation getWltpRepresentation() {
		return wltpRepresentation;
	}

	/**
	 * Sets the wltp representation.
	 * 
	 * @param wltpRepresentation the new wltp representation
	 */
	public void setWltpRepresentation(WLTPVLowHighDataRepresentation wltpRepresentation) {
		this.wltpRepresentation = wltpRepresentation;
	}

	/**
	 * Gets the display date.
	 * 
	 * @return the display date
	 */
	public String getDisplayDate() {
		return displayDate;
	}

	/**
	 * Sets the display date.
	 * 
	 * @param displayDate the new display date
	 */
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}

	/**
	 * Checks if is adds the tg for rg.
	 * 
	 * @return true, if is adds the tg for rg
	 */
	public boolean isAddTgForRg() {
		return addTgForRg;
	}

	/**
	 * Sets the adds the tg for rg.
	 * 
	 * @param addTgForRg the new adds the tg for rg
	 */
	public void setAddTgForRg(boolean addTgForRg) {
		this.addTgForRg = addTgForRg;
	}

	/**
	 * Checks if is new rg version.
	 * 
	 * @return true, if is new rg version
	 */
	public boolean isNewRgVersion() {
		return newRgVersion;
	}

	/**
	 * Sets the new rg version.
	 * 
	 * @param newRgVersion the new new rg version
	 */
	public void setNewRgVersion(boolean newRgVersion) {
		this.newRgVersion = newRgVersion;
	}

	/**
	 * Gets the statistical rule representation.
	 * 
	 * @return the statistical rule representation
	 */
	public StatisticalCalculationRuleRepresentation getStatisticalRuleRepresentation() {
		return statisticalRuleRepresentation;
	}

	/**
	 * Sets the statistical rule representation.
	 * 
	 * @param statisticalRuleRepresentation the new statistical rule representation
	 */
	public void setStatisticalRuleRepresentation(StatisticalCalculationRuleRepresentation statisticalRuleRepresentation) {
		this.statisticalRuleRepresentation = statisticalRuleRepresentation;
	}

	/**
	 * Gets the old statistical object.
	 * 
	 * @return the old statistical object
	 */
	public StatisticalCalculationRuleRepresentation getOldStatisticalObject() {
		return oldStatisticalObject;
	}

	/**
	 * Sets the old statistical object.
	 * 
	 * @param oldStatisticalObject the new old statistical object
	 */
	public void setOldStatisticalObject(StatisticalCalculationRuleRepresentation oldStatisticalObject) {
		this.oldStatisticalObject = oldStatisticalObject;
	}

	/**
	 * @return the rgvaluedesDependantTcl
	 */

}
