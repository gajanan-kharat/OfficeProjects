/*
 * Creation : May 30, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedfcl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;

import com.inetpsa.poc00.DomainConstants;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.factorcoeffapplicationtype.FactorCoeffApplicationType;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedfactorcoeff.TvvValuedFactorCoefficents;

/**
 * The Class TvvValuedEsDepFCL.
 */
@Entity
@Table(name = "COPQTVFL")
public class TvvValuedEsDepFCL extends BaseAggregateRoot<Long> {

	/** The entity id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long entityId;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The label. */
	@Column(name = "LABEL")
	private String label;

	/** The version. */
	@Column(name = "VERSION")
	private String version;

	/** The status. */
	@Column(name = "STATUS")
	String status;

	/** The factor or coeffiecients. */
	@OneToMany(mappedBy = "fcList", cascade = CascadeType.ALL)
	private List<TvvValuedFactorCoefficents> factorOrCoeffiecients;

	/** The fc application types. */
	@ManyToMany
	@JoinTable(name = "COPQTMFA", joinColumns = { @JoinColumn(name = "FCL_ID", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "FCL_APP_ID", nullable = false) })
	private Set<FactorCoeffApplicationType> fcApplicationTypes = new HashSet<FactorCoeffApplicationType>();

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
	public TvvValuedEsDepFCL() {
		super();
	}

	/**
	 * Instantiates a new tvv valued es dep fcl.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 */
	public TvvValuedEsDepFCL(String label, String description, String version) {
		this.label = label;
		this.description = description;
		this.version = version;
	}

	/**
	 * Instantiates a new tvv valued es dep fcl.
	 *
	 * @param tvvValuedEsDepFCL the tvv valued es dep fcl
	 */
	public TvvValuedEsDepFCL(TvvValuedEsDepFCL tvvValuedEsDepFCL) {
		this.entityId = 0;
		this.label = tvvValuedEsDepFCL.label;
		this.description = tvvValuedEsDepFCL.description;
		this.version = tvvValuedEsDepFCL.version;

	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * Sets the fc application types.
	 * 
	 * @param fcApplicationTypes the new fc application types
	 */
	public void setFcApplicationTypes(Set<FactorCoeffApplicationType> fcApplicationTypes) {
		this.fcApplicationTypes = fcApplicationTypes;
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
	 * Sets the fc application type.
	 * 
	 * @param fcApplicationTypes the new fc application type
	 */
	public void setFcApplicationType(Set<FactorCoeffApplicationType> fcApplicationTypes) {
		this.fcApplicationTypes = fcApplicationTypes;
	}

	/**
	 * Gets the fc application types.
	 * 
	 * @return the fc application types
	 */
	public Set<FactorCoeffApplicationType> getFcApplicationTypes() {
		return this.fcApplicationTypes;
	}

	/**
	 * Gets the emission standard.
	 * 
	 * @return the emission standard
	 */
	public EmissionStandard getEmissionStandard() {
		return this.emissionStandard;
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
	 * Gets the factor or coeffiecients.
	 * 
	 * @return the factor or coeffiecients
	 */
	public List<TvvValuedFactorCoefficents> getFactorOrCoeffiecients() {
		return factorOrCoeffiecients;
	}

	/**
	 * Sets the factor or coeffiecients.
	 * 
	 * @param factorOrCoeffiecients the new factor or coeffiecients
	 */
	public void setFactorOrCoeffiecients(List<TvvValuedFactorCoefficents> factorOrCoeffiecients) {
		this.factorOrCoeffiecients = factorOrCoeffiecients;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seedstack.business.domain.BaseEntity#toString()
	 */
	@Override
	public String toString() {

		StringBuilder temp = new StringBuilder();

		if (factorOrCoeffiecients != null && !factorOrCoeffiecients.isEmpty()) {
			for (TvvValuedFactorCoefficents obj : factorOrCoeffiecients) {
				temp.append(obj.getFclabel()).append(DomainConstants.VERSION_SEPARATOR).append(obj.getDefaultValue()).append(" ");
			}
		}

		return label + DomainConstants.VERSION_SEPARATOR + version + "[" + temp.toString() + "]";
	}

}
