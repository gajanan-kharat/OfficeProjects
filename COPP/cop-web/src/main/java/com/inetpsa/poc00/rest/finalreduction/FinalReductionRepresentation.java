package com.inetpsa.poc00.rest.finalreduction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;

/**
 * The Class FinalReductionRepresentation.
 */
public class FinalReductionRepresentation {

	/** The rule id. */
	private Long ruleId;

	/** The final reductionlabel. */
	private String finalReductionlabel;

	/** The Final reduction dcw. */
	private String finalReductionDCW;

	/** The Final reduction kmat. */
	private String finalReductionKmat;

	/** The Final reduction fr lable. */
	private String finalReductionFRLable;

	/** The Final reduction tvv rule id. */
	private Long finalReductionTvvRuleId;

	/** The entity id. */
	private Long entityId;

	/** The tvv rule id. */
	@JsonIgnore
	private GenomeTVVRule tvvRuleId;

	/**
	 * Instantiates a new final reduction representation.
	 * 
	 * @param lable the lable
	 * @param kmat the kmat
	 * @param tvvRuleId the tvv rule id
	 * @param frLabel the FR lable
	 * @param finalReductionlabel the final reductionlabel
	 * @param entityId the entity id
	 */
	public FinalReductionRepresentation(String lable, String kmat, Long tvvRuleId, String frLabel, String finalReductionlabel, Long entityId) {

		this.finalReductionDCW = lable;
		this.finalReductionKmat = kmat;
		this.finalReductionFRLable = frLabel;
		this.finalReductionTvvRuleId = tvvRuleId;
		if (finalReductionlabel == null) {
			finalReductionlabel = frLabel;
		}
		this.finalReductionlabel = finalReductionlabel;
		this.entityId = entityId;
	}

	/**
	 * Instantiates a new final reduction representation.
	 * 
	 * @param entityId the entity id
	 */
	public FinalReductionRepresentation(Long entityId) {

		this.entityId = entityId;
	}

	/**
	 * Instantiates a new final reduction representation.
	 * 
	 * @param finalReductionlabel the final reductionlabel
	 * @param entityId the entity id
	 */
	public FinalReductionRepresentation(String finalReductionlabel, Long entityId) {

		this.finalReductionlabel = finalReductionlabel;
		this.entityId = entityId;
	}

	/**
	 * Instantiates a new final reduction representation.
	 */
	public FinalReductionRepresentation() {
		// Default Constructor
	}

	/**
	 * Gets the rule id.
	 * 
	 * @return the rule id
	 */
	public Long getRuleId() {
		return ruleId;
	}

	/**
	 * Sets the rule id.
	 * 
	 * @param ruleId the new rule id
	 */
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * Gets the final reductionlabel.
	 * 
	 * @return the final reductionlabel
	 */
	public String getFinalReductionlabel() {
		return finalReductionlabel;
	}

	/**
	 * Sets the final reductionlabel.
	 * 
	 * @param finalReductionlabel the new final reductionlabel
	 */
	public void setFinalReductionlabel(String finalReductionlabel) {
		this.finalReductionlabel = finalReductionlabel;
	}

	/**
	 * Gets the tvv rule id.
	 * 
	 * @return the tvv rule id
	 */
	public GenomeTVVRule getTvvRuleId() {
		return tvvRuleId;
	}

	/**
	 * Sets the tvv rule id.
	 * 
	 * @param tvvRuleId the new tvv rule id
	 */
	public void setTvvRuleId(GenomeTVVRule tvvRuleId) {
		this.tvvRuleId = tvvRuleId;
	}

	/**
	 * Gets the final reduction dcw.
	 * 
	 * @return the final reduction dcw
	 */
	public String getFinalReductionDCW() {
		return finalReductionDCW;
	}

	/**
	 * Sets the final reduction dcw.
	 * 
	 * @param finalReductionDCW the new final reduction dcw
	 */
	public void setFinalReductionDCW(String finalReductionDCW) {
		this.finalReductionDCW = finalReductionDCW;
	}

	/**
	 * Gets the final reduction kmat.
	 * 
	 * @return the final reduction kmat
	 */
	public String getFinalReductionKmat() {
		return finalReductionKmat;
	}

	/**
	 * Sets the final reduction kmat.
	 * 
	 * @param finalReductionKmat the new final reduction kmat
	 */
	public void setFinalReductionKmat(String finalReductionKmat) {
		this.finalReductionKmat = finalReductionKmat;
	}

	/**
	 * Gets the final reduction fr lable.
	 * 
	 * @return the final reduction fr lable
	 */
	public String getFinalReductionFRLable() {
		return finalReductionFRLable;
	}

	/**
	 * Sets the final reduction fr lable.
	 * 
	 * @param finalReductionFRLable the new final reduction fr lable
	 */
	public void setFinalReductionFRLable(String finalReductionFRLable) {
		this.finalReductionFRLable = finalReductionFRLable;
	}

	/**
	 * Gets the final reduction tvv rule id.
	 * 
	 * @return the final reduction tvv rule id
	 */
	public Long getFinalReductionTvvRuleId() {
		return finalReductionTvvRuleId;
	}

	/**
	 * Sets the final reduction tvv rule id.
	 * 
	 * @param finalReductionTvvRuleId the new final reduction tvv rule id
	 */
	public void setFinalReductionTvvRuleId(Long finalReductionTvvRuleId) {
		this.finalReductionTvvRuleId = finalReductionTvvRuleId;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
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

}
