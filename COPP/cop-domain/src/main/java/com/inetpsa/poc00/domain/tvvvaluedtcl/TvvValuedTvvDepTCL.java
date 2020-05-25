/*
 * Creation : May 24, 2016
 */
package com.inetpsa.poc00.domain.tvvvaluedtcl;

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
import com.inetpsa.poc00.domain.testconditioncomment.TestConditionComment;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.domain.tvvvaluedtc.TvvValuedTestCondition;

/**
 * The Class TvvValuedTvvDepTCL.
 */
@Entity
@Table(name = "COPQTVCL")
public class TvvValuedTvvDepTCL extends BaseAggregateRoot<Long> {

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
	@OneToMany(mappedBy = "tvvValuedTCL", cascade = CascadeType.ALL)
	private List<TvvValuedTestCondition> valuedTestCondition = new ArrayList<>();

	/** The valued test condition. */
	@OneToMany(mappedBy = "tvvVldTvvDepTCL", cascade = CascadeType.ALL)
	private List<TestConditionComment> testConditionComment = new ArrayList<>();

	/** The tvv obj. */
	@ManyToOne
	@JoinColumn(name = "TVV_ID")
	private TVV tvvObj;

	/**
	 * Instantiates a new tvv valued tvv dep tcl.
	 * 
	 * @param label the label
	 * @param description the description
	 * @param version the version
	 */
	TvvValuedTvvDepTCL(String label, String description, String version) {
		this.label = label;
		this.description = description;
		this.version = version;
	}

	/**
	 * Instantiates a new tvv valued tvv dep tcl.
	 */
	public TvvValuedTvvDepTCL() {
		// Default Constructor
	}

	/**
	 * Instantiates a new tvv valued tvv dep TCL.
	 *
	 * @param tvvValuedTvvDepTCL the tvv valued tvv dep TCL
	 */
	TvvValuedTvvDepTCL(TvvValuedTvvDepTCL tvvValuedTvvDepTCL) {
		this.entityId = 0;
		this.label = tvvValuedTvvDepTCL.label;
		this.description = tvvValuedTvvDepTCL.description;
		this.version = tvvValuedTvvDepTCL.version;
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
	 * Gets the valued test condition.
	 *
	 * @return the valued test condition
	 */
	public List<TvvValuedTestCondition> getValuedTestCondition() {
		return valuedTestCondition;
	}

	/**
	 * Sets the valued test condition.
	 *
	 * @param valuedTestCondition the new valued test condition
	 */
	public void setValuedTestCondition(List<TvvValuedTestCondition> valuedTestCondition) {
		this.valuedTestCondition = valuedTestCondition;
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
