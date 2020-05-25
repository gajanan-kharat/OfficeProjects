/*
 * Creation : May 24, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedtdl;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;

/**
 * The Class TvvValuedTvvDepTDL.
 */
@Entity
@Table(name = "COPQTVDL")
public class TvvValuedTvvDepTDL extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long entityId;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The version. */
	@Column(name = "VERSION")
	private String version;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The valued technical data. */
	@OneToMany(mappedBy = "tvvValuedTDL", cascade = CascadeType.ALL)
	private List<TvvValuedTechData> valuedTechnicalData;

	/** The tvv obj. */
	@ManyToOne
	@JoinColumn(name = "TVV_ID")
	private TVV tvvObj;

	/**
	 * Instantiates a new tvv valued tvv dep tdl.
	 */
	public TvvValuedTvvDepTDL() {
		super();
	}

	/**
	 * Instantiates a new tvv valued tvv dep tdl.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 */
	public TvvValuedTvvDepTDL(String label, String description, String version) {
		this.label = label;
		this.description = description;
		this.version = version;
	}

	/**
	 * Instantiates a new tvv valued tvv dep TDL.
	 *
	 * @param tdlObject the tdl object
	 */
	public TvvValuedTvvDepTDL(TvvValuedTvvDepTDL tdlObject) {
		this.entityId = 0;
		this.label = tdlObject.label;
		this.description = tdlObject.description;
		this.version = tdlObject.version;
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
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.domain.BaseEntity#getEntityId()
	 */
	@Override
	public Long getEntityId() {
		return this.entityId;
	}

	/**
	 * Gets the valued technical data.
	 * 
	 * @return the valued technical data
	 */
	public List<TvvValuedTechData> getValuedTechnicalData() {
		return valuedTechnicalData;
	}

	/**
	 * Sets the valued technical data.
	 * 
	 * @param valuedTechnicalData the new valued technical data
	 */
	public void setValuedTechnicalData(List<TvvValuedTechData> valuedTechnicalData) {
		this.valuedTechnicalData = valuedTechnicalData;
	}

	/**
	 * Gets the tvv obj.
	 * 
	 * @return the tvv obj
	 */
	public TVV getTvvObj() {
		return tvvObj;
	}

	/**
	 * Sets the tvv obj.
	 * 
	 * @param tvvObj the new tvv obj
	 */
	public void setTvvObj(TVV tvvObj) {
		this.tvvObj = tvvObj;
	}

	/**
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {

		StringBuilder valuedTechData = new StringBuilder();

		if (valuedTechnicalData != null && !valuedTechnicalData.isEmpty()) {
			for (TvvValuedTechData vtd : valuedTechnicalData) {
				valuedTechData.append(vtd.getLabel()).append(DomainConstants.VERSION_SEPARATOR).append(vtd.getValue());
			}
		}

		return label + DomainConstants.VERSION_SEPARATOR + version + "[" + valuedTechData.toString() + "]";
	}
}
