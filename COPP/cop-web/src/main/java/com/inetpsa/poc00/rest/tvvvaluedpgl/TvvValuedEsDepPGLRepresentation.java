/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedpgl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.common.BaseRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;

/**
 * The Class TvvValuedEsDepPGLRepresentation.
 */
public class TvvValuedEsDepPGLRepresentation extends BaseRepresentation {

	/** The entity id. */
	protected long entityId;

	/** The description. */
	private String description;

	/** The version. */
	private String version;

	/** The label. */
	private String label;
	
	/** The label. */
	private String pgLabel;
	
	/** The pollutant gas limit. */
	private List<TvvValuedPollutantLimitRepresentation> pollutantGasLimit;

	/** The emission standard. */
	private EmissionStandardRepresentation emissionStandard;

	/** The min co2 limit. */
	private Double minCo2Limit;

	/** The max co2 limit. */
	private Double maxCo2Limit;

	/** The edited values. */
	private int editedValues;

	/** The tvv obj. */
	@JsonIgnore
	private TvvRepresentation tvvObj;

	/**
	 * Instantiates a new tvv valued es dep pgl representation.
	 */
	public TvvValuedEsDepPGLRepresentation() {
		super();
	}

	/**
	 * Instantiates a new tvv valued es dep pgl representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 * @param emsId the ems id
	 */
	public TvvValuedEsDepPGLRepresentation(long entityId, String description, String version, String label, long emsId) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;
		this.emissionStandard = new EmissionStandardRepresentation(emsId);

	}

	/**
	 * Instantiates a new tvv valued es dep pgl representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 */
	public TvvValuedEsDepPGLRepresentation(long entityId, String description, String version, String label) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;

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
	 * Gets the pollutant gas limit.
	 * 
	 * @return the pollutant gas limit
	 */
	public List<TvvValuedPollutantLimitRepresentation> getPollutantGasLimit() {
		return pollutantGasLimit;
	}

	/**
	 * Sets the pollutant gas limit.
	 * 
	 * @param dataList the new pollutant gas limit
	 */
	public void setPollutantGasLimit(List<TvvValuedPollutantLimitRepresentation> dataList) {
		this.pollutantGasLimit = dataList;
	}

	/**
	 * Gets the emission standard.
	 * 
	 * @return the emission standard
	 */
	public EmissionStandardRepresentation getEmissionStandard() {
		return emissionStandard;
	}

	/**
	 * Sets the emission standard.
	 * 
	 * @param emissionStandard the new emission standard
	 */
	public void setEmissionStandard(EmissionStandardRepresentation emissionStandard) {
		this.emissionStandard = emissionStandard;
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
	 * Gets the tvv obj.
	 * 
	 * @return the tvv obj
	 */
	public TvvRepresentation getTvvObj() {
		return tvvObj;
	}

	/**
	 * Sets the tvv obj.
	 * 
	 * @param tvvObj the new tvv obj
	 */
	public void setTvvObj(TvvRepresentation tvvObj) {
		this.tvvObj = tvvObj;
	}

	/**
	 * Gets the max co2 limit.
	 * 
	 * @return the max co2 limit
	 */
	public Double getMaxCo2Limit() {
		return maxCo2Limit;
	}

	/**
	 * Sets the max co2 limit.
	 * 
	 * @param maxCo2Limit the new max co2 limit
	 */
	public void setMaxCo2Limit(double maxCo2Limit) {
		this.maxCo2Limit = maxCo2Limit;
	}

	/**
	 * Gets the min co2 limit.
	 * 
	 * @return the min co2 limit
	 */
	public Double getMinCo2Limit() {
		return minCo2Limit;
	}

	/**
	 * Sets the min co2 limit.
	 * 
	 * @param minCo2Limit the new min co2 limit
	 */
	public void setMinCo2Limit(double minCo2Limit) {
		this.minCo2Limit = minCo2Limit;
	}

	/**
	 * Getter pgLabel.
	 * 
	 * @return the pgLabel
	 */
	public String getPgLabel() {
		return pgLabel;
	}

	/**
	 * Setter pgLabel.
	 * 
	 * @param pgLabel the pgLabel to set
	 */
	public void setPgLabel(String pgLabel) {
		this.pgLabel = pgLabel;
	}

}
