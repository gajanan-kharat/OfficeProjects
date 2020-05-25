package com.inetpsa.pv2.rest.ruleofuses;

import com.inetpsa.pv2.rest.pictofamily.PictoFamilyRepresentation;

/* class: RuleOfUsesRepresentation */
public class RuleOfUsesRepresentation {

	private Long id;
	private String name;
	private String docLink;
	private PictoFamilyRepresentation familyId;

	public RuleOfUsesRepresentation() {

	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
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
	 * @param docLink the docLink to set
	 */
	public void setDocLink(String docLink) {
		this.docLink = docLink;
	}

	/**
	 * @return the familyId
	 */
	public PictoFamilyRepresentation getFamilyId() {
		return familyId;
	}

	/**
	 * @param familyId the familyId to set
	 */
	public void setFamilyId(PictoFamilyRepresentation familyId) {
		this.familyId = familyId;
	}

}
