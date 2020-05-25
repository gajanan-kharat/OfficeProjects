package org.seedstack.pv2.infrastructure.data.ruleofuses;

import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.pv2.infrastructure.data.pictofamily.PictoFamilyDTO;


/**
 * The Class RuleOfUsesDTO.
 */
public class RuleOfUsesDTO {

	/** The id. */
	private Long id;
	
	/** The name. */
	private String name;
	
	/** The doc link. */
	private String docLink;
	
	/** The family id. */
	private PictoFamilyDTO familyId;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@MatchingEntityId
	@MatchingFactoryParameter(index = 0)
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@MatchingFactoryParameter(index = 1)
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the doc link.
	 *
	 * @return the docLink
	 */
	@MatchingFactoryParameter(index = 2)
	public String getDocLink() {
		return docLink;
	}

	/**
	 * Sets the doc link.
	 *
	 * @param docLink the docLink to set
	 */
	public void setDocLink(String docLink) {
		this.docLink = docLink;
	}

	/**
	 * Gets the family id.
	 *
	 * @return the familyId
	 */
	@MatchingFactoryParameter(index = 3)
	public PictoFamilyDTO getFamilyId() {
		return familyId;
	}

	/**
	 * Sets the family id.
	 *
	 * @param familyId the familyId to set
	 */
	public void setFamilyId(PictoFamilyDTO familyId) {
		this.familyId = familyId;
	}

}
