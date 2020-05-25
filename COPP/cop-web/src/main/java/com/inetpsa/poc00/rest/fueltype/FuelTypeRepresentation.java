package com.inetpsa.poc00.rest.fueltype;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;

/**
 * The Class FuelTypeRepresentation.
 */
public class FuelTypeRepresentation {

	/** The entity id. */
	private Long entityId;

	/** The rule id. */
	private Long ruleId;

	/** The fuel type lable. */
	private String fuelTypeLable;

	/** The dcd. */
	private String dcd;

	/** The carbu kmat. */
	private String carbuKmat;

	/** The carbu FR lable. */
	private String carbuFRLable;

	/** The carbu tvv rule id. */
	private Long carbuTvvRuleId;

	/** The tvv rule id. */
	@JsonIgnore
	private GenomeTVVRule tvvRuleId;

	/**
	 * Instantiates a new fuel type representation.
	 *
	 * @param entityId the entity id
	 * @param fuelTypeLable the fuel type lable
	 */
	public FuelTypeRepresentation(Long entityId, String fuelTypeLable) {
		this.entityId = entityId;
		this.fuelTypeLable = fuelTypeLable;
	}

	/**
	 * Instantiates a new fuel type representation.
	 *
	 * @param lable the lable
	 * @param kmat the kmat
	 * @param frLabel the FR lable
	 * @param fuelTypeLable the fuel type lable
	 * @param tvvRuleId the tvv rule id
	 * @param entityId the entity id
	 */
	public FuelTypeRepresentation(String lable, String kmat, String frLabel, String fuelTypeLable, Long tvvRuleId, Long entityId) {

		this.dcd = lable;
		this.carbuKmat = kmat;
		this.carbuFRLable = frLabel;
		this.carbuTvvRuleId = tvvRuleId;
		if (fuelTypeLable == null) {
			fuelTypeLable = frLabel;
		}
		this.fuelTypeLable = fuelTypeLable;
		this.entityId = entityId;
	}

	/**
	 * Instantiates a new fuel type representation.
	 *
	 * @param fuelTypeLable the fuel type lable
	 * @param entityId the entity id
	 */
	public FuelTypeRepresentation(String fuelTypeLable, Long entityId) {

		this.fuelTypeLable = fuelTypeLable;
		this.entityId = entityId;
	}

	/**
	 * Instantiates a new fuel type representation.
	 */
	public FuelTypeRepresentation() {
		super();
	}

	/**
	 * Instantiates a new fuel type representation.
	 *
	 * @param label the label
	 */
	public FuelTypeRepresentation(Long entityId) {
		this.entityId = entityId;

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

	/**
	 * Gets the fuel type lable.
	 *
	 * @return the fuel type lable
	 */
	public String getFuelTypeLable() {
		return fuelTypeLable;
	}

	/**
	 * Sets the fuel type lable.
	 *
	 * @param fuelTypeLable the new fuel type lable
	 */
	public void setFuelTypeLable(String fuelTypeLable) {
		this.fuelTypeLable = fuelTypeLable;
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
	 * Gets the dcd.
	 *
	 * @return the dcd
	 */
	public String getDcd() {
		return dcd;
	}

	/**
	 * Sets the dcd.
	 *
	 * @param dcd the new dcd
	 */
	public void setDcd(String dcd) {
		this.dcd = dcd;
	}

	/**
	 * Gets the carbu kmat.
	 *
	 * @return the carbu kmat
	 */
	public String getCarbuKmat() {
		return carbuKmat;
	}

	/**
	 * Sets the carbu kmat.
	 *
	 * @param carbuKmat the new carbu kmat
	 */
	public void setCarbuKmat(String carbuKmat) {
		this.carbuKmat = carbuKmat;
	}

	/**
	 * Gets the carbu FR lable.
	 *
	 * @return the carbu FR lable
	 */
	public String getCarbuFRLable() {
		return carbuFRLable;
	}

	/**
	 * Sets the carbu FR lable.
	 *
	 * @param carbuFRLable the new carbu FR lable
	 */
	public void setCarbuFRLable(String carbuFRLable) {
		this.carbuFRLable = carbuFRLable;
	}

	/**
	 * Gets the carbu tvv rule id.
	 *
	 * @return the carbu tvv rule id
	 */
	public Long getCarbuTvvRuleId() {
		return carbuTvvRuleId;
	}

	/**
	 * Sets the carbu tvv rule id.
	 *
	 * @param carbuTvvRuleId the new carbu tvv rule id
	 */
	public void setCarbuTvvRuleId(Long carbuTvvRuleId) {
		this.carbuTvvRuleId = carbuTvvRuleId;
	}

}
