package org.seedstack.pv2.domain.ruleofuses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;

@Entity
@Table(name = "PV2QTROU")
public class RuleOfUses extends BaseAggregateRoot<Long> {

	/**
	 * Rule id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long ruleId;

	/**
	 * Rule Name
	 */
	@Column(name = "NAME")
	private String name;

	/**
	 * Document link
	 */
	@Column(name = "DOC_LINK")
	private String docLink;

	/**
	 * Picto family id
	 */
	@ManyToOne
	@JoinColumn(name = "PFM_ID")
	private PictoFamily familyId;

	/**
	 * 
	 */

	public RuleOfUses() {

	}

	RuleOfUses(Long id, String name, String docLink, PictoFamily familyId) {
		this.ruleId = id;
		this.name = name;
		this.docLink = docLink;
		this.familyId = familyId;
	}

	/**
	 * @return the ruleId
	 */
	public Long getEntityId() {
		return ruleId;
	}

	/**
	 * @param ruleId
	 *            the ruleId to set
	 */
	public void setEntityId(Long ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the docLink
	 */
	public String getDocLink() {
		return docLink;
	}

	/**
	 * @param docLink
	 *            the docLink to set
	 */
	public void setDocLink(String docLink) {
		this.docLink = docLink;
	}

	/**
	 * @return the familyId
	 */
	public PictoFamily getFamilyId() {
		return familyId;
	}

	/**
	 * @param familyId
	 *            the familyId to set
	 */
	public void setFamilyId(PictoFamily familyId) {
		this.familyId = familyId;
	}

}
