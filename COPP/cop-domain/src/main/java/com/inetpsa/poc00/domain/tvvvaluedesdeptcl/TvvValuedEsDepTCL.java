/*
 * Creation : May 27, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedesdeptcl;

import java.util.ArrayList;
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
import com.inetpsa.poc00.domain.testconditioncomment.TestConditionComment;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition;

/**
 * The Class TvvValuedEsDepTCL.
 */
@Entity
@Table(name = "COPQTVLC")
public class TvvValuedEsDepTCL extends BaseAggregateRoot<Long> {

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

	/** The valued test condition. */
	@OneToMany(mappedBy = "tvvValuedEsTCL", cascade = CascadeType.ALL)
	private List<TvvValuedTestCondition> valuedTestCondition = new ArrayList<>();

	/** The test condition comment. */
	@OneToMany(mappedBy = "tvvVldEsDepTCL", cascade = CascadeType.ALL)
	private List<TestConditionComment> testConditionComment = new ArrayList<>();

	/** The tvv obj. */
	@ManyToOne
	@JoinColumn(name = "TVV_ID")
	private TVV tvvObj;

	/** The emission standard. */
	@ManyToOne
	@JoinColumn(name = "EMS_ID")
	private EmissionStandard emissionStandard;

	/**
	 * Instantiates a new tvv valued es dep tcl.
	 */
	public TvvValuedEsDepTCL() {
		super();
	}

	/**
	 * Instantiates a new tvv valued es dep tcl.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 */
	public TvvValuedEsDepTCL(String label, String description, String version) {
		this.label = label;
		this.description = description;
		this.version = version;
	}

	/**
	 * Instantiates a new tvv valued es dep TCL.
	 *
	 * @param tvvValuedEsDepTCL the tvv valued es dep TCL
	 */
	public TvvValuedEsDepTCL(TvvValuedEsDepTCL tvvValuedEsDepTCL) {
		this.entityId = 0;
		this.label = tvvValuedEsDepTCL.label;
		this.description = tvvValuedEsDepTCL.description;
		this.version = tvvValuedEsDepTCL.version;
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
	 * Gets the generic test condition.
	 * 
	 * @return the generic test condition
	 */
	public List<TvvValuedTestCondition> getGenericTestCondition() {
		return valuedTestCondition;
	}

	/**
	 * Sets the generic test condition.
	 * 
	 * @param valuedTestCondition the new generic test condition
	 */
	public void setGenericTestCondition(List<TvvValuedTestCondition> valuedTestCondition) {
		this.valuedTestCondition = valuedTestCondition;
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
	 * Getter testConditionComment.
	 *
	 * @return the testConditionComment
	 */
	public List<TestConditionComment> getTestConditionComment() {
		return testConditionComment;
	}

	/**
	 * Setter testConditionComment.
	 *
	 * @param testConditionComment the testConditionComment to set
	 */
	public void setTestConditionComment(List<TestConditionComment> testConditionComment) {
		this.testConditionComment = testConditionComment;
	}

	/**
	 * to String implementation.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {

		StringBuilder temp = new StringBuilder();

		if (valuedTestCondition != null && !valuedTestCondition.isEmpty()) {
			for (TvvValuedTestCondition obj : valuedTestCondition) {
				temp.append(obj.getLabel()).append(DomainConstants.VERSION_SEPARATOR).append(obj.getDefaultValue()).append(" ");
			}
		}

		return label + DomainConstants.VERSION_SEPARATOR + version + "[" + temp.toString() + "]";
	}

}
