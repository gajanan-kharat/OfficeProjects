/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.rest.tvvvaluedfcl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.emsdepfcl.FactorCoeffApplicationTypeRepresentation;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;

/**
 * The Class TvvValuedEsDepFCLRepresentation.
 */
public class TvvValuedEsDepFCLRepresentation {
	/** The entity id. */
	protected long entityId;

	/** The description. */
	private String description;

	/** The label. */
	private String label;

	/** The version. */
	private String version;

	/** The fc application types. */
	private Set<FactorCoeffApplicationTypeRepresentation> fcApplicationTypes = new HashSet<FactorCoeffApplicationTypeRepresentation>();
	
	/** The factor or coeffiecients. */
	private List<TvvValuedFactorCoefficentsRepresentation> factorOrCoeffiecients;

	/** The emission standard. */

	private EmissionStandardRepresentation emissionStandard;

	/** The tvv obj. */
	@JsonIgnore
	private TvvRepresentation tvvObj;

	/**
	 * Instantiates a new factor coefficent list representation.
	 */
	public TvvValuedEsDepFCLRepresentation() {
		super();
	}

	/**
	 * Instantiates a new factor coefficent list representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 */
	public TvvValuedEsDepFCLRepresentation(long entityId, String description, String version, String label) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;
	}

	/**
	 * Instantiates a new factor coefficent list representation.
	 * 
	 * @param entityId the entity id
	 * @param description the description
	 * @param version the version
	 * @param label the label
	 * @param emsId the ems id
	 */
	public TvvValuedEsDepFCLRepresentation(long entityId, String description, String version, String label, long emsId) {
		this.entityId = entityId;
		this.description = description;
		this.version = version;
		this.label = label;
		this.emissionStandard = new EmissionStandardRepresentation(emsId);

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
	 * Gets the factor or coeffiecients.
	 * 
	 * @return the factor or coeffiecients
	 */
	public List<TvvValuedFactorCoefficentsRepresentation> getFactorOrCoeffiecients() {
		return factorOrCoeffiecients;
	}

	/**
	 * Sets the factor or coeffiecients.
	 * 
	 * @param factorOrCoeffiecients the new factor or coeffiecients
	 */
	public void setFactorOrCoeffiecients(List<TvvValuedFactorCoefficentsRepresentation> factorOrCoeffiecients) {
		this.factorOrCoeffiecients = factorOrCoeffiecients;
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
	 * Gets the fc application types.
	 * 
	 * @return the fc application types
	 */
	public Set<FactorCoeffApplicationTypeRepresentation> getFcApplicationTypes() {
		return fcApplicationTypes;
	}

	/**
	 * Sets the fc application types.
	 * 
	 * @param fcApplicationTypes the new fc application types
	 */
	public void setFcApplicationTypes(Set<FactorCoeffApplicationTypeRepresentation> fcApplicationTypes) {
		this.fcApplicationTypes = fcApplicationTypes;
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

}
