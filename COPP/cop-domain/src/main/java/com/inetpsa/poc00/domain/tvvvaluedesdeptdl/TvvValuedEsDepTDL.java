/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedesdeptdl;

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
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedtd.TvvValuedTechData;

/**
 * The Class TvvValuedEsDepTDL.
 */
@Entity
@Table(name = "COPQTVLD")
public class TvvValuedEsDepTDL extends BaseAggregateRoot<Long> {
	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long entityId;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The version. */
	@Column(name = "VERSION")
	private String version;

	/** The generic technical data. */
	@OneToMany(mappedBy = "tvvValuedEsTDL", cascade = CascadeType.ALL)
	private List<TvvValuedTechData> genericTechnicalData;

	/** The emission standard. */
	@ManyToOne
	@JoinColumn(name = "EMS_ID")
	private EmissionStandard emissionStandard;

	/** The tvv obj. */
	@ManyToOne
	@JoinColumn(name = "TVV_ID")
	private TVV tvvObj;

	/**
	 * Constructor are in visibility package because only Factories can create aggregates and entities.
	 * <p/>
	 * Factories are in the same package so he can access package visibility.
	 */
	public TvvValuedEsDepTDL() {
		super();
	}

	/**
	 * Instantiates a new emission std dep tdl.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 */
	public TvvValuedEsDepTDL(String label, String description, String version) {
		this.label = label;
		this.description = description;
		this.version = version;
	}

	public TvvValuedEsDepTDL(TvvValuedEsDepTDL tvvValuedEsDepTDL) {
		this.entityId = 0;
		this.label = tvvValuedEsDepTDL.label;
		this.description = tvvValuedEsDepTDL.description;
		this.version = tvvValuedEsDepTDL.version;
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
	 * {@inheritDoc}
	 * 
	 * @see org.seedstack.business.domain.BaseEntity#getEntityId()
	 */
	@Override
	public Long getEntityId() {
		return entityId;
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
	 * Gets the generic technical data.
	 * 
	 * @return the generic technical data
	 */
	public List<TvvValuedTechData> getGenericTechnicalData() {
		return genericTechnicalData;
	}

	/**
	 * Sets the generic technical data.
	 * 
	 * @param genericTechnicalData the new generic technical data
	 */
	public void setGenericTechnicalData(List<TvvValuedTechData> genericTechnicalData) {
		this.genericTechnicalData = genericTechnicalData;
	}

	/**
	 * Gets the emission standard.
	 * 
	 * @return the emission standard
	 */
	public EmissionStandard getEmissionStandard() {
		return emissionStandard;
	}

	/**
	 * Sets the emission standard.
	 * 
	 * @param emissionStandard the new emission standard
	 */
	public void setEmissionStandard(EmissionStandard emissionStandard) {
		this.emissionStandard = emissionStandard;
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

		StringBuilder temp = new StringBuilder();

		if (genericTechnicalData != null && !genericTechnicalData.isEmpty()) {
			for (TvvValuedTechData obj : genericTechnicalData) {
				temp.append(obj.getLabel()).append(DomainConstants.VERSION_SEPARATOR).append(obj.getValue()).append(" ");
			}
		}

		return label + DomainConstants.VERSION_SEPARATOR + version + "[" + temp.toString() + "]";
	}
}
